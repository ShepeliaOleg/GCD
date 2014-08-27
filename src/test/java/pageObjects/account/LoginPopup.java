package pageObjects.account;

import enums.Page;
import pageObjects.HomePage;
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
	public static final String LABEL_VALIDATION_ERROR_XP=	ROOT_XP + "//*[contains(@class,'portlet-msg-error')]";
    public static final String LABEL_TIMEOUT_ERROR_XP=   ROOT_XP + "//*[contains(@class, 'portlet-msg-error') and contains(text(), 'Timeout occurred')]";
	public static final String INPUT_USERNAME_XP=			ROOT_XP + "//*[@name='username']";
	private static final String INPUT_PASSWORD_XP=			ROOT_XP + "//*[@name='password']";
	private static final String CHECKBOX_REMEMBERME_XP=		ROOT_XP + "//*[@name='remember-me']";
	private static final String BUTTON_CANCEL_XP=			ROOT_XP + "//a[contains(@class, 'jsCancelButton')]";
	public static final String BUTTON_SUBMIT_XP=			ROOT_XP + "//button";
	private static final String LINK_REGISTER_XP=			ROOT_XP + "//a[@class='registration-link']";
	private static final String LINK_CONTACTUS_XP=			ROOT_XP + "//a[not(@class)]";
	private static final String LINK_FORGOTTEN_XP=			ROOT_XP + "//a[@class='jsForgotPasswordButton']";

	public LoginPopup(){
		super(new String[]{BUTTON_SUBMIT_XP, LINK_REGISTER_XP, LINK_FORGOTTEN_XP});
	}

	public boolean validationErrorVisible(){
		return WebDriverUtils.isVisible(LABEL_VALIDATION_ERROR_XP);
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

	public HomePage cancel(){
		WebDriverUtils.click(BUTTON_CANCEL_XP);
		return new HomePage();
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
		AbstractPageObject result=null;

		switch(expectedPage){
			case homePage:
				result=(HomePage) NavigationUtils.closeAllPopups(Page.homePage);
				break;
			case changePasswordPopup:
				result=(ChangePasswordPopup) NavigationUtils.closeAllPopups(Page.changePasswordPopup);
				break;
			case loginPopup:
				result=(LoginPopup) NavigationUtils.closeAllPopups(Page.loginPopup);
				break;
			default:
				break;
		}
		return result;
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

	public ContactUsPopup navigateToContactUs(){
		WebDriverUtils.click(LINK_CONTACTUS_XP);
		return new ContactUsPopup();
	}

	public HomePage close(){
		WebDriverUtils.click(BUTTON_CLOSE_XP);
		return new HomePage();
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
