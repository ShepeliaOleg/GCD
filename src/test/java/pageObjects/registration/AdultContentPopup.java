package pageObjects.registration;

import pageObjects.core.AbstractPopup;

public class AdultContentPopup extends AbstractPopup{

	private static final String ADULT_CONTENT_XP="//*[contains(@class, 'plus')]";

	public AdultContentPopup(){
		super(new String[]{ADULT_CONTENT_XP});
	}

}
