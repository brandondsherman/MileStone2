//Kian Faroughi
//Csc165 - Assignment 1
//Doctor Gordon
//CSUS Fall 2015
//Extention of TriMesh, Code from Dr. Gordon


package gameWorldObjects;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import sage.scene.TriMesh;
import sage.scene.shape.Teapot;

public class MyPyramid extends TriMesh
{
private static float[] vrts = new float[] {0,1,0,-1,-1,1,1,-1,1,1,-1,-1,-1,-1,-1};
private static float[] cl = new float[] {1,0,0,1,0,1,0,1,0,0,1,1,1,1,0,1,1,0,1,1};
private static int[] triangles = new int[] {0,1,2,0,2,3,0,3,4,0,4,1,1,4,2,4,3,2};
 public MyPyramid()
 { int i;
 FloatBuffer vertBuf =
 com.jogamp.common.nio.Buffers.newDirectFloatBuffer(vrts);
 FloatBuffer colorBuf =
 com.jogamp.common.nio.Buffers.newDirectFloatBuffer(cl);
 IntBuffer triangleBuf =
 com.jogamp.common.nio.Buffers.newDirectIntBuffer(triangles);
 this.setVertexBuffer(vertBuf);
 this.setColorBuffer(colorBuf);
 this.setIndexBuffer(triangleBuf); } }