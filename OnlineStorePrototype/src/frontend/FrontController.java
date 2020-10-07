package frontend;

import java.util.HashMap;
import java.util.Scanner;

import common.SessionManagement;
import common.User;

public class FrontController {
	private LoginView loginView;
	private SessionManagement session_endpoint;
	private Scanner scanner;
	private Boolean isActive;
	
	Dispatcher dispatcher;

	public FrontController(SessionManagement session_endpoint, Scanner scanner) {
		loginView = new LoginView();
		loginView.setController(this);
		this.session_endpoint = session_endpoint;
		this.scanner = scanner;
		this.isActive = true;
		this.dispatcher = new Dispatcher(this.loginView);
		dispatcher.dispatch(null);
	}

	/*
	 * method used to simulate user input user input will be received from command
	 * line
	 */
	public void getChoice() {

		while (this.isActive) {
			System.out.println("Home Options");
			System.out.println("1 -- Login");
			System.out.println("2 -- Register");
			System.out.println("3 -- Exit");
			boolean login = false;
			User u=null;
			
			while (this.isActive && !login) {
				// Get user input
				int choice = this.scanner.nextInt();
				this.scanner.nextLine();
				switch (choice) {
				case 1: // Login
					HashMap<String, String> creds = getCredential();
					u = login(creds);
					login = true;
					break;
				case 2: // Register
					register();
					break;
				case 3: // Quit
					this.isActive = false;
					break;
				default: // Invalid Input
					this.loginView.displayResults("INVALID OPTION");
				}
			}
			
			if(login){
				this.dispatcher.dispatch(u);
			}
		}

	}

	/*
	 * method used to login a user with provided credentials login using session
	 * interface display results
	 */
	private User login(HashMap<String, String> creds) {
		String username = creds.get("USERNAME");
		String password = creds.get("PASSWORD");
		try {
			User u = session_endpoint.login(username, password);
			//this.dispatcher.dispatch(u);
			return u;
		} catch (Exception e) {
			this.loginView.displayResults(e.getMessage());
			return null;
		}
	}

	/*
	 * method used to register a customer retrieve desired credentials register
	 * using session interface display results
	 */
	private void register() {
		HashMap<String, String> creds = getCredential();
		String username = creds.get("USERNAME");
		String password = creds.get("PASSWORD");

		try {
			session_endpoint.registerCustomer(username, password);
			this.loginView.displayResults("REGISTERED SUCCESSFULLY");
		} catch (Exception e) {
			this.loginView.displayResults(e.getMessage());
		}
	}

	/*
	 * Used to retrieve username, password details from user credentials are stored
	 * and returned in key, value pairs
	 */
	private HashMap<String, String> getCredential() {
		System.out.println("Enter Username : ");
		String username = this.scanner.nextLine();
		System.out.println("Enter Password : ");
		String password = this.scanner.nextLine();

		HashMap<String, String> result = new HashMap<String, String>();
		result.put("USERNAME", username);
		result.put("PASSWORD", password);
		return result;
	}
}