package backend;

import java.rmi.Naming;

import common.ProductManagement;
import common.SessionManagement;

public class Backend {

	public static void main(String[] args) {
		try {
			SessionManagement session_endpoint = new SessionManagementImpl();
			ProductManagement product_endpoint = new ProductManagementImpl();
			
			String machine_name = "in-csci-rrpc01";
			String port = "5231";
			String session_name = String.format("//%s:%s/Session", machine_name, port);
			String product_name = String.format("//%s:%s/Product", machine_name, port);
			
			
			Naming.rebind(session_name, session_endpoint);
			Naming.rebind(product_name, product_endpoint);
			
			System.out.println("Server Ready");
		} catch (Exception e) {
			System.out.println("Server error: " + e.getMessage());
			e.printStackTrace();
		}
	}

}