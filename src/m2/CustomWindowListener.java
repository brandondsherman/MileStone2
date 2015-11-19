//Kian Faroughi
//CSc 133 Fall 2014 - Snake!

package m2;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
/** Custom Window Listener so we can...
 * 	windowClosing(WindowEvent e) - prompt user to validate if they want to exit using the X adornment 
 * */
import javax.swing.JOptionPane;

import client.Client;

public class CustomWindowListener implements WindowListener {
	
private Client client;
private MyGame myGame;

public CustomWindowListener(MyGame myGame, Client client)
{
	super();
	this.client = client;
	this.myGame = myGame;
}
//asks if user really wants to quit
	public void windowClosing(WindowEvent e) {
		int result = JOptionPane.showConfirmDialog
			(e.getWindow(), 						
				"Are you sure you want to exit ?", 
				"Confirm Exit", 
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.QUESTION_MESSAGE);
		
				if (result == JOptionPane.YES_OPTION) { 
					
					myGame.byeMessage();
					myGame.quitGame();
					
				}
				else
				{
					return; 
				}
				
				
	}
	
	@Override
	/** Does nothing */
	public void windowActivated(WindowEvent arg0) { }
	
	@Override
	/** Does nothing  */
	public void windowClosed(WindowEvent arg0) { }
	
	@Override
	/** Does nothing  */
	public void windowDeactivated(WindowEvent arg0) { }
	
	@Override
	/** Does nothing  */
	public void windowDeiconified(WindowEvent arg0) { }
	
	@Override
	/** Does nothing  */
	public void windowIconified(WindowEvent arg0) { }
	
	@Override
	/** Does nothing  */
	public void windowOpened(WindowEvent arg0) { }
}
