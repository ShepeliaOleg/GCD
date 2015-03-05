package pageObjects.bonus;

import pageObjects.core.AbstractPortalPage;
import pageObjects.core.AbstractPortalPopup;
import pageObjects.registration.ReadTermsAndConditionsPopup;
import utils.WebDriverUtils;
import utils.core.WebDriverFactory;

public class BonusPage extends AbstractPortalPage{

	//private static final String PROMOTION_CODE_PORTLET = 	"//section[contains(@id, 'promotioncode')]";
	private static final String FIELD_PROMO= 				"//*[contains(@class, 'js-promo-value')]";
	private static final String BONUS_ROOT= 				"//div[@id='column-1']/div/div[contains(@class, 'bonus-multiview')]";
	private static final String BUTTON_SUBMIT_PROMO = 		"//button[@type='submit']";
	private static final String PROMOCODE = 				"AUTOFREE";
	//private static final String FREE_BONUS = 				"//a[@data-code='45508']";
	//private static final String OPT_IN = 					"//a[@data-code='45507']";
	//private static final String BUY_IN = 					"//a[@data-code='45609']";
	private static final String BONUS_LINK =				"//td[1]/a[@data-item=";
	private static final String TC_LINK =					"//td[3]/a[@data-item=";

	public BonusPage(){
		super(new String[]{BONUS_ROOT});
	}

	public void getBonus(String bonusID, String text) {
		FreeBonusPopup freeBonusPopup = (FreeBonusPopup) clickFreeBonusLink(bonusID);
		freeBonusPopup.clickGetBonus(text);
	}

	public void getBonus(String bonusID) {
		FreeBonusPopup freeBonusPopup = (FreeBonusPopup) clickFreeBonusLink(bonusID);
		freeBonusPopup.clickGetBonus();
	}

	public AbstractPortalPopup clickFreeBonusLink(String bonusID) {
		WebDriverUtils.waitForElement(WebDriverFactory.getPortalDriver(), BONUS_LINK + bonusID + "]");
		WebDriverUtils.click(BONUS_LINK + bonusID + "]");
		return new FreeBonusPopup();
	}

	public void clickTCLink(String bonusID) {
		WebDriverUtils.waitForElement(TC_LINK + bonusID + "]");
		WebDriverUtils.click(TC_LINK + bonusID + "]");
		AbstractPortalPopup popup = new ReadTermsAndConditionsPopup(true);
		popup.closePopup();
	}

	public String getBonusTitle(String bonusID) {
		WebDriverUtils.waitForElement(BONUS_LINK + bonusID + "]");
		return WebDriverUtils.getElementText(BONUS_LINK + bonusID + "]");
	}
/*
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

	public BuyInPopup clickBuyIn(){
		WebDriverUtils.click(BUY_IN);
		return new BuyInPopup();
	}*/
}
