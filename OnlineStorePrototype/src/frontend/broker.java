package frontend;


import java.util.ArrayList;

public class broker {
	
private ArrayList<CommandView> commandList = new ArrayList<CommandView>();
	
	public void addCommand(CommandView command)
	{
		commandList.add(command);
	}
	
	public void executeCommand()
	{
		for(CommandView command : commandList)
		{
			command.execute();
		}
	commandList.clear();
	}


}
