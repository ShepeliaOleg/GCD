package utils.core;

import io.selendroid.client.SelendroidDriver;
import io.selendroid.common.SelendroidCapabilities;
import org.openqa.selenium.Capabilities;
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
import org.openqa.selenium.safari.SafariDriver;
import org.uiautomation.ios.IOSCapabilities;
import org.uiautomation.ios.client.uiamodels.impl.RemoteIOSDriver;
import springConstructors.DeviceData;
import springConstructors.DriverData;

import java.net.MalformedURLException;
import java.net.URL;

public class WebDriverFactory{

	private static WebDriver portalDriver;
    private static WebDriver serverDriver;
    private static WebDriver logDriver;
    private static String browser;
    private static String os;
    private static String device;
    private static URL remote;
    private static String serial;

    private static final String REMOTE = "172.29.49.73";
    private static final String REMOTE_MAC = "172.29.46.41";

    public static void initializeWebDrivers(DriverData driverData, DeviceData deviceData){
        browser =   driverData.getBrowser();
        os =        driverData.getOs();
        device =    driverData.getDevice();
        remote = deviceData.getRemoteByName(device, browser);
        serial = deviceData.getSerialByName(device);
//		try{
//			logDriver = initializeWebDriver(browser, os);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
		portalDriver = initializeWebDriver();
        setServerDriver(getRemoteDriver("firefox"));
	}

	private static WebDriver initializeWebDriver(){
		WebDriver driver = null;
		try{
            switch (os) {
                case "ios":
                    driver = createIOSDriver();
                    break;
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
                case "mac":
                    driver = getDesktopDriver();
                    break;
                case "remote":
                    driver = getRemoteDriver();
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
            quitWebDriver(portalDriver);
            quitWebDriver(serverDriver);
            quitWebDriver(logDriver);
        }catch (Exception e){
            e.printStackTrace();
        }
	}

    private static void quitWebDriver(WebDriver webdriver) {
        if(webdriver!=null){
            webdriver.quit();
        }
    }

    public static WebDriver getDesktopDriver(){
        WebDriver driver;
        switch (browser){
            case "chrome":driver=createChromeDriver(os);
                break;
            case "firefox":driver=createFireFoxDriver();
                break;
            case "explorer":driver=createIEDriver();
                break;
            case "safari":driver=createSafariDriver();
                break;
            default: throw new RuntimeException("Please set correct browser, current '"+browser+"'");
        }
        driver.manage().window().setSize(new Dimension(1920, 1080));
        return driver;
    }

    private static WebDriver getRemoteDriver(String browser){
        WebDriver driver = createRemoteDriver(browser);
        driver.manage().window().setSize(new Dimension(1920, 1080));
        return driver;
    }

    private static WebDriver getRemoteDriver(){
        return getRemoteDriver(browser);
    }

    public static WebDriver getServerDriver() {
        return serverDriver;
    }

    public static void setServerDriver(WebDriver serverDriver) {
        WebDriverFactory.serverDriver = serverDriver;
    }

//    public static void initServerDriver() {
//        if (getServerDriver() == null) {
//            try{
//                setServerDriver(getRemoteDriver("firefox"));
//            }catch(Exception e){
//                throw new RuntimeException("Starting webdriver failed \n" + e);
//            }
//        }
//    }

//    public static void switchToAdditionalWebDriver(){
//		storedWebDriver = webDriver;
//		try{
//			webDriver = getRemoteDriver("firefox");
//		}catch(Exception e){
//			throw new RuntimeException("Starting webdriver failed \n" + e);
//		}
//	}
//
//	public static void switchToMainWebDriver(){
//		webDriver.quit();
//		webDriver = storedWebDriver;
//	}

    private static WebDriver createRemoteDriver(String browser){
        Capabilities capabilities;
        URL url;
        String address = REMOTE;
        switch (browser){
            case "chrome":capabilities  = DesiredCapabilities.chrome();
                break;
            case "firefox":capabilities  = DesiredCapabilities.firefox();
                break;
            case "explorer":capabilities  = DesiredCapabilities.internetExplorer();
                break;
            case "safari":capabilities  = DesiredCapabilities.safari();
                address = REMOTE_MAC;
                break;
            default:
                throw new RuntimeException("Please set browser, now '"+browser+"'");
        }
        try{
            url = new URL("http://"+address+":4444/wd/hub");
        }catch (MalformedURLException e){
            throw new RuntimeException("Please set correct URL");
        }
        return new RemoteWebDriver(url, capabilities);
    }

    private static WebDriver createChromeDriver(String osType){
        ChromeOptions chromeOptions=new ChromeOptions();
        chromeOptions.addArguments("--ignore-certificate-errors");
        if (osType.equals("linux")) { chromeOptions.setBinary("/usr/bin/google-chrome");}
        return new ChromeDriver(chromeOptions);
    }

    private static WebDriver createSafariDriver(){
        return new SafariDriver();
    }

    private static WebDriver createFireFoxDriver(){
        FirefoxProfile profile=new FirefoxProfile();
        profile.setPreference("focusmanager.testmode", true);
        return new FirefoxDriver(profile);
    }

    private static WebDriver createIEDriver(){
        System.setProperty("webdriver.ie.driver","C:/Playtech/webdriver/iedriver/IEDriverServer.exe");
        DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
        caps.setCapability("nativeEvents", false);
        return new InternetExplorerDriver(caps);
    }

    /*Mobile*/

    private static WebDriver createWinPhoneDriver(){
        try {
            return new RemoteWebDriver(new URL("http://10.251.192.148:8080"), DesiredCapabilities.internetExplorer());
        }catch (Exception e){
            throw new RuntimeException("Starting webdriver failed \n" + e);
        }
    }

    private static WebDriver createAndroidDriver(){
        WebDriver driver;
        SelendroidCapabilities capabilities = new SelendroidCapabilities();
        capabilities.setBrowserName("android");
        capabilities.setPlatform(Platform.ANDROID);
        capabilities.setSerial(serial);
        try {
            driver = new SelendroidDriver(remote, capabilities);
        }catch (Exception e){
            throw new RuntimeException("Starting webdriver failed \n" + e);
        }
        return driver;
    }

    private static WebDriver createIOSDriver(){
        WebDriver driver;
        IOSCapabilities capabilities = IOSCapabilities.iphone("Safari");
        capabilities.setCapability(IOSCapabilities.SIMULATOR, false);
        capabilities.setCapability("rotatable", true);
        try {
            driver = new RemoteIOSDriver(new URL("http://localhost:5555/wd/hub"), capabilities);

        }catch (Exception e){
            throw new RuntimeException("Starting webdriver failed \n" + e);
        }
        return driver;
    }

    private static WebDriver getMobileChromeDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("androidPackage", "com.android.chrome");
        chromeOptions.setExperimentalOption("androidDeviceSerial", serial);
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        // return new RemoteWebDriver(remote, capabilities);
        return new ChromeDriver(capabilities);
    }

    public static WebDriver getPortalDriver() {
        return portalDriver;
    }

    public static WebDriver getLogDriver() {
        return logDriver;
    }


}
