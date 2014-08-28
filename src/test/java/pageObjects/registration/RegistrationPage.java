package pageObjects.registration;

import enums.Page;
import pageObjects.HomePage;
import pageObjects.base.AbstractPage;
import pageObjects.base.AbstractPageObject;
import pageObjects.popups.ReadTermsAndConditionsPopup;
import springConstructors.UserData;
import springConstructors.ValidationRule;
import utils.NavigationUtils;
import utils.validation.ValidationUtils;
import utils.WebDriverUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RegistrationPage extends AbstractPage{

    private static final String ROOT_XP = 											"//*[contains(@class, 'fn-register-content')]";
    private final static String FIELD_BASE_XP =				 				        ROOT_XP + "//*[@id='"+PLACEHOLDER+"']";
    private static final String TOOLTIP_ERROR_XP = 									ROOT_XP + "//div[contains(@class,'error-tooltip')]";

    private static final String BUTTON_SUBMIT_XP = 									ROOT_XP + "//*[contains(@class,'fn-submit')]";
    private static final String CHECKBOX_TERMS_AND_CONDITION_XP = 					"//*[@id='terms-checkbox']";
    private static final String CHECKBOX_TERMS_AND_CONDITION_VALIDATION_ERROR_XP = 	CHECKBOX_TERMS_AND_CONDITION_XP+TOOLTIP_ERROR_XP;
    private static final String LINK_TERMS_AND_CONDITION_XP = 						"//*[contains(@class, 'terms')]/a";
    public  static final String LABEL_MESSAGE_ERROR_XP = 							ROOT_XP + "//*[contains(@class,'portlet-msg-error')]";
    private static final String LABEL_USERNAME_SUGGESTION_XP = 						"//*[@id='usernameSuggestionsTmpl']";
    private final static String LABEL_BONUS_CODE_ERROR_XPATH = 						"//*[contains(text(), 'Bonus code is not found or not available')]";
    public  final static String LABEL_ERROR_TIMEOUT_XP =                            ROOT_XP + "//*[contains(@class, 'portlet-msg-error') and contains(text(), 'Timeout occurred')]";
    private final static String BONUS_CODE = 										"TEST";

    private final static String DROPDOWN_GENDER_ID=									"gender";
    private final static String LABEL_GENDER_XP=									"//label[@for='gender']";
    private final static String FIELD_FIRSTNAME_ID =				 				"firstname";
	private final static String LABEL_FIRSTNAME_XP =								"//label[@for='firstname']";
    private final static String FIELD_LASTNAME_ID = 								"lastname";
	private final static String LABEL_LASTNAME_XP=									"//label[@for='lastname']";
    private final static String FIELD_EMAIL_ID = 									"email";
    private final static String LABEL_EMAIL_XP=										"//label[@for='email']";
    private final static String FIELD_STATE_ID = 									"state";
    private final static String LABEL_STATE_XP=										"//label[@for='state']";
    private final static String DROPDOWN_COUNTRY_ID = 								"countrycode";
    private final static String DROPDOWN_COUNTRY_XP = 								ROOT_XP + "//*[@name='"+DROPDOWN_COUNTRY_ID+"']";
    private final static String LABEL_COUNTRY_XP=									"//span[following-sibling::div/*[@name='countrycode']]";
    private final static String FIELD_CITY_ID = 									"city";
    private final static String LABEL_CITY_XP=										"//label[@for='city']";
    private final static String FIELD_ADDRESS1_ID = 								"address";
    private final static String LABEL_ADDRESS1_XP =									"//label[@for='address']";
    private final static String FIELD_ADDRESS2_ID = 								"address2";
    private final static String LABEL_ADDRESS2_XP =									"//label[@for='address2']";
    private final static String FIELD_HOUSE_ID = 								    "house";
    private final static String LABEL_HOUSE_XP =									"//label[@for='house']";
    private final static String FIELD_POSTCODE_ID = 								"zip";
    private final static String LABEL_POSTCODE_XP=									"//label[@for='zip']";
    private final static String FIELD_PHONE_COUNTRY_CODE_ID  = 						"phoneAreaCode";
    private final static String FIELD_PHONE_XP = 									"//*[@id='phoneNumber']";
    private final static String LABEL_PHONE_XP=										"//span[following-sibling::div/span/*[@id='phoneNumber']]";
    private final static String FIELD_USERNAME_ID = 								"userName";
    private final static String LABEL_USERNAME_XP=									"//label[@for='userName']";
    private final static String FIELD_PASSWORD_ID = 								"password";
    private final static String LABEL_PASSWORD_XP=									ROOT_XP+"//label[@for='password']";
    private final static String FIELD_ANSWER_ID = 								    "answer";
    private final static String LABEL_ANSWER_XP=									"//label[@for='answer']";
    private final static String DROPDOWN_CURRENCY_ID = 								"currencyCode";
    private final static String DROPDOWN_CURRENCY_XP = 								ROOT_XP + "//*[@name='"+DROPDOWN_CURRENCY_ID+"']";
    private final static String LABEL_CURRENCY_XP=									"//span[following-sibling::div/*[@name='currencyCode']]";
    private final static String FIELD_BONUSCODE_ID = 								"coupon";
    private final static String LABEL_BONUSCODE_XP=									"//label[@for='coupon']";
    private final static String DROPDOWN_BIRTHDAY_ID=								"birthDay";
    private final static String DROPDOWN_BIRTHMONTH_XP=								"//select[@id='birthMonth']";
    private final static String DROPDOWN_BIRTHYEAR_XP=								"//select[@id='birthYear']";
    private final static String LABEL_BIRTHDAY_XP=									"//span[following-sibling::div/div[@class='date-section']]";
    private final static String DROPDOWN_NATIONALITY_XP=							"//select[@id='nationality']";
    private final static String FIELD_EMAIL_VERIFICATION_ID = 						"confirmEmail";
    private final static String LABEL_EMAIL_VERIFICATION_XP=						"//label[@for='confirmEmail']";
    private final static String FIELD_PASSWORD_VERIFICATION_ID = 					"passwordVerify";
    private final static String LABEL_PASSWORD_VERIFICATION_XP=						"//label[@for='passwordVerify']";
	private final static String CHECKBOX_RECEIVE_BONUSES_XP=						"//*[@id='nobonus']";
	private final static String LABEL_RECEIVE_BONUSES_XP=							"//label[@for='nobonus']";
	private final static String LINK_ADULT_CONTENT_XP=								"//*[@id='webContent18']//a";
	private final static String BUTTON_FILL_FIELDS_XP= 								"//*[@id='fillFields']";

    public RegistrationPage(){
		super(new String[]{ROOT_XP, BUTTON_SUBMIT_XP});
	}

	public boolean getFillFieldsButtonVisibleState(){
		return WebDriverUtils.isVisible(BUTTON_FILL_FIELDS_XP);
	}

	private void setGender(){
		setGender("Male");
	}

	private void setGender(String title){
		WebDriverUtils.setDropdownOptionByText(getXpathByID(DROPDOWN_GENDER_ID), title);
	}

	public List<String> fillAutoByPostCode(String postCode) {
		fillPostCode(postCode);
		WebDriverUtils.click(BUTTON_FILL_FIELDS_XP);
		List<String> fullAddress = new ArrayList<String>();
		fullAddress.add(getCity());
		fullAddress.add(getAddress1());

		return fullAddress;
	}

	private String getCity(){
		return WebDriverUtils.getInputFieldText(getXpathByID(FIELD_CITY_ID));
	}

	private String getAddress1(){
		return WebDriverUtils.getInputFieldText(getXpathByID(FIELD_ADDRESS1_ID));
	}

	public boolean autofilledFieldsAreEditable() {
		boolean city = WebDriverUtils.isEditable(getXpathByID(FIELD_CITY_ID));
		boolean address1 = WebDriverUtils.isEditable(getXpathByID(FIELD_ADDRESS1_ID));
		return city && address1;
	}

	public String getPhoneAreaCode(){
		return WebDriverUtils.getInputFieldText(getXpathByID(FIELD_PHONE_COUNTRY_CODE_ID));
	}

	private void fillUsername(String username){
		WebDriverUtils.clearAndInputTextToField(getXpathByID(FIELD_USERNAME_ID), username);
	}

	private void fillPassword(String password){
		WebDriverUtils.clearAndInputTextToField(getXpathByID(FIELD_PASSWORD_ID), password);
	}

	public void fillConfirmPassword(String confirmPassword){
		WebDriverUtils.clearAndInputTextToField(getXpathByID(FIELD_PASSWORD_VERIFICATION_ID), confirmPassword);
	}

	private void fillEmail(String email){
		WebDriverUtils.clearAndInputTextToField(getXpathByID(FIELD_EMAIL_ID), email);
	}

	public void fillConfirmEmail(String confirmEmail){
		WebDriverUtils.clearAndInputTextToField(getXpathByID(FIELD_EMAIL_VERIFICATION_ID), confirmEmail);
	}

	private void fillFirstName(String firstName){
		WebDriverUtils.clearAndInputTextToField(getXpathByID(FIELD_FIRSTNAME_ID), firstName);
	}

	private void fillLastName(String lastName){
		WebDriverUtils.clearAndInputTextToField(getXpathByID(FIELD_LASTNAME_ID), lastName);
	}

	private void fillBirthDay(String birthDay){
		WebDriverUtils.setDropdownOptionByText(getXpathByID(DROPDOWN_BIRTHDAY_ID), birthDay);
	}

	private void fillBirthMonth(String birthMonth){
		WebDriverUtils.setDropdownOptionByText(DROPDOWN_BIRTHMONTH_XP, birthMonth);
	}

	private void fillBirthYear(String birthYear){
		WebDriverUtils.setDropdownOptionByText(DROPDOWN_BIRTHYEAR_XP, birthYear);
	}

	public void fillCountry(String countryCode){
		WebDriverUtils.setDropdownOptionByValue(DROPDOWN_COUNTRY_XP, countryCode);
	}

	private void fillCity(String city){
		WebDriverUtils.clearAndInputTextToField(getXpathByID(FIELD_CITY_ID), city);
	}

	private void fillAddress(String address){
		WebDriverUtils.clearAndInputTextToField(getXpathByID(FIELD_ADDRESS1_ID), address);
	}

	private void fillPostCode(String postCode){
		WebDriverUtils.clearAndInputTextToField(getXpathByID(FIELD_POSTCODE_ID), postCode);
	}

	private void fillPhoneAreaCode(String phoneAreaCode){
		WebDriverUtils.clearAndInputTextToField(getXpathByID(FIELD_PHONE_COUNTRY_CODE_ID), phoneAreaCode);
	}

	private void fillPhone(String phone){
		WebDriverUtils.clearAndInputTextToField(FIELD_PHONE_XP, phone);
	}

	private void setCurrency(String currencyCode){
		WebDriverUtils.setDropdownOptionByText(DROPDOWN_CURRENCY_XP, currencyCode);
	}

	private void fillBonusCode(String coupon){
		WebDriverUtils.clearAndInputTextToField(getXpathByID(FIELD_BONUSCODE_ID), coupon);
	}

	public boolean getCheckboxStateTermsAndConditions(){
		return WebDriverUtils.getCheckBoxState(CHECKBOX_TERMS_AND_CONDITION_XP);
	}

	private void setCheckboxTermsAndConditions(boolean desiredState){
		WebDriverUtils.setCheckBoxState(CHECKBOX_TERMS_AND_CONDITION_XP, desiredState);
	}

	public boolean getCheckboxStateReceiveBonuses(){
		return WebDriverUtils.getCheckBoxState(CHECKBOX_RECEIVE_BONUSES_XP);
	}

	private void setCheckboxReceiveBonuses(boolean desiredState){
		WebDriverUtils.setCheckBoxState(CHECKBOX_RECEIVE_BONUSES_XP, desiredState);
	}

	public void submit(){
		WebDriverUtils.click(BUTTON_SUBMIT_XP);
	}

	public boolean checkboxTermsAndConditionsValidationErrorVisible(){
		return WebDriverUtils.isVisible(CHECKBOX_TERMS_AND_CONDITION_VALIDATION_ERROR_XP);
	}

	public AdultContentPage clickAdultContent(){
		WebDriverUtils.click(LINK_ADULT_CONTENT_XP);
		return new AdultContentPage();
	}

	public HomePage registerUser(UserData userData){
		return (HomePage)this.registerUser(userData, false);
	}

	public AbstractPageObject registerUser(UserData userData, Page expectedPage){
		return this.registerUser(userData, true, false, false, "", expectedPage);
	}

	public AbstractPageObject registerUser(UserData userData, boolean isReceiveBonusesChecked){
		return this.registerUser(userData, true, isReceiveBonusesChecked, false);
	}

	public AbstractPageObject registerUser(UserData userData, String invalidBonusCode){
		return this.registerUser(userData, true, false, true, invalidBonusCode, Page.registrationPage);
	}

	public AbstractPageObject registerUser(UserData userData, boolean isReceiveBonusesChecked, boolean isBonusCodeEntered){
		return this.registerUser(userData, true, isReceiveBonusesChecked, isBonusCodeEntered, null, Page.homePage);
	}

	public AbstractPageObject registerUser(UserData userData, boolean isTermsAndConditionsChecked, boolean isReceiveBonusesChecked, boolean isBonusCodeEntered){
		Page page;
		if(!isTermsAndConditionsChecked){
			page=Page.registrationPage;
		}else{
			page=Page.homePage;
		}
		return this.registerUser(userData, isTermsAndConditionsChecked, isReceiveBonusesChecked, isBonusCodeEntered, null, page);
	}

	public AbstractPageObject registerUser(UserData userData, boolean isTermsAndConditionsChecked, boolean isReceiveBonusesChecked, boolean isBonusCodeEntered, String bonusCode, Page expectedPage){
		fillRegistrationForm(userData, isTermsAndConditionsChecked, isReceiveBonusesChecked, isBonusCodeEntered, bonusCode);
		submit();
		return NavigationUtils.closeAllPopups(expectedPage);
	}

	public void fillRegistrationForm(UserData userData){
		this.fillRegistrationForm(userData, true, false, false, "");
	}

	public void fillRegistrationForm(UserData userData, boolean tac, boolean isReceiveBonusesChecked, boolean isBonusCodeEntered, String bonusCode){
		fillUserData(userData);
		fillOther(tac, isReceiveBonusesChecked, isBonusCodeEntered, bonusCode);
	}

	private void fillUserData(UserData userData){
		fillFirstName(userData.getFirstName());
		fillLastName(userData.getLastName());
		//
		fillBirthDay(userData.getBirthDay());
		fillBirthMonth(userData.getBirthMonth());
		fillBirthYear(userData.getBirthYear());
		fillEmail(userData.getEmail());
		fillConfirmEmail(userData.getEmail());
		//
		fillCountry(userData.getCountry());
		fillCity(userData.getCity());
		fillAddress(userData.getAddress());
		//
		fillPostCode(userData.getPostCode());
		fillPhoneAreaCode(userData.getPhoneAreaCode());
		fillPhone(userData.getPhone());
		//
		fillUsername(userData.getUsername());
		fillPassword(userData.getPassword());
		fillConfirmPassword(userData.getPassword());
		//
		setCurrency(userData.getCurrency());
	}

	private void fillOther(boolean tac, boolean isReceiveBonusesChecked, boolean isBonusCodeEntered, String bonusCode){
		setGender();
//		setCheckboxTermsAndConditions(tac);
		setCheckboxReceiveBonuses(isReceiveBonusesChecked);
		if(isBonusCodeEntered && bonusCode == null){
			fillBonusCode(BONUS_CODE);
		}else if(isBonusCodeEntered && bonusCode != null){
			fillBonusCode(bonusCode);
		}
	}

	public String getSelectedCurrency(){
		return WebDriverUtils.getDropdownSelectedOption(getXpathByID(DROPDOWN_CURRENCY_ID)).getText();
	}

	public String getSelectedCountryName(){
		return WebDriverUtils.getDropdownSelectedOption(getXpathByID(DROPDOWN_COUNTRY_ID)).getText();
	}

	public boolean isBonusCodeErrorPresent(){
		return WebDriverUtils.isVisible(LABEL_BONUS_CODE_ERROR_XPATH);
	}

	public boolean isAdultContentPresent(){
		boolean adultContentPresent=false;
		AdultContentPage adultContentPage=clickAdultContent();
		if(adultContentPage != null){
			adultContentPresent=true;
		}
		return adultContentPresent;
	}

	public ReadTermsAndConditionsPopup navigateToTermsAndConditions(){
		WebDriverUtils.click(LINK_TERMS_AND_CONDITION_XP);
		return new ReadTermsAndConditionsPopup();
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
		boolean address1=labelMarkedAsRequired(LABEL_ADDRESS1_XP);
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

    public String getErrorMessageText() {
        return getVisibleMessageText(LABEL_MESSAGE_ERROR_XP);
    }

	public String getUsernameSuggestionText() {
		return getVisibleMessageText(LABEL_USERNAME_SUGGESTION_XP);
	}

    public String getTooltipMessageText() {
        return getVisibleMessageText(TOOLTIP_ERROR_XP);
    }

    private String getVisibleMessageText(String xpath) {
        if (WebDriverUtils.isVisible(xpath, 1)) {
            return WebDriverUtils.getElementText(xpath);
        } else {
			WebDriverUtils.runtimeExceptionWithLogs("Expected message is not visible: " + xpath);
        }
		return null;
    }

    public Collection<String> getCountriesCodesList() {
        return WebDriverUtils.getDropdownOptionsValue(getXpathByID(DROPDOWN_COUNTRY_ID));
    }

    public Collection<String> getNationalitiesCodesList() {
        return WebDriverUtils.getDropdownOptionsValue(DROPDOWN_NATIONALITY_XP);
    }

    public Collection<String> getCurrencyList() {
        return WebDriverUtils.getDropdownOptionsText(getXpathByID(DROPDOWN_CURRENCY_ID));
    }


    private String getXpathByID(String id){
        return FIELD_BASE_XP.replace(PLACEHOLDER, id);
    }

    /* Fields validation */

    public void validateGender(ValidationRule rule) {
        ValidationUtils.validateDropdown(getXpathByID(DROPDOWN_GENDER_ID), rule, DROPDOWN_GENDER_ID);
    }

    public void validateFirstname(ValidationRule rule) {
        ValidationUtils.validateField(getXpathByID(FIELD_FIRSTNAME_ID), rule, FIELD_FIRSTNAME_ID);
    }

    public void validateLastname(ValidationRule rule) {
        ValidationUtils.validateField(getXpathByID(FIELD_LASTNAME_ID), rule, FIELD_LASTNAME_ID);
    }

    public void validateEmail(ValidationRule rule) {
        ValidationUtils.validateField(getXpathByID(FIELD_EMAIL_ID), rule, FIELD_EMAIL_ID);
    }

    public void validateState(ValidationRule rule) {
        ValidationUtils.validateField(getXpathByID(FIELD_STATE_ID), rule, FIELD_STATE_ID);
    }

    public void validateCountry(ValidationRule rule) {
        ValidationUtils.validateDropdown(DROPDOWN_COUNTRY_XP, rule, DROPDOWN_COUNTRY_ID);
    }

    public void validateCity(ValidationRule rule) {
        ValidationUtils.validateField(getXpathByID(FIELD_CITY_ID), rule, FIELD_CITY_ID);
    }

    public void validateAddress1(ValidationRule rule) {
        ValidationUtils.validateField(getXpathByID(FIELD_ADDRESS1_ID), rule, FIELD_ADDRESS1_ID);
    }

    public void validateAddress2(ValidationRule rule) {
        ValidationUtils.validateField(getXpathByID(FIELD_ADDRESS2_ID), rule, FIELD_ADDRESS2_ID);
    }

    public void validateHouse(ValidationRule rule) {
        ValidationUtils.validateField(getXpathByID(FIELD_HOUSE_ID), rule, FIELD_HOUSE_ID);
    }

    public void validatePostcode(ValidationRule rule) {
        ValidationUtils.validateField(getXpathByID(FIELD_POSTCODE_ID), rule, FIELD_POSTCODE_ID);
    }

    public void validatePhoneCountryCode(ValidationRule rule) {
        ValidationUtils.validateField(getXpathByID(FIELD_PHONE_COUNTRY_CODE_ID), rule, FIELD_PHONE_COUNTRY_CODE_ID);
    }

    public void validatePhone(ValidationRule rule) {
        ValidationUtils.validateField(FIELD_PHONE_XP, rule, FIELD_PHONE_COUNTRY_CODE_ID);
    }

    public void validateUsername(ValidationRule rule) {
        ValidationUtils.validateField(getXpathByID(FIELD_USERNAME_ID), rule, FIELD_USERNAME_ID);
    }

    public void validatePassword(ValidationRule rule) {
        ValidationUtils.validateField(getXpathByID(FIELD_PASSWORD_ID), rule, FIELD_PASSWORD_ID);
    }

    public void validateAnswer(ValidationRule rule) {
        ValidationUtils.validateField(getXpathByID(FIELD_ANSWER_ID), rule, FIELD_ANSWER_ID);
    }

    public void validateCurrency(ValidationRule rule) {
        ValidationUtils.validateDropdown(DROPDOWN_CURRENCY_XP, rule, DROPDOWN_CURRENCY_ID);
    }

    public void validateBonusCode(ValidationRule rule) {
        ValidationUtils.validateField(getXpathByID(FIELD_BONUSCODE_ID), rule, FIELD_BONUSCODE_ID);
    }

}
