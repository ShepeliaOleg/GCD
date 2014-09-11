package pageObjects.core;

import utils.WebDriverUtils;

public class AbstractBrowserWindow extends AbstractPageObject{
    private String mainWindowHandle;

    public AbstractBrowserWindow(String mainWindowHandle){
		super(mainWindowHandle);
        this.mainWindowHandle=mainWindowHandle;
	}

	public AbstractBrowserWindow(String mainWindowHandle, String[] clickableBys){
		super(mainWindowHandle, clickableBys);
	}

    public String getWindowUrl(){
        return WebDriverUtils.getCurrentUrl();
    }

    public String getMainWindowHandle(){
        return mainWindowHandle;
    }

    public void close(){
        WebDriverUtils.closeCurrentWindow();
        WebDriverUtils.switchToWindow(getMainWindowHandle());
    }
}
