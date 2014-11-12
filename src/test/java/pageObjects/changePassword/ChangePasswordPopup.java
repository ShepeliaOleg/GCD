package pageObjects.changePassword;

import enums.Licensee;
import pageObjects.core.AbstractPortalPopup;
import utils.WebDriverUtils;
import utils.core.DataContainer;

public class ChangePasswordPopup extends AbstractPortalPopup{
	private  final static String BUTTON_SUBMIT_XP    =               "//*[contains(@class, 'fn-changepassword')][contains(@class, 'button')]";
    public static final String ROOT_XP = BUTTON_SUBMIT_XP;
    private final static String INPUT_OLD_PASSWORD_XP =             "//*[@name='oldPassword']";
    private final static String INPUT_NEW_PASSWORD_XP =             "//*[@name='newPassword']";
    private final static String INPUT_NEW_PASSWORD_VERIFICATION_XP ="//*[@name='newPasswordConfirm']";

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
	}

    private void fillFormAndClickSubmit(String oldPassword, String newPassword){
        fillOldPassword(oldPassword);
        fillNewPassword(newPassword);
        fillNewPasswordValidation(newPassword);
        submit();
        ChangedPasswordPopup changedPasswordPopup = new ChangedPasswordPopup();
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.sevenRegal)){
            changedPasswordPopup.closePopup();
        }
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
}
