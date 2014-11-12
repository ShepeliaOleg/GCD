package pageObjects.bonus;

import pageObjects.core.AbstractPortalPopup;
import utils.WebDriverUtils;

public class OptInPopup extends AbstractPortalPopup{

	private static final String BUTTON_OPT_IN = "//a[@data-action='OPTIN']";

	public OptInPopup(){
		super(new String[]{BUTTON_OPT_IN});
	}

	public OptedInPopup clickOptIn(){
		WebDriverUtils.click(BUTTON_OPT_IN);
		return new OptedInPopup();
	}
}
