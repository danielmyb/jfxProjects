package model;

public class Rennstrecke {

	private double aussenradiusX; 
	private double aussenradiusY;
	private double innenradiusX;
	private double innenradiusY;
	
	
	public Rennstrecke() {
		
	}
	
	public Rennstrecke(double aX, double aY, double iX, double iY) {
		this.aussenradiusX = aX;
		this.aussenradiusY = aY;
		this.innenradiusX = iX;
		this.innenradiusX = iY;
	}

	public double getAussenradiusX() {
		return aussenradiusX;
	}


	public void setAussenradiusX(double aussenradiusX) {
		this.aussenradiusX = aussenradiusX;
	}


	public double getAussenradiusY() {
		return aussenradiusY;
	}


	public void setAussenradiusY(double aussenradiusY) {
		this.aussenradiusY = aussenradiusY;
	}


	public double getInnenradiusX() {
		return innenradiusX;
	}


	public void setInnenradiusX(double innenradiusX) {
		this.innenradiusX = innenradiusX;
	}


	public double getInnenradiusY() {
		return innenradiusY;
	}


	public void setInnenradiusY(double innenradiusY) {
		this.innenradiusY = innenradiusY;
	}
	
}
