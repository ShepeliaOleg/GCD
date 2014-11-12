package pageObjects.changeMyDetails;

import pageObjects.core.AbstractPortalPage;
import springConstructors.UserData;
import springConstructors.ValidationRule;
import utils.WebDriverUtils;
import utils.core.AbstractTest;
import utils.validation.ValidationUtils;

public class ChangeMyDetailsPage extends AbstractPortalPage {

	public static final String ROOT_XP =		                "//*[contains(@class,'portlet-update-my-details')]";
    private final static String ADDRESS_NAME=			        "address";
    private final static String COUNTRY_NAME=			        "country";
    private final static String PHONE_NAME =                    "phoneWithArea";
    private final static String CITY_NAME =                     "city";
    private final static String ZIP_NAME =                      "zip";
    private final static String EMAIL_NAME =                    "email";

    private final static String LABEL_NAME =					"//*[@id='name']";
    private final static String FIELD_EMAIL_XP= 				"//*[@id='"+EMAIL_NAME+"']";
    private final static String FIELD_CITY_XP= 					"//*[@id='"+CITY_NAME+"']";
    private final static String FIELD_ADDRESS_XP=  				"//*[@id='"+ADDRESS_NAME+"']";
    private final static String FIELD_POSTCODE_XP = 			"//*[@id='"+ZIP_NAME+"']";
    private final static String FIELD_PHONE_XP = 				"//*[@id='telephone']";
    private final static String FIELD_MOBILE_XP= 				"//*[@id='cellphone']";
	private final static String DROPDOWN_COUNTRY_XP=			"//*[@id='countryCode']";
	private static final String BUTTON_UPDATE_XP=				ROOT_XP + "//button[@type='submit']";
    private static final String DISABLED_XP =                   "[@disabled='']";

    public ChangeMyDetailsPage(){
        super(new String[]{ROOT_XP, BUTTON_UPDATE_XP});
    }

    public boolean isButtonDisabled() {
        return WebDriverUtils.isVisible(BUTTON_UPDATE_XP+DISABLED_XP, 1);
    }

    public DetailsChangedPopup changeDetailsAndSubmit(UserData userData){
		changeDetails(userData);
		submitChanges();
        return new DetailsChangedPopup();
	}

    public void changeDetails(UserData userData){
        setCountry(userData.getCountry());
        setAddress(userData.getFullAddress());
        setCity(userData.getCity());
        setPostCode(userData.getPostCode());
//        setPhone(userData.getPhoneAreaCode().concat(userData.getPhone()));
        setMobile(userData.getMobileAreaCode().concat(userData.getMobile()));
        setEmail(userData.getEmail());
    }

    public void assertUserData(UserData userData) {
        AbstractTest.assertTrue(nameContains(userData.getTitle()), "Name field contains title");
        AbstractTest.assertTrue(nameContains(userData.getFirstName()), "Name field contains firstname");
        AbstractTest.assertTrue(nameContains(userData.getLastName()), "Name field contains lastname");
        AbstractTest.assertEquals(userData.getCountry(), WebDriverUtils.getDropdownSelectedOptionValue(DROPDOWN_COUNTRY_XP), "Country");
        AbstractTest.assertEquals(userData.getFullAddress(), WebDriverUtils.getInputFieldText(FIELD_ADDRESS_XP), "Address");
        AbstractTest.assertEquals(userData.getCity(), WebDriverUtils.getInputFieldText(FIELD_CITY_XP), "City");
        AbstractTest.assertEquals(userData.getPostCode().toUpperCase(), (WebDriverUtils.getInputFieldText(FIELD_POSTCODE_XP)), "Postcode");
//        AbstractTest.assertEquals(userData.getPhoneAreaCode()+userData.getPhone(), WebDriverUtils.getInputFieldText(FIELD_PHONE_XP), "Phone");
        AbstractTest.assertEquals(userData.getMobileAreaCode()+userData.getMobile(), WebDriverUtils.getInputFieldText(FIELD_MOBILE_XP), "Mobile");
        AbstractTest.assertEquals(userData.getEmail(), WebDriverUtils.getInputFieldText(FIELD_EMAIL_XP), "Email");
    }

	private boolean nameContains(String title){
        return WebDriverUtils.getInputFieldText(LABEL_NAME).contains(title);
	}

    private void setCountry(String country){
        WebDriverUtils.setDropdownOptionByValue(DROPDOWN_COUNTRY_XP, country);
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

    private void submitChanges(){
        WebDriverUtils.click(BUTTON_UPDATE_XP);
    }

    /* Fields validation */

    public void validateEmail(ValidationRule rule) {
		ValidationUtils.validateField(FIELD_EMAIL_XP, rule, EMAIL_NAME);
    }

    public void validateCity(ValidationRule rule) {
		ValidationUtils.validateField(FIELD_CITY_XP, rule, CITY_NAME);
    }

    public void validateAddress(ValidationRule rule) {
		ValidationUtils.validateField(FIELD_ADDRESS_XP, rule, ADDRESS_NAME);
    }

    public void validatePostcode(ValidationRule rule) {
		 ValidationUtils.validateField(FIELD_POSTCODE_XP, rule, ZIP_NAME);
    }

    public void validatePhone(ValidationRule rule) {
        ValidationUtils.validateField(FIELD_PHONE_XP, rule, PHONE_NAME);
    }

    public void validateMobile(ValidationRule rule) {
		ValidationUtils.validateField(FIELD_MOBILE_XP, rule, PHONE_NAME);
    }

    public void validateCountry(ValidationRule rule) {
        ValidationUtils.validateDropdown(DROPDOWN_COUNTRY_XP, rule, COUNTRY_NAME);
    }
}
