package videoteka;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import enumeracije.Aktivan;
import enumeracije.Medijum;
import enumeracije.Pol;
import enumeracije.Status;
import iznajmljivanje.Iznajmljivanje;
import iznajmljivanje.Primerci;
import osobe.Clanovi;
import osobe.Zaposleni;

public class Videoteka {

	private String pib;
	private String naziv;
	private String adresa;

	public static int cenaVHS;
	public static int cenaDVD;
	public static int cenaBRDVD;

	private Zaposleni ulogovaniZaposlen;

	private ArrayList<Zaposleni> zaposleni;
	private ArrayList<Clanovi> clanovi;
	private ArrayList<Filmovi> filmovi;
	private ArrayList<Zanr> zanrovi;
	private ArrayList<Primerci> primerci;
	private ArrayList<Iznajmljivanje> iznajm;
	private ArrayList<String> linije;

	// Konstruktor
	public Videoteka() {
		this.zaposleni = new ArrayList<Zaposleni>();
		this.clanovi = new ArrayList<Clanovi>();
		this.filmovi = new ArrayList<Filmovi>();
		this.zanrovi = new ArrayList<Zanr>();
		this.primerci = new ArrayList<Primerci>();
		this.iznajm = new ArrayList<Iznajmljivanje>();

		ucitajVideoteku();
		ucitajZaposlene();
		ucitajClanove();
		ucitajZanrove();
		ucitajFilmove();
		ucitajPrimerke();
		ucitajIznajmljivanja();

	}

	// Metode:
	private void readFile(String fileName) {
		try {
			File fajl = new File(fileName);
			BufferedReader br = new BufferedReader(new FileReader(fajl));
			String line = null;
			linije = new ArrayList<String>();

			while ((line = br.readLine()) != null) {
				linije.add(line);
			}
			br.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Greska prilikom otvaranja fajla!");
			e.printStackTrace();
		}

	}

	public void ucitajVideoteku() {
		readFile("src/fajlovi/videoteka.txt");

		String[] l = null;
		for (String linija : linije) {
			l = linija.split("\\|");
		}

		this.pib = l[0];
		this.naziv = l[1];
		this.adresa = l[2];
		cenaVHS = Integer.parseInt(l[3]);
		cenaDVD = Integer.parseInt(l[4]);
		cenaBRDVD = Integer.parseInt(l[5]);

	}

	public void ucitajZaposlene() {
		readFile("src/fajlovi/zaposleni.txt");

		for (String linija : linije) {
			String[] l = linija.split("\\|");
			Zaposleni z = new Zaposleni(l[0], l[1], l[2], l[3], Pol.fromSlovo(l[4]), Integer.parseInt(l[5]), l[6], l[7],
					Status.toStatus(Integer.parseInt(l[8])));
			zaposleni.add(z);
		}

	}

	public void ucitajClanove() {
		readFile("src/fajlovi/clanovi.txt");

		for (String linija : linije) {
			String[] l = linija.split("\\|");
			Clanovi c = new Clanovi(l[0], l[1], l[2], l[3], Pol.fromSlovo(l[4]), l[5],
					Aktivan.fromInt(Integer.parseInt(l[6])));
			clanovi.add(c);
		}

	}

	public void ucitajZanrove() {
		readFile("src/fajlovi/zanr.txt");

		for (String linija : this.linije) {
			String[] l = linija.split("\\|");
			Zanr z = new Zanr(l[0], l[1]);
			zanrovi.add(z);
		}
	}

	public void ucitajFilmove() {
		readFile("src/fajlovi/filmovi.txt");

		for (String linija : linije) {
			String[] l = linija.split("\\|");
			for (Zanr zanr : zanrovi) {
				if (zanr.getOznaka().equals(l[3])) {
					Filmovi f = new Filmovi(l[0], l[1], l[2], zanr, l[4], l[5], Integer.parseInt(l[6]));
					filmovi.add(f);
				}

			}
		}
	}

	public void ucitajPrimerke() {
		readFile("src/fajlovi/primerci.txt");

		for (String linija : linije) {
			String[] l = linija.split("\\|");

			for (Filmovi film : filmovi) {
				if (l[1].equals(film.getNaslovSrpski())) {
					Primerci primerak = new Primerci(Integer.parseInt(l[0]), film,
							Medijum.fromBroj(Integer.parseInt(l[2])), Integer.parseInt(l[3]));
					primerci.add(primerak);
				}
			}

		}
	}

	public void ucitajIznajmljivanja() {
		readFile("src/fajlovi/iznajm.txt");

		for (String linija : linije) {
			String[] l = linija.split("\\|");

			Zaposleni zaposleniIzFajla = null;
			Clanovi clanIzFajla = null;
			ArrayList<Primerci> primerciIzFajla = new ArrayList<>();

			for (Zaposleni z : zaposleni) {
				if (l[1].equals(z.getUsername())) {
					zaposleniIzFajla = z;
					break;
				}
			}

			for (Clanovi c : clanovi) {
				if (l[2].equals(c.getBrojClankseKarte())) {
					clanIzFajla = c;
					break;
				}
			}

			String[] brojeviPrimeraka = l[5].split(",");
			for (Primerci p : primerci) {
				for (String b : brojeviPrimeraka) {
					if (Integer.parseInt(b) == p.getId()) {
						Primerci p1;
						p1 = p.clone();
						p1.setKolicina(1);
						primerciIzFajla.add(p1);
					}

				}
			}

			Iznajmljivanje iz = new Iznajmljivanje(Integer.parseInt(l[0]), zaposleniIzFajla, clanIzFajla, l[3], l[4],
					primerciIzFajla, Integer.parseInt(l[6]));

			iznajm.add(iz);
		}
	}

	public void sacuvajIznajmljivanje() {

		try {
			File fajl = new File("src/fajlovi/iznajm.txt");
			String content = "";

			for (Iznajmljivanje iz : iznajm) {
				String contentPrimerci = "";

				for (Primerci p : iz.getPrimerci()) {
					contentPrimerci += p.getId() + ",";
				}

				content += iz.getId() + "|" + iz.getZaposlen().getUsername() + "|" + iz.getClan().getBrojClankseKarte()
						+ "|" + iz.getDatumIznajm() + "|" + iz.getDatumVracanja() + "|" + contentPrimerci + "|"
						+ iz.getCena() + "\n";
			}

			BufferedWriter bf = new BufferedWriter(new FileWriter(fajl));
			bf.write(content);
			bf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sacuvajPrimerke() {

		try {
			File f = new File("src/fajlovi/primerci.txt");
			String content = "";

			for (Primerci p : primerci) {
				content += p.getId() + "|" + p.getFilm().getNaslovSrpski() + "|" + Medijum.toBroj(p.getMedijum()) + "|"
						+ p.getKolicina() + "\n";
			}

			BufferedWriter bf = new BufferedWriter(new FileWriter(f));
			bf.write(content);
			bf.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sacuvajClanove() {
		try {
			File f = new File("src/fajlovi/clanovi.txt");
			String content = "";

			for (Clanovi clan : clanovi) {
				content += clan.getIme() + "|" + clan.getPrezime() + "|" + clan.getJmbg() + "|" + clan.getAdresa() + "|"
						+ Pol.toSlovo(clan.getPol()) + "|" + clan.getBrojClankseKarte() + "|"
						+ Aktivan.toInt(clan.getAktivan()) + "\n";
			}

			BufferedWriter bf = new BufferedWriter(new FileWriter(f));
			bf.write(content);
			bf.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sacuvajZanrove() {

		try {
			File f = new File("src/fajlovi/zanr.txt");
			String content = "";

			for (Zanr z : zanrovi) {
				content += z.getOznaka() + "|" + z.getNaziv() + "\n";
			}

			BufferedWriter bf = new BufferedWriter(new FileWriter(f));
			bf.write(content);
			bf.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void sacuvajFilmove() {
		try {
			File fajl = new File("src/fajlovi/filmovi.txt");
			String content = "";

			for (Filmovi f : filmovi) {
				content += f.getNaslovSrpski() + "|" + f.getNaslovOriginal() + "|" + f.getGodIzdanja() + "|"
						+ f.getZanr().getOznaka() + "|" + f.getImePrezimeRezisera() + "|" + f.getOpis() + "|"
						+ f.getTrajanje() + "\n";
			}

			BufferedWriter bf = new BufferedWriter(new FileWriter(fajl));
			bf.write(content);
			bf.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sacuvajZaposlene() {
		try {
			File f = new File("src/fajlovi/zaposleni.txt");
			String content = "";

			for (Zaposleni z : zaposleni) {
				content += z.getIme() + "|" + z.getPrezime() + "|" + z.getJmbg() + "|" + z.getAdresa() + "|"
						+ Pol.toSlovo(z.getPol()) + "|" + z.getPlata() + "|" + z.getUsername() + "|" + z.getPassword()
						+ "|" + Status.toNum(z.getStatus()) + "\n";

			}

			BufferedWriter bf = new BufferedWriter(new FileWriter(f));
			bf.write(content);
			bf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sacuvajVideoteku() {

		try {
			File f = new File("src/fajlovi/videoteka.txt");
			String content = this.pib + "|" + this.naziv + "|" + this.adresa + "|" + cenaVHS + "|" + cenaDVD + "|"
					+ cenaBRDVD;

			BufferedWriter bf = new BufferedWriter(new FileWriter(f));
			bf.write(content);
			bf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Zaposleni login(String username, String password) {
		for (Zaposleni zaposlen : zaposleni) {
			if (zaposlen.getUsername().equals(username) && zaposlen.getPassword().equals(password)) {
				return zaposlen;
			}
		}

		return null;
	}

	// proverava da li postoji clan:

	public Clanovi pronadjiClana(String brojCK) {
		for (Clanovi clan : clanovi) {
			if (clan.getBrojClankseKarte().equals(brojCK)) {
				return clan;
			}
		}
		return null;
	}

	public Zanr pronadjiZanr(String id) {

		for (Zanr z : zanrovi) {
			if (z.getOznaka().equals(id)) {
				return z;
			}
		}
		return null;

	}

	public Primerci pronadjiPrimerak(int id) {
		for (Primerci p : primerci) {
			if (p.getId() == id) {
				return p;
			}
		}
		return null;
	}

	public Filmovi pronadjiFilm(String nazivSrpski) {
		for (Filmovi f : filmovi) {
			if (f.getNaslovSrpski().equals(nazivSrpski)) {
				return f;
			}
		}
		return null;
	}

	public Zaposleni pronadjiZaposlenog(String username) {
		for (Zaposleni z : zaposleni) {
			if (z.getUsername().equals(username)) {
				return z;
			}
		}
		return null;
	}

	public Iznajmljivanje pronadjiIznajmljivanje(int id) {
		for (Iznajmljivanje iz : iznajm) {
			if (iz.getId() == id) {
				return iz;
			}
		}
		return null;
	}

	public int odrediIDIznajmljivanja() {
		return iznajm.size() + 1;

	}

	public String odrediBrojClanskeKarte() {

		int broj = Integer.parseInt(clanovi.get(clanovi.size() - 1).getBrojClankseKarte());

		return String.valueOf(broj + 1);
	}

	public String odrediOznakuZanra() {
		int broj = Integer.parseInt(zanrovi.get(zanrovi.size() - 1).getOznaka());

		return String.valueOf(broj + 1);
	}

	public int odrediIdPrimerka() {
		int id = primerci.get(primerci.size() - 1).getId();
		return id + 1;

	}

	public static boolean proveriUnos(String unos) {
		Character[] zabranjeniKarakteri = new Character[] { '|', '\\' };
		ArrayList<Character> zK = new ArrayList<Character>();

		for (Character ch : zabranjeniKarakteri) {
			zK.add(ch);
		}

		// Ide kroz svaki karakter u unosi i trazi
		// dal se on nalazi u 'zabranjeniKarateri'
		for (char c : unos.toCharArray()) {
			if (zK.contains(c)) {
				return false;
			}

		}

		return true;

	}

	// Getters & Setters...
	public ArrayList<Zaposleni> getZaposleni() {
		return zaposleni;
	}

	public void setZaposleni(ArrayList<Zaposleni> zaposleni) {
		this.zaposleni = zaposleni;
	}

	public ArrayList<Clanovi> getClanovi() {
		return clanovi;
	}

	public void setClanovi(ArrayList<Clanovi> clanovi) {
		this.clanovi = clanovi;
	}

	public ArrayList<Filmovi> getFilmovi() {
		return filmovi;
	}

	public String getPib() {
		return pib;
	}

	public void setPib(String pib) {
		this.pib = pib;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public ArrayList<Zanr> getZanrovi() {
		return zanrovi;
	}

	public ArrayList<Primerci> getPrimerci() {
		return primerci;
	}

	public void setPrimerci(ArrayList<Primerci> primerci) {
		this.primerci = primerci;
	}

	public ArrayList<Iznajmljivanje> getIznajm() {
		return iznajm;
	}

	public Zaposleni getUlogovaniZaposlen() {
		return ulogovaniZaposlen;
	}

	public void setUlogovaniZaposlen(Zaposleni ulogovaniZaposlen) {
		this.ulogovaniZaposlen = ulogovaniZaposlen;
	}

	// toString

	@Override
	public String toString() {
		return "PIB: " + pib + "\n" + "Naziv: " + naziv + "\n" + "Adresa: " + adresa;

	}

	public static int cenaVHS() {
		return cenaVHS;
	}

	public static int cenaDVD() {
		return cenaDVD;
	}

	public static int cenaBRDVD() {
		return cenaBRDVD;
	}

	// public int getCenaVHS() {
	// return cenaVHS;
	// }
	//
	// public void setCenaVHS(int cenaVHS) {
	// this.cenaVHS = cenaVHS;
	// }
	//
	// public int getCenaDVD() {
	// return cenaDVD;
	// }
	//
	// public void setCenaDVD(int cenaDVD) {
	// this.cenaDVD = cenaDVD;
	// }
	//
	// public int getCenaBRDVD() {
	// return cenaBRDVD;
	// }
	//
	// public void setCenaBRDVD(int cenaBRDVD) {
	// this.cenaBRDVD = cenaBRDVD;
	// }
}
