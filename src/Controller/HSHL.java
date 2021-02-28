package Controller;

import Model.ModelCrossing;
import Model.ModelGamefield;
import Model.ModelHSHL;
import Model.ModelPlayerData;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;
import static javafx.scene.layout.Priority.SOMETIMES;

public class HSHL extends Gamefield implements Controller  {

    public ModelHSHL Mgamefield;
    @FXML GridPane hshl_matrix;
    @FXML protected ArrayList<ModelCrossing> hshlcross;
    @FXML Player playerController;

    /**
     * Die StartGame()-Methode startet die Timeline fuer den verzoegerten Start, welche nach Beendigung die Timeline des Spiels startet und somit das Spiel startet.
     */
    public void StartGame()
    {
        if(!outter_zone.getChildren().contains(playerController.getModel().getPacman())) {
            outter_zone.getChildren().add(playerController.getModel().getPacman());
        }
        playerController.getModel().setKolchecker_gamefield(Mgamefield.KolChecker); // passed Kolchecker, um bei Pausescreen Kolchecker an/aus zu machen
        pauseScreenController.pauseScreen.setKolchecker_gamefield(Mgamefield.KolChecker);
        Mgamefield.FreezeStart.setOnFinished(r -> Mgamefield.KolChecker.play());
        Mgamefield.KolChecker.setCycleCount(Animation.INDEFINITE);
        Mgamefield.FreezeStart.play();
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
        scene.getWindow().setHeight(Integer.parseInt(startscreenController.startscreen.getGameoptions().gameoptiondata.getGameoptionsave().getResolution_y())+40);
        scene.getWindow().setWidth(Integer.parseInt(startscreenController.startscreen.getGameoptions().gameoptiondata.getGameoptionsave().getResolution_x())+15);
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
    public void FinishedLoad(Scene main_scene, Player playerController)
    {
        this.scene = main_scene;
        this.playerController = playerController;
        playerController.setMovement(main_scene);// Steuerung-Ingame
        outter_zone.getChildren().add(playerController.getModel().getPacman());
        ghostsController.setCross(cross);
        ghostsController.getGhosts()[0].setPlayer(playerController);
        ghostsController.getGhosts()[1].setPlayer(playerController);
        ghostsController.getGhosts()[2].setPlayer(playerController);
        ghostsController.getGhosts()[3].setPlayer(playerController);
        playerController.getModel().setScore(Integer.valueOf(highscore.getText()));
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
            playerController.setToStart(cross.get(58));
            return playerController.getModel().getLife();
        }
        else{ // wenn Spieler Game Over ist
            life_img1.setVisible(false);
            return playerController.getModel().getLife(); // In Timeline Kolchecker is ne abfrage dazu für das Gameover
        }
    }

    /**
     * Die CreateMatrix()-Methode erstellt fuer die Spielfeld-GridPane Column- und RowConstraints und fuegt sie der GridPane hinzu.
     */
    public void CreateMatrix()
    {
        for (int i = 0; i <= 24; i++)
        {
            ColumnConstraints column = new ColumnConstraints();
            RowConstraints row = new RowConstraints();
            column.setPercentWidth(2);
            column.setHgrow(SOMETIMES);
            row.setPercentHeight(4);
            row.setVgrow(SOMETIMES);
            hshl_matrix.getColumnConstraints().add(column);
            hshl_matrix.getRowConstraints().add(row);
        }
        for (int j = 0; j <= 24; j++)
        {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(2);
            column.setHgrow(SOMETIMES);
            hshl_matrix.getColumnConstraints().add(column);
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

        setScorePoints(cross.get(0),cross.get(5),20,true); // horizontale Reihen
        setScorePoints(cross.get(6),cross.get(9),17,true);
        setScorePoints(cross.get(10),cross.get(11),2,true);
        setScorePoints(cross.get(12),cross.get(17),17,true);
        setScorePoints(cross.get(18),cross.get(22),14,true);
        setScorePoints(cross.get(25),cross.get(26),1,true);
        setScorePoints(cross.get(26),cross.get(25),1,true);
        setScorePoints(cross.get(35),cross.get(39),17,true);
        setScorePoints(cross.get(40),cross.get(42),6,true);
        setScorePoints(cross.get(43),cross.get(44),3,true);
        setScorePoints(cross.get(45),cross.get(46),2,true);
        setScorePoints(cross.get(47),cross.get(48),2,true);
        setScorePoints(cross.get(49),cross.get(52),17,true);
        setScorePoints(cross.get(53),cross.get(55),1,true);
        setScorePoints(cross.get(54),cross.get(56),1,true);
        setScorePoints(cross.get(55),cross.get(57),12,true);

        setScorePoints(cross.get(0),cross.get(12),3,false); // vertikale Reihen
        setScorePoints(cross.get(1),cross.get(50),15,false);
        setScorePoints(cross.get(14),cross.get(25),3,false);
        setScorePoints(cross.get(31),cross.get(37),4,false);
        setScorePoints(cross.get(2),cross.get(51),15,false);
        setScorePoints(cross.get(3),cross.get(16),3,false);
        setScorePoints(cross.get(4),cross.get(17),3,false);
        setScorePoints(cross.get(39),cross.get(52),3,false);
        setScorePoints(cross.get(5),cross.get(10),2,false);
        setScorePoints(cross.get(46),cross.get(53),1,false);
        setScorePoints(cross.get(6),cross.get(11),2,false);
        setScorePoints(cross.get(47),cross.get(54),1,false);
        setScorePoints(cross.get(7),cross.get(18),3,false);
        setScorePoints(cross.get(40),cross.get(55),3,false);
        setScorePoints(cross.get(19),cross.get(26),3,false);
        setScorePoints(cross.get(32),cross.get(41),4,false);
        setScorePoints(cross.get(8),cross.get(56),15,false);
        setScorePoints(cross.get(21),cross.get(43),9,false);
        setScorePoints(cross.get(9),cross.get(22),3,false);
        setScorePoints(cross.get(44),cross.get(57),3,false);


        deleteDoubleScorePoints();
        for(Circle c : scorePoints) c.toBack();
        scorePoints.get(0).setRadius(10); // Power-Score oben links
        scorePoints.get(104).setRadius(10); // Power-Score unten links
        scorePoints.get(136).setRadius(10); // Power-Score unten rechts
        scorePoints.get(38).setRadius(10);// Power-Score oben rechts

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
        if((playerController.getModel().getScore()-playerController.getModel().getScoreWhenNextLevel()) % 3200 >= 1900 && !cherry.isVisible() && !cherry_imgView.isVisible()){
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
        cross.get(27).setLeft(true); // linker Tunnel-Durchgang verbunden mit Rechts
        cross.get(27).setDistToLeft(0);
        cross.get(27).setCrossLeft(cross.get(30));
        cross.get(30).setRight(true); // rechter Tunnel-Durchgang verbunden mit Links
        cross.get(30).setDistToRight(0);
        cross.get(30).setCrossRight(cross.get(27));
    }

    /**
     * Die GameOver()-Methode, welche ausgefuehrt wird sobald der Spieler keine Leben mehr uebrig hat, stoppt das Spiel und macht die Gameover-Menue Knoepfe sichtbar.
     * Ebenfalls wird die SaveData()-Methode des Highscores aufgerufen, wodurch hier der Spieler gespeichert wird.
     */
    public void GameOver()
    {
        playerController.getModel().setScore(parseInt(highscore.getText()));
        startscreenController.startscreen.getHighscore().SaveData();
        this.Mgamefield.KolChecker.stop();
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
        this.cross = hshlcross;
        CreateMatrix();
        initModel();
        playerController.getModel().setPauseScreen(pauseScreenController);
        playerController.getModel().setScene(scene);
    }

    /**
     * Die initModel()-Methode initialisiert das HSHLdmodel und erstellt die fuer das Spiel wichtigen Timelines.
     */
    public void initModel()
    {
        Mgamefield  = new ModelHSHL(playerController,ghostsController,outter_zone,hshlcross,scorePoints);
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
                            ghostsController.ghost3, ghostsController.ghost4, cross.get(58),highscore);
                    if(collidedGhost == 5){
                        if(CheckLife() == 0)
                        {
                            GameOver();
                        }
                        ghostsController.setAllToStart(cross.get(60)); // setzt Geister auf Startposition zurueck
                    } else if(collidedGhost != -1 && collidedGhost != 5){
                        ghostsController.setThisToStart(cross.get(60), collidedGhost);
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
                    createScorePoints(); setCrossingEdges(cross); playerController.setToStart(cross.get(58)); ghostsController.setAllToStart(cross.get(60));
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
     * Die setScreens()-Methode bekommt den Controller des Startscreens und des Pausenscreens, und die Szene uebergeben und setzt diese in die Attribute des HSHL-Gamefields ein.
     */
    public void setScreens(Startscreen startscreenController, PauseScreen pauseScreenController, Scene scene)
    {
        this.startscreenController = startscreenController;
        this.pauseScreenController = pauseScreenController;
        this.scene = scene;
        Mgamefield.setScene(scene);
        playerController.getModel().setScene(scene);
        playerController.getModel().setPauseScreen(pauseScreenController);
    }
}
