package pageObjects.popups;

import pageObjects.base.AbstractPopup;
import utils.WebDriverUtils;

/*
 * User: ivanva
 * Date: 4/19/13
 */

public class WelcomePopup extends AbstractPopup{
	public static final String BUTTON_OK_XP=ROOT_XP + "//button[@class='ok']";

	public WelcomePopup(){
		super(new String[]{BUTTON_OK_XP});
	}

	public void clickClose(){
		WebDriverUtils.click(BUTTON_OK_XP);
	}

}


