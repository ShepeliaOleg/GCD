package springConstructors;

public class DriverData{

    public static String baseUrl;
    public static String browser;
    public static String os;
    public static String platform;
    public static String device;

    public static String getBaseUrl(){
        return baseUrl;
    }

    public static void setBaseUrl(String baseUrl){
        DriverData.baseUrl=baseUrl;
    }

	public static String getBrowser(){
        return browser;
	}

    public static void setBrowser(String browser){
        DriverData.browser=browser;
    }

    public static String getOs() {
        return os;
    }

    public static void setOs(String os) {
        DriverData.os = os;
    }

    public static String getPlatform() {
        return platform;
    }

    public static void setPlatform(String platform) {
        DriverData.platform = platform;
    }

    public static String getDevice() {
        return device;
    }

    public static void setDevice(String device) {
        DriverData.device = device;
    }

}
