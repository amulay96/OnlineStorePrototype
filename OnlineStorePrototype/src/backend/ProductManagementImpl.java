package backend;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import common.Product;
import common.ProductManagement;
import common.User;

public class ProductManagementImpl extends UnicastRemoteObject implements ProductManagement{
	private static final long serialVersionUID = -7137128944927005487L;
	//private ArrayList<Product> products;
	
	
	public ProductManagementImpl() throws RemoteException{
		super();
//		ArrayList<Product> products = new ArrayList<Product>();
//		products.add(new Product("SHOE", "THIS IS A SHOE", 19.99, 10));
//		products.add(new Product("SHIRT", "THIS IS A SHIRT", 9.99, 25));
//		products.add(new Product("PANTS", "THIS IS A PANT", 14.99, 30));
//		products.add(new Product("WATCH", "THIS IS A WATCH", 99.99, 5));
//		this.products = products;
		SQLConn.getConnection();
	}

	/*
	 * Caling Database Access Objects for Product Management
	 */
	
	@Override
	public ArrayList<Product> getProducts(User user) throws Exception {
		return ProductManagementDAO.getProducts(user);
	}
	
	public void updateProduct(User user,String type,String description,Double price,Integer quantity) throws Exception
	{	
		ProductManagementDAO.updateProduct(user, type, description, price, quantity);			
	}

	public void removeProducts(User user,String type) throws Exception
	{
		ProductManagementDAO.removeProducts(user, type);
	}
	
	public void addProducts(User user,Product product) throws Exception
	{
		ProductManagementDAO.addProducts(user, product);
	}
	
	public void addProductsToCart(User user,Product product) throws Exception
	{
		ProductManagementDAO.addProductToCart(user,product);
	}

	public void purchaseProducts(User user) throws Exception
	{
		ProductManagementDAO.purchaseProducts(user);
	}

	
	public Boolean canAddProductToCart(User user,Product product) throws Exception
	{
		return ProductManagementDAO.canAddProductToCart(user, product);
	}	
}