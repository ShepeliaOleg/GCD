import enums.ConfiguredPages;
import enums.Page;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.registration.AdultContentPopup;
import pageObjects.registration.AfterRegistrationPopup;
import pageObjects.registration.RegistrationPage;
import pageObjects.registration.classic.RegistrationPageAllSteps;
import springConstructors.Defaults;
import springConstructors.IMS;
import springConstructors.UserData;
import springConstructors.ValidationRule;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.TypeUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;
import utils.core.WebDriverObject;
import utils.validation.ValidationUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


public class RegistrationTest extends AbstractTest{

	@Autowired
	@Qualifier("userData")
	private UserData defaultUserData;

    @Autowired
    @Qualifier("genderValidationRule")
    private ValidationRule genderValidationRule;
	
	@Autowired
	@Qualifier("firstNameValidationRule")
	private ValidationRule firstNameValidationRule;

    @Autowired
    @Qualifier("lastNameValidationRule")
    private ValidationRule lastNameValidationRule;

    @Autowired
    @Qualifier("dateOfBirthValidationRule")
    private ValidationRule dateOfBirthValidationRule;

	@Autowired
	@Qualifier("emailValidationRule")
	private ValidationRule emailValidationRule;

    @Autowired
    @Qualifier("stateValidationRule")
    private ValidationRule stateValidationRule;

    @Autowired
    @Qualifier("countryValidationRule")
    private ValidationRule countryValidationRule;

	@Autowired
	@Qualifier("cityValidationRule")
	private ValidationRule cityValidationRule;

	@Autowired
	@Qualifier("addressValidationRule")
	private ValidationRule addressValidationRule;

    @Autowired
    @Qualifier("address2ValidationRule")
    private ValidationRule address2ValidationRule;

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
    @Qualifier("usernameValidationRule")
    private ValidationRule usernameValidationRule;

    @Autowired
    @Qualifier("passwordValidationRule")
    private ValidationRule passwordValidationRule;

    @Autowired
    @Qualifier("answerValidationRule")
    private ValidationRule answerValidationRule;

    @Autowired
    @Qualifier("currencyValidationRule")
    private ValidationRule currencyValidationRule;

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
	@Test(groups = {"registration","smoke"})
	public void validUserRegistration() {
        HomePage homePage = PortalUtils.registerUser(defaultUserData.getRandomUserData());
        TypeUtils.assertTrueWithLogs(homePage.isLoggedIn());
	}

	/*#2. Registration with receive bonuses check box checked*/
	@Test(groups = {"registration","regression"})
	public void receiveBonusesIsCheckedInIMS(){
		UserData userData=defaultUserData.getRandomUserData();
		PortalUtils.registerUser(userData,true);
		boolean receiveBonusesCheckedInIMS=iMS.navigateToPlayedDetails(userData.getUsername()).getAllChannelsCheckboxState();
		TypeUtils.assertTrueWithLogs(receiveBonusesCheckedInIMS);
	}

    /*#3. Registration with receive bonuses check box unchecked*/
	@Test(groups = {"registration","regression"})
	public void receiveBonusesIsNotCheckedInIMS(){
		UserData userData=defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData,false);
		boolean receiveBonusesNotCheckedInIMS=iMS.navigateToPlayedDetails(userData.getUsername()).getAllChannelsCheckboxState();
        TypeUtils.assertTrueWithLogs(!receiveBonusesNotCheckedInIMS);
	}

    /*#4. Registration with bonus code provided*/
	@Test(groups = {"registration","regression"})
	public void registrationWithBonusCoupon(){
        UserData userData=defaultUserData.getRandomUserData();
        HomePage homePage = (HomePage) PortalUtils.registerUser(userData,false,true);
		boolean bonusesPresent=homePage.getBalance().equals("£10.00") || homePage.getBalance().equals("£ 10.00");
        TypeUtils.assertTrueWithLogs(bonusesPresent);
	}

    /*#5. Registration without bonus code provided*/
    @Test(groups = {"registration","regression"})
	public void registrationWithOutBonusCoupon(){
        UserData userData=defaultUserData.getRandomUserData();
        HomePage homePage = (HomePage) PortalUtils.registerUser(userData,false,false);
		boolean bonusesNotPresent=homePage.getBalance().equals("£0.00") || homePage.getBalance().equals("£ 0.00");
        TypeUtils.assertTrueWithLogs(bonusesNotPresent);
	}

    /*#6. Player is registered with currency selected*/
	@Test(groups = {"registration","regression","desktop"})
	public void validHeaderUnitBalance(){
        UserData userData=defaultUserData.getRandomUserData();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        String defaultCurrencyInRegistrationForm = registrationPage.getSelectedCurrency();
        HomePage homePage=(HomePage)registrationPage.registerUser(userData, true, false, null, Page.homePage);
		String currencyInHeader=homePage.getBalance();
        TypeUtils.assertTrueWithLogs(defaultCurrencyInRegistrationForm.contains("GBP"));
        TypeUtils.assertTrueWithLogs(currencyInHeader.contains("£"));
	}

    /*#7. After registration web content*/
	@Test(groups = {"registration","regression"})
	public void afterRegistrationWebContent(){
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		UserData userData=defaultUserData.getRandomUserData();
		AfterRegistrationPopup afterRegistrationPopup=(AfterRegistrationPopup) registrationPage.registerUser(userData, Page.afterRegistrationPopup);
	}

    /*#10. After-registration redirect*/
	@Test(groups = {"registration","regression"})
	public void afterRegistrationRedirect(){
		UserData userData=defaultUserData.getRandomUserData();
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        HomePage homePage=(HomePage)registrationPage.registerUser(userData);
		String actualUrl=WebDriverUtils.getCurrentUrl();
        TypeUtils.assertTrueWithLogs(actualUrl.endsWith("/admin"));
	}

    /*#11. The list of supported countries is correct*/
	@Test(groups = {"registration","regression"})
	public void countryList(){
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		Collection<String> actualCountriesCodesList = registrationPage.getCountriesCodesList(defaultUserData.getRandomUserData());
		Collection<String> diff=TypeUtils.getDiffElementsFromLists(actualCountriesCodesList, defaults.getCountryCodesList());
        TypeUtils.assertTrueWithLogs(diff.isEmpty(), diff.toString());
	}

    /*#??. The list of supported nationalities is correct*/
    @Test(groups = {"registration","spanish", "desktop"})
    public void nationalityList(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        Collection<String> actualNationalitiesCodesList = registrationPage.registrationPageAllSteps().getNationalitiesCodesList();
        Collection<String> diff=TypeUtils.getDiffElementsFromLists(actualNationalitiesCodesList, defaults.getCountryCodesList());
        TypeUtils.assertTrueWithLogs(diff.isEmpty());
    }

    /*#12. The list of supported currencies is correct*/
	@Test(groups = {"registration","regression"})
	public void currencyList(){
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		Collection<String> actualCurrencyList=registrationPage.getCurrencyList(defaultUserData.getRandomUserData());
		Collection<String> diff= TypeUtils.getDiffElementsFromLists(actualCurrencyList, defaults.getCurrencyList());
        TypeUtils.assertTrueWithLogs(diff.isEmpty(), diff.toString());
	}

//    /*#13. T&C web content is shown when clicking on T&C link*/
//	@Test(groups = {"regression"})
//	public void openTermsAndConditionsPopup(){
//		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
//		ReadTermsAndConditionsPopup readTermsAndConditionsPopup=registrationPage.navigateToTermsAndConditions();
//	}
//
    /*#14. 18+ web content is shown when clicking on 18+ link*/
	@Test(groups = {"registration","regression", "mobile"})
	public void adultContentIsShown() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        AdultContentPopup adultContentPopup = registrationPage.registrationPageStepOne().clickAdultContent();
	}

//	/*#15. Logs registration*/
//	@Test(groups = {"regression", "logs"})
//	public void checkLogParametersRegistration(){
//        try{
//            LogCategory[] logCategories = new LogCategory[]{LogCategory.SetPlayerInfoRequest, LogCategory.SetPlayerInfoResponse};
//            UserData userData=defaultUserData.getRandomUserData();
//            String[] parameters = {"objectIdentity="+userData.getUsername()+"-playtech81001",
//                    "KV(1, playtech81001)",
//                    "KV(2, "+userData.getUsername()+")",
//                    "KV(7, "+userData.getCity()+")",
//                    "KV(19, "+userData.getEmail()+")",
//                    "KV(21, "+userData.getFirstName()+")",
//                    "KV(24, "+userData.getLastName()+")",
//                    "KV(27, "+userData.getPhoneAreaCode()+userData.getPhone()+")",
//                    "KV(34, "+userData.getPostCode()+")"};
//            RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
//            registrationPage.registerUser(userData);
//            Log log = LogUtils.getCurrentLogs(logCategories);
//            log.doResponsesContainErrors();
//            LogEntry regReq = log.getEntry(LogCategory.SetPlayerInfoRequest);
//            regReq.containsParameters(parameters);
//        }catch (RuntimeException e){
//            if(e.getMessage().contains("Not all registration logs appeared") || e.toString().contains("Logs have not been updated")){
//                throw new SkipException("Log page issue"+WebDriverUtils.getUrlAndLogs());
//            }else{
//                throw new RuntimeException(e.getMessage());
//            }
//        }
//	}
//
//	/*#16. Logs username check*/
//	@Test(groups = {"regression", "logs"})
//	public void checkLogParametersUsername(){
//        try{
//            LogCategory[] logCategories = new LogCategory[]{LogCategory.CheckUsernameRequest, LogCategory.CheckUsernameResponse};
//            UserData userData=defaultUserData.getRandomUserData();
//            String[] parameters = {"objectIdentity=playtech81001",
//                    "username="+userData.getUsername(),
//                    "firstName="+userData.getFirstName(),
//                    "lastName="+userData.getLastName(),
//                    "email="+userData.getEmail()};
//            RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
//            registrationPage.registerUser(userData);
//            Log log = LogUtils.getCurrentLogs(logCategories);
//            log.doResponsesContainErrors();
//            LogEntry usrReq = log.getEntry(LogCategory.CheckUsernameRequest);
//            usrReq.containsParameters(parameters);
//        }catch (RuntimeException e){
//            if(e.getMessage().contains("Not all registration logs appeared") || e.toString().contains("Logs have not been updated")){
//                throw new SkipException("Log page issue"+WebDriverUtils.getUrlAndLogs());
//            }else{
//                throw new RuntimeException(e.getMessage());
//            }
//        }
//	}
//
    /*#17. IMS Player Details Page*/
	@Test(groups = {"registration","regression"})
	public void verifyRegistrationDataIsShownCorrectlyInIMS(){
		UserData userData=defaultUserData.getRandomUserData();
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.registerUser(userData);
		boolean registrationValuesAreCorrect=iMS.validateRegisterData(userData);
        TypeUtils.assertTrueWithLogs(registrationValuesAreCorrect);
	}

    /*#19. All required fields are marked with asterisks*/
	@Test(groups = {"registration","regression", "desktop"})
	public void requiredFieldsLabelsMarkedWithStar(){
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		boolean checkboxTermsAndConditionsregressionErrorVisible=registrationPage.registrationPageAllSteps().labelsRequiredMarkingCorrect();
        TypeUtils.assertTrueWithLogs(checkboxTermsAndConditionsregressionErrorVisible);
	}

    /*#20. By default T&C check box is unchecked while "I'd like to receive bonuses" is checked*/
	@Test(groups = {"registration","regression", "desktop"})
	public void defaultCheckboxesState(){
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		boolean receiveBonusesChecked=registrationPage.getCheckboxStateReceiveBonuses(defaultUserData.getRandomUserData());
//		boolean termsAndConditionsChecked=registrationPage.getCheckboxStateTermsAndConditions();
        TypeUtils.assertTrueWithLogs(receiveBonusesChecked == true); // && (termsAndConditionsChecked == false));
	}

    /*#22. Country - countryCode code and phone prefix mapping*/
	@Test(groups = {"registration","regression", "desktop"})
	public void countryCodePhoneCodeMappingDesktop(){
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        RegistrationPageAllSteps registrationPageAllSteps = registrationPage.registrationPageAllSteps();
		Collection<String> countriesCodesList = defaults.getCountryCodesList();
		for (String countryCode : countriesCodesList) {
            registrationPageAllSteps.fillCountry(countryCode);
			String phoneAreaCode = registrationPageAllSteps.getPhoneAreaCode();
			String expectedAreaCode = defaults.getPhoneCodeByCountryCode(countryCode);
            TypeUtils.assertEqualsWithLogs(phoneAreaCode, "+" + expectedAreaCode, "Phone area code \"" + phoneAreaCode + "\" is not equal to expected area code \"" + expectedAreaCode + "\" on selecting \'" + countryCode + "\" country code.");
		}
	}

    /*#??. Country - countryCode code and name mapping*/
    @Test(groups = {"registration","regression", "desktop"})
    public void countryCodeNamePrefix(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        Collection<String> countriesCodesList = defaults.getCountryCodesList();
        for (String countryCode : countriesCodesList) {
            registrationPage.fillCountry(countryCode);
            String actualCountryName = registrationPage.getSelectedCountryName().trim();
            String expectedCountryName = defaults.getCountryNameByCountryCode(countryCode);
            TypeUtils.assertEqualsWithLogs(actualCountryName, expectedCountryName, "Country name (" + actualCountryName + ") is not equal to expected country name (" + expectedCountryName + ") on selecting '" + countryCode + "' country code.");

        }
    }

    /*#??. Country - countryCode code and name mapping*/
    @Test(groups = {"registration","regression", "mobile"})
    public void countryCodeNamePrefixMobile(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        Collection<String> countriesCodesList = defaults.getCountryCodesList();
        registrationPage.registrationPageStepTwo(defaultUserData.getRandomUserData());
        for (String countryCode : countriesCodesList) {
            registrationPage.fillCountry(countryCode);
            String actualCountryName = registrationPage.getSelectedCountryName().trim();
            String expectedCountryName = defaults.getCountryNameByCountryCode(countryCode);
            TypeUtils.assertEqualsWithLogs(actualCountryName, expectedCountryName, "Country name (" + actualCountryName + ") is not equal to expected country name (" + expectedCountryName + ") on selecting '" + countryCode + "' country code.");

        }
    }

    /*#23. Default selected countryCode*/
	@Test(groups = {"registration","regression", "desktop"})
	public void defaultCountry(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		String selectedCountryName = registrationPage.getSelectedCountryName();
        TypeUtils.assertTrueWithLogs(selectedCountryName.equals(defaults.getDefaultCountryName()), "Selected country by default is \"" + selectedCountryName + "\" but we expect \"" + defaults.getDefaultCountryName() + "\"");
		// TypeUtils.assertTrueWithLogs(registrationPage.isFindMyAddressButtonVisible());
	}

    /*#23. Default selected countryCode*/
    @Test(groups = {"registration","regression", "mobile"})
    public void defaultCountryMobile(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.registrationPageStepTwo(defaultUserData.getRandomUserData());
        String selectedCountryName = registrationPage.getSelectedCountryName();
        TypeUtils.assertTrueWithLogs(selectedCountryName.equals(defaults.getDefaultCountryName()), "Selected country by default is \"" + selectedCountryName + "\" but we expect \"" + defaults.getDefaultCountryName() + "\"");
        // TypeUtils.assertTrueWithLogs(registrationPage.isFindMyAddressButtonVisible());
    }


//    /*#24. Change countryCode from UK to any and selecting UK again Fill all fields element is displayed*/
//	@Test(groups = {"regression"})
//	public void fillFieldsAppearDisappear(){
//        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
//		// stage 1
//		boolean fillFieldsVisibleStage1 = registrationPage.isFindMyAddressButtonVisible();
//		String randomCountryCode;
//		String defaultCountryCode = defaults.getDefaultCountry();
//		do {
//			randomCountryCode = defaults.getRandomCountryCode();
//		}
//		while (randomCountryCode.equals(defaultCountryCode));
//		// stage 2
//		registrationPage.fillCountry(randomCountryCode);
//		boolean fillFieldsVisibleStage2 = registrationPage.isFindMyAddressButtonVisible();
//		// stage 3
//		registrationPage.fillCountry(defaultCountryCode);
//		boolean fillFieldsVisibleStage3 = registrationPage.isFindMyAddressButtonVisible();
//        TypeUtils.assertTrueWithLogs(fillFieldsVisibleStage1);
//        TypeUtils.assertFalse(fillFieldsVisibleStage2);
//        TypeUtils.assertTrueWithLogs(fillFieldsVisibleStage3);
//	}
//
//    /*#25. Enter zipcode - City/Address1/Address2/House number updated on Fill all fields click and are editable*/
//	@Test(groups = {"regression"})
//	public void zipCodeFillsAddress(){
//        try {
//            RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
//            List<String> fullAddressActual = registrationPage.fillAutoByPostCode("SE17RL");
//            List<String> fullAddressExpected = Arrays.asList("London", "35-41 Lower Marsh", "", "35-41");
//            boolean autofilledFieldsAreEditable = registrationPage.autofilledFieldsAreEditable();
//            if (fullAddressActual.equals(fullAddressExpected) == false || autofilledFieldsAreEditable == false) {
//                WebDriverUtils.runtimeExceptionWithUrl("<div>Actual: " + fullAddressActual + " </div><div>Expected: " + fullAddressExpected + "</div><div>Editable: " + autofilledFieldsAreEditable + " | Expected: True</div>");
//            }
//        }catch (RuntimeException e){
//            if(e.getMessage().contains("35-41/")){
//                throw new SkipException("Skipped until D-10144 is fixed"+WebDriverUtils.getUrlAndLogs());
//            }else{
//                WebDriverUtils.runtimeExceptionWithUrl(e.getMessage());
//            }
//        }
//	}
//
    @Test(groups = {"registration","regression", "affiliate"})
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
        //registrationPage = new RegistrationPage();
        registrationPage.registerUser(userData);
        iMS.validateAffiliate(userData.getUsername(), advertiser, banner, profile, url, customTitle, customValue);
	}

    @Test(groups = {"registration","regression", "affiliate"})
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

    @Test(groups = {"registration","regression", "affiliate"})
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

    /*#??. Suggestion appeared on entering already registered username*/
    @Test(groups = {"registration","regression"})
    public void usernameSuggestion(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        String username=defaultUserData.getRegisteredUserData().getUsername();
        String usernameSuggestionMessage = registrationPage.getUsernameSuggestion(username, defaultUserData.getRandomUserData());
        TypeUtils.assertTrueWithLogs(usernameSuggestionMessage.startsWith("This username is already in use. Suggested username is:"), "Username suggestion message doesn't contain preamble: " + usernameSuggestionMessage);
        TypeUtils.assertTrueWithLogs(usernameSuggestionMessage.contains(username.toUpperCase()), "Username suggestion message doesn't contain '" + username + "': '" + usernameSuggestionMessage + "'");
    }

    //    /*NEGATIVE*/

    /*#1. Try to use already existing username*/
	@Test(groups = {"registration","regression"})
	public void alreadyUsedUsername(){
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		UserData generatedUserData=defaultUserData.getRandomUserData();
		UserData registeredUserData=defaultUserData.getRegisteredUserData();
		generatedUserData.setUsername(registeredUserData.getUsername());
		registrationPage=(RegistrationPage) registrationPage.registerUser(generatedUserData, Page.registrationPage);
        String actualregressionStatus = ValidationUtils.validationStatusIs(RegistrationPage.FIELD_USERNAME_NAME, ValidationUtils.STATUS_FAILED, registeredUserData.getUsername());
        TypeUtils.assertTrueWithLogs(actualregressionStatus.equals(ValidationUtils.PASSED), actualregressionStatus);
	}

    /*3. Try to use invalid bonus code*/
	@Test(groups = {"registration","regression"})
	public void registrationWithInvalidBonusCoupon() {
		String invalidBonusCoupon = bonusCodeValidationRule.generateValidString();
		UserData userData = defaultUserData.getRandomUserData();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        HomePage homePage= (HomePage) registrationPage.registerUser(userData, "invalid", Page.homePage);
		//boolean bonusErrorPresent = registrationPage.isBonusCodeErrorPresent();
        //TypeUtils.assertTrueWithLogs(bonusErrorPresent);
        boolean noBonus = homePage.getBalance().equals("£0.00") || homePage.getBalance().equals("£ 0.00");
        TypeUtils.assertTrueWithLogs(noBonus);
	}

//    /*#6. Try to clickLogin registration form without accepting T&C (without checking check box)*/
//	@Test(groups = {"regression"})
//	public void submitWithUncheckedTermsAndConditions(){
//		UserData userData=defaultUserData.getRandomUserData();
//		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
//		registrationPage=(RegistrationPage) registrationPage.registerUser(userData, false, false, false);
//		boolean checkboxTermsAndConditionsregressionErrorVisible=registrationPage.checkboxTermsAndConditionsregressionErrorVisible();
//        TypeUtils.assertTrueWithLogs(checkboxTermsAndConditionsregressionErrorVisible);
//	}

    /*regression*/
    @Test(groups = {"registration","regression","desktop"})
    public void genderDropdownregression() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.validateGender(genderValidationRule);
    }

	@Test(groups = {"registration","regression","desktop"})
	public void firstnameFieldregression() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validateFirstname(firstNameValidationRule);
	}

    @Test(groups = {"registration","regression","desktop"})
    public void lastnameFieldregression() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.validateLastname(lastNameValidationRule);
    }

    @Test(groups = {"registration","regression","desktop"})
    public void dateOfBirthregression() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.validateDateOfBirth(dateOfBirthValidationRule);
    }

	@Test(groups = {"registration","regression","desktop"})
	public void emailFieldregression() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validateEmail(emailValidationRule);
	}

    /*#4. Email and email confirmation do not match*/
    @Test(groups = {"registration","regression", "desktop"})
    public void emailConfirmationregression(){
        String message="";
        String xpath = RegistrationPageAllSteps.FIELD_EMAIL_VERIFICATION_XP;
        String id = RegistrationPageAllSteps.FIELD_EMAIL_VERIFICATION_NAME;
        ArrayList<String> results = new ArrayList<>();
        UserData generatedUserData=defaultUserData.getRandomUserData();
        String email = generatedUserData.getEmail();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.registrationPageAllSteps().clickEmailConfirmation();
        results.add(ValidationUtils.validationStatusIs(id, ValidationUtils.STATUS_NONE, ""));
        String tooltip = "Please retype your email.";
        results.add(ValidationUtils.tooltipStatusIs(id, ValidationUtils.STATUS_PASSED, ""));
        results.add(ValidationUtils.tooltipTextIs(id, tooltip, ""));
        registrationPage.fillEmail(email);
        ValidationUtils.inputFieldAndRefocus(xpath, email);
        results = ValidationUtils.validateStatusAndToolTips(results, ValidationUtils.STATUS_NONE, xpath, id, email, ValidationUtils.STATUS_PASSED);
        for(String result:results){
            if(!result.equals(ValidationUtils.PASSED)){
                message += "<div>" + result + "</div>";
            }
        }
        if(!message.isEmpty()){
            WebDriverUtils.runtimeExceptionWithUrl(message);
        }
    }

    @Test(groups = {"registration","regression", "desktop"})
    public void emailDoNotMatch(){
        UserData generatedUserData=defaultUserData.getRandomUserData();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.fillEmail(generatedUserData.getEmail());
        registrationPage.registrationPageAllSteps().fillEmailVerification(emailValidationRule.generateValidString());
        String tooltipMessageText=ValidationUtils.getTooltipText(RegistrationPageAllSteps.FIELD_EMAIL_VERIFICATION_NAME);
        boolean usernameUsedMessageDisplayed=tooltipMessageText.equals("Emails don't match");
        TypeUtils.assertTrueWithLogs(usernameUsedMessageDisplayed, "Expected 'Emails don't match', Actual " + "'"+tooltipMessageText+"'");
    }

    @Test(groups = {"registration","regression", "desktop"})
    public void stateFieldregression() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.registrationPageAllSteps().validateState(stateValidationRule);
    }

    @Test(groups = {"registration","regression","desktop"})
    public void countryDropdownregression() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.validateCountry(countryValidationRule,defaultUserData.getRandomUserData());
    }

	@Test(groups = {"registration","regression","desktop"})
	public void cityFieldregression() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validateCity(cityValidationRule,defaultUserData.getRandomUserData());
	}

	@Test(groups = {"registration","regression","desktop"})
     public void address1Fieldregression() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.validateAddress(addressValidationRule,defaultUserData.getRandomUserData());
    }

    @Test(groups = {"registration","regression", "desktop"})
    public void address2Fieldregression() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.registrationPageAllSteps().validateAddress2(address2ValidationRule);
    }

    @Test(groups = {"registration","regression", "desktop"})
    public void houseFieldregression() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.registrationPageAllSteps().validateHouse(houseValidationRule);
    }

	@Test(groups = {"registration","regression","desktop"})
	public void postcodeFieldregression() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validatePostcode(postcodeValidationRule,defaultUserData.getRandomUserData());
	}

    @Test(groups = {"registration","regression", "desktop"})
    public void phoneCountryCodeFieldregression() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.registrationPageAllSteps().validatePhoneCountryCode(countryPhoneCodeValidationRule);
    }

	@Test(groups = {"registration","regression", "desktop"})
	public void phoneFieldregression() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.registrationPageAllSteps().validatePhone(phoneValidationRule);
	}

	@Test(groups = {"registration","regression","desktop"})
	public void usernameFieldregression() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validateUsername(usernameValidationRule,defaultUserData.getRandomUserData());
	}

	@Test(groups = {"registration","regression","desktop"})
	public void passwordFieldregression() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validatePassword(passwordValidationRule,defaultUserData.getRandomUserData());
	}

    @Test(groups = {"registration","regression", "desktop"})
    public void passwordConfirmationregression(){
        String message="";
        String xpath = RegistrationPage.FIELD_PASSWORD_VERIFICATION_XP;
        String id = RegistrationPage.FIELD_PASSWORD_VERIFICATION_NAME;
        ArrayList<String> results = new ArrayList<>();
        UserData generatedUserData=defaultUserData.getRandomUserData();
        String password = generatedUserData.getPassword();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.clickPasswordConfirmation();
        results.add(ValidationUtils.validationStatusIs(id, ValidationUtils.STATUS_NONE, ""));
        String tooltip = "Please reytpe your password.";
        results.add(ValidationUtils.tooltipStatusIs(id, ValidationUtils.STATUS_PASSED, ""));
        results.add(ValidationUtils.tooltipTextIs(id, tooltip, ""));
        registrationPage.fillPassword(password);
        ValidationUtils.inputFieldAndRefocus(xpath, password);
        results = ValidationUtils.validateStatusAndToolTips(results, ValidationUtils.STATUS_NONE, xpath, id, password, ValidationUtils.STATUS_PASSED);
        for(String result:results){
            if(!result.equals(ValidationUtils.PASSED)){
                message += "<div>" + result + "</div>";
            }
        }
        if(!message.isEmpty()){
            WebDriverUtils.runtimeExceptionWithUrl(message);
        }
    }

//    @Test(groups = {"registration","regression", "mobile"})
//    public void passwordConfirmationregressionMobile(){
//        String message="";
//        String xpath = RegistrationPage.FIELD_PASSWORD_VERIFICATION_XP;
//        String id = RegistrationPage.FIELD_PASSWORD_VERIFICATION_NAME;
//        ArrayList<String> results = new ArrayList<>();
//        UserData generatedUserData=defaultUserData.getRandomUserData();
//        String password = generatedUserData.getPassword();
//        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
//        registrationPage.registrationPageStepThree(generatedUserData);
//        registrationPage.clickPasswordConfirmation();
//        results.add(ValidationUtils.regressionStatusIs(xpath, ValidationUtils.STATUS_NONE, ""));
//        String tooltip = "Please reytpe your password.";
//        results.add(ValidationUtils.tooltipStatusIs(id, ValidationUtils.STATUS_PASSED, ""));
//        results.add(ValidationUtils.tooltipTextIs(id, tooltip, ""));
//        registrationPage.fillPassword(password);
//        ValidationUtils.inputFieldAndRefocus(xpath, password);
//        results = ValidationUtils.validateStatusAndToolTips(results, ValidationUtils.STATUS_NONE, xpath, id, password, ValidationUtils.STATUS_PASSED);
//        for(String result:results){
//            if(!result.equals(ValidationUtils.PASSED)){
//                message += "<div>" + result + "</div>";
//            }
//        }
//        if(!message.isEmpty()){
//            WebDriverUtils.runtimeExceptionWithUrl(message);
//        }
//    }

    /*#5. Password & Confirmation do not match*/
    @Test(groups = {"registration","regression", "desktop"})
    public void passwordDoNotMatch(){
        UserData generatedUserData=defaultUserData.getRandomUserData();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.fillPassword(generatedUserData.getPassword());
        registrationPage.fillPasswordVerification(passwordValidationRule.generateValidString());
        String errorMessageText=ValidationUtils.getTooltipText(RegistrationPage.FIELD_PASSWORD_VERIFICATION_NAME);
        boolean emailUsedMessageDisplayed=errorMessageText.equals("Sorry, Your passwords don't match");
        TypeUtils.assertTrueWithLogs(emailUsedMessageDisplayed, "Expected 'Sorry, Your passwords don't match', Actual " + "'"+errorMessageText+"'");
    }

//    /*#5. Password & Confirmation do not match*/
//    @Test(groups = {"registration","regression", "mobile"})
//    public void passwordDoNotMatchMobile(){
//        UserData generatedUserData=defaultUserData.getRandomUserData();
//        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
//        registrationPage.registrationPageStepThree(generatedUserData);
//        registrationPage.fillPassword(generatedUserData.getPassword());
//        registrationPage.fillPasswordVerification(passwordValidationRule.generateValidString());
//        String errorMessageText=ValidationUtils.getTooltipText(RegistrationPage.FIELD_PASSWORD_VERIFICATION_NAME);
//        boolean emailUsedMessageDisplayed=errorMessageText.equals("Sorry, Your passwords don't match");
//        TypeUtils.assertTrueWithLogs(emailUsedMessageDisplayed, "Expected 'Sorry, Your passwords don't match', Actual " + "'"+errorMessageText+"'");
//    }

    @Test(groups = {"registration","regression", "desktop"})
    public void answerFieldregression() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.registrationPageAllSteps().validateAnswer(answerValidationRule);
    }

    @Test(groups = {"registration","regression","desktop"})
    public void currencyDropdownregression() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.validateCurrency(currencyValidationRule,defaultUserData.getRandomUserData());
    }

	@Test(groups = {"registration","regression","desktop"})
	public void bonusCodeFieldregression() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validateBonusCode(bonusCodeValidationRule,defaultUserData.getRandomUserData());
	}
}
