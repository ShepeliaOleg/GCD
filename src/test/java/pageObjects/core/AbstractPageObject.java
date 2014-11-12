package pageObjects.core;

import org.openqa.selenium.WebDriver;
import utils.WebDriverUtils;
import utils.core.AbstractTest;
import utils.core.WebDriverFactory;

public abstract class AbstractPageObject{

    protected static final String PLACEHOLDER =     "$PLACEHOLDER$";
    private static final int TIMEOUT =              60;

	public AbstractPageObject(WebDriver webDriver, String mainWindowHandle){
		WebDriverUtils.switchToOtherWindow(webDriver, mainWindowHandle);
		validate(webDriver, null, null);
	}

	public AbstractPageObject(WebDriver webDriver, String mainWindowHandle, String[] clickableBys){
		WebDriverUtils.switchToOtherWindow(webDriver, mainWindowHandle);
		validate(webDriver, clickableBys, null);
	}

	public AbstractPageObject(WebDriver webDriver, String[] clickableBys){
		this(webDriver, clickableBys, null);
	}

	public AbstractPageObject(WebDriver webDriver, String[] clickableBys, String[] invisibleBys){
		validate(webDriver, clickableBys, invisibleBys);
	}

	public AbstractPageObject(WebDriver webDriver, String[] clickableBys, String[] invisibleBys, String iframeId){
		WebDriverUtils.switchToIframeById(webDriver, iframeId);
		validate(webDriver, clickableBys, invisibleBys);
	}

    private void validate(WebDriver webDriver, String[] clickableBys, String[] invisibleBys){
        if(clickableBys!=null){
            for(String xpath:clickableBys){
                WebDriverUtils.waitForElement(webDriver, xpath, TIMEOUT);
            }
        }
        if(invisibleBys!=null){
            for(String xpath:invisibleBys){
                WebDriverUtils.waitForElementToDisappear(webDriver, xpath,TIMEOUT);
            }
        }
    }

    protected void validate(WebDriver webDriver, String[] clickableBys, String[] invisibleBys, String name){
        try{
            validate(webDriver, clickableBys, invisibleBys);
        }catch (Exception e){
            AbstractTest.failTest(name + " did not load");
        }
    }

//   methods overridden in child classes
    public String getTranslationText() {
        return null;
    }

    public void copyAndPasteEmail(){

    }
}



