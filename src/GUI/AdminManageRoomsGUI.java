package GUI;
/**
 * @author Derek Mulhern
 * @author Thomas Murphy
 */
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import Database.*;
import Model.*;

public class AdminManageRoomsGUI extends JPanel implements ActionListener {

	private JComboBox comboBoxRoomType, update_typeCombo;
	private String[] types = { "Single", "Double", "Twin" };
	private Hotel hotel;
	private RoomOperations ro;
	private JButton addbutton, updateButton, deleteRoom;
	private JTextField roomNumberField_ADD, roomNumberField_DELETE,
			roomNumberField_UPDATE;
	private JLabel roomNumber_ADD, roomType, roomNumberLabel_DELETE,
			updateRoomNumberLabel, newTypeLabel_UPDATE;
	private JPanel roomsDetails, addbuttonPanel, addRooms, container,
			deleteRooms, updateRooms;
	private Color color = new Color(227, 99, 26);
	private Font fontBigger;

	public AdminManageRoomsGUI() {
		hotel = new Hotel();
		ro = new RoomOperations();
		this.setLayout(null);
		container = new JPanel();
		container.setLayout(null);
		container.setVisible(true);
		container.setBounds(10, 119, 998, 420);
		add(container);

		fontBigger = new Font("Veranda", Font.PLAIN, 18);

		 //2nd Panel - ADD ROOM
        addRooms = new JPanel();
        addRooms.setBorder(new TitledBorder("Add Rooms"));
        addRooms.setBounds(10, 105, 316, 180);
        container.add(addRooms);
        addRooms.setLayout(null);

        roomsDetails = new JPanel();
        roomsDetails.setBounds(6, 25, 287, 90);
        addRooms.add(roomsDetails);
        roomsDetails.setLayout(null);

        roomNumber_ADD = new JLabel("Room Number");
        roomNumber_ADD.setBounds(0, 4, 132, 26);
        roomNumber_ADD.setFont(fontBigger);
        roomsDetails.add(roomNumber_ADD);

        roomNumberField_ADD = new JTextField();
        roomNumberField_ADD.setBounds(129, 0, 145, 30);
        roomsDetails.add(roomNumberField_ADD);
        
        roomType = new JLabel("Room Type");
        roomType.setBounds(0, 41, 132, 26);
        roomType.setFont(fontBigger);
        roomsDetails.add(roomType);

        comboBoxRoomType = new JComboBox(types);
        comboBoxRoomType.setBounds(129, 38, 145, 30);
        comboBoxRoomType.setFont(fontBigger);
        roomsDetails.add(comboBoxRoomType);

        addbuttonPanel = new JPanel();
        addbuttonPanel.setBounds(6, 116, 265, 56);
        addRooms.add(addbuttonPanel);
        addbuttonPanel.setLayout(null);

        addbutton = new JButton("Add");
        addbutton.setBounds(73, 6, 118, 30);
        addbutton.setFont(fontBigger);
        addbutton.setBackground(color);
        addbuttonPanel.add(addbutton);
        addbutton.addActionListener(this);

		// 3rd Panel - DELETE ROOM
		deleteRooms = new JPanel();
		deleteRooms.setBorder(new TitledBorder("Delete Rooms"));
		deleteRooms.setBounds(662, 105, 304, 180);
		deleteRooms.setLayout(null);
		container.add(deleteRooms);

		roomNumberLabel_DELETE = new JLabel("Room Number");
		roomNumberLabel_DELETE.setFont(fontBigger);
		roomNumberLabel_DELETE.setBounds(10, 32, 135, 23);
		deleteRooms.add(roomNumberLabel_DELETE);

		roomNumberField_DELETE = new JTextField();
		roomNumberField_DELETE.setBounds(145, 25, 145, 30);
		deleteRooms.add(roomNumberField_DELETE);
		roomNumberField_DELETE.setColumns(10);

		deleteRoom = new JButton("Delete");
		deleteRoom.setBounds(79, 126, 118, 30);
		deleteRooms.add(deleteRoom);
		deleteRoom.addActionListener(this);
		deleteRoom.setBackground(color);
		deleteRoom.setFont(fontBigger);

		// 4th Panel - UPDATE ROOM
		updateRooms = new JPanel();
		updateRooms.setBorder(new TitledBorder("Update Room"));
		updateRooms.setBounds(335, 105, 316, 180);
		container.add(updateRooms);
		updateRooms.setLayout(null);

		updateButton = new JButton("Update");
		updateButton.setBackground(color);
		updateButton.setFont(fontBigger);
		updateButton.setBounds(90, 123, 118, 30);
		updateRooms.add(updateButton);
		updateButton.addActionListener(this);

		updateRoomNumberLabel = new JLabel("Room Number");
		updateRoomNumberLabel.setFont(fontBigger);
		updateRoomNumberLabel.setBounds(10, 33, 123, 23);
		updateRooms.add(updateRoomNumberLabel);

		roomNumberField_UPDATE = new JTextField();
		roomNumberField_UPDATE.setBounds(153, 25, 145, 30);
		roomNumberField_UPDATE.setColumns(10);
		updateRooms.add(roomNumberField_UPDATE);

		newTypeLabel_UPDATE = new JLabel("Room Type");
		newTypeLabel_UPDATE.setFont(fontBigger);
		newTypeLabel_UPDATE.setBounds(10, 70, 133, 23);
		updateRooms.add(newTypeLabel_UPDATE);

		update_typeCombo = new JComboBox(types);
		update_typeCombo.setFont(fontBigger);
		update_typeCombo.setBounds(153, 66, 145, 30);
		updateRooms.add(update_typeCombo);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addbutton) {
			try {
				int roomNumber = 0;
				if (roomNumberField_ADD.getText().contains("-")) {
					JOptionPane.showMessageDialog(null,
							"Please enter a number", "Error adding room",
							JOptionPane.WARNING_MESSAGE);
				} else {
					roomNumber = Integer
							.parseInt(roomNumberField_ADD.getText());
					char roomAvailability = 'T';
					int roomTypeID = comboBoxRoomType.getSelectedIndex();

					if (comboBoxRoomType.getSelectedIndex() == 0)
						roomTypeID = 900;
					if (comboBoxRoomType.getSelectedIndex() == 1)
						roomTypeID = 901;
					if (comboBoxRoomType.getSelectedIndex() == 2)
						roomTypeID = 902;

					hotel.addRoom();
					Room r = new Room(roomNumber, roomAvailability, roomTypeID);

					try {
						if (ro.addRoom(r) == true) {
							JOptionPane.showMessageDialog(null,
									"Room " + r.getRoomNumber() + " added",
									"Room added",
									JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}

			} catch (NumberFormatException se) {
				JOptionPane.showMessageDialog(null, "Please enter a number",
						"Error adding room", JOptionPane.WARNING_MESSAGE);

			}

		}

		else if (e.getSource() == updateButton) {
			try {
				int roomNumber = Integer.parseInt(roomNumberField_UPDATE
						.getText());
				int roomTypeID = update_typeCombo.getSelectedIndex();
				if (update_typeCombo.getSelectedIndex() == 0)
					roomTypeID = 900;
				else if (update_typeCombo.getSelectedIndex() == 1)
					roomTypeID = 901;
				else if (update_typeCombo.getSelectedIndex() == 2)
					roomTypeID = 902;
				ro.updateRoom(roomNumber, roomTypeID);
			} catch (NumberFormatException ae) {
				JOptionPane.showMessageDialog(null, "Please enter a number",
						"Error updating room", JOptionPane.WARNING_MESSAGE);
			}

		} else if (e.getSource() == deleteRoom) {

			try {
				int roomNumber = Integer.parseInt(roomNumberField_DELETE
						.getText());
				try {
					ro.deleteRoom(roomNumber);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null,
						"Please enter a valid number", "Error Deleting Room",
						JOptionPane.WARNING_MESSAGE);

			}
		}
	}
}
