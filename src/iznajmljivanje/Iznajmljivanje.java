package iznajmljivanje;

import java.util.ArrayList;

import osobe.Clanovi;
import osobe.Zaposleni;

public class Iznajmljivanje {

	// jedinstvena oznaka, zaposleni koji je obradio iznajmljivanje, član koji
	// je iznajmio film, datum iznajmljivanja, datum vraćanja (omogućiti da može
	// da se ne unese) i primerak filma koji je iznajmljen. Cena iznajmljivanja
	// računa se tako što se broj dana iznajmljivanja (ukoliko je unesen datum
	// vraćanja) pomnoži sa dnevnom cenom medijuma primeraka.

	private int id;
	private Zaposleni zaposlen;
	private Clanovi clan;
	private String datumIznajm;
	private String datumVracanja;
	// private ArrayList<Integer> primerciID = new ArrayList<>();
	private ArrayList<Primerci> primerci;
	private int cena;

	// Konstruktor...
	public Iznajmljivanje(int id, Zaposleni zaposlen, Clanovi clan, String datumIznajm, String datumVracanja,
			ArrayList<Primerci> primerci, int cena) {

		this.id = id;
		this.zaposlen = zaposlen;
		this.clan = clan;
		this.datumIznajm = datumIznajm;
		this.datumVracanja = datumVracanja;
		this.primerci = primerci;
		this.cena = cena;
	}

	// Getters & Setters...
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Zaposleni getZaposlen() {
		return zaposlen;
	}

	public void setZaposlen(Zaposleni zaposlen) {
		this.zaposlen = zaposlen;
	}

	public Clanovi getClan() {
		return clan;
	}

	public void setClan(Clanovi clan) {
		this.clan = clan;
	}

	public String getDatumIznajm() {
		return datumIznajm;
	}

	public void setDatumIznajm(String datumIznajm) {
		this.datumIznajm = datumIznajm;
	}

	public String getDatumVracanja() {
		return datumVracanja;
	}

	public void setDatumVracanja(String datumVracanja) {
		this.datumVracanja = datumVracanja;
	}

	public ArrayList<Primerci> getPrimerci() {
		return primerci;
	}

	public void setPrimerci(ArrayList<Primerci> primerci) {
		this.primerci = primerci;
	}

	public int getCena() {
		return cena;
	}

	public void setCena(int cena) {
		this.cena = cena;
	}

	// toString...
	@Override
	public String toString() {
		return "Iznajmljivanje [id=" + id + ", zaposlen=" + zaposlen + ", clan=" + clan + ", datumIznajm=" + datumIznajm
				+ ", datumVracanja=" + datumVracanja + ", primerci=" + primerci + ", cena=" + cena + "]";
	}

}
