package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;
import view.GameView;

public class GameController {

	@FXML
	private AnchorPane ap;
	@FXML
	private Button sButton;
	@FXML
	private Slider obsSlider;
	
	private GameModel gameModel;
	private GameView gameView;
	private Scene scene;

	private Stage stage;

	public static int obsCount;
	
	public GameController() {
		;
	}

	public GameController(GameModel gameModel, GameView gameView) {
		this.gameView = gameView;
		this.gameModel = gameModel;
		this.scene = gameView.getScene();
		// Set up keylistener
		setUpInputHandler();
	}

	public void setStage(Stage s) {
		this.stage = s;
	}

	/**
	 * Updates all needed dependencies every frame
	 *
	 * @param timeDifferenceInSeconds
	 *            the time passed since last frame
	 */
	public void updateContinuously(double timeDifferenceInSeconds) {

	}

	private void setUpInputHandler() {
		/*
		 * Useful actions: setOnKeyPressed, setOnKeyReleased
		 */
	}

	@FXML
	private void initialize() {
		sButton.setOnAction((event) -> {
			gameView = new GameView((Stage) ap.getScene().getWindow());
			gameView.setUpGameWindow();
		});
		obsSlider.valueProperty().addListener((observable,oldvalue,newvalue) -> {
			obsCount = newvalue.intValue();
		});
		
	}
}
