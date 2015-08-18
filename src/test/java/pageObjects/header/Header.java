package pageObjects.header;

import org.apache.commons.lang3.ArrayUtils;
import pageObjects.core.AbstractPageObject;
import pageObjects.core.AbstractPortalPage;
import pageObjects.menu.Menu;
import utils.Locator;
import utils.WebDriverUtils;
import utils.core.WebDriverFactory;

import java.util.Collection;
import java.util.List;

public class Header extends AbstractPageObject{
	protected static final String ROOT_XP=	"//div[contains(@class, 'main-header ')]";//"//*[@class='main-header ']";
	public static final String BALANCE_AREA ="//*[contains(@class, 'main-header__balance')]";
    //Desktop only
    private static final String DROPDOWN_LANGUAGE_XP =	"//*[contains(@class, 'fn-language-trigger')]";
    //Mobile only
    public static final Locator MENU_XP =       new Locator("fn-open-menu", ROOT_XP + "//*[contains(@class,'fn-open-menu')]", null);

    public Header(){
		super(WebDriverFactory.getPortalDriver(), new String[]{ROOT_XP});
	}

	public Header(String[] clickableBys){
		super(WebDriverFactory.getPortalDriver(), ArrayUtils.addAll(clickableBys, new String[]{ROOT_XP}));
	}

	public boolean isLoggedIn(){
		return WebDriverUtils.isVisible(BALANCE_AREA, 1);
	}

    public AbstractPortalPage waitForLogout(){
        WebDriverUtils.waitForElementToDisappear(BALANCE_AREA, 30);
        return new AbstractPortalPage();
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
