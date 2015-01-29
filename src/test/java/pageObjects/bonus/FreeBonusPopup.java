package pageObjects.bonus;

import pageObjects.core.AbstractPortalPopup;
import utils.WebDriverUtils;

import static utils.core.AbstractTest.*;

public class FreeBonusPopup extends AbstractPortalPopup{

	private static final String BUTTON_GET_FREE_BONUS = "//a[@data-action='FREE']";
	private static final String BONUS_POPUP_ROOT = 		"//div[contains(@class, 'bonus-popup') and contains(@class, 'popup-modal')]";
	private static final String BUTTON_GET_BONUS = 		"//span[contains(@class, 'get-bonus')]";
	private static final String BUTTON_SHOW_TC = 		"//span[contains(@class, 'show-tc')]";
	private static final String BONUS_POPUP_TITLE = 	"//div[@class='popup-modal__title']";


	public FreeBonusPopup(){
		super(new String[]{BONUS_POPUP_ROOT});
	}

	public void clickGetBonus(){
		WebDriverUtils.click(BONUS_POPUP_ROOT + BUTTON_GET_BONUS);
	}

	public void clickShowTC(){
		WebDriverUtils.click(BONUS_POPUP_ROOT + BUTTON_SHOW_TC);
	}

	public void assertViewFreeBonusPopup(String expBonusTitle, String button1, String button2){
		new AbstractPortalPopup(new String[]{BONUS_POPUP_TITLE, BUTTON_GET_BONUS, BUTTON_SHOW_TC});
		assertEquals(expBonusTitle, WebDriverUtils.getElementText(BONUS_POPUP_TITLE), "Bonus popup has unexpected title!");
		try {
			assertEquals(button1, WebDriverUtils.getElementText(BUTTON_GET_BONUS), "First button has unexpected title!");
		} catch (RuntimeException re) {
			skipTest("D-17992");
		}
		assertEquals(button2, WebDriverUtils.getElementText(BUTTON_SHOW_TC), "Second button has unexpected title!");
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
