package pageObjects.header;

import org.apache.commons.lang3.ArrayUtils;
import pageObjects.core.AbstractPage;
import pageObjects.core.AbstractPageObject;
import pageObjects.menu.Menu;
import pageObjects.referAFriend.ReferAFriendPopup;
import utils.WebDriverUtils;

import java.util.Collection;

public class Header extends AbstractPageObject{
	protected static final String ROOT_XP=	"//*[@class='main-header']";
	public static final String BALANCE_AREA ="//*[contains(@class, 'main-header__balance')]";
    //Desktop only
    private static final String DROPDOWN_LANGUAGE_XP =	ROOT_XP + "//*[contains(@class, 'language-selector-replacer')]";
    //Mobile only
    public static final String MENU_XP =       ROOT_XP + "//*[contains(@class,'fn-open-menu')]";

    public Header(){
		super(new String[]{ROOT_XP});
	}

	public Header(String[] clickableBys){
		super(ArrayUtils.addAll(clickableBys, new String[]{ROOT_XP}));
	}

	public boolean isLoggedIn(){
		return WebDriverUtils.isVisible(BALANCE_AREA, 1);
	}

    public AbstractPage waitForLogout(){
        WebDriverUtils.waitForElementToDisappear(BALANCE_AREA, 30);
        return new AbstractPage();
    }

    public Menu openMenu() {
        WebDriverUtils.click(MENU_XP);
        return new Menu();
    }

    private Collection<String> getLanguageCodes() {
        return WebDriverUtils.getListOfAttributeValues(DROPDOWN_LANGUAGE_XP, "data-lang");
    }

    public Collection<String> getLanguageCodesList() {
        if (platform.equals(PLATFORM_DESKTOP)) {
            return getLanguageCodes();
        } else {
            Menu menu = openMenu();
            menu.showLanguageItems();
            return menu.getLanguageCodes();
        }

    }
}
