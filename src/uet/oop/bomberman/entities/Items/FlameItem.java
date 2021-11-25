package uet.oop.bomberman.entities.Items;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.BombHandle.Bomb;
import uet.oop.bomberman.entities.Grass;

public class FlameItem extends Item{
    public FlameItem(int x, int y, Image img) {
        super(x, y, img);
    }

    public static boolean isFlameItem(char[][] arr,int x, int y) {
        return arr[x][y] == 'f';
    }

    public static int flameLength(int x, int y, String direction) {
        int i = 1;
        switch (direction) {
            case "DOWN" : {
                while(BombermanGame.getStillObjects().get(31*(y+i) + x) instanceof Grass && i != Bomb.bombRate + 1) {
                    i++;
                }
                break;
            }
            case "UP" : {
                while(BombermanGame.getStillObjects().get(31*(y-i) + x) instanceof Grass && i != Bomb.bombRate + 1) {
                    i++;
                }
                break;
            }
            case "LEFT" : {
                while(BombermanGame.getStillObjects().get(31*y + x - i) instanceof Grass && i != Bomb.bombRate + 1) {
                    i++;
                }
                break;
            }
            case "RIGHT" : {
                while(BombermanGame.getStillObjects().get(31*y + x + i) instanceof Grass && i != Bomb.bombRate + 1) {
                    i++;
                }
                break;
            }
        }
        return i - 1;
    }
}
