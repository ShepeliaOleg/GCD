package pageObjects.forgotPassword;

import pageObjects.HomePage;
import pageObjects.account.LoginPopup;
import pageObjects.base.AbstractPopup;
import pageObjects.registration.RegistrationPage;
import springConstructors.UserData;
import springConstructors.validation.ValidationRule;
import utils.ValidationUtils;
import utils.WebDriverUtils;

public class ForgotPasswordPopup extends AbstractPopup{

    private final static String ROOT_XP =            				"//*[@id='forgotPasswordPopupForm']";
    private final static String FIELD_USERNAME_XP =             	ROOT_XP + "//*[@id='userName']";
    private final static String FIELD_EMAIL_XP =                	ROOT_XP + "//*[@id='email']";
    private final static String DROPDOWN_BIRTH_DAY =            	ROOT_XP + "//select[@id='birthDay']";
    private final static String DROPDOWN_BIRTH_MONTH =          	ROOT_XP + "//select[@id='birthMonth']";
    private final static String DROPDOWN_BIRTH_YEAR =         	  	ROOT_XP + "//select[@id='birthYear']";
    private final static String BUTTON_APPROVE_XP =             	ROOT_XP + "//button/span/strong[contains(text(),'Approve')]";
    private final static String BUTTON_CANCEL_XP =              	ROOT_XP + "//a[@class='cancelButton']";
    private final static String LINK_LOGIN_XP =               		ROOT_XP + "//a[@class='loginButton']";
    private final static String LINK_REGISTRATION_XP =				ROOT_XP + "//a[contains(@href,'reg')]";
    private final static String LINK_CONTACT_US_XP =            	ROOT_XP + "//a[@data-title]";
    private final static String LABEL_VALIDATION_ERROR_MESSAGE_XP =	ROOT_XP + "//*[contains(@class,'portlet-msg-error')]";
    private final static String LABEL_VALIDATION_ERROR_ICON_XP =	ROOT_XP + "//span[contains(@class,'error')]";

    public ForgotPasswordPopup(){
        super(new String[]{ROOT_XP, FIELD_USERNAME_XP, FIELD_EMAIL_XP,
                DROPDOWN_BIRTH_DAY, DROPDOWN_BIRTH_MONTH, DROPDOWN_BIRTH_YEAR,
                BUTTON_CANCEL_XP, BUTTON_APPROVE_XP, LINK_LOGIN_XP,
                LINK_REGISTRATION_XP, LINK_CONTACT_US_XP});
    }

    public ForgotPasswordPopup(String page){
        super(new String[]{FIELD_USERNAME_XP, FIELD_EMAIL_XP,
                DROPDOWN_BIRTH_DAY, DROPDOWN_BIRTH_MONTH, DROPDOWN_BIRTH_YEAR,
                BUTTON_CANCEL_XP, BUTTON_APPROVE_XP, LINK_LOGIN_XP,
                LINK_REGISTRATION_XP, LINK_CONTACT_US_XP}, null, ROOT_XP);
    }

	private void fillUsername(String username){
		WebDriverUtils.clearAndInputTextToField(FIELD_USERNAME_XP, username);
	}

	private void fillEmail(String email){
		WebDriverUtils.clearAndInputTextToField(FIELD_EMAIL_XP, email);
	}

	private void fillBirthDay(String birthDay){
		WebDriverUtils.setDropdownOptionByText(DROPDOWN_BIRTH_DAY, birthDay);
	}

	private void fillBirthMonth(String birthMonth){
		WebDriverUtils.setDropdownOptionByText(DROPDOWN_BIRTH_MONTH, birthMonth);
	}

	private void fillBirthYear(String birthYear){
		WebDriverUtils.setDropdownOptionByText(DROPDOWN_BIRTH_YEAR, birthYear);
	}

	public void submit(){
		WebDriverUtils.click(BUTTON_APPROVE_XP);
	}

	public HomePage clickCancel(){
		WebDriverUtils.click(BUTTON_CANCEL_XP);
		return new HomePage();
	}

	public void recoverPassword(UserData userData){
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

	public void fillUserData(UserData userData){
		fillUsername(userData.getUsername());
		fillEmail(userData.getEmail());
		fillBirthDay(userData.getBirthDay());
		fillBirthMonth(userData.getBirthMonth());
		fillBirthYear(userData.getBirthYear());
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

	public void validateUsername(ValidationRule rule) {
		ValidationUtils.validate(FIELD_USERNAME_XP, rule);
	}

	public void validateEmail(ValidationRule rule) {
		ValidationUtils.validate(FIELD_EMAIL_XP, rule);
	}
}
