package pageObjects;

import pageObjects.core.AbstractPortalPage;

public class HomePage extends AbstractPortalPage{
	private static final String IMG_LOGO_XP = "//img[contains(@class, 'logo')]/..";

	public HomePage(){
		super(new String[]{IMG_LOGO_XP});
	}

}
