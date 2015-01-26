package pageObjects.bonus;

import org.openqa.selenium.WebElement;
import pageObjects.core.AbstractPortalPopup;
import utils.WebDriverUtils;
import utils.core.WebDriverFactory;

public class FreeBonusPopup extends AbstractPortalPopup{

	private static final String BUTTON_GET_FREE_BONUS = "//a[@data-action='FREE']";
	private static final String BONUS_POPUP_ROOT = 		"//div[contains(@class, 'bonus-popup') and contains(@class, 'popup-modal')]";
	private static final String BUTTON_GET_BONUS = 		"//span[contains(@class, 'get-bonus')]";
	private static final String BUTTON_SHOW_TC = 		"//span[contains(@class, 'show-tc')]";

	public FreeBonusPopup(){
		super(new String[]{BONUS_POPUP_ROOT});
	}

	public void clickGetBonus(){
		WebDriverUtils.click(BONUS_POPUP_ROOT + BUTTON_GET_BONUS);
	}

	public void clickShowTC(){
		WebDriverUtils.click(BONUS_POPUP_ROOT + BUTTON_SHOW_TC);
	}
	//OBSOLETE
	public OkBonusPopup clickFreeBonus(){
		WebDriverUtils.click(BUTTON_GET_FREE_BONUS);
		return new OkBonusPopup();
	}

	public String buttonGetBonusIsDisplayed(){
		return WebDriverUtils.getElementText(ROOT_XP + BUTTON_GET_BONUS);
	}
}
