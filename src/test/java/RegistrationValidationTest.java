import enums.ConfiguredPages;
import enums.Licensee;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.registration.RegistrationPage;
import springConstructors.UserData;
import springConstructors.ValidationRule;
import utils.NavigationUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;
import utils.validation.ValidationUtils;

public class RegistrationValidationTest extends AbstractTest{

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
	@Qualifier("mobileCountryPhoneCodeValidationRule")
	private ValidationRule mobileCountryPhoneCodeValidationRule;

	@Autowired
	@Qualifier("mobilePhoneValidationRule")
	private ValidationRule mobilePhoneValidationRule;

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

    /*#19. All required fields are marked with asterisks*/
    @Test(groups = {"registration","regression", "desktop"})
    public void requiredFieldsLabelsMarkedWithStar(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        validateTrue(registrationPage.registrationPageAllSteps().labelsRequiredMarkingCorrect(), "T&C validation error visible");
    }

    @Test(groups = {"registration","regression","validation"})
    public void genderDropdownValidation() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.validateGender(genderValidationRule);
    }

	@Test(groups = {"registration","regression","validation"})
	public void firstnameFieldValidation() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validateFirstname(firstNameValidationRule);
	}

    @Test(groups = {"registration","regression","validation"})
    public void lastnameFieldValidation() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.validateLastname(lastNameValidationRule);
    }

    @Test(groups = {"registration","regression","validation"})
    public void dateOfBirthValidation() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.validateDateOfBirth(dateOfBirthValidationRule);
    }

    @Test(groups = {"registration","regression","validation"})
    public void dateOfBirthValues() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.fillBirthYear("1992");
        registrationPage.fillBirthMonth("02");
        assertTrue(registrationPage.getDays().contains("29"), "Leap year contains Feb 29");
        registrationPage.fillBirthYear("1991");
        registrationPage.fillBirthMonth("02");
        assertFalse(registrationPage.getDays().contains("29"), "Non-Leap year contains Feb 29");
    }

	@Test(groups = {"registration","regression","validation"})
	public void emailFieldValidation() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validateEmail(emailValidationRule);
	}

    /*#4. Email and email confirmation do not match*/
    @Test(groups = {"registration","regression","validation"})
    public void emailConfirmationValidation(){
        String id = RegistrationPage.getEmailVerificationName();
        String email = DataContainer.getUserData().getRandomUserData().getEmail();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.sevenRegal)){
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
        registrationPage.fillEmail(DataContainer.getUserData().getRandomUserData().getEmail());
        registrationPage.fillEmailVerificationAndRefocus(emailValidationRule.generateValidString());
        assertEquals("Emails dont match", ValidationUtils.getTooltipText(RegistrationPage.getEmailVerificationName()), "Tooltip text");
    }

    @Test(groups = {"registration","regression"})
    public void stateFieldValidation() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.validateState(stateValidationRule, DataContainer.getUserData().getRandomUserData());
    }

    @Test(groups = {"registration","regression"})
    public void countryDropdownValidation() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.registerNoClientType);
        registrationPage.validateCountry(countryValidationRule,DataContainer.getUserData().getRandomUserData());
    }

	@Test(groups = {"registration","regression"})
	public void cityFieldValidation() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validateCity(cityValidationRule,DataContainer.getUserData().getRandomUserData());
	}

	@Test(groups = {"registration","regression"})
     public void address1FieldValidation() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.validateAddress(addressValidationRule,DataContainer.getUserData().getRandomUserData());
    }

    @Test(groups = {"registration","regression"})
    public void address2FieldValidation() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.validateAddress2(address2ValidationRule,DataContainer.getUserData().getRandomUserData());
    }

    @Test(groups = {"registration","regression"})
    public void houseFieldValidation() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.validateHouse(houseValidationRule,DataContainer.getUserData().getRandomUserData());
    }

	@Test(groups = {"registration","regression"})
	public void postcodeFieldValidation() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validatePostcode(postcodeValidationRule,DataContainer.getUserData().getRandomUserData());
	}

    @Test(groups = {"registration","regression"})
    public void phoneAreaCodeFieldValidation() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.validatePhoneAreaCode(mobileCountryPhoneCodeValidationRule, DataContainer.getUserData().getRandomUserData());
    }

	@Test(groups = {"registration","regression"})
	public void phoneFieldValidation() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validatePhone(mobilePhoneValidationRule, DataContainer.getUserData().getRandomUserData());
	}

	@Test(groups = {"registration","regression","desktop"})
	public void usernameFieldValidation() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validateUsername(usernameValidationRule,DataContainer.getUserData().getRandomUserData());
	}

	@Test(groups = {"registration","regression"})
	public void passwordFieldValidation() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validatePassword(passwordValidationRule,DataContainer.getUserData().getRandomUserData());
	}

    @Test(groups = {"registration","regression"})
    public void passwordConfirmationValidation(){
        String id = RegistrationPage.FIELD_PASSWORD_VERIFICATION_NAME;
        UserData generatedUserData=DataContainer.getUserData().getRandomUserData();
        String password = generatedUserData.getPassword();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.core)){
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
        UserData generatedUserData=DataContainer.getUserData().getRandomUserData();
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.core)){
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
//        registrationPage.validateQuestion(questionValidationRule,DataContainer.getUserData().getRandomUserData());
//    }

    @Test(groups = {"registration","regression"})
    public void answerFieldValidation() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.validateAnswer(answerValidationRule,DataContainer.getUserData().getRandomUserData());
    }

    @Test(groups = {"registration","regression"})
    public void currencyDropdownValidation() {
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.validateCurrency(currencyValidationRule,DataContainer.getUserData().getRandomUserData());
    }

	@Test(groups = {"registration","regression"})
	public void bonusCodeFieldValidation() {
		RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
		registrationPage.validateBonusCode(bonusCodeValidationRule,DataContainer.getUserData().getRandomUserData());
	}
}
