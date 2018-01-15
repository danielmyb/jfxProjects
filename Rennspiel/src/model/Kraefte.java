package model;

public class Kraefte {

	private double groeﬂe;
	private boolean inFahrtrichtung;
	private double luftdichte;
	private double beschleunigung;
	private double rollwiderstand;
	
	public Kraefte() {
		
	}

	public double getGroeﬂe() {
		return groeﬂe;
	}

	public void setGroeﬂe(double groeﬂe) {
		this.groeﬂe = groeﬂe;
	}

	public boolean isInFahrtrichtung() {
		return inFahrtrichtung;
	}

	public void setInFahrtrichtung(boolean inFahrtrichtung) {
		this.inFahrtrichtung = inFahrtrichtung;
	}

	public double getLuftdichte() {
		return luftdichte;
	}

	public void setLuftdichte(double luftdichte) {
		this.luftdichte = luftdichte;
	}

	public double getBeschleunigung() {
		return beschleunigung;
	}

	public void setBeschleunigung(double beschleunigung) {
		this.beschleunigung = beschleunigung;
	}

	public double getRollwiderstand() {
		return rollwiderstand;
	}

	public void setRollwiderstand(double rollwiderstand) {
		this.rollwiderstand = rollwiderstand;
	}
	
}
