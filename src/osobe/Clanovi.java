package osobe;

import enumeracije.Aktivan;
import enumeracije.Pol;

public class Clanovi extends Osobe {

	private String brojClankseKarte;
	private Aktivan aktivan;

	// konstruktor
	public Clanovi(String ime, String prezime, String jmbg, String adresa, Pol pol, String brojClankseKarte,
			Aktivan aktivan) {
		super(ime, prezime, jmbg, adresa, pol);
		this.brojClankseKarte = brojClankseKarte;
		this.aktivan = aktivan;
	}

	public String getBrojClankseKarte() {
		return brojClankseKarte;
	}

	public void setBrojClankseKarte(String brojClankseKarte) {
		this.brojClankseKarte = brojClankseKarte;
	}

	public Aktivan getAktivan() {
		return aktivan;
	}

	public void setAktivan(Aktivan aktivan) {
		this.aktivan = aktivan;
	}

	@Override
	public String toString() {
		return "Clanovi [brojClankseKarte=" + brojClankseKarte + ", aktivan=" + aktivan + ", Ime=" + getIme()
				+ ", Prezime=" + getPrezime() + ", Jmbg=" + getJmbg() + ", Adresa=" + getAdresa()
				+ ", Pol=" + getPol() + "]";
	}

}
