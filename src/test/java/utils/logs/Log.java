package utils.logs;

import enums.LogCategory;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

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
				logList.add(new LogEntry("<div>"+fullLog.substring(indexList.get(i))+"</div>"));
			}else{
				logList.add(new LogEntry("<div>"+fullLog.substring(indexList.get(i), indexList.get(i+1))+"</div>"));
			}
		}
		return logList;
	}

	public String print(){
		String result = "";
		for(LogEntry entry:logs){
			result = result + "\n " + entry.print();
		}
		return result;
	}

	public LogEntry getEntry(LogCategory logCategory){
		LogEntry logEntry = new LogEntry("noLogs");
		for(LogEntry entry:logs){
			if(entry.isRequiredEntry(logCategory)){
				logEntry = entry;
			}
		}
		return logEntry;
	}

	public boolean contains(String timestamp){
		for(LogEntry entry:logs){
			if(entry.contains(timestamp)){
				return true;
			}
		}
		return false;
	}

	public void doResponsesContainErrors(){
		for(LogEntry entry:logs){
			if(entry.contains("Response")){
                entry.containsErrorWithException();
			}
		}
	}

	public void checkEntriesArePresent(){
		for(LogEntry entry:logs){
			if(entry.contains("noLogs")){
                AbstractTest.failTest("Not all registration logs appeared");
			}
		}
	}


}
