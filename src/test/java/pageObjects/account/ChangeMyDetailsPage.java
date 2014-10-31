package pageObjects.account;

import pageObjects.core.AbstractPage;
import springConstructors.UserData;
import springConstructors.ValidationRule;
import utils.WebDriverUtils;
import utils.core.AbstractTest;
import utils.validation.ValidationUtils;

/*
 * User: ivanva
 * Date: 5/30/13
 */

public class ChangeMyDetailsPage extends AbstractPage{

	public static final String ROOT_XP =		                "//*[contains(@class,'portlet-update-my-details')]";
	private static final String LABEL_CONFIRMATION_MESSAGE_XP=	"//*[contains(@class,'portlet-msg portlet-msg-info')]";
    private static final String LABEL_ERROR_MESSAGE_XP=			"//*[contains(@class,'portlet-msg portlet-msg-error')]";
	private final static String LABEL_TITLE=					"//*[@id='updateMyDetailsModel']//h3";
    private static final String LABEL_TOOLTIP_XP = 				"//*[@id='tooltip']//span";
    private final static String FIELD_EMAIL_XP= 				"//*[@id='email']";
    private final static String FIELD_EMAIL_VERIFICATION_XP= 	"//*[@id='emailVerification']";
    private final static String FIELD_CITY_XP= 					"//*[@id='city']";
    private final static String FIELD_ADDRESS_XP=  				"//*[@id='address']";
    private final static String FIELD_POSTCODE_XP = 			"//*[@id='zip']";
    private final static String FIELD_PHONE_XP = 				"//*[@id='telephone']";
    private final static String FIELD_MOBILE_XP= 				"//*[@id='cellPhone']";
	private final static String DROPDOWN_COUNTRY_XP=			"//*[@id='countryCode']";
	private static final String BUTTON_UPDATE_XP=				ROOT_XP + "//button[contains(@class,'btn')]";
    private static final String CHECKBOX_NOTIFICATION_EMAIL_XP=	"//*[@id='accountNotificationEmail']";
    private static final String CHECKBOX_NOTIFICATION_PHONE_XP=	"//*[@id='accountNotificationPhone']";
    private static final String CHECKBOX_NOTIFICATION_SMS_XP=	"//*[@id='accountNotificationSms']";

    public ChangeMyDetailsPage(){
        super(new String[]{ROOT_XP, BUTTON_UPDATE_XP});
    }

    public boolean isButtonActive() {
        //TODO
        return false;
    }

    public void editDetails(UserData userData){
		setCountry(userData.getCountry());
		setAddress(userData.getFullAddress());
		setCity(userData.getCity());
		setPostCode(userData.getPostCode());
		setPhone(userData.getPhoneAreaCode().concat(userData.getPhone()));
		setMobile(userData.getMobileAreaCode().concat(userData.getMobile()));
		setEmail(userData.getEmail());
		setEmailVerification(userData.getEmail());
		submitChanges();
	}

    public void assertUserData(UserData userData) {
        AbstractTest.assertTrue(isTitleContains(userData.getTitle()), "Title contains title");
        AbstractTest.assertTrue(isTitleContains(userData.getFirstName()), "Title contains firstname");
        AbstractTest.assertTrue(isTitleContains(userData.getLastName()), "Title contains lastname");
        AbstractTest.assertEquals(userData.getCountry(), WebDriverUtils.getDropdownSelectedOptionText(DROPDOWN_COUNTRY_XP), "Country");
        AbstractTest.assertEquals(userData.getFullAddress(), WebDriverUtils.getInputFieldText(FIELD_ADDRESS_XP), "Address");
        AbstractTest.assertEquals(userData.getCity(), WebDriverUtils.getInputFieldText(FIELD_CITY_XP), "City");
        AbstractTest.assertEquals(userData.getPostCode(), WebDriverUtils.getInputFieldText(FIELD_POSTCODE_XP), "Postcode");
        AbstractTest.assertEquals(userData.getPhoneAreaCode()+userData.getPhone(),WebDriverUtils.getInputFieldText(FIELD_PHONE_XP),"Country");
        AbstractTest.assertEquals(userData.getMobileAreaCode()+userData.getMobile(),WebDriverUtils.getInputFieldText(FIELD_MOBILE_XP),"Country");
        AbstractTest.assertEquals(userData.getEmail(),WebDriverUtils.getInputFieldText(FIELD_EMAIL_XP),"Country");
        AbstractTest.assertEquals(userData.getEmail(),WebDriverUtils.getInputFieldText(FIELD_EMAIL_VERIFICATION_XP),"Country");
    }

    public boolean isVisibleConfirmationMessage(){
		return WebDriverUtils.isVisible(LABEL_CONFIRMATION_MESSAGE_XP);
	}

	private boolean isTitleContains(String title){
		return WebDriverUtils.getElementText(LABEL_TITLE).contains(title);
	}

    private void setNotificationCheckboxEmail(boolean state){
        WebDriverUtils.setCheckBoxState(CHECKBOX_NOTIFICATION_EMAIL_XP, state);
    }

    private void setNotificationCheckboxTelephone(boolean state){
        WebDriverUtils.setCheckBoxState(CHECKBOX_NOTIFICATION_PHONE_XP, state);
    }

    private void setNotificationCheckboxSMS(boolean state){
        WebDriverUtils.setCheckBoxState(CHECKBOX_NOTIFICATION_SMS_XP, state);
    }

    private void setCountry(String country){
        WebDriverUtils.setDropdownOptionByText(DROPDOWN_COUNTRY_XP, country);
    }

    private void setAddress(String address){
        WebDriverUtils.clearAndInputTextToField(FIELD_ADDRESS_XP, address);
    }

    private void setCity(String city){
        WebDriverUtils.clearAndInputTextToField(FIELD_CITY_XP, city);
    }

    private void setPostCode(String postCode){
        WebDriverUtils.clearAndInputTextToField(FIELD_POSTCODE_XP, postCode);
    }

    private void setPhone(String phone){
        WebDriverUtils.clearAndInputTextToField(FIELD_PHONE_XP, phone);
    }

    private void setMobile(String mobile){
        WebDriverUtils.clearAndInputTextToField(FIELD_MOBILE_XP, mobile);
    }

    public void setEmail(String email){
        WebDriverUtils.clearAndInputTextToField(FIELD_EMAIL_XP, email);
    }

    public void setEmailVerification(String email){
        WebDriverUtils.clearAndInputTextToField(FIELD_EMAIL_VERIFICATION_XP, email);
    }

    private void submitChanges(){
        WebDriverUtils.click(BUTTON_UPDATE_XP);
    }

    /* Fields validation */

    public void validateEmail(ValidationRule rule) {
		ValidationUtils.validateField(FIELD_EMAIL_XP, rule, "");
    }

    public void validateVerificationEmail(ValidationRule rule) {
		ValidationUtils.validateField(FIELD_EMAIL_VERIFICATION_XP, rule, "");
    }

    public void validateCity(ValidationRule rule) {
		ValidationUtils.validateField(FIELD_CITY_XP, rule, "");
    }

    public void validateAddress(ValidationRule rule) {
		ValidationUtils.validateField(FIELD_ADDRESS_XP, rule, "");
    }

    public void validatePostcode(ValidationRule rule) {
		 ValidationUtils.validateField(FIELD_POSTCODE_XP, rule, "");
    }

    public void validatePhone(ValidationRule rule) {
        ValidationUtils.validateField(FIELD_PHONE_XP, rule, "");
    }

    public void validateMobile(ValidationRule rule) {
		ValidationUtils.validateField(FIELD_MOBILE_XP, rule, "");
    }

    public static String getEmailVerificationXpath() {
        return FIELD_EMAIL_VERIFICATION_XP;
    }
}
