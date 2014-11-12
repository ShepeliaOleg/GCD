package pageObjects.core;

import utils.core.WebDriverFactory;

public class AbstractPortalBrowserWindow extends AbstractBrowserWindow{

    public AbstractPortalBrowserWindow(String mainWindowHandle){
		super(WebDriverFactory.getPortalDriver(), mainWindowHandle);
    }

	public AbstractPortalBrowserWindow(String mainWindowHandle, String[] clickableBys){
		super(WebDriverFactory.getPortalDriver(), mainWindowHandle, clickableBys);
	}

    public String getWindowUrl(){
        return getWindowUrl(WebDriverFactory.getPortalDriver());
    }

    public void close(){
        close(WebDriverFactory.getPortalDriver());
    }
}
