import enums.ConfiguredPages;
import enums.Page;
import enums.PasswordStrength;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.registration.AdultContentPopup;
import pageObjects.registration.AfterRegistrationPopup;
import pageObjects.registration.RegistrationPage;
import pageObjects.registration.classic.RegistrationPageAllSteps;
import springConstructors.*;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.TypeUtils;
import utils.WebDriverUtils;
import utils.cookie.AffiliateCookie;
import utils.core.AbstractTest;
import utils.validation.ValidationUtils;

import java.util.ArrayList;
import java.util.Collection;


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
    @Qualifier("questionValidationRule")
    private ValidationRule questionValidationRule;

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

    @Autowired
    @Qualifier("affiliate")
    private AffiliateData affiliateData;
	
	/*POSITIVE*/
	
	/*1. Valid user registration*/
	@Test(groups = {"registration","smoke"})
	public void validUserRegistration() {
        HomePage homePage = PortalUtils.registerUser(defaultUserData.getRandomUserData());
        TypeUtils.assertTrueWithLogs(homePage.isLoggedIn());
	}

    /*#2. Registration with receive bonuses check box checked*/
    @Test(groups = {"registration","regression"})
    public void receivePromotionOffersDefaultState(){
        UserData userData=defaultUserData.getRandomUserData();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        TypeUtils.assertTrueWithLogs(registrationPage.getReceivePromotionsCheckboxState(userData), "Promotional checkbox is checked by default");
    }

    @Test(groups = {"registration","regression"})
    public void receivePromotionOffersText(){
        String expectedText = "I would like to receive great bonuses and exciting offers";
        UserData userData=defaultUserData.getRandomUserData();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        String actualText = registrationPage.getReceivePromotionsCheckboxText(userData);
        TypeUtils.assertTrueWithLogs(actualText.equals(expectedText), "Expected '"+expectedText+"', but it is '"+actualText+"'");
    }

	/*#2. Registration with receive bonuses check box checked*/
	@Test(groups = {"registration","regression"})
	public void receivePromotionOffersIsCheckedInIMS(){
		UserData userData=defaultUserData.getRandomUserData();
		PortalUtils.registerUser(userData,true);
		boolean receiveBonusesCheckedInIMS=iMS.navigateToPlayedDetails(userData.getUsername()).getAllChannelsCheckboxState();
		TypeUtils.assertTrueWithLogs(receiveBonusesCheckedInIMS, "Promotion checkboxes are checked");
	}

    /*#3. Registration with receive bonuses check box unchecked*/
	@Test(groups = {"registration","regression"})
	public void receivePromotionOffersIsNotCheckedInIMS(){
		UserData userData=defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData,false);
		boolean receiveBonusesNotCheckedInIMS=iMS.navigateToPlayedDetails(userData.getUsername()).getAllChannelsCheckboxState();
        TypeUtils.assertTrueWithLogs(!receiveBonusesNotCheckedInIMS, "Promotion checkboxes are not checked");
	}

    /*#4. Registration with bonus code provided*/
	@Test(groups = {"registration","regression"})
	public void registrationWithBonusCoupon(){
        UserData userData=defaultUserData.getRandomUserData();
        HomePage homePage = (HomePage) PortalUtils.registerUser(userData,true,true, "valid", Page.homePage);
		boolean bonusesPresent=homePage.getBalance().equals("£1.00") || homePage.getBalance().equals("£ 1.00");
        TypeUtils.assertTrueWithLogs(bonusesPresent);
	}

    /*#5. Registration without bonus code provided*/
    @Test(groups = {"registration","regression"})
	public void registrationWithOutBonusCoupon(){
        UserData userData=defaultUserData.getRandomUserData();
        RegistrationPage registrationPage = (RegistrationPage) PortalUtils.registerUser(userData,true,true, "invalid", Page.registrationPage);
        String message = registrationPage.getPortletErrorMessage();
        String expectedMessage = "Coupon code is not found or not available";
        TypeUtils.assertTrueWithLogs(message.equals(expectedMessage), "Expected '"+expectedMessage+"', found '"+message+"'");
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

    /*Client type poker*/
    @Test(groups = {"registration","regression"})
    public void registrationWithClientType(){
        UserData userData=defaultUserData.getRandomUserData();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.registerClientType);
        registrationPage.registerUser(userData);
        String expectedClientType = "poker";
        String clientType = iMS.getClientType(userData);
        TypeUtils.assertTrueWithLogs(clientType.equals(expectedClientType), "Expected '"+expectedClientType+"', actual '"+clientType+"'");
    }

    /*Client type empty*/
    @Test(groups = {"registration","regression"})
    public void registrationWithoutClientType(){
        UserData userData=defaultUserData.getRandomUserData();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.registerNoClientType);
        registrationPage.registerUser(userData);
        String expectedClientType = "casino";
        String clientType = iMS.getClientType(userData);
        TypeUtils.assertTrueWithLogs(clientType.equals(expectedClientType), "Expected '"+expectedClientType+"', actual '"+clientType+"'");

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
		boolean receiveBonusesChecked=registrationPage.getReceivePromotionsCheckboxState(defaultUserData.getRandomUserData());
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

    /* Password strength*/
    @Test(groups = {"registration", "regression", "desktop"})
    public void passwordStrength(){
        ArrayList<String> results = new ArrayList<>();
        String resultMessage = "";
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        results = RegistrationPage.verifyPasswordStrength(results, "aaaaaa", PasswordStrength.zero);
        results = RegistrationPage.verifyPasswordStrength(results, "AAAAAA", PasswordStrength.zero);
        results = RegistrationPage.verifyPasswordStrength(results, "000000", PasswordStrength.zero);
        results = RegistrationPage.verifyPasswordStrength(results, "0000aa", PasswordStrength.one);
        results = RegistrationPage.verifyPasswordStrength(results, "0000AA", PasswordStrength.one);
        results = RegistrationPage.verifyPasswordStrength(results, "00aaAA", PasswordStrength.two);
        results = RegistrationPage.verifyPasswordStrength(results, "@0aaAA", PasswordStrength.three);
        results = RegistrationPage.verifyPasswordStrength(results, "0000@@", PasswordStrength.two);
        results = RegistrationPage.verifyPasswordStrength(results, "aaaAAA", PasswordStrength.one);
        results = RegistrationPage.verifyPasswordStrength(results, "aaaaaaaa", PasswordStrength.one);
        results = RegistrationPage.verifyPasswordStrength(results, "AAAAAAAA", PasswordStrength.one);
        results = RegistrationPage.verifyPasswordStrength(results, "aaaaaaAA", PasswordStrength.two);
        results = RegistrationPage.verifyPasswordStrength(results, "aaaaaa00", PasswordStrength.two);
        results = RegistrationPage.verifyPasswordStrength(results, "000000@@", PasswordStrength.three);
        results = RegistrationPage.verifyPasswordStrength(results, "00aaaaAA", PasswordStrength.three);
        results = RegistrationPage.verifyPasswordStrength(results, "@@aaaaAA", PasswordStrength.three);
        results = RegistrationPage.verifyPasswordStrength(results, "@@00aaAA", PasswordStrength.four);
        results = RegistrationPage.verifyPasswordStrength(results, "aaaaaaaaaaaa", PasswordStrength.two);
        results = RegistrationPage.verifyPasswordStrength(results, "AAAAAAAAAAAA", PasswordStrength.two);
        results = RegistrationPage.verifyPasswordStrength(results, "00AAAAAAAAAA", PasswordStrength.three);
        results = RegistrationPage.verifyPasswordStrength(results, "00aaaaaaaaaa", PasswordStrength.three);
        results = RegistrationPage.verifyPasswordStrength(results, "aaaaaaaaaaAA", PasswordStrength.three);
        results = RegistrationPage.verifyPasswordStrength(results, "@@aaaaaaaaAA", PasswordStrength.four);
        results = RegistrationPage.verifyPasswordStrength(results, "00aaaaaaaaAA", PasswordStrength.four);
        results = RegistrationPage.verifyPasswordStrength(results, "00aaaaaa@@AA", PasswordStrength.five);
        for(String result:results){
            if(!result.equals("passed")){
                resultMessage += "<div>"+result+"</div>";
            }
        }
        if(!resultMessage.isEmpty()){
            WebDriverUtils.runtimeExceptionWithUrl(resultMessage);
        }
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
 /*B-10271*/
    /*1*/
@Test(groups = {"registration","regression", "affiliate"})
public void affiliateCookieSingleCreferrer(){
    UserData userData = defaultUserData.getRandomUserData();
    AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
    RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
    AffiliateCookie affiliateCookie = new AffiliateCookie(affiliateDataSingle);
    affiliateCookie.add();
    WebDriverUtils.refreshPage();
    registrationPage.registerUser(userData);
    iMS.validateAffiliate(userData.getUsernameUppercase(), affiliateDataSingle);
}

    /*2*/
    @Test(groups = {"registration","regression", "affiliate"})
    public void affiliateCookieAdvertisingNotExists(){
        UserData userData = defaultUserData.getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        affiliateDataSingle.setAdvertiser("notExists");
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        AffiliateCookie affiliateCookie = new AffiliateCookie(affiliateDataSingle);
        affiliateCookie.add();
        WebDriverUtils.refreshPage();
        registrationPage.registerUser(userData);
        affiliateDataSingle.setAdvertiser(affiliateDataSingle.getDefaultAdvertiser());
        iMS.validateAffiliate(userData.getUsernameUppercase(), affiliateDataSingle);
    }

    /*3*/
    @Test(groups = {"registration","regression", "affiliate"})
    public void affiliateCookieMultipleCreferrer(){
        UserData userData = defaultUserData.getRandomUserData();
        AffiliateData affiliateDataMultiple = affiliateData.getAffiliateDataMultiple();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        AffiliateCookie affiliateCookie = new AffiliateCookie(affiliateDataMultiple);
        affiliateCookie.add();
        WebDriverUtils.refreshPage();
        registrationPage.registerUser(userData);
        iMS.validateAffiliate(userData.getUsernameUppercase(), affiliateDataMultiple);
    }

    /*4*/
    @Test(groups = {"registration","regression", "affiliate"})
    public void affiliateCookieNoReferrerUrl(){
        UserData userData = defaultUserData.getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        affiliateDataSingle.setUrl("");
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        AffiliateCookie affiliateCookie = new AffiliateCookie(affiliateDataSingle);
        affiliateCookie.add();
        WebDriverUtils.refreshPage();
        registrationPage.registerUser(userData);
        iMS.validateAffiliate(userData.getUsernameUppercase(), affiliateDataSingle);
    }

    /*5*/
    @Test(groups = {"registration","regression", "affiliate"})
    public void affiliateCookieBannerIsRegexp(){
        UserData userData = defaultUserData.getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        affiliateDataSingle.setBanner("*");
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        AffiliateCookie affiliateCookie = new AffiliateCookie(affiliateDataSingle);
        affiliateCookie.add();
        WebDriverUtils.refreshPage();
        registrationPage.registerUser(userData);
        iMS.validateAffiliate(userData.getUsernameUppercase(), affiliateDataSingle);
    }

    /*6*/
    @Test(groups = {"registration","regression", "affiliate"})
    public void affiliateCookieSingleNoBannerNoProfileNoCreferrer(){
        UserData userData = defaultUserData.getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        affiliateDataSingle.setBanner("");
        affiliateDataSingle.setProfile("");
        affiliateDataSingle.setCreferer("");
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        AffiliateCookie affiliateCookie = new AffiliateCookie(affiliateDataSingle);
        affiliateCookie.add();
        WebDriverUtils.refreshPage();
        registrationPage.registerUser(userData);
        iMS.validateAffiliate(userData.getUsernameUppercase(), affiliateDataSingle);
    }


    /*7*/
    @Test(groups = {"registration","regression", "affiliate"})
    public void affiliateCookieSingleNoAdvertiserNoReferrerUrl(){
        UserData userData = defaultUserData.getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        affiliateDataSingle.setAdvertiser("");
        affiliateDataSingle.setUrl("");
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        AffiliateCookie affiliateCookie = new AffiliateCookie(affiliateDataSingle);
        affiliateCookie.add();
        WebDriverUtils.refreshPage();
        registrationPage.registerUser(userData);
        affiliateDataSingle.setAdvertiser(affiliateDataSingle.getDefaultAdvertiser());
        iMS.validateAffiliate(userData.getUsernameUppercase(), affiliateDataSingle);
    }

    /*11*/
    @Test(groups = {"registration","regression", "affiliate"})
    public void affiliateSupportURL(){
        UserData userData = defaultUserData.getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        WebDriverUtils.clearCookies();
        WebDriverUtils.navigateToInternalURL(affiliateDataSingle.getRelativeURL());
        registrationPage.registerUser(userData);
        iMS.validateAffiliate(userData.getUsernameUppercase(), affiliateDataSingle);
    }

    /*#??. Suggestion does not appear on entering new username*/
    @Test(groups = {"registration","regression"})
    public void usernameSuggestionNoSuggestion(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        String username=defaultUserData.getRandomUserData().getUsername();
        String usernameSuggestionMessage = registrationPage.getUsernameSuggestion(username, defaultUserData.getRandomUserData());
        TypeUtils.assertTrueWithLogs(usernameSuggestionMessage.equals("No username suggestion"), "Suggestion did not appear for unique username");
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

    /*#??. Suggestion filled out*/
    @Test(groups = {"registration","regression"})
    public void usernameSuggestionClick(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        String username=defaultUserData.getRegisteredUserData().getUsername();
        boolean result = registrationPage.clickUsernameSuggestionAndValidateInput(username, defaultUserData.getRandomUserData());
        TypeUtils.assertTrueWithLogs(result, "Suggestion entered after click");
    }

    /*#??. Suggestion disappers after refocus*/
    @Test(groups = {"registration","regression"})
    public void usernameSuggestionClickAfterFill(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        String username=defaultUserData.getRegisteredUserData().getUsername();
        registrationPage.clickUsernameSuggestionAndValidateInput(username, defaultUserData.getRandomUserData());
        registrationPage.clickUsernameField();
        WebDriverUtils.waitFor(1000);
        TypeUtils.assertFalseWithLogs(registrationPage.isSuggestionVisible(), "Suggestion tooltip still visible after click");
    }

    /*#??. Field is editable after suggestion has been used*/
    @Test(groups = {"registration","regression"})
    public void usernameSuggestionEditableAfterFill(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        String username=defaultUserData.getRegisteredUserData().getUsername();
        registrationPage.clickUsernameSuggestionAndValidateInput(username, defaultUserData.getRandomUserData());
        String newUsername = defaultUserData.getRandomUserData().getUsername();
        RegistrationPage.fillUsername(newUsername);
        TypeUtils.assertTrueWithLogs(newUsername.equals(registrationPage.getFilledUsername()), "username can be reentered");
    }

    /*#??. Suggestions are done on each Username field refocus*/
    @Test(groups = {"registration","regression"})
    public void usernameSuggestionAppearsEveryTime(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        String username=defaultUserData.getRegisteredUserData().getUsername();
        registrationPage.clickUsernameSuggestionAndValidateInput(username, defaultUserData.getRandomUserData());
        String newUsername = defaultUserData.getRandomUserData().getUsername();
        RegistrationPage.inputAndRefocusUsername(username);
        TypeUtils.assertTrueWithLogs(RegistrationPage.isSuggestionVisible(), "Suggestion visible second time");
        RegistrationPage.inputAndRefocusUsername(username);
        TypeUtils.assertTrueWithLogs(RegistrationPage.isSuggestionVisible(), "Suggestion visible third time");
        RegistrationPage.inputAndRefocusUsername(newUsername);
        TypeUtils.assertFalseWithLogs(RegistrationPage.isSuggestionVisible(), "Suggestion visible on valid email  time");
        RegistrationPage.inputAndRefocusUsername(username);
        TypeUtils.assertTrueWithLogs(RegistrationPage.isSuggestionVisible(), "Suggestion visible fourth time");
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
        registrationPage = (RegistrationPage) registrationPage.registerUser(userData, "invalid", Page.registrationPage);
        TypeUtils.assertTrueWithLogs(registrationPage.getPortletErrorMessage().equals("Coupon code is not found or not available"));
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
    @Test(groups = {"registration","regression"})
    public void genderDropdownValidation() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.validateGender(genderValidationRule);
    }

	@Test(groups = {"registration","regression"})
	public void firstnameFieldValidation() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validateFirstname(firstNameValidationRule);
	}

    @Test(groups = {"registration","regression"})
    public void lastnameFieldValidation() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.validateLastname(lastNameValidationRule);
    }

    @Test(groups = {"registration","regression"})
    public void dateOfBirthValidation() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.validateDateOfBirth(dateOfBirthValidationRule);
    }

	@Test(groups = {"registration","regression"})
	public void emailFieldValidation() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validateEmail(emailValidationRule);
	}

    /*#4. Email and email confirmation do not match*/
    @Test(groups = {"registration","regression","desktop"})
    public void emailConfirmationValidation(){
        String message="";
        String xpath = RegistrationPage.getEmailVerificationXpath();;
        String id = RegistrationPage.getEmailVerificationName();
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
        results = ValidationUtils.validateStatusAndToolTips(results, ValidationUtils.STATUS_NONE, id, email, ValidationUtils.STATUS_PASSED, ValidationUtils.STATUS_NONE);
        for(String result:results){
            if(!result.equals(ValidationUtils.PASSED)){
                message += "<div>" + result + "</div>";
            }
        }
        if(!message.isEmpty()){
            WebDriverUtils.runtimeExceptionWithUrl(message);
        }
    }

    /*#4. Email and email confirmation do not match*/
    @Test(groups = {"registration","regression","mobile"})
    public void emailConfirmationValidationMobile(){
        String message="";
        String xpath = RegistrationPage.getEmailVerificationXpath();;
        String id = RegistrationPage.getEmailVerificationName();
        ArrayList<String> results = new ArrayList<>();
        UserData generatedUserData=defaultUserData.getRandomUserData();
        String email = generatedUserData.getEmail();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.fillEmail(email);
        ValidationUtils.inputFieldAndRefocus(xpath, email);
        results = ValidationUtils.validateStatusAndToolTips(results, ValidationUtils.STATUS_NONE, id, email, ValidationUtils.STATUS_PASSED, ValidationUtils.STATUS_NONE);
        for(String result:results){
            if(!result.equals(ValidationUtils.PASSED)){
                message += "<div>" + result + "</div>";
            }
        }
        if(!message.isEmpty()){
            WebDriverUtils.runtimeExceptionWithUrl(message);
        }
    }

    @Test(groups = {"registration","regression"})
    public void emailDoNotMatch(){
        String message = "Emails dont match";
        UserData generatedUserData=defaultUserData.getRandomUserData();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.fillEmail(generatedUserData.getEmail());
        registrationPage.fillEmailVerificationAndRefocus(emailValidationRule.generateValidString());
        String tooltipMessageText=ValidationUtils.getTooltipText(RegistrationPage.getEmailVerificationName());
        boolean usernameUsedMessageDisplayed=tooltipMessageText.equals(message);
        TypeUtils.assertTrueWithLogs(usernameUsedMessageDisplayed, "Expected '"+message+"', Actual " + "'"+tooltipMessageText+"'");
    }

    @Test(groups = {"registration","regression"})
    public void stateFieldValidation() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.validateState(stateValidationRule, defaultUserData.getRandomUserData());
    }

    @Test(groups = {"registration","regression"})
    public void countryDropdownValidation() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.registerNoClientType);
        registrationPage.validateCountry(countryValidationRule,defaultUserData.getRandomUserData());
    }

	@Test(groups = {"registration","regression"})
	public void cityFieldValidation() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validateCity(cityValidationRule,defaultUserData.getRandomUserData());
	}

	@Test(groups = {"registration","regression"})
     public void address1FieldValidation() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.validateAddress(addressValidationRule,defaultUserData.getRandomUserData());
    }

    @Test(groups = {"registration","regression"})
    public void address2FieldValidation() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.validateAddress2(address2ValidationRule,defaultUserData.getRandomUserData());
    }

    @Test(groups = {"registration","regression"})
    public void houseFieldValidation() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.validateHouse(houseValidationRule,defaultUserData.getRandomUserData());
    }

	@Test(groups = {"registration","regression"})
	public void postcodeFieldValidation() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validatePostcode(postcodeValidationRule,defaultUserData.getRandomUserData());
	}

    @Test(groups = {"registration","regression"})
    public void phoneAreaCodeFieldValidation() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.validatePhoneAreaCode(countryPhoneCodeValidationRule, defaultUserData.getRandomUserData());
    }

	@Test(groups = {"registration","regression"})
	public void phoneFieldValidation() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validatePhone(phoneValidationRule, defaultUserData.getRandomUserData());
	}

	@Test(groups = {"registration","regression","desktop"})
	public void usernameFieldValidation() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validateUsername(usernameValidationRule,defaultUserData.getRandomUserData());
	}

	@Test(groups = {"registration","regression"})
	public void passwordFieldValidation() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validatePassword(passwordValidationRule,defaultUserData.getRandomUserData());
	}

    @Test(groups = {"registration","regression", "desktop"})
    public void passwordConfirmationValidation(){
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

    @Test(groups = {"registration","regression", "mobile"})
    public void passwordConfirmationValidationMobile(){
        String message="";
        String xpath = RegistrationPage.FIELD_PASSWORD_VERIFICATION_XP;
        String id = RegistrationPage.FIELD_PASSWORD_VERIFICATION_NAME;
        ArrayList<String> results = new ArrayList<>();
        UserData generatedUserData=defaultUserData.getRandomUserData();
        String password = generatedUserData.getPassword();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.registrationPageStepThree(generatedUserData);
        registrationPage.fillPassword(password);
        registrationPage.fillPasswordVerificationAndRefocus("");
        results.add(ValidationUtils.validationStatusIs(id, ValidationUtils.STATUS_FAILED, "empty"));
        String tooltip = "Please retype your password.";
        results.add(ValidationUtils.tooltipStatusIs(id, ValidationUtils.STATUS_FAILED, "empty"));
        results.add(ValidationUtils.tooltipTextIs(id, tooltip, "empty"));
        registrationPage.fillPasswordVerificationAndRefocus(password);
        results = ValidationUtils.validateStatusAndToolTips(results, ValidationUtils.STATUS_NONE, id, password, ValidationUtils.STATUS_PASSED, ValidationUtils.STATUS_NONE);
        for(String result:results){
            if(!result.equals(ValidationUtils.PASSED)){
                message += "<div>" + result + "</div>";
            }
        }
        if(!message.isEmpty()){
            WebDriverUtils.runtimeExceptionWithUrl(message);
        }
    }

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

    /*#5. Password & Confirmation do not match*/
    @Test(groups = {"registration","regression", "mobile"})
    public void passwordDoNotMatchMobile(){
        String message = "Passwords are not the same";
        UserData generatedUserData=defaultUserData.getRandomUserData();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.registrationPageStepThree(generatedUserData);
        registrationPage.fillPassword(generatedUserData.getPassword());
        String validPass = passwordValidationRule.generateValidString();
        registrationPage.fillPasswordVerificationAndRefocus(validPass);
        String errorMessageText=ValidationUtils.getTooltipText(RegistrationPage.FIELD_PASSWORD_VERIFICATION_NAME);
        boolean emailUsedMessageDisplayed=errorMessageText.equals(message);
        TypeUtils.assertTrueWithLogs(emailUsedMessageDisplayed, "Expected '"+message+"', Actual " + "'"+errorMessageText+"', for value '"+validPass+"'");
    }

//    @Test(groups = {"registration","regression"})
//    public void questionFieldValidation() {
//        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
//        registrationPage.validateQuestion(questionValidationRule,defaultUserData.getRandomUserData());
//    }

    @Test(groups = {"registration","regression"})
    public void answerFieldValidation() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.validateAnswer(answerValidationRule,defaultUserData.getRandomUserData());
    }

    @Test(groups = {"registration","regression"})
    public void currencyDropdownValidation() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.validateCurrency(currencyValidationRule,defaultUserData.getRandomUserData());
    }

	@Test(groups = {"registration","regression"})
	public void bonusCodeFieldValidation() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validateBonusCode(bonusCodeValidationRule,defaultUserData.getRandomUserData());
	}
}
