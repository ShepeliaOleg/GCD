package pageObjects.bonus;

import pageObjects.base.AbstractPopup;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 2/7/14
 */

public class OptInPopup extends AbstractPopup{

	private static final String BUTTON_OPT_IN = "//a[@data-action='OPTIN']";

	public OptInPopup(){
		super(new String[]{BUTTON_OPT_IN});
	}

	public OptedInPopup clickOptIn(){
		WebDriverUtils.click(BUTTON_OPT_IN);
		return new OptedInPopup();
	}
}
