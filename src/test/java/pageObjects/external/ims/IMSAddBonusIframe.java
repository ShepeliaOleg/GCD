package pageObjects.external.ims;

import enums.Page;
import pageObjects.core.AbstractIframe;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

/**
 * User: sergiich
 * Date: 2/6/14
 */

public class IMSAddBonusIframe extends AbstractIframe{
	private static final String DROPDOWN_BONUS_TEMPLATE = 	"//*[@id='bonus_template_select_1']";
	private static final String FIELD_BONUS_AMOUNT = 		"//*[@id='amount']";
	private static final String BUTTON_ADD_BONUS = 			"//*[@id='submit']";
	private static final String OK_VALUE= 					"42747";
	private static final String ACCEPT_DECLINE_VALUE= 		"44327";


	public IMSAddBonusIframe(String iframeId){
		super(iframeId);
	}

	public void sendBonus(Page pushMessages, String amount){
		selectBonusType(pushMessages);
		enterAmount(amount);
		clickSubmit();
	}

	private void selectBonusType(Page pushMessages){
		if(pushMessages == Page.okBonus){
			WebDriverUtils.setDropdownOptionByValue(DROPDOWN_BONUS_TEMPLATE, OK_VALUE);
		}else if (pushMessages == Page.acceptDeclineBonus) {
			WebDriverUtils.setDropdownOptionByValue(DROPDOWN_BONUS_TEMPLATE, ACCEPT_DECLINE_VALUE);
		}else{
            AbstractTest.failTest("Unknown bonus type requested");
		}
	}

	private void enterAmount(String amount){
		WebDriverUtils.clearAndInputTextToField(FIELD_BONUS_AMOUNT, amount);
	}

	private void clickSubmit(){
		WebDriverUtils.click(BUTTON_ADD_BONUS);
	}

}
