package uet.oop.bomberman.entities.Items;

import javafx.scene.image.Image;

public class SpeedItem extends Item {
    public SpeedItem(int x, int y, Image img) {
        super(x, y, img);
    }

    public static boolean isSpeedItem(char[][] arr,int x, int y) {
        return arr[x][y] == 's';
    }
}
