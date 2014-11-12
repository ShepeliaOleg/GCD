package pageObjects.bonus;

import pageObjects.core.AbstractPortalPopup;
import utils.WebDriverUtils;

public class OptOutPopup extends AbstractPortalPopup{

	private static final String BUTTON_OPT_OUT = "//a[@data-action='OPTOUT']";

	public OptOutPopup(){
		super(new String[]{BUTTON_OPT_OUT});
	}

	public OptedOutPopup clickOptOut(){
		WebDriverUtils.click(BUTTON_OPT_OUT);
		return new OptedOutPopup();
	}
}
