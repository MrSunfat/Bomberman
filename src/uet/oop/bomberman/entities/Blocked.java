package uet.oop.bomberman.entities;

import uet.oop.bomberman.*;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Arrays;
import java.util.List;

public class Blocked {

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

        List<Entity> objects = BombermanGame.getStillObjects();
        return !((objects.get(31*nextY_1 + nextX_1) instanceof Wall
                || objects.get(31*nextY_1 + nextX_1) instanceof Brick
                || isBomb(nextX_1, nextY_1))

                || (objects.get(31*nextY_2 + nextX_2) instanceof Wall
                || objects.get(31*nextY_2 + nextX_2) instanceof Brick
                || isBomb(nextX_2, nextY_2))

                || (objects.get(31*nextY_3 + nextX_3) instanceof Wall
                || objects.get(31*nextY_3 + nextX_3) instanceof Brick
                || isBomb(nextX_3, nextY_3))

                || (objects.get(31*nextY_4 + nextX_4) instanceof Wall
                || objects.get(31*nextY_4 + nextX_4) instanceof Brick)
                || isBomb(nextX_4, nextY_4));
    }


    public boolean isBomb(int posX, int posY) {
        for (int i = 0; i < BombermanGame.bombs.size(); i++) {
            if(BombermanGame.bombs.get(i).getX() / 32 == posX
                    && BombermanGame.bombs.get(i).getY() / 32 == posY) {
                return true;
            }
        }
        return false;
    }

}
