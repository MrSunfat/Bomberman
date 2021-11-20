package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.BombHandle.Bomb;
import uet.oop.bomberman.entities.Items.BombItem;
import uet.oop.bomberman.entities.Items.FlameItem;
import uet.oop.bomberman.entities.Items.Item;
import uet.oop.bomberman.entities.Items.SpeedItem;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends Entity {
    public int speed = 2;
    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }
    public int framePlayer = 0, intervalPlayer = 5, indexAnimPlayer = 0;
    public static Image[] animUpPlayer, animDownPlayer, animLeftPlayer, animRightPlayer;
    public static boolean isPlayerLive;
    public int playerLifeNumb = 3;


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

        if (deadBomber()) {
            moving = false;
            isLive = false;

        }

        if(isLive) {
            moving = false;
//            isPlayerLive = true;
            if(itemIndexReceived() != -1) {
                int itemX = BombermanGame.getItems().get(itemIndexReceived()).getX() / 32;
                int itemY = BombermanGame.getItems().get(itemIndexReceived()).getY() / 32;
                BombermanGame.getStillObjects().set(31*itemY + itemX, new Grass(itemX, itemY, Sprite.grass.getFxImage()));

                if(BombermanGame.getItems().get(itemIndexReceived()) instanceof SpeedItem) {
                    speed++;
                }
                if(BombermanGame.getItems().get(itemIndexReceived()) instanceof BombItem) {
                    Bomb.bombNum++;
                }
                if(BombermanGame.getItems().get(itemIndexReceived()) instanceof FlameItem) {
                    Bomb.bombRate++;
                }

                BombermanGame.getItems().remove(itemIndexReceived());
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
//            isPlayerLive = false;
            img = Sprite.player_dead1.getFxImage();
//            if(playerLifeNumb != 0) {
//                playerLifeNumb--;
//                System.out.println(playerLifeNumb);
//            }
            System.out.println("Hi");
            framePlayer++;
            if(framePlayer > 30) {
                BombermanGame.getEntities().remove(this);
            }
        }

//        System.out.println(this.x + "----"+ this.y);
    }


    public boolean canMove(int nextX, int nextY) {
        int size = Sprite.SCALED_SIZE;

        int nextX_1 = nextX / size;
        int nextY_1 = nextY / size;

        int nextX_2 = (nextX + size - 3) / size;
        int nextY_2 = nextY / size;

        int nextX_3 = nextX / size;
        int nextY_3 = (nextY + size - 3) / size;

        int nextX_4 = (nextX + size - 3) / size;
        int nextY_4 = (nextY + size - 3) / size;

        return !((BombermanGame.getStillObjects().get(31*nextY_1 + nextX_1) instanceof Wall
                || BombermanGame.getStillObjects().get(31*nextY_1 + nextX_1) instanceof Brick)
                || (BombermanGame.getStillObjects().get(31*nextY_2 + nextX_2) instanceof Wall
                || BombermanGame.getStillObjects().get(31*nextY_2 + nextX_2) instanceof Brick)
                || (BombermanGame.getStillObjects().get(31*nextY_3 + nextX_3) instanceof Wall
                || BombermanGame.getStillObjects().get(31*nextY_3 + nextX_3) instanceof Brick)
                || (BombermanGame.getStillObjects().get(31*nextY_4 + nextX_4) instanceof Wall
                || BombermanGame.getStillObjects().get(31*nextY_4 + nextX_4) instanceof Brick));
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
        int size = Sprite.SCALED_SIZE;

        int x1 = x / size;
        int y1 = y / size;

        int x2 = (x + size - 10) / size;
        int y2 = y / size;

        int x3 = x / size;
        int y3 = (y + size - 10) / size;

        int x4 = (x + size - 10) / size;
        int y4 = (y + size - 10) / size;

        return isExplode(x1, y1) || isExplode(x2, y2) || isExplode(x3, y3) || isExplode(x4, y4)
                || isMob(x1, y1) || isMob(x2, y2) || isMob(x3, y3) || isMob(x4, y4);
    }

    public boolean isMob(int x, int y) {
        for(int i = 0; i < BombermanGame.getMobs().size(); i++) {
            if(BombermanGame.getMobs().get(i).getX() / 32 == x
                && BombermanGame.getMobs().get(i).getY() / 32 == y) {
                return true;
            }
        }
        return false;
    }

}
