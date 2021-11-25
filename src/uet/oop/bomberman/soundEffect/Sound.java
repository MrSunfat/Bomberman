package uet.oop.bomberman.soundEffect;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Sound {
    private MediaPlayer mediaPlayer;
    public void soundEffect(String nameSong) {
        String path = "res/media/" + nameSong;

        //Instantiating Media class
        Media media = new Media(new File(path).toURI().toString());

        //Instantiating MediaPlayer class
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.2);
        //by setting this property to true, the audio will be played
        mediaPlayer.play();

    }
}
