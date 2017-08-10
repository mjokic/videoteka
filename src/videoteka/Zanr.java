package videoteka;

public class Zanr {

	private String oznaka;
	private String naziv;

	// Konstruktor
	public Zanr(String oznaka, String naziv) {
		this.oznaka = oznaka;
		this.naziv = naziv;
	}

	// Getters & Setters
	public String getOznaka() {
		return oznaka;
	}

	public void setOznaka(String oznaka) {
		this.oznaka = oznaka;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	// toString
	@Override
	public String toString() {
		return naziv;
	}

}
