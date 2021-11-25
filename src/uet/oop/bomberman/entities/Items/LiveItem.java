package uet.oop.bomberman.entities.Items;

import javafx.scene.image.Image;

public class LiveItem extends Item {
    public LiveItem(int x, int y, Image img) {
        super(x, y, img);
    }

    public static boolean isLiveItem(char[][] arr,int x, int y) {
        return arr[x][y] == 'l';
    }
}
