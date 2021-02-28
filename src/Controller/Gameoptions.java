package Controller;
import Model.ModelGameoptionData;
import Model.ModelGameoptions;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.io.*;
import java.util.ArrayList;


/**
 * @Author: Patrick Pavlenko
 */

public class Gameoptions implements Controller {

    public ModelGameoptions gameoptiondata;

    @FXML GridPane options;
    @FXML private Button mute;
    @FXML private Button resolution;
    @FXML private Button goback;
    @FXML private Button audio_text;
    @FXML protected Button joystick_text;
    @FXML private ObservableList<String> resolutions;
    @FXML private Button box_resolution;
    @FXML private Button map_choose;

    /**
     * Button des Controllers ändert sein Text + Controller wird deaktiviert oder aktiviert
     * Ebenfalls wird fuer die Abspeicherung die an oder ausschaltung aufgenommen in das Model zum abspeichern
     */
    @FXML public void ChangeControll()
    {
        if(joystick_text.getText().equals("ON"))
        {
            joystick_text.setText("OFF");
            gameoptiondata.getGameoptionsave().setController("OFF");
            gameoptiondata.getJoystick().joystick.getJoystick_input().stop();
        }
        else
        {
            joystick_text.setText("ON");
            gameoptiondata.getGameoptionsave().setController("ON");
            gameoptiondata.getJoystick().controllerButtonOn();
        }
    }

    /**
     * Button zum wechseln zwischen der CLASSIC und HSHL Karte
     * Ebenfalls wird fuer die Abspeicherung die Karte in das Model zum abspeichern aufgenommen
     */
    @FXML public void ChangeMap()
    {
        if(map_choose.getText().equals("CLASSIC"))
        {
            map_choose.setText("HSHL");
            gameoptiondata.getStartscreen_c().startscreen.setMap_name("HSHL");
            gameoptiondata.getGameoptionsave().setMap("HSHL");
        }
        else
        {
            map_choose.setText("CLASSIC");
            gameoptiondata.getStartscreen_c().startscreen.setMap_name("CLASSIC");
            gameoptiondata.getGameoptionsave().setMap("CLASSIC");
        }
    }

    /**
     * Button zum wechseln der Aufloseung
     * waehlbar zwischen 3 verschiedenen Aufloseungen
     * Dabei aendern sich von allen Grids des Spieles ( Matrix_container) die Groessen
     * Genau so wie fuer das Fenster des Spieles
     */
    @FXML public void ChangeResolution()
    {
        if(box_resolution.getText().equals("640x800"))
        {
            gameoptiondata.getScene().getWindow().setWidth(735);
            gameoptiondata.getScene().getWindow().setHeight(940);
            gameoptiondata.getScene().getWindow().setY(0);
            for(GridPane matrix : gameoptiondata.getMatrix_container()) { matrix.setPrefSize(735,940); }
            gameoptiondata.getGameoptionsave().setResolution_x("720");
            gameoptiondata.getGameoptionsave().setResolution_y("900");
            box_resolution.setText("720x900");
            setGrid720900();
        }
        else if(box_resolution.getText().equals("720x900"))
        {
            gameoptiondata.getScene().getWindow().setWidth(815);
            gameoptiondata.getScene().getWindow().setHeight(1040);
            gameoptiondata.getScene().getWindow().setY(0);
            for(GridPane matrix : gameoptiondata.getMatrix_container()) { matrix.setPrefSize(815,1040); }
            gameoptiondata.getGameoptionsave().setResolution_x("800");
            gameoptiondata.getGameoptionsave().setResolution_y("1000");
            box_resolution.setText("800x1000");
            setGrid8001000();
        }
        else if(box_resolution.getText().equals("800x1000"))
        {
            gameoptiondata.getScene().getWindow().setWidth(655);
            gameoptiondata.getScene().getWindow().setHeight(840);
            gameoptiondata.getScene().getWindow().setY(0);
            for(GridPane matrix : gameoptiondata.getMatrix_container()) { matrix.setPrefSize(655,840); }
            gameoptiondata.getGameoptionsave().setResolution_x("640");
            gameoptiondata.getGameoptionsave().setResolution_y("800");
            box_resolution.setText("640x800");
            setGrid640800();
        }
    }

    /**
     * Wechseln der Hintergrundbildder bei anderer Aufloseung sowie
     * kleine Feinschliffe fuer den Usereingabe am Anfang des Programmes
     */
    public void setGrid640800() // ändert die Hintergrundauflösungen zur ihrer jeweiligen Window Auflösung
    {
        gameoptiondata.getStartscreen().setStyle("-fx-background-image: url(/Images/startscreen.png);");
        gameoptiondata.getPausescreen().setStyle("-fx-background-image: url(/Images/Pausescren640x800.png);");
        options.setStyle("-fx-background-image: url(/Images/pac_optionen_640_800.png);");
        gameoptiondata.getMatrix_container()[1].setStyle("-fx-background-image: url(/Images/Leaderboard640x800.png);");
        gameoptiondata.getMatrix_container()[2].setStyle("-fx-background-image: url(/Images/Pac_Man_final.png);");
        gameoptiondata.getStartscreen_c().startscreen.getGamefieldController().gameover_button_quit.setTranslateX(30);
        gameoptiondata.getStartscreen_c().startscreen.getGamefieldController().gameover_button_restart.setTranslateY(30);
        gameoptiondata.getStartscreen_c().startscreen.getGamefieldController().gameover_button_restart.setTranslateX(10);
        gameoptiondata.getStartscreen_c().startscreen.getGamefieldController().gameover_text.setTranslateX(6);
    }

    public void setGrid720900()
    {
        gameoptiondata.getStartscreen().setStyle("-fx-background-image: url(/Images/startscreen_720_900.png);");
        gameoptiondata.getPausescreen().setStyle("-fx-background-image: url(/Images/Pausescreen720x900.png);");
        options.setStyle("-fx-background-image: url(/Images/pac_optionen_720_900.png);");
        gameoptiondata.getMatrix_container()[1].setStyle("-fx-background-image: url(/Images/Leaderboard-720x900.png);");
        gameoptiondata.getMatrix_container()[2].setStyle("-fx-background-image: url(/Images/gamefield720x900.png);");
        gameoptiondata.getStartscreen_c().startscreen.getGamefieldController().gameover_button_quit.setTranslateX(50);
        gameoptiondata.getStartscreen_c().startscreen.getGamefieldController().gameover_button_restart.setTranslateX(40);
        gameoptiondata.getStartscreen_c().startscreen.getGamefieldController().gameover_button_restart.setTranslateY(30);
        gameoptiondata.getStartscreen_c().startscreen.getGamefieldController().gameover_text.setTranslateX(50);
    }

    public void setGrid8001000()
    {
        gameoptiondata.getStartscreen().setStyle("-fx-background-image: url(/Images/startscreen_800_1000.png);");
        gameoptiondata.getPausescreen().setStyle("-fx-background-image: url(/Images/Pausescreen800x1000.png);");
        gameoptiondata.getMatrix_container()[1].setStyle("-fx-background-image: url(/Images/Leaderboard800x1000.png);");
        options.setStyle("-fx-background-image: url(/Images/pac_optionen_800_1000.png);");
        gameoptiondata.getMatrix_container()[2].setStyle("-fx-background-image: url(/Images/gamefield800x1000.png);");
        gameoptiondata.getStartscreen_c().startscreen.getGamefieldController().gameover_button_quit.setTranslateX(50);
        gameoptiondata.getStartscreen_c().startscreen.getGamefieldController().gameover_button_restart.setTranslateX(90);
        gameoptiondata.getStartscreen_c().startscreen.getGamefieldController().gameover_button_restart.setTranslateY(30);
        gameoptiondata.getStartscreen_c().startscreen.getGamefieldController().gameover_text.setTranslateX(100);
    }

    /**
     * Button um zurueck in Startscreen ( bei Checkparent = 1 ) oder in das Pausenscreen ( Bei CheckParent = 2 ) zu kommen
     * Dabei werden die auszuwaehlende Buttons des Joystick gewechselt
     */
    @FXML public void back() // Checkt, ob von Startscreen oder von Pausescreen kommt
    {
        if(gameoptiondata.getCheckParentScreen() == 1)
        {
            gameoptiondata.getScene().setRoot(gameoptiondata.getStartscreen());
            goback.setStyle("-fx-background-color: black;");
            gameoptiondata.getJoystick().joystick.setDefault();
        }
        else if(gameoptiondata.getCheckParentScreen() == 2 )
        {
            goback.setStyle("-fx-background-color: black;");
            gameoptiondata.getJoystick().joystick.setPause();
            gameoptiondata.getScene().setRoot(gameoptiondata.getPausescreen());
            turnOnButton();
        }
        SaveData();
    }


    /**
     * An und ausschalten aller Audio medien innerhalb des Programmes
     */
    public void audioOff()
    {
        gameoptiondata.getPauseScreen().pauseScreen.getButtonsound_player().setMute(true);
        gameoptiondata.getPauseScreen().pauseScreen.getButtonsound_player_2().setMute(true);
        gameoptiondata.getPauseScreen().pauseScreen.getButtonsound_player_3().setMute(true);
        gameoptiondata.getPauseScreen().pauseScreen.getButtonsound_player_4().setMute(true);
        gameoptiondata.getStartscreen_c().startscreen.getButtonsound_player().setMute(true);
        gameoptiondata.getStartscreen_c().startscreen.getButtonsound_player_2().setMute(true);
        gameoptiondata.getStartscreen_c().startscreen.getButtonsound_player_3().setMute(true);
        gameoptiondata.getStartscreen_c().startscreen.getButtonsound_player_4().setMute(true);
        gameoptiondata.getGamefield().Mgamefield.getMediaPlayer().setMute(true);
        gameoptiondata.getHighscore().highscoredata.getButtonsound_player().setMute(true);
        gameoptiondata.getButtonsound_player().setMute(true);
        gameoptiondata.getButtonsound_player2().setMute(true);
        gameoptiondata.getButtonsound_player3().setMute(true);
        gameoptiondata.getButtonsound_player4().setMute(true);
        gameoptiondata.getButtonsound_player().setMute(true);
        audio_text.setText("OFF");
        gameoptiondata.getJoystick().joystick.getButtonsound_down().setMute(true);
        gameoptiondata.getJoystick().joystick.getButtonsound_up().setMute(true);
        gameoptiondata.getGamefield().playerController.getModel().getPacmanMoveSound().setMute(true);
        gameoptiondata.getGamefield().playerController.getModel().getDiesSound().setMute(true);
        gameoptiondata.getGameoptionsave().setAudio("OFF");
        gameoptiondata.getHshl().getPlayerController().getModel().getDiesSound().setMute(true);
        gameoptiondata.getHshl().getPlayerController().getModel().getPacmanMoveSound().setMute(true);
        gameoptiondata.getHshl().Mgamefield.getMediaPlayer().setMute(true);
    }

    public void audioOn()
    {
        gameoptiondata.getPauseScreen().pauseScreen.getButtonsound_player().setMute(false);
        gameoptiondata.getPauseScreen().pauseScreen.getButtonsound_player_2().setMute(false);
        gameoptiondata.getPauseScreen().pauseScreen.getButtonsound_player_3().setMute(false);
        gameoptiondata.getPauseScreen().pauseScreen.getButtonsound_player_4().setMute(false);
        gameoptiondata.getStartscreen_c().startscreen.getButtonsound_player().setMute(false);
        gameoptiondata.getStartscreen_c().startscreen.getButtonsound_player_2().setMute(false);
        gameoptiondata.getStartscreen_c().startscreen.getButtonsound_player_3().setMute(false);
        gameoptiondata.getStartscreen_c().startscreen.getButtonsound_player_4().setMute(false);
        gameoptiondata.getGamefield().Mgamefield.getMediaPlayer().setMute(false);
        gameoptiondata.getHighscore().highscoredata.getButtonsound_player().setMute(false);
        gameoptiondata.getButtonsound_player().setMute(false);
        gameoptiondata.getJoystick().joystick.getButtonsound_down().setMute(false);
        gameoptiondata.getJoystick().joystick.getButtonsound_up().setMute(false);
        gameoptiondata.getButtonsound_player().setMute(false);
        gameoptiondata.getButtonsound_player2().setMute(false);
        gameoptiondata.getButtonsound_player3().setMute(false);
        gameoptiondata.getButtonsound_player4().setMute(false);
        gameoptiondata.getGamefield().playerController.getModel().getPacmanMoveSound().setMute(false);
        gameoptiondata.getGamefield().playerController.getModel().getDiesSound().setMute(false);
        gameoptiondata.getHshl().getPlayerController().getModel().getDiesSound().setMute(false);
        gameoptiondata.getHshl().getPlayerController().getModel().getPacmanMoveSound().setMute(false);
        gameoptiondata.getHshl().Mgamefield.getMediaPlayer().setMute(false);
        audio_text.setText("ON");
        gameoptiondata.getGameoptionsave().setAudio("ON");
    }

    /**
     * obigen Methoden werden benutzt bei Buttonklick des Audio buttons
     * zum an/ausschalten der Audiomedien
     */
    @FXML public void mute()
    {
        if (audio_text.getText().equals("ON")) { audioOff(); }
        else { audioOn(); }
    }

    /**
     * Bei hovern der Buttons wird ein Sound abgespielt
     * hier wird das ganze intialisiert
     * Allen wird eine seperate Audio gegeben, damit es sich fluessiger anfuehlt bei hovern von mehreren
     * Buttons in kurzer Zeit
     */
    public void setTest() // Audio-hover für Startscreen Buttons
    {
        goback.setOnMouseEntered(e->  gameoptiondata.getButtonsound_player().play() );
        audio_text.setOnMouseEntered(e->  gameoptiondata.getButtonsound_player2().play() );
        joystick_text.setOnMouseEntered(e->  gameoptiondata.getButtonsound_player3().play() );
        box_resolution.setOnMouseEntered(e->  gameoptiondata.getButtonsound_player4().play() );
        map_choose.setOnMouseEntered(e->  gameoptiondata.getButtonsound_player().play() );
    }

    /**
     * Joystick wird initiailisiert und einzelne Objekte zugewiesen
     * @param gameoptions der Gameoptionscontroller
     * @param player der playercontroller im gamefielddd
     */
    public void setJoystick(Gameoptions gameoptions,Player player)
    {
        Joystick joystick = new Joystick();
        gameoptiondata.setJoystick(joystick);
        gameoptiondata.getJoystick().joystick.setGameoptions(gameoptions);
        gameoptiondata.getJoystick().joystick.setPlayer(player);
    }


    /**
     * Abgespeicherte Objekte fur Optionen wird aufgerufen
     * Bei einer nicht vorhandenen Datei wird zuerst die Abspeicherfunktion (Savedata) verwendet
     * um auf etwas zuzugreifen zu koennen
     */
    public void getSave()
    {
        try {
            FileInputStream fileIn = new FileInputStream("src/options.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            gameoptiondata.setGameoptionsave((ModelGameoptionData) in.readObject());
            in.close();
            fileIn.close();
        }
        catch(FileNotFoundException e)
        {
            SaveData();
            getSave();
        }
        catch (IOException i) {
            i.printStackTrace();
            return;
        }
        catch (ClassNotFoundException c) {
            c.printStackTrace();
            return;
        }
    }

    /**
     * Abspeicherung des Werte der Buttons um bei neustart die Buttons im gleichen Zustand zu haben
     */
    public void SaveData()
    {
        try {
            FileOutputStream fileOut = new FileOutputStream("src/options.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(gameoptiondata.getGameoptionsave());
            out.close();
            fileOut.close();
            out.flush();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * hier werden die abspeicherte Werte in das Spiel integriert bei Start des Programmes
     * Die Buttons bekommen also ihre abgespeicherten Werte
     * Benutzerabfrage wird hier seperat eingestellt von der groesse her um Skalierung zu optimieren
     */
    public void setSavedOptionsView() // gepspeicherte Optionen werden eingesetzt in der View
    {
        if(gameoptiondata.getGameoptionsave().getAudio() == null) // Default Werte,wenn kein Speicher vorhanden
        {
            gameoptiondata.getGameoptionsave().setAudio("ON");
        }
        if(gameoptiondata.getGameoptionsave().getController() == null)
        {
            gameoptiondata.getGameoptionsave().setController("OFF");
        }
        if(gameoptiondata.getGameoptionsave().getMap() == null)
        {
            gameoptiondata.getGameoptionsave().setMap("CLASSIC");
        }
        String x = gameoptiondata.getGameoptionsave().getResolution_x();
        String y = gameoptiondata.getGameoptionsave().getResolution_y();
        if(x != null)
        {
            for(GridPane matrix : gameoptiondata.getMatrix_container()) // einzelne Grids werden gespeicherte Resolution zugwiesen
            {
                matrix.setPrefSize(Integer.parseInt(x),Integer.parseInt(y));
            } // hier unten wird der username abfrage grid eine Auflösung seperat gegeben ( weil es nicht mit setpref funktoniert)
            if(Integer.parseInt(x) <= 655)
            {
                box_resolution.setText(x+"x"+y);
                setGrid640800();

            }
            if(Integer.parseInt(x)>655 && Integer.parseInt(x) <=740)
            {
                gameoptiondata.getStartscreen_c().getUser_input_area().setPrefSize(Integer.parseInt(x)-30,Integer.parseInt(y));
                gameoptiondata.getStartscreen_c().getUser_input_area().setTranslateY(300);

                box_resolution.setText(x+"x"+y);
                setGrid720900();
            }
            if(Integer.parseInt(x)>=800)
            {
                gameoptiondata.getStartscreen_c().getUser_input_area().setPrefSize(Integer.parseInt(x),Integer.parseInt(y)+100);
                gameoptiondata.getStartscreen_c().getUser_input_area().setTranslateY(300);
                box_resolution.setText(x+"x"+y);
                setGrid8001000();
            }
            gameoptiondata.getUser_input_matrix().setMinHeight(Integer.parseInt(x)+300); // 300 um untere Lücke zu füllen
            gameoptiondata.getUser_input_matrix().setMinWidth(Integer.parseInt(y));

        }
        if(x == null)
        {
            gameoptiondata.getGameoptionsave().setResolution_x("640");
            gameoptiondata.getGameoptionsave().setResolution_y("800");
            gameoptiondata.getStartscreen_c().getUser_input_area().setPrefSize(640,810);
            gameoptiondata.getStartscreen_c().getUser_input_area().setTranslateY(280);
            gameoptiondata.getStartscreen_c().getUser_input_area().setTranslateX(0);
        }
            joystick_text.setText( gameoptiondata.getGameoptionsave().getController()); // Texte an Gameoptions verwiesen
            map_choose.setText(gameoptiondata.getGameoptionsave().getMap());
            audio_text.setText(gameoptiondata.getGameoptionsave().getAudio());
            if(gameoptiondata.getGameoptionsave().getAudio().equals("OFF")){audioOff();}

    }

    /**
     * Model von Gameoptions wird hier initialisiert
     * Und Buttonstop bei ende einer Audioabspielung um die Audio wiederholen zu koennen
     */
    public void initModel()
    {
        gameoptiondata = new ModelGameoptions(goback);
        gameoptiondata.set_buttons(goback,resolution,mute);
        gameoptiondata.getButtonsound_player().setOnEndOfMedia(new Runnable() // Nach Abspielung von buttonhover sound direkt stoppen ,nicht mit lambda ersetzen
        {
            public void run() { gameoptiondata.getButtonsound_player().stop(); }
        });
        gameoptiondata.getButtonsound_player2().setOnEndOfMedia(new Runnable() // Nach Abspielung von buttonhover sound direkt stoppen ,nicht mit lambda ersetzen
        {
            public void run() { gameoptiondata.getButtonsound_player2().stop(); }
        });
        gameoptiondata.getButtonsound_player3().setOnEndOfMedia(new Runnable() // Nach Abspielung von buttonhover sound direkt stoppen ,nicht mit lambda ersetzen
        {
            public void run() { gameoptiondata.getButtonsound_player3().stop(); }
        });
        gameoptiondata.getButtonsound_player4().setOnEndOfMedia(new Runnable() // Nach Abspielung von buttonhover sound direkt stoppen ,nicht mit lambda ersetzen
        {
            public void run() { gameoptiondata.getButtonsound_player4().stop(); }
        });
        getSave();
    }

    /**
     *  Für Pausescreen, damit einige Buttons nicht verwendet werden können (Aufloesungen und Levelwahl)
     */
    public void turnOffButton()
    {
        map_choose.setDisable(true);
        box_resolution.setDisable(true);
    }

    public void turnOnButton()
    {
        map_choose.setDisable(false);
        box_resolution.setDisable(false);
    }

    public void setGameoptiondata(ModelGameoptions gameoptiondata) { this.gameoptiondata = gameoptiondata; }

    public ArrayList<Button> getButton()
    {
        ArrayList<Button> array = new ArrayList<>();
        array.add(audio_text);
        array.add(box_resolution);
        array.add(joystick_text);
        array.add(map_choose);
        array.add(goback);
        return array;
    }

    public Button getJoystick_text() { return joystick_text; }
    public Button getMap_choose() { return map_choose; }
    public Button getBox_resolution() { return box_resolution; }

    public Gameoptions()
    {
        initModel();
    }
}