package gameWorldObjects;

import java.awt.Color;
import java.util.UUID;

import m2.MyGame;
import graphicslib3D.Matrix3D;
import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;


public class GhostAvatar {
	
	private UUID ghostID;
	private Point3D ghostLoc;
	private Point3D ghostCamLoc;
	private Vector3D ghostCamRot1;
	private Vector3D ghostCamRot2;
	private MyCube ghost;
	private MyCube ghostCameraBox;
	
	public GhostAvatar(UUID ghostID, double ghostYPos, String[] pos, String[] cameraPos, String[] cameraRot1, String[] cameraRot2, String color, float scaleX, float scaleY, float scaleZ, String cameraBoxColor, float ghostScaleX, float ghostScaleY, float ghostScaleZ)
	{
		this.ghostID = ghostID;
		
		 ghostLoc = new Point3D(Float.valueOf(pos[0]), Float.valueOf(pos[1]),Float.valueOf(pos[2]));
		
		 ghost = new MyCube(color, ghostID);
		 ghost.translate((float)ghostLoc.getX(),(float)ghostYPos,(float)ghostLoc.getZ());
		 ghost.scale(scaleX, scaleY, scaleZ);
		 
		 ghostCamLoc = new Point3D(Float.valueOf(cameraPos[0]), Float.valueOf(cameraPos[1]),Float.valueOf(cameraPos[2]));
			
		 ghostCameraBox = new MyCube(cameraBoxColor, ghostID);
		 ghostCameraBox.translate((float)ghostCamLoc.getX(),(float)ghostCamLoc.getY(),(float)ghostCamLoc.getZ());
		 ghostCameraBox.scale(ghostScaleX, ghostScaleY, ghostScaleZ);
		 
		 ghostCamRot1 = new Vector3D(Float.valueOf(cameraRot1[0]), Float.valueOf(cameraRot1[1]),Float.valueOf(cameraRot1[2]));
		 ghostCamRot2 = new Vector3D(Float.valueOf(cameraRot2[0]), Float.valueOf(cameraRot2[1]),Float.valueOf(cameraRot2[2]));
		
		 Matrix3D cameraRotation = new Matrix3D();
		 cameraRotation.setCol(1, ghostCamRot1);
		 cameraRotation.setCol(2, ghostCamRot2);
		 ghostCameraBox.setLocalRotation(cameraRotation);

		 
		
	}
	
	public MyCube returnGhost()
	{
		return ghost;
	}
	
	public MyCube returnGhostCamera()
	{
		return ghostCameraBox;
	}
	
	public UUID returnID()
	{
		return ghostID;
		
	}
	
	public Point3D returnGhostLocation()
	{
		return ghostLoc;
	}
	
	public Point3D returnGhostCameraLocation()
	{
		return ghostCamLoc;
	}
	
	public Vector3D returnGhostCameraRotation1()
	{
		return ghostCamRot1;
	}
	
	public Vector3D returnGhostCameraRotation2()
	{
		return ghostCamRot2;
	}
	
	public void moveGhost(double ghostYpos, String[] pos, String[] cameraPos, String[] cameraRot1, String[] cameraRot2)
	{
		 ghostLoc = new Point3D(Float.valueOf(pos[0]), Float.valueOf(pos[1]),Float.valueOf(pos[2]));
		 ghostCamLoc = new Point3D(Float.valueOf(cameraPos[0]), Float.valueOf(cameraPos[1]),Float.valueOf(cameraPos[2]));
		 ghostCamRot1 = new Vector3D(Float.valueOf(cameraRot1[0]), Float.valueOf(cameraRot1[1]),Float.valueOf(cameraRot1[2]));
		 ghostCamRot2 = new Vector3D(Float.valueOf(cameraRot2[0]), Float.valueOf(cameraRot2[1]),Float.valueOf(cameraRot2[2]));
		
		 
		Matrix3D translation = new Matrix3D();
		translation.translate(Float.valueOf(pos[0]), (float)ghostYpos, Float.valueOf(pos[2]));
		ghost.setLocalTranslation(translation);
		
		Matrix3D cameraBoxTranslation = new Matrix3D();
		cameraBoxTranslation.translate(Float.valueOf(cameraPos[0]), Float.valueOf(cameraPos[1]), Float.valueOf(cameraPos[2]));
		ghostCameraBox.setLocalTranslation(cameraBoxTranslation);
		
		
		 Matrix3D cameraRotation = new Matrix3D();
		 cameraRotation.setCol(1, ghostCamRot1);
		 cameraRotation.setCol(2, ghostCamRot2);
		 ghostCameraBox.setLocalRotation(cameraRotation);
		 
	}

}
