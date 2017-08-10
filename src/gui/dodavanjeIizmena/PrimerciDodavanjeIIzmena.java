package gui.dodavanjeIizmena;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import enumeracije.Medijum;
import gui.prikaz.PrimerciPrikaz;
import iznajmljivanje.Primerci;
import net.miginfocom.swing.MigLayout;
import videoteka.Filmovi;
import videoteka.Videoteka;

public class PrimerciDodavanjeIIzmena extends JDialog {

	private Videoteka videoteka;
	private Primerci primerak;
	private PrimerciPrikaz pP;

	private JLabel lbId;
	private JTextField txtId;
	private JLabel lbFilm;
	private JComboBox cbFilm;
	private JLabel lbMedijum;
	private JComboBox cbMedijum;
	private JLabel lbKolicina;
	private JTextField txtKolicina;
	private JButton btnDone;

	public PrimerciDodavanjeIIzmena(Videoteka videoteka, Primerci primerak, PrimerciPrikaz pP) {

		this.videoteka = videoteka;
		this.primerak = primerak;
		this.pP = pP;
		setSize(420, 175);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setModal(true);
		initGUI();
		initActions();
		if (primerak == null) {
			setTitle("Dodavanje Primerka");
		} else {
			setTitle("Izmena Primerka");
			initValues();
		}

	}

	private void initGUI() {

		setLayout(new MigLayout("wrap 2", "[][]", "[][][][]10[]"));

		lbId = new JLabel("Id");
		txtId = new JTextField(10);
		txtId.setEnabled(false);
		lbFilm = new JLabel("Film");
		cbFilm = new JComboBox();
		lbMedijum = new JLabel("Medijum");
		cbMedijum = new JComboBox(new String[] { "VHS", "DVD", "BRDVD" });
		lbKolicina = new JLabel("Kolicina");
		txtKolicina = new JTextField(10);

		for (Filmovi f : videoteka.getFilmovi()) {
			cbFilm.addItem(f);
		}

		if (primerak == null) {
			btnDone = new JButton("Dodaj");
			txtId.setText(String.valueOf(videoteka.odrediIdPrimerka()));

		} else {
			btnDone = new JButton("Izmeni");

		}

		add(lbId);
		add(txtId);
		add(lbFilm);
		add(cbFilm, "growx");
		add(lbMedijum);
		add(cbMedijum);
		add(lbKolicina);
		add(txtKolicina);
		add(btnDone, "span,growx");

	}

	private void initActions() {

		btnDone.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				boolean praznoCheck = true;
				boolean intCheck = true;
				boolean validanUnos = true;
				boolean primerakCheck = true;

				if (txtId.getText().trim().equals("") || txtKolicina.getText().trim().equals("")) {

					praznoCheck = false;

				}

				try {
					int broj = Integer.parseInt(txtKolicina.getText().trim());
				} catch (NumberFormatException e2) {
					// TODO: handle exception
					intCheck = false;
				}

				for (Primerci prm : videoteka.getPrimerci()) {
					if (prm.getFilm().equals((Filmovi) cbFilm.getSelectedItem())
							&& Medijum.toBroj(prm.getMedijum()) == cbMedijum.getSelectedIndex()) {
						primerakCheck = false;
					}
				}

				if (!praznoCheck) {
					JOptionPane.showMessageDialog(null, "Neki podaci nisu uneti!", "Greska",
							JOptionPane.WARNING_MESSAGE);
				} else if (!intCheck) {
					JOptionPane.showMessageDialog(null, "Morate uneti broj za kolicinu!", "Greska!",
							JOptionPane.WARNING_MESSAGE);

				} else if (!primerakCheck) {

					JOptionPane.showMessageDialog(null, "Izabrani primerak vec postoji na izabranom medijumu!",
							"Greska!", JOptionPane.WARNING_MESSAGE);

				} else {

					int red = pP.getPrimerciTabela().getSelectedRow();
					DefaultTableModel tableModel = (DefaultTableModel) pP.getPrimerciTabela().getModel();

					if (primerak == null) {

						Filmovi f = (Filmovi) cbFilm.getSelectedItem();

						Primerci prim = new Primerci(Integer.parseInt(txtId.getText().trim()), f,
								Medijum.fromBroj(cbMedijum.getSelectedIndex()),
								Integer.parseInt(txtKolicina.getText().trim()));

						videoteka.getPrimerci().add(prim);

						Object[] noviRed = new Object[] { prim.getId(), prim.getFilm().getNaslovSrpski(),
								prim.getMedijum(), prim.getKolicina() };

						tableModel.addRow(noviRed);

					} else {

						primerak.setId(Integer.parseInt(txtId.getText().trim()));
						primerak.setFilm((Filmovi) cbFilm.getSelectedItem());
						primerak.setMedijum(Medijum.fromBroj(cbMedijum.getSelectedIndex()));
						primerak.setKolicina(Integer.parseInt(txtKolicina.getText().trim()));

						pP.getPrimerciTabela().setValueAt(primerak.getId(), red, 0);
						pP.getPrimerciTabela().setValueAt(primerak.getFilm().getNaslovSrpski(), red, 1);
						pP.getPrimerciTabela().setValueAt(primerak.getMedijum(), red, 2);
						pP.getPrimerciTabela().setValueAt(primerak.getKolicina(), red, 3);

					}

					videoteka.sacuvajPrimerke();
					PrimerciDodavanjeIIzmena.this.dispose();
				}

			}
		});

	}

	private void initValues() {

		txtId.setText(String.valueOf(primerak.getId()));
		cbFilm.setSelectedItem(primerak.getFilm());
		cbMedijum.setSelectedIndex(Medijum.toBroj(primerak.getMedijum()));
		txtKolicina.setText(String.valueOf(primerak.getKolicina()));

	}

}
