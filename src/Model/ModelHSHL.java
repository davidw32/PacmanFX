package Model;

import Controller.Ghost;
import Controller.Player;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class ModelHSHL extends ModelGamefield implements Model {

    public ModelHSHL(Player playerController, Ghost ghostsController, AnchorPane outter_zone, ArrayList<ModelCrossing> cross, ArrayList<Circle> scorePoints)
    {
        super(playerController,ghostsController,outter_zone,cross,scorePoints);
    }

}
