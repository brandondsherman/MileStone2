//Kian Faroughi
//Csc165 - Assignment 2
//Doctor Gordon
//CSUS Fall 2015

package m2;

import sage.app.BaseGame;
import sage.camera.ICamera;
import sage.camera.JOGLCamera;
import sage.display.*;
import sage.event.EventManager;
import sage.event.IEventListener;
import sage.event.IEventManager;
import sage.event.IGameEvent;
import sage.input.IInputManager;
import sage.input.InputManager;
import sage.networking.IGameConnection.ProtocolType;
import sage.renderer.IRenderer;
import sage.scene.SceneNode;
import sage.scene.SkyBox;
import sage.scene.SkyBox.Face;
import sage.scene.TriMesh;
import sage.scene.shape.Cube;
import sage.scene.shape.Line;
import sage.scene.shape.Pyramid;
import sage.scene.shape.Rectangle;
import sage.scene.shape.Sphere;
import sage.scene.shape.Teapot;
import sage.scene.state.RenderState;
import sage.scene.state.TextureState;
import sage.terrain.AbstractHeightMap;
import sage.terrain.HillHeightMap;
import sage.terrain.TerrainBlock;
import sage.texture.Texture;
import sage.texture.TextureManager;
import scriptManagers.ScriptManager;
import sage.scene.Group;
import sage.scene.HUDString;
import graphicslib3D.Matrix3D;
import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;
import net.java.games.input.Controller;
import net.java.games.input.Keyboard;
import physics.PhysicsBase;
import physics.StartPhysics;

import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.UUID;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JOptionPane;

import m2.Camera3P;
import gameWorldObjects.GhostAvatar;
import gameWorldObjects.MyCube;
import gameWorldObjects.MySphere;
import gameWorldObjects.TerrainManager;
import gameWorldObjects.models.Arrow;
import keyboardMovement.*;
import client.Client;
import movementActions.*;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;


public class MyGame extends BaseGame implements MouseListener, MouseMotionListener {

private float time = 0;
private HUDString timeString;

private IDisplaySystem display;
private IRenderer renderer;

IEventManager eventManager;
IInputManager im;
private ArrayList<Controller> controllers;

private int frameWidth = 1024;
private int frameHeight = 768;
private boolean isFullScreenMode = false;

private Client client;
private boolean isConnected = false;
private String serverAddress;
private int serverPort;
private ProtocolType serverProtocol;
private ArrayList<GhostAvatar> ghostAvatars;

private ScriptEngine jsEngine;
private FileReader fileReader;
private String scriptFileName = "scriptFile.js";
private String scriptFileName2 = "scriptFileDynamic.js";
private File dynamicScript = new File("scriptFileDynamic.js");
private long fileLastModifiedTime = dynamicScript.lastModified();
private boolean dynamicScriptOn;

private ICamera camera1;
private Camera3P cc1;
private SkyBox skybox1;
private MyCube player1;
private MyCube player1CameraBox;
private MyCube groundPlane, transparentGroundPlane;
private ArrayList<Line> grid;

private double ghostSpawnY = 0.4f;
private double ghostScaleX = 4f;
private double ghostScaleY = 0.2f;
private double ghostScaleZ = 4f;
private String ghostColor = "green";

private double ghostCameraBoxScaleX = 0.5f;
private double ghostCameraBoxScaleY = 0.5f;
private double ghostCameraBoxScaleZ = 0.5f;
private String ghostCameraBoxColor = "camera";

private double camera1ViewAngle = 90;
private double camera1AspectRatio = (16/9);
private double camera1NearClip = 0.01;
private double camera1FarClip = 1000;
private double camera1Left = 0;
private double camera1Right = 1;
private double camera1Bottom = 0;
private double camera1Top = 1;

private double skyboxScaleX = 40;
private double skyboxScaleY = 40;
private double skyboxScaleZ = 40;
private String skyboxNorthPicture = "skybox/north.jpg";
private String skyboxEastPicture = "skybox/east.jpg";
private String skyboxSouthPicture = "skybox/south.jpg";
private String skyboxWestPicture = "skybox/west.jpg";
private String skyboxUpPicture = "skybox/up.jpg";
private String skyboxDownPicture = "skybox/down.jpg";

private double camera1DistanceFromTarget = 45.0f;
private double camera1Azimuth = 180;
private double camera1Elevation = 65.0f;

private int width = 300;
private int gridWidth = 380;
private int transparentGridWidth = 301;
private int offset = 10;
private double gridY = 1;
private double groundPlaneY = -1;
private double transparentPlaneY = 0;

private double player1SpawnX = 15.0f;
private double player1SpawnY = 0.6f;
private double player1SpawnZ = -5.0f;
private double player1ScaleX = 4f;
private double player1ScaleY = 0.2f;
private double player1ScaleZ = 4f;
private double player1CameraBoxScaleX = 0.5f;
private double player1CameraBoxScaleY = 0.5f;
private double player1CameraBoxScaleZ = 0.5f;

private double maxElevation = 80;
private double minElevation = 25;

private double minZoom = 30;
private double maxZoom = 60;

private int scaleForwardBackwardKeyboard = 16;
private int scaleLeftRightKeyboard = 24;
private int scaleOrbitKeyboard = 25;
private int scaleZoom = 50;

private int scaleForwardBackwardGamepad = 16;
private int scaleLeftRightGamepad = 24;
private int scaleOrbitGamepad = 25;

private String gridLineColor = "blue";
private String transparentGridColor = "blue";
private String gridColor = "grey";

private String player1Color = "red";
private String player1CameraBoxColor = "camera";
private boolean isPressed;
private Object mousePrevLocation;
private int mousePrevX;
private int mousePrevY;
private PhysicsBase physicsBase;
private MySphere sphere;

public static ArrayList<ScriptManager> scriptManagers = new ArrayList<ScriptManager>();



public MyGame(String serverAddr, int sPort)
{
	super();
	this.serverAddress = serverAddr;
	this.serverPort = sPort;
	this.serverProtocol = ProtocolType.TCP;
}

protected void initSystem()
{	

	this.setUpScript();
	
	this.runScript(scriptFileName);
	this.evalScript1();
	
	this.runScript(scriptFileName2);
	this.evalScript2();
	
	System.out.println("\nScripts Read Successfully.\n");
	
	display = createDisplaySystem();
	renderer = display.getRenderer();
	setDisplaySystem(display);
	
	InputManager inputManager = new InputManager();
	setInputManager(inputManager);
	im = getInputManager();

	ArrayList<SceneNode> gameWorld = new ArrayList<SceneNode>();
	setGameWorld(gameWorld);

}

private IDisplaySystem createDisplaySystem()
{
	display = new MyDisplaySystem(frameWidth, frameHeight, 32, 50, isFullScreenMode, "sage.renderer.jogl.JOGLRenderer", this, client);
	int count = 0;
	
	while(!display.isCreated())
	{
		try
		{
			Thread.sleep(10);
		}
		catch (InterruptedException e)
		{
			throw new RuntimeException("Display creation interrupted");
		}
		count++;
	
		if(count%80==0)
		{
	
		}if(count>2000)
		{
			throw new RuntimeException("Unable to create display");
		}
	}

	
	return display;
	
}

public void shutdown()
{
	super.shutdown();
	
	if(client!=null)
	{
		setIsConnected(false);
		client.sendByeMessage();
		try {
			wait(15);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	try
	{
		client.shutdown();
	}
	catch(IOException e)
	{
		e.printStackTrace();
	}
}

public void quitGame()
{	
	System.out.println("War (Working Title) Shutdown.");
	this.setGameOver(true);
	System.exit(0); 
}

public void byeMessage()
{
	client.sendByeMessage();
}

protected void render()
{
	updateSkyboxes();
	renderer.setCamera(camera1);
	super.render();
}

public void setUpScript()
{
	ScriptEngineManager factory = new ScriptEngineManager();
	//List<ScriptEngineFactory> list = factory.getEngineFactories();
	
	jsEngine = factory.getEngineByName("js");
	jsEngine.put("game",this);
}

private void runScript(String name)	
{		
	try {
		fileReader = new FileReader(name);
		jsEngine.eval(fileReader);
		fileReader.close();
		
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	catch(IOException e2)
	{
		System.out.println("IO problem with " + scriptFileName + e2);
	}
	catch(ScriptException e3)
	{
		System.out.println("ScriptException in " + scriptFileName + e3);
	}
	catch(NullPointerException e4)
	{
		System.out.println("Null pointer exception in " + scriptFileName + e4);
	}

	
}

public void evalScript1()
{
	/*
	dynamicScriptOn = (boolean)jsEngine.get("dynamicScriptOn");
	
	isFullScreenMode = (boolean)jsEngine.get("isFullScreenMode");
	
	camera1Left = (double)jsEngine.get("camera1Left");
	camera1Right = (double)jsEngine.get("camera1Right");
	camera1Bottom = (double)jsEngine.get("camera1Bottom");
	camera1Top = (double)jsEngine.get("camera1Top");
		
	skyboxScaleX = (double)jsEngine.get("skyboxScaleX");
	skyboxScaleY = (double)jsEngine.get("skyboxScaleY");
	skyboxScaleZ = (double)jsEngine.get("skyboxScaleZ");
	
	skyboxNorthPicture = (String)jsEngine.get("skyboxNorthPicture");
	skyboxEastPicture = (String)jsEngine.get("skyboxEastPicture");
	skyboxSouthPicture = (String)jsEngine.get("skyboxSouthPicture");
	skyboxWestPicture = (String)jsEngine.get("skyboxWestPicture");
	skyboxUpPicture = (String)jsEngine.get("skyboxUpPicture");
	skyboxDownPicture = (String)jsEngine.get("skyboxDownPicture");
	
	camera1DistanceFromTarget = (double)jsEngine.get("camera1DistanceFromTarget");
	camera1Azimuth = (double)jsEngine.get("camera1Azimuth");
	camera1Elevation = (double)jsEngine.get("camera1Elevation");
	
	width = (int)jsEngine.get("width");
	gridWidth = (int)jsEngine.get("gridWidth");
	transparentGridWidth = (int)jsEngine.get("transparentGridWidth");
	offset = (int)jsEngine.get("offset");
	
	player1SpawnX = (double)jsEngine.get("player1SpawnX");
	player1SpawnY = (double)jsEngine.get("player1SpawnY");
	player1SpawnZ = (double)jsEngine.get("player1SpawnZ");
	
	gridY = (double)jsEngine.get("gridY");
	groundPlaneY = (double)jsEngine.get("groundPlaneY");
	transparentPlaneY = (double)jsEngine.get("transparentPlaneY");
	ghostSpawnY = (double)jsEngine.get("ghostSpawnY");
	*/
}

public void evalScript2()
{
	/*frameWidth = (int)jsEngine.get("frameWidth");
	frameHeight = (int)jsEngine.get("frameHeight");
	
	camera1ViewAngle = (double)jsEngine.get("camera1ViewAngle");
	camera1NearClip = (double)jsEngine.get("camera1NearClip");
	camera1FarClip = (double)jsEngine.get("camera1FarClip");
	
	gridLineColor = (String)jsEngine.get("gridLineColor");
	transparentGridColor = (String)jsEngine.get("transparentGridColor");
	gridColor = (String)jsEngine.get("gridColor");

	player1Color = (String)jsEngine.get("player1Color");
	player1ScaleX = (double)jsEngine.get("player1ScaleX");
	player1ScaleY = (double)jsEngine.get("player1ScaleY");
	player1ScaleZ = (double)jsEngine.get("player1ScaleZ");
	
	player1CameraBoxColor = (String)jsEngine.get("player1CameraBoxColor");
	player1CameraBoxScaleX = (double)jsEngine.get("player1CameraBoxScaleX");
	player1CameraBoxScaleY = (double)jsEngine.get("player1CameraBoxScaleY");
	player1CameraBoxScaleZ = (double)jsEngine.get("player1CameraBoxScaleZ");
	
	ghostColor = (String)jsEngine.get("ghostColor");
	ghostScaleX = (double)jsEngine.get("ghostScaleX");
	ghostScaleY = (double)jsEngine.get("ghostScaleY");
	ghostScaleZ = (double)jsEngine.get("ghostScaleZ");
	
	ghostCameraBoxColor = (String)jsEngine.get("ghostCameraBoxColor");
	ghostCameraBoxScaleX = (double)jsEngine.get("ghostCameraBoxScaleX");
	ghostCameraBoxScaleY = (double)jsEngine.get("ghostCameraBoxScaleY");
	ghostCameraBoxScaleZ = (double)jsEngine.get("ghostCameraBoxScaleZ");
	
	scaleForwardBackwardKeyboard = (int)jsEngine.get("scaleForwardBackwardKeyboard");
	scaleLeftRightKeyboard = (int)jsEngine.get("scaleForwardBackwardKeyboard");
	scaleOrbitKeyboard = (int)jsEngine.get("scaleOrbitKeyboard");
	scaleZoom = (int)jsEngine.get("scaleZoom");
	scaleForwardBackwardGamepad = (int)jsEngine.get("scaleForwardBackwardGamepad");
	scaleLeftRightGamepad = (int)jsEngine.get("scaleLeftRightGamepad");
	scaleOrbitGamepad  = (int)jsEngine.get("scaleOrbitGamepad");
	
	maxElevation = (double)jsEngine.get("maxElevation");
	minElevation = (double)jsEngine.get("minElevation");
	minZoom = (double)jsEngine.get("minZoom");
	maxZoom = (double)jsEngine.get("maxZoom");
	*/
}

protected void initGame()
{	

	setUpClient();

	initGameObjects();

	setUpController();
	
	display.addMouseListener(this);
	display.addMouseMotionListener(this);
	
	cc1.update(0);
	
}

public void setUpClient()
{
	try
	 { 
	
		client = new Client(InetAddress.getByName(serverAddress),
		serverPort, serverProtocol, this); 
		client.sendJoinMessage();

	 }
	catch (UnknownHostException e) 
	{ 
		e.printStackTrace(); 
	}
	catch (IOException e) 
	{ 
		e.printStackTrace(); 
	}
	
	ghostAvatars = new ArrayList<GhostAvatar>();
	
}

public void initGameObjects()
{

	eventManager = EventManager.getInstance();

	createSkyboxes();
	createPlayers();
	
	//Create HUDs
	createHUD();
	
	//Create all game objects 
	createGameObjects();
	
	super.update((float) 0.0);
	
}

public void setUpController()
{
	
	 //Print connected controllers
	  /*
		for(int i = 0; i<controllers.size();i++)
		{
			if(controllers.get(i) instanceof Controller)
			{
				Controller currentController = controllers.get(i);
				controller = controllers.get(i).toString();
				System.out.println("Controller name: " + currentController.getName() + "  Controller Type: " + currentController.getType());
		
			}
		}
		*/
	
		
		controllers = im.getControllers();
		//Initialize controller that will be used in the game
		cc1 = new Camera3P(this);

		
		//Movement
		MoveForwardAction3P moveForward3P = new MoveForwardAction3P(this);
		MoveLeftAction3P moveLeft3P = new MoveLeftAction3P(this);
		MoveBackwardAction3P moveBackward3P = new MoveBackwardAction3P(this);
		MoveRightAction3P moveRight3P = new MoveRightAction3P(this);
		//orbit camera
		OrbitUpAction orbitUp = new OrbitUpAction(this);
		OrbitDownAction orbitDown = new OrbitDownAction(this);
		OrbitZoomInAction orbitZoomIn = new OrbitZoomInAction(this);
		OrbitZoomOutAction orbitZoomOut = new OrbitZoomOutAction(this);
		
		//Game Exit
		GameOverAction endGame = new GameOverAction(this);
		
		//Controller Movement and Camera Look
		MoveForwardBackwardAction3P moveForwardBackward3P = new MoveForwardBackwardAction3P(this);
		MoveRightLeftAction3P moveRightLeft3P = new MoveRightLeftAction3P(this);
		MoveRYAxisAction3P moveRYAxis3P = new MoveRYAxisAction3P(this);
		
		//physics
		physicsBase = new PhysicsBase(player1, sphere);
		StartPhysics startPhysics = new StartPhysics(physicsBase);
		
		
		for(int i=0;i<controllers.size(); i++)
		{
			String s = ("" + controllers.get(i).getType());
			if(s.equals("Keyboard"))
			{
				//Map Actions to Keyboard Keys
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Key.W, moveForward3P, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Key.A, moveLeft3P, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Key.S, moveBackward3P, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Key.D, moveRight3P, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Key.UP, orbitUp, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Key.DOWN, orbitDown, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Key.ESCAPE, endGame, IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Key.R, orbitZoomIn, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Key.E, orbitZoomOut, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Key.SPACE,startPhysics,IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
				
			}
			if(s.equals("Gamepad"))
			{
				
				//Map Movement and Camera Look to Analog Sticks of Controller
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Axis.Y, moveForwardBackward3P, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Axis.X, moveRightLeft3P, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Axis.RY, moveRYAxis3P, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);	
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Button._4, orbitZoomOut, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);	
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Button._5, orbitZoomIn, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);	
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Button._7, endGame, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);	
			

		
			}
			if(s.equals("Stick"))
			{
				//Map Movement and Camera Look to Analog Sticks of Controller
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Axis.Y, moveForwardBackward3P, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Axis.X, moveRightLeft3P, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Axis.RY, moveRYAxis3P, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);	
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Button._4, orbitZoomOut, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);	
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Button._5, orbitZoomIn, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);	
				im.associateAction(controllers.get(i), net.java.games.input.Component.Identifier.Button._7, endGame, IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);	

			}
		}

	
}
protected void initTerrain(){
	TerrainManager tm = new TerrainManager();
	HillHeightMap hhm = tm.getHHM();
	TerrainBlock terr = tm.getTerrain();
	Texture snowTex = TextureManager.loadTexture2D("skybox"+File.separator+"snowGround.jpg");
	snowTex.setApplyMode(sage.texture.Texture.ApplyMode.Replace);
	TextureState snowState;
	snowState = (TextureState) display.getRenderer().createRenderState(RenderState.RenderStateType.Texture);
	snowState.setTexture(snowTex,0);
	snowState.setEnabled(true);
	terr.setRenderState(snowState);
	addGameWorldObject(terr);
}



public void update(float elapsedTimeMS)
{
	if(dynamicScriptOn)
	{
		for(ScriptManager sm : scriptManagers){
			sm.executeDynamicScripts();
		}
		long modTime = dynamicScript.lastModified();
		
		if(modTime>fileLastModifiedTime)
		{
			fileLastModifiedTime = modTime;
			this.runScript(scriptFileName2);
			this.evalScript2();
			
			
			display.setHeight(frameHeight);
			display.setWidth(frameWidth);
			
			camera1.setPerspectiveFrustum(camera1ViewAngle,camera1AspectRatio,camera1NearClip,camera1FarClip);
			camera1.setViewport(camera1Left, camera1Right, camera1Bottom, camera1Top);
			
					for(int i=0;i<grid.size();i++)
			{
				grid.get(i).setColor(getLineColor());
				
			}
			
			transparentGroundPlane.setColorBuffer(transparentGridColor);
			
			groundPlane.setColorBuffer(gridColor);
			
			
			player1.setColorBuffer(player1Color);
			Matrix3D dynamicScale = new Matrix3D();
			dynamicScale.scale(player1ScaleX, player1ScaleY, player1ScaleZ);
			player1.setLocalScale(dynamicScale);
			
			
			player1CameraBox.setColorBuffer(player1CameraBoxColor);
			Matrix3D dynamicCameraScale = new Matrix3D();
			dynamicCameraScale.scale(player1CameraBoxScaleX, player1CameraBoxScaleY, player1CameraBoxScaleZ);
			player1CameraBox.setLocalScale(dynamicCameraScale);
			
	
			Matrix3D ghostScale = new Matrix3D();
			Matrix3D ghostCameraScale = new Matrix3D();
			
			for(int i =0;i<ghostAvatars.size();i++)
			{
				ghostAvatars.get(i).returnGhost().setColorBuffer(ghostColor);
				ghostScale.setToIdentity();
				ghostScale.scale(ghostScaleX, ghostScaleY, ghostScaleZ);
				ghostAvatars.get(i).returnGhost().setLocalScale(ghostScale);
				
				
				ghostAvatars.get(i).returnGhostCamera().setColorBuffer(ghostCameraBoxColor);
				ghostCameraScale.setToIdentity();
				ghostCameraScale.scale(ghostCameraBoxScaleX, ghostCameraBoxScaleY, ghostCameraBoxScaleZ);
				ghostAvatars.get(i).returnGhostCamera().setLocalScale(ghostCameraScale);
				
			}
		}
	}
		
	
	
	super.update(elapsedTimeMS);
	
	client.processPackets();
	
	updateDisplay(elapsedTimeMS);
	
	cc1.update(elapsedTimeMS);
	
	physicsBase.update(elapsedTimeMS);
	
}

public void updateDisplay(float elapsedTimeMS)
{	
	time += elapsedTimeMS;
	DecimalFormat df = new DecimalFormat("0.0");
	timeString.setText("Time = " + df.format(time/1000));
}

public void updateSkyboxes()
{
	Matrix3D cameraLocation = new Matrix3D();
	cameraLocation.translate(cc1.getDesiredLocation().getX(), cc1.getDesiredLocation().getY(), cc1.getDesiredLocation().getZ());
	skybox1.setLocalTranslation(cameraLocation);
}

public void updateGhost(UUID ghostID, String[] pos, String[] cameraPos, String[] cameraRot1, String[] cameraRot2)
{
	while(true)
	{
		
	for(int i = 0; i<ghostAvatars.size(); i++)
	{
		boolean thisGhost = ghostAvatars.get(i).returnID().equals(ghostID);
		
		if(thisGhost)
		{
			ghostAvatars.get(i).moveGhost(ghostSpawnY, pos, cameraPos, cameraRot1, cameraRot2 );
			
			break;
		}
	}
	break;
	}
	
}

public void removeGhost(UUID ghostID)
{
	for(int i = 0; i<ghostAvatars.size(); i++)
	{
		boolean thisGhost = ghostAvatars.get(i).returnID().equals(ghostID);
		
		if(thisGhost)
		{
			removeGameWorldObject(ghostAvatars.get(i).returnGhost());
			removeGameWorldObject(ghostAvatars.get(i).returnGhostCamera());
			ghostAvatars.remove(i);
		
		}
	}
}

public void createGameObjects()
{

	//Create the x,y,z axis in the game
	createYAxis();
	createGrid();
	createGroundPlane();
	createTransparentGround();
	//SceneNode arrow = (SceneNode)new Arrow().getObj();
	//addGameWorldObject(arrow);
	sphere = new MySphere();		
	Matrix3D translate = sphere.getLocalTranslation();
	translate.translate(15,2,-5);
	sphere.setLocalTranslation(translate);
	sphere.scale(5,5,5);
	sphere.updateLocalBound();
	sphere.updateGeometricState(0, true);
	addGameWorldObject(sphere);
}

public void createGhostAvatar(UUID ghostID, String[] pos, String[] cameraPos, String[] cameraRot1, String[] cameraRot2)
{
	GhostAvatar ghostAvatar = new GhostAvatar(ghostID, ghostSpawnY, pos, cameraPos, cameraRot1, cameraRot2, ghostColor, (float)ghostScaleX, (float)ghostScaleY, (float)ghostScaleZ, ghostCameraBoxColor, (float)ghostCameraBoxScaleX, (float)ghostCameraBoxScaleY, (float)ghostCameraBoxScaleZ);
	addGameWorldObject(ghostAvatar.returnGhost());
	addGameWorldObject(ghostAvatar.returnGhostCamera());
	ghostAvatars.add(ghostAvatar);

}

public void createPlayers()
{
	createPlayerOne();
}

public void createPlayerOne()
{
	createAvatarOne();
	createCameraBoxOne();
}

public void createSkyboxes()
{
	
	Texture northTexture = TextureManager.loadTexture2D(skyboxNorthPicture);
	Texture eastTexture = TextureManager.loadTexture2D(skyboxEastPicture);
	Texture southTexture = TextureManager.loadTexture2D(skyboxSouthPicture);
	Texture westTexture = TextureManager.loadTexture2D(skyboxWestPicture);
	Texture upTexture = TextureManager.loadTexture2D(skyboxUpPicture);
	Texture downTexture = TextureManager.loadTexture2D(skyboxDownPicture);
	
	skybox1 = new SkyBox("skybox1",1.0f, 1.0f, 1.0f);
	skybox1.scale((float)skyboxScaleX, (float)skyboxScaleY, (float)skyboxScaleZ);
	skybox1.setTexture(SkyBox.Face.North, northTexture);
	skybox1.setTexture(SkyBox.Face.East, eastTexture);
	skybox1.setTexture(SkyBox.Face.South, southTexture);
	skybox1.setTexture(SkyBox.Face.West,  westTexture);
	skybox1.setTexture(SkyBox.Face.Up, upTexture);
	skybox1.setTexture(SkyBox.Face.Down, downTexture);
	
	addGameWorldObject(skybox1);
	 
}

public void createAvatarOne()
{	 
	 
	 player1 = new MyCube(player1Color);
	 player1.translate((float)player1SpawnX, (float)player1SpawnY, (float)player1SpawnZ);
	 player1.scale((float)player1ScaleX, (float)player1ScaleY, (float)player1ScaleZ);
	 addGameWorldObject(player1);
	 
	 camera1 = new JOGLCamera(renderer);
	 camera1.setPerspectiveFrustum(camera1ViewAngle,camera1AspectRatio,camera1NearClip,camera1FarClip);
	 camera1.setViewport(camera1Left, camera1Right, camera1Bottom, camera1Top);

}

public void createCameraBoxOne()
{
	player1CameraBox = new MyCube(player1CameraBoxColor);
	player1CameraBox.scale((float)player1CameraBoxScaleX, (float)player1CameraBoxScaleY, (float)player1CameraBoxScaleZ);
	addGameWorldObject(player1CameraBox);
}

public void createHUD()
{
	//Update each of the strings on the HUD
	timeString = new HUDString("Time: " + time);
	timeString.setLocation(0.01,.01);
	timeString.setColor(Color.black);
	timeString.setRenderMode(sage.scene.SceneNode.RENDER_MODE.ORTHO);
	timeString.setCullMode(sage.scene.SceneNode.CULL_MODE.NEVER);
	
	camera1.addToHUD(timeString);

}


public void createGroundPlane()
{
	groundPlane = new MyCube(gridColor);
	groundPlane.translate(0,(float)groundPlaneY,0);
	groundPlane.scale((float)gridWidth, 0, (float)gridWidth);
	
	addGameWorldObject(groundPlane);

}

public void createTransparentGround()
{
	transparentGroundPlane = new MyCube(transparentGridColor);
	transparentGroundPlane.translate(0,(float)transparentPlaneY,0);
	transparentGroundPlane.scale((float)transparentGridWidth, 0, (float)transparentGridWidth);
	addGameWorldObject(transparentGroundPlane);
}

public void createYAxis()
{
	Point3D originY = new Point3D(0,2,0);
	Point3D positiveY = new Point3D(0,102,0);
	Point3D negativeY = new Point3D(0,-100,0);
	Line positiveYAxis = new Line(originY, positiveY, Color.green, 2);
	addGameWorldObject(positiveYAxis);
	Color lightGreen = new Color((float).6,(float)1,(float).6);
	Line negativeYAxis = new Line(originY, negativeY, lightGreen, 2);
	addGameWorldObject(negativeYAxis);
	
	Point3D originX = new Point3D(0,10,0);
	Point3D positiveX = new Point3D(102,10,0);
	Point3D negativeX = new Point3D(0,2,-102);
	Line positiveXAxis = new Line(originX, positiveX, Color.yellow, 2);
	addGameWorldObject(positiveXAxis);
	
	Point3D originZ = new Point3D(0,10,0);
	Point3D positiveZ = new Point3D(0,10,102);
	Point3D negativeZ = new Point3D(0,2,-102);
	Line positiveZAxis = new Line(originX, positiveZ, Color.red, 2);
	addGameWorldObject(positiveZAxis);
	
}

public void createGrid()
{
	grid = new ArrayList<Line>();

	int currentOffset = width;

	createOneGrid(currentOffset);
	
	int iterations = width/offset;
	
	for(int i = 0; i<iterations*2; i++)
	{
		currentOffset -=offset;
		createOneGrid(currentOffset);
	}
	
}

public void createOneGrid(double offset)
{
	Color thisColor = Color.black;
	thisColor = getLineColor();
	
	//Create the game axis. Positive direction are dark colors, while light versions are negative axis
	Point3D originX = new Point3D(offset,gridY,0);
	
	Point3D originZ = new Point3D(0,gridY,offset);


	Point3D positiveX = new Point3D(width,gridY,offset);
	Point3D negativeX = new Point3D(-width,gridY,offset);
		
	Point3D positiveZ = new Point3D(offset,gridY,width);
	Point3D negativeZ = new Point3D(offset,gridY,-width);

	Line positiveXAxis = new Line(originZ, positiveX, thisColor, 5);
	addGameWorldObject(positiveXAxis);
	grid.add(positiveXAxis);
	
	Line negativeXAxis = new Line(originZ, negativeX, thisColor, 5);
	addGameWorldObject(negativeXAxis);
	grid.add(negativeXAxis);
	
	Line positiveZAxis = new Line(originX, positiveZ, thisColor, 5);
	addGameWorldObject(positiveZAxis);
	grid.add(positiveZAxis);
	
	Line negativeZAxis = new Line(originX, negativeZ, thisColor, 5);
	addGameWorldObject(negativeZAxis);
	grid.add(negativeZAxis);
}

public Color getLineColor()
{
	if(gridLineColor=="blue")
	{
		return Color.blue;
	}
	else if(gridLineColor=="green")
	{
		return Color.green;
	}
	else if(gridLineColor=="red")
	{
		return Color.red;
	}
	else if(gridLineColor=="black")
	{
		return Color.black;
	}
	else if(gridLineColor=="purple")
	{
		Color purple = new Color(127,0,255);
		return purple;
	}
	else
	{
		return Color.blue;
	}
}



public void printCameraLocation(Point3D cameraLocation)
{
	//Print out camera information
	System.out.println("Current Camera Location- X: " + cameraLocation.getX() + ", Y: " + cameraLocation.getY() + ", Z: " + cameraLocation.getZ());

}

public Vector3D getPlayerPosition()
{

	return player1.getLocalTranslation().getCol(3);
}

public Vector3D getCameraBoxPosition()
{
	return player1CameraBox.getLocalTranslation().getCol(3);
}

public Vector3D getCameraBoxRotation1()
{
	return player1CameraBox.getLocalRotation().getCol(1);
}

public Vector3D getCameraBoxRotation2()
{
	return player1CameraBox.getLocalRotation().getCol(2);
}


public boolean isConnected()
{
	return isConnected;
}


public void setIsConnected(Boolean b)
{
	isConnected = b;
}


public ICamera getCamera()
{
	return camera1;
}

public Camera3P getCamera3P()
{
	return cc1;
}

public SkyBox getSkybox()
{
	return skybox1;
}

public MyCube returnPlayer1()
{
	return player1;
}

public MyCube returnPlayer1CameraBox()
{
	return player1CameraBox;
}

public MyCube getGroundPlane()
{
	return groundPlane;
}

public MyCube getTransparentGroundPlane()
{
	return transparentGroundPlane;
}

public ArrayList<Line> getGridLines()
{
	return grid;
}

public int returnWidth()
{
	return width;
}

public int returnGridWidth()
{
	return gridWidth;
}

public int returnTransparentGridWidth()
{
	return transparentGridWidth;
}

public int returnOffest()
{
	return offset;
}

public double getCamera1DistanceFromTarget()
{
	return camera1DistanceFromTarget;
}

public double getCamera1Azimuth()
{
	return camera1Azimuth;
}

public double getCamera1Elevation()
{
	return camera1Elevation;
}

public double getMaxElevation()
{
	return maxElevation;
}

public double getMinElevation()
{
	return minElevation;
}

public double getMinZoom()
{
	return minZoom;
}

public double getMaxZoom()
{
	return maxZoom;
}

public int getScaleForwardBackwardKeyboard()
{
	return scaleForwardBackwardKeyboard;
}

public int getScaleLeftRightKeyboard()
{
	return scaleLeftRightKeyboard;
}

public int getScaleOrbitKeyboard()
{
	return scaleOrbitKeyboard;
	
}

public int getScaleZoom()
{
	return scaleZoom;
}

public int getScaleForwardBackwardGamepad()
{
	return scaleForwardBackwardGamepad;
}

public int getScaleLeftRightGamepad()
{
	return scaleLeftRightGamepad;
}

public int getScaleOrbitGamepad()
{
	return scaleOrbitGamepad;
}

public Client getClient()
{
	return client;
}

public String getServerAddress()
{
	return serverAddress;
}

public int serverPort()
{
	return serverPort();
}

public ArrayList<GhostAvatar> getGhostAvatarList()
{
	return ghostAvatars;
}



//used to generate a random integer
private static int randomInt(int min, int max) {
	Random random = new Random();
    int randomNum = random.nextInt((max - min) + 1) + min;
    return randomNum;
}

@Override
public void mouseClicked(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseEntered(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseExited(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void mousePressed(MouseEvent arg0) {
	// TODO Auto-generated method stub
	isPressed = true;
	mousePrevX = arg0.getX();
	mousePrevY = arg0.getY();
	System.out.println("Mouse Pressed"+mousePrevX + "" + mousePrevY);
}

@Override
public void mouseReleased(MouseEvent arg0) {
	// TODO Auto-generated method stub
	isPressed = false;
	float curMouseX = arg0.getX();
	float curMouseY = arg0.getY();
	
	float mouseDeltaX = mousePrevX - curMouseX;
	float mouseDeltaY = mousePrevY - curMouseY;
	System.out.println("Mouse Released"+curMouseX + "" + curMouseY);
	moveX(mouseDeltaX);
	moveY(mouseDeltaY);
	
	
	
}

@Override
public void mouseDragged(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseMoved(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}

protected void moveX(float mouseDeltaX){
	player1.translate(.1f*mouseDeltaX,0,0);
	cc1.update(time);
	client.sendMoveMessage(player1.getLocalTranslation().getCol(3), player1CameraBox.getLocalTranslation().getCol(3), player1CameraBox.getLocalRotation().getCol(1), player1CameraBox.getLocalRotation().getCol(2));
}

protected void moveY(float mouseDeltaY){
	player1.translate(0, 0, .1f*mouseDeltaY);
	cc1.update(time);
	client.sendMoveMessage(player1.getLocalTranslation().getCol(3), player1CameraBox.getLocalTranslation().getCol(3), player1CameraBox.getLocalRotation().getCol(1), player1CameraBox.getLocalRotation().getCol(2));

}


public float getTime() {
	return time;
}

public IDisplaySystem getDisplay() {
	return display;
}

public int getFrameWidth() {
	return frameWidth;
}

public void setFrameWidth(int frameWidth) {
	this.frameWidth = frameWidth;
}

public int getFrameHeight() {
	return frameHeight;
}

public void setFrameHeight(int frameHeight) {
	this.frameHeight = frameHeight;
}

public boolean isFullScreenMode() {
	return isFullScreenMode;
}

public void setFullScreenMode(boolean isFullScreenMode) {
	this.isFullScreenMode = isFullScreenMode;
}

public int getServerPort() {
	return serverPort;
}

public ArrayList<GhostAvatar> getGhostAvatars() {
	return ghostAvatars;
}

public void setGhostAvatars(ArrayList<GhostAvatar> ghostAvatars) {
	this.ghostAvatars = ghostAvatars;
}

public boolean isDynamicScriptOn() {
	return dynamicScriptOn;
}

public void setDynamicScriptOn(boolean dynamicScriptOn) {
	this.dynamicScriptOn = dynamicScriptOn;
}

public ICamera getCamera1() {
	return camera1;
}

public void setCamera1(ICamera camera1) {
	this.camera1 = camera1;
}

public Camera3P getCc1() {
	return cc1;
}

public void setCc1(Camera3P cc1) {
	this.cc1 = cc1;
}

public SkyBox getSkybox1() {
	return skybox1;
}

public void setSkybox1(SkyBox skybox1) {
	this.skybox1 = skybox1;
}

public MyCube getPlayer1() {
	return player1;
}

public void setPlayer1(MyCube player1) {
	this.player1 = player1;
}

public MyCube getPlayer1CameraBox() {
	return player1CameraBox;
}

public void setPlayer1CameraBox(MyCube player1CameraBox) {
	this.player1CameraBox = player1CameraBox;
}

public ArrayList<Line> getGrid() {
	return grid;
}

public void setGrid(ArrayList<Line> grid) {
	this.grid = grid;
}

public double getGhostSpawnY() {
	return ghostSpawnY;
}

public void setGhostSpawnY(double ghostSpawnY) {
	this.ghostSpawnY = ghostSpawnY;
}

public double getGhostScaleX() {
	return ghostScaleX;
}

public void setGhostScaleX(double ghostScaleX) {
	this.ghostScaleX = ghostScaleX;
}

public double getGhostScaleY() {
	return ghostScaleY;
}

public void setGhostScaleY(double ghostScaleY) {
	this.ghostScaleY = ghostScaleY;
}

public double getGhostScaleZ() {
	return ghostScaleZ;
}

public void setGhostScaleZ(double ghostScaleZ) {
	this.ghostScaleZ = ghostScaleZ;
}

public String getGhostColor() {
	return ghostColor;
}

public void setGhostColor(String ghostColor) {
	this.ghostColor = ghostColor;
}

public double getGhostCameraBoxScaleX() {
	return ghostCameraBoxScaleX;
}

public void setGhostCameraBoxScaleX(double ghostCameraBoxScaleX) {
	this.ghostCameraBoxScaleX = ghostCameraBoxScaleX;
}

public double getGhostCameraBoxScaleY() {
	return ghostCameraBoxScaleY;
}

public void setGhostCameraBoxScaleY(double ghostCameraBoxScaleY) {
	this.ghostCameraBoxScaleY = ghostCameraBoxScaleY;
}

public double getGhostCameraBoxScaleZ() {
	return ghostCameraBoxScaleZ;
}

public void setGhostCameraBoxScaleZ(double ghostCameraBoxScaleZ) {
	this.ghostCameraBoxScaleZ = ghostCameraBoxScaleZ;
}

public String getGhostCameraBoxColor() {
	return ghostCameraBoxColor;
}

public void setGhostCameraBoxColor(String ghostCameraBoxColor) {
	this.ghostCameraBoxColor = ghostCameraBoxColor;
}

public double getCamera1ViewAngle() {
	return camera1ViewAngle;
}

public void setCamera1ViewAngle(double camera1ViewAngle) {
	this.camera1ViewAngle = camera1ViewAngle;
}

public double getCamera1AspectRatio() {
	return camera1AspectRatio;
}

public void setCamera1AspectRatio(double camera1AspectRatio) {
	this.camera1AspectRatio = camera1AspectRatio;
}

public double getCamera1NearClip() {
	return camera1NearClip;
}

public void setCamera1NearClip(double camera1NearClip) {
	this.camera1NearClip = camera1NearClip;
}

public double getCamera1FarClip() {
	return camera1FarClip;
}

public void setCamera1FarClip(double camera1FarClip) {
	this.camera1FarClip = camera1FarClip;
}

public double getCamera1Left() {
	return camera1Left;
}

public void setCamera1Left(double camera1Left) {
	this.camera1Left = camera1Left;
}

public double getCamera1Right() {
	return camera1Right;
}

public void setCamera1Right(double camera1Right) {
	this.camera1Right = camera1Right;
}

public double getCamera1Bottom() {
	return camera1Bottom;
}

public void setCamera1Bottom(double camera1Bottom) {
	this.camera1Bottom = camera1Bottom;
}

public double getCamera1Top() {
	return camera1Top;
}

public void setCamera1Top(double camera1Top) {
	this.camera1Top = camera1Top;
}

public double getSkyboxScaleX() {
	return skyboxScaleX;
}

public void setSkyboxScaleX(double skyboxScaleX) {
	this.skyboxScaleX = skyboxScaleX;
}

public double getSkyboxScaleY() {
	return skyboxScaleY;
}

public void setSkyboxScaleY(double skyboxScaleY) {
	this.skyboxScaleY = skyboxScaleY;
}

public double getSkyboxScaleZ() {
	return skyboxScaleZ;
}

public void setSkyboxScaleZ(double skyboxScaleZ) {
	this.skyboxScaleZ = skyboxScaleZ;
}

public String getSkyboxNorthPicture() {
	return skyboxNorthPicture;
}

public void setSkyboxNorthPicture(String skyboxNorthPicture) {
	this.skyboxNorthPicture = skyboxNorthPicture;
}

public String getSkyboxEastPicture() {
	return skyboxEastPicture;
}

public void setSkyboxEastPicture(String skyboxEastPicture) {
	this.skyboxEastPicture = skyboxEastPicture;
}

public String getSkyboxSouthPicture() {
	return skyboxSouthPicture;
}

public void setSkyboxSouthPicture(String skyboxSouthPicture) {
	this.skyboxSouthPicture = skyboxSouthPicture;
}

public String getSkyboxWestPicture() {
	return skyboxWestPicture;
}

public void setSkyboxWestPicture(String skyboxWestPicture) {
	this.skyboxWestPicture = skyboxWestPicture;
}

public String getSkyboxUpPicture() {
	return skyboxUpPicture;
}

public void setSkyboxUpPicture(String skyboxUpPicture) {
	this.skyboxUpPicture = skyboxUpPicture;
}

public String getSkyboxDownPicture() {
	return skyboxDownPicture;
}

public void setSkyboxDownPicture(String skyboxDownPicture) {
	this.skyboxDownPicture = skyboxDownPicture;
}

public int getWidth() {
	return width;
}

public void setWidth(int width) {
	this.width = width;
}

public int getGridWidth() {
	return gridWidth;
}

public void setGridWidth(int gridWidth) {
	this.gridWidth = gridWidth;
}

public int getTransparentGridWidth() {
	return transparentGridWidth;
}

public void setTransparentGridWidth(int transparentGridWidth) {
	this.transparentGridWidth = transparentGridWidth;
}

public int getOffset() {
	return offset;
}

public void setOffset(int offset) {
	this.offset = offset;
}

public double getGridY() {
	return gridY;
}

public void setGridY(double gridY) {
	this.gridY = gridY;
}

public double getGroundPlaneY() {
	return groundPlaneY;
}

public void setGroundPlaneY(double groundPlaneY) {
	this.groundPlaneY = groundPlaneY;
}

public double getTransparentPlaneY() {
	return transparentPlaneY;
}

public void setTransparentPlaneY(double transparentPlaneY) {
	this.transparentPlaneY = transparentPlaneY;
}

public double getPlayer1SpawnX() {
	return player1SpawnX;
}

public void setPlayer1SpawnX(double player1SpawnX) {
	this.player1SpawnX = player1SpawnX;
}

public double getPlayer1SpawnY() {
	return player1SpawnY;
}

public void setPlayer1SpawnY(double player1SpawnY) {
	this.player1SpawnY = player1SpawnY;
}

public double getPlayer1SpawnZ() {
	return player1SpawnZ;
}

public void setPlayer1SpawnZ(double player1SpawnZ) {
	this.player1SpawnZ = player1SpawnZ;
}

public double getPlayer1ScaleX() {
	return player1ScaleX;
}

public void setPlayer1ScaleX(double player1ScaleX) {
	this.player1ScaleX = player1ScaleX;
}

public double getPlayer1ScaleY() {
	return player1ScaleY;
}

public void setPlayer1ScaleY(double player1ScaleY) {
	this.player1ScaleY = player1ScaleY;
}

public double getPlayer1ScaleZ() {
	return player1ScaleZ;
}

public void setPlayer1ScaleZ(double player1ScaleZ) {
	this.player1ScaleZ = player1ScaleZ;
}

public double getPlayer1CameraBoxScaleX() {
	return player1CameraBoxScaleX;
}

public void setPlayer1CameraBoxScaleX(double player1CameraBoxScaleX) {
	this.player1CameraBoxScaleX = player1CameraBoxScaleX;
}

public double getPlayer1CameraBoxScaleY() {
	return player1CameraBoxScaleY;
}

public void setPlayer1CameraBoxScaleY(double player1CameraBoxScaleY) {
	this.player1CameraBoxScaleY = player1CameraBoxScaleY;
}

public double getPlayer1CameraBoxScaleZ() {
	return player1CameraBoxScaleZ;
}

public void setPlayer1CameraBoxScaleZ(double player1CameraBoxScaleZ) {
	this.player1CameraBoxScaleZ = player1CameraBoxScaleZ;
}

public String getGridLineColor() {
	return gridLineColor;
}

public void setGridLineColor(String gridLineColor) {
	this.gridLineColor = gridLineColor;
}

public String getTransparentGridColor() {
	return transparentGridColor;
}

public void setTransparentGridColor(String transparentGridColor) {
	this.transparentGridColor = transparentGridColor;
}

public String getGridColor() {
	return gridColor;
}

public void setGridColor(String gridColor) {
	this.gridColor = gridColor;
}

public String getPlayer1Color() {
	return player1Color;
}

public void setPlayer1Color(String player1Color) {
	this.player1Color = player1Color;
}

public String getPlayer1CameraBoxColor() {
	return player1CameraBoxColor;
}

public void setPlayer1CameraBoxColor(String player1CameraBoxColor) {
	this.player1CameraBoxColor = player1CameraBoxColor;
}

public void setClient(Client client) {
	this.client = client;
}

public void setConnected(boolean isConnected) {
	this.isConnected = isConnected;
}

public void setServerAddress(String serverAddress) {
	this.serverAddress = serverAddress;
}

public void setGroundPlane(MyCube groundPlane) {
	this.groundPlane = groundPlane;
}

public void setTransparentGroundPlane(MyCube transparentGroundPlane) {
	this.transparentGroundPlane = transparentGroundPlane;
}

public void setCamera1DistanceFromTarget(double camera1DistanceFromTarget) {
	this.camera1DistanceFromTarget = camera1DistanceFromTarget;
}

public void setCamera1Azimuth(double camera1Azimuth) {
	this.camera1Azimuth = camera1Azimuth;
}

public void setCamera1Elevation(double camera1Elevation) {
	this.camera1Elevation = camera1Elevation;
}

public void setMaxElevation(double maxElevation) {
	this.maxElevation = maxElevation;
}

public void setMinElevation(double minElevation) {
	this.minElevation = minElevation;
}

public void setMinZoom(double minZoom) {
	this.minZoom = minZoom;
}

public void setMaxZoom(double maxZoom) {
	this.maxZoom = maxZoom;
}

public void setScaleForwardBackwardKeyboard(int scaleForwardBackwardKeyboard) {
	this.scaleForwardBackwardKeyboard = scaleForwardBackwardKeyboard;
}

public void setScaleLeftRightKeyboard(int scaleLeftRightKeyboard) {
	this.scaleLeftRightKeyboard = scaleLeftRightKeyboard;
}

public void setScaleOrbitKeyboard(int scaleOrbitKeyboard) {
	this.scaleOrbitKeyboard = scaleOrbitKeyboard;
}

public void setScaleZoom(int scaleZoom) {
	this.scaleZoom = scaleZoom;
}

public void setScaleForwardBackwardGamepad(int scaleForwardBackwardGamepad) {
	this.scaleForwardBackwardGamepad = scaleForwardBackwardGamepad;
}

public void setScaleLeftRightGamepad(int scaleLeftRightGamepad) {
	this.scaleLeftRightGamepad = scaleLeftRightGamepad;
}

public void setScaleOrbitGamepad(int scaleOrbitGamepad) {
	this.scaleOrbitGamepad = scaleOrbitGamepad;
}

}
	

