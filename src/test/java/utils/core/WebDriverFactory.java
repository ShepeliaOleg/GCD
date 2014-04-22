package utils.core;

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

	@Autowired
	@Qualifier("driverData")
	private DriverData driverData;

	public WebDriverFactory(){
		browser = driverData.getBrowser();
		baseUrl = driverData.getBaseUrl();
	}

	public void initializeWebDriver(){
		try{
			logdriver = initializeWebDriver(browser);
		}catch(Exception e){
			e.printStackTrace();
		}
		webDriver = initializeWebDriver(browser);
	}

	public WebDriver initializeWebDriver(String browserType){
		WebDriver driver = null;
		try{
			if(browserType.equals("chrome")){
				driver=createChromeDriver();
			}else if(browserType.equals("firefox")){
				driver=createFireFoxDriver();
			}
			driver.manage().window().maximize();
		}catch(Exception e){
			throw new RuntimeException("Starting webdriver failed \n" + e);
		}
		return driver;
	}

	private WebDriver createChromeDriver(){
		ChromeOptions chromeOptions=new ChromeOptions();
		chromeOptions.addArguments("--ignore-certificate-errors");
		return new ChromeDriver(chromeOptions);
	}

	private WebDriver createFireFoxDriver(){
		FirefoxProfile profile=new FirefoxProfile();
		profile.setAcceptUntrustedCertificates(true);
		profile.setAssumeUntrustedCertificateIssuer(false);
		return new FirefoxDriver(profile);
	}

	public static void shutDown(){
		webDriver.quit();
		logdriver.quit();
	}

	public static void switchToAdditionalWebDriver(){
		storedWebDriver = webDriver;
		try{
			webDriver=getWebDriver();
		}catch(Exception e){
			throw new RuntimeException("Starting webdriver failed \n" + e);
		}
	}

	public static void switchToMainWebDriver(){
		webDriver.quit();
		webDriver = storedWebDriver;
	}
}
