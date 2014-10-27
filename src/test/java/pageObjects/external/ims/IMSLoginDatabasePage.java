package pageObjects.external.ims;

import pageObjects.core.AbstractPage;
import utils.WebDriverUtils;

public class IMSLoginDatabasePage extends AbstractPage{

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

	private static final String CASINO = 						"800075";
	private static final String CLIENT_PLATFORM = 				"mobile";
	private static final String CLIENT_TYPE = 					"casino";

    public IMSLoginDatabasePage(){
        super(new String[]{LABEL_LOGIN_LOGOUT_DATABASE_XP});
    }

	private IMSWelcomeMessagePage clickMessage(String xpath){
		WebDriverUtils.click(xpath);
		return new IMSWelcomeMessagePage();
	}

    private void setup(boolean state){
        WebDriverUtils.setDropdownOptionByValue(DROPDOWN_CASINO_XP, CASINO);
        WebDriverUtils.setDropdownOptionByValue(DROPDOWN_CLIENT_TYPE_XP, CLIENT_TYPE);
        WebDriverUtils.setDropdownOptionByValue(DROPDOWN_CLIENT_PLATFORM_XP, CLIENT_PLATFORM);
        WebDriverUtils.setCheckBoxState(CHECKBOX_FROZEN_XP, state);
        WebDriverUtils.click(BUTTON_SEARCH_XP);
        WebDriverUtils.waitForElementToDisappear(LOADER_POPUP_XP);
    }

    public void unfreeze(int count) {
        setup(true);
        for(int i=0;i<count;i++){
            if(WebDriverUtils.isVisible(MESSAGES[i])){
                clickMessage(MESSAGES[i]).setFreezeStateAndUpdate(false);
            }
        }
    }

    public void freezeAll(){
        setup(false);
        for(String xpath:MESSAGES){
            if(WebDriverUtils.isVisible(xpath)){
                clickMessage(xpath).setFreezeStateAndUpdate(true);
            }
        }
    }

}
