package controller;

import java.io.IOException;

import com.sun.javafx.geom.Vec2d;

import javafx.animation.AnimationTimer;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import model.Car;
import model.Checkpointlinie;
import model.GameModel;
import model.Kraefte;
import model.StartZiellinie;
import view.GameView;

/**
 * @author danie
 *
 */
public class GamePaneController {

	@FXML
	private Button mButton;
	@FXML
	private AnchorPane apGP;
	@FXML
	private Line leftLine;
	@FXML
	private Line rightLine;
	@FXML
	private Circle carbox;

	private GameModel gameModel;
	private GameView gameView;

	private Car car;
	private StartZiellinie sz;
	private Checkpointlinie cl;

	private long oldTime;

	public GamePaneController() {

	}

	@FXML
	private void initialize() {
		gameLoop();
		car = new Car();
		car.setPos(new Vec2d(carbox.getCenterX(), carbox.getCenterY()));
		sz = new StartZiellinie();
		sz.setAnfang(new Vec2d(leftLine.getStartX(), leftLine.getStartY()));
		sz.setEnde(new Vec2d(leftLine.getEndX(), leftLine.getEndY()));
		cl = new Checkpointlinie();
		cl.setAnfang(new Vec2d(rightLine.getStartX(), rightLine.getStartY()));
		cl.setEnde(new Vec2d(rightLine.getEndX(), rightLine.getEndY()));
		mButton.setOnAction((event) -> {
			try {
				gameView = new GameView((Stage) apGP.getScene().getWindow());
				gameView.setUpMenu();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		apGP.setOnKeyPressed((event) -> {
			try {
				System.out.println(event.getCode());
				switch (event.getCode()) {
				case UP: {
					car.setBeschleunigung(car.getBeschleunigung() + 1);
					break;
				}
				case DOWN: {
					car.setBeschleunigung(car.getBeschleunigung() - 1);
					break;
				}
				case LEFT: {
					car.setRotation(car.getRotation() - 0.25);
					break;
				}
				case RIGHT: {
					car.setRotation(car.getRotation() + 0.25);
					break;
				}
				case R: {
					gameView = new GameView((Stage) apGP.getScene().getWindow());
					gameView.setUpMenu();
					break;
				}
				case O:
					System.out.println("Oh oh");
				default:
					break;
				}
			} catch (IOException e) {

			}
		});
	}

	/**
	 * Updates all needed dependencies every frame
	 *
	 * @param timeDifferenceInSeconds
	 *            the time passed since last frame
	 */
	public void gameLoop() {
		new AnimationTimer() {

			public void handle(long now) {
				double timeDifferenceInSeconds = (now - oldTime) / 1000000000.0;
				oldTime = now;
				updateContinously(timeDifferenceInSeconds);
			}
		}.start();
	}

	public void updateContinously(double t) {
		calcNewPos(car);
		carbox.setCenterX(car.getPos().x);
		carbox.setCenterY(car.getPos().y);
	}

	private void calcNewPos(Car car) {
		if (car.getGeschwindigkeit() > 0.0) {
			car.setGeschwindigkeit((car.getGeschwindigkeit() - car.getLuftwiderstandbeiwert() * car.getStirnflaeche()
					* (0.5 * Kraefte.LUFTDICHTE) * Math.pow(car.getGeschwindigkeit(), 2)
					- 9.81 * Kraefte.ROLLWIDERSTAND_ASPHALT));
		}
		if (car.getGeschwindigkeit() < 0.0) {
			car.setGeschwindigkeit((car.getGeschwindigkeit() + car.getLuftwiderstandbeiwert() * car.getStirnflaeche()
					* (0.5 * Kraefte.LUFTDICHTE) * Math.pow(car.getGeschwindigkeit(), 2)
					+ 9.81 * Kraefte.ROLLWIDERSTAND_ASPHALT));
		}
		if (car.getGeschwindigkeit() < 0.075) {
			car.setGeschwindigkeit(0.0);
		}
		System.out.println(car.getGeschwindigkeit());
		car.setGeschwindigkeit(car.getGeschwindigkeit() + car.getBeschleunigung());
		double newX = car.getPos().x + car.getGeschwindigkeit() * Math.cos(car.getRotation());
		double newY = car.getPos().y + car.getGeschwindigkeit() * Math.sin(car.getRotation());
		car.setPos(new Vec2d(newX, newY));

		if (car.getBeschleunigung() > 0.01) {
			car.setBeschleunigung(car.getBeschleunigung() - 0.2);
		} else if (car.getBeschleunigung() < 0.01) {
			car.setBeschleunigung(car.getBeschleunigung() + 0.2);
		}
	}
}
