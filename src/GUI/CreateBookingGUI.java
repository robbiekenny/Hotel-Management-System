package GUI;
/**
 * @author Mark Lordan
 * @author Robert Kenny
 */
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import Model.Booking;
import Model.User;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JDayChooser;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;

import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.border.BevelBorder;

public class CreateBookingGUI extends JPanel implements ActionListener {
	private String[] nights = { "1", "2", "3", "4", "5", "6", "7", "8", "9",
			"10", "11", "12", "13", "14" };

	private static boolean loggedIn;
	private JDateChooser dateChooser;
	private JYearChooser day, year;
	private JMonthChooser month;

	private JComboBox numNights;
	private JLabel lblnumNights, arrivalDate;
	private JButton btnSearch;
	private Font font, fontBigger;
	private Calendar chosenDate;
	private JPanel panel;
	private ArrayList<User> users;
	private String usersID;

	private Color color = new Color(227, 99, 26);

	public CreateBookingGUI(String user, ArrayList<User> users) {
		Calendar cal = Calendar.getInstance();

		font = new Font("Veranda", font.ITALIC, 24);
		fontBigger = new Font("Veranda", font.PLAIN, 18);
		setLoggedIn(false);
		setLayout(null);

		usersID = user;
		this.users = users;

		panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		panel.setBounds(155, 220, 635, 285);
		add(panel);
		panel.setLayout(null);

		lblnumNights = new JLabel("Number of Nights:");
		lblnumNights.setBounds(161, 140, 151, 26);
		panel.add(lblnumNights);
		lblnumNights.setFont(fontBigger);

		numNights = new JComboBox(nights);
		numNights.setBounds(333, 140, 194, 20);
		panel.add(numNights);
		numNights.setFont(fontBigger);

		arrivalDate = new JLabel("Opt 2: Arrival Date:");
		arrivalDate.setBounds(161, 102, 162, 26);
		panel.add(arrivalDate);
		arrivalDate.setFont(fontBigger);

		day = new JYearChooser();
		day.setBounds(333, 102, 35, 20);
		panel.add(day);
		day.setYear((cal.get(Calendar.DAY_OF_MONTH)));
		day.setMaximum(31);
		day.setMinimum(1);

		month = new JMonthChooser();
		month.setBounds(371, 102, 107, 20);
		panel.add(month);
		month.setMonth(cal.get(Calendar.MONTH));

		year = new JYearChooser();
		year.setBounds(480, 102, 47, 20);
		panel.add(year);
		year.setYear(cal.get(Calendar.YEAR));
		year.setMaximum(2016);
		year.setMinimum(2014);

		// ////////////////////////////////////////////////////////////////////////////////////////////////////
		// JDateChooser //
		// Sets date of comboBoxes to selected date //
		// for-loop needed, otherwise it looks for position 2014,2015 etc //
		// //
		// //
		// ////////////////////////////////////////////////////////////////////////////////////////////////////

		dateChooser = new JDateChooser();
		dateChooser.getCalendarButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		dateChooser.setBounds(333, 65, 194, 26);
		panel.add(dateChooser);
		dateChooser.setMinSelectableDate(cal.getTime());
		dateChooser.setIcon(new ImageIcon("TitanfallImages/cal.jpg"));
		cal.add(Calendar.YEAR, 2);
		dateChooser.setMaxSelectableDate(cal.getTime());

		btnSearch = new JButton("Search");
		btnSearch.setBackground(color);
		btnSearch.setFont(fontBigger);
		btnSearch.setBounds(161, 200, 366, 26);
		panel.add(btnSearch);

		JLabel lblNewLabel = new JLabel("Opt 1: Calendar:");
		lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
		lblNewLabel.setBounds(161, 65, 151, 26);
		panel.add(lblNewLabel);
		btnSearch.addActionListener(this);
		dateChooser.getDateEditor().addPropertyChangeListener(
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent e) {
						if ("date".equals(e.getPropertyName())) {
							chosenDate = Calendar.getInstance();
							chosenDate.setTime((Date) e.getNewValue());
							day.setYear(chosenDate.get(Calendar.DAY_OF_MONTH));
							month.setMonth(chosenDate.get(Calendar.MONTH));
							year.setYear(chosenDate.get(Calendar.YEAR));

						}

					}
				});
		month.addPropertyChangeListener(new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent e) {
				if (month.getMonth() == 3 || month.getMonth() == 5
						|| month.getMonth() == 8 || month.getMonth() == 10) {
					day.setMaximum(30);
					if (day.getYear() == 31) {
						day.setYear(30);
					}
				} else if (month.getMonth() == 1 && year.getYear() != 2016) {
					day.setMaximum(28);
					if (day.getYear() == 29 || day.getYear() == 30
							|| day.getYear() == 31) {
						day.setYear(28);
					}
				} else if (year.getYear() == 2016 && month.getMonth() == 1) {
					day.setMaximum(29);
					if (day.getYear() == 30 || day.getYear() == 31) {
						day.setYear(29);
					}
				} else {
					day.setMaximum(31);
				}
			}
		});
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnSearch) {
			Calendar calDate = Calendar.getInstance();
			calDate.set(year.getYear(), (month.getMonth()), day.getYear());

			if (calDate.compareTo(Calendar.getInstance()) >= 0) {
				Calendar selectedDate = Calendar.getInstance();
				selectedDate.set(year.getYear(), month.getMonth(),
						day.getYear());
				Booking b = new Booking(selectedDate,
						(numNights.getSelectedIndex()) + 1);
				if (b.availability().size() > 0) {
					Booking bb = new Booking(selectedDate,
							(numNights.getSelectedIndex()) + 1);
					AvailabilityGUI a = new AvailabilityGUI(usersID, users, selectedDate, (numNights.getSelectedIndex() + 1));
					a.listContent(bb.availability());
					panel.setVisible(false);
					a.setVisible(true);
					a.setBounds(172, 200, 700, 300);
					add(a);
				} else {
					if (JOptionPane
							.showConfirmDialog(
									null,
									"Hotel is fully booked for selected dates. Do you want to try again?",
									"Hotel Booked Out",
									JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
						System.exit(0);
					}

				}

			}
		}
	}

	// Getter and setter to set the loggedIn value to true or false throughout
	// the system
	public static boolean isLoggedIn() {
		return loggedIn;
	}

	public static void setLoggedIn(boolean loggedIna) {
		loggedIn = loggedIna;
	}

	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == 3 || e.getStateChange() == 5
				|| e.getStateChange() == 8 || e.getStateChange() == 10) {
			day.setMaximum(30);
		}

	}
}
