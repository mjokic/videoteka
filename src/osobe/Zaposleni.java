package osobe;

import enumeracije.Pol;
import enumeracije.Status;

public class Zaposleni extends Osobe {

	private int plata;
	private String username;
	private String password;
	private Status status;

	// konstruktor
	public Zaposleni(String ime, String prezime, String jmbg, String adresa, Pol pol, int plata, String username,
			String password, Status status) {
		super(ime, prezime, jmbg, adresa, pol);
		this.plata = plata;
		this.username = username;
		this.password = password;
		this.status = status;
	}

	public int getPlata() {
		return plata;
	}

	public void setPlata(int plata) {
		this.plata = plata;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Zaposleni [plata=" + plata + ", username=" + username + ", password=" + password + ", status=" + status
				+ ", getIme()=" + getIme() + ", getPrezime()=" + getPrezime() + ", getJmbg()=" + getJmbg()
				+ ", getAdresa()=" + getAdresa() + ", getPol()=" + getPol() + "]";
	}

}
