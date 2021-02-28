package Test;

import Controller.*;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;

public class TestHSHL {

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
    public void setUp() throws Exception{
        JFXPanel fxPanel = new JFXPanel();

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

        hshl_controller.setScreens(startscreen_controller,pausescreen_controller,main_scene);
    }

    @Test
    public void testStartGame(){
        Assert.assertNotEquals(hshl_controller.getPlayerController().getModel().getKolchecker_gamefield(), hshl_controller.Mgamefield.KolChecker);
        Assert.assertNotEquals(hshl_controller.Mgamefield.KolChecker, pausescreen_controller.pauseScreen.getKolchecker_gamefield());
        hshl_controller.StartGame();
        Assert.assertEquals(hshl_controller.getPlayerController().getModel().getKolchecker_gamefield(), hshl_controller.Mgamefield.KolChecker);
        Assert.assertEquals(hshl_controller.Mgamefield.KolChecker, pausescreen_controller.pauseScreen.getKolchecker_gamefield());
    }

    @Test
    public void testCheckLife(){
        Assert.assertEquals(3, hshl_controller.getPlayerController().getModel().getLife());
        Assert.assertEquals(2,hshl_controller.CheckLife());
        Assert.assertEquals(2, hshl_controller.getPlayerController().getModel().getLife());
        Assert.assertEquals(1,hshl_controller.CheckLife());
        Assert.assertEquals(1, hshl_controller.getPlayerController().getModel().getLife());
        Assert.assertEquals(0,hshl_controller.CheckLife());
        Assert.assertEquals(0,hshl_controller.CheckLife());
    }

    @Test
    public void testSetCrossingEdges(){
        Assert.assertNotEquals(true, hshl_controller.Mgamefield.getCross().get(27).getLeft());
        Assert.assertNotEquals(true, hshl_controller.Mgamefield.getCross().get(30).getRight());
        Assert.assertNotEquals(hshl_controller.Mgamefield.getCross().get(30), hshl_controller.Mgamefield.getCross().get(27).getCrossLeft());
        Assert.assertNotEquals(hshl_controller.Mgamefield.getCross().get(27), hshl_controller.Mgamefield.getCross().get(30).getCrossRight());

        hshl_controller.setCrossingEdges(hshl_controller.Mgamefield.getCross());

        Assert.assertEquals(true, hshl_controller.Mgamefield.getCross().get(27).getLeft());
        Assert.assertEquals(true, hshl_controller.Mgamefield.getCross().get(30).getRight());
        Assert.assertEquals(hshl_controller.Mgamefield.getCross().get(30), hshl_controller.Mgamefield.getCross().get(27).getCrossLeft());
        Assert.assertEquals(hshl_controller.Mgamefield.getCross().get(27), hshl_controller.Mgamefield.getCross().get(30).getCrossRight());
    }

    @Test
    public void ConstructNull()
    {
        Assert.assertNotNull(hshl_controller.Mgamefield);
    }

    @Test
    public void TestAudio()
    {
        assertNotNull(hshl_controller.Mgamefield.getMediaPlayer());
        hshl_controller.Mgamefield.getMediaPlayer().play();
    }
}
