package springConstructors;

/**
 * User: sergiich
 * Date: 4/11/14
 */
public class DriverData{

    public static String baseUrl;
    public static String browser;
    public static String os;
    public static String platform;
    public static String defaultBaseUrl;
    public static String defaultBrowser;
    public static String defaultOs;
    public static String defaultPlatform;

    public static String getBaseUrl(){
        if(baseUrl!=null){
            return baseUrl;
        }else{
            return getDefaultBaseUrl();
        }
    }

    public static void setBaseUrl(String baseUrl){
        DriverData.baseUrl=baseUrl;
    }

	public static String getBrowser(){
		if(browser!=null){
            return browser;
        }else{
            return getDefaultBrowser();
        }
	}

    public static void setBrowser(String browser){
        DriverData.browser=browser;
    }

    public static String getOs() {
        if(os!=null){
            return os;
        }else{
            return getDefaultOs();
        }
    }

    public static void setOs(String os) {
        DriverData.os = os;
    }

    public static String getPlatform() {
        if(platform!=null){
            return platform;
        }else{
            return getDefaultPlatform();
        }
    }

    public static void setPlatform(String platform) {
        DriverData.platform = platform;
    }

    public static String getDefaultBaseUrl() {
        return defaultBaseUrl;
    }

    public static void setDefaultBaseUrl(String defaultBaseUrl) {
        DriverData.defaultBaseUrl = defaultBaseUrl;
    }

    public static String getDefaultBrowser() {
        return defaultBrowser;
    }

    public static void setDefaultBrowser(String defaultBrowser) {
        DriverData.defaultBrowser = defaultBrowser;
    }

    public static String getDefaultOs() {
        return defaultOs;
    }

    public static void setDefaultOs(String defaultOs) {
        DriverData.defaultOs = defaultOs;
    }

    public static String getDefaultPlatform() {
        return defaultPlatform;
    }

    public static void setDefaultPlatform(String defaultPlatform) {
        DriverData.defaultPlatform = defaultPlatform;
    }
}
