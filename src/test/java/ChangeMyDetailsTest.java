import enums.LogCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.account.ChangeMyDetailsPage;
import pageObjects.account.MyAccountPage;
import pageObjects.registration.RegistrationPage;
import springConstructors.Defaults;
import springConstructors.IMS;
import springConstructors.UserData;
import springConstructors.validation.ValidationRule;
import testUtils.AbstractTest;
import utils.NavigationUtils;
import utils.WebDriverUtils;
import utils.logs.Log;
import utils.logs.LogEntry;
import utils.logs.LogUtils;

/**
 * User: sergiich
 * Date: 4/10/14
 */
public class ChangeMyDetailsTest extends AbstractTest{

	@Autowired
	@Qualifier("iMS")
	private IMS iMS;

	@Autowired
	@Qualifier("emailValidationRule")
	private ValidationRule emailValidationRule;

	@Autowired
	@Qualifier("fullPhoneValidationRule")
	private ValidationRule phoneValidationRule;

	@Autowired
	@Qualifier("fullAddressValidationRule")
	private ValidationRule addressValidationRule;

	@Autowired
	@Qualifier("postcodeValidationRule")
	private ValidationRule postcodeValidationRule;


	@Autowired
	@Qualifier("cityValidationRule")
	private ValidationRule cityValidationRule;

	@Autowired
	@Qualifier("defaults")
	private Defaults countryList;

	@Autowired
	@Qualifier("userData")
	private UserData defaultUserData;

	/*POSITIVE*/

	/* 1. Portlet is displayed */
	@Test(groups = {"smoke"})
	public void portletIsDisplayedOnMyAccountChangeMyDetailsPage() {
		UserData userData = defaultUserData.getRegisteredUserData();
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		homePage = (HomePage)homePage.login(userData);
		MyAccountPage myAccountPage = homePage.navigateToMyAccount();
		ChangeMyDetailsPage changeMyDetailsPage = myAccountPage.navigateToChangeMyDetails();
	}

	/* 2. Correct User Details are displayed by default */
	@Test(groups = {"regression"})
	public void userInfoShownCorrectly(){
		//New user registration
		HomePage homePage=NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage=homePage.navigateToRegistration();
		UserData userData=defaultUserData.getRandomUserData();
		homePage=registrationPage.registerUser(userData);
		//Navigate to Change My Details page
		MyAccountPage myAccountPage=homePage.navigateToMyAccount();
		ChangeMyDetailsPage changeMyDetailsPage=myAccountPage.navigateToChangeMyDetails();
		// Check if player data equals to that used during registration
		boolean detailsDefault=changeMyDetailsPage.detailsAreEqualsTo(userData);
		// If user data is the same player registered with then the test returns PASS
		Assert.assertTrue(detailsDefault);
	}

	/* 3. Player updates his details with valid values and new values are saved */
	@Test(groups = {"regression"})
	public void userInfoEditableSavedCorrectly(){
		//New user registration
		HomePage homePage=NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage=homePage.navigateToRegistration();
		UserData userData=defaultUserData.getRandomUserData();
		homePage=registrationPage.registerUser(userData);
		//Navigate to Change My Details page
		MyAccountPage myAccountPage=homePage.navigateToMyAccount();
		ChangeMyDetailsPage changeMyDetailsPage=myAccountPage.navigateToChangeMyDetails();
		//Generate new player details and fill in UMD fields with new data
		userData = defaultUserData.getRandomUserData();
		userData.setEmail(emailValidationRule.generateValidString());
		changeMyDetailsPage.editDetails(userData);
		//Check that details are updated
		boolean detailsUpdatedSuccessfully=changeMyDetailsPage.detailsAreEqualsTo(userData);
		//Check that success message appeared
		boolean messageAppeared=changeMyDetailsPage.isVisibleConfirmationMessage();
		Assert.assertTrue(detailsUpdatedSuccessfully && messageAppeared);
	}

	/* 4. Player updates his details, logs out, logs in again and new values are displayed */
	@Test(groups = {"regression"})
	public void editUserInfoAndCheckIfSavedAfterLogout(){
		//New user registration
		HomePage homePage=NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage=homePage.navigateToRegistration();
		UserData userData=defaultUserData.getRandomUserData();
		homePage=registrationPage.registerUser(userData);
		//Navigate to Change My Details page
		MyAccountPage myAccountPage=homePage.navigateToMyAccount();
		ChangeMyDetailsPage changeMyDetailsPage=myAccountPage.navigateToChangeMyDetails();
		//Change player details
		UserData userData2 = defaultUserData.getRandomUserData();
		userData2.setEmail(emailValidationRule.generateValidString());
		changeMyDetailsPage.editDetails(userData2);
		// Check that details have been changed
		boolean detailsUpdatedSuccessfully=changeMyDetailsPage.detailsAreEqualsTo(userData2);
		//Check that success message appeared
		boolean messageAppeared=changeMyDetailsPage.isVisibleConfirmationMessage();
		// Player logs out and logs in again
		homePage=NavigationUtils.navigateToHome();
		homePage=(HomePage)homePage.logout();
		homePage=(HomePage)homePage.login(userData);
		myAccountPage=homePage.navigateToMyAccount();
		changeMyDetailsPage=myAccountPage.navigateToChangeMyDetails();
		boolean detailsKeptAfterRelogin =changeMyDetailsPage.detailsAreEqualsTo(userData2);
		Assert.assertTrue(detailsUpdatedSuccessfully && messageAppeared && detailsKeptAfterRelogin);
	}

	/* 5. If player clicks “Update Details” without having changed any data then success message is displayed but changes are not saved */
	@Test(groups = {"regression"})
	public void userInfoNotChangedIfNoChangesSaved(){
		//Player logs in
		HomePage homePage=NavigationUtils.navigateToPortal(true);
		UserData userData=defaultUserData.getRegisteredUserData();
		homePage=(HomePage)homePage.login(userData);
		// navigate to Change My Details Page
		MyAccountPage myAccountPage=homePage.navigateToMyAccount();
		ChangeMyDetailsPage changeMyDetailsPage=myAccountPage.navigateToChangeMyDetails();
		// Click Save button without applying any changes
		changeMyDetailsPage.submitChanges();
		// Check that success message is displayed
		boolean messageAppeared=changeMyDetailsPage.isVisibleConfirmationMessage();
		//Check that no changes are applied
		boolean detailsNotUpdated =changeMyDetailsPage.detailsAreEqualsTo(userData);
		//If message was displayed but user data was not changed then test passes
		Assert.assertTrue(messageAppeared && detailsNotUpdated);
	}

	/*6. Player performs several consecutive updates of UMD portlet */
	@Test(groups = {"regression"})
	public void userInfoChangedDuringConsecutiveUpdates() {
		//New user registration
		HomePage homePage=NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage=homePage.navigateToRegistration();
		UserData userData=defaultUserData.getRandomUserData();
		homePage=registrationPage.registerUser(userData);
		//Navigate to Change My Details page
		MyAccountPage myAccountPage=homePage.navigateToMyAccount();
		ChangeMyDetailsPage changeMyDetailsPage=myAccountPage.navigateToChangeMyDetails();
		//Change player details for the 1st time
		userData = defaultUserData.getRandomUserData();
		userData.setEmail(emailValidationRule.generateValidString());
		changeMyDetailsPage.editDetails(userData);
		// Check that details have been changed
		boolean detailsChanged1=changeMyDetailsPage.detailsAreEqualsTo(userData);
		//Check that success message appeared
		boolean messageAppeared1=changeMyDetailsPage.isVisibleConfirmationMessage();

		//Change player details for the second time
		userData = defaultUserData.getRandomUserData();
		userData.setEmail(emailValidationRule.generateValidString());
		changeMyDetailsPage.editDetails(userData);
		// Check that details have been changed
		boolean detailsChanged2=changeMyDetailsPage.detailsAreEqualsTo(userData);
		//Check that success message appeared
		boolean messageAppeared2=changeMyDetailsPage.isVisibleConfirmationMessage();
		Assert.assertTrue(messageAppeared1 && messageAppeared2 && detailsChanged1 && detailsChanged2);
	}

	/*7. Logs*/
	@Test(groups = {"regression"})
	public void logsChangeDetails(){
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
		HomePage homePage=NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage=homePage.navigateToRegistration();
		homePage=registrationPage.registerUser(userData);
		ChangeMyDetailsPage changeMyDetailsPage = homePage.navigateToMyAccount().navigateToChangeMyDetails();
		changeMyDetailsPage.editDetails(userData);
		Log log = LogUtils.getCurrentLogs(logCategories);
		log.doResponsesContainErrors();
		LogEntry request = log.getEntry(LogCategory.SetPlayerInfoRequest);
		request.containsParameters(parameters);
	}

	/*8. IMS player details are updated*/
	@Test(groups = {"regression"})
	public void iMSPlayerInfoIsUpdatedAfterPlayerDetailsChanged() {
		//New user registration
		HomePage homePage=NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage=homePage.navigateToRegistration();
		UserData userData=defaultUserData.getRandomUserData();
		homePage=registrationPage.registerUser(userData);
		//Navigate to Change My Details page
		MyAccountPage myAccountPage=homePage.navigateToMyAccount();
		ChangeMyDetailsPage changeMyDetailsPage=myAccountPage.navigateToChangeMyDetails();
		//Change player details
		String username = userData.getUsername();
		userData = defaultUserData.getRandomUserData();
		userData.setUsername(username);
		userData.setEmail(emailValidationRule.generateValidString());
		changeMyDetailsPage.editDetails(userData);
		// Check that details have been changed
		boolean detailsUpdatedSuccessfully=changeMyDetailsPage.detailsAreEqualsTo(userData);
		//Check that success message appeared
		boolean messageAppeared=changeMyDetailsPage.isVisibleConfirmationMessage();
		//Check user details are changed on IMS
		boolean iMSDetailsCoincide =  iMS.validateRegisterData(userData);
		Assert.assertTrue(detailsUpdatedSuccessfully && messageAppeared && iMSDetailsCoincide);
	}

	/*NEGATIVE CASES*/

    /*1. Email and confirmation do not match */
	@Test(groups = {"regression"})
	public void errorTooltipWhenEmailAndConfirmationDoNotMatch () {
		//New user registration
		HomePage homePage=NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage=homePage.navigateToRegistration();
		UserData userData=defaultUserData.getRandomUserData();
		homePage=registrationPage.registerUser(userData);
		//Navigate to Change My Details page
		MyAccountPage myAccountPage=homePage.navigateToMyAccount();
		ChangeMyDetailsPage changeMyDetailsPage=myAccountPage.navigateToChangeMyDetails();
		//Change player details
		String email = emailValidationRule.generateValidString();
		String emailConfirmation = email.concat("a");
		changeMyDetailsPage.editEmail(email);
		changeMyDetailsPage.editEmailVerification(emailConfirmation);
		changeMyDetailsPage.submitChanges();
		changeMyDetailsPage.clickEmailField();
		String errorMessageText=changeMyDetailsPage.getTooltipMessageText();
		boolean correctErrorTooltipIsDisplayed=errorMessageText.equals("This address is different from the one above, please correct");
		Assert.assertTrue(correctErrorTooltipIsDisplayed);
	}

	/*VALIDATION CASES*/

	/*1. Required fields are empty >> error tooltip is displayed for each required field*/
	@Test(groups = {"validation"})
	public void validationRequiredFieldsEmpty(){
		//New user registration
		HomePage homePage=NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage=homePage.navigateToRegistration();
		UserData userData=defaultUserData.getRandomUserData();
		homePage=registrationPage.registerUser(userData);
		//Navigate to Change My Details page
		MyAccountPage myAccountPage=homePage.navigateToMyAccount();
		ChangeMyDetailsPage changeMyDetailsPage=myAccountPage.navigateToChangeMyDetails();
		//Try to submit UMD form with empty required fields
		userData.setHouse("");
		userData.setAddress("");
		userData.setAddress2("");
		userData.setCity("");
		userData.setPostCode("");
		userData.setPhoneAreaCode("");
		userData.setPhone("");
		userData.setMobileAreaCode("");
		userData.setMobile("");
		userData.setEmail("");
		changeMyDetailsPage.editDetails(userData);
		int validationErrorsCount= WebDriverUtils.getXpathCount(changeMyDetailsPage.VALIDATION_ERROR_XP);
		Assert.assertTrue(validationErrorsCount >= 6);
	}

	/*2. Address field validation*/
	@Test(groups = {"validation"})
	public void addressFieldValidation() {
		//Player logs in
		HomePage homePage=NavigationUtils.navigateToPortal(true);
		UserData userData=defaultUserData.getRegisteredUserData().cloneUserData();
		homePage=(HomePage)homePage.login(userData);
		//Navigate to Change My Details page
		MyAccountPage myAccountPage=homePage.navigateToMyAccount();
		ChangeMyDetailsPage changeMyDetailsPage=myAccountPage.navigateToChangeMyDetails();
		//validation
		changeMyDetailsPage.validateAddress(addressValidationRule);
	}

	/*3. City field validation*/
	@Test(groups = {"validation"})
	public void cityFieldValidation() {
		//Player logs in
		HomePage homePage=NavigationUtils.navigateToPortal(true);
		UserData userData=defaultUserData.getRegisteredUserData().cloneUserData();
		homePage=(HomePage)homePage.login(userData);
		//Navigate to Change My Details page
		MyAccountPage myAccountPage=homePage.navigateToMyAccount();
		ChangeMyDetailsPage changeMyDetailsPage=myAccountPage.navigateToChangeMyDetails();
		//validation
		changeMyDetailsPage.validateCity(cityValidationRule);
	}

	/*4. Post Code field validation*/
	@Test(groups = {"validation"})
	public void postCodeFieldValidation() {
		//Player logs in
		HomePage homePage=NavigationUtils.navigateToPortal(true);
		UserData userData=defaultUserData.getRegisteredUserData().cloneUserData();
		homePage=(HomePage)homePage.login(userData);
		//Navigate to Change My Details page
		MyAccountPage myAccountPage=homePage.navigateToMyAccount();
		ChangeMyDetailsPage changeMyDetailsPage=myAccountPage.navigateToChangeMyDetails();
		//validation
		changeMyDetailsPage.validatePostcode(postcodeValidationRule);
	}

	/*5. Phone field validation*/
	@Test(groups = {"validation"})
	public void phoneFieldValidation() {
		//Player logs in
		HomePage homePage=NavigationUtils.navigateToPortal(true);
		UserData userData=defaultUserData.getRegisteredUserData().cloneUserData();
		homePage=(HomePage)homePage.login(userData);
		//Navigate to Change My Details page
		MyAccountPage myAccountPage=homePage.navigateToMyAccount();
		ChangeMyDetailsPage changeMyDetailsPage=myAccountPage.navigateToChangeMyDetails();
		//validation
		changeMyDetailsPage.validatePhone(phoneValidationRule);
	}

	/*6. Mobile Field validation*/
	@Test(groups = {"validation"})
	public void mobileFieldValidation() {
		//Player logs in
		HomePage homePage=NavigationUtils.navigateToPortal(true);
		UserData userData=defaultUserData.getRegisteredUserData().cloneUserData();
		homePage=(HomePage)homePage.login(userData);
		//Navigate to Change My Details page
		MyAccountPage myAccountPage=homePage.navigateToMyAccount();
		ChangeMyDetailsPage changeMyDetailsPage=myAccountPage.navigateToChangeMyDetails();
		//validation
		changeMyDetailsPage.validateMobile(phoneValidationRule);
	}

	/*7. Email field validation*/
	@Test(groups = {"validation"})
	public void emailFieldValidation() {
		//Player logs in
		HomePage homePage=NavigationUtils.navigateToPortal(true);
		UserData userData=defaultUserData.getRegisteredUserData().cloneUserData();
		homePage=(HomePage)homePage.login(userData);
		//Navigate to Change My Details page
		MyAccountPage myAccountPage=homePage.navigateToMyAccount();
		ChangeMyDetailsPage changeMyDetailsPage=myAccountPage.navigateToChangeMyDetails();
		//validation
		changeMyDetailsPage.validateEmail(emailValidationRule);
	}

	/*8. Email Verification field validation*/
	@Test(groups = {"validation"})
	public void verificationEmailFieldValidation() {
		//Player logs in
		HomePage homePage=NavigationUtils.navigateToPortal(true);
		UserData userData=defaultUserData.getRegisteredUserData().cloneUserData();
		homePage=(HomePage)homePage.login(userData);
		//Navigate to Change My Details page
		MyAccountPage myAccountPage=homePage.navigateToMyAccount();
		ChangeMyDetailsPage changeMyDetailsPage=myAccountPage.navigateToChangeMyDetails();
		//validation
		changeMyDetailsPage.validateVerificationEmail(emailValidationRule);
	}

}
