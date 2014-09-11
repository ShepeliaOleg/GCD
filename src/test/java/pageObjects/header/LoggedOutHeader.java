package pageObjects.header;

import enums.Page;
import pageObjects.core.AbstractPageObject;
import pageObjects.forgotPassword.ForgotPasswordPopup;
import pageObjects.login.LoginPopup;
import pageObjects.registration.classic.RegistrationPageAllSteps;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.WebDriverUtils;

public class LoggedOutHeader extends Header{

    private static final String BUTTON_LOGIN_XP = 		ROOT_XP+"//*[contains(@class, 'btn fr')]";
    //Desktop only
    private static final String FIELD_USERNAME_XP= 		ROOT_XP+"//*[@id='name']";
    private static final String FIELD_PASSWORD_XP= 		ROOT_XP+"//*[@id='password']";
	private static final String LINK_REGISTER_XP=		ROOT_XP+"//a[contains(@class,'btn-register')]";
	private static final String LINK_FORGOT_PASSWORD_XP=ROOT_XP+"//*[contains(@class, 'forgot-password')]";
	private static final String CHECKBOX_REMEMBER_ME=	ROOT_XP+"//*[@id='rememberme']";

	public LoggedOutHeader(){
		super(new String[]{BUTTON_LOGIN_XP});
	}

    public LoginPopup clickButtonLogin(){
        WebDriverUtils.click(BUTTON_LOGIN_XP);
        return new LoginPopup();
    }

    public ForgotPasswordPopup navigateToForgotPasswordPopup(){
        if(platform.equals(PLATFORM_DESKTOP)){
            WebDriverUtils.click(LINK_FORGOT_PASSWORD_XP);
        }else {
            clickButtonLogin().clickForgotPassword();
        }
        return new ForgotPasswordPopup();
    }

    public RegistrationPageAllSteps openRegistrationForm(){
        if(platform.equals(PLATFORM_DESKTOP)){
            WebDriverUtils.click(LINK_REGISTER_XP);
        }else {
            openMenu().loggedOutMenu().clickRegister();
        }
        return new RegistrationPageAllSteps();
    }

    public AbstractPageObject login(UserData userData, boolean rememberMeEnable, Page expectedPage){
        if(platform.equals(PLATFORM_DESKTOP)){
            return fillLoginFormAndSubmit(userData, rememberMeEnable, expectedPage);
        }else {
            return clickButtonLogin().login(userData, rememberMeEnable, expectedPage);
        }
    }

    //Desktop only
	public void fillUsername(String username){
		WebDriverUtils.clearAndInputTextToField(FIELD_USERNAME_XP, username);
	}

    public void fillPassword(String password){
        WebDriverUtils.clearAndInputTextToField(FIELD_PASSWORD_XP, password);
    }

	public String getUsernameText(){
		return WebDriverUtils.getInputFieldText(FIELD_USERNAME_XP);
	}

    public boolean getRememberMeCheckBoxState(){
        return WebDriverUtils.getCheckBoxState(CHECKBOX_REMEMBER_ME);
    }

	public void setRememberMeCheckBoxState(boolean desiredState){
		WebDriverUtils.setCheckBoxState(CHECKBOX_REMEMBER_ME, desiredState);
	}

	private AbstractPageObject fillLoginFormAndSubmit(UserData userData, boolean rememberMeEnable, Page expectedPage) throws RuntimeException{
		setRememberMeCheckBoxState(rememberMeEnable);
		fillUsername(userData.getUsername());
		fillPassword(userData.getPassword());
		clickButtonLogin();
		return NavigationUtils.closeAllPopups(expectedPage);
	}

	/* VALIDATION */

//	public void validateUsername(ValidationRule rule) {
//		ValidationUtils.validate(FIELD_USERNAME_XP, rule);
//	}
//
//	public void validatePassword(ValidationRule rule) {
//		ValidationUtils.validate(FIELD_PASSWORD_XP, rule);
//	}
}
