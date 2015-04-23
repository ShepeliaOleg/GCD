package pageObjects.replacers;

import pageObjects.core.AbstractPortalPage;
import utils.WebDriverUtils;

/**
 * Created by serhiist on 4/10/2015.
 */
public class AllFreeSpinsPage extends AbstractPortalPage {
    private static int ordinalPosition = 0;
    private static final String ROOT_XP = "//*[@id='layout-column__column-1']";
    private static final String FREE_SPINS_BALANCE_ROOT2_XP = "//*[@class='free-spins-balance']";
    private static String FREE_SPINS_HEADER_XP = "//*[@class='freespin__header'][" + ordinalPosition + "]";
    private static final String TEXT_NAME_XP = FREE_SPINS_HEADER_XP + "//*[contains(@class, 'freespin__header-title')]";
    private static final String TEXT_SPINSCOUNT_XP = FREE_SPINS_HEADER_XP + "//*[contains(@class, 'freespin__header-spinscount')]";
    private static String FREE_SPINS_CONTAINER_XP = "//*[@class='freespin-container'][" + ordinalPosition + "]";
    private static final String VALUE_EXPIRY_DATE_XP = FREE_SPINS_CONTAINER_XP + "//*[contains(@class, 'freespin__expiry-date')]";
    private static final String VALUE_PENDING_BONUS_WINNINGS_XP = FREE_SPINS_CONTAINER_XP + "//*[contains(@class, 'freespin__wincount-count')]";
    private static final String GAMES_INFO_XP = FREE_SPINS_CONTAINER_XP + "//*[contains(@class, 'fn-gameitems')]";
    private static final String TEXT_FREE_SPINS_XP = "//*[@class='freespin__title']";
    private static final String TAG_GAME_ID = "data-key";


    public void setOrdinalPosition(int ordinalPosition) {
        AllFreeSpinsPage.ordinalPosition = ordinalPosition;
    }

    public boolean isReplacerVisible() {
        return WebDriverUtils.isVisible(FREE_SPINS_BALANCE_ROOT2_XP);
    }

    public String getFreeSpinsTitle() {
        return WebDriverUtils.getElementText(TEXT_FREE_SPINS_XP);
    }

    public String getFreeSpinsMarketingName() {
        return WebDriverUtils.getElementText(TEXT_NAME_XP);
    }

    public String getFreeSpinsMarketingName(int ordinalPosition) {
        setOrdinalPosition(ordinalPosition);
        return WebDriverUtils.getElementText(TEXT_NAME_XP);
    }

    public void clickFreeSpin() {
        WebDriverUtils.click(TEXT_NAME_XP);
    }

    public void clickFreeSpin(int ordinalPosition) {
        setOrdinalPosition(ordinalPosition);
        WebDriverUtils.click(TEXT_NAME_XP);
    }

    public String getFreeSpinsAmount() {
        return WebDriverUtils.getElementText(TEXT_SPINSCOUNT_XP).split(" ")[0];
    }

    public String getFreeSpinsAmount(int ordinalPosition) {
        setOrdinalPosition(ordinalPosition);
        return WebDriverUtils.getElementText(TEXT_SPINSCOUNT_XP).split(" ")[0];
    }

    public String getFreeSpinsExpirationDate() {
        return WebDriverUtils.getElementText(VALUE_EXPIRY_DATE_XP);
    }

    public String getPendingBonusWinnings() {
        return WebDriverUtils.getElementText(VALUE_PENDING_BONUS_WINNINGS_XP);
    }

    public String getPendingBonusWinnings(int ordinalPosition) {
        setOrdinalPosition(ordinalPosition);
        return WebDriverUtils.getElementText(VALUE_PENDING_BONUS_WINNINGS_XP);
    }

    public boolean isGamesInfoVisible() {
        return WebDriverUtils.isVisible(GAMES_INFO_XP);
    }

    public boolean isGamesInfoVisible(int ordinalPosition) {
        setOrdinalPosition(ordinalPosition);
        return WebDriverUtils.isVisible(GAMES_INFO_XP);
    }

    public String getGameID(int index){
        String id;
        String itemViewXP = GAMES_INFO_XP +"//ul[1]/li["+index+"]/div";
        id = WebDriverUtils.getAttribute(itemViewXP, TAG_GAME_ID);
        return id;
    }

    public String getGameName(int index){
/*
        String id;
        String itemViewXP = GAMES_INFO_XP +"//ul[1]/li["+index+"]/div";
        id = WebDriverUtils.getAttribute(itemViewXP, TAG_GAME_ID);
        return id;
*/
        return null;
    }
}

