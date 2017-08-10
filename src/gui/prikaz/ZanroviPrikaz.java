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

import gui.dodavanjeIizmena.ZanroviDodavanjeIIzmena;
import videoteka.Filmovi;
import videoteka.Videoteka;
import videoteka.Zanr;

public class ZanroviPrikaz extends JFrame {

	private Videoteka videoteka;

	private JToolBar toolBar;
	private JButton btnAdd;
	private JButton btnEdit;
	private JButton btnRemove;

	private JTable zanroviTabela;
	private JScrollPane skrollPane;

	public ZanroviPrikaz(Videoteka videoteka) {
		this.videoteka = videoteka;
		setTitle("Prikaz Zanrova");
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

		String[] zaglavlje = new String[] { "ID", "Naziv" };

		ArrayList<Zanr> sviZanrovi = new ArrayList<Zanr>();
		sviZanrovi.addAll(videoteka.getZanrovi());

		Object[][] podaci = new Object[sviZanrovi.size()][zaglavlje.length];

		for (int i = 0; i != sviZanrovi.size(); i++) {
			Zanr z = sviZanrovi.get(i);
			podaci[i][0] = z.getOznaka();
			podaci[i][1] = z.getNaziv();

		}

		DefaultTableModel tableModel = new DefaultTableModel(podaci, zaglavlje);
		zanroviTabela = new JTable(tableModel);
		zanroviTabela.setRowSelectionAllowed(true);
		zanroviTabela.setColumnSelectionAllowed(false);
		zanroviTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		zanroviTabela.setDefaultEditor(Object.class, null);

		skrollPane = new JScrollPane(zanroviTabela);
		add(skrollPane, BorderLayout.CENTER);

	}

	private void initActions() {

		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				ZanroviDodavanjeIIzmena zd = new ZanroviDodavanjeIIzmena(videoteka, null, ZanroviPrikaz.this);
				zd.setVisible(true);

			}
		});

		btnEdit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				int selektovaniRed = zanroviTabela.getSelectedRow();

				if (selektovaniRed == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati zanr!", "Greska!",
							JOptionPane.WARNING_MESSAGE);
				} else {
					Zanr zr = videoteka.pronadjiZanr((String) zanroviTabela.getValueAt(selektovaniRed, 0));

					if (zr != null) {
						ZanroviDodavanjeIIzmena zd = new ZanroviDodavanjeIIzmena(videoteka, zr, ZanroviPrikaz.this);
						zd.setVisible(true);
						

					} else {
						JOptionPane.showMessageDialog(null, "Ne postoji izabrani zanr!", "Greska!",
								JOptionPane.WARNING_MESSAGE);
					}
				}

			}
		});

		btnRemove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				int selektovaniRed = zanroviTabela.getSelectedRow();

				if (selektovaniRed == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati zanr!", "Greska!",
							JOptionPane.WARNING_MESSAGE);
				} else {

					int izbor = JOptionPane.showConfirmDialog(null, "Da li ste sigurni?", "Brisanje zanra!",
							JOptionPane.YES_NO_OPTION);

					if (izbor == JOptionPane.YES_OPTION) {

						String id = (String) zanroviTabela.getValueAt(selektovaniRed, 0);
						Zanr z = videoteka.pronadjiZanr(id);

						if (z != null) {

							boolean check = true;

							// za svaki film proverava da li ima izabrani zanr,
							// ako ima onda ne moze da se obrise,
							// ako nema onda se brise.
							for (int i = 0; i != videoteka.getFilmovi().size(); i++) {
								Filmovi f = videoteka.getFilmovi().get(i);
								if (z.getOznaka().equals(f.getZanr().getOznaka())) {
									check = false;
									break;
								}
							}

							if (!check) {
								JOptionPane.showMessageDialog(null,
										"Ne mozete obrisati ovaj zanr, jer potoje filmovi ovog zanra!", "Greska!",
										JOptionPane.ERROR_MESSAGE);

							} else {
								videoteka.getZanrovi().remove(z);
								DefaultTableModel tbm = (DefaultTableModel) ZanroviPrikaz.this.zanroviTabela.getModel();
								tbm.removeRow(selektovaniRed);
								videoteka.sacuvajZanrove();
							}

						}

					}

				}

			}
		});
	}

	public JTable getZanroviTabela() {
		return zanroviTabela;
	}

	
	
}
