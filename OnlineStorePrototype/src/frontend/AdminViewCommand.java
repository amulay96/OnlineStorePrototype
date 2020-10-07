package frontend;

import common.User;

public class AdminViewCommand implements CommandView{

	AdminView adminView;
	Dispatcher dispatcher;
	User user;
	
	public AdminViewCommand(AdminView adminView,Dispatcher dispatcher, User u) {
		super();
		this.adminView = adminView;
		this.dispatcher= dispatcher;
		this.user = u;
	}

	/*
	 * Concrete Implementation of Command Pattern execute() method
	 */
	@Override
	public void execute() {
		AdminController ac = new AdminController(this.user, Frontend.product_endpoint, Frontend.session_endpoint, 
				Frontend.scanner, dispatcher, this.adminView);
		adminView.display();
	}

}
