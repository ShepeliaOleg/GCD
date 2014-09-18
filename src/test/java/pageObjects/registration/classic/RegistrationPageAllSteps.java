package pageObjects.registration.classic;

import pageObjects.registration.RegistrationPage;
import springConstructors.UserData;
import springConstructors.ValidationRule;
import utils.WebDriverUtils;
import utils.validation.ValidationUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RegistrationPageAllSteps extends RegistrationPage {

    public final static String FIELD_EMAIL_VERIFICATION_NAME = 					        "confirmEmail";
    protected final static String FIELD_PHONE_XP = 									    "//*[@id='phoneNumber']";
    protected final static String FIELD_PHONE_COUNTRY_CODE_NAME  = 						"phoneAreaCode";

    protected final static String LABEL_GENDER_XP=									    "//label[@for='gender']";
    protected final static String LABEL_FIRSTNAME_XP =								    "//label[@for='firstname']";
    protected final static String LABEL_LASTNAME_XP=									"//label[@for='lastname']";
    protected final static String LABEL_BIRTHDAY_XP=									"//span[following-sibling::div/div[@class='date-section']]";
    protected final static String LABEL_EMAIL_XP=										"//label[@for='email']";
    protected final static String LABEL_EMAIL_VERIFICATION_XP=						    "//label[@for='confirmEmail']";
    protected final static String LABEL_ADDRESS_XP =									"//label[@for='address']";
    protected final static String LABEL_ADDRESS2_XP =									"//label[@for='address2']";
    protected final static String LABEL_HOUSE_XP =									    "//label[@for='house']";
    protected final static String LABEL_CITY_XP=										"//label[@for='city']";
    protected final static String LABEL_STATE_XP=										"//label[@for='state']";
    protected final static String LABEL_POSTCODE_XP=									"//label[@for='zip']";
    protected final static String LABEL_COUNTRY_XP=									    "//span[following-sibling::div/*[@name='"+DROPDOWN_COUNTRY_NAME+"']]";
    protected final static String LABEL_PHONE_XP=										"//span[following-sibling::div/span/*[@id='phoneNumber']]";
    protected final static String LABEL_USERNAME_XP=									ROOT_XP + "//label[@for='" + FIELD_USERNAME_NAME + "']";
    protected final static String LABEL_PASSWORD_XP=									ROOT_XP + "//label[@for='" + FIELD_PASSWORD_NAME + "']";
    protected final static String LABEL_PASSWORD_VERIFICATION_XP=						"//label[@for='"+FIELD_PASSWORD_VERIFICATION_NAME+"']";
    protected final static String LABEL_CURRENCY_XP=									"//span[following-sibling::div/*[@name='"+ DROPDOWN_CURRENCY_NAME +"']]";
    protected final static String LABEL_ANSWER_XP=									    "//label[@for='answer']";
    protected final static String LABEL_BONUSCODE_XP=									"//label[@for='coupon']";

    //optional
    protected final static String BUTTON_FILL_FIELDS_XP= 								"//*[@id='fillFields']";
    protected final static String DROPDOWN_NATIONALITY_XP=							    "//select[@id='nationality']";
    public final static String FIELD_EMAIL_VERIFICATION_XP=                             "//*[@name='"+FIELD_EMAIL_VERIFICATION_NAME+"']";
    public final static String LOADING_ANIMATION_XP=                                    "//*[@class='registration-overlay']";

    public RegistrationPageAllSteps(){
        super(new String[]{FIELD_PHONE_XP});
    }

    /*General*/

    public void registerNewUser(UserData userData, boolean isReceiveBonusesChecked, String bonusCode){
        fillRegistrationForm(userData, isReceiveBonusesChecked, bonusCode);
        clickSubmit();
    }

    public static void fillRegistrationForm(UserData userData, boolean isReceiveBonusesChecked, String bonusCode){
        fillUserData(userData);
        fillBonusAndPromotional(isReceiveBonusesChecked, bonusCode);
    }

    private static void fillUserData(UserData userData){
        fillGender(userData.getGender());
        fillFirstName(userData.getFirstName());
        fillLastName(userData.getLastName());
        fillBirthDay(userData.getBirthDay());
        fillBirthMonth(userData.getBirthMonth());
        fillBirthYear(userData.getBirthYear());
        fillEmail(userData.getEmail());
        fillEmailVerification(userData.getEmail());
        fillCountry(userData.getCountry());
        fillCity(userData.getCity());
        fillAddress(userData.getAddress());
        fillPostCode(userData.getPostCode());
        fillPhoneAreaCode(userData.getPhoneAreaCode());
        fillPhone(userData.getPhone());
        fillUsername(userData.getUsername());
        fillPassword(userData.getPassword());
        fillPasswordVerification(userData.getPassword());
        setCurrency(userData.getCurrency());
    }

    /*Inputs*/

    public static void fillEmailVerification(String confirmEmail){
        WebDriverUtils.clearAndInputTextToField(getXpathByName(FIELD_EMAIL_VERIFICATION_NAME), confirmEmail);
    }

    private static void fillPhone(String phone){
        WebDriverUtils.clearAndInputTextToField(FIELD_PHONE_XP, phone);
    }

    public static void fillPhoneAreaCode(String phoneAreaCode){
        WebDriverUtils.clearAndInputTextToField(getXpathByName(FIELD_PHONE_COUNTRY_CODE_NAME), phoneAreaCode);
    }

    /*Utility*/

    public static String getPhoneAreaCode(){
        return WebDriverUtils.getInputFieldText(getXpathByName(FIELD_PHONE_COUNTRY_CODE_NAME));
    }

    public Collection<String> getNationalitiesCodesList() {
        return WebDriverUtils.getDropdownOptionsValue(DROPDOWN_NATIONALITY_XP);
    }

    public void clickEmailConfirmation() {
        WebDriverUtils.click(getXpathByName(FIELD_EMAIL_VERIFICATION_NAME));
    }

    /*Postcode autofill*/

    public boolean isFindMyAddressButtonVisible(){
        return WebDriverUtils.isVisible(BUTTON_FILL_FIELDS_XP);
    }

    public boolean autofilledFieldsAreEditable() {
        boolean city = WebDriverUtils.isEditable(getXpathByName(FIELD_CITY_NAME));
        boolean address1 = WebDriverUtils.isEditable(getXpathByName(FIELD_ADDRESS_NAME));
        return city && address1;
    }

    public List<String> fillAutoByPostCode(String postCode) {
        fillPostCode(postCode);
        WebDriverUtils.click(BUTTON_FILL_FIELDS_XP);
        List<String> fullAddress = new ArrayList<>();
        fullAddress.add(getCity());
        fullAddress.add(getAddress());
        return fullAddress;
    }

    /*Validation*/

    public void validatePhoneAreaCodeField(ValidationRule rule) {
        ValidationUtils.validateField(getXpathByName(FIELD_PHONE_COUNTRY_CODE_NAME), rule, FIELD_PHONE_COUNTRY_CODE_NAME);
    }

    public void validatePhoneField(ValidationRule rule) {
        ValidationUtils.validateField(getXpathByName(FIELD_PHONE_XP), rule, FIELD_PHONE_COUNTRY_CODE_NAME);
    }

    private boolean labelMarkedAsRequired(String xpath){
        String labelText=WebDriverUtils.getElementText(xpath);
        return labelText.endsWith("*");
    }

    public boolean labelsRequiredMarkingCorrect(){
        boolean gender=labelMarkedAsRequired(LABEL_GENDER_XP);
        boolean first=labelMarkedAsRequired(LABEL_FIRSTNAME_XP);
        boolean last=labelMarkedAsRequired(LABEL_LASTNAME_XP);
        boolean birthDate=labelMarkedAsRequired(LABEL_BIRTHDAY_XP);
        boolean email1=labelMarkedAsRequired(LABEL_EMAIL_XP);
        boolean email2=labelMarkedAsRequired(LABEL_EMAIL_VERIFICATION_XP);
        boolean country=labelMarkedAsRequired(LABEL_COUNTRY_XP);
        boolean city=labelMarkedAsRequired(LABEL_CITY_XP);
        boolean address1=labelMarkedAsRequired(LABEL_ADDRESS_XP);
        boolean postCode=labelMarkedAsRequired(LABEL_POSTCODE_XP);
        boolean phone=labelMarkedAsRequired(LABEL_PHONE_XP);
        boolean username=labelMarkedAsRequired(LABEL_USERNAME_XP);
        boolean password1=labelMarkedAsRequired(LABEL_PASSWORD_XP);
        boolean password2=labelMarkedAsRequired(LABEL_PASSWORD_VERIFICATION_XP);
        boolean currency=labelMarkedAsRequired(LABEL_CURRENCY_XP);
        boolean bonus=labelMarkedAsRequired(LABEL_BONUSCODE_XP);
        boolean requiredMarked=gender && first && last && birthDate && email1 && email2 && country && city && address1 && postCode && phone && username && password1 && password2 && currency;
        boolean optionalUnmarked=bonus;
        return (requiredMarked == true && optionalUnmarked == false);
    }
}
