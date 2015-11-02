 package GUI;
/**
 * @author Mark Lordan
 * @author Robert Kenny
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.swing.*;

import Database.CreateTables;
import Database.Queries;
import Database.ManageBookingOperations;
import Model.Booking;

import com.toedter.calendar.JCalendar;

import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class EditBookingGUI extends JPanel implements ActionListener {
	private JPanel edit, addRooms;
	private JComboBox numNightsCombo;
	private ArrayList<Object[]> availableList, availableListRight;
	private Object[][] array2dLeft, array2dRight;
	private int bookingid,numNightsCounter;
	private double price, priceprice;
	private int numGuests, numRooms, numNights;
	private Calendar arrival, departure, currentDate, maxDate;
	private JTextField day;
	private JTextField month;
	private JTextField year;
	private JTextField totalCostField;
	private Color color = new Color(227, 99, 26);
	private String usersID;
	private JTable tableLeft, tableRight;
	private JButton btnCancel, btnSaveChanges, btnAdd, addRow, back, removeRow,
			saveBtn;
	private JTextField newArrivalDay, newArrivalMonth, newArrivalYear,
			txtTotalCost;
	private DefaultTableModel model, modelLeft;
	private final JCalendar calSelector;
	private Queries q;
	/*
	 * extra panel is created inside action performed
	 * used for swapping rooms to/from booking
	 * 2 tables and 2 models used
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public EditBookingGUI(String usersID, int bookingid, int numGuests,
			int numRooms, int numNights, double price, Date arrivalD,
			Date departureD) {
		System.out.println(bookingid + " " + numGuests + " " + numRooms + " "
				+ price + " " + arrivalD + " " + departureD);
		this.usersID = usersID;
		this.numNights = numNights;
		this.bookingid = bookingid;
		this.numGuests = numGuests;
		this.numRooms = numRooms;
		this.price = price;
		arrival = Calendar.getInstance();
		arrival.setTime(arrivalD);
		departure = Calendar.getInstance();
		departure.setTime(departureD);
		setLayout(null);
		edit = new JPanel();
		edit.setBorder(new TitledBorder(null,
				"Change booking details - Booking ID: " + this.bookingid,
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		edit.setBounds(128, 225, 745, 294);
		add(edit);
		edit.setLayout(null);

		System.out.println(this.bookingid + " " + this.numGuests + " "
				+ this.numRooms + " " + this.price + " " + arrival.getTime()
				+ " " + departure.getTime());

		JPanel leftPanel = new JPanel();
		leftPanel.setBounds(155, 35, 260, 200);
		edit.add(leftPanel);

		this.bookingid = bookingid;

		JLabel lblNumberOfNights = new JLabel("Number of Nights:                             ");
		leftPanel.add(lblNumberOfNights);

		numNightsCombo = new JComboBox(new DefaultComboBoxModel(new Integer[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14}));
		numNightsCombo.setSelectedIndex(numNights - 1);	
		numNightsCombo.addActionListener(this);
		numNightsCounter = (numNightsCombo.getSelectedIndex() +1);
		leftPanel.add(numNightsCombo);

		JLabel lblArrivalDate = new JLabel("Original Arrival Date:  ");
		leftPanel.add(lblArrivalDate);

		day = new JTextField();
		day.setEditable(false);
		leftPanel.add(day);
		day.setColumns(2);

		month = new JTextField("");
		month.setEditable(false);
		leftPanel.add(month);
		month.setColumns(3);

		year = new JTextField("2014");
		year.setEditable(false);
		leftPanel.add(year);
		year.setColumns(3);
		day.setText(Integer.toString(arrival.get(Calendar.DAY_OF_MONTH)));
		month.setText(dateConverter(arrival.get(Calendar.MONTH)));
		year.setText(Integer.toString(arrival.get(Calendar.YEAR)));
		JLabel lblNewTotalCost = new JLabel(
				"New Total Cost:                        ");
		leftPanel.add(lblNewTotalCost);

		totalCostField = new JTextField();
		totalCostField.setEditable(false);
		leftPanel.add(totalCostField);
		totalCostField.setColumns(6);
		totalCostField.setText(Double.toString(price));

		btnAdd = new JButton("Add/Remove rooms");
		btnAdd.setBackground(color);
		btnAdd.addActionListener(this);
		leftPanel.add(btnAdd);

		JPanel rigthPanel = new JPanel();
		rigthPanel.setBounds(415, 35, 260, 200);
		edit.add(rigthPanel);
		
		calSelector = new JCalendar();
		calSelector.setDate(arrivalD);
		currentDate = Calendar.getInstance();
		calSelector.setMinSelectableDate(currentDate.getTime());
		currentDate.add(Calendar.DAY_OF_MONTH, -1);
		maxDate = Calendar.getInstance();
		maxDate.add(Calendar.YEAR, 2);
		calSelector.setMaxSelectableDate(maxDate.getTime());

		rigthPanel.add(calSelector);

		JLabel lblNewArrivalDate = new JLabel("New Arrival Date: ");
		rigthPanel.add(lblNewArrivalDate);

		newArrivalDay = new JTextField();
		newArrivalDay.setEditable(false);
		newArrivalDay.setColumns(2);
		rigthPanel.add(newArrivalDay);

		newArrivalMonth = new JTextField((String) null);
		newArrivalMonth.setEditable(false);
		newArrivalMonth.setColumns(3);
		rigthPanel.add(newArrivalMonth);

		newArrivalYear = new JTextField("");
		newArrivalYear.setEditable(false);
		newArrivalYear.setColumns(3);
		rigthPanel.add(newArrivalYear);
		calSelector.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent p) {
				if (calSelector.getDate().after(currentDate.getTime())
						&& calSelector.getDate().before(maxDate.getTime())) {
					newArrivalDay.setText(Integer.toString(calSelector
							.getDayChooser().getDay()));
					newArrivalMonth.setText(dateConverter(calSelector
							.getMonthChooser().getMonth()));
					newArrivalYear.setText(Integer.toString(calSelector
							.getYearChooser().getYear()));
				}

			}

		});

		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(230, 260, 100, 25);
		btnCancel.addActionListener(this);
		btnCancel.setBackground(color);
		edit.add(btnCancel);

		btnSaveChanges = new JButton("Save Changes");
		btnSaveChanges.setBackground(color);
		btnSaveChanges.addActionListener(this);
		btnSaveChanges.setBounds(470, 260, 150, 25);
		edit.add(btnSaveChanges);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnCancel)) {
			ManageBookingGUI m = new ManageBookingGUI(usersID);
			edit.setVisible(false);
			m.setVisible(true);
			add(m);
		}
		// new panel that holds JTables for rooms
		//possibly put this in a separate class
		if (e.getSource().equals(btnAdd)) {
			addRooms = new JPanel();
			addRooms.setBorder(new TitledBorder(null,
					"Change booking details - Booking ID: " + this.bookingid,
					TitledBorder.LEADING, TitledBorder.TOP, null, null));
			addRooms.setBounds(128, 225, 745, 294);
			edit.setVisible(false);
			addRooms.setLayout(null);
			addRooms.setVisible(true);
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(30, 40, 200, 170);
			addRooms.add(scrollPane);
			Object[] columnNames = { "Room Number", "Room Type", "Room Price" };
			getAvailableRooms("left");
			modelLeft = new DefaultTableModel(array2dLeft, columnNames){
				 @Override
				    public boolean isCellEditable(int row, int column) {
				       //all cells false
				       return false;
				    }
			};

			tableLeft = new JTable(modelLeft);
			tableLeft.setBorder(null);
			tableLeft.removeColumn(tableLeft.getColumnModel().getColumn(0));
			tableLeft.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableLeft.getTableHeader().setReorderingAllowed(false);
			scrollPane.setViewportView(tableLeft);
			add(addRooms);

			back = new JButton("Back");
			back.setBackground(color);
			back.setBounds(65, 220, 120, 25);
			back.addActionListener(this);
			addRooms.add(back);

			saveBtn = new JButton("Save Changes");
			saveBtn.setBackground(color);
			saveBtn.setBounds(560, 220, 120, 25);
			saveBtn.addActionListener(this);
			addRooms.add(saveBtn);

			addRow = new JButton("Add Room >>");
			addRow.addActionListener(this);
			addRow.setBackground(color);
			addRow.setBounds(300, 40, 140, 25);
			addRooms.add(addRow);

			removeRow = new JButton("<< Remove Room");
			removeRow.addActionListener(this);
			removeRow.setBackground(color);
			removeRow.setBounds(300, 185, 140, 25);
			addRooms.add(removeRow);

			JScrollPane scrollPaneRight = new JScrollPane();
			scrollPaneRight.setBounds(520, 40, 200, 170);
			addRooms.add(scrollPaneRight);

			getAvailableRooms("right");
			model = new DefaultTableModel(array2dRight, columnNames){
				 @Override
				    public boolean isCellEditable(int row, int column) {
				       //all cells false
				       return false;
				    }
			};
			tableRight = new JTable(model);
			tableRight.setBorder(null);
			tableRight.removeColumn(tableRight.getColumnModel().getColumn(0));
			tableRight.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableRight.getTableHeader().setReorderingAllowed(false);
			scrollPaneRight.setViewportView(tableRight);

			JLabel totalCostlbl = new JLabel("Total Cost:  €");
			totalCostlbl.setBounds(300, 250, 110, 25);
			addRooms.add(totalCostlbl);

			txtTotalCost = new JTextField();
			txtTotalCost.setEditable(false);
			txtTotalCost.setBounds(390, 250, 50, 25);
			txtTotalCost.setText(getTotalCost());
			addRooms.add(txtTotalCost);
			txtTotalCost.setColumns(6);
		}
		if(e.getSource().equals(numNightsCombo)){
			q = new Queries();
			System.out.println( numNightsCounter + " IS NUMnIGHTS");
	    	int tempCounter = (numNightsCombo.getSelectedIndex() + 1);
	    	System.out.println(tempCounter);
	    	priceprice = q.getTotalPrice(bookingid, numNightsCounter, tempCounter);
	    	numNightsCounter = tempCounter;   	
	    	System.out.println(priceprice + "");
	        totalCostField.setText(Double.toString(priceprice));
		}
		if (e.getSource().equals(back)) {
			if (JOptionPane.showConfirmDialog(
							null,
							"Are you sure you want to cancel? Any changes made will not be saved",
							"Cancel editing a booking",
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				ManageBookingGUI m = new ManageBookingGUI(usersID);
				m.setVisible(true);
				addRooms.setVisible(false);
				add(m);
			} else {

			}
		}

		// Following code is for swapping rooms from left JTable to right JTable
		if (e.getSource().equals(addRow)) {
			if (tableLeft.getSelectedRow() == -1) { // if no row selected
				JOptionPane.showMessageDialog(null, "No room selected to add",
						"Edit Booking error", JOptionPane.OK_OPTION);
			} else {
				Object[] newRow = {
						modelLeft.getValueAt(tableLeft.getSelectedRow(), 0),
						modelLeft.getValueAt(tableLeft.getSelectedRow(), 1),
						modelLeft.getValueAt(tableLeft.getSelectedRow(), 2) };
				model.insertRow(tableRight.getRowCount(), newRow);
				modelLeft.removeRow(tableLeft.getSelectedRow());
				txtTotalCost.setText(getTotalCost());
			}
		}
		if (e.getSource().equals(removeRow)) {
			if (tableRight.getSelectedRow() == -1) { // if no row selected
				JOptionPane.showMessageDialog(null,
						"No room selected to remove", "Edit Booking error",
						JOptionPane.OK_OPTION);
			} else {
				Object[] newRow = {
						model.getValueAt(tableRight.getSelectedRow(), 0),
						model.getValueAt(tableRight.getSelectedRow(), 1),
						model.getValueAt(tableRight.getSelectedRow(), 2) };
				modelLeft.insertRow(tableLeft.getRowCount(), newRow);
				model.removeRow(tableRight.getSelectedRow());
				txtTotalCost.setText(getTotalCost());
			}
		}
		if (e.getSource().equals(saveBtn)) {
			if (JOptionPane
					.showConfirmDialog(
							null,
							"Your room choice will be overridden for your booking. Do you wish to continue?",
							"Save Changes", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				System.out.println("Preparing SQL query...");
				double cost = Double.valueOf(txtTotalCost.getText());
				CreateTables c = new CreateTables();

				System.out.println(model.getRowCount());
				ArrayList<Integer> roomsChosen = new ArrayList<Integer>();
				for (int i = 0; i < model.getRowCount(); i++) {
					roomsChosen.add((int) model.getValueAt(i, 0));
					System.out.println(roomsChosen.get(i));
				}
				for (int i = 0; i < array2dRight.length; i++) {

				}
				Booking b = new Booking(bookingid, cost);
				if(roomsChosen.size() > 0){ //check to see if users new room choice is greater than 0
				c.updateBookingRooms(b, roomsChosen);
				JOptionPane.showMessageDialog(null, "Your changes to booking  "
						+ bookingid + " have been saved",
						"Booking Successfully Changed", JOptionPane.OK_OPTION);
				ManageBookingGUI m = new ManageBookingGUI(usersID);
				m.setVisible(true);
				addRooms.setVisible(false);
				add(m);
				}
				else{
					JOptionPane.showMessageDialog(null, "You must have at least 1 room in your booking",
							"Booking Error", JOptionPane.OK_OPTION);
				}
			}
		}
		// end of JTable code
		if (e.getSource().equals(btnSaveChanges)) {
			if (JOptionPane
					.showConfirmDialog(
							null,
							"Your previous booking details will be overriden. Do you wish to continue?",
							"Save Changes", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				int chosenNumNights = (numNightsCombo.getSelectedIndex() +1);
				Calendar newArrival = Calendar.getInstance();
				System.out.println(calSelector.getDate() + " is the date I'm given, I think it should be different");
				newArrival.setTime(calSelector.getDate());
				System.out.println(newArrival.getTime());
				System.out.println(newArrival.get(Calendar.DAY_OF_MONTH) + " " + (newArrival.get(Calendar.MONTH) +1)+ " " + newArrival.get(Calendar.YEAR));
				Booking b = new Booking();
				b.setBookingID(bookingid);
				b.setNumNights(chosenNumNights);
				b.setTotalCost(Double.valueOf(totalCostField.getText()));
				CreateTables c = new CreateTables();
				c.updateBookingDates(b, newArrival );
				ManageBookingGUI m = new ManageBookingGUI(usersID);
				edit.setVisible(false);
				m.setVisible(true);
				add(m);
			}
		}
	}

	private void getAvailableRooms(String leftORright) {
		Queries q = new Queries();
		if (leftORright.equals("left")) {
			availableList = new ArrayList<Object[]>(q.editBookings(arrival,
					numNights, usersID));
			array2dLeft = availableList
					.toArray(new Object[availableList.size()][]);
		} else {
			availableListRight = new ArrayList<Object[]>(
					q.getUserBookingInfo(bookingid));
			array2dRight = availableListRight
					.toArray(new Object[availableListRight.size()][]);
		}
	}

	public String dateConverter(int monthIndex) {
		String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
				"Aug", "Sep", "Oct", "Nov", "Dec" };
		return months[monthIndex];
	}
	
	/*Method below is used for total cost
	 * booking might have a special added to it
	 * cant just * numnights 
	 * need to minus special cost then do maths
	 */
	public String getTotalCost() {
		ManageBookingOperations m = new ManageBookingOperations();
		double specialDiff = m.getSpecialCost(bookingid);
		String cost;
		double dubCost = 0;
		for (int i = 0; i < model.getRowCount(); i++) {
			dubCost = dubCost + (double) model.getValueAt(i, 2);
		}
		dubCost = dubCost * numNights;
		dubCost = dubCost + specialDiff;
		cost = Double.toString(dubCost);
		return cost;

	}

}
