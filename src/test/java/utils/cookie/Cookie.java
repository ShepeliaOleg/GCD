package utils.cookie;

import utils.WebDriverUtils;

import java.util.Date;

public abstract class Cookie {
    private String name;
    private String value;
    private String domain;
    private String path;
    private Date   expiry;

    public Cookie(String name, String value) {
        this.name =     name;
        this.value =    value;
        this.domain =   WebDriverUtils.getDomain();
        this.path =     "/";
        this.expiry =   new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24); // tomorrow
    }

    public void add() {
        delete();
        WebDriverUtils.addCookie(name, value, domain, path, expiry);
    }

    public void delete() {
        if (isPresent()) {
            WebDriverUtils.deleteCookie(name);
        }
    }

    public boolean isPresent() {
        return WebDriverUtils.isCookieExists(name);
    }

    public String getValue() {
        return WebDriverUtils.getCookieValue(name);
    }
}
