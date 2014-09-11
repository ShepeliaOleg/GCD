package pageObjects.external.ims;

import pageObjects.core.AbstractPage;

/**
 * Created by sergiich on 5/21/14.
 */
public class IMSAffiliatePage extends AbstractPage{

    public static final String SEARCH_IFRAME_ID="main-content";
    public static final String SEARCH_IFRAME_XP="//*[@id='main-content']";

    public IMSAffiliatePage(){
        super(new String[]{SEARCH_IFRAME_XP});
    }

    public IMSAffiliateIframe navigateToAffiliateIframe(){
        return new IMSAffiliateIframe(SEARCH_IFRAME_ID);
    }
}
