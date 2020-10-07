package backend;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import common.User;

public class SessionManagementDAO {
	
	/*
	 * Implementation of Database Access Objects for User Session Management
	 */
	
	public static void registerAdmin(User user, String username, String password) throws Exception {
		if (!checkUserExists(username)) {
			if (isAdminRole(user)) {
				String insertUser_sql = "insert into Users values ('"+username+"','"+password+"',"+"'ADMIN'"+");";
				PreparedStatement pst;
				try {
					pst = SQLConn.con.prepareStatement(insertUser_sql);
					int i = pst.executeUpdate();
					System.out.println(i+"Rows Inserted");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				throw new Exception("ONLY ADMINS CAN REGISTER OTHER ADMINS");
			} 
		}
		else {
			throw new Exception("USERNAME ALREADY EXISTS");
		}

	}

	public static void registerCustomer(String username,String password) throws Exception
	{
		if(!checkUserExists(username))
		{
			String insertUser_sql = "insert into Users values ('"+username+"','"+password+"',"+"'CUSTOMER'"+")";
			PreparedStatement pst;
			try {
				pst = SQLConn.con.prepareStatement(insertUser_sql);
				int i = pst.executeUpdate();
				System.out.println(i+"Rows Inserted");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else
		{
			throw new Exception("USERNAME ALREADY EXISTS");
		}
	}
	
	public static void removeCustomer(User user, String username) throws Exception
	{
		if(isAdminRole(user))
		{
			if(checkUserExists(username))
			{
				try {
					String usernameDelete_sql = "delete from Users where Username='"+username+"'";
					PreparedStatement pst;
					pst = SQLConn.con.prepareStatement(usernameDelete_sql);
					int i = pst.executeUpdate();
					System.out.println(i+"Rows Deleted");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else
			{
				throw new Exception("USERNAME DOES NOT EXIST");
			}
		}
		else
		{
			throw new Exception("ONLY ADMINS CAN REMOVE CUSTOMERS");
		}
	}	
	
	public static User login(String username,String password) throws Exception
	{
		String u=username;
		String p=password;
		try {
			String select_login = "select Username,Password,Role from Users;";
			Statement st = SQLConn.con.createStatement();
			ResultSet rs = st.executeQuery(select_login);
			while(rs.next())
			{
				if(rs.getString(1).equals(u) && rs.getString(2).equals(p))
				{
					String temp=rs.getString(3);
					User usr = new User(u, p, User.Role.valueOf(temp.toUpperCase()));
					return usr;
				}
				else if(rs.getString(1).equals(u) && !rs.getString(2).equals(p))
				{
					throw new Exception("INCORRECT PASSWORD");
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		throw new Exception("USERNAME NOT FOUND");
	}


	private static Boolean checkUserExists(String username)
	{
		try {
			String usernames_sql = "select Username from Users;";
			Statement st = SQLConn.con.createStatement();
			ResultSet rs = st.executeQuery(usernames_sql);
			while(rs.next())
			{
				if(rs.getString(1).equals(username))
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
			String select_role = "select Role from Users where Username='"+user.getUsername()+"'";
			Statement st = SQLConn.con.createStatement();
			ResultSet rs = st.executeQuery(select_role);
			rs.next();
			String role = rs.getString(1);
			if(role.equals("ADMIN"))
			{
				return true;
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return false;
	}

}
