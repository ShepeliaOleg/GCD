package pageObjects.registration;

import pageObjects.core.AbstractPage;

/**
 * User: sergiich
 * Date: 8/14/13
 */

public class AdultContentPopup extends AbstractPage{

	private static final String ADULT_CONTENT_XP="//*[contains(@class, 'plus')]";

	public AdultContentPopup(){
		super(new String[]{ADULT_CONTENT_XP});
	}

}
