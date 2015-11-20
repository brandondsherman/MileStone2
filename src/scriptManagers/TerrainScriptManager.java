package scriptManagers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import gameWorldObjects.TerrainManager;
import m2.Camera3P;
import m2.MyGame;
import scripts.Script;

public class TerrainScriptManager extends ScriptManager{

	private static TerrainScriptManager instance;
	private TerrainManager terrMan;
	
	private TerrainScriptManager(String folderLoc, TerrainManager terrMan){
		this.folderLoc = folderLoc;
		this.terrMan = terrMan;
		ScriptEngineManager factory = new ScriptEngineManager();
		engine = factory.getEngineByName("js");
		engine.put("terrMan", terrMan);
		engine.put("scriptMan",this);
		makeDynamic = false;
		MyGame.scriptManagers.add(this);
	}
	
	public static TerrainScriptManager getInstance(String folderLoc, TerrainManager terrMan){
		if(instance == null){
			instance = new TerrainScriptManager(folderLoc, terrMan);
		}
		return instance;
	}
	
	public void executeDynamicScripts() {
		  for(Script s : dynamicScripts){
			  
			  if(s.getFileLastModified()){
			  executeScript(s.getScriptFileName());
			  }
		  }
	}



	public void executeScript(String name) {
		try {
			makeDynamic = false;
			FileReader fileReader = new FileReader(folderLoc+name);
			engine.eval(fileReader);
			fileReader.close();
			if(makeDynamic){
				Script s = new Script(name, folderLoc+name);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(IOException e2)
		{
			System.out.println("IO problem with " + name + e2);
		}
		catch(ScriptException e3)
		{
			System.out.println("ScriptException in " + name + e3);
		}
		catch(NullPointerException e4)
		{
			System.out.println("Null pointer exception in " + name + e4);
		}
	}

}
