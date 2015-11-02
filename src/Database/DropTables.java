package Database;
/**
 * @author Derek Mulhern
 */
import java.sql.*;

import oracle.jdbc.pool.OracleDataSource;

public class DropTables {

	private Statement stmt;
	private Queries q = new Queries();
	
	//The tables are dropped in sequence so there is no conflict between Primary Key and Foreign Key
	public void dropTables() {
		
		System.out.println("Checking for existing TitanFall database tables.\n");
		try {
			q.open();
			stmt = q.getConn().createStatement();
			try{
				//Drop the SpecialBookings table
				stmt.execute("DROP TABLE specialbookings");
				System.out.println("SpecialBookings table dropped.");
			}
			catch (SQLException ex) {

			}
			try{
				//Drop the Special table & Special Sequence
				stmt.execute("DROP TABLE specials");
				stmt.execute("DROP SEQUENCE special_seq");
				System.out.println("Specials table dropped.");
				System.out.println("Special sequence dropped.");
			}
			catch (SQLException ex) {

			}
			try{
				//Drop the Room Booking table.
				stmt.execute("DROP TABLE roombookings");
				System.out.println("RoomBookings table dropped.");
			}
			catch (SQLException ex) {

			}
			try{
				//Drop the Booking table & Booking Sequence
				stmt.execute("DROP TABLE bookings");
				stmt.execute("DROP SEQUENCE booking_seq");
				System.out.println("Bookings table dropped.");
				System.out.println("Booking sequence dropped.");
			}
			catch (SQLException ex) {

			}
			try {
				// Drop the User table.
				stmt.execute("DROP TABLE users");
				System.out.println("Users table dropped.");
			} 
			catch (SQLException ex) {

			}
			try{
				//Drop the Hotel table & Hotel Sequence
				stmt.execute("DROP TABLE hotels");
				stmt.execute("DROP SEQUENCE hotel_seq");
				System.out.println("Hotels table dropped.");
				System.out.println("Hotel sequence dropped.");
			}
			catch (SQLException ex) {

			}
			try{
				//Drop the Room table & Room Sequence
				stmt.execute("DROP TABLE rooms");
				stmt.execute("DROP SEQUENCE room_seq");
				System.out.println("Rooms table dropped.");
				System.out.println("Room sequence dropped.");
			}
			catch (SQLException ex) {
			}
			try{
				//Drop the Room Type table & RoomType Sequence
				stmt.execute("DROP TABLE roomtypes");
				stmt.execute("DROP SEQUENCE roomType_seq");
				System.out.println("RoomTypes table dropped.");
				System.out.println("RoomType sequence dropped.\n");
			}
			catch (SQLException ex) {
			}
		} catch (SQLException ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		}
		q.close();
	}
}
