package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Entity {
        public Blocked blocked = new Blocked();
        public int speed = 1;
        private int timeToRandom = 120;
        public int frameOneal = 0, intervalOneal = 20, indexAnimOneal = 0;
        public static Image[] animUpOneal, animDownOneal, animLeftOneal, animRightOneal;


        public Oneal(){}

        public Oneal(int x, int y, Image img) {
            super(x, y, img);
        }


        public Image[] upImageoneal() {
            animUpOneal = new Image[] {
                    Sprite.oneal_left1.getFxImage(),
                    Sprite.oneal_left2.getFxImage(),
                    Sprite.oneal_left3.getFxImage()
            };
            return animUpOneal;
        }

        public Image[] downImageoneal() {
            animDownOneal = new Image[] {
                    Sprite.oneal_right1.getFxImage(),
                    Sprite.oneal_right2.getFxImage(),
                    Sprite.oneal_right3.getFxImage()
            };
            return animDownOneal;
        }

        public Image[] leftImageoneal() {
            animLeftOneal = new Image[] {
                    Sprite.oneal_left1.getFxImage(),
                    Sprite.oneal_left2.getFxImage(),
                    Sprite.oneal_left3.getFxImage()
            };
            return animLeftOneal;
        }

        public Image[] rightImageoneal() {
            animRightOneal = new Image[] {
                    Sprite.oneal_right1.getFxImage(),
                    Sprite.oneal_right2.getFxImage(),
                    Sprite.oneal_right3.getFxImage()
            };
            return animRightOneal;
        }


        @Override
        public void update() {
            if (deadOneal()) {
                moving = false;
                isLive = false;
            }

            if (isLive) {
                speed = (int) (Math.random()*2 + 1);
                moving = false;
                if (timeToRandom == 0) {
                    int rand = (int) (Math.random() * 100);
                    if (rand >= 0 && rand < 25) {
                        up = true;
                        down = false;
                        left = false;
                        right = false;
                    } else if (rand >= 25 && rand < 50) {
                        down = true;
                        up = false;
                        left = false;
                        right = false;
                    } else if (rand >= 50 && rand < 75) {
                        left = true;
                        right = false;
                        up = false;
                        down = false;
                    } else if (rand >= 75 && rand < 100) {
                        right = true;
                        left = false;
                        up = false;
                        down = false;
                    }
                    timeToRandom = 120;
                } else {
                    timeToRandom--;
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
                    frameOneal++;
                    if (frameOneal > intervalOneal) {
                        frameOneal = 0;
                        indexAnimOneal++;
                        if (indexAnimOneal > 2) {
                            indexAnimOneal = 0;
                        }
                    }
                    if (right) {
                        img = rightImageoneal()[indexAnimOneal];
                    } else if (left) {
                        img = leftImageoneal()[indexAnimOneal];
                    } else if (up) {
                        img = upImageoneal()[indexAnimOneal];
                    } else if (down) {
                        img = downImageoneal()[indexAnimOneal];
                    }
                } else {
                    img = downImageoneal()[1];
                }
            } else {
                img = Sprite.oneal_dead.getFxImage();
                frameOneal++;
                if (frameOneal > 40) {
                    BombermanGame.getMobs().remove(this);
                }
            }
        }



        public boolean deadOneal() {
            int size = Sprite.SCALED_SIZE;

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


