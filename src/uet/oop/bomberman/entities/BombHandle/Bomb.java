package uet.oop.bomberman.entities.BombHandle;

import javafx.scene.image.Image;
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
//                                if (BombItem.isBombItem(BombermanGame.arrMap, (y + i + 1), x)) {
//                                    BombermanGame.getStillObjects().set(31 * (y + i + 1) + x,
//                                            new BombItem(x, y + i + 1, Sprite.powerup_bombs.getFxImage()));
//                                } else if (SpeedItem.isSpeedItem(BombermanGame.arrMap, y + i + 1, x)) {
//                                    BombermanGame.getStillObjects().set(31 * (y + i + 1) + x,
//                                            new SpeedItem(x, y + i + 1, Sprite.powerup_speed.getFxImage()));
//                                } else if (FlameItem.isFlameItem(BombermanGame.arrMap, y + i + 1, x)) {
//                                    BombermanGame.getStillObjects().set(31 * (y + i + 1) + x,
//                                            new FlameItem(x, y + i + 1, Sprite.powerup_flames.getFxImage()));
//                                } else if (Portal.isPortal(BombermanGame.arrMap, (y + i + 1), x)) {
//                                    BombermanGame.getStillObjects().set(31 * (y + i + 1) + x,
//                                            new Portal(x, y + i + 1, Sprite.portal.getFxImage()));
//                                }
//                                else {
//                                    BombermanGame.getStillObjects().set(31 * (y + i + 1) + x,
//                                            new Grass(x, y + i + 1, Sprite.grass.getFxImage()));
//                                }
                                BombermanGame.getStillObjects().get(31 * (y+ i + 1) + x).isLive = false;
                                hasBrickDown = true;
                                break;
                            }
                        }
                    }

                    if(countUp < bombRate) {
                        for (int i = 0; i <= countUp; i++) {
                            if (BombermanGame.getStillObjects().get(31 * (y - i - 1) + x) instanceof Brick) {
//                                if (BombItem.isBombItem(BombermanGame.arrMap, (y - i - 1), x)) {
//                                    BombermanGame.getStillObjects().set(31 * (y - i - 1) + x,
//                                            new BombItem(x, y - i - 1, Sprite.powerup_bombs.getFxImage()));
//                                } else if (SpeedItem.isSpeedItem(BombermanGame.arrMap, y - i - 1, x)) {
//                                    BombermanGame.getStillObjects().set(31 * (y - i - 1) + x,
//                                            new SpeedItem(x, y - i - 1, Sprite.powerup_speed.getFxImage()));
//                                } else if (FlameItem.isFlameItem(BombermanGame.arrMap, y - i - 1, x)) {
//                                    BombermanGame.getStillObjects().set(31 * (y - i - 1) + x,
//                                            new FlameItem(x, y - i - 1, Sprite.powerup_flames.getFxImage()));
//                                } else if (Portal.isPortal(BombermanGame.arrMap, (y - i - 1), x)) {
//                                    BombermanGame.getStillObjects().set(31 * (y - i - 1) + x,
//                                            new Portal(x, y - i - 1, Sprite.portal.getFxImage()));
//                                }
//                                else {
//                                    BombermanGame.getStillObjects().set(31 * (y - i - 1) + x,
//                                            new Grass(x, y - i - 1, Sprite.grass.getFxImage()));

//                                }
                                BombermanGame.getStillObjects().get(31 * (y - i - 1) + x).isLive = false;
                                hasBrickTop = true;
                                break;
                            }
                        }
                    }

                    if(countLeft < bombRate) {
                        for (int i = 0; i <= countLeft; i++) {
                            if (BombermanGame.getStillObjects().get(31 * (y) + x  - i - 1) instanceof Brick) {
//                                if (BombItem.isBombItem(BombermanGame.arrMap, (y), x - i - 1)) {
//                                    BombermanGame.getStillObjects().set(31 * (y) + x - i - 1,
//                                            new BombItem(x - i - 1, y, Sprite.powerup_bombs.getFxImage()));
//                                } else if (SpeedItem.isSpeedItem(BombermanGame.arrMap, y, x - i - 1)) {
//                                    BombermanGame.getStillObjects().set(31 * (y) + x - i - 1,
//                                            new SpeedItem(x - i - 1, y, Sprite.powerup_speed.getFxImage()));
//                                } else if (FlameItem.isFlameItem(BombermanGame.arrMap, y, x- i - 1)) {
//                                    BombermanGame.getStillObjects().set(31 * (y) + x - i - 1,
//                                            new FlameItem(x - i - 1, y, Sprite.powerup_flames.getFxImage()));
//                                } else if (Portal.isPortal(BombermanGame.arrMap, (y), x - i - 1)) {
//                                    BombermanGame.getStillObjects().set(31 * (y) + x- i - 1,
//                                            new Portal(x - i - 1, y, Sprite.portal.getFxImage()));
//                                }
//                                else {
//                                    BombermanGame.getStillObjects().set(31 * (y) + x - i - 1,
//                                            new Grass(x - i - 1, y, Sprite.grass.getFxImage()));
//                                }
                                BombermanGame.getStillObjects().get(31 * (y) + x  - i - 1).isLive = false;
                                hasBrickLeft = true;
                                break;
                            }
                        }
                    }

                    if(countRight < bombRate) {
                        for (int i = 0; i <= countRight; i++) {
                            if (BombermanGame.getStillObjects().get(31 * (y) + x + i + 1) instanceof Brick) {
//                                if (BombItem.isBombItem(BombermanGame.arrMap, (y), x + i + 1)) {
//                                    BombermanGame.getStillObjects().set(31 * (y ) + x+ i + 1,
//                                            new BombItem(x + i + 1, y, Sprite.powerup_bombs.getFxImage()));
//                                } else if (SpeedItem.isSpeedItem(BombermanGame.arrMap, y, x + i + 1)) {
//                                    BombermanGame.getStillObjects().set(31 * (y) + x + i + 1,
//                                            new SpeedItem(x + i + 1, y, Sprite.powerup_speed.getFxImage()));
//                                } else if (FlameItem.isFlameItem(BombermanGame.arrMap, y, x + i + 1)) {
//                                    BombermanGame.getStillObjects().set(31 * (y) + x + i + 1,
//                                            new FlameItem(x + i + 1, y, Sprite.powerup_flames.getFxImage()));
//                                } else if (Portal.isPortal(BombermanGame.arrMap, (y), x + i + 1)) {
//                                    BombermanGame.getStillObjects().set(31 * (y) + x + i + 1,
//                                            new Portal(x + i + 1, y, Sprite.portal.getFxImage()));
//                                }
//                                else {
//                                    BombermanGame.getStillObjects().set(31 * (y) + x + i + 1,
//                                            new Grass(x + i + 1, y, Sprite.grass.getFxImage()));
//                                }
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
//                int bombIndex = BombermanGame.bombs.indexOf(this);
//
//                BombermanGame.bombs.remove(this);
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


}



