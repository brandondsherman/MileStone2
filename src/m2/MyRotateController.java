package m2;

import graphicslib3D.Matrix3D;
import sage.scene.Controller;
import sage.scene.SceneNode;

public class MyRotateController extends Controller {
	
	private double rotationRate = .05;


	public void update(double time)
	{

		double rotationAmount = rotationRate * time;
		
		Matrix3D newRotation = new Matrix3D();
		
		newRotation.rotate(0, rotationAmount,0);
		
		for(SceneNode node: controlledNodes)
		{
			Matrix3D currentRotation = node.getLocalRotation();
			currentRotation.concatenate(newRotation);
			node.setLocalRotation(currentRotation);
		}
		
	}

}
