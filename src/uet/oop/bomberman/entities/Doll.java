package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.Items.*;

public class Doll extends Entity {
    public Blocked blocked = new Blocked();
    public int speed = 1;
    public int frameDoll = 0, intervalDoll = 20, indexAnimDoll = 0;
    public static Image[] animUpDoll, animDownDoll, animLeftDoll, animRightDoll;

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Doll(){}

    public Doll(int x, int y, Image img) {
        super(x, y, img);
    }


    public Image[] upImageDoll() {
        animUpDoll = new Image[] {
                Sprite.doll_left1.getFxImage(),
                Sprite.doll_left2.getFxImage(),
                Sprite.doll_left3.getFxImage()
        };
        return animUpDoll;
    }

    public Image[] downImageDoll() {
        animDownDoll = new Image[] {
                Sprite.doll_right1.getFxImage(),
                Sprite.doll_right2.getFxImage(),
                Sprite.doll_right3.getFxImage()
        };
        return animDownDoll;
    }

    public Image[] leftImageDoll() {
        animLeftDoll = new Image[] {
                Sprite.doll_left1.getFxImage(),
                Sprite.doll_left2.getFxImage(),
                Sprite.doll_left3.getFxImage()
        };
        return animLeftDoll;
    }

    public Image[] rightImageDoll() {
        animRightDoll = new Image[] {
                Sprite.doll_right1.getFxImage(),
                Sprite.doll_right2.getFxImage(),
                Sprite.doll_right3.getFxImage()
        };
        return animRightDoll;
    }


    @Override
    public void update() {

        if(!timeStop.isTimeStop) {
            setSpeed(1);
        }

        Entity player = BombermanGame.getBomberman();

        if (deadDoll()) {
            moving = false;
            isLive = false;
        }

        if (isLive) {
            moving = false;

            if(!checkPlayerInRange()) {
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
            } else {
                if(this.x % 32 == 0 && this.y % 32 == 0) {
                    if(player.x / 32 > this.x / 32) {
                        right = true;
                        left = false;
                        up = false;
                        down = false;
                    } else if(player.x / 32 < this.x / 32) {
                        left = true;
                        up = false;
                        down = false;
                        right = false;
                    } else if(player.y /32 > this.y /32) {
                        left = false;
                        up = false;
                        down = true;
                        right = false;
                    } else if(player.y /32 < this.y / 32) {
                        left = false;
                        up = true;
                        down = false;
                        right = false;
                    }
                }
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
                frameDoll++;
                if (frameDoll > intervalDoll) {
                    frameDoll = 0;
                    indexAnimDoll++;
                    if (indexAnimDoll > 2) {
                        indexAnimDoll = 0;
                    }
                }
                if (right) {
                    img = rightImageDoll()[indexAnimDoll];
                } else if (left) {
                    img = leftImageDoll()[indexAnimDoll];
                } else if (up) {
                    img = upImageDoll()[indexAnimDoll];
                } else if (down) {
                    img = downImageDoll()[indexAnimDoll];
                }
            } else {
                img = downImageDoll()[1];
            }
        } else {
            img = Sprite.doll_dead.getFxImage();

            frameDoll++;
            if (frameDoll > 40) {
                BombermanGame.getBomberman().score += 400;
                BombermanGame.getMobs().remove(this);
            }
        }
    }



    public boolean deadDoll() {

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

    public boolean checkPlayerInRange() {

        int playerX = BombermanGame.getBomberman().getX() / 32;
        int playerY = BombermanGame.getBomberman().getY() / 32;
        int botX = this.x / 32;
        int botY = this.y / 32;
        if((playerX >= botX - 5 && playerX <= botX + 5 )
                && (playerY >= botY - 5 && playerY <= botY + 5)) {
            return true;
        }
        return false;
    }
}
