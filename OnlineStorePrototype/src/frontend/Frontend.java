package frontend;

import java.rmi.Naming;
import java.util.Scanner;

import common.ProductManagement;
import common.SessionManagement;

public class Frontend {
	public static SessionManagement session_endpoint = null;
	public static ProductManagement product_endpoint = null;
	public static Scanner scanner;
	private static  Dispatcher dispatcher;
	public static void main(String[] args) {
		try {
			String session_location = "//in-csci-rrpc01:5231/Session";
			String product_location = "//in-csci-rrpc01:5231/Product";
			session_endpoint = (SessionManagement)Naming.lookup(session_location);
			System.out.println("Found Server Session Object");
			product_endpoint = (ProductManagement)Naming.lookup(product_location);
			System.out.println("Found Server Product Object");	
			scanner = new Scanner(System.in);
			
			FrontController controller = new FrontController(session_endpoint, scanner);
			
			
			scanner.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}
}