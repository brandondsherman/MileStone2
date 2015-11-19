//Kian Faroughi
//Csc165 - Assignment 1
//Doctor Gordon
//CSUS Fall 2015
//Event class used by keyboard key to move backward


package keyboardMovement;

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

public class MoveBackwardAction3P extends AbstractInputAction {
	
	private ICamera camera;
	private Camera3P cc1;
	private SceneNode cameraBox;
	private int scaleSpeed;
	private int width;
	private int offset;
	private int max;
	private SceneNode avatar;
	private Client client;
	private MyGame game;

	
	public MoveBackwardAction3P(MyGame game)
	{
			this.game = game;
			camera = game.getCamera();
			avatar = game.returnPlayer1();
			this.width = game.returnWidth();
			this.scaleSpeed = game.getScaleForwardBackwardKeyboard();
			this.offset = game.returnOffest();
			max = width - (offset/2);
			this.client = game.getClient();
			this.cc1 = game.getCamera3P();
			this.cameraBox = game.returnPlayer1CameraBox();
	}
	
	public void performAction(float time, Event event)
	{	
		scaleSpeed = game.getScaleForwardBackwardKeyboard();
		
		if(avatar.getLocalTranslation().getCol(3).getZ()>(-max))
		{
		Matrix3D rot = avatar.getLocalRotation();
		Vector3D dir = new Vector3D(0,0,1);
		dir = dir.mult(rot);
		dir.scale((double)time/(scaleSpeed));
		avatar.translate(-(float)dir.getX(),0,-(float)dir.getZ());
		cc1.update(time);
		client.sendMoveMessage(avatar.getLocalTranslation().getCol(3), cameraBox.getLocalTranslation().getCol(3), cameraBox.getLocalRotation().getCol(1), cameraBox.getLocalRotation().getCol(2));
			
		
		}
		
	}

}
