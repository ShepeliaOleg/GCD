package pageObjects.external.ims;

import pageObjects.core.AbstractServerPage;
import utils.WebDriverUtils;
import utils.core.AbstractTest;
import utils.core.WebDriverFactory;

public class IMSAdvancedPlayerSearchPage extends AbstractServerPage {

	private static final String ROOT_XP=				"//*[@id='search-border_body']";
	private static final String FIELD_USERNAME_XP=		"//*[@id='search_username']";
	private static final String BUTTON_SEARCH_XP=		"//*[@id='search-submit']";
	private static final String RADIOBUTTON_INTERNAL_XP="//*[@id='search_internalAccount-True']";
	private static final String RADIOBUTTON_BOTH_XP= 	"//*[@id='search_internalAccount-Both-label']";
	private static final String FIELD_SIGNUP_DATE_XP = 	"//*[@id='search_signupDate_to']";
	private static final String DROPDOWN_DATE_XP = 		"//*[@id='wrapper_0']";
	private static final String LABEL_CLEAR_XP = 		"//*[@class='smdr-clear']";


	public IMSAdvancedPlayerSearchPage(){
		super(new String[]{ROOT_XP, BUTTON_SEARCH_XP});
	}

	private void inputUsername(String username){
		WebDriverUtils.clearAndInputTextToField(WebDriverFactory.getServerDriver(), FIELD_USERNAME_XP, username);
	}

	private void clickSearch(){
		WebDriverUtils.click(WebDriverFactory.getServerDriver(), BUTTON_SEARCH_XP);
	}

	private void clickInternal(){
		WebDriverUtils.click(WebDriverFactory.getServerDriver(), RADIOBUTTON_INTERNAL_XP);
	}

	private void clickBoth(){
		WebDriverUtils.click(WebDriverFactory.getServerDriver(), RADIOBUTTON_BOTH_XP);
	}

	private void resetDate(){
		WebDriverUtils.click(WebDriverFactory.getServerDriver(), FIELD_SIGNUP_DATE_XP);
		WebDriverUtils.waitForElement(WebDriverFactory.getServerDriver(), DROPDOWN_DATE_XP);
		WebDriverUtils.click(WebDriverFactory.getServerDriver(), LABEL_CLEAR_XP);
	}

	public IMSPlayerDetailsPage search(String username){
		String userLink = "//a[contains(text(), '"+username+"')] | //a[contains(text(), '"+username.toUpperCase()+"')]";
		if(!WebDriverUtils.isVisible(WebDriverFactory.getServerDriver(), userLink, 0)){
			resetDate();
			clickBoth();
			inputUsername(username);
			clickSearch();
		}
        if(WebDriverUtils.isVisible(WebDriverFactory.getServerDriver(), userLink, 30)) {
            WebDriverUtils.click(WebDriverFactory.getServerDriver(), userLink);
        }else{
            AbstractTest.skipTest("User was not found on IMS");
        }
        return new IMSPlayerDetailsPage();
	}
}
