package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.BombHandle.Bomb;

import uet.oop.bomberman.entities.Items.*;
import uet.oop.bomberman.graphics.Levels;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.soundEffect.Sound;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    private static Entity bomberman;
    public static Entity bomb = null;

    private GraphicsContext gc;
    private GraphicsContext gcHeader;
    private GraphicsContext gcHeaderBackground;
    private Canvas canvas;
    private Canvas header;
    private Canvas headerBackground;
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
    public int time = 300;
    public int countTime = 0;
    public boolean isRunning = true;
    public int countToRestart = 0;
    public int countToNextLevel = 0;
    public int level = 1;
    public Sound sound = new Sound();
    public boolean isPlaySound = false;
    public static MediaPlayer mediaPlayer;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        String path = "res/media/nhacnen.wav";
        //Instantiating Media class
        Media media = new Media(new File(path).toURI().toString());

        //Instantiating MediaPlayer class
        mediaPlayer = new MediaPlayer(media);

        //by setting this property to true, the audio will be played
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
        mediaPlayer.setVolume(0.1);

        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        header = new Canvas(Sprite.SCALED_SIZE*WIDTH, Sprite.SCALED_SIZE);
        headerBackground = new Canvas(Sprite.SCALED_SIZE*WIDTH, Sprite.SCALED_SIZE);
        canvas.setTranslateY(32);

        gc = canvas.getGraphicsContext2D();
        gcHeader = header.getGraphicsContext2D();
        gcHeaderBackground = headerBackground.getGraphicsContext2D();

        gc.clearRect(0, 0, Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE);
        gcHeader.clearRect(0, 0, Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE);
        gcHeaderBackground.clearRect(0, 0, Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE);


        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);
        root.getChildren().add(headerBackground);
        root.getChildren().add(header);

        // Tao scene
        Scene scene = new Scene(root);
        stage.setTitle("Bomberman");

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                    if(isRunning) {
                        render();
                        update();

                        if(isCompletedLevel() && level < levels.levelList.size()) {
                            countToNextLevel++;
                            drawText("Level " + (level + 1));
//                            mediaPlayer.stop();
                            if(!isPlaySound) {
                                mediaPlayer.stop();
                                sound = new Sound();
                                sound.soundEffect("levelup.mp3");  //11s
                                isPlaySound = true;
                            }
                            if(countToNextLevel >= 120) {
                                level++;
                                nextLevel();
                                countToNextLevel = 0;
                                mediaPlayer.play();
                            }
                        } else if(isCompletedLevel() && level == levels.levelList.size()) {
                            countToNextLevel++;
//                            mediaPlayer.stop();

                            if(countToNextLevel < 660) {
                                drawText("VICTORY");
                                if(!isPlaySound) {
                                    mediaPlayer.stop();
                                    sound = new Sound();
//                                    sound.soundEffect("win.wav");  7s
                                    sound.soundEffect("minecraftwin.mp3");  //11s
                                    isPlaySound = true;
                                }
                            }
                            if (countToNextLevel >= 660 && countToNextLevel < 780) {
                                drawText("Level 1");

                            }
                            if(countToNextLevel >= 780) {
                                level = 1;
                                mediaPlayer.play();
                                newGame();
                                countToNextLevel = 0;
                            }
                        }
                    } else {
                        countToRestart++;
                        level = 1;
//                        mediaPlayer.stop();
                        if(countToRestart < 120) {
                            if(!isPlaySound) {
                                mediaPlayer.stop();
                                sound = new Sound();
                                sound.soundEffect("well-be-right-back.mp3");
                                isPlaySound = true;
                            }
                        }
                        if (countToRestart >= 120 && countToRestart < 240) {
                            drawText("Level " + level);
                        }
                        if(countToRestart >= 240) {
                            newGame();
                            countToRestart = 0;
                            isRunning = true;
                            mediaPlayer.play();
                        }
                    }
            }
        };

        timer.start();

        bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);

        createMap(1);

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
//                    sound.soundEffect("Bomb.wav");
                    sound.soundEffect("bomb-has-been-planted-sound-effect-cs-go.mp3");
                    int x,y;
                    x = (bomberman.getX() + (Sprite.SCALED_SIZE) / 2 ) / Sprite.SCALED_SIZE;
                    y = (bomberman.getY() + (Sprite.SCALED_SIZE) / 2 ) / Sprite.SCALED_SIZE;
                    bomb = new Bomb(x, y, Sprite.bomb.getFxImage());
                    bombs.add(bomb);
                    Bomb.bombNum--;
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


    public void createMap(int level) {
        levels = new Levels();
        levels.addLevelToLevelList();
        levels.loadLevelFromFile(levels.levelList.get(level - 1));
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
                        grass.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        break;
                    }
                    case '1' : {
                        Entity balloom = new Balloom(j, i, Sprite.balloom_left1.getFxImage());
                        balloom.left = true;
                        mobs.add(balloom);
                        object = new Grass(j, i, Sprite.grass.getFxImage());

                        break;
                    }
                    case '2' : {
                        Entity oneal = new Oneal(j, i, Sprite.oneal_left1.getFxImage());
                        oneal.left = true;
                        mobs.add(oneal);
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        break;
                    }
                    case '3' : {
                        Entity doll = new Doll(j, i, Sprite.doll_left1.getFxImage());
                        doll.left = true;
                        mobs.add(doll);
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
                    case 'l' : {
                        items.add(new LiveItem(j, i, Sprite.powerup_detonator.getFxImage()));
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
                    case 't' : {
                        items.add(new timeStop(j, i, Sprite.timeStop.getFxImage()));
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
        if(isRunning) {
            endGame();
        }

    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        grass.forEach(g -> g.render(gc));
        stillObjects.forEach(g -> g.render(gc));
        mobs.forEach(g -> g.render(gc));
        bombs.forEach(g -> g.render(gc));
        bombExplosions.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));

//        Gameplay time
        countTime++;
        if(countTime > 60) {
            time--;
            countTime = 0;
        }

        gcHeader.clearRect(0, 0, header.getWidth(), header.getHeight());

//        update thoi gian choi
        gcHeader.fillText(String.valueOf(time), 64, 24);
        gcHeader.strokeText(String.valueOf(time), 64, 24);

//        update score
        gcHeader.fillText(String.valueOf(bomberman.score), 480, 24);
        gcHeader.strokeText(String.valueOf(bomberman.score), 480, 24);

//        update player life
        gcHeader.fillText(String.valueOf(bomberman.playerLifeNumb), 912, 24);
        gcHeader.strokeText(String.valueOf(bomberman.playerLifeNumb), 912, 24);

        drawHeaderBackground();
        drawTiles();
    }

    public void endGame() {
        if(entities.isEmpty() || time == 0) {
            drawText("GAME OVER");
            isRunning = false;
        }
    }

    public void newGame() {
        clearCanvas();
        clearLevel();
        isPlaySound = false;
        Portal.isPortal = false;
        Bomb.bombRate = 1;
        Bomb.bombNum = 1;
        time = 300;
        createMap(level);
        bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);
    }

    public void nextLevel() {
        clearCanvas();
        clearLevel();
        Portal.isPortal = false;
        isPlaySound = false;
        entities.add(bomberman);
        time = 300;

        createMap(level);

        bomberman.setX(32);
        bomberman.setY(32);
    }

    public void drawHeaderBackground() {
        gcHeaderBackground.setFill(Color.LIGHTGREY);
        gcHeaderBackground.fillRect(0 ,0, Sprite.SCALED_SIZE*WIDTH, Sprite.SCALED_SIZE);
    }

    public void drawTiles() {
        gcHeader.setFill(Color.BLACK);
        gcHeader.setFont(new Font("Helvetica", 16));
        gcHeader.fillText(String.valueOf(time), 64, 24);
        gcHeader.strokeText(String.valueOf(time), 64, 24);

        gcHeaderBackground.setFill(Color.BLACK);
        gcHeaderBackground.setFont(new Font("Helvetica", 16));
        gcHeaderBackground.fillText("TIME: ", 12, 24);
        gcHeaderBackground.strokeText("TIME", 12, 24);

        gcHeaderBackground.setFill(Color.BLACK);
        gcHeaderBackground.setFont(new Font("Helvetica", 16));
        gcHeaderBackground.fillText("SCORE: ", 416, 24);
        gcHeaderBackground.strokeText("SCORE", 416, 24);

        gcHeaderBackground.setFill(Color.BLACK);
        gcHeaderBackground.setFont(new Font("Helvetica", 16));
        gcHeaderBackground.fillText("LEFT: ", 864, 24);
        gcHeaderBackground.strokeText("LEFT", 864, 24);
    }

    public void drawText(String text) {
        gc.clearRect(0, 0, Sprite.SCALED_SIZE*WIDTH, Sprite.SCALED_SIZE);

        gcHeaderBackground.setFill(Color.BLACK);
        gcHeaderBackground.fillRect(0, 0, Sprite.SCALED_SIZE*WIDTH, Sprite.SCALED_SIZE);
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, Sprite.SCALED_SIZE*WIDTH, Sprite.SCALED_SIZE*HEIGHT);

        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Helvetica", 50));
        gc.fillText(text, 380, 200);
    }

    public void clearCanvas() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gcHeader.clearRect(0, 0, header.getWidth(), header.getHeight());
        gcHeaderBackground.clearRect(0, 0, headerBackground.getWidth(), headerBackground.getHeight());
    }

    public void clearLevel() {
        if(!stillObjects.isEmpty()) {
            stillObjects.clear();
        }
        if(!entities.isEmpty()) {
            entities.clear();
        }
        if(!bombs.isEmpty()) {
            bombs.clear();
        }
        if(!bombExplosions.isEmpty()) {
            bombExplosions.clear();
        }

        if(!grass.isEmpty()) {
            grass.clear();
        }

        if(!items.isEmpty()) {
            items.clear();
        }
        if(!mobs.isEmpty()) {
            mobs.clear();
        }
    }

    public boolean isCompletedLevel() {
        if(mobs.isEmpty() && Portal.isPortal) {
            return true;
        }
        return false;
    }

}

