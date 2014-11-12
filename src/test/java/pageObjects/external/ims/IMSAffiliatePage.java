package pageObjects.external.ims;

import pageObjects.core.AbstractServerPage;

public class IMSAffiliatePage extends AbstractServerPage {

    public static final String SEARCH_IFRAME_ID="main-content";
    public static final String SEARCH_IFRAME_XP="//*[@id='main-content']";

    public IMSAffiliatePage(){
        super(new String[]{SEARCH_IFRAME_XP});
    }

    public IMSAffiliateIframe navigateToAffiliateIframe(){
        return new IMSAffiliateIframe(SEARCH_IFRAME_ID);
    }
}
