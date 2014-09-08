package pageObjects.account;

import pageObjects.base.AbstractPage;
import springConstructors.UserData;
import utils.WebDriverUtils;

/*
 * User: ivanva
 * Date: 5/30/13
 */

public class ChangeMyDetailsPage extends AbstractPage{

	public static final String ROOT_XP =		                "//*[contains(@id,'updatemydetails')]";
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

    public void setNotificationCheckboxes(boolean state){
        setNotificationCheckboxes(state, state, state);
    }

    public void setNotificationCheckboxes(boolean email, boolean phone, boolean sms){
        setNotificationCheckboxEmail(email);
        setNotificationCheckboxTelephone(phone);
        setNotificationCheckboxSMS(sms);
        submitChanges();
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

	private void editCountry(String country){
		WebDriverUtils.setDropdownOptionByText(DROPDOWN_COUNTRY_XP, country);
	}

	private void editAddress(String address){
		WebDriverUtils.clearAndInputTextToField(FIELD_ADDRESS_XP, address);
	}

	private void editCity(String city){
		WebDriverUtils.clearAndInputTextToField(FIELD_CITY_XP, city);
	}

	private void editPostCode(String postCode){
		WebDriverUtils.clearAndInputTextToField(FIELD_POSTCODE_XP, postCode);
	}

	private void editPhone(String phone){
		WebDriverUtils.clearAndInputTextToField(FIELD_PHONE_XP, phone);
	}

	private void editMobile(String mobile){
		WebDriverUtils.clearAndInputTextToField(FIELD_MOBILE_XP, mobile);
	}

	public void editEmail(String email){
		WebDriverUtils.clearAndInputTextToField(FIELD_EMAIL_XP, email);
	}

	public void editEmailVerification(String email){
		WebDriverUtils.clearAndInputTextToField(FIELD_EMAIL_VERIFICATION_XP, email);
	}

	public void submitChanges(){
		WebDriverUtils.click(BUTTON_UPDATE_XP);
	}

	public void editDetails(UserData userData){
		editCountry(userData.getCountry());
		editAddress(userData.getFullAddress());
		editCity(userData.getCity());
		editPostCode(userData.getPostCode());
		editPhone(userData.getPhoneAreaCode().concat(userData.getPhone()));
		editMobile(userData.getMobileAreaCode().concat(userData.getMobile()));
		editEmail(userData.getEmail());
		editEmailVerification(userData.getEmail());
		submitChanges();
	}

    public boolean detailsAreEqualsTo(UserData userData) {
        boolean title1=isTitleContains(userData.getTitle());
        boolean title2=isTitleContains(userData.getFirstName());
        boolean title3=isTitleContains(userData.getLastName());
        boolean country=isCountryEqualsTo(userData.getCountry());
        boolean address=isAddressEqualsTo(userData.getFullAddress());
        boolean city=isCityEqualsTo(userData.getCity());
        boolean postCode=isPostCodeEqualsTo(userData.getPostCode());
        boolean phone=isPhoneEqualsTo(userData.getPhoneAreaCode().concat(userData.getPhone()));
        boolean mobile=isMobileEqualsTo(userData.getMobileAreaCode().concat(userData.getMobile()));
        boolean email1=isEmailEqualsTo(userData.getEmail());
        boolean email2=isConfirmEmailEqualsTo(userData.getEmail());
        return (title1 && title2 && title3 && country && address && city && postCode && phone && mobile && email1 && email2);
    }

    public boolean isVisibleConfirmationMessage(){
		return WebDriverUtils.isVisible(LABEL_CONFIRMATION_MESSAGE_XP);
	}

    public boolean isVisibleErrorMessage(){
        return WebDriverUtils.isVisible(LABEL_ERROR_MESSAGE_XP);
    }

	private boolean isTitleContains(String title){
		return WebDriverUtils.getElementText(LABEL_TITLE).contains(title);
	}

	private boolean isCountryEqualsTo(String country){
		return WebDriverUtils.getDropdownSelectedOptionText(DROPDOWN_COUNTRY_XP).equals(country);
	}

	private boolean isAddressEqualsTo(String address){
		return WebDriverUtils.getInputFieldText(FIELD_ADDRESS_XP).equals(address);
	}

	private boolean isCityEqualsTo(String city){
		return WebDriverUtils.getInputFieldText(FIELD_CITY_XP).equals(city);
	}

	private boolean isPostCodeEqualsTo(String postCode){
		return (WebDriverUtils.getInputFieldText(FIELD_POSTCODE_XP)).equalsIgnoreCase(postCode);
	}

	private boolean isPhoneEqualsTo(String phone){
		return WebDriverUtils.getInputFieldText(FIELD_PHONE_XP).equals(phone);
	}

	private boolean isMobileEqualsTo(String mobile){
		return WebDriverUtils.getInputFieldText(FIELD_MOBILE_XP).equals(mobile);
	}

	private boolean isEmailEqualsTo(String email){
		return WebDriverUtils.getInputFieldText(FIELD_EMAIL_XP).equals(email);
	}

	private boolean isConfirmEmailEqualsTo(String email){
		return WebDriverUtils.getInputFieldText(FIELD_EMAIL_VERIFICATION_XP).equals(email);
	}


    //Tooltips methods

    public String getTooltipMessageText() {
        return getVisibleMessageText(LABEL_TOOLTIP_XP);
    }

    private String getVisibleMessageText(String xpath) {
        if (WebDriverUtils.isVisible(xpath, 1)) {
            return WebDriverUtils.getElementText(xpath);
        } else {
			WebDriverUtils.runtimeExceptionWithLogs("Expected message is not visible: " + xpath);
        }
		return null;
    }

	public void clickEmailField() {
		WebDriverUtils.click(FIELD_EMAIL_VERIFICATION_XP);
	}

    /* Fields validation */

//    public void validateEmail(ValidationRule rule) {
//		ValidationUtils.validate(FIELD_EMAIL_XP, rule);
//    }
//
//    public void validateVerificationEmail(ValidationRule rule) {
//		ValidationUtils.validate(FIELD_EMAIL_VERIFICATION_XP, rule);
//    }
//
//    public void validateCity(ValidationRule rule) {
//		ValidationUtils.validate(FIELD_CITY_XP, rule);
//    }
//
//    public void validateAddress(ValidationRule rule) {
//		ValidationUtils.validate(FIELD_ADDRESS_XP, rule);
//    }
//
//    public void validatePostcode(ValidationRule rule) {
//		 ValidationUtils.validate(FIELD_POSTCODE_XP, rule);
//    }
//
//    public void validatePhone(ValidationRule rule) {
//        ValidationUtils.validate(FIELD_PHONE_XP, rule);
//    }
//
//    public void validateMobile(ValidationRule rule) {
//		ValidationUtils.validate(FIELD_MOBILE_XP, rule);
//    }

}
