package frontend;


public class UserView {

	private UserController controller;
	
	public void display() {

		controller.getChoice();
	}
	
	public void displayResults(String result) {
		System.out.println(result);
		System.out.println("------------------------");
	}
	
	public void setController(UserController controller) {
		this.controller = controller;
	}
	
}