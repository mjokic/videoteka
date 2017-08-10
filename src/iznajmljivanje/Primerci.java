package iznajmljivanje;

import enumeracije.Medijum;
import videoteka.Filmovi;

public class Primerci implements Cloneable{

	// jedinstvena oznaka, film kojem primerak
	// pripada, medijum na kojem je primerak, kao i broj medijuma na koliko se
	// primerak nalazi (ukoliko
	// kopija filma dolazi na više kaseta ili diskova).

	private int id;
	private Filmovi film;
	private Medijum medijum;
	private int kolicina;

	public Primerci(int id, Filmovi film, Medijum medijum, int kolicina) {
		this.id = id;
		this.film = film;
		this.medijum = medijum;
		this.kolicina = kolicina;
	}
	
	public Primerci clone() {
        try {
			return (Primerci) super.clone();
			
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Filmovi getFilm() {
		return film;
	}

	public void setFilm(Filmovi film) {
		this.film = film;
	}

	public Medijum getMedijum() {
		return medijum;
	}

	public void setMedijum(Medijum medijum) {
		this.medijum = medijum;
	}

	public int getKolicina() {
		return kolicina;
	}

	public void setKolicina(int kolicina) {
		this.kolicina = kolicina;
	}

	@Override
	public String toString() {
		return film + " - " + medijum + " (" + kolicina + ")";
	}

}
