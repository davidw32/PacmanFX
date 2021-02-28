package Controller;

import Model.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;
import static javafx.scene.layout.Priority.SOMETIMES;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

import javax.swing.*;
import Model.ModelGamefield;
import javafx.scene.control.Button;

/***
 * @Author: Patrick Pavlenko
 *
 */

public class Gamefield implements Controller{

    public Scene scene;
    protected PauseScreen pauseScreenController;
    protected Startscreen startscreenController;
    @FXML protected Player playerController;  // Player-Controller inkludierte Tag
    @FXML protected Ghost ghostsController;  // Ghost-Controller inkludierte Tag
    @FXML protected Circle cherry;
    @FXML protected ImageView cherry_imgView;
    @FXML private GridPane root; // Hauptgrid des Spiels
    @FXML AnchorPane outter_zone;

    @FXML protected ArrayList<ModelCrossing> cross;
    @FXML protected ArrayList<Circle> scorePoints = new ArrayList<Circle>();
    @FXML protected Label highscore;
    @FXML protected ImageView life_img1;
    @FXML protected ImageView life_img2;
    @FXML protected ImageView life_img3;
    @FXML protected Label gameover_text;
    @FXML protected Button gameover_button_restart;
    @FXML protected Button gameover_button_quit;
    @FXML protected GridPane gameovermatrix;

    public ModelGamefield Mgamefield;

    /**
     * Die Restart()-Methode setzt das Spielfeld auf den Startzustand zurueck und startet das Spiel neu.
     */
    @FXML private void Restart()
    {
        highscore.setText("0");
        gameover_text.setVisible(false);
        gameover_button_restart.setVisible(false);
        gameover_button_quit.setVisible(false);
        playerController.ResetPlayerStats(life_img1,life_img2,life_img3);
        createScorePoints();
        startscreenController.startscreen.getGameoptions().gameoptiondata.getJoystick().joystick.getJoystick_input().stop();
        StartGame();
    }

    /**
     * Die SwitchStartscreen()-Methode setzt das Spielfeld auf den Startzustand zurueck und wechselt zum Startmenue
     */
    @FXML public void SwitchStartscreen()
    {
        playerController.getModel().setScore(parseInt(highscore.getText()));
        startscreenController.startscreen.getHighscore().highscoredata.getPlayer_save().add(new ModelPlayerData(playerController.getModel().getUsername(),playerController.getModel().getScore()));
        startscreenController.startscreen.getHighscore().SaveData();
        startscreenController.startscreen.getHighscore().AddHighscorePlayers();
        highscore.setText("0");
        gameover_text.setVisible(false);
        gameover_button_restart.setVisible(false);
        gameover_button_quit.setVisible(false);
        playerController.ResetPlayerStats(life_img1,life_img2,life_img3);
        createScorePoints();
        Mgamefield.FreezeStart.stop();  // hierdurch wird bei schnell quit von spielfeld,bevor anfangssound fertig ist weiterer sound vermieden
        startscreenController.Startscreen();
        playerController.getModel().setOpenpausescreen(false);
        startscreenController.startscreen.getHighscore().highscoredata.setCheckParentScreen(1);
        startscreenController.startscreen.getGameoptions().gameoptiondata.getJoystick().joystick.setDefault();
    }

    /**
     * Die FinishedLoad()-Methode wird aufgerufen wenn das Spielfeld initialisiert ist und uebergibt dem Spieler die Szene auf der das onKeyPressed-Event fuer die Bewegung ausgeloest wird.
     * Zusaetzlich uebergibt sie den Geistern das Kreuzungspunktarray und ihren jeweiligen Spieler.
     */
    public void FinishedLoad(Scene main_scene)
    {
        playerController.setMovement(main_scene);     // Steuerung-Ingame
        ghostsController.setCross(cross);
        ghostsController.getGhosts()[0].setPlayer(playerController);
        ghostsController.getGhosts()[1].setPlayer(playerController);
        ghostsController.getGhosts()[2].setPlayer(playerController);
        ghostsController.getGhosts()[3].setPlayer(playerController);
        playerController.getModel().setScore(Integer.valueOf(highscore.getText()));
    }

    /**
     * Die StartGame()-Methode startet die Timeline fuer den verzoegerten Start, welche nach Beendigung die Timeline des Spiels startet und somit das Spiel startet.
     */
    public void StartGame()
    {
        if(!outter_zone.getChildren().contains(playerController.getModel().getPacman())) {
            outter_zone.getChildren().add(playerController.getModel().getPacman());
        }
        playerController.getModel().setKolchecker_gamefield(Mgamefield.KolChecker); // uebergibt Kolchecker, um bei Pausescreen Kolchecker an/aus zu machen
        pauseScreenController.pauseScreen.setKolchecker_gamefield(Mgamefield.KolChecker);
        Mgamefield.FreezeStart.setOnFinished(r -> Mgamefield.KolChecker.play());
        Mgamefield.KolChecker.setCycleCount(Animation.INDEFINITE);
        Mgamefield.FreezeStart.play();
    }

    /**
     * Die CheckLife()-Methode ueberprueft die verbleibenden Leben des Spielers und passt dementsprechend die Lebensanzeige an.
     * Die uebrigen Leben werden anschließend zurueckgegeben.
     */
    public int CheckLife()
    {
        if(playerController.getModel().getLife()>0)
        {
            playerController.getModel().getDiesSound().stop();
            playerController.getModel().getDiesSound().play();
            playerController.getModel().setLife(playerController.getModel().getLife()-1);
            switch(playerController.getModel().getLife())
            {
                case 2: life_img3.setVisible(false); break;
                case 1: life_img2.setVisible(false); break;
                default: break;
            }
            playerController.setToStart(cross.get(66));
            return playerController.getModel().getLife();
        }
        else{ // wenn Spieler Game Over ist
            life_img1.setVisible(false);
            return playerController.getModel().getLife(); // In Timeline Kolchecker ist eine Abfrage dazu für das Gameover
        }
    }

    /**
     * Die CreateMatrix()-Methode erstellt fuer die Spielfeld-GridPane Column- und RowConstraints und fuegt sie der GridPane hinzu.
     */
    public void CreateMatrix()
    {
        for (int i = 0; i <= 31; i++)
        {
         ColumnConstraints column = new ColumnConstraints();
         RowConstraints row = new RowConstraints();
         column.setPercentWidth(3.125);
         column.setHgrow(SOMETIMES);
         row.setPercentHeight(2.5);
         row.setVgrow(SOMETIMES);
         root.getColumnConstraints().add(column);
         root.getRowConstraints().add(row);
        }
        for (int j = 0; j <= 7; j++)
        {
             RowConstraints row = new RowConstraints();
             row.setPercentHeight(2.5);
             row.setVgrow(SOMETIMES);
             root.getRowConstraints().add(row);
        }
    }

    /**
     * Die setScorePoints()-Methode erstellt zwischen zwei Kreuzungspunkten in gleichen Abstaenden eine durch number vorgebene Anzahl an von Pacman essbaren Punktzahl-Kreisen.
     * Die Varibale xDirection gibt an, ob eine Reihe (xDirection == true) oder eine Spalte (xDirection == false) befuellt wird.
     */
    public void setScorePoints(Circle circle1, Circle circle2, int number, boolean xDirection) // number = Anzahl an Score Punkten zwischen zwei Crossings
    {
        if(xDirection)
        {
            double crossDistance = circle2.getBoundsInParent().getCenterX() - circle1.getBoundsInParent().getCenterX(); // Die Entfernung zwischen zwei Crossings auf der X-Achse
            double scoreDistance = crossDistance/number; // Die Entfernung zwischen zwei Score Punkten
            for(int i = 0; i*scoreDistance<crossDistance+3;i++)
            {
                scorePoints.add(new Circle(3,Color.TAN));
                scorePoints.get(scorePoints.size()-1).setCenterY(circle1.getBoundsInParent().getCenterY());
                scorePoints.get(scorePoints.size()-1).setCenterX(circle1.getBoundsInParent().getCenterX() + (i*scoreDistance)); // platziert die Score Punkte mit bestimmter Entfernung
                outter_zone.getChildren().add(scorePoints.get(scorePoints.size()-1));
            }
        }
        else
        {
            double crossDistance = circle2.getBoundsInParent().getCenterY() - circle1.getBoundsInParent().getCenterY(); // Die Entfernung zwischen zwei Crossings auf der Y-Achse
            double scoreDistance = crossDistance/number;
            for(int i = 1; i*scoreDistance<crossDistance;i++)
            {
                scorePoints.add(new Circle(3,Color.TAN));
                scorePoints.get(scorePoints.size()-1).setCenterX(circle1.getBoundsInParent().getCenterX());
                scorePoints.get(scorePoints.size()-1).setCenterY(circle1.getBoundsInParent().getCenterY() + (i*scoreDistance));
                outter_zone.getChildren().add(scorePoints.get(scorePoints.size()-1));
            }
        }
    }

    /**
     * Die deleteDoubleScorePoints()-Methode entfernt uebereinanderliegende und somit ueberfluessige Punktzahl-Punkte.
     */
    public void deleteDoubleScorePoints()
    {
        for(int i = 0; i<scorePoints.size();i++){
            for(int j = 0;j<scorePoints.size();j++){
                if(i != j && scorePoints.get(i).getBoundsInParent().intersects(scorePoints.get(j).getBoundsInParent())){
                    outter_zone.getChildren().remove(scorePoints.get(i));
                    scorePoints.remove(i);
                }
            }
        }
    }

    /**
     * Die createScorePoints()-Methode erstellt, nachdem sie alle uebrig gebliebenen Punktzahl-Punkte aus der ArrayList entfernt hat,
     * abhaengig von den Kreuzungspunkten die Punktzahl-Punkte und vergroeßert den Radius der vier verstaerkenden Punkte.
     */
    public void createScorePoints()
    {
        for(Circle point : scorePoints){
            outter_zone.getChildren().remove(point);
        }
        scorePoints.clear();

        setScorePoints(cross.get(0),cross.get(2),11,true); // horizontale Reihen
        setScorePoints(cross.get(3),cross.get(5),11,true);
        setScorePoints(cross.get(6),cross.get(13),25,true);
        setScorePoints(cross.get(14),cross.get(15),5,true);
        setScorePoints(cross.get(16),cross.get(17),3,true);
        setScorePoints(cross.get(18),cross.get(19),3,true);
        setScorePoints(cross.get(20),cross.get(21),5,true);
        setScorePoints(cross.get(34),cross.get(37),11,true);
        setScorePoints(cross.get(38),cross.get(41),11,true);
        setScorePoints(cross.get(42),cross.get(43),2,true);
        setScorePoints(cross.get(44),cross.get(49),15,true);
        setScorePoints(cross.get(50),cross.get(51),2,true);
        setScorePoints(cross.get(52),cross.get(54),5,true);
        setScorePoints(cross.get(55),cross.get(56),3,true);
        setScorePoints(cross.get(57),cross.get(58),3,true);
        setScorePoints(cross.get(59),cross.get(61),5,true);
        setScorePoints(cross.get(62),cross.get(65),25,true);

        setScorePoints(cross.get(0),cross.get(14),7,false); // vertikale Reihen
        setScorePoints(cross.get(34),cross.get(42),3,false);
        setScorePoints(cross.get(52),cross.get(62),3,false);
        setScorePoints(cross.get(43),cross.get(53),3,false);
        setScorePoints(cross.get(1),cross.get(54),25,false);
        setScorePoints(cross.get(8),cross.get(16),3,false);
        setScorePoints(cross.get(45),cross.get(55),3,false);
        setScorePoints(cross.get(2),cross.get(9),4,false);
        setScorePoints(cross.get(37),cross.get(46),3,false);
        setScorePoints(cross.get(56),cross.get(63),3,false);
        setScorePoints(cross.get(3),cross.get(10),4,false);
        setScorePoints(cross.get(38),cross.get(47),3,false);
        setScorePoints(cross.get(57),cross.get(64),3,false);
        setScorePoints(cross.get(11),cross.get(19),3,false);
        setScorePoints(cross.get(48),cross.get(58),3,false);
        setScorePoints(cross.get(4),cross.get(59),25,false);
        setScorePoints(cross.get(50),cross.get(60),3,false);
        setScorePoints(cross.get(5),cross.get(21),7,false);
        setScorePoints(cross.get(41),cross.get(51),3,false);
        setScorePoints(cross.get(61),cross.get(65),3,false);

        deleteDoubleScorePoints();
        for(Circle c : scorePoints) c.toBack();
        scorePoints.get(153).setRadius(10); // Power-Score oben links
        scorePoints.get(86).setRadius(10); // Power-Score unten links
        scorePoints.get(105).setRadius(10); // Power-Score unten rechts
        scorePoints.get(237).setRadius(10); // Power-Score oben rechts

        outter_zone.getChildren().remove(scorePoints.get(95)); // linker Punkt an Pacmans Startposition entfernen
        scorePoints.remove(95);
        outter_zone.getChildren().remove(scorePoints.get(95)); // rechter Punkt an Pacmans Startposition entfernen
        scorePoints.remove(95);

        setCherryPosition();
    }

    /**
     * Die setCherryPosition()-Methode setzt am Anfang des Spiels die Kirsche auf einen zufaelligen Punktzahl-Punkt und macht diese unsichtbar.
     */
    public void setCherryPosition(){
        cherry.setVisible(false);
        cherry_imgView.setVisible(false);
        int randomScorePoint = (int) Math.round(Math.random()*(scorePoints.size()-1));
        cherry.setCenterX(scorePoints.get(randomScorePoint).getBoundsInParent().getCenterX());
        cherry.setCenterY(scorePoints.get(randomScorePoint).getBoundsInParent().getCenterY());
    }

    /**
     * Die checkCherry()-Methode ueberprueft ob die Kirsche abhaengig von einer bestimmten Punktzahl auf dem Spielfeld sichtbar gemacht werden kann und ob sie dann vom Spieler eingesammtelt wird.
     */
    public void checkCherry(){
        if((playerController.getModel().getScore()-playerController.getModel().getScoreWhenNextLevel()) % 3200 >= 2000 && !cherry.isVisible() && !cherry_imgView.isVisible()){
            cherry.setVisible(true);
            for(Circle score : scorePoints){
                if(cherry.getBoundsInParent().intersects(score.getBoundsInParent())){
                    score.setVisible(false);
                }
            }
        }
        if(cherry.isVisible() && playerController.getModel().getPacman().getBoundsInParent().intersects(cherry.getBoundsInParent())){
            playerController.UpdateScore(highscore, 200);
            cherry.setVisible(false);
            cherry_imgView.setVisible(true);
        }
    }

    /**
     * Die setCrossingEdges()-Methode ermittelt von den Kreuzungspunkten ihre jeweiligen Nachbarpunkte und die dazwischenliegenden Entfernungen.
     * Zusaetzlich setzt sie noch die Nachbarn der in den Tunneln liegenden Kreuzungspunkten auf den jweils anderen Tunnelpunkt.
     */
    public void setCrossingEdges(ArrayList<ModelCrossing> cross){
        for(ModelCrossing crossing : cross){
            crossing.findRightCross(cross);
            crossing.findLeftCross(cross);
            crossing.findUpCross(cross);
            crossing.findDownCross(cross);
        }
        cross.get(26).setLeft(true); // linker Tunnel-Durchgang verbunden mit Rechts
        cross.get(26).setDistToLeft(0);
        cross.get(26).setCrossLeft(cross.get(31));
        cross.get(31).setRight(true); // rechter Tunnel-Durchgang verbunden mit Links
        cross.get(31).setDistToRight(0);
        cross.get(31).setCrossRight(cross.get(26));
    }

    /**
     * Die GameOver()-Methode, welche ausgefuehrt wird sobald der Spieler keine Leben mehr uebrig hat, stoppt das Spiel und macht die Gameover-Menue Knoepfe sichtbar.
     * Ebenfalls wird die SaveData()-Methode des Highscores aufgerufen, wodurch hier der Spieler gespeichert wird.
     */
    public void GameOver()
    {
        playerController.getModel().setScore(parseInt(highscore.getText()));
        startscreenController.startscreen.getHighscore().SaveData();
        Mgamefield.KolChecker.stop();
        playerController.UpdateScore(highscore, 0);
        playerController.ResetPlayerStats(life_img1,life_img2,life_img3);
        gameover_text.setVisible(true);
        gameover_button_restart.setVisible(true);
        gameover_button_quit.setVisible(true);
        startscreenController.startscreen.getGameoptions().gameoptiondata.getJoystick().joystick.getJoystick_input().play();

    }


    @FXML
    public void initialize()
    {
        CreateMatrix();
        initModel();
        playerController.getModel().setPauseScreen(pauseScreenController);
        playerController.getModel().setScene(scene);
    }

    /**
     * Die initModel()-Methode initialisiert das Gamefieldmodel und erstellt die fuer das Spiel wichtigen Timelines.
     */
    public void initModel()
    {
        Mgamefield  = new ModelGamefield(playerController,ghostsController,outter_zone,cross,scorePoints);
        Mgamefield.KolChecker =  new Timeline( // Kollisionschecker (momentan nur geister + spieler)
                new KeyFrame(Duration.millis(8), e -> {
                    if(scorePoints.size() == 0) // Hier Abfrage für nächste Level
                    {
                        Mgamefield.KolChecker.stop();
                        playerController.getModel().setScoreWhenNextLevel(playerController.getModel().getScore());
                        StartGame();
                    }
                    playerController.pacmanMove();
                    int collidedGhost = playerController.collide(ghostsController.ghost1, ghostsController.ghost2,
                            ghostsController.ghost3, ghostsController.ghost4, cross.get(66),highscore);
                    if(collidedGhost == 5){
                        if(CheckLife() == 0)
                        {
                            GameOver();
                        }
                        ghostsController.setAllToStart(cross.get(68)); // setzt Geister auf Startposition zurueck
                    } else if(collidedGhost != -1 && collidedGhost != 5){
                        ghostsController.setThisToStart(cross.get(68), collidedGhost);
                    }
                    playerController.checkPowerMode();
                    playerController.onCrossing(cross,startscreenController.startscreen.getGameoptions().gameoptiondata);
                    ghostsController.ghostMove(startscreenController.startscreen.getGameoptions().gameoptiondata);

                    int scoreNumber = playerController.onScore(scorePoints,highscore); // Nummer des Score Punktes, welcher von Pacman beruehrt wird
                    if(scoreNumber != -1){
                        outter_zone.getChildren().remove(scorePoints.get(scoreNumber)); // gegessener Score Punkt wird entfernt
                        scorePoints.remove(scoreNumber);
                    }
                    checkCherry();
                    if(startscreenController.startscreen.getGameoptions().joystick_text.getText().equals("ON"))
                    {
                        startscreenController.startscreen.getGameoptions().gameoptiondata.getJoystick().setDirection();
                    }
                })
        );
         Mgamefield.FreezeStart = new Timeline(
                 new KeyFrame(Duration.millis(10),e-> { // Soundausgabe vom Start + Punktesetzung von Spielfeld ( Spieler kann sich nicht bewegen bis Sound abläuft)
                     Mgamefield.getMediaPlayer().play();
                     createScorePoints(); setCrossingEdges(cross); playerController.setToStart(cross.get(66)); ghostsController.setAllToStart(cross.get(68));
                 }),
                 new KeyFrame(Duration.millis(4500),e-> { // nach 4,5 sek moven sich objekte = Spielstart
                     Mgamefield.KolChecker.stop();
                     playerController.getModel().setOpenpausescreen(true);
                     Mgamefield.getMediaPlayer().stop();
                 })
         );
         Mgamefield.Freeze = new Timeline(  // zukünftige Timeline für Pausenscreen
                 new KeyFrame(Duration.seconds(8),e-> {
                     Mgamefield.KolChecker.stop();
                 }));
                setButtonSound();

                Mgamefield.Freeze.setOnFinished((e)->
                        {
                            pauseScreenController.pauseScreen.getHighscore().highscoredata.setCheckParentScreen(2);
                            startscreenController.startscreen.getGameoptions().gameoptiondata.setCheckParentScreen(2);
                        });
    }

    /**
     * Die setScreens()-Methode bekommt den Controller des Startscreens und des Pausenscreens, und die Szene uebergeben und setzt diese in die Attribute des Gamefields ein.
     */
    public void setScreens(Startscreen startscreenController,PauseScreen pauseScreenController,Scene scene)
    {
        this.startscreenController = startscreenController;
        this.pauseScreenController = pauseScreenController;
        this.scene = scene;
        Mgamefield.setScene(scene);
        playerController.getModel().setScene(scene);
        playerController.getModel().setPauseScreen(pauseScreenController);
    }

    /**
     * Die setButtonSound()-Methode setzt die Sounds fuer die Gameover-Menue Knoepfe.
     */
    public void setButtonSound()
    {
        gameover_button_quit.setOnMouseEntered(e -> startscreenController.startscreen.getButtonsound_player().play());
        gameover_button_restart.setOnMouseEntered(e -> startscreenController.startscreen.getButtonsound_player().play());
    }

    /**
     * Die getButtons()-Methode gibt die Gameover-Knoepfe in einer ArrayList zurueck.
     */
    public ArrayList<Button> getButtons()
    {
        ArrayList<Button> array = new ArrayList<>();
        array.add(gameover_button_restart);
        array.add(gameover_button_quit);
        return  array;
    }

    public GridPane getGameovermatrix() { return gameovermatrix; }
    public Player getPlayerController() { return playerController; }
}
