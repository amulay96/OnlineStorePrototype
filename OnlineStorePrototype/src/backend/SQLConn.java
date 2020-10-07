package backend;

import java.sql.Connection;
import java.sql.DriverManager;
/*
 * Connecting to MYSQL Database
 */
public class SQLConn {

	public static Connection con;
	public static void getConnection() {
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			String username="Admin";
			String password="";
			con = DriverManager.getConnection("jdbc:mysql://localhost:8597/onlinestore?useSSL=false",username,password);
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}

	}

}
