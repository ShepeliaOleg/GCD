import enums.LogCategory;
import enums.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.popups.AfterRegistrationPopup;
import pageObjects.popups.ReadTermsAndConditionsPopup;
import pageObjects.registration.RegistrationPage;
import springConstructors.Defaults;
import springConstructors.IMS;
import springConstructors.UserData;
import springConstructors.validation.ValidationRule;
import testUtils.AbstractTest;
import utils.NavigationUtils;
import utils.RandomUtils;
import utils.WebDriverUtils;
import utils.logs.Log;
import utils.logs.LogEntry;
import utils.logs.LogUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * User: sergiich
 * Date: 4/10/14
 */
public class RegistrationTest extends AbstractTest{

	@Autowired
	@Qualifier("userData")
	private UserData defaultUserData;
	
	@Autowired
	@Qualifier("nameValidationRule")
	private ValidationRule nameValidationRule;

	@Autowired
	@Qualifier("emailValidationRule")
	private ValidationRule emailValidationRule;

	@Autowired
	@Qualifier("cityValidationRule")
	private ValidationRule cityValidationRule;

	@Autowired
	@Qualifier("addressValidationRule")
	private ValidationRule addressValidationRule;

	@Autowired
	@Qualifier("houseValidationRule")
	private ValidationRule houseValidationRule;

	@Autowired
	@Qualifier("postcodeValidationRule")
	private ValidationRule postcodeValidationRule;

	@Autowired
	@Qualifier("countryPhoneCodeValidationRule")
	private ValidationRule countryPhoneCodeValidationRule;

	@Autowired
	@Qualifier("phoneValidationRule")
	private ValidationRule phoneValidationRule;

	@Autowired
	@Qualifier("searchValidationRule")
	private ValidationRule searchValidationRule;

	@Autowired
	@Qualifier("usernameValidationRule")
	private ValidationRule usernameValidationRule;

	@Autowired
	@Qualifier("passwordValidationRule")
	private ValidationRule passwordValidationRule;

	@Autowired
	@Qualifier("answerValidationRule")
	private ValidationRule answerValidationRule;

	@Autowired
	@Qualifier("bonusCodeValidationRule")
	private ValidationRule bonusCodeValidationRule;

	@Autowired
	@Qualifier("iMS")
	private IMS iMS;

	@Autowired
	@Qualifier("defaults")
	private Defaults defaults;
	
	/*POSITIVE*/
	
	/*1. Valid user registration*/
	@Test(groups = {"smoke"})
	public void validUserRegistration() {
		RegistrationPage registrationPage = NavigationUtils.navigateToRegistration(true);
		UserData userData = defaultUserData.getRandomUserData();
		HomePage homePage = registrationPage.registerUser(userData);
		Assert.assertEquals(homePage.isLoggedIn(), true);
	}

	/*#2. Registration with receive bonuses check box checked*/
	@Test(groups = {"regression"})
	public void receiveBonusesIsCheckedInIMS(){
		UserData userData=defaultUserData.getRandomUserData();
		RegistrationPage registrationPage = NavigationUtils.navigateToRegistration(true);
		HomePage homePage=(HomePage) registrationPage.registerUser(userData, true);
		boolean receiveBonusesCheckedInIMS=iMS.navigateToPlayedDetails(userData.getUsername()).getAllChannelsCheckboxState();
		Assert.assertTrue(receiveBonusesCheckedInIMS);
	}

    /*#3. Registration with receive bonuses check box unchecked*/
	@Test(groups = {"regression"})
	public void receiveBonusesIsNotCheckedInIMS(){
		UserData userData=defaultUserData.getRandomUserData();
		RegistrationPage registrationPage = NavigationUtils.navigateToRegistration(true);
		registrationPage.registerUser(userData, false);
		boolean receiveBonusesNotCheckedInIMS=iMS.navigateToPlayedDetails(userData.getUsername()).getAllChannelsCheckboxState();
		Assert.assertTrue(!receiveBonusesNotCheckedInIMS);
	}

    /*#4. Registration with bonus code provided*/
	@Test(groups = {"regression"})
	public void registrationWithBonusCoupon(){
		UserData userData=defaultUserData.getRandomUserData();
		RegistrationPage registrationPage = NavigationUtils.navigateToRegistration(true);
        HomePage homePage=(HomePage)registrationPage.registerUser(userData, false, true);
		boolean bonusesPresent=homePage.getBalance().equals("£20.00") || homePage.getBalance().equals("£ 20.00");
		Assert.assertTrue(bonusesPresent);
	}

    /*#5. Registration without bonus code provided*/

	public void registrationWithOutBonusCoupon(){
		UserData userData=defaultUserData.getRandomUserData();
		RegistrationPage registrationPage = NavigationUtils.navigateToRegistration(true);
        HomePage homePage=(HomePage) registrationPage.registerUser(userData, false, false);
		boolean bonusesNotPresent=homePage.getBalance().equals("£0.00") || homePage.getBalance().equals("£ 0.00");
		Assert.assertTrue(bonusesNotPresent);
	}

    /*#6. Player is registered with currency selected*/
	@Test(groups = {"regression"})
	public void validHeaderUnitBalance(){
		RegistrationPage registrationPage = NavigationUtils.navigateToRegistration(true);
		// String defaultCurrencyInRegistrationForm = registrationPage.getCurrency();
		UserData userData=defaultUserData.getRandomUserData();
        HomePage homePage=(HomePage)registrationPage.registerUser(userData);
		String currencyInHeader=homePage.getBalance();
		Assert.assertTrue(currencyInHeader.contains("£"));
	}

    /*#7. After registration web content*/
	@Test(groups = {"regression"})
	public void afterRegistrationWebContent(){
		RegistrationPage registrationPage = NavigationUtils.navigateToRegistration(true);
		UserData userData=defaultUserData.getRandomUserData();
		AfterRegistrationPopup afterRegistrationPopup=(AfterRegistrationPopup) registrationPage.registerUser(userData, Page.afterRegistrationPopup);
	}

    /*#10. After-registration redirect*/
	@Test(groups = {"regression"})
	public void afterRegistrationRedirect(){
		UserData userData=defaultUserData.getRandomUserData();
		RegistrationPage registrationPage = NavigationUtils.navigateToRegistration(true);
        HomePage homePage=(HomePage)registrationPage.registerUser(userData);
		String actualUrl=WebDriverUtils.getCurrentUrl();
		Assert.assertTrue(actualUrl.endsWith("/afterreg"));
	}

    /*#11. The list of supported countries is correct*/
	@Test(groups = {"regression"})
	public void countryList(){
		RegistrationPage registrationPage = NavigationUtils.navigateToRegistration(true);
		Collection<String> actualCountryList=registrationPage.getCountryList();
		Collection<String> diff=RandomUtils.getDiffElementsFromLists(actualCountryList, defaults.getCountryList());
		boolean listsEquals=diff.isEmpty();
		Assert.assertTrue(listsEquals == true);
	}

    /*#12. The list of supported currencies is correct*/
	@Test(groups = {"regression"})
	public void currencyList(){
		RegistrationPage registrationPage = NavigationUtils.navigateToRegistration(true);
		Collection<String> actualCurrencyList=registrationPage.getCurrencyList();
		Collection<String> diff=RandomUtils.getDiffElementsFromLists(actualCurrencyList, defaults.getCurrencyList());
		boolean listsEquals=diff.isEmpty();
		Assert.assertTrue(listsEquals == true);
	}

    /*#13. T&C web content is shown when clicking on T&C link*/
	@Test(groups = {"regression"})
	public void openTermsAndConditionsPopup(){
		RegistrationPage registrationPage = NavigationUtils.navigateToRegistration(true);
		ReadTermsAndConditionsPopup readTermsAndConditionsPopup=registrationPage.navigateToTermsAndConditions();
	}

    /*#14. 18+ web content is shown when clicking on 18+ link*/
	@Test(groups = {"regression"})
	public void adultContentIsShown() {
		UserData userData = defaultUserData.getRandomUserData();
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		userData.setUsername(usernameValidationRule.generateValidString());
		RegistrationPage registrationPage = homePage.navigateToRegistration();
		Assert.assertTrue(registrationPage.isAdultContentPresent());
	}

	/*#15. Logs registration*/
	@Test(groups = {"regression"})
	public void checkLogParametersRegistration(){
		LogCategory[] logCategories = new LogCategory[]{LogCategory.SetPlayerInfoRequest, LogCategory.SetPlayerInfoResponse};
		UserData userData=defaultUserData.getRandomUserData();
		String[] parameters = {"objectIdentity="+userData.getUsername()+"-playtech81001",
				"KV(1, playtech81001)",
				"KV(2, "+userData.getUsername()+")",
				"KV(7, "+userData.getCity()+")",
				"KV(19, "+userData.getEmail()+")",
				"KV(21, "+userData.getFirstName()+")",
				"KV(24, "+userData.getLastName()+")",
				"KV(27, "+userData.getPhoneAreaCode()+userData.getPhone()+")",
				"KV(34, "+userData.getPostCode()+")"};
		RegistrationPage registrationPage = NavigationUtils.navigateToRegistration(true);
		registrationPage.registerUser(userData);
		Log log = LogUtils.getCurrentLogs(logCategories);
		log.doResponsesContainErrors();
		LogEntry regReq = log.getEntry(LogCategory.SetPlayerInfoRequest);
		regReq.containsParameters(parameters);
	}

	/*#16. Logs username check*/
	@Test(groups = {"regression"})
	public void checkLogParametersUsername(){
		LogCategory[] logCategories = new LogCategory[]{LogCategory.CheckUsernameRequest, LogCategory.CheckUsernameResponse};
		UserData userData=defaultUserData.getRandomUserData();
		String[] parameters = {"objectIdentity=playtech81001",
				"username="+userData.getUsername(),
				"firstName="+userData.getFirstName(),
				"lastName="+userData.getLastName(),
				"email="+userData.getEmail()};
		RegistrationPage registrationPage = NavigationUtils.navigateToRegistration(true);
		registrationPage.registerUser(userData);
		Log log = LogUtils.getCurrentLogs(logCategories);
		log.doResponsesContainErrors();
		LogEntry usrReq = log.getEntry(LogCategory.CheckUsernameRequest);
		usrReq.containsParameters(parameters);
	}

    /*#17. IMS Player Details Page*/
	@Test(groups = {"regression"})
	public void verifyRegistrationDataIsShownCorrectlyInIMS(){
		UserData userData=defaultUserData.getRandomUserData();
		RegistrationPage registrationPage = NavigationUtils.navigateToRegistration(true);
		registrationPage.registerUser(userData);
		boolean registrationValuesAreCorrect=iMS.validateRegisterData(userData);
		Assert.assertTrue(registrationValuesAreCorrect);
	}

    /*#18. Registration with unique username is possible*/
	@Test(groups = {"regression"})
	public void verifyRegistrationWithUniqueUsername() {
		UserData userData = defaultUserData.getRandomUserData();
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage = homePage.navigateToRegistration();
		homePage = (HomePage) registrationPage.registerUser(userData);
	}

    /*#19. All required fields are marked with asterisks*/
	@Test(groups = {"regression"})
	public void requiredFieldsLabelsMarkedWithStar(){
		RegistrationPage registrationPage = NavigationUtils.navigateToRegistration(true);
		boolean checkboxTermsAndConditionsValidationErrorVisible=registrationPage.labelsRequiredMarkingCorrect();
		Assert.assertTrue(checkboxTermsAndConditionsValidationErrorVisible);
	}

    /*#20. By default T&C check box is unchecked while "I'd like to receive bonuses" is checked*/
	@Test(groups = {"regression"})
	public void defaultCheckboxesState(){
		RegistrationPage registrationPage = NavigationUtils.navigateToRegistration(true);
		boolean receiveBonusesChecked=registrationPage.getCheckboxStateReceiveBonuses();
		boolean termsAndConditionsChecked=registrationPage.getCheckboxStateTermsAndConditions();
		Assert.assertTrue((receiveBonusesChecked == true) && (termsAndConditionsChecked == false));
	}

    /*#22. Country - country phone prefix mapping*/
	@Test(groups = {"regression"})
	public void countryPhonePrefix(){
		RegistrationPage registrationPage = NavigationUtils.navigateToRegistration(true);
		Collection<String> countryList = defaults.getCountryList();
		for (String country:countryList) {
			registrationPage.fillCountry(country);
			String phoneAreaCode = registrationPage.getPhoneAreaCode();
			String mobileAreaCode = registrationPage.getMobileAreaCode();
			String expectedAreaCode = defaults.getPhoneCodeByCountryName(country);
			if (!phoneAreaCode.equals(mobileAreaCode)) {
				throw new RuntimeException("Phone area code (" + phoneAreaCode + ") and mobile area code (" + mobileAreaCode + ") are not equal on selecting " + country + " country.");
			}
			else {
				if (!phoneAreaCode.equals("+" + expectedAreaCode)) {
					throw new RuntimeException("Phone area code (" + phoneAreaCode + ") and mobile area code (" + mobileAreaCode +") are not equal to expected area code (" + expectedAreaCode + ") on selecting " + country + " country.");
				}
			}
		}
	}

    /*#23. Default selected country*/
	@Test(groups = {"regression"})
	public void defaultCountry(){
		HomePage homePage=NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage = homePage.navigateToRegistration();
		String defaultCountryName = registrationPage.getCountry();
		boolean defaultCountrySelected = defaultCountryName.equals(defaults.getDefaultCountry());
		boolean fillFieldsVisible = registrationPage.getFillFieldsButtonVisibleState();
		Assert.assertTrue(defaultCountrySelected == true && fillFieldsVisible == true);
	}

    /*#24. Change country from UK to any and selecting UK again Fill all fields element is displayed*/
	@Test(groups = {"regression"})
	public void fillFieldsAppearDisappear(){
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage = homePage.navigateToRegistration();
		// stage 1
		boolean fillFieldsVisibleStage1 = registrationPage.getFillFieldsButtonVisibleState();
		String randomCountry;
		String defaultCountry = defaults.getDefaultCountry();
		do {
			randomCountry = defaults.getRandomCountryName();
		}
		while (randomCountry.equals(defaultCountry));
		// stage 2
		registrationPage.fillCountry(randomCountry);
		boolean fillFieldsVisibleStage2 = registrationPage.getFillFieldsButtonVisibleState();
		// stage 3
		registrationPage.fillCountry(defaultCountry);
		boolean fillFieldsVisibleStage3 = registrationPage.getFillFieldsButtonVisibleState();
		Assert.assertTrue(fillFieldsVisibleStage1 == true && fillFieldsVisibleStage2 == false && fillFieldsVisibleStage3 == true);
	}

    /*#25. Enter zipcode - City/Address1/Address2/House number updated on Fill all fields click and are editable*/
	@Test(groups = {"regression"})
	public void zipcodeFillsAddress(){
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage = homePage.navigateToRegistration();
		List<String> fullAddressActual = registrationPage.fillAutoByPostCode("SE17RL");
		List<String> fullAddressExpected = Arrays.asList("London", "35-41 Lower Marsh", "", "35-41");
		boolean autofilledFieldsAreEditable = registrationPage.autofilledFieldsAreEditable();
		Assert.assertTrue(fullAddressActual.equals(fullAddressExpected) == true && autofilledFieldsAreEditable == true);
	}

//	public void affiliateSupport(){
//		UserData userData = defaultUserData.getRandomUserData();
//		HomePage homePage = NavigationUtils.navigateToPortal(true);
//		RegistrationPage registrationPage = homePage.navigateToRegistration();
//		WebDriverUtils.addCookie("banner_domainclick", "advert1,v2,v3,v4,BTAG:12333", baseUrl.toString(), new Date(2014,1,1)));
//		homePage = (HomePage) registrationPage.registerUser(userData);
//		WebDriverUtils.runtimeExceptionWithLogs(userData.getUsername());
//
//	}

    /*NEGATIVE*/

    /*#1. Try to use already existing username*/
	@Test(groups = {"regression"})
	public void alreadyUsedUsername(){
		RegistrationPage registrationPage = NavigationUtils.navigateToRegistration(true);
		UserData generatedUserData=defaultUserData.getRandomUserData();
		UserData registeredUserData=defaultUserData.getRegisteredUserData();
		generatedUserData.setUsername(registeredUserData.getUsername());
		registrationPage=(RegistrationPage) registrationPage.registerUser(generatedUserData, Page.registrationPage);
		String errorMessageText=registrationPage.getUsernameSuggestionText();
		boolean usernameUsedMessageDisplayed=errorMessageText.contains("Not available, check suggestions:");
		Assert.assertTrue(usernameUsedMessageDisplayed);
	}

    /*#2. Try to use already used email*/
	@Test(groups = {"regression"})
	public void alreadyUsedEmail(){
		RegistrationPage registrationPage = NavigationUtils.navigateToRegistration(true);
		UserData generatedUserData=defaultUserData.getRandomUserData();
		generatedUserData.setEmail("playtech@spamavert.com");
		registrationPage=(RegistrationPage) registrationPage.registerUser(generatedUserData, Page.registrationPage);
		String errorMessageText=registrationPage.getErrorMessageText();
		boolean emailUsedMessageDisplayed=errorMessageText.equals("The specified email address is already in use.");

		Assert.assertTrue(emailUsedMessageDisplayed);
	}

    /*3. Try to use invalid bonus code*/
	@Test(groups = {"regression"})
	public void registrationWithInvalidBonusCoupon() {
		String invalidBonusCoupon = searchValidationRule.generateValidString();
		UserData userData = defaultUserData.getRandomUserData();
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage = homePage.navigateToRegistration();
		registrationPage = (RegistrationPage) registrationPage.registerUser(userData, invalidBonusCoupon);
		boolean bonusErrorPresent = registrationPage.isBonusCodeErrorPresent();
		Assert.assertTrue(bonusErrorPresent);
	}

    /*#4. Email and email confirmation do not match*/
	@Test(groups = {"regression"})
	public void emailDoNotMatch(){
		UserData generatedUserData=defaultUserData.getRandomUserData();
		RegistrationPage registrationPage = NavigationUtils.navigateToRegistration(true);
		registrationPage.fillRegistrationForm(generatedUserData);
		registrationPage.fillConfirmEmail("incorrect@playtech.com");
		registrationPage.submit();
		String tooltipMessageText=registrationPage.getTooltipMessageText();
		boolean usernameUsedMessageDisplayed=tooltipMessageText.equals("This address is different from the one above, please correct");
		Assert.assertTrue(usernameUsedMessageDisplayed);
	}

    /*#5. Password & Confirmation do not match*/
	@Test(groups = {"regression"})
	public void passwordDoNotMatch(){
		UserData generatedUserData=defaultUserData.getRandomUserData();
		RegistrationPage registrationPage = NavigationUtils.navigateToRegistration(true);
		registrationPage.fillRegistrationForm(generatedUserData);
		registrationPage.fillConfirmPassword("incorrect");
		registrationPage.submit();
		String errorMessageText=registrationPage.getTooltipMessageText();
		boolean emailUsedMessageDisplayed=errorMessageText.equals("Passwords are not the same");
		Assert.assertTrue(emailUsedMessageDisplayed);
	}

    /*#6. Try to submit registration form without accepting T&C (without checking check box)*/
	@Test(groups = {"regression"})
	public void submitWithUncheckedTermsAndConditions(){
		UserData userData=defaultUserData.getRandomUserData();
		RegistrationPage registrationPage = NavigationUtils.navigateToRegistration(true);
		registrationPage=(RegistrationPage) registrationPage.registerUser(userData, false, false, false);
		boolean checkboxTermsAndConditionsValidationErrorVisible=registrationPage.checkboxTermsAndConditionsValidationErrorVisible();
		Assert.assertTrue(checkboxTermsAndConditionsValidationErrorVisible);
	}

    /*VALIDATION*/
	@Test(groups = {"validation"})
	public void validationRequiredFieldsEmpty(){
		RegistrationPage registrationPage = NavigationUtils.navigateToRegistration(true);
		registrationPage.submit();
		int validationErrorsCount=WebDriverUtils.getXpathCount(RegistrationPage.VALIDATION_ERROR_XP);
		Assert.assertTrue(validationErrorsCount >= 17);
	}

	@Test(groups = {"validation"})
	public void firstnameFieldValidation() {
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage = homePage.navigateToRegistration();
		registrationPage.validateFirstname(nameValidationRule);
	}

	@Test(groups = {"validation"})
	public void lastnameFieldValidation() {
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage = homePage.navigateToRegistration();
		registrationPage.validateLastname(nameValidationRule);
	}

	@Test(groups = {"validation"})
	public void emailFieldValidation() {
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage = homePage.navigateToRegistration();
		registrationPage.validateEmail(emailValidationRule);
	}

	@Test(groups = {"validation"})
	public void verificationEmailFieldValidation() {
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage = homePage.navigateToRegistration();
		registrationPage.validateVerificationEmail(emailValidationRule);
	}

	@Test(groups = {"validation"})
	public void cityFieldValidation() {
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage = homePage.navigateToRegistration();
		registrationPage.validateCity(cityValidationRule);
	}

	@Test(groups = {"validation"})
	public void address1FieldValidation() {
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage = homePage.navigateToRegistration();
		registrationPage.validateAddress1(addressValidationRule);
	}

	@Test(groups = {"validation"})
	public void address2FieldValidation() {
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage = homePage.navigateToRegistration();
		registrationPage.validateAddress2(addressValidationRule);
	}

	@Test(groups = {"validation"})
	public void houseFieldValidation() {
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage = homePage.navigateToRegistration();
		registrationPage.validateHouse(houseValidationRule);
	}

	@Test(groups = {"validation"})
	public void postcodeFieldValidation() {
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage = homePage.navigateToRegistration();
		registrationPage.validatePostcode(postcodeValidationRule);
	}

	@Test(groups = {"validation"})
	public void phoneFieldValidation() {
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage = homePage.navigateToRegistration();
		registrationPage.validatePhone(phoneValidationRule);
	}

	@Test(groups = {"validation"})
	public void phoneCountryCodeFieldValidation() {
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage = homePage.navigateToRegistration();
		registrationPage.validatePhoneCountryCode(countryPhoneCodeValidationRule);
	}

	@Test(groups = {"validation"})
	public void mobileFieldValidation() {
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage = homePage.navigateToRegistration();
		registrationPage.validateMobile(phoneValidationRule);
	}

	@Test(groups = {"validation"})
	public void mobileCountryCodeFieldValidation() {
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage = homePage.navigateToRegistration();
		registrationPage.validateMobileCountryCode(countryPhoneCodeValidationRule);
	}

	@Test(groups = {"validation"})
	public void usernameFieldValidation() {
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage = homePage.navigateToRegistration();
		registrationPage.validateUsername(usernameValidationRule);
	}

	@Test(groups = {"validation"})
	public void passwordFieldValidation() {
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage = homePage.navigateToRegistration();
		registrationPage.validatePassword(passwordValidationRule);
	}

	@Test(groups = {"validation"})
	public void verificationPasswordFieldValidation() {
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage = homePage.navigateToRegistration();
		registrationPage.validateVerificationPassword(passwordValidationRule);
	}

	@Test(groups = {"validation"})
	public void answerFieldValidation() {
		HomePage homePage = NavigationUtils.navigateToHome();
		RegistrationPage registrationPage = homePage.navigateToRegistration();
		registrationPage.validateAnswer(answerValidationRule);
	}

	@Test(groups = {"validation"})
	public void bonusCodeFieldValidation() {
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage = homePage.navigateToRegistration();
		registrationPage.validateBonusCode(bonusCodeValidationRule);
	}
}
