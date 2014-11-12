package pageObjects;

import pageObjects.core.AbstractPortalPage;

public class HomePage extends AbstractPortalPage{
	private static final String NAVIGATION_PANEL= 	"//*[@id = 'nav']";
	private static final String LOGO= 				"//img[contains(@class, 'logo')]";

	public HomePage(){
		super(new String[]{LOGO});
	}

}
