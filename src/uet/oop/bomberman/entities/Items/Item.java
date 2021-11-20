package uet.oop.bomberman.entities.Items;


import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Item extends Entity {

    public Item(int x, int y, Image img) {
        super(x,y,img);
    }

    public Item(){}

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public static int itemIndex(int posX, int posY) {
        for (int i = 0; i < BombermanGame.getItems().size(); i++) {
            if(BombermanGame.getItems().get(i).getX() / 32 == posX
                && BombermanGame.getItems().get(i).getY() / 32 == posY) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void update() {

    }
}
