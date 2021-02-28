package Model;

import Controller.Player;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.PriorityQueue;

/***
 * @Author: Patrick Pavlenko
 * @version: 0.1
 *
 */
public class ModelGhost implements Model {

    private Circle ghostCenter = new Circle();
    private Circle ghostbody;
    private Player player;
    private ImagePattern ghostRight_img;
    private ImagePattern ghostUp_img;
    private ImagePattern ghostDown_img;
    private ImagePattern ghostFrightened_img;
    private int pointsToStart;
    private int pointsWhenKilled = 0; // wird gesetzt sobald der Spieler oder der Geist zurueckgesetzt wird, um den Einsatz der Geister zu verzoegern
    private int newDirection = 0;
    private int move = 0;
    private boolean x = true;
    private double X_G; // X-Position
    private double Y_G; // Y-Position


    public void setPlayer(Player player){
        this.player=player;
    }
    public Player getPlayer(){
        return this.player;
    }
    public Circle getGhostCenter(){ return ghostCenter;}
    public void setGhostCenterX(){
        this.ghostCenter.setCenterX(X_G);
    }
    public void setGhostCenterY(){
        this.ghostCenter.setCenterY(Y_G);
    }

    public ImagePattern getGhostRight_img() { return ghostRight_img; }
    public ImagePattern getGhostUp_img() { return ghostUp_img; }
    public ImagePattern getGhostDown_img() { return ghostDown_img; }
    public ImagePattern getGhostFrightened_img() { return ghostFrightened_img; }
    public int getPointsToStart(){ return pointsToStart; }
    public int getPointDifference(int playerPoints){ return playerPoints - pointsWhenKilled; }
    public void setPointsWhenKilled(int pointsWhenKilled){ this.pointsWhenKilled=pointsWhenKilled; }

    public void setAxis()
    {
        X_G = ghostbody.getBoundsInParent().getCenterX();
        ghostCenter.setCenterX(X_G);
        Y_G = ghostbody.getBoundsInParent().getCenterY();
        ghostCenter.setCenterY(Y_G);
    }

    public void setGhostbody(Circle ghostbody) { this.ghostbody = ghostbody; }
    public Circle getGhostbody() { return ghostbody; }

    public void setNewDirection(int newDirection) {
        this.newDirection = newDirection;
    }
    public int getNewDirection() { return newDirection; }
    public void setMove(int move) { this.move = move; }
    public int getMove() { return move; }
    public void setX(boolean x) { this.x = x; }
    public boolean getX(){ return this.x; }
    public void setX_G(double x_G) { X_G = x_G; }
    public double getX_G() { return X_G; }
    public void setY_G(double y_G) { Y_G = y_G; }
    public double getY_G() { return Y_G; }

    public ModelGhost(Circle ghostbody, ImagePattern ghostRight_img, ImagePattern ghostUp_img, ImagePattern ghostDown_img, ImagePattern ghostFrightened_img, int pointsToStart)
    {
        this.ghostbody = ghostbody;
        this.ghostRight_img = ghostRight_img;
        this.ghostUp_img = ghostUp_img;
        this.ghostDown_img = ghostDown_img;
        this.ghostFrightened_img = ghostFrightened_img;
        this.pointsToStart = pointsToStart;
        this.ghostCenter.setRadius(0);

    }


}
