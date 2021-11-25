package uet.oop.bomberman.graphics;

import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.Wall;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Levels extends Level{
    public List<String> levelList = new ArrayList<>();

    public void addLevelToLevelList() {
        File file = new File("res/levels");
        String[] paths = file.list();
        assert paths != null;
        for (String path : paths) {
            if(!levelList.contains(path)) {
                levelList.add(path);
            }
        }
    }

    public void loadLevelFromFile(String path) {
        String levelPath = "res/levels/" + path;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(levelPath));

            String levelInfo = bufferedReader.readLine();
            StringTokenizer info = new StringTokenizer(levelInfo);

            level = Integer.parseInt(info.nextToken());
            height = Integer.parseInt(info.nextToken());
            width = Integer.parseInt(info.nextToken());

            map = new String[height];
            for(int i = 0; i < height; i++) {
                map[i] = bufferedReader.readLine();
//                System.out.println(map[i]);
            }
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
//        Levels lv = new Levels();
//        lv.addLevelToLevelList();
//        lv.loadLevelFromFile("Level1.txt");
    }
}
