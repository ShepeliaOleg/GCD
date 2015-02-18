package utils;

import enums.ConfiguredPages;
import enums.Licensee;
import io.selendroid.client.SelendroidDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.html5.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.core.AbstractPortalPage;
import utils.core.AbstractTest;
import utils.core.CustomExpectedConditions;
import utils.core.DataContainer;
import utils.core.WebDriverFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class WebDriverUtils{

	private static final int TIMEOUT = 10;
    private static final int WAIT = 1000;

    // Waits

    public static void waitFor(){
        waitFor(WAIT);
    }

    public static void waitFor(long millisec){
        System.out.println("Waiting for "+millisec+" ms");
        if(millisec > 0){
            try{
                Thread.sleep(millisec);
            }catch(InterruptedException e){
                AbstractTest.failTest("Sleep failed");
            }
        }else {
            AbstractTest.failTest("Please set correct wait time");
        }
    }

    public static void waitForElement(String xpath){
        waitForElement(WebDriverFactory.getPortalDriver(), xpath);
    }
    public static void waitForElement(WebDriver webDriver, String xpath){
        waitForElement(webDriver, xpath, TIMEOUT);
    }

    public static void waitForElement(String xpath, long timeout){
        waitForElement(WebDriverFactory.getPortalDriver(), xpath, timeout);
    }

	public static void waitForElement(WebDriver webDriver, String xpath, long timeout){
        System.out.println("Waiting for "+xpath+" element");
        WebDriverWait wait=new WebDriverWait(webDriver, timeout);
		try{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		}catch(TimeoutException e){
			AbstractTest.failTest("Element: " + xpath + "<div>Did not appear after: " + timeout + " s</div>");
		}
	}

    public static void waitForElementToDisappear(String xpath){
        waitForElementToDisappear(WebDriverFactory.getPortalDriver(), xpath);
    }
	public static void waitForElementToDisappear(WebDriver webDriver, String xpath){
		waitForElementToDisappear(webDriver, xpath, TIMEOUT);
	}

    public static void waitForElementToDisappear(String xpath, long timeout){
        waitForElementToDisappear(WebDriverFactory.getPortalDriver(), xpath, timeout);
    }

	public static void waitForElementToDisappear(WebDriver webDriver, String xpath, long timeout){
        System.out.println("Waiting for "+xpath+" element to disappear");
		WebDriverWait wait=new WebDriverWait(webDriver, timeout);
		try{
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
		}catch(TimeoutException e){
			AbstractTest.failTest("Element: " + xpath + "<div>Did not disappear after: " + timeout + " s</div>");
		}
	}

    public static void waitForPageToLoad(){
        waitForPageToLoad(WebDriverFactory.getPortalDriver());
        waitForElementToDisappear(AbstractPortalPage.SITE_LOADER_XP);
    }

	public static void waitForPageToLoad(WebDriver webDriver){
		WebDriverWait wait=new WebDriverWait(webDriver, 30);
		try{
			wait.until(CustomExpectedConditions.pageLoadComplete());
		} catch(TimeoutException e) {
			AbstractTest.failTest("Page didn't load in 30 sec");
		}
	}

    public static void waitForNumberOfElements(String xpath, int number){
        waitForNumberOfElements(WebDriverFactory.getPortalDriver(), xpath, number);
    }

    public static void waitForNumberOfElements(WebDriver webDriver, String xpath, int number){
        waitForNumberOfElements(webDriver, xpath, number, TIMEOUT);
    }

    public static void waitForNumberOfElements(WebDriver webDriver, String xpath, int number, long timeout){
        WebDriverWait wait=new WebDriverWait(webDriver, timeout);
        try{
            wait.until(CustomExpectedConditions.numberOfElementsEquals(number, xpath));
        } catch(TimeoutException e) {
            AbstractTest.failTest("Number of elements did not become: " + number + "<div>In " + timeout + " ms</div>");
        }
    }

    //Element actions

    public static void click(String xpath){
        click(WebDriverFactory.getPortalDriver(), xpath);
    }

    public static void click(WebDriver webDriver, String xpath) {
        System.out.println("Clicking "+xpath+" element");
        WebElement webElement = getElement(webDriver, xpath);
        mouseOver(webDriver, webElement);
        try {
            //FOR Internet Explorer 11
            //Actions actions = new Actions(WebDriverFactory.getPortalDriver());
            //actions.moveToElement(webElement).click().perform();
            webElement.click();
        }catch (WebDriverException e){
            AbstractTest.failTest("Could not click element by xpath: " + xpath);
        }
    }

    private static Actions getAction(WebDriver webDriver) {
        return new Actions(webDriver);
    }

    public static void click(String xpath, int offset){
        click(WebDriverFactory.getPortalDriver(), xpath, offset);
    }

    public static void click(WebDriver webDriver, String xpath, int offset) {
        System.out.println("Clicking "+xpath+" element with offset "+offset+"");
        try {
            getAction(webDriver).moveToElement(getElement(webDriver, xpath), -offset, 0).click().build().perform();
        }catch (WebDriverException e){
            AbstractTest.failTest("Could not click element by xpath: " + xpath);
        }
    }

    public static String getAttribute( String xpath, String attribute){
        System.out.println("Getting attribute "+attribute+" of "+xpath+" element");
        return getAttribute(WebDriverFactory.getPortalDriver(), xpath, attribute);
    }

    public static String getAttribute(WebDriver webDriver, String xpath, String attribute){
        System.out.println("Getting attribute "+attribute+" of "+xpath+" element");
        try{
            return getElement(webDriver, xpath).getAttribute(attribute);
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element by Xpath: " + xpath);
        }
        return null;
    }

    public static int getElementHeight(String xpath){
        return getElementHeight(WebDriverFactory.getPortalDriver(), xpath);
    }

    public static int getElementHeight(WebDriver webDriver, String xpath){
        try{
            return getElement(webDriver, xpath).getSize().getHeight();
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element: " + xpath);
        }
        return 0;
    }

    public static int getElementWidth(String xpath){
        return getElementWidth(WebDriverFactory.getPortalDriver(), xpath);
    }

    public static int getElementWidth(WebDriver webDriver, String xpath){
        try{
            return getElement(webDriver, xpath).getSize().getWidth();
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element: " + xpath);
        }
        return 0;
    }

    public static String getElementText(String xpath) {
        return getElementText(WebDriverFactory.getPortalDriver(), xpath);
    }

    public static String getElementText(WebDriver webDriver, String xpath) {
        System.out.println("Getting text of "+xpath+" element");
        return getElement(webDriver, xpath).getText();
    }

    private static String getElementValue(WebElement webElement) {
        return webElement.getAttribute("value");
    }

    public static boolean isVisible(String xpath) {
        return isVisible(WebDriverFactory.getPortalDriver(), xpath);
    }

    public static boolean isVisible(WebDriver webDriver, String xpath){
        return isVisible(webDriver, xpath, TIMEOUT);
    }

    public static boolean isVisible(String xpath, long timeout){
        return isVisible(WebDriverFactory.getPortalDriver(), xpath, timeout);
    }

    public static boolean isVisible(WebDriver webDriver, String xpath, long timeout){
        System.out.println("Checking if "+xpath+" visible");
        WebDriverWait wait=new WebDriverWait(webDriver, timeout);
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        }catch(TimeoutException e){
            return false;
        }
        return true;
    }

    public static boolean isTextVisible(String text) {
        return isTextVisible(WebDriverFactory.getPortalDriver(), text);
    }

    public static boolean isTextVisible(WebDriver webDriver, String text) {
        return isVisible(webDriver, getElementXpathWithText(text), 1);
    }

    public static boolean isTextVisible(WebDriver webDriver, String text, int timeOutInSeconds) {
        return isVisible(webDriver, getElementXpathWithText(text), timeOutInSeconds);
    }

    private static String getElementXpathWithText(String text) {
        return "//*[text()='" + text + "']";
    }

    public static boolean isClickable(WebDriver webDriver, String xpath){
        return isVisible(webDriver, xpath, TIMEOUT);
    }

    public static boolean isClickable(WebDriver webDriver, String xpath, long timeout){
        WebDriverWait wait=new WebDriverWait(webDriver, timeout);
        try{
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        }catch(TimeoutException e){
            return false;
        }
        return true;
    }

    public static boolean isElementVisible(WebDriver webDriver, String xpath){
        return isVisible(webDriver, xpath) && isElementDimensionsPositive(webDriver, xpath);
    }

    public static boolean isElementVisible(String xpath, long timeout){
        return isElementVisible(WebDriverFactory.getPortalDriver(), xpath, timeout);
    }

    public static boolean isElementVisible(WebDriver webDriver, String xpath, long timeout){
        return isVisible(webDriver, xpath, timeout) && isElementDimensionsPositive(webDriver, xpath);
    }

    public static boolean isElementDimensionsPositive(WebDriver webDriver, String xpath){
        int width = getElementWidth(webDriver, xpath);
        int height = getElementHeight(webDriver, xpath);
        return width > 0 && height > 0;
    }

    public static void mouseOver(String xpath){
        mouseOver(WebDriverFactory.getPortalDriver(), xpath);
    }

	public static void mouseOver(WebDriver webDriver, String xpath){
        mouseOver(webDriver, getElement(webDriver, xpath));
    }

    private static void mouseOver(WebDriver webDriver, WebElement webElement){
        getAction(webDriver).moveToElement(webElement).perform();
    }

	//Input field

    public static void clearAndInputTextToField(String xpath, String text){
        clearAndInputTextToField(WebDriverFactory.getPortalDriver(), xpath, text);
    }

    public static void clearAndInputTextToField(WebDriver webDriver, String xpath, String text){
        clearAndInputTextToField(webDriver, xpath, text, 0);
    }

    public static void clearAndInputTextToField(String xpath, String text, int sleepTimeMillisec){
        clearAndInputTextToField(WebDriverFactory.getPortalDriver(), xpath, text, sleepTimeMillisec);
    }

    public static void clearAndInputTextToField(WebDriver webDriver, String xpath, String text, int sleepTimeMillisec){
        try{
            if(sleepTimeMillisec != 0){
                clearField(webDriver, xpath);
                inputTextToField(webDriver, xpath, text, sleepTimeMillisec);
            }else{
                clearField(webDriver, xpath);
                inputTextToField(webDriver, xpath, text);
            }
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element: " + xpath);
        }
    }

    protected static void clearField(WebDriver webDriver, String xpath){
        try{
            getElement(webDriver, xpath).clear();
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element: " + xpath);
        }
    }

    public static String getInputFieldText(String xpath){
        return getInputFieldText(WebDriverFactory.getPortalDriver(), xpath);
    }

	public static String getInputFieldText(WebDriver webDriver, String xpath){
    	return getElement(webDriver, xpath).getAttribute("value");
	}

    public static void inputTextToField(String xpath, String text){
        inputTextToField(WebDriverFactory.getPortalDriver(), xpath, text);
    }

	public static void inputTextToField(WebDriver webDriver, String xpath, String text){
        System.out.println("Inputting "+text+" ro field "+xpath);
        try{
			getElement(webDriver, xpath).sendKeys(text);
        }catch(NoSuchElementException e){
			AbstractTest.failTest("Could not find element: " + xpath);
		}
    }

    public static void inputTextToField(WebDriver webDriver, String xpath, String text, int sleepTimeMillisec){
		for(int i=0; i < text.length(); i++){
			try{
				getElement(webDriver, xpath).sendKeys(text.charAt(i) + "");
				Thread.sleep(sleepTimeMillisec);
			}catch(InterruptedException e){
				AbstractTest.failTest("Sleep failed");
			}
		}
	}

    public static boolean isEditable(String xpath) {
        return isEditable(WebDriverFactory.getPortalDriver(), xpath);
    }

    public static boolean isEditable(WebDriver webDriver, String xpath) {
        String baseText = getInputFieldText(webDriver, xpath);
        String text = "editable";
        clearAndInputTextToField(webDriver, xpath, text);
        String editedText = getInputFieldText(webDriver, xpath);
        if (editedText.equals(text)) {
            clearAndInputTextToField(webDriver, xpath, baseText);
            return true;
        } else {
            return false;
        }
    }

    public static boolean isEditableNew(String xpath) {
        return WebDriverFactory.getPortalDriver().findElement(By.xpath(xpath)).isEnabled();
    }

    public static void pressKey(Keys key) {
        pressKey(WebDriverFactory.getPortalDriver(), key);
    }

	public static void pressKey(WebDriver webDriver, Keys key){
        System.out.println("Pressing key"+key);
        getAction(webDriver).sendKeys(key).perform();
	}

	//Checkbox element

    public static boolean getCheckBoxState(String xpath){
        return getCheckBoxState(WebDriverFactory.getPortalDriver(), xpath);
    }

	public static boolean getCheckBoxState(WebDriver webDriver, String xpath){
		try{
			return getElement(webDriver, xpath).isSelected();
		}catch(NoSuchElementException e){
			AbstractTest.failTest("Could not find element: " + xpath);
		}
		return false;
	}

    public static void setCheckBoxState(String xpath, boolean desiredState){
        setCheckBoxState(WebDriverFactory.getPortalDriver(), xpath, desiredState);
    }

	public static void setCheckBoxState(WebDriver webDriver, String xpath, boolean desiredState){
		try{
			if(getCheckBoxState(webDriver, xpath)!= desiredState){
                click(webDriver, xpath);
			}
		}catch(NoSuchElementException e){
			AbstractTest.failTest("Could not find element: " + xpath);
		}
	}

	public static void setCheckBoxState(String id, String attribute, boolean value){
		AbstractTest.failTest("$('" + id + "')." + attribute + " = " + value + "");
	}

	//Dropdown

    private static Select createDropdown(WebDriver webDriver, String xpath){
        return new Select(getElement(webDriver, xpath));
    }

    private static WebElement getDropdownLastOption(WebDriver webDriver, String xpath){
        WebElement option=null;
        try{
            List<WebElement> options=getDropdownOptions(webDriver, xpath);
            int lastIndex=options.size() - 1;
            option=options.get(lastIndex);
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element: " + xpath);
        }
        return option;
    }

    public static void setDropdownOptionByText(String xpath, String value){
        setDropdownOptionByText(WebDriverFactory.getPortalDriver(), xpath, value);
    }

    public static void setDropdownOptionByText(WebDriver webDriver, String xpath, String value){
        try{
            Select dropdown= createDropdown(webDriver, xpath);
            dropdown.selectByVisibleText(value);
        }catch(NoSuchElementException e){
            AbstractTest.failTest(e.getMessage() + " on web element " + xpath);
        }
    }

    public static void setDropdownOptionByIndex(String xpath, int index){
        setDropdownOptionByIndex(WebDriverFactory.getPortalDriver(), xpath, index);
    }

    public static void setDropdownOptionByIndex(WebDriver webDriver, String xpath, int index){
        try{
            Select dropdown= createDropdown(webDriver, xpath);
            dropdown.selectByIndex(index);
        }catch(NoSuchElementException e){
            AbstractTest.failTest(e.getMessage() + " on web element " + xpath);
        }
    }

    public static void setDropdownOptionByValue(String xpath, String value){
        setDropdownOptionByValue(WebDriverFactory.getPortalDriver(), xpath, value);
    }

    public static void setDropdownOptionByValue(WebDriver webDriver, String xpath, String value){
        try{
            Select dropdown=createDropdown(webDriver, xpath);
            dropdown.selectByValue(value);
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element " + xpath + " with value \"" + value + "\"");
        }
    }

    private static List<WebElement> getDropdownOptions(WebDriver webDriver, String xpath){
        List<WebElement> options=null;
        try{
            Select dropdown=createDropdown(webDriver, xpath);
            options=dropdown.getOptions();
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element: " + xpath);
        }
        return options;
    }

    public static List<String> getDropdownOptionsText(String xpath){
        return getDropdownOptionsText(WebDriverFactory.getPortalDriver(), xpath);
    }

    public static List<String> getDropdownOptionsText(WebDriver webDriver, String xpath){
        List<String> optionsText=null;
        try{
            List<WebElement> options = getDropdownOptions(webDriver, xpath);
            optionsText= new ArrayList<String>();
            for(WebElement option : options){
                optionsText.add(option.getText());
            }
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element: " + xpath);
        }
        return optionsText;
    }

    public static List<String> getDropdownOptionsValue(String xpath) {
        return getDropdownOptionsValue(WebDriverFactory.getPortalDriver(), xpath);
    }

    public static List<String> getDropdownOptionsValue(WebDriver webDriver, String xpath){
        List<String> optionsValue =null;
        try{
            List<WebElement> options=getDropdownOptions(webDriver, xpath);
            optionsValue = new ArrayList<String>();
            for(WebElement option : options){
                optionsValue.add(getElementValue(option));
            }
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element: " + xpath);
        }
        return optionsValue;
    }

	private static WebElement getDropdownSelectedOption(WebDriver webDriver, String xpath){
		WebElement option=null;
		try{
			Select dropdown=createDropdown(webDriver, xpath);
			option=dropdown.getFirstSelectedOption();
		}catch(NoSuchElementException e){
			AbstractTest.failTest("Could not find element: " + xpath);
		}
		return option;
	}

    public static String getDropdownSelectedOptionText(String xpath){
        return getDropdownSelectedOptionText(WebDriverFactory.getPortalDriver(), xpath);
    }
	public static String getDropdownSelectedOptionText(WebDriver webDriver, String xpath){
		try{
			return getDropdownSelectedOption(webDriver, xpath).getText().trim();
		}catch(NoSuchElementException e){
			AbstractTest.failTest("Could not find element: " + xpath);
		}
		return null;
	}

    public static String getDropdownSelectedOptionValue(String xpath){
        return getDropdownSelectedOptionValue(WebDriverFactory.getPortalDriver(), xpath);
    }

    public static String getDropdownSelectedOptionValue(WebDriver webDriver, String xpath){
        try{
            return getDropdownSelectedOption(webDriver, xpath).getAttribute("value").replace("[","").replace("]","").replace(",","").replace(" ","");
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element: " + xpath);
        }
        return null;
    }

    public static int getDropdownItemsAmount(String xpath){
        return getDropdownOptionsValue(xpath).size();
    }

    //Custom dropdown

    public static void setCustomDropdownOptionByText(WebDriver webDriver, String xpath, String text){
        try{
            String list = openCustomDropdown(webDriver, xpath);
            click(webDriver, list+"//li[contains(text(), '"+text+"')]");
            waitForElementToDisappear(webDriver, list);
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element " + xpath + " with text '" + text + "'");
        }
    }

    public static void setCustomDropdownOptionByValue(String xpath, String value){
        setCustomDropdownOptionByValue(WebDriverFactory.getPortalDriver(), xpath, value);
    }
    public static void setCustomDropdownOptionByValue(WebDriver webDriver, String xpath, String value){
        try{
            String list = openCustomDropdown(webDriver, xpath);
            click(webDriver, list+"//li[@data-lang ='"+value+"']");
            waitForElementToDisappear(webDriver, list);
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element " + xpath + " with value '" + value + "'");
        }
    }

    public static List<String> getCustomDropdownOptionsText(WebDriver webDriver, String xpath){
        List<String> optionsText= new ArrayList<>();
        try{
            for(WebElement option:getCustomDropdownOptions(webDriver, xpath)){
                optionsText.add(option.getText());
            }
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element: " + xpath);
        }
        return optionsText;
    }

    public static List<String> getCustomDropdownOptionsValue(String xpath){
        return getCustomDropdownOptionsValue(WebDriverFactory.getPortalDriver(), xpath);
    }

    public static List<String> getCustomDropdownOptionsValue(WebDriver webDriver, String xpath){
        List<String> optionsValue = new ArrayList<>();
        try{
            for(WebElement option:getCustomDropdownOptions(webDriver, xpath)){
                optionsValue.add(option.getAttribute("data-lang"));
            }
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element: " + xpath);
        }
        return optionsValue;
    }

    public static String getCustomDropdownSelectedOptionText(WebDriver webDriver, String xpath){
        try{
            return getCustomDropdownSelectedOption(webDriver, xpath).getText();
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element: " + xpath);
        }
        return null;
    }

    public static String getCustomDropdownSelectedOptionValue(String xpath){
        return getCustomDropdownSelectedOptionValue(WebDriverFactory.getPortalDriver(), xpath);
    }

    public static String getCustomDropdownSelectedOptionValue(WebDriver webDriver, String xpath){
        try{
            WebElement option = getCustomDropdownSelectedOption(webDriver, xpath);
            if(DataContainer.getDriverData().getLicensee().equals(Licensee.core)){
                return option.getAttribute("data-lang");
            }else {
                return option.getAttribute("class").split(" ")[1];
            }
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element: " + xpath);
        }
        return null;
    }

    private static WebElement getCustomDropdownSelectedOption(WebDriver webDriver, String xpath){
        try{
            if(DataContainer.getDriverData().getLicensee().equals(Licensee.core)){
                String list = getFollowingElement(xpath);
                return getElement(webDriver, list+"//li[contains(@class, 'selected')]");
            }else {
                return getElement(webDriver, xpath+"//span");
            }
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element: " + xpath);
        }
        return null;
    }

    public static String openCustomDropdown(String xpath){
        return openCustomDropdown(WebDriverFactory.getPortalDriver(), xpath);
    }

    public static String openCustomDropdown(WebDriver webDriver, String xpath){
        String list = getFollowingElement(xpath);
        click(webDriver, xpath);
        waitForElement(webDriver, list);
        return list;
    }

    private static List<WebElement> getCustomDropdownOptions(WebDriver webDriver, String xpath){
        String list = getFollowingElement(xpath);
        return getElementsByXpath(webDriver, list + "//li");
    }

    private static List<WebElement> getElementsByXpath(WebDriver webDriver, String xpath) {
        return webDriver.findElements(By.xpath(xpath));
    }

    public static List<WebElement> getElementsByClassName(String className) {
        return getElementsByClassName(WebDriverFactory.getPortalDriver(), className);
    }

    private static List<WebElement> getElementsByClassName(WebDriver webDriver, String className) {
        return webDriver.findElements(By.className(className));
    }

    //List

    public static List<String> getListOfAttributeValues(WebDriver webDriver, String xpath, String attributeName){
        List<String> result = new ArrayList();
        String xpathItems = xpath + "//ul/li";
        for (int itemIndex=1; itemIndex <= getXpathCount(webDriver, xpathItems); itemIndex++) {
            result.add(getAttribute(webDriver, xpathItems + "[" + itemIndex + "]", attributeName));
        }
        return result;
    }

    //Cookie

    public static String getCookieValue(WebDriver webDriver, String name) {
        return webDriver.manage().getCookieNamed(name).getValue();
    }

    public static void clearCookies(){
        clearCookies(WebDriverFactory.getPortalDriver());
    }

    public static void clearCookies(WebDriver webDriver){
        webDriver.manage().deleteAllCookies();
    }

    public static void addCookie(WebDriver webDriver, String name, String value, String domain, String path, Date expiry){
        Cookie cookie = new Cookie(name, value, domain, path, expiry);
        webDriver.manage().addCookie(cookie);
    }


    public static Cookie getCookie(WebDriver webDriver, String cookieName) {
        return webDriver.manage().getCookieNamed(cookieName);
    }

    public static boolean isCookieExists(WebDriver webDriver, String cookieName) {
        return getCookie(webDriver, cookieName) != null;
    }

    public static void deleteCookie(WebDriver webDriver, String cookieName) {
        webDriver.manage().deleteCookieNamed(cookieName);
    }

	//Navigation

    public static String getCurrentUrl(){
        return getCurrentUrl(WebDriverFactory.getPortalDriver());
    }

	public static String getCurrentUrl(WebDriver webDriver){
		return webDriver.getCurrentUrl();
	}

    public static String getCurrentLanguageCode(){
        String leftover = getCurrentUrl(WebDriverFactory.getPortalDriver()).replace(DataContainer.getDriverData().getCurrentUrl(), "");
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

    public static void navigateToInternalURL(String relativeURL) {
        navigateToInternalURL(WebDriverFactory.getPortalDriver(), relativeURL);
    }

	public static void navigateToInternalURL(WebDriver webDriver, String relativeURL){
		String url=DataContainer.getDriverData().getCurrentUrl() + relativeURL;
        navigateToURL(webDriver, url);
	}

	public static void navigateToInternalURL(WebDriver webDriver, String baseUrl, String relativeURL){
		String url=baseUrl + relativeURL;
        navigateToURL(webDriver, url);
	}

    public static void navigateToURL(String url){
        navigateToURL(WebDriverFactory.getPortalDriver(), url);
    }

	public static void navigateToURL(WebDriver webDriver, String url){
        System.out.println("Navigating to url "+url );
        webDriver.get(url);
	}

    public static void refreshPage(){
        System.out.println("Refreshing page");
        refreshPage(WebDriverFactory.getPortalDriver());
        waitForPageToLoad();
    }

    public static void refreshPage(WebDriver webDriver){
        webDriver.navigate().refresh();
    }

	//WindowHandling

    public static void closeCurrentWindow(WebDriver webDriver){
        webDriver.close();
    }

	public static String getWindowHandle(WebDriver webDriver){
		return webDriver.getWindowHandle();
	}

    public static Set<String> getWindowHandles(WebDriver webDriver){
        return webDriver.getWindowHandles();
    }

	public static void switchToOtherWindow(WebDriver webDriver, String mainWindowHandle){
		for(String winHandle : getWindowHandles(webDriver)){
			if(!winHandle.equals(mainWindowHandle)){
				switchToWindow(webDriver, winHandle);
			}
		}
	}

	public static void switchToWindow(WebDriver webDriver, String handle){
        System.out.println("Switching to window " + handle);
		webDriver.switchTo().window(handle);
	}

    public static boolean isGameLaunched(ConfiguredPages page){
        if(getWindowHandles(WebDriverFactory.getPortalDriver()).size() == 1 && getCurrentUrl(WebDriverFactory.getPortalDriver()).contains(page.toString())){
            return false;
        }else {
            return true;
        }
    }

	//iFrame

    public static void switchFromIframe(WebDriver webDriver){
        System.out.println("Switching from iframe");
        webDriver.switchTo().defaultContent();
    }

	public static void switchToIframeById(WebDriver webDriver, String iframeId){
        System.out.println("Switching to iframe "+iframeId);
        webDriver.switchTo().frame(iframeId);
	}

    public static void switchToIframeByXpath(WebDriver webDriver, String iframeXpath){
        System.out.println("Switching to iframe "+iframeXpath);
        webDriver.switchTo().frame(getElement(webDriver, iframeXpath));
    }

	//Script

    public static void acceptJavaScriptAlert(WebDriver webDriver){
        Alert alert = webDriver.switchTo().alert();
        alert.accept();
    }

    public static void executeScript(String javascript){
        executeScript(WebDriverFactory.getPortalDriver(), javascript);
    }

	public static void executeScript(WebDriver webDriver, String javascript){
        System.out.println("Executing JS "+javascript);
        ((JavascriptExecutor) webDriver).executeScript(javascript);
	}

    //Mobile

    public static void setOrientation(ScreenOrientation screenOrientation){
        SelendroidDriver selendroidDriver = (SelendroidDriver)WebDriverFactory.getPortalDriver();
        selendroidDriver.rotate(screenOrientation);
        waitFor(1000);
    }

    public static ScreenOrientation getOrientation(){
        SelendroidDriver selendroidDriver = (SelendroidDriver)WebDriverFactory.getPortalDriver();
        return selendroidDriver.getOrientation();
    }

    public static String getDomain() {
        String baseUrl = DataContainer.getDriverData().getCurrentUrl();
        int firstDotIndex = baseUrl.indexOf(".");
        return baseUrl.substring(firstDotIndex).replace("/", "").replace(":8080", "");
    }

    private static WebElement getElement(WebDriver webDriver, String xpath) {
        System.out.println("Getting element by xpath "+xpath);
        try{
            return webDriver.findElement(By.xpath(xpath));
        }catch(NoSuchElementException e){
            AbstractTest.failTest("Could not find element: " + xpath);
        }
        return null;
    }

    //Xpath actions

    public static int getXpathCount(String xpath){
        return getXpathCount(WebDriverFactory.getPortalDriver(), xpath);
    }

    public static int getXpathCount(WebDriver webDriver, String xpath){
        return getElementsByXpath(webDriver, xpath).size();
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

    public static void paste(String xpath) {
        paste(WebDriverFactory.getPortalDriver(), xpath);
    }

    public static void paste(WebDriver webDriver, String xpath) {
        getElement(webDriver, xpath).sendKeys(Keys.chord(Keys.CONTROL, "v"));
    }

    public static void copy(String xpath) {
        copy(WebDriverFactory.getPortalDriver(), xpath);
    }

    public static void copy(WebDriver webDriver, String xpath) {
        WebElement element = getElement(webDriver, xpath);
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.chord(Keys.CONTROL, "c"));
    }

    /*Local storage*/

    private static LocalStorage getLocalStorage(WebDriver webDriver) {
        return ((WebStorage) webDriver).getLocalStorage();
    }

    public static int getLocalStorageSize(WebDriver webDriver) {
        return getLocalStorage(webDriver).size();
    }

    public static String getLocalStorageItem(WebDriver webDriver, String key) {
        return getLocalStorage(webDriver).getItem(key);
    }

    public static void setLocalStorageItem(WebDriver webDriver, String key, String value) {
        getLocalStorage(webDriver).setItem(key, value);
    }

    public static void removeLocalStorageItem(WebDriver webDriver, String key) {
        getLocalStorage(webDriver).removeItem(key);
    }

    public static void clearLocalStorage(WebDriver webDriver) {
        getLocalStorage(webDriver).clear();
    }

    public static void clearLocalStorage() {
        executeScript("window.localStorage.clear();");
//        getLocalStorage(WebDriverFactory.getPortalDriver()).clear();
    }

}
