package model;

import com.sun.javafx.geom.Vec2d;

public class Hindernis {

	private Vec2d position;
	private double radius;

	public Hindernis() {

	}

	public Hindernis(Vec2d p, double r) {
		this.position = p;
		this.radius = r;
	}

	public Vec2d getPosition() {
		return position;
	}

	public void setPosition(Vec2d position) {
		this.position = position;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

}
