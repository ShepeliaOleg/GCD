package pageObjects.bonus;

import pageObjects.core.AbstractPortalPopup;
import utils.WebDriverUtils;

public class OptedOutPopup extends AbstractPortalPopup{

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
