package springConstructors;

import enums.Licensee;

public class DriverData{

    public String baseUrl;
    public String browser;
    public String os;
    public Licensee licensee;
    public String device;

    public String getBaseUrl(){
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl){
        this.baseUrl=baseUrl;
    }

	public String getBrowser(){
        return browser;
	}

    public void setBrowser(String browser){
        this.browser=browser;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public Licensee getLicensee() {
        return licensee;
    }

    public void setLicensee(Licensee licensee) {
        this.licensee = licensee;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

}
