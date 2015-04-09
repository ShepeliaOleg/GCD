package pageObjects.login;

import enums.Page;
import pageObjects.core.AbstractPageObject;
import pageObjects.core.AbstractPortalPage;
import pageObjects.core.AbstractPortalPopup;
import pageObjects.forgotPassword.ForgotPasswordPopup;
import pageObjects.registration.RegistrationPage;
import springConstructors.UserData;
import utils.Locator;
import utils.NavigationUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

public class LoginPopup extends AbstractPortalPopup {

    public  static final String  INPUT_USERNAME_XP=			ROOT_XP + "//*[@name='userName']";
    private static final String  INPUT_PASSWORD_XP=			ROOT_XP + "//*[@name='password']";
    private static final String  CHECKBOX_REMEMBERME_XP=	ROOT_XP + "//*[@id='rememberme']";
    private static final Locator LINK_FORGOTTEN_XP=			new Locator("fn-forgot-password", ROOT_XP + "//*[@class='fn-forgot-password']", null);
    public  static final String  BUTTON_LOGIN_XP =			ROOT_XP + "//*[contains(@class, 'fn-login-btn')]";
    private static final String  LINK_REGISTER_XP=			ROOT_XP + "//*[@href='/register']";
    public  static final String  LABEL_VALIDATION_ERROR_XP=	ROOT_XP + "//*[contains(@class,'error')]";
    private static final String  DESCRIPTION_MSG_XP=        ROOT_XP + "//*[@class='popup-modal__description']";
    public  static final String  LABEL_TIMEOUT_ERROR_XP=    LABEL_VALIDATION_ERROR_XP + "[contains(text(), 'Timeout occurred')]";

	public LoginPopup(){
		super(new String[]{INPUT_USERNAME_XP});
	}

    private void fillUsername(String username){
        WebDriverUtils.clearAndInputTextToField(INPUT_USERNAME_XP, username);
    }

    public String getUsernameText(){
        return WebDriverUtils.getInputFieldText(INPUT_USERNAME_XP);
    }

    private void fillPassword(String password){
        WebDriverUtils.clearAndInputTextToField(INPUT_PASSWORD_XP, password);
    }

    public String getPasswordText(){
        return WebDriverUtils.getInputFieldText(INPUT_PASSWORD_XP);
    }

    public void setRememberMeCheckBoxState(boolean desiredState){
        WebDriverUtils.setCheckBoxState(CHECKBOX_REMEMBERME_XP, desiredState);
    }

    public boolean getRememberMeCheckBoxState(){
        return WebDriverUtils.getCheckBoxState(CHECKBOX_REMEMBERME_XP);
    }

    public static String getDescriptionMsgText() {
        return WebDriverUtils.getElementText(DESCRIPTION_MSG_XP);
    }

    public void clickLogin(){
        WebDriverUtils.click(BUTTON_LOGIN_XP);
    }

    public AbstractPortalPage login(Page expectedPage){
        return (AbstractPortalPage) this.login(DataContainer.getUserData().getRegisteredUserData(), false, expectedPage);
    }

    public AbstractPortalPage login(UserData userData){
        return (AbstractPortalPage)this.login(userData,false, Page.homePage);
    }

    public AbstractPageObject login(UserData userData, boolean rememberMeEnable){
        return this.login(userData,rememberMeEnable, Page.homePage);
    }

    public AbstractPageObject login(UserData userData, boolean rememberMeEnable, Page expectedPage) throws RuntimeException{
        fillUsername(userData.getUsername());
        fillPassword(userData.getPassword());
        setRememberMeCheckBoxState(rememberMeEnable);
        clickLogin();
        if(expectedPage.equals(Page.loginPopup)){
            return new LoginPopup();
        }else {
            try{
                WebDriverUtils.waitForElementToDisappear(BUTTON_LOGIN_XP, 30);
            }catch (Exception e){
                NavigationUtils.registrationError();
            }
            WebDriverUtils.waitForElement(AbstractPortalPopup.ROOT_XP); // wait for popup appear before trying to close it
            return NavigationUtils.closeAllPopups(expectedPage);
        }
    }

    public ForgotPasswordPopup clickForgotPassword(){
        WebDriverUtils.click(LINK_FORGOTTEN_XP);
        return new ForgotPasswordPopup();
    }

    public RegistrationPage clickRegistration(){
        WebDriverUtils.click(LINK_REGISTER_XP);
        return new RegistrationPage();
    }

	public boolean validationErrorVisible(){
		return WebDriverUtils.isVisible(LABEL_VALIDATION_ERROR_XP, 1);
	}

    public void validateDescriptionMesageDublicateEmail(){
        AbstractTest.assertEquals("Sorry an account already exists for this email address.", getDescriptionMsgText(), "Description message in Login popup is wrong");
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
