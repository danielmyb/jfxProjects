package controller;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;

import com.sun.javafx.geom.Vec2d;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Car;
import model.Checkpointlinie;
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
	private AnchorPane ap;
	@FXML
	private Line leftLine;
	@FXML
	private Line rightLine;
	@FXML
	private Rectangle carbox;
	@FXML
	private TextField timerField;
	@FXML
	private TextField checkPointTime;
	@FXML
	private Ellipse bigEli;
	@FXML
	private Ellipse smlEli;

	private GameView gameView;

	private Car car;
	private StartZiellinie sz;
	private Checkpointlinie cl;

	private boolean raceStarted;

	private long oldTime;
	private long now;

	private double rangeX = 0.0;
	private double rangeY = 0.0;

	private LinkedList<Circle> obstacles = new LinkedList<Circle>();

	private long raceTime;
	private long curTime;
	private long pauseTime;

	private boolean checkpointPassed = false;
	private boolean raceFinished = false;

	public static long finishTime;

	private boolean pause;

	public GamePaneController() {

	}

	@FXML
	private void initialize() {
		pauseTime = 0;
		pause = false;
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
			gameView = new GameView((Stage) ap.getScene().getWindow());
			gameView.setUpMenu();
			GameController.obsCount = 0;
		});
		ap.setOnKeyPressed((event) -> {
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
				if (car.getGeschwindigkeit() != 0) {
					car.setRotation(car.getRotation() - 5);
				}
				break;
			}
			case RIGHT: {
				if (car.getGeschwindigkeit() != 0) {
					car.setRotation(car.getRotation() + 5);
				}
				break;
			}
			case R: {
				gameView = new GameView((Stage) ap.getScene().getWindow());
				gameView.setUpGameWindow();
				break;
			}
			case P: {
				if (!pause) {
					pauseTime += now;
				} else {
					pauseTime = now - pauseTime;
				}
				pause = !pause;
			}
			case O: {
				System.out.println("Oh oh");
			}
			default:
				break;
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
				setNow(now);
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

	private void setNow(long now) {
		this.now = now;
	}

	private void insertObstacles() {
		for (int i = 0; i < GameController.obsCount; i++) {
			Circle c = new Circle();
			c.setLayoutX(Math.random() * 1300);
			c.setLayoutY(Math.random() * 800);
			c.setRadius(Math.random() * 20);
			c.setId("obs" + i);
			obstacles.add(c);
			ap.getChildren().add(c);
			System.out.println("Hindernis " + i + " bei X:" + c.getLayoutX() + " || Y:" + c.getLayoutY() + " || R:"
					+ c.getRadius());
		}

	}

	private void calcTimer(long now) {
		if (!raceFinished) {
			if (!pause) {
				curTime = (now - raceTime - pauseTime) / 1000000000;
				timerField.setText((curTime / 60) % 60 + " : " + curTime % 60);
			}
		}
	}

	private void checkStart() {
		if (Double.compare(carbox.getLayoutY() + carbox.getY(), leftLine.getLayoutY()) < 0) {
			raceStarted = true;
		}
	}

	public void updateContinously(double t) {
		if (!pause) {
			calcNewPos(car);
			carbox.setX(car.getPos().x);
			carbox.setY(car.getPos().y);
			carbox.setRotate(car.getRotation());
		}
	}

	private void calcNewPos(Car car) {
		//System.out.println("Pre Ges  " + car.getGeschwindigkeit());
		//System.out.println("Pre Bes  " + car.getBeschleunigung());
		
		calcForces();
		// v += (a_Motor [-+] ((c_R * g) + (c_W * A * 1/2 * p * v² / m))) * t
		calcDR();

		//System.out.println("Post Ges " + car.getGeschwindigkeit());
		//System.out.println("Post Bes " + car.getBeschleunigung());

		double newX = car.getPos().x + car.getGeschwindigkeit() * Math.cos(Math.toRadians(car.getRotation()));
		double newY = car.getPos().y + car.getGeschwindigkeit() * Math.sin(Math.toRadians(car.getRotation()));

		testOuterBounds();

		testForObstacles();

		testCheckpoint();

		testFinish();
		
		if(car.getGeschwindigkeit() > 0.0) {
			URL url = getClass().getResource("/resources/motorSound.wav");
			SoundManager.playSound(url);
		} else {
			SoundManager.stopSound();
		}

		car.setPos(new Vec2d(newX, newY));

	}

	private void calcForces() {
		double widerstand = Kraefte.ROLLWIDERSTAND_ERDWEG;
		if (carbox.getBoundsInParent().intersects(bigEli.getBoundsInParent())
				&& !carbox.getBoundsInParent().intersects(smlEli.getBoundsInParent())) {
			widerstand = Kraefte.ROLLWIDERSTAND_ASPHALT;
		}
		car.setGeschwindigkeit(car.getGeschwindigkeit() + car.getBeschleunigung());
			if (Double.compare(car.getGeschwindigkeit(), 0.0) > 0.0) {
				car.setGeschwindigkeit(car.getBeschleunigung()
						- ((car.getLuftwiderstandbeiwert() * car.getStirnflaeche() * (0.5 * Kraefte.LUFTDICHTE)
								* Math.pow(car.getGeschwindigkeit(), 2)) / car.getGewicht() + 9.81 * widerstand));
			}
			if (Double.compare(car.getGeschwindigkeit(), 0.0) < 0.0) {
				car.setGeschwindigkeit(car.getBeschleunigung()
						+ ((car.getLuftwiderstandbeiwert() * car.getStirnflaeche() * (0.5 * Kraefte.LUFTDICHTE)
								* Math.pow(car.getGeschwindigkeit(), 2)) / car.getGewicht() + 9.81 * widerstand));
			}
	}

	private void calcDR() {
		if (Double.compare(Math.abs(car.getGeschwindigkeit()), 0.05) <= 0.0) {
			car.setGeschwindigkeit(0.0);
		} else if (car.getGeschwindigkeit() > 4.0) {
			car.setGeschwindigkeit(4.0);
		}
		if (Math.abs(car.getBeschleunigung()) > 0.005)

		{
			if (Double.compare(car.getBeschleunigung(), 0.0) > 0.0) {
				car.setBeschleunigung(car.getBeschleunigung() - 0.005);
			} else {
				car.setBeschleunigung(car.getBeschleunigung() + 0.005);
			}
		} else {
			car.setBeschleunigung(0.0);
			car.setGeschwindigkeit(0.0);
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
				if (car.getGeschwindigkeit() > 3.0) {
					gameView = new GameView((Stage) ap.getScene().getWindow());
					gameView.setUpGameOver();
				}
				if (car.getGeschwindigkeit() != 0.0) {
					System.out.println(car.getGeschwindigkeit());
				}
				car.setBeschleunigung(0.0);
				car.setGeschwindigkeit(0.0);

			}

		}
	}

	private void testCheckpoint() {
		if (carbox.getBoundsInParent().intersects(rightLine.getBoundsInParent())) {
			checkpointPassed = true;
			long cTime = curTime;
			checkPointTime.setText(((cTime / 60) % 60) + " : " + (cTime % 60));
		}
	}

	private void testFinish() {
		if (checkpointPassed) {
			if (carbox.getBoundsInParent().intersects(leftLine.getBoundsInParent())) {
				raceFinished = true;
				finishTime = curTime;

				gameView = new GameView((Stage) ap.getScene().getWindow());
				gameView.setUpWinWindow();

			}
		}
	}
}
