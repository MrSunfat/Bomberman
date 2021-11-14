package uet.oop.bomberman.entities.BombHandle;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends Entity {
    public boolean exploded;
    public int countToExploded, intervalToExploded = 4;
    public Entity bombExplosion;

    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }

    public Bomb() {
    }

    @Override
    public void update() {
        if (BombermanGame.bomb != null) {
            frame++;
            if(frame == 7) {
                frame = 0;
                indexAnim++;
                if (indexAnim > 2) {
                    indexAnim = 0;
                    countToExploded++;
                }
                if (countToExploded >= intervalToExploded) {
                    exploded = true;
                    int x = BombermanGame.bomb.getX() / 32;
                    int y = BombermanGame.bomb.getY() / 32;

                    if(BombermanGame.arrMap[ y + 1][x] == '*') {
//                        System.out.println(BombermanGame.getStillObjects().get(31*(y+1) + x ));
                        BombermanGame.getStillObjects().set(31*(y + 1) + x, new Grass(x, y + 1, Sprite.grass.getFxImage()));
                    }
                    if(BombermanGame.arrMap[ y - 1][x] == '*') {
//                        System.out.println(BombermanGame.getStillObjects().get(31*(y-1) + x ));
                        BombermanGame.getStillObjects().set(31*(y - 1) + x, new Grass(x, y - 1, Sprite.grass.getFxImage()));
                    }
                    if(BombermanGame.arrMap[ y][x + 1] == '*') {
//                        System.out.println(BombermanGame.getStillObjects().get(31*y + x + 1 ));
                        BombermanGame.getStillObjects().set(31*y + x + 1, new Grass(x + 1, y, Sprite.grass.getFxImage()));
                    }
                    if(BombermanGame.arrMap[ y][x - 1] == '*') {
//                        System.out.println(BombermanGame.getStillObjects().get(31*y + x-1 ));
                        BombermanGame.getStillObjects().set(31*y + x - 1, new Grass(x - 1, y, Sprite.grass.getFxImage()));
                    }
                }
            }
            img = anim[indexAnim];
        }
        if (exploded) {

//            bombExplosion.frame++;

//            if(bombExplosion.frame == bombExplosion.interval) {
//
//                bombExplosion.frame = 0;
//                bombExplosion.indexAnim++;

//                if(bombExplosion.indexAnim == 4) {
//                    bombExplosion.indexAnim = 0;

                    BombermanGame.bombs.clear();
                    BombermanGame.bomb = null;
//                }
//            }


            exploded = false;
        }
    }
}
