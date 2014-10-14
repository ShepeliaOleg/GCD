package pageObjects.forgotPassword;

import pageObjects.core.AbstractPopup;

public class ForgotPasswordConfirmationPopup extends AbstractPopup{

    public static final String ROOT_XP =            "//*[contains(@class, 'popup_type_info ') or @class='info__content']";
    public static final String MODULE_LOCATOR_XP =  "//*[contains(text(),'Email with temporary password has been sent')]";

	public ForgotPasswordConfirmationPopup(){
		super(new String[]{MODULE_LOCATOR_XP}, null, ROOT_XP);
	}
}
