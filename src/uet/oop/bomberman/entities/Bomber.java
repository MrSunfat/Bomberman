package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends Entity {
    public int speed = 2;
    Blocked blocked = new Blocked();
    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }


    public Image[] upImagePlayer() {
        animUp = new Image[] {
                Sprite.player_up.getFxImage(),
                Sprite.player_up_1.getFxImage(),
                Sprite.player_up_2.getFxImage()
        };
        return animUp;
    }

    public Image[] downImagePlayer() {
        animDown = new Image[] {
                Sprite.player_down.getFxImage(),
                Sprite.player_down_1.getFxImage(),
                Sprite.player_down_2.getFxImage()
        };
        return animDown;
    }

    public Image[] leftImagePlayer() {
        animLeft = new Image[] {
                Sprite.player_left.getFxImage(),
                Sprite.player_left_1.getFxImage(),
                Sprite.player_left_2.getFxImage()
        };
        return animLeft;
    }

    public Image[] rightImagePlayer() {
        animRight = new Image[] {
                Sprite.player_right.getFxImage(),
                Sprite.player_right_1.getFxImage(),
                Sprite.player_right_2.getFxImage()
        };
        return animRight;
    }

    @Override
    public void update() {
        moving = false;
        if(up && blocked.isFree(this.x, this.y - speed)) {
            this.y -= speed;
            moving = true;
        }
        if(down && blocked.isFree(this.x, this.y + speed)) {
            this.y += speed;
            moving = true;
        }
        if(left && blocked.isFree(this.x - speed, this.y)) {
            this.x -= speed;
            moving = true;
        }
        if(right && blocked.isFree(this.x + speed, this.y)) {
            this.x += speed;
            moving = true;
        }

        if(moving) {
            frame++;
            if(frame > interval) {
                frame = 0;
                indexAnim++;
                if(indexAnim > 2) {
                    indexAnim = 0;
                }
            }
            if (right) {
                img = rightImagePlayer()[indexAnim];
            } else if(left) {
                img = leftImagePlayer()[indexAnim];
            } else if(up) {
                img = upImagePlayer()[indexAnim];
            } else if(down) {
                img = downImagePlayer()[indexAnim];
            }
        } else {
            img = downImagePlayer()[1];
        }
//        System.out.println(this.x + "----"+ this.y);
    }
}
