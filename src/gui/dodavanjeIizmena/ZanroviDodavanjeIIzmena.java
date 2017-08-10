package gui.dodavanjeIizmena;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import gui.prikaz.ZanroviPrikaz;
import net.miginfocom.swing.MigLayout;
import videoteka.Filmovi;
import videoteka.Videoteka;
import videoteka.Zanr;

public class ZanroviDodavanjeIIzmena extends JDialog {

	private Videoteka videoteka;
	private Zanr zanr;
	private ZanroviPrikaz zp;

	private JLabel lbOznaka;
	private JTextField txtOznaka;
	private JLabel lbNaziv;
	private JTextField txtNaziv;

	private JButton btnDone;

	public ZanroviDodavanjeIIzmena(Videoteka videoteka, Zanr zanr, ZanroviPrikaz zp) {

		this.videoteka = videoteka;
		this.zanr = zanr;
		this.zp = zp;

		setSize(200, 120);
		setLocationRelativeTo(null);
		setResizable(false);
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		initGUI();
		initActions();

		if (zanr == null) {
			setTitle("Dodavanje Zanra");
		} else {
			setTitle("Izmena Zanra");
			initValues();
		}

	}

	private void initGUI() {

		setLayout(new MigLayout("wrap 2", "[][]", "[][]10[]"));

		lbOznaka = new JLabel("Oznaka");
		txtOznaka = new JTextField(10);
		txtOznaka.setEnabled(false);
		lbNaziv = new JLabel("Naziv");
		txtNaziv = new JTextField(10);

		if (zanr == null) {
			txtOznaka.setText(videoteka.odrediOznakuZanra());
			btnDone = new JButton("Dodaj");
		} else {
			btnDone = new JButton("Izmeni");

		}

		add(lbOznaka);
		add(txtOznaka);
		add(lbNaziv);
		add(txtNaziv);
		add(btnDone, "span,pushx,growx");

	}

	private void initActions() {

		btnDone.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				boolean praznoCheck = true;
				boolean validanUnos = true;
				boolean zanrNazivCheck = true;

				if (txtOznaka.equals("") || txtNaziv.equals("")) {
					praznoCheck = false;
				} else if (Videoteka.proveriUnos(txtOznaka.getText().trim()) == false
						|| Videoteka.proveriUnos(txtNaziv.getText().trim()) == false) {
					validanUnos = false;

				}

				for (Zanr z : videoteka.getZanrovi()) {
					if (z.getNaziv().toLowerCase().equals(txtNaziv.getText().trim().toLowerCase())) {
						zanrNazivCheck = false;
					}
				}

				if (!praznoCheck) {
					JOptionPane.showMessageDialog(null, "Neki podaci nisu uneti!", "Greska!",
							JOptionPane.WARNING_MESSAGE);

				} else if (!validanUnos) {
					JOptionPane.showMessageDialog(null, "Unos nije validan!\nSadrzi neki od zabranjenih karaktera!",
							"Greska!", JOptionPane.WARNING_MESSAGE);

				} else if (!zanrNazivCheck) {

					JOptionPane.showMessageDialog(null, "Naziv zanra vec postoji!",
							"Greska!", JOptionPane.WARNING_MESSAGE);

				} else {
					int red = zp.getZanroviTabela().getSelectedRow();
					DefaultTableModel tableModel = (DefaultTableModel) zp.getZanroviTabela().getModel();

					if (zanr == null) {
						Zanr z = new Zanr(txtOznaka.getText().trim(), txtNaziv.getText().trim());
						videoteka.getZanrovi().add(z);

						Object[] noviRed = new Object[] { z.getOznaka(), z.getNaziv() };

						tableModel.addRow(noviRed);

					} else {

						boolean check = true;

						// za svaki film proverava da li ima izabrani zanr,
						// ako ima onda on ne moze da se menja,
						// ako nema onda moze.
						for (int i = 0; i != videoteka.getFilmovi().size(); i++) {
							Filmovi f = videoteka.getFilmovi().get(i);
							if (zanr.getOznaka().equals(f.getZanr().getOznaka())) {
								check = false;
								break;
							}
						}

						if (!check) {

							JOptionPane.showMessageDialog(null,
									"Ne mozete menjati ovaj zanr, jer potoje filmovi ovog zanra!", "Greska!",
									JOptionPane.ERROR_MESSAGE);

						} else {

							zanr.setOznaka(txtOznaka.getText().trim());
							zanr.setNaziv(txtNaziv.getText().trim());

							zp.getZanroviTabela().setValueAt(zanr.getOznaka(), zp.getZanroviTabela().getSelectedRow(),
									0);
							zp.getZanroviTabela().setValueAt(zanr.getNaziv(), zp.getZanroviTabela().getSelectedRow(),
									1);
						}
					}

					videoteka.sacuvajZanrove();
					ZanroviDodavanjeIIzmena.this.dispose();

				}

			}
		});

	}

	private void initValues() {
		txtOznaka.setText(zanr.getOznaka());
		txtNaziv.setText(zanr.getNaziv());

	}

}
