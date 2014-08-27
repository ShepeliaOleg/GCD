package utils.core;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import springConstructors.DriverData;

/**
 * User: sergiich
 * Date: 4/9/14
 */
public class WebDriverFactory extends WebDriverObject{

	private static WebDriver storedWebDriver;
	private static String browser;
    private static String os;

	@Autowired
	@Qualifier("driverData")
	private DriverData driverData;

	public WebDriverFactory(){
        baseUrl =   driverData.getBaseUrl();
        browser =   driverData.getBrowser();
        os =        driverData.getOs();
	}

	public void initializeWebDrivers(){
//		try{
//			logDriver = initializeWebDriver(browser, os);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
		webDriver = initializeWebDriver(browser, os);
	}

	public WebDriver initializeWebDriver(String browserType, String osType){
		WebDriver driver = null;
		try{
			if(browserType.equals("chrome")){
				driver=createChromeDriver(osType);
			}else if(browserType.equals("firefox")){
				driver=createFireFoxDriver();
			}
			driver.manage().window().setSize(new Dimension(1920, 1080));
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

	public void switchToAdditionalWebDriver(){
		storedWebDriver = webDriver;
		try{
			webDriver=initializeWebDriver(browser, os);
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
}
