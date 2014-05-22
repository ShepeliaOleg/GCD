package pageObjects.forgotPassword;

import pageObjects.HomePage;
import pageObjects.account.LoginPopup;
import pageObjects.base.AbstractPopup;
import pageObjects.registration.RegistrationPage;
import springConstructors.UserData;
import springConstructors.validation.ValidationRule;
import utils.ValidationUtils;
import utils.WebDriverUtils;

public abstract class ForgotPopup extends AbstractPopup{

    public boolean forgotType;  // true == ForgotPassword; false == ForgotUsername
    public final static String ROOT_XP =            				"//*[contains(@class,'forgot-password-wrap')]";
    public final static String FORGOT_PASSWORD_TAB_XP =             ROOT_XP + "//a[contains(@data-tab,'password')]";
    public final static String FORGOT_USERNAME_TAB_XP =             ROOT_XP + "//a[contains(@data-tab,'name')]";
    public final static String FIELD_EMAIL_XP =                	    ROOT_XP + "//*[@id='email']";
    public final static String BUTTON_APPROVE_XP =             	    ROOT_XP + "//button/span/strong[contains(text(),'Approve')]";
    public final static String BUTTON_CANCEL_XP =              	    ROOT_XP + "//a[@class='cancelButton']";
    public final static String LINK_LOGIN_XP =               		ROOT_XP + "//a[@class='loginButton']";
    public final static String LINK_REGISTRATION_XP =				ROOT_XP + "//a[contains(@href,'reg')]";
    public final static String LINK_CONTACT_US_XP =            	    ROOT_XP + "//a[@data-title]";
    public final static String LABEL_VALIDATION_ERROR_MESSAGE_XP =	ROOT_XP + "//*[contains(@class,'portlet-msg-error')]";
    public final static String LABEL_VALIDATION_ERROR_ICON_XP =	    ROOT_XP + "//span[contains(@class,'error')]";


    public ForgotPopup(String[] xpathes){
        super(xpathes);
    }

    public ForgotPopup(String[] xpathes, String page){
        super(xpathes, null, ROOT_XP);
    }

    public ForgotUsernamePopup switchToForgotUsernamePopup(){
        WebDriverUtils.click(FORGOT_USERNAME_TAB_XP);
        return new ForgotUsernamePopup();
    }

    private ForgotPasswordPopup switchToForgotPasswordPopup(){
        WebDriverUtils.click(FORGOT_PASSWORD_TAB_XP);
        return new ForgotPasswordPopup();
    }

    public static void fillEmail(String email){
		WebDriverUtils.clearAndInputTextToField(FIELD_EMAIL_XP, email);
	}

	public void submit(){
		WebDriverUtils.click(BUTTON_APPROVE_XP);
	}

	public HomePage clickCancel(){
		WebDriverUtils.click(BUTTON_CANCEL_XP);
		return new HomePage();
	}

	public void recover(UserData userData){
		fillUserData(userData);
		submit();
	}

	public HomePage fillDataAndCancel(UserData userData){
		fillUserData(userData);
		return clickCancel();
	}

	public HomePage fillDataAndClosePopup(UserData userData){
		fillUserData(userData);
		clickClose();
		return new HomePage();
	}

    private void fillUserData (UserData userData) {
        if (forgotType) {
            ForgotPasswordPopup.fillUserData(userData);
        } else {
            ForgotUsernamePopup.fillUserData(userData);
        }
    }

	public boolean confirmationPopupVisible(){
		return WebDriverUtils.isVisible(ForgotPasswordConfirmationPopup.MODULE_LOCATOR_XP);
	}

	public boolean validationErrorMessageVisible(){
		return WebDriverUtils.isVisible(LABEL_VALIDATION_ERROR_MESSAGE_XP);
	}

	public boolean validationErrorIconVisible(){
		return WebDriverUtils.isVisible(LABEL_VALIDATION_ERROR_ICON_XP);
	}


    public LoginPopup navigateToLoginPopup() {
        WebDriverUtils.click(LINK_LOGIN_XP);
        return new LoginPopup();
    }

    public RegistrationPage navigateToRegistrationPage() {
        WebDriverUtils.click(LINK_REGISTRATION_XP);
        return new RegistrationPage();
    }

    public ContactUsPopup navigateToContactUs() {
        WebDriverUtils.click(LINK_CONTACT_US_XP);
        return new ContactUsPopup();
    }

	public void validateEmail(ValidationRule rule) {
		ValidationUtils.validate(FIELD_EMAIL_XP, rule);
	}
}
