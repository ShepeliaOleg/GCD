package utils.cookie;

import springConstructors.AffiliateData;
import utils.core.AbstractTest;
import utils.core.WebDriverFactory;

public class AffiliateCookie extends Cookie {
    private static final String name = "banner_domainclick";

    public AffiliateCookie(AffiliateData affiliateData) {
        super(name, affiliateData.getAdvertiser()+AffiliateData.COMMA+affiliateData.getBanner()+AffiliateData.COMMA+affiliateData.getProfile()+AffiliateData.COMMA+affiliateData.getUrl()+AffiliateData.COMMA+affiliateData.getCreferrer());
    }

    public AffiliateCookie(String value) {
        super(name, value);
    }

    public void validateValue(AffiliateData affiliateData) {
        String actualCookieValue = getValue(WebDriverFactory.getPortalDriver());
        String expectedCookieValue = affiliateData.getValue();
        AbstractTest.assertEquals(expectedCookieValue, actualCookieValue, "Incorrect affiliate data was passed to cookie value from url parameters.");
    }

    public void add() {
        add(WebDriverFactory.getPortalDriver());
    }

    public boolean isPresent() {
        return isPresent(WebDriverFactory.getPortalDriver());
    }
}
