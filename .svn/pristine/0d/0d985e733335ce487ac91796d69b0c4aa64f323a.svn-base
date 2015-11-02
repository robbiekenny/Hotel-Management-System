package Model;

import java.sql.ResultSet;
import java.util.ArrayList;

import Database.CreateTables;

public class User {
	// Instance variables
	private String userType,fName,lName,homeaddress,email,phoneNum,userID,password;
	private ArrayList<Booking> bookings;
	private ResultSet rset;
	private CreateTables c;

	
	public User(String userID,String userType,String fName, String lName,String homeaddress,
			String phoneNum,String email, String password) {
		this.userID = userID;
		this.userType = userType;
		this.fName = fName;
		this.lName = lName;
		this.homeaddress = homeaddress;
		this.email = email;
		this.phoneNum = phoneNum;
		this.password = password;
	}
	
	public User(String userID,CreateTables c){
		this.userID = userID;
		this.c = c;
		bookings = new ArrayList<Booking>();
	}
	
	public void addBooking()
	{
		try {
			 {
				Booking b = new Booking(rset.getInt(1), rset.getInt(2),
						rset.getInt(3),rset.getInt(4), rset.getDouble(5), rset.getString(6),rset.getString(7),
						rset.getInt(8),rset.getString(9));
				
				bookings.add(b);
				
				System.out.println(rset.getInt(1) + ", " + rset.getInt(2) + ", " + rset.getInt(3) + ", " +  rset.getInt(4) + ", "
				+  rset.getDouble(5) + ", " + rset.getString(6) + ", " + rset.getString(7) + ", " +
						  rset.getInt(8) + ", " + rset.getString(9));
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	//Getters and Setters
	public ArrayList<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(ArrayList<Booking> bookings) {
		this.bookings = bookings;
	}
	

	public String getHomeaddress() {
		return homeaddress;
	}
	
	public String getUserID() {
		return userID;
	}

	public String getUserType() {
		return userType;
	}

	public String getfName() {
		return fName;
	}

	public String getlName() {
		return lName;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
	
	public void setfName(String fname){
		this.fName = fname;
	}
	
	public void setlName(String lname){
		this.lName = lname;
	}
	
	public void setHomeAddress(String add){
		this.homeaddress = add;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public void setPhoneNum(String phone){
		this.phoneNum = phone;
	}
	
	public void setPassword(String p){
		this.password = p;
	}
	
}
