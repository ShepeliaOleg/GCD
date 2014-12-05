package pageObjects.external.ims;

import pageObjects.core.AbstractServerPage;
import utils.WebDriverUtils;
import utils.core.WebDriverFactory;

public class IMSLoginDatabasePage extends AbstractServerPage {

    private static final String LABEL_LOGIN_LOGOUT_DATABASE_XP=	"//*[contains(text(), 'Login/Logout Messages List')]";
    public static final String[] MESSAGES = {
            "//*[contains(text(), 'AUTO_WELCOME_1')]",
            "//*[contains(text(), 'AUTO_WELCOME_2')]",
            "//*[contains(text(), 'AUTO_WELCOME_3')]"};
    private static final String DROPDOWN_CASINO_XP =            "//*[@id='search_casino']";
	private static final String DROPDOWN_CLIENT_TYPE_XP=		"//*[@id='search_clientType']";
	private static final String DROPDOWN_CLIENT_PLATFORM_XP=	"//*[@id='search_clientPlatform']";
    private static final String CHECKBOX_FROZEN_XP =	        "//*[@id='search_show-FROZEN']";
    private static final String BUTTON_SEARCH_XP =		        "//*[@id='search-submit']";
	private static final String LOADER_POPUP_XP = 				"//*[@id='progressBarFrame_c' and contains(@style, 'visible')]";

	private static final String CASINO = 						"810013";
	private static final String CLIENT_PLATFORM = 				"mobile";
	private static final String CLIENT_TYPE = 					"casino";

    public IMSLoginDatabasePage(){
        super(new String[]{LABEL_LOGIN_LOGOUT_DATABASE_XP});
    }

	private IMSWelcomeMessagePage clickMessage(String xpath){
		WebDriverUtils.click(WebDriverFactory.getServerDriver(), xpath);
		return new IMSWelcomeMessagePage();
	}

    private void setup(boolean state){
        WebDriverUtils.setDropdownOptionByValue(WebDriverFactory.getServerDriver(), DROPDOWN_CASINO_XP, CASINO);
        WebDriverUtils.setDropdownOptionByValue(WebDriverFactory.getServerDriver(), DROPDOWN_CLIENT_TYPE_XP, CLIENT_TYPE);
        WebDriverUtils.setDropdownOptionByValue(WebDriverFactory.getServerDriver(), DROPDOWN_CLIENT_PLATFORM_XP, CLIENT_PLATFORM);
        WebDriverUtils.setCheckBoxState(WebDriverFactory.getServerDriver(), CHECKBOX_FROZEN_XP, state);
        WebDriverUtils.click(WebDriverFactory.getServerDriver(), BUTTON_SEARCH_XP);
        WebDriverUtils.waitForElementToDisappear(WebDriverFactory.getServerDriver(), LOADER_POPUP_XP);
    }

    public void unfreeze(int count) {
        setup(true);
        for(int i=0;i<count;i++){
            if(WebDriverUtils.isVisible(WebDriverFactory.getServerDriver(), MESSAGES[i])){
                clickMessage(MESSAGES[i]).setFreezeStateAndUpdate(false);
            }
        }
    }

    public void freezeAll(){
        setup(false);
        for(String xpath:MESSAGES){
            if(WebDriverUtils.isVisible(WebDriverFactory.getServerDriver(), xpath)){
                clickMessage(xpath).setFreezeStateAndUpdate(true);
            }
        }
    }

}
