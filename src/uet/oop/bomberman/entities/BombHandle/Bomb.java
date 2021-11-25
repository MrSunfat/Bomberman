package uet.oop.bomberman.entities.BombHandle;

import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.Items.BombItem;
import uet.oop.bomberman.entities.Items.FlameItem;
import uet.oop.bomberman.entities.Items.Portal;
import uet.oop.bomberman.entities.Items.SpeedItem;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.soundEffect.Sound;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class Bomb extends Entity {
    public boolean exploded;
    public int countToExploded, intervalToExploded = 4;
    public Entity bombExplosion = new BombExplosion();
    public int frameBomb = 0, intervalBomb = 10, indexAnimBomb = 0;
    public boolean hasBrickTop, hasBrickDown, hasBrickLeft, hasBrickRight;
    public static int bombNum = 1;
    public static int bombRate = 1;
    public int countDown, countUp, countLeft, countRight;
    public Sound sound = new Sound();

    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }

    public Bomb() {
    }

    public static Image[] animBomb() {
        Image[] animBomb = new Image[]{
                Sprite.bomb.getFxImage(),
                Sprite.bomb_1.getFxImage(),
                Sprite.bomb_2.getFxImage()
        };
        return animBomb;
    }

    @Override
    public void update() {
        if(!isLeftBomb) {
            if(isPlayerLeftBomb()) {
                isLeftBomb = true;
            }
        }

        int x = this.x / 32;
        int y = this.y / 32;

        int lengthDown = FlameItem.flameLength(x, y, "DOWN");
        int lengthUp = FlameItem.flameLength(x, y, "UP");
        int lengthLeft = FlameItem.flameLength(x, y, "LEFT");
        int lengthRight = FlameItem.flameLength(x, y, "RIGHT");

        if(!hasBrickDown) {
            countDown = lengthDown;
        }
        if(!hasBrickTop) {
            countUp = lengthUp;
        }
        if(!hasBrickLeft) {
            countLeft = lengthLeft;
        }
        if(!hasBrickRight) {
            countRight = lengthRight;
        }

        if (BombermanGame.bomb != null) {
            if(explodeBomb()) {
                countToExploded = intervalToExploded;
            }
            frameBomb++;
            if (frameBomb == intervalBomb) {
                frameBomb = 0;
                indexAnimBomb++;
                if (indexAnimBomb > 2) {
                    indexAnimBomb = 0;
                    countToExploded++;
                }

//                Xoa gach xung quanh
                if (countToExploded == intervalToExploded) {
                    exploded = true;
                    if(countDown < bombRate) {
                        for (int i = 0; i <= countDown; i++) {
                            if (BombermanGame.getStillObjects().get(31 * (y + i + 1) + x) instanceof Brick) {
                                //Check isLive cua brick de thuc hien animation
                                BombermanGame.getStillObjects().get(31 * (y+ i + 1) + x).isLive = false;
                                hasBrickDown = true;
                                break;
                            }
                        }
                    }

                    if(countUp < bombRate) {
                        for (int i = 0; i <= countUp; i++) {
                            if (BombermanGame.getStillObjects().get(31 * (y - i - 1) + x) instanceof Brick) {
                                BombermanGame.getStillObjects().get(31 * (y - i - 1) + x).isLive = false;
                                hasBrickTop = true;
                                break;
                            }
                        }
                    }

                    if(countLeft < bombRate) {
                        for (int i = 0; i <= countLeft; i++) {
                            if (BombermanGame.getStillObjects().get(31 * (y) + x  - i - 1) instanceof Brick) {
                                BombermanGame.getStillObjects().get(31 * (y) + x  - i - 1).isLive = false;
                                hasBrickLeft = true;
                                break;
                            }
                        }
                    }

                    if(countRight < bombRate) {
                        for (int i = 0; i <= countRight; i++) {
                            if (BombermanGame.getStillObjects().get(31 * (y) + x + i + 1) instanceof Brick) {
                                BombermanGame.getStillObjects().get(31 * (y) + x  + i + 1).isLive = false;
                                hasBrickRight = true;
                                break;
                            }
                        }
                    }
                }
            }
        }

        if (exploded) {

            bombExplosion.frame++;

            if (bombExplosion.frame == 5) {
                bombExplosion.frame = 0;
                bombExplosion.indexAnim++;

                if (bombExplosion.indexAnim == 3) {

                    bombExplosion.indexAnim = 0;
                    sound.soundEffect("bomb_explosion.wav");

                    BombermanGame.bombs.remove(this);
                    BombermanGame.bombExplosions.clear();

                    bombNum++;
                    exploded = false;
                }
            }
        }
        img = animBomb()[indexAnimBomb];

//        Ve tia lua bom no
        if (BombermanGame.bomb != null) {
            if (exploded) {

                Entity object;
                object = new BombExplosion(x, y, BombExplosion.fontImageExplosion()[bombExplosion.indexAnim]);

                BombermanGame.bombExplosions.add(object);

                if(countDown!= 0) {
                    for (int i = 1; i <= countDown; i++) {
                        if (i == countDown) {
                            object = new BombExplosion(x, y + i, BombExplosion.downImageExplosion()[bombExplosion.indexAnim]);
                            BombermanGame.bombExplosions.add(object);
                        } else {
                            object = new BombExplosion(x, y + i, BombExplosion.verticalImage()[bombExplosion.indexAnim]);
                            BombermanGame.bombExplosions.add(object);
                        }
                    }
                }

                if(countUp!= 0) {
                    for (int i = 1; i <= countUp; i++) {
                        if (i == countUp) {
                            object = new BombExplosion(x, y - i, BombExplosion.upImageExplosion()[bombExplosion.indexAnim]);
                            BombermanGame.bombExplosions.add(object);
                        } else {
                            object = new BombExplosion(x, y - i, BombExplosion.verticalImage()[bombExplosion.indexAnim]);
                            BombermanGame.bombExplosions.add(object);
                        }
                    }
                }

                if(countLeft != 0) {
                    for (int i = 1; i <= countLeft; i++) {
                        if (i == countLeft) {
                            object = new BombExplosion(x - i, y, BombExplosion.leftImageExplosion()[bombExplosion.indexAnim]);
                            BombermanGame.bombExplosions.add(object);
                        } else {
                            object = new BombExplosion(x - i, y, BombExplosion.horizontalImage()[bombExplosion.indexAnim]);
                            BombermanGame.bombExplosions.add(object);
                        }
                    }
                }

                if(countRight!= 0) {
                    for (int i = 1; i <= countRight; i++) {
                        if (i == countRight) {
                            object = new BombExplosion(x + i, y, BombExplosion.rightImageExplosion()[bombExplosion.indexAnim]);
                            BombermanGame.bombExplosions.add(object);
                        } else {
                            object = new BombExplosion(x + i, y, BombExplosion.horizontalImage()[bombExplosion.indexAnim]);
                            BombermanGame.bombExplosions.add(object);
                        }
                    }
                }


            }
        }
    }


//    Kiem tra bom co cham vao tia lua khac ko
    public boolean explodeBomb() {
        int size = Sprite.SCALED_SIZE;

        int x1 = x / size;
        int y1 = y / size;

        int x2 = (x + size - 1) / size;
        int y2 = y / size;

        int x3 = x / size;
        int y3 = (y + size - 1) / size;

        int x4 = (x + size - 1) / size;
        int y4 = (y + size - 1) / size;

        return isExplode(x1, y1) || isExplode(x2, y2) || isExplode(x3, y3) || isExplode(x4, y4);
    }

//    Kiem tra vi tri player khac vi tri bomb
    public boolean isPlayerLeftBomb() {
        int size = Sprite.SCALED_SIZE;

        int playerX = BombermanGame.getBomberman().getX();
        int playerY = BombermanGame.getBomberman().getY();

        int x1 = playerX / size;
        int y1 = playerY / size;

        int x2 = (playerX + size - 1) / size;
        int y2 = playerY / size;

        int x3 = playerX / size;
        int y3 = (playerY + size - 1) / size;

        int x4 = (playerX + size - 1) / size;
        int y4 = (playerY + size - 1) / size;
        return !((x1 == x/32 && y1 == y/32)
                || (x2 == x/32 && y2 == y/32)
                || (x3 == x/32 && y3 == y/32)
                || (x4 == x/32 && y4 == y/32));
    }

}



