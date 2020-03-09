package com.jefrenivi;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
	
	//_________Welcome page_________
	private void welcomePage() throws SQLException {
		System.out.println("\t********** WELCOME TO JEFRENIVI **********\n");
		System.out.println("Select options below\n1. Orders\n2. Customers\n3. Products\n4. Shippers and Suppliers\n5. Exit");
		System.out.println("\t<<<<<<<<<< Enter Option Number >>>>>>>>>>\n");
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
			
		case "5":
			try {
				System.out.println("Goodbye!");
				sql.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.exit(0);

		default:
			System.err.println("Sorry, that option is not available");
			welcomePage();
		}

	}
	
	//_________Orders page_________
	private void orders() throws SQLException {
		System.out.println("\t********** ORDERS MENU **********");
		System.out.println("1. View Orders\n2. Cancel Orders\n3. Go Back\n");
		System.out.println("\t<<<<<<<<<< Enter Option Number >>>>>>>>>>");
		String opt = scan.nextLine();
		switch (opt) {
		case "1":
			viewOrders();
			break;

		case "2":
			cancelOrders();
			break;
		
		case "3":
			welcomePage();
			break;

		default:
			System.err.println("Sorry, that option is not available");
			orders();

		}

	}

	private void viewOrders() throws SQLException {
		System.out.println("\t********** VIEWING ORDERS **********");
		System.out.println("1. View All Orders\n2. View Open Orders\n3. View Closed Orders\n4. Sort Orders\n5. View Specific Order\n6. Go Back\n7. Main Menu\n");
		System.out.println("\t<<<<<<<<<< Enter Option Number >>>>>>>>>>");
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
			
		case "6":
			orders();
			break;
			
		case "7":
			welcomePage();
			break;

		default:
			System.err.println("Sorry, that option is not available");
			viewOrders();

		}

	}

	//__________View Orders sub menus__________
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
		System.out.println("\t<<<<<<<<<< HOW WOULD YOU LIKE TO SORT THE ORDERS >>>>>>>>>>");
		System.out.println("1. Sort By Descending Total $$$ Amount\n2. View Orders Exceeding Given Total\n3. Go Back\n4. Main Menu\n");
		System.out.println("\t<<<<<<<<<< Enter Option Number >>>>>>>>>>");
		String opt = scan.nextLine();
		switch (opt) {
		case "1":
			sortByDescendingAmount();
			break;

		case "2":
			sortByExceedingTotal();
			
		case "3":
			viewOrders();
			break;
			
		case "4":
			welcomePage();
			break;

		default:
			System.err.println("Sorry, that option is not available");
			sortOrders();

		}
	}

	//__________Sort sub-menus__________
	private void sortByDescendingAmount() throws SQLException {
		ResultSet rs = sql.getAllOrdersDescendingTotal();
		displayResults(rs);
		viewOrders();
	}

	// displays orders that exceed total $$$ inputed from user
	private void sortByExceedingTotal() throws SQLException {
		try {
		System.out.println("\t<<<<<<<<<< Enter $$$ Amount >>>>>>>>>>");
		String input = scan.nextLine();
			double total = Double.parseDouble(input);
			ResultSet rs = sql.getAllOrdersWithTotalGreaterThan(total);
			displayResults(rs);
			sortOrders();
			
		} catch (NumberFormatException e) {
			System.err.println("Sorry, that wasn't a number. Try Again!");
			sortByExceedingTotal();
		}

	}

	private void viewSpecificOrder() throws SQLException {
		try{
			System.out.println("\t<<<<<<<<<< ENTER ORDER ID OF ORDER YOU WANT TO SEE >>>>>>>>>>>>");
			int orderId = scan.nextInt();
			scan.nextLine();
			ResultSet rs = sql.getOrder(orderId);
			displayResults(rs);
			rs = sql.getProductsFromOrder(orderId);
			displayResults(rs);
			viewOrders();	
			
		} catch (NumberFormatException e) {
			System.err.println("Sorry, that wasn't a number. Please Try Again");
			viewSpecificOrder();
			
		}
	}

	//__________Cancel Orders sub menu__________
	private void cancelOrders() throws SQLException {
		ResultSet rs = sql.getAllOrders();
		displayResults(rs);
		System.out.println("\t<<<<<<<<<< Please Enter Order ID You Want To Cancel >>>>>>>>>>>");
		int orderId = scan.nextInt();
		scan.nextLine();
		sql.deleteOrder(orderId); 
		
		if (orderId >= 1) {
			
			System.out.println("\t<<<<<<<<<< YOUR ORDER " + orderId + " WAS CANCELLED SUCCESSFULLY >>>>>>>>>>>>");
			orders();
			
		} else {
			System.err.println("Record Not Found, Please Try Again...");
			orders();
		}
		
	}

	//_________Cutomers Menu_________
	private void customers() throws SQLException {
		System.out.println("\t********** CUSTOMERS MENU **********");
		System.out.println("1. View All Customers\n2. View Customer(s) by Zipcode\n3. View Customer(s) Lifetime Total\n4. Go Back\n");
		System.out.println("\t<<<<<<<<<< Enter Option Number >>>>>>>>>>");
		String opt = scan.nextLine();
		switch (opt) {
			case "1":
				viewAllCustomers();
				break;
			
			case "2":
				viewCustomerByZip();
				break;
				
				
			case "3":
				viewCustomerTotal();
				break;
				
			case "4":
				welcomePage();
				break;
				
			default: 
				System.err.println("Sorry, that option is not available");
				customers();
		}

	}
	
	private void viewAllCustomers() throws SQLException {
		System.out.println("\t********** VIEWING ALL CUSTOMERS **********");
		ResultSet rs = sql.getAllCustomers();
		displayResults(rs);
		customers();
	}

	private void viewCustomerByZip() throws SQLException {
		try {
			System.out.println("\t<<<<<<<<<< Type in a 5 digit zipcode to find Customer(s) >>>>>>>>>>");
			String zip = scan.nextLine();
			ResultSet rs = sql.getCustomersByZip(zip);
			displayResults(rs);
			customers();
		} catch (NumberFormatException e) {
			System.err.println("Sorry,that wasn't a number. Please try again.");
			viewCustomerByZip();
		}
	}
	
	//shows customers lifetime $$$ sum purchase
	private void viewCustomerTotal() throws SQLException {
		try {
			System.out.println("\t<<<<<<<<<< Enter $$$ Amount >>>>>>>>>>");
			double total = scan.nextDouble();
			scan.nextLine();
			ResultSet rs = sql.getCustomersByLifetimeTotals(total);
			displayResults(rs);
			customers();
		} catch (NumberFormatException e) {
			System.err.println("Sorry, that wasn't a number. Please try again");
			viewCustomerTotal();
		}
		
	}
	
	//_________Products Menu_________
	private void products() throws SQLException {
		System.out.println("\t********** PRODUCTS MENU **********");
		System.out.println("1. View All Products\n2. View All Products from a Category\n3. Go Back\n");
		System.out.println("\t<<<<<<<<<< Enter Option Number >>>>>>>>>>");
		String opt = scan.nextLine();
		switch (opt) {
		case "1":
			viewAllProducts();
			break;
			
		case"2":
			viewProductsFromCategory();
			break;
			
		case "3":
			welcomePage();
			break;
		
		default:
			System.err.println("Sorry, that option is not available");
			products();
		}

	}
	
	private void viewAllProducts() throws SQLException {
		System.out.println("\t********* VIEWING ALL PRODUCTS **********");
		ResultSet rs = sql.getAllProducts();
		displayResults(rs);
		products();
	}
	
	private void viewProductsFromCategory() throws SQLException {
		ResultSet rs = sql.getAllCategories();
		displayResults(rs);
		System.out.println("\t<<<<<<<<<< Type Category Number to view Products >>>>>>>>>>");
		String input = scan.nextLine();
		int category = Integer.parseInt(input);
		rs = sql.getProductsFromCategory(category);
		displayResults(rs);
		products();
		
		}
	
	//_________Shippers and Supplies Menu_________
	private void shippersAndSuppliers() throws SQLException {
		System.out.println("\t********** SHIPPERS AND SUPPLIERS MENU **********");
		System.out.println("1. View Shippers\n2. View Suppliers\n3. Go Back\n");
		System.out.println("\t<<<<<<<<<< Enter Option Number >>>>>>>>>>");
		String opt = scan.nextLine();
		switch (opt) {
		case "1":
			viewShippers();
			break;
	
		case "2":
			viewSuppliers();
			break;
			
		case "3":
			welcomePage();
			break;
			
		default:
			System.err.println("Sorry, that option is not available");
			shippersAndSuppliers();
		}
	}
	
	private void viewShippers() throws SQLException {
		System.out.println("\t********** Viewing All Shippers **********");
		ResultSet rs = sql.getAllShippers();
		displayResults(rs);
		shippersAndSuppliers();
	}
	
	private void viewSuppliers() throws SQLException {
		System.out.println("\t**********Viewing All Suppliers **********");
		ResultSet rs = sql.getAllSuppliers();
		displayResults(rs);
		shippersAndSuppliers();
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
