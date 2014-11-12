package pageObjects.core;

import utils.core.WebDriverFactory;

public abstract class AbstractPortalIframe extends AbstractIframe{

	public AbstractPortalIframe(String iframeId){
		super(WebDriverFactory.getPortalDriver(), iframeId);
	}
}
