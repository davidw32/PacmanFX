package Model;

import Controller.Gamefield;
import Controller.Gameoptions;
import Controller.HSHL;
import Controller.Highscore;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class ModelStartscreen implements Model {

    private GridPane startscreen_grid; // Startmenüfeld
    private GridPane highscore_grid; // Higscoreliste
    private GridPane game_options_grid; // Spieloptionen
    private GridPane gamefield_grid; // Spielfeld
    private GridPane hshl_grid; //HSHL Spielfeld
    private Scene main_scene;
    private Button start_button;
    private Button highscore_list;
    private Button game_options;
    private Button quit_game;
    private Gamefield gamefieldController;
    private HSHL hshlController;
    private Media hover_StartscreenButtons =  new Media(new File("./src/Audio/startscreen_button.wav").toURI().toString());
    private MediaPlayer buttonsound_player = new MediaPlayer(hover_StartscreenButtons);
    private MediaPlayer buttonsound_player_2 = new MediaPlayer(hover_StartscreenButtons);
    private MediaPlayer buttonsound_player_3 = new MediaPlayer(hover_StartscreenButtons);
    private MediaPlayer buttonsound_player_4 = new MediaPlayer(hover_StartscreenButtons);
    private Highscore highscore;
    private Gameoptions gameoptions;
    private String map_name = "CLASSIC";

    public String getMap_name() { return map_name; }
    public MediaPlayer getButtonsound_player() { return buttonsound_player; }
    public MediaPlayer getButtonsound_player_2() { return buttonsound_player_2; }
    public MediaPlayer getButtonsound_player_3() { return buttonsound_player_3; }
    public MediaPlayer getButtonsound_player_4() { return buttonsound_player_4; }
    public Gameoptions getGameoptions() { return gameoptions; }
    public Gamefield getGamefieldController(){return gamefieldController; }
    public Gamefield getHSHLController(){return hshlController; }
    public Button getStart_button(){return this.start_button;}
    public Button getGame_options() { return game_options; }
    public Highscore getHighscore() { return highscore; }
    public GridPane getStartScreen(){return startscreen_grid;}
    public GridPane getGameScreen(){return gamefield_grid;}
    public GridPane getHSHLScreen(){return hshl_grid;}
    public GridPane getOptionsScreen(){return game_options_grid;}
    public GridPane getHighscoreScreen(){return highscore_grid;}


    public void setMap_name(String map_name) { this.map_name = map_name; }
    public void setStartscreen_grid(GridPane startscreen_grid){this.startscreen_grid = startscreen_grid;}
    public void setGamefieldController(Gamefield GamefieldController){this.gamefieldController = GamefieldController; }
    public void setHshlController(HSHL hshlController){this.hshlController = hshlController; }
    public void setMain_scene(Scene main_scene){this.main_scene = main_scene;}
    public Scene getMain_scene(){return this.main_scene;}
    public void setMatrix(GridPane matrix){this.gamefield_grid = matrix;}
    public void setHighscore(Highscore highscore) { this.highscore = highscore; }
    public void setGameoptions(Gameoptions gameoptions) { this.gameoptions = gameoptions; }
    public void setScreens(GridPane start, GridPane highscore, GridPane options, GridPane gamefield, GridPane hshl)
    {
        startscreen_grid = start;
        highscore_grid = highscore;
        game_options_grid = options;
        this.gamefield_grid = gamefield;
        hshl_grid=hshl;
    }
    public void set_buttons(Button start_button,Button highscore_list, Button game_options,Button quit_game)
    {
        this.start_button = start_button;
        this.highscore_list = highscore_list;
        this.game_options = game_options;
        this.quit_game = quit_game;
    }
}
