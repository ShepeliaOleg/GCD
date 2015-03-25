package pageObjects.bonus;

import pageObjects.core.AbstractPortalPage;
import pageObjects.core.AbstractPortalPopup;
import pageObjects.registration.ReadTermsAndConditionsPopup;
import utils.WebDriverUtils;
import utils.core.WebDriverFactory;

import static utils.core.AbstractTest.assertEquals;

public class BonusPage extends AbstractPortalPage{

	//private static final String PROMOTION_CODE_PORTLET = 	"//section[contains(@id, 'promotioncode')]";
	private static final String FIELD_PROMO= 				"//*[contains(@class, 'js-promo-value')]";
	//private static final String BONUS_ROOT= 				"//div[@id='column-1']/div/div[contains(@class, 'bonus-multiview')]";
	private static final String BONUS_ROOT= 				"//div[@id='column-1']//div[contains(@class, 'bonus-multiview')]/table/thead/tr/th[text()='Promotion name']";
	private static final String BUTTON_SUBMIT_PROMO = 		"//button[@type='submit']";
	public static final String PROMOCODE = 				"AUTOFREE";
	private static final String BONUS_LINK =				"//td[1]/a[@data-item=";
	private static final String TC_LINK =					"//td[3]/a[@data-item=";
	protected static final String LOADER =          "//*[contains(@class, 'fn-loader')]";
	//private Float oldBalanceAmount = null;

	public BonusPage(){
		super(new String[]{BONUS_ROOT});
		//WebDriverUtils.waitForElementToDisappear(LOADER);
		//oldBalanceAmount = Float.parseFloat(new AbstractPortalPage().getBalanceAmount(false));
	}

	public void getBonus(String bonusID, String text) {
		FreeBonusPopup freeBonusPopup = (FreeBonusPopup) clickFreeBonusLink(bonusID, 1);
		freeBonusPopup.clickGetBonus(text);
	}

//	public void checkAmount(Float bonusAmount) {
//		new AbstractPortalPopup().closePopup();
//		assertEquals(String.format("%1$.2f", (bonusAmount + oldBalanceAmount)), new AbstractPortalPage().getBalanceAmount(true), "The current user amount isn't correspond expected bonus amount!");
// }

	public void getBonus(String bonusID, String text, int numCount) {
		FreeBonusPopup freeBonusPopup = (FreeBonusPopup) clickFreeBonusLink(bonusID, numCount);
		freeBonusPopup.clickGetBonus(text);
	}

	public OkBonusPopup getBonus(String bonusID) {
		FreeBonusPopup freeBonusPopup = (FreeBonusPopup) clickFreeBonusLink(bonusID, 1);
		freeBonusPopup.clickGetBonus();
		return new OkBonusPopup();
	}

	public void openAndDeclineBonus(String bonusID) {
		clickFreeBonusLink(bonusID, 1);
		new AbstractPortalPopup().closePopup();
	}

	public AbstractPortalPopup clickFreeBonusLink(String bonusID) {
		String xpath = "//div[@id='column-1']/div/div[1]"+BONUS_LINK + bonusID + "]";
		WebDriverUtils.waitForElement(WebDriverFactory.getPortalDriver(), xpath);
		WebDriverUtils.click(xpath);
		return new FreeBonusPopup();
	}

	public AbstractPortalPopup clickFreeBonusLink(String bonusID, int divNumber) {
		String xpath = "//div[@id='column-1']/div/div["+divNumber+"]"+BONUS_LINK + bonusID + "]";
		WebDriverUtils.waitForElement(WebDriverFactory.getPortalDriver(), xpath);
		WebDriverUtils.click(xpath);
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

	public boolean isBonusDisplayed(String bonusID) {

		return WebDriverUtils.isElementVisible(BONUS_LINK + bonusID + "]", 0);
	}
}
