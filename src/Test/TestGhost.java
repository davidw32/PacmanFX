package Test;

import Controller.*;
import Model.ModelCrossing;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;

public class TestGhost {

    public Scene main_scene;
    public FXMLLoader loader_gamefield = new FXMLLoader(getClass().getResource("/View/ViewGame.fxml"));
    public FXMLLoader loader_startscreen = new FXMLLoader(getClass().getResource("/View/ViewStartscreen.fxml"));
    public FXMLLoader loader_pausescreen = new FXMLLoader(getClass().getResource("/View/ViewPauseScreen.fxml"));
    public FXMLLoader loader_highscore = new FXMLLoader(getClass().getResource("/View/ViewHighscorelist.fxml"));
    public FXMLLoader loader_options = new FXMLLoader(getClass().getResource("/View/ViewGameoptions.fxml"));
    public FXMLLoader loader_HSHL = new FXMLLoader(getClass().getResource("/View/ViewHSHL.fxml"));

    public Gamefield gamefield_controller;
    public Startscreen startscreen_controller;
    public PauseScreen pausescreen_controller;
    public Highscore highscore_controller;
    public Gameoptions gameoptions_controller;
    public HSHL hshl_controller;

    public GridPane root = new GridPane();
    public GridPane start = new GridPane();
    public GridPane root_matrix = new GridPane();
    public GridPane highscore_matrix = new GridPane();
    public GridPane options_matrix = new GridPane();
    public GridPane hshl_matrix = new GridPane();

    public Ghost ghost = new Ghost();

    @Before
    public void setUp() throws Exception {
        JFXPanel fxPanel = new JFXPanel();

        start = loader_startscreen.load();
        root = loader_gamefield.load();
        root_matrix = loader_pausescreen.load();
        highscore_matrix = loader_highscore.load();
        options_matrix = loader_options.load();
        hshl_matrix = loader_HSHL.load();


        pausescreen_controller = loader_pausescreen.getController();
        hshl_controller = loader_HSHL.getController();
        startscreen_controller = loader_startscreen.getController();
        gameoptions_controller = loader_options.getController();
        pausescreen_controller = loader_pausescreen.getController();
        gamefield_controller = loader_gamefield.getController();
        highscore_controller = loader_highscore.getController();

        gamefield_controller.setScreens(startscreen_controller,pausescreen_controller,main_scene);

        ghost = gamefield_controller.Mgamefield.getGhostsController();
        ghost.getGhosts()[0].setPlayer(gamefield_controller.getPlayerController());


    }

    @Test
    public void testDirectionsFrightenedGhost(){
        int direction = gamefield_controller.Mgamefield.getGhostsController().directionsFrightenedGhost(gamefield_controller.Mgamefield.getCross().get(0));
        Assert.assertTrue(direction == 1 || direction == 4);
        Assert.assertFalse(direction == 2);
        Assert.assertFalse(direction == 3);
        direction = gamefield_controller.Mgamefield.getGhostsController().directionsFrightenedGhost(gamefield_controller.Mgamefield.getCross().get(1));
        Assert.assertTrue(direction == 1 || direction == 4 || direction==2);
        Assert.assertFalse(direction == 3);
    }

    @Test
    public void testChangeDirection(){
        ghost.changeDirection(ghost.getGhosts(),0,1);
        Assert.assertEquals(true, ghost.getGhosts()[0].getX());
        Assert.assertEquals(1, ghost.getGhosts()[0].getMove());
        ghost.changeDirection(ghost.getGhosts(),0,2);
        Assert.assertEquals(true, ghost.getGhosts()[0].getX());
        Assert.assertEquals(-1, ghost.getGhosts()[0].getMove());
        ghost.changeDirection(ghost.getGhosts(),0,3);
        Assert.assertEquals(false, ghost.getGhosts()[0].getX());
        Assert.assertEquals(-1, ghost.getGhosts()[0].getMove());
        ghost.changeDirection(ghost.getGhosts(),0,4);
        Assert.assertEquals(false, ghost.getGhosts()[0].getX());
        Assert.assertEquals(1, ghost.getGhosts()[0].getMove());
    }

    @Test
    public void testFindShortestWay(){
        ArrayList<ModelCrossing> cross = new ArrayList<>();
        cross.add(new ModelCrossing(true,false,false,false));
        cross.get(0).setCenterX(10);
        cross.get(0).setCenterY(10);
        cross.add(new ModelCrossing(true,true,false,true));
        cross.get(1).setCenterX(20);
        cross.get(1).setCenterY(10);
        cross.add(new ModelCrossing(false,true,false,false));
        cross.get(2).setCenterX(30);
        cross.get(2).setCenterY(10);
        cross.add(new ModelCrossing(false,false,true,false));
        cross.get(3).setCenterX(20);
        cross.get(3).setCenterY(30);

        cross.get(0).setCrossRight(cross.get(1));
        cross.get(1).setCrossDown(cross.get(3));
        cross.get(1).setCrossRight(cross.get(2));
        cross.get(1).setCrossLeft(cross.get(0));
        cross.get(2).setCrossLeft(cross.get(1));
        cross.get(3).setCrossUp(cross.get(1));

        ArrayList<ModelCrossing> unvisitedCrossings = new ArrayList<ModelCrossing>();
        for(int i = 0; i<cross.size();i++){
            unvisitedCrossings.add(cross.get(i));
            unvisitedCrossings.get(i).setDistanceToStartCrossing(500000);
            unvisitedCrossings.get(i).setPreCrossing(null);
        }
        cross.get(0).setDistanceToStartCrossing(0);

        ghost.findShortestWay(unvisitedCrossings,cross.get(0),cross.get(2),ghost.getGhosts(),0);
        Assert.assertTrue((ghost.getGhosts()[0].getX() == true && ghost.getGhosts()[0].getMove()==1));

        unvisitedCrossings = new ArrayList<ModelCrossing>();
        for(int i = 0; i<cross.size();i++){
            unvisitedCrossings.add(cross.get(i));
            unvisitedCrossings.get(i).setDistanceToStartCrossing(500000);
            unvisitedCrossings.get(i).setPreCrossing(null);
        }
        cross.get(0).setDistanceToStartCrossing(0);

        ghost.findShortestWay(unvisitedCrossings,cross.get(0),cross.get(3),ghost.getGhosts(),0);
        Assert.assertTrue((ghost.getGhosts()[0].getX() == true && ghost.getGhosts()[0].getMove()==1));

        unvisitedCrossings = new ArrayList<ModelCrossing>();
        for(int i = 0; i<cross.size();i++){
            unvisitedCrossings.add(cross.get(i));
            unvisitedCrossings.get(i).setDistanceToStartCrossing(500000);
            unvisitedCrossings.get(i).setPreCrossing(null);
        }
        cross.get(2).setDistanceToStartCrossing(0);

        ghost.findShortestWay(unvisitedCrossings,cross.get(2),cross.get(0),ghost.getGhosts(),0);
        Assert.assertTrue((ghost.getGhosts()[0].getX() == true && ghost.getGhosts()[0].getMove()==-1));

        unvisitedCrossings = new ArrayList<ModelCrossing>();
        for(int i = 0; i<cross.size();i++){
            unvisitedCrossings.add(cross.get(i));
            unvisitedCrossings.get(i).setDistanceToStartCrossing(500000);
            unvisitedCrossings.get(i).setPreCrossing(null);
        }
        cross.get(2).setDistanceToStartCrossing(0);

        ghost.findShortestWay(unvisitedCrossings,cross.get(2),cross.get(3),ghost.getGhosts(),0);
        Assert.assertTrue((ghost.getGhosts()[0].getX() == true && ghost.getGhosts()[0].getMove()==-1));

        unvisitedCrossings = new ArrayList<ModelCrossing>();
        for(int i = 0; i<cross.size();i++){
            unvisitedCrossings.add(cross.get(i));
            unvisitedCrossings.get(i).setDistanceToStartCrossing(500000);
            unvisitedCrossings.get(i).setPreCrossing(null);
        }
        cross.get(3).setDistanceToStartCrossing(0);

        ghost.findShortestWay(unvisitedCrossings,cross.get(3),cross.get(0),ghost.getGhosts(),0);
        Assert.assertTrue((ghost.getGhosts()[0].getX() == false && ghost.getGhosts()[0].getMove()==-1));

        unvisitedCrossings = new ArrayList<ModelCrossing>();
        for(int i = 0; i<cross.size();i++){
            unvisitedCrossings.add(cross.get(i));
            unvisitedCrossings.get(i).setDistanceToStartCrossing(500000);
            unvisitedCrossings.get(i).setPreCrossing(null);
        }
        cross.get(3).setDistanceToStartCrossing(0);

        ghost.findShortestWay(unvisitedCrossings,cross.get(3),cross.get(2),ghost.getGhosts(),0);
        Assert.assertTrue((ghost.getGhosts()[0].getX() == false && ghost.getGhosts()[0].getMove()==-1));
    }



}
