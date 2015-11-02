package GUI;
/**
 * @author Mark Lordan
 * @author Thomas Murphy
 */
import javax.swing.*;

import Model.Booking;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class StartScreenGUI extends JFrame implements MouseListener{
	private JLabel welcome, background, help;
	private JPanel startPanel1;
	private static boolean loggedIn;
	private Font font;
	private Color color = new Color(227,99,26);

	public StartScreenGUI() {
		super("TitanFall Towers");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 600);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		this.setResizable(false);
		this.setBackground(Color.gray);
		setLoggedIn(false); // user is not logged in
		
		ImageIcon icon = new ImageIcon("TitanfallImages/TitanFallLogo.png");  //welcome to titanfall header logo
		welcome = new JLabel(icon);
		welcome.setBounds(150, 11, 700, 196);
		getContentPane().add(welcome);
		font = new Font("Veranda", font.PLAIN, 20);
		help = new JLabel("<html><b>Help</b></html>");
		help.setFont(font);
		help.setBounds(920, 30, 127, 23);
		help.setFocusable(true);
		help.addMouseListener(this);
		getContentPane().add(help);
		ImageIcon backgroundImage = new ImageIcon("TitanfallImages/titanfallBackground.png");
		background = new JLabel(backgroundImage);
		background.setBounds(0, 280, 1000,300 );
		getContentPane().add(background);
		startPanel1 = new StartPanelGUI();
		startPanel1.setBounds(150, 216, 1000, 384);
		getContentPane().add(startPanel1);
	}
	// getter and setter to set the loggedIn value to true or false throughout
	// the system
	public static boolean isLoggedIn() {
		return loggedIn;
	}

	public static void setLoggedIn(boolean loggedIna) {
		loggedIn = loggedIna;
	}
	
	public void mouseEntered(MouseEvent e) {

		help.setForeground(color);
	}

	public void mouseExited(MouseEvent e) {

		help.setForeground(color.BLACK);
	}

	public void mousePressed(MouseEvent e) {
		help.setForeground(Color.BLUE);

	}

	public void mouseReleased(MouseEvent arg0) {
		help.setForeground(Color.BLUE);

	}
	public void mouseClicked(MouseEvent e) {
		try {
			java.awt.Desktop.getDesktop().open(new File("TitanFall Website/00_Index.html"));
		} catch (IOException io) {
			// TODO Auto-generated catch block
			io.printStackTrace();
		}
	}
}
