package scripts;

import java.io.File;
import java.util.ArrayList;

public class Script {
	protected String scriptFileName;
	protected File file;
	protected long fileLastModified;
	
	public Script(String fileName, String fullName){
		scriptFileName = fileName;
		file = new File(fullName);
		fileLastModified = file.lastModified();
	}
	
	public String getScriptFileName(){
		return scriptFileName;
	}
	
	public boolean getFileLastModified(){
		if(file.lastModified() > fileLastModified){
			fileLastModified = file.lastModified();
			return true;
		}else return false;
	}
}
