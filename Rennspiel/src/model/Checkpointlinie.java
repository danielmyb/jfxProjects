package model;

import com.sun.javafx.geom.Vec2d;

public class Checkpointlinie {

	private Vec2d anfang;
	private Vec2d ende;

	public Checkpointlinie() {

	}

	public Checkpointlinie(Vec2d a, Vec2d e) {
		this.anfang = a;
		this.ende = e;
	}

	public Vec2d getAnfang() {
		return anfang;
	}

	public void setAnfang(Vec2d anfang) {
		this.anfang = anfang;
	}

	public Vec2d getEnde() {
		return ende;
	}

	public void setEnde(Vec2d ende) {
		this.ende = ende;
	}

}
