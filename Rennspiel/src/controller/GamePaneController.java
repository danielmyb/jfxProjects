package controller;

import java.io.IOException;
import java.util.LinkedList;

import com.sun.javafx.geom.Vec2d;

import javafx.animation.AnimationTimer;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Car;
import model.Checkpointlinie;
import model.GameModel;
import model.Hindernis;
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
	private Rectangle carbox;
	@FXML
	private TextField timerField;
	private GameModel gameModel;
	private GameView gameView;

	private Car car;
	private StartZiellinie sz;
	private Checkpointlinie cl;

	private boolean raceStarted;

	private long oldTime;

	private double rangeX = 0.0;
	private double rangeY = 0.0;

	private LinkedList<Circle> obstacles = new LinkedList<Circle>();

	private long raceTime;

	public GamePaneController() {

	}

	@FXML
	private void initialize() {
		raceStarted = false;
		gameLoop();
		car = new Car();
		car.setPos(new Vec2d(carbox.getX(), carbox.getY()));
		sz = new StartZiellinie();
		sz.setAnfang(new Vec2d(leftLine.getStartX(), leftLine.getStartY()));
		sz.setEnde(new Vec2d(leftLine.getEndX(), leftLine.getEndY()));
		cl = new Checkpointlinie();
		cl.setAnfang(new Vec2d(rightLine.getStartX(), rightLine.getStartY()));
		cl.setEnde(new Vec2d(rightLine.getEndX(), rightLine.getEndY()));
		Paint paint = Color.color(Math.random(), Math.random(), Math.random());
		carbox.setFill(paint);
		insertObstacles();
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
				switch (event.getCode()) {
				case UP: {
					if (car.getBeschleunigung() < 4) {
						car.setBeschleunigung(car.getBeschleunigung() + 0.2);
					}
					break;
				}
				case DOWN: {
					if (car.getBeschleunigung() > -4) {
						car.setBeschleunigung(car.getBeschleunigung() - 0.2);
					}
					break;
				}
				case LEFT: {
					if (car.getBeschleunigung() != 0) {
						car.setRotation(car.getRotation() - 5);
					}
					break;
				}
				case RIGHT: {
					if (car.getBeschleunigung() != 0) {
						car.setRotation(car.getRotation() + 5);
					}
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

		carbox.setOnMousePressed((event) -> {
			carbox.setFill(Color.color(Math.random(), Math.random(), Math.random()));
			rangeX = event.getScreenX();
			rangeY = event.getScreenY();
		});
		carbox.setOnMouseReleased((event) -> {
			double mX = Math.abs(rangeX - event.getScreenX());
			double mY = Math.abs(rangeY - event.getScreenY());
			car.setBeschleunigung(car.getBeschleunigung() + (mX + mY) / 5.0);
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

				if (raceStarted) {
					calcTimer(now);
				} else {
					checkStart();
					raceTime = now;
				}
				updateContinously(timeDifferenceInSeconds);
			}
		}.start();
	}

	private void insertObstacles() {
		for (int i = 0; i < 10; i++) {
			Circle c = new Circle();
			c.setLayoutX(Math.random() * 1300);
			c.setLayoutY(Math.random() * 800);
			c.setRadius(Math.random() * 20);
			c.setId("obs" + i);
			obstacles.add(c);
			apGP.getChildren().add(c);
			System.out.println("Hindernis " + i + " bei X:" + c.getLayoutX() + " || Y:" + c.getLayoutY() + " || R:"
					+ c.getRadius());
		}

	}

	private void calcTimer(long now) {
		long curTime = (now - raceTime) / 1000000000;
		timerField.setText((curTime / 60) % 60 + " : " + curTime % 60);
	}

	private void checkStart() {
		if (Double.compare(carbox.getLayoutY() + carbox.getY(), leftLine.getLayoutY()) < 0) {
			raceStarted = true;
		}
	}

	public void updateContinously(double t) {
		calcNewPos(car);
		carbox.setX(car.getPos().x);
		carbox.setY(car.getPos().y);
		carbox.setRotate(car.getRotation());
	}

	private void calcNewPos(Car car) {

		calcForces();
		// v += (a_Motor [-+] ((c_R * g) + (c_W * A * 1/2 * p * v² / m))) * t

		if (car.getGeschwindigkeit() < 0.075) {
			car.setGeschwindigkeit(0.0);
		}
		car.setGeschwindigkeit(car.getGeschwindigkeit() + car.getBeschleunigung());
		double newX = car.getPos().x + car.getGeschwindigkeit() * Math.cos(Math.toRadians(car.getRotation()));
		double newY = car.getPos().y + car.getGeschwindigkeit() * Math.sin(Math.toRadians(car.getRotation()));

		testOuterBounds();
		testForObstacles();
		car.setPos(new Vec2d(newX, newY));

		if (Math.abs(car.getBeschleunigung()) > 0.01) {
			if (car.getBeschleunigung() > 0) {
				car.setBeschleunigung(car.getBeschleunigung() - 0.01);
			} else {
				car.setBeschleunigung(car.getBeschleunigung() + 0.01);
			}
		} else {
			car.setBeschleunigung(0.0);
		}
	}

	private void calcForces() {
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
	}

	private void testOuterBounds() {
		if ((carbox.getY() + carbox.getLayoutY()) < 0.0) {
			car.setBeschleunigung(0.0);
			car.setGeschwindigkeit(0.0);
			car.setPos(new Vec2d(car.getPos().x, 800.0));
		}
		if ((carbox.getY() + carbox.getLayoutY()) > 800.0) {
			car.setBeschleunigung(0.0);
			car.setGeschwindigkeit(0.0);
			car.setPos(new Vec2d(car.getPos().x, 800.0));
		}
		if ((carbox.getX() + carbox.getLayoutX()) < 0.0) {
			car.setBeschleunigung(0.0);
			car.setGeschwindigkeit(0.0);
			car.setPos(new Vec2d(0.0, car.getPos().y));
		}
		if ((carbox.getX() + carbox.getLayoutX()) > 1260.0) {
			car.setBeschleunigung(0.0);
			car.setGeschwindigkeit(0.0);
			car.setPos(new Vec2d(1260.0, car.getPos().y));
		}
	}

	private void testForObstacles() {
		for (Circle c : obstacles) {
			if (carbox.getBoundsInParent().intersects(c.getBoundsInParent())) {
				car.setBeschleunigung(0.0);
				car.setGeschwindigkeit(0.0);
				System.out.println("BOOM");
			}

		}
	}
}
