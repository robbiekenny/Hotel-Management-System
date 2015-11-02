package Database;
/**
 * @author Derek Mulhern
 * @author Robert Kenny 
 * @author Thomas Murphy
 * @author Mark Lordan
 */
import java.sql.*;
import java.sql.Date;
import java.util.*;

import javax.swing.JOptionPane;

import Model.*;
import oracle.jdbc.pool.OracleDataSource;

public class CreateTables {

	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rset;
	private Queries q = new Queries();
	private Hotel h;
	private String dayString, monthString, yearString, dayString2,
	monthString2, yearString2;
	private int day, month, year, day2, month2, year2;

	/*
	 * This method helps out when inserting dates into the database using
	 * prepared statements that include sequences ** Reference Ms. Patricia Magee **
	 */
	public java.sql.Date convertDate(int day, int month, int year) {
		GregorianCalendar cal = (GregorianCalendar) Calendar.getInstance();
		cal.clear();
		cal.set(year, month, day);
		java.sql.Date releaseDate = new java.sql.Date(cal.getTime().getTime());
		return releaseDate;
	}

	public void buildTitanFallTables() {
		try {
			q.open();
			stmt = q.getConn().createStatement();

			// USERS TABLE
			stmt.executeUpdate("CREATE TABLE Users "
					+ "(User_ID	varchar2(50) NOT NULL PRIMARY KEY, UserType varchar2(1) CHECK (UserType IN ('G','A')), First_Name varchar2(50), Last_Name varchar2(50), HomeAddress varchar2(255), Phone_Number varchar2(50), Email_Address varchar2(50), UserPassword varchar2(50) NOT NULL)");

			String sqlInsert = "INSERT INTO users VALUES (?,?,?,?,?,?,?,?)";
			pstmt = q.getConn().prepareStatement(sqlInsert);

			// USERS Insert row #1.
			pstmt.setString(1, "01");
			pstmt.setString(2, "G");
			pstmt.setString(3, "Derek");
			pstmt.setString(4, "Mulhern");
			pstmt.setString(5, "Celbridge");
			pstmt.setString(6, "088123456");
			pstmt.setString(7, "delpeter@gmail.com");
			pstmt.setString(8, "9zFn82OjhKk=");
			pstmt.executeUpdate();

			// USERS Insert row #2.
			pstmt.setString(1, "02");
			pstmt.setString(2, "G");
			pstmt.setString(3, "Robert");
			pstmt.setString(4, "Kenny");
			pstmt.setString(5, "101 The Jacks");
			pstmt.setString(6, "088123457");
			pstmt.setString(7, "robertkenny@gmail.com");
			pstmt.setString(8, "0+nu06G9r0o=");
			pstmt.executeUpdate();

			// USERS Insert row #3.
			pstmt.setString(1, "03");
			pstmt.setString(2, "G");
			pstmt.setString(3, "Mark");
			pstmt.setString(4, "Lordan");
			pstmt.setString(5, "121 The Whatever");
			pstmt.setString(6, "088123458");
			pstmt.setString(7, "marklordan@gmail.com");
			pstmt.setString(8, "GVb3hMSMrVM=");
			pstmt.executeUpdate();

			// USERS Insert row #4.
			pstmt.setString(1, "04");
			pstmt.setString(2, "G");
			pstmt.setString(3, "Thomas");
			pstmt.setString(4, "Murphy");
			pstmt.setString(5, "7 The Pub");
			pstmt.setString(6, "088123459");
			pstmt.setString(7, "thomasmurphy@gmail.com");
			pstmt.setString(8, "bua6oR4xy/M=");
			pstmt.executeUpdate();

			// USERS Insert row #5.
			pstmt.setString(1, "05");
			pstmt.setString(2, "A");
			pstmt.setString(3, "Eileen");
			pstmt.setString(4, "Costello");
			pstmt.setString(5, "88 The Titanfall");
			pstmt.setString(6, "088123460");
			pstmt.setString(7, "eileencostello@gmail.com");
			pstmt.setString(8, "cz/uuFILsAU=");
			pstmt.executeUpdate();

			// HOTELS TABLE
			stmt.executeUpdate("CREATE TABLE Hotels "
					+ "(Hotel_ID number NOT NULL PRIMARY KEY, Hotel_Name varchar2(50), Hotel_PhoneNumber varchar2(50), Hotel_Address varchar2(50), NumOfRooms number, HotelRating number)");

			stmt.executeUpdate("CREATE SEQUENCE hotel_seq start with 2222 increment by 2222");

			String hotelInsert = "INSERT INTO hotels VALUES(hotel_seq.nextval,?,?,?,?,?)";
			pstmt = q.getConn().prepareStatement(hotelInsert);

			// HOTELS Insert row #1.
			pstmt.setString(1, "TitanFall Tower Hotel");
			pstmt.setString(2, "087998877");
			pstmt.setString(3, "100 Star Living Street");
			pstmt.setInt(4, 15);
			pstmt.setInt(5, 5);
			pstmt.executeUpdate();

			// BOOKINGS TABLE
			stmt.executeUpdate("CREATE TABLE Bookings "
					+ "(Booking_ID number NOT NULL PRIMARY KEY, Number_Of_Guests number NOT NULL, Number_Of_Nights number NOT NULL, Number_Of_Rooms number NOT NULL, Total_Cost number(6,2), "
					+ "ArrivalDate Date, DepartureDate Date, Hotel_ID number, User_ID varchar2(50), FOREIGN KEY (Hotel_ID) REFERENCES hotels (Hotel_ID) ON DELETE CASCADE, FOREIGN KEY (User_ID) REFERENCES users (User_ID) ON DELETE CASCADE)");

			stmt.executeUpdate("CREATE SEQUENCE booking_seq start with 500 increment by 1");

			String bookingInsert = "INSERT INTO bookings VALUES(booking_seq.nextval,?,?,?,?,?,?,hotel_seq.currval,?)";
			pstmt = q.getConn().prepareStatement(bookingInsert);

			// BOOKINGS Insert row #1.
			pstmt.setInt(1, 2);
			pstmt.setInt(2, 1);
			pstmt.setInt(3, 1);
			pstmt.setDouble(4, 59);
			pstmt.setDate(5, convertDate(1, 0, 2015)); // 0 represents January,
														// 1 is February etc.
			pstmt.setDate(6, convertDate(2, 0, 2015));
			pstmt.setString(7, "01");
			pstmt.executeUpdate();

			// ROOMTYPE TABLE
			stmt.executeUpdate("CREATE TABLE Roomtypes "
					+ "(Type_ID number NOT NULL PRIMARY KEY, Type_Name varchar2(50), RoomType_Price number(5,2))");

			stmt.executeUpdate("CREATE SEQUENCE roomType_seq start with 900 increment by 1");

			String roomTypeInsert = "INSERT INTO roomtypes values(roomType_seq.nextval,?,?)";
			pstmt = q.getConn().prepareStatement(roomTypeInsert);

			// ROOMTYPE Insert row #1
			pstmt.setString(1, "Single");
			pstmt.setDouble(2, 59);
			pstmt.executeUpdate();

			// ROOMS TABLE
			stmt.executeUpdate("CREATE TABLE Rooms "
					+ "(Room_Number number NOT NULL PRIMARY KEY, Room_Availability char(1) CHECK (Room_Availability IN('T','F')), Type_ID number, FOREIGN KEY (Type_ID) REFERENCES roomtypes (Type_ID) ON DELETE CASCADE)");

			stmt.executeUpdate("CREATE SEQUENCE room_seq start with 1101 increment by 1");

			String roomInsert = "INSERT INTO rooms values(room_seq.nextval,?,roomType_seq.currval)";
			pstmt = q.getConn().prepareStatement(roomInsert);

			// ROOMS Insert row #1.
			pstmt.setString(1, "T");
			pstmt.executeUpdate();

			// ROOMBOOKINGS TABLE
			stmt.executeUpdate("CREATE TABLE RoomBookings "
					+ "(Room_Number number NOT NULL, Booking_ID number NOT NULL, DateOfBooking date, PRIMARY KEY(Room_Number, Booking_ID), FOREIGN KEY (Room_Number) REFERENCES rooms (Room_Number) ON DELETE CASCADE, FOREIGN KEY (Booking_ID) REFERENCES bookings (Booking_ID) ON DELETE CASCADE)");

			String roomBookingsInsert = "INSERT INTO roombookings VALUES(room_seq.currval, booking_seq.currval, ?)";
			pstmt = q.getConn().prepareStatement(roomBookingsInsert);

			// ROOMBOOKINGS Insert row #1.
			pstmt.setDate(1, convertDate(28, 1, 2014));
			pstmt.executeUpdate();

			// SPECIALS TABLE
			stmt.executeUpdate("CREATE TABLE Specials "
					+ "(Special_ID number NOT NULL PRIMARY KEY, Special_Name varchar2(50), Special_Cost number(5,2))");

			stmt.executeUpdate("CREATE SEQUENCE special_seq start with 11 increment by 11");

			String specialInsert = "INSERT INTO specials VALUES(special_seq.nextval,?,?)";
			pstmt = q.getConn().prepareStatement(specialInsert);

			// SPECIALS Insert row #1.
			pstmt.setString(1, "Golf");
			pstmt.setDouble(2, 100);
			pstmt.executeUpdate();

			String roomInsert2 = "INSERT INTO rooms values(room_seq.nextval,?,roomType_seq.currval)";
			pstmt = q.getConn().prepareStatement(roomInsert2);

			// ROOMS Insert row #2.
			pstmt.setString(1, "T");
			pstmt.executeUpdate();

			// ROOMS Insert row #3.
			pstmt.setString(1, "T");
			pstmt.executeUpdate();

			// ROOMS Insert row #4.
			pstmt.setString(1, "T");
			pstmt.executeUpdate();

			String bookingInsert2 = "INSERT INTO bookings VALUES(booking_seq.nextval,?,?,?,?,?,?,hotel_seq.currval,?)";
			pstmt = q.getConn().prepareStatement(bookingInsert2);

			// BOOKINGS Insert row #2.
			pstmt.setInt(1, 1);
			pstmt.setInt(2, 2);
			pstmt.setInt(3, 1);
			pstmt.setDouble(4, 118);
			pstmt.setDate(5, convertDate(2, 0, 2016)); // 0 represents January
			pstmt.setDate(6, convertDate(3, 0, 2016)); // 0 represents January
			pstmt.setString(7, "01");
			pstmt.executeUpdate();

			String roomInsert3 = "INSERT INTO rooms values(room_seq.nextval,?,roomType_seq.currval)";
			pstmt = q.getConn().prepareStatement(roomInsert3);

			// ROOMS Insert row #5.
			pstmt.setString(1, "T");
			pstmt.executeUpdate();

			String roomTypeInsert3 = "INSERT INTO roomtypes values(roomType_seq.nextval,?,?)";
			pstmt = q.getConn().prepareStatement(roomTypeInsert3);

			// ROOMTYPES Insert row 2
			pstmt.setString(1, "Double");
			pstmt.setDouble(2, 99);
			pstmt.executeUpdate();

			String roomBookingsInsert2 = "INSERT INTO roombookings VALUES(room_seq.currval,booking_seq.currval,?)";
			pstmt = q.getConn().prepareStatement(roomBookingsInsert2);

			// ROOMBOOKINGS Insert row #2.
			pstmt.setDate(1, convertDate(17, 10, 2013));
			pstmt.executeUpdate();

			String specialInsert2 = "INSERT INTO specials VALUES(special_seq.nextval,?,?)";
			pstmt = q.getConn().prepareStatement(specialInsert2);

			// SPECIALS Insert row #2.
			pstmt.setString(1, "Spa Treatment");
			pstmt.setDouble(2, 150);
			pstmt.executeUpdate();

			String roomInsert4 = "INSERT INTO rooms values(room_seq.nextval,?,roomType_seq.currval)";
			pstmt = q.getConn().prepareStatement(roomInsert4);

			// ROOMS Insert row #6.
			pstmt.setString(1, "T");
			pstmt.executeUpdate();
			// ROOMS Insert row #7.
			pstmt.setString(1, "T");
			pstmt.executeUpdate();

			String bookingInsert3 = "INSERT INTO bookings VALUES(booking_seq.nextval,?,?,?,?,?,?,hotel_seq.currval,?)";
			pstmt = q.getConn().prepareStatement(bookingInsert3);

			// BOOKINGS Insert row #3.
			pstmt.setInt(1, 4);
			pstmt.setInt(2, 5);
			pstmt.setInt(3, 1);
			pstmt.setDouble(4, 495);
			pstmt.setDate(5, convertDate(02, 7, 2014)); // 7 represents August
			pstmt.setDate(6, convertDate(7, 7, 2014)); // 7 represents August
			pstmt.setString(7, "01");
			pstmt.executeUpdate();

			String roomInsert5 = "INSERT INTO rooms values(room_seq.nextval,?,roomType_seq.currval)";
			pstmt = q.getConn().prepareStatement(roomInsert5);
			// ROOMS Insert row #8.
			pstmt.setString(1, "T");
			pstmt.executeUpdate();

			String roomBookingsInsert3 = "INSERT INTO roombookings VALUES(room_seq.currval,booking_seq.currval,?)";
			pstmt = q.getConn().prepareStatement(roomBookingsInsert3);

			// ROOMBOOKINGS Insert row #3.
			pstmt.setDate(1, convertDate(3, 11, 2014));
			pstmt.executeUpdate();

			String specialInsert3 = "INSERT INTO specials VALUES(special_seq.nextval,?,?)";
			pstmt = q.getConn().prepareStatement(specialInsert3);

			// SPECIALS Insert row #3.
			pstmt.setString(1, "Breakfast");
			pstmt.setDouble(2, 19.99);
			pstmt.executeUpdate();

			String roomInsert6 = "INSERT INTO rooms values(room_seq.nextval,?,roomType_seq.currval)";
			pstmt = q.getConn().prepareStatement(roomInsert6);

			// ROOMS Insert row #9.
			pstmt.setString(1, "T");
			pstmt.executeUpdate();

			// ROOMS Insert row #10.
			pstmt.setString(1, "T");
			pstmt.executeUpdate();

			String bookingInsert4 = "INSERT INTO bookings VALUES(booking_seq.nextval,?,?,?,?,?,?,hotel_seq.currval,?)";
			pstmt = q.getConn().prepareStatement(bookingInsert4);

			// BOOKINGS Insert row #4.
			pstmt.setInt(1, 2);
			pstmt.setInt(2, 2);
			pstmt.setInt(3, 1);
			pstmt.setDouble(4, 99);
			pstmt.setDate(5, convertDate(20, 1, 2015)); // 1 represents February
			pstmt.setDate(6, convertDate(22, 1, 2015)); // 1 represents February
			pstmt.setString(7, "02");
			pstmt.executeUpdate();

			String roomBookingsInsert4 = "INSERT INTO roombookings VALUES(room_seq.currval,booking_seq.currval,?)";
			pstmt = q.getConn().prepareStatement(roomBookingsInsert4);

			// ROOMBOOKINGS Insert row #4.
			pstmt.setDate(1, convertDate(21, 4, 2014));
			pstmt.executeUpdate();

			String specialInsert4 = "INSERT INTO specials VALUES(special_seq.nextval,?,?)";
			pstmt = q.getConn().prepareStatement(specialInsert4);

			// SPECIALS Insert row #4.
			pstmt.setString(1, "Go-karting");
			pstmt.setDouble(2, 50);
			pstmt.executeUpdate();

			String roomTypeInsert4 = "INSERT INTO roomtypes values(roomType_seq.nextval,?,?)";
			pstmt = q.getConn().prepareStatement(roomTypeInsert4);

			// ROOMTYPES Insert row 3
			pstmt.setString(1, "Twin");
			pstmt.setDouble(2, 199);
			pstmt.executeUpdate();

			String roomInsert7 = "INSERT INTO rooms values(room_seq.nextval,?,roomType_seq.currval)";
			pstmt = q.getConn().prepareStatement(roomInsert7);

			// ROOMS Insert row #11.
			pstmt.setString(1, "T");
			pstmt.executeUpdate();
			// ROOMS Insert row #12.
			pstmt.setString(1, "T");
			pstmt.executeUpdate();
			// ROOMS Insert row #13.
			pstmt.setString(1, "T");
			pstmt.executeUpdate();

			String bookingInsert5 = "INSERT INTO bookings VALUES(booking_seq.nextval,?,?,?,?,?,?,hotel_seq.currval,?)";
			pstmt = q.getConn().prepareStatement(bookingInsert5);

			// BOOKINGS Insert row #5.
			pstmt.setInt(1, 2);
			pstmt.setInt(2, 7);
			pstmt.setInt(3, 1);
			pstmt.setDouble(4, 1393);
			pstmt.setDate(5, convertDate(29, 10, 2014)); // 10 represents November
			pstmt.setDate(6, convertDate(6, 11, 2014)); // 11 represents December
			pstmt.setString(7, "03");
			pstmt.executeUpdate();

			String roomBookingsInsert5 = "INSERT INTO roombookings VALUES(room_seq.currval,booking_seq.currval,?)";
			pstmt = q.getConn().prepareStatement(roomBookingsInsert5);

			// ROOMBOOKINGS Insert row #5.
			pstmt.setDate(1, convertDate(31, 11, 2013));
			pstmt.executeUpdate();

			String roomInsert8 = "INSERT INTO rooms values(room_seq.nextval,?,roomType_seq.currval)";
			pstmt = q.getConn().prepareStatement(roomInsert8);

			// ROOMS Insert row #14.
			pstmt.setString(1, "T");
			pstmt.executeUpdate();

			String bookingInsert6 = "INSERT INTO bookings VALUES(booking_seq.nextval,?,?,?,?,?,?,hotel_seq.currval,?)";
			pstmt = q.getConn().prepareStatement(bookingInsert6);

			// BOOKINGS Insert row #6.
			pstmt.setInt(1, 2);
			pstmt.setInt(2, 1);
			pstmt.setInt(3, 1);
			pstmt.setDouble(4, 199);
			pstmt.setDate(5, convertDate(15, 1, 2016));
			pstmt.setDate(6, convertDate(16, 1, 2016));
			pstmt.setString(7, "04");
			pstmt.executeUpdate();

			String roomBookingsInsert6 = "INSERT INTO roombookings VALUES(room_seq.currval,booking_seq.currval,?)";
			pstmt = q.getConn().prepareStatement(roomBookingsInsert6);

			// ROOMBOOKINGS Insert row #6.
			pstmt.setDate(1, convertDate(27, 2, 2014));
			pstmt.executeUpdate();

			String roomInsert9 = "INSERT INTO rooms values(room_seq.nextval,?,roomType_seq.currval)";
			pstmt = q.getConn().prepareStatement(roomInsert9);

			// ROOMS Insert row #15.
			pstmt.setString(1, "T");
			pstmt.executeUpdate();

			// CREATE SPECIALBOOKINGS TABLE
			stmt.executeUpdate("CREATE TABLE SpecialBookings "
					+ "(Special_Number number,Special_ID number NOT NULL,"
					+ "Booking_ID number NOT NULL,"
					+ "PRIMARY KEY(Special_ID, Booking_ID),"
					+ " FOREIGN KEY (Special_ID) REFERENCES Specials (Special_ID) ON DELETE CASCADE,"
					+ " FOREIGN KEY (Booking_ID) REFERENCES Bookings (Booking_ID) ON DELETE CASCADE)");

			System.out.println("Hotels table created.");
			System.out.println("Bookings table created.");
			System.out.println("Users table created.");
			System.out.println("Rooms table created.");
			System.out.println("Roomtypes table created.");
			System.out.println("RoomBookings table created.");
			System.out.println("Specials table created.");
			System.out.println("SpecialBookings table created.\n");

		} catch (SQLException ex) {
			System.out.println("ERROR:  buildTitanFallTables "
					+ ex.getMessage());
			ex.printStackTrace();
		}
		q.close();
	}
	
	/**
	 * This method returns the last booking id currently in the database
	 * which is incremented by 1 to make a booking in the add booking method
	 */
	public int getLastRow() {
		q.open();
		String sqlStatement = "SELECT * FROM bookings ORDER BY Booking_ID";
		int bookingID = 0;
		try {
			pstmt = q.getConn().prepareStatement(sqlStatement,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rset = pstmt.executeQuery();
			rset.last();

			System.out.println(rset.getInt("Booking_ID"));
			bookingID = rset.getInt("Booking_ID");
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		q.close();
		return bookingID;
	}

	/*
	 * This method returns titanfall towers hotel as a hotel object which will be used to get 
	 * the users of titanfall
	 */
	public Hotel getHotel() {
		String hotelsqlS = "SELECT * FROM Hotels";
		try {
			q.open();
			Statement stmt = q.getConn().createStatement();

			rset = stmt.executeQuery(hotelsqlS);
			while (rset.next()) {
				System.out.printf("%10d %10s %10s  %10s %10d %10d\n",
						rset.getInt(1), rset.getString(2), rset.getString(3),
						rset.getString(4), rset.getInt(5), rset.getInt(6));

				h = new Hotel(rset.getInt(1), rset.getString(2),
						rset.getString(3), rset.getString(4), rset.getInt(5),
						rset.getInt(6));
			}
		} catch (Exception ex) {
			System.out.println("ERROR: getHotel " + ex.getMessage());
		}
		q.close();
		return h;
	}
	
	/*
	 * Here an array list of users within the titanfall hotel is passed back.
	 * 
	 */
	public ArrayList<User> getUsers() {
		String sqlStatement = "SELECT * FROM Users";
		try {
			q.open();
			Statement stmt = q.getConn().createStatement();

			rset = stmt.executeQuery(sqlStatement);

			while (rset.next()) {
				System.out.printf("%5s %5s %8s %15s %20s %15s %25s %10s\n",
						rset.getString("User_ID"), rset.getString("UserType"),
						rset.getString("First_Name"),
						rset.getString("Last_Name"),
						rset.getString("HomeAddress"),
						rset.getString("Phone_Number"),
						rset.getString("Email_Address"),
						rset.getString("UserPassword"));

				User u = new User(rset.getString("User_ID"),
						rset.getString("UserType"),
						rset.getString("First_Name"),
						rset.getString("Last_Name"),
						rset.getString("HomeAddress"),
						rset.getString("Phone_Number"),
						rset.getString("Email_Address"),
						rset.getString("UserPassword"));

				h.addUsers(u);
			}

		} catch (Exception ex) {
			System.out.println("ERROR: getUsers " + ex.getMessage());
		}
		q.close();
		return h.getUsers();
	}

	/*
	 * The number of guests is calculated by using the rooms the user has chosen
	 * any room the user has booked that is a single will result in 1 guest
	 * any room the user has booked that is a double will result in 2 guests
	 * any room the user has booked that is a twin will result in 4 guests
	 * the number of guests will be added to a variable which will hold the total number of guests
	 */
	
	public int calculateNumGuests(ArrayList<Integer> roomChoice){
		int numGuests = 0;
		try{
			//q.open();
			stmt = q.getConn().createStatement();
			for (int i = 0; i < roomChoice.size(); i++) {
				String sqlGuests = "SELECT TYPE_ID FROM ROOMS WHERE ROOM_NUMBER = " + roomChoice.get(i);
				rset = stmt.executeQuery(sqlGuests);
				while(rset.next()){
					if(rset.getInt(1) == 900){
						numGuests = numGuests + 1;
					}
					else if(rset.getInt(1) == 901){
						numGuests = numGuests + 2;
					}
					else 
						numGuests = numGuests + 4;
					}
				}
				
			}
		catch (Exception se) {
			System.out.println("Error calculating number of guests " + se);
			//se.printStackTrace();
		}
		return numGuests;
		}
	
	/*
	 * the arrival and departure dates are passed to here as strings.
	 * these strings containing the dates are split into 3 seperate substrings for day month and year
	 * decision values used to distinguish between editing a booking or making a new booking
	 */
	public void addBooking(Booking b, ArrayList<Integer> roomChoice,
			int decision) {

		dayString = b.getArrivalDate().substring(0, 2);
		monthString = b.getArrivalDate().substring(3, 5);
		yearString = b.getArrivalDate().substring(6, 10);

		day = Integer.parseInt(dayString);
		month = Integer.parseInt(monthString);
		month = month - 1; // Subtract 1 to get precise month i.e 01 should be
							// 00 to represent January
		year = Integer.parseInt(yearString);

		dayString2 = b.getDepartureDate().substring(0, 2);
		monthString2 = b.getDepartureDate().substring(3, 5);
		yearString2 = b.getDepartureDate().substring(6, 10);

		day2 = Integer.parseInt(dayString2);
		month2 = Integer.parseInt(monthString2);
		month2 = month2 - 1; // Subtract 1 to get precise month i.e 01 should be
								// 00 to represent January
		year2 = Integer.parseInt(yearString2);
		for (int i = 0; i < roomChoice.size(); i++) {
			System.out.println(roomChoice.get(i) + " is a room number booked");
			System.out.println(b.getNumRooms() + " is the number of rooms we have tried to book");
		}
		try {
			q.open();
			String sql = "INSERT INTO Bookings VALUES (?,?,?,?,?,?,?,?,?) ";

			pstmt = q.getConn().prepareStatement(sql);
			if (decision == 1) {
				pstmt.setInt(1, (b.getBookingID() + 1)); //used for creating a new booking (continues sequnces)
			} else {
				pstmt.setInt(1, (b.getBookingID()));  //this else is used for editing a booking so booking id is 
													//not incremented
			}
			pstmt.setInt(2, calculateNumGuests(roomChoice));
			pstmt.setInt(3, b.getNumNights());
			pstmt.setInt(4, b.getNumRooms());
			pstmt.setDouble(5, b.getTotalCost());
			pstmt.setDate(6, convertDate(day, month, year)); // Arrival Date
			pstmt.setDate(7, convertDate(day2, month2, year2)); // Departure Date
			pstmt.setInt(8, 2222);
			pstmt.setString(9, b.getUserID());

			pstmt.executeUpdate();
			System.out.println("Booking created for: " + b.getUserID());
			String sql2 = "INSERT INTO RoomBookings VALUES (?,?,?) ";

			pstmt = q.getConn().prepareStatement(sql2);
			Calendar cal = Calendar.getInstance();

			for (int i = 0; i < roomChoice.size(); i++) {
				pstmt.setInt(1, roomChoice.get(i));
				if (decision == 1) {							//DECIDES BETWEEN EDITING A BOOKING OR MAKING A NEW BOOKING
					pstmt.setInt(2, (b.getBookingID() + 1));
				} else {
					pstmt.setInt(2, (b.getBookingID()));
				}
				pstmt.setDate(
						3,
						convertDate(cal.get(Calendar.DAY_OF_MONTH),
								cal.get(Calendar.MONTH), cal.get(Calendar.YEAR))); 
				pstmt.executeUpdate();
				System.out.println("Print statement to check if new bookings works");
			}
		} catch (Exception se) {
			System.out.println("Error creating a booking " + se);
			//se.printStackTrace();
		}
		q.close();
	}
	/*
	 * updates rooms in roombookings associated with booking ID
	 * takes in new room choice
	 */

	public void updateBookingRooms(Booking b, ArrayList<Integer> roomChoice) {
		try {
			q.open();
			for (int i = 0; i < roomChoice.size(); i++) {
				System.out.println(roomChoice.size());
			}

			String sql = "UPDATE bookings SET  NUMBER_OF_ROOMS = "
					+ roomChoice.size() + ", total_cost = " + b.getTotalCost()
					+ ", NUMBER_OF_GUESTS = " + calculateNumGuests(roomChoice)
					+ " WHERE BOOKING_ID = " + b.getBookingID();

			stmt = q.getConn().createStatement();
			stmt.executeUpdate(sql);
			System.out.println("Booking " + b.getBookingID() + " updated.");

			sql = "DELETE FROM roombookings WHERE booking_id = "
					+ b.getBookingID(); // removes previous room choice
			stmt.executeUpdate(sql);
			System.out.println("Rooms of " + b.getBookingID() + " deleted.");

			sql = "INSERT INTO roombookings VALUES (?,?,?)"; // adds new choice
																// from
																// EditBookingGUI
			Calendar cal = Calendar.getInstance();
			pstmt = q.getConn().prepareStatement(sql);
			for (int i = 0; i < roomChoice.size(); i++) {
				pstmt.setInt(1, roomChoice.get(i));
				pstmt.setInt(2, b.getBookingID());
				pstmt.setDate(
						3,
						convertDate(cal.get(Calendar.DAY_OF_MONTH),
								cal.get(Calendar.MONTH), cal.get(Calendar.YEAR)));
				pstmt.executeUpdate();
				System.out.println("Booking edited with new values");
			}

		} catch (Exception e) {
			System.out.println("Update booking error" + e);
		}
		q.close();
	}

	/*
	 * This will update the number of guests, number of nights, and the dates of
	 * the bookingNeeds to check availability of the rooms if they want to stay
	 * longer than originally requested or if they change the arrival date
	 * Otherwise just overwrite the departure
	 */

	public void updateBookingDates(Booking b, Calendar newArrival) {
		try {
			Calendar departureDate = Calendar.getInstance();
			departureDate.setTime(newArrival.getTime());
			departureDate.add(Calendar.DAY_OF_MONTH, b.getNumNights());
			q.open();
			stmt = q.getConn().createStatement();
			/*
			 * keeps original booking in case of failure, 
			 * in which case original booking is added back into the db
			 */
			Booking origB = new Booking(); 
			String origBooking = "SELECT * FROM BOOKINGS WHERE BOOKING_ID = "
					+ b.getBookingID();
			rset = stmt.executeQuery(origBooking);
			while (rset.next()) {
				origB.setBookingID(rset.getInt(1));
				origB.setNumGuests(rset.getInt(2));
				origB.setNumNights(rset.getInt(3));
				origB.setNumRooms(rset.getInt(4));
				origB.setTotalCost(rset.getDouble(5));
				origB.setArrivalDate(origB.dateConverter(rset.getDate(6)));
				origB.setDepartureDate(origB.dateConverter(rset.getDate(7)));
				origB.setHotelID(rset.getInt(8));
				origB.setUserID(rset.getString(9));
			}
			ArrayList<Object[]> roomBookings = new ArrayList<Object[]>();
			String getRoomDate = "SELECT DATEOFBOOKING FROM ROOMBOOKINGS WHERE BOOKING_ID = "
					+ origB.getBookingID();
			rset = stmt.executeQuery(getRoomDate);
			Date dateofbooking = null;
			while (rset.next()) {
				dateofbooking = rset.getDate(1);

			}
			String sql = "SELECT room_number FROM roombookings WHERE booking_id = "
					+ b.getBookingID();
			rset = stmt.executeQuery(sql);
			ArrayList<Integer> currentRooms = new ArrayList<Integer>(); // change
																		// to
																		// integer
																		// array/list
			while (rset.next()) { // check these room numbers against available
									// rooms for the arrival/departure dates
				int i = rset.getInt("ROOM_NUMBER"); // use availability query in
													// Queries with arrival and
													// numNights
				System.out.println(i + " is a room booked for the selected booking");
				currentRooms.add(i);
			}
			//must delete and then re-add to all tables with foreign keys
			String tempGetSpecials = "SELECT S.SPECIAL_ID ,S.SPECIAL_COST FROM SPECIALBOOKINGS SB, SPECIALS S WHERE BOOKING_ID = " + origB.getBookingID()
										+ " AND S.SPECIAL_ID = SB.SPECIAL_ID";
			rset = stmt.executeQuery(tempGetSpecials);
			ArrayList<Integer> specialsBooked = new ArrayList<Integer>();
			ArrayList<Double> specialsCostBooked = new ArrayList<Double>();
			while(rset.next()){
				specialsBooked.add(rset.getInt(1));
				specialsCostBooked.add(rset.getDouble(2));
			}
			String tempDeleteSpecial = "DELETE FROM SPECIALBOOKINGS WHERE BOOKING_ID = "
					+ origB.getBookingID();
			stmt.executeUpdate(tempDeleteSpecial);
			String tempDeleteRoomBooking = "DELETE FROM ROOMBOOKINGS WHERE BOOKING_ID = "
					+ origB.getBookingID();
			stmt.executeUpdate(tempDeleteRoomBooking);
			String tempDeleteBooking = "DELETE FROM BOOKINGS WHERE BOOKING_ID = "
					+ origB.getBookingID();
			stmt.executeUpdate(tempDeleteBooking);
			ArrayList<Room> availableRooms = new ArrayList<Room>(
					q.availabilityQuery(newArrival, b.getNumNights()));
			int count = 0;
			for (int i = 0; i < currentRooms.size(); i++) {
				for (int j = 0; j < availableRooms.size(); j++) {
					if (availableRooms.get(j).getRoomNumber() == currentRooms.get(i)) {
						System.out.println("hooray");
						count++;    //count should be equal to the number of rooms the user is trying to add
									// if not then a room is unavailable, update cancelled (below)

					}

				}
			}
			newArrival.add(Calendar.DATE, -b.getNumNights()); //arrival gets set to departure date after this query because they're connected
															// minus number of nights to reset it
			System.out.println(count + " is the count");
			q.close();
			addBooking(origB, currentRooms, 2);
			q.open();
			sql = "INSERT INTO SPECIALBOOKINGS VALUES(?,?,?)";
			pstmt = q.getConn().prepareStatement(sql);
			int counter =0;
			for (int i = 0; i < specialsBooked.size(); i++) {
				counter++;
				pstmt.setInt(1, counter);
				pstmt.setInt(2, specialsBooked.get(i));
				pstmt.setInt(3, origB.getBookingID());
				pstmt.executeUpdate();
			}
			sql = "INSERT INTO ROOMBOOKINGS VALUES(?,?,?)";

			pstmt = q.getConn().prepareStatement(sql);
			for (int i = 0; i < currentRooms.size(); i++) {
				pstmt.setInt(1, currentRooms.get(i));
				pstmt.setInt(2, origB.getBookingID());
				pstmt.setDate(3, dateofbooking);
			}
			for (int i = 0; i < currentRooms.size(); i++) {
				System.out.println(currentRooms.get(i));
			}
			if (count < currentRooms.size()) {
				JOptionPane.showMessageDialog(null,
						"One or more rooms unavailable for the selected dates");	
			}
			else{
				System.out.println(b.getTotalCost() + " should be a total cost");
				String updateSQL = "UPDATE bookings SET  NUMBER_OF_GUESTS = "
						+ origB.getNumGuests() + ", NUMBER_OF_NIGHTS = "
						+ b.getNumNights() + ", TOTAL_COST = "
						+ b.getTotalCost() 
						+ ", ARRIVALDATE =  TO_DATE('" + newArrival.get(Calendar.YEAR)+"/" + (newArrival.get(Calendar.MONTH) +1) + "/"+ newArrival.get(Calendar.DAY_OF_MONTH)+ "','YYYY/MM/DD')"
						+ ", DEPARTUREDATE = TO_DATE('" + departureDate.get(Calendar.YEAR)+"/" + (departureDate.get(Calendar.MONTH) +1) + "/"+ departureDate.get(Calendar.DAY_OF_MONTH)+ "','YYYY/MM/DD')"
						+ " WHERE BOOKING_ID = "+ b.getBookingID();
				stmt = q.getConn().createStatement();
				stmt.executeUpdate(updateSQL);
				System.out.println("No overlap of rooms found. Booking updated ");
			}
		}
		catch (Exception e) {
			System.out.println("Update booking error " + e);
			e.printStackTrace();
		}
		q.close();
	}
}
