import enums.ConfiguredPages;
import enums.LogCategory;
import enums.Page;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.base.AbstractPage;
import pageObjects.popups.AfterRegistrationPopup;
import pageObjects.popups.ReadTermsAndConditionsPopup;
import pageObjects.registration.RegistrationPage;
import springConstructors.Defaults;
import springConstructors.IMS;
import springConstructors.UserData;
import springConstructors.ValidationRule;
import testUtils.AbstractTest;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.TypeUtils;
import utils.WebDriverUtils;
import utils.core.WebDriverObject;
import utils.logs.Log;
import utils.logs.LogEntry;
import utils.logs.LogUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
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
        PortalUtils.registerUser(defaultUserData.getRandomUserData());
		Assert.assertTrue(new AbstractPage().isLoggedIn());
	}

	/*#2. Registration with receive bonuses check box checked*/
	@Test(groups = {"regression"})
	public void receiveBonusesIsCheckedInIMS(){
		UserData userData=defaultUserData.getRandomUserData();
		RegistrationPage registrationPage = (RegistrationPage)NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		HomePage homePage=(HomePage) registrationPage.registerUser(userData, true);
		boolean receiveBonusesCheckedInIMS=iMS.navigateToPlayedDetails(userData.getUsername()).getAllChannelsCheckboxState();
		Assert.assertTrue(receiveBonusesCheckedInIMS);
	}

    /*#3. Registration with receive bonuses check box unchecked*/
	@Test(groups = {"regression"})
	public void receiveBonusesIsNotCheckedInIMS(){
		UserData userData=defaultUserData.getRandomUserData();
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.registerUser(userData, false);
		boolean receiveBonusesNotCheckedInIMS=iMS.navigateToPlayedDetails(userData.getUsername()).getAllChannelsCheckboxState();
		Assert.assertTrue(!receiveBonusesNotCheckedInIMS);
	}

    /*#4. Registration with bonus code provided*/
	@Test(groups = {"regression"})
	public void registrationWithBonusCoupon(){
		UserData userData=defaultUserData.getRandomUserData();
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        HomePage homePage=(HomePage)registrationPage.registerUser(userData, false, true);
		boolean bonusesPresent=homePage.getBalance().equals("£10.00") || homePage.getBalance().equals("£ 10.00");
		Assert.assertTrue(bonusesPresent);
	}

    /*#5. Registration without bonus code provided*/
    @Test(groups = {"regression"})
	public void registrationWithOutBonusCoupon(){
		UserData userData=defaultUserData.getRandomUserData();
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        HomePage homePage=(HomePage) registrationPage.registerUser(userData, false, false);
		boolean bonusesNotPresent=homePage.getBalance().equals("£0.00") || homePage.getBalance().equals("£ 0.00");
		Assert.assertTrue(bonusesNotPresent);
	}

    /*#6. Player is registered with currency selected*/
	@Test(groups = {"regression"})
	public void validHeaderUnitBalance(){
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		// String defaultCurrencyInRegistrationForm = registrationPage.getCurrency();
		UserData userData=defaultUserData.getRandomUserData();
        HomePage homePage=(HomePage)registrationPage.registerUser(userData);
		String currencyInHeader=homePage.getBalance();
		Assert.assertTrue(currencyInHeader.contains("£"));
	}

    /*#7. After registration web content*/
	@Test(groups = {"regression"})
	public void afterRegistrationWebContent(){
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		UserData userData=defaultUserData.getRandomUserData();
		AfterRegistrationPopup afterRegistrationPopup=(AfterRegistrationPopup) registrationPage.registerUser(userData, Page.afterRegistrationPopup);
	}

    /*#10. After-registration redirect*/
	@Test(groups = {"regression"})
	public void afterRegistrationRedirect(){
		UserData userData=defaultUserData.getRandomUserData();
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        HomePage homePage=(HomePage)registrationPage.registerUser(userData);
		String actualUrl=WebDriverUtils.getCurrentUrl();
		Assert.assertTrue(actualUrl.endsWith("/afterreg"));
	}

    /*#11. The list of supported countries is correct*/
	@Test(groups = {"regression"})
	public void countryList(){
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		Collection<String> actualCountryList=registrationPage.getCountryList();
		Collection<String> diff=TypeUtils.getDiffElementsFromLists(actualCountryList, defaults.getCountryList());
		boolean listsEquals=diff.isEmpty();
		Assert.assertTrue(listsEquals == true);
	}

    /*#??. The list of supported nationalities is correct*/
    @Test(groups = {"spanish"})
    public void nationalityList(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        Collection<String> actualNationalityList =registrationPage.getNationalityList();
        Collection<String> diff=TypeUtils.getDiffElementsFromLists(actualNationalityList, defaults.getCountryList());
        boolean listsEquals=diff.isEmpty();
        Assert.assertTrue(listsEquals == true);
    }

    /*#12. The list of supported currencies is correct*/
	@Test(groups = {"regression"})
	public void currencyList(){
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		Collection<String> actualCurrencyList=registrationPage.getCurrencyList();
		Collection<String> diff= TypeUtils.getDiffElementsFromLists(actualCurrencyList, defaults.getCurrencyList());
		boolean listsEquals=diff.isEmpty();
		Assert.assertTrue(listsEquals == true);
	}

    /*#13. T&C web content is shown when clicking on T&C link*/
	@Test(groups = {"regression"})
	public void openTermsAndConditionsPopup(){
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		ReadTermsAndConditionsPopup readTermsAndConditionsPopup=registrationPage.navigateToTermsAndConditions();
	}

    /*#14. 18+ web content is shown when clicking on 18+ link*/
	@Test(groups = {"regression"})
	public void adultContentIsShown() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		Assert.assertTrue(registrationPage.isAdultContentPresent());
	}

	/*#15. Logs registration*/
	@Test(groups = {"regression", "logs"})
	public void checkLogParametersRegistration(){
        try{
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
            RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
            registrationPage.registerUser(userData);
            Log log = LogUtils.getCurrentLogs(logCategories);
            log.doResponsesContainErrors();
            LogEntry regReq = log.getEntry(LogCategory.SetPlayerInfoRequest);
            regReq.containsParameters(parameters);
        }catch (RuntimeException e){
            if(e.getMessage().contains("Not all registration logs appeared") || e.toString().contains("Logs have not been updated")){
                throw new SkipException("Log page issue"+WebDriverUtils.getUrlAndLogs());
            }else{
                throw new RuntimeException(e.getMessage());
            }
        }
	}

	/*#16. Logs username check*/
	@Test(groups = {"regression", "logs"})
	public void checkLogParametersUsername(){
        try{
            LogCategory[] logCategories = new LogCategory[]{LogCategory.CheckUsernameRequest, LogCategory.CheckUsernameResponse};
            UserData userData=defaultUserData.getRandomUserData();
            String[] parameters = {"objectIdentity=playtech81001",
                    "username="+userData.getUsername(),
                    "firstName="+userData.getFirstName(),
                    "lastName="+userData.getLastName(),
                    "email="+userData.getEmail()};
            RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
            registrationPage.registerUser(userData);
            Log log = LogUtils.getCurrentLogs(logCategories);
            log.doResponsesContainErrors();
            LogEntry usrReq = log.getEntry(LogCategory.CheckUsernameRequest);
            usrReq.containsParameters(parameters);
        }catch (RuntimeException e){
            if(e.getMessage().contains("Not all registration logs appeared") || e.toString().contains("Logs have not been updated")){
                throw new SkipException("Log page issue"+WebDriverUtils.getUrlAndLogs());
            }else{
                throw new RuntimeException(e.getMessage());
            }
        }
	}

    /*#17. IMS Player Details Page*/
	@Test(groups = {"regression"})
	public void verifyRegistrationDataIsShownCorrectlyInIMS(){
		UserData userData=defaultUserData.getRandomUserData();
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.registerUser(userData);
		boolean registrationValuesAreCorrect=iMS.validateRegisterData(userData);
		Assert.assertTrue(registrationValuesAreCorrect);
	}

    /*#19. All required fields are marked with asterisks*/
	@Test(groups = {"regression"})
	public void requiredFieldsLabelsMarkedWithStar(){
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		boolean checkboxTermsAndConditionsValidationErrorVisible=registrationPage.labelsRequiredMarkingCorrect();
		Assert.assertTrue(checkboxTermsAndConditionsValidationErrorVisible);
	}

    /*#20. By default T&C check box is unchecked while "I'd like to receive bonuses" is checked*/
	@Test(groups = {"regression"})
	public void defaultCheckboxesState(){
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		boolean receiveBonusesChecked=registrationPage.getCheckboxStateReceiveBonuses();
		boolean termsAndConditionsChecked=registrationPage.getCheckboxStateTermsAndConditions();
		Assert.assertTrue((receiveBonusesChecked == true) && (termsAndConditionsChecked == false));
	}

    /*#22. Country - country phone prefix mapping*/
	@Test(groups = {"regression", "code"})
	public void countryPhonePrefix(){
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
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
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		String defaultCountryName = registrationPage.getCountry();
		boolean defaultCountrySelected = defaultCountryName.equals(defaults.getDefaultCountry());
		boolean fillFieldsVisible = registrationPage.getFillFieldsButtonVisibleState();
		Assert.assertTrue(defaultCountrySelected == true && fillFieldsVisible == true);
	}

    /*#24. Change country from UK to any and selecting UK again Fill all fields element is displayed*/
	@Test(groups = {"regression"})
	public void fillFieldsAppearDisappear(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
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
        try {
            RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
            List<String> fullAddressActual = registrationPage.fillAutoByPostCode("SE17RL");
            List<String> fullAddressExpected = Arrays.asList("London", "35-41 Lower Marsh", "", "35-41");
            boolean autofilledFieldsAreEditable = registrationPage.autofilledFieldsAreEditable();
            if (fullAddressActual.equals(fullAddressExpected) == false || autofilledFieldsAreEditable == false) {
                WebDriverUtils.runtimeExceptionWithLogs("<div>Actual: " + fullAddressActual + " </div><div>Expected: " + fullAddressExpected + "</div><div>Editable: " + autofilledFieldsAreEditable + " | Expected: True</div>");
            }
        }catch (RuntimeException e){
            if(e.getMessage().contains("35-41/")){
                throw new SkipException("Skipped untill D-10144 is fixed"+WebDriverUtils.getUrlAndLogs());
            }else{
                WebDriverUtils.runtimeExceptionWithLogs(e.getMessage());
            }
        }
	}

    @Test(groups = {"regression", "affiliate"})
	public void affiliateSupportCookieAll(){
        String advertiser="advert1";
        String banner="v2";
        String profile="v3";
        String url="v4";
        String customTitle="BTAG";
        String customValue="12333";
        UserData userData = defaultUserData.getRandomUserData();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        WebDriverUtils.clearCookies();
        WebDriverUtils.addCookie("banner_domainclick", advertiser+","+banner+","+profile+","+url+","+customTitle+":"+customValue, WebDriverObject.getBaseUrl().replace("http:", "").replace("/", ""),"/", new Date(115,1,1));
        WebDriverUtils.refreshPage();
        registrationPage.registerUser(userData);
        iMS.validateAffiliate(userData.getUsername(), advertiser, banner, profile, url, customTitle, customValue);
	}

    @Test(groups = {"regression", "affiliate"})
    public void affiliateSupportCookieFirst(){
        String advertiser="advert1";
        UserData userData = defaultUserData.getRandomUserData();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        WebDriverUtils.clearCookies();
        WebDriverUtils.addCookie("banner_domainclick", advertiser+",,,,", WebDriverObject.getBaseUrl().replace("http:", "").replace("/", ""),"/", new Date(115,1,1));
        WebDriverUtils.refreshPage();
        registrationPage.registerUser(userData);
        iMS.validateAffiliate(userData.getUsername(), advertiser);
    }

    @Test(groups = {"regression", "affiliate"})
    public void affiliateSupportURL(){
        String advertiser="advert1";
        String banner="v2";
        String profile="v3";
        String url="v4";
        String customTitle="BTAG";
        String customValue="12333";
        UserData userData = defaultUserData.getRandomUserData();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        WebDriverUtils.clearCookies();
        WebDriverUtils.navigateToInternalURL("register?advertiser="+advertiser+"&profileid="+profile+"&bannerid="+banner+"&refererurl="+url+"&creferer="+customTitle+":"+customValue);
        registrationPage.registerUser(userData);
        iMS.validateAffiliate(userData.getUsername(), advertiser, banner, profile, url, customTitle, customValue);
    }

    /*NEGATIVE*/

    /*#1. Try to use already existing username*/
	@Test(groups = {"regression"})
	public void alreadyUsedUsername(){
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
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
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		UserData generatedUserData=defaultUserData.getRandomUserData();
		generatedUserData.setEmail("playtech@spamavert.com");
		registrationPage=(RegistrationPage) registrationPage.registerUser(generatedUserData, Page.registrationPage);
		String errorMessageText=registrationPage.getErrorMessageText();
        if(errorMessageText.contains("Timeout occurred")){
            throw new SkipException("IMS timeout"+WebDriverUtils.getUrlAndLogs());
        }
		boolean emailUsedMessageDisplayed=errorMessageText.equals("The specified email address is already in use.");

		Assert.assertTrue(emailUsedMessageDisplayed);
	}

    /*3. Try to use invalid bonus code*/
	@Test(groups = {"regression"})
	public void registrationWithInvalidBonusCoupon() {
		String invalidBonusCoupon = searchValidationRule.generateValidString();
		UserData userData = defaultUserData.getRandomUserData();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage = (RegistrationPage) registrationPage.registerUser(userData, invalidBonusCoupon);
		boolean bonusErrorPresent = registrationPage.isBonusCodeErrorPresent();
		Assert.assertTrue(bonusErrorPresent);
	}

    /*#4. Email and email confirmation do not match*/
	@Test(groups = {"regression"})
	public void emailDoNotMatch(){
		UserData generatedUserData=defaultUserData.getRandomUserData();
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
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
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
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
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage=(RegistrationPage) registrationPage.registerUser(userData, false, false, false);
		boolean checkboxTermsAndConditionsValidationErrorVisible=registrationPage.checkboxTermsAndConditionsValidationErrorVisible();
		Assert.assertTrue(checkboxTermsAndConditionsValidationErrorVisible);
	}

    /*VALIDATION*/
	@Test(groups = {"validation"})
	public void validationRequiredFieldsEmpty(){
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.submit();
		int validationErrorsCount=WebDriverUtils.getXpathCount(RegistrationPage.VALIDATION_ERROR_XP);
		Assert.assertTrue(validationErrorsCount >= 17);
	}

	@Test(groups = {"validation"})
	public void firstnameFieldValidation() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validateFirstname(nameValidationRule);
	}

	@Test(groups = {"validation"})
	public void lastnameFieldValidation() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validateLastname(nameValidationRule);
	}

	@Test(groups = {"validation"})
	public void emailFieldValidation() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validateEmail(emailValidationRule);
	}

	@Test(groups = {"validation"})
	public void verificationEmailFieldValidation() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validateVerificationEmail(emailValidationRule);
	}

	@Test(groups = {"validation"})
	public void cityFieldValidation() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validateCity(cityValidationRule);
	}

	@Test(groups = {"validation"})
	public void address1FieldValidation() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validateAddress1(addressValidationRule);
	}

	@Test(groups = {"validation"})
	public void address2FieldValidation() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validateAddress2(addressValidationRule);
	}

	@Test(groups = {"validation"})
	public void houseFieldValidation() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validateHouse(houseValidationRule);
	}

	@Test(groups = {"validation"})
	public void postcodeFieldValidation() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validatePostcode(postcodeValidationRule);
	}

	@Test(groups = {"validation"})
	public void phoneFieldValidation() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validatePhone(phoneValidationRule);
	}

	@Test(groups = {"validation"})
	public void phoneCountryCodeFieldValidation() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validatePhoneCountryCode(countryPhoneCodeValidationRule);
	}

	@Test(groups = {"validation"})
	public void mobileFieldValidation() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validateMobile(phoneValidationRule);
	}

	@Test(groups = {"validation"})
	public void mobileCountryCodeFieldValidation() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validateMobileCountryCode(countryPhoneCodeValidationRule);
	}

	@Test(groups = {"validation"})
	public void usernameFieldValidation() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validateUsername(usernameValidationRule);
	}

	@Test(groups = {"validation"})
	public void passwordFieldValidation() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validatePassword(passwordValidationRule);
	}

	@Test(groups = {"validation"})
	public void verificationPasswordFieldValidation() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validateVerificationPassword(passwordValidationRule);
	}

	@Test(groups = {"validation"})
	public void answerFieldValidation() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validateAnswer(answerValidationRule);
	}

	@Test(groups = {"validation"})
	public void bonusCodeFieldValidation() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validateBonusCode(bonusCodeValidationRule);
	}
}
