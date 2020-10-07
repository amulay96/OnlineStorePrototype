package frontend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import common.Product;
import common.ProductManagement;
import common.SessionManagement;
import common.User;

public class AdminController {

	private AdminView view;
	private User user;
	private ProductManagement product_endpoint;
	private SessionManagement session_endpoint;
	private Scanner scanner;
	private Boolean isActive;
	private Dispatcher dispatcher;

	public AdminController(User user, ProductManagement product_endpoint, SessionManagement session_endpoint,
			Scanner scanner,Dispatcher dispatcher, AdminView view) {
		this.view = view;
		view.setController(this);

		this.user = user;
		this.product_endpoint = product_endpoint;
		this.session_endpoint = session_endpoint;
		this.scanner = scanner;
		this.isActive = true;
		this.dispatcher=dispatcher;
	}

	/*
	 * method used to simulate user input user input will be received from command
	 * line
	 */
	
	public void getChoice() {
		while (this.isActive) {
			System.out.println("Admin Options");
			System.out.println("1 -- View Products");
			System.out.println("2 -- Add Product");
			System.out.println("3 -- Update Product");
			System.out.println("4 -- Delete Product");
			System.out.println("5 -- Add Admin");
			System.out.println("6 -- Add Customer");
			System.out.println("7 -- Delete Customer");
			System.out.println("8 -- Logout");
			// Get user input
			int input = this.scanner.nextInt();
			this.scanner.nextLine();
			
			ArrayList<String> paramList;
			HashMap<String, String> values;
			
			switch (input) {
			case 1: // View Product
				try {
					ArrayList<Product> products = this.product_endpoint.getProducts(this.user);
					this.view.displayResults(products.toString());
				} catch (Exception e) {
					this.view.displayResults(e.getMessage());
				}
				break;
			case 2: // Add Product
				paramList = new ArrayList<String>();
				paramList.add("TYPE");
				paramList.add("DESCRIPTION");
				paramList.add("PRICE");
				paramList.add("QUANTITY");
				values = getDetails(paramList);
				try {
					Product p = new Product(values.get("TYPE"), values.get("DESCRIPTION"),
							Double.parseDouble((values.get("PRICE"))), Integer.parseInt(values.get("QUANTITY")));
					this.product_endpoint.addProducts(user, p);
					this.view.displayResults("PRODUCT ADDED");
				} catch (Exception e) {
					this.view.displayResults(e.getMessage());
				}
				break;
			case 3: // Update Product
				paramList = new ArrayList<String>();
				paramList.add("TYPE");
				paramList.add("DESCRIPTION");
				paramList.add("PRICE");
				paramList.add("QUANTITY");
				values = getDetails(paramList);
				try {
					this.product_endpoint.updateProduct(this.user, values.get("TYPE"), values.get("DESCRIPTION"),
							Double.parseDouble((values.get("PRICE"))), Integer.parseInt(values.get("QUANTITY")));
					this.view.displayResults("PRODUCT UPDATED");
				} catch (Exception e) {
					this.view.displayResults(e.getMessage());
				}
				break;
			case 4: // Delete Product
				paramList = new ArrayList<String>();
				paramList.add("TYPE");
				values = getDetails(paramList);
				try {
					this.product_endpoint.removeProducts(this.user, values.get("TYPE"));
					this.view.displayResults("PRODUCT REMOVED");
				} catch (Exception e) {
					this.view.displayResults(e.getMessage());
				}
				break;
			case 5: // Add Admin
				paramList = new ArrayList<String>();
				paramList.add("USERNAME");
				paramList.add("PASSWORD");
				values = getDetails(paramList);
				try {
					this.session_endpoint.registerAdmin(this.user, values.get("USERNAME"), values.get("PASSWORD"));
					this.view.displayResults("ADMIN ADDED");
				} catch (Exception e) {
					this.view.displayResults(e.getMessage());
				}
				break;
			case 6: // Add Customer
				paramList = new ArrayList<String>();
				paramList.add("USERNAME");
				paramList.add("PASSWORD");
				values = getDetails(paramList);
				try {
					this.session_endpoint.registerCustomer(values.get("USERNAME"), values.get("PASSWORD"));
					this.view.displayResults("CUSTOMER ADDED");
				} catch (Exception e) {
					this.view.displayResults(e.getMessage());
				}
				break;
			case 7: // Remove Customer
				paramList = new ArrayList<String>();
				paramList.add("USERNAME");
				values = getDetails(paramList);
				try {
					this.session_endpoint.removeCustomer(this.user, values.get("USERNAME"));
					this.view.displayResults("CUSTOMER REMOVED");
				} catch (Exception e) {
					this.view.displayResults(e.getMessage());
				}
				break;
			case 8: // Logout
				//Frontend.setNext(new LoginController(Frontend.session_endpoint, this.scanner,dispatcher));
				this.isActive = false;
				break;
			default: // Invalid Input
				this.view.displayResults("INVALID OPTION");
			}
		}
	}

	/*
	 * method used to retrieve input from user
	 * input to received should be in an array
	 * result is stored and returned as key, value (Hashmap)
	 */
	public HashMap<String, String> getDetails(ArrayList<String> input) {
		HashMap<String, String> result = new HashMap<String, String>();
		for (String key : input) {
			System.out.println("Enter " + key);
			String value = this.scanner.nextLine();
			result.put(key, value);
		}
		return result;

	}
}