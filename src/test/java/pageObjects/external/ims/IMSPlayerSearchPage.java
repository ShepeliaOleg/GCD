package pageObjects.external.ims;

import pageObjects.core.AbstractServerPage;
import utils.WebDriverUtils;
import utils.core.WebDriverFactory;

public class IMSPlayerSearchPage extends AbstractServerPage {

	private static final String ROOT_XP=					"//*[@id='search']";
	private static final String BUTTON_SEARCH_XP=			"//*[@id='search-submit']";
	private static final String FIELD_SEARCH_XP=			"//*[@id='smartSearchField']";
	private static final String LINK_ADVANCED_SEARCH_XP = 	"//a[contains(text(), 'Advanced Player Search')]";

	public IMSPlayerSearchPage(){
		super(new String[]{ROOT_XP, BUTTON_SEARCH_XP});
	}

	private IMSPlayerDetailsPage clickSearch(){
		WebDriverUtils.click(WebDriverFactory.getServerDriver(), BUTTON_SEARCH_XP);
		return new IMSPlayerDetailsPage();
	}

	private IMSAdvancedPlayerSearchPage clickAdvancedSearch(){
		WebDriverUtils.click(WebDriverFactory.getServerDriver(), LINK_ADVANCED_SEARCH_XP);
		return new IMSAdvancedPlayerSearchPage();
	}

	private void setFieldSearch(String xpath, String playerLogin){
		WebDriverUtils.clearAndInputTextToField(WebDriverFactory.getServerDriver(), xpath, playerLogin);
	}

	public IMSPlayerDetailsPage search(String playerLogin){
		IMSAdvancedPlayerSearchPage IMSAdvancedPlayerSearchPage= clickAdvancedSearch();
		return IMSAdvancedPlayerSearchPage.search(playerLogin);
	}
}
