package frontend;

import common.User;

public class UserViewCommand implements CommandView{

	UserView userView;
	Dispatcher dispatcher;
	User user;
	
	public UserViewCommand(UserView userView, Dispatcher dispatcher,User user) {
		super();
		this.userView = userView;
		this.dispatcher=dispatcher;
		this.user=user;
	}

	/*
	 * Concrete Implementation of Command Pattern execute() method
	 */
	@Override
	public void execute() {
		UserController uc = new UserController(Frontend.product_endpoint, user, Frontend.scanner, 
				dispatcher, this.userView);
		userView.display();
	}
}
