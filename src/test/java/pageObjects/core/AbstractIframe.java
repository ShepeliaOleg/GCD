package pageObjects.core;

import org.openqa.selenium.WebDriver;

public abstract class AbstractIframe extends AbstractPageObject{

	private static final String ERROR_XP="//*[contains(text(), '404')]";

	public AbstractIframe(WebDriver webDriver, String iframeId){
		super(webDriver, null, new String[]{ERROR_XP}, iframeId);
	}
}
