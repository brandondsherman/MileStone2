package scriptManagers;

import javax.script.ScriptEngineManager;

import m2.MyGame;

public class SceneNodeScriptManager extends ScriptManager{
	
	private SceneNodeScriptManager instance;
	private MyGame game;
	
	private SceneNodeScriptManager(String folderLoc, MyGame game){
		this.folderLoc = folderLoc;
		this.game = game;
		ScriptEngineManager factory = new ScriptEngineManager();
		engine = factory.getEngineByName("js");
		engine.put("game", game);
		makeDynamic=false;
	}
	
	public SceneNodeScriptManager getInstance(String folderLoc, MyGame game){
		if(instance == null){
			instance = new SceneNodeScriptManager(folderLoc, game);
		}
		return instance;
	}
	
	@Override
	public void executeScript(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executeDynamicScripts() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
