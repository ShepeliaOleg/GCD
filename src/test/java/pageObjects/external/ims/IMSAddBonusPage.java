package pageObjects.external.ims;

import enums.Page;
import pageObjects.core.AbstractServerIframe;
import pageObjects.core.AbstractServerPage;
import utils.WebDriverUtils;
import utils.core.AbstractTest;
import utils.core.WebDriverFactory;

public class IMSAddBonusPage extends AbstractServerPage {
	private static final String ADD_BONUS_IFRAME_XP="//*[@id='main-content']";
    private static final String ADD_BONUS_IFRAME_ID="main-content";

	public IMSAddBonusPage(){
		super(new String[]{ADD_BONUS_IFRAME_XP});
	}

	public IMSPlayerDetailsPage addBonus(Page pushMessages, String amount){
        new IMSAddBonusIframe(ADD_BONUS_IFRAME_ID).sendBonus(pushMessages, amount);
		WebDriverUtils.acceptJavaScriptAlert(WebDriverFactory.getServerDriver());
        WebDriverUtils.waitFor();
        WebDriverUtils.acceptJavaScriptAlert(WebDriverFactory.getServerDriver());
        WebDriverUtils.waitFor(1500);
        return new IMSPlayerDetailsPage();
	}

    public class IMSAddBonusIframe extends AbstractServerIframe {
        private static final String DROPDOWN_BONUS_TEMPLATE_XP = "//*[@id='bonus_template_select_1']";
        private static final String FIELD_BONUS_AMOUNT_XP = "//*[@id='amount']";
        private static final String BUTTON_ADD_BONUS_XP = "//*[@id='submit']";
        private static final String OK_TEXT =               "AUTO_NO_ACCEPT_DECLINE";
        private static final String ACCEPT_DECLINE_TEXT =   "AUTO_ACCEPT_DECLINE";
        private static final String LOSE_ON_WITHDRAW =      "AUTO_LOSE_ON_WITHDRAW";
        private static final String RINGFENCING_TEXT =      "AUTO_RINGFENCING";

        public IMSAddBonusIframe(String iframeId) {
            super(iframeId);
        }

        public void sendBonus(Page pushMessages, String amount) {
            selectBonusType(pushMessages);
            WebDriverUtils.clearAndInputTextToField(WebDriverFactory.getServerDriver(), FIELD_BONUS_AMOUNT_XP, amount);
            WebDriverUtils.click(WebDriverFactory.getServerDriver(), BUTTON_ADD_BONUS_XP);
        }

        private void selectBonusType(Page pushMessages) {
            String bonus = null;
            switch (pushMessages){
                case okBonus : bonus=OK_TEXT;
                    break;
                case acceptDeclineBonus : bonus=ACCEPT_DECLINE_TEXT;
                    break;
                case loseOnWithdraw : bonus=LOSE_ON_WITHDRAW;
                    break;
                case ringfencing : bonus=RINGFENCING_TEXT;
                    break;
                default : AbstractTest.failTest("Unknown bonus type requested");
            }
            WebDriverUtils.setDropdownOptionByText(WebDriverFactory.getServerDriver(), DROPDOWN_BONUS_TEMPLATE_XP, bonus);
        }
    }
}
