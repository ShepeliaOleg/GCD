package pageObjects.changePassword;

import pageObjects.core.AbstractPortalPopup;
import springConstructors.UserData;
import springConstructors.ValidationRule;
import utils.WebDriverUtils;
import utils.validation.ValidationUtils;

public class ChangePasswordPopup extends AbstractPortalPopup{
	private  final static String BUTTON_SUBMIT_XP    =               "//*[contains(@class, 'fn-changepassword')][contains(@class, 'button')]";
    public static final String ROOT_XP = BUTTON_SUBMIT_XP;
    private final static String INPUT_OLD_PASSWORD_XP =             "//*[@name='oldPassword']";
    private final static String INPUT_NEW_PASSWORD_XP =             "//*[@name='newPassword']";
    private final static String INPUT_NEW_PASSWORD_VERIFICATION_XP ="//*[@name='newPasswordConfirm']";
	private final static String ERROR = ".//input[@name='oldPassword']";

	public ChangePasswordPopup(){
		super(new String[]{ROOT_XP});
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

    private void fillFormAndClickSubmit(String oldPassword, String newPassword){
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
		System.out.println(errorMessage);
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
}
