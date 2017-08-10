package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import osobe.Zaposleni;
import videoteka.Videoteka;

public class LoginWindow extends JFrame {

	private JLabel welcomeMessage;
	private JLabel lbUsername;
	private JTextField txtUsername;
	private JLabel lbPassword;
	private JPasswordField pwPassword;
	private JButton btnLogin;
	private Videoteka videoteka;

	public LoginWindow(Videoteka videoteka) {
		this.videoteka = videoteka;
		setTitle("Login");
		setSize(400, 200);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initGUI();
		initActions();
	}

	private void initGUI() {
		MigLayout layout = new MigLayout("wrap 2", "[][]", "[]20[][]20[]");
		setLayout(layout);

		this.welcomeMessage = new JLabel("Dobrodosli! Molim vas prijavite se:");
		this.lbUsername = new JLabel("Username");
		this.txtUsername = new JTextField(20);
		this.lbPassword = new JLabel("Password");
		this.pwPassword = new JPasswordField(20);
		this.btnLogin = new JButton("Login");
		this.getRootPane().setDefaultButton(btnLogin);

		add(welcomeMessage, "span 2");
		add(lbUsername);
		add(txtUsername);
		add(lbPassword);
		add(pwPassword);
		add(btnLogin, "span 2, growx");
		pack();
	}

	private void initActions() {

		btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String uname = txtUsername.getText().trim();
				String passw = new String(pwPassword.getPassword());

				if (uname.equals("") || passw.equals("")) {
					JOptionPane.showMessageDialog(null, "Polja ne mogu biti prazna!", "Error!",
							JOptionPane.WARNING_MESSAGE);
				} else {
					Zaposleni zaposlen = videoteka.login(uname, passw);
					if (zaposlen != null) {
						LoginWindow.this.setVisible(false);
						LoginWindow.this.dispose();
						MainWindow mainWin = new MainWindow(zaposlen, videoteka);
						mainWin.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "Pogresan username/password!", "Error!",
								JOptionPane.ERROR_MESSAGE);
						pwPassword.setText("");
					}
				}

			}
		});

	}

}
