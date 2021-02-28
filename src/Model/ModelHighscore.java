package Model;

import Controller.Gameoptions;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;

import java.io.File;
import java.util.ArrayList;


/**
 * @Author: Patrick Pavlenko
 */

public class ModelHighscore {

    private int place;
    private Button back;
    private GridPane pausescreen;
    private GridPane startscreen;
    private Scene scene;
    private int CheckParentScreen = 2; // integer zum abchecken von wo der User her kommt aus pausescreen oder startscreen
    private Media hover_StartscreenButtons =  new Media(new File("./src/Audio/startscreen_button.wav").toURI().toString());
    private MediaPlayer buttonsound_player = new MediaPlayer(hover_StartscreenButtons);
    private ArrayList<ModelPlayerData> player_save = new ArrayList<>();
    private ArrayList<ModelPlayer> playerArray = new ArrayList<>(); // Abgespeichterte Informationen über Ranking von hier aufrufbar
    private Text[] names = new Text[10];
    private Text[] points = new Text[10];
    private Gameoptions gameoptions;


    public void setGameoptions(Gameoptions gameoptions) { this.gameoptions = gameoptions; }
    public void setPlayer_save(ArrayList<ModelPlayerData> player_save) { this.player_save = player_save; }
    public void setPlace(int place) { this.place = place; }
    public void setGridsAndScene(GridPane pausescreen,GridPane startscreen,Scene scene)
    {
        this.pausescreen = pausescreen;
        this.startscreen = startscreen;
        this.scene = scene;
    }
    public void setCheckParentScreen(int CheckParentScreen){this.CheckParentScreen = CheckParentScreen;}

    public Gameoptions getGameoptions() { return gameoptions; }
    public int getCheckParentScreen() { return CheckParentScreen; }
    public ArrayList<ModelPlayerData> getPlayer_save() { return player_save; }
    public GridPane getPausescreen() { return pausescreen; }
    public GridPane getStartscreen() { return startscreen; }
    public Scene getScene() { return scene; }
    public MediaPlayer getButtonsound_player() { return buttonsound_player; }
    public int getPlace() { return place; }
    public Text[] getPoints() { return points; }
    public Text[] getNames() { return names; }
    public void setPoints(String point, int position) { this.points[position].setText(point); }
    public void setNames(String name, int position) { this.names[position].setText(name); }

    public ModelHighscore(Button back)
    {
        this.back = back;
    }
}
