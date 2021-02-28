package Controller;
import Model.ModelPausescreen;
import Model.ModelPlayerData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import Model.ModelPausescreen;
import Model.Model;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * @Author: Patrick Pavlenko
 */

public class PauseScreen implements  Controller {

        public ModelPausescreen pauseScreen;

        @FXML private Button resume_bt;
        @FXML private Button highscore_bt;
        @FXML private Button options_bt;
        @FXML private Button quit_bt;

        // OnAction Attribut in Startscreen button für die Methode
        // Dadurch wird auf die Methode referenziert

    /**
     * User gelangt wieder zurueck in das Spielfeld
     * Mit Spielfeld wieder interaktivon moeglich
     * Abfrage um welchen Spielfeld (HSHL/CLASSIC ) es sich handelt
     * wechselt der Controllerbuttons zur steuerung von Controller
     */
        @FXML public void startgame()
        {
            pauseScreen.getHighscore().highscoredata.getGameoptions().gameoptiondata.getJoystick().joystick.setGame(); // zu Spielbuttons
            pauseScreen.getHighscore().highscoredata.getGameoptions().gameoptiondata.getJoystick().joystick.getJoystick_input().stop();
            if(pauseScreen.getHighscore().highscoredata.getGameoptions().gameoptiondata.getGameoptionsave().getMap().equals("CLASSIC")) {
                pauseScreen.getKolchecker_gamefield().play();
                pauseScreen.getMain_scene().setRoot(pauseScreen.getMatrix());
                pauseScreen.getGamefield().getPlayerController().getModel().setOpenpausescreen(true);
            }
            else if(pauseScreen.getHighscore().highscoredata.getGameoptions().gameoptiondata.getGameoptionsave().getMap().equals("HSHL")){
                pauseScreen.getHshlField().scene.getWindow().setHeight(540);
                pauseScreen.getHshlField().scene.getWindow().setWidth(1015);

                pauseScreen.getHshlField().Mgamefield.KolChecker.play();
                pauseScreen.getMain_scene().setRoot(pauseScreen.getHshl_matrix());
                pauseScreen.getGamefield().getPlayerController().getModel().setOpenpausescreen(true);
            }
            resume_bt.setStyle("-fx-background-color: black;");
        }

    /**
     * User gelangt in die Gameoptionen innerhalb des Pausenscreens
     * wechselt der Controllerbuttons zur Steuerung von Controller
     */
        @FXML public void options()
        {
            pauseScreen.getMain_scene().setRoot(pauseScreen.getOptions_screen());
            pauseScreen.getHighscore().highscoredata.getGameoptions().gameoptiondata.getJoystick().joystick.setOptions();
            options_bt.setStyle("-fx-background-color: black;");
            pauseScreen.getHighscore().highscoredata.getGameoptions().turnOffButton();
        }
    /**
     * User beendet Spiel und damit setzt sich die Spielsession zurueck
     * User gelangt zum Startscreen
     * wechselt der Controllerbuttons zur Steuerung von Controller
     */
        @FXML public void quit_session()
        {
            if(pauseScreen.getHighscore().highscoredata.getGameoptions().gameoptiondata.getGameoptionsave().getMap().equals("CLASSIC")) {
                pauseScreen.getHighscore().highscoredata.getGameoptions().gameoptiondata.getJoystick().joystick.setDefault(); // zu Startscreen Buttons für Controller
                pauseScreen.getGamefield().SwitchStartscreen();
                pauseScreen.getGamefield().Mgamefield.KolChecker.stop();
            }
            else if(pauseScreen.getHighscore().highscoredata.getGameoptions().gameoptiondata.getGameoptionsave().getMap().equals("HSHL")) {
                pauseScreen.getHighscore().highscoredata.getGameoptions().gameoptiondata.getJoystick().joystick.setDefault(); // zu Startscreen Buttons für Controller
                pauseScreen.getHshlField().SwitchStartscreen();
                pauseScreen.getHshlField().Mgamefield.KolChecker.stop();
            }
            pauseScreen.getMain_scene().setRoot(pauseScreen.getStart_screen());
            quit_bt.setStyle("-fx-background-color: black;");
            pauseScreen.getHighscore().highscoredata.setCheckParentScreen(1);

        }

    /**
     * User gelangt in die Highscore Bestenliste innerhalb des Pausenscreens
     * wechselt der Controllerbuttons zur Steuerung von Controller
     */
    @FXML public void highscore()
        {
            pauseScreen.getHighscore().highscoredata.getGameoptions().gameoptiondata.getJoystick().joystick.getJoystick_input().stop();
            pauseScreen.getHighscore().highscoredata.getGameoptions().gameoptiondata.getJoystick().joystick.getCurrentButtons().clear();
            pauseScreen.getHighscore().highscoredata.getGameoptions().gameoptiondata.getJoystick().joystick.getCurrentButtons().add(pauseScreen.getHighscore().getBackbt());
            pauseScreen.getHighscore().highscoredata.getGameoptions().gameoptiondata.getJoystick().joystick.getJoystick_input().play();
            pauseScreen.getMain_scene().setRoot(pauseScreen.getHighscore_screen());
            highscore_bt.setStyle("-fx-background-color: black;");
        }

    public void setTest() // Audio-hover für Startscreen Buttons
    {
        resume_bt.setOnMouseEntered(e->  pauseScreen.getButtonsound_player().play() );
        highscore_bt.setOnMouseEntered(e->  pauseScreen.getButtonsound_player_2().play() );
        options_bt.setOnMouseEntered(e->  pauseScreen.getButtonsound_player_3().play() );
        quit_bt.setOnMouseEntered(e->  pauseScreen.getButtonsound_player_4().play() );
        // multiplayer.setOnMouseEntered(e-> { startscreen.getButtonsound_player().play(); }); <-- hier bitte multiplayer später
    }

    /**
     * Modelinitialisierung  sowie einige Buttonkonfigurierungen damit
     * Buttons nicht verzoegert Sound abgegeben bei hover
     */
        public void initModel()
        {
            pauseScreen = new ModelPausescreen(resume_bt,highscore_bt,options_bt,quit_bt);
            pauseScreen.getButtonsound_player().setOnEndOfMedia(new Runnable() // Nach Abspielung von buttonhover sound direkt stoppen ,nicht mit lambda ersetzen
            {
                public void run() { pauseScreen.getButtonsound_player().stop(); }
            });
            pauseScreen.getButtonsound_player_2().setOnEndOfMedia(new Runnable()
            {
                public void run() { pauseScreen.getButtonsound_player_2().stop(); }
            });
            pauseScreen.getButtonsound_player_3().setOnEndOfMedia(new Runnable()
            {
                public void run() { pauseScreen.getButtonsound_player_3().stop(); }
            });
            pauseScreen.getButtonsound_player_4().setOnEndOfMedia(new Runnable()
            {
                public void run() { pauseScreen.getButtonsound_player_4().stop(); }
            });
        }


        public ArrayList<Button> getButtons()
        {
            ArrayList<Button> array = new ArrayList<>();
            array.add(resume_bt);
            array.add(highscore_bt);
            array.add(options_bt);
            array.add(quit_bt);
            return array;
        }
        public PauseScreen()
        {
            initModel();
        }
    }

