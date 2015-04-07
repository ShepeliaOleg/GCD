package pageObjects.replacers;

import enums.Page;
import pageObjects.core.AbstractPortalPage;
import utils.TypeUtils;
import utils.WebDriverUtils;

/**
 * Created by serhiist on 3/24/2015.
 */
public class BonusHistoryPage extends AbstractPortalPage {
    private static final String BONUS_BALANCE_ROOT_XP = "//*[@id='layout-column__column-1']";
    private static final String TOTAL_BONUS_BALANCE_XP = BONUS_BALANCE_ROOT_XP + "//div[1]//p/span";
    private static final String BONUS_DETAILS_ROOT_XP = "//*[contains(@class, 'fn-bonus-history')]";
    private static final String FIELD_BONUS_DETAILS_NAME_XP = BONUS_DETAILS_ROOT_XP + "//*[contains(text(), '" + PLACEHOLDER + "')]";
    private static final String FIELD_BONUS_DETAILS_AMOUNT_XP = "/following-sibling::*[1]";
    private static final String FIELD_CURRENT_BALANCE_XP = "//ul[" + PLACEHOLDER + "]//*[@class='info-list info-list_type_sub row']/li[contains(., 'Current balance')]/span[last()]";
    private static final String ABSTRACT_BONUS_ELEMENT_XP = "//*[contains(@class, 'fn-bonus-history')]//ul";
    private static final String BUTTON_LOAD_MORE_XP = "//*[contains(@class, 'fn-load-more')]";
    private static final String LOADER_XP = "//*[contains(@class, 'fn-loader')]";

    public BonusHistoryPage() {
        super(new String[]{BONUS_BALANCE_ROOT_XP, BONUS_DETAILS_ROOT_XP});
    }

    public String getTotalBonusBalance() {
        WebDriverUtils.waitForElement(TOTAL_BONUS_BALANCE_XP);
        WebDriverUtils.waitFor(1000);
        return WebDriverUtils.getElementText(TOTAL_BONUS_BALANCE_XP);
    }

    private String getBonusDetailsNameXP(Page bonusType) {
        String bonusName = "";
        switch (bonusType) {
            case okBonus:
                bonusName = "AUTO_NO_ACCEPT_DECLINE";
                break;
            case acceptDeclineBonus:
                bonusName = "AUTO_ACCEPT_DECLINE";
                break;
        }
        return FIELD_BONUS_DETAILS_NAME_XP.replace(PLACEHOLDER, bonusName);
    }

    public boolean isBonusAddedToList(Page bonusType) {
        return WebDriverUtils.isVisible(getBonusDetailsNameXP(bonusType));
    }

    public String getBonusAmount(Page bonusType) {
        return TypeUtils.getBalanceAmount(WebDriverUtils.getElementText(getBonusDetailsNameXP(bonusType) + FIELD_BONUS_DETAILS_AMOUNT_XP));
    }

    public void clickOnBonus(Page bonusName) {
        String bonusXP = getBonusDetailsNameXP(bonusName);
        WebDriverUtils.waitForElementToDisappear(LOADER_XP);
        WebDriverUtils.waitForElement(bonusXP);
        WebDriverUtils.click(bonusXP);
    }

    public String getCurrentBalanceValue(Page bonusName) {
        return TypeUtils.getBalanceAmount(WebDriverUtils.getElementText(FIELD_CURRENT_BALANCE_XP.replace(PLACEHOLDER, getBonusDetailsNameXP(bonusName))));
    }

    public String getCurrentBalanceCurrency(Page bonusName) {
        return TypeUtils.getBalanceCurrency(WebDriverUtils.getElementText(FIELD_CURRENT_BALANCE_XP.replace(PLACEHOLDER, getBonusDetailsNameXP(bonusName))));
    }

    public int getNumberOfBonusesInList() {
        return WebDriverUtils.getCountOfNodes(ABSTRACT_BONUS_ELEMENT_XP);
    }

    public void clickLoadMoreButton() {
        while (isLoadButtonVisible()) {
            WebDriverUtils.click(BUTTON_LOAD_MORE_XP);
        }
    }

    public boolean isLoadButtonVisible() {
        return WebDriverUtils.isVisible(BUTTON_LOAD_MORE_XP);
    }
}
