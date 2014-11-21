package pageObjects.account;

import pageObjects.core.AbstractPortalPage;
import utils.TypeUtils;
import utils.WebDriverUtils;

public class PendingWithdrawPage extends AbstractPortalPage {

    private static final String ROOT_XP =                   "//*[contains(@class,'portlet-mobile-cashier')]";
    private static final String TITLE_XP =                  ROOT_XP + "//*[contains(@class,'title')]/span";
    private static final String TOTAL_VALUE_XP =            ROOT_XP + "//span[contains(@class,'sum')]";
    private static final String WITHDRAW_LIST_XP =          ROOT_XP + "//ul[contains(@class,'fn-pending-withdraw-list')]";
    private static final String WITHDRAW_LIST_ITEM_XP =     WITHDRAW_LIST_XP + "/li";
    private static final String WITHDRAW_OPENED_XP =        "[contains(@class,'opened')]";
    private static final String WITHDRAW_HEADER_XP =        "//span[contains(@class,'fn-accordion')]";
    private static final String WITHDRAW_HEADER_TIME_XP =   WITHDRAW_HEADER_XP + "/time";
    private static final String WITHDRAW_HEADER_AMOUNT_XP = WITHDRAW_HEADER_XP + "/span[1]";
    private static final String WITHDRAW_STATUS_XP =        "//ul/li[1]";
    private static final String WITHDRAW_ACCOUNT_XP =       "//ul/li[2]";
    private static final String WITHDRAW_METHOD_XP =        "//ul/li[3]";
    private static final String WITHDRAW_CODE_XP =          "//ul/li[4]";
    private static final String BUTTON_WITHDRAW_CANCEL_XP = "//span[contains(@class,'fn-cancel-withdraw')";
    private static final String BUTTON_LOAD_MORE_XP =       ROOT_XP + "//*[contains(@class,'fn-load-more')]";

    public PendingWithdrawPage() {
        super(new String[]{ROOT_XP});
    }

    public int getWithdrawRecordsCount() {
        return WebDriverUtils.getXpathCount(WITHDRAW_LIST_ITEM_XP);
    }

    private String getWithdrawXpathByIndex(int withdrawIndex) {
        return WITHDRAW_LIST_ITEM_XP + "[" + withdrawIndex + "]";
    }

    public String getWithdrawDate(int withdrawIndex) {
        return TypeUtils.getBalanceAmount(WebDriverUtils.getElementText(getWithdrawXpathByIndex(withdrawIndex) + WITHDRAW_HEADER_TIME_XP));
    }

    public String getWithdrawAmount(int withdrawIndex) {
        return TypeUtils.getBalanceAmount(getWithdrawBalance(withdrawIndex));
    }

    public String getWithdrawCurrency(int withdrawIndex) {
        return TypeUtils.getBalanceCurrency(getWithdrawBalance(withdrawIndex));
    }

    private String getWithdrawBalance(int withdrawIndex) {
        return WebDriverUtils.getElementText(getWithdrawXpathByIndex(withdrawIndex) + WITHDRAW_HEADER_AMOUNT_XP);
    }

    public String getWithdrawStatus(int withdrawIndex) {
        openWithdrawDetailsIfClosed(withdrawIndex);
        return WebDriverUtils.getElementText(getWithdrawXpathByIndex(withdrawIndex) + WITHDRAW_STATUS_XP);
    }

    public String getWithdrawAccount(int withdrawIndex) {
        openWithdrawDetailsIfClosed(withdrawIndex);
        return WebDriverUtils.getElementText(getWithdrawXpathByIndex(withdrawIndex) + WITHDRAW_ACCOUNT_XP);
    }

    public String getWithdrawMethod(int withdrawIndex) {
        openWithdrawDetailsIfClosed(withdrawIndex);
        return WebDriverUtils.getElementText(getWithdrawXpathByIndex(withdrawIndex) + WITHDRAW_METHOD_XP);
    }

    private String getWithdrawCode(int withdrawIndex) {
        openWithdrawDetailsIfClosed(withdrawIndex);
        return WebDriverUtils.getElementText(getWithdrawXpathByIndex(withdrawIndex) + WITHDRAW_CODE_XP);
    }

    private void openWithdrawDetailsIfClosed(int withdrawIndex) {
        if (!withdrawIsOpened(withdrawIndex)) {
            clickOnWithdrawByIndex(withdrawIndex);
        }
    }

    private void clickOnWithdrawByIndex(int withdrawIndex) {
        WebDriverUtils.click(getWithdrawXpathByIndex(withdrawIndex));
    }

    private boolean withdrawIsOpened(int withdrawIndex) {
        return WebDriverUtils.getAttribute(getWithdrawXpathByIndex(withdrawIndex), "class").contains("opened");
    }

    public void cancelWithdraw(int withdrawIndex){
        openWithdrawDetailsIfClosed(withdrawIndex);
        WebDriverUtils.click(getWithdrawXpathByIndex(withdrawIndex) + BUTTON_WITHDRAW_CANCEL_XP);
    }

    public void loadMore() {
        WebDriverUtils.click(BUTTON_LOAD_MORE_XP);
    }

    public int getOpenedWithdrawCount() {
        return WebDriverUtils.getXpathCount(WITHDRAW_LIST_ITEM_XP + WITHDRAW_OPENED_XP);
    }

    public String getTotalAmount() {
        return TypeUtils.getBalanceAmount(WebDriverUtils.getElementText(TOTAL_VALUE_XP));
    }
}

