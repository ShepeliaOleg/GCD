package pageObjects.admin.settings;

import pageObjects.core.AbstractLiferayPopup;
import utils.WebDriverUtils;
import utils.core.WebDriverFactory;

/**
 * Created by serhiist on 2/18/2015.
 */
public class GamesManagementPopup extends AbstractLiferayPopup {
    public static final String GAMES_MANAGEMENT_CONTENT_XP = "//*[@id='listViewContainer']";
    private static final String BUTTON_ADD_XP = "//*[@id='addNewGame']";
    private static final String BUTTON_CANCEL_XP = "//*[@id='cancelListView']";
    private static final String BUTTON_SAVE_XP = "//*[@id='saveListView']";
    private static final String DROPDOWN_GAME_TYPE_XP = "//*[@id='gamesTypes']";
    private static final String SEARCH_FIELD_XP = "//*[@id='gmSearchBox']";
    private static final String BUTTON_EDIT_XP = "//*[@class='editGame']";
    private static final String BUTTON_DELETE_XP = "//*[@class='deleteGame']";

    public GamesManagementPopup() {
    }

    public AddGamePopup addGamePopup(){
        WebDriverUtils.click(BUTTON_ADD_XP);
        WebDriverUtils.waitForElement(GameManagementPopup.GAME_MANAGEMENT_CONTENT_XP);
        return new AddGamePopup();
    }

    public EditGamePopup editGamePopup(String gameName){
        WebDriverUtils.inputTextToField(SEARCH_FIELD_XP, gameName);
        WebDriverUtils.click(BUTTON_EDIT_XP);
        WebDriverUtils.waitForElement(GameManagementPopup.GAME_MANAGEMENT_CONTENT_XP);
        return new EditGamePopup();
    }

    public void deleteGame(String gameName){
        WebDriverUtils.inputTextToField(SEARCH_FIELD_XP, gameName);
        WebDriverUtils.click(BUTTON_DELETE_XP);
        WebDriverUtils.acceptJavaScriptAlert(WebDriverFactory.getPortalDriver());
    }
}
