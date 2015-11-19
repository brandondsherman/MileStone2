package gameWorldObjects;

import java.io.File;

import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;
import sage.scene.state.RenderState;
import sage.scene.state.TextureState;
import sage.terrain.AbstractHeightMap;
import sage.terrain.HillHeightMap;
import sage.terrain.TerrainBlock;
import sage.texture.Texture;
import sage.texture.TextureManager;
import scriptManagers.TerrainScriptManager;

public class TerrainManager {
	//HillHeightMap Vars
	private int size;
	private int iterations;
	private float minRadius;
	private float maxRadius;
	private byte flattening;
	private long seed;

	
	//TerrainBlock vars
	private float xScale;
	private float yScale;
	private float zScale;
	
	private HillHeightMap hhm;
	private TerrainBlock terrain;

	
	public float getXScale() {
		return xScale;
	}

	public void setXScale(float xScale) {
		this.xScale =(float) xScale;
	}

	public float getYScale() {
		return yScale;
	}

	public void setYScale(float yScale) {
		this.yScale = (float)yScale;
	}

	public float getZScale() {
		return zScale;
	}

	public void setZScale(float zScale) {
		this.zScale = (float)zScale;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getIterations() {
		return iterations;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	public double getMinRadius() {
		return minRadius;
	}

	public void setMinRadius(double minRadius) {
		this.minRadius = (float)minRadius;
	}

	public double getMaxRadius() {
		return maxRadius;
	}

	public void setMaxRadius(double maxRadius) {
		this.maxRadius = (float) maxRadius;
	}

	public byte getFlattening() {
		return flattening;
	}

	public void setFlattening(byte flattening) {
		this.flattening = flattening;
	}

	public long getSeed() {
		return seed;
	}

	public void setSeed(long seed) {
		this.seed = seed;
	}
	
	public HillHeightMap getHHM(){
		return hhm;
	}
	
	public TerrainBlock getTerrain(){
		return terrain;
	}
	
	public TerrainManager(){
		TerrainScriptManager sm = TerrainScriptManager.getInstance("src"+File.separator+"scripts"+File.separator+"terrainScriptFiles"+File.separator,this);
		sm.executeScript("init.js");
		hhm = new HillHeightMap(size,iterations,minRadius,maxRadius,(byte)flattening,seed);
		terrain = createTerBlock(hhm);
		
	}
	
	protected TerrainBlock createTerBlock(AbstractHeightMap heightMap){
		Vector3D terrainScale = new Vector3D(xScale,yScale,zScale);
		int terrainSize = heightMap.getSize();
		float cornerHeight = heightMap.getTrueHeightAtPoint(0,0)*yScale;
		Point3D terrainOrigin = new Point3D(0,-cornerHeight,0);
		String name = "Terrain:"+heightMap.getClass().getSimpleName();
		TerrainBlock tb = new TerrainBlock(name,terrainSize,terrainScale,heightMap.getHeightData(),terrainOrigin);
		return tb;
	}

	

}
