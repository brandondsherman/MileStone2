//Kian Faroughi
//Csc165 - Assignment 1
//Doctor Gordon
//CSUS Fall 2015
//Helper class that is used to determine the components connecting to the system

package helpers;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Version;

public class FindComponents {

	public void listControllers()
	{
		System.out.println("JInput version: " + Version.getVersion());
		ControllerEnvironment ce = ControllerEnvironment.getDefaultEnvironment();
		
		//get the set of controllers from the controller environment 
		{
			Controller[] cs = ce.getControllers();
			
			//print details and sub-controllers for each of the controllers
			for (int i =0; i<cs.length; i++)
			{
				System.out.println("\nController #" + i);
				listComponents(cs[i]);
			}
		}
	}
	
	//Report the component information for a controller.
	//Recursively visit any sub-controller and report their details as well.
	
	private void listComponents(Controller controller)
	{
		System.out.println("Name: " + controller.getName() + ". Type ID: " + controller.getType());
		
		//get the components in the controller and list their details
		
		Component[] components = controller.getComponents();
		for(int i=0; i<components.length; i++)
		{
			//components[i].
			System.out.println(" " + components[i].getName() + " ID: " + components[i].getIdentifier());
		}
		
		Controller[] subControllers = controller.getControllers();
		for(int j=0; j<subControllers.length; j++)
		{
			System.out.println(" " + controller.getName() + " Subcontroller #" + j);
			listComponents(subControllers[j]);
		}
	}
	
}
