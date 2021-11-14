package uet.oop.bomberman.entities;

import uet.oop.bomberman.*;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Arrays;

public class Blocked {
    public String[] blocks = BombermanGame.map;

    public boolean isFree(int nextX, int nextY) {
        int size = Sprite.SCALED_SIZE;

        int nextX_1 = nextX / size;
        int nextY_1 = nextY / size;
        System.out.println(nextY_1 +"----" + nextX_1);
        int nextX_2 = (nextX + size - 1) / size;
        int nextY_2 = nextY / size;
        System.out.println(nextY_2 +"----" + nextX_2);
        int nextX_3 = nextX / size;
        int nextY_3 = (nextY + size - 1) / size;
        System.out.println(nextY_3 +"----" + nextX_3);
        int nextX_4 = (nextX + size - 1) / size;
        int nextY_4 = (nextY + size - 1) / size;
        System.out.println(nextY_4 +"----" + nextX_4);
//        System.out.println(BombermanGame.getStillObjects().get(31*nextY_1 + nextX_1));

//        return !((blocks[nextY_1].charAt(nextX_1) == '#' || blocks[nextY_1].charAt(nextX_1) == '*')
//                || (blocks[nextY_2].charAt(nextX_2) == '#' || blocks[nextY_2].charAt(nextX_2) == '*')
//                || (blocks[nextY_3].charAt(nextX_3) == '#' || blocks[nextY_3].charAt(nextX_3) == '*')
//                || (blocks[nextY_4].charAt(nextX_4) == '#' || blocks[nextY_4].charAt(nextX_4) == '*'));

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
