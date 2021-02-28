package Test;
import java.io.*;
import static org.junit.Assert.*;

import Controller.*;
import Model.ModelGameoptionData;
import Model.ModelGameoptions;
import Model.ModelPlayerData;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.*;

public class TestGameOption {


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
    public void testSave()
    {
        ModelGameoptionData test = new ModelGameoptionData();
        test.setResolution_x("640");
        test.setResolution_y("800");
        test.setAudio("ON");
        test.setController("ON");
        test.setMap("CLASSIC");
        gameoptions_controller.gameoptiondata.setGameoptionsave(null);
        gameoptions_controller.gameoptiondata.setGameoptionsave(test);
        gameoptions_controller.SaveData();
        gameoptions_controller.gameoptiondata.setGameoptionsave(null);
        gameoptions_controller.getSave();
        assertEquals("640",test.getResolution_x());
        assertEquals("800",test.getResolution_y());

    }

    @Test
    public void changeMap()
    {
        gameoptions_controller.gameoptiondata.setControllers(highscore_controller,gamefield_controller,pausescreen_controller,startscreen_controller,hshl_controller);
        gameoptions_controller.getMap_choose().setText("CLASSIC");
        gameoptions_controller.ChangeMap();
        assertEquals("HSHL",gameoptions_controller.getMap_choose().getText());

        gameoptions_controller.getMap_choose().setText("HSHL");
        gameoptions_controller.ChangeMap();
        assertEquals("CLASSIC",gameoptions_controller.getMap_choose().getText());
    }

    @Test
    public void changeController()
    {
        gameoptions_controller.gameoptiondata.setControllers(highscore_controller,gamefield_controller,pausescreen_controller,startscreen_controller,hshl_controller);
        gameoptions_controller.getJoystick_text().setText("ON");
        gameoptions_controller.ChangeControll();
        assertEquals("OFF",gameoptions_controller.getJoystick_text().getText());

        gameoptions_controller.getJoystick_text().setText("OFF");
        gameoptions_controller.ChangeControll();
        assertEquals("ON",gameoptions_controller.getJoystick_text().getText());
    }


    @Test
    public void changeRes() // ?
    {
        gameoptions_controller.getBox_resolution().setText("640x800");
        gameoptions_controller.ChangeResolution();
        gameoptions_controller.getBox_resolution().setText("720x900");
        gameoptions_controller.ChangeResolution();
        gameoptions_controller.getBox_resolution().setText("800x1000");
    }
}
