package frontend;



public class AdminView {
	private AdminController controller;
	
	public void display() {
		
		this.controller.getChoice();
	}
	
	public void displayResults(String result) {
		System.out.println(result);
		System.out.println("------------------------");
	}

	public void setController(AdminController controller) {
		this.controller = controller;
	}
	
}