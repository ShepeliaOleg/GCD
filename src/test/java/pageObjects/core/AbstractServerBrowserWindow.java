package pageObjects.core;

import utils.core.WebDriverFactory;

public class AbstractServerBrowserWindow extends AbstractBrowserWindow{

    public AbstractServerBrowserWindow(String mainWindowHandle){
		super(WebDriverFactory.getServerDriver(), mainWindowHandle);
    }

	public AbstractServerBrowserWindow(String mainWindowHandle, String[] clickableBys){
		super(WebDriverFactory.getServerDriver(), mainWindowHandle, clickableBys);
	}

    public String getWindowUrl(){
        return getWindowUrl(WebDriverFactory.getServerDriver());
    }

    public void close(){
        close(WebDriverFactory.getServerDriver());
    }
}
