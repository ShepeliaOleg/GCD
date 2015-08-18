package pageObjects;

import pageObjects.core.AbstractPortalPage;

public class HomePage extends AbstractPortalPage{
	private static final String IMG_LOGO_XP = "//div[contains(@class, 'main-header ')]"; //"//button[contains(@class, 'btn btn_orange btn_s fn-login-btn')]"

	public HomePage(){
		super(new String[]{IMG_LOGO_XP});
	}

}
