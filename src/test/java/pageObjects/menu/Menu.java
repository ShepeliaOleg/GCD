package pageObjects.menu;

import org.apache.commons.lang3.ArrayUtils;
import pageObjects.HomePage;
import pageObjects.core.AbstractPage;
import pageObjects.core.AbstractPageObject;
import utils.WebDriverUtils;

import java.util.Collection;

public class Menu extends AbstractPageObject {
	protected static final String ROOT_XP =                 "//*[contains(@class,'fn-slide-menu-wrapper')]";
    private static final String BUTTON_CLOSE_XP =           ROOT_XP + "//*[contains(@class,'fn-close-menu')]";
    private static final String HOME_XP =	                ROOT_XP + "//*[contains(@class,'micon-home')]";
    private static final String LANGUAGE_ICON_XP =          ROOT_XP + "//*[contains(@class,'micon-language')]";
    private static final String LANGUAGE_XP =	            "//*[contains(@class, 'fn-language-trigger')]";
    private static final String GETTING_STARTED_XP =	    ROOT_XP + "//*[contains(@class,'micon-getting-started')]";
    private static final String SUPPORT_XP =	            ROOT_XP + "//*[contains(@class,'micon-support')]";
    private static final String RESPONSIBLE_GAMING_XP =     ROOT_XP + "//*[contains(@class,'micon-responsible-gaming')]";
    private static final String CONTACT_US_XP =	            ROOT_XP + "//*[contains(@class,'micon-contact-us')]";

	public Menu(){
		super(new String[]{ROOT_XP});
	}

	public Menu(String[] clickableBys){
		super(ArrayUtils.addAll(clickableBys, new String[]{ROOT_XP}));
	}

    public AbstractPage closeMenu() {
        WebDriverUtils.click(BUTTON_CLOSE_XP);
        return new HomePage();
    }

    public LoggedInMenu loggedInMenu(){
        return new LoggedInMenu();
    }

    public LoggedOutMenu loggedOutMenu(){
        return new LoggedOutMenu();
    }

    public void showLanguageItems() {
        WebDriverUtils.click(LANGUAGE_ICON_XP);
    }

    public Collection<String> getLanguageCodes() {
        return WebDriverUtils.getCustomDropdownOptionsValue(LANGUAGE_XP);
    }

    public void setLanguage(String languageCode) {
        WebDriverUtils.setCustomDropdownOptionByValue(LANGUAGE_XP, languageCode);
        WebDriverUtils.waitForElementToDisappear(ROOT_XP);
    }
}
