package view;

import java.io.IOException;

import controller.GameController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Contains every GUI element
 */
public class GameView {
	
	@FXML
	private AnchorPane apMM;
	
	@FXML
	private Button sButton;
    //The scene where all is stacked up
    private Scene scene;

    //Stackpane, where all dialogs are stacked
    private AnchorPane rootPane;

    private Pane gamePane;
    private FXMLLoader loader;
    private Stage stage;
    public Scene getScene() {
        return scene;
    }
    
    public GameView() {
    	stage = new Stage();
    }
    /**
     * GameView object for setting up the GUI
     *
     * @param stage the primary stage
     * @throws IOException 
     */
    public GameView(Stage stage) throws IOException {
    	this.stage = stage;
    	loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        stage.setTitle("Rennspiel");
        stage.setResizable(false);
        stage.sizeToScene();
        rootPane = loader.load();
        scene = new Scene(rootPane, 1300, 800);

    	GameController gc = loader.getController();
    	gc.setStage(stage);
        
        stage.setScene(scene);
        
    }
    
    /**
     * Sets up the main game window with the course as panebackground,
     * the car in the initial Position
     * @throws IOException 
     */
    public void setUpGameWindow() throws IOException {
    	loader = new FXMLLoader(getClass().getResource("GamePane.fxml"));
        rootPane = loader.load();
        scene = new Scene(rootPane, 1300, 800);

        stage.setScene(scene);
    }
}