package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.BombHandle.Bomb;

import uet.oop.bomberman.entities.Items.*;
import uet.oop.bomberman.graphics.Levels;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.Bomber;

import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    private static Entity bomberman;
    public static Entity bomb = null;

    private GraphicsContext gc;
    private Canvas canvas;
    private Levels levels;
    public static String[] map;
    public static char[][] arrMap;

    private static List<Entity> entities = new ArrayList<>();
    private static List<Entity> stillObjects = new ArrayList<>();
    public static List<Entity> bombs = new ArrayList<>();
    public static List<Entity> bombExplosions = new ArrayList<>();
    public static List<Entity> mobs = new ArrayList<>();
    public static List<Item> items = new ArrayList<>();
    public static List<Entity> grass = new ArrayList<>();

    public static List<Entity> getStillObjects() {
        return stillObjects;
    }
    public static List<Entity> getEntities() {
        return entities;
    }
    public static List<Item> getItems() {
        return items;
    }
    public static List<Entity> getMobs() {
        return mobs;
    }

    public static Entity getBomberman() {
        return bomberman;
    }


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);

        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                    render();
                    update();
            }
        };

        timer.start();

        bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);

        createMap();

        scene.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.UP) || keyEvent.getCode().equals(KeyCode.W)) {
                bomberman.up = true;
            }
            if(keyEvent.getCode().equals(KeyCode.DOWN) || keyEvent.getCode().equals(KeyCode.S)) {
                bomberman.down = true;
            }
            if(keyEvent.getCode().equals(KeyCode.LEFT) || keyEvent.getCode().equals(KeyCode.A)) {
                bomberman.left = true;
            }
            if(keyEvent.getCode().equals(KeyCode.RIGHT) || keyEvent.getCode().equals(KeyCode.D)) {
                bomberman.right = true;
            }
            if(keyEvent.getCode().equals(KeyCode.SPACE)) {
                if(Bomb.bombNum != 0) {
                    int x,y;
                    x = (bomberman.getX() + (Sprite.SCALED_SIZE) / 2 ) / Sprite.SCALED_SIZE;
                    y = (bomberman.getY() + (Sprite.SCALED_SIZE) / 2 ) / Sprite.SCALED_SIZE;
                    bomb = new Bomb(x, y, Sprite.bomb.getFxImage());
                    bombs.add(bomb);
                    Bomb.bombNum--;
//                    System.out.println(entities);
//                    System.out.println("Bomb Y---X: " + bomb.getY() + "----" + bomb.getX());
//                    System.out.println("Player Y---X: " + bomberman.getY() + "----" + bomberman.getX());
//                    System.out.println();
                }
            }
        });

        scene.setOnKeyReleased(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.UP) || keyEvent.getCode().equals(KeyCode.W)) {
                bomberman.up = false;
            }
            if(keyEvent.getCode().equals(KeyCode.DOWN) || keyEvent.getCode().equals(KeyCode.S)) {
                bomberman.down = false;
            }
            if(keyEvent.getCode().equals(KeyCode.LEFT) || keyEvent.getCode().equals(KeyCode.A)) {
                bomberman.left = false;
            }
            if(keyEvent.getCode().equals(KeyCode.RIGHT) || keyEvent.getCode().equals(KeyCode.D)) {
                bomberman.right = false;
            }
        });

    }


    public void createMap() {
        levels = new Levels();
        levels.addLevelToLevelList();
        levels.loadLevelFromFile(levels.levelList.get(0));
        map = levels.getMap();
        arrMap = levels.getArrMap();
        for (int i = 0; i < levels.getHeight(); i++) {
            for (int j = 0; j < levels.getWidth(); j++) {
                Entity object;
//                char entity = map[i].charAt(j);
                char entity = arrMap[i][j];
                switch (entity) {
                    case '#': {
                        object = new Wall(j, i, Sprite.wall.getFxImage());
                        break;
                    }
                    case '*': {
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        grass.add(new Grass(j,i, Sprite.grass.getFxImage()));
                        break;
                    }
                    case '1' : {
                        mobs.add(new Balloom(j, i, Sprite.balloom_left1.getFxImage()));
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        break;
                    }
                    case '2' : {
                        mobs.add(new Oneal(j, i, Sprite.oneal_left1.getFxImage()));
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        break;
                    }
                    case 'b' : {
                        items.add(new BombItem(j, i, Sprite.powerup_bombs.getFxImage()));
                        grass.add(new Grass(j,i, Sprite.grass.getFxImage()));
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        break;
                    }
                    case 'f' : {
                        items.add(new FlameItem(j, i, Sprite.powerup_flames.getFxImage()));
                        grass.add(new Grass(j,i, Sprite.grass.getFxImage()));
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        break;
                    }
                    case 's' : {
                        items.add(new SpeedItem(j, i, Sprite.powerup_speed.getFxImage()));
                        grass.add(new Grass(j,i, Sprite.grass.getFxImage()));
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        break;
                    }
                    case 'x' : {
                        items.add(new Portal(j, i, Sprite.portal.getFxImage()));
                        grass.add(new Grass(j,i, Sprite.grass.getFxImage()));
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        break;
                    }
                    default: {
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        break;
                    }
                }
                stillObjects.add(object);
            }
        }
    }

    public void update(){
//           entities.forEach(Entity::update);
            for (int i = 0; i< entities.size(); i++) {
                entities.get(i).update();
            }
           for (int i = 0; i < bombs.size(); i++) {
               bombs.get(i).update();
           }
           for (int i = 0; i < mobs.size(); i++) {
               mobs.get(i).update();
           }
           for(int i = 0; i < stillObjects.size(); i++) {
               if(stillObjects.get(i) instanceof Brick) {
                   stillObjects.get(i).update();
               }
           }

//           bombs.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        grass.forEach(g -> g.render(gc));
        stillObjects.forEach(g -> g.render(gc));
        mobs.forEach(g -> g.render(gc));
        bombs.forEach(g -> g.render(gc));
        bombExplosions.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));

    }
}
