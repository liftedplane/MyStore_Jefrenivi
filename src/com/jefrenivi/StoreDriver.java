package com.jefrenivi;

import java.sql.ResultSet;
import java.util.Scanner;

//need to import JDBC class from jeff

public class StoreDriver {
	Scanner scan = new Scanner(System.in);
	//need to make an object of JDBC class from jeff
	
	
	public static void main(String[] args) {
		new StoreDriver().welcomePage();
	}
	
	private void welcomePage() {
		System.out.println("Welcome to Jeffrenivi!");
		System.out.println("Select options below\n"
				+ "1. Orders\n"
				+ "2. Customers\n"
				+ "3. Products\n");
		
		System.out.println("Enter option number");
		String opt = scan.nextLine();
		
		switch(opt) {
		case "1": 
			orders();
			break;
		
		case "2": 
			customers();
			break;
			
		case "3": 
			products();
			break;
			
		default:
			System.out.println("Sorry, that option is not available\n");
			welcomePage();
		}
		
	}
	
	private void orders() {
		System.out.println("What would you like to do?");
		System.out.println("1. View orders\n"
				+ "2. Cancel orders\n");
		System.out.println("Enter option number");
		String opt = scan.nextLine();
		switch(opt) {
		case "1": 
			viewOrders();
			break;
		
		case "2": 
			
			break;
		
		default:
			System.out.println("Sorry tat option is not available\n");
			orders();
			
		}
		
	}
	
	private void viewOrders() {
		System.out.println("1. View All"
				+ "2. View Open orders\n"
				+ "3. View Closed orders\n"
				+ "4. Sort orders\n"
				+ "5. View Specific order\n");
		System.out.println("Enter option number");
		String opt = scan.nextLine();
		switch(opt) {
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
			System.out.println("Sorry, that option is not available");
			viewOrders();
			
		}
		
	}
	
	//sql will be the name of the JDBC object name, have not been made yet.
	private void viewAll() {
		ResultSet rs = sql.allOrders();
		while(rs.next) {
			System.out.println(rs.getString(columnIndex));
		}
	}
	
	//body will generally be similar to viewAll()
	private void viewOpenOrders() {
		ResultSet rs = sql.
	}
	
	
	
	private void customers() {
		
	}
	
	private void products() {
		
	}

}
