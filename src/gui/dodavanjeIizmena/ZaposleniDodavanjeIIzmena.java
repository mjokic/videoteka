package gui.dodavanjeIizmena;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import enumeracije.Aktivan;
import enumeracije.Pol;
import enumeracije.Status;
import gui.prikaz.ZaposleniPrikaz;
import net.miginfocom.swing.MigLayout;
import osobe.Clanovi;
import osobe.Zaposleni;
import videoteka.Videoteka;

public class ZaposleniDodavanjeIIzmena extends JDialog {

	private Videoteka videoteka;
	private Zaposleni zaposleni;
	private ZaposleniPrikaz zP;

	private JLabel lbIme;
	private JTextField txtIme;
	private JLabel lbPrezime;
	private JTextField txtPrezime;
	private JLabel lbJmbg;
	private JTextField txtJmbg;
	private JLabel lbAdresa;
	private JTextField txtAdresa;
	private JLabel lbPol;
	private JComboBox cbPol;
	private JLabel lbPlata;
	private JTextField txtPlata;
	private JLabel lbUsername;
	private JTextField txtUsername;
	private JLabel lbPassword;
	private JTextField txtPassword;
	private JLabel lbStatus;
	private JComboBox cbStatus;
	private JButton btnDone;

	public ZaposleniDodavanjeIIzmena(Videoteka videoteka, Zaposleni zaposleni, ZaposleniPrikaz zP) {
		this.videoteka = videoteka;
		this.zaposleni = zaposleni;
		this.zP = zP;

		setSize(270, 300);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setModal(true);
		initGUI();
		initActions();
		if (zaposleni == null) {
			setTitle("Dodavanje Zaposlenog");
		} else {
			setTitle("Izmena Zaposlenog");
			initValues();
		}
	}

	private void initGUI() {

		setLayout(new MigLayout("wrap 2", "[][]", "[][][][][][][][][]10[]"));

		lbIme = new JLabel("Ime");
		txtIme = new JTextField(10);

		lbPrezime = new JLabel("Prezime");
		txtPrezime = new JTextField(10);

		lbJmbg = new JLabel("JMBG");
		txtJmbg = new JTextField(10);

		lbAdresa = new JLabel("Adresa");
		txtAdresa = new JTextField(10);

		lbPol = new JLabel("Pol");
		cbPol = new JComboBox(new String[] { "M", "Z" });

		lbPlata = new JLabel("Plata");
		txtPlata = new JTextField(10);

		lbUsername = new JLabel("Username");
		txtUsername = new JTextField(10);

		lbPassword = new JLabel("Password");
		txtPassword = new JTextField(10);

		lbStatus = new JLabel("ADMIN?");
		cbStatus = new JComboBox(new String[] { "DA", "NE" });
		cbStatus.setSelectedIndex(1);

		if (zaposleni == null) {
			btnDone = new JButton("Dodaj");
		} else {
			btnDone = new JButton("Izmeni");
		}

		add(lbIme);
		add(txtIme);
		add(lbPrezime);
		add(txtPrezime);
		add(lbJmbg);
		add(txtJmbg);
		add(lbAdresa);
		add(txtAdresa);
		add(lbPol);
		add(cbPol);
		add(lbPlata);
		add(txtPlata);
		add(lbUsername);
		add(txtUsername);
		add(lbPassword);
		add(txtPassword);
		add(lbStatus);
		add(cbStatus);
		add(btnDone, "span,growx,pushx");

	}

	private void initActions() {

		btnDone.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				boolean praznoCheck = true;
				boolean intCheck = true;
				boolean jmbgCheck = true;
				boolean validanUnos = true;
				boolean usernameCheck = true;

				if (txtIme.getText().trim().equals("") || txtPrezime.getText().trim().equals("")
						|| txtJmbg.getText().trim().equals("") || txtAdresa.getText().trim().equals("")
						|| txtPlata.getText().trim().equals("") || txtUsername.getText().trim().equals("")
						|| txtPassword.getText().trim().equals("")) {

					praznoCheck = false;

				} else if (txtJmbg.getText().trim().length() != 13) {
					jmbgCheck = false;

				} else if (Videoteka.proveriUnos(txtIme.getText().trim()) == false
						|| Videoteka.proveriUnos(txtPrezime.getText().trim()) == false
						|| Videoteka.proveriUnos(txtAdresa.getText().trim()) == false
						|| Videoteka.proveriUnos(txtUsername.getText().trim()) == false
						|| Videoteka.proveriUnos(txtPassword.getText().trim()) == false) {

					validanUnos = false;

				}

				try {
					int plata = Integer.parseInt(txtPlata.getText().trim());
					long jmbg = Long.parseLong(txtJmbg.getText().trim());

				} catch (NumberFormatException e2) {
					// TODO: handle exception
					intCheck = false;
				}
				
				
				for(Zaposleni z:videoteka.getZaposleni()){
					if(z.getUsername().toLowerCase().equals(txtUsername.getText().trim().toLowerCase())){
						usernameCheck = false;
					}
				}

				if (!praznoCheck) {
					JOptionPane.showMessageDialog(null, "Neki podaci nisu uneti!", "Greska",
							JOptionPane.WARNING_MESSAGE);
				} else if (!intCheck) {
					JOptionPane.showMessageDialog(null, "JMBG i plata moraju biti broj!", "Greska!",
							JOptionPane.WARNING_MESSAGE);

				} else if (!jmbgCheck) {
					JOptionPane.showMessageDialog(null, "Niste uneli validan JMBG!", "Greska!",
							JOptionPane.WARNING_MESSAGE);

				} else if (!validanUnos) {

					JOptionPane.showMessageDialog(null, "Unos nije validan!\nSadrzi neki od zabranjenih karaktera!",
							"Greska!", JOptionPane.WARNING_MESSAGE);
					
				}else if(!usernameCheck){
					JOptionPane.showMessageDialog(null, "Username vec postoji!",
							"Greska!", JOptionPane.WARNING_MESSAGE);

				} else {

					int red = zP.getTabelaZaposlenih().getSelectedRow();
					DefaultTableModel tableModel = (DefaultTableModel) zP.getTabelaZaposlenih().getModel();

					if (zaposleni == null) {
						Zaposleni zap = new Zaposleni(txtIme.getText().trim(), txtPrezime.getText().trim(),
								txtJmbg.getText().trim(), txtAdresa.getText().trim(),
								Pol.fromSlovo((String) cbPol.getSelectedItem()),
								Integer.parseInt(txtPlata.getText().trim()), txtUsername.getText().trim(),
								txtPassword.getText().trim(), Status.toStatus((int) cbStatus.getSelectedIndex()));

						videoteka.getZaposleni().add(zap);

						String tmp = "";
						if (Status.toNum(zap.getStatus()) == 0) {
							tmp = "DA";
						} else {
							tmp = "NE";
						}
						Object[] noviRed = new Object[] { zap.getIme(), zap.getPrezime(), zap.getJmbg(),
								zap.getAdresa(), zap.getPol(), zap.getPlata(), zap.getUsername(), zap.getPassword(), tmp

						};

						tableModel.addRow(noviRed);

					} else {

						zaposleni.setIme(txtIme.getText().trim());
						zaposleni.setPrezime(txtPrezime.getText().trim());
						zaposleni.setJmbg(txtJmbg.getText().trim());
						zaposleni.setAdresa(txtAdresa.getText().trim());
						zaposleni.setPol(Pol.fromSlovo((String) cbPol.getSelectedItem()));
						zaposleni.setPlata(Integer.parseInt(txtPlata.getText().trim()));
						zaposleni.setUsername(txtUsername.getText().trim());
						zaposleni.setPassword(txtPassword.getText().trim());
						zaposleni.setStatus(Status.toStatus(cbStatus.getSelectedIndex()));

						String tmp1 = "";
						if (Status.toNum(zaposleni.getStatus()) == 0) {
							tmp1 = "DA";
						} else {
							tmp1 = "NE";
						}

						zP.getTabelaZaposlenih().setValueAt(zaposleni.getIme(), red, 0);
						zP.getTabelaZaposlenih().setValueAt(zaposleni.getPrezime(), red, 1);
						zP.getTabelaZaposlenih().setValueAt(zaposleni.getJmbg(), red, 2);
						zP.getTabelaZaposlenih().setValueAt(zaposleni.getAdresa(), red, 3);
						zP.getTabelaZaposlenih().setValueAt(zaposleni.getPol(), red, 4);
						zP.getTabelaZaposlenih().setValueAt(zaposleni.getPlata(), red, 5);
						zP.getTabelaZaposlenih().setValueAt(zaposleni.getUsername(), red, 6);
						zP.getTabelaZaposlenih().setValueAt(zaposleni.getPassword(), red, 7);
						zP.getTabelaZaposlenih().setValueAt(tmp1, red, 8);

					}

					videoteka.sacuvajZaposlene();
					ZaposleniDodavanjeIIzmena.this.dispose();

				}

			}
		});

	}

	private void initValues() {

		txtIme.setText(zaposleni.getIme());
		txtPrezime.setText(zaposleni.getPrezime());
		txtJmbg.setText(zaposleni.getJmbg());
		txtAdresa.setText(zaposleni.getAdresa());
		txtPlata.setText(String.valueOf(zaposleni.getPlata()));
		txtUsername.setText(zaposleni.getUsername());
		txtPassword.setText(zaposleni.getPassword());

		if (Pol.toSlovo(zaposleni.getPol()) == "M") {
			cbPol.setSelectedIndex(0);
		} else {
			cbPol.setSelectedIndex(1);

		}

		if (Status.toNum(zaposleni.getStatus()) == 0) {
			cbStatus.setSelectedIndex(0);
		} else {
			cbStatus.setSelectedIndex(1);
		}

	}
}
