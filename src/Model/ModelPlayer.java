package Model;

import Controller.PauseScreen;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import net.java.games.input.Controller;

import java.io.File;
import Controller.Gamefield;

/***
 * @Author: Patrick Pavlenko
 * @version: 0.1
 *
 */

public class ModelPlayer extends ModelPlayerData implements Model {

    private Scene scene;
    private int life;
    private Circle pacman;
    private Circle center = new Circle(); //Mittelpunkt von Pacman
    private int move = 0;
    private boolean x = false;
    private int newDirection = 0;
    private int oldDirection = 0;
    private boolean powerMode = false; // wird auf wahr gesetzt sobald Pacman einen großen Punkt isst und die Geister essen kann
    private int scoreWhenPowerMode;
    private int scoreWhenNextLevel; // wird auf die Punktzahl gesetzt die der Spieler hat wenn er alle Punkte eingesammelt hat
    private ModelCrossing currentCrossing;
    private Media diesSoundPath =  new Media(new File("./src/Audio/pacman_dead.mp3").toURI().toString());
    private Media pacmanMoveSoundPath =  new Media(new File("./src/Audio/pacman_chomp.wav").toURI().toString());
    private MediaPlayer diesSound = new MediaPlayer(diesSoundPath);
    private MediaPlayer pacmanMoveSound = new MediaPlayer(pacmanMoveSoundPath);
    private PauseScreen pauseScreen;
    private Timeline Kolchecker_gamefield;
    private boolean godmode = false;
    private boolean openpausescreen = false;

    public void setOpenpausescreen(boolean openpausescreen) { this.openpausescreen = openpausescreen; }
    public void setKolchecker_gamefield(Timeline kolchecker_gamefield) { Kolchecker_gamefield = kolchecker_gamefield; }
    public void setScene(Scene scene){this.scene = scene;}
    public void setPauseScreen(PauseScreen pauseScreen){this.pauseScreen = pauseScreen;}
    public void setLife(int life) {
        this.life = life;
    }
    public void setPacman(Circle pacman) {
        this.pacman = pacman;
    }

    public boolean isOpenpausescreen() { return openpausescreen; }
    public boolean isGodmode() { return godmode; }
    public Timeline getKolchecker_gamefield() { return Kolchecker_gamefield; }
    public Scene getScene(){return scene;}
    public double getX_P(){ return pacman.getCenterX();}
    public double getY_P(){ return pacman.getCenterY();}
    public Circle getPacman(){return pacman;}
    public Circle getCenter(){return center;}
    public void setMove(int move) { this.move = move; }
    public int getMove() { return move; }
    public void setX(boolean x) { this.x = x; }
    public boolean getX() { return this.x; }
    public PauseScreen getPauseScreen() { return pauseScreen; }
    public int getLife() { return life; }
    public boolean getPowerMode(){ return powerMode; }
    public void setPowerMode(boolean powerMode){ this.powerMode = powerMode; }
    public int getScoreWhenPowerMode(){ return scoreWhenPowerMode; }
    public void setScoreWhenPowerMode(int scoreWhenPowerMode){ this.scoreWhenPowerMode=scoreWhenPowerMode; }
    public int getScoreWhenNextLevel(){ return scoreWhenNextLevel; }
    public void setScoreWhenNextLevel(int scoreWhenNextLevel){ this.scoreWhenNextLevel=scoreWhenNextLevel; }
    public ModelCrossing getCurrentCrossing(){ return currentCrossing;}
    public ModelCrossing getNextCrossing(){ // gibt das Crossing aus auf das Pacman zusteuert
        if(this.x && this.move==1){ return this.getCurrentCrossing().getCrossRight(); }
        else if(this.x && move==-1){ return this.getCurrentCrossing().getCrossLeft(); }
        else if(!this.x && move==-1){ return this.getCurrentCrossing().getCrossUp(); }
        else if(!this.x && move==1){ return this.getCurrentCrossing().getCrossDown(); }
        else return this.getCurrentCrossing();
    }

    public void setNewDirection(int newDirection) {
        this.newDirection = newDirection;
    }
    public int getNewDirection() {
        return newDirection;
    }
    public void setOldDirection(int oldDirection) { this.oldDirection = oldDirection; }
    public int getOldDirection() { return oldDirection; }

    public void setCurrentCrossing(ModelCrossing currentCrossing){ this.currentCrossing=currentCrossing; }
    public MediaPlayer getDiesSound(){return diesSound;}
    public MediaPlayer getPacmanMoveSound(){return pacmanMoveSound;}


    public ModelPlayer(int life, int score, Circle pacman)
    {
        this.life = life;
        this.score = score;
        this.pacman = pacman;
        center.setRadius(0);
       // center.setCenterX(getX_P()); <-- entfernt damit testmethode geht,behalten?
      //  center.setCenterY((getY_P()));
    }
}
