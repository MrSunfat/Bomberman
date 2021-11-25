package uet.oop.bomberman.entities.Items;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Items.Item;

public class timeStop extends Item {
    public static int timeStopCount = 0;
    public static boolean isTimeStop;
    public timeStop(int x, int y, Image img) {
        super(x, y, img);
    }

    public static boolean isTimeStopItem(char[][] arr,int x, int y) {
        return arr[x][y] == 't';
    }
}
