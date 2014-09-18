package utils.cookie;

public class LanguageCookie extends Cookie {
    private static String name = "GUEST_LANGUAGE_ID";

    public LanguageCookie(String value) {
        super(name, value);
    }

    public LanguageCookie() {
        super(name, "");
    }
}
