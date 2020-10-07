package common;

import java.rmi.Remote;

public interface SessionManagement extends Remote{
	void registerAdmin(User user, String username, String password) throws Exception;
	void registerCustomer(String username, String password) throws Exception;
	User login(String username, String password) throws Exception;
	void removeCustomer(User user, String username) throws Exception;
}