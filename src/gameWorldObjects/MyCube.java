//Kian Faroughi
//Csc165 - Assignment 2
//Doctor Gordon
//CSUS Fall 2015
//Extention of TriMesh, Code from Dr. Gordon


package gameWorldObjects;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.UUID;

import sage.scene.TriMesh;
import sage.scene.shape.Teapot;
import sage.texture.Texture;
import sage.texture.TextureManager;

public class MyCube extends TriMesh
{
	
private UUID name;
private static float transparency =0.0f;	

private static float[] vrts = new float[] {1,-1,1,-1,-1,1,-1,-1,-1,1,-1,-1,1,1,1,-1,1,1,-1,1,-1,1,1,-1};
private static float[] textureCoordinates = new float[] {0,0,0,0,0,0,0,0,1,0,0,0,0,1,1,1};
private static float[] cl = new float[] {.8f,.8f,.8f,1,.8f,.8f,.8f,1,.8f,.8f,.8f,1,.8f,.8f,.8f,1,.8f,.8f,.8f,1,.8f,.8f,.8f,1,.8f,.8f,.8f,1,.8f,.8f,.8f,1};

private static float[] purple = new float[] {0.8f,0.7f, 1.0f, transparency, 0.8f,0.7f, 1.0f, transparency, 0.8f,0.7f, 1.0f, transparency, 0.8f,0.7f, 1.0f, transparency, 0.8f,0.7f, 1.0f, transparency, 0.8f,0.7f, 1.0f, transparency, 0.8f,0.7f, 1.0f, transparency, 0.8f,0.7f, 1.0f, transparency};
private static float[] blue = new float[] {0.6f,0.6f, 1.0f, transparency, 0.6f,0.6f, 1.0f, transparency, 0.6f,0.6f, 1.0f, transparency, 0.6f,0.6f, 1.0f, transparency, 0.6f,0.6f, 1.0f, transparency, 0.6f,0.6f, 1.0f, transparency, 0.6f,0.6f, 1.0f, transparency, 0.6f,0.6f, 1.0f, transparency};
private static float[] red = new float[] {1.0f, 0.6f, 0.6f, transparency, 1.0f, 0.6f, 0.6f, transparency,1.0f, 0.6f, 0.6f, transparency,1.0f, 0.6f, 0.6f, transparency,1.0f, 0.6f, 0.6f, transparency,1.0f, 0.6f, 0.6f, transparency,1.0f, 0.6f, 0.6f, transparency,1.0f, 0.6f, 0.6f, transparency};
private static float[] green = new float[] {0.6f, 1.0f, 0.6f, transparency, 0.6f, 1.0f, 0.6f, transparency, 0.6f, 1.0f, 0.6f, transparency, 0.6f, 1.0f, 0.6f, transparency, 0.6f, 1.0f, 0.6f, transparency, 0.6f, 1.0f, 0.6f, transparency, 0.6f, 1.0f, 0.6f, transparency, 0.6f, 1.0f, 0.6f, transparency};
private static float[] camera = new float[] {1.0f,1.0f,1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f,1.0f,0.0f,1.0f,1.0f,1.0f,0.0f,1.0f, 1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,0.0f,1.0f, 1.0f,1.0f,0.0f,1.0f};
private static int[] triangles = new int[] {0,1,2,0,2,3,0,3,4,3,4,7,0,4,1,1,4,5,1,2,5,2,5,6,2,3,6,3,6,7,4,5,6,4,6,7};


public MyCube(String s)
 {

 FloatBuffer vertBuf =
 com.jogamp.common.nio.Buffers.newDirectFloatBuffer(vrts);
 
 FloatBuffer colorBuf;
 this.name = null;
 if(s=="blue")
 {
	 colorBuf =
	 com.jogamp.common.nio.Buffers.newDirectFloatBuffer(blue);
	 this.setColorBuffer(colorBuf);
 }
 else if(s=="green")
 {
	colorBuf =
	com.jogamp.common.nio.Buffers.newDirectFloatBuffer(green);
	this.setColorBuffer(colorBuf);
 }
 else if(s=="red")
 {
	  colorBuf =
	 com.jogamp.common.nio.Buffers.newDirectFloatBuffer(red);
	  this.setColorBuffer(colorBuf);
 }
 else if(s=="camera")
 {
	  colorBuf =
	com.jogamp.common.nio.Buffers.newDirectFloatBuffer(camera);
	  this.setColorBuffer(colorBuf);
 }
 else if(s=="purple")
 {	
	 colorBuf = 
	 com.jogamp.common.nio.Buffers.newDirectFloatBuffer(purple);
	  this.setColorBuffer(colorBuf);
			
 }
 else
 {
 colorBuf =
 com.jogamp.common.nio.Buffers.newDirectFloatBuffer(cl);
 this.setColorBuffer(colorBuf);
 }
 
 IntBuffer triangleBuf =
 com.jogamp.common.nio.Buffers.newDirectIntBuffer(triangles);
 this.setVertexBuffer(vertBuf);
 this.setIndexBuffer(triangleBuf); } 
 
public MyCube(String s, UUID name)
{
	int i;
	 FloatBuffer vertBuf =
	 com.jogamp.common.nio.Buffers.newDirectFloatBuffer(vrts);
	 
	 FloatBuffer colorBuf;
	 this.name = name;
	 if(s=="blue")
	 {
		 colorBuf =
		 com.jogamp.common.nio.Buffers.newDirectFloatBuffer(blue);
		 this.setColorBuffer(colorBuf);
	 }
	 else if(s=="green")
	 {
		colorBuf =
		com.jogamp.common.nio.Buffers.newDirectFloatBuffer(green);
		this.setColorBuffer(colorBuf);
	 }
	 else if(s=="red")
	 {
		  colorBuf =
		 com.jogamp.common.nio.Buffers.newDirectFloatBuffer(red);
		  this.setColorBuffer(colorBuf);
	 }
	 else if(s=="camera")
	 {
		  colorBuf =
		com.jogamp.common.nio.Buffers.newDirectFloatBuffer(camera);
		  this.setColorBuffer(colorBuf);
	 }
	 else if(s=="purple")
	 {	
		
		 colorBuf = 
		 com.jogamp.common.nio.Buffers.newDirectFloatBuffer(purple);
		  this.setColorBuffer(colorBuf);
	 }
	 else
	 {
	 colorBuf =
	 com.jogamp.common.nio.Buffers.newDirectFloatBuffer(cl);
	 this.setColorBuffer(colorBuf);
	 }
	 
	 IntBuffer triangleBuf =
	 com.jogamp.common.nio.Buffers.newDirectIntBuffer(triangles);
	 this.setVertexBuffer(vertBuf);
	 this.setIndexBuffer(triangleBuf); 
}


public UUID returnUUID()
{
	return name;
}


public void setColorBuffer(String s)
{ 
	FloatBuffer colorBuf;
	
	
	if(s=="blue")
	 {
		 colorBuf =
		 com.jogamp.common.nio.Buffers.newDirectFloatBuffer(blue);
		 this.setColorBuffer(colorBuf);
	 }
	 else if(s=="green")
	 {
		colorBuf =
		com.jogamp.common.nio.Buffers.newDirectFloatBuffer(green);
		this.setColorBuffer(colorBuf);
	 }
	 else if(s=="red")
	 {
		  colorBuf =
		 com.jogamp.common.nio.Buffers.newDirectFloatBuffer(red);
		  this.setColorBuffer(colorBuf);
	 }
	 else if(s=="camera")
	 {
		  colorBuf =
		com.jogamp.common.nio.Buffers.newDirectFloatBuffer(camera);
		  this.setColorBuffer(colorBuf);
	 }
	 else if(s=="purple")
	 {	
		
		 colorBuf = 
		 com.jogamp.common.nio.Buffers.newDirectFloatBuffer(purple);
		  this.setColorBuffer(colorBuf);
	 }
	 else
	 {
	 colorBuf =
	 com.jogamp.common.nio.Buffers.newDirectFloatBuffer(cl);
	 this.setColorBuffer(colorBuf);
	 }
}

}


