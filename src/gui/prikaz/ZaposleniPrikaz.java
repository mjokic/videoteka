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
import gui.dodavanjeIizmena.ClanoviDodavanjeIIzmena;
import gui.dodavanjeIizmena.ZaposleniDodavanjeIIzmena;
import osobe.Clanovi;
import osobe.Zaposleni;
import videoteka.Videoteka;

public class ZaposleniPrikaz extends JFrame {

	private Videoteka videoteka;

	private JTable tabelaZaposlenih;
	private JScrollPane skrollPane;

	private JToolBar toolBar;

	private JButton btnAdd;
	private JButton btnEdit;
	private JButton btnRemove;

	public ZaposleniPrikaz(Videoteka videoteka) {
		this.videoteka = videoteka;
		setTitle("Prikaz Zaposlenih");
		setSize(800, 500);
		setLocationRelativeTo(null);
		setResizable(false);
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

		String[] zaglavlje = new String[] { "Ime", "Prezime", "JMBG", "Adresa", "Pol", "Plata", "Username", "Password",
				"Admin?" };

		ArrayList<Zaposleni> sviZaposleni = new ArrayList<Zaposleni>();
		sviZaposleni.addAll(videoteka.getZaposleni());

		Object[][] podaci = new Object[sviZaposleni.size()][zaglavlje.length];

		for (int i = 0; i != sviZaposleni.size(); i++) {
			Zaposleni zap = sviZaposleni.get(i);

			podaci[i][0] = zap.getIme();
			podaci[i][1] = zap.getPrezime();
			podaci[i][2] = zap.getJmbg();
			podaci[i][3] = zap.getAdresa();
			podaci[i][4] = zap.getPol();
			podaci[i][5] = zap.getPlata();
			podaci[i][6] = zap.getUsername();
			podaci[i][7] = zap.getPassword();
			if (Status.toNum(zap.getStatus()) == 0) {
				podaci[i][8] = "DA";
			} else {
				podaci[i][8] = "NE";
			}

		}

		DefaultTableModel tableModel = new DefaultTableModel(podaci, zaglavlje);
		tabelaZaposlenih = new JTable(tableModel);
		tabelaZaposlenih.setRowSelectionAllowed(true);
		tabelaZaposlenih.setColumnSelectionAllowed(false);
		tabelaZaposlenih.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaZaposlenih.setDefaultEditor(Object.class, null);

		skrollPane = new JScrollPane(tabelaZaposlenih);
		add(skrollPane, BorderLayout.CENTER);

	}

	public void initActions() {

		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				ZaposleniDodavanjeIIzmena zd = new ZaposleniDodavanjeIIzmena(videoteka, null, ZaposleniPrikaz.this);
				zd.setVisible(true);

			}
		});

		btnEdit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				int selektovaniRed = tabelaZaposlenih.getSelectedRow();
				if (selektovaniRed == -1) {
					JOptionPane.showMessageDialog(null, "Morate Selektovati Zaposlenog!", "Greska!",
							JOptionPane.WARNING_MESSAGE);
				} else {
					String username = (String) tabelaZaposlenih.getValueAt(selektovaniRed, 6);
					Zaposleni zapo = videoteka.pronadjiZaposlenog(username);

					if (zapo != null) {
						ZaposleniDodavanjeIIzmena zd = new ZaposleniDodavanjeIIzmena(videoteka, zapo,
								ZaposleniPrikaz.this);
						zd.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "Greska prilikom pronalazenja zaposlenog!", "Greska!",
								JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});

		btnRemove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				int selektovaniRed = tabelaZaposlenih.getSelectedRow();

				if (selektovaniRed == -1) {
					JOptionPane.showMessageDialog(null, "Morate Selektovati Zapolenog!", "Greska!",
							JOptionPane.WARNING_MESSAGE);
				} else {
					int izbor = JOptionPane.showConfirmDialog(null, "Da li ste sigurni?", "Brisanje Zaposlenog",
							JOptionPane.YES_NO_OPTION);

					if (izbor == JOptionPane.YES_OPTION) {
						String username = (String) tabelaZaposlenih.getValueAt(selektovaniRed, 6);
						Zaposleni zap = videoteka.pronadjiZaposlenog(username);

						if (zap != null) {
							videoteka.getZaposleni().remove(zap);
							DefaultTableModel tbm = (DefaultTableModel) ZaposleniPrikaz.this.tabelaZaposlenih
									.getModel();
							tbm.removeRow(selektovaniRed);

							videoteka.sacuvajZaposlene();
						}

					}
				}

			}
		});

	}

	public JTable getTabelaZaposlenih() {
		return tabelaZaposlenih;
	}

}
