package Model;

import Controller.Gamefield;
import Controller.Gameoptions;
import Controller.HSHL;
import Controller.Highscore;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class ModelPausescreen implements Model {

    private Button resume_bt;
    private Button highscore_bt;
    private Button options_bt;
    private Button quit_bt;
    private GridPane gamefield_screen;
    private GridPane options_screen;
    private GridPane highscore_screen;
    private GridPane start_screen;
    private GridPane matrix_pause;
    private GridPane hshl_matrix;
    private Scene scene;
    private Gamefield gamefield;
    private HSHL hshlfield;
    private Highscore highscore;
    private Timeline Kolchecker_gamefield;

    public Timeline getKolchecker_gamefield() { return Kolchecker_gamefield; }
    public GridPane getMatrix_pause() { return matrix_pause; }
    public void setMatrix_pause(GridPane matrix_pause){this.matrix_pause = matrix_pause;}
    public GridPane getMatrix(){return gamefield_screen;}
    public GridPane getHshl_matrix() { return hshl_matrix; }

    public Gamefield getGamefield() { return gamefield; }
    public HSHL getHshlField() { return hshlfield; }
    public GridPane getOptions_screen() { return options_screen; }
    public GridPane getHighscore_screen() { return highscore_screen; }
    public GridPane getStart_screen() { return start_screen; }
    public Scene getMain_scene() { return scene; }
    private Media hover_StartscreenButtons =  new Media(new File("./src/Audio/startscreen_button.wav").toURI().toString());
    private MediaPlayer buttonsound_player = new MediaPlayer(hover_StartscreenButtons);
    private MediaPlayer buttonsound_player_2 = new MediaPlayer(hover_StartscreenButtons);
    private MediaPlayer buttonsound_player_3 = new MediaPlayer(hover_StartscreenButtons);
    private MediaPlayer buttonsound_player_4 = new MediaPlayer(hover_StartscreenButtons);
    public Highscore getHighscore() { return highscore; }
    public MediaPlayer getButtonsound_player() { return buttonsound_player; }
    public MediaPlayer getButtonsound_player_2() { return buttonsound_player_2; }
    public MediaPlayer getButtonsound_player_3() { return buttonsound_player_3; }
    public MediaPlayer getButtonsound_player_4() { return buttonsound_player_4; }

    public void setKolchecker_gamefield(Timeline kolchecker_gamefield) { Kolchecker_gamefield = kolchecker_gamefield; }
    public void setController(Gamefield gamefield,Highscore highscore, HSHL hshlfield)
    {
        this.gamefield = gamefield;
        this.highscore = highscore;
        this.hshlfield = hshlfield;
    }
    public void setGridsAndScene(GridPane gamefield_screen, GridPane options_screen, GridPane highscore_screen, GridPane start_screen,GridPane hshl_matrix, Scene scene)
    {
        this.gamefield_screen = gamefield_screen;
        this.options_screen = options_screen;
        this.highscore_screen = highscore_screen;
        this.start_screen = start_screen;
        this.hshl_matrix = hshl_matrix;
        this.scene = scene;
    }


    public ModelPausescreen(Button resume_bt,Button highscore_bt,Button options_bt,Button quit_bt)
    {
        this.resume_bt = resume_bt;
        this.highscore_bt = highscore_bt;
        this.options_bt = options_bt;
        this.quit_bt = quit_bt;
    }

}
