package pageObjects.changePassword;

import pageObjects.core.AbstractPopup;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 2/11/14
 */

public class ChangedPasswordPopup extends AbstractPopup{
    public static final String ROOT_XP =            "//*[contains(@class, 'popup_type_info ') or @class='info__content']";
	public final static String BUTTON_OK_XP=				"//*[@title='ok']";
	private final static String LABEL_MESSAGE_SUCCESS_XP=	"//*[contains(text(),'Password Changed')]";

	public ChangedPasswordPopup(){
		super(new String[]{LABEL_MESSAGE_SUCCESS_XP}, null, ROOT_XP);
	}

	public boolean successfulMessageAppeared(){
		return WebDriverUtils.isVisible(LABEL_MESSAGE_SUCCESS_XP);
	}
}

