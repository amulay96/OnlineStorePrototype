package frontend;
import common.User;

public class Dispatcher {
	
	private AdminView adminView;
	private UserView userView;
	private LoginView loginView;
	private broker broker;	
	private CommandFactory factory;
	
	public Dispatcher(LoginView view)
	{
		super();
	this.adminView = new AdminView();
	this.userView = new UserView();
	this.loginView = view;
	this.broker = new broker();
	this.factory = new CommandFactory();
	}


	public void dispatch(User u)
	{
		if(u!=null)
		{
			if(u.getRole().equals(User.Role.ADMIN))
			{
				System.out.println("Dispatcher : Dispatching request to AdminView");
				AdminViewCommand cmd1 = this.factory.createAdminCommand(adminView,this, u);	//Creating Admin Command Factory Object
				broker.addCommand(cmd1);	// Adding to Command List
				broker.executeCommand();	// Excuting the Command List
			}
			else
			{
				System.out.println("Dispatcher : Dispatching request to UserView");
				UserViewCommand cmd2=this.factory.createUserCommand(userView,this,u);	//Creating User Command Factory Object
				broker.addCommand(cmd2);	//Adding to Command List
				broker.executeCommand();	//Executing the Command List
			}
		}
		else
		{
			loginView.display();
		}
	}

}
