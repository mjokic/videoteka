package videoteka;

public class Filmovi {

	private String naslovSrpski;
	private String naslovOriginal;
	private String godIzdanja;
	private Zanr zanr;
	private String imePrezimeRezisera;
	private String opis;
	private int trajanje;

	// Konstruktor
	public Filmovi(String naslovSrpski, String naslovOriginal, String godIzdanja, Zanr zanr, String imePrezimeRezisera,
			String opis, int trajanje) {
		this.naslovSrpski = naslovSrpski;
		this.naslovOriginal = naslovOriginal;
		this.godIzdanja = godIzdanja;
		this.zanr = zanr;
		this.imePrezimeRezisera = imePrezimeRezisera;
		this.opis = opis;
		this.trajanje = trajanje;
	}

	// Getters & setters
	public String getNaslovSrpski() {
		return naslovSrpski;
	}

	public void setNaslovSrpski(String naslovSrpski) {
		this.naslovSrpski = naslovSrpski;
	}

	public String getNaslovOriginal() {
		return naslovOriginal;
	}

	public void setNaslovOriginal(String naslovOriginal) {
		this.naslovOriginal = naslovOriginal;
	}

	public String getGodIzdanja() {
		return godIzdanja;
	}

	public void setGodIzdanja(String godIzdanja) {
		this.godIzdanja = godIzdanja;
	}

	public Zanr getZanr() {
		return zanr;
	}

	public void setZanr(Zanr zanr) {
		this.zanr = zanr;
	}

	public String getImePrezimeRezisera() {
		return imePrezimeRezisera;
	}

	public void setImePrezimeRezisera(String imePrezimeRezisera) {
		this.imePrezimeRezisera = imePrezimeRezisera;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public int getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(int trajanje) {
		this.trajanje = trajanje;
	}

	// toString
	@Override
	public String toString() {
		return naslovSrpski+" ("+naslovOriginal+")";
	}
	
	

}
