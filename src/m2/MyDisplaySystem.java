package m2;

import java.awt.Canvas;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;

import client.Client;
import sage.display.DisplaySystem;
import sage.display.IDisplaySystem;
import sage.renderer.IRenderer;
import sage.renderer.RendererFactory;
import sage.renderer.jogl.JOGLRenderer;

public class MyDisplaySystem implements IDisplaySystem {
	
	private JFrame myFrame;
	private GraphicsDevice device;
	private JOGLRenderer myRenderer;
	private int width, height, bitDepth, refreshRate;
	private Canvas rendererCanvas;
	private boolean isCreated;
	private boolean isFullScreen;
	private boolean isShowing;
	private MyGame myGame;
	private Client client;

	
	public MyDisplaySystem(int w, int h, int depth, int rate, boolean isFS, String rName, MyGame myGame, Client client)
	{
		width = w;
		height = h;
		bitDepth = depth;
		refreshRate = rate;
		this.isFullScreen = isFS;

		this.myGame = myGame;
		this.client = client;
		myRenderer = new JOGLRenderer();
		
		
	//	myRenderer = RendererFactory.createRenderer(rName);
		if(myRenderer == null)
		{
			throw new RuntimeException("Unable to find Renderer.");
		}
		
		rendererCanvas = myRenderer.getCanvas();
		myFrame = new JFrame("Gem Collector 3D");
		myFrame.add(rendererCanvas);
		
		DisplayMode displayMode = new DisplayMode(width, height, bitDepth, refreshRate);
		initScreen(displayMode, isFullScreen);
		DisplaySystem.setCurrentDisplaySystem(this);

		myFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		myFrame.addWindowListener(new CustomWindowListener(myGame, client));
	
		
		
		myFrame.setVisible(true);
		isCreated = true;
		
		myFrame.setTitle("War (Working Title)");
	
	}

	
	private void initScreen(DisplayMode dispMode, boolean FSRequested)
	{
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		device = environment.getDefaultScreenDevice();

		
		
		
		if(device.isFullScreenSupported()&& FSRequested)
		{
			myFrame.setUndecorated(true);
			myFrame.setResizable(false);
			myFrame.setIgnoreRepaint(true);
			
			device.setFullScreenWindow(myFrame);
			
			if(dispMode != null && device.isDisplayChangeSupported())
			{
				try
				{
			
					device.setDisplayMode(dispMode);
					myFrame.setSize(dispMode.getWidth(),  dispMode.getHeight());
					isShowing = true;
				}
				catch (Exception ex)
				{
					System.err.println("Exception setting DisplayMode: " + ex);
				}
			}
			else
			{
				System.err.println("Cannot set display mode");
			}
		}
		else
		{
			//windowsed mode
			myFrame.setSize(width,  height);
			myFrame.setLocationRelativeTo(null);
			isShowing = true;
		}

	}
	
	
	public void close()
	{
		if (device!=null)
		{
			Window window = device.getFullScreenWindow();
			if(window!=null)
			{
				window.dispose();
			}
			device.setFullScreenWindow(null);
		}
	}
	
	
	
	@Override
	public void addKeyListener(KeyListener arg0) {
		// TODO Auto-generated method stub
		myFrame.addKeyListener(arg0);
	}

	@Override
	public void addMouseListener(MouseListener arg0) {
		// TODO Auto-generated method stub
		myFrame.addMouseListener(arg0);
	}

	@Override
	public void addMouseMotionListener(MouseMotionListener arg0) {
		// TODO Auto-generated method stub
		myFrame.addMouseMotionListener(arg0);
	}


	@Override
	public void convertPointToScreen(Point arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getBitDepth() {
		// TODO Auto-generated method stub
		return bitDepth;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		
		return height;
	}

	@Override
	public int getRefreshRate() {
		// TODO Auto-generated method stub
		return refreshRate;
	}

	@Override
	public IRenderer getRenderer() {
		// TODO Auto-generated method stub
		return myRenderer;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return width;
	}

	@Override
	public boolean isCreated() {
		// TODO Auto-generated method stub
		return isCreated;
	}

	@Override
	public boolean isFullScreen() {
		// TODO Auto-generated method stub
		return isFullScreen;
	}

	@Override
	public boolean isShowing() {
		// TODO Auto-generated method stub
		return isShowing;
	}

	@Override
	public void setBitDepth(int arg0) {
		// TODO Auto-generated method stub
			bitDepth = arg0;
	}

	@Override
	public void setCustomCursor(String arg0) {
		// TODO Auto-generated method stub
			
	}

	@Override
	public void setHeight(int arg0) {
		// TODO Auto-generated method stub
			height = arg0;
			myFrame.setSize(width,  height);
			
	}

	@Override
	public void setPredefinedCursor(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRefreshRate(int arg0) {
		// TODO Auto-generated method stub
			refreshRate = arg0;
	}

	@Override
	public void setTitle(String arg0) {
		// TODO Auto-generated method stub
			myFrame.setTitle(arg0);
	}

	@Override
	public void setWidth(int arg0) {
		// TODO Auto-generated method stub
			width = arg0;
			myFrame.setSize(width, height);
	}

}
