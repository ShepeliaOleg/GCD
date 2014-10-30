package utils.logs;

import enums.LogCategory;
import org.openqa.selenium.WebDriver;
import utils.WebDriverUtils;
import utils.core.DataContainer;
import utils.core.WebDriverFactory;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogUtils {
	public static final String TIMESTAMP_REGEXP = "(([0-5][0-9]|[0-9])(:[0-5][0-9]){2},[0-9]{3} (INFO|WARN|PM|ums|WARN|ERROR))";

    private static String timestamp;

	public static void setTimestamp(){
		String result = "noLogs";
        timestamp = result;
		try{
            WebDriver logDriver = WebDriverFactory.getLogDriver();
			WebDriverUtils.navigateToInternalURL(logDriver, DataContainer.getDriverData().getBaseUrl(), "html/logs.txt");
			String fullLog = WebDriverUtils.getElementText(logDriver, "//pre");
			Pattern pattern = Pattern.compile(TIMESTAMP_REGEXP);
			Matcher matcher = pattern.matcher(fullLog);
			while (matcher.find()){
				result = matcher.group(1);
			}
		}catch(Exception e){
			System.out.println(e);
		}
		timestamp = result;
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
            WebDriver logDriver = WebDriverFactory.getLogDriver();
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

}
