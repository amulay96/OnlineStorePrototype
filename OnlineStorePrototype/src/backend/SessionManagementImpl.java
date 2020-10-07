package backend;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import common.SessionManagement;
import common.User;

public class SessionManagementImpl extends UnicastRemoteObject implements SessionManagement {
	private static final long serialVersionUID = 3697865453300084619L;
	//private ArrayList<User> users;
	
	public SessionManagementImpl() throws RemoteException{
		super();
//		ArrayList<User> users = new ArrayList<User>();
//		users.add(new User("ADMIN1", "ADMIN1PASS", User.Role.ADMIN));
//		this.users = users;
		SQLConn.getConnection();
	}

	/*
	 * Calling Database Access Objects for SessionManagement
	 */
	@Override
	public void registerAdmin(User user, String username, String password) throws Exception {
		SessionManagementDAO.registerAdmin(user, username, password);	
	}
	
	public void registerCustomer(String username,String password) throws Exception
	{
		SessionManagementDAO.registerCustomer(username, password);
	}
	
	public User login(String username,String password) throws Exception
	{
		return SessionManagementDAO.login(username, password);
	}

	public void removeCustomer(User user, String username) throws Exception
	{
		SessionManagementDAO.removeCustomer(user, username);
	}	
}