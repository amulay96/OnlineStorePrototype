package frontend;

public class LoginView {
	
	private FrontController controller;
	
	public void display()
	{
		this.controller.getChoice();
	}
	public void displayResults(String result)
	{
		System.out.println(result);
		System.out.println("------------------------");
	}
	
	public void setController(FrontController controller) {
		this.controller = controller;
	}
}