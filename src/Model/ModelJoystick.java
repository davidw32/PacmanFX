package Model;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import net.java.games.input.*;
import Controller.Gameoptions;
import Controller.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Patrick Pavlenko
 */

public class ModelJoystick {

    private Controller[] ca = ControllerEnvironment.getDefaultEnvironment().getControllers();
    private net.java.games.input.Controller main_joystick;
    private EventQueue eq;
    private Event ev = new Event();
    private Gameoptions gameoptions;
    private Player player;
    private List<Button> currentButtons = new ArrayList<>(); // Erste Element muss immer der erste Button im Grid sein!
    private List<Button> defaultButtons = new ArrayList<>();
    private List<Button> pauseButtons = new ArrayList<>();
    private List<Button> gameButtons = new ArrayList<>();
    private List<Button> optionsButtons = new ArrayList<>();
    private List<Button> highscoreButtons = new ArrayList<>();
    private Button activeButton;
    private Timeline joystick_input;
    private Media hover_StartscreenButtons =  new Media(new File("./src/Audio/startscreen_button.wav").toURI().toString());
    private MediaPlayer buttonsound_up = new MediaPlayer(hover_StartscreenButtons);
    private MediaPlayer buttonsound_down = new MediaPlayer(hover_StartscreenButtons);

    public Gameoptions getGameoptions() { return gameoptions; }
    public MediaPlayer getButtonsound_down() { return buttonsound_down; }
    public MediaPlayer getButtonsound_up() { return buttonsound_up; }
    public List<Button> getHighscoreButtons() { return highscoreButtons; }
    public List<Button> getGameButtons() { return gameButtons; }
    public List<Button> getOptionsButtons() { return optionsButtons; }
    public List<Button> getPauseButtons() { return pauseButtons; }
    public List<Button> getDefaultButtons() { return defaultButtons; }
    public Button getActiveButton() { return activeButton; }
    public Timeline getJoystick_input() { return joystick_input; }
    public List<Button> getCurrentButtons() { return currentButtons; }
    public Player getPlayer() { return player; }
    public Event getEv() { return ev; }
    public EventQueue getEq() { return eq; }
    public Controller[] getCa() { return ca; }
    public Controller getMain_joystick() { return main_joystick; }

    public void setHighscoreButtons(List<Button> highscoreButtons) { this.highscoreButtons = highscoreButtons; }
    public void setHighscore()// null um rekursive Methodenaufruf zu vermeiden
    {
        currentButtons.clear();
        for(Button bt : highscoreButtons)
        {
            currentButtons.add(bt);
        }
        setActiveButton(null);
    }
    public void setGame()
    {
        currentButtons.clear();
        for(Button bt : gameButtons)
        {
            currentButtons.add(bt);
        }
        setActiveButton(null);
    }
    public void setPause()
    {
        currentButtons.clear();
        for(Button bt : pauseButtons)
        {
            currentButtons.add(bt);
        }
        setActiveButton(null);
    }
    public void setOptions()
    {
        currentButtons.clear();
        for(Button bt : optionsButtons)
        {
            currentButtons.add(bt);
        }
        setActiveButton(null);
    }
    public void setDefault()
    {
        currentButtons.clear();
        for(Button bt : defaultButtons)
        {
            currentButtons.add(bt);
        }
        setActiveButton(null);
    }
    public void setPauseButtons(List<Button> pauseButtons) { this.pauseButtons = pauseButtons; }
    public void setOptionsButtons(List<Button> optionsButtons) { this.optionsButtons = optionsButtons; }
    public void setGameButtons(List<Button> gameButtons) { this.gameButtons = gameButtons; }
    public void setCurrentButtons(List<Button> currentButtons) { this.currentButtons = currentButtons; }
    public void setActiveButton(Button activeButton) { this.activeButton = activeButton; }
    public void setJoystick_input(Timeline joystick_input) { this.joystick_input = joystick_input; }
    public void setCurrentButtons(Button button1) // es werden verschiedene Anzahlen von Buttons gebraucht, weil verschiedene Controller
    {
        currentButtons.clear();
        currentButtons.add(button1);
    }

    public void setCurrentButtons(Button button1,Button button2,Button button3,Button button4)
    {
        currentButtons.add(button1);
        currentButtons.add(button2);
        currentButtons.add(button3);
        currentButtons.add(button4);
        defaultButtons.add(button1);
        defaultButtons.add(button2);
        defaultButtons.add(button3);
        defaultButtons.add(button4);
    }
    public void setPlayer(Player player) { this.player = player; }
    public void setGameoptions(Gameoptions gameoptions) { this.gameoptions = gameoptions; }
    public void setEq(EventQueue eq) { this.eq = eq; }
    public void setMain_joystick(Controller main_joystick) { this.main_joystick = main_joystick; }
}