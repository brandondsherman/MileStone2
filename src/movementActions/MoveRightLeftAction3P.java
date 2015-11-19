//Kian Faroughi
//Csc165 - Assignment 1
//Doctor Gordon
//CSUS Fall 2015
//Event class used by controller to move along x axis with left analog

package movementActions;

import m2.Camera3P;
import m2.MyGame;
import client.Client;
import graphicslib3D.Matrix3D;
import graphicslib3D.Vector3D;
import net.java.games.input.Event;
import sage.camera.ICamera;
import sage.input.action.AbstractInputAction;
import sage.scene.SceneNode;

public class MoveRightLeftAction3P extends AbstractInputAction {
	
	private ICamera camera;
	private Camera3P cc1;
	private SceneNode cameraBox;
	private int scaleSpeed;
	private int width;
	private int offset;
	private SceneNode avatar;
	private int max;
	int test = 0;
	private Client client;
	private MyGame game;
	
	public MoveRightLeftAction3P(MyGame game)
	{
			this.game = game;
			camera = game.getCamera();
			this.cameraBox = game.returnPlayer1CameraBox();
			this.cc1 = game.getCamera3P();
			this.scaleSpeed = game.getScaleLeftRightGamepad();
			this.width = game.returnWidth();
			this.offset = game.returnOffest();
			this.avatar = game.returnPlayer1();
			max = width - (offset/2);
			this.client = game.getClient();
	}
	
	public void performAction(float time, Event event)
	{
		

		
		if(event.getValue()>0.35 || event.getValue()<-0.35)
		{
			scaleSpeed = game.getScaleLeftRightGamepad();
			
			if(event.getValue()>0)
			{
				if(avatar.getLocalTranslation().getCol(3).getX()>-max)
				{
			
					float normalizeTime = (-1*(time/(scaleSpeed))*event.getValue());
				
					Matrix3D rot = avatar.getLocalRotation();
					Vector3D dir = new Vector3D(1,0,0);
					dir = dir.mult(rot);
					dir.scale(normalizeTime);
					avatar.translate((float)dir.getX(),0,(float)dir.getZ());
					
					cc1.update(time);
					client.sendMoveMessage(avatar.getLocalTranslation().getCol(3), cameraBox.getLocalTranslation().getCol(3), cameraBox.getLocalRotation().getCol(1), cameraBox.getLocalRotation().getCol(2));
				
				}
			}
			else 
			{
				if(avatar.getLocalTranslation().getCol(3).getX()<max)
				{
			
					float normalizeTime = (-1*(time/(scaleSpeed))*event.getValue());
				
					Matrix3D rot = avatar.getLocalRotation();
					Vector3D dir = new Vector3D(1,0,0);
					dir = dir.mult(rot);
					dir.scale(normalizeTime);
					avatar.translate((float)dir.getX(),0,(float)dir.getZ());
					
					cc1.update(time);
					client.sendMoveMessage(avatar.getLocalTranslation().getCol(3), cameraBox.getLocalTranslation().getCol(3), cameraBox.getLocalRotation().getCol(1), cameraBox.getLocalRotation().getCol(2));
				
				}
			}
	
		}
		}
	

}
