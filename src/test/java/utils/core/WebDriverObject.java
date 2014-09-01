package utils.core;

import org.openqa.selenium.WebDriver;

public class WebDriverObject{

	public static WebDriver webDriver;
    protected static String baseUrl;
    protected static WebDriver logDriver;
    protected static String timestamp;

    public static String getBaseUrl() {
        return baseUrl;
    }

	public static WebDriver getWebDriver(){
		return webDriver;
	}

	public static void setWebDriver(WebDriver webDriver){
		WebDriverObject.webDriver=webDriver;
	}
}
