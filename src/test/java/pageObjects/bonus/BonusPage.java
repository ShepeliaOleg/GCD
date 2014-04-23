package pageObjects.bonus;

import pageObjects.base.AbstractPage;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 2/7/14
 */

public class BonusPage extends AbstractPage{

	private static final String PROMOTION_CODE_PORTLET = 	"//section[contains(@id, 'promotioncode')]";
	private static final String FIELD_PROMO= 				"//*[contains(@class, 'js-promo-value')]";
	private static final String BUTTON_SUBMIT_PROMO = 		"//button[@type='submit']";
	private static final String PROMOCODE = 				"PORTALFREE";
	private static final String FREE_BONUS = 				"//a[@data-code='45508']";
	private static final String OPT_IN = 					"//a[@data-code='45507']";
	private static final String BUY_IN = 					"//a[@data-code='45508']";

	public BonusPage(){
		super(new String[]{PROMOTION_CODE_PORTLET});
	}

	public OkBonusPopup submitCode(){
		inputPromoCode();
		clickSubmit();
		return new OkBonusPopup();
	}

	private void inputPromoCode(){
		WebDriverUtils.clearAndInputTextToField(FIELD_PROMO, PROMOCODE);
	}

	private void clickSubmit(){
		WebDriverUtils.click(BUTTON_SUBMIT_PROMO);
	}

	public FreeBonusPopup clickFreeBonus(){
		WebDriverUtils.click(FREE_BONUS);
		return new FreeBonusPopup();
	}

	public OptInPopup clickOptIn(){
		WebDriverUtils.click(OPT_IN);
		return new OptInPopup();
	}

	public OptOutPopup clickOptOutBonus(){
		WebDriverUtils.click(OPT_IN);
		return new OptOutPopup();
	}

	public FreeBonusPopup clickBuyIn(){
		WebDriverUtils.click(BUY_IN);
		return new FreeBonusPopup();
	}
}
