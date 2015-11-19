//Kian Faroughi
//Csc165 - Assignment 1
//Doctor Gordon
//CSUS Fall 2015
//Event class used by controller to look up and down with right analog stick

package movementActions;

import m2.Camera3P;
import m2.MyGame;
import client.Client;
import net.java.games.input.Event;
import sage.camera.ICamera;
import sage.input.action.AbstractInputAction;
import sage.scene.SceneNode;

public class MoveRYAxisAction3P extends AbstractInputAction {
	
	private Camera3P cc;
	private SceneNode cameraBox;
	private Client client;
	private int scaleSpeed;
	private float minElevation;
	private float maxElevation;
	private SceneNode avatar;
	private MyGame game;
	
	
	private float elevation, distanceFromTarget;
	
	public MoveRYAxisAction3P(MyGame game)
	{	
			this.game = game;
			cc = game.getCamera3P();
			this.scaleSpeed = game.getScaleOrbitGamepad();
			this.minElevation = (float)game.getMinElevation();
			this.maxElevation = (float)game.getMaxElevation();
			this.avatar = game.returnPlayer1();
			this.client = game.getClient();
			this.cameraBox = game.returnPlayer1CameraBox();
		
	}
	
	public void performAction(float time, Event event)
	{
			
			
			
		 if(event.getValue()>0.35 || event.getValue()<-0.35)
			{
			 	scaleSpeed = game.getScaleOrbitGamepad();
			 	minElevation = (float)game.getMinElevation();
			 	maxElevation = (float)game.getMaxElevation();
			 	
			 	float normalizeTime = ((time/(scaleSpeed))*event.getValue());
	
			 	
				 elevation = cc.getElevation();
				 distanceFromTarget = cc.getDistanceFromTarget();
				 float y = distanceFromTarget/elevation;
			
				 if(event.getValue()>0)
				{
				 if(elevation<maxElevation)
				 {
				 cc.setElevation(elevation+normalizeTime);
				 
				cc.update(time);
				client.sendMoveMessage(avatar.getLocalTranslation().getCol(3), cameraBox.getLocalTranslation().getCol(3), cameraBox.getLocalRotation().getCol(1), cameraBox.getLocalRotation().getCol(2));
				

				 }
				}
				else
				{
				if(elevation>minElevation)
				{
				cc.setElevation(elevation+normalizeTime);
				
				cc.update(time);
				client.sendMoveMessage(avatar.getLocalTranslation().getCol(3), cameraBox.getLocalTranslation().getCol(3), cameraBox.getLocalRotation().getCol(1), cameraBox.getLocalRotation().getCol(2));
			

				
				}
				}
			 
			}
		
		
	}
	
}
		
			


