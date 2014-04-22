package pageObjects.external.ims;

import pageObjects.base.AbstractPage;

/**
 * User: sergiich
 * Date: 8/16/13
 */

public class IMSTermsAndConditionsPage extends AbstractPage{

	private static final String SEARCH_IFRAME_ID="main-content";
    private static final String SEARCH_IFRAME_XP="//*[@id='main-content']";

	public IMSTermsAndConditionsPage(){
		super(new String[]{SEARCH_IFRAME_XP});
	}

	public IMSTermsAndConditionsIframe navigateToSearchIframe(){
		return new IMSTermsAndConditionsIframe(SEARCH_IFRAME_ID);
	}
}
