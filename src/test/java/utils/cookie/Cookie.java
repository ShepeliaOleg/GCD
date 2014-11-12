package utils.cookie;

import org.openqa.selenium.WebDriver;
import utils.WebDriverUtils;

import java.util.Date;

public abstract class Cookie {
    private String name;
    private String value;
    private String domain;
    private String path;
    private Date   expiry;

    protected Cookie(String name, String value) {
        this.name =     name;
        this.value =    value;
        this.domain =   WebDriverUtils.getDomain();
        this.path =     "/";
        this.expiry =   new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24); // tomorrow
    }

    protected void add(WebDriver webDriver) {
        delete(webDriver);
        WebDriverUtils.addCookie(webDriver, name, value, domain, path, expiry);
    }

    protected void delete(WebDriver webDriver) {
        if (isPresent(webDriver)) {
            WebDriverUtils.deleteCookie(webDriver, name);
        }
    }

    protected boolean isPresent(WebDriver webDriver) {
        return WebDriverUtils.isCookieExists(webDriver, name);
    }

    protected String getValue(WebDriver webDriver) {
        return WebDriverUtils.getCookieValue(webDriver, name);
    }
}
