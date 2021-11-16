package uet.oop.bomberman.entities.BombHandle;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.graphics.Sprite;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class Bomb extends Entity {
    public boolean exploded;
    public int countToExploded, intervalToExploded = 4;
    public Entity bombExplosion = new BombExplosion();
    public int frameBomb = 0, intervalBomb = 10, indexAnimBomb = 0;
    private boolean hasBrickTop, hasBrickDown, hasBrickLeft, hasBrickRight;

    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }

    public Bomb() {
    }
    public static Image[] animBomb() {
        Image[] animBomb = new Image[] {
                Sprite.bomb.getFxImage(),
                Sprite.bomb_1.getFxImage(),
                Sprite.bomb_2.getFxImage()
        };
        return animBomb;
    }

    @Override
    public void update() {
        int x = BombermanGame.bomb.getX() / 32;
        int y = BombermanGame.bomb.getY() / 32;
        if (BombermanGame.bomb != null) {

            frameBomb++;
            if (frameBomb == intervalBomb) {
                frameBomb = 0;
                indexAnimBomb++;
                if (indexAnimBomb > 2) {
                    indexAnimBomb = 0;
                    countToExploded++;
                }
                if (countToExploded >= intervalToExploded) {
                    exploded = true;
                    if (BombermanGame.getStillObjects().get(31 * (y + 1) + x) instanceof Brick) {
//                        System.out.println(BombermanGame.getStillObjects().get(31*(y+1) + x ));
                        hasBrickDown = true;
                        BombermanGame.getStillObjects().set(31 * (y + 1) + x, new Grass(x, y + 1, Sprite.grass.getFxImage()));
                    }
                    if (BombermanGame.getStillObjects().get(31 * (y - 1) + x) instanceof Brick) {
//                        System.out.println(BombermanGame.getStillObjects().get(31*(y-1) + x ));
                        hasBrickTop = true;
                        BombermanGame.getStillObjects().set(31 * (y - 1) + x, new Grass(x, y - 1, Sprite.grass.getFxImage()));
                    }
                    if (BombermanGame.getStillObjects().get(31 * y + x + 1) instanceof Brick) {
//                        System.out.println(BombermanGame.getStillObjects().get(31*y + x + 1 ));
                        hasBrickRight = true;
                        BombermanGame.getStillObjects().set(31 * y + x + 1, new Grass(x + 1, y, Sprite.grass.getFxImage()));
                    }
                    if (BombermanGame.getStillObjects().get(31 * y + x - 1) instanceof Brick) {
//                        System.out.println(BombermanGame.getStillObjects().get(31*y + x-1 ));
                        hasBrickLeft = true;
                        BombermanGame.getStillObjects().set(31 * y + x - 1, new Grass(x - 1, y, Sprite.grass.getFxImage()));
                    }
                }
            }

            img = animBomb()[indexAnimBomb];

            if (exploded) {

                bombExplosion.frame++;

                if (bombExplosion.frame == 5) {
                    bombExplosion.frame = 0;
                    bombExplosion.indexAnim++;

                    if (bombExplosion.indexAnim == 3) {
                        bombExplosion.indexAnim = 0;
                        BombermanGame.bombs.remove(this);
                        BombermanGame.bomb = null;
                        BombermanGame.bombExplosions.clear();
                    }
                }
            }
        }

        if (BombermanGame.bomb != null) {
            if (exploded) {
                Entity object;
                object = new BombExplosion(x, y, BombExplosion.fontImageExplosion()[bombExplosion.indexAnim]);
                BombermanGame.bombExplosions.add(object);
                if (BombermanGame.getStillObjects().get(31*(y+1) + x) instanceof Grass && !hasBrickDown) {
                    object = new BombExplosion(x, y+1, BombExplosion.downImageExplosion()[bombExplosion.indexAnim]);
                    BombermanGame.bombExplosions.add(object);
                }
                if (BombermanGame.getStillObjects().get(31*(y-1) + x) instanceof Grass && !hasBrickTop) {
                    object = new BombExplosion(x, y - 1, BombExplosion.upImageExplosion()[bombExplosion.indexAnim]);
                    BombermanGame.bombExplosions.add(object);
                }
                if (BombermanGame.getStillObjects().get(31*y + x + 1) instanceof Grass && !hasBrickRight) {
                    object = new BombExplosion(x + 1, y, BombExplosion.rightImageExplosion()[bombExplosion.indexAnim]);
                    BombermanGame.bombExplosions.add(object);
                }
                if (BombermanGame.getStillObjects().get(31*y + x - 1) instanceof Grass && !hasBrickLeft) {
                    object = new BombExplosion(x - 1, y, BombExplosion.leftImageExplosion()[bombExplosion.indexAnim]);
                    BombermanGame.bombExplosions.add(object);
                }
                }
            }
        }
    }

