package main;
import java.sql.*;

public class Driver {

	public static void main(String[] args) {

		try {
			//get a connection to db
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/johnson_mickey_db", "root", "France7&");
			
			//create statement
			Statement myStmt = myConn.createStatement();
			
			//execute statement
			ResultSet myRs = myStmt.executeQuery("select * from PRODUCT");
			
			//process result
			while (myRs.next()) {
				System.out.println("col 1" + myRs.getInt("ProductNumber") + " " + myRs.getString("Description"));
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}