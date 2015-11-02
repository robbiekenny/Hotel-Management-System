package GUI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;

import Model.User;
/**
 * @author Thomas Murphy
 * @author Mark Lordan
 * @author Robert Kenny
 * @author Derek Mulhern
 */
public class UserTabbedScreenGUI extends JFrame implements MouseListener {
	private JTabbedPane tabbedPane;

	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JPanel panel5;
	private JPanel panel6;
	private String usersID;
	private Font font, fontRegular;
	private ArrayList<User> users;
	private JLabel welcome,  welcomeUser,signOut;
	private String usersFirstName;
	private Color color = new Color(227,99,26);
	
	public UserTabbedScreenGUI(String user, ArrayList<User> users) {
		
		
		super("Titanfall Towers-User Home Screen");
		font = new Font("Veranda", font.ITALIC, 20);
		fontRegular = new Font("Veranda", font.PLAIN, 16);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000,600);
		setLocationRelativeTo(null);
		usersID = user;
		this.users = users;
		//if the users log in was successful then they are brought to this page and LoggedIn is set to true
		this.setResizable(false);
		this.setBackground(Color.gray);
		StartScreenGUI.setLoggedIn(true);
		ImageIcon icon = new ImageIcon("TitanfallImages/TitanFallLogo.png");
		welcome = new JLabel(icon);
		welcome.setBounds(150, 50, 700, 196);
		add(welcome);
		
		welcomeUser = new JLabel("Welcome, " + getUsersFirstName());
		welcomeUser.setBounds(740, 30, 140, 23);
		welcomeUser.setFont(fontRegular);
		add(welcomeUser);
		
		signOut = new JLabel("Sign Out");
		signOut.setFocusable(true);
		signOut.addMouseListener(this);
		signOut.setBounds(920, 30, 127, 23);
		signOut.setFont(new Font("Veranda", font.PLAIN, 16));
		this.add(signOut);

		// Create the tab pages
		panel1 = new CreateBookingGUI(usersID, this.users);
		panel2 = new ManageBookingGUI(this.usersID);
		panel3 = new CalendarGUI();
		panel5 = new HelpGUI();
		panel6 = new ManageAccountGUI(this.usersID, this.users);
		
		// Create a tabbed pane
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Create Booking", panel1);
		tabbedPane.addTab("Manage Booking", panel2);
		tabbedPane.addTab("Calendar", panel3);
		tabbedPane.addTab("Help", panel5);
		tabbedPane.addTab("Manage Account", panel6);
		this.add(tabbedPane);
	}
	public String getUsersFirstName() {
		for (int i = 0; i < users.size(); i++) {

			if (usersID.equals(users.get(i).getUserID())) {
				usersFirstName = users.get(i).getfName();
			}
		}

		return usersFirstName;
	}
	public void mouseClicked(MouseEvent e) {

		StartScreenGUI s = new StartScreenGUI();
		setVisible(false);
		s.setVisible(true);
		s.setLoggedIn(false);

	}

	public void mouseEntered(MouseEvent e) {

		signOut.setForeground(color);
	}

	public void mouseExited(MouseEvent e) {

		signOut.setForeground(color.BLACK);
	}

	public void mousePressed(MouseEvent e) {
		signOut.setForeground(Color.BLUE);

	}

	public void mouseReleased(MouseEvent arg0) {
		signOut.setForeground(Color.BLUE);

	}
	
}
