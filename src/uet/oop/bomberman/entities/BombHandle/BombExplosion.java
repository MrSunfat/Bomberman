package uet.oop.bomberman.entities.BombHandle;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class BombExplosion extends Entity {
    public static int frameExplosion = 0, intervalExplosion, indexAnimExplosion = 0;

    public BombExplosion(int x, int y, Image img){
        super(x,y,img);
    }

//    @Override
//    public void setImg(Image img) {
//        super.setImg(img);
//    }

    public BombExplosion(){}


    public static Image[] fontImageExplosion() {
        Image[] fontExplosion = new Image[]{
                Sprite.bomb_exploded.getFxImage(),
                Sprite.bomb_exploded1.getFxImage(),
                Sprite.bomb_exploded2.getFxImage()
        };
        return fontExplosion;
    }

    public static Image[] upImageExplosion() {
        Image[] upExplosion = new Image[]{
                Sprite.explosion_vertical_top_last.getFxImage(),
                Sprite.explosion_vertical_top_last1.getFxImage(),
                Sprite.explosion_vertical_top_last2.getFxImage()
        };
        return upExplosion;
    }

    public static Image[] downImageExplosion() {
        Image[] downExplosion = new Image[]{
                Sprite.explosion_vertical_down_last.getFxImage(),
                Sprite.explosion_vertical_down_last1.getFxImage(),
                Sprite.explosion_vertical_down_last2.getFxImage()
        };
        return downExplosion;
    }

    public static Image[] leftImageExplosion() {
        Image[] leftExplosion = new Image[]{
                Sprite.explosion_horizontal_left_last.getFxImage(),
                Sprite.explosion_horizontal_left_last1.getFxImage(),
                Sprite.explosion_horizontal_left_last2.getFxImage(),
        };
        return leftExplosion;
    }

    public static Image[] rightImageExplosion() {
        Image[] rightExplosion = new Image[]{
                Sprite.explosion_horizontal_right_last.getFxImage(),
                Sprite.explosion_horizontal_right_last1.getFxImage(),
                Sprite.explosion_horizontal_right_last2.getFxImage(),
        };
        return rightExplosion;
    }

    public static Image[] horizontalImage() {
        Image[] horizontal = new Image[]{
                Sprite.explosion_horizontal.getFxImage(),
                Sprite.explosion_horizontal1.getFxImage(),
                Sprite.explosion_horizontal2.getFxImage()
        };
        return horizontal;
    }

    public static Image[] verticalImage() {
        Image[] vertical = new Image[]{
                Sprite.explosion_vertical.getFxImage(),
                Sprite.explosion_vertical1.getFxImage(),
                Sprite.explosion_vertical2.getFxImage()
        };
        return vertical;
    }

    @Override
    public void update() {

    }
}
