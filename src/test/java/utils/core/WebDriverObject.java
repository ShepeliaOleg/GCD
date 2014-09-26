package utils.core;

import org.openqa.selenium.WebDriver;
import utils.WebDriverUtils;

import java.util.ArrayList;

public class WebDriverObject{

	public static WebDriver webDriver;
    protected static String baseUrl;
    protected static WebDriver logDriver;

    protected static String platform;

    protected static String timestamp = "noLogs";

    public final static String PLATFORM_DESKTOP = "desktop";
    public final static String PLATFORM_MOBILE = "mobile";
    public static String getBaseUrl() {
        return baseUrl;
    }

	public static WebDriver getWebDriver(){
		return webDriver;
	}

	public static void setWebDriver(WebDriver webDriver){
		WebDriverObject.webDriver=webDriver;
	}

    public static String getPlatform() {
        return platform;
    }


}
