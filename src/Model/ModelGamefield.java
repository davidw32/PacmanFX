package Model;
import Controller.Ghost;
import Controller.Highscore;
import Controller.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
/***
 * @Author: Patrick Pavlenko
 * @version: 0.1
 *
 */

public class ModelGamefield implements Model{

    protected Scene scene;
    protected Player playerController;  // Player-Controller inkludierte Tag
    protected Ghost ghostsController;  // Ghost-Controller inkludierte Tag
    protected AnchorPane outter_zone;
    protected ArrayList<ModelCrossing> cross;
    protected ArrayList<Circle> scorePoints;
    protected Media startSound =  new Media(new File("./src/Audio/start_session.mp3").toURI().toString());
    protected MediaPlayer mediaPlayer = new MediaPlayer(startSound);
    public Timeline KolChecker;
    public Timeline FreezeStart;
    public Timeline Freeze;

    public MediaPlayer getMediaPlayer() { return mediaPlayer; }

    public void setScene(Scene scene){this.scene = scene;}

    public ArrayList<ModelCrossing> getCross() { return cross; }

    public Ghost getGhostsController() { return ghostsController; }

    public ModelGamefield(Player playerController, Ghost ghostsController, AnchorPane outter_zone, ArrayList<ModelCrossing> cross, ArrayList<Circle> scorePoints)
    {
        this.playerController = playerController;
        this.ghostsController = ghostsController;
        this.outter_zone = outter_zone;
        this.cross = cross;
        this.scorePoints = scorePoints;
    }

}
