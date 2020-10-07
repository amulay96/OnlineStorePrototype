package frontend;

import java.util.ArrayList;
import java.util.Scanner;

import common.Product;
import common.ProductManagement;
import common.User;

public class UserController{

	private UserView view;
	private User user;
	private ProductManagement product_endpoint;
	private ArrayList<Product> cart;
	private Scanner scanner;
	private Boolean isActive;
	private Dispatcher dispatcher;
	
	public UserController(ProductManagement product_endpoint, User user, Scanner scanner,
			Dispatcher dispatcher, UserView view) {
		this.view = view;
		this.view.setController(this);
		this.user = user;
		this.product_endpoint = product_endpoint;
		this.cart = new ArrayList<Product>();
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
			// Get user input
			System.out.println("User Options");
			System.out.println("1 -- Browse Products");
			System.out.println("2 -- Add Product to Cart");
			System.out.println("3 -- Purchase Items in Cart");
			System.out.println("4 -- Logout");
			int input = this.scanner.nextInt();
			this.scanner.nextLine();
			
			switch (input) {
				case 1:
					try {
						ArrayList<Product> products = this.product_endpoint.getProducts(this.user);
						this.view.displayResults(products.toString());
					} catch (Exception e) {
						this.view.displayResults(e.getMessage());
					}
					break;
				case 2: // Add Product to Cart
					addToCart();
					break;
				case 3: // Purchase Products in Cart
					try {
						this.product_endpoint.purchaseProducts(this.user);	
						//empty the cart
						//this.cart = new ArrayList<Product>();
						this.view.displayResults("Successfully Purchased");
					} catch (Exception e) {
						this.view.displayResults(e.getMessage());
					}
					break;
				case 4: // Logout
					//Frontend.setNext(new LoginController(Frontend.session_endpoint, this.scanner,dispatcher));
					this.isActive = false;
					break;
				default: // Invalid Input
					this.view.displayResults("INVALID OPTION");
				}
		}	
	}
	
	/*
	 * method used to add a Product to Cart
	 * Retrieve User Input
	 * Use Product interface to add to cart
	 */
	private void addToCart() {
		System.out.println("Enter Product Type : ");
		String type = this.scanner.nextLine();
		System.out.println("Enter Quantity : ");
		Integer quantity = this.scanner.nextInt();
		this.scanner.nextLine();
		
		try {
			Product p = new Product(type.toUpperCase(), "", 0.00, quantity);
			if (this.product_endpoint.canAddProductToCart(this.user, p)) {
				//this.cart.add(p);
				this.product_endpoint.addProductsToCart(user, p);
				this.view.displayResults("ADDED ITEM TO CART");
			}
			else {
				this.view.displayResults("CAN NOT ADD PRODUCT TO CART");
			}
		} catch (Exception e) {
			this.view.displayResults(e.getMessage());
		}
	}
}