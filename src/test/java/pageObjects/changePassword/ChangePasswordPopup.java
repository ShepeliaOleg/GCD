package pageObjects.changePassword;

import pageObjects.core.AbstractPopup;
import pageObjects.core.AbstractPortalPopup;
import springConstructors.UserData;
import springConstructors.ValidationRule;
import utils.Locator;
import utils.WebDriverUtils;
import utils.core.WebDriverFactory;
import utils.validation.ValidationUtils;

import static utils.core.AbstractTest.assertEquals;

public class ChangePasswordPopup extends AbstractPortalPopup{
	private  final static Locator BUTTON_SUBMIT_XP    = new Locator("fn-changepassword", "//*[contains(@class, 'fn-changepassword')][contains(@class, 'button')]", ".popup-modal .fn-changepassword");
    private final static String INPUT_OLD_PASSWORD_XP =             "//*[@name='oldPassword']";
    //OLD locators
    //private final static String INPUT_NEW_PASSWORD_XP =             "//*[@name='newPassword']";
    //private final static String INPUT_NEW_PASSWORD_VERIFICATION_XP ="//*[@name='newPasswordConfirm']";
	private final static String INPUT_NEW_PASSWORD_XP =             "//*[@name='password']";
	private final static String INPUT_NEW_PASSWORD_VERIFICATION_XP ="//*[@name='passwordVerify']";
	private final static String ERROR =								".//input[@name='oldPassword']";
	private final static String GENERAL_ERROR_MSG =					"//*[contains(@class,'error')]";

	public ChangePasswordPopup(){
		super(new String[]{BUTTON_SUBMIT_XP.getXpath()});
	}

	private void fillOldPassword(String password){
		WebDriverUtils.clearAndInputTextToField(INPUT_OLD_PASSWORD_XP, password);
	}

	private void fillNewPassword(String password){
		WebDriverUtils.clearAndInputTextToField(INPUT_NEW_PASSWORD_XP, password);
	}

	private void fillNewPasswordValidation(String password){
		WebDriverUtils.clearAndInputTextToField(INPUT_NEW_PASSWORD_VERIFICATION_XP, password);
	}

	private void submit(){
		WebDriverUtils.click(BUTTON_SUBMIT_XP);
	}

	public void fillFormAndSubmit(String oldPassword, String newPassword){
        fillFormAndClickSubmit(oldPassword, newPassword);
		ChangedPasswordNotification changedPasswordPopup = new ChangedPasswordNotification();
	}

    public void fillFormAndClickSubmit(String oldPassword, String newPassword){
        fillOldPassword(oldPassword);
        fillNewPassword(newPassword);
        fillNewPasswordValidation(newPassword);
        submit();
        //*if(DataContainer.getDriverData().getLicensee().equals(Licensee.sevenRegal)){
        //*    changedPasswordPopup.closePopup();
        //*}
	}

	public void fillValues(String oldPassword, String newPassword1, String newPassword2, String errorMessage){
		fillOldPassword(oldPassword);
		fillNewPassword(newPassword1);
		fillNewPasswordValidation(newPassword2);
		submit();
		assertEquals(errorMessage, getErrorMsg(), "Error message was not as expected!");
	}

    public ChangePasswordPopup fillIncorrectFormAndSubmit(String oldPassword, String newPassword){
        fillFormAndClickSubmit(oldPassword, newPassword);
        return new ChangePasswordPopup();
    }

//    public void validateOldPassword(ValidationRule rule) {
//        ValidationUtils.validate(INPUT_OLD_PASSWORD_XP, rule);
//    }
//
//    public void validateNewPassword(ValidationRule rule) {
//		ValidationUtils.validate(INPUT_NEW_PASSWORD_XP, rule);
//    }

	public void validatePassword(ValidationRule rule, UserData userData) {
		ValidationUtils.validateField(ERROR, rule, "oldPassword");
	}

	public String getErrorMsg(){
		WebDriverUtils.waitForElement(AbstractPopup.ROOT_XP + GENERAL_ERROR_MSG);
		return WebDriverUtils.getElementText(WebDriverFactory.getPortalDriver(), AbstractPopup.ROOT_XP + GENERAL_ERROR_MSG);
	}
}
