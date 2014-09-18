package pageObjects.header;

import org.apache.commons.lang3.ArrayUtils;
import pageObjects.core.AbstractPage;
import pageObjects.core.AbstractPageObject;
import pageObjects.menu.Menu;
import pageObjects.referAFriend.ReferAFriendPopup;
import utils.WebDriverUtils;

import java.util.Collection;
import java.util.List;

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

    public Collection<String> getLanguageCodes() {
        List<String> languages = WebDriverUtils.getListOfAttributeValues(DROPDOWN_LANGUAGE_XP, "data-lang");
        languages.add(WebDriverUtils.getAttribute(DROPDOWN_LANGUAGE_XP + "//span", "class").replace("icon-flag ", ""));
        return languages;
    }

    public void setLanguage(String language) {
        WebDriverUtils.setDropdownOptionByValue(DROPDOWN_LANGUAGE_XP, language);
    }
}
