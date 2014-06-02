package pageObjects.account;

import pageObjects.base.AbstractPopup;
import springConstructors.validation.ValidationRule;
import utils.ValidationUtils;
import utils.WebDriverUtils;

public class ChangePasswordPopup extends AbstractPopup{
	public  final static String BUTTON_SUBMIT_XP    =               "//*[@id='changePassword']";
	private final static String LABEL_MESSAGE_ERROR_XP=				ROOT_XP+"//div[contains(@class,'error')]";
    private final static String INPUT_OLD_PASSWORD_XP =             "//*[@id='oldPassword']";
    private final static String INPUT_NEW_PASSWORD_XP =             "//*[@id='newPassword']";
    private final static String INPUT_NEW_PASSWORD_VERIFICATION_XP ="//*[@id='newPasswordVerification']";

	public ChangePasswordPopup(){
		super(new String[]{BUTTON_SUBMIT_XP});
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

	public ChangedPasswordPopup fillFormAndSubmit(String oldPassword, String newPassword){
		fillOldPassword(oldPassword);
		fillNewPassword(newPassword);
		fillNewPasswordValidation(newPassword);
		submit();
		return new ChangedPasswordPopup();
	}

    public ChangePasswordPopup fillIncorrectFormAndSubmit(String oldPassword, String newPassword){
        fillOldPassword(oldPassword);
        fillNewPassword(newPassword);
        fillNewPasswordValidation(newPassword);
        submit();
        return new ChangePasswordPopup();
    }

	public boolean errorMessageAppeared(){
		return WebDriverUtils.isVisible(LABEL_MESSAGE_ERROR_XP);
	}

    public void validateOldPassword(ValidationRule rule) {
        ValidationUtils.validate(INPUT_OLD_PASSWORD_XP, rule);
    }

    public void validateNewPassword(ValidationRule rule) {
		ValidationUtils.validate(INPUT_NEW_PASSWORD_XP, rule);
    }
}
