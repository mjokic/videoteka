package gui.dodavanjeIizmena;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ListResourceBundle;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import enumeracije.Aktivan;
import enumeracije.Pol;
import gui.prikaz.IznajmljivanjaPrikaz;
import iznajmljivanje.Iznajmljivanje;
import iznajmljivanje.Primerci;
import net.miginfocom.swing.MigLayout;
import osobe.Clanovi;
import osobe.Zaposleni;
import videoteka.Videoteka;

public class IznajmljivanjeIzmena extends JDialog {

	private Videoteka videoteka;
	private Iznajmljivanje iznajm;
	private IznajmljivanjaPrikaz ip;

	// 1|marko|100|22/06/2016|28/06/2016|1,|900

	private JLabel lbId;
	private JTextField txtId;
	private JLabel lbZaposlen;
	private JTextField txtZaposlen;
	private JLabel lbBrojClKarte;
	private JTextField txtBrojClKarte;
	private JLabel lbDatumIznajmljivanja;
	private JTextField txtDatumIznajmljivanja;
	private JLabel lbDatumVracanja;
	private JTextField txtDatumVracanja;
	private JLabel lbPrimerci;
	private JList<Primerci> listPrimerci;
	private JLabel lbUkCena;
	private JTextField txtUkCena;
	private JButton btnDone;

	private DefaultListModel<Primerci> modelLista;

	public IznajmljivanjeIzmena(Videoteka videoteka, Iznajmljivanje iznajm, IznajmljivanjaPrikaz ip) {
		this.videoteka = videoteka;
		this.iznajm = iznajm;
		this.ip = ip;
		setSize(400, 300);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setModal(true);
		initGUI();
		initActions();
		initValues();

	}

	private void initGUI() {

		setLayout(new MigLayout("wrap 2", "[][]", "[][][][][][fill][]20[]"));

		lbId = new JLabel("ID");
		txtId = new JTextField(10);
		txtId.setEnabled(false);

		lbZaposlen = new JLabel("Zaposlen");
		txtZaposlen = new JTextField(10);

		lbBrojClKarte = new JLabel("Broj Clanske Karte");
		txtBrojClKarte = new JTextField(10);

		lbDatumIznajmljivanja = new JLabel("Datum Iznajmljivanja");
		txtDatumIznajmljivanja = new JTextField(10);

		lbDatumVracanja = new JLabel("Datum Vracanja");
		txtDatumVracanja = new JTextField(10);

		lbPrimerci = new JLabel("Primerci");
		listPrimerci = new JList<Primerci>();

		modelLista = new DefaultListModel<Primerci>();

		for (Primerci p : iznajm.getPrimerci()) {
			modelLista.addElement(p);
		}

		listPrimerci.setModel(modelLista);

		lbUkCena = new JLabel("Cena");
		txtUkCena = new JTextField(6);

		btnDone = new JButton("Izmeni");

		add(lbId);
		add(txtId);
		add(lbZaposlen);
		add(txtZaposlen);
		add(lbBrojClKarte);
		add(txtBrojClKarte);
		add(lbDatumIznajmljivanja);
		add(txtDatumIznajmljivanja);
		add(lbDatumVracanja);
		add(txtDatumVracanja);
		add(lbPrimerci);
		add(new JScrollPane(listPrimerci));
		add(lbUkCena);
		add(txtUkCena);
		add(btnDone, "span,growx,pushx");

	}

	private void initActions() {

		btnDone.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				boolean praznoCheck = true;
				boolean intCheck = true;
				boolean validanUnos = true;

				if (txtZaposlen.getText().trim().equals("") || txtBrojClKarte.getText().trim().equals("")
						|| txtDatumIznajmljivanja.getText().trim().equals("")
						|| txtDatumVracanja.getText().trim().equals("") || txtUkCena.getText().trim().equals("")) {

					praznoCheck = false;

				} else if (Videoteka.proveriUnos(txtZaposlen.getText().trim()) == false
						|| Videoteka.proveriUnos(txtDatumIznajmljivanja.getText().trim()) == false
						|| Videoteka.proveriUnos(txtDatumVracanja.getText().trim()) == false) {

					validanUnos = false;

				}

				try {
					int broj = Integer.parseInt(txtUkCena.getText().trim());
					int brCK = Integer.parseInt(txtBrojClKarte.getText().trim());
				} catch (NumberFormatException e1) {
					// TODO: handle exception
					intCheck = false;
				}

				if (!praznoCheck) {
					JOptionPane.showMessageDialog(null, "Neki podaci nisu uneti!", "Greska",
							JOptionPane.WARNING_MESSAGE);

				} else if (!intCheck) {
					JOptionPane.showMessageDialog(null, "Cena i Br. Clanske Karte moraju biti broj!", "Greska!",
							JOptionPane.WARNING_MESSAGE);

				} else if (!validanUnos) {

					JOptionPane.showMessageDialog(null, "Unos nije validan!\nSadrzi neki od zabranjenih karaktera!",
							"Greska!", JOptionPane.WARNING_MESSAGE);

				} else {

					int red = ip.getTabelaIznajmljivanja().getSelectedRow();
					DefaultTableModel tableModel = (DefaultTableModel) ip.getTabelaIznajmljivanja().getModel();

					boolean greska = false;

					iznajm.setId(Integer.parseInt(txtId.getText()));
					Zaposleni zaposlen = videoteka.pronadjiZaposlenog(txtZaposlen.getText().trim());
					if (zaposlen != null) {
						iznajm.setZaposlen(zaposlen);
					} else {
						JOptionPane.showMessageDialog(null, "Ne postoji zaposleni sa zadatim username!", "Greska!",
								JOptionPane.ERROR_MESSAGE);
						greska = true;
					}

					Clanovi clan = videoteka.pronadjiClana(txtBrojClKarte.getText().trim());
					if (clan != null) {
						iznajm.setClan(clan);
					} else {
						JOptionPane.showMessageDialog(null, "Ne postoji clan sa zadatim brojem clanske karte!",
								"Greska!", JOptionPane.ERROR_MESSAGE);
						greska = true;
					}

					if (!greska) {
						iznajm.setDatumIznajm(txtDatumIznajmljivanja.getText().trim());
						iznajm.setDatumVracanja(txtDatumVracanja.getText().trim());
						iznajm.setCena(Integer.parseInt(txtUkCena.getText().trim()));

						ip.getTabelaIznajmljivanja().setValueAt(iznajm.getZaposlen().getUsername(), red, 1);
						ip.getTabelaIznajmljivanja().setValueAt(iznajm.getClan().getBrojClankseKarte(), red, 2);
						ip.getTabelaIznajmljivanja().setValueAt(iznajm.getDatumIznajm(), red, 3);
						ip.getTabelaIznajmljivanja().setValueAt(iznajm.getDatumVracanja(), red, 4);
						ip.getTabelaIznajmljivanja().setValueAt(iznajm.getCena(), red, 6);

						videoteka.sacuvajIznajmljivanje();
						IznajmljivanjeIzmena.this.dispose();

					}

				}

			}
		});
	}

	private void initValues() {

		txtId.setText(String.valueOf(iznajm.getId()));
		txtZaposlen.setText(iznajm.getZaposlen().getUsername());
		txtBrojClKarte.setText(iznajm.getClan().getBrojClankseKarte());
		txtDatumIznajmljivanja.setText(iznajm.getDatumIznajm());
		if (iznajm.getDatumVracanja().equals("")) {
			txtDatumVracanja.setText("/");
		} else {
			txtDatumVracanja.setText(iznajm.getDatumVracanja());
		}
		txtUkCena.setText(String.valueOf(iznajm.getCena()));

	}

}
