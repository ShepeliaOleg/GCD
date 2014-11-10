package pageObjects.registration;

import enums.Licensee;
import enums.Page;
import enums.PasswordStrength;
import enums.PromoCode;
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
import utils.core.AbstractTest;
import utils.core.DataContainer;
import utils.validation.ValidationUtils;

import java.util.Collection;
import java.util.List;

public class RegistrationPage extends AbstractPage{

    protected final static String ROOT_XP =                                             "//*[contains(@class, 'fn-register-content')]";
    protected final static String FIELD_BASE_XP =                                       ROOT_XP + "//*[@name='"+PLACEHOLDER+"']";
    protected final static String DROPDOWN_GENDER_VALIDATION_NAME =		                "gender";
    protected final static String DROPDOWN_GENDER_NAME =		                        "sex";
    protected final static String FIELD_FIRSTNAME_NAME =				 				"firstname";
    protected final static String FIELD_LASTNAME_NAME = 								"lastname";
    protected final static String DROPDOWN_BIRTHDATE_VALIDATION_NAME =					"birthdate";
    protected final static String DROPDOWN_BIRTHDAY_NAME =						        "birthDay";
    protected final static String DROPDOWN_BIRTHMONTH_NAME=								"birthMonth";
    protected final static String DROPDOWN_BIRTHYEAR_NAME=								"birthYear";
    public    final static String DROPDOWN_BIRTHDAY_XP =                                getXpathByName(DROPDOWN_BIRTHDAY_NAME);
    public    final static String DROPDOWN_BIRTHMONTH_XP =                              getXpathByName(DROPDOWN_BIRTHMONTH_NAME);
    public    final static String DROPDOWN_BIRTHYEAR_XP =                               getXpathByName(DROPDOWN_BIRTHYEAR_NAME);
    protected final static String FIELD_EMAIL_NAME = 									"email";
    protected final static String FIELD_ADDRESS2_NAME = 								"address2";
    protected final static String FIELD_HOUSE_NAME = 								    "address3";
    protected final static String FIELD_ANSWER_NAME = 								    "answer";
    protected final static String FIELD_ADDRESS_NAME = 								    "address";
    protected final static String FIELD_CITY_NAME = 									"city";
    protected final static String FIELD_STATE_NAME = 									"state";
    protected final static String FIELD_POSTCODE_NAME = 								"zip";
    protected final static String DROPDOWN_COUNTRY_VALIDATION_NAME = 					"country";
    protected final static String DROPDOWN_COUNTRY_NAME = 					            "countrycode";
    public    final static String FIELD_USERNAME_NAME = 								"userName";
    protected final static String FIELD_PASSWORD_NAME = 								"password";
    public    final static String FIELD_PASSWORD_VERIFICATION_NAME = 					"passwordVerify";
    protected final static String DROPDOWN_CURRENCY_NAME = 						        "currencyCode";
    protected final static String FIELD_BONUSCODE_NAME = 								"coupon";
    protected final static String CHECKBOX_RECEIVE_BONUSES_XP=						    "//*[@name='subscription']";
    protected final static String BUTTON_SUBMIT_XP = 									ROOT_XP + "//*[contains(@class,'fn-submit')]";
    protected final static String LABEL_USERNAME_SUGGESTION_LINK_XP = 					"//a[@class='fn-suggestion']";
    protected final static String LABEL_USERNAME_SUGGESTION_TOOLTIP_XP = 				LABEL_USERNAME_SUGGESTION_LINK_XP + "/..";
    protected final static String LINK_TERMS_AND_CONDITION_XP = 						"//*[@data-title='Terms & Conditions']";
    protected final static String LABEL_RECEIVE_PROMOTIONS_XP=							"//label[@for='subscription-checkbox']";
    public    final static String FIELD_PHONE_COUNTRY_CODE_DESKTOP_XP =					"//*[@name='phoneAreaCode']";
    public    final static String FIELD_PHONE_COUNTRY_CODE_MOBILE_XP=					"//*[@name='area']";
    public    final static String FIELD_PHONE_XP=								        "//*[@name='phoneNumber']";
    public    final static String FIELD_USERNAME_XP=								    "//*[@name='"+FIELD_USERNAME_NAME+"']";
    public    final static String FIELD_PASSWORD_VERIFICATION_XP=						"//*[@name='"+FIELD_PASSWORD_VERIFICATION_NAME+"']";
    private   final static String PASSWORD_METER_XP =                                   "//*[@class='password-meter']/*[1]";
    private   final static String PASSWORD_STRENGTH_TOOLTIP =                           "//*[contains(@class, 'password-meter_message')]/p";

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

    public RegistrationPageStepTwo registrationPageStepTwo(){
        return registrationPageStepOne().fillDataAndSubmit(DataContainer.getUserData().getRandomUserData());
    }

    public RegistrationPageStepTwo registrationPageStepTwo(UserData userData){
        return registrationPageStepOne().fillDataAndSubmit(userData);
    }

    public RegistrationPageStepThree registrationPageStepThree(){
        UserData userData = DataContainer.getUserData().getRandomUserData();
        return registrationPageStepTwo(userData).fillDataAndSubmit(userData);
    }

    public RegistrationPageStepThree registrationPageStepThree(UserData userData){
        return registrationPageStepTwo(userData).fillDataAndSubmit(userData);
    }

    /*General*/

    public AbstractPageObject registerUser(UserData userData){
        return registerUser(userData, Page.homePage);
    }

    public AbstractPageObject registerUser(UserData userData, Page expectedPage){
        return registerUser(userData, true, false, null, expectedPage, true);
    }

    public AbstractPageObject registerUser(UserData userData, boolean clearDeviceId){
        return registerUser(userData, true, false, null, Page.homePage, clearDeviceId);
    }

    public AbstractPageObject registerUser(UserData userData, PromoCode promoCode, Page expectedPage){
        return registerUser(userData, true, false, promoCode, expectedPage, true);
    }

    public AbstractPageObject registerUser(UserData userData, boolean termsAndConditions, boolean promotions, PromoCode promoCode, Page expectedPage, boolean clearDeviceId){
        System.out.println("Registering user: \n"
                + "Username: " + userData.getUsername()+"\n"
                + "Country:  " + userData.getCountry()+"\n"
                + "Currency: " + userData.getCurrencyName()+"\n");
        if (clearDeviceId) {
            WebDriverUtils.removeLocalStorageItem("serial");
        }
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.sevenRegal)){
            registrationPageAllSteps().registerNewUser(userData, promotions, promoCode);
        }else{
            registrationPageStepThree(userData).fillDataAndSubmit(userData, termsAndConditions, promotions, promoCode);
        }
        if(expectedPage.equals(Page.registrationPage)){
            return new RegistrationPage();
        }else {
            return NavigationUtils.closeAllPopups(expectedPage);
        }
    }

    protected static void fillBonusAndPromotional(boolean isReceivePromotionalOffers, PromoCode promoCode){
        setCheckboxReceivePromotional(isReceivePromotionalOffers);
        if(promoCode!=null){
            fillBonusCode(promoCode.getCode());
        }
    }

    public RegistrationPage fillAllFields(UserData userData, boolean termsAndConditions, boolean promotions, PromoCode promoCode){
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.sevenRegal)){
            new RegistrationPageAllSteps().fillRegistrationForm(userData, promotions, promoCode);
        }else{
            registrationPageStepThree(userData).fillData(userData, termsAndConditions, promotions, promoCode);
        }
        return new RegistrationPage();
    }

    public void fillAllFieldsAndSubmit(UserData userData, boolean termsAndConditions, boolean promotions, PromoCode promoCode){
        fillAllFields(userData, termsAndConditions, promotions, promoCode).clickSubmit();
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
        WebDriverUtils.setDropdownOptionByValue(DROPDOWN_BIRTHDAY_XP, birthDay);
    }

    public void fillBirthMonth(String birthMonth){
        WebDriverUtils.setDropdownOptionByValue(DROPDOWN_BIRTHMONTH_XP, birthMonth);
    }

    public void fillBirthYear(String birthYear){
        WebDriverUtils.setDropdownOptionByValue(DROPDOWN_BIRTHYEAR_XP, birthYear);
    }

    public static List<String> getBirthYearList() {
        return WebDriverUtils.getDropdownOptionsText(DROPDOWN_BIRTHYEAR_XP);
    }

    public static void fillEmail(String email){
        WebDriverUtils.clearAndInputTextToField(getXpathByName(FIELD_EMAIL_NAME), email);
    }
    public static void fillEmailVerification(String confirmEmail){
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.sevenRegal)){
            RegistrationPageAllSteps.fillEmailVerification(confirmEmail);
        }else {
            RegistrationPageStepOne.fillEmailVerification(confirmEmail);
        }
    }

    public void fillEmailVerificationAndRefocus(String confirmEmail) {
        String xpath = RegistrationPageStepOne.getEmailVerificationXpath();
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.sevenRegal)){
            xpath = RegistrationPageAllSteps.getEmailVerificationXpath();
        }
        ValidationUtils.inputFieldAndRefocus(xpath, confirmEmail);
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

    public void fillPassword(String password){
        WebDriverUtils.clearAndInputTextToField(getXpathByName(FIELD_PASSWORD_NAME), password);
    }

    public static void fillPasswordVerification(String confirmPassword){
        WebDriverUtils.clearAndInputTextToField(getXpathByName(FIELD_PASSWORD_VERIFICATION_NAME), confirmPassword);
    }

    public static void fillPasswordVerificationAndRefocus(String confirmPassword){
        ValidationUtils.inputFieldAndRefocus(getXpathByName(FIELD_PASSWORD_VERIFICATION_NAME), confirmPassword);
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

    public void clickSubmit(){
        WebDriverUtils.click(BUTTON_SUBMIT_XP);
    }

    public ReadTermsAndConditionsPopup navigateToTermsAndConditions(){
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.sevenRegal)){
            // do nothing
        }else {
            registrationPageStepThree(DataContainer.getUserData().getRegisteredUserData());
        }
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
        return WebDriverUtils.getDropdownSelectedOptionText(getXpathByName(DROPDOWN_COUNTRY_NAME));
    }

    public String getSelectedCurrency(){
        return WebDriverUtils.getDropdownSelectedOptionText(getXpathByName(DROPDOWN_CURRENCY_NAME));
    }

    public boolean getReceivePromotionsCheckboxState(UserData userData){
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.core)){
            registrationPageStepThree(userData);
        }
        return WebDriverUtils.getCheckBoxState(CHECKBOX_RECEIVE_BONUSES_XP);
    }

    public String getReceivePromotionsCheckboxText(UserData userData){
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.core)){
            registrationPageStepThree(userData);
        }
        return WebDriverUtils.getElementText(LABEL_RECEIVE_PROMOTIONS_XP);
    }

    public Collection<String> getCountriesCodesList(UserData userData) {
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.core)){
            registrationPageStepTwo(userData);
        }
        return WebDriverUtils.getDropdownOptionsValue(getXpathByName(DROPDOWN_COUNTRY_NAME));
    }

    public Collection<String> getCurrencyList(UserData userData) {
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.core)){
            registrationPageStepThree(userData);
        }
        return WebDriverUtils.getDropdownOptionsText(getXpathByName(DROPDOWN_CURRENCY_NAME));
    }

    public String getUsernameSuggestion(String username, UserData userData) {
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.core)){
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

    public boolean isSuggestionVisible(){
        return WebDriverUtils.isVisible(LABEL_USERNAME_SUGGESTION_TOOLTIP_XP, 0);
    }
    public static String getFilledUsername(){
        return WebDriverUtils.getInputFieldText(getXpathByName(FIELD_USERNAME_NAME));
    }

    public static String getPasswordStrength(){
        return WebDriverUtils.getAttribute(PASSWORD_METER_XP, "class");
    }

    public static String getPasswordStrengthTooltip(){
        return WebDriverUtils.getElementText(PASSWORD_STRENGTH_TOOLTIP);
    }

    public void assertPasswordStrengthStatus(String value, PasswordStrength expectedStrength){
        fillPassword(value);
        AbstractTest.assertEquals(expectedStrength.toString(), getPasswordStrength(), "Password strength status for '"+value+"'");
    }

    public static void assertPasswordStrengthTooltip(String value, PasswordStrength expectedStrength){
        WebDriverUtils.waitFor(500);
        AbstractTest.assertEquals(expectedStrength.getTooltip(), getPasswordStrengthTooltip(), "Password strength tooltip for '"+value+"'");
    }

    public void assertPasswordStrength(String value, PasswordStrength expectedStrength){
        assertPasswordStrengthStatus(value, expectedStrength);
        assertPasswordStrengthTooltip(value, expectedStrength);
    }

    /* Fields validation */

    public void validateGender(ValidationRule rule) {
        ValidationUtils.validateDropdown(getXpathByName(DROPDOWN_GENDER_NAME), rule, DROPDOWN_GENDER_VALIDATION_NAME);
    }

    public void validateFirstname(ValidationRule rule) {
        ValidationUtils.validateField(getXpathByName(FIELD_FIRSTNAME_NAME), rule, FIELD_FIRSTNAME_NAME);
    }

    public void validateLastname(ValidationRule rule) {
        ValidationUtils.validateField(getXpathByName(FIELD_LASTNAME_NAME), rule, FIELD_LASTNAME_NAME);
    }

    public void validateDateOfBirth(ValidationRule rule) {
        ValidationUtils.validateDropdown(DROPDOWN_BIRTHYEAR_XP, rule, DROPDOWN_BIRTHDATE_VALIDATION_NAME);
    }

    public void validateEmail(ValidationRule rule) {
        ValidationUtils.validateField(getXpathByName(FIELD_EMAIL_NAME), rule, FIELD_EMAIL_NAME);
    }

    public void validateCountry(ValidationRule rule, UserData userData) {
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.core)){
            registrationPageStepTwo(userData);
        }
        ValidationUtils.validateDropdown(getXpathByName(DROPDOWN_COUNTRY_NAME), rule, DROPDOWN_COUNTRY_VALIDATION_NAME);
    }

    public void validateCity(ValidationRule rule, UserData userData) {
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.core)){
            registrationPageStepTwo(userData);
        }
        ValidationUtils.validateField(getXpathByName(FIELD_CITY_NAME), rule, FIELD_CITY_NAME);
    }

    public void validateAddress(ValidationRule rule, UserData userData) {
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.core)){
            registrationPageStepTwo(userData);
        }
        ValidationUtils.validateField(getXpathByName(FIELD_ADDRESS_NAME), rule, FIELD_ADDRESS_NAME);
    }

    public void validatePostcode(ValidationRule rule, UserData userData) {
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.core)){
            registrationPageStepTwo(userData);
        }
        ValidationUtils.validateField(getXpathByName(FIELD_POSTCODE_NAME), rule, FIELD_POSTCODE_NAME);
    }

    public void validatePhone(ValidationRule rule, UserData userData){
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.core)){
            registrationPageStepTwo(userData).validatePhoneField(rule);
        }else {
            registrationPageAllSteps().validatePhoneField(rule);
        }
    }

    public void validatePhoneAreaCode(ValidationRule rule, UserData userData){
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.core)){
            registrationPageStepTwo(userData).validatePhoneAreaCodeField(rule);
        }else {
            registrationPageAllSteps().validatePhoneAreaCodeField(rule);
        }
    }

    public void validateUsername(ValidationRule rule, UserData userData) {
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.core)){
            registrationPageStepThree(userData);
        }
        ValidationUtils.validateField(getXpathByName(FIELD_USERNAME_NAME), rule, FIELD_USERNAME_NAME);
    }

    public void validatePassword(ValidationRule rule, UserData userData) {
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.core)){
            registrationPageStepThree(userData);
        }
        ValidationUtils.validateField(getXpathByName(FIELD_PASSWORD_NAME), rule, FIELD_PASSWORD_NAME);
    }

    public void validateState(ValidationRule rule, UserData userData) {
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.core)){
            registrationPageStepTwo(userData);
        }
        ValidationUtils.validateField(getXpathByName(FIELD_STATE_NAME), rule, FIELD_STATE_NAME);
    }

    public void validateAddress2(ValidationRule rule, UserData userData) {
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.core)){
            registrationPageStepTwo(userData);
        }
        ValidationUtils.validateField(getXpathByName(FIELD_ADDRESS2_NAME), rule, FIELD_ADDRESS2_NAME);
    }

    public void validateHouse(ValidationRule rule, UserData userData) {
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.core)){
            registrationPageStepTwo(userData);
        }
        ValidationUtils.validateField(getXpathByName(FIELD_HOUSE_NAME), rule, FIELD_HOUSE_NAME);
    }

    public void validateQuestion(ValidationRule rule, UserData userData) {
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.core)){
            registrationPageStepThree(userData).validateQuestionField(rule);
        }
    }

    public void validateAnswer(ValidationRule rule, UserData userData) {
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.core)){
            registrationPageStepThree(userData).validateAnswerField(rule);
        }
    }

    public void validateCurrency(ValidationRule rule, UserData userData) {
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.core)){
            registrationPageStepThree(userData);
        }
        ValidationUtils.validateDropdown(getXpathByName(DROPDOWN_CURRENCY_NAME), rule, DROPDOWN_CURRENCY_NAME);
    }

    public void validateBonusCode(ValidationRule rule, UserData userData) {
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.core)){
            registrationPageStepThree(userData);
        }
        ValidationUtils.validateField(getXpathByName(FIELD_BONUSCODE_NAME), rule, FIELD_BONUSCODE_NAME);
    }

    protected static String getXpathByName(String id){
        return FIELD_BASE_XP.replace(PLACEHOLDER, id);
    }

    public static String getEmailVerificationXpath(){
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.sevenRegal)){
            return getXpathByName(RegistrationPageAllSteps.FIELD_EMAIL_VERIFICATION_NAME);
        }else {
            return getXpathByName(RegistrationPageStepOne.FIELD_EMAIL_VERIFICATION_NAME);
        }
    }

    public static String getEmailVerificationName(){
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.sevenRegal)){
            return RegistrationPageAllSteps.FIELD_EMAIL_VERIFICATION_NAME;
        }else {
            return RegistrationPageStepOne.FIELD_EMAIL_VERIFICATION_NAME;
        }
    }

    public void clickUsernameField() {
        WebDriverUtils.click(getXpathByName(FIELD_USERNAME_NAME));
    }

    public void copyAndPastePassword() {
        WebDriverUtils.copy(getXpathByName(FIELD_PASSWORD_NAME));
        WebDriverUtils.paste(FIELD_PASSWORD_VERIFICATION_XP);
    }

    public String getPasswordVerification() {
        return WebDriverUtils.getElementText(FIELD_PASSWORD_VERIFICATION_XP);
    }

    public String getEmailVerification() {
        return null;
    }

    public List getDays() {
        return WebDriverUtils.getDropdownOptionsValue(DROPDOWN_BIRTHDAY_XP);
    }
}
