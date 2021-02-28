package Test;
import Controller.Startscreen;
import Model.ModelStartscreen;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestStartscreen {

    private Startscreen startscreen;
    public FXMLLoader loader_startscreen = new FXMLLoader(getClass().getResource("/View/ViewStartscreen.fxml"));
    public GridPane start = new GridPane();

    @Before
    public void fixToolkitError() throws  Exception // und instanziierungen
    {
        JFXPanel fxPanel = new JFXPanel();
        start = loader_startscreen.load();
        startscreen = loader_startscreen.getController();
    }


    public void AskUserName() throws  Exception // setzte die Objekte bei der Userabfrage durch Koordinatensetzung mittels Breite/Höhenanteile
    {
        Pattern p = Pattern.compile("[^A-Za-z0-9]"); // Diese Symbole (der Bereich) ist/sind nur erlaubt
        Matcher m = p.matcher(startscreen.getUser_input().getText());
        boolean specialSymbol = m.find();
        if ((startscreen.getUser_input().getText().length() < 4 | startscreen.getUser_input().getText().length() > 16) && !specialSymbol)
        {
            startscreen.getWrong_input_text().setText("Invalid entry: Your name must contain at least 4 or a maximum of 16 characters!");
            startscreen.getWrong_input_text().setVisible(true);
        }
        else if(specialSymbol)
        {
            startscreen.getWrong_input_text().setText("Invalid entry: Make sure there are no special characters in your name!");
            startscreen.getWrong_input_text().setVisible(true);
        }
        else
        {
            startscreen.getUser_input_area().setVisible(false);
        }
    }

    @Test
    public void AskUserNameTest() throws Exception // Fragt ob bei Falschen Werten der Text für falsche ausgaben kommt oder es weiter geht
    {
        AskUserName();
        assertTrue(  startscreen.getWrong_input_text().isVisible()); // ab hier nur falsche eingaben
        assertTrue(startscreen.getUser_input_area().isVisible());
        startscreen.getWrong_input_text().setVisible(false);
        startscreen.getUser_input().setText("");
        AskUserName();
        assertTrue(  startscreen.getWrong_input_text().isVisible());
        assertTrue(startscreen.getUser_input_area().isVisible());
        startscreen.getWrong_input_text().setVisible(false);
        startscreen.getUser_input().setText("@asd");
        AskUserName();
        assertTrue(  startscreen.getWrong_input_text().isVisible());
        assertTrue(startscreen.getUser_input_area().isVisible());
        startscreen.getWrong_input_text().setVisible(false);
        startscreen.getUser_input().setText("#asfda");
        AskUserName();
        assertTrue(startscreen.getWrong_input_text().isVisible());
        assertTrue(startscreen.getUser_input_area().isVisible());
        startscreen.getWrong_input_text().setVisible(false);
        startscreen.getUser_input().setText("$sdfsdf");
        AskUserName();
        assertTrue(  startscreen.getWrong_input_text().isVisible());
        assertTrue(startscreen.getUser_input_area().isVisible());
        startscreen.getWrong_input_text().setVisible(false);
        startscreen.getUser_input().setText("!?sdfds");
        AskUserName();
        assertTrue(  startscreen.getWrong_input_text().isVisible());
        assertTrue(startscreen.getUser_input_area().isVisible());
        startscreen.getWrong_input_text().setVisible(false);
        startscreen.getUser_input().setText("asd");
        AskUserName();
        assertTrue(  startscreen.getWrong_input_text().isVisible());
        assertTrue(startscreen.getUser_input_area().isVisible());
        startscreen.getWrong_input_text().setVisible(false);
        startscreen.getUser_input().setText("Richtig"); // Hier richtige Eingabe
        AskUserName();
        assertFalse(startscreen.getUser_input_area().isVisible());

    }

    @Test
    public void TestInitModel()
    {
        startscreen.initModel();
        assertNotNull(startscreen);
    }
}
