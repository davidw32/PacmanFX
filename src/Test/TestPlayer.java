package Test;
import Controller.Player;

/**
 * @Author: Patrick Pavlenko
 * @since 10.05.2019
 * Testet den Controller vom Player
 */

import Model.ModelCrossing;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class TestPlayer {

    protected Player player = new Player();
    protected Circle pac = new Circle();
    protected Circle g1 = new Circle(); //Geister
    protected Circle g2 = new Circle();
    protected Circle g3 = new Circle();
    protected Circle g4 = new Circle();
    protected Label text ;
    protected ModelCrossing cross1 = new ModelCrossing(true,false,false,false);
    protected ModelCrossing cross2 = new ModelCrossing(false,true,false,false);
    protected ModelCrossing cross3 = new ModelCrossing(false,false,true,false);
    protected ModelCrossing cross4 = new ModelCrossing(false,false,false,true);



    @Before
    public void fixToolkitError()
    {
        JFXPanel fxPanel = new JFXPanel();
        player.initModel();
        player.getModel().setPacman(pac);
    }

    @Test
    public void testDirectionPacman()
    {
        assertEquals(1,player.directionsPacman(1,cross1,true));
        assertEquals(2,player.directionsPacman(2,cross2,true));
        assertEquals(3,player.directionsPacman(3,cross3,true));
        assertEquals(4,player.directionsPacman(4,cross4,true));
        player.getModel().setOldDirection(1);
        assertEquals(1,player.directionsPacman(2,cross1,true));
    }

    @Test
    public void testChangeDirection(){
        player.getModel().setNewDirection(2);
        player.getModel().setOldDirection(1);
        player.changeDirection(cross1);
        assertTrue(player.getModel().getX() && player.getModel().getMove() == 1);
        player.changeDirection(cross2);
        assertTrue(player.getModel().getX() && player.getModel().getMove() == -1);
        player.getModel().setNewDirection(1);
        player.changeDirection(cross2);
        assertTrue(player.getModel().getX() && player.getModel().getMove() == -1);
        player.getModel().setNewDirection(3);
        player.changeDirection(cross3);
        assertTrue(!player.getModel().getX() && player.getModel().getMove() == -1);
        player.getModel().setNewDirection(2);
        player.changeDirection(cross3);
        assertTrue(!player.getModel().getX() && player.getModel().getMove() == -1);
        player.getModel().setNewDirection(4);
        player.changeDirection(cross4);
        assertTrue(!player.getModel().getX() && player.getModel().getMove() == 1);
        player.getModel().setNewDirection(2);
        player.changeDirection(cross1);
        assertTrue(player.getModel().getX() && player.getModel().getMove() == 0);
    }

    @Test
    public void testCheckPowerMode(){
        player.getModel().setPowerMode(false);
        player.getModel().setScore(200);
        player.getModel().setScoreWhenPowerMode(0);
        player.checkPowerMode();
        assertTrue(!player.getModel().getPowerMode());
        player.getModel().setPowerMode(true);
        player.checkPowerMode();
        assertTrue(player.getModel().getPowerMode());
        player.getModel().setScore(300);
        player.checkPowerMode();
        assertTrue(!player.getModel().getPowerMode());

    }

    @Test
    public void testCollide()
    {
        text = new Label();
        pac.setCenterX(50);
        pac.setCenterY(50);
        g1.setCenterX(50);
        g1.setCenterY(50);
        g2.setCenterX(50);
        g2.setCenterY(50);
        g3.setCenterX(50);
        g3.setCenterY(50);
        g4.setCenterX(50);
        g4.setCenterY(50);
        assertEquals(5,player.collide(g1,g2,g3,g4,cross1,text));
        player.getModel().setPowerMode(true);
        assertEquals(0,player.collide(g1,g2,g3,g4,cross1,text));
        g1.setCenterY(200);
        assertEquals(1,player.collide(g1,g2,g3,g4,cross1,text));
        g2.setCenterY(200);
        assertEquals(2,player.collide(g1,g2,g3,g4,cross1,text));
        g3.setCenterY(200);
        assertEquals(3,player.collide(g1,g2,g3,g4,cross1,text));
        g1.setCenterY(500);
        g2.setCenterY(500);
        g3.setCenterY(500);
        g4.setCenterY(500);
        assertEquals(-1,player.collide(g1,g2,g3,g4,cross1,text));
    }

    @Test
    public void testUpdateScore(){
        text = new Label();
        player.getModel().setScore(0);
        player.UpdateScore(text, 50);
        assertEquals(50, player.getModel().getScore());

    }

    @Test
    public void testResetPlayerStats(){
        ImageView img1 = new ImageView();
        ImageView img2 = new ImageView();
        ImageView img3 = new ImageView();
        player.getModel().setScore(40);
        player.getModel().setLife(0);
        player.ResetPlayerStats(img1,img2,img3);
        assertEquals(0, player.getModel().getScore());
        assertEquals(3, player.getModel().getLife());
    }





}
