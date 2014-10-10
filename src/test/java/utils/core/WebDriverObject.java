package utils.core;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import springConstructors.AdminUserData;
import springConstructors.UserData;

public class WebDriverObject{

    public static WebDriver webDriver;
    protected static String baseUrl;
    protected static WebDriver logDriver;
    protected static String platform;
    protected static String timestamp = "noLogs";
    public final static String PLATFORM_DESKTOP = "desktop";
    public final static String PLATFORM_MOBILE = "mobile";
    public static AdminUserData adminUserData;
    public static UserData userData;

    public AdminUserData getAdminUserData() {
        return adminUserData;
    }

    public static void setAdminUserData(AdminUserData adminUserData) {
        WebDriverObject.adminUserData = adminUserData;
    }

    public static UserData getUserData() {
        return userData;
    }

    public static void setUserData(UserData userData) {
        WebDriverObject.userData = userData;
    }

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
