package pageObjects.forgotPassword;

import pageObjects.core.AbstractPortalPopup;

public class ForgotPasswordConfirmationPopup extends AbstractPortalPopup{

    public static final String ROOT_XP =            "//*[contains(@class, 'popup_type_info ') or @class='info__content']";
    public static final String MODULE_LOCATOR_XP =  "//*[contains(text(),'Email with temporary password has been sent')]";

	public ForgotPasswordConfirmationPopup(){
		super(new String[]{MODULE_LOCATOR_XP}, null, ROOT_XP);
	}
}
