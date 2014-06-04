package pageObjects.forgotPassword;

import springConstructors.UserData;

public class ForgotUsernamePopup extends ForgotPopup{

    public ForgotUsernamePopup(){
        super(new String[]{ROOT_XP, FORGOT_PASSWORD_TAB_XP, FORGOT_USERNAME_TAB_XP, FIELD_EMAIL_XP, BUTTON_CANCEL_XP, BUTTON_APPROVE_XP, LINK_LOGIN_XP, LINK_REGISTRATION_XP, LINK_CONTACT_US_XP});
        forgotType = false;
    }

    public ForgotUsernamePopup(String page){
        super(new String[]{FIELD_EMAIL_XP, BUTTON_CANCEL_XP, BUTTON_APPROVE_XP, LINK_LOGIN_XP, LINK_REGISTRATION_XP, LINK_CONTACT_US_XP}, page);
        forgotType = false;
    }

	public static void fillUserData(UserData userData){
		fillEmail(userData.getEmail());
	}

}
