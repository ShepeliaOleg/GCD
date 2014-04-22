package pageObjects.external.ims;

import pageObjects.base.AbstractPage;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 7/24/13
 */

public class IMSPlayerManagementPage extends AbstractPage{

	private static final String BUTTON_PLAYER_SEARCH_XP="//*[@id='link_386']";

	public IMSPlayerManagementPage(){
		super(new String[]{BUTTON_PLAYER_SEARCH_XP});
	}

	public IMSPlayerSearchPage clickPlayerSearch(){
		WebDriverUtils.click(BUTTON_PLAYER_SEARCH_XP);
		return new IMSPlayerSearchPage();
	}
}
