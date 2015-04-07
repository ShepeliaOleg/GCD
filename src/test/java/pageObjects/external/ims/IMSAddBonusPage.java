package pageObjects.external.ims;

import enums.BonusType;
import enums.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import pageObjects.core.AbstractServerIframe;
import pageObjects.core.AbstractServerPage;
import utils.Locator;
import utils.WebDriverUtils;
import utils.core.AbstractTest;
import utils.core.WebDriverFactory;

public class IMSAddBonusPage extends AbstractServerPage {
    private static final String ADD_BONUS_IFRAME_XP = "//*[@id='main-content']";
    private static final String ADD_BONUS_IFRAME_ID = "main-content";


    public IMSAddBonusPage() {
        super(new String[]{ADD_BONUS_IFRAME_XP});
    }

    public IMSPlayerDetailsPage addBonus(Page pushMessages, String amount) {
        new IMSAddBonusIframe(ADD_BONUS_IFRAME_ID).sendBonus(pushMessages, amount);
        WebDriverUtils.acceptJavaScriptAlert(WebDriverFactory.getServerDriver());
        WebDriverUtils.waitFor();
        WebDriverUtils.acceptJavaScriptAlert(WebDriverFactory.getServerDriver());
        WebDriverUtils.waitFor(2000);
        return new IMSPlayerDetailsPage();
    }

    public IMSPlayerDetailsPage addBonus(Page pushMessages, String amount, BonusType bonusType) {
        new IMSAddBonusIframe(ADD_BONUS_IFRAME_ID).sendBonus(pushMessages, amount, bonusType);
        WebDriverUtils.acceptJavaScriptAlert(WebDriverFactory.getServerDriver());
        WebDriverUtils.waitFor();
        if (!pushMessages.equals(Page.freeSpins)) {
            WebDriverUtils.acceptJavaScriptAlert(WebDriverFactory.getServerDriver());
        }
        WebDriverUtils.waitFor(2000);
        return new IMSPlayerDetailsPage();
    }

    public class IMSAddBonusIframe extends AbstractServerIframe {
        private static final String DROPDOWN_BONUS_TEMPLATE_PLAYABLE_XP = "//*[@id='bonus_template_select_1']";
        private static final String DROPDOWN_BONUS_TEMPLATE_FREE_SPINS_XP = "//*[@id='bonus_template_select_3']";
        private static final String FIELD_BONUS_AMOUNT_XP = "//*[@id='amount']";
        private static final String FIELD_FREE_SPINS_AMOUNT_XP = "//*[@id='freespinscount']";
        private static final String BUTTON_ADD_BONUS_XP = "//*[@id='submit']";
        private static final String OK_TEXT = "AUTO_NO_ACCEPT_DECLINE";
        private static final String ACCEPT_DECLINE_TEXT = "AUTO_ACCEPT_DECLINE";
        private static final String LOSE_ON_WITHDRAW = "AUTO_LOSE_ON_WITHDRAW";
        private static final String RINGFENCING_TEXT = "AUTO_RINGFENCING";
        private static final String RADIOGROUP_BONUS_TYPE = "//*[@name='bonus_playtype'][REPLACER]";
        private static final String FREE_SPINS_MANUAL_NIKA = "Free Spins Manual Nika";

        public IMSAddBonusIframe(String iframeId) {
            super(iframeId);
        }

        public void sendBonus(Page pushMessages, String amount) {
            selectBonusTemplate(pushMessages);
            WebDriverUtils.clearAndInputTextToField(WebDriverFactory.getServerDriver(), ((pushMessages.equals(Page.freeSpins)) ? FIELD_FREE_SPINS_AMOUNT_XP : FIELD_BONUS_AMOUNT_XP), amount);
            WebDriverUtils.click(WebDriverFactory.getServerDriver(), BUTTON_ADD_BONUS_XP);
        }

        public void sendBonus(Page pushMessages, String amount, BonusType bonusType) {
            selectBonusType(bonusType);
            sendBonus(pushMessages, amount);
        }

        private void selectBonusTemplate(Page pushMessages) {
            String bonus = null;
            String dropdown = null;
            switch (pushMessages) {
                case okBonus:
                    bonus = OK_TEXT;
                    dropdown = DROPDOWN_BONUS_TEMPLATE_PLAYABLE_XP;
                    break;
                case acceptDeclineBonus:
                    bonus = ACCEPT_DECLINE_TEXT;
                    dropdown = DROPDOWN_BONUS_TEMPLATE_PLAYABLE_XP;
                    break;
                case loseOnWithdraw:
                    bonus = LOSE_ON_WITHDRAW;
                    dropdown = DROPDOWN_BONUS_TEMPLATE_PLAYABLE_XP;
                    break;
                case ringfencing:
                    bonus = RINGFENCING_TEXT;
                    dropdown = DROPDOWN_BONUS_TEMPLATE_PLAYABLE_XP;
                    break;
                case freeSpins:
                    bonus = FREE_SPINS_MANUAL_NIKA;
                    dropdown = DROPDOWN_BONUS_TEMPLATE_FREE_SPINS_XP;
                    break;
                default:
                    AbstractTest.failTest("Unknown bonus template requested");
            }
            WebDriverUtils.setDropdownOptionByText(WebDriverFactory.getServerDriver(), dropdown, bonus);
        }

        private void selectBonusType(BonusType bonusType) {
            int bonus = 1;
            switch (bonusType) {
                case playable:
                    bonus = 1;
                    break;
                case afterWager:
                    bonus = 2;
                    break;
                case freeSpins:
                    bonus = 3;
                    break;
                case spendOnly:
                    bonus = 4;
                    break;
                default:
                    AbstractTest.failTest("Unknown bonus type requested");
            }
            WebDriverUtils.waitForElement(WebDriverFactory.getServerDriver(), RADIOGROUP_BONUS_TYPE.replace("REPLACER", String.valueOf(bonus)));
//            WebDriverUtils.click(WebDriverFactory.getServerDriver(), RADIOGROUP_BONUS_TYPE.replace("REPLACER", String.valueOf(bonus)));
//            JavascriptExecutor executor = (JavascriptExecutor)WebDriverFactory.getServerDriver();
//            executor.executeScript("arguments[0].click();", WebDriverUtils.getElement(WebDriverFactory.getServerDriver(), RADIOGROUP_BONUS_TYPE.replace("REPLACER", String.valueOf(bonus))));
            WebDriverUtils.clickWithOffset(WebDriverFactory.getServerDriver(), WebDriverUtils.getElement(WebDriverFactory.getServerDriver(), RADIOGROUP_BONUS_TYPE.replace("REPLACER", String.valueOf(bonus))), 40, 0);
        }
    }
}
