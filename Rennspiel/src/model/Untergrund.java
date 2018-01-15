package model;

public class Untergrund {

	private double rollwiderstandskoeffizient;
	private boolean typ;

	public Untergrund() {

	}

	public Untergrund(double r, boolean t) {
		this.rollwiderstandskoeffizient = r;
		this.typ = t;
	}

	public double getRollwiderstandskoeffizient() {
		return rollwiderstandskoeffizient;
	}

	public void setRollwiderstandskoeffizient(double rollwiderstandskoeffizient) {
		this.rollwiderstandskoeffizient = rollwiderstandskoeffizient;
	}

	public boolean isTyp() {
		return typ;
	}

	public void setTyp(boolean typ) {
		this.typ = typ;
	}

}
