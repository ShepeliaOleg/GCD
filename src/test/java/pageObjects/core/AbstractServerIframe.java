package pageObjects.core;

import utils.core.WebDriverFactory;

public abstract class AbstractServerIframe extends AbstractIframe{

	public AbstractServerIframe(String iframeId){
		super(WebDriverFactory.getServerDriver(), iframeId);
	}
}
