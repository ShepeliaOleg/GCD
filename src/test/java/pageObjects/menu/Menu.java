package pageObjects.menu;

import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.apache.commons.lang3.ArrayUtils;
import pageObjects.HomePage;
import pageObjects.changeLanguage.ChangeLanguagePage;
import pageObjects.core.AbstractPageObject;
import pageObjects.core.AbstractPortalPage;
import utils.NavigationUtils;
import utils.WebDriverUtils;
import utils.core.WebDriverFactory;

import java.util.Collection;

public class Menu extends AbstractPageObject {
	protected static final String ROOT_XP =                 "//*[contains(@class,'fn-slide-menu-wrapper')]";
    private static final String BUTTON_CLOSE_XP =           ROOT_XP + "//*[contains(@class,'fn-close-menu')]";
    private static final String HOME_XP =	                ROOT_XP + "//*[contains(@class,'micon-home')]";
    private static final String LANGUAGE_ICON_XP =          ROOT_XP + "//*[contains(@class,'micon-language')]";
    private static final String GETTING_STARTED_XP =	    ROOT_XP + "//*[contains(@class,'micon-getting-started')]";
    private static final String SUPPORT_XP =	            ROOT_XP + "//*[contains(@class,'micon-support')]";
    private static final String RESPONSIBLE_GAMING_XP =     ROOT_XP + "//*[contains(@class,'micon-responsible-gaming')]";
    private static final String CONTACT_US_XP =	            ROOT_XP + "//*[contains(@class,'micon-contact-us')]";

	public Menu(){
		super(WebDriverFactory.getPortalDriver(), new String[]{ROOT_XP});
	}

	public Menu(String[] clickableBys){
		super(WebDriverFactory.getPortalDriver(), ArrayUtils.addAll(clickableBys, new String[]{ROOT_XP}));
	}

    public AbstractPortalPage closeMenu() {
        WebDriverUtils.click(BUTTON_CLOSE_XP);
        return new HomePage();
    }

    public LoggedInMenu loggedInMenu(){
        return new LoggedInMenu();
    }

    public LoggedOutMenu loggedOutMenu(){
        return new LoggedOutMenu();
    }

    public ChangeLanguagePage openLanguagePage() {
        WebDriverUtils.click(LANGUAGE_ICON_XP);
        return new ChangeLanguagePage();
    }
}
