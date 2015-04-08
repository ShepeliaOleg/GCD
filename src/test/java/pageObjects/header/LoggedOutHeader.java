package pageObjects.header;

import enums.Licensee;
import enums.Page;
import pageObjects.core.AbstractPageObject;
import pageObjects.forgotPassword.ForgotPasswordPopup;
import pageObjects.login.LoginPopup;
import pageObjects.registration.classic.RegistrationPageAllSteps;
import springConstructors.UserData;
import utils.Locator;
import utils.NavigationUtils;
import utils.WebDriverUtils;
import utils.core.DataContainer;

public class LoggedOutHeader extends Header{

    public static final Locator BUTTON_LOGIN_XP = 		new Locator("fn-login", ROOT_XP+"//*[contains(@class, 'fn-login')]", ".btn.fn-login");
    //Desktop only
    private static final String FIELD_USERNAME_XP= 		ROOT_XP+"//*[@id='name']";
    private static final String FIELD_PASSWORD_XP= 		ROOT_XP+"//*[@id='password']";
	private static final String LINK_REGISTER_XP=		ROOT_XP+"//a[contains(@class,'btn-register')]";
	private static final String LINK_FORGOT_PASSWORD_XP=ROOT_XP+"//*[contains(@class, 'forgot-password')]";
	private static final String CHECKBOX_REMEMBER_ME=	ROOT_XP+"//*[@id='rememberme']";

	public LoggedOutHeader(){
		super(new String[]{BUTTON_LOGIN_XP.getXpath()});
	}

    public void clickButtonLogin(){
        WebDriverUtils.click(BUTTON_LOGIN_XP);
    }

    public LoginPopup navigateToLoginPopup(){
        clickButtonLogin();
        return new LoginPopup();
    }

    public ForgotPasswordPopup navigateToForgotPasswordPopup(){
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.sevenRegal)){
            WebDriverUtils.click(LINK_FORGOT_PASSWORD_XP);
        }else {
            navigateToLoginPopup().clickForgotPassword();
        }
        return new ForgotPasswordPopup();
    }

    public RegistrationPageAllSteps openRegistrationForm(){
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.sevenRegal)){
            WebDriverUtils.click(LINK_REGISTER_XP);
        }else {
            openMenu().loggedOutMenu().clickRegister();
        }
        return new RegistrationPageAllSteps();
    }

    public AbstractPageObject login(UserData userData, boolean rememberMeEnable, Page expectedPage){
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.sevenRegal)){
            return fillLoginFormAndSubmit(userData, rememberMeEnable, expectedPage);
        }else {
            return navigateToLoginPopup().login(userData, rememberMeEnable, expectedPage);
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

    public String getLoginButtonText() {
        return WebDriverUtils.getElementText(BUTTON_LOGIN_XP.getXpath());
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
