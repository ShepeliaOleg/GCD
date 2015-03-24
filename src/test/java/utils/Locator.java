package utils;

public class Locator {

    private String functionalClass;
    private String xpath;
    private String css;


    public Locator(String xpath) {
        this.functionalClass = null;
        this.xpath = xpath;
        this.css = null;
    }

    public Locator(Locator locator, String xpath) {
        this.functionalClass = null;
        this.xpath =  locator.getXpath() + xpath;
        this.css = null;
    }

    public Locator(String functionalClass, String xpath, String css) {
        this.functionalClass = functionalClass;
        this.xpath = xpath;
        this.css = css;
    }

    public String getFunctionalClass() {
        return functionalClass;
    }

    public void setFunctionalClass(String functionalClass) {
        this.functionalClass = functionalClass;
    }

    public String getXpath() {
        if (xpath != null) {
            return xpath;
        } else
            return getXpathByFunctionalClass(getFunctionalClass());
    }

    public void setXpath(String xpath) {
        this.xpath = xpath;
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    private String getXpathByFunctionalClass(String functionalClass) {
        if (functionalClass != null) {
            return "//*[contains(@class,'" + functionalClass + "')]";
        } else throw new RuntimeException("Cannot generate xpath: functional class is not defined in Locator.");

    }
}
