//Kian Faroughi
//Csc165 - Assignment 1
//Doctor Gordon
//CSUS Fall 2015
//Event class used by keyboard key to move right

package keyboardMovement;


import m2.Camera3P;
import m2.MyGame;
import client.Client;
import graphicslib3D.Matrix3D;
import graphicslib3D.Vector3D;
import net.java.games.input.Event;
import sage.camera.ICamera;
import sage.input.action.AbstractInputAction;
import sage.scene.SceneNode;

public class MoveRightAction3P extends AbstractInputAction {
	
	private ICamera camera;
	private Camera3P cc1;
	private SceneNode cameraBox;
	private int scaleSpeed;
	private int width;
	private int offset;
	private int max;
	private SceneNode avatar;
	private Client client;
	MyGame game;
	
	public MoveRightAction3P(MyGame game)
	{
			this.game = game;
			camera = game.getCamera();
			this.cc1 = game.getCamera3P();
			this.cameraBox = game.returnPlayer1CameraBox();
			this.scaleSpeed = game.getScaleLeftRightKeyboard();
			this.width = game.returnWidth();
			this.offset = game.returnOffest();
			this.max = width-(offset/2);
			this.avatar = game.returnPlayer1();
			this.client = game.getClient();
	}
	
	public void performAction(float time, Event event)
	{
		scaleSpeed = game.getScaleLeftRightKeyboard();
		
		if(avatar.getLocalTranslation().getCol(3).getX()>(-max))
		{
		
		Matrix3D rot = avatar.getLocalRotation();
		 Vector3D dir = new Vector3D(1,0,0);
		 dir = dir.mult(rot);
		 dir.scale((double)time/(scaleSpeed));
		 avatar.translate(-(float)dir.getX(),0,-(float)dir.getZ());
		 cc1.update(time);
		client.sendMoveMessage(avatar.getLocalTranslation().getCol(3), cameraBox.getLocalTranslation().getCol(3), cameraBox.getLocalRotation().getCol(1), cameraBox.getLocalRotation().getCol(2));
		
		}
	}

}