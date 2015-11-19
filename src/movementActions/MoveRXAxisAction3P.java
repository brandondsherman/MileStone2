//Kian Faroughi
//Csc165 - Assignment 1
//Doctor Gordon
//CSUS Fall 2015
//Event class used by controller to look right and left with right analog stick

package movementActions;


import m2.Camera3P;
import m2.MyGame;
import client.Client;
import graphicslib3D.Matrix3D;
import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;
import net.java.games.input.Event;
import sage.camera.ICamera;
import sage.input.action.AbstractInputAction;
import sage.scene.SceneNode;

public class MoveRXAxisAction3P extends AbstractInputAction {
	
	private Camera3P cc;
	private SceneNode cameraBox;
	private Client client;
	private int scaleSpeed;
	private SceneNode avatar;
	private MyGame game;
	
	private float f;
	
	public MoveRXAxisAction3P(MyGame game)
	{
			this.game = game;
			cc = game.getCamera3P();
			this.scaleSpeed = game.getScaleOrbitGamepad();
			this.avatar = game.returnPlayer1();
			this.client = game.getClient();
		
	}
	
	public void performAction(float time, Event event)
	{
		

		 if(event.getValue()>0.35 || event.getValue()<-0.35)
			{
			 
			 scaleSpeed = game.getScaleOrbitGamepad();
				
			 float normalizeTime = ((time/(scaleSpeed))*event.getValue());
	 
			 f = cc.getAzimuth();
			 cc.setAzimuth(f+normalizeTime);
			 f = cc.getAzimuth();
			 cc.setAzimuth(f%360);
		
			 Matrix3D rot = cameraBox.getLocalRotation();
			rot.rotateY(normalizeTime);
			cameraBox.setLocalRotation(rot);
			
			cc.update(time);
			client.sendMoveMessage(avatar.getLocalTranslation().getCol(3), cameraBox.getLocalTranslation().getCol(3), cameraBox.getLocalRotation().getCol(1), cameraBox.getLocalRotation().getCol(2));
		
		
	}
	}
	
}
		


