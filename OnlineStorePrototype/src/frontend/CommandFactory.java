package frontend;

import common.User;

public class CommandFactory {
	
	/*
	 * Methods for Navigate to Corresponding View Command 
	 */
	
	public AdminViewCommand createAdminCommand(AdminView adminView,Dispatcher dispatcher, User u)
	{
		return new AdminViewCommand(adminView, dispatcher, u);
	}
	public UserViewCommand createUserCommand(UserView userView, Dispatcher dispatcher,User user)
	{
		return new UserViewCommand(userView, dispatcher, user);
	}
}