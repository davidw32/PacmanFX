package Controller;
import Model.ModelStartscreen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Patrick Pavlenko
 */

public class Startscreen implements Controller {

    public ModelStartscreen startscreen;

    @FXML private GridPane root_start;
    @FXML private Button start;
    @FXML private Button highscore_list;
    @FXML private Button game_options;
    @FXML private Button quit_game;
    @FXML private Rectangle background_username;
    @FXML private TextField user_input;
    @FXML private Text username_text;
    @FXML private GridPane user_input_area;
    @FXML private Button submit_name;
    @FXML private Text wrong_input_text;


    /**
     * Methode um bei betaetigung des Startbuttons in das Spielfeld bzw das Spiel zu kommen
     * Classic = normale Spielfeld
     * HSHL = HSHL Spielfeld
     */
    @FXML public void startgame()
    {
        if(startscreen.getGameoptions().gameoptiondata.getGameoptionsave().getMap().equals("CLASSIC"))
        {
            startscreen.getMain_scene().setRoot(startscreen.getGameScreen());
            startscreen.getGamefieldController().StartGame();
            startscreen.getGameoptions().gameoptiondata.getJoystick().joystick.getJoystick_input().stop();
            startscreen.getGameoptions().gameoptiondata.getJoystick().joystick.setCurrentButtons( startscreen.getGameoptions().gameoptiondata.getJoystick().joystick.getGameButtons());
            startscreen.getHighscore().highscoredata.setCheckParentScreen(2);
            startscreen.getGameoptions().gameoptiondata.setCheckParentScreen(2);
            start.setStyle("-fx-background-color:black;");
        }
        else if(startscreen.getGameoptions().gameoptiondata.getGameoptionsave().getMap().equals("HSHL"))
        {
            startscreen.getMain_scene().setRoot(startscreen.getGameoptions().gameoptiondata.getHshl_matrix());
            startscreen.getMain_scene().getWindow().setHeight(540);
            startscreen.getMain_scene().getWindow().setWidth(1015);
            startscreen.getHSHLController().StartGame();
            startscreen.getGameoptions().gameoptiondata.getJoystick().joystick.getJoystick_input().stop();
            startscreen.getGameoptions().gameoptiondata.getJoystick().joystick.setCurrentButtons( startscreen.getGameoptions().gameoptiondata.getJoystick().joystick.getGameButtons());
            startscreen.getHighscore().highscoredata.setCheckParentScreen(2);
            startscreen.getGameoptions().gameoptiondata.setCheckParentScreen(2);
            startscreen.getGamefieldController().getGameovermatrix().setTranslateX(300);
            start.setStyle("-fx-background-color:black;");
        }
    }

    /**
     * Durch diese Method kommt man in die Highscoreliste vom Startmenue
     */
    @FXML public void highscore_list()
    {
        startscreen.getHighscore().highscoredata.setCheckParentScreen(1);
        startscreen.getMain_scene().setRoot(startscreen.getHighscoreScreen());
        startscreen.getGameoptions().gameoptiondata.getJoystick().joystick.getJoystick_input().stop();
        startscreen.getGameoptions().gameoptiondata.getJoystick().joystick.getCurrentButtons().clear();
        startscreen.getGameoptions().gameoptiondata.getJoystick().joystick.getCurrentButtons().add(startscreen.getHighscore().getBackbt());
        startscreen.getGameoptions().gameoptiondata.getJoystick().joystick.getJoystick_input().play();
        highscore_list.setStyle("-fx-background-color:black;");
    }

    /**
     * Damit kommt man ins Optionenmenue im Startmenue
     */
    @FXML public void game_options()
    {
        startscreen.getGameoptions().gameoptiondata.setCheckParentScreen(1);
        startscreen.getMain_scene().setRoot(startscreen.getOptionsScreen());
        startscreen.getGameoptions().gameoptiondata.getJoystick().joystick.setOptions();
        game_options.setStyle("-fx-background-color:black;");
    }
    //Programmende (QUIT Button)
    @FXML public void quit_game() { System.exit(0); }

    public void Startscreen() { startscreen.getMain_scene().setRoot(startscreen.getStartScreen()); }
    public void setTest() // Audio-hover für Startscreen Buttons
    {
       start.setOnMouseEntered(e->  startscreen.getButtonsound_player().play() );
       highscore_list.setOnMouseEntered(e->  startscreen.getButtonsound_player_2().play() );
       game_options.setOnMouseEntered(e->  startscreen.getButtonsound_player_3().play() );
       quit_game.setOnMouseEntered(e->  startscreen.getButtonsound_player_4().play() );
    }

    /**
     * Usereingabe am Anfang beim Start des Progammes
     * eine Validierung wird ausgefuehrt fuer falsche Zeichen oder zu kurzer oder langer Name
     * bei richtiger Eingabe ( letzte else ) wird der Userinput verschwinden
     */
    public void AskUserName() // setzte die Objekte bei der Userabfrage durch Koordinatensetzung mittels Breite/Höhenanteile
    {
        Pattern p = Pattern.compile("[^A-Za-z0-9]"); // Diese Symbole (der Bereich) ist/sind nur erlaubt
        Matcher m = p.matcher(user_input.getText());
        boolean specialSymbol = m.find();
        if ((user_input.getText().length() < 3 | user_input.getText().length() > 16) && !specialSymbol)
        {
            wrong_input_text.setText("Invalid entry: Your name must contain at least 3 or a maximum of 16 characters!");
            wrong_input_text.setVisible(true);
        }
        else if(specialSymbol)
        {
            wrong_input_text.setText("Invalid entry: Make sure there are no special characters in your name!");
            wrong_input_text.setVisible(true);
        }
        else
        {
            user_input_area.setVisible(false);
            startscreen.getGamefieldController().getPlayerController().getModel().setUsername(user_input.getText());
            if(startscreen.getGameoptions().getJoystick_text().getText().equals("ON"))
            {
                startscreen.getGameoptions().gameoptiondata.getJoystick().controllerButtonOn();
            }
        }
    }

    /**
     * Modelinitialisierung  sowie einige Buttonkonfigurierungen damit
     * Buttons nicht verzoegert Sound abgegeben bei hover
     */
    public void initModel()
    {
        startscreen = new ModelStartscreen();
        startscreen.set_buttons(start,highscore_list,game_options,quit_game); // Switchen von Buttons
        startscreen.getButtonsound_player().setOnEndOfMedia(new Runnable() // Nach Abspielung von buttonhover sound direkt stoppen ,nicht mit lambda ersetzen
        {
            public void run() { startscreen.getButtonsound_player().stop(); }
        });
        startscreen.getButtonsound_player_2().setOnEndOfMedia(new Runnable()
        {
            public void run() { startscreen.getButtonsound_player_2().stop(); }
        });
        startscreen.getButtonsound_player_3().setOnEndOfMedia(new Runnable()
        {
            public void run() { startscreen.getButtonsound_player_3().stop(); }
        });
        startscreen.getButtonsound_player_4().setOnEndOfMedia(new Runnable()
        {
            public void run() { startscreen.getButtonsound_player_4().stop(); }
        });
    }


    public Text getWrong_input_text() { return wrong_input_text; }
    public Button getStart() { return start; }
    public Button getQuit_game() { return quit_game; }
    public Button getGame_options() { return game_options; }
    public Button getHighscore_list() { return highscore_list; }
    public GridPane getUser_input_area() { return user_input_area; }
    public TextField getUser_input() { return user_input; }

    public void setUser_input(TextField user_input) { this.user_input = user_input; }
    public void setUser_input_area(GridPane user_input_area) { this.user_input_area = user_input_area; }
    public void setStartscreen(ModelStartscreen startscreen) { this.startscreen = startscreen; }

    public Startscreen()
    {
        initModel();
    }

}
