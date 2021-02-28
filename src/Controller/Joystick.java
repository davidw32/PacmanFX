package Controller;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import net.java.games.input.*;
import Model.ModelJoystick;

/**
 * @Author: Patrick Pavlenko
 */

public class Joystick implements  Controller {

    // Klasse hat kein View, rein funktional,wird im Gameoptions freigeschaltet ( oder durch Systemstart durch aufgereifen serialisierung)
    public ModelJoystick joystick;

    /**
     * Componente wird ermittelt
     * der Identifier dder Componenten darf dabei nicht den wert RY oder RX haben
     * @return Ausgabe der Componente
     */
    public Component RequestComponent()  // Holt sich den Button der gedrück wurde
    {
        try{
            joystick.getMain_joystick().poll();
            joystick.getEq().getNextEvent(joystick.getEv());
            Component comp = joystick.getEv().getComponent();
            while(comp.getIdentifier().equals("ry") && comp.getIdentifier().equals("rx"))
            {
                joystick.getMain_joystick().poll();
                joystick.getEq().getNextEvent(joystick.getEv());
                comp = joystick.getEv().getComponent();
            }
            return comp;
        }
        catch(Exception e) { }
        Component comp1 = joystick.getEv().getComponent();
        return comp1;
    }

    /**
     * Richtung des Pacmans im Spielfeld wird zugewiesen ( Steuerung im Spielfeld)
     * Alle DPAD Buttons sowie Pausebutton moeglich
     */
    public void setDirection()
    {
        try
        {
            Component.Identifier identifier = RequestComponent().getIdentifier();
            double num_axis = RequestComponent().getPollData();

            if(num_axis == Component.POV.RIGHT && joystick.getPlayer().getModel().getNewDirection() != 1) // rechts
            {
                joystick.getPlayer().getModel().setOldDirection(joystick.getPlayer().getModel().getNewDirection());
                joystick.getPlayer().getModel().setNewDirection(1);

            }
            else if(num_axis == Component.POV.LEFT && joystick.getPlayer().getModel().getNewDirection()  != 2) // links
            {
                joystick.getPlayer().getModel().setOldDirection(joystick.getPlayer().getModel().getNewDirection());
                joystick.getPlayer().getModel().setNewDirection(2);
            }
            else if( num_axis == Component.POV.UP && joystick.getPlayer().getModel().getNewDirection()  != 3) // unten?
            {
                joystick.getPlayer().getModel().setOldDirection(joystick.getPlayer().getModel().getNewDirection());
                joystick.getPlayer().getModel().setNewDirection(3);
            }
            else if(num_axis == Component.POV.DOWN && joystick.getPlayer().getModel().getNewDirection()  != 4) // oben
            {
                joystick.getPlayer().getModel().setOldDirection(joystick.getPlayer().getModel().getNewDirection());
                joystick.getPlayer().getModel().setNewDirection(4);
            }
            if(identifier == Component.Identifier.Button._7 && joystick.getGameoptions().gameoptiondata.getCheckParentScreen() == 2) // bringt zum pausenscreen,falls im gamefield drinne
            {
                if(joystick.getGameoptions().gameoptiondata.getGameoptionsave().getMap().equals("HSHL")){
                    joystick.getGameoptions().gameoptiondata.getScene().getWindow().setHeight(Integer.parseInt(joystick.getGameoptions().gameoptiondata.getGameoptionsave().getResolution_y()));
                    joystick.getGameoptions().gameoptiondata.getScene().getWindow().setWidth(Integer.parseInt(joystick.getGameoptions().gameoptiondata.getGameoptionsave().getResolution_x()));
                }
                joystick.getGameoptions().gameoptiondata.getScene().setRoot(joystick.getGameoptions().gameoptiondata.getPausescreen());
                joystick.getPlayer().getModel().getKolchecker_gamefield().stop();
                joystick.getJoystick_input().play();
                joystick.setPause();
                return;
            }

        }catch(Exception e) {}
        return;

    }

    /**
     * Wechsel des aktiven Buttons mittels Controllers ausserhalb des Spielfeldes (Also alle Grids ausser Gamefield und HSHL)
     * @param f_b ermittelt ob nach oben ( -1) oder nach unten (1) gegangen wird
     */
    public void switchButton(int f_b) //forward_backward
    {
        int size = joystick.getCurrentButtons().size()-1;
        for(Button bt : joystick.getCurrentButtons())
        {
            if(joystick.getActiveButton() == null) // falls keiner ausgewählt ist (bzw man die grid betritt)
            {
                joystick.setActiveButton(joystick.getCurrentButtons().get(0));
                joystick.getCurrentButtons().get(0).setStyle("-fx-background-color:blue;");
                return;
            }

                int index = joystick.getCurrentButtons().indexOf(bt);
                int index_current = joystick.getCurrentButtons().indexOf(joystick.getActiveButton());

            if(f_b == 1 && index_current != joystick.getCurrentButtons().size()-1 ) // nach unten drücken
            {
                joystick.getActiveButton().setStyle("-fx-background-color:black;");
                joystick.getCurrentButtons().get(index_current+f_b).setStyle("-fx-background-color: blue;");
                joystick.setActiveButton(joystick.getCurrentButtons().get(index_current+f_b));
                return;
            }
               if(index == joystick.getCurrentButtons().size()-1 && f_b == 1)
                {
                    joystick.getActiveButton().setStyle("-fx-background-color:black;");
                    joystick.getCurrentButtons().get(0).setStyle("-fx-background-color: blue;");
                    joystick.setActiveButton(joystick.getCurrentButtons().get(0));
                    return;
                }
            if(f_b == -1 && index_current != 0) // nach oben drücken
            {
                joystick.getActiveButton().setStyle("-fx-background-color:black;");
                joystick.getCurrentButtons().get(index_current-1).setStyle("-fx-background-color: blue;");
                joystick.setActiveButton(joystick.getCurrentButtons().get(index_current-1));
                return;
            }
            if(f_b == -1 && index_current == 0) // von ganz oben hover --> nach oben drücken = untere objekt hover
            {
                joystick.getActiveButton().setStyle("-fx-background-color:black;");
                joystick.getCurrentButtons().get(joystick.getCurrentButtons().size()-1).setStyle("-fx-background-color: blue;");
                joystick.setActiveButton(joystick.getCurrentButtons().get(joystick.getCurrentButtons().size()-1));
                return;
            }

        }
    }

    /**
     * Bei betaetigung von A Button wird der aktive (der Button im hover) betaetigt
     * @param actiontype um welche Buttonnnummer es sich handelt
     */
    public void FireEvent(int actiontype)
    {
        if(joystick.getActiveButton() == null) { return; }
        if(actiontype == 5)
        {
                joystick.getActiveButton().fire();
                joystick.getEv().set(new Event());
                return;
        }
        if(actiontype == 6)
        {
            joystick.getEv().set(new Event());
            return;
        }
    }

    /**
     * Abfrage welcher Button beim Event erzeugt wurde
     * Bleibt gleich wenn keine neue Eingabe kommt
     * @return gibt die Nummer des Buttons zurueck
     */
    public int setDirectionButton()
    {
        try
        {
            Component.Identifier identifier = RequestComponent().getIdentifier();
            double num_axis = RequestComponent().getPollData();
            if(num_axis == Component.POV.RIGHT) { return 4; }// rechts
            if(num_axis == Component.POV.LEFT) { return 3; } // links
            if(num_axis == Component.POV.UP) { return 2; } // oben
            if(num_axis == Component.POV.DOWN){return 1; }  // unten
            if(identifier == Component.Identifier.Button._0){ return 5;  } // Button A
            if(identifier == Component.Identifier.Button._2){ return 6; } // Button X
            if(identifier == Component.Identifier.Button._7){ return 7; } // Start
        }
        catch(Exception e){}
        return 0;
    }

    /**
     * Modelinitialisierung
     * und Timeline fuer Controller zur Abfrage von Buttons etc
     */
    public void initModel()
    {
        joystick = new ModelJoystick();
        for(net.java.games.input.Controller con : joystick.getCa())
        {
            if(con.getType() == net.java.games.input.Controller.Type.GAMEPAD)
            {
                joystick.setMain_joystick(con);
                joystick.setEq(joystick.getMain_joystick().getEventQueue());

            }
        }
        Timeline joystick_pass = new Timeline
                (
                        new KeyFrame(Duration.millis(150), e ->
                        {
                            switch(setDirectionButton())
                            {
                                case 1: switchButton(1);
                                    joystick.getButtonsound_down().play();
                                    break;
                                case 2: switchButton(-1);
                                    joystick.getButtonsound_up().play();
                                    break;
                                case 5: FireEvent(5);
                                    break;
                                case 6: FireEvent(6);
                                    break;
                            }
                        })
                );
        joystick.setJoystick_input(joystick_pass);
        joystick.getButtonsound_up().setOnEndOfMedia(new Runnable() // Nach Abspielung von buttonhover sound direkt stoppen ,nicht mit lambda ersetzen
        {
            public void run() { joystick.getButtonsound_up().stop(); }
        });
        joystick.getButtonsound_down().setOnEndOfMedia(new Runnable() // Nach Abspielung von buttonhover sound direkt stoppen ,nicht mit lambda ersetzen
        {
            public void run() { joystick.getButtonsound_down().stop(); }
        });
    }

    public void controllerButtonOn()
    {
        joystick.getJoystick_input().setCycleCount(Animation.INDEFINITE);
        joystick.getJoystick_input().play();
    }

    public Joystick()
    {
        initModel();
    }


}
