package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Database.CreateTables;
import Database.RoomOperations;
import Database.ManageAccountOperations;
/**
 * @author Thomas Murphy
 * @author Mark Lordan
 * @author Robert Kenny
 * @author Derek Mulhern
 */
public class Hotel {
	// Instance variables
	private String hotelName, hotelPhoneNumber, hotelAddress;
	private int hotelID, totalNumberRooms, hotelRating; // Need to change hotelID to an integer to correspond with the ERD and class diagram
	private ArrayList<User> users = new ArrayList<User>();
	private ArrayList<Room> roomList;
	private RoomOperations roomOp;
	private ResultSet rset;
	
	//Hotel constructor for adding a room into the database
	private Hotel(RoomOperations ro) {	
		this.roomOp = ro;
	}
	public Hotel(int id, String name, String phoneNumber, String address, int totalNumberRooms, int hoteRating){
		this.hotelID = id;
		this.hotelName = name;
		this.hotelPhoneNumber = phoneNumber;
		this.hotelAddress = address;
		this.totalNumberRooms = totalNumberRooms;
		this.hotelRating = hoteRating;
	}
	
	// Default constructor
	public Hotel() {
	}
	
	public void updateUsersDetails(String id,String fname,String lname,String address,String email,String phone){
		
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUserID().equals(id)){
				users.get(i).setfName(fname);
				users.get(i).setlName(lname);
				users.get(i).setHomeAddress(address);
				users.get(i).setEmail(email);
				users.get(i).setPhoneNum(phone);
			}	
		}	
		ManageAccountOperations m = new ManageAccountOperations();
		m.updateDeatils(id, fname, lname, address, email, phone);
	}
	
	public void updateUsersPassword(String id,String password){
		
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUserID().equals(id)) {
				users.get(i).setPassword(password);
			}	
		}
		ManageAccountOperations m = new ManageAccountOperations();
		m.updatePassword(id, password);
		
	}
	
	public void addUsers(User u){
		users.add(u);
	}
	
	public void removeUsers(User u){
		users.remove(u);
	}

	// Getters and Setters
	public ArrayList<User> getUsers() {
		return users;
	}
	public int getNumUsers() {
		return users.size();
	}
	public User getUser(int index) {
		return users.get(index);
	}
	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
	public int getHotelID() {
		return hotelID;
	}
	public void setHotelID(int hotelID) {
		this.hotelID = hotelID;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getHotelPhoneNumber() {
		return hotelPhoneNumber;
	}
	public void setHotelPhoneNumber(String hotelPhoneNumber) {
		this.hotelPhoneNumber = hotelPhoneNumber;
	}
	public String getHotelAddress() {
		return hotelAddress;
	}
	public void setHotelAddress(String hotelAddress) {
		this.hotelAddress = hotelAddress;
	}
	public int getTotalNumberRooms() {
		return totalNumberRooms;
	}
	public void setTotalNumberRooms(int totalNumberRooms) {
		this.totalNumberRooms = totalNumberRooms;
	}
	public int getHotelRating() {
		return hotelRating;
	}
	public void setHotelRating(int hotelRating) {
		this.hotelRating = hotelRating;
	}
	
	public void refreshList(){
		rset = roomOp.getRooms();
		
		if(roomList.size() > 0){
			for (int i = roomList.size(); i >= 0; i--) {
				roomList.remove(i);
			}
		}
		try{
			while(rset.next()){
				Room r = new Room(rset.getInt(1), rset.getString(2), rset.getInt(3));
				roomList.add(r);
			}
		}catch (Exception e){
			System.out.println(e);
		}
	}
	
	/*
	 * This method adds a room to the database
	 * */
	public void addRoom(){
		roomOp = new RoomOperations();
		rset = roomOp.getLastRow();
		try{
			Room r = new Room(rset.getInt(1),rset.getString(2), rset.getInt(3));
			roomList = new ArrayList<Room>();
			refreshList();
			totalNumberRooms++;
			roomList.add(r);
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	/*
	 * This method updates a room in the hotel and is reflected in the
	 * database
	 */
	public void updateRoom(int roomNumber, int roomType){
		roomOp = new RoomOperations();
		for (int i = 0; i < roomList.size(); i++) {
			if(roomList.get(i).getRoomType().equals(roomType))
				roomList.get(i).setRoomNumber(roomNumber);
				roomList.get(i).setRoomTypeID(roomType);
		}
		roomOp.updateRoom(roomNumber, roomType);
	}
	public int deleteRoom(int roomNumber){
		int num = 0;
		for(int i = 0; i < roomList.size(); i++){
			if(roomNumber == roomList.get(i).getRoomNumber()){
				roomList.remove(i);
				try {
					roomOp.deleteRoom(roomNumber);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				num++;
			}
		}
		return num;
	}
}
