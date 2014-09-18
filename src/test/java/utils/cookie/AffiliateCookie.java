package utils.cookie;

import springConstructors.AffiliateData;

public class AffiliateCookie extends Cookie {
    private static final String name = "banner_domainclick";

    public AffiliateCookie(AffiliateData affiliateData) {
        super(name, affiliateData.getAdvertiser()+","+affiliateData.getProfile()+","+affiliateData.getBanner()+","+affiliateData.getUrl()+","+affiliateData.getCreferer());
    }
}
