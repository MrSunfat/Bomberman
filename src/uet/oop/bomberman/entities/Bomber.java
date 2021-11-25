package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.BombHandle.Bomb;
import uet.oop.bomberman.entities.Items.*;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.soundEffect.Sound;

import java.util.List;

public class Bomber extends Entity {
    public int speed = 2;
    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }
    public Bomber(){}
    public int framePlayer = 0, intervalPlayer = 5, indexAnimPlayer = 0;
    public static Image[] animUpPlayer, animDownPlayer, animLeftPlayer, animRightPlayer;
    public static boolean isPlayerLive = true;
    public boolean canRevival;
    public int frameToLive = 0, animRevival = 0;
    public Sound sound = new Sound();


    public Image[] revivalPlayer() {
        return new Image[] {
          Sprite.grass.getFxImage(),
          Sprite.player_down.getFxImage(),
          Sprite.grass.getFxImage()
        };
    }
    public Image[] upImagePlayer() {
        animUpPlayer = new Image[] {
                Sprite.player_up.getFxImage(),
                Sprite.player_up_1.getFxImage(),
                Sprite.player_up_2.getFxImage()
        };
        return animUpPlayer;
    }

    public Image[] downImagePlayer() {
        animDownPlayer = new Image[] {
                Sprite.player_down.getFxImage(),
                Sprite.player_down_1.getFxImage(),
                Sprite.player_down_2.getFxImage()
        };
        return animDownPlayer;
    }

    public Image[] leftImagePlayer() {
        animLeftPlayer = new Image[] {
                Sprite.player_left.getFxImage(),
                Sprite.player_left_1.getFxImage(),
                Sprite.player_left_2.getFxImage()
        };
        return animLeftPlayer;
    }

    public Image[] rightImagePlayer() {
        animRightPlayer = new Image[] {
                Sprite.player_right.getFxImage(),
                Sprite.player_right_1.getFxImage(),
                Sprite.player_right_2.getFxImage()
        };
        return animRightPlayer;
    }

    @Override
    public void update() {

        if(timeStop.isTimeStop) {
            timeStop.timeStopCount++;
            if(timeStop.timeStopCount == 600) {
                timeStop.isTimeStop = false;
                timeStop.timeStopCount = 0;
            }
        }


        if(canRevival) {
            x = 32;
            y = 32;
            frameToLive++;
            if(frameToLive > 20) {
                frameToLive = 0;
                animRevival++;
                if(animRevival > 2) {
                    animRevival = 0;
                    isLive = true;
                    canRevival = false;
                    img = Sprite.player_down.getFxImage();
                }
            }
            img = revivalPlayer()[animRevival];
            return;
        }

        if (deadBomber()) {
            if(isPlayerLive) {
                sound.soundEffect("dead.mp3");
                isPlayerLive = false;
            }
            moving = false;
            isLive = false;
        }

        if(isLive) {
            moving = false;

            if(itemIndexReceived() != -1) {
                Item item = BombermanGame.getItems().get(itemIndexReceived());
                int itemX = BombermanGame.getItems().get(itemIndexReceived()).getX() / 32;
                int itemY = BombermanGame.getItems().get(itemIndexReceived()).getY() / 32;
                if(!(item instanceof Portal)) {
                    sound.soundEffect("Item.wav");
                    BombermanGame.getStillObjects().set(31 * itemY + itemX, new Grass(itemX, itemY, Sprite.grass.getFxImage()));
                }
                if (item instanceof SpeedItem) {
                    speed++;
                    System.out.println("Speed: " + speed);
                }
                if (item instanceof BombItem) {
                    Bomb.bombNum++;
                    System.out.println("Bomb: " + Bomb.bombNum);
                }
                if (item instanceof FlameItem) {
                    Bomb.bombRate++;
                    System.out.println("Flame: " + Bomb.bombRate);
                }
                if (item instanceof LiveItem) {
                   playerLifeNumb++;
                    sound.soundEffect("liveup.mp3");
                    System.out.println("Live: " + playerLifeNumb);
                }
                if (item instanceof timeStop) {
                    sound.soundEffect("timestop.mp3");
                    for(int i = 0; i < BombermanGame.getMobs().size(); i++) {
                        BombermanGame.getMobs().get(i).setSpeed(0);
                    }
                    timeStop.isTimeStop = true;
                }

                if (item instanceof Portal && BombermanGame.mobs.isEmpty()) {
                    Portal.isPortal = true;
                }

                if(!(item instanceof Portal)) {
                    BombermanGame.getItems().remove(itemIndexReceived());
                }
            }

            if(up && canMove(this.x, this.y - speed)) {
                this.y -= speed;
                moving = true;
            }
            if(down && canMove(this.x, this.y + speed)) {
                this.y += speed;
                moving = true;
            }
            if(left && canMove(this.x - speed, this.y)) {
                this.x -= speed;
                moving = true;
            }
            if(right && canMove(this.x + speed, this.y)) {
                this.x += speed;
                moving = true;
            }

            if(moving) {
                framePlayer++;
                if(framePlayer > intervalPlayer) {
                    framePlayer = 0;
                    indexAnimPlayer++;
                    if(indexAnimPlayer > 2) {
                        indexAnimPlayer = 0;
                    }
                }
                if (right) {
                    img = rightImagePlayer()[indexAnimPlayer];
                } else if(left) {
                    img = leftImagePlayer()[indexAnimPlayer];
                } else if(up) {
                    img = upImagePlayer()[indexAnimPlayer];
                } else if(down) {
                    img = downImagePlayer()[indexAnimPlayer];
                }
            } else {
                img = downImagePlayer()[1];
            }
        } else {
            img = Sprite.player_dead1.getFxImage();
            framePlayer++;
            if (framePlayer > 30 && playerLifeNumb != 0) {
                isPlayerLive = true;
                canRevival = true;
                playerLifeNumb--;
                System.out.println("Live: " + playerLifeNumb);
                framePlayer = 0;
            } else if(framePlayer > 30) {
                BombermanGame.getEntities().remove(this);
                framePlayer = 0;
            }
        }

//        System.out.println(this.x + "----"+ this.y);
    }


    public boolean canMove(int nextX, int nextY) {

        int nextX_1 = nextX / size;
        int nextY_1 = nextY / size;

        int nextX_2 = (nextX + size - 3) / size;
        int nextY_2 = nextY / size;

        int nextX_3 = nextX / size;
        int nextY_3 = (nextY + size - 3) / size;

        int nextX_4 = (nextX + size - 3) / size;
        int nextY_4 = (nextY + size - 3) / size;

        List<Entity> objects = BombermanGame.getStillObjects();
        return !((objects.get(31*nextY_1 + nextX_1) instanceof Wall
                || objects.get(31*nextY_1 + nextX_1) instanceof Brick
                || isBomb(nextX_1, nextY_1))

                || (objects.get(31*nextY_2 + nextX_2) instanceof Wall
                || objects.get(31*nextY_2 + nextX_2) instanceof Brick
                || isBomb(nextX_2, nextY_2))

                || (objects.get(31*nextY_3 + nextX_3) instanceof Wall
                || objects.get(31*nextY_3 + nextX_3) instanceof Brick
                || isBomb(nextX_3, nextY_3))

                || (objects.get(31*nextY_4 + nextX_4) instanceof Wall
                || objects.get(31*nextY_4 + nextX_4) instanceof Brick
                || isBomb(nextX_4, nextY_4)));
    }

    public int itemIndexReceived() {
        int size = Sprite.SCALED_SIZE;

        int x1 = x / size;
        int y1 = y / size;

        int x2 = (x + size - 3) / size;
        int y2 = y / size;

        int x3 = x / size;
        int y3 = (y + size - 3) / size;

        int x4 = (x + size - 3) / size;
        int y4 = (y + size - 3) / size;

        if(Item.itemIndex(x1, y1) != -1)
            return Item.itemIndex(x1, y1);
        if(Item.itemIndex(x2, y2) != -1)
            return Item.itemIndex(x2, y2);
        if(Item.itemIndex(x3, y3) != -1)
            return Item.itemIndex(x3, y3);
        if(Item.itemIndex(x4, y4) != -1)
            return Item.itemIndex(x4, y4);
        return -1;
    }

    public boolean deadBomber() {

        int x1 = x / size;
        int y1 = y / size;

        int x2 = (x + size - 1) / size;
        int y2 = y / size;

        int x3 = x / size;
        int y3 = (y + size - 1) / size;

        int x4 = (x + size - 1) / size;
        int y4 = (y + size - 1) / size;

        return isExplode(x1, y1) || isExplode(x2, y2) || isExplode(x3, y3) || isExplode(x4, y4)
                || isMob(x1, y1) || isMob(x2, y2) || isMob(x3, y3) || isMob(x4, y4);
    }

    public boolean isMob(int posX, int posY) {
        for(int i = 0; i < BombermanGame.getMobs().size(); i++) {
            if((BombermanGame.getMobs().get(i).getX() + 24) / 32 == posX
                && (BombermanGame.getMobs().get(i).getY() + 24) / 32 == posY) {
                return true;
            }
        }
        return false;
    }

    public boolean isBomb(int posX, int posY) {
        for (int i = 0; i < BombermanGame.bombs.size(); i++) {
            if(BombermanGame.bombs.get(i).isLeftBomb) {
                if(BombermanGame.bombs.get(i).getX() / 32 == posX
                        && BombermanGame.bombs.get(i).getY() / 32 == posY) {
                    return true;
                }
            }
        }
        return false;
    }

}
