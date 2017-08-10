package gui.prikaz;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import enumeracije.Aktivan;
import gui.dodavanjeIizmena.ClanoviDodavanjeIIzmena;
import osobe.Clanovi;
import videoteka.Videoteka;

public class ClanoviPrikaz extends JFrame {

	private Videoteka videoteka;
	private JToolBar mainToolBar;
	private JButton btnAdd;
	private JButton btnRemove;
	private JButton btnEdit;

	private JTable tabelaSvihClanova;
	private JScrollPane skrollPane;

	public ClanoviPrikaz(Videoteka videoteka) {
		this.videoteka = videoteka;
		setTitle("Prikaz Clanova");
		setSize(800, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initGUI();
		initActions();
	}

	public void initGUI() {
		mainToolBar = new JToolBar();
		ImageIcon addImage = new ImageIcon(getClass().getResource("/slike/add.gif"));
		btnAdd = new JButton(addImage);
		ImageIcon editImage = new ImageIcon(getClass().getResource("/slike/edit.gif"));
		btnEdit = new JButton(editImage);
		ImageIcon removeImage = new ImageIcon(getClass().getResource("/slike/remove.gif"));
		btnRemove = new JButton(removeImage);

		mainToolBar.add(btnAdd);
		mainToolBar.add(btnEdit);
		mainToolBar.add(btnRemove);

		add(mainToolBar, BorderLayout.NORTH);

		String[] zaglavlje = new String[] { "Ime", "Prezime", "JMBG", "Adresa", "Pol", "Broj Clanske Karte",
				"Aktivan" };

		ArrayList<Clanovi> sviClanovi = new ArrayList<Clanovi>();
		sviClanovi.addAll(videoteka.getClanovi());

		Object[][] podaci = new Object[sviClanovi.size()][zaglavlje.length];

		for (int i = 0; i != sviClanovi.size(); i++) {
			Clanovi cl = sviClanovi.get(i);

			podaci[i][0] = cl.getIme();
			podaci[i][1] = cl.getPrezime();
			podaci[i][2] = cl.getJmbg();
			podaci[i][3] = cl.getAdresa();
			podaci[i][4] = cl.getPol();
			podaci[i][5] = cl.getBrojClankseKarte();
			podaci[i][6] = cl.getAktivan();

		}

		DefaultTableModel tableModel = new DefaultTableModel(podaci, zaglavlje);
		tabelaSvihClanova = new JTable(tableModel);
		tabelaSvihClanova.setDefaultEditor(Object.class, null);
		tabelaSvihClanova.setRowSelectionAllowed(true);
		tabelaSvihClanova.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaSvihClanova.setDefaultEditor(Object.class, null);

		skrollPane = new JScrollPane(tabelaSvihClanova);
		add(skrollPane, BorderLayout.CENTER);

	}

	public void initActions() {

		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ClanoviDodavanjeIIzmena cd = new ClanoviDodavanjeIIzmena(videoteka, null, ClanoviPrikaz.this);
				cd.setVisible(true);
				cd.setAlwaysOnTop(true);

			}
		});

		btnEdit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				int selektovaniRed = tabelaSvihClanova.getSelectedRow();
				if (selektovaniRed == -1) {
					JOptionPane.showMessageDialog(null, "Morate Selektovati Clana!", "Greska!",
							JOptionPane.WARNING_MESSAGE);
				} else {
					String brojCK = (String) tabelaSvihClanova.getValueAt(selektovaniRed, 5);
					Clanovi clan = videoteka.pronadjiClana(brojCK);

					if (clan != null) {
						ClanoviDodavanjeIIzmena cd = new ClanoviDodavanjeIIzmena(videoteka, clan, ClanoviPrikaz.this);
						cd.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "Greska prilikom pronalazenja clana!", "Greska!",
								JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});

		btnRemove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				int selektovaniRed = tabelaSvihClanova.getSelectedRow();

				if (selektovaniRed == -1) {
					JOptionPane.showMessageDialog(null, "Morate Selektovati Clana!", "Greska!",
							JOptionPane.WARNING_MESSAGE);
				} else {
					int izbor = JOptionPane.showConfirmDialog(null, "Da li ste sigurni?", "Brisanje Clana",
							JOptionPane.YES_NO_OPTION);

					if (izbor == JOptionPane.YES_OPTION) {
						String brojCK = (String) tabelaSvihClanova.getValueAt(selektovaniRed, 5);
						Clanovi clan = videoteka.pronadjiClana(brojCK);

						if (clan != null) {
							clan.setAktivan(Aktivan.fromInt(1));

							tabelaSvihClanova.setValueAt(clan.getAktivan(), selektovaniRed, 6);
							videoteka.sacuvajClanove();
						}

					}
				}
			}
		});
	}

	public JTable getTabelaSvihClanova() {
		return tabelaSvihClanova;
	}

}
