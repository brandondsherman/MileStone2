package gameWorldObjects.models;

import java.io.File;

import graphicslib3D.Matrix3D;
import sage.model.loader.OBJLoader;
import sage.scene.TriMesh;

public class Arrow {
	
	private TriMesh obj;
	
	public Arrow(){
		OBJLoader loader = new OBJLoader();
		loader.setShowWarnings(false);
		obj = loader.loadModel("src"+File.separator+"gameWorldObjects"+File.separator+"models"+File.separator+"untitled.obj");
		
		Matrix3D translate = obj.getLocalTranslation();
		translate.translate(15,15,-5);
		obj.setLocalTranslation(translate);
		obj.scale(5,5,5);
		obj.updateLocalBound();
		obj.updateGeometricState(0, true);
	}
	
	public TriMesh getObj(){
		return obj;
	}
	
}
