package pageObjects.bonus;

import pageObjects.core.AbstractPopup;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 2/7/14
 */

public class OptedOutPopup extends AbstractPopup{

	private static final String TEXT_BONUS_OPTED_OUT=	ROOT_XP+"//*[contains(text(), 'Bonus opted out successfully')]";
	public final static String CLOSE_XP=				"//div[preceding-sibling::h2[contains(text(), 'redemption')]]//a";

	public OptedOutPopup(){
		super(new String[]{TEXT_BONUS_OPTED_OUT});
	}

	public void closePopup(){
		WebDriverUtils.click(CLOSE_XP);
		WebDriverUtils.waitForElementToDisappear(CLOSE_XP);
	}
}
