package pageObjects.core;

import org.openqa.selenium.WebDriver;
import utils.WebDriverUtils;

public class AbstractBrowserWindow extends AbstractPageObject{
    private String mainWindowHandle;

    public AbstractBrowserWindow(WebDriver webDriver, String mainWindowHandle){
		super(webDriver, mainWindowHandle);
        this.mainWindowHandle=mainWindowHandle;
	}

	public AbstractBrowserWindow(WebDriver webDriver, String mainWindowHandle, String[] clickableBys){
		super(webDriver, mainWindowHandle, clickableBys);
	}

    public String getWindowUrl(WebDriver webDriver){
        return WebDriverUtils.getCurrentUrl(webDriver);
    }

    public String getMainWindowHandle(){
        return mainWindowHandle;
    }

    public void close(WebDriver webDriver){
        WebDriverUtils.closeCurrentWindow(webDriver);
        WebDriverUtils.switchToWindow(webDriver, getMainWindowHandle());
    }
}
