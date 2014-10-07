package pageObjects.external.ims;

import org.testng.SkipException;
import pageObjects.core.AbstractPage;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 11/19/13
 */

public class IMSAdvancedPlayerSearchPage extends AbstractPage{

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
		WebDriverUtils.clearAndInputTextToField(FIELD_USERNAME_XP, username);
	}

	private void clickSearch(){
		WebDriverUtils.click(BUTTON_SEARCH_XP);
	}

	private void clickInternal(){
		WebDriverUtils.click(RADIOBUTTON_INTERNAL_XP);
	}

	private void clickBoth(){
		WebDriverUtils.click(RADIOBUTTON_BOTH_XP);
	}

	private void resetDate(){
		WebDriverUtils.click(FIELD_SIGNUP_DATE_XP);
		WebDriverUtils.waitForElement(DROPDOWN_DATE_XP);
		WebDriverUtils.click(LABEL_CLEAR_XP);
	}

	public IMSPlayerDetailsPage search(String username){
		String userLink = "//a[contains(text(), '"+username+"')] | //a[contains(text(), '"+username.toUpperCase()+"')]";
		if(!WebDriverUtils.isVisible(userLink, 0)){
			resetDate();
			clickBoth();
			inputUsername(username);
			clickSearch();
		}
        if(WebDriverUtils.isVisible(userLink, 30)) {
            WebDriverUtils.click(userLink);
        }else{
            throw new SkipException("User was not found on IMS");
        }
        return new IMSPlayerDetailsPage();
	}
}
