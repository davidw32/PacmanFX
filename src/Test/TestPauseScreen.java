package Test;
import Controller.*;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestPauseScreen {

    public Scene main_scene;
    public FXMLLoader loader_gamefield = new FXMLLoader(getClass().getResource("/View/ViewGame.fxml"));
    public FXMLLoader loader_startscreen = new FXMLLoader(getClass().getResource("/View/ViewStartscreen.fxml"));
    public FXMLLoader loader_pausescreen = new FXMLLoader(getClass().getResource("/View/ViewPauseScreen.fxml"));
    public FXMLLoader loader_highscore = new FXMLLoader(getClass().getResource("/View/ViewHighscorelist.fxml"));
    public FXMLLoader loader_options = new FXMLLoader(getClass().getResource("/View/ViewGameoptions.fxml"));
    public FXMLLoader loader_HSHL = new FXMLLoader(getClass().getResource("/View/ViewHSHL.fxml"));

    public Gamefield gamefield_controller;
    public Startscreen startscreen_controller;
    public PauseScreen pausescreen_controller;
    public Highscore highscore_controller;
    public Gameoptions gameoptions_controller;
    public HSHL hshl_controller;

    public GridPane root = new GridPane();
    public GridPane start = new GridPane();
    public GridPane root_matrix = new GridPane();
    public GridPane highscore_matrix = new GridPane();
    public GridPane options_matrix = new GridPane();
    public GridPane hshl_matrix = new GridPane();


    @Before
    public void setUp() throws  Exception
    {
        JFXPanel jfxPanel = new JFXPanel();

        start = loader_startscreen.load();
        root = loader_gamefield.load();
        root_matrix = loader_pausescreen.load();
        highscore_matrix = loader_highscore.load();
        options_matrix = loader_options.load();
        hshl_matrix = loader_HSHL.load();


        pausescreen_controller = loader_pausescreen.getController();
        hshl_controller = loader_HSHL.getController();
        startscreen_controller = loader_startscreen.getController();
        gameoptions_controller = loader_options.getController();
        pausescreen_controller = loader_pausescreen.getController();
        gamefield_controller = loader_gamefield.getController();
        highscore_controller = loader_highscore.getController();

    }

    @Test
    public void testSwitchScreen() throws  Exception
    {
        main_scene = new Scene(root_matrix);
        pausescreen_controller.setTest();
        highscore_controller.setTest();
        startscreen_controller.startscreen.setHighscore(highscore_controller);
        highscore_controller.highscoredata.setGameoptions(gameoptions_controller);
        pausescreen_controller.pauseScreen.setGridsAndScene(root,options_matrix,highscore_matrix,start,hshl_matrix,main_scene);
        pausescreen_controller.pauseScreen.setController(gamefield_controller,highscore_controller,hshl_controller);
        gamefield_controller.setScreens(startscreen_controller,pausescreen_controller,main_scene);
        highscore_controller.highscoredata.getGameoptions().gameoptiondata.getGameoptionsave().setMap("CLASSIC");
        pausescreen_controller.pauseScreen.setGridsAndScene(root,options_matrix,highscore_matrix,start,hshl_matrix,main_scene);
        pausescreen_controller.pauseScreen.setController(gamefield_controller,highscore_controller,hshl_controller);


        gameoptions_controller.setJoystick(gameoptions_controller,gamefield_controller.getPlayerController());
        assertEquals(root_matrix,main_scene.getRoot());
        pausescreen_controller.highscore();
        assertEquals(highscore_matrix,main_scene.getRoot());
        pausescreen_controller.options();
        assertEquals(options_matrix,main_scene.getRoot());
    }

}
