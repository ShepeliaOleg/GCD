package pageObjects.login;

import pageObjects.core.AbstractPopup;
import utils.WebDriverUtils;

/*
 * User: ivanva
 * Date: 4/19/13
 */

public class WelcomePopup extends AbstractPopup{
	public static final String BUTTON_OK_XP=    ROOT_XP + "//button[@class='ok']";
    public static final String LABEL_CONTENTS = ROOT_XP + "//p[contains(text(), 'Welcome, Player!')]";

	public WelcomePopup(){
		super(new String[]{BUTTON_OK_XP, LABEL_CONTENTS});
	}

	public void clickClose(){
		WebDriverUtils.click(BUTTON_OK_XP);
	}

}


