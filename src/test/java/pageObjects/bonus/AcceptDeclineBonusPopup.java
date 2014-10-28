package pageObjects.bonus;

import pageObjects.HomePage;
import pageObjects.core.AbstractPopup;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 2/6/14
 */

public class AcceptDeclineBonusPopup extends AbstractPopup{

	private static final String BUTTON_ACCEPT= 	ROOT_XP + "//*[@class='accept']";
	private static final String BUTTON_DECLINE= ROOT_XP + "//*[@class='decline']";
	public static final String BONUS_TITLE_XP=  TITLE_XP + "[contains(text(), 'Congratulations')]";

	public AcceptDeclineBonusPopup(){
		super(new String[]{BONUS_TITLE_XP, BUTTON_ACCEPT});
	}

	public HomePage accept(){
		WebDriverUtils.click(BUTTON_ACCEPT);
        WebDriverUtils.waitForElementToDisappear(ROOT_XP);
		return new HomePage();
	}

	public HomePage decline(){
		WebDriverUtils.click(BUTTON_DECLINE);
		return new HomePage();
	}
}
