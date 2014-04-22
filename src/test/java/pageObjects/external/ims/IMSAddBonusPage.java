package pageObjects.external.ims;

import enums.Page;
import pageObjects.base.AbstractPage;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 2/6/14
 */

public class IMSAddBonusPage extends AbstractPage{
	private static final String ADD_BONUS_IFRAME_XP="//*[@id='main-content']";
    private static final String ADD_BONUS_IFRAME_ID="main-content";

	public IMSAddBonusPage(){
		super(new String[]{ADD_BONUS_IFRAME_XP});
	}

	private IMSAddBonusIframe navigateToAddBonusIframe(){
		return new IMSAddBonusIframe(ADD_BONUS_IFRAME_ID);
	}

	public void addBonus(Page pushMessages, String amount){
		navigateToAddBonusIframe().sendBonus(pushMessages, amount);
		WebDriverUtils.acceptJavaScriptAlert();
	}
}
