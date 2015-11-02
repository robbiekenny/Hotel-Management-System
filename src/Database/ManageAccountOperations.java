package Database;
/**
 * @author Robert Kenny 
 * @author Derek Mulhern
 */
import java.sql.Statement;

public class ManageAccountOperations {
	private Queries q = new Queries();
	private Statement stmt;
	
	/* 	
	 * This method updates a particular users details in the users table
	 * based on the id of the user 
	*/
	public void updateDeatils(String id,String fname,String lname,String add,String email,String phone) {
		try {
			q.open();
			String sql = "UPDATE Users SET First_Name = '" + fname + "', Last_Name = '" + lname + "', HomeAddress = '"
					+ add + "',Email_Address = '" + email + "',Phone_Number = " + phone + "WHERE User_ID = '" + id + "'";

			stmt = q.getConn().createStatement();
			stmt.executeUpdate(sql);
			
			System.out.println("Users deatils have been updated");
		} catch (Exception e) {
			System.out.println("Update details error" + e);
		}
		q.close();
	}
	/* 	
	 * This method updates a particular users password in the database
	 */
	public void updatePassword(String id,String password) {
		try {
			q.open();
			String sql = "UPDATE Users SET UserPassword = '" + password + "' WHERE User_ID = '" + id + "'";

			stmt = q.getConn().createStatement();
			stmt.executeUpdate(sql);
			
			System.out.println("Users password have been updated");
		} catch (Exception e) {
			System.out.println("Update password error" + e);
		}
		q.close();
	}
}
