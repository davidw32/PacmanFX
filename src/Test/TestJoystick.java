package Test;
import Controller.*;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import net.java.games.input.*;
import net.java.games.input.Controller;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.experimental.theories.suppliers.TestedOn;

public class TestJoystick {
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
        gameoptions_controller.setJoystick(gameoptions_controller,gamefield_controller.getPlayerController());

    }

    @Before
    public void controllerSetup()
    {
        Controller[] ca = ControllerEnvironment.getDefaultEnvironment().getControllers();
        for(Controller con : ca)
        {
            if(con.getType() == Controller.Type.GAMEPAD)
            {
                assertTrue(true);
            }
        }
    }

    @Test
    public void getControllerType() // Kein Controller = Null
    {
        Controller[] ca = ControllerEnvironment.getDefaultEnvironment().getControllers();
        for(Controller con : ca)
        {
            if(con.getType() == Controller.Type.GAMEPAD)
            {
                gameoptions_controller.gameoptiondata.getJoystick().joystick.setMain_joystick(con);
            }
        }
    }


}

