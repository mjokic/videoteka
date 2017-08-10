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
import enumeracije.Status;
import gui.MainWindow;
import gui.dodavanjeIizmena.ClanoviDodavanjeIIzmena;
import gui.dodavanjeIizmena.IznajmljivanjeIzmena;
import iznajmljivanje.Iznajmljivanje;
import iznajmljivanje.Primerci;
import osobe.Clanovi;
import videoteka.Videoteka;

public class IznajmljivanjaPrikaz extends JFrame {

	private Videoteka videoteka;

	private JToolBar mainToolBar;
	private JButton btnRemove;
	private JButton btnEdit;

	private JTable tabelaIznajmljivanja;
	private JScrollPane tabelaSkrol;

	public IznajmljivanjaPrikaz(Videoteka videoteka) {
		this.videoteka = videoteka;
		setTitle("Pregled svih iznajmljivanja");
		setSize(1000, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initGUI();
		initActions();

	}

	public void initGUI() {

		mainToolBar = new JToolBar();
		ImageIcon editImage = new ImageIcon(getClass().getResource("/slike/edit.gif"));
		btnEdit = new JButton(editImage);
		ImageIcon removeImage = new ImageIcon(getClass().getResource("/slike/remove.gif"));
		btnRemove = new JButton(removeImage);

		mainToolBar.add(btnEdit);
		mainToolBar.add(btnRemove);

		if (Status.toNum(videoteka.getUlogovaniZaposlen().getStatus()) == 0) {
			add(mainToolBar, BorderLayout.NORTH);
		}

		String[] zaglavlje = new String[] { "ID", "Zaposleni", "Broj Clanske Karte", "Datum Izdavanja",
				"Datum Vracanja", "Primerci", "Cena" };

		ArrayList<Iznajmljivanje> svaIznajmljivanja = new ArrayList<Iznajmljivanje>();
		svaIznajmljivanja.addAll(videoteka.getIznajm());

		Object[][] podaci = new Object[svaIznajmljivanja.size()][zaglavlje.length];

		for (int i = 0; i != svaIznajmljivanja.size(); i++) {
			Iznajmljivanje izn = svaIznajmljivanja.get(i);
			podaci[i][0] = izn.getId();
			podaci[i][1] = izn.getZaposlen().getUsername();
			podaci[i][2] = izn.getClan().getBrojClankseKarte();
			podaci[i][3] = izn.getDatumIznajm();

			if (izn.getDatumVracanja().equals("")) {
				podaci[i][4] = "/";

			} else {
				podaci[i][4] = izn.getDatumVracanja();
			}

			String sviPrimerci = "";
			for (Primerci p : izn.getPrimerci()) {
				sviPrimerci += p.getFilm().getNaslovSrpski() + "(" + p.getMedijum() + "), ";
			}
			podaci[i][5] = sviPrimerci;
			podaci[i][6] = izn.getCena();
		}

		DefaultTableModel tabelaModel = new DefaultTableModel(podaci, zaglavlje);
		tabelaIznajmljivanja = new JTable(tabelaModel);
		tabelaIznajmljivanja.setCellSelectionEnabled(false);
		tabelaIznajmljivanja.setRowSelectionAllowed(true);
		tabelaIznajmljivanja.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaIznajmljivanja.setDefaultEditor(Object.class, null);

		tabelaSkrol = new JScrollPane(tabelaIznajmljivanja);
		add(tabelaSkrol, BorderLayout.CENTER);

	}

	public void initActions() {

		btnEdit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				int selektovaniRed = tabelaIznajmljivanja.getSelectedRow();
				if (selektovaniRed == -1) {
					JOptionPane.showMessageDialog(null, "Morate Selektovati Iznajmljivanje!", "Greska!",
							JOptionPane.WARNING_MESSAGE);
				} else {
					int id = (int) tabelaIznajmljivanja.getValueAt(selektovaniRed, 0);
					Iznajmljivanje izn = videoteka.pronadjiIznajmljivanje(id);

					if (izn != null) {
						IznajmljivanjeIzmena iz = new IznajmljivanjeIzmena(videoteka, izn, IznajmljivanjaPrikaz.this);
						iz.setVisible(true);

					} else {
						JOptionPane.showMessageDialog(null, "Greska prilikom pronalazenja Iznajmljivanja!", "Greska!",
								JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});

		btnRemove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				int selektovaniRed = tabelaIznajmljivanja.getSelectedRow();

				if (selektovaniRed == -1) {
					JOptionPane.showMessageDialog(null, "Morate Selektovati Iznajmljivanje!", "Greska!",
							JOptionPane.WARNING_MESSAGE);
				} else {
					int izbor = JOptionPane.showConfirmDialog(null, "Da li ste sigurni?", "Brisanje Iznajmljivanja",
							JOptionPane.YES_NO_OPTION);

					if (izbor == JOptionPane.YES_OPTION) {
						int id = (int) tabelaIznajmljivanja.getValueAt(selektovaniRed, 0);
						Iznajmljivanje iz = videoteka.pronadjiIznajmljivanje(id);

						if (iz != null && !iz.getDatumVracanja().equals("")) {

							videoteka.getIznajm().remove(iz);
							DefaultTableModel tbm = (DefaultTableModel) IznajmljivanjaPrikaz.this.tabelaIznajmljivanja
									.getModel();
							tbm.removeRow(selektovaniRed);

							videoteka.sacuvajIznajmljivanje();
						} else {
							JOptionPane.showMessageDialog(null, "Ne mozete obrisati nedovrseno iznajmljivanje!",
									"Greska!", JOptionPane.ERROR_MESSAGE);
						}

					}

				}

			}
		});

	}

	public JTable getTabelaIznajmljivanja() {
		return tabelaIznajmljivanja;
	}

}
