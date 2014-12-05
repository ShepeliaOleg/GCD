package pageObjects.admin;

import pageObjects.admin.settings.SettingsPopup;
import pageObjects.core.AbstractPortalPage;
import utils.WebDriverUtils;

public class AdminPageAdmin extends AbstractPortalPage {

    public final static String DOCKBAR_XP = "//*[@id='dockbar']";
    private final static String DOCKBAR_SETTINGS_XP = DOCKBAR_XP + "//*[@id='dockbarOrgSettings']";
    private final static String BUTTON_LOGOUT_XP = "//*[@class='sign-out']";

    public AdminPageAdmin(){
        super(new String[]{DOCKBAR_XP});
    }

    public SettingsPopup openSettings() {
        WebDriverUtils.click(DOCKBAR_SETTINGS_XP);
        WebDriverUtils.waitForElement(SettingsPopup.MENU_XP, 60);
        return new SettingsPopup();
    }

    public static void clickLogout(){
        WebDriverUtils.click(BUTTON_LOGOUT_XP);
    }
}
