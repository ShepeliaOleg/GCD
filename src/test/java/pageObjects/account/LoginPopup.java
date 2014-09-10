package pageObjects.account;

import enums.Page;
import pageObjects.HomePage;
import pageObjects.base.AbstractPage;
import pageObjects.base.AbstractPageObject;
import pageObjects.base.AbstractPopup;
import pageObjects.forgotPassword.ContactUsPopup;
import pageObjects.forgotPassword.ForgotPasswordPopup;
import pageObjects.registration.RegistrationPage;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.WebDriverUtils;

/**
 * User: ivan
 * Date: 8/09/13
 */

public class LoginPopup extends AbstractPopup{
	public static final String LABEL_VALIDATION_ERROR_XP=	ROOT_XP + "//*[contains(@class,'error')]";
    public static final String LABEL_TIMEOUT_ERROR_XP=   ROOT_XP + "//*[contains(@class, 'portlet-msg-error') and contains(text(), 'Timeout occurred')]";
	public static final String INPUT_USERNAME_XP=			ROOT_XP + "//*[@name='userName']";
	private static final String INPUT_PASSWORD_XP=			ROOT_XP + "//*[@name='password']";
	private static final String CHECKBOX_REMEMBERME_XP=		ROOT_XP + "//*[@id='rememberme']";
    private static final String LINK_FORGOTTEN_XP=			ROOT_XP + "//a[@class='fn-forgot-password']";
    public static final String BUTTON_SUBMIT_XP=			ROOT_XP + "//*[contains(@class, 'fn-login-btn')]";
    private static final String LINK_REGISTER_XP=			ROOT_XP + "//*[contains(@class, 'register-btn')]";

	public LoginPopup(){
		super(new String[]{BUTTON_SUBMIT_XP, LINK_REGISTER_XP, LINK_FORGOTTEN_XP});
	}

	public boolean validationErrorVisible(){
		return WebDriverUtils.isVisible(LABEL_VALIDATION_ERROR_XP, 1);
	}

	private void fillUsername(String username){
		WebDriverUtils.clearAndInputTextToField(INPUT_USERNAME_XP, username);
	}

	public String getUsernameText(){
		return WebDriverUtils.getInputFieldText(INPUT_USERNAME_XP);
	}

	public String getPasswordText(){
		return WebDriverUtils.getInputFieldText(INPUT_PASSWORD_XP);
	}

	private void fillPassword(String password){
		WebDriverUtils.clearAndInputTextToField(INPUT_PASSWORD_XP, password);
	}

	public boolean getRememberMeCheckBoxState(){
		return WebDriverUtils.getCheckBoxState(CHECKBOX_REMEMBERME_XP);
	}

	public void setRememberMeCheckBoxState(boolean desiredState){
		WebDriverUtils.setCheckBoxState(CHECKBOX_REMEMBERME_XP, desiredState);
	}

	public void submit(){
		WebDriverUtils.click(BUTTON_SUBMIT_XP);
	}

	private void fillUserCredentials(String username, String password){
		fillUsername(username);
		fillPassword(password);
	}

	public HomePage login(UserData userData) throws RuntimeException{
		return (HomePage)login(userData, false);
	}

	public AbstractPageObject login(UserData userData, boolean rememberMeEnable) throws RuntimeException{
		return login(userData, rememberMeEnable, Page.homePage);
	}

	public AbstractPageObject login(UserData userData, Page expectedPage) throws RuntimeException{
		return login(userData, false, expectedPage);
	}

	public AbstractPageObject login(UserData userData, boolean rememberMeEnable, Page expectedPage) throws RuntimeException{
		setRememberMeCheckBoxState(rememberMeEnable);
		String username=userData.getUsername();
		String password=userData.getPassword();
		fillUserCredentials(username, password);
		submit();
        WebDriverUtils.waitFor(1000);
        return NavigationUtils.closeAllPopups(expectedPage);
	}

//    public HomePage login(UserData userData, boolean rememberMeEnable) {
//        setRememberMeCheckBoxState(rememberMeEnable);
//        String username = userData.getUsername();
//        String password = userData.getPassword();
//        fillUserCredentials(username,password);
//        submit();
//
//
//        if (WebDriverUtils.isVisible(WelcomePopup.BUTTON_OK_XP)) {
//            WelcomePopup welcomePopup = new WelcomePopup();
//            welcomePopup.close();
//        }
//
//        WebDriverUtils.waitForElement(SignedInAccountHeader.WELCOME_LOCATOR_XP);
//        return new HomePage();
//    }

	public RegistrationPage navigateToRegistration(){
		WebDriverUtils.click(LINK_REGISTER_XP);
		return new RegistrationPage();
	}

	public ForgotPasswordPopup navigateToForgotPassword(){
		WebDriverUtils.click(LINK_FORGOTTEN_XP);
		return new ForgotPasswordPopup();
	}

	public AbstractPage close(){
		clickClose();
		return new AbstractPage();
	}

    /* VALIDATION*/

//    public void validateUsername(ValidationRule rule) {
//        ValidationUtils.validate(INPUT_USERNAME_XP, rule);
//    }
//
//    public void validatePassword(ValidationRule rule) {
//		ValidationUtils.validate(INPUT_PASSWORD_XP, rule);
//    }
}
