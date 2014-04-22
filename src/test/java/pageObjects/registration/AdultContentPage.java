package pageObjects.registration;

import pageObjects.base.AbstractPage;

/**
 * User: sergiich
 * Date: 8/14/13
 */

public class AdultContentPage extends AbstractPage{

	private static final String ADULT_CONTENT_XP="//a[contains(text(), '18plus')]";

	public AdultContentPage(){
		super(new String[]{ADULT_CONTENT_XP});
	}

}
