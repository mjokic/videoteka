package gui.dodavanjeIizmena;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import videoteka.Videoteka;

public class VideotekaIzmena extends JDialog {

	private Videoteka videoteka;

	private JLabel lbPib;
	private JTextField txtPib;

	private JLabel lbNaziv;
	private JTextField txtNaziv;

	private JLabel lbAdresa;
	private JTextField txtAdresa;

	private JLabel lbCenaVHS;
	private JTextField txtCenaVHS;

	private JLabel lbCenaDVD;
	private JTextField txtCenaDVD;

	private JLabel lbCenaBRDVD;
	private JTextField txtCenaBRDVD;

	private JButton btnDone;

	public VideotekaIzmena(Videoteka videoteka) {
		this.videoteka = videoteka;
		setSize(210, 240);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setModal(true);
		initGUI();
		initActions();
	}

	private void initGUI() {

		setLayout(new MigLayout("wrap 2", "[][]", "[][][]10[]"));

		lbPib = new JLabel("PIB");
		txtPib = new JTextField(20);

		lbNaziv = new JLabel("Naziv");
		txtNaziv = new JTextField(20);

		lbAdresa = new JLabel("Adresa");
		txtAdresa = new JTextField(20);

		lbCenaVHS = new JLabel("Cena VHS");
		txtCenaVHS = new JTextField(20);

		lbCenaDVD = new JLabel("Cena DVD");
		txtCenaDVD = new JTextField(20);

		lbCenaBRDVD = new JLabel("Cena BRDVD");
		txtCenaBRDVD = new JTextField(20);

		btnDone = new JButton("Izmeni");

		txtPib.setText(videoteka.getPib());
		txtNaziv.setText(videoteka.getNaziv());
		txtAdresa.setText(videoteka.getAdresa());
		txtCenaVHS.setText(String.valueOf(Videoteka.cenaVHS));
		txtCenaDVD.setText(String.valueOf(Videoteka.cenaDVD));
		txtCenaBRDVD.setText(String.valueOf(Videoteka.cenaBRDVD));

		add(lbPib);
		add(txtPib);
		add(lbNaziv);
		add(txtNaziv);
		add(lbAdresa);
		add(txtAdresa);
		add(lbCenaVHS);
		add(txtCenaVHS);
		add(lbCenaDVD);
		add(txtCenaDVD);
		add(lbCenaBRDVD);
		add(txtCenaBRDVD);
		add(btnDone, "span,growx,pushx");

	}

	private void initActions() {

		btnDone.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				boolean validanUnos = true;

				if (txtPib.getText().trim().equals("") || txtNaziv.getText().trim().equals("")
						|| txtAdresa.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Polja ne mogu biti prazna!", "Greska!",
							JOptionPane.WARNING_MESSAGE);

				} else if (Videoteka.proveriUnos(txtPib.getText().trim()) == false
						|| Videoteka.proveriUnos(txtNaziv.getText().trim()) == false
						|| videoteka.proveriUnos(txtAdresa.getText().trim()) == false) {

					JOptionPane.showMessageDialog(null, "Unos nije validan!\nSadrzi neki od zabranjenih karaktera!",
							"Greska!", JOptionPane.WARNING_MESSAGE);

				} else {
					videoteka.setPib(txtPib.getText().trim());
					videoteka.setNaziv(txtNaziv.getText().trim());
					videoteka.setAdresa(txtAdresa.getText().trim());

					Videoteka.cenaVHS = Integer.parseInt(txtCenaVHS.getText().trim());
					Videoteka.cenaDVD = Integer.parseInt(txtCenaDVD.getText().trim());
					Videoteka.cenaBRDVD = Integer.parseInt(txtCenaBRDVD.getText().trim());

					videoteka.sacuvajVideoteku();
					VideotekaIzmena.this.dispose();

				}

			}
		});

	}

}
