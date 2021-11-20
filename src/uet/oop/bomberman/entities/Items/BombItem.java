package uet.oop.bomberman.entities.Items;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class BombItem extends Item{

    public BombItem(int x, int y, Image img) {
        super(x, y, img);
    }

    public static boolean isBombItem(char[][] arr,int x, int y) {
        return arr[x][y] == 'b';
    }


}
