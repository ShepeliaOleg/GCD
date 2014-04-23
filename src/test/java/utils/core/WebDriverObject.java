package utils.core;

import org.openqa.selenium.WebDriver;

/**
 * User: sergiich
 * Date: 4/9/14
 */

public class WebDriverObject{

	public static WebDriver webDriver;
	protected static WebDriver logDriver;
	protected static String timestamp;
	protected static String baseUrl;

	public static WebDriver getWebDriver(){
		return webDriver;
	}

	public static void setWebDriver(WebDriver webDriver){
		WebDriverObject.webDriver=webDriver;
	}
}
