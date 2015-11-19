//Kian Faroughi
//Csc165 - Assignment 1
//Doctor Gordon
//CSUS Fall 2015
//MySphere object expands on Sphere object for my game project to allow for points

package gameWorldObjects;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import sage.scene.shape.Sphere;
import sage.scene.shape.Teapot;
import sage.texture.Texture;

public class MySphere extends Sphere {
	
	private Boolean pointsUsed = false;
	private int points;
	private Color myColor;
	private ArrayList<Color> colors = new ArrayList<Color>();
	
	public MySphere()
	{	
		super();
	
		Random r = new Random();
		
		colors.add(Color.pink);
		colors.add(Color.green);
		colors.add(Color.blue);
		colors.add(Color.white);
		colors.add(Color.orange);
		colors.add(Color.CYAN);
		colors.add(Color.yellow);
		
		this.setColor(colors.get(r.nextInt(7)));
		
		
		
		points = randomInt(1,5);
		
	}
	
	public Boolean getPointsUsed()
	{
		return pointsUsed;
		
	}
	
	public void usePoints()
	{
		pointsUsed = true;
		changeToUsed();
	}
	
	public void changeToUsed()
	{
		this.setColor(Color.red);
	}
	
	public int getPoints()
	{
		return points;
	}
	
	private static int randomInt(int min, int max) {
	    Random random = new Random();
	    int randomNum = random.nextInt((max - min) + 1) + min;
	    return randomNum;
	}

}
