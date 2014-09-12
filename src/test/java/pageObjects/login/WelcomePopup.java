package pageObjects.login;

import pageObjects.core.AbstractPopup;
import utils.WebDriverUtils;

/*
 * User: ivanva
 * Date: 4/19/13
 */

public class WelcomePopup extends AbstractPopup{
    public static final String LABEL_CONTENTS = ROOT_XP + "//p[contains(text(), 'Welcome, Player!')]";

	public WelcomePopup(){
		super(new String[]{});
	}
}


