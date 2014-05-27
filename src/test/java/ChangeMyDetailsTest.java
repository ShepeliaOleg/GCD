import enums.ConfiguredPages;
import enums.LogCategory;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.account.ChangeMyDetailsPage;
import springConstructors.Defaults;
import springConstructors.IMS;
import springConstructors.UserData;
import springConstructors.validation.ValidationRule;
import testUtils.AbstractTest;
import utils.NavigationUtils;
import utils.PortalUtils;
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
		ChangeMyDetailsPage changeMyDetailsPage = (ChangeMyDetailsPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.changeMyDetails, defaultUserData.getRegisteredUserData());
	}

	/* 2. Correct User Details are displayed by default */
	@Test(groups = {"regression"})
	public void userInfoShownCorrectly(){
        UserData userData=defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
		ChangeMyDetailsPage changeMyDetailsPage=(ChangeMyDetailsPage) NavigationUtils.navigateToPage(ConfiguredPages.changeMyDetails);
		Assert.assertTrue(changeMyDetailsPage.detailsAreEqualsTo(userData));
	}

	/* 3. Player updates his details with valid values and new values are saved */
	@Test(groups = {"regression"})
	public void userInfoEditableSavedCorrectly(){
        UserData userData=defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        ChangeMyDetailsPage changeMyDetailsPage=(ChangeMyDetailsPage) NavigationUtils.navigateToPage(ConfiguredPages.changeMyDetails);
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
        UserData userData=defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        ChangeMyDetailsPage changeMyDetailsPage=(ChangeMyDetailsPage) NavigationUtils.navigateToPage(ConfiguredPages.changeMyDetails);
		userData = defaultUserData.getRandomUserData();
		userData.setEmail(emailValidationRule.generateValidString());
		changeMyDetailsPage.editDetails(userData);
		// Check that details have been changed
		boolean detailsUpdatedSuccessfully=changeMyDetailsPage.detailsAreEqualsTo(userData);
		//Check that success message appeared
		boolean messageAppeared=changeMyDetailsPage.isVisibleConfirmationMessage();
		// Player logs out and logs in again
        changeMyDetailsPage=(ChangeMyDetailsPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.changeMyDetails, userData);
		boolean detailsKeptAfterRelogin =changeMyDetailsPage.detailsAreEqualsTo(userData);
		Assert.assertTrue(detailsUpdatedSuccessfully && messageAppeared && detailsKeptAfterRelogin);
	}

	/* 5. If player clicks “Update Details” without having changed any data then success message is displayed but changes are not saved */
	@Test(groups = {"regression"})
	public void userInfoNotChangedIfNoChangesSaved(){
        UserData userData=defaultUserData.getRegisteredUserData();
        ChangeMyDetailsPage changeMyDetailsPage=(ChangeMyDetailsPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.changeMyDetails, userData);
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
        UserData userData=defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        ChangeMyDetailsPage changeMyDetailsPage=(ChangeMyDetailsPage) NavigationUtils.navigateToPage(ConfiguredPages.changeMyDetails);
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
        UserData userData=defaultUserData.getRandomUserData();
        LogCategory[] logCategories = new LogCategory[]{LogCategory.SetPlayerInfoRequest, LogCategory.SetPlayerInfoResponse};
		String[] parameters = {"objectIdentity="+userData.getUsername()+"-playtech81001",
				"KV(1, playtech81001)",
				"KV(2, "+userData.getUsername()+")",
				"KV(7, "+userData.getCity()+")",
				"KV(19, "+userData.getEmail()+")",
				"KV(21, "+userData.getFirstName()+")",
				"KV(24, "+userData.getLastName()+")",
				"KV(27, "+userData.getPhoneAreaCode()+userData.getPhone()+")",
				"KV(34, "+userData.getPostCode()+")"};
		PortalUtils.registerUser(userData);
        ChangeMyDetailsPage changeMyDetailsPage=(ChangeMyDetailsPage) NavigationUtils.navigateToPage(ConfiguredPages.changeMyDetails);
		changeMyDetailsPage.editDetails(userData);
		Log log = LogUtils.getCurrentLogs(logCategories);
		log.doResponsesContainErrors();
		LogEntry request = log.getEntry(LogCategory.SetPlayerInfoRequest);
		request.containsParameters(parameters);
	}

	/*8. IMS player details are updated*/
	@Test(groups = {"regression"})
	public void iMSPlayerInfoIsUpdatedAfterPlayerDetailsChanged() {
        UserData userData=defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        ChangeMyDetailsPage changeMyDetailsPage=(ChangeMyDetailsPage) NavigationUtils.navigateToPage(ConfiguredPages.changeMyDetails);
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
        UserData userData=defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        ChangeMyDetailsPage changeMyDetailsPage=(ChangeMyDetailsPage) NavigationUtils.navigateToPage(ConfiguredPages.changeMyDetails);
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
        UserData userData=defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        ChangeMyDetailsPage changeMyDetailsPage=(ChangeMyDetailsPage) NavigationUtils.navigateToPage(ConfiguredPages.changeMyDetails);
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
                ChangeMyDetailsPage changeMyDetailsPage = (ChangeMyDetailsPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.changeMyDetails, defaultUserData.getRegisteredUserData());
		changeMyDetailsPage.validateAddress(addressValidationRule);
	}

	/*3. City field validation*/
	@Test(groups = {"validation"})
	public void cityFieldValidation() {
                ChangeMyDetailsPage changeMyDetailsPage = (ChangeMyDetailsPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.changeMyDetails, defaultUserData.getRegisteredUserData());
		changeMyDetailsPage.validateCity(cityValidationRule);
	}

	/*4. Post Code field validation*/
	@Test(groups = {"validation"})
	public void postCodeFieldValidation() {
                ChangeMyDetailsPage changeMyDetailsPage = (ChangeMyDetailsPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.changeMyDetails, defaultUserData.getRegisteredUserData());
		changeMyDetailsPage.validatePostcode(postcodeValidationRule);
	}

	/*5. Phone field validation*/
	@Test(groups = {"validation"})
	public void phoneFieldValidation() {
                ChangeMyDetailsPage changeMyDetailsPage = (ChangeMyDetailsPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.changeMyDetails, defaultUserData.getRegisteredUserData());
		changeMyDetailsPage.validatePhone(phoneValidationRule);
	}

	/*6. Mobile Field validation*/
	@Test(groups = {"validation"})
	public void mobileFieldValidation() {
                ChangeMyDetailsPage changeMyDetailsPage = (ChangeMyDetailsPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.changeMyDetails, defaultUserData.getRegisteredUserData());
		changeMyDetailsPage.validateMobile(phoneValidationRule);
	}

	/*7. Email field validation*/
	@Test(groups = {"validation"})
	public void emailFieldValidation() {
                ChangeMyDetailsPage changeMyDetailsPage = (ChangeMyDetailsPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.changeMyDetails, defaultUserData.getRegisteredUserData());
		changeMyDetailsPage.validateEmail(emailValidationRule);
	}

	/*8. Email Verification field validation*/
	@Test(groups = {"validation"})
	public void verificationEmailFieldValidation() {
                ChangeMyDetailsPage changeMyDetailsPage = (ChangeMyDetailsPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.changeMyDetails, defaultUserData.getRegisteredUserData());
		changeMyDetailsPage.validateVerificationEmail(emailValidationRule);
	}
}
