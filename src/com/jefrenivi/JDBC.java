package com.jefrenivi;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class JDBC implements Closeable {

	private Connection con;
	
	public JDBC() {
		establishConnection();
	}
	
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
		String sql = "SELECT OrderID, CustomerID, Date, ShipStatus FROM Orders";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		ps.close();
		return rs;
	}
	
	public ResultSet getAllOrdersDescendingTotal() throws SQLException {
		String sql = "SELECT Orders.OrderID, CustomerID, Date, ShipStatus, SUM(OrderDetails.Price * OrderDetails.Quantity) AS Total "
				+ "FROM Orders JOIN OrderDetails ON Orders.OrderID = OrderDetails.OrderID "
				+ "GROUP BY OrderDetails.OrderID "
				+ "ORDER BY Total DESC";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		ps.close();
		return rs;
	}
	
//	public ResultSet getAllOrdersWithTotalGreaterThan(double total) throws SQLException {
//		String sql = "SELECT Orders.OrderID, CustomerID, Date, ShipStatus, SUM(OrderDetails.Price * OrderDetails.Quantity) AS Total "
//				+ "FROM Orders JOIN OrderDetails ON Orders.OrderID = OrderDetails.OrderID "
//				+ "GROUP BY OrderDetails.OrderID "
//				+ "ORDER BY Total DESC";
//	}
	
	public ResultSet getOrder(int orderid) throws SQLException {
		String sql = "SELECT * FROM Orders WHERE CustomerID = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, orderid);
		ResultSet rs = ps.executeQuery();
		ps.close();
		return rs;
	}
	
	public ResultSet getOpenOrders() throws SQLException {
		String sql = "SELECT * FROM Orders WHERE ShipStatus = FALSE";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		ps.close();
		return rs;
	}
	
	public ResultSet getClosedOrders() throws SQLException {
		String sql = "SELECT * FROM Orders WHERE ShipStatus = TRUE";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		ps.close();
		return rs;
	}
	
	// ---------- PRODUCT QUERIES ----------
	
	public ResultSet getProductsFromCategory(int categoryid) throws SQLException {
		String sql = "SELECT * FROM Products WHERE CategoryID = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, categoryid);
		ResultSet rs = ps.executeQuery();
		ps.close();
		return rs;
	}
}
