package pageObjects;

import pageObjects.base.AbstractPage;

/**
 * User: sergiich
 * Date: 4/9/14
 */

public class HomePage extends AbstractPage{
	private static final String NAVIGATION_PANEL= 	"//*[@id = 'nav']";
	private static final String LOGO= 				"//*[contains(@class, 'logo')]";

	public HomePage(){
		super(new String[]{NAVIGATION_PANEL, LOGO});
	}

}
