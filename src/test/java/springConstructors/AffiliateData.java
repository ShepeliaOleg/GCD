package springConstructors;

public class AffiliateData {
    private String defaultAdvertiser;
    private String advertiser;
    private String banner;
    private String profile;
    private String url;
    private String creferrer;
    private String creferrer1;
    private String creferrer2;
    private String creferrerRegistrationPortletProperty;

    public static final String ASCII_CODE_COMMA =      "%2C";    // ,
    public static final String ASCII_CODE_COLON =      "%3A";    // :
    public static final String ASCII_CODE_SEMICOLON =  "%3B";    // ;

    public AffiliateData(String defaultAdvertiser, String advertiser, String banner, String profile, String url, String creferrer1, String creferrer2, String creferrerRegistrationPortletProperty) {
        this.defaultAdvertiser = defaultAdvertiser;
        this.advertiser = advertiser;
        this.banner = banner;
        this.profile = profile;
        this.url = url;
        this.creferrer1 = creferrer1;
        this.creferrer2 = creferrer2;
        this.creferrerRegistrationPortletProperty = creferrerRegistrationPortletProperty;
    }

    public AffiliateData(String advertiser, String banner, String profile, String url, String creferrer) {
        this.advertiser = advertiser;
        this.banner = banner;
        this.profile = profile;
        this.url = url;
        this.creferrer = creferrer;
    }

    public String getDefaultAdvertiser() {
        return defaultAdvertiser;
    }

    public void setDefaultAdvertiser(String defaultAdvertiser) {
        this.defaultAdvertiser = defaultAdvertiser;
    }

    public String getAdvertiser() {
        return advertiser;
    }

    public void setAdvertiser(String advertiser) {
        this.advertiser = advertiser;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreferrer() {
        return creferrer;
    }

    public void setCreferrer(String creferrer) {
        this.creferrer = creferrer;
    }

    public String getCreferrer1() {
        return creferrer1;
    }

    public void setCreferrer1(String creferrer1) {
        this.creferrer1 = creferrer1;
    }

    public String getCreferrer2() {
        return creferrer2;
    }

    public void setCreferrer2(String creferrer2) {
        this.creferrer2 = creferrer2;
    }

    public String getCrefererSingle() {
        return creferrer1;
    }

    private String getCrefererMultiple() {
        return creferrer1 + ASCII_CODE_SEMICOLON + creferrer2;
    }

    public String getCreferrerRegistrationPortletProperty() {
        return creferrerRegistrationPortletProperty;
    }

    public void setCreferrerRegistrationPortletProperty(String creferrerRegistrationPortletProperty) {
        this.creferrerRegistrationPortletProperty = creferrerRegistrationPortletProperty;
    }

    public String getRelativeURL() {
        return "?advertiser=" + getAdvertiser() +  "&bannerid=" + getBanner() + "&profileid=" + getProfile() +"&refererurl=" + getUrl() + "&creferrer=" + getCreferrer();
    }

    public AffiliateData getAffiliateDataSingle() {
        return new AffiliateData(getAdvertiser(), getBanner(), getProfile(), getUrl(), getCrefererSingle());
    }


    public AffiliateData getAffiliateDataMultiple() {
        return new AffiliateData(getAdvertiser(), getBanner(), getProfile(), getUrl(), getCrefererMultiple());
    }

    public void addCreferer(String creferrer) {
        if (getCreferrer() == null) {
            setCreferrer(creferrer);
        } else {
            setCreferrer(getCreferrer() + ASCII_CODE_SEMICOLON + creferrer);
        }
    }

    public String getValue() {

        return getAdvertiser() + ASCII_CODE_COMMA + getBanner() + ASCII_CODE_COMMA + getProfile() + ASCII_CODE_COMMA + getUrl() + ASCII_CODE_COMMA + getCreferrer();
    }
}
