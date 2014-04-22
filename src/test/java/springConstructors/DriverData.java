package springConstructors;

/**
 * User: sergiich
 * Date: 4/11/14
 */
public class DriverData{

	public static String baseUrl;
	public static String pasUrl;
	public static String browser;

	public static String getBaseUrl(){
		return baseUrl;
	}

	public static String getPasUrl(){
		return pasUrl;
	}

	public static String getBrowser(){
		return browser;
	}

	public static void setBaseUrl(String baseUrl){
		DriverData.baseUrl=baseUrl;
	}

	public static void setPasUrl(String pasUrl){
		DriverData.pasUrl=pasUrl;
	}

	public static void setBrowser(String browser){
		DriverData.browser=browser;
	}

}
