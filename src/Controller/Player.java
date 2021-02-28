package Controller;
import Model.ModelCrossing;
import Model.ModelGameoptions;
import Model.ModelPlayer;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.animation.*;
import javafx.util.Duration;
import net.java.games.input.*;

import java.util.ArrayList;
import net.java.games.input.*;

/***
 * @Author: Patrick Pavlenko,David Waelsch
 *
 */
public class Player implements Controller {


    private ModelPlayer player;

    @FXML Image pacman_img;
    @FXML Circle pacman; // Model bitte benutzen :)

    @FXML
    public void initialize() // Szene hier noch nicht intialisiert!
    {
        pacman.toFront();
        initModel();
    }

    /**
     * Die initModel()-Mezhode initialisiert das Playermodel.
     */
    public void initModel()
    {
        player = new ModelPlayer(3, 0, pacman);
        player.getPacmanMoveSound().setOnEndOfMedia(new Runnable()
        {
            public void run()
            {
                player.getPacmanMoveSound().stop(); // waka waka sound soll stoppen nach ende des abspielens
            }
        });

    }

    /**
     * Die pacmanMove()-Methode bewegt den Pacman durch Pixelspruenge in die vom Spieler gewaehlte zulaessige Richtung und spielt dabei den Bewegungssound von Pacman ab.
     */
    public void pacmanMove(){
        if (player.getX())
        {
            player.getPacman().setCenterX(player.getX_P() + player.getMove());
            player.getCenter().setCenterX(player.getX_P());
        }
        else
        {
            player.getPacman().setCenterY(player.getY_P() + player.getMove());
            player.getCenter().setCenterY(player.getY_P());
        }
        if(player.getPacmanMoveSound().getStatus() == MediaPlayer.Status.STOPPED || player.getPacmanMoveSound().getStatus() == MediaPlayer.Status.READY) // neustart von waka waka sound
        {
            player.getPacmanMoveSound().play();
        }
    }

    /**
     * Die directionsPacman()-Methode ermittelt, ob die vom Spieler gewaehlte Richtung zulaessig ist und gibt diese in dem Fall zurueck.
     * Wenn dies nicht der Fall ist wird die vorherige Richtung auf selbiges ueberprueft und wenn diese
     * ebenfalls nicht gegangen werden kann wird 0 zurueckgegeben.
     */
    public int directionsPacman(int direction, ModelCrossing cross, boolean repeat) {
        if (direction == 1 && cross.getRight()) {
            return 1; //rechts
        } else if (direction  == 2 && cross.getLeft()) {
            return 2; //links
        } else if (direction  == 3 && cross.getUp()) {
            return 3; //oben
        } else if (direction  == 4 && cross.getDown()) {
            return 4; //unten
        } else {
            if(repeat){
                return directionsPacman(player.getOldDirection(), cross, false);
            }
            else{
                return 0;
            }
        }
    }

    /**
     * Die changeDirection()-Methode aendert die Bewegungsrichtung in die von der directionsPacman() ermittelten Richtung.
     * Dabei wird das Aussehen von Pacman in die entsprechende Richtung rotiert.
     */
    public void changeDirection(ModelCrossing cross){
        if(directionsPacman(player.getNewDirection() ,cross, true) == 1){
            player.setX(true);
            player.setMove(1);
            player.getPacman().setRotate(0);
            player.setOldDirection(1);
        }
        else if(directionsPacman(player.getNewDirection() ,cross, true) == 2){
            player.setX(true);
            player.setMove(-1);
            player.getPacman().setRotate(180);
            player.setOldDirection(2);
        }
        else if(directionsPacman(player.getNewDirection() ,cross,true) == 3){
            player.setX(false);
            player.setMove(-1);
            player.getPacman().setRotate(270);
            player.setOldDirection(3);
        }
        else if(directionsPacman(player.getNewDirection() ,cross,true) == 4){
            player.setX(false);
            player.setMove(1);
            player.getPacman().setRotate(90);
            player.setOldDirection(4);
        }
        else{
            player.setX(true);
            player.setMove(0);
        }

    }

    /**
     * Die onCrossing()-Methode ueberprueft, ob sich Pacman auf einem Kreuzungspunkte des jeweiligen Spielfelds befindet.
     * Sollte er sich in einem der Teleportationstunnel befinden wird Pacman auf die andere Seite des Spielfelds gesetzt.
     * Sonst wird die changeDirection()-Methode aufgerufen und die aktuelle Kreuzung des Spielers aktualisiert.
     */
    public void onCrossing(ArrayList<ModelCrossing> cross, ModelGameoptions gameoptiondata){
        for(int i=0; i<cross.size(); i++) {

            if (player.getCenter().getBoundsInParent().contains(cross.get(i).getBoundsInParent())) {
                if(i==31 && gameoptiondata.getGameoptionsave().getMap().equals("CLASSIC")){ player.getPacman().setCenterX(cross.get(26).getBoundsInParent().getCenterX()+1); } // wenn in rechtem Durchgang dann wird Pacman in den linken Durchgang gesetzt
                else if(i==26 && gameoptiondata.getGameoptionsave().getMap().equals("CLASSIC")){ player.getPacman().setCenterX(cross.get(31).getBoundsInParent().getCenterX()-1); }// wenn in linkem Durchgang dann wird Pacman in den rechten Durchgang gesetzt
                else if(i==27 && gameoptiondata.getGameoptionsave().getMap().equals("HSHL")){ player.getPacman().setCenterX(cross.get(30).getBoundsInParent().getCenterX()-1); }
                else if(i==30 && gameoptiondata.getGameoptionsave().getMap().equals("HSHL")){ player.getPacman().setCenterX(cross.get(27).getBoundsInParent().getCenterX()+1); }
                else{ changeDirection(cross.get(i)); }
                player.setCurrentCrossing(cross.get(i));
                break;
            }
        }
    }

    /**
     * Die onScore()-Methode ueberprueft, ob sich Pacman auf einem der essbaren Score-Punkte befindet. Wenn dies der Fall ist wird der Score-Punkt gegessen
     * und die Punktzahl des Spielers aktualisiert.
     * Wenn dieser Score-Punkt einer der vier groesseren Score-Punkte ist wird zusaetzlich der Power-Modus des Pacmans aktiviert.
     */
    public int onScore(ArrayList<Circle> scorePoints, Label text)
    {
        for(int i = 0; i<scorePoints.size(); i++){
            if(player.getPacman().getBoundsInParent().intersects(scorePoints.get(i).getBoundsInParent())){
                if(scorePoints.get(i).getRadius()>3){ // bei großen Score Punkten gibt es 50 Punkte
                    UpdateScore(text,50);
                    player.setPowerMode(true);
                    player.setScoreWhenPowerMode(player.getScore());
                    return i;
                }
                else{
                    UpdateScore(text,10); // bei kleinen Score Punkten gibt es 10 Punkte
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Die setMovement()-Methode uebergibt der Szene das setOnKeyPressed-Event mit welchem der Spieler die Bewegungen des Pacmans steuern kann.
     * Durch das druecken der Escape-Taste kann zusaetzlich das Pausenmenue aufgerufen werden.
     */
    public void setMovement(Scene scene) // aufgerufen von Gamefield
    {
        scene.setOnKeyPressed(e ->{
            switch(e.getCode())
            {
                case RIGHT:
                    player.setOldDirection(player.getNewDirection());
                    player.setNewDirection(1);
                    break;
                case LEFT:
                    player.setOldDirection(player.getNewDirection());
                    player.setNewDirection(2);
                    break;
                case UP:
                    player.setOldDirection(player.getNewDirection());
                    player.setNewDirection(3);
                    break;
                case DOWN:
                    player.setOldDirection(player.getNewDirection());
                    player.setNewDirection(4);
                    break;
                case ESCAPE:
                    if(player.isOpenpausescreen())
                    {
                        if(player.getPauseScreen().pauseScreen.getHighscore().highscoredata.getGameoptions().gameoptiondata.getGameoptionsave().getMap().equals("HSHL")){
                            scene.getWindow().setHeight(Integer.parseInt(player.getPauseScreen().pauseScreen.getHighscore().highscoredata.getGameoptions().gameoptiondata.getGameoptionsave().getResolution_y())+40);
                            scene.getWindow().setWidth(Integer.parseInt(player.getPauseScreen().pauseScreen.getHighscore().highscoredata.getGameoptions().gameoptiondata.getGameoptionsave().getResolution_x())+15);
                        }
                        player.getScene().setRoot(player.getPauseScreen().pauseScreen.getMatrix_pause());
                        player.getKolchecker_gamefield().stop();
                        player.setOpenpausescreen(false);
                    }
                    break;
        }

        });
    }

    /**
     * Die setToStart()-Methode setzt Pacman auf seine Startposition zurueck.
     */
    public void setToStart(ModelCrossing cross){
        player.getPacman().setCenterX(cross.getBoundsInParent().getCenterX());
        player.getPacman().setCenterY(cross.getBoundsInParent().getCenterY());
        player.getCenter().setCenterX(player.getPacman().getCenterX());
        player.getCenter().setCenterY(player.getPacman().getCenterY());
        player.setNewDirection(0);
        player.setOldDirection(0);
        player.setMove(0);
    }

    /**
     * Die checkPowerMode()-Mwthode ueberprueft, ob sich Pacman im Power-Modus befindet und setzt diesen zurueck sobald er im Power-Modus ueber 300 Punkte erziehlt hat.
     */
    public void checkPowerMode(){
        if(player.getPowerMode() && player.getScore() - player.getScoreWhenPowerMode() >= 300){
            player.setPowerMode(false);
        }
    }

    /**
     * Die collide()-Methode ueberprueft, ob der Spieler mit einem der Geister kollidiert.
     * Wenn dies der Fall ist und der Spieler niht im Power-Modus ist, wird 5 zurueckgeben, der Spieler also zurueckgesetzt.
     * Wenn sich der Spieler im Power-Modus befindet wird der entsprechende Geist auf seine Startposition gesetzt und die Punktzahl des Spieler wird aktualisiert.
     */
    public int collide(Circle ghost1,Circle ghost2,Circle ghost3,Circle ghost4, ModelCrossing cross, Label text)
    {
        if((player.getPacman().getBoundsInParent().intersects(ghost1.getBoundsInParent()) ||
           player.getPacman().getBoundsInParent().intersects(ghost2.getBoundsInParent()) ||
           player.getPacman().getBoundsInParent().intersects(ghost3.getBoundsInParent()) ||
           player.getPacman().getBoundsInParent().intersects(ghost4.getBoundsInParent())) && !player.getPowerMode() && !player.isGodmode() )
        {
            return 5; // Spieler wird zurueckgestzt
        }
        else if(player.getPowerMode() && player.getPacman().getBoundsInParent().intersects(ghost1.getBoundsInParent())){
            UpdateScore(text,50);
            return 0;
        } else if(player.getPowerMode() && player.getPacman().getBoundsInParent().intersects(ghost2.getBoundsInParent())){
            UpdateScore(text,50);
            return 1;
        } else if(player.getPowerMode() && player.getPacman().getBoundsInParent().intersects(ghost3.getBoundsInParent())){
            UpdateScore(text,50);
            return 2;
        } else if(player.getPowerMode() && player.getPacman().getBoundsInParent().intersects(ghost4.getBoundsInParent())){
            UpdateScore(text,50);
            return 3;
        } else { return -1; }
    }

    /**
     * Die getModel()-Methode gibt das Spielermodel zurueck.
     */
    public ModelPlayer getModel()
    {
        return player;
    }

    /**
     * Die setModel()-Methode aktualisiert das Spielermodel.
     */
    public void setModel(ModelPlayer model)
    {
        this.player = model;
    }

    /**
     * Die UpdateScore()-Methode aktualisiert die Punktzahl des Spielers um den angegebenen Weert.
     */
    public void UpdateScore(Label text,int add) // text bzw Highscoreanzeige oben rechts , add = inkrement
    {
        player.setScore(add+player.getScore());
        text.setText(Integer.toString(player.getScore()));
    }

    /**
     * Die ResetPlayerStats()-Methode setzt alle Werte des Spielers zurueck.
     */
    public void ResetPlayerStats(ImageView life1, ImageView life2, ImageView life3)
    {
        player.setScore(0);
        player.setLife(3);
        life1.setVisible(true);
        life2.setVisible(true);
        life3.setVisible(true);
    }
}
