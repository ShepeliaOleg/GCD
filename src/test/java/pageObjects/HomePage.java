package pageObjects;

import pageObjects.base.AbstractPage;

public class HomePage extends AbstractPage{
	private static final String NAVIGATION_PANEL= 	"//*[@id = 'nav']";
	private static final String LOGO= 				"//img[contains(@class, 'logo')]";

	public HomePage(){
		super(new String[]{LOGO});
	}

}
