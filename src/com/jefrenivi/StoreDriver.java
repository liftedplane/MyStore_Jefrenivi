package com.jefrenivi;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
//testing
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
	
	//_________Welcome page_________
	private void welcomePage() throws SQLException {
		System.out.println("**********WELCOME TO JEFRENIVI**********\n");
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
	
	//_________Orders page_________
	private void orders() throws SQLException {
		System.out.println("********** ORDERS MENU **********");
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
		System.out.println("********** VIEWING ORDERS **********");
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
		viewOrders();

	}

	private void viewOpenOrders() throws SQLException {
		ResultSet rs = sql.getOpenOrders();
		displayResults(rs);
		viewOrders();
	}

	private void viewClosedOrders() throws SQLException {
		ResultSet rs = sql.getClosedOrders();
		displayResults(rs);
		viewOrders();
	}

	private void sortOrders() throws SQLException {
		System.out.println("********** HOW WOULD YOU LIKE TO SORT THE ORDERS **********");
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
		viewOrders();
	}

	// displays orders that exceed total $$$ inputed from user
	private void sortByExceedingTotal() throws SQLException {
		System.out.println("Enter $$$ Amount");
		double total = scan.nextDouble();
		ResultSet rs = sql.getAllOrdersWithTotalGreaterThan(total);
		displayResults(rs);
		sortByExceedingTotal();

	}

	private void viewSpecificOrder() throws SQLException {
		System.out.println("********** ENTER ORDER ID OF ORDER YOU WANT TO SEE **********");
		int orderId = scan.nextInt();
		ResultSet rs = sql.getOrder(orderId);
		displayResults(rs);
		viewSpecificOrder();
	}

	// waiting on J
	private void cancelOrders() {

	}
	
	
	//_________Cutomers Menu_________
	//Need to make customers methods
	private void customers() throws SQLException {
		System.out.println("********** CUSTOMERS MENU **********");
		System.out.println("1. View All Customers\n2. View Customer(s) by Zipcode");
		System.out.println("Enter Option Number");
		String opt = scan.nextLine();
		switch (opt) {
			case "1":
				viewAllCustomers();
				break;
			
			case "2":
				viewCustomerByZip();
				break;
			default: 
				System.err.println("Sorry, that option is not available");
				customers();
		}
			

	}
	
	private void viewAllCustomers() throws SQLException {
		System.out.println("********** VIEWING ALL CUSTOEMRS **********");
		ResultSet rs = sql.getAllCustomers();
		displayResults(rs);
		customers();
	}

	private void viewCustomerByZip() throws SQLException {
			System.out.println("Type in 5 digit zipcode to find Customer(s)");
			String zip = scan.nextLine();
			ResultSet rs = sql.getCustomersByZip(zip);
			displayResults(rs);
			viewCustomerByZip();
	}
	
	//_________Products Menu_________
	//need to make methods for products
	private void products() throws SQLException {
		System.out.println("********** PRODUCTS MENU **********");
		System.out.println("1. View All Products\n2. View All Products from a Category");
		System.out.println("Enter Option Number");
		String opt = scan.nextLine();
		switch (opt) {
		case "1":
			viewAllProducts();
			break;
			
		case"2":
			viewProductsFromCategory();
			break;
		
		default:
			System.err.println("Sorry, that option is not available");
			products();
		}

	}
	
	private void viewAllProducts() throws SQLException {
		System.out.println("********* VIEWING ALL PRODUCTS **********");
		ResultSet rs = sql.getAllProducts();
		displayResults(rs);
		products();
	}
	
	private void viewProductsFromCategory() throws SQLException {
		System.out.println("Type Category to view Product");
		String Catrgory = scan.nextLine();
		ResultSet rs = sql.getCustomersByZip(Catrgory);
		displayResults(rs);
		viewProductsFromCategory();
		}
	
	//_________Shippers and Supplies Menu_________
	//need to create method body for Ships and Supplies
	private void shippersAndSuppliers() {
		System.out.println("********** SHIPPERS AND SUPPLIERS MENU **********");
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
		int columnCount = rsmd.getColumnCount();
		int[] columnWidths = new int[columnCount];
		List<Object[]> rowList = new ArrayList<>();
		
		while (rs.next()) {

			Object[] row = new Object[columnCount];
			for (int i = 1; i <= columnCount; i++) {
				row[i - 1] = rs.getObject(i);
			}
			rowList.add(row);
		}
		
		for (int i = 1; i <= columnCount; i++) {
			if (rsmd.getColumnName(i).length() > columnWidths[i - 1]) {
				columnWidths[i - 1] = rsmd.getColumnName(i).length();
			}
		}
		
		rowList.forEach(r -> {
			for (int j = 0; j < columnCount; j++) {
				String value;
				if (r[j] != null) value = r[j].toString();
				else value = "null";
				if (value.length() > columnWidths[j]) {
					columnWidths[j] = value.length();
				}
			}
		});
		
		String horizontalLine = "+";
		for (int i = 0; i < columnWidths.length; i++) {
			for (int j = 0; j < columnWidths[i] + 2; j++) horizontalLine += "-";
			horizontalLine += "+";
		}


		System.out.println(horizontalLine);
		System.out.print("|");
		for (int i = 0; i < columnCount; i++) {
			System.out.printf(" %-" + columnWidths[i] + "s |", rsmd.getColumnName(i + 1));
		}
		System.out.println();
		System.out.println(horizontalLine);
		rowList.forEach(r -> {
			System.out.print("|");
			for (int j = 0; j < columnCount; j++) {
				System.out.printf(" %-" + columnWidths[j] + "s |", r[j]);
			}
			System.out.println();
		});
		System.out.println(horizontalLine);
	}

}
