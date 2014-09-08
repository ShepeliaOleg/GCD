package pageObjects.account;

import pageObjects.base.AbstractPopup;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 2/11/14
 */

public class ChangedPasswordPopup extends AbstractPopup{
	public final static String BUTTON_OK_XP=				"//*[@title='ok']";
	private final static String LABEL_MESSAGE_SUCCESS_XP=	ROOT_XP + "//div[contains(text(),'Password Changed')]";

	public ChangedPasswordPopup(){
		super(new String[]{LABEL_MESSAGE_SUCCESS_XP});
	}

	public boolean successfulMessageAppeared(){
		return WebDriverUtils.isVisible(LABEL_MESSAGE_SUCCESS_XP);
	}
}

