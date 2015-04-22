package pageObjects.account;

import pageObjects.core.AbstractPortalPage;
import utils.WebDriverUtils;

public class TransactionHistoryPage extends AbstractPortalPage {

    private static final String ROOT_XP = "//*[contains(@class,'portlet-mobile-cashier')]";
    private static final String HISTORY_NAVIGATION_XP = "//select[@name='group']";
    private static final String PERIOD_SELECT_XP = "//*[contains(@class,'fn-transaction-select')]/option[@value='";
    private static final String VIEW_HISTORY_BTN_XP = "//*[contains(@class,'portlet__actions')]/button[@class='btn']";
    private static final String TRANSACTION_LIST = "//*[contains(@class,'fn-transaction-list')]/li";

    public static final String ONE_DAY = "1";
    public static final String THREE_DAYS = "3";
    public static final String WEEK = "7";
    public static final String MONTH = "30";
    public static final String THREE_MONTH = "90";
    public static final String GAME_OPT = "game";
    public static final String GENERAL_OPT = "general";
    public static final String ALL_OPT = "all";

    public TransactionHistoryPage() {
        super(new String[]{ROOT_XP});
        WebDriverUtils.waitForPageToLoad();
    }

    public String getGroupText(String groupValue){
        return WebDriverUtils.getElementText(HISTORY_NAVIGATION_XP + "/option[@value='" + groupValue + "']");
    }

    public void selectPeriod(String value){
        WebDriverUtils.click(PERIOD_SELECT_XP + value + "']");
    }

    public void clickGroups(String groupsInput){
        WebDriverUtils.click(HISTORY_NAVIGATION_XP + "/option[@value='" + groupsInput + "']");
    }

    public void clickViewHistoryBtn(){
        WebDriverUtils.click(VIEW_HISTORY_BTN_XP);
    }

    public int getTransactionCount(){
        return WebDriverUtils.getXpathCount(TRANSACTION_LIST);
    }
}

