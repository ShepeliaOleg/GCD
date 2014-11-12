package pageObjects.registration;

import pageObjects.core.AbstractPortalPopup;

public class AdultContentPopup extends AbstractPortalPopup {

	private static final String ADULT_CONTENT_XP="//*[contains(@class, 'plus')]";

	public AdultContentPopup(){
		super(new String[]{ADULT_CONTENT_XP});
	}

}
