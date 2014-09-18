package pageObjects.external.ims;

import pageObjects.core.AbstractPage;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 7/24/13
 */

public class IMSPlayerManagementPage extends AbstractPage{

	private static final String BUTTON_PLAYER_SEARCH_XP="//a[@href='/ims/PlayerFind']";
    private static final String BUTTON_ADVANCED_PLAYER_SEARCH_XP="//a[@href='/ims/UsersSearch']";

	public IMSPlayerManagementPage(){
		super(new String[]{BUTTON_PLAYER_SEARCH_XP});
	}

	public IMSPlayerSearchPage clickPlayerSearch(){
		WebDriverUtils.click(BUTTON_PLAYER_SEARCH_XP);
		return new IMSPlayerSearchPage();
	}

    public IMSAdvancedPlayerSearchPage clickAdvancedPlayerSearch(){
        WebDriverUtils.click(BUTTON_ADVANCED_PLAYER_SEARCH_XP);
        return new IMSAdvancedPlayerSearchPage();
    }
}
