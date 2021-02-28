package Model;

import javafx.beans.NamedArg;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class ModelCrossing extends Circle implements Model {

    private boolean right;
    private boolean left;
    private boolean up;
    private boolean down;
    private double distToRight;
    private double distToLeft;
    private double distToUp;
    private double distToDown;
    private ModelCrossing crossRight;
    private ModelCrossing crossLeft;
    private ModelCrossing crossUp;
    private ModelCrossing crossDown;
    private double distanceToStartCrossing;
    private ModelCrossing preCrossing; // Vorgaengercrossing

    public ModelCrossing(@NamedArg("right") boolean right, @NamedArg("left") boolean left,
                         @NamedArg("up") boolean up, @NamedArg("down") boolean down) {
        this.right=right;
        this.left=left;
        this.up=up;
        this.down=down;
        this.setRadius(0);
    }

    public void findRightCross(ArrayList<ModelCrossing> cross){
        if(this.getRight()) {
            Circle circle = new Circle(0);
            circle.setCenterX(this.getBoundsInParent().getCenterX());
            circle.setCenterY(this.getBoundsInParent().getCenterY());
            boolean found = false;
            for (int i = 1; i < 600; i++) {
                circle.setCenterX(this.getBoundsInParent().getCenterX() + i);
                for (ModelCrossing nextCrossing : cross) {
                    if (circle.getBoundsInParent().intersects(nextCrossing.getBoundsInParent())) {
                        this.setDistToRight(i);
                        this.setCrossRight(nextCrossing);
                        found = true;
                        break;
                    }
                }
                if (found) {
                    break;
                }
            }
        }
    }
    public void findLeftCross(ArrayList<ModelCrossing> cross){
        if(this.getLeft()) {
            Circle circle = new Circle(0);
            circle.setCenterX(this.getBoundsInParent().getCenterX());
            circle.setCenterY(this.getBoundsInParent().getCenterY());
            boolean found = false;
            for (int i = 1; i < 600; i++) {
                circle.setCenterX(this.getBoundsInParent().getCenterX() - i);
                for (ModelCrossing nextCrossing : cross) {
                    if (circle.getBoundsInParent().intersects(nextCrossing.getBoundsInParent())) {
                        this.setDistToLeft(i);
                        this.setCrossLeft(nextCrossing);
                        found = true;
                        break;
                    }
                }
                if (found) {
                    break;
                }
            }
        }
    }
    public void findUpCross(ArrayList<ModelCrossing> cross){
        if(this.getUp()) {
            Circle circle = new Circle(0);
            circle.setCenterX(this.getBoundsInParent().getCenterX());
            circle.setCenterY(this.getBoundsInParent().getCenterY());
            boolean found = false;
            for (int i = 1; i < 600; i++) {
                circle.setCenterY(this.getBoundsInParent().getCenterY() - i);
                for (ModelCrossing nextCrossing : cross) {
                    if (circle.getBoundsInParent().intersects(nextCrossing.getBoundsInParent())) {
                        this.setDistToUp(i);
                        this.setCrossUp(nextCrossing);
                        found = true;
                        break;
                    }
                }
                if (found) {
                    break;
                }
            }
        }
    }
    public void findDownCross(ArrayList<ModelCrossing> cross){
        if(this.getDown()) {
            Circle circle = new Circle(0);
            circle.setCenterX(this.getBoundsInParent().getCenterX());
            circle.setCenterY(this.getBoundsInParent().getCenterY());
            boolean found = false;
            for (int i = 1; i < 600; i++) {
                circle.setCenterY(this.getBoundsInParent().getCenterY() + i);
                for (ModelCrossing nextCrossing : cross) {
                    if (circle.getBoundsInParent().intersects(nextCrossing.getBoundsInParent())) {
                        this.setDistToDown(i);
                        this.setCrossDown(nextCrossing);
                        found = true;
                        break;
                    }
                }
                if (found) {
                    break;
                }
            }
        }
    }

    public boolean getRight()
    {
        return right;
    }
    public boolean getLeft()
    {
        return left;
    }
    public boolean getUp()
    {
        return up;
    }
    public boolean getDown()
    {
        return down;
    }

    public double getDistToRight() {
        return distToRight;
    }

    public double getDistToLeft() {
        return distToLeft;
    }

    public double getDistToUp() {
        return distToUp;
    }

    public double getDistToDown() {
        return distToDown;
    }

    public ModelCrossing getCrossRight() {
        return crossRight;
    }

    public ModelCrossing getCrossLeft() {
        return crossLeft;
    }

    public ModelCrossing getCrossUp() {
        return crossUp;
    }

    public ModelCrossing getCrossDown() {
        return crossDown;
    }

    public void setDistToRight(double distToRight) {
        this.distToRight = distToRight;
    }

    public void setDistToLeft(double distToLeft) {
        this.distToLeft = distToLeft;
    }

    public void setDistToUp(double distToUp) {
        this.distToUp = distToUp;
    }

    public void setDistToDown(double distToDown) {
        this.distToDown = distToDown;
    }

    public void setCrossRight(ModelCrossing crossRight) {
        this.crossRight = crossRight;
    }

    public void setCrossLeft(ModelCrossing crossLeft) {
        this.crossLeft = crossLeft;
    }

    public void setCrossUp(ModelCrossing crossUp) {
        this.crossUp = crossUp;
    }

    public void setCrossDown(ModelCrossing crossDown) {
        this.crossDown = crossDown;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public double getDistanceToStartCrossing() {
        return distanceToStartCrossing;
    }

    public void setDistanceToStartCrossing(double distanceToStartCrossing) {
        this.distanceToStartCrossing = distanceToStartCrossing;
    }

    public ModelCrossing getPreCrossing() {
        return preCrossing;
    }

    public void setPreCrossing(ModelCrossing preCrossing) {
        this.preCrossing = preCrossing;
    }
}
