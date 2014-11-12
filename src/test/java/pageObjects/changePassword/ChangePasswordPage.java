package pageObjects.changePassword;

import pageObjects.core.AbstractPortalPage;
import utils.WebDriverUtils;

public class ChangePasswordPage extends AbstractPortalPage {
	private static final String ROOT_XP=						"//*[contains(@id, 'portlet_changepassword')]";
	private static final String FIELD_PASSWORD_OLD_XP=			"//*[@id='oldPassword']";
	private static final String FIELD_PASSWORD_NEW_XP=			"//*[@id='newPassword']";
	private static final String FIELD_PASSWORD_NEW_RETYPE_XP=	"//*[@id='newPasswordVerification']";
	private static final String BUTTON_CHANGE_XP=				"//*[@id='changePassword']";
	private static final String FIELD_VALIDATOR=				"//span[@class='empty error']";
	private static final String LABEL_ERROR_MESSAGE=			"//*[contains(@class, 'portlet-msg-error')]";
	private static final String LABEL_SUCCESS_MESSAGE=			"//*[contains(@class, 'portlet-msg-info')]";


	public ChangePasswordPage(){
		super(new String[]{ROOT_XP, BUTTON_CHANGE_XP});
	}

	public void inputOldPassword(String password){
		WebDriverUtils.clearAndInputTextToField(FIELD_PASSWORD_OLD_XP, password);
	}

	public void inputNewPassword(String password){
		WebDriverUtils.clearAndInputTextToField(FIELD_PASSWORD_NEW_XP, password);
	}

	public void inputRetypeNewPassword(String password){
		WebDriverUtils.clearAndInputTextToField(FIELD_PASSWORD_NEW_RETYPE_XP, password);
	}

	public void clickChange(){
		WebDriverUtils.click(BUTTON_CHANGE_XP);
	}

	public ChangePasswordPage changePassword(String oldPass, String newPass){
		return changePassword(oldPass, newPass, newPass);
	}

	public ChangePasswordPage changePassword(String oldPass, String newPass, String confirmation){
		inputOldPassword(oldPass);
		inputNewPassword(newPass);
		inputRetypeNewPassword(confirmation);
		clickChange();
		return new ChangePasswordPage();
	}

	public boolean isErrorPresent(){
		return WebDriverUtils.isVisible(LABEL_ERROR_MESSAGE);
	}

	public boolean isSuccessMessagePresent(){
		return WebDriverUtils.isVisible(LABEL_SUCCESS_MESSAGE);
	}

	public boolean isFieldValidatorPresent(){
		return WebDriverUtils.isVisible(FIELD_VALIDATOR);
	}

//    public void validateOldPassword(ValidationRule rule) {
//        ValidationUtils.validate(FIELD_PASSWORD_OLD_XP, rule);
//    }
//
//    public void validateNewPassword(ValidationRule rule) {
//		ValidationUtils.validate(FIELD_PASSWORD_NEW_XP, rule);
//    }
}
