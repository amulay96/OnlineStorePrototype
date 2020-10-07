package common;

import java.rmi.Remote;
import java.util.ArrayList;

public interface ProductManagement extends Remote{
	ArrayList<Product> getProducts(User user) throws Exception;
	void updateProduct(User user,String type, String description, Double price, Integer quantity) throws Exception;
	void removeProducts(User user, String type) throws Exception;
	void addProducts(User user, Product product) throws Exception;
	//void purchaseProducts(User user, ArrayList<Product> cart) throws Exception;
	Boolean canAddProductToCart(User user, Product product) throws Exception;
	public void addProductsToCart(User user,Product product) throws Exception;
	void purchaseProducts(User user) throws Exception;
}