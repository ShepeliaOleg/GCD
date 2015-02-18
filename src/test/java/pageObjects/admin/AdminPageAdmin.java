package pageObjects.admin;

import pageObjects.admin.settings.AddGamePopup;
import pageObjects.admin.settings.GamesManagementPopup;
import pageObjects.admin.settings.SettingsPopup;
import pageObjects.core.AbstractPortalPage;
import utils.WebDriverUtils;

public class AdminPageAdmin extends AbstractPortalPage {

    public final static String DOCKBAR_XP = "//*[@id='dockbar']";
    private final static String DOCKBAR_SETTINGS_XP = DOCKBAR_XP + "//*[@id='dockbarOrgSettings']";
    private final static String DOCKBAR_GAMES_MANAGEMENT_XP = DOCKBAR_XP + "//*[@id='gamesmanagementdocklink']";
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

    public GamesManagementPopup openGamesManagement() {
        WebDriverUtils.click(DOCKBAR_GAMES_MANAGEMENT_XP);
        WebDriverUtils.waitForElement(GamesManagementPopup.GAMES_MANAGEMENT_CONTENT_XP, 60);
        return new GamesManagementPopup();
    }

    public AddGamePopup addGame() {
        return openGamesManagement().addGamePopup();
    }
}
