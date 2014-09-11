package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.core.CustomExpectedConditions;
import utils.core.WebDriverFactory;
import utils.core.WebDriverObject;
import utils.logs.Log;
import utils.logs.LogUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
                runtimeExceptionWithUrl("Sleep failed");
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
			runtimeExceptionWithUrl("Element: " + xpath + "<div>Did not appear after: " + timeout + " s</div>");
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
			runtimeExceptionWithUrl("Element: " + xpath + "<div>Did not disappear after: " + timeout + " s</div>");
		}
	}

	public static void waitForPageToLoad(){
		WebDriverWait wait=new WebDriverWait(webDriver, 30);
		try{
			wait.until(CustomExpectedConditions.pageLoadComplete());
		} catch(TimeoutException e) {
			runtimeExceptionWithUrl("Page didn't load in 30 sec");
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
            runtimeExceptionWithUrl("Number of elements did not become: " + number + "<div>In " + timeout + " ms</div>");
        }
    }

    //Element actions

    public static void click(String xpath){
        try {
            webDriver.findElement(By.xpath(xpath)).click();
        }catch (WebDriverException e){
            runtimeExceptionWithUrl("Could not click element by xpath: " + xpath);
        }
    }

    public static String getAttribute(String xpath, String attribute){
        try{
            return webDriver.findElement(By.xpath(xpath)).getAttribute(attribute);
        }catch(NoSuchElementException e){
            runtimeExceptionWithUrl("Could not find element by Xpath: " + xpath);
        }
        return null;
    }

    public static int getElementHeight(String xpath){
        try{
            return webDriver.findElement(By.xpath(xpath)).getSize().getHeight();
        }catch(NoSuchElementException e){
            runtimeExceptionWithUrl("Could not find element: " + xpath);
        }
        return 0;
    }

    public static int getElementWidth(String xpath){
        try{
            return webDriver.findElement(By.xpath(xpath)).getSize().getWidth();
        }catch(NoSuchElementException e){
            runtimeExceptionWithUrl("Could not find element: " + xpath);
        }
        return 0;
    }

    public static String getElementText(String xpath){
        try{
            return webDriver.findElement(By.xpath(xpath)).getText();
        }catch(NoSuchElementException e){
            runtimeExceptionWithUrl("Could not find element by Xpath: " + xpath);
        }
        return null;
    }

    public static String getElementValue(WebElement webElement){
        return webElement.getAttribute("value");
    }

    public static String getElementText(WebDriver webDriver, String xpath){
        try{
            return webDriver.findElement(By.xpath(xpath)).getText();
        }catch(NoSuchElementException e){
            runtimeExceptionWithUrl("Could not find element by Xpath: " + xpath);
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
			actions.moveToElement(webDriver.findElement(By.xpath(xpath))).perform();
		}catch(NoSuchElementException e){
			runtimeExceptionWithUrl("Could not find element by Xpath: " + xpath);
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
            runtimeExceptionWithUrl("Could not find element: " + xpath);
        }
    }

    protected static void clearField(String xpath){
        try{
            webDriver.findElement(By.xpath(xpath)).clear();
        }catch(NoSuchElementException e){
            runtimeExceptionWithUrl("Could not find element: " + xpath);
        }
    }

	public static String getInputFieldText(String xpath){
		try{
			return webDriver.findElement(By.xpath(xpath)).getAttribute("value");
		}catch(NoSuchElementException e){
			runtimeExceptionWithUrl("Could not find element: " + xpath);
		}
		return null;
	}

	public static void inputTextToField(String xpath, String text){
		try{
			webDriver.findElement(By.xpath(xpath)).sendKeys(text);
		}catch(NoSuchElementException e){
			runtimeExceptionWithUrl("Could not find element: " + webDriver.findElement(By.xpath(xpath)));
		}
	}

	public static void inputTextToField(String xpath, String text, int sleepTimeMillisec){
		for(int i=0; i < text.length(); i++){
			try{
				webDriver.findElement(By.xpath(xpath)).sendKeys(text.charAt(i) + "");
				Thread.sleep(sleepTimeMillisec);
			}catch(InterruptedException e){
				runtimeExceptionWithUrl("Sleep failed");
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
			return webDriver.findElement(By.xpath(xpath)).isSelected();
		}catch(NoSuchElementException e){
			runtimeExceptionWithUrl("Could not find element: " + xpath);
		}
		return false;
	}

	public static void setCheckBoxState(String xpath, boolean desiredState){
		try{
			if(getCheckBoxState(xpath)!= desiredState){
				webDriver.findElement(By.xpath(xpath)).click();
			}
		}catch(NoSuchElementException e){
			runtimeExceptionWithUrl("Could not find element: " + xpath);
		}
	}

	public static void setCheckBoxState(String id, String attribute, boolean value){
		runtimeExceptionWithUrl("$('" + id + "')." + attribute + " = " + value + "");
	}

	//Dropdown

    private static Select createDropdown(String xpath){
        return new Select(webDriver.findElement(By.xpath(xpath)));
    }

    public static WebElement getDropdownLastOption(String xpath){
        WebElement option=null;
        try{
            List<WebElement> options=getDropdownOptions(xpath);
            int lastIndex=options.size() - 1;
            option=options.get(lastIndex);
        }catch(NoSuchElementException e){
            runtimeExceptionWithUrl("Could not find element: " + xpath);
        }
        return option;
    }

    public static void setDropdownOptionByText(String xpath, String value){
        try{
            Select dropdown= createDropdown(xpath);
            dropdown.selectByVisibleText(value);
        }catch(NoSuchElementException e){
            runtimeExceptionWithUrl(e.getMessage() + " on web element " + xpath);
        }
    }

    public static void setDropdownOptionByValue(String xpath, String value){
        try{
            Select dropdown=createDropdown(xpath);
            dropdown.selectByValue(value);
        }catch(NoSuchElementException e){
            runtimeExceptionWithUrl("Could not find element " + xpath + " with value \"" + value + "\"");
        }
    }

    public static List<WebElement> getDropdownOptions(String xpath){
        List<WebElement> options=null;
        try{
            Select dropdown=createDropdown(xpath);
            options=dropdown.getOptions();
        }catch(NoSuchElementException e){
            runtimeExceptionWithUrl("Could not find element: " + xpath);
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
            runtimeExceptionWithUrl("Could not find element: " + xpath);
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
            runtimeExceptionWithUrl("Could not find element: " + xpath);
        }
        return optionsValue;
    }

	public static WebElement getDropdownSelectedOption(String xpath){
		WebElement option=null;
		try{
			Select dropdown=createDropdown(xpath);
			option=dropdown.getFirstSelectedOption();
		}catch(NoSuchElementException e){
			runtimeExceptionWithUrl("Could not find element: " + xpath);
		}
		return option;
	}

	public static String getDropdownSelectedOptionText(String xpath){
		try{
			return getDropdownSelectedOption(xpath).getText();
		}catch(NoSuchElementException e){
			runtimeExceptionWithUrl("Could not find element: " + xpath);
		}
		return null;
	}

	//Navigation

    public static void clearCookies(){
        webDriver.manage().deleteAllCookies();
    }

    public static void addCookie(String name, String value, String domain, String path, Date expiry){
        Cookie cookie = new Cookie(name, value, domain, path, expiry);
        webDriver.manage().addCookie(cookie);
    }


	public static String getCurrentUrl(){
		return webDriver.getCurrentUrl();
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

    public static void openAdditionalSession(){
        WebDriverFactory webDriverFactory = new WebDriverFactory();
        webDriverFactory.switchToAdditionalWebDriver();
    }

	public static void switchToPopup(String mainWindowHandle){
		for(String winHandle : webDriver.getWindowHandles()){
			if(!winHandle.equals(mainWindowHandle)){
				webDriver.switchTo().window(winHandle);
			}
		}
	}

	public static void switchToWindow(String handle){
		webDriver.switchTo().window(handle);
	}

	//iFrame

    public static void switchFromIframe(){
        webDriver.switchTo().defaultContent();
    }

	public static void switchToIframeById(String iframeId){
		webDriver.switchTo().frame(iframeId);
	}

    public static void switchToIframeByXpath(String iframeXpath){
        webDriver.switchTo().frame(webDriver.findElement(By.xpath(iframeXpath)));
    }

	//Exceptions

	public static void runtimeExceptionWithUrl(String exceptionMessage){
		throw new RuntimeException(exceptionMessage + getUrlAndLogs());
	}

    public static String getUrlAndLogs(){
        Log currentLogs = new Log("Log page is unavailable");
        if(!timestamp.equals("noLogs")){
            currentLogs = LogUtils.getCurrentLogs();
        }
        return "<div>URL: "+webDriver.getCurrentUrl()+"</div> %$%" + currentLogs.print();
    }

	//Script

    public static void acceptJavaScriptAlert(){
        Alert alert = webDriver.switchTo().alert();
        alert.accept();
    }

	public static void executeScript(String javascript){
		((JavascriptExecutor) webDriver).executeScript(javascript);
	}
}
