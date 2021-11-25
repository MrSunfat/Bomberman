package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;


public abstract class Entity {
    public boolean up, down, right, left;
    public boolean moving;
    public int frame = 0, interval, indexAnim = 0;
    public boolean isLive = true;
    public int playerLifeNumb = 2;
    public int score = 0;
    public boolean isLeftBomb = false;
    int size = Sprite.SCALED_SIZE;
    public int speed;

    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x = 0;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y = 0;

    protected Image img;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity( int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public Entity(){}

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
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

    public boolean isExplode(int posX, int posY) {
        for (int i = 0; i < BombermanGame.bombExplosions.size(); i++) {
            if(BombermanGame.bombExplosions.get(i).getX() / 32 == posX
            && BombermanGame.bombExplosions.get(i).getY() / 32 == posY) {
                return true;

            }
        }
        return false;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
    public abstract void update();
}
