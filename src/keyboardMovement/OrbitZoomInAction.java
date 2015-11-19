package keyboardMovement;


import m2.Camera3P;
import m2.MyGame;
import client.Client;
import net.java.games.input.Event;
import sage.input.action.AbstractInputAction;
import sage.scene.SceneNode;

public class OrbitZoomInAction  extends AbstractInputAction {
	 
	private Camera3P cc;
	private SceneNode cameraBox;
	private int scaleSpeed;
	private float minZoom;
	private float maxZoom;
	private SceneNode avatar;
	private float f;
	private MyGame game;
	private Client client;

	
	public OrbitZoomInAction(MyGame game)
	{
			this.game = game;
			cc = game.getCamera3P();
			this.cameraBox = game.returnPlayer1CameraBox();
			this.scaleSpeed = game.getScaleZoom();
			this.minZoom = (float)game.getMinZoom();
			this.maxZoom = (float)game.getMaxZoom();
			this.avatar = game.returnPlayer1();
			this.client = game.getClient();
			
	}
	
	public void performAction(float time, Event evt)
	 {
		
		scaleSpeed = game.getScaleZoom();
		minZoom = (float)game.getMinZoom();
		
		float normalizeTime = time/(scaleSpeed);
	
	
		 f = cc.getDistanceFromTarget();
		 if(f>minZoom)
		 {
		 cc.setDistanceFromTarget(f-normalizeTime);
		 
		 cc.update(time);
		client.sendMoveMessage(avatar.getLocalTranslation().getCol(3), cameraBox.getLocalTranslation().getCol(3), cameraBox.getLocalRotation().getCol(1), cameraBox.getLocalRotation().getCol(2));
		 
	 }
	 
	 
	
	 
} 
	
} 