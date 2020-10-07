package backend;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import common.Product;
import common.User;

public class ProductManagementDAO {
	
	/*
	 * Implementation of Database Access Objects for Product Management
	 */
	
	public static ArrayList<Product> getProducts(User user)
	{
		ArrayList<Product> data = new ArrayList<Product>();
		ArrayList<String> intermediate = new ArrayList<String>();
		
		try
		{
			String select_sql = "select * from Products";
			Statement st = SQLConn.con.createStatement();
			ResultSet rs = st.executeQuery(select_sql);
			while(rs.next())
			{
				intermediate.add(rs.getString(1));
				intermediate.add(rs.getString(2));
				intermediate.add(rs.getString(3));
				intermediate.add(rs.getString(4));
				Product p = new Product(intermediate.get(0).toString(),intermediate.get(1).toString(),Double.valueOf(intermediate.get(2)),Integer.valueOf(intermediate.get(3)));
				data.add(p);
				intermediate.clear();
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
		return data;
	}

	public static void updateProduct(User user,String type,String description,Double price,Integer quantity) throws Exception
	{
		if(isAdminRole(user))
		{
			if(checkProductExists(type))
			{
				String update_sql ="update Products set Product_description='"+description+"', Product_price='"+price+"', Product_quantity='"+quantity+"' where Product_type='"+type+"'";
				PreparedStatement pst;
				try {
					pst = SQLConn.con.prepareStatement(update_sql);
					int i = pst.executeUpdate();
					System.out.println(i+"Rows Inserted");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else
			{
				throw new Exception("PRODUCT DOESN'T EXIST");
			}	
		}
		else
		{
			throw new Exception("ONLY ADMINS CAN UPDATE PRODUCTS");
		}
			
	}
	
	public static void removeProducts(User user,String type) throws Exception
	{
		if(isAdminRole(user))
		{
			if(checkProductExists(type))
			{
				String delete_sql = "delete from Products where Product_type='"+type+"'";
				PreparedStatement pst;
				try {
					pst = SQLConn.con.prepareStatement(delete_sql);
					pst.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else
			{
				throw new Exception("PRODUCT DOESN'T EXIST");
			}
		}
		else
		{
			throw new Exception("ONLY ADMINS CAN DELETE PRODUCTS");
		}
	}
	
	public static void addProducts(User user,Product product) throws Exception
	{
		if(isAdminRole(user))
		{
			if(!checkProductExists(product.getType()))
			{
				String insert_sql = "insert into Products values ('"+product.getType()+"','"+product.getDescription()+"','"+product.getPrice()+"','"+product.getQuantity()+"');";
				PreparedStatement pst;
				try {
					pst = SQLConn.con.prepareStatement(insert_sql);
					int i = pst.executeUpdate();
					System.out.println(i+"Rows Inserted");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else
			{
				throw new Exception("PRODUCT ALREADY EXISTS");
			}
		}
		else
		{
			throw new Exception("ONLY ADMINS CAN ADD PRODUCTS");
		}
	}
	
	public static void addProductToCart(User user,Product product) throws Exception
	{
		if(isAdminRole(user) || isCustomerRole(user))
		{
			String insert_to_cart_sql = "insert into Cart values ('"+product.getType()+"','"+product.getDescription()+"','"+product.getPrice()+"','"+product.getQuantity()+"');";
			PreparedStatement pst;
			try {
				pst = SQLConn.con.prepareStatement(insert_to_cart_sql);
				int i = pst.executeUpdate();
				System.out.println(i+"Rows Inserted in Cart");
			}
			catch (SQLException e) {
				e.printStackTrace();
				}
		}
		else
		{
			throw new Exception("ONLY ADMINS CAN ADD PRODUCTS");
		}
	}
	
	public static void purchaseProducts(User user) throws Exception
	{
		if(isCustomerRole(user))
		{
			ArrayList<Product> productsFromCart = getCartProducts(user);
			for(int i=0;i<productsFromCart.size();i++)
			{
				double amount=0.0;
				amount +=productsFromCart.get(i).getQuantity()*productsFromCart.get(i).getPrice();
				String select_Quantity = "select Product_quantity from Products where Product_type='"+productsFromCart.get(i).getType()+"'";
				Statement st = SQLConn.con.createStatement();
				ResultSet rs = st.executeQuery(select_Quantity);
				rs.next();
				int q = rs.getInt(1);
				q=q-productsFromCart.get(i).getQuantity();
				String update_after_purchase = "update Products set Product_quantity='"+q+"' where Product_type='"+productsFromCart.get(i).getType()+"'";
				PreparedStatement pst;
				try {
					pst = SQLConn.con.prepareStatement(update_after_purchase);
					pst.executeUpdate();
					//System.out.println("Total Amount For Order : "+amount);
					//System.out.println(i1+"Rows Updated");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			String delete_after_purchase = "delete from Cart;";
			PreparedStatement pst;
			try {
				pst = SQLConn.con.prepareStatement(delete_after_purchase);
				pst.executeUpdate();
				System.out.println("Cart Empty");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static ArrayList<Product> getCartProducts(User user)
	{
		ArrayList<Product> data = new ArrayList<Product>();
		ArrayList<String> intermediate = new ArrayList<String>();
		
		try
		{
			String select_sql = "select * from Cart";
			Statement st = SQLConn.con.createStatement();
			ResultSet rs = st.executeQuery(select_sql);
			while(rs.next())
			{
				intermediate.add(rs.getString(1));
				intermediate.add(rs.getString(2));
				intermediate.add(rs.getString(3));
				intermediate.add(rs.getString(4));
				Product p = new Product(intermediate.get(0).toString(),intermediate.get(1).toString(),Double.valueOf(intermediate.get(2)),Integer.valueOf(intermediate.get(3)));
				data.add(p);
				intermediate.clear();
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
		return data;
	}
	

	
	public static Boolean canAddProductToCart(User user,Product product) throws Exception
	{
		if(isCustomerRole(user))
		{
			ArrayList<Product> returnedProds = getProducts(user);
			for(Product p : returnedProds)
			{
				if(p.getType().equals(product.getType()))
				{
					if(p.getQuantity()>=product.getQuantity())
					{
						return true;
					}
					else
					{
						throw new Exception("QUANTITY DEMANDED IS GREATOR THAN INVENTORY");
					}
				}
			}
			throw new Exception("CANNOT FIND PRODUCT");
		}
		else
		{
			throw new Exception("ONLY CUSTOMER CAN ADD TO CART");
		}
	}
	
	private static Boolean checkProductExists(String type)
	{
		try {
			String select_ptypes = "select Product_type from Products;";
			Statement st = SQLConn.con.createStatement();
			ResultSet rs = st.executeQuery(select_ptypes);
			while(rs.next())
			{
				if(rs.getString(1).equals(type))
				{
					return true;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	private static Boolean isAdminRole(User user)
	{
		try {
			String select_ptypes = "select Role from Users where Username='"+user.getUsername()+"'";
			Statement st = SQLConn.con.createStatement();
			ResultSet rs = st.executeQuery(select_ptypes);
			rs.next();
			String role = rs.getString(1);
			if(role.equals("ADMIN"))
			{
				return true;
			}
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	private static Boolean isCustomerRole(User user)
	{
		String role="";
		try {
			String select_ptypes_cust = "select Role from Users where Username='"+user.getUsername()+"'";
			Statement st = SQLConn.con.createStatement();
			ResultSet rs = st.executeQuery(select_ptypes_cust);
			rs.next();
			role = rs.getString(1);
			if(role.equals("CUSTOMER"))
			{
				System.out.println(role);
				return true;
			}
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(role);
		return false;
	}

}
