package pageObjects.forgotPassword;

import pageObjects.core.AbstractPopup;

/**
 * User: svetlanagl
 * Date: 3/20/13
 */

public class ForgotPasswordConfirmationPopup extends AbstractPopup{

    public static final String ROOT_XP =            "//*[contains(@class, 'popup_type_info ')] | //*[@class='info__content']";
    public static final String MODULE_LOCATOR_XP =  ROOT_XP + "//*[contains(text(),'Email with temporary password has been sent')]";

	public ForgotPasswordConfirmationPopup(){
		super(new String[]{ROOT_XP}, null, ROOT_XP);
	}
}
