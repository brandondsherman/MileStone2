package scriptManagers;

import javax.script.ScriptEngineManager;

import m2.Camera3P;

public class CameraScriptManager extends ScriptManager{

	private CameraScriptManager instance;
	private Camera3P camCont;
	
	private CameraScriptManager(String folderLoc, Camera3P camCont){
		this.folderLoc = folderLoc;
		this.camCont = camCont;
		ScriptEngineManager factory = new ScriptEngineManager();
		engine = factory.getEngineByName("js");
		engine.put("camCont", camCont);
		makeDynamic = false;
		executeInitScripts();
	}
	
	public CameraScriptManager getInstance(String folderLoc, Camera3P camCont){
		if(instance == null){
			instance = new CameraScriptManager(folderLoc, camCont);
		}
		return instance;
	}
	
	public void executeDynamicScripts() {
		/*
		 * for(Script s : dynamicScripts){
		 * 		executeScript();
		 * }
		 */
	}

	protected void executeInitScripts() {
		//find init folder
		/*for(Script s : init folder){
			executeScript(s);
			if(makeDynamic){
				addToDynamicList(s);
			}
			makeDynamic=false;
		}
		
		*/
	}

	public void executeScript(String name) {

	}

}
