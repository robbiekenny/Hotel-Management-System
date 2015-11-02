package Database;
/**
 * @author Derek Mulhern
 * @author Thomas Murphy
 */
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JOptionPane;

import Model.*;
import Database.*;
import oracle.jdbc.pool.OracleDataSource;

public class RoomOperations {

	private Connection connection;
	private ResultSet rset;
	private Statement stmt;
	private PreparedStatement pstmt;
	Queries q = new Queries();
	private Calendar today;
	private SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
	private String dayString,monthString,yearString;
	private int day,month,year;
	
	//this method takes in the month as an integer and returns the sting representation of it so it can
		//be used to compare departure dates to todays date
		public String getMonth(int month){
			String m = "";
			ArrayList<String> months = new ArrayList<String>();
			months.add("JAN");
			months.add("FEB");
			months.add("MAR");
			months.add("APR");
			months.add("MAY");
			months.add("JUN");
			months.add("JUL");
			months.add("AUG");
			months.add("SEP");
			months.add("OCT");
			months.add("NOV");
			months.add("DEC");
			
			for(int i = 0; i < months.size(); i++){
				m = months.get(month);
			}
			return m;
		}

	/*
	 * This method takes a reference variable of type Room as a parameter and
	 * uses a prepared statement to add the room to the Rooms table in the
	 * database
	 */
	
	//Instead of try catching i added throws exception here 
	//so when you try add the room in the manage rooms it trys to do the below if its succesfull 
	//the method returns true
	//if it fails it doesnt add the room but shows an error dialog 
	public boolean addRoom(Room r) throws SQLException {
		boolean added = false;
			try
			{
				q.open();
			String addRoomSQL = "INSERT INTO Rooms (Room_Number, Room_Availability, Type_ID) VALUES (?,?,?)";
			pstmt = q.getConn().prepareStatement(addRoomSQL);
			pstmt.setInt(1, r.getRoomNumber());
			pstmt.setString(2, Character.toString(r.isRoomAvailability()));
			pstmt.setInt(3, r.getRoomTypeID());
			pstmt.executeUpdate();
			System.out.println("room added");
			added = true;
			}
			catch(Exception ae){
				JOptionPane.showMessageDialog(null, "Room already exists","Error adding room",JOptionPane.WARNING_MESSAGE);
				System.out.println("room already exists ");
//				ae.printStackTrace();
			}
			q.close();
			return added;
	}

	/*
	 * This method updates a particular room in the hotel
	 */
	public void updateRoom(int roomNumber, int roomTypeID) {
		today = Calendar.getInstance(); //checks to see if a booking has passed todays date
		String todaysDate = s.format(today.getTime());
		dayString = todaysDate.substring(0, 2);
		monthString = todaysDate.substring(3, 5);
		yearString =  todaysDate.substring(8, 10);
		
		day = Integer.parseInt(dayString);
		month = Integer.parseInt(monthString);
		month = month -1;			//subtract 1 to get precise month i.e 01 should be 00 to represent Jan
		year = Integer.parseInt(yearString);
		try {
				q.open();
			String sql = "select Room_Number from ROOMBOOKINGS,Bookings where "
					+ "ROOMBOOKINGS.BOOKING_ID = Bookings.BOOKING_ID and ROOM_NUMBER = " + roomNumber
					+ " and BOOKINGS.DEPARTUREDATE >= '" + day + "-" + getMonth(month) + "-" + year + "'";
			
			pstmt = q.getConn().prepareStatement(sql);
			
			rset = pstmt.executeQuery();
			//check to see if room  has a booking on it
			if(rset.next() == true){ //if it does
				JOptionPane.showMessageDialog(null, "Room " + roomNumber + " has a booking on it and cannot be updated",

					"Error Updating Room",JOptionPane.ERROR_MESSAGE);

			}
			else
			{
				//check to see if the room exists because it doesn't have a booking on it when it gets to here
				sql = "select * from ROOMS where ROOM_NUMBER = " + roomNumber;
				
				pstmt = q.getConn().prepareStatement(sql);
				
				rset = pstmt.executeQuery();
				if(rset.next() == true){ //means it does exist
					String updateRoomQuery = "UPDATE Rooms SET Type_ID = " + 
							+ roomTypeID + "WHERE Room_Number = " 
							+ roomNumber;
					stmt = q.getConn().createStatement();
					stmt.executeUpdate(updateRoomQuery);
					JOptionPane.showMessageDialog(null, "Room " + roomNumber + " has been updated","Room updated"
							 ,JOptionPane.INFORMATION_MESSAGE);
					 System.out.println("room updated ");
				}
				else //doesnt exist
				{
					JOptionPane.showMessageDialog(null, "Room " + roomNumber + " does not exist",
							"Error updating room",JOptionPane.ERROR_MESSAGE);
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Please enter a number",
					"Error updating room",JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		q.close();
	}

	// This method takes a room number parameter which is the room number to be
	// deleted. The SQL statement deletes this roomnumber.
	public void deleteRoom(int roomNumber)throws SQLException {
		today = Calendar.getInstance(); //checks to see if a booking has passed todays date
		String todaysDate = s.format(today.getTime());
		dayString = todaysDate.substring(0, 2);
		monthString = todaysDate.substring(3, 5);
		yearString =  todaysDate.substring(8, 10);
		
		day = Integer.parseInt(dayString);
		month = Integer.parseInt(monthString);
		month = month -1;			//subtract 1 to get precise month i.e 01 should be 00 to represent Jan
		year = Integer.parseInt(yearString);
		try
		{
			q.open();
		String sql = "select Room_Number from ROOMBOOKINGS,Bookings where "
				+ "ROOMBOOKINGS.BOOKING_ID = Bookings.BOOKING_ID and ROOM_NUMBER = " + roomNumber
				+ " and BOOKINGS.DEPARTUREDATE >= '" + day + "-" + getMonth(month) + "-" + year + "'";
		
		pstmt = q.getConn().prepareStatement(sql);
		
		rset = pstmt.executeQuery();
		//check to see if room  has a booking on it
		if(rset.next() == true){
			JOptionPane.showMessageDialog(null, "Room " + roomNumber + " has a booking on it and cannot be removed",
				"Error deleting room",JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			//check to see if the room exists because it doesn't have a booking on it when it gets to here
			sql = "select * from ROOMS where ROOM_NUMBER = " + roomNumber;
			
			pstmt = q.getConn().prepareStatement(sql);
			
			rset = pstmt.executeQuery();
			
			if(rset.next() == true){
				sql = "Delete from roombookings where Room_Number = " + roomNumber;
				
				pstmt = q.getConn().prepareStatement(sql);
				pstmt.executeUpdate();
				sql = "Delete from rooms where Room_Number = " + roomNumber;
				
				pstmt = q.getConn().prepareStatement(sql);
				pstmt.executeUpdate();
				JOptionPane.showMessageDialog(null, "Room " + roomNumber + " has been removed",
						"Room removed",JOptionPane.INFORMATION_MESSAGE);
				System.out.println("room " + roomNumber + " removed");
				
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Room " + roomNumber + " does not exist",
						"Error deleting room",JOptionPane.ERROR_MESSAGE);
			}
		}
		}catch(Exception e){
			System.out.println("could not remove room " + e);
		}
		q.close();
	}

	/*
	 * This method returns a ResultSet which holds all the data from the Rooms
	 * table ordered by the room number
	 */
	public ResultSet getRooms() {
		try {
			q.open();
			String queryString = "SELECT * FROM Rooms ORDER BY Room_Number";

			stmt = q.getConn().createStatement();
			rset = stmt.executeQuery(queryString);
		} catch (Exception e) {
			System.out.println(e);
		}
		return rset;
	}

	public ResultSet getLastRow() {
		String addRoomSQL = "SELECT * FROM Rooms ORDER By Room_Number";
		try {
			q.open();
			pstmt = q.getConn().prepareStatement(addRoomSQL,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rset = pstmt.executeQuery();
			rset.last();
			System.out.println(rset.getInt(1) + "," + rset.getString(2) + ","
					+ rset.getInt(3));
		} catch (Exception exc) {
			System.out.println("ERROR:  " + exc.getMessage());
		}
		return rset;
	}

	/*
	 * This method is for testing purposes and prints out the contents of the
	 * room table
	 */
	public void queryRooms() {
		try {
			q.open();
			String roomQuery = "SELECT count(Room_Number) FROM rooms";

			stmt = q.getConn().createStatement();
			rset = stmt.executeQuery(roomQuery);

			while (rset.next()) {
				System.out.println("Database" + rset.getInt(1) + ","
						+ rset.getString(2) + "," + rset.getInt(3));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		q.close();
	}

}
