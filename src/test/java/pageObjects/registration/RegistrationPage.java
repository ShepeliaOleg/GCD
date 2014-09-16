package pageObjects.registration;

import enums.Page;
import pageObjects.core.AbstractPage;
import pageObjects.core.AbstractPageObject;
import pageObjects.registration.classic.RegistrationPageAllSteps;
import pageObjects.registration.threeStep.RegistrationPageStepOne;
import pageObjects.registration.threeStep.RegistrationPageStepThree;
import pageObjects.registration.threeStep.RegistrationPageStepTwo;
import springConstructors.UserData;
import springConstructors.ValidationRule;
import utils.NavigationUtils;
import utils.WebDriverUtils;
import utils.validation.ValidationUtils;

import java.util.Collection;

public class RegistrationPage extends AbstractPage{

    protected final static String ROOT_XP =                                             "//*[contains(@class, 'fn-register-content')]";
    protected final static String FIELD_BASE_XP =                                       ROOT_XP + "//*[@name='"+PLACEHOLDER+"']";

    protected final static String BONUS_CODE_VALID = 			                        "TEST";
    protected final static String BONUS_CODE_INVALID = 		                            "HELL";

    protected final static String DROPDOWN_GENDER_NAME=		                            "sex";
    protected final static String FIELD_FIRSTNAME_NAME =				 				"firstname";
    protected final static String FIELD_LASTNAME_NAME = 								"lastname";
    protected final static String DROPDOWN_BIRTHDAY_NAME=								"birthDay";
    protected final static String DROPDOWN_BIRTHMONTH_NAME=								"birthMonth";
    protected final static String DROPDOWN_BIRTHYEAR_NAME=								"birthYear";
    protected final static String FIELD_EMAIL_NAME = 									"email";
    protected final static String FIELD_ADDRESS2_NAME = 								"address2";
    protected final static String FIELD_HOUSE_NAME = 								    "house";
    protected final static String FIELD_ANSWER_NAME = 								    "answer";
    protected final static String FIELD_ADDRESS_NAME = 								    "address";
    protected final static String FIELD_CITY_NAME = 									"city";
    protected final static String FIELD_STATE_NAME = 									"state";
    protected final static String FIELD_POSTCODE_NAME = 								"zip";
    protected final static String DROPDOWN_COUNTRY_NAME = 								"countrycode";
    public final static String FIELD_USERNAME_NAME = 								    "userName";
    protected final static String FIELD_PASSWORD_NAME = 								"password";
    public final static String FIELD_PASSWORD_VERIFICATION_NAME = 					    "passwordVerify";
    protected final static String DROPDOWN_CURRENCY_NAME = 						        "currencyCode";
    protected final static String FIELD_BONUSCODE_NAME = 								"coupon";
    protected final static String CHECKBOX_RECEIVE_BONUSES_XP=						    "//*[@id='nobonus'] | //*[@name='subscription']";
    protected final static String BUTTON_SUBMIT_XP = 									ROOT_XP + "//*[contains(@class,'fn-submit')]";

    protected final static String LABEL_USERNAME_SUGGESTION_LINK_XP = 					"//a[@class='fn-suggestion']";
    protected final static String LABEL_USERNAME_SUGGESTION_TOOLTIP_XP = 				LABEL_USERNAME_SUGGESTION_LINK_XP + "/..";
    protected final static String LINK_TERMS_AND_CONDITION_XP = 						"//*[@data-title='Terms & Conditions']";

    public final static String DROPDOWN_BIRTHDAY_XP=								    "//*[@name='"+DROPDOWN_BIRTHDAY_NAME+"']";
    public final static String DROPDOWN_BIRTHMONTH_XP=								    "//*[@name='"+DROPDOWN_BIRTHMONTH_NAME+"']";
    public final static String DROPDOWN_BIRTHYEAR_XP=								    "//*[@name='"+DROPDOWN_BIRTHYEAR_NAME+"']";
    public final static String FIELD_PHONE_COUNTRY_CODE_DESKTOP_XP =					"//*[@name='phoneAreaCode']";
    public final static String FIELD_PHONE_COUNTRY_CODE_MOBILE_XP=						"//*[@name='area']";
    public final static String FIELD_PHONE_XP=								            "//*[@name='phoneNumber']";
    public final static String FIELD_USERNAME_XP=								        "//*[@name='"+FIELD_USERNAME_NAME+"']";
    public final static String FIELD_PASSWORD_VERIFICATION_XP=							"//*[@name='"+FIELD_PASSWORD_VERIFICATION_NAME+"']";

    public RegistrationPage(String[] elements){
        super(elements);
    }

    public RegistrationPage(){
        super(new String[]{ROOT_XP});
    }

    public RegistrationPageAllSteps registrationPageAllSteps(){
        return new RegistrationPageAllSteps();
    }

    public RegistrationPageStepOne registrationPageStepOne(){
        return new RegistrationPageStepOne();
    }

    public RegistrationPageStepTwo registrationPageStepTwo(UserData userData){
        return new RegistrationPageStepOne().fillDataAndSubmit(userData);
    }

    public RegistrationPageStepThree registrationPageStepThree(UserData userData){
        return new RegistrationPageStepOne().fillDataAndSubmit(userData).fillDataAndSubmit(userData);
    }

    /*General*/

    public RegistrationPage fillAllFields(UserData userdata){
        if(platform.equals(PLATFORM_DESKTOP)){
            RegistrationPageAllSteps.fillRegistrationForm(userdata, true, null);
        }else{
            registrationPageStepThree(userdata).fillData(userdata, true, true, null);
        }
        return new RegistrationPage();
    }

    public AbstractPageObject registerUser(UserData userData){
        return registerUser(userData, Page.homePage);
    }

    public AbstractPageObject registerUser(UserData userData, Page expectedPage){
        return registerUser(userData, true, false, null, expectedPage);
    }

    public AbstractPageObject registerUser(UserData userData, String bonusCode, Page expectedPage){
        return registerUser(userData, true, false, bonusCode, expectedPage);
    }

    public AbstractPageObject registerUser(UserData userData, boolean termsAndConditions, boolean promotions, String bonusCode, Page expectedPage){
        if(platform.equals(PLATFORM_DESKTOP)){
            new RegistrationPageAllSteps().registerNewUser(userData, promotions, bonusCode);
        }else {
            new RegistrationPageStepOne().registerNewUser(userData, termsAndConditions, promotions, bonusCode);
        }
        return NavigationUtils.closeAllPopups(expectedPage);
    }

    protected static void fillBonusAndPromotional(boolean isReceivePromotionalOffers, String bonusCode){
        setCheckboxReceivePromotional(isReceivePromotionalOffers);
        if(bonusCode!=null){
            if(bonusCode.equals("valid")){
                fillBonusCode(BONUS_CODE_VALID);
            }else if(bonusCode.equals("invalid")){
                fillBonusCode(BONUS_CODE_INVALID);
            }
        }

    }

    /*Inputs*/

    protected static void fillGender(String title){
        WebDriverUtils.setDropdownOptionByValue(getXpathByName(DROPDOWN_GENDER_NAME), title);
    }

    protected static void fillFirstName(String firstName){
        WebDriverUtils.clearAndInputTextToField(getXpathByName(FIELD_FIRSTNAME_NAME), firstName);
    }

    protected static void fillLastName(String lastName){
        WebDriverUtils.clearAndInputTextToField(getXpathByName(FIELD_LASTNAME_NAME), lastName);
    }

    protected static void fillBirthDay(String birthDay){
        WebDriverUtils.setDropdownOptionByText(getXpathByName(DROPDOWN_BIRTHDAY_NAME), birthDay);
    }

    protected static void fillBirthMonth(String birthMonth){
        WebDriverUtils.setDropdownOptionByText(getXpathByName(DROPDOWN_BIRTHMONTH_NAME), birthMonth);
    }

    protected static void fillBirthYear(String birthYear){
        WebDriverUtils.setDropdownOptionByText(getXpathByName(DROPDOWN_BIRTHYEAR_NAME), birthYear);
    }

    public static void fillEmail(String email){
        WebDriverUtils.clearAndInputTextToField(getXpathByName(FIELD_EMAIL_NAME), email);
    }
    public static void fillEmailVerification(String confirmEmail){
        if(platform.equals(PLATFORM_DESKTOP)){
            RegistrationPageAllSteps.fillEmailVerification(confirmEmail);
        }else {
            RegistrationPageStepOne.fillEmailVerification(confirmEmail);
        }
    }

    protected static void fillAddress(String address){
        WebDriverUtils.clearAndInputTextToField(getXpathByName(FIELD_ADDRESS_NAME), address);
    }

    protected static void fillCity(String city){
        WebDriverUtils.clearAndInputTextToField(getXpathByName(FIELD_CITY_NAME), city);
    }

    protected static void fillPostCode(String postCode){
        WebDriverUtils.clearAndInputTextToField(getXpathByName(FIELD_POSTCODE_NAME), postCode);
    }

    public static void fillCountry(String countryCode){
        WebDriverUtils.setDropdownOptionByValue(getXpathByName(DROPDOWN_COUNTRY_NAME), countryCode);
    }

    public static void fillUsername(String username){
        WebDriverUtils.clearAndInputTextToField(getXpathByName(FIELD_USERNAME_NAME), username);
    }

    public static void fillPassword(String password){
        WebDriverUtils.clearAndInputTextToField(getXpathByName(FIELD_PASSWORD_NAME), password);
    }

    public static void fillPasswordVerification(String confirmPassword){
        WebDriverUtils.clearAndInputTextToField(getXpathByName(FIELD_PASSWORD_VERIFICATION_NAME), confirmPassword);
    }

    protected static void setCurrency(String currencyCode){
        WebDriverUtils.setDropdownOptionByText(getXpathByName(DROPDOWN_CURRENCY_NAME), currencyCode);
    }

    protected static void fillBonusCode(String coupon){
        WebDriverUtils.clearAndInputTextToField(getXpathByName(FIELD_BONUSCODE_NAME), coupon);
    }

    private static void setCheckboxReceivePromotional(boolean desiredState){
        WebDriverUtils.setCheckBoxState(CHECKBOX_RECEIVE_BONUSES_XP, desiredState);
    }

    protected static void clickSubmit(){
        WebDriverUtils.click(BUTTON_SUBMIT_XP);
    }

    protected ReadTermsAndConditionsPopup navigateToTermsAndConditions(){
        WebDriverUtils.click(LINK_TERMS_AND_CONDITION_XP);
        return new ReadTermsAndConditionsPopup();
    }

    /*Utility*/

    protected String getCity(){
        return WebDriverUtils.getInputFieldText(getXpathByName(FIELD_CITY_NAME));
    }

    protected String getAddress(){
        return WebDriverUtils.getInputFieldText(getXpathByName(FIELD_ADDRESS_NAME));
    }

    public String getSelectedCountryName(){
        return WebDriverUtils.getDropdownSelectedOption(getXpathByName(DROPDOWN_COUNTRY_NAME)).getText().trim();
    }

    public String getSelectedCurrency(){
        return WebDriverUtils.getDropdownSelectedOption(getXpathByName(DROPDOWN_CURRENCY_NAME)).getText();
    }

    public boolean getCheckboxStateReceiveBonuses(UserData userData){
        if(platform.equals(PLATFORM_MOBILE)){
            registrationPageStepThree(userData);
        }
        return WebDriverUtils.getCheckBoxState(CHECKBOX_RECEIVE_BONUSES_XP);
    }

    public void clickPasswordConfirmation(){
        WebDriverUtils.click(getXpathByName(FIELD_PASSWORD_VERIFICATION_NAME));
    }

    public Collection<String> getCountriesCodesList(UserData userData) {
        if(platform.equals(PLATFORM_MOBILE)){
            registrationPageStepTwo(userData);
        }
        return WebDriverUtils.getDropdownOptionsValue(getXpathByName(DROPDOWN_COUNTRY_NAME));
    }

    public Collection<String> getCurrencyList(UserData userData) {
        if(platform.equals(PLATFORM_MOBILE)){
            registrationPageStepThree(userData);
        }
        return WebDriverUtils.getDropdownOptionsText(getXpathByName(DROPDOWN_CURRENCY_NAME));
    }

    public String getUsernameSuggestion(String username, UserData userData) {
        if(platform.equals(PLATFORM_MOBILE)){
            registrationPageStepThree(userData);
        }
        inputAndRefocusUsername(username);
        if(isSuggestionVisible()){
            return WebDriverUtils.getElementText(LABEL_USERNAME_SUGGESTION_TOOLTIP_XP);
        }else {
            return "No username suggestion";
        }
    }

    public boolean clickUsernameSuggestionAndValidateInput(String username, UserData userData){
        String suggestion= getUsernameSuggestion(username, userData);
        if(suggestion.equals("No username suggestion")){
            return false;
        }else {
            String text = WebDriverUtils.getElementText(LABEL_USERNAME_SUGGESTION_LINK_XP);
            WebDriverUtils.click(LABEL_USERNAME_SUGGESTION_LINK_XP);
            WebDriverUtils.waitFor(1000);
            return text.equals(getFilledUsername());
        }
    }

    public static void inputAndRefocusUsername(String username){
        ValidationUtils.inputFieldAndRefocus(getXpathByName(FIELD_USERNAME_NAME), username);
    }

    public static boolean isSuggestionVisible(){
        return WebDriverUtils.isVisible(LABEL_USERNAME_SUGGESTION_TOOLTIP_XP, 0);
    }
    public static String getFilledUsername(){
        return WebDriverUtils.getInputFieldText(getXpathByName(FIELD_USERNAME_NAME));
    }

    /* Fields validation */

    public void validateGender(ValidationRule rule) {
        ValidationUtils.validateDropdown(getXpathByName(DROPDOWN_GENDER_NAME), rule, DROPDOWN_GENDER_NAME);
    }

    public void validateFirstname(ValidationRule rule) {
        ValidationUtils.validateField(getXpathByName(FIELD_FIRSTNAME_NAME), rule, FIELD_FIRSTNAME_NAME);
    }

    public void validateLastname(ValidationRule rule) {
        ValidationUtils.validateField(getXpathByName(FIELD_LASTNAME_NAME), rule, FIELD_LASTNAME_NAME);
    }

    public void validateDateOfBirth(ValidationRule rule) {
        ValidationUtils.validateDropdown(getXpathByName(DROPDOWN_BIRTHDAY_NAME), rule, DROPDOWN_BIRTHDAY_NAME);
    }

    public void validateEmail(ValidationRule rule) {
        ValidationUtils.validateField(getXpathByName(FIELD_EMAIL_NAME), rule, FIELD_EMAIL_NAME);
    }

    public void validateCountry(ValidationRule rule, UserData userData) {
        if(platform.equals(PLATFORM_MOBILE)){
            registrationPageStepTwo(userData);
        }
        ValidationUtils.validateDropdown(getXpathByName(DROPDOWN_COUNTRY_NAME), rule, DROPDOWN_COUNTRY_NAME);
    }

    public void validateCity(ValidationRule rule, UserData userData) {
        if(platform.equals(PLATFORM_MOBILE)){
            registrationPageStepTwo(userData);
        }
        ValidationUtils.validateField(getXpathByName(FIELD_CITY_NAME), rule, FIELD_CITY_NAME);
    }

    public void validateAddress(ValidationRule rule, UserData userData) {
        if(platform.equals(PLATFORM_MOBILE)){
            registrationPageStepTwo(userData);
        }
        ValidationUtils.validateField(getXpathByName(FIELD_ADDRESS_NAME), rule, FIELD_ADDRESS_NAME);
    }

    public void validatePostcode(ValidationRule rule, UserData userData) {
        if(platform.equals(PLATFORM_MOBILE)){
            registrationPageStepTwo(userData);
        }
        ValidationUtils.validateField(getXpathByName(FIELD_POSTCODE_NAME), rule, FIELD_POSTCODE_NAME);
    }

    public void validatePhone(ValidationRule rule, UserData userData){
        if(platform.equals(PLATFORM_MOBILE)){
            registrationPageStepTwo(userData).validatePhone(rule, userData);
        }else {
            registrationPageAllSteps().validatePhone(rule, userData);
        }
    }

    public void validatePhoneAreaCode(ValidationRule rule, UserData userData){
        if(platform.equals(PLATFORM_MOBILE)){
            registrationPageStepTwo(userData).validatePhoneAreaCode(rule, userData);
        }else {
            registrationPageAllSteps().validatePhoneAreaCode(rule, userData);
        }
    }

    public void validateUsername(ValidationRule rule, UserData userData) {
        if(platform.equals(PLATFORM_MOBILE)){
            registrationPageStepThree(userData);
        }
        ValidationUtils.validateField(getXpathByName(FIELD_USERNAME_NAME), rule, FIELD_USERNAME_NAME);
    }

    public void validatePassword(ValidationRule rule, UserData userData) {
        if(platform.equals(PLATFORM_MOBILE)){
            registrationPageStepThree(userData);
        }
        ValidationUtils.validateField(getXpathByName(FIELD_PASSWORD_NAME), rule, FIELD_PASSWORD_NAME);
    }

    public void validateState(ValidationRule rule, UserData userData) {
        if(platform.equals(PLATFORM_MOBILE)){
            registrationPageStepTwo(userData);
        }
        ValidationUtils.validateField(getXpathByName(FIELD_STATE_NAME), rule, FIELD_STATE_NAME);
    }

    public void validateAddress2(ValidationRule rule, UserData userData) {
        if(platform.equals(PLATFORM_MOBILE)){
            registrationPageStepTwo(userData);
        }
        ValidationUtils.validateField(getXpathByName(FIELD_ADDRESS2_NAME), rule, FIELD_ADDRESS2_NAME);
    }

    public void validateHouse(ValidationRule rule, UserData userData) {
        if(platform.equals(PLATFORM_MOBILE)){
            registrationPageStepTwo(userData);
        }
        ValidationUtils.validateField(getXpathByName(FIELD_HOUSE_NAME), rule, FIELD_HOUSE_NAME);
    }

    public void validateQuestion(ValidationRule rule, UserData userData) {
        if(platform.equals(PLATFORM_MOBILE)){
            registrationPageStepThree(userData).validateQuestion(rule, userData);
        }
    }

    public void validateAnswer(ValidationRule rule, UserData userData) {
        if(platform.equals(PLATFORM_MOBILE)){
            registrationPageStepThree(userData).validateAnswer(rule, userData);
        }
    }

    public void validateCurrency(ValidationRule rule, UserData userData) {
        if(platform.equals(PLATFORM_MOBILE)){
            registrationPageStepThree(userData);
        }
        ValidationUtils.validateDropdown(getXpathByName(DROPDOWN_CURRENCY_NAME), rule, DROPDOWN_CURRENCY_NAME);
    }

    public void validateBonusCode(ValidationRule rule, UserData userData) {
        if(platform.equals(PLATFORM_MOBILE)){
            registrationPageStepThree(userData);
        }
        ValidationUtils.validateField(getXpathByName(FIELD_BONUSCODE_NAME), rule, FIELD_BONUSCODE_NAME);
    }

    protected static String getXpathByName(String id){
        return FIELD_BASE_XP.replace(PLACEHOLDER, id);
    }

    public static String getEmailVerificationXpath(){
        if(platform.equals(PLATFORM_DESKTOP)){
            return getXpathByName(RegistrationPageAllSteps.FIELD_EMAIL_VERIFICATION_NAME);
        }else {
            return getXpathByName(RegistrationPageStepOne.FIELD_EMAIL_VERIFICATION_NAME);
        }
    }

    public static String getEmailVerificationName(){
        if(platform.equals(PLATFORM_DESKTOP)){
            return RegistrationPageAllSteps.FIELD_EMAIL_VERIFICATION_NAME;
        }else {
            return RegistrationPageStepOne.FIELD_EMAIL_VERIFICATION_NAME;
        }
    }

    public void clickUsernameField() {
        WebDriverUtils.click(getXpathByName(FIELD_USERNAME_NAME));
    }
}
