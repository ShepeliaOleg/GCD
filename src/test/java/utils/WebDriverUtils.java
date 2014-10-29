package utils;

import enums.ConfiguredPages;
import io.selendroid.SelendroidDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.core.AbstractTest;
import utils.core.CustomExpectedConditions;
import utils.core.WebDriverFactory;
import utils.core.WebDriverObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class WebDriverUtils extends WebDriverObject{

	private static final int TIMEOUT = 10;

    // Waits

    public static void waitFor(){
        waitFor(1000);
    }

    public static void waitFor(long millisec){
        if(millisec > 0){
            try{
                Thread.sleep(millisec);
            }catch(InterruptedException e){
                AbstractTest.failTest("Sleep failed");
            }
        }
    }

    public static void waitForElement(String xpath){
        waitForElement(xpath, TIMEOUT);
    }

	public static void waitForElement(String xpath, long timeout){
		WebDriverWait wait=new WebDriverWait(webDriver, timeout);
		try{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		}catch(TimeoutException e){
			AbstractTest.failTest("Element: " + xpath + "<div>Did not appear after: " + timeout + " s</div>");
		}
	}

	public static void waitForElementToDisappear(String xpath){
		waitForElementToDisappear(xpath, TIMEOUT);
	}

	public static void waitForElementToDisappear(String xpath, long timeout){
		WebDriverWait wait=new WebDriverWait(webDriver, timeout);
		try{
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
		}catch(TimeoutException e){
			AbstractTest.failTest("Element: " + xpath + "<div>Did not disappear after: " + timeout + " s</div>");
		}
	}

	public static void waitForPageToLoad(){
		WebDriverWait wait=new WebDriverWait(webDriver, 30);
		try{
			wait.until(CustomExpectedConditions.pageLoadComplete());
		} catch(TimeoutException e) {
			AbstractTest.failTest("Page didn't load in 30 sec");
		}
	}

    public static void waitForNumberOfElements(String xpath, int number){
        waitForNumberOfElements(xpath, number, TIMEOUT);
    }

    public static void waitForNumberOfElements(String xpath, int number, long timeout){
        WebDriverWait wait=new WebDriverWait(webDriver, timeout);
        try{
            wait.until(CustomExpectedConditions.numberOfElementsEquals(number, xpath));
        } catch(TimeoutException e) {
            AbstractTest.failTest("Number of elements did not become: " + number + "<div>In " + timeout + " ms</div>");
        }
    }

    //Element actions

    public static void click(String xpath){
        waitFor();
        try {
            getElement(xpath).click();
        }catch (WebDriverException e){
            AbstractTest.failTest("Could not click element by xpath: " + xpath);
        }
    }

    public static void click(String xpath, int offset){
        Actions builder = new Actions(webDriver);
        try {
            builder.moveToElement(getElement(xpath), -offset, 0).click().build().perform();
        }catch (WebDriverException e){
            AbstractTest.failTest("Could not click element by xpath: " + xpath);
        }
    }

    public static String getAttribute(String xpath, String attribute){
        try{
            return getElement(xpath).getAttribute(attribute);
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element by Xpath: " + xpath);
        }
        return null;
    }

    public static int getElementHeight(String xpath){
        try{
            return getElement(xpath).getSize().getHeight();
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element: " + xpath);
        }
        return 0;
    }

    public static int getElementWidth(String xpath){
        try{
            return getElement(xpath).getSize().getWidth();
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element: " + xpath);
        }
        return 0;
    }

    public static String getElementText(String xpath){
        try{
            return getElement(xpath).getText();
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element: " + xpath);
        }
        return null;
    }

    private static String getElementValue(WebElement webElement){
        return webElement.getAttribute("value");
    }

    public static String getElementText(WebDriver webDriver, String xpath){
        try{
            return getElement(xpath).getText();
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element by Xpath: " + xpath);
        }
        return null;
    }

    public static boolean isVisible(String xpath){
        return isVisible(xpath, TIMEOUT);
    }

    public static boolean isVisible(String xpath, long timeout){
        WebDriverWait wait=new WebDriverWait(webDriver, timeout);
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        }catch(TimeoutException e){
            return false;
        }
        return true;
    }

    public static boolean isTextVisible(String text) {
        return isVisible(getElementXpathWithText(text), 1);
    }

    private static String getElementXpathWithText(String text) {
        return "//*[text()='" + text + "']";
    }

    public static boolean isClickable(String xpath){
        return isVisible(xpath, TIMEOUT);
    }

    public static boolean isClickable(String xpath, long timeout){
        WebDriverWait wait=new WebDriverWait(webDriver, timeout);
        try{
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        }catch(TimeoutException e){
            return false;
        }
        return true;
    }

    public static boolean isElementVisible(String xpath){
        return isVisible(xpath) && isElementDimensionsPositive(xpath);
    }

    public static boolean isElementVisible(String xpath, long timeout){
        return isVisible(xpath, timeout) && isElementDimensionsPositive(xpath);
    }

    public static boolean isElementDimensionsPositive(String xpath){
        int width = getElementWidth(xpath);
        int height = getElementHeight(xpath);
        return width > 0 && height > 0;
    }

	public static void mouseOver(String xpath){
		try{
			Actions actions=new Actions(webDriver);
			actions.moveToElement(getElement(xpath)).perform();
		}catch(NoSuchElementException e){
			AbstractTest.failTest("Could not find element: " + xpath);
		}

	}

	//Input field

    public static void clearAndInputTextToField(String xpath, String text){
        clearAndInputTextToField(xpath, text, 0);
    }

    public static void clearAndInputTextToField(String xpath, String text, int sleepTimeMillisec){
        try{
            if(sleepTimeMillisec != 0){
                clearField(xpath);
                inputTextToField(xpath, text, sleepTimeMillisec);
            }else{
                clearField(xpath);
                inputTextToField(xpath, text);
            }
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element: " + xpath);
        }
    }

    protected static void clearField(String xpath){
        try{
            getElement(xpath).clear();
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element: " + xpath);
        }
    }

	public static String getInputFieldText(String xpath){
		try{
			return getElement(xpath).getAttribute("value");
		}catch(NoSuchElementException e){
			AbstractTest.failTest("Could not find element: " + xpath);
		}
		return null;
	}

	public static void inputTextToField(String xpath, String text){
		try{
			getElement(xpath).sendKeys(text);
		}catch(NoSuchElementException e){
			AbstractTest.failTest("Could not find element: " + xpath);
		}
	}

	public static void inputTextToField(String xpath, String text, int sleepTimeMillisec){
		for(int i=0; i < text.length(); i++){
			try{
				getElement(xpath).sendKeys(text.charAt(i) + "");
				Thread.sleep(sleepTimeMillisec);
			}catch(InterruptedException e){
				AbstractTest.failTest("Sleep failed");
			}
		}
	}

    public static boolean isEditable(String xpath) {
        String baseText = getInputFieldText(xpath);
        String text = "editable";
        clearAndInputTextToField(xpath, text);
        String editedText = getInputFieldText(xpath);
        if (editedText.equals(text)) {
            clearAndInputTextToField(xpath, baseText);
            return true;
        } else {
            return false;
        }
    }

	public static void pressKey(Keys key){
		Actions action = new Actions(webDriver);
		action.sendKeys(key).perform();
	}

	//Checkbox element

	public static boolean getCheckBoxState(String xpath){
		try{
			return getElement(xpath).isSelected();
		}catch(NoSuchElementException e){
			AbstractTest.failTest("Could not find element: " + xpath);
		}
		return false;
	}

	public static void setCheckBoxState(String xpath, boolean desiredState){
		try{
			if(getCheckBoxState(xpath)!= desiredState){
				getElement(xpath).click();
			}
		}catch(NoSuchElementException e){
			AbstractTest.failTest("Could not find element: " + xpath);
		}
	}

	public static void setCheckBoxState(String id, String attribute, boolean value){
		AbstractTest.failTest("$('" + id + "')." + attribute + " = " + value + "");
	}

	//Dropdown

    private static Select createDropdown(String xpath){
        return new Select(getElement(xpath));
    }

    private static WebElement getDropdownLastOption(String xpath){
        WebElement option=null;
        try{
            List<WebElement> options=getDropdownOptions(xpath);
            int lastIndex=options.size() - 1;
            option=options.get(lastIndex);
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element: " + xpath);
        }
        return option;
    }

    public static void setDropdownOptionByText(String xpath, String value){
        try{
            Select dropdown= createDropdown(xpath);
            dropdown.selectByVisibleText(value);
        }catch(NoSuchElementException e){
            AbstractTest.failTest(e.getMessage() + " on web element " + xpath);
        }
    }

    public static void setDropdownOptionByValue(String xpath, String value){
        try{
            Select dropdown=createDropdown(xpath);
            dropdown.selectByValue(value);
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element " + xpath + " with value \"" + value + "\"");
        }
    }

    private static List<WebElement> getDropdownOptions(String xpath){
        List<WebElement> options=null;
        try{
            Select dropdown=createDropdown(xpath);
            options=dropdown.getOptions();
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element: " + xpath);
        }
        return options;
    }

    public static List<String> getDropdownOptionsText(String xpath){
        List<String> optionsText=null;
        try{
            List<WebElement> options=getDropdownOptions(xpath);
            optionsText= new ArrayList<String>();
            for(WebElement option : options){
                optionsText.add(option.getText());
            }
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element: " + xpath);
        }
        return optionsText;
    }

    public static List<String> getDropdownOptionsValue(String xpath){
        List<String> optionsValue =null;
        try{
            List<WebElement> options=getDropdownOptions(xpath);
            optionsValue = new ArrayList<String>();
            for(WebElement option : options){
                optionsValue.add(getElementValue(option));
            }
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element: " + xpath);
        }
        return optionsValue;
    }

	private static WebElement getDropdownSelectedOption(String xpath){
		WebElement option=null;
		try{
			Select dropdown=createDropdown(xpath);
			option=dropdown.getFirstSelectedOption();
		}catch(NoSuchElementException e){
			AbstractTest.failTest("Could not find element: " + xpath);
		}
		return option;
	}

	public static String getDropdownSelectedOptionText(String xpath){
		try{
			return getDropdownSelectedOption(xpath).getText().trim();
		}catch(NoSuchElementException e){
			AbstractTest.failTest("Could not find element: " + xpath);
		}
		return null;
	}

    public static String getDropdownSelectedOptionValue(String xpath){
        try{
            return getDropdownSelectedOption(xpath).getAttribute("value").replace("[","").replace("]","").replace(",","").replace(" ","");
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element: " + xpath);
        }
        return null;
    }

    //Custom dropdown

    public static void setCustomDropdownOptionByText(String xpath, String text){
        try{
            String list = openCustomDropdown(xpath);
            click(list+"//li[contains(text(), '"+text+"')]");
            waitForElementToDisappear(list);
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element " + xpath + " with text '" + text + "'");
        }
    }

    public static void setCustomDropdownOptionByValue(String xpath, String value){
        try{
            String list = openCustomDropdown(xpath);
            click(list+"//li[@data-lang ='"+value+"']");
            waitForElementToDisappear(list);
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element " + xpath + " with value '" + value + "'");
        }
    }

    public static List<String> getCustomDropdownOptionsText(String xpath){
        List<String> optionsText= new ArrayList<>();
        try{
            for(WebElement option:getCustomDropdownOptions(xpath)){
                optionsText.add(option.getText());
            }
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element: " + xpath);
        }
        return optionsText;
    }

    public static List<String> getCustomDropdownOptionsValue(String xpath){
        List<String> optionsValue = new ArrayList<>();
        try{
            for(WebElement option:getCustomDropdownOptions(xpath)){
                optionsValue.add(option.getAttribute("data-lang"));
            }
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element: " + xpath);
        }
        return optionsValue;
    }

    public static String getCustomDropdownSelectedOptionText(String xpath){
        try{
            return getCustomDropdownSelectedOption(xpath).getText();
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element: " + xpath);
        }
        return null;
    }

    public static String getCustomDropdownSelectedOptionValue(String xpath){
        try{
            WebElement option = getCustomDropdownSelectedOption(xpath);
            if(platform.equals(PLATFORM_MOBILE)){
                return option.getAttribute("data-lang");
            }else {
                return option.getAttribute("class").split(" ")[1];
            }
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element: " + xpath);
        }
        return null;
    }

    private static WebElement getCustomDropdownSelectedOption(String xpath){
        try{
            if(platform.equals(PLATFORM_MOBILE)){
                String list = getFollowingElement(xpath);
                return getElement(list+"//li[contains(@class, 'selected')]");
            }else {
                return getElement(xpath+"//span");
            }
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element: " + xpath);
        }
        return null;
    }

    private static String openCustomDropdown(String xpath){
        String list = getFollowingElement(xpath);
        click(xpath);
        waitForElement(list);
        return list;
    }

    private static List<WebElement> getCustomDropdownOptions(String xpath){
        String list = getFollowingElement(xpath);
        return webDriver.findElements(By.xpath(list+"//li"));
    }

    //List

    public static List<String> getListOfAttributeValues(String xpath, String attributeName){
        List<String> result = new ArrayList();
        String xpathItems = xpath + "//ul/li";
        for (int itemIndex=1; itemIndex <= getXpathCount(xpathItems); itemIndex++) {
            result.add(getAttribute(xpathItems + "[" + itemIndex + "]", attributeName));
        }
        return result;
    }

    //Cookie

    public static String getCookieValue(String name) {
        return webDriver.manage().getCookieNamed(name).getValue();
    }

    public static void clearCookies(){
        webDriver.manage().deleteAllCookies();
    }

    public static void addCookie(String name, String value, String domain, String path, Date expiry){
        Cookie cookie = new Cookie(name, value, domain, path, expiry);
        webDriver.manage().addCookie(cookie);
    }


    public static Cookie getCookie(String cookieName) {
        return webDriver.manage().getCookieNamed(cookieName);
    }

    public static boolean isCookieExists(String cookieName) {
        return getCookie(cookieName) != null;
    }

    public static void deleteCookie(String cookieName) {
        webDriver.manage().deleteCookieNamed(cookieName);
    }

	//Navigation

	public static String getCurrentUrl(){
		return webDriver.getCurrentUrl();
	}

    public static String getCurrentLanguageCode(){
        String leftover = getCurrentUrl().replace(getBaseUrl(), "");
        int index = leftover.indexOf("/");
        if(index!=-1){
            return leftover.substring(0, index);
        }else {
            if(leftover.length()==2||leftover.length()==5){
                return leftover;
            }else {
                return "";
            }
        }
    }

	public static void navigateToInternalURL(String relativeURL){
		String url=baseUrl + relativeURL;
        navigateToURL(url);
	}

	public static void navigateToInternalURL(WebDriver webDriver, String baseUrl, String relativeURL){
		String url=baseUrl + relativeURL;
		webDriver.get(url);
	}

	public static void navigateToURL(String url){
		webDriver.get(url);
	}

    public static void refreshPage(){
        webDriver.navigate().refresh();
    }

	//Xpath actions

	public static int getXpathCount(String xPath){
		return webDriver.findElements(By.xpath(xPath)).size();
	}

	//WindowHandling

    public static void closeAdditionalSession(){
        WebDriverFactory.switchToMainWebDriver();
    }

    public static void closeCurrentWindow(){
        webDriver.close();
    }

	public static String getWindowHandle(){
		return webDriver.getWindowHandle();
	}

    public static Set<String> getWindowHandles(){
        return webDriver.getWindowHandles();
    }

    public static void openAdditionalSession(){
        WebDriverFactory webDriverFactory = new WebDriverFactory();
        webDriverFactory.switchToAdditionalWebDriver();
    }

	public static void switchToOtherWindow(String mainWindowHandle){
		for(String winHandle : getWindowHandles()){
			if(!winHandle.equals(mainWindowHandle)){
				switchToWindow(winHandle);
			}
		}
	}

	public static void switchToWindow(String handle){
		webDriver.switchTo().window(handle);
	}

    public static boolean isGameLaunched(ConfiguredPages page){
        if(getWindowHandles().size() == 1&&getCurrentUrl().contains(page.toString())){
            return false;
        }else {
            return true;
        }
    }

	//iFrame

    public static void switchFromIframe(){
        webDriver.switchTo().defaultContent();
    }

	public static void switchToIframeById(String iframeId){
		webDriver.switchTo().frame(iframeId);
	}

    public static void switchToIframeByXpath(String iframeXpath){
        webDriver.switchTo().frame(getElement(iframeXpath));
    }

	//Script

    public static void acceptJavaScriptAlert(){
        Alert alert = webDriver.switchTo().alert();
        alert.accept();
    }

	public static void executeScript(String javascript){
		((JavascriptExecutor) webDriver).executeScript(javascript);
	}

    //Mobile

    public static void setOrientation(ScreenOrientation screenOrientation){
        SelendroidDriver selendroidDriver = (SelendroidDriver)webDriver;
        selendroidDriver.rotate(screenOrientation);
        waitFor(1000);
    }

    public static ScreenOrientation getOrientation(){
        SelendroidDriver selendroidDriver = (SelendroidDriver)webDriver;
        return selendroidDriver.getOrientation();
    }

    public static String getDomain() {
        int firstDotIndex = baseUrl.indexOf(".");
        return baseUrl.substring(firstDotIndex).replace("/", "").replace(":8080", "");
    }

    private static WebElement getElement(String xpath) {
        return webDriver.findElement(By.xpath(xpath));
    }

    public static String getFollowingElement(String xpath, int index) {
        return "//*[preceding-sibling::"+xpath.substring(2)+"]["+index+"]";
    }

    public static String getFollowingElement(String xpath) {
        return "//*[preceding-sibling::"+xpath.substring(2)+"]";
    }

    public static String getPrecedingElement(String xpath, int index) {
        return "//*[following-sibling::"+xpath.substring(2)+"]["+index+"]";
    }

    public static String getPrecedingElement(String xpath) {
        return "//*[following-sibling::"+xpath.substring(2)+"]";
    }
}
