package springConstructors;

public class AffiliateData {
    private String defaultAdvertiser;
    private String advertiser;
    private String profile;
    private String banner;
    private String url;
    private String creferer;
    private String crefererSingle;
    private String crefererMultiple;

    public AffiliateData(String defaultAdvertiser, String advertiser, String profile, String banner, String url, String crefererSingle, String crefererMultiple) {
        this.defaultAdvertiser = defaultAdvertiser;
        this.advertiser = advertiser;
        this.profile = profile;
        this.banner = banner;
        this.url = url;
        this.crefererSingle = crefererSingle;
        this.crefererMultiple = crefererMultiple;
    }

    public AffiliateData(String advertiser, String profile, String banner, String url, String creferer) {
        this.advertiser = advertiser;
        this.profile = profile;
        this.banner = banner;
        this.url = url;
        this.creferer = creferer;
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

    public String getCreferer() {
        return creferer;
    }

    public void setCreferer(String creferer) {
        this.creferer = creferer;
    }

    public String getCrefererSingle() {
        return crefererSingle;
    }

    public void setCrefererSingle(String crefererSingle) {
        this.crefererSingle = crefererSingle;
    }

    public String getCrefererMultiple() {
        return crefererMultiple;
    }

    public void setCrefererMultiple(String crefererMultiple) {
        this.crefererMultiple = crefererMultiple;
    }

    public String getRelativeURL() {
        return "register?advertiser="+getAdvertiser()+"&profileid="+getProfile()+"&bannerid="+getBanner()+"&refererurl="+getUrl()+"&creferer="+getCreferer();
    }

    public AffiliateData getAffiliateDataSingle() {
        return new AffiliateData(advertiser, profile, banner, url, crefererSingle);
    }

    public AffiliateData getAffiliateDataMultiple() {
        return new AffiliateData(advertiser, profile, banner, url, crefererMultiple);
    }

    public AffiliateData getAffiliateDataDefaultAdvertiser() {
        return new AffiliateData(defaultAdvertiser, profile, banner, url, crefererSingle);
    }
}
