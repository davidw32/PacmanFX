package Controller;

import Model.ModelGameoptions;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import Model.ModelGhost;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import Model.ModelCrossing;

/***
 * @Author: Patrick Pavlenko,David Waelsch
 *
 */
public class Ghost implements Controller  {

    @FXML Circle ghost1;
    @FXML Circle ghost2;
    @FXML Circle ghost3;
    @FXML Circle ghost4;

    private ArrayList<ModelCrossing> cross;
    public ModelGhost[] M_Ghosts;

    /**
     * Die initModel()-Methode initialisiert alle benoetigten Geister-Models und gibt ihnen ihre FXML-Objekte.
     */
    public void initModel()
    {
        ModelGhost ghost1_M = new ModelGhost(ghost1,new ImagePattern(new Image("/Icon/rot_rechts_anim.gif")),new ImagePattern(new Image("/Icon/rot_oben_anim.gif")),new ImagePattern(new Image("/Icon/rot_unten_anim.gif")),new ImagePattern(new Image("/Icon/powerup_ghost_anim.gif")),100);
        ModelGhost ghost2_M = new ModelGhost(ghost2,new ImagePattern(new Image("/Icon/pink_rechts_anim.gif")),new ImagePattern(new Image("/Icon/pink_oben_anim.gif")),new ImagePattern(new Image("/Icon/pink_unten_anim.gif")),new ImagePattern(new Image("/Icon/powerup_ghost_anim.gif")),200);
        ModelGhost ghost3_M = new ModelGhost(ghost3,new ImagePattern(new Image("/Icon/tuerkis_rechts_anim.gif")),new ImagePattern(new Image("/Icon/tuerkis_oben_anim.gif")),new ImagePattern(new Image("/Icon/tuerkis_unten_anim.gif")),new ImagePattern(new Image("/Icon/powerup_ghost_anim.gif")),300);
        ModelGhost ghost4_M = new ModelGhost(ghost4,new ImagePattern(new Image("/Icon/orange_rechts_anim.gif")),new ImagePattern(new Image("/Icon/orange_oben_anim.gif")),new ImagePattern(new Image("/Icon/orange_unten_anim.gif")),new ImagePattern(new Image("/Icon/powerup_ghost_anim.gif")),400);

        ghost1.setFill(ghost1_M.getGhostUp_img());
        ghost2.setFill(ghost2_M.getGhostUp_img());
        ghost3.setFill(ghost3_M.getGhostUp_img());
        ghost4.setFill(ghost4_M.getGhostUp_img());

        ghost1_M.setAxis();
        ghost2_M.setAxis();
        ghost3_M.setAxis();
        ghost4_M.setAxis();

        M_Ghosts = new ModelGhost[]{ghost1_M,ghost2_M,ghost3_M,ghost4_M};
    }

    /**
     * Die setCross()-Methode uebergibt dem Controller die dem Spielfeld entsprechende Liste von Kreuzungspunkten.
     */
    public void setCross(ArrayList<ModelCrossing> cross)
    {
        this.cross = cross;
    }

    public void initialize()
    {
        ghost1.toFront();
        ghost2.toFront();
        ghost3.toFront();
        ghost4.toFront();
        initModel();
    }

    /**
     * Die ghostMove()-Methode laesst jeden Geist einzeln durch Pixelspruenge in die jeweils bestimmte Richtung laufen, sobald der Geist das "Geisterhaus" verlassen darf.
     * Im Anschluss wird ueberprueft, ob sich der Geist auf einem Kreuzungspunkt befindet.
     */
    public void ghostMove(ModelGameoptions gameoptiondata){
        for(int geisteranzahl = 0;geisteranzahl <M_Ghosts.length;geisteranzahl++)
        {
            ModelGhost ghost = M_Ghosts[geisteranzahl];
            if(ghost.getPointDifference(ghost.getPlayer().getModel().getScore()) >= ghost.getPointsToStart()) {
                if (ghost.getX()) {
                    ghost.getGhostbody().setCenterX(ghost.getX_G() + ghost.getMove());
                    ghost.setX_G(ghost.getGhostbody().getCenterX());
                    ghost.setGhostCenterX();
                } else {
                    ghost.getGhostbody().setCenterY(ghost.getY_G() + ghost.getMove());
                    ghost.setY_G(ghost.getGhostbody().getCenterY());
                    ghost.setGhostCenterY();
                }
                onCrossing(cross, M_Ghosts, geisteranzahl, gameoptiondata);
            }
        }
    }

    /**
     * Die directionsFrightenedGhost()-Methode bestimmt von dem ensprechenden Kreuzungspunkt welche moeglichen Laufrichtungen es gibt
     * und bestimmt fuer einen fluechtenden Geist dann zufaellig in welche Richtung er sich bewegen soll.
     * Diese zufaellige Richtung wird dann zurueckgegeben.
     */
    public int directionsFrightenedGhost(ModelCrossing cross) {
        ArrayList<Integer> possibleDirections = new ArrayList();
        if(cross.getRight()){ possibleDirections.add(1); }
        if(cross.getLeft()){ possibleDirections.add(2); }
        if(cross.getUp()){ possibleDirections.add(3); }
        if(cross.getDown()){ possibleDirections.add(4); }
        int randDirection = possibleDirections.get(ThreadLocalRandom.current()
                .nextInt(possibleDirections.size())
                % 4);
        return randDirection;
    }

    /**
     * Die changeDirection()-Methode aendert die Bewegungsrichtung der Geister in die im vorraus bestimmte Richtung.
     * Dabei aendert sich auch das Bild des Geistes, damit die Augen weiterhin in die richtige Richtung zeigen.
     * Wenn der Geist fluechtet wird das Bild auf das ghostFrightened_img gesetzt.
     */
    public void changeDirection(ModelGhost[] ghost,int gnr, int direction){
        if(direction == 1){
            ghost[gnr].setX(true);
            ghost[gnr].setMove(1);
            if(!ghost[gnr].getPlayer().getModel().getPowerMode()) {
                ghost[gnr].getGhostbody().setFill(ghost[gnr].getGhostRight_img());
                ghost[gnr].getGhostbody().setScaleX(1);
            }
        }
        else if(direction == 2){
            ghost[gnr].setX(true);
            ghost[gnr].setMove(-1);
            if(!ghost[gnr].getPlayer().getModel().getPowerMode()) {
                ghost[gnr].getGhostbody().setFill(ghost[gnr].getGhostRight_img());
                ghost[gnr].getGhostbody().setScaleX(-1);
            }
        }
        else if(direction == 3){
            ghost[gnr].setX(false);
            ghost[gnr].setMove(-1);
            if(!ghost[gnr].getPlayer().getModel().getPowerMode()) {
                ghost[gnr].getGhostbody().setFill(ghost[gnr].getGhostUp_img());
            }
        }
        else if(direction == 4){
            ghost[gnr].setX(false);
            ghost[gnr].setMove(1);
            if(!ghost[gnr].getPlayer().getModel().getPowerMode()) {
                ghost[gnr].getGhostbody().setFill(ghost[gnr].getGhostDown_img());
            }
        }
        else{
            ghost[gnr].setX(true);
            ghost[gnr].setMove(0);
            ghost[gnr].getGhostbody().setFill(ghost[gnr].getGhostUp_img());
        }
        if(ghost[gnr].getPlayer().getModel().getPowerMode() && ghost[gnr].getGhostbody().getFill() != ghost[gnr].getGhostFrightened_img()){
            ghost[gnr].getGhostbody().setFill(ghost[gnr].getGhostFrightened_img());
        }

    }

    /**
     * Die onCrossing()-Methode ueberprueft fuer einen Geist, ob er sich gerade auf einem Kreuzungspunkt von dem jeweiligen Spielfeld befindet.
     * Wenn dies der Fall ist und der Geist sich in einem der Teleportationstunnel auf den Spielfeldern befindet, wird er auf die andere Seite des Spielfelds gesetzt.
     * Wenn Pacman sich nicht in seinem Power-Mode befindet, dann wird der kuerzeste Weg zu der Zielkreuzung des Geistes gesucht. Sonst wird eine zufaellige Richtung fuer den Geist gesucht.
     */
    public void onCrossing(ArrayList<ModelCrossing> cross,ModelGhost[] ghost,int gnr, ModelGameoptions gameoptiondata){
        for(int i=0; i<cross.size(); i++) {
            if (ghost[gnr].getGhostCenter().getBoundsInParent().intersects(cross.get(i).getBoundsInParent())) {
                if(i==31 && gameoptiondata.getGameoptionsave().getMap().equals("CLASSIC")){ ghost[gnr].setX_G(cross.get(26).getBoundsInParent().getCenterX()+1); } // wenn in rechtem Durchgang dann wird Geist in den linken Durchgang gesetzt
                else if(i==26 && gameoptiondata.getGameoptionsave().getMap().equals("CLASSIC")){ ghost[gnr].setX_G(cross.get(31).getBoundsInParent().getCenterX()-1); } // wenn in linkem Durchgang dann wird Geist in den rechten Durchgang gesetzt
                else if(i==27 && gameoptiondata.getGameoptionsave().getMap().equals("HSHL")){ ghost[gnr].setX_G(cross.get(30).getBoundsInParent().getCenterX()-1); }
                else if(i==30 && gameoptiondata.getGameoptionsave().getMap().equals("HSHL")){ ghost[gnr].setX_G(cross.get(27).getBoundsInParent().getCenterX()+1); }

                if(ghost[gnr].getPlayer().getModel().getPowerMode() == false){
                    initializeShortestWay(cross,cross.get(i),ghost,gnr);
                }
                else{
                    changeDirection(ghost, gnr, directionsFrightenedGhost(cross.get(i)));
                }
                break;
            }
        }
    }

    /**
     * Die findShortestWay()-Methode sucht mit Hilfe des Dijkstra-Algorithmus den kuerzesten Weg von dem Startkreuzungspunkt auf dem sich der Geist befindet zu dem
     * vom Geist abhaengigen Zielkreuzungspunkt.
     */
    public void findShortestWay(ArrayList<ModelCrossing> unvisitedCrossings, ModelCrossing startCrossing, ModelCrossing targetCrossing, ModelGhost[] ghosts, int gnr){ // findet kuerzesten Weg zum Spieler-Knoten
        while(!unvisitedCrossings.isEmpty()){
            ModelCrossing nearestCrossing = unvisitedCrossings.get(0);
            for(ModelCrossing crossing : unvisitedCrossings){
                if(nearestCrossing.getDistanceToStartCrossing() > crossing.getDistanceToStartCrossing()){
                    nearestCrossing = crossing;
                }
            }
            if(nearestCrossing.getRight()){
                if(unvisitedCrossings.contains(nearestCrossing.getCrossRight())){
                    updateDistToStart(nearestCrossing, nearestCrossing.getCrossRight(), nearestCrossing.getDistToRight());
                }
            }
            if(nearestCrossing.getLeft()){
                if(unvisitedCrossings.contains(nearestCrossing.getCrossLeft())){
                    updateDistToStart(nearestCrossing, nearestCrossing.getCrossLeft(), nearestCrossing.getDistToLeft());
                }
            }
            if(nearestCrossing.getUp()){
                if(unvisitedCrossings.contains(nearestCrossing.getCrossUp())){
                    updateDistToStart(nearestCrossing, nearestCrossing.getCrossUp(), nearestCrossing.getDistToUp());
                }
            }
            if(nearestCrossing.getDown()){
                if(unvisitedCrossings.contains(nearestCrossing.getCrossDown())){
                    updateDistToStart(nearestCrossing, nearestCrossing.getCrossDown(), nearestCrossing.getDistToDown());
                }
            }
            unvisitedCrossings.remove(nearestCrossing);
        }
        findDirection(startCrossing, targetCrossing, ghosts, gnr);

    }

    /**
     * Die initializeShortestWay()-Methode setzt vorbereitend auf den Dijkstra-Algorithmus den Zielkreuzungspunkt vom Geist abhaengig fest.
     * Zusaetzlich setzt sie die Entfernungen von jedem Kreuzungspunkt zum Startpunkt auf eine sehr hohe Zahl und setzt den Vorgaenger jedes Knotens auf null.
     * Danach wird die Entfernung des Startpunkts zum Startpunkt wieder auf 0 gesetzt.
     */
    public void initializeShortestWay(ArrayList<ModelCrossing> cross, ModelCrossing startCrossing, ModelGhost[] ghosts, int gnr){
        ModelCrossing targetCrossing;
        switch(gnr){
            case 0:
                targetCrossing = ghosts[gnr].getPlayer().getModel().getCurrentCrossing();
                break;
            case 1:
                targetCrossing = ghosts[gnr].getPlayer().getModel().getNextCrossing();
                if(targetCrossing == startCrossing){
                    targetCrossing = ghosts[gnr].getPlayer().getModel().getCurrentCrossing();
                }
                break;
            case 2:
                targetCrossing = ghosts[gnr].getPlayer().getModel().getCurrentCrossing();
                if(Math.abs(ghosts[gnr].getGhostbody().getCenterX() - ghosts[gnr].getPlayer().getModel().getPacman().getCenterX()) < 80 && Math.abs(ghosts[gnr].getGhostbody().getCenterY() - ghosts[gnr].getPlayer().getModel().getPacman().getCenterY()) < 80){
                    targetCrossing = cross.get(5);
                }
                break;
            case 3:
                targetCrossing = ghosts[gnr].getPlayer().getModel().getCurrentCrossing();
                if(Math.abs(ghosts[gnr].getGhostbody().getCenterX() - ghosts[gnr].getPlayer().getModel().getPacman().getCenterX()) < 150 && Math.abs(ghosts[gnr].getGhostbody().getCenterY() - ghosts[gnr].getPlayer().getModel().getPacman().getCenterY()) < 150){
                    targetCrossing = cross.get(0);
                }
                break;
            default :
                targetCrossing = ghosts[gnr].getPlayer().getModel().getCurrentCrossing();
        }
        if(startCrossing==targetCrossing){
            targetCrossing=ghosts[0].getPlayer().getModel().getNextCrossing(); // wenn die Geister schon am Ziel sind dann wird ihr neues Ziel auf das vorraussichtliche Crossing von Pacman gesetzt
        }
        ArrayList<ModelCrossing> unvisitedCrossings = new ArrayList<ModelCrossing>();
        for(int i = 0; i<cross.size();i++){
            unvisitedCrossings.add(cross.get(i));
            unvisitedCrossings.get(i).setDistanceToStartCrossing(500000);
            unvisitedCrossings.get(i).setPreCrossing(null);
        }
        startCrossing.setDistanceToStartCrossing(0);
        findShortestWay(unvisitedCrossings, startCrossing, targetCrossing, ghosts, gnr);
    }

    /**
     * Die updateDistToStart()-Methode aktualisiert von dem aktuell ueberprueften Kreuzungspunkt die Entfernung zum Startpunkt falls diese Entfernung kleiner ist als die Vorherige.
     */
    public void updateDistToStart(ModelCrossing crossing, ModelCrossing nearCrossing, double crossDistance){
        double possibleDistance = crossing.getDistanceToStartCrossing() + crossDistance;
        if(nearCrossing.getDistanceToStartCrossing() > possibleDistance){
            nearCrossing.setDistanceToStartCrossing(possibleDistance);
            nearCrossing.setPreCrossing(crossing);
        }
    }

    /**
     * Die findDirection()-Methode ermittelt in welche Richtung der vom Startpunkt aus nachste Punkt auf der kuerzesten Strecke
     * zum Zielpunkt liegt und uebergibt der changeDirection()-Methode diesen Wert.
     */
    public void findDirection(ModelCrossing startCrossing, ModelCrossing targetCrossing, ModelGhost[] ghosts, int gnr){
        ModelCrossing crossing = targetCrossing;
        while(crossing.getPreCrossing() != startCrossing){
            crossing = crossing.getPreCrossing();
        }
        if(crossing == startCrossing.getCrossRight()){
            changeDirection(ghosts,gnr,1);
        }
        else if(crossing == startCrossing.getCrossLeft()){
            changeDirection(ghosts,gnr,2);
        }
        else if(crossing == startCrossing.getCrossUp()){
            changeDirection(ghosts,gnr,3);
        }
        else if(crossing == startCrossing.getCrossDown()){
            changeDirection(ghosts,gnr,4);
        }
        else{
            changeDirection(ghosts, gnr, 0);
        }
    }

    /**
     * Die setAllToStart()-Methode setzt alle Geister auf ihren Anfangspunkt zurueck.
     */
    public void setAllToStart(ModelCrossing cross)
    {
        for(int gnr = 0; gnr < M_Ghosts.length; gnr++){
            M_Ghosts[gnr].setX_G(cross.getBoundsInParent().getCenterX());
            M_Ghosts[gnr].setY_G(cross.getBoundsInParent().getCenterY());
            M_Ghosts[gnr].getGhostbody().setCenterX(M_Ghosts[gnr].getX_G());
            M_Ghosts[gnr].getGhostbody().setCenterY(M_Ghosts[gnr].getY_G());
            M_Ghosts[gnr].setGhostCenterX();
            M_Ghosts[gnr].setGhostCenterY();
            M_Ghosts[gnr].setNewDirection(0);
            M_Ghosts[gnr].setMove(0);
            M_Ghosts[gnr].setPointsWhenKilled(M_Ghosts[gnr].getPlayer().getModel().getScore());
        }
    }

    /**
     * Die setThisToStart()-Methode setzt einen, von Pacman gegessenen, Geist auf seinen Startpunkt zurueck.
     */
    public void setThisToStart(ModelCrossing cross, int ghost) // setzt den gegessenen Geist auf seinen Anfangspunkt zurueck
    {
        M_Ghosts[ghost].setX_G(cross.getBoundsInParent().getCenterX());
        M_Ghosts[ghost].setY_G(cross.getBoundsInParent().getCenterY());
        M_Ghosts[ghost].getGhostbody().setCenterX(M_Ghosts[ghost].getX_G());
        M_Ghosts[ghost].getGhostbody().setCenterY(M_Ghosts[ghost].getY_G());
        M_Ghosts[ghost].setGhostCenterX();
        M_Ghosts[ghost].setGhostCenterY();
        M_Ghosts[ghost].setNewDirection(0);
        M_Ghosts[ghost].setMove(0);
        M_Ghosts[ghost].setPointsWhenKilled(M_Ghosts[ghost].getPlayer().getModel().getScore());
    }

    /**
     * Die getGhosts()-Methode gibt die Geistermodels zurueck.
     */
    public ModelGhost[] getGhosts()
    {
        return M_Ghosts;
    }


}
