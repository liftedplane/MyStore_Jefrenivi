package com.jefrenivi;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.stream.IntStream;

public class JDBC implements Closeable {

	private Connection con;
	
	public JDBC() {
		establishConnection();
	}
	
	//Random change
	
	public void establishConnection() {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String url = "jdbc:mysql://localhost:3306/jefrenivi";
        System.out.print("Enter User Name: ");
        String user = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        System.out.println("Connecting to DB...");
        try {
			con = DriverManager.getConnection(url, user, password);
			System.out.println("Connected to " + url);
		} catch (SQLException e) {
			System.out.println("Error connecting to " + url);
			e.printStackTrace();
		}
	}

	@Override
	public void close() throws IOException {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// ---------- ORDER QUERIES ----------
	
	public ResultSet getAllOrders() throws SQLException {
		String sql = "SELECT * FROM AllOrders";
		PreparedStatement ps = con.prepareStatement(sql);
		return ps.executeQuery();
	}
	
	public ResultSet getAllOrdersDescendingTotal() throws SQLException {
		String sql = "SELECT * FROM OrdersDescendingTotal";
		PreparedStatement ps = con.prepareStatement(sql);
		return ps.executeQuery();
	}
	
	public ResultSet getAllOrdersWithTotalGreaterThan(double total) throws SQLException {
		String sql = 
				"SELECT OrderID, Customer, Name, Payment, OrderDate, ShipDate, CONCAT('$', FORMAT(Total, 2)) AS Total "
				+ "FROM OrdersMinTotal WHERE Total > ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setDouble(1, total);
		return ps.executeQuery();
	}
	
	public ResultSet getOrder(int orderid) throws SQLException {
		String sql = "SELECT * FROM AllOrders WHERE OrderID = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, orderid);
		return ps.executeQuery();
	}
	
	public ResultSet getOpenOrders() throws SQLException {
		String sql = "SELECT * FROM AllOrders WHERE OpenStatus = 'True'";
		PreparedStatement ps = con.prepareStatement(sql);
		return ps.executeQuery();
	}
	
	public ResultSet getClosedOrders() throws SQLException {
		String sql = "SELECT * FROM AllOrders WHERE OpenStatus = 'False'";
		PreparedStatement ps = con.prepareStatement(sql);
		return ps.executeQuery();
	}
	
	public ResultSet getProductsFromOrder(int orderid) throws SQLException {
		String sql = "SELECT * FROM ProductsInOrder WHERE OrderID = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, orderid);
		return ps.executeQuery();
	}
	
	public int deleteOrder(int orderid) throws SQLException {
		String sql = "DELETE FROM OrderDetails WHERE OrderID = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, orderid);
		ps.addBatch();
		sql = "DELETE FROM Orders WHERE OrderID = ?";
		ps.setInt(1, orderid);
		ps.addBatch();
		int[] rowsUpdated = ps.executeBatch();
		return IntStream.of(rowsUpdated).sum();
	}
	
	// ---------- PRODUCT QUERIES ----------
	
	public ResultSet getAllProducts() throws SQLException {
		String sql = "SELECT * FROM AllProducts";
		PreparedStatement ps = con.prepareStatement(sql);
		return ps.executeQuery();
	}
	
	// Don't ever ask me how this works, because I don't know
	public ResultSet getProductsFromCategory(int categoryid) throws SQLException {
		String sql = 
				"SELECT p.ProductID, p.Name, c.Name AS Category, CONCAT('$', p.Price) AS Price, "
				+ "CONCAT(FORMAT(p.Weight, 3),'kg') AS Weight, p.Stock, p.ReorderLevel "
				+ "FROM Products p JOIN (SELECT  CategoryID, Name, Description, ParentCategoryID "
				+ "FROM (SELECT * FROM Categories ORDER BY ParentCategoryID, CategoryID) categories_sorted, "
				+ "(SELECT @pv := '?') init WHERE find_in_set(ParentCategoryID, @pv) "
				+ "AND length(@pv := concat(@pv, ',', CategoryID))) c ON p.CategoryID = c.CategoryID";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, categoryid);
		return ps.executeQuery();
	}
	
	// ---------- CUSTOMER QUERIES ----------
	
	public ResultSet getAllCustomers() throws SQLException {
		String sql = "SELECT * FROM CUSTOMER";
		PreparedStatement ps = con.prepareStatement(sql);
		return ps.executeQuery();
	}
	
	public ResultSet getCustomersByZip(String zip) throws SQLException {
		String sql = "SELECT * FROM AllCustomers WHERE Zip = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		return ps.executeQuery();
	}
}
