package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends Entity {
    public int speed = 2;
    Blocked blocked = new Blocked();
    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }
    public int framePlayer = 0, intervalPlayer = 5, indexAnimPlayer = 0;
    public static Image[] animUpPlayer, animDownPlayer, animLeftPlayer, animRightPlayer;

    public Image[] upImagePlayer() {
        animUpPlayer = new Image[] {
                Sprite.player_up.getFxImage(),
                Sprite.player_up_1.getFxImage(),
                Sprite.player_up_2.getFxImage()
        };
        return animUpPlayer;
    }

    public Image[] downImagePlayer() {
        animDownPlayer = new Image[] {
                Sprite.player_down.getFxImage(),
                Sprite.player_down_1.getFxImage(),
                Sprite.player_down_2.getFxImage()
        };
        return animDownPlayer;
    }

    public Image[] leftImagePlayer() {
        animLeftPlayer = new Image[] {
                Sprite.player_left.getFxImage(),
                Sprite.player_left_1.getFxImage(),
                Sprite.player_left_2.getFxImage()
        };
        return animLeftPlayer;
    }

    public Image[] rightImagePlayer() {
        animRightPlayer = new Image[] {
                Sprite.player_right.getFxImage(),
                Sprite.player_right_1.getFxImage(),
                Sprite.player_right_2.getFxImage()
        };
        return animRightPlayer;
    }

    @Override
    public void update() {
        moving = false;
        if(up && canMove(this.x, this.y - speed)) {
            this.y -= speed;
            moving = true;
        }
        if(down && canMove(this.x, this.y + speed)) {
            this.y += speed;
            moving = true;
        }
        if(left && canMove(this.x - speed, this.y)) {
            this.x -= speed;
            moving = true;
        }
        if(right && canMove(this.x + speed, this.y)) {
            this.x += speed;
            moving = true;
        }

        if(moving) {
            framePlayer++;
            if(framePlayer > intervalPlayer) {
                framePlayer = 0;
                indexAnimPlayer++;
                if(indexAnimPlayer > 2) {
                    indexAnimPlayer = 0;
                }
            }
            if (right) {
                img = rightImagePlayer()[indexAnimPlayer];
            } else if(left) {
                img = leftImagePlayer()[indexAnimPlayer];
            } else if(up) {
                img = upImagePlayer()[indexAnimPlayer];
            } else if(down) {
                img = downImagePlayer()[indexAnimPlayer];
            }
        } else {
            img = downImagePlayer()[1];
        }
//        System.out.println(this.x + "----"+ this.y);
    }


    public boolean canMove(int nextX, int nextY) {
        int size = Sprite.SCALED_SIZE;

        int nextX_1 = nextX / size;
        int nextY_1 = nextY / size;

        int nextX_2 = (nextX + size - 1) / size;
        int nextY_2 = nextY / size;

        int nextX_3 = nextX / size;
        int nextY_3 = (nextY + size - 1) / size;

        int nextX_4 = (nextX + size - 1) / size;
        int nextY_4 = (nextY + size - 1) / size;

        return !((BombermanGame.getStillObjects().get(31*nextY_1 + nextX_1) instanceof Wall
                || BombermanGame.getStillObjects().get(31*nextY_1 + nextX_1) instanceof Brick)
                || (BombermanGame.getStillObjects().get(31*nextY_2 + nextX_2) instanceof Wall
                || BombermanGame.getStillObjects().get(31*nextY_2 + nextX_2) instanceof Brick)
                || (BombermanGame.getStillObjects().get(31*nextY_3 + nextX_3) instanceof Wall
                || BombermanGame.getStillObjects().get(31*nextY_3 + nextX_3) instanceof Brick)
                || (BombermanGame.getStillObjects().get(31*nextY_4 + nextX_4) instanceof Wall
                || BombermanGame.getStillObjects().get(31*nextY_4 + nextX_4) instanceof Brick));
    }
}
