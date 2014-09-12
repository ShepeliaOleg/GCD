package utils.core;

import io.selendroid.SelendroidCapabilities;
import io.selendroid.SelendroidDriver;
import io.selendroid.SelendroidLauncher;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import springConstructors.DeviceData;
import springConstructors.DriverData;

import java.net.URL;

/**
 * User: sergiich
 * Date: 4/9/14
 */
public class WebDriverFactory extends WebDriverObject{

	private static WebDriver storedWebDriver;
	public static String browser;
    private static String os;
    private static String device;
    public static SelendroidLauncher selendroidServer;

	@Autowired
	@Qualifier("driverData")
	private DriverData driverData;

    @Autowired
    @Qualifier("deviceData")
    private DeviceData deviceData;

	public WebDriverFactory(){
        baseUrl =   driverData.getBaseUrl();
        platform =  driverData.getPlatform();
        browser =   driverData.getBrowser();
        os =        driverData.getOs();
        device =    driverData.getDevice();
	}

	public void initializeWebDrivers(){
//		try{
//			logDriver = initializeWebDriver(browser, os);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
		webDriver = initializeWebDriver();
	}

	public WebDriver initializeWebDriver(){
		WebDriver driver = null;
		try{
            switch (os) {
                case "android":
                    if (browser.equals("native")) {
                        driver = createAndroidDriver();
                    } else if (browser.equals("chrome")) {
                        driver = getMobileChromeDriver();
                    }
                    break;
                case "winPhone":
                    driver = createWinPhoneDriver();
                    break;
                case "windows":
                case "linux":
                    getDesktopDriver(driver);
                    break;
                default:
                    throw new RuntimeException("OS was not set correctly");
            }
		}catch(Exception e){
			throw new RuntimeException("Starting webdriver failed \n" + e);
		}
		return driver;
	}

	public static void shutDown(){
		try{
            webDriver.quit();
        }catch (Exception e){
            e.printStackTrace();
        }
//        try{
//            logDriver.quit();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
	}

    private WebDriver getDesktopDriver(WebDriver driver){
        if(browser.equals("chrome")){
            driver=createChromeDriver(os);
        }else if(browser.equals("firefox")){
            driver=createFireFoxDriver();
        }else if(browser.equals("explorer")){
            driver=createIEDriver();
        }
        driver.manage().window().setSize(new Dimension(1920, 1080));
        return driver;
    }

	public void switchToAdditionalWebDriver(){
		storedWebDriver = webDriver;
		try{
			webDriver=initializeWebDriver();
		}catch(Exception e){
			throw new RuntimeException("Starting webdriver failed \n" + e);
		}
	}

	public static void switchToMainWebDriver(){
		webDriver.quit();
		webDriver = storedWebDriver;
	}

    private WebDriver createChromeDriver(String osType){
        ChromeOptions chromeOptions=new ChromeOptions();
        chromeOptions.addArguments("--ignore-certificate-errors");
        if (osType.equals("linux")) { chromeOptions.setBinary("/usr/bin/google-chrome");}
        return new ChromeDriver(chromeOptions);
    }

    private WebDriver createFireFoxDriver(){
        FirefoxProfile profile=new FirefoxProfile();
        profile.setPreference("focusmanager.testmode", true);
        return new FirefoxDriver(profile);
    }

    private WebDriver createIEDriver(){
        System.setProperty("webdriver.ie.driver","C:/Playtech/webdriver/iedriver/IEDriverServer.exe");
        return new InternetExplorerDriver();
    }

    /*Mobile*/

    private WebDriver createWinPhoneDriver(){
        try {
            return new RemoteWebDriver(new URL("http://10.251.192.148:8080"), DesiredCapabilities.internetExplorer());
        }catch (Exception e){
            throw new RuntimeException("Starting webdriver failed \n" + e);
        }
    }

    private WebDriver createAndroidDriver(){
        WebDriver driver;
        SelendroidCapabilities capabilities = new SelendroidCapabilities();
        capabilities.setBrowserName("android");
        capabilities.setPlatform(Platform.ANDROID);
        capabilities.setSerial(deviceData.getSerialByName(device));
        try {
            driver = new SelendroidDriver(deviceData.getRemoteByName(device), capabilities);
        }catch (Exception e){
            throw new RuntimeException("Starting webdriver failed \n" + e);
        }
        return driver;
    }

    public WebDriver getMobileChromeDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("androidPackage", "com.android.chrome");
        chromeOptions.setExperimentalOption("androidDeviceSerial", deviceData.getSerialByName(device));
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        return new RemoteWebDriver(deviceData.getRemoteByName(device), capabilities);
    }


}
