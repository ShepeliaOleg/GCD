package pageObjects.admin.settings;

import enums.GameType;
import pageObjects.core.AbstractLiferayPopup;
import pageObjects.core.AbstractPopup;
import springConstructors.GameData;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

/**
 * Created by serhiist on 2/18/2015.
 */
public class GameManagementPopup extends AbstractLiferayPopup {
    public static final String GAME_MANAGEMENT_CONTENT_XP = "//*[@id='gmDetailViewForm']";
    private static final String GAME_TYPE_XP = "//*[@id='gmGameType']";
    private static final String GAME_CODE_XP = "//*[@id='currentlyChangedGame.gameCode']";
    private static final String MOBILE_GAME_CODE_XP = "//*[@id='mobileGameCodeId']";
    private static final String MOBILE_GAME_TYPE_XP = "//*[@id='currentlyChangedGame.mobileGameType']";
    private static final String GAME_NAME_XP = "//*[@id='gmDetailName']";
    private static final String ARTICLE_ID_XP = "//*[@id='gmDetailArticleId']";
    private static final String MOBILE_ARTICLE_ID_XP = "//input[@id='gmMobileDetailArticleId']";
    private static final String GAME_DESCRIPTION_XP = "//*[@id='gmDetailDesc']";
    private static final String MAIN_IMAGE_XP = "//*[@id='imageURL']";
    private static final String MOBILE_IMAGE_XP = "//*[@id='mobileImageURL']";
    private static final String JACKPOT_CODE_XP = "//*[@id='gmJackpotCode']";
    private static final String FUN_SUPPORT_XP = "//*[@id='gmDetailIconUrl']";
    private static final String NEW_HIGHLIGHT_XP = "//*[@id='gmDetailNew']";
    private static final String POPULAR_XP = "//*[@id='gmDetailPopular']";
    private static final String SAVE_BUTTON_XP = "//*[@id='saveDetailView']";
    private static final String CANCEL_BUTTON_XP = "//*[@id='cancelDetailView']";
    private static final String VF_GAME_TYPE_XP = "//*[@id='vfGameType']";
    private static final String SMALL_IMAGE_XP = "//*[@id='iconUrl']";
    private static final String BINGO_CATEGORY_XP = "//*[@id='gmDetailPortalCategory']";
    private static final String GAME_CODE_ON_EDIT_PAGE_XP = "//*[@id='gmGameCode']/td[2]";
    private static final String GAME_POPUP_ERROR_MSG_XP = "//*[@class='portlet-msg portlet-msg-error']";


    private void setBingoCategory(GameData gameData) {
        WebDriverUtils.inputTextToField(BINGO_CATEGORY_XP, gameData.getBingoCategory());
    }

    private void setVFGameType(GameData gameData) {
        WebDriverUtils.inputTextToField(VF_GAME_TYPE_XP, gameData.getVfGameType());
    }

    private void setSmallImage(GameData gameData) {
        WebDriverUtils.inputTextToField(SMALL_IMAGE_XP, gameData.getSmallImage());
    }

    private void setGameType(GameData gameData) {
        WebDriverUtils.setDropdownOptionByText(GAME_TYPE_XP, gameData.getGameType());
    }

    private void setGameCode(GameData gameData) {
        WebDriverUtils.inputTextToField(GAME_CODE_XP, gameData.getGameCode());
    }

    private void setMobileGameCode(GameData gameData) {
        WebDriverUtils.inputTextToField(MOBILE_GAME_CODE_XP, gameData.getMobileGameCode());
    }

    private void setMobileGameType(GameData gameData) {
        WebDriverUtils.setDropdownOptionByText(MOBILE_GAME_TYPE_XP, gameData.getMobileGameType());
    }

    private void setGameName(GameData gameData) {
        WebDriverUtils.inputTextToField(GAME_NAME_XP, gameData.getGameName());
    }

    private void setArticleId(GameData gameData) {
        WebDriverUtils.inputTextToField(ARTICLE_ID_XP, gameData.getArticleId());
    }

    private void setMobileArticleId(GameData gameData) {
        WebDriverUtils.inputTextToField(MOBILE_ARTICLE_ID_XP, gameData.getMobileArticleId());
    }

    private void setGameDescription(GameData gameData) {
        WebDriverUtils.inputTextToField(GAME_DESCRIPTION_XP, gameData.getGameDescription());
    }

    private void setMainImage(GameData gameData) {
        WebDriverUtils.inputTextToField(MAIN_IMAGE_XP, gameData.getMainImage());
    }

    private void setMobileImage(GameData gameData) {
        WebDriverUtils.inputTextToField(MOBILE_IMAGE_XP, gameData.getMobileImage());
    }

    private void setJackpotCode(GameData gameData) {
        WebDriverUtils.inputTextToField(JACKPOT_CODE_XP, gameData.getJackpotCode());
    }

    private void setFunSupport(GameData gameData) {
        WebDriverUtils.setCheckBoxState(FUN_SUPPORT_XP, (gameData.getFunSupport().equals("true") ? true : false));
    }

    private void setNewHighlight(GameData gameData) {
        WebDriverUtils.setCheckBoxState(NEW_HIGHLIGHT_XP, (gameData.getNewHighlight().equals("true") ? true : false));
    }

    private void setPopular(GameData gameData) {
        WebDriverUtils.setCheckBoxState(POPULAR_XP, (gameData.getPopular().equals("true") ? true : false));
    }

    private String getErrorMsg(){
        return WebDriverUtils.getElementText(GAME_POPUP_ERROR_MSG_XP);
    }

    public void clickSaveButton() {
        WebDriverUtils.click(SAVE_BUTTON_XP);
        AbstractTest.assertFalse(WebDriverUtils.isElementVisible(GameManagementPopup.GAME_MANAGEMENT_CONTENT_XP, 1000), "Unable to save game: " + getErrorMsg());
    }

    public String getGameCodeOnEditPage() {
        return WebDriverUtils.getElementText(GAME_CODE_ON_EDIT_PAGE_XP);
    }

    public void clickCancelButton() {
        WebDriverUtils.click(CANCEL_BUTTON_XP);
    }

    protected void fillAllFieldsForGameType(GameType gameType, GameData gameData, String state) {
        setGameType(gameData);
        setMobileGameType(gameData);
        setGameName(gameData);
        setArticleId(gameData);
        setMobileArticleId(gameData);
        setGameDescription(gameData);
        setMainImage(gameData);
        setMobileImage(gameData);
        setNewHighlight(gameData);
        setPopular(gameData);
        switch (gameType) {
            case Casino:
                inputAllFieldsCasinoGame(gameData, state);
                break;
            case VFRoomGame:
                inputAllFieldsVFRoomGame(gameData, state);
                break;
            case VFSlotGame:
                inputAllFieldsVFSlotGame(gameData, state);
                break;
        }
    }

    private void inputAllFieldsCasinoGame(GameData gameData, String state) {
        if (state.equals("new"))
            setGameCode(gameData);
//        setMobileGameCode(gameData);
        setJackpotCode(gameData);
        setFunSupport(gameData);
    }

    private void inputAllFieldsVFSlotGame(GameData gameData, String state) {
        if (state.equals("new"))
            setGameCode(gameData);
//        setMobileGameCode(gameData);
        setVFGameType(gameData);
        setSmallImage(gameData);
        setFunSupport(gameData);
    }

    private void inputAllFieldsVFRoomGame(GameData gameData, String state) {
        setJackpotCode(gameData);
        setBingoCategory(gameData);
    }
}

