//Kian Faroughi
//Csc165 - Assignment 1
//Doctor Gordon
//CSUS Fall 2015
//Event class used by controller to look up and down with right analog stick

package keyboardMovement;


import m2.Camera3P;
import m2.MyGame;
import client.Client;
import graphicslib3D.Matrix3D;
import net.java.games.input.Event;
import sage.input.action.AbstractInputAction;
import sage.scene.SceneNode;

public class OrbitDownAction extends AbstractInputAction {
	 
	private Camera3P cc;
	private SceneNode cameraBox;
	private Client client;
	private int scaleSpeed;
	private float minElevation;
	private float maxElevation;
	private SceneNode avatar;
	private float f;
	private MyGame game;
	
	
	int x = 0;
	
	public OrbitDownAction(MyGame game)
	{
			this.game = game;
			cc = game.getCamera3P();
			this.scaleSpeed = game.getScaleOrbitKeyboard();
			this.minElevation = (float)game.getMinElevation();
			this.maxElevation = (float)game.getMaxElevation();
			this.avatar = game.returnPlayer1();
			this.client = game.getClient();
			this.cameraBox = game.returnPlayer1CameraBox();
			
	}
	
	public void performAction(float time, Event evt)
	 {
		
		scaleSpeed = game.getScaleOrbitKeyboard();
		maxElevation = (float)game.getMaxElevation();
		
		 float normalizeTime = time/(scaleSpeed);
		
		f = cc.getElevation();
		if(f<maxElevation)
		{
			cc.setElevation(f+normalizeTime);
			
			 Matrix3D rot = cameraBox.getLocalRotation();
			 rot.rotateX(normalizeTime*1.2);
			 cameraBox.setLocalRotation(rot);
			 
			 cc.update(time);
			client.sendMoveMessage(avatar.getLocalTranslation().getCol(3), cameraBox.getLocalTranslation().getCol(3), cameraBox.getLocalRotation().getCol(1), cameraBox.getLocalRotation().getCol(2));
			
			 
		}
		
		
		
	} 
	
} 