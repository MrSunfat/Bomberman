package uet.oop.bomberman.entities;


import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Balloom extends Entity{
    public Blocked blocked = new Blocked();
    public int speed = 1;
    private int timeToRandom = 120;

    public Balloom(){}

    public Balloom(int x, int y, Image img) {
        super(x, y, img);
    }


    public Image[] upImageBalloom() {
        animUp = new Image[] {
                Sprite.balloom_left1.getFxImage(),
                Sprite.balloom_left2.getFxImage(),
                Sprite.balloom_left3.getFxImage()
        };
        return animUp;
    }

    public Image[] downImageBalloom() {
        animDown = new Image[] {
                Sprite.balloom_right1.getFxImage(),
                Sprite.balloom_right2.getFxImage(),
                Sprite.balloom_right3.getFxImage()
        };
        return animDown;
    }

    public Image[] leftImageBalloom() {
        animLeft = new Image[] {
                Sprite.balloom_left1.getFxImage(),
                Sprite.balloom_left2.getFxImage(),
                Sprite.balloom_left3.getFxImage()
        };
        return animLeft;
    }

    public Image[] rightImageBalloom() {
        animRight = new Image[] {
                Sprite.balloom_right1.getFxImage(),
                Sprite.balloom_right2.getFxImage(),
                Sprite.balloom_right3.getFxImage()
        };
        return animRight;
    }


    @Override
    public void update() {
        moving = false;
        if(timeToRandom == 0) {
            int rand = (int) (Math.random()*100);
            if(rand >= 0 && rand < 25) {
                up = true;
                down = false;
                left = false;
                right = false;
            } else if(rand >= 25 && rand < 50) {
                down = true;
                up = false;
                left = false;
                right = false;
            } else if(rand >= 50 && rand < 75) {
                left = true;
                right = false;
                up = false;
                down = false;
            } else if(rand >= 75 && rand < 100) {
                right = true;
                left = false;
                up = false;
                down = false;
            }
            timeToRandom = 120;
        } else {
            timeToRandom--;
        }

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
            if(frame > 20) {
                frame = 0;
                indexAnim++;
                if(indexAnim > 2) {
                    indexAnim = 0;
                }
            }
            if (right) {
                img = rightImageBalloom()[indexAnim];
            } else if(left) {
                img = leftImageBalloom()[indexAnim];
            } else if(up) {
                img = upImageBalloom()[indexAnim];
            } else if(down) {
                img = downImageBalloom()[indexAnim];
            }
        } else {
            img = downImageBalloom()[1];
        }
    }
}
