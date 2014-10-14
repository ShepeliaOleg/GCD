package utils.cookie;

import springConstructors.AffiliateData;
import utils.core.AbstractTest;

public class AffiliateCookie extends Cookie {
    private static final String name = "banner_domainclick";

    public AffiliateCookie(AffiliateData affiliateData) {
        super(name, affiliateData.getAdvertiser()+AffiliateData.COMMA+affiliateData.getProfile()+AffiliateData.COMMA+affiliateData.getBanner()+AffiliateData.COMMA+affiliateData.getUrl()+AffiliateData.COMMA+affiliateData.getCreferrer());
    }

    public AffiliateCookie(String value) {
        super(name, value);
    }

    public void validateValue(AffiliateData affiliateData) {
        String actualCookieValue = getValue();
        String expectedCookieValue = affiliateData.getValue();
        AbstractTest.assertEquals(expectedCookieValue, actualCookieValue, "Incorrect affiliate data was passed to cookie value from url parameters.");
    }
}
