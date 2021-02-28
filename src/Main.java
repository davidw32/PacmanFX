import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import Controller.*;
import javafx.stage.WindowEvent;
import javafx.scene.text.Font;

/**
 * @Author: Patrick Pavlenko
 */

public class Main extends Application {

    private int window_height = 800;
    private int window_width = 640;

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


    /**
     * Objekte werden hier initialisiert
     * es werden Werte weitergegeben bzw Objekte
     * @param primaryStage die primaere Stage
     * @throws Exception wird fuer die Startmethode immer benoetigt
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Font.loadFont(Main.class.getResource("/Font/PressStart2P-Regular.ttf").toExternalForm(), 10);
        /**
         * Alle Grids erhalten ihre FXML_loader bzw werden initialisiert
         */
        root = loader_gamefield.load();
        start = loader_startscreen.load();
        root_matrix = loader_pausescreen.load();
        highscore_matrix = loader_highscore.load();
        options_matrix = loader_options.load();
        hshl_matrix = loader_HSHL.load();

        main_scene = initScene("/css/style.css",start);

        /**
         * Hier werden Werde oder Objekte weitergegeben an den jeweiligen Objekt
         */
        pausescreen_controller = loader_pausescreen.getController();
        hshl_controller = loader_HSHL.getController();
        pausescreen_controller.pauseScreen.setMatrix_pause(root_matrix);
        startscreen_controller = loader_startscreen.getController();
        gameoptions_controller = loader_options.getController();
        pausescreen_controller = loader_pausescreen.getController();
        startscreen_controller.startscreen.setMain_scene(main_scene);
        startscreen_controller.startscreen.setMatrix(root);
        startscreen_controller.startscreen.setStartscreen_grid(start);
        gamefield_controller = loader_gamefield.getController();
        highscore_controller = loader_highscore.getController();
        gamefield_controller.FinishedLoad(main_scene);
        hshl_controller.FinishedLoad(main_scene, gamefield_controller.getPlayerController());
        startscreen_controller.startscreen.setScreens(start,highscore_matrix,options_matrix,root,hshl_matrix);
        startscreen_controller.startscreen.setGamefieldController(gamefield_controller);
        startscreen_controller.startscreen.setHshlController(hshl_controller);
        gamefield_controller.setScreens(startscreen_controller,pausescreen_controller,main_scene);
        hshl_controller.setScreens(startscreen_controller,pausescreen_controller,main_scene);
        startscreen_controller.setTest(); // audio hover button
        pausescreen_controller.pauseScreen.setGridsAndScene(root,options_matrix,highscore_matrix,start,hshl_matrix,main_scene);
        pausescreen_controller.pauseScreen.setController(gamefield_controller,highscore_controller,hshl_controller);
        highscore_controller.highscoredata.setGridsAndScene(root_matrix,start,main_scene);
        startscreen_controller.startscreen.setHighscore(highscore_controller);
        startscreen_controller.startscreen.setGameoptions(gameoptions_controller);
        gameoptions_controller.gameoptiondata.setGridAndScene(main_scene,options_matrix,root_matrix,start);
        pausescreen_controller.setTest(); // audio hover button
        highscore_controller.setTest();
        gameoptions_controller.setTest();
        gameoptions_controller.gameoptiondata.setControllers(highscore_controller,gamefield_controller,pausescreen_controller,startscreen_controller,hshl_controller); // Für Optionen
        gameoptions_controller.gameoptiondata.setScene(main_scene);
        highscore_controller.highscoredata.setGameoptions(gameoptions_controller);

        gameoptions_controller.gameoptiondata.SetAllMatrix(root_matrix,highscore_matrix,root,options_matrix,start,hshl_matrix,startscreen_controller.getUser_input_area(),gamefield_controller.getGameovermatrix());
        highscore_controller.getSave();
        highscore_controller.AddHighscorePlayers();
        gameoptions_controller.setJoystick(gameoptions_controller,gamefield_controller.getPlayerController());
        gameoptions_controller.setSavedOptionsView();
        gameoptions_controller.gameoptiondata.getJoystick().joystick.setCurrentButtons(
                startscreen_controller.getStart(),startscreen_controller.getHighscore_list(),
                startscreen_controller.getGame_options(),startscreen_controller.getQuit_game()
        );
        gameoptions_controller.gameoptiondata.getJoystick().joystick.setOptionsButtons(gameoptions_controller.getButton());
        gameoptions_controller.gameoptiondata.getJoystick().joystick.setPauseButtons(pausescreen_controller.getButtons());
        gameoptions_controller.gameoptiondata.getJoystick().joystick.setGameButtons(gamefield_controller.getButtons());
        startscreen_controller.startscreen.setMap_name(gameoptions_controller.getMap_choose().getText());

        window_height = Integer.parseInt(gameoptions_controller.gameoptiondata.getGameoptionsave().getResolution_y())+40;
        window_width = Integer.parseInt(gameoptions_controller.gameoptiondata.getGameoptionsave().getResolution_x())+15;


        primaryStage.setResizable(false);
        primaryStage.setTitle("Pac Man");
        primaryStage.setScene(main_scene);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/Icon/PacMan_icon.png")));
        main_scene.getWindow().setHeight(window_height); // bitte die Dinger nicht verschieben, es muss vor setScene stehen!
        main_scene.getWindow().setWidth(window_width);
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() // eventhandler für button-klick oben rechts fenster zum schließen
        {
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });
        }

    /**
     * CSS Pfad wird hier in die Szene intialisiert
     * @param css_path Pfad fuer die style.css also die stylesheet
     * @param root die Grid welche am anfang des Programmstarts aufgerufen wird (Startscreen)
     * @return gibt dann die Szene aus
     */
    public Scene initScene(String css_path, Parent root)
    {
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(css_path).toExternalForm());
        return scene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
