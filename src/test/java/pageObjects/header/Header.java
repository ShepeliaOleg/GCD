package pageObjects.header;

import org.apache.commons.lang3.ArrayUtils;
import pageObjects.core.AbstractPage;
import pageObjects.core.AbstractPageObject;
import pageObjects.menu.Menu;
import utils.WebDriverUtils;

import java.util.Collection;
import java.util.List;

public class Header extends AbstractPageObject{
	protected static final String ROOT_XP=	"//*[@class='main-header']";
	public static final String BALANCE_AREA ="//*[contains(@class, 'main-header__balance')]";
    //Desktop only
    private static final String DROPDOWN_LANGUAGE_XP =	"//*[contains(@class, 'fn-language-trigger')]";
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
        WebDriverUtils.waitFor();
        return new Menu();
    }

    public Collection<String> getLanguageCodes() {
        List<String> languages = WebDriverUtils.getCustomDropdownOptionsValue(DROPDOWN_LANGUAGE_XP);
        languages.add(WebDriverUtils.getCustomDropdownSelectedOptionValue(DROPDOWN_LANGUAGE_XP));
        return languages;
    }

    public void setLanguage(String language) {
        if(!WebDriverUtils.getCustomDropdownSelectedOptionValue(DROPDOWN_LANGUAGE_XP).equals(language)){
            WebDriverUtils.setCustomDropdownOptionByValue(DROPDOWN_LANGUAGE_XP, language);
        }
    }
}
