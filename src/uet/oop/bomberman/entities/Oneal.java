package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.Items.*;
import uet.oop.bomberman.soundEffect.Sound;

public class Oneal extends Entity {
    public Blocked blocked = new Blocked();
    public int speed = 2;
    public int frameOneal = 0, intervalOneal = 20, indexAnimOneal = 0;
    public static Image[] animUpOneal, animDownOneal, animLeftOneal, animRightOneal;

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }

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
            if(!timeStop.isTimeStop) {
                setSpeed(2);
            }

            Entity player = BombermanGame.getBomberman();
            if (deadOneal()) {
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
                            if(!blocked.canMove(this.x + speed, this.y)) {
                                right = false;
                                left = false;
                                up = false;
                                down = true;
                            } else {
                                right = true;
                                left = false;
                                up = false;
                                down = false;
                            }

                        } else if(player.x / 32 < this.x / 32) {
                            if(!blocked.canMove(this.x - speed, this.y)) {
                                right = false;
                                left = false;
                                up = true;
                                down = false;
                            } else {
                                left = true;
                                up = false;
                                down = false;
                                right = false;
                            }
                        } else if(player.y /32 > this.y /32) {
                            if(!blocked.canMove(this.x, this.y + speed)) {
                                left = false;
                                up = false;
                                down = false;
                                right = true;
                            } else {
                                left = false;
                                up = false;
                                down = true;
                                right = false;
                            }
                        } else if(player.y /32 < this.y / 32) {
                            if(!blocked.canMove(this.x, this.y - speed)) {
                                left = true;
                                up = false;
                                down = false;
                                right = false;
                            } else {
                                left = false;
                                up = true;
                                down = false;
                                right = false;
                            }
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
                    BombermanGame.getBomberman().score += 200;
                    BombermanGame.getMobs().remove(this);
                }
            }
        }



        public boolean deadOneal() {

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


