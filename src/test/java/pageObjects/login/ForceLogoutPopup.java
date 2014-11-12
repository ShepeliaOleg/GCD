package pageObjects.login;

import pageObjects.HomePage;
import pageObjects.core.AbstractPortalPopup;
import utils.WebDriverUtils;

public class ForceLogoutPopup extends AbstractPortalPopup{

	public static final String LOGOUT_POPUP = 	"//*[@class='data-dialog']";
	private static final String CLOSE_BUTTON = 	ROOT_XP + "//*[@class='close']";

	public ForceLogoutPopup(){
		super(new String[]{LOGOUT_POPUP});
	}

	public HomePage close(){
		WebDriverUtils.click(CLOSE_BUTTON);
		WebDriverUtils.waitForElementToDisappear(CLOSE_BUTTON);
		return new HomePage();
	}
}
