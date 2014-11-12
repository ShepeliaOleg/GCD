package pageObjects.bonus;

import pageObjects.core.AbstractPortalPopup;
import utils.WebDriverUtils;

public class FreeBonusPopup extends AbstractPortalPopup{

	private static final String BUTTON_GET_FREE_BONUS = "//a[@data-action='FREE']";

	public FreeBonusPopup(){
		super(new String[]{BUTTON_GET_FREE_BONUS});
	}

	public OkBonusPopup clickFreeBonus(){
		WebDriverUtils.click(BUTTON_GET_FREE_BONUS);
		return new OkBonusPopup();
	}
}
