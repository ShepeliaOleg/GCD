package pageObjects.forgotPassword;

import pageObjects.HomePage;
import pageObjects.account.LoginPopup;
import pageObjects.base.AbstractPopup;
import pageObjects.registration.RegistrationPage;
import springConstructors.UserData;
import springConstructors.validation.ValidationRule;
import utils.ValidationUtils;
import utils.WebDriverUtils;

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
