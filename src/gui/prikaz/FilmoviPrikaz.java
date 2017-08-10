package gui.prikaz;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import enumeracije.Aktivan;
import gui.dodavanjeIizmena.ClanoviDodavanjeIIzmena;
import gui.dodavanjeIizmena.FilmoviDodavanjeIIzmena;
import iznajmljivanje.Primerci;
import osobe.Clanovi;
import videoteka.Filmovi;
import videoteka.Videoteka;

public class FilmoviPrikaz extends JFrame {

	private Videoteka videoteka;
	private JToolBar toolBar;
	private JButton btnAdd;
	private JButton btnEdit;
	private JButton btnRemove;

	private JTable tabelaFilmova;
	private JScrollPane skrollPane;

	public FilmoviPrikaz(Videoteka videoteka) {
		this.videoteka = videoteka;
		setTitle("Prikaz Filmova");
		setSize(800, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initGUI();
		initActions();

	}

	public void initGUI() {
		toolBar = new JToolBar();
		ImageIcon btnAddImage = new ImageIcon(getClass().getResource("/slike/add.gif"));
		btnAdd = new JButton(btnAddImage);
		ImageIcon btnEditImage = new ImageIcon(getClass().getResource("/slike/edit.gif"));
		btnEdit = new JButton(btnEditImage);
		ImageIcon btnRemoveImage = new ImageIcon(getClass().getResource("/slike/remove.gif"));
		btnRemove = new JButton(btnRemoveImage);

		toolBar.add(btnAdd);
		toolBar.add(btnEdit);
		toolBar.add(btnRemove);

		add(toolBar, BorderLayout.NORTH);

		String[] zaglavlje = new String[] { "Naslov (Srpski jez.)", "Naslov (Original jez.)", "Godina Izdavanja",
				"Zanr", "Reziser", "Opis", "Trajanje (min.)" };

		ArrayList<Filmovi> sviFilmovi = new ArrayList<Filmovi>();
		sviFilmovi.addAll(videoteka.getFilmovi());

		Object[][] podaci = new Object[sviFilmovi.size()][zaglavlje.length];

		for (int i = 0; i != sviFilmovi.size(); i++) {
			Filmovi film = sviFilmovi.get(i);

			podaci[i][0] = film.getNaslovSrpski();
			podaci[i][1] = film.getNaslovOriginal();
			podaci[i][2] = film.getGodIzdanja();
			podaci[i][3] = film.getZanr();
			podaci[i][4] = film.getImePrezimeRezisera();
			podaci[i][5] = film.getOpis();
			podaci[i][6] = film.getTrajanje();

		}

		DefaultTableModel tableModel = new DefaultTableModel(podaci, zaglavlje);
		tabelaFilmova = new JTable(tableModel);
		tabelaFilmova.setRowSelectionAllowed(true);
		tabelaFilmova.setColumnSelectionAllowed(false);
		tabelaFilmova.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaFilmova.setDefaultEditor(Object.class, null);

		skrollPane = new JScrollPane(tabelaFilmova);
		add(skrollPane);

	}

	public void initActions() {

		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				FilmoviDodavanjeIIzmena fd = new FilmoviDodavanjeIIzmena(videoteka, null, FilmoviPrikaz.this);
				fd.setVisible(true);

			}
		});

		btnEdit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				int selektovaniRed = tabelaFilmova.getSelectedRow();
				if (selektovaniRed == -1) {
					JOptionPane.showMessageDialog(null, "Morate Selektovati Film!", "Greska!",
							JOptionPane.WARNING_MESSAGE);
				} else {
					String nazivSrpski = (String) tabelaFilmova.getValueAt(selektovaniRed, 0);
					Filmovi f = videoteka.pronadjiFilm(nazivSrpski);

					if (f != null) {
						FilmoviDodavanjeIIzmena fd = new FilmoviDodavanjeIIzmena(videoteka, f, FilmoviPrikaz.this);
						fd.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "Greska prilikom pronalazenja filma!", "Greska!",
								JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});

		btnRemove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				int selektovaniRed = tabelaFilmova.getSelectedRow();

				if (selektovaniRed == -1) {
					JOptionPane.showMessageDialog(null, "Morate Selektovati Film!", "Greska!",
							JOptionPane.WARNING_MESSAGE);
				} else {
					int izbor = JOptionPane.showConfirmDialog(null, "Da li ste sigurni?", "Brisanje Filma",
							JOptionPane.YES_NO_OPTION);

					if (izbor == JOptionPane.YES_OPTION) {
						String naslovSrpski = (String) tabelaFilmova.getValueAt(selektovaniRed, 0);
						Filmovi f = videoteka.pronadjiFilm(naslovSrpski);

						boolean obrisiCheck = true;

						if (f != null) {

							for (Primerci p : videoteka.getPrimerci()) {
								if (p.getFilm().getNaslovSrpski().equals(f.getNaslovSrpski())) {
									obrisiCheck = false;
									break;

								}
							}

							if (!obrisiCheck) {
								JOptionPane.showMessageDialog(null,
										"Ne mozete obrisati ovaj film jer postoje primerci ovog filma!", "Greska!",
										JOptionPane.WARNING_MESSAGE);

							} else {
								videoteka.getFilmovi().remove(f);
								DefaultTableModel tbm = (DefaultTableModel) FilmoviPrikaz.this.tabelaFilmova.getModel();
								tbm.removeRow(selektovaniRed);
								videoteka.sacuvajFilmove();

							}
						}

					}
				}
			}

		});

	}

	public JTable getTabelaFilmova() {
		return tabelaFilmova;
	}

}
