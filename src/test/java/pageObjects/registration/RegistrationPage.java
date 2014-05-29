package pageObjects.registration;

import enums.Page;
import pageObjects.HomePage;
import pageObjects.base.AbstractPage;
import pageObjects.base.AbstractPageObject;
import pageObjects.popups.ReadTermsAndConditionsPopup;
import springConstructors.UserData;
import springConstructors.validation.ValidationRule;
import utils.NavigationUtils;
import utils.ValidationUtils;
import utils.WebDriverUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: ivan
 * Date: 7/31/13
 */

public class RegistrationPage extends AbstractPage{
	
    private static final String ROOT_XP = 											"//*[@id='registration']";
    private static final String BUTTON_SUBMIT_XP = 									"//button/span/strong[contains(text(),'Register')]";
    private static final String CHECKBOX_TERMS_AND_CONDITION_XP = 					"//*[@id='termsAndConditions']";
    private static final String CHECKBOX_TERMS_AND_CONDITION_VALIDATION_ERROR_XP = 	"//*[@id='acceptTerms']//span[contains(@class,'error')]";
    private static final String LINK_TERMS_AND_CONDITION_XP = 						"//*[@id='termsAndConditionsLabel']/a";
    private static final String LABEL_ALL_VALIDATION_EMPTY_XP= 						"//span[contains(@class,'empty')]";
    public static final String LABEL_MESSAGE_ERROR_XP = 							"//*[@id='registration']//*[contains(@class,'portlet-msg-error')]";
	private static final String LABEL_USERNAME_SUGGESTION_XP = 						"//*[@id='usernameSuggestionsTmpl']";
    private static final String LABEL_TOOLTIP_XP = 									"//*[@id='tooltip']//span";
    private final static String LABEL_BONUS_CODE_ERROR_XPATH = 						"//*[contains(text(), 'Bonus code is not found or not available')]";
    public final static String LABEL_ERROR_TIMEOUT_XP =                            "//*[contains(@class, 'portlet-msg-error') and contains(text(), 'Timeout occurred')]";
    private final static String BONUS_CODE = 										"TEST";
	private final static String DROPDOWN_TITLE_XP=									"//*[@id='title']";
	private final static String LABEL_TITLE_XP=										"//label[@for='title']";
	private final static String RADIOBUTTON_GENDER_MALE_XP=							"//*[@id='male']";
	private final static String RADIOBUTTON_GENDER_FEMALE_XP=						"//*[@id='female']";
	private final static String LABEL_GENDER_XP=									"//*[@id='registration-personal']//label[not(@for)]";
    private final static String FIELD_FIRSTNAME_XP =				 				"//*[@id='firstName']";
	private final static String LABEL_FIRSTNAME_XP =								"//label[@for='firstName']";
    private final static String FIELD_LASTNAME_XP = 								"//*[@id='lastName']";
	private final static String LABEL_LASTNAME_XP=									"//label[@for='lastName']";
	private final static String DROPDOWN_BIRTHDAY_XP=								"//select[@id='birthDay']";
	private final static String DROPDOWN_BIRTHMONTH_XP=								"//select[@id='birthMonth']";
	private final static String DROPDOWN_BIRTHYEAR_XP=								"//select[@id='birthYear']";
	private final static String LABEL_BIRTHDAY_XP=									"//label[@for='birthDay']";
    private final static String FIELD_EMAIL_XP = 									"//*[@id='email']";
	private final static String LABEL_EMAIL_XP=										"//label[@for='email']";
    private final static String FIELD_EMAIL_VERIFICATION_XP = 						"//*[@id='emailVerification']";
	private final static String LABEL_EMAIL_VERIFICATION_XP=						"//label[@for='emailVerification']";
    private final static String DROPDOWN_COUNTRY_XP = 								"//*[@id='country']";
	private final static String LABEL_COUNTRY_XP=									"//label[@for='country']";
    private final static String FIELD_CITY_XP = 									"//*[@id='city']";
	private final static String LABEL_CITY_XP=										"//label[@for='city']";
    private final static String FIELD_ADDRESS1_XP = 								"//*[@id='address']";
	private final static String LABEL_ADDRESS1_XP =									"//label[@for='address']";
    private final static String FIELD_ADDRESS2_XP = 								"//*[@id='address2']";
	private final static String LABEL_ADDRESS2_XP =									"//label[@for='address2']";
    private final static String FIELD_HOUSE_XP = 									"//*[@id='house']";
	private final static String LABEL_HOUSE_XP=										"//label[@for='house']";
    private final static String FIELD_POSTCODE_XP = 								"//*[@id='postCode']";
	private final static String LABEL_POSTCODE_XP=									"//label[@for='postCode']";
    private final static String FIELD_PHONE_COUNTRY_CODE_XP  = 						"//*[@id='phoneAreaCode']";
    private final static String FIELD_PHONE_XP = 									"//*[@id='phone']";
	private final static String LABEL_PHONE_XP=										"//label[@for='phone']";
    private final static String FIELD_MOBILE_COUNTRY_CODE_XP= 							"//*[@id='mobileAreaCode']";
    private final static String FIELD_MOBILE_XP = 									"//*[@id='mobile']";
	private final static String LABEL_MOBILE_XP =									"//label[@for='mobile']";
    private final static String FIELD_USERNAME_XP = 								"//*[@id='userName']";
	private final static String LABEL_USERNAME_XP=									"//label[@for='userName']";
    private final static String FIELD_PASSWORD_XP = 								"//*[@id='userPassword']";
	private final static String LABEL_PASSWORD_XP=									"//label[@for='userPassword']";
    private final static String FIELD_PASSWORD_VERIFICATION_XP = 					"//*[@id='passwordVerify']";
	private final static String LABEL_PASSWORD_VERIFICATION_XP=						"//label[@for='passwordVerify']";
	private final static String DROPDOWN_VERIFICATION_QUESTION_XP=					"//*[@id='verificationQuestion']";
	private final static String LABEL_DROPDOWN_VERIFICATION_QUESTION_XP=			"//label[@for='verificationQuestion']";
    private final static String FIELD_ANSWER_XP = 									"//*[@id='verificationAnswer']";
	private final static String LABEL_ANSWER_XP=									"//label[@for='verificationAnswer']";
    private final static String DROPDOWN_CURRENCY_XP = 								"//*[@id='currency']";
	private final static String LABEL_CURRENCY_XP=									"//label[@for='currency']";
    private final static String FIELD_BONUSCODE_XP = 								"//*[@id='coupon']";
	private final static String LABEL_BONUSCODE_XP=									"//label[@for='coupon']";
	private final static String CHECKBOX_RECEIVE_BONUSES_XP=						"//*[contains(@id, 'romotionalNotification')]";
	private final static String LABEL_RECEIVE_BONUSES_XP=							"//label[contains(@for, 'romotionalNotification')]";
	private final static String LINK_ADULT_CONTENT_XP=								"//*[@id='webContent18']//a";
	private final static String BUTTON_FILL_FIELDS_XP= 									"//*[@id='fillFields']";

    public RegistrationPage(){
		super(new String[]{ROOT_XP, BUTTON_SUBMIT_XP});
	}

	public boolean getFillFieldsButtonVisibleState(){
		return WebDriverUtils.isVisible(BUTTON_FILL_FIELDS_XP);
	}

	private void setTitle(){
		setTitle("Mr");
	}

	private void setTitle(String title){
		WebDriverUtils.setDropdownOptionByText(DROPDOWN_TITLE_XP, title);
	}

	private void setGender(){
		setGender(true);
	}

	private void setGender(boolean gender){
		if(gender){
			WebDriverUtils.click(RADIOBUTTON_GENDER_MALE_XP);
		}else{
			WebDriverUtils.click(RADIOBUTTON_GENDER_FEMALE_XP);
		}
	}

	public List<String> fillAutoByPostCode(String postCode) {
		fillPostCode(postCode);
		WebDriverUtils.click(BUTTON_FILL_FIELDS_XP);
		List<String> fullAddress = new ArrayList<String>();
		fullAddress.add(getCity());
		fullAddress.add(getAddress1());
		fullAddress.add(getAddress2());
		fullAddress.add(getHouse());

		return fullAddress;
	}

	private String getCity(){
		return WebDriverUtils.getInputFieldText(FIELD_CITY_XP);
	}

	private String getAddress1(){
		return WebDriverUtils.getInputFieldText(FIELD_ADDRESS1_XP);
	}

	private String getAddress2(){
		return WebDriverUtils.getInputFieldText(FIELD_ADDRESS2_XP);
	}

	private String getHouse(){
		return WebDriverUtils.getInputFieldText(FIELD_HOUSE_XP);
	}

	public boolean autofilledFieldsAreEditable() {
		boolean city = WebDriverUtils.isEditable(FIELD_CITY_XP);
		boolean address1 = WebDriverUtils.isEditable(FIELD_ADDRESS1_XP);
		boolean address2 = WebDriverUtils.isEditable(FIELD_ADDRESS2_XP);
		boolean house = WebDriverUtils.isEditable(FIELD_HOUSE_XP);
		return city && address1 && address2 && house;
	}

	public String getPhoneAreaCode(){
		return WebDriverUtils.getInputFieldText(FIELD_PHONE_COUNTRY_CODE_XP);
	}

	public String getMobileAreaCode(){
		return WebDriverUtils.getInputFieldText(FIELD_MOBILE_COUNTRY_CODE_XP);
	}

	private void fillUsername(String username){
		WebDriverUtils.clearAndInputTextToField(FIELD_USERNAME_XP, username);
	}

	private void fillPassword(String password){
		WebDriverUtils.clearAndInputTextToField(FIELD_PASSWORD_XP, password);
	}

	public void fillConfirmPassword(String confirmPassword){
		WebDriverUtils.clearAndInputTextToField(FIELD_PASSWORD_VERIFICATION_XP, confirmPassword);
	}

	private void fillEmail(String email){
		WebDriverUtils.clearAndInputTextToField(FIELD_EMAIL_XP, email);
	}

	public void fillConfirmEmail(String confirmEmail){
		WebDriverUtils.clearAndInputTextToField(FIELD_EMAIL_VERIFICATION_XP, confirmEmail);
	}

	private void fillFirstName(String firstName){
		WebDriverUtils.clearAndInputTextToField(FIELD_FIRSTNAME_XP, firstName);
	}

	private void fillLastName(String lastName){
		WebDriverUtils.clearAndInputTextToField(FIELD_LASTNAME_XP, lastName);
	}

	private void fillBirthDay(String birthDay){
		WebDriverUtils.setDropdownOptionByText(DROPDOWN_BIRTHDAY_XP, birthDay);
	}

	private void fillBirthMonth(String birthMonth){
		WebDriverUtils.setDropdownOptionByText(DROPDOWN_BIRTHMONTH_XP, birthMonth);
	}

	private void fillBirthYear(String birthYear){
		WebDriverUtils.setDropdownOptionByText(DROPDOWN_BIRTHYEAR_XP, birthYear);
	}

	public void fillCountry(String country){
		WebDriverUtils.setDropdownOptionByText(DROPDOWN_COUNTRY_XP, country);
	}

	private void fillCity(String city){
		WebDriverUtils.clearAndInputTextToField(FIELD_CITY_XP, city);
	}

	private void fillAddress(String address){
		WebDriverUtils.clearAndInputTextToField(FIELD_ADDRESS1_XP, address);
	}

    private void fillAddress2(String address){
        WebDriverUtils.clearAndInputTextToField(FIELD_ADDRESS2_XP, address);
    }

	private void fillHouse(String house){
		WebDriverUtils.clearAndInputTextToField(FIELD_HOUSE_XP, house);
	}

	private void fillPostCode(String postCode){
		WebDriverUtils.clearAndInputTextToField(FIELD_POSTCODE_XP, postCode);
	}

	private void fillPhoneAreaCode(String phoneAreaCode){
		WebDriverUtils.clearAndInputTextToField(FIELD_PHONE_COUNTRY_CODE_XP, phoneAreaCode);
	}

	private void fillPhone(String phone){
		WebDriverUtils.clearAndInputTextToField(FIELD_PHONE_XP, phone);
	}

	private void fillMobileAreaCode(String mobileAreaCode){
		WebDriverUtils.clearAndInputTextToField(FIELD_MOBILE_COUNTRY_CODE_XP, mobileAreaCode);
	}

	private void fillMobilePhone(String mobile){
		WebDriverUtils.clearAndInputTextToField(FIELD_MOBILE_XP, mobile);
	}

	private void fillVerificationQuestion(String verificationQuestion){
		WebDriverUtils.setDropdownOptionByText(DROPDOWN_VERIFICATION_QUESTION_XP, verificationQuestion);
	}

	private void fillVerificationAnswer(String verificationAnswer){
		WebDriverUtils.clearAndInputTextToField(FIELD_ANSWER_XP, verificationAnswer);
	}

	private void setCurrency(String currencyCode){
		WebDriverUtils.setDropdownOptionByText(DROPDOWN_CURRENCY_XP, currencyCode);
	}

	private void fillBonusCode(String coupon){
		WebDriverUtils.clearAndInputTextToField(FIELD_BONUSCODE_XP, coupon);
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

	public boolean allValidationEmptyVisible(){
		return WebDriverUtils.isVisible(LABEL_ALL_VALIDATION_EMPTY_XP);
	}

//    public boolean anyValidationErrorVisible() {
//        return WebDriverUtils.isVisible(ALL_VALIDATION_ERROR_XP);
//    }

//    private boolean validationUserNameAvailable () {
//        return WebDriverUtils.isVisible(USERNAME_VALIDATION_ERROR_XP);
//    }

	public boolean checkboxTermsAndConditionsValidationErrorVisible(){
		return WebDriverUtils.isVisible(CHECKBOX_TERMS_AND_CONDITION_VALIDATION_ERROR_XP);
	}

	public AdultContentPage clickAdultContent(){
		WebDriverUtils.click(LINK_ADULT_CONTENT_XP);
		return new AdultContentPage();
	}

//    public HomePage registerUser(){
//        return (HomePage)this.registerUser(defaultUserData.getRandomUserData(), false);
//    }

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
		AbstractPageObject result=null;

		switch(expectedPage){

			case homePage:
				result=NavigationUtils.closeAllPopups(Page.homePage);
				break;
			case afterRegistrationPopup:
				result=NavigationUtils.closeAllPopups(Page.afterRegistrationPopup);
				break;
			case welcomePopup:
				result=NavigationUtils.closeAllPopups(Page.welcomePopup);
				break;
			case registrationPage:
				result =NavigationUtils.closeAllPopups(Page.registrationPage);
				break;
			default:
				break;
		}
		return result;
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
        fillAddress2(userData.getAddress2());
		//
		fillHouse(userData.getHouse());
		fillPostCode(userData.getPostCode());
		fillPhoneAreaCode(userData.getPhoneAreaCode());
		fillPhone(userData.getPhone());
		fillMobileAreaCode(userData.getMobileAreaCode());
		fillMobilePhone(userData.getMobile());
		//
		fillUsername(userData.getUsername());
		fillPassword(userData.getPassword());
		fillConfirmPassword(userData.getPassword());
		//
		fillVerificationQuestion(userData.getVerificationQuestion());
		fillVerificationAnswer(userData.getVerificationAnswer());
		setCurrency(userData.getCurrency());
	}

	private void fillOther(boolean tac, boolean isReceiveBonusesChecked, boolean isBonusCodeEntered, String bonusCode){
		setTitle();
		setGender();
		setCheckboxTermsAndConditions(tac);
		setCheckboxReceiveBonuses(isReceiveBonusesChecked);
		if(isBonusCodeEntered && bonusCode == null){
			fillBonusCode(BONUS_CODE);
		}else if(isBonusCodeEntered && bonusCode != null){
			fillBonusCode(bonusCode);
		}
	}

	public String getCurrency(){
		return WebDriverUtils.getDropdownSelectedOption(DROPDOWN_CURRENCY_XP).getText();
	}

	public String getCountry(){
		return WebDriverUtils.getDropdownSelectedOption(DROPDOWN_COUNTRY_XP).getText();
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
		boolean title=labelMarkedAsRequired(LABEL_TITLE_XP);
		boolean gender=labelMarkedAsRequired(LABEL_GENDER_XP);
		boolean first=labelMarkedAsRequired(LABEL_FIRSTNAME_XP);
		boolean last=labelMarkedAsRequired(LABEL_LASTNAME_XP);
		boolean birthDate=labelMarkedAsRequired(LABEL_BIRTHDAY_XP);
		boolean email1=labelMarkedAsRequired(LABEL_EMAIL_XP);
		boolean email2=labelMarkedAsRequired(LABEL_EMAIL_VERIFICATION_XP);
		boolean country=labelMarkedAsRequired(LABEL_COUNTRY_XP);
		boolean city=labelMarkedAsRequired(LABEL_CITY_XP);
		boolean address1=labelMarkedAsRequired(LABEL_ADDRESS1_XP);
		boolean address2=labelMarkedAsRequired(LABEL_ADDRESS2_XP);
		boolean house=labelMarkedAsRequired(LABEL_HOUSE_XP);
		boolean mobile=labelMarkedAsRequired(LABEL_MOBILE_XP);
		boolean postCode=labelMarkedAsRequired(LABEL_POSTCODE_XP);
		boolean phone=labelMarkedAsRequired(LABEL_PHONE_XP);
		boolean username=labelMarkedAsRequired(LABEL_USERNAME_XP);
		boolean password1=labelMarkedAsRequired(LABEL_PASSWORD_XP);
		boolean password2=labelMarkedAsRequired(LABEL_PASSWORD_VERIFICATION_XP);
		boolean question=labelMarkedAsRequired(LABEL_DROPDOWN_VERIFICATION_QUESTION_XP);
		boolean answer=labelMarkedAsRequired(LABEL_ANSWER_XP);
		boolean currency=labelMarkedAsRequired(LABEL_CURRENCY_XP);
		boolean bonus=labelMarkedAsRequired(LABEL_BONUSCODE_XP);

		boolean requiredMarked=title && gender && first && last && birthDate && email1 && email2 && country && city && address1 && postCode && phone && username && password1 && password2 && question && answer && currency;
		boolean optionalUnmarked=address2 || house || mobile || bonus;


		return (requiredMarked == true && optionalUnmarked == false);
	}

    public String getErrorMessageText() {
        return getVisibleMessageText(LABEL_MESSAGE_ERROR_XP);
    }

	public String getUsernameSuggestionText() {
		return getVisibleMessageText(LABEL_USERNAME_SUGGESTION_XP);
	}

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

    public Collection<String> getCountryList() {
        return WebDriverUtils.getDropdownOptionsText(DROPDOWN_COUNTRY_XP);
    }

    public Collection<String> getCurrencyList() {
        return WebDriverUtils.getDropdownOptionsText(DROPDOWN_CURRENCY_XP);
    }

    /* Fields validation */

    public void validateFirstname(ValidationRule rule) {
        ValidationUtils.validate(FIELD_FIRSTNAME_XP, rule);
    }

    public void validateLastname(ValidationRule rule) {
        ValidationUtils.validate(FIELD_LASTNAME_XP, rule);
    }

    public void validateEmail(ValidationRule rule) {
        ValidationUtils.validate(FIELD_EMAIL_XP, rule);
    }

    public void validateVerificationEmail(ValidationRule rule) {
        ValidationUtils.validate(FIELD_EMAIL_VERIFICATION_XP, rule);
    }

    public void validateCity(ValidationRule rule) {
        ValidationUtils.validate(FIELD_CITY_XP, rule);
    }

    public void validateAddress1(ValidationRule rule) {
        ValidationUtils.validate(FIELD_ADDRESS1_XP, rule);
    }

    public void validateAddress2(ValidationRule rule) {
        ValidationUtils.validate(FIELD_ADDRESS2_XP, rule);
    }

    public void validateHouse(ValidationRule rule) {
        ValidationUtils.validate(FIELD_HOUSE_XP, rule);
    }

    public void validatePostcode(ValidationRule rule) {
        ValidationUtils.validate(FIELD_POSTCODE_XP, rule);
    }

    public void validatePhoneCountryCode(ValidationRule rule) {
        ValidationUtils.validate(FIELD_PHONE_COUNTRY_CODE_XP, rule);
    }

    public void validatePhone(ValidationRule rule) {
        ValidationUtils.validate(FIELD_PHONE_XP, rule);
    }

    public void validateMobileCountryCode(ValidationRule rule) {
        ValidationUtils.validate(FIELD_MOBILE_COUNTRY_CODE_XP, rule);
    }

    public void validateMobile(ValidationRule rule) {
        ValidationUtils.validate(FIELD_MOBILE_XP, rule);
    }

    public void validateUsername(ValidationRule rule) {
        ValidationUtils.validate(FIELD_USERNAME_XP, rule);
    }

    public void validatePassword(ValidationRule rule) {
        ValidationUtils.validate(FIELD_PASSWORD_XP, rule);
    }

    public void validateVerificationPassword(ValidationRule rule) {
        ValidationUtils.validate(FIELD_PASSWORD_VERIFICATION_XP, rule);
    }

    public void validateAnswer(ValidationRule rule) {
        ValidationUtils.validate(FIELD_ANSWER_XP, rule);
    }

    public void validateBonusCode(ValidationRule rule) {
        ValidationUtils.validate(FIELD_BONUSCODE_XP, rule);
    }

}
