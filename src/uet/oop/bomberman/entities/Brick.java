package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Items.*;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;

public class Brick extends Entity{
    public int frameBrick = 0, intervalBrick = 4, indexAnimBrick = 0;

    public Brick(int x, int y, Image img) {
        super(x, y, img);
    }

    public static Image[] explodedBrick() {
        Image[] img = new Image[]{
                Sprite.brick_exploded.getFxImage(),
                Sprite.brick_exploded1.getFxImage(),
                Sprite.brick_exploded2.getFxImage()
        };
        return img;
    }

    @Override
    public void update() {
        if(!isLive) {
            frameBrick++;
            if(frameBrick == intervalBrick) {
                frameBrick = 0;
                indexAnimBrick++;
                if(indexAnimBrick > 2) {
                    indexAnimBrick = 0;
                    int xPos = x/32;
                    int yPos = y/32;
                    List<Entity> objects = BombermanGame.getStillObjects();
                    if (BombItem.isBombItem(BombermanGame.arrMap, yPos, xPos)) {
                        objects.set(31 * yPos + xPos, new BombItem(xPos, yPos, Sprite.powerup_bombs.getFxImage()));

                    } else if (SpeedItem.isSpeedItem(BombermanGame.arrMap, yPos, xPos)) {
                        objects.set(31 * yPos + xPos, new SpeedItem(xPos, yPos, Sprite.powerup_speed.getFxImage()));

                    } else if (FlameItem.isFlameItem(BombermanGame.arrMap, yPos, xPos)) {
                        objects.set(31 * yPos + xPos, new FlameItem(xPos, yPos, Sprite.powerup_flames.getFxImage()));

                    } else if (LiveItem.isLiveItem(BombermanGame.arrMap, yPos , xPos)) {
                        objects.set(31 * yPos + xPos, new LiveItem(xPos, yPos, Sprite.powerup_detonator.getFxImage()));

                    } else if (Portal.isPortal(BombermanGame.arrMap, yPos , xPos)) {
                        objects.set(31 * yPos + xPos, new Portal(xPos, yPos, Sprite.portal.getFxImage()));

                    } else if (timeStop.isTimeStopItem(BombermanGame.arrMap, yPos , xPos)) {
                        objects.set(31 * yPos + xPos, new timeStop(xPos, yPos, Sprite.timeStop.getFxImage()));

                    } else {
                        objects.set(31*yPos + xPos, new Grass(xPos, yPos, Sprite.grass.getFxImage()));
                    }
                }
            }
            img = explodedBrick()[indexAnimBrick];
        }
    }
}
