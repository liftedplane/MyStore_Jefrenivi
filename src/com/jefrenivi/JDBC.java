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
        sc.close();
	}

	@Override
	public void close() throws IOException {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet allOrders() throws SQLException {
		String sql = "SELECT * FROM Orders";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		ps.close();
		return rs;
	}
	
//	public ResultSet allOrdersDescendingTotal() throws SQLException {
//		String sql = "SELECT *, SUM(OrderDetails.Price FROM Orders JOIN OrderDetails ON Orders.OrderID = OrderDetails.OrderID "
//	}
}
