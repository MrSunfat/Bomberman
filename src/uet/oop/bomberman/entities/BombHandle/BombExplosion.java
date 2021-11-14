package uet.oop.bomberman.entities.BombHandle;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class BombExplosion extends Entity {

    public BombExplosion(int x, int y, Image img){
        super(x,y,img);
    }

    public BombExplosion(){}

    public Image[] fontImageExplosion() {
        anim = new Image[] {
                Sprite.bomb_exploded.getFxImage(),
                Sprite.bomb_exploded1.getFxImage(),
                Sprite.bomb_exploded2.getFxImage()
        };
        return anim;
    }

    public static Image[] upImageExplosion() {
        animUp = new Image[] {
                Sprite.explosion_vertical_top_last.getFxImage(),
                Sprite.explosion_vertical_top_last1.getFxImage(),
                Sprite.explosion_vertical_top_last2.getFxImage()
        };
        return animUp;
    }

    public Image[] downImageExplosion() {
        animDown = new Image[] {
                Sprite.explosion_vertical_down_last.getFxImage(),
                Sprite.explosion_vertical_down_last1.getFxImage(),
                Sprite.explosion_vertical_down_last2.getFxImage()
        };
        return animDown;
    }

    public Image[] leftImageExplosion() {
        animLeft = new Image[] {
                Sprite.explosion_horizontal_left_last.getFxImage(),
                Sprite.explosion_horizontal_left_last1.getFxImage(),
                Sprite.explosion_horizontal_left_last2.getFxImage(),
        };
        return animLeft;
    }

    public Image[] rightImageExplosion() {
        animRight = new Image[] {
                Sprite.explosion_horizontal_right_last.getFxImage(),
                Sprite.explosion_horizontal_right_last1.getFxImage(),
                Sprite.explosion_horizontal_right_last2.getFxImage(),
        };
        return animRight;
    }

    public Image[] horizontalImage() {
        horizontal = new Image[] {
                Sprite.explosion_horizontal.getFxImage(),
                Sprite.explosion_horizontal1.getFxImage(),
                Sprite.explosion_horizontal2.getFxImage()
        };
        return horizontal;
    }

    public Image[] verticalImage() {
        vertical = new Image[] {
                Sprite.explosion_vertical.getFxImage(),
                Sprite.explosion_vertical1.getFxImage(),
                Sprite.explosion_vertical2.getFxImage()
        };
        return horizontal;
    }

    @Override
    public void update() {

    }
}
