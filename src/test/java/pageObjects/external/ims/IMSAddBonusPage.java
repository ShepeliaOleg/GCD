package pageObjects.external.ims;

import enums.Page;
import pageObjects.core.AbstractIframe;
import pageObjects.core.AbstractPage;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

public class IMSAddBonusPage extends AbstractPage{
	private static final String ADD_BONUS_IFRAME_XP="//*[@id='main-content']";
    private static final String ADD_BONUS_IFRAME_ID="main-content";

	public IMSAddBonusPage(){
		super(new String[]{ADD_BONUS_IFRAME_XP});
	}

	public IMSPlayerDetailsPage addBonus(Page pushMessages, String amount){
        new IMSAddBonusIframe(ADD_BONUS_IFRAME_ID).sendBonus(pushMessages, amount);
		WebDriverUtils.acceptJavaScriptAlert();
        return new IMSPlayerDetailsPage();
	}

    public class IMSAddBonusIframe extends AbstractIframe {
        private static final String DROPDOWN_BONUS_TEMPLATE_XP = "//*[@id='bonus_template_select_1']";
        private static final String FIELD_BONUS_AMOUNT_XP = "//*[@id='amount']";
        private static final String BUTTON_ADD_BONUS_XP = "//*[@id='submit']";
        private static final String OK_TEXT = "AUTO_NO_ACCEPT_DECLINE";
        private static final String ACCEPT_DECLINE_TEXT = "AUTO_ACCEPT_DECLINE";

        public IMSAddBonusIframe(String iframeId) {
            super(iframeId);
        }

        public void sendBonus(Page pushMessages, String amount) {
            selectBonusType(pushMessages);
            WebDriverUtils.clearAndInputTextToField(FIELD_BONUS_AMOUNT_XP, amount);
            WebDriverUtils.click(BUTTON_ADD_BONUS_XP);
        }

        private void selectBonusType(Page pushMessages) {
            String bonus = null;
            switch (pushMessages){
                case okBonus : bonus=OK_TEXT;
                    break;
                case acceptDeclineBonus : bonus=ACCEPT_DECLINE_TEXT;
                    break;
                default : AbstractTest.failTest("Unknown bonus type requested");
            }
            WebDriverUtils.setDropdownOptionByText(DROPDOWN_BONUS_TEMPLATE_XP, bonus);
        }
    }
}
