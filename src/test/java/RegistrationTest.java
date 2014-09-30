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
import pageObjects.registration.threeStep.RegistrationPageStepThree;
import pageObjects.registration.threeStep.RegistrationPageStepTwo;
import springConstructors.*;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.TypeUtils;
import utils.WebDriverUtils;
import utils.cookie.AffiliateCookie;
import utils.core.AbstractTest;
import utils.core.WebDriverObject;
import utils.validation.ValidationUtils;
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
        validateTrue(homePage.isLoggedIn(), "User is logged in");
	}

    /*#2. Registration with receive bonuses check box checked*/
    @Test(groups = {"registration","regression"})
    public void receivePromotionOffersDefaultState(){
        UserData userData=defaultUserData.getRandomUserData();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        validateTrue(registrationPage.getReceivePromotionsCheckboxState(userData), "Promotional checkbox is checked by default");
    }

    @Test(groups = {"registration","regression"})
    public void receivePromotionOffersText(){
        UserData userData=defaultUserData.getRandomUserData();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        validateEquals("I would like to receive great bonuses and exciting offers", registrationPage.getReceivePromotionsCheckboxText(userData), "Promotional offers checkbox text");
    }

	/*#2. Registration with receive bonuses check box checked*/
	@Test(groups = {"registration","regression"})
	public void receivePromotionOffersIsCheckedInIMS(){
		UserData userData=defaultUserData.getRandomUserData();
		PortalUtils.registerUser(userData,true);
		boolean receiveBonusesCheckedInIMS=iMS.navigateToPlayedDetails(userData.getUsername()).getAllChannelsCheckboxState();
		validateTrue(receiveBonusesCheckedInIMS, "Promotion checkboxes are checked in IMS");
	}

    /*#3. Registration with receive bonuses check box unchecked*/
	@Test(groups = {"registration","regression"})
	public void receivePromotionOffersIsNotCheckedInIMS(){
		UserData userData=defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData,false);
		boolean receiveBonusesNotCheckedInIMS=iMS.navigateToPlayedDetails(userData.getUsername()).getAllChannelsCheckboxState();
        validateFalse(receiveBonusesNotCheckedInIMS, "Promotion checkboxes are checked in IMS");
	}

    /*#4. Registration with bonus code provided*/
	@Test(groups = {"registration","regression"})
	public void registrationWithBonusCoupon(){
        UserData userData=defaultUserData.getRandomUserData();
        HomePage homePage = (HomePage) PortalUtils.registerUser(userData,true,true, "valid", Page.homePage);
        validateEquals("£ 1.00", homePage.getBalance(), "User balance -");
	}

    /*#5. Registration without bonus code provided*/
    @Test(groups = {"registration","regression"})
	public void registrationWithOutBonusCoupon(){
        UserData userData=defaultUserData.getRandomUserData();
        RegistrationPage registrationPage = (RegistrationPage) PortalUtils.registerUser(userData,true,true, "invalid", Page.registrationPage);
        validateEquals("Coupon code is not found or not available" , registrationPage.getPortletErrorMessage(), "No bonus error message");
	}

    /*#6. Player is registered with currency selected*/
	@Test(groups = {"registration","regression","desktop"})
	public void validHeaderUnitBalance(){
        String currency = "GBP";
        String currencySign = "£";
        UserData userData=defaultUserData.getRandomUserData();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        String defaultCurrencyInRegistrationForm = registrationPage.getSelectedCurrency();
        HomePage homePage=(HomePage)registrationPage.registerUser(userData, true, false, null, Page.homePage);
        assertTrue(defaultCurrencyInRegistrationForm.contains(currency), "Default register currency is '"+currency+"'");
        assertTrue(homePage.getBalance().contains(currencySign), "Currency sign in header is '"+currency+"'");
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
        String afterRegistrationPage = "/admin";
		PortalUtils.registerUser(defaultUserData.getRandomUserData());
		String actualUrl=WebDriverUtils.getCurrentUrl();
        assertTrue(actualUrl.endsWith(afterRegistrationPage), "Moved to afterRegistration page '"+afterRegistrationPage+"'");
	}

    /*#11. The list of supported countries is correct*/
	@Test(groups = {"registration","regression"})
	public void countryList(){
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		Collection<String> actualCountriesCodesList = registrationPage.getCountriesCodesList(defaultUserData.getRandomUserData());
		Collection<String> diff=TypeUtils.getDiffElementsFromLists(actualCountriesCodesList, defaults.getCountryCodesList());
        assertTrue(diff.isEmpty(), "(Actual diff: '"+diff.toString()+"')Country codes correspond with configuration");
	}

    /*#??. The list of supported nationalities is correct*/
    @Test(groups = {"registration","spanish", "desktop"})
    public void nationalityList(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        Collection<String> actualNationalitiesCodesList = registrationPage.registrationPageAllSteps().getNationalitiesCodesList();
        Collection<String> diff=TypeUtils.getDiffElementsFromLists(actualNationalitiesCodesList, defaults.getCountryCodesList());
        assertTrue(diff.isEmpty(), "(Actual diff: '"+diff.toString()+"')Nationality codes correspond with configuration");
    }

    /*#12. The list of supported currencies is correct*/
	@Test(groups = {"registration","regression"})
	public void currencyList(){
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		Collection<String> actualCurrencyList=registrationPage.getCurrencyList(defaultUserData.getRandomUserData());
		Collection<String> diff= TypeUtils.getDiffElementsFromLists(actualCurrencyList, defaults.getCurrencyList());
        assertTrue(diff.isEmpty(), "(Actual diff: '"+diff.toString()+"')Currency codes correspond with configuration");
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
		iMS.validateRegisterData(userData);
	}

    /*Client type poker*/
    @Test(groups = {"registration","regression"})
    public void registrationWithClientType(){
        UserData userData=defaultUserData.getRandomUserData();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.registerClientTypeCreferrer);
        registrationPage.registerUser(userData);
        validateEquals("poker", iMS.getClientType(userData), "Client type");
    }

    /*Client type empty*/
    @Test(groups = {"registration","regression"})
    public void registrationWithoutClientType(){
        UserData userData=defaultUserData.getRandomUserData();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.registerNoClientType);
        registrationPage.registerUser(userData);
        validateEquals("casino", iMS.getClientType(userData), "Client type");

    }

    /*#19. All required fields are marked with asterisks*/
	@Test(groups = {"registration","regression", "desktop"})
	public void requiredFieldsLabelsMarkedWithStar(){
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        validateTrue(registrationPage.registrationPageAllSteps().labelsRequiredMarkingCorrect(), "T&C validation error visible");
	}

    /*#20. By default T&C check box is unchecked by default*/
	@Test(groups = {"registration","regression", "mobile"})
	public void defaultCheckboxesState(){
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        validateTrue(registrationPage.registrationPageStepThree(defaultUserData.getRandomUserData()).getTermsCheckbox(), "T&C checkbox checked by default");
	}

    /*#22. Country - countryCode code and phone prefix mapping*/
	@Test(groups = {"registration","regression", "desktop"})
	public void countryCodePhoneCodeMappingDesktop(){
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        RegistrationPageAllSteps registrationPageAllSteps = registrationPage.registrationPageAllSteps();
		for (String countryCode : defaults.getCountryCodesList()) {
            registrationPageAllSteps.fillCountry(countryCode);
            assertEquals("+" + defaults.getPhoneCodeByCountryCode(countryCode), registrationPageAllSteps.getPhoneAreaCode(), "Phone area code for '"+countryCode+"'");
		}
	}

    /*#??. Country - countryCode code and name mapping*/
    @Test(groups = {"registration","regression", "desktop"})
    public void countryCodeNamePrefix(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        for (String countryCode : defaults.getCountryCodesList()) {
            registrationPage.fillCountry(countryCode);
            assertEquals(defaults.getCountryNameByCountryCode(countryCode), registrationPage.getSelectedCountryName().trim(), "Country name for '"+countryCode+"'");
        }
    }

    /*#??. Country - countryCode code and name mapping*/
    @Test(groups = {"registration","regression", "mobile"})
    public void countryCodeNamePrefixMobile(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.registrationPageStepTwo(defaultUserData.getRandomUserData());
        for (String countryCode : defaults.getCountryCodesList()) {
            registrationPage.fillCountry(countryCode);
            assertEquals(defaults.getCountryNameByCountryCode(countryCode), registrationPage.getSelectedCountryName().trim(), "Country name for '"+countryCode+"'");
        }
    }

    /*#23. Default selected countryCode*/
	@Test(groups = {"registration","regression", "desktop"})
	public void defaultCountry(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        assertEquals(defaults.getDefaultCountryName(), registrationPage.getSelectedCountryName(), "Default country name");
		// assertTrue(registrationPage.isFindMyAddressButtonVisible());
	}

    /*#23. Default selected countryCode*/
    @Test(groups = {"registration","regression", "mobile"})
    public void defaultCountryMobile(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.registrationPageStepTwo(defaultUserData.getRandomUserData());
        assertEquals(defaults.getDefaultCountryName(), registrationPage.getSelectedCountryName(), "Default country name");
        // assertTrue(registrationPage.isFindMyAddressButtonVisible());
    }

    /* Password strength*/
    @Test(groups = {"registration", "regression", "desktop"})
    public void passwordStrength(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.assertPasswordStrength("aaaaaa", PasswordStrength.zero);
        registrationPage.assertPasswordStrength("AAAAAA", PasswordStrength.zero);
        registrationPage.assertPasswordStrength("000000", PasswordStrength.zero);
        registrationPage.assertPasswordStrength("0000aa", PasswordStrength.one);
        registrationPage.assertPasswordStrength("0000AA", PasswordStrength.one);
        registrationPage.assertPasswordStrength("00aaAA", PasswordStrength.two);
        registrationPage.assertPasswordStrength("@0aaAA", PasswordStrength.three);
        registrationPage.assertPasswordStrength("0000@@", PasswordStrength.two);
        registrationPage.assertPasswordStrength("aaaAAA", PasswordStrength.one);
        registrationPage.assertPasswordStrength("aaaaaaaa", PasswordStrength.one);
        registrationPage.assertPasswordStrength("AAAAAAAA", PasswordStrength.one);
        registrationPage.assertPasswordStrength("aaaaaaAA", PasswordStrength.two);
        registrationPage.assertPasswordStrength("aaaaaa00", PasswordStrength.two);
        registrationPage.assertPasswordStrength("000000@@", PasswordStrength.three);
        registrationPage.assertPasswordStrength("00aaaaAA", PasswordStrength.three);
        registrationPage.assertPasswordStrength("@@aaaaAA", PasswordStrength.three);
        registrationPage.assertPasswordStrength("@@00aaAA", PasswordStrength.four);
        registrationPage.assertPasswordStrength("aaaaaaaaaaaa", PasswordStrength.two);
        registrationPage.assertPasswordStrength("AAAAAAAAAAAA", PasswordStrength.two);
        registrationPage.assertPasswordStrength("00AAAAAAAAAA", PasswordStrength.three);
        registrationPage.assertPasswordStrength("00aaaaaaaaaa", PasswordStrength.three);
        registrationPage.assertPasswordStrength("aaaaaaaaaaAA", PasswordStrength.three);
        registrationPage.assertPasswordStrength("@@aaaaaaaaAA", PasswordStrength.four);
        registrationPage.assertPasswordStrength("00aaaaaaaaAA", PasswordStrength.four);
        registrationPage.assertPasswordStrength("00aaaaaa@@AA", PasswordStrength.five);
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
//        assertTrue(fillFieldsVisibleStage1);
//        TypeUtils.assertFalse(fillFieldsVisibleStage2);
//        assertTrue(fillFieldsVisibleStage3);
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
    @Test(groups = {"registration","regression", "affiliate", "cookie"})
    public void affiliateCookieSingleCreferrer(){
        UserData userData = defaultUserData.getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        AffiliateCookie affiliateCookie = new AffiliateCookie(affiliateDataSingle);
        affiliateCookie.add();
        registrationPage.registerUser(userData);
        iMS.validateAffiliate(userData.getUsername(), affiliateDataSingle);
    }

    /*2*/
    @Test(groups = {"registration","regression", "affiliate", "cookie"})
    public void affiliateCookieAdvertiserNotExists(){
        UserData userData = defaultUserData.getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        affiliateDataSingle.setAdvertiser("notExists");
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        AffiliateCookie affiliateCookie = new AffiliateCookie(affiliateDataSingle);
        affiliateCookie.add();
        registrationPage.registerUser(userData);
        affiliateDataSingle.setAdvertiser(affiliateDataSingle.getDefaultAdvertiser());
        iMS.validateAffiliate(userData.getUsername(), affiliateDataSingle);
    }

    /*3*/
    @Test(groups = {"registration","regression", "affiliate", "cookie"})
    public void affiliateCookieCreffererNotExists(){
        UserData userData = defaultUserData.getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        affiliateDataSingle.setCreferrer("notExists:123");
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        AffiliateCookie affiliateCookie = new AffiliateCookie(affiliateDataSingle);
        affiliateCookie.add();
        registrationPage.registerUser(userData);
        iMS.validateAffiliate(userData.getUsername(), affiliateDataSingle, false);
    }

    /*4*/
    @Test(groups = {"registration","regression", "affiliate", "cookie"})
    public void affiliateCookieMultipleCreferrer(){
        UserData userData = defaultUserData.getRandomUserData();
        AffiliateData affiliateDataMultiple = affiliateData.getAffiliateDataMultiple();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        AffiliateCookie affiliateCookie = new AffiliateCookie(affiliateDataMultiple);
        affiliateCookie.add();
        registrationPage.registerUser(userData);
        iMS.validateAffiliate(userData.getUsername(), affiliateDataMultiple);
    }

    /*5*/
    @Test(groups = {"registration","regression", "affiliate", "cookie"})
    public void affiliateCookieNoReferrerUrl(){
        UserData userData = defaultUserData.getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        affiliateDataSingle.setUrl("");
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        AffiliateCookie affiliateCookie = new AffiliateCookie(affiliateDataSingle);
        affiliateCookie.add();
        registrationPage.registerUser(userData);
        iMS.validateAffiliate(userData.getUsername(), affiliateDataSingle);
    }

    /*6*/
    @Test(groups = {"registration","regression", "affiliate", "cookie"})
    public void affiliateCookieBannerIsRegexp(){
        UserData userData = defaultUserData.getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        affiliateDataSingle.setBanner("*");
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        AffiliateCookie affiliateCookie = new AffiliateCookie(affiliateDataSingle);
        affiliateCookie.add();
        registrationPage.registerUser(userData);
        iMS.validateAffiliate(userData.getUsername(), affiliateDataSingle);
    }

    /*7*/
    @Test(groups = {"registration","regression", "affiliate", "cookie"})
    public void affiliateCookieNoBannerNoProfileNoCreferrer(){
        UserData userData = defaultUserData.getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        affiliateDataSingle.setBanner("");
        affiliateDataSingle.setProfile("");
        affiliateDataSingle.setCreferrer("");
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        AffiliateCookie affiliateCookie = new AffiliateCookie(affiliateDataSingle);
        affiliateCookie.add();
        registrationPage.registerUser(userData);
        iMS.validateAffiliate(userData.getUsername(), affiliateDataSingle);
    }

    /*8*/
    @Test(groups = {"registration","regression", "affiliate", "cookie"})
    public void affiliateCookieNoAdvertiserNoReferrerUrl(){
        UserData userData = defaultUserData.getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        affiliateDataSingle.setAdvertiser("");
        affiliateDataSingle.setUrl("");
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        AffiliateCookie affiliateCookie = new AffiliateCookie(affiliateDataSingle);
        affiliateCookie.add();
        registrationPage.registerUser(userData);
        affiliateDataSingle.setAdvertiser(affiliateDataSingle.getDefaultAdvertiser());
        iMS.validateAffiliate(userData.getUsername(), affiliateDataSingle);
    }

    /*9*/
    @Test(groups = {"registration","regression", "affiliate", "cookie"})
    public void affiliateCookieThreeFirstParametersOnly(){
        UserData userData = defaultUserData.getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        affiliateDataSingle.setUrl("");
        affiliateDataSingle.setCreferrer("");
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        AffiliateCookie affiliateCookie = new AffiliateCookie(affiliateDataSingle.getAdvertiser() + "," + affiliateDataSingle.getBanner() + "," + affiliateDataSingle.getProfile());
        affiliateCookie.add();
        registrationPage.registerUser(userData);
        iMS.validateAffiliate(userData.getUsername(), affiliateDataSingle);
    }

    /*10*/
    @Test(groups = {"registration","regression", "affiliate", "cookie"})
    public void affiliateCookieIncorrectValueFormatNotEnoughCommas(){
        UserData userData = defaultUserData.getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        AffiliateCookie affiliateCookie = new AffiliateCookie(affiliateDataSingle.getAdvertiser() + "," + affiliateDataSingle.getProfile() + "," + affiliateDataSingle.getCrefererSingle());
        affiliateCookie.add();
        registrationPage.registerUser(userData);
        iMS.validateNoAffiliate(userData.getUsername(), affiliateDataSingle);
    }

    /*11*/
    @Test(groups = {"registration","regression", "affiliate", "cookie"})
    public void affiliateCookieEmptyValue(){
        UserData userData = defaultUserData.getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        AffiliateCookie affiliateCookie = new AffiliateCookie("");
        affiliateCookie.add();
        registrationPage.registerUser(userData);
        iMS.validateNoAffiliate(userData.getUsername(), affiliateDataSingle);
    }

    /*12*/
    @Test(groups = {"registration","regression", "affiliate", "cookie"})
    public void affiliateCookieAfterCookie(){
        UserData userData = defaultUserData.getRandomUserData();
        AffiliateData affiliateDataCookie1 = affiliateData.getAffiliateDataSingle();
        affiliateDataCookie1.setAdvertiser("advertiser");
        affiliateDataCookie1.setBanner("banner");
        affiliateDataCookie1.setProfile("profile");
        affiliateDataCookie1.setUrl("url");
        affiliateDataCookie1.setCreferrer("creferrer");
        AffiliateData affiliateDataCookie2 = affiliateData.getAffiliateDataSingle();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        AffiliateCookie affiliateCookie1 = new AffiliateCookie(affiliateDataCookie1);
        affiliateCookie1.add();
        AffiliateCookie affiliateCookie2 = new AffiliateCookie(affiliateDataCookie2);
        affiliateCookie2.add();
        registrationPage.registerUser(userData);
        iMS.validateNoAffiliate(userData.getUsername(), affiliateDataCookie2);
    }

    /*13*/
    @Test(groups = {"registration","regression", "affiliate", "url"})
    public void affiliateUrlSingleCreferrer(){
        UserData userData = defaultUserData.getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        NavigationUtils.navigateToAffiliateURL(ConfiguredPages.register, affiliateDataSingle);
        registrationPage.registerUser(userData);
        iMS.validateAffiliate(userData.getUsername(), affiliateDataSingle);
    }

    /*14*/
    @Test(groups = {"registration","regression", "affiliate", "url"})
    public void affiliateUrlAdvertiserNotExists(){
        UserData userData = defaultUserData.getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        affiliateDataSingle.setAdvertiser("notExists");
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        NavigationUtils.navigateToAffiliateURL(ConfiguredPages.register, affiliateDataSingle);
        registrationPage.registerUser(userData);
        affiliateDataSingle.setAdvertiser(affiliateDataSingle.getDefaultAdvertiser());
        iMS.validateAffiliate(userData.getUsername(), affiliateDataSingle);
    }

    /*15*/
    @Test(groups = {"registration","regression", "affiliate", "url"})
    public void affiliateUrlCreffererNotExists(){
        UserData userData = defaultUserData.getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        affiliateDataSingle.setCreferrer("notExists:123");
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        NavigationUtils.navigateToAffiliateURL(ConfiguredPages.register, affiliateDataSingle);
        registrationPage.registerUser(userData);
        affiliateDataSingle.setAdvertiser(affiliateDataSingle.getDefaultAdvertiser());
        iMS.validateAffiliate(userData.getUsername(), affiliateDataSingle, false);
    }

    /*16*/
    @Test(groups = {"registration","regression", "affiliate", "url"})
    public void affiliateUrlMultipleCreferrer(){
        UserData userData = defaultUserData.getRandomUserData();
        AffiliateData affiliateDataMultiple = affiliateData.getAffiliateDataMultiple();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        NavigationUtils.navigateToAffiliateURL(ConfiguredPages.register, affiliateDataMultiple);
        registrationPage.registerUser(userData);
        iMS.validateAffiliate(userData.getUsername(), affiliateDataMultiple);
    }

    /*17*/
    @Test(groups = {"registration","regression", "affiliate", "url"})
    public void affiliateUrl4Parameters(){
        UserData userData = defaultUserData.getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        affiliateDataSingle.setUrl("");
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        NavigationUtils.navigateToAffiliateURL(ConfiguredPages.register, affiliateDataSingle);
        registrationPage.registerUser(userData);
        iMS.validateAffiliate(userData.getUsername(), affiliateDataSingle);
    }

    /*18*/
    @Test(groups = {"registration","regression", "affiliate", "url"})
    public void affiliateUrl3Parameters(){
        UserData userData = defaultUserData.getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        affiliateDataSingle.setBanner("");
        affiliateDataSingle.setAdvertiser("");
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        NavigationUtils.navigateToAffiliateURL(ConfiguredPages.register, affiliateDataSingle);
        registrationPage.registerUser(userData);
        affiliateDataSingle.setAdvertiser(affiliateDataSingle.getDefaultAdvertiser());
        iMS.validateAffiliate(userData.getUsername(), affiliateDataSingle);
    }

    /*19*/
    @Test(groups = {"registration","regression", "affiliate", "url"})
    public void affiliateUrl2Parameters(){
        UserData userData = defaultUserData.getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        affiliateDataSingle.setProfile("");
        affiliateDataSingle.setUrl("");
        affiliateDataSingle.setCreferrer("");
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        NavigationUtils.navigateToAffiliateURL(ConfiguredPages.register, affiliateDataSingle);
        registrationPage.registerUser(userData);
        iMS.validateAffiliate(userData.getUsername(), affiliateDataSingle);
    }

    /*20*/
    @Test(groups = {"registration","regression", "affiliate", "url"})
    public void affiliateUrl1Parameter(){
        UserData userData = defaultUserData.getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        affiliateDataSingle.setAdvertiser("advertiser");
        affiliateDataSingle.setBanner("banner");
        affiliateDataSingle.setProfile("profile");
        affiliateDataSingle.setUrl("url");
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        NavigationUtils.navigateToAffiliateURL(ConfiguredPages.register, affiliateDataSingle);
        registrationPage.registerUser(userData);
        affiliateDataSingle.setAdvertiser(affiliateDataSingle.getDefaultAdvertiser());
        iMS.validateAffiliate(userData.getUsername(), affiliateDataSingle);
    }

    /*21*/
    @Test(groups = {"registration","regression", "affiliate", "url"})
    public void affiliateUrlAfterUrl(){
        UserData userData = defaultUserData.getRandomUserData();
        AffiliateData affiliateDataUrl1 = affiliateData.getAffiliateDataSingle();
        affiliateDataUrl1.setAdvertiser("advertiser");
        affiliateDataUrl1.setBanner("banner");
        affiliateDataUrl1.setProfile("profile");
        affiliateDataUrl1.setUrl("url");
        affiliateDataUrl1.setCreferrer("creferrer");
        AffiliateData affiliateDataUrl2 = affiliateData.getAffiliateDataSingle();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        NavigationUtils.navigateToAffiliateURL(ConfiguredPages.register, affiliateDataUrl1);
        NavigationUtils.navigateToAffiliateURL(ConfiguredPages.register, affiliateDataUrl2);
        registrationPage.registerUser(userData);
        iMS.validateAffiliate(userData.getUsername(), affiliateDataUrl2);
    }

    /*22*/
    @Test(groups = {"registration","regression", "affiliate", "cookie", "url"})
    public void affiliateCookieAndUrl(){
        UserData userData = defaultUserData.getRandomUserData();
        AffiliateData affiliateDataUrl =    affiliateData.getAffiliateDataSingle();
        AffiliateData affiliateDataCookie = affiliateData.getAffiliateDataSingle();
        affiliateDataCookie.setAdvertiser("advertiser");
        affiliateDataCookie.setBanner("banner");
        affiliateDataCookie.setProfile("profile");
        affiliateDataCookie.setUrl("url");
        affiliateDataCookie.setCreferrer("name:value");
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        AffiliateCookie affiliateCookie = new AffiliateCookie(affiliateDataCookie);
        affiliateCookie.add();
        NavigationUtils.navigateToAffiliateURL(ConfiguredPages.register, affiliateDataUrl);
        registrationPage.registerUser(userData);
        iMS.validateAffiliate(userData.getUsername(), affiliateDataUrl);
    }


    /*B-11324 Creferrer*/
    /*2*/
    @Test(groups = {"registration","regression", "affiliate", "creferrer"})
    public void affiliateCreferrerRegistrationPreference(){
        UserData userData = defaultUserData.getRandomUserData();
        String creferrerRegistration = affiliateData.getCreferrerRegistrationPortletProperty();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.registerClientTypeCreferrer);
        registrationPage.registerUser(userData);
        iMS.validateCreferrer(userData.getUsername(), creferrerRegistration);
    }

    /*3*/
    @Test(groups = {"registration","regression", "affiliate", "creferrer", "cookie"})
    public void affiliateCreferrerRegistrationPreferenceAndCookie(){
        UserData userData = defaultUserData.getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.registerClientTypeCreferrer);
        AffiliateCookie affiliateCookie = new AffiliateCookie(affiliateDataSingle);
        affiliateCookie.add();
        registrationPage.registerUser(userData);
        affiliateDataSingle.addCreferer(affiliateData.getCreferrerRegistrationPortletProperty());
        iMS.validateAffiliate(userData.getUsername(), affiliateDataSingle);
    }

    /*4*/
    @Test(groups = {"registration","regression", "affiliate", "url"})
    public void affiliateCreferrerRegistrationPreferenceAndUrl(){
        UserData userData = defaultUserData.getRandomUserData();
        AffiliateData affiliateDataSingle = affiliateData.getAffiliateDataSingle();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.registerClientTypeCreferrer);
        NavigationUtils.navigateToAffiliateURL(ConfiguredPages.register, affiliateDataSingle);
        registrationPage.registerUser(userData);
        affiliateDataSingle.addCreferer(affiliateData.getCreferrerRegistrationPortletProperty());
        iMS.validateAffiliate(userData.getUsername(), affiliateDataSingle);
    }

    /*5.1*/
    @Test(groups = {"registration","regression", "affiliate", "cookie", "url"})
    public void affiliateCreferrerRegistrationPreferenceAndCookieAndUrlCookieFirst(){
        UserData userData = defaultUserData.getRandomUserData();
        AffiliateData affiliateDataCookie = affiliateData.getAffiliateDataSingle();
        affiliateDataCookie.setAdvertiser("advertiser");
        affiliateDataCookie.setBanner("banner");
        affiliateDataCookie.setProfile("profile");
        affiliateDataCookie.setUrl("url");
        affiliateDataCookie.setCreferrer("creferrer");
        AffiliateData affiliateDataUrl = affiliateData.getAffiliateDataSingle();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.registerClientTypeCreferrer);
        AffiliateCookie affiliateCookie = new AffiliateCookie(affiliateDataCookie);
        affiliateCookie.add();
        NavigationUtils.navigateToAffiliateURL(ConfiguredPages.register, affiliateDataUrl);
        registrationPage.registerUser(userData);
        affiliateDataUrl.addCreferer(affiliateData.getCreferrerRegistrationPortletProperty());
        iMS.validateAffiliate(userData.getUsername(), affiliateDataUrl);
    }

    /*5.2*/
    @Test(groups = {"registration","regression", "affiliate", "cookie", "url"})
    public void affiliateCreferrerRegistrationPreferenceAndCookieAndUrlUrlFirst(){
        UserData userData = defaultUserData.getRandomUserData();
        AffiliateData affiliateDataUrl = affiliateData.getAffiliateDataSingle();
        affiliateDataUrl.setAdvertiser("advertiser");
        affiliateDataUrl.setBanner("banner");
        affiliateDataUrl.setProfile("profile");
        affiliateDataUrl.setUrl("url");
        affiliateDataUrl.setCreferrer("creferrer");
        AffiliateData affiliateDataCookie = affiliateData.getAffiliateDataSingle();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.registerClientTypeCreferrer);
        NavigationUtils.navigateToAffiliateURL(ConfiguredPages.register, affiliateDataUrl);
        AffiliateCookie affiliateCookie = new AffiliateCookie(affiliateDataCookie);
        affiliateCookie.add();
        registrationPage.registerUser(userData);
        affiliateDataCookie.addCreferer(affiliateData.getCreferrerRegistrationPortletProperty());
        iMS.validateAffiliate(userData.getUsername(), affiliateDataCookie);
    }

    /*B-11233*/

    /*1*/
    @Test(groups = {"registration", "regression", "desktop"})
    public void countryCurrencySinglePageRegistration(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        for (String countryCode : defaults.getCountryCodesList()) {
            registrationPage.fillCountry(countryCode);
            assertEquals(defaults.getCurrencyByCountryCode(countryCode), registrationPage.getSelectedCurrency(), "Currency for "+defaults.getCountryNameByCountryCode(countryCode) +" (" + countryCode+")");
        }
    }

    /*2*/
    @Test(groups = {"registration", "regression", "mobile"})
    public void countryCurrencyThreeStepRegistration(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        RegistrationPageStepThree registrationPageStepThree = registrationPage.registrationPageStepThree(defaultUserData.getRandomUserData());
        RegistrationPageStepTwo registrationPageStepTwo;
        for (String countryCode : defaults.getCountryCodesList()) {
            registrationPageStepTwo = registrationPageStepThree.clickPrevious();
            registrationPage.fillCountry(countryCode);
            registrationPageStepTwo.clickNext();
            assertEquals(defaults.getCurrencyByCountryCode(countryCode), registrationPage.getSelectedCurrency(), "Currency for "+defaults.getCountryNameByCountryCode(countryCode) +" (" + countryCode+")");
        }
    }

    /*#??. Suggestion does not appear on entering new username*/
    @Test(groups = {"registration","regression"})
    public void usernameSuggestionNoSuggestion(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        String username=defaultUserData.getRandomUserData().getUsername();
        assertEquals("No username suggestion", registrationPage.getUsernameSuggestion(username, defaultUserData.getRandomUserData()), "Suggestion for unique username");
    }

    /*#??. Suggestion appeared on entering already registered username*/
    @Test(groups = {"registration","regression"})
    public void usernameSuggestion(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        String username=defaultUserData.getRegisteredUserData().getUsername();
        String usernameSuggestionMessage = registrationPage.getUsernameSuggestion(username, defaultUserData.getRandomUserData());
        assertTrue(usernameSuggestionMessage.startsWith("This username is already in use. Suggested username is:"), "(Actual: '"+usernameSuggestionMessage+"')Username suggestion message contains preamble");
        assertTrue(usernameSuggestionMessage.contains(username.toUpperCase()), "(Actual: '"+usernameSuggestionMessage+"')Username suggestion message contains '" + username + "'");
    }

    /*#??. Suggestion filled out*/
    @Test(groups = {"registration","regression"})
    public void usernameSuggestionClick(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        String username=defaultUserData.getRegisteredUserData().getUsername();
        assertTrue(registrationPage.clickUsernameSuggestionAndValidateInput(username, defaultUserData.getRandomUserData()), "Suggestion entered after click");
    }

    /*#??. Suggestion disappers after refocus*/
    @Test(groups = {"registration","regression"})
    public void usernameSuggestionClickAfterFill(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        String username=defaultUserData.getRegisteredUserData().getUsername();
        registrationPage.clickUsernameSuggestionAndValidateInput(username, defaultUserData.getRandomUserData());
        registrationPage.clickUsernameField();
        WebDriverUtils.waitFor(1000);
        assertFalse(registrationPage.isSuggestionVisible(), "Suggestion tooltip still visible after click");
    }

    /*#??. Field is editable after suggestion has been used*/
    @Test(groups = {"registration","regression"})
    public void usernameSuggestionEditableAfterFill(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        String username=defaultUserData.getRegisteredUserData().getUsername();
        registrationPage.clickUsernameSuggestionAndValidateInput(username, defaultUserData.getRandomUserData());
        String newUsername = defaultUserData.getRandomUserData().getUsername();
        RegistrationPage.fillUsername(newUsername);
        assertEquals(newUsername, registrationPage.getFilledUsername(), "Username can be reentered");
    }

    /*#??. Suggestions are done on each Username field refocus*/
    @Test(groups = {"registration","regression"})
    public void usernameSuggestionAppearsEveryTime(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        String username=defaultUserData.getRegisteredUserData().getUsername();
        registrationPage.clickUsernameSuggestionAndValidateInput(username, defaultUserData.getRandomUserData());
        String newUsername = defaultUserData.getRandomUserData().getUsername();
        RegistrationPage.inputAndRefocusUsername(username);
        assertTrue(registrationPage.isSuggestionVisible(), "Suggestion visible second time");
        RegistrationPage.inputAndRefocusUsername(username);
        assertTrue(registrationPage.isSuggestionVisible(), "Suggestion visible third time");
        RegistrationPage.inputAndRefocusUsername(newUsername);
        assertFalse(registrationPage.isSuggestionVisible(), "Suggestion visible on valid email");
        RegistrationPage.inputAndRefocusUsername(username);
        assertTrue(registrationPage.isSuggestionVisible(), "Suggestion visible fourth time");
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
        ValidationUtils.assertValidationStatus(RegistrationPage.FIELD_USERNAME_NAME, ValidationUtils.STATUS_FAILED, registeredUserData.getUsername());
	}

    /*3. Try to use invalid bonus code*/
	@Test(groups = {"registration","regression"})
	public void registrationWithInvalidBonusCoupon() {
        UserData userData = defaultUserData.getRandomUserData();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage = (RegistrationPage) registrationPage.registerUser(userData, "invalid", Page.registrationPage);
        assertEquals("Coupon code is not found or not available", registrationPage.getPortletErrorMessage(), "Invalid bonus error message");
    }

//    /*#6. Try to clickLogin registration form without accepting T&C (without checking check box)*/
//	@Test(groups = {"regression"})
//	public void submitWithUncheckedTermsAndConditions(){
//		UserData userData=defaultUserData.getRandomUserData();
//		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
//		registrationPage=(RegistrationPage) registrationPage.registerUser(userData, false, false, false);
//		boolean checkboxTermsAndConditionsregressionErrorVisible=registrationPage.checkboxTermsAndConditionsregressionErrorVisible();
//        assertTrue(checkboxTermsAndConditionsregressionErrorVisible);
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
    @Test(groups = {"registration","regression"})
    public void emailConfirmationValidation(){
        String id = RegistrationPage.getEmailVerificationName();
        String email = defaultUserData.getRandomUserData().getEmail();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        if(WebDriverObject.getPlatform().equals(WebDriverObject.PLATFORM_DESKTOP)){
            registrationPage.registrationPageAllSteps().clickEmailConfirmation();
            ValidationUtils.assertValidationStatus(id, ValidationUtils.STATUS_NONE, "");
            ValidationUtils.assertTooltipStatus(id, ValidationUtils.STATUS_PASSED, "");
            ValidationUtils.assertTooltipText(id, "Please retype your email.", "");
        }
        registrationPage.fillEmail(email);
        ValidationUtils.inputFieldAndRefocus(RegistrationPage.getEmailVerificationXpath(), email);
        ValidationUtils.validateStatusAndToolTips(ValidationUtils.STATUS_NONE, id, email, ValidationUtils.STATUS_PASSED, ValidationUtils.STATUS_NONE);
    }

    @Test(groups = {"registration","regression"})
    public void emailDoNotMatch(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.fillEmail(defaultUserData.getRandomUserData().getEmail());
        registrationPage.fillEmailVerificationAndRefocus(emailValidationRule.generateValidString());
        assertEquals("Emails dont match", ValidationUtils.getTooltipText(RegistrationPage.getEmailVerificationName()), "Tooltip text");
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

    @Test(groups = {"registration","regression"})
    public void passwordConfirmationValidation(){
        String id = RegistrationPage.FIELD_PASSWORD_VERIFICATION_NAME;
        UserData generatedUserData=defaultUserData.getRandomUserData();
        String password = generatedUserData.getPassword();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        if(WebDriverObject.getPlatform().equals(WebDriverObject.PLATFORM_MOBILE)){
            registrationPage.registrationPageStepThree(generatedUserData);
        }
        registrationPage.fillPassword(password);
        registrationPage.fillPasswordVerificationAndRefocus("");
        ValidationUtils.assertValidationStatus(id, ValidationUtils.STATUS_FAILED, "empty");
        ValidationUtils.assertTooltipStatus(id, ValidationUtils.STATUS_FAILED, "empty");
        ValidationUtils.assertTooltipText(id, "Please retype your password.", "empty");
        registrationPage.fillPasswordVerificationAndRefocus(password);
        ValidationUtils.validateStatusAndToolTips(ValidationUtils.STATUS_NONE, id, password, ValidationUtils.STATUS_PASSED, ValidationUtils.STATUS_NONE);
    }

    /*#5. Password & Confirmation do not match*/
    @Test(groups = {"registration","regression"})
    public void passwordDoNotMatch(){
        UserData generatedUserData=defaultUserData.getRandomUserData();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        if(WebDriverObject.getPlatform().equals(WebDriverObject.PLATFORM_MOBILE)){
            registrationPage.registrationPageStepThree(generatedUserData);
        }
        registrationPage.fillPassword(generatedUserData.getPassword());
        String validPass = passwordValidationRule.generateValidString();
        registrationPage.fillPasswordVerificationAndRefocus(validPass);
        assertEquals("Passwords are not the same", ValidationUtils.getTooltipText(RegistrationPage.FIELD_PASSWORD_VERIFICATION_NAME), "Tooltip text for '"+validPass+"' -");
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
