package pageObjects.external.ims;

import pageObjects.core.AbstractServerPage;
import utils.WebDriverUtils;
import utils.core.WebDriverFactory;

public class IMSPlayerManagementPage extends AbstractServerPage {

	private static final String BUTTON_PLAYER_SEARCH_XP="//a[@href='/ims/PlayerFind']";
    private static final String BUTTON_ADVANCED_PLAYER_SEARCH_XP="//a[@href='/ims/UsersSearch']";

	public IMSPlayerManagementPage(){
		super(new String[]{BUTTON_PLAYER_SEARCH_XP});
	}

	public IMSPlayerSearchPage clickPlayerSearch(){
		WebDriverUtils.click(WebDriverFactory.getServerDriver(), BUTTON_PLAYER_SEARCH_XP);
		return new IMSPlayerSearchPage();
	}

    public IMSAdvancedPlayerSearchPage clickAdvancedPlayerSearch(){
        WebDriverUtils.click(WebDriverFactory.getServerDriver(), BUTTON_ADVANCED_PLAYER_SEARCH_XP);
        return new IMSAdvancedPlayerSearchPage();
    }
}
