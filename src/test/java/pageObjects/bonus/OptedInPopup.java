package pageObjects.bonus;

import pageObjects.core.AbstractPortalPage;
import pageObjects.core.AbstractPortalPopup;
import utils.WebDriverUtils;
import utils.core.WebDriverFactory;

import static utils.core.AbstractTest.assertEquals;
import static utils.core.AbstractTest.assertFalse;
import static utils.core.AbstractTest.assertTrue;

public class OptedInPopup extends AbstractPortalPopup{

	private static final String TEXT_BONUS_APPLIED = ROOT_XP +	"//*[contains(text(), 'Buy in with')]";
	//public final static String CLOSE_XP=			"//div[preceding-sibling::h2[contains(text(), 'redemption')]]//a";
	public static final String CLOSE_XP = ROOT_XP +				"//button[contains(@class, 'fn-close')]";
	public final static String BUY_IN_XP = ROOT_XP +			"//button[contains(@class, 'fn-get-buy-in-bonus')]";
	//public final static String TERMS_CHECKBOX = ROOT_XP + 		"//input[@id='terms-checkbox']";
	public static final String TERMS_CHECKBOX = ROOT_XP + 		"//span[@class='js-checkbox']";
	public static final String TERMS_LINK = ROOT_XP +	 		"//a[contains(@class, 'fn-show-tc')]";
	public static final String TITLE_TEXT = ROOT_XP + 			"/div[contains(@class, 'title')][contains(text(), '";
	public static final String INPUT_AMOUNT = ROOT_XP + 		"//input[@name='amount']";
	public static final String INFO_MSG_XP = ROOT_XP + 			"//p[@class='message infoMessage']";
	private Float oldBalanceAmount = null;

	public OptedInPopup(){
		super(new String[]{TEXT_BONUS_APPLIED});
		oldBalanceAmount = Float.parseFloat(new AbstractPortalPage().getBalanceAmount(false));
	}

	public void assertOptedInPopupIsCorrect(String buyInBonusID, String expTitle, Float bonusAmount, String info){
		assertEquals(expTitle, WebDriverUtils.getElementText(TITLE_TEXT + buyInBonusID + "')]"), "Bonus popup has unexpected title!");
		assertTrue(WebDriverUtils.isElementVisible(INPUT_AMOUNT, 0), "WebElement 'amount' field was not visible");
		assertEquals(info, WebDriverUtils.getElementText(INFO_MSG_XP), "Get bonus value was not as expected");
		assertTrue(WebDriverUtils.isElementVisible(BUY_IN_XP, 0), "WebElement 'Buy-In' button was not visible");
		assertTrue(WebDriverUtils.isElementVisible(CLOSE_XP, 0), "WebElement 'Close' button was not visible");
		assertTrue(WebDriverUtils.isElementVisible(TERMS_CHECKBOX, 0), "WebElement 'Terms & Condition' checkbox was not visible");
		assertTrue(WebDriverUtils.isElementVisible(TERMS_LINK, 0), "WebElement 'Terms & Condition' link was not visible");
		if (null == bonusAmount) {
			assertEquals(String.format("%1$.2f", oldBalanceAmount), WebDriverUtils.getInputFieldText(INPUT_AMOUNT), "Amount value was not as expected");
		} else assertEquals(String.format("%1$.2f", bonusAmount), WebDriverUtils.getInputFieldText(INPUT_AMOUNT), "Amount value was not as expected");
	}

	public void closePopup(){
		WebDriverUtils.click(CLOSE_XP);
		WebDriverUtils.waitForElementToDisappear(CLOSE_XP);
	}

	public void validate(boolean checkbox){
		WebDriverUtils.setCheckBoxState(WebDriverFactory.getPortalDriver(), TERMS_CHECKBOX, checkbox);
		clickBuyInButton();
	}

	public void clickBuyInButton(){
		WebDriverUtils.click(BUY_IN_XP);
	}

	public void getBonusAndCheckAmount(Float bonusAmount) {
		confirmBuyInBonus();
		new AbstractPortalPopup().closePopup();
		assertEquals(String.format("%1$.2f", (bonusAmount + oldBalanceAmount)), new AbstractPortalPage().getBalanceAmount(true), "The current user amount isn't correspond expected bonus amount!");
	}

	public void confirmBuyInBonus(){
		WebDriverUtils.setCheckBoxState(WebDriverFactory.getPortalDriver(), TERMS_CHECKBOX, true);
		clickBuyInButton();
		WebDriverUtils.waitForElementToDisappear(BUY_IN_XP);
	}

	public void assertErrorMessage(String expText){
		assertTrue(WebDriverUtils.isElementVisible(ROOT_XP + PORTLET_ERROR_XP, 0), "Tooltip error message was not displayed");
		assertEquals(expText, WebDriverUtils.getElementText(WebDriverFactory.getPortalDriver(), ROOT_XP + PORTLET_ERROR_XP), "Tooltip error message was not as expected");	}
}
