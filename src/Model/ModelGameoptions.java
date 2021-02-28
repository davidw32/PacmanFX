package Model;

import Controller.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class ModelGameoptions {

    private GridPane game_options_grid;
    private Button resolution;
    private Button goback;
    private Button mute;
    private GridPane pausescreen;
    private GridPane startscreen;
    private Scene scene;
    private int CheckParentScreen = 2;
    private Highscore highscore;
    private Gamefield gamefield;
    private PauseScreen pauseScreen_c;
    private Startscreen startscreen_c;
    private HSHL hshl;
    private Media hover_backButtons =  new Media(new File("./src/Audio/startscreen_button.wav").toURI().toString());
    private MediaPlayer buttonsound_player = new MediaPlayer(hover_backButtons);
    private MediaPlayer buttonsound_player2 = new MediaPlayer(hover_backButtons);
    private MediaPlayer buttonsound_player3 = new MediaPlayer(hover_backButtons);
    private MediaPlayer buttonsound_player4 = new MediaPlayer(hover_backButtons);
    private GridPane pausescreen_matrix;
    private GridPane highscore_matrix;
    private GridPane gamefield_matrix;
    private GridPane options_matrix;
    private GridPane startscreen_matrix;
    private GridPane gameover_matrix;
    private GridPane hshl_matrix;
    private GridPane user_input_matrix;
    private GridPane[] matrix_container = new GridPane[8]; // alle Grids zusammen
    private Joystick joystick;
    private ModelGameoptionData gameoptionsave = new ModelGameoptionData();
    private Button currentHover; // der Button, der momentan ausgewählt ist bei Controllersteuerung
    private String currentController;

    public HSHL getHshl() { return hshl; }
    public MediaPlayer getButtonsound_player2() { return buttonsound_player2; }
    public MediaPlayer getButtonsound_player3() { return buttonsound_player3; }
    public MediaPlayer getButtonsound_player4() { return buttonsound_player4; }
    public String getCurrentController() { return currentController; }
    public Button getCurrentHover() { return currentHover; }
    public GridPane getUser_input_matrix() { return user_input_matrix; }
    public ModelGameoptionData getGameoptionsave() { return gameoptionsave; }
    public Joystick getJoystick() { return joystick; }
    public GridPane getHshl_matrix() { return hshl_matrix; }
    public GridPane[] getMatrix_container() { return matrix_container; }
    public MediaPlayer getButtonsound_player() { return buttonsound_player; }
    public Highscore getHighscore() { return highscore; }
    public Gamefield getGamefield() { return gamefield; }
    public PauseScreen getPauseScreen() { return pauseScreen_c; }
    public Startscreen getStartscreen_c() { return startscreen_c; }
    public int getCheckParentScreen() { return CheckParentScreen; }
    public GridPane getPausescreen() { return pausescreen; }
    public GridPane getStartscreen() { return startscreen; }
    public Scene getScene() { return scene; }
    public GridPane getOptionsScreen() {
        return game_options_grid;
    }

    public void setCurrentController(String currentController) { this.currentController = currentController; }
    public void setCurrentHover(Button currentHover) { this.currentHover = currentHover; }
    public void setStartscreen_c(Startscreen startscreen_c) { this.startscreen_c = startscreen_c; }
    public void setUser_input_matrix(GridPane user_input_matrix) { this.user_input_matrix = user_input_matrix; }
    public void setGameoptionsave(ModelGameoptionData gameoptionsave) { this.gameoptionsave = gameoptionsave; }
    public void setJoystick(Joystick joystick) { this.joystick = joystick; }
    public void SetAllMatrix(GridPane pausescreen_matrix, GridPane highscore_matrix, GridPane gamefield_matrix,
                             GridPane options_matrix, GridPane startscreen_matrix, GridPane hshl_matrix,
                             GridPane user_input_matrix,GridPane gameover_matrix)
    {
        this.pausescreen_matrix = pausescreen_matrix;
        this.highscore_matrix = highscore_matrix;
        this.gamefield_matrix = gamefield_matrix;
        this.options_matrix = options_matrix;
        this.startscreen_matrix = startscreen_matrix;
        this.hshl_matrix = hshl_matrix;
        this.user_input_matrix = user_input_matrix;
        matrix_container[0] = pausescreen_matrix;
        matrix_container[1] = highscore_matrix;
        matrix_container[2] = gamefield_matrix;
        matrix_container[3] = options_matrix;
        matrix_container[4] = startscreen_matrix;
        matrix_container[5] = hshl_matrix;
        matrix_container[6] = user_input_matrix;
        matrix_container[7] = gameover_matrix;
    }
    public void setCheckParentScreen(int CheckParentScreen){this.CheckParentScreen = CheckParentScreen;}
    public void setScene(Scene scene) { this.scene = scene; }
    public void setControllers(Highscore highscore,Gamefield gamefield,PauseScreen pauseScreen_c,Startscreen startscreen_c,HSHL hshl)
    {
        this.highscore = highscore;
        this.gamefield = gamefield;
        this.pauseScreen_c = pauseScreen_c;
        this.startscreen_c = startscreen_c;
        this.hshl = hshl;
    }
    public void set_buttons(Button goback, Button resolution, Button mute) {

        this.goback = goback;
        this.resolution = resolution;
        this.mute = mute;
    }
    public void setGridAndScene(Scene scene,GridPane game_options_grid,GridPane pausescreen,GridPane startscreen)
    {
        this.scene = scene;
        this.game_options_grid = game_options_grid;
        this.pausescreen = pausescreen;
        this.startscreen = startscreen;
    }


    public ModelGameoptions(Button goback)
    {
        this.goback = goback;
    }

}