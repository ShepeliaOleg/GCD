package utils.cookie;

import springConstructors.AffiliateData;
import utils.core.AbstractTest;

public class AffiliateCookie extends Cookie {
    private static final String name = "banner_domainclick";

    public AffiliateCookie(AffiliateData affiliateData) {
        super(name, affiliateData.getAdvertiser()+AffiliateData.ASCII_CODE_COMMA+affiliateData.getProfile()+AffiliateData.ASCII_CODE_COMMA+affiliateData.getBanner()+AffiliateData.ASCII_CODE_COMMA+affiliateData.getUrl()+AffiliateData.ASCII_CODE_COMMA+affiliateData.getCreferrer());
    }

    public AffiliateCookie(String value) {
        super(name, value);
    }

    public void validateValue(AffiliateData affiliateData) {
        String actualCookieValue = getValue();
        String expectedCookieValue = affiliateData.getValue();
        AbstractTest.assertEquals(actualCookieValue, expectedCookieValue, "Incorrect affiliate data was passed to cookie value from url parameters.");
    }
}
