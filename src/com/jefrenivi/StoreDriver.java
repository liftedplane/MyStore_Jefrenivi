package com.jefrenivi;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

public class StoreDriver {
	Scanner scan = new Scanner(System.in);
	private static JDBC sql;

	public static void main(String[] args) {
		sql = new JDBC();
		try {
			new StoreDriver().welcomePage();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void welcomePage() throws SQLException {
		System.out.println("WELCOME TO JEFFRENIVI!!!");
		System.out.println("Select options below\n1. Orders\n2. Customers\n3. Products\n4. Shippers / Suppliers");
		System.out.println("Enter option number");
		String opt = scan.nextLine();

		switch (opt) {
		case "1":
			orders();
			break;

		case "2":
			customers();
			break;

		case "3":
			products();
			break;
		
		case "4":
			shippersAndSuppliers();
			break;

		default:
			System.err.println("Sorry, that option is not available");
			welcomePage();
		}

	}

	private void orders() throws SQLException {
		System.out.println("ORDERS MENU");
		System.out.println("1. View orders\n2. Cancel orders\n");
		System.out.println("Enter option number");
		String opt = scan.nextLine();
		switch (opt) {
		case "1":
			viewOrders();
			break;

		case "2":
			cancelOrders(); // still need to make body for method. waiting on jeff
			break;

		default:
			System.err.println("Sorry, that option is not available");
			orders();

		}

	}

	private void viewOrders() throws SQLException {
		System.out.println("VIEWING ORDERS");
		System.out.println("1. View All Orders\n2. View Open orders\n3. View Closed orders\n4. Sort orders\n5. View Specific order\n");
		System.out.println("Enter option number");
		String opt = scan.nextLine();
		switch (opt) {
		case "1":
			viewAll();
			break;

		case "2":
			viewOpenOrders();
			break;

		case "3":
			viewClosedOrders();
			break;

		case "4":
			sortOrders();
			break;

		case "5":
			viewSpecificOrder();
			break;

		default:
			System.err.println("Sorry, that option is not available");
			viewOrders();

		}

	}

	// outputs ALL orders open or closed
	private void viewAll() throws SQLException {
		ResultSet rs = sql.getAllOrders();
		displayResults(rs);

	}

	private void viewOpenOrders() throws SQLException {
		ResultSet rs = sql.getOpenOrders();
		displayResults(rs);
	}

	private void viewClosedOrders() throws SQLException {
		ResultSet rs = sql.getClosedOrders();
		displayResults(rs);
	}

	private void sortOrders() throws SQLException {
		System.out.println("HOW WOULD YOU LIKE TO SORT THE ORDERS???");
		System.out.println("1. Sort by descending total $$$ amount\n2. View Orders exceeding given total\n");
		System.out.println("Enter option number");
		String opt = scan.nextLine();
		switch (opt) {
		case "1":
			sortByDescendingAmount();
			break;

		case "2":
			sortByExceedingTotal();
			break;

		default:
			System.err.println("Sorry, that option is not available");
			sortOrders();

		}
	}

	private void sortByDescendingAmount() throws SQLException {
		ResultSet rs = sql.getAllOrdersDescendingTotal();
		displayResults(rs);
	}

	// displays orders that exceed total $$$ inputed from user
	private void sortByExceedingTotal() throws SQLException {
		System.out.println("Enter $$$ Amount");
		double total = scan.nextDouble();
		ResultSet rs = sql.getAllOrdersWithTotalGreaterThan(total);
		displayResults(rs);

	}

	private void viewSpecificOrder() throws SQLException {
		System.out.println("ENTER ORDER ID OF ORDER YOU WANT TO SEE...");
		int orderId = scan.nextInt();
		ResultSet rs = sql.getOrder(orderId);
		displayResults(rs);
	}

	// waiting on J
	private void cancelOrders() {

	}

	//Need to make customers methods
	private void customers() {
		System.out.println("CUSTOMERS MENU");
		System.out.println("1. View All Customers\n2. View Customer(s) by Zipcode");
		System.out.println("Enter Option Number");
		String opt = scan.nextLine();
		switch (opt) {
			case "1":
//				viewAllCustomers();
				break;
			
			case "2":
//				viewCustomerByZip();
				break;
			default: 
				System.err.println("Sorry, that option is not available");
				customers();
		}
			

	}

	//need to make methods for products
	private void products() {
		System.out.println("PRODUCTS MENU");
		System.out.println("1. View All Products\n2. View All Products from a Category");
		System.out.println("Enter Option Number");
		String opt = scan.nextLine();
		switch (opt) {
		case "1":
//			viewAllProducts();
			break;
			
		case"2":
//			viewProductsFromCategory();
			break;
		
		default:
			System.err.println("Sorry, that option is not available");
			products();
		}

	}
	
	//need to create method body for Ships and Supplies
	private void shippersAndSuppliers() {
		System.out.println("SHIPPERS AND SUPPLIERS MENU");
		System.out.println("1. View Shippers\n2. View Suppliers");
		System.out.println("Enter Option Number");
		String opt = scan.nextLine();
		switch (opt) {
		case "1":
//			viewShippers();
			break;
			
		case "2":
//			viewSuppliers();
			break;
			
		default:
			System.err.println("Sorry, that option is not available");
			shippersAndSuppliers();
		}
	}

	// displays results when called
	private void displayResults(ResultSet rs) throws SQLException {

		ResultSetMetaData rsmd = rs.getMetaData();

		while (rs.next()) {
			for (int i = 1; i <= rsmd.getColumnCount(); i++)
				System.out.printf("%s:\t%s\n", rsmd.getColumnName(i), rs.getObject(i));
				System.out.println();

		}
	}

}
