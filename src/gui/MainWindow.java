package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import enumeracije.Aktivan;
import enumeracije.Medijum;
import enumeracije.Status;
import gui.dodavanjeIizmena.VideotekaIzmena;
import gui.prikaz.ClanoviPrikaz;
import gui.prikaz.FilmoviPrikaz;
import gui.prikaz.IznajmljivanjaPrikaz;
import gui.prikaz.PrimerciPrikaz;
import gui.prikaz.ZanroviPrikaz;
import gui.prikaz.ZaposleniPrikaz;
import iznajmljivanje.Iznajmljivanje;
import iznajmljivanje.Primerci;
import net.miginfocom.swing.MigLayout;
import osobe.Clanovi;
import osobe.Zaposleni;
import videoteka.Filmovi;
import videoteka.Videoteka;

public class MainWindow extends JFrame {

	private boolean novoIznajm;
	private Iznajmljivanje staroIznajm;

	private Zaposleni zaposlen;
	private Clanovi clan;
	private Videoteka videoteka;
	// Meniji:
	private JMenuBar menuBar;

	private JMenu fileMenu;
	private JMenuItem izmeniPodatke;
	private JMenuItem logoutItem;
	private JMenuItem aboutItem;

	private JMenu clanoviMenu;
	private JMenuItem prikazClanovaItem;

	private JMenu filmoviMenu;
	private JMenuItem prikazFilmovaItem;
	private JMenuItem prikazZanrovaItem;
	private JMenuItem prikazPrimerakaItem;

	private JMenu zaposleniMenu;
	private JMenuItem prikazZaposlenihItem;

	// Labele i buttoni
	private JLabel lbBrojClanskeKarte;
	private JTextField txtBrojClanskeKarte;
	private JButton btnProveri;
	private JLabel lbKorpa;
	private JList<Primerci> listboxKorpa;
	private JLabel lbFilmovi;
	private JList<Primerci> listboxPrimerciFilmova;
	private JButton btnRemove;
	private JButton btnAdd;
	private JButton btnInfo;
	private JButton btnGotovo;
	private JLabel lbUkupnaCena;
	private JLabel lbCena;
	private JButton btnPregled;
	private JButton btnRefresh;

	// Modeli za prikaz filmova u korpa i filmovi listbox
	private DefaultListModel<Primerci> modelPrimerciFilmovi;
	private DefaultListModel<Primerci> modelKorpa;

	// Konstruktor
	public MainWindow(Zaposleni zaposlen, Videoteka videoteka) {
		this.zaposlen = zaposlen;
		this.videoteka = videoteka;
		this.videoteka.setUlogovaniZaposlen(this.zaposlen);
		setTitle("Welcome - " + this.zaposlen.getIme());
		setSize(500, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		initGUI();
		initActions();

		pack();

	}

	public void initGUI() {
		initMenu();

		setLayout(new MigLayout("wrap 2", "[]70[]", "20[]20[][][]20[]"));

		lbBrojClanskeKarte = new JLabel("Broj Clanske Karte:");
		txtBrojClanskeKarte = new JTextField(10);
		btnProveri = new JButton("Proveri");
		lbKorpa = new JLabel("Korpa:");
		listboxKorpa = new JList<>();
		lbFilmovi = new JLabel("Filmovi:");
		listboxPrimerciFilmova = new JList<>();
		ImageIcon removeImage = new ImageIcon(getClass().getResource("/slike/remove.gif"));
		btnRemove = new JButton(removeImage);
		ImageIcon addImage = new ImageIcon(getClass().getResource("/slike/leftArrow.gif"));
		btnAdd = new JButton(addImage);
		ImageIcon infoImage = new ImageIcon(getClass().getResource("/slike/info.gif"));
		btnInfo = new JButton(infoImage);
		btnGotovo = new JButton("Gotovo");
		lbUkupnaCena = new JLabel("Cena:");
		lbCena = new JLabel("0");
		btnPregled = new JButton("Pregled");
		ImageIcon refreshImage = new ImageIcon(getClass().getResource("/slike/refresh.png"));
		btnRefresh = new JButton(refreshImage);
		this.getRootPane().setDefaultButton(btnProveri);

		// listboxFilmovi options
		listboxPrimerciFilmova.setVisibleRowCount(10);
		listboxPrimerciFilmova.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listboxPrimerciFilmova.setFixedCellWidth(250);
		listboxPrimerciFilmova.setFixedCellHeight(35);

		// listboxKorpa options
		listboxKorpa.setVisibleRowCount(10);
		listboxKorpa.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listboxKorpa.setFixedCellWidth(250);
		listboxKorpa.setFixedCellHeight(35);

		// List modeli za oba listboxa:
		this.modelPrimerciFilmovi = new DefaultListModel<>();
		this.modelKorpa = new DefaultListModel<>();
		listboxPrimerciFilmova.setModel(modelPrimerciFilmovi);
		listboxKorpa.setModel(modelKorpa);

		// Dodavanje widgeta na frejm
		add(lbBrojClanskeKarte, "split 4,span,alignx center");
		add(txtBrojClanskeKarte);
		add(btnProveri);
		add(btnRefresh);
		add(lbKorpa, "alignx center");
		add(lbFilmovi, "alignx center");
		add(new JScrollPane(listboxKorpa));
		add(new JScrollPane(listboxPrimerciFilmova));
		add(btnRemove, "split 3");
		add(lbUkupnaCena);
		add(lbCena);
		add(btnAdd, "split 2");
		add(btnInfo);
		add(btnGotovo, "growx");
		add(btnPregled, "growx");

	}

	public void initMenu() {
		this.menuBar = new JMenuBar();

		this.fileMenu = new JMenu("Videoteka");
		this.izmeniPodatke = new JMenuItem("Izmeni");
		this.logoutItem = new JMenuItem("Logout");
		this.aboutItem = new JMenuItem("O Videoteci");

		this.clanoviMenu = new JMenu("Clanovi");
		this.prikazClanovaItem = new JMenuItem("Prikaz Clanova");

		this.filmoviMenu = new JMenu("Filmovi");
		this.prikazFilmovaItem = new JMenuItem("Prikaz Filmova");
		this.prikazZanrovaItem = new JMenuItem("Prikaz Zanrova");
		this.prikazPrimerakaItem = new JMenuItem("Prikaz Primeraka");

		this.zaposleniMenu = new JMenu("Zaposleni");
		this.prikazZaposlenihItem = new JMenuItem("Prikaz Zaposlenih");

		if (Status.toNum(videoteka.getUlogovaniZaposlen().getStatus()) == 0) {
			this.fileMenu.add(izmeniPodatke);
		}
		this.fileMenu.add(aboutItem);
		this.fileMenu.add(logoutItem);
		this.clanoviMenu.add(prikazClanovaItem);
		this.filmoviMenu.add(prikazFilmovaItem);
		this.filmoviMenu.add(prikazZanrovaItem);
		this.filmoviMenu.add(prikazPrimerakaItem);

		this.zaposleniMenu.add(prikazZaposlenihItem);

		this.menuBar.add(fileMenu);
		this.menuBar.add(clanoviMenu);
		this.menuBar.add(filmoviMenu);
		// ako je admin prikazace dodatni meni
		if (Status.toNum(zaposlen.getStatus()) == 0) {
			this.menuBar.add(zaposleniMenu);

		}

		setJMenuBar(this.menuBar);
	}

	public void initActions() {
		// Confirm dijalog kada se stisne "x"
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				int option = JOptionPane.showConfirmDialog(null, "Da li ste sigurni?", "???", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE);

				if (option == JOptionPane.YES_OPTION) {
					dispose();
				}
			}

		});

		izmeniPodatke.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				VideotekaIzmena vi = new VideotekaIzmena(videoteka);
				vi.setVisible(true);

			}
		});

		logoutItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
				dispose();
				// Nisam siguran dal ovo valja...!!!
				LoginWindow lw = new LoginWindow(new Videoteka());
				lw.setVisible(true);
				// !!!!!!
			}
		});

		aboutItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				JOptionPane.showMessageDialog(null, videoteka, "O Videoteci", JOptionPane.INFORMATION_MESSAGE);

			}
		});

		prikazClanovaItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ClanoviPrikaz cpw = new ClanoviPrikaz(videoteka);
				cpw.setVisible(true);
			}
		});

		prikazZaposlenihItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ZaposleniPrikaz zp = new ZaposleniPrikaz(videoteka);
				zp.setVisible(true);
			}
		});

		prikazFilmovaItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				FilmoviPrikaz filmoviPrikazWindow = new FilmoviPrikaz(videoteka);
				filmoviPrikazWindow.setVisible(true);

			}
		});

		prikazZanrovaItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				ZanroviPrikaz pz = new ZanroviPrikaz(videoteka);
				pz.setVisible(true);

			}
		});

		prikazPrimerakaItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				PrimerciPrikaz pP = new PrimerciPrikaz(videoteka);
				pP.setVisible(true);

			}
		});

		btnProveri.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				novoIznajm = true;
				Clanovi clan = videoteka.pronadjiClana(txtBrojClanskeKarte.getText().trim());
				if (clan == null || Aktivan.toInt(clan.getAktivan()) == 1) {
					JOptionPane.showMessageDialog(null, "Clan " + txtBrojClanskeKarte.getText().trim() + " ne postoji!",
							"Greska!", JOptionPane.ERROR_MESSAGE);
				} else {
					MainWindow.this.clan = clan;

					// za sva iznajmljivanja iz fajla
					// trazi se clan za cije iznajmljivanje ne postoji datum
					// vracanja, tj. clan nije vratio primerke
					for (Iznajmljivanje izn : videoteka.getIznajm()) {
						int cena = 0;
						if (izn.getClan().equals(clan)) {
							if (izn.getDatumVracanja().equals("")) {
								staroIznajm = izn;
								novoIznajm = false;
								btnAdd.setEnabled(false);
								btnRemove.setEnabled(false);
								int n = 0;

								// Racunanje cene na osnovu koliko dana je
								// proslo
								// Cena se samo izracuna i prikazuje i labeli
								// ali se iznajmljivanje obejekat ne postavlja
								// na tu cenu
								// odma, vec se postavlja kad se klikne dugme
								// 'Gotovo'
								try {

									Date datum = new Date();
									SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

									int brojDana = (int) ((datum.getTime()
											- sdf.parse(staroIznajm.getDatumIznajm()).getTime())
											/ (1000 * 60 * 60 * 24));

									if (brojDana > 0) {
										int novaCena = brojDana * staroIznajm.getCena();
										lbCena.setText(String.valueOf(novaCena));
									} else {
										n += 1;
									}

								} catch (Exception e1) {
									// TODO: handle exception
								}

								for (Primerci p : izn.getPrimerci()) {
									cena += Medijum.toCena(p.getMedijum());
									MainWindow.this.modelKorpa.addElement(p);
								}
								if (n == 1) {
									lbCena.setText(String.valueOf(cena));

								}
							}
						}

					}

					// Prikaz svih filmova u listboxFilmovi
					for (Primerci primerak : videoteka.getPrimerci()) {
						MainWindow.this.modelPrimerciFilmovi.addElement(primerak);
						MainWindow.this.btnProveri.setEnabled(false);

					}
				}
			}
		});

		btnRefresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (btnProveri.isEnabled() == false) {
					MainWindow.this.btnProveri.setEnabled(true);
					MainWindow.this.txtBrojClanskeKarte.setText("");

					MainWindow.this.modelPrimerciFilmovi.removeAllElements();
					MainWindow.this.modelKorpa.removeAllElements();
					btnAdd.setEnabled(true);
					btnRemove.setEnabled(true);
					lbCena.setText("0");
				}
			}
		});

		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int selektovaniRed = listboxPrimerciFilmova.getSelectedIndex();
				if (selektovaniRed == -1) {
					JOptionPane.showMessageDialog(null, "Niste selektovali film", "Greska!", JOptionPane.ERROR_MESSAGE);
				} else {

					Primerci primerak = listboxPrimerciFilmova.getSelectedValue();
					Primerci primerak1 = primerak.clone(); // pravi kopiju
															// objekta da bi
															// kasnije taj
															// objekat smestili
															// u korpu

					int pKolicina = primerak.getKolicina();

					if (pKolicina > 0) {
						primerak.setKolicina(pKolicina - 1);

						primerak1.setKolicina(1);
						modelKorpa.addElement(primerak1);

						lbCena.setText(String
								.valueOf(Integer.parseInt(lbCena.getText()) + Medijum.toCena(primerak.getMedijum())));

					} else {
						JOptionPane.showMessageDialog(null, "Nema na stanju!", "Greska", JOptionPane.WARNING_MESSAGE);
					}

				}

				if (modelKorpa.isEmpty() == false) {
					btnRefresh.setEnabled(false);
				}
			}
		});

		btnInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int selektovaniRed = listboxPrimerciFilmova.getSelectedIndex();
				if (selektovaniRed == -1) {
					JOptionPane.showMessageDialog(null, "Niste selektovali film", "Greska!", JOptionPane.ERROR_MESSAGE);
				} else {
					Primerci primerak = listboxPrimerciFilmova.getSelectedValue();
					Filmovi film = primerak.getFilm();

					String opis = film.getOpis();
					int opisLen = opis.length();
					String opisShort = "";
					if (opisLen > 50) {

						opisShort = opis.substring(0, 50) + "...";

					}

					String filmInfo = "Naziv: " + film.getNaslovSrpski() + " (" + film.getNaslovOriginal() + ")" + "\n"
							+ "Zanr: " + film.getZanr() + "\n" + "Opis: " + opisShort + "\n" + "Reziser : "
							+ film.getImePrezimeRezisera() + "\n" + "Godina Izdanja : " + film.getGodIzdanja() + "\n"
							+ "Trajanje: " + film.getTrajanje() + " min\n\n" + "Cena: "
							+ Medijum.toCena(primerak.getMedijum()) + "\n" + "Na stanju: " + primerak.getKolicina()
							+ "\n";

					JOptionPane.showMessageDialog(null, filmInfo, "Informacije o Filmu",
							JOptionPane.INFORMATION_MESSAGE);

				}
			}
		});

		btnRemove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (listboxKorpa.getSelectedIndex() == -1) {
					int option = JOptionPane.showConfirmDialog(null, "Da li ste sigurni?\nObrisacete sve iz korpe",
							"Da li ste sigurni", JOptionPane.WARNING_MESSAGE);
					if (option == 0) {

						// Za svaki primerak u korpi pronalazi odgovarajuci
						// primerak u 'Filmovi' listboxu i povecava mu kolicinu
						for (int j = 0; j != modelKorpa.getSize(); j++) {
							Primerci selektovaniPrimerak = modelKorpa.getElementAt(j);
							for (int i = 0; i != modelPrimerciFilmovi.getSize(); i++) {
								Primerci primerak = modelPrimerciFilmovi.getElementAt(i);
								int kol = primerak.getKolicina();
								if (primerak.getId() == selektovaniPrimerak.getId()) {
									primerak.setKolicina(kol + selektovaniPrimerak.getKolicina());
									lbCena.setText(String.valueOf(Integer.parseInt(lbCena.getText())
											- Medijum.toCena(primerak.getMedijum())));

								}
							}
						}

						modelKorpa.removeAllElements();

					}

				} else {
					Primerci selektovaniPrimerak = listboxKorpa.getSelectedValue();
					modelKorpa.removeElement(selektovaniPrimerak);

					// Za selektovani primerak u 'Korpa' listbox, pronalazi
					// odgovarajuci primerak u 'Filmovi' listboxu i uvecava mu
					// kolicinu za 1
					for (int i = 0; i != modelPrimerciFilmovi.getSize(); i++) {
						Primerci primerak = modelPrimerciFilmovi.getElementAt(i);
						int kol = primerak.getKolicina();
						if (primerak.getId() == selektovaniPrimerak.getId()) {
							primerak.setKolicina(kol + 1);
							lbCena.setText(String.valueOf(
									Integer.parseInt(lbCena.getText()) - Medijum.toCena(primerak.getMedijum())));

						}

					}

				}

				if (modelKorpa.isEmpty() == true) {
					btnRefresh.setEnabled(true);
				}

			}
		});

		btnGotovo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				Date datum = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String formatriranDatum = sdf.format(datum);

				if (modelKorpa.size() == 0) {
					JOptionPane.showMessageDialog(null, "Niste dodali nista u korpu", "Greska!",
							JOptionPane.WARNING_MESSAGE);
				} else {

					ArrayList<Primerci> naruceniPrimerci = new ArrayList<>();

					for (int i = 0; i != modelKorpa.size(); i++) {
						naruceniPrimerci.add(modelKorpa.getElementAt(i));
					}

					if (novoIznajm) {
						int id = videoteka.odrediIDIznajmljivanja();
						Iznajmljivanje iz = new Iznajmljivanje(id, zaposlen, clan, formatriranDatum, "",
								naruceniPrimerci, Integer.parseInt(MainWindow.this.lbCena.getText()));

						videoteka.getIznajm().add(iz);
					} else {

						// Ovde se zapravo postavlja datum vracanja
						// i ukupna cena celokupnog iznajmljivanja

						staroIznajm.setDatumVracanja(formatriranDatum);

						Date dIz = null;
						Date dVr = null;
						try {
							dIz = sdf.parse(staroIznajm.getDatumIznajm());
							dVr = sdf.parse(staroIznajm.getDatumVracanja());
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						int brojDana = (int) ((dVr.getTime() - dIz.getTime()) / (1000 * 60 * 60 * 24));

						if (brojDana > 0) {
							staroIznajm.setCena(brojDana * staroIznajm.getCena());
						}

						// vracanje primeraka nazad na stanje...
						for (Primerci p : staroIznajm.getPrimerci()) {
							for (Primerci p1 : videoteka.getPrimerci()) {
								if (p1.getId() == p.getId()) {
									p1.setKolicina(p1.getKolicina() + 1);
								}
							}
						}
					}

					videoteka.sacuvajIznajmljivanje();
					videoteka.sacuvajPrimerke();

					// Izmenjeni listbox primerci ubaciju u arraylist primerci u
					// videoteci
					ArrayList<Primerci> tempPrimerci = new ArrayList<Primerci>();
					for (int i = 0; i != modelPrimerciFilmovi.getSize(); i++) {
						tempPrimerci.add(modelPrimerciFilmovi.getElementAt(i));
					}

					videoteka.setPrimerci(tempPrimerci);

					JOptionPane.showMessageDialog(null, "Uspesno izvrseno izdavanje!", "Uspesno!",
							JOptionPane.INFORMATION_MESSAGE);

					btnRefresh.setEnabled(true);
					btnRefresh.doClick();

				}
			}
		});

		btnPregled.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				IznajmljivanjaPrikaz izPrikaz = new IznajmljivanjaPrikaz(videoteka);
				izPrikaz.setVisible(true);
				izPrikaz.setAlwaysOnTop(true);
			}
		});
	}
}
