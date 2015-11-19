package scriptManagers;

import java.util.ArrayList;

import javax.script.ScriptEngine;

import scripts.Script;

/*
 * Made so a ScriptManager can run abstract away the process of running scripts from other
 * classes. Each ScriptManager knows how to run a certain type of script and how to process the 
 * new variables and gives its ScriptEngine what it needs. When you use the ExecuteScript method 
 * you only need the file name as the parameter. The path to it abstracted away as well. 
 * You will need to know where to put a new script if you make one. 
 */

public abstract class ScriptManager {
	
	protected ScriptEngine engine;
	protected ArrayList<Script> dynamicScripts;
	protected boolean makeDynamic;
	protected String folderLoc;
	
	public abstract void executeScript(String name);
	public abstract void executeDynamicScripts();
	
	
	public void addToDynamicList(Script script){
		dynamicScripts.add(script);
	}
	
	public void setMakeDynamic(boolean arg){
		makeDynamic = arg;
	}
}
