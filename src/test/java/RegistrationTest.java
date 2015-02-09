import com.sun.jna.platform.unix.X11;
import enums.*;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.core.AbstractPortalPage;
import pageObjects.registration.*;
import pageObjects.registration.classic.RegistrationPageAllSteps;
import pageObjects.registration.threeStep.RegistrationPageStepOne;
import pageObjects.registration.threeStep.RegistrationPageStepThree;
import pageObjects.registration.threeStep.RegistrationPageStepTwo;
import springConstructors.UserData;
import utils.*;
import utils.core.AbstractTest;
import utils.core.DataContainer;
import utils.validation.ValidationUtils;

import java.util.List;

public class RegistrationTest extends AbstractTest{

	/*POSITIVE*/

	//*1. Valid user registration
	@Test(groups = {"registration","smoke","regression"})
	public void validUserRegistration() {
        HomePage homePage = PortalUtils.registerUser();
        validateTrue(homePage.isLoggedIn(), "User is logged in");
	}

    //*Copy paste email
    @Test(groups = {"registration","regression"})
    public void copyPasteEmail(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.core)) {
            registrationPage = registrationPage.registrationPageStepOne();
        }else {
            registrationPage = registrationPage.registrationPageAllSteps();
        }
        registrationPage.fillEmail(DataContainer.getUserData().getRandomUserData().getEmail());
        registrationPage.copyAndPasteEmail();
        assertEquals("", registrationPage.getEmailVerification(), "Email verification");
    }

    //*Copy paste password
    @Test(groups = {"registration","regression"})
    public void copyPastePassword(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.core)){
            registrationPage = registrationPage.registrationPageStepThree();
        }
        registrationPage.fillPassword("123asdASD");
        registrationPage.copyAndPastePassword();
        assertEquals("", registrationPage.getPasswordVerification(), "Password verification");
    }

    //*Copy paste password
    @Test(groups = {"registration", "mobile","regression"})
    public void navigationBetweenSteps(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        RegistrationPageStepThree registrationPageStepThree = registrationPage.registrationPageStepThree();
        RegistrationPageStepTwo registrationPageStepTwo = registrationPageStepThree.clickPrevious();
        RegistrationPageStepOne registrationPageStepOne = registrationPageStepTwo.clickPrevious();
    }

    //*Click next on every step without filled data, click submit without filled data
    @Test(groups = {"registration", "mobile","regression"})
    public void emptyRegisterAttempts(){
        UserData userData = DataContainer.getUserData().getRandomUserData();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        RegistrationPageStepOne registrationPageStepOne = registrationPage.registrationPageStepOne();
        registrationPageStepOne.clickNext();
        RegistrationPageStepTwo registrationPageStepTwo = new RegistrationPageStepOne().fillDataAndSubmit(userData);
        registrationPageStepTwo.clickNext();
        RegistrationPageStepThree registrationPageStepThree = new RegistrationPageStepTwo().fillDataAndSubmit(userData);
        registrationPageStepThree.clickSubmit();
        new RegistrationPageStepThree();
    }

    //* Frozen user registration
    @Test(groups = {"registration","regression"})
    public void frozenUserRegistration() {
        UserData userData = DataContainer.getUserData().getFrozenUserData();
        PortalUtils.registerUser(userData, Page.registrationPage);
        new FrozenNotificationPopup().clickAccept();
        new AbstractPortalPage();
        assertEquals(DataContainer.getDriverData().getCurrentUrl(), WebDriverUtils.getCurrentUrl(), "Player redirected to root");
    }


    //*#2. Receive bonuses check box default state and text
    @Test(groups = {"registration","regression"})
    public void receivePromotionOffersDefaultState(){
        UserData userData=DataContainer.getUserData().getRandomUserData();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.core)){
            registrationPage.registrationPageStepThree(userData);
        }
        assertTrue(registrationPage.getReceivePromotionsCheckboxState(), "Promotional checkbox is checked by default");
        assertEquals("I would like to receive great bonuses and exciting offers", registrationPage.getReceivePromotionsCheckboxText(), "Promotional offers checkbox text");
    }

	//*#2. Registration with receive bonuses check box checked
	@Test(groups = {"registration","regression"})
	public void receivePromotionOffersIsCheckedInIMS(){
		UserData userData=DataContainer.getUserData().getRandomUserData();
		PortalUtils.registerUser(userData,true);
		boolean receiveBonusesCheckedInIMS=IMSUtils.navigateToPlayedDetails(userData.getUsername()).getAllChannelsCheckboxState();
		validateTrue(receiveBonusesCheckedInIMS, "Promotion checkboxes are checked in IMS");
	}

    //*#3. Registration with receive bonuses check box unchecked
	@Test(groups = {"registration","regression"})
	public void receivePromotionOffersIsNotCheckedInIMS(){
		UserData userData=DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData,false);
		boolean receiveBonusesNotCheckedInIMS= IMSUtils.navigateToPlayedDetails(userData.getUsername()).getAllChannelsCheckboxState();
        validateFalse(receiveBonusesNotCheckedInIMS, "Promotion checkboxes are checked in IMS");
	}

    //*#4. Registration with bonus code provided
	@Test(groups = {"registration","regression"})
	public void registrationWithBonusCoupon(){
        UserData userData=DataContainer.getUserData().getRandomUserData();
        HomePage homePage = (HomePage) PortalUtils.registerUser(userData, true, true, PromoCode.valid, Page.homePage);
        validateEquals(PromoCode.valid.getAmount(), homePage.getBalanceAmount(), "User balance");
	}

    //*#5. Registration without bonus code provided
    @Test(groups = {"registration","regression"})
	public void registrationWithOutBonusCoupon(){
        UserData userData = DataContainer.getUserData().getRandomUserData();
        HomePage homePage = PortalUtils.registerUser(userData);
        validateEquals("0.00", homePage.getBalanceAmount(), "User balance");
    }

    //*3. Try to use invalid bonus code
    @Test(groups = {"registration","regression"})
    public void registrationWithInvalidBonusCoupon() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage = (RegistrationPage) registrationPage.registerUser(userData, PromoCode.invalid, Page.registrationPage);
        assertEquals(INVALID_BONUS_CODE_MESSAGE, registrationPage.getPortletErrorMessage(), "Invalid bonus error message");
    }

//    /*#6. Player is registered with currency selected*/
//	@Test(groups = {"registration","regression"})
//	public void validHeaderUnitBalance(){
//        String currency = "GBP";
//        String currencySign = "£";
//        UserData userData=DataContainer.getUserData().getRandomUserData();
//        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
//        String defaultCurrencyInRegistrationForm = registrationPage.getSelectedCurrency();
//        HomePage homePage=(HomePage)registrationPage.registerUser(userData, true, false, null, Page.homePage);
//        assertTrue(defaultCurrencyInRegistrationForm.contains(currency), "Default register currency is '"+currency+"'");
//        assertTrue(homePage.getBalance().contains(currencySign), "Currency sign in header is '"+currency+"'");
//	}

    //*#7. After registration web content
	@Test(groups = {"registration","regression"})
	public void afterRegistrationWebContent(){
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		UserData userData=DataContainer.getUserData().getRandomUserData();
		AfterRegistrationPopup afterRegistrationPopup=(AfterRegistrationPopup) registrationPage.registerUser(userData, Page.afterRegistrationPopup);
	}

    //*#10. After-registration redirect
	@Test(groups = {"registration","regression"})
	public void afterRegistrationRedirect(){
		PortalUtils.registerUser();
        AfterRegistrationPage afterRegistrationPage = new AfterRegistrationPage();
	}

    //*#11. The list of supported countries is correct
	@Test(groups = {"registration","regression"})
	public void countryList(){
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        assertEqualsCollections(DataContainer.getDefaults().getCountryCodesList(), registrationPage.getCountriesCodesList(DataContainer.getUserData().getRandomUserData()), "Country codes correspond with configuration");
	}

    //*#??.DESKTOP The list of supported nationalities is correct
    @Test(groups = {"registration","spanish", "desktop"})
    public void nationalityList(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        assertEqualsCollections(DataContainer.getDefaults().getCountryCodesList(), registrationPage.registrationPageAllSteps().getNationalitiesCodesList(),"Nationality codes correspond with configuration");
    }

    //*#12. The list of supported currencies is correct
	@Test(groups = {"registration","regression"})
	public void currencyList(){
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        assertEqualsCollections(DataContainer.getDefaults().getCurrencyCodesList(), registrationPage.getCurrencyList(DataContainer.getUserData().getRandomUserData()), "Currency codes correspond with configuration");
	}

    //*#13. T&C web content is shown when clicking on T&C link
	@Test(groups = {"regression"})
	public void openTermsAndConditionsPopup(){
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.core)){
            registrationPage = registrationPage.registrationPageStepThree(DataContainer.getUserData().getRegisteredUserData());
        }
		ReadTermsAndConditionsPopup readTermsAndConditionsPopup=registrationPage.navigateToTermsAndConditions();
	}

    //*#14. 18+ web content is shown when clicking on 18+ link
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
//            UserData userData=DataContainer.getUserData().getRandomUserData();
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
//            UserData userData=DataContainer.getUserData().getRandomUserData();
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
    //*#17. IMS Player Details Page
	@Test(groups = {"registration","regression"})
	public void verifyRegistrationDataIsShownCorrectlyInIMS(){
        UserData userData = DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData);
		IMSUtils.assertRegisterData(userData);
	}

    //*Client type poker
    @Test(groups = {"registration","regression"})
    public void registrationWithClientType(){
        UserData userData=DataContainer.getUserData().getRandomUserData();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.registerClientTypeCreferrer);
        registrationPage.registerUser(userData);
        validateEquals("poker", IMSUtils.getClientType(userData), "Client type");
    }

    //*Client type empty
    @Test(groups = {"registration","regression"})
    public void registrationWithoutClientType(){
        UserData userData=DataContainer.getUserData().getRandomUserData();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.registerNoClientType);
        registrationPage.registerUser(userData);
        validateEquals("casino", IMSUtils.getClientType(userData), "Client type");

    }

    //*#20. T&C check box is unchecked by default
	@Test(groups = {"registration","regression", "mobile"})
	public void defaultTnCCheckboxState(){
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        validateFalse(registrationPage.registrationPageStepThree(DataContainer.getUserData().getRandomUserData()).getTermsCheckbox(), "T&C checkbox checked by default");
	}

    //*#22. DESKTOP Country - countryCode code and phone prefix mapping
	@Test(groups = {"registration","regression", "desktop"})
	public void countryCodePhoneCodeMappingDesktop(){
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        RegistrationPageAllSteps registrationPageAllSteps = registrationPage.registrationPageAllSteps();
		for (String countryCode : DataContainer.getDefaults().getCountryCodesList()) {
            registrationPageAllSteps.fillCountry(countryCode);
            assertEquals("+" + DataContainer.getDefaults().getPhoneCodeByCountryCode(countryCode), registrationPageAllSteps.getPhoneAreaCode(), "Phone area code for '"+countryCode+"'");
		}
	}

    //*#??. Country - countryCode code and name mapping
    @Test(groups = {"registration","regression"})
    public void countryCodeNamePrefix(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        if (DataContainer.getDriverData().getLicensee().equals(Licensee.core)) {
            registrationPage.registrationPageStepTwo(DataContainer.getUserData().getRandomUserData());
        }
        for (String countryCode : DataContainer.getDefaults().getCountryCodesList()) {
            registrationPage.fillCountry(countryCode);
            assertEquals(DataContainer.getDefaults().getCountryNameByCountryCode(countryCode), registrationPage.getSelectedCountryName().trim(), "Country name for '"+countryCode+"'");
        }
    }

//    /*#23. Default selected countryCode*/
//    @Test(groups = {"registration","regression"})
//    public void defaultCountryMobile(){
//        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
//        if (DataContainer.getDriverData().getLicensee().equals(Licensee.core)) {
//            registrationPage.registrationPageStepTwo(DataContainer.getUserData().getRandomUserData());
//        }
//        assertEquals(DataContainer.getDefaults().getDefaultCountryName(), registrationPage.getSelectedCountryName(), "Default country name");
//        // assertTrue(registrationPage.isFindMyAddressButtonVisible());
//    }

    //* DESKTOP Password strength
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
//		String defaultCountryCode = DataContainer.getDefaults().getDefaultCountry();
//		do {
//			randomCountryCode = DataContainer.getDefaults().getRandomCountryCode();
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

    /*26. Check if Find button is visible*/
    @Test(groups = {"registration","regression"})
    public void isButtonFindVisible() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        RegistrationPageStepTwo registrationPageStepTwo = registrationPage.registrationPageStepTwo();
        registrationPageStepTwo.selectUKCountry();
        assertTrue(registrationPageStepTwo.isFindButtonVisible(), "Button 'Find' visibility when selected country is UK");
        registrationPageStepTwo.fillCountry("RU");
        assertFalse(registrationPageStepTwo.isFindButtonVisible(), "Button 'Find' visibility when selected country is not UK");
    }

    /*27. Enter a valid postcode which refer to 1-6 addresses. Click "Find"*/
    @Test(groups = {"registration","regression"})
    public void enterValidPostcodeWhichReferToOneSixAddresses() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        RegistrationPageStepTwo registrationPageStepTwo = registrationPage.registrationPageStepTwo();
        registrationPageStepTwo.selectUKCountry();
        registrationPageStepTwo.fillPostcodeWithOneSixAddresses();
        registrationPageStepTwo.clickFind();
        registrationPageStepTwo.selectRandomAddressFromDropdown();
        assertTrue(registrationPageStepTwo.isAddressFieldsEditableAndEmpty(), "Address fields are editable and not empty");
    }

    /*28. Enter a valid postcode which refer to many addresses. Click "Find"*/
    @Test(groups = {"registration","regression"})
    public void enterValidPostcodeWhichReferToManyAddresses() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        RegistrationPageStepTwo registrationPageStepTwo = registrationPage.registrationPageStepTwo();
        registrationPageStepTwo.selectUKCountry();
        registrationPageStepTwo.fillPostcodeWithManyAddresses();
        registrationPageStepTwo.clickFind();
        registrationPageStepTwo.selectRandomAddressFromDropdown();
        assertTrue(registrationPageStepTwo.isAddressFieldsEditableAndEmpty(), "Address fields are editable and not empty");
    }

    /*DEPOSIT LIMITS*/

    /*29. Deposit limits dropdowns visibility*/
    @Test(groups = {"registration","regression"})
    public void depositLimitsFieldsVisibility() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.registerDepositLimits);
        RegistrationPageStepThree registrationPageStepThree = registrationPage.registrationPageStepThree();
        registrationPageStepThree.isDepositLimitsDropdownsVisible();
    }

    /*30. Deposit limits. Check data on IMS */
    @Test(groups = {"registration","regression"})
    public void isDepositLimitsSavedToIMS(){
        UserData userData = DataContainer.getUserData().getRandomUserData();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.registerDepositLimits);
        RegistrationPageStepThree registrationPageStepThree = registrationPage.registrationPageStepThree(userData);
        registrationPageStepThree.setDepositLimits(1, 2, 3);
        String dayLimit = registrationPageStepThree.getSelectedDailyDepositLimit();
        String weekLimit = registrationPageStepThree.getSelectedWeeklyDepositLimit();
        String monthLimit = registrationPageStepThree.getSelectedMonthlyDepositLimit();
        registrationPageStepThree.fillDataAndSubmit(userData, true, true, null);
        registrationPageStepThree.validateDepositLimitsIMS(userData.getUsername(), dayLimit, weekLimit, monthLimit);
    }

    //*B-11233 this BUG CLOSED
    //*1 DESKTOP
    @Test(groups = {"registration", "regression", "desktop"})
    public void countryCurrencySinglePageRegistration(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        for (String countryCode : DataContainer.getDefaults().getCountryCodesList()) {
            registrationPage.fillCountry(countryCode);
            assertEquals(DataContainer.getDefaults().getCurrencyByCountryCode(countryCode), registrationPage.getSelectedCurrency(), "Currency for "+DataContainer.getDefaults().getCountryNameByCountryCode(countryCode) +" (" + countryCode+")");
        }
    }

    //*2
    @Test(groups = {"registration", "regression", "mobile"})
    public void countryCurrencyThreeStepRegistration(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        RegistrationPageStepThree registrationPageStepThree = registrationPage.registrationPageStepThree(DataContainer.getUserData().getRandomUserData());
        RegistrationPageStepTwo registrationPageStepTwo;
        for (String countryCode : DataContainer.getDefaults().getCountryCodesList()) {
            registrationPageStepTwo = registrationPageStepThree.clickPrevious();
            registrationPage.fillCountry(countryCode);
            registrationPage.fillCity(DataContainer.getUserData().getCity());
            registrationPage.fillAddress(DataContainer.getUserData().getAddress());
            registrationPageStepTwo.clickNext();
            assertEquals(DataContainer.getDefaults().getCurrencyByCountryCode(countryCode), registrationPage.getSelectedCurrency(), "Currency for "+DataContainer.getDefaults().getCountryNameByCountryCode(countryCode) +" (" + countryCode+")");
        }
    }

    //*B-13316 this BUG CLOSED
    //*1
    @Test(groups = {"registration", "regression", "desktop"})
    public void birthDayYearChangingMechanism(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        List<String> availableBirthYears = registrationPage.getBirthYearList();
        int currentYear = DateUtils.getCurrentYear();
        String firstYear = String.valueOf(currentYear - 18);
        String lastYear = String.valueOf(currentYear - 100);
        assertEquals(84, availableBirthYears.size(), "Number of available options in birth year dropdown.");
        assertEquals(firstYear, availableBirthYears.get(1), "First available option in birth year dropdown.");
        assertEquals(lastYear, availableBirthYears.get(83), "Last available option in birth year dropdown.");
    }

    //*B-11951 this BUG CLOSED
    //*1
    @Test(groups = {"registration", "regression", "admin"})
    public void sendDeviceIdToIMSOnRegistration(){
        UserData userData = DataContainer.getUserData().getRandomUserData();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.registerUser(userData);
        String deviceIdExpected = TypeUtils.generateDeviceId(userData.getUsername()) + "WEB";
        String deviceIdOnIMS = IMSUtils.navigateToPlayedDetails(userData.getUsername()).getDeviceIdRegistration();
        assertEquals(deviceIdExpected, deviceIdOnIMS, "Device Id value in IMS.");
    }

    //*2
    @Test(groups = {"registration", "regression", "admin"})
    public void unableToRegisterFromSameDevice(){
        PortalUtils.registerUser();
        PortalUtils.logout();
        UserData userData = DataContainer.getUserData().getRandomUserData();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.registerUser(userData, Page.registrationPage);
        assertEquals("Real money casino account already exists with this serial and signup remote ip combination.", registrationPage.getPortletErrorMessage(), "Error message text on try to register new player from same device." );
    }

    /*NEGATIVE*/

    //*#1. Try to use already existing username
	@Test(groups = {"registration","regression"})
	public void alreadyUsedUsername(){
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		UserData generatedUserData=DataContainer.getUserData().getRandomUserData();
		UserData registeredUserData=DataContainer.getUserData().getRegisteredUserData();
		generatedUserData.setUsername(registeredUserData.getUsername());
		registrationPage = (RegistrationPage) registrationPage.registerUser(generatedUserData, Page.registrationPage);
        ValidationUtils.assertValidationStatus(RegistrationPage.FIELD_USERNAME_NAME, ValidationUtils.STATUS_FAILED, registeredUserData.getUsername());
	}

    //*#6. Try to register without accepting T&C (without checking check box)
	@Test(groups = {"regression"})
	public void submitWithUncheckedTermsAndConditions(){
        RegistrationPage registrationPage = (RegistrationPage) PortalUtils.registerUser(DataContainer.getUserData().getRandomUserData(), false, true);
        assertTrue(registrationPage.isPortletErrorVisible(), "T&C are not accepted error visible");
	}
}
