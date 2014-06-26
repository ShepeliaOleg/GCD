package pageObjects.header;

import enums.Page;
import pageObjects.account.LoginPopup;
import pageObjects.base.AbstractPageObject;
import pageObjects.forgotPassword.ForgotPasswordPopup;
import pageObjects.registration.RegistrationPage;
import springConstructors.UserData;
import springConstructors.ValidationRule;
import utils.NavigationUtils;
import utils.ValidationUtils;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 4/10/14
 */

public class LoggedOutHeader extends Header{

	private static final String ROOT_XP = 				"//*[@class='login-replacer']";
	private static final String FIELD_USERNAME_XP= 		"//*[@id='name']";
	private static final String FIELD_PASSWORD_XP= 		ROOT_XP+"//*[@id='password']";
	private static final String BUTTON_SUBMIT_XP = 		"//button[(@type='submit')]";
	private static final String LINK_REGISTER_XP=		"//a[contains(@class,'btn-register')]";
	private static final String LINK_FORGOT_PASSWORD_XP="//*[contains(@class, 'forgot-password')]";
	private static final String CHECKBOX_REMEMBER_ME=	"//*[@id='rememberme']";

	public LoggedOutHeader(){
		super(new String[]{ROOT_XP});
	}

	public void fillUsername(String username){
		WebDriverUtils.clearAndInputTextToField(FIELD_USERNAME_XP, username);
	}

	public String getUsernameText(){
		return WebDriverUtils.getInputFieldText(FIELD_USERNAME_XP);
	}

	public void fillPassword(String password){
		WebDriverUtils.clearAndInputTextToField(FIELD_PASSWORD_XP, password);
	}

	public ForgotPasswordPopup navigateToForgotPasswordPopup(){
		WebDriverUtils.click(LINK_FORGOT_PASSWORD_XP);
		return new ForgotPasswordPopup();
	}

	public void clickSubmit(){
		WebDriverUtils.click(BUTTON_SUBMIT_XP);
	}

	public LoginPopup openLoginForm(){
		clickSubmit();
		return new LoginPopup();
	}

	public RegistrationPage openRegistrationForm(){
		WebDriverUtils.click(LINK_REGISTER_XP);
		return new RegistrationPage();
	}

	public void setRememberMeCheckBoxState(boolean desiredState){
		WebDriverUtils.setCheckBoxState(CHECKBOX_REMEMBER_ME, desiredState);
	}

	public boolean getRememberMeCheckBoxState(){
		return WebDriverUtils.getCheckBoxState(CHECKBOX_REMEMBER_ME);
	}

	public AbstractPageObject login(UserData userData) throws RuntimeException{
		return login(userData, false);
	}

	public AbstractPageObject login(UserData userData, boolean rememberMeEnable) throws RuntimeException{
		return login(userData, rememberMeEnable, Page.homePage);
	}

	public AbstractPageObject login(UserData userData, Page expectedPage) throws RuntimeException{
		return login(userData, false, expectedPage);
	}

	public AbstractPageObject login(UserData userData, boolean rememberMeEnable, Page expectedPage) throws RuntimeException{
		setRememberMeCheckBoxState(rememberMeEnable);
		fillUsername(userData.getUsername());
		fillPassword(userData.getPassword());
		clickSubmit();
		return NavigationUtils.closeAllPopups(expectedPage);
	}

	/* VALIDATION */

	public void validateUsername(ValidationRule rule) {
		ValidationUtils.validate(FIELD_USERNAME_XP, rule);
	}

	public void validatePassword(ValidationRule rule) {
		ValidationUtils.validate(FIELD_PASSWORD_XP, rule);
	}
}
