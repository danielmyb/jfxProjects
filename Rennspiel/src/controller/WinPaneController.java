package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.GameView;

public class WinPaneController {

	@FXML
	private AnchorPane ap;
	@FXML
	private TextField winTime;
	@FXML
	private Button mButton;
	@FXML
	private Button rButton;
	private GameView gameView;

	@FXML
	private void initialize() throws IOException {
		long fTime = GamePaneController.finishTime;
		winTime.setText(((fTime / 60) % 60) + ":" + (fTime % 60));

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
