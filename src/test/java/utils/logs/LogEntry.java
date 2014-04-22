package utils.logs;

import enums.LogCategory;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 11/13/13
 */

public class LogEntry{

	private String entry;

	public LogEntry(String entry){
		this.entry = entry;
	}

	public String print(){
		return entry;
	}

	public Boolean isRequiredEntry(LogCategory logCategory){
		return entry.contains(logCategory.toString());
	}

	public boolean containsError(){
		return !entry.contains(",errorCode=0,");
	}

	public void containsErrorWithException(){
		if(containsError()){
			WebDriverUtils.runtimeExceptionWithLogs("\n Error code is: " + getError());
		}
	}

	public boolean contains(String string){
		return entry.contains(string);
	}

	public boolean containsParameter(String parameter){
		return entry.contains(parameter);
	}
	public void containsParameters(String[] parameters){
		for(int i=0;i<parameters.length;i++){
			if(!containsParameter(parameters[i])){
				WebDriverUtils.runtimeExceptionWithLogs("Some parameters were not correct, - " + parameters[i]);
			}
		}
	}

	public String getError(){
		int start = entry.indexOf("errorCode=");
		int end = entry.substring(start).indexOf(',');
		return entry.substring(start, end);
	}
}
