package gui.dodavanjeIizmena;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import enumeracije.Medijum;
import gui.prikaz.FilmoviPrikaz;
import iznajmljivanje.Primerci;
import net.miginfocom.swing.MigLayout;
import videoteka.Filmovi;
import videoteka.Videoteka;
import videoteka.Zanr;

public class FilmoviDodavanjeIIzmena extends JDialog {

	private Videoteka videoteka;
	private Filmovi film;
	private FilmoviPrikaz fP;

	private JLabel lbNaslovSrp;
	private JTextField txtNaslovSrp;
	private JLabel lbNaslovOrig;
	private JTextField txtNaslovOrig;
	private JLabel lbGodinaIzdavanja;
	private JTextField txtGodinaIzdavanja;
	private JLabel lbZanr;
	private JComboBox cbZanr;
	private JLabel lbReziser;
	private JTextField txtReziser;
	private JLabel lbOpis;
	private JTextArea txtOpis;
	private JLabel lbTrajanje;
	private JTextField txtTrajanje;
	private JButton btnDone;

	private JScrollPane opisSkroll;

	public FilmoviDodavanjeIIzmena(Videoteka videoteka, Filmovi film, FilmoviPrikaz fP) {
		this.videoteka = videoteka;
		this.film = film;
		this.fP = fP;

		setSize(300, 360);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setModal(true);
		initGUI();
		initActions();
		if (film == null) {
			setTitle("Dodavanje Filmova");
		} else {
			setTitle("Izmena Filmova");
			initValues();
		}

	}

	private void initGUI() {

		setLayout(new MigLayout("wrap 2", "[][]", "[][][][][][grow,fill][]15[]"));

		lbNaslovSrp = new JLabel("Naslov - Srpski");
		txtNaslovSrp = new JTextField(20);
		lbNaslovOrig = new JLabel("Naslov - Original");
		txtNaslovOrig = new JTextField(20);
		lbGodinaIzdavanja = new JLabel("Godina Izdavanja");
		txtGodinaIzdavanja = new JTextField(20);
		lbZanr = new JLabel("Zanr");
		cbZanr = new JComboBox();
		lbReziser = new JLabel("Reziser");
		txtReziser = new JTextField(20);
		lbOpis = new JLabel("Opis");
		// txtOpis = new JTextField(20);
		txtOpis = new JTextArea(15, 15);
		txtOpis.setLineWrap(true);
		txtOpis.setWrapStyleWord(true);
		// txtOpis.setRows(15);
		// txtOpis.setColumns(15);
		txtOpis.setAutoscrolls(true);
		lbTrajanje = new JLabel("Trajanje (min)");
		txtTrajanje = new JTextField(20);

		for (Zanr z : videoteka.getZanrovi()) {
			cbZanr.addItem(z);
		}

		if (film == null) {
			btnDone = new JButton("Dodaj");

		} else {
			btnDone = new JButton("Izmeni");
		}

		opisSkroll = new JScrollPane(txtOpis, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		opisSkroll.setPreferredSize(new Dimension(500, 500));

		add(lbNaslovSrp);
		add(txtNaslovSrp);
		add(lbNaslovOrig);
		add(txtNaslovOrig);
		add(lbGodinaIzdavanja);
		add(txtGodinaIzdavanja);
		add(lbZanr);
		add(cbZanr);
		add(lbReziser);
		add(txtReziser);
		add(lbOpis);
		// add(txtOpis);
		add(opisSkroll);
		add(lbTrajanje);
		add(txtTrajanje);
		add(btnDone, "span,pushx,growx");

	}

	private void initActions() {

		btnDone.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				boolean praznoCheck = true;
				boolean intCheck = true;
				boolean validanUnos = true;
				boolean filmNaslovCheck = true;

				if (txtNaslovSrp.getText().trim().equals("") || txtNaslovOrig.getText().trim().equals("")
						|| txtGodinaIzdavanja.getText().trim().equals("") || txtReziser.getText().trim().equals("")
						|| txtOpis.getText().trim().equals("") || txtTrajanje.getText().trim().equals("")) {

					praznoCheck = false;

				} else if (Videoteka.proveriUnos(txtNaslovOrig.getText().trim()) == false
						|| Videoteka.proveriUnos(txtNaslovOrig.getText().trim()) == false
						|| Videoteka.proveriUnos(txtReziser.getText().trim()) == false
						|| Videoteka.proveriUnos(txtOpis.getText().trim()) == false) {

					validanUnos = false;
				}

				for (Filmovi f : videoteka.getFilmovi()) {
					if (f.getNaslovSrpski().toLowerCase().equals(txtNaslovSrp.getText().trim().toLowerCase()) || f
							.getNaslovOriginal().toLowerCase().equals(txtNaslovOrig.getText().trim().toLowerCase())) {
						filmNaslovCheck = false;
					}
				}

				try {
					int trajanje = Integer.parseInt(txtTrajanje.getText().trim());
					int godina = Integer.parseInt(txtGodinaIzdavanja.getText().trim());
				} catch (NumberFormatException e2) {
					// TODO: handle exception
					intCheck = false;
				}

				if (!praznoCheck) {
					JOptionPane.showMessageDialog(null, "Neki podaci nisu uneti!", "Greska",
							JOptionPane.WARNING_MESSAGE);

				} else if (!intCheck) {
					JOptionPane.showMessageDialog(null, "Morate uneti broj za godinu i trajanje!", "Greska!",
							JOptionPane.WARNING_MESSAGE);

				} else if (!validanUnos) {

					JOptionPane.showMessageDialog(null, "Unos nije validan!\nSadrzi neki od zabranjenih karaktera!",
							"Greska!", JOptionPane.WARNING_MESSAGE);

				} else if (!filmNaslovCheck) {
					JOptionPane.showMessageDialog(null, "Naslov filma vec postoji!", "Greska!",
							JOptionPane.WARNING_MESSAGE);

				} else {

					int red = fP.getTabelaFilmova().getSelectedRow();
					DefaultTableModel tableModel = (DefaultTableModel) fP.getTabelaFilmova().getModel();

					if (film == null) {

						Zanr z = (Zanr) cbZanr.getSelectedItem();

						Filmovi f = new Filmovi(txtNaslovSrp.getText().trim(), txtNaslovOrig.getText().trim(),
								txtGodinaIzdavanja.getText().trim(), (Zanr) cbZanr.getSelectedItem(),
								txtReziser.getText().trim(), txtOpis.getText().trim(),
								Integer.parseInt(txtTrajanje.getText().trim()));

						videoteka.getFilmovi().add(f);

						Object[] noviRed = new Object[] { f.getNaslovSrpski(), f.getNaslovOriginal(), f.getGodIzdanja(),
								f.getZanr(), f.getImePrezimeRezisera(), f.getOpis(), f.getTrajanje() };

						tableModel.addRow(noviRed);

					} else {

						film.setNaslovSrpski(txtNaslovSrp.getText().trim());
						film.setNaslovOriginal(txtNaslovOrig.getText().trim());
						film.setGodIzdanja(txtGodinaIzdavanja.getText().trim());
						film.setZanr((Zanr) cbZanr.getSelectedItem());
						film.setImePrezimeRezisera(txtReziser.getText().trim());
						film.setOpis(txtOpis.getText().trim());
						film.setTrajanje(Integer.parseInt(txtTrajanje.getText().trim()));

						fP.getTabelaFilmova().setValueAt(film.getNaslovSrpski(), red, 0);
						fP.getTabelaFilmova().setValueAt(film.getNaslovOriginal(), red, 1);
						fP.getTabelaFilmova().setValueAt(film.getGodIzdanja(), red, 2);
						fP.getTabelaFilmova().setValueAt(film.getZanr(), red, 3);
						fP.getTabelaFilmova().setValueAt(film.getImePrezimeRezisera(), red, 4);
						fP.getTabelaFilmova().setValueAt(film.getOpis(), red, 5);
						fP.getTabelaFilmova().setValueAt(film.getTrajanje(), red, 6);

					}

					videoteka.sacuvajFilmove();
					FilmoviDodavanjeIIzmena.this.dispose();
				}

			}
		});

	}

	private void initValues() {

		txtNaslovSrp.setText(film.getNaslovSrpski());
		txtNaslovOrig.setText(film.getNaslovOriginal());
		txtGodinaIzdavanja.setText(film.getGodIzdanja());
		txtReziser.setText(film.getImePrezimeRezisera());
		txtOpis.setText(film.getOpis());
		txtTrajanje.setText(String.valueOf(film.getTrajanje()));

		cbZanr.setSelectedItem(film.getZanr());

	}

}
