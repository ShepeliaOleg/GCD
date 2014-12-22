package pageObjects.changeLanguage;

import pageObjects.core.AbstractPortalPage;
import utils.WebDriverUtils;

import java.util.Collection;

public class ChangeLanguagePage extends AbstractPortalPage {

	private static final String ROOT_XP =                   "//*[contains(@class,'fn-portlet')]";
    private static final String LANGUAGE_SELECTOR_ICON_XP = ROOT_XP + "//*[contains(@class, 'micon-language')]";
    private static final String LANGUAGE_SELECTOR_XP =	    "//*[contains(@class, 'fn-language-trigger')]";
    private static final String LANGUAGE_LIST_XP =	        "//*[preceding-sibling::*[contains(@class, 'fn-language-trigger')]]";

    public ChangeLanguagePage(){
        super(new String[]{LANGUAGE_SELECTOR_XP});
    }

    private boolean languageListIsOpened() {
        return WebDriverUtils.getAttribute(LANGUAGE_SELECTOR_XP,"class").contains("active");
    }

    private void showLanguageList() {
        if (!languageListIsOpened()) {
            WebDriverUtils.openCustomDropdown(LANGUAGE_SELECTOR_XP);
        }
    }


    public Collection<String> getLanguageCodes() {
        return WebDriverUtils.getCustomDropdownOptionsValue(LANGUAGE_SELECTOR_XP);
    }

    public void changeLanguage(String languageCode) {
        WebDriverUtils.setCustomDropdownOptionByValue(LANGUAGE_SELECTOR_XP, languageCode);
        WebDriverUtils.waitForElementToDisappear(LANGUAGE_LIST_XP);
    }
}
