package model;

import com.sun.javafx.geom.Vec2d;

public class StartZiellinie {

	private Vec2d Anfang;
	private Vec2d Ende;
	
	public StartZiellinie() {
		
	}
	
	public StartZiellinie(Vec2d a, Vec2d e) {
		this.Anfang = a;
		this.Ende = e;
	}

	public Vec2d getAnfang() {
		return Anfang;
	}

	public void setAnfang(Vec2d anfang) {
		Anfang = anfang;
	}

	public Vec2d getEnde() {
		return Ende;
	}

	public void setEnde(Vec2d ende) {
		Ende = ende;
	}
	
}
