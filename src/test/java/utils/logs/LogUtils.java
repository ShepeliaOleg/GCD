package utils.logs;

import enums.LogCategory;
import utils.WebDriverUtils;
import utils.core.WebDriverObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: sergiich
 * Date: 4/11/14
 */
public class LogUtils extends WebDriverObject{
	public static final String TIMESTAMP_REGEXP = "(([0-5][0-9]|[0-9])(:[0-5][0-9]){2},[0-9]{3} (INFO|WARN|PM|ums|WARN|ERROR))";

	public static String getLatestTimestamp(){
		String result = "noLogs";
		try{
			WebDriverUtils.navigateToInternalURL(logDriver, baseUrl, "html/logs.txt");
			String fullLog = WebDriverUtils.getElementText(logDriver, "//pre");
			Pattern pattern = Pattern.compile(TIMESTAMP_REGEXP);
			Matcher matcher = pattern.matcher(fullLog);
			while (matcher.find()){
				result = matcher.group(1);
			}
		}catch(Exception e){
			System.out.println(e);
		}
		return result;
	}

	public static Log getCurrentLogs(){
		return getCurrentLogs(0);
	}

	public static Log getCurrentLogs(LogCategory[] logCategories){
		return getCurrentLogs(logCategories, 0);
	}

	public static Log getCurrentLogs(LogCategory[] logCategories, int seconds){
		ArrayList<LogEntry> logEntries = new ArrayList<LogEntry>();
		Log log = getCurrentLogs(seconds);
		for(int i=0;i<logCategories.length; i++){
			logEntries.add(log.getEntry(logCategories[i]));
		}
		Log result = new Log(logEntries);
		return result;
	}

	public static Log getCurrentLogs(int seconds){
		String result = "";
		WebDriverUtils.waitFor(seconds*1000);
		Log log = new Log("Could not get logs");
		if(timestamp.equals("noLogs")){
			log = new Log("Could not get logs");
			return log;
		}
		try{
			logDriver.navigate().refresh();
			String fullLog = WebDriverUtils.getElementText(logDriver, "//pre");

			Pattern pattern = Pattern.compile(TIMESTAMP_REGEXP);
			Matcher matcher = pattern.matcher(fullLog);
			while (matcher.find()){
				result = matcher.group(1);
			}
			if(result.equals(timestamp)){
				log = new Log("Logs have not been updated");
			}else{
				String currentLog = fullLog.substring(fullLog.indexOf(timestamp));
				log = new Log(currentLog);
			}
		}catch(Exception e){
			System.out.println(e);
		}
		return log;
	}

	public static void setTimestamp(){
		timestamp = getLatestTimestamp();
	}
}
