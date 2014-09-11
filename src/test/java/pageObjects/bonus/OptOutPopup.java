package pageObjects.bonus;

import pageObjects.core.AbstractPopup;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 2/7/14
 */

public class OptOutPopup extends AbstractPopup{

	private static final String BUTTON_OPT_OUT = "//a[@data-action='OPTOUT']";

	public OptOutPopup(){
		super(new String[]{BUTTON_OPT_OUT});
	}

	public OptedOutPopup clickOptOut(){
		WebDriverUtils.click(BUTTON_OPT_OUT);
		return new OptedOutPopup();
	}
}
