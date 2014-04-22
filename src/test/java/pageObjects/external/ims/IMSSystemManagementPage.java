package pageObjects.external.ims;

import pageObjects.base.AbstractPage;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 8/16/13
 */

public class IMSSystemManagementPage extends AbstractPage{

	private static final String BUTTON_TERMS_AND_CONDITIONS_XP="//*[@id='link_239']";

	public IMSSystemManagementPage(){
		super(new String[]{BUTTON_TERMS_AND_CONDITIONS_XP});
	}

	public IMSTermsAndConditionsSearchPage clickTermsAndConditions(){
		WebDriverUtils.click(BUTTON_TERMS_AND_CONDITIONS_XP);
		return new IMSTermsAndConditionsSearchPage();
	}
}
