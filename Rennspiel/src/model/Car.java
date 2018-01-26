package model;

import com.sun.javafx.geom.Vec2d;

/**
 * Class car represents the race-car in the race game.
 */
public class Car {

	private double laenge;
	private double breite;
	private double gewicht;
	private Vec2d pos;
	private double rotation;
	private double stirnflaeche;
	private double rotationsradius;
	private boolean totalschaden;
	private double luftwiderstandbeiwert;
	private double geschwindigkeit;
	private double beschleunigung;

	public Car() {
		this.laenge = 4.255;
		this.breite = 2.027;
		this.gewicht = 1500;
		this.rotation = 270;
		this.stirnflaeche = 2.19;
		this.rotationsradius = 0.0;
		this.totalschaden = false;
		this.luftwiderstandbeiwert = 0.28;
		this.geschwindigkeit = 0.0;
		this.beschleunigung = 0.0;
	}

	public Car(double l, double b, double g, Vec2d p, double r, double s, double rr, boolean t, double lwb, double ge) {
		this.laenge = l;
		this.breite = b;
		this.gewicht = g;
		this.pos = p;
		this.rotation = r;
		this.stirnflaeche = s;
		this.rotationsradius = rr;
		this.totalschaden = t;
		this.luftwiderstandbeiwert = lwb;
		this.geschwindigkeit = ge;
	}

	public double getLaenge() {
		return laenge;
	}

	public void setLaenge(double laenge) {
		this.laenge = laenge;
	}

	public double getBreite() {
		return breite;
	}

	public void setBreite(double breite) {
		this.breite = breite;
	}

	public double getGewicht() {
		return gewicht;
	}

	public void setGewicht(double gewicht) {
		this.gewicht = gewicht;
	}

	public Vec2d getPos() {
		return pos;
	}

	public void setPos(Vec2d pos) {
		this.pos = pos;
	}

	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	public double getStirnflaeche() {
		return stirnflaeche;
	}

	public void setStirnflaeche(double stirnflaeche) {
		this.stirnflaeche = stirnflaeche;
	}

	public double getRotationsradius() {
		return rotationsradius;
	}

	public void setRotationsradius(double rotationsradius) {
		this.rotationsradius = rotationsradius;
	}

	public boolean isTotalschaden() {
		return totalschaden;
	}

	public void setTotalschaden(boolean totalschaden) {
		this.totalschaden = totalschaden;
	}

	public double getLuftwiderstandbeiwert() {
		return luftwiderstandbeiwert;
	}

	public void setLuftwiderstandbeiwert(double luftwiderstandbeiwert) {
		this.luftwiderstandbeiwert = luftwiderstandbeiwert;
	}

	public double getGeschwindigkeit() {
		return geschwindigkeit;
	}

	public void setGeschwindigkeit(double geschwindigkeit) {
		this.geschwindigkeit = geschwindigkeit;
	}

	public double getBeschleunigung() {
		return beschleunigung;
	}

	public void setBeschleunigung(double b) {
		this.beschleunigung = b;
	}
}
