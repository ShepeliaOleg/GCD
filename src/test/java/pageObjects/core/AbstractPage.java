package pageObjects.core;

import org.openqa.selenium.WebDriver;
import utils.WebDriverUtils;

public class AbstractPage extends AbstractPageObject{

	private String mainWindowHandle;

	public AbstractPage(WebDriver webDriver, String[] clickableBys, String[] invisibleBys){
		super(webDriver, clickableBys, invisibleBys);
		this.mainWindowHandle=WebDriverUtils.getWindowHandle(webDriver);
	}

	public String getMainWindowHandle(){
		return mainWindowHandle;
	}

	public AbstractPage(WebDriver webDriver, String[] clickableBys){
		this(webDriver, clickableBys, null);
	}

	public AbstractPage(){
		this(null, null);
	}

}



