package pageObjects.account;

import pageObjects.core.AbstractPortalPage;

public class TransactionHistoryPage extends AbstractPortalPage {

    private static final String ROOT_XP = "//*[contains(@class,'portlet-mobile-cashier')]";
    private static final String HISTORY_NAVIGATION_XP = "//*[contains(@class,'tabs-nav')]";
    private static final String ALL_TAB_XP = HISTORY_NAVIGATION_XP + "//*[contains(@class,'tabs-col')][1]";
    private static final String DEPOSIT_TAB_XP = HISTORY_NAVIGATION_XP + "//*[contains(@class,'tabs-col')][2]";
    private static final String WITHDRAW_TAB_XP = HISTORY_NAVIGATION_XP + "//*[contains(@class,'tabs-col')][3]";

    public TransactionHistoryPage() {
        super(new String[]{ROOT_XP});
    }
}

