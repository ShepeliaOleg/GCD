package utils.core;

import enums.PlatForm;
import io.appium.java_client.ios.IOSDriver;
import io.selendroid.client.SelendroidDriver;
import io.selendroid.common.SelendroidCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import springConstructors.Device;
import springConstructors.DeviceData;
import springConstructors.DriverData;

import java.net.MalformedURLException;
import java.net.URL;

public class WebDriverFactory{

	private static WebDriver portalDriver;
    private static WebDriver serverDriver;
    private static WebDriver logDriver;
    private static String    browser;
    private static String    os;
    private static String    deviceId;
    private static PlatForm  platform;
    private static String    pathToDownloadsFolder;
    private static final String LOCALHOST =  "127.0.0.1";
    private static final String REMOTE =     "172.29.49.73";
    private static final String REMOTE_MAC = "172.29.46.170";

    public static void initializeWebDrivers(DriverData driverData, DeviceData deviceData){
        browser =    driverData.getBrowser();
        os =         driverData.getOs();
        deviceId =   driverData.getDeviceId();
        platform =   getPlatform(os);
		portalDriver = initializeWebDriver(deviceData.getDeviceById(deviceId));
        setServerDriver(getRemoteDriver("firefox"));
        pathToDownloadsFolder = setPathToDownloadsFolder("Downloads");
	}

    private static PlatForm getPlatform(String os) {
        switch (os) {
            case "ios":
            case "android":
            case "winPhone":
                return PlatForm.mobile;
            case "windows":
            case "linux":
            case "mac":
            case "remote":
                return PlatForm.desktop;
            default:
                return null;
        }
    }

    public static String getOs() {
        return os;
    }

    private static WebDriver initializeWebDriver(Device device){
		WebDriver driver = null;
		try{
            switch (os) {
                case "ios":
                    driver = createIOSDriver(device);
                    break;
                case "android":
                    if (browser.equals("native")) {
                        driver = createAndroidDriver(device);
                    } else if (browser.equals("chrome")) {
                        driver = getMobileChromeDriver(device);
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

    private static WebDriver createRemoteDriver(String browser){
        DesiredCapabilities capabilities;
        String address = REMOTE;
        switch (browser){
            case "chrome":capabilities  = DesiredCapabilities.chrome();
                break;
            case "firefox":
                FirefoxProfile  firefoxProfile = new FirefoxProfile();
                firefoxProfile.setPreference("browser.download.folderList",1);
                firefoxProfile.setPreference("browser.download.manager.showWhenStarting",false);
                firefoxProfile.setPreference("browser.download.dir", System.getProperty("user.dir") + "\\target\\download\\");
                firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk","text/csv");
                capabilities  = DesiredCapabilities.firefox();
                capabilities.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
                break;
            case "explorer":capabilities  = DesiredCapabilities.internetExplorer();
                break;
            case "safari":capabilities  = DesiredCapabilities.safari();
                address = REMOTE_MAC;
                break;
            default:
                throw new RuntimeException("Please set browser, now '"+browser+"'");
        }

        return new RemoteWebDriver(getUrl(address, "4444", "wd/hub"), capabilities);
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
            return new RemoteWebDriver(getUrl("10.251.192.148", "8080" , ""), DesiredCapabilities.internetExplorer());
        }catch (Exception e){
            throw new RuntimeException("Starting webdriver failed \n" + e);
        }
    }

    private static WebDriver createAndroidDriver(Device device){
        WebDriver driver;
        SelendroidCapabilities capabilities = new SelendroidCapabilities();
        capabilities.setBrowserName("android");
        capabilities.setPlatform(Platform.ANDROID);
        capabilities.setSerial(device.getSerial());
        try {
            driver = new SelendroidDriver(getUrl(device.getRemote()), capabilities);
        }catch (Exception e){
            throw new RuntimeException("Starting webdriver failed \n" + e);
        }
        return driver;
    }

    private static WebDriver createIOSDriver(Device iOSDevice) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platfromName", "iOS");
        capabilities.setCapability("browserName", "Safari");
        capabilities.setCapability("autoWebview", true);
        capabilities.setCapability("deviceName", iOSDevice.getName());
        capabilities.setCapability("platfromVersion", iOSDevice.getVersion());
        capabilities.setCapability("udid", iOSDevice.getSerial());

        IOSDriver driver = new IOSDriver(getUrl(iOSDevice.getRemote()), capabilities);
        return driver;
    }

    private static URL getUrl(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Please set correct URL");
        }
    }

    private static URL getUrl(String host, String port, String suffix) {
       return getUrl("http://"+ host +":" + port + "/" + suffix);
    }

    private static WebDriver getMobileChromeDriver(Device device) {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("androidPackage", "com.android.chrome");
        chromeOptions.setExperimentalOption("androidDeviceSerial", device.getSerial());
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

    private static String setPathToDownloadsFolder(String downloadFolderName){
        String homePath = System.getProperty("user.home");
        String fileSeparator = System.getProperty("file.separator");
        return homePath + fileSeparator + downloadFolderName + fileSeparator;
    }

    public static String getPathToDownloadsFolder(){
        return pathToDownloadsFolder;
    }

    public static PlatForm getPlatform() {
        return platform;
    }
}
