package pageObjects.external.ims;

import pageObjects.core.AbstractPage;
import utils.WebDriverUtils;

/**
 * Created by sergiich on 3/5/14.
 */

public class IMSLoginDatabasePage extends AbstractPage{

    private static final String LABEL_LOGIN_LOGOUT_DATABASE_XP=	"//*[contains(text(), 'Login/Logout Messages List')]";
	private static final String LABEL_WELCOME_MESSAGE_XPATH = 	"//a[contains(text(), 'AUTO_WELCOME')]";
	private static final String DROPDOWN_CASINO_XP				="//*['@id='search_casino']";
	private static final String DROPDOWN_CLIENT_TYPE_XP=		"//*['@id='search_clientType']";
	private static final String DROPDOWN_CLIENT_PLATFORM_XP=	"//*['@id='search_clientPlatform']";
	private static final String BUTTON_SHOW_MESSAGES_XP=		"//*['@id='search-submit']";
	private static final String LOADER_POPUP_XP = 				"//*[@id='progressBarFrame_c' and contains(@style, 'visible')]";

	private static final String CASINO = 						"81001";
	private static final String CLIENT_PLATFORM = 				"web";
	private static final String CLIENT_TYPE = 					"portal";

    public IMSLoginDatabasePage(){
        super(new String[]{LABEL_LOGIN_LOGOUT_DATABASE_XP});
    }

	private void chooseCasino(){
		WebDriverUtils.setDropdownOptionByValue(DROPDOWN_CASINO_XP, CASINO);
	}
	private void choosePlatform(){
		WebDriverUtils.setDropdownOptionByValue(DROPDOWN_CLIENT_PLATFORM_XP, CLIENT_PLATFORM);
	}
	private void chooseClientType(){
		WebDriverUtils.setDropdownOptionByValue(DROPDOWN_CLIENT_TYPE_XP, CLIENT_TYPE);
	}
	private void clickShowMessages(){
		WebDriverUtils.click(BUTTON_SHOW_MESSAGES_XP);
	}
	private IMSWelcomeMessagePage clickMessage(){
		WebDriverUtils.waitForElement(LABEL_WELCOME_MESSAGE_XPATH);
		WebDriverUtils.waitForElementToDisappear(LOADER_POPUP_XP);
		WebDriverUtils.click(LABEL_WELCOME_MESSAGE_XPATH);
		return new IMSWelcomeMessagePage();
	}

	public IMSWelcomeMessagePage navigateToWelcomeMessage(){
		chooseCasino();
		chooseClientType();
		choosePlatform();
		clickShowMessages();
		return clickMessage();
	}

}
