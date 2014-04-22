package pageObjects.external.ims;

import pageObjects.base.AbstractPage;

/**
 * User: sergiich
 * Date: 8/16/13
 */

public class IMSTermsAndConditionsSearchPage extends AbstractPage{

	public static final String SEARCH_IFRAME_ID="main-content";
    public static final String SEARCH_IFRAME_XP="//*[@id='main-content']";

	public IMSTermsAndConditionsSearchPage(){
		super(new String[]{SEARCH_IFRAME_XP});
	}

	public IMSTermsAndConditionsSearchIframe navigateToSearchIframe(){
		return new IMSTermsAndConditionsSearchIframe(SEARCH_IFRAME_ID);
	}


}
