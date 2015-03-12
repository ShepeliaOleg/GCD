package pageObjects.bonus;

import pageObjects.core.AbstractPortalPopup;
import utils.WebDriverUtils;
import utils.core.WebDriverFactory;

public class OptedInPopup extends AbstractPortalPopup{

	private static final String TEXT_BONUS_APPLIED = ROOT_XP +	"//*[contains(text(), 'Buy in with')]";
	//public final static String CLOSE_XP=			"//div[preceding-sibling::h2[contains(text(), 'redemption')]]//a";
	public final static String CLOSE_XP = ROOT_XP +				"//button[contains(@class, 'fn-close')]";
	public final static String BUY_IN_XP = ROOT_XP +			"//button[contains(@class, 'fn-get-buy-in-bonus')]";
	public final static String TERMS_CHECKBOX = ROOT_XP + 		"//*[@id='terms-checkbox']";

	public OptedInPopup(){
		super(new String[]{TEXT_BONUS_APPLIED});
	}

	public void closePopup(){
		WebDriverUtils.click(CLOSE_XP);
		WebDriverUtils.waitForElementToDisappear(CLOSE_XP);
	}

	public void clickBuyInButton(){
		WebDriverUtils.click(BUY_IN_XP);
	}

	public void getBuyInBonus(){
		WebDriverUtils.setCheckBoxState(WebDriverFactory.getPortalDriver(), TERMS_CHECKBOX, true);
		clickBuyInButton();
		WebDriverUtils.waitForElementToDisappear(BUY_IN_XP);
	}
}
