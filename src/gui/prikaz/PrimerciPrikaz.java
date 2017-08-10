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

import enumeracije.Medijum;
import gui.dodavanjeIizmena.PrimerciDodavanjeIIzmena;
import iznajmljivanje.Primerci;
import videoteka.Videoteka;
import videoteka.Zanr;

public class PrimerciPrikaz extends JFrame {

	private Videoteka videoteka;

	private JToolBar toolBar;
	private JButton btnAdd;
	private JButton btnEdit;
	private JButton btnRemove;

	private JTable primerciTabela;
	private JScrollPane skrollPane;

	public PrimerciPrikaz(Videoteka videoteka) {
		this.videoteka = videoteka;
		setTitle("Prikaz Primeraka");
		setSize(500, 400);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initGUI();
		initActions();
	}

	private void initGUI() {

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

		String[] zaglavlje = new String[] { "ID", "Naziv Filma", "Medijum", "Kolicina" };

		ArrayList<Primerci> sviPrimerci = new ArrayList<Primerci>();
		sviPrimerci.addAll(videoteka.getPrimerci());

		Object[][] podaci = new Object[sviPrimerci.size()][zaglavlje.length];

		for (int i = 0; i != sviPrimerci.size(); i++) {
			Primerci p = sviPrimerci.get(i);

			podaci[i][0] = p.getId();
			podaci[i][1] = p.getFilm().getNaslovSrpski();
			podaci[i][2] = p.getMedijum();
			podaci[i][3] = p.getKolicina();

		}

		DefaultTableModel tableModel = new DefaultTableModel(podaci, zaglavlje);
		primerciTabela = new JTable(tableModel);
		primerciTabela.setRowSelectionAllowed(true);
		primerciTabela.setColumnSelectionAllowed(false);
		primerciTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		primerciTabela.setDefaultEditor(Object.class, null);

		skrollPane = new JScrollPane(primerciTabela);
		add(skrollPane, BorderLayout.CENTER);

	}

	private void initActions() {

		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				PrimerciDodavanjeIIzmena pd = new PrimerciDodavanjeIIzmena(videoteka, null, PrimerciPrikaz.this);
				pd.setVisible(true);

			}
		});

		btnEdit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				int selektovaniRed = primerciTabela.getSelectedRow();

				if (selektovaniRed == -1) {
					JOptionPane.showMessageDialog(null, "Morate Selektovati Primerak!", "Greska!",
							JOptionPane.WARNING_MESSAGE);
				} else {
					int selektovaniId = (int) primerciTabela.getValueAt(selektovaniRed, 0);
					Primerci primerak = videoteka.pronadjiPrimerak(selektovaniId);

					if (primerak != null) {
						PrimerciDodavanjeIIzmena pd = new PrimerciDodavanjeIIzmena(videoteka, primerak,
								PrimerciPrikaz.this);
						pd.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "Greska prilikom pronalazenja primerka!", "Greska!",
								JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});

		btnRemove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				int selektovaniRed = primerciTabela.getSelectedRow();

				if (selektovaniRed == -1) {

					JOptionPane.showMessageDialog(null, "Morate Selektovati Primerak!", "Greska!",
							JOptionPane.WARNING_MESSAGE);

				} else {

					int izbor = JOptionPane.showConfirmDialog(null, "Da li ste sigurni?", "Brisanje Primerka",
							JOptionPane.YES_NO_OPTION);

					if (izbor == JOptionPane.YES_OPTION) {
						int id = (int) primerciTabela.getValueAt(selektovaniRed, 0);
						Primerci prim = videoteka.pronadjiPrimerak(id);

						if (prim != null) {
							videoteka.getPrimerci().remove(prim);
							DefaultTableModel tm = (DefaultTableModel) PrimerciPrikaz.this.primerciTabela.getModel();
							tm.removeRow(selektovaniRed);

							videoteka.sacuvajPrimerke();

						} else {

							JOptionPane.showMessageDialog(null, "Nije pronadjen primerak!", "Greska!",
									JOptionPane.ERROR_MESSAGE);

						}
					}
				}

			}
		});
	}

	public JTable getPrimerciTabela() {
		return primerciTabela;
	}

}
