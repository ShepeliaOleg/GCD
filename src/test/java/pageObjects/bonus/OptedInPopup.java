package pageObjects.bonus;

import pageObjects.base.AbstractPopup;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 2/7/14
 */

public class OptedInPopup extends AbstractPopup{

	private static final String TEXT_BONUS_APPLIED=	ROOT_XP+"//*[contains(text(), 'Bonus applied successfully')]";
	public final static String CLOSE_XP=			"//div[preceding-sibling::h2[contains(text(), 'redemption')]]//a";

	public OptedInPopup(){
		super(new String[]{TEXT_BONUS_APPLIED});
	}

	public void closePopup(){
		WebDriverUtils.click(CLOSE_XP);
		WebDriverUtils.waitForElementToDisappear(CLOSE_XP);
	}
}
