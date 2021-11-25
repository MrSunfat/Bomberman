package uet.oop.bomberman.entities.Items;

import javafx.scene.image.Image;

public class Portal extends Item {
    public static boolean isPortal;

    public Portal(){}

    public Portal(int x, int y, Image img) {
        super(x, y, img);
    }

    public static boolean isPortal(char[][] arr,int x, int y) {
        return arr[x][y] == 'x';
    }
}
