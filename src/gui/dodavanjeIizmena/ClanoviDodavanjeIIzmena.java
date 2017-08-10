package gui.dodavanjeIizmena;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicTreeUI.SelectionModelPropertyChangeHandler;
import javax.swing.table.DefaultTableModel;

import enumeracije.Aktivan;
import enumeracije.Pol;
import gui.prikaz.ClanoviPrikaz;
import net.miginfocom.swing.MigLayout;
import osobe.Clanovi;
import videoteka.Videoteka;

public class ClanoviDodavanjeIIzmena extends JDialog {

	private Videoteka videoteka;
	private Clanovi clan;
	private ClanoviPrikaz cp;

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
	private JLabel lbBrojClanskeKarte;
	private JTextField txtBrojClanskeKarte;
	private JButton btnAdd;

	public ClanoviDodavanjeIIzmena(Videoteka videoteka, Clanovi clan, ClanoviPrikaz cp) {
		this.videoteka = videoteka;
		this.clan = clan;
		this.cp = cp;
		setSize(300, 240);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setModal(true);
		initGUI();
		initActions();
		if (clan == null) {
			setTitle("Dodavanje Clana");
		} else {
			setTitle("Izmena Clan");
			initValues();
		}
	}

	public void initGUI() {

		setLayout(new MigLayout("wrap 2", "[][]", "[][][][][]15[]"));

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
		lbBrojClanskeKarte = new JLabel("Broj Clanske Karte");
		txtBrojClanskeKarte = new JTextField(10);
		txtBrojClanskeKarte.setText(videoteka.odrediBrojClanskeKarte());
		txtBrojClanskeKarte.setEnabled(false);
		if (clan == null) {
			btnAdd = new JButton("Dodaj");
		} else {
			btnAdd = new JButton("Izmeni");
		}
		add(lbIme);
		add(txtIme, "growx");
		add(lbPrezime);
		add(txtPrezime, "growx");
		add(lbJmbg);
		add(txtJmbg, "growx");
		add(lbAdresa);
		add(txtAdresa, "growx");
		add(lbPol);
		add(cbPol);
		add(lbBrojClanskeKarte);
		add(txtBrojClanskeKarte, "pushx");
		add(btnAdd, "span,growx,growy,pushy");

	}

	public void initActions() {

		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				boolean praznoCheck = true;
				boolean jmbgCheck = true;
				boolean validanUnos = true;

				Videoteka.proveriUnos(txtIme.getText());

				if (txtIme.getText().equals("") || txtPrezime.getText().equals("") || txtAdresa.getText().equals("")
						|| txtJmbg.equals("")) {
					praznoCheck = false;
				} else if (txtJmbg.getText().length() != 13) {
					jmbgCheck = false;

				} else if (Videoteka.proveriUnos(txtIme.getText().trim()) == false
						|| Videoteka.proveriUnos(txtPrezime.getText().trim()) == false
						|| Videoteka.proveriUnos(txtAdresa.getText().trim()) == false) {

					validanUnos = false;

				}
				
				try {
					long broj = Long.parseLong(txtJmbg.getText().trim());
				} catch (NumberFormatException e2) {
					// TODO: handle exception
					jmbgCheck = false;
				}

				if (!praznoCheck) {
					JOptionPane.showMessageDialog(null, "Neki podaci nisu uneti!", "Greska",
							JOptionPane.WARNING_MESSAGE);

				} else if (!jmbgCheck) {
					JOptionPane.showMessageDialog(null, "Niste uneli validan JMBG!", "Greska!",
							JOptionPane.WARNING_MESSAGE);

				} else if (!validanUnos) {

					JOptionPane.showMessageDialog(null, "Unos nije validan!\nSadrzi neki od zabranjenih karaktera!",
							"Greska!", JOptionPane.WARNING_MESSAGE);

				} else {
					int red = cp.getTabelaSvihClanova().getSelectedRow();
					DefaultTableModel tableModel = (DefaultTableModel) cp.getTabelaSvihClanova().getModel();

					if (clan == null) {
						Clanovi cl = new Clanovi(txtIme.getText().trim(), txtPrezime.getText().trim(),
								txtJmbg.getText().trim(), txtAdresa.getText().trim(),
								(Pol) Pol.fromSlovo((String) cbPol.getSelectedItem()), txtBrojClanskeKarte.getText(),
								Aktivan.fromInt(0));

						videoteka.getClanovi().add(cl);

						Object[] noviRed = new Object[] { cl.getIme(), cl.getPrezime(), cl.getJmbg(), cl.getAdresa(),
								cl.getPol(), cl.getBrojClankseKarte(), cl.getAktivan() };

						tableModel.addRow(noviRed);

					} else {

						clan.setIme(txtIme.getText().trim());
						clan.setPrezime(txtPrezime.getText().trim());
						clan.setJmbg(txtJmbg.getText().trim());
						clan.setAdresa(txtAdresa.getText().trim());
						clan.setPol(Pol.fromSlovo((String) cbPol.getSelectedItem()));

						cp.getTabelaSvihClanova().setValueAt(clan.getIme(), red, 0);
						cp.getTabelaSvihClanova().setValueAt(clan.getPrezime(), red, 1);
						cp.getTabelaSvihClanova().setValueAt(clan.getJmbg(), red, 2);
						cp.getTabelaSvihClanova().setValueAt(clan.getAdresa(), red, 3);
						cp.getTabelaSvihClanova().setValueAt(clan.getPol(), red, 4);

					}

					videoteka.sacuvajClanove();
					ClanoviDodavanjeIIzmena.this.dispose();
				}

			}
		});

	}

	public void initValues() {
		txtIme.setText(clan.getIme());
		txtPrezime.setText(clan.getPrezime());
		txtAdresa.setText(clan.getAdresa());
		txtJmbg.setText(clan.getJmbg());
		txtBrojClanskeKarte.setText(clan.getBrojClankseKarte());

		if (Pol.toSlovo(clan.getPol()) == "M") {
			cbPol.setSelectedIndex(0);
		} else {
			cbPol.setSelectedIndex(1);

		}

	}

}
