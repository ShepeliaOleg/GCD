package utils.logs;

import enums.LogCategory;
import utils.WebDriverUtils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: sergiich
 * Date: 11/13/13
 */

public class Log{

	private ArrayList<LogEntry> logs;

	public Log(String fullog){
		logs = split(fullog);
	}

	public Log(ArrayList<LogEntry> logEntries){
		logs = logEntries;
		checkEntriesArePresent();
	}

	private ArrayList split(String fullLog){
		ArrayList<LogEntry> logList = new ArrayList<LogEntry>();
		if(fullLog.equals("Log page is unavailable")||fullLog.equals("noLogs")||fullLog.equals("Logs have not been updated")){
			logList.add(new LogEntry(fullLog));
			return logList;
		}
		ArrayList<Integer> indexList = new ArrayList<Integer>();
		Pattern pattern = Pattern.compile(LogUtils.TIMESTAMP_REGEXP);
		Matcher matcher = pattern.matcher(fullLog);
		while (matcher.find()){
			indexList.add(matcher.start());
		}
		for(int i=0;i<indexList.size(); i++){
			if(i==(indexList.size()-1)){
				logList.add(new LogEntry(fullLog.substring(indexList.get(i))));
			}else{
				logList.add(new LogEntry(fullLog.substring(indexList.get(i), indexList.get(i+1))));
			}
		}
		return logList;
	}

	public String print(){
		String result = "";
		for(int i=0; i<logs.size();i++){
			result = result + "\n " + logs.get(i).print();
		}
		return result;
	}

	public LogEntry getEntry(LogCategory logCategory){
		LogEntry logEntry = new LogEntry("noLogs");
		for(int i=0; i<logs.size();i++){
			if(logs.get(i).isRequiredEntry(logCategory)){
				logEntry = logs.get(i);
			}
		}
		return logEntry;
	}

	public boolean contains(String timestamp){
		for(int i=0; i<logs.size();i++){
			if(logs.get(i).contains(timestamp)){
				return true;
			}
		}
		return false;
	}

	public void doResponsesContainErrors(){
		for(int i=0;i<logs.size();i++){
			if(logs.get(i).contains("Response")){
				logs.get(i).containsErrorWithException();
			}
		}
	}

	public void checkEntriesArePresent(){
		for(int i=0;i<logs.size();i++){
			if(logs.get(i).contains("noLogs")){
				WebDriverUtils.runtimeExceptionWithLogs("Not all registration logs appeared");
			}
		}
	}


}
