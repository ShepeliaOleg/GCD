package pageObjects.external.ims;

import pageObjects.base.AbstractPage;
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

	public IMSPlayerDetailsPage search(String username){
		String userLink = "//a[contains(text(), '"+username+"')]";
		if(!WebDriverUtils.isVisible(userLink)){
			clickBoth();
			inputUsername(username);
			clickSearch();
			WebDriverUtils.waitForElement(userLink, 30);
		}
		WebDriverUtils.click(userLink);
		return new IMSPlayerDetailsPage();
	}
}
