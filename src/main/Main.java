package main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import gui.LoginWindow;
import videoteka.Videoteka;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Videoteka v = new Videoteka();

		LoginWindow logWindow = new LoginWindow(v);
		logWindow.setVisible(true);

		
		
	}

}
