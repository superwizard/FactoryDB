/**
 * TCSS 491 SPRING 2015
 * Mickey Johnson
 * Final DB project
 */


package main;
import gui.GUImain;
import gui.GUIstart;

import java.sql.*;

import javax.swing.JOptionPane;

/**
 * This is the main class for this program.
 * The methods are called by various parts of the GUI to do specific tasks.
 * @author Mickey Johnson
 *
 */
public class Driver {

	private static Connection myConn;
	
	public static void main(String[] args) {
		GUIstart.start();


	}
	
	/**
	 * Used for logging in to the system.
	 * Upon successful login will dispose login window and load system window.
	 * @param address Address of the server as address:port/dbname
	 * @param username Username entered
	 * @param password Password entered
	 */
	public static void attemptLogin(String address, String username, char[] password) {
		try {
			myConn = DriverManager.getConnection("jdbc:mysql://" + address, username, new String(password));
			if (myConn.isValid(1)) {
				GUIstart.dispose();
				GUImain.start();
			}
			
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Invalid username or password.");
			System.out.println(e);
		}
	}
	
	/**
	 * Gets the number of orders in the system
	 * @return The number of orders.
	 */
	public static int getNumJobs() {
		int result = 0;
		try {
			Statement statement = myConn.createStatement();
			ResultSet myRes = statement.executeQuery("select count(OrderNumber) as orders from CUSTOMER_ORDER where DateShipped is null;");
			myRes.next();
			result = myRes.getInt("orders");
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return result;
	}
	
	/**
	 * Gets Customer order info from the system.
	 * @return The info.
	 */
	public static ResultSet getJobInfo() {
		ResultSet myRes = null;
		try {
			Statement statement = myConn.createStatement();
			myRes = statement.executeQuery("select * from CUSTOMER_ORDER ORDER BY DateRequired;");
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return myRes;
	}
	
	/**
	 * Gets the next job number, number is incremented by one each time.
	 * @return The number.
	 */
	public static int getNextJobNumber() {
		int result = 0;
		try {
			Statement statement = myConn.createStatement();
			ResultSet myRes = statement.executeQuery("SELECT Count(OrderNumber) AS theCount FROM CUSTOMER_ORDER;");
			myRes.next();
			result = myRes.getInt("theCount") + 1;
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return result;
	}
	
	/**
	 * Inserts a new order into the system.
	 * @param theOrder The order number for this order.
	 * @param theCustomer The customer name for this order.
	 * @param theDate The date the order is required by.
	 */
	public static void createNewOrder(int theOrder, String theCustomer, int theDate) {
		Statement statement;
		try {
			statement = myConn.createStatement();
			statement.executeUpdate("INSERT INTO CUSTOMER_ORDER VALUES(" + theOrder + ", \"" + theCustomer + "\", " + theDate + ", null);");
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Completes an order already in the system.
	 * @param theOrder The order to complete.
	 * @param theDate The date the order was shipped on.
	 */
	public static void completeOrder(int theOrder, int theDate) {
		Statement statement;
		try {
			statement = myConn.createStatement();
			statement.executeUpdate("UPDATE CUSTOMER_ORDER SET DateShipped = " + theDate + " WHERE OrderNumber = " + theOrder + ";");
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Adds a product to an order in the system.
	 * @param theOrder The order number to use.
	 * @param theProduct The product number to add.
	 * @param theNumber The number of the product to produce.
	 */
	public static void createNewProductOrder(int theOrder, int theProduct, int theNumber) {
		Statement statement;
		try {
			statement = myConn.createStatement();
			statement.executeUpdate("INSERT INTO PRODUCT_ORDER VALUES(" + theOrder + ", " +  theProduct + ", " + theNumber + ");");
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Checks if an order is in the system.
	 * @param theOrder The order to check.
	 * @return
	 */
	public static boolean checkValidOrder(int theOrder) {
		int result = 0;
		try {
			Statement statement = myConn.createStatement();
			ResultSet myRes = statement.executeQuery("SELECT Count(OrderNumber) AS theCount FROM CUSTOMER_ORDER WHERE OrderNumber = " + theOrder + ";");
			myRes.next();
			result = myRes.getInt("theCount");
		} catch (Exception e) {
			System.out.println(e);
		}
		return (result > 0);
	}

	/**
	 * Gets the number of different products for an order.
	 * @param theOrder The order to check.
	 * @return The number of different products.
	 */
	public static int getNumJobProducts(int theOrder) {
		int result = 0;
		try {
			Statement statement = myConn.createStatement();
			ResultSet myRes = statement.executeQuery("select count(ProductNumber) as prods from PRODUCT_ORDER where OrderNumber = " + theOrder + ";");
			myRes.next();
			result = myRes.getInt("prods");
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return result;
	}
	
	public static ResultSet getProductionOrder(int theOrder) {
		ResultSet myRes = null;
		try {
			Statement statement = myConn.createStatement();
			myRes = statement.executeQuery("select PRODUCT.Description as Descr, PRODUCT_ORDER.NumToProduce as Num from PRODUCT_ORDER, PRODUCT WHERE PRODUCT.ProductNumber = "
					+ "PRODUCT_ORDER.ProductNumber AND PRODUCT_ORDER.OrderNumber = " + theOrder + ";");
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return myRes;
	}
	
	/**
	 * Checks if a product number is valid.
	 * @param theProduct The number to check.
	 * @return If it is valid.
	 */
	public static boolean checkValidProduct(int theProduct) {
		int result = 0;
		try {
			Statement statement = myConn.createStatement();
			ResultSet myRes = statement.executeQuery("SELECT Count(ProductNumber) AS theCount FROM PRODUCT WHERE ProductNumber = " + theProduct + ";");
			myRes.next();
			result = myRes.getInt("theCount");
		} catch (Exception e) {
			System.out.println(e);
		}
		return (result > 0);
	}
	
	/**
	 * Gets the next product number in the system.
	 * Product number incremented by 1 each time.
	 * @return The next number.
	 */
	public static int getNextProductNumber() {
		int result = 0;
		try {
			Statement statement = myConn.createStatement();
			ResultSet myRes = statement.executeQuery("SELECT Count(ProductNumber) AS theCount FROM PRODUCT;");
			myRes.next();
			result = myRes.getInt("theCount") + 1;
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return result;
	}
	
	/**
	 * Adds a new product to the system.
	 * @param theProduct The product number to add.
	 * @param theTime The time it takes to produce the product.
	 * @param theDesc The description of the product.
	 */
	public static void createNewProduct(int theProduct, int theTime, String theDesc) {
		Statement statement;
		try {
			statement = myConn.createStatement();
			statement.executeUpdate("INSERT INTO PRODUCT VALUES(" + theProduct + ", " + theTime + ", \"" + theDesc + "\");");
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Checks if a part number is valid.
	 * @param thePart The number to check.
	 * @return If it is valid.
	 */
	public static boolean checkValidPart(int thePart) {
		int result = 0;
		try {
			Statement statement = myConn.createStatement();
			ResultSet myRes = statement.executeQuery("SELECT Count(PartNumber) AS theCount FROM PART WHERE PartNumber = " + thePart + ";");
			myRes.next();
			result = myRes.getInt("theCount");
		} catch (Exception e) {
			System.out.println(e);
		}
		return (result > 0);
	}
	
	/**
	 * Adds a part to a product.
	 * @param theProduct The product number to add to.
	 * @param thePart The part number to add.
	 * @param theNum The number of this part required.
	 */
	public static void createNewProductPart(int theProduct, int thePart, int theNum) {
		Statement statement;
		try {
			statement = myConn.createStatement();
			statement.executeUpdate("INSERT INTO PRODUCT_PARTLIST VALUES(" + theProduct + ", " + thePart + ", " + theNum + ");");
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Gets the next part number for the system.
	 * Part number is incremented by 1.
	 * @return The next part number.
	 */
	public static int getNextPartNumber() {
		int result = 0;
		try {
			Statement statement = myConn.createStatement();
			ResultSet myRes = statement.executeQuery("SELECT Count(PartNumber) AS theCount FROM PART;");
			myRes.next();
			result = myRes.getInt("theCount") + 1;
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return result;
	}
	
	/**
	 * Creates a new part in the system.
	 * @param thePart The part number to use.
	 * @param theDesc The description of the part.
	 */
	public static void createNewPart(int thePart, String theDesc) {
		Statement statement;
		try {
			statement = myConn.createStatement();
			statement.executeUpdate("INSERT INTO PART VALUES(" + thePart +  ", \"" + theDesc + "\");");
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Gets the number of different parts in the system.
	 * @return The number of different parts.
	 */
	public static int getNumParts() {
		return getNextPartNumber() - 1;
	}
	
	/**
	 * Gets the stock of the part in inventory.
	 * @param thePart The part number to check.
	 * @return The number in inventory.
	 */
	public static int getPartInventory(int thePart) {
		int result = 0;
		try {
			Statement statement = myConn.createStatement();
			if (checkValidPart(thePart)) {
				ResultSet myRes = statement.executeQuery("SELECT Stock from INVENTORY WHERE PartNumber = " + thePart + ";");
				myRes.next();
				result = myRes.getInt("Stock");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return result;
	}
	
	/**
	 * Gets all part orders for a specific part.
	 * @param thePart The part number to check.
	 * @return The part orders for this part as a SQL Result Set.
	 */
	public static ResultSet getPartOrders(int thePart) {
		ResultSet myRes = null;
		try {
			Statement statement = myConn.createStatement();
			myRes = statement.executeQuery("SELECT * from PART_ORDERS WHERE PartNumber = " + thePart + ";");
		} catch (Exception e) {
			System.out.println(e);
		}
		return myRes;
	}
	
	/**
	 * Creates a new order for a part.
	 * @param theOrder The order number.
	 * @param thePart The part number.
	 * @param theNumb The number to order.
	 * @param theDate The delivery date for the order.
	 */
	public static void createNewPartOrder(int theOrder, int thePart, int theNumb, int theDate) {
		Statement statement;
		try {
			statement = myConn.createStatement();
			statement.executeUpdate("INSERT INTO PART_ORDERS VALUES( " + theOrder + ", " + thePart + ", " + theNumb + ", " + theDate + ");");
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
}