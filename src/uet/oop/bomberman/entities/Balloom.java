package uet.oop.bomberman.entities;


import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.BombHandle.BombExplosion;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.Items.*;
import uet.oop.bomberman.soundEffect.Sound;

public class Balloom extends Entity{
    public Blocked blocked = new Blocked();
    public int speed = 1;
    public int frameBalloom = 0, intervalBalloom = 20, indexAnimBalloom = 0;
    public static Image[] animUpBalloom, animDownBalloom, animLeftBalloom, animRightBalloom;
    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Balloom(){}

    public Balloom(int x, int y, Image img) {
        super(x, y, img);
    }


    public Image[] upImageBalloom() {
        animUpBalloom = new Image[] {
                Sprite.balloom_left1.getFxImage(),
                Sprite.balloom_left2.getFxImage(),
                Sprite.balloom_left3.getFxImage()
        };
        return animUpBalloom;
    }

    public Image[] downImageBalloom() {
        animDownBalloom = new Image[] {
                Sprite.balloom_right1.getFxImage(),
                Sprite.balloom_right2.getFxImage(),
                Sprite.balloom_right3.getFxImage()
        };
        return animDownBalloom;
    }

    public Image[] leftImageBalloom() {
        animLeftBalloom = new Image[] {
                Sprite.balloom_left1.getFxImage(),
                Sprite.balloom_left2.getFxImage(),
                Sprite.balloom_left3.getFxImage()
        };
        return animLeftBalloom;
    }

    public Image[] rightImageBalloom() {
        animRightBalloom = new Image[] {
                Sprite.balloom_right1.getFxImage(),
                Sprite.balloom_right2.getFxImage(),
                Sprite.balloom_right3.getFxImage()
        };
        return animRightBalloom;
    }


    @Override
    public void update() {
        if(!timeStop.isTimeStop) {
            setSpeed(1);
        }

        if (deadBalloom()) {
            moving = false;
            isLive = false;
        }

        if (isLive) {
            moving = false;
            if (!blocked.canMove(this.x, this.y + speed)) {
                up = true;
                down = false;
            }
            if (!blocked.canMove(this.x, this.y - speed)) {
                down = true;
                up = false;
            }
            if (!blocked.canMove(this.x + speed, this.y)) {
                left = true;
                right = false;
            }
            if (!blocked.canMove(this.x - speed, this.y)) {
                right = true;
                left = false;
            }

            if (up && blocked.canMove(this.x, this.y - speed)) {
                this.y -= speed;
                moving = true;
            }
            if (down && blocked.canMove(this.x, this.y + speed)) {
                this.y += speed;
                moving = true;
            }
            if (left && blocked.canMove(this.x - speed, this.y)) {
                this.x -= speed;
                moving = true;
            }
            if (right && blocked.canMove(this.x + speed, this.y)) {
                this.x += speed;
                moving = true;
            }

            if (moving) {
                frameBalloom++;
                if (frameBalloom > intervalBalloom) {
                    frameBalloom = 0;
                    indexAnimBalloom++;
                    if (indexAnimBalloom > 2) {
                        indexAnimBalloom = 0;
                    }
                }
                if (right) {
                    img = rightImageBalloom()[indexAnimBalloom];
                } else if (left) {
                    img = leftImageBalloom()[indexAnimBalloom];
                } else if (up) {
                    img = upImageBalloom()[indexAnimBalloom];
                } else if (down) {
                    img = downImageBalloom()[indexAnimBalloom];
                }
            } else {
                img = downImageBalloom()[1];
            }
        } else {
            img = Sprite.balloom_dead.getFxImage();

            frameBalloom++;
            if (frameBalloom > 40) {
                BombermanGame.getBomberman().score += 100;
                BombermanGame.getMobs().remove(this);
            }
        }
    }



    public boolean deadBalloom() {

        int x1 = x / size;
        int y1 = y / size;

        int x2 = (x + size - 1) / size;
        int y2 = y / size;

        int x3 = x / size;
        int y3 = (y + size - 1) / size;

        int x4 = (x + size - 1) / size;
        int y4 = (y + size - 1) / size;

        return isExplode(x1, y1) || isExplode(x2, y2) || isExplode(x3, y3) || isExplode(x4, y4);
    }
}
