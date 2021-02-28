package Test;
import Controller.*;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import org.junit.Before;
import org.junit.Test;
import Model.ModelPlayerData;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import Model.ModelHighscore;
import javafx.scene.control.Button;
import org.junit.runner.RunWith;
import java.io.*;
import static org.junit.Assert.*;


public class TestHighscore {

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
        ModelPlayerData test = new ModelPlayerData();
        highscore_controller.highscoredata.getPlayer_save().clear();
        test.setScore(200);
        highscore_controller.highscoredata.getPlayer_save().add(test);
        highscore_controller.SaveData();
        highscore_controller.getSave();
        assertEquals(200,highscore_controller.highscoredata.getPlayer_save().get(0).getScore());
    }

    @Test
    public void testHighscore()
    {
        ModelPlayerData test = new ModelPlayerData();
        ModelPlayerData test2 = new ModelPlayerData();
        ModelPlayerData test3 = new ModelPlayerData();
        ModelPlayerData test4 = new ModelPlayerData();
        highscore_controller.highscoredata.getPlayer_save().clear();
        test.setScore(200);
        test2.setScore(300);
        test3.setScore(400);
        test4.setScore(500);
        highscore_controller.highscoredata.getPlayer_save().add(test);
        highscore_controller.highscoredata.getPlayer_save().add(test2);
        highscore_controller.highscoredata.getPlayer_save().add(test3);
        highscore_controller.highscoredata.getPlayer_save().add(test4);
        highscore_controller.createHighscoreTexts();
        highscore_controller.AddHighscorePlayers();
        for(ModelPlayerData player : highscore_controller.highscoredata.getPlayer_save())
        {
            int index = highscore_controller.highscoredata.getPlayer_save().indexOf(player);
            if(index == highscore_controller.highscoredata.getPlayer_save().size()-1){index -= 1;}
            if(highscore_controller.highscoredata.getPlayer_save().get(index).getScore() < highscore_controller.highscoredata.getPlayer_save().get(index+1).getScore())
            {
                fail();
            }
        }
    }


}
