package pageObjects.bonus;

import pageObjects.base.AbstractPopup;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 2/7/14
 */

public class FreeBonusPopup extends AbstractPopup{

	private static final String BUTTON_GET_FREE_BONUS = "//a[@data-action='FREE']";

	public FreeBonusPopup(){
		super(new String[]{BUTTON_GET_FREE_BONUS});
	}

	public OkBonusPopup clickFreeBonus(){
		WebDriverUtils.click(BUTTON_GET_FREE_BONUS);
		return new OkBonusPopup();
	}
}
