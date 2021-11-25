package uet.oop.bomberman.graphics;

public class Level {
    protected int width, height, level;
    protected String[] map;
    protected char[][] arrMap;

    public String[] getMap() {
        String[] map = this.map;
        return map;
    }

    public char[][] getArrMap() {
        arrMap = new char[map.length][];
        for(int i = 0; i < map.length; i++) {
            arrMap[i] = map[i].toCharArray();
        }
        return arrMap;
    }

    public int getHeight() {
        return height;
    }

    public int getLevel() {
        return level;
    }

    public int getWidth() {
        return width;
    }
}
