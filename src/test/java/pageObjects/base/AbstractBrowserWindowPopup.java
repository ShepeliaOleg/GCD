package pageObjects.base;

/**
 * User: sergiich
 * Date: 7/31/13
 */

public class AbstractBrowserWindowPopup extends AbstractPageObject{

	public AbstractBrowserWindowPopup(String mainWindowHandle){
		super(mainWindowHandle);
	}

	public AbstractBrowserWindowPopup(String mainWindowHandle, String[] clickableBys){
		super(mainWindowHandle, clickableBys);
	}
}
