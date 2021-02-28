package Controller;
import Model.ModelHighscore;
import Model.ModelPlayer;
import Model.ModelPlayerData;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.*;
import java.util.*;

import static javafx.scene.paint.Color.WHITE;

/**
 * @Author: Patrick Pavlenko
 */

public class Highscore implements Controller {

    public ModelHighscore highscoredata;

    @FXML private GridPane Highscore;
    @FXML private Button backbt;

    /**
     * es wird zurueck zum Startscreen ( Checkparent = 1) oder zum Pausescreen (Checkparent = 2) weitergeleitet
     */
    @FXML public void back() // Checkt, ob von Gamefield oder von Startscreen kommt (integer wird Startscreen bei onclick funktion geändert,ansonsten bleibt 2)
    {
        if(highscoredata.getCheckParentScreen() == 1)
        {
            backbt.setStyle("-fx-background-color: black;");
            highscoredata.getScene().setRoot(highscoredata.getStartscreen());
            highscoredata.getGameoptions().gameoptiondata.getJoystick().joystick.setDefault();
            return;
        }
        else if(highscoredata.getCheckParentScreen() == 2 )
        {
            backbt.setStyle("-fx-background-color: black;");
            highscoredata.getScene().setRoot(highscoredata.getPausescreen());
            highscoredata.getGameoptions().gameoptiondata.getJoystick().joystick.setPause();
        }
    }


    /**
     * Abgespeicherte Objekte fur Highscore wird aufgerufen
     * Bei einer nicht vorhandenen Datei wird zuerst die Abspeicherfunktion (Savedata) verwendet
     * um auf etwas zuzugreifen zu koennen
     */
    public void getSave()
    {
        try {
            FileInputStream fileIn = new FileInputStream("src/ranking.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            highscoredata.setPlayer_save((ArrayList<ModelPlayerData>) in.readObject());
            in.close();
            fileIn.close();
        } catch(FileNotFoundException e)
        {
            SaveData();
            getSave();
        }
        catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            return;
        }
    }
    public void createHighscoreTexts(){
        for(int iterator = 0; iterator < 10; iterator++){
            highscoredata.getPoints()[iterator] = new Text();
            highscoredata.getNames()[iterator] = new Text();
            Highscore.add(highscoredata.getPoints()[iterator],2,iterator+1);
            Highscore.add(highscoredata.getNames()[iterator],1,iterator+1);
            GridPane.setHalignment(highscoredata.getPoints()[iterator], HPos.CENTER);
            GridPane.setHalignment(highscoredata.getNames()[iterator], HPos.CENTER);
        }
    }

    /**
     * Alle abgespeicherten Spieler werden sortiert mittels mergesort und ihre Werte werden in einzelne
     * Array fuer spaeter verlegt
     */
    public void AddHighscorePlayers()
    {
        if(!Highscore.getChildren().contains(highscoredata.getPoints()[0])){
            createHighscoreTexts();
        }
        highscoredata.setPlayer_save(mergeSort(highscoredata.getPlayer_save())); // sortiert die Highscoreliste
        while(highscoredata.getPlayer_save().size() > 10){ highscoredata.getPlayer_save().remove(10); }
        for(int iterator = 0; iterator < highscoredata.getPlayer_save().size(); iterator++) {
            highscoredata.setNames(highscoredata.getPlayer_save().get(iterator).getUsername(), iterator);
            highscoredata.setPoints(Integer.toString(highscoredata.getPlayer_save().get(iterator).getScore()), iterator);
            highscoredata.getNames()[iterator].setFill(WHITE);
            highscoredata.getPoints()[iterator].setFill(WHITE);
            highscoredata.getNames()[iterator].setFont(Font.font ("Press Start 2P Regular", 20));
            highscoredata.getPoints()[iterator].setFont(Font.font ("Press Start 2P Regular", 20));
        }
    }

    /**
     * Abspeicherungsfunktion der ganzen Highscore Spieler
     */
    public void SaveData()
    {
        try {
            FileOutputStream fileOut = new FileOutputStream("src/ranking.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(highscoredata.getPlayer_save());
            out.close();
            fileOut.close();
            out.flush();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Mergesort zum sortieren der Spieler im Highscore Bestenliste
     * @param ar Liste der ganzen Spieler
     */
    public static ArrayList<ModelPlayerData> mergeSort(ArrayList<ModelPlayerData> ar){
        if(ar.size()<=1) {
            return ar;
        }
        else{
            ArrayList<ModelPlayerData> leftAr = new ArrayList<>();
            ArrayList<ModelPlayerData> rightAr = new ArrayList<>();
            for(int i = 0; i<ar.size(); i++) {
                if(i < ar.size()/2) {
                    leftAr.add(ar.get(i));
                }
                else {
                    rightAr.add(ar.get(i));
                }
            }
            leftAr = mergeSort(leftAr);
            rightAr = mergeSort(rightAr);
            return merge(leftAr, rightAr);
        }
    }
    public static ArrayList<ModelPlayerData> merge(ArrayList<ModelPlayerData> leftAr, ArrayList<ModelPlayerData> rightAr) {
        ArrayList<ModelPlayerData> newArray = new ArrayList<>();
        while(!leftAr.isEmpty() && !rightAr.isEmpty()) {
            if(leftAr.get(0).getScore() >= rightAr.get(0).getScore()) {
                newArray.add(leftAr.get(0));
                leftAr.remove(0);
            }
            else {
                newArray.add(rightAr.get(0));
                rightAr.remove(0);
            }
        }
        while(!leftAr.isEmpty()) {
            newArray.add(leftAr.get(0));
            leftAr.remove(0);
        }
        while(!rightAr.isEmpty()) {
            newArray.add(rightAr.get(0));
            rightAr.remove(0);
        }
        return newArray;
    }

    /**
     * Modelinitialisierung
     */
    public void initModel()
    {
        highscoredata = new ModelHighscore(backbt);
        highscoredata.getButtonsound_player().setOnEndOfMedia(new Runnable() // Nach Abspielung von buttonhover sound direkt stoppen ,nicht mit lambda ersetzen
        {
            public void run() { highscoredata.getButtonsound_player().stop(); }
        });
    }



    public Button getBackbt() { return backbt; }
    public ArrayList<Button> getButtons()
    {
        ArrayList<Button> array = new ArrayList<>();
        array.add(backbt);
        return array;
    }

    // Audio-hover für Startscreen Buttons
    public void setTest() { backbt.setOnMouseEntered(e->  highscoredata.getButtonsound_player().play() ); }
    public void setHighscore(GridPane highscore) {
        this.Highscore = highscore;
    }

    public Highscore()
    {
        initModel();
        getSave();
    }
}
