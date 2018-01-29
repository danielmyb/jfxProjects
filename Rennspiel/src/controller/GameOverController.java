package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.GameView;

public class GameOverController {

	@FXML
	private AnchorPane ap;
	@FXML
	private Button mButton;
	@FXML
	private Button rButton;
	
	private GameView gameView;
	
	@FXML
	private void initialize() {
		
		mButton.setOnAction((event) -> {
			gameView = new GameView((Stage) ap.getScene().getWindow());
			gameView.setUpMenu();
		});
		rButton.setOnAction((event) -> {
			gameView = new GameView((Stage) ap.getScene().getWindow());
			gameView.setUpGameWindow();
		});
	}
}
