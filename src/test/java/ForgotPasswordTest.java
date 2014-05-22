import enums.ConfiguredPages;
import enums.LogCategory;
import enums.Page;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.account.ChangePasswordPopup;
import pageObjects.account.ChangedPasswordPopup;
import pageObjects.account.LoginPopup;
import pageObjects.external.ims.IMSPlayerDetailsPage;
import pageObjects.external.mail.MailServicePage;
import pageObjects.forgotPassword.ContactUsPopup;
import pageObjects.forgotPassword.ForgotPasswordPage;
import pageObjects.forgotPassword.ForgotPasswordPopup;
import pageObjects.registration.RegistrationPage;
import springConstructors.IMS;
import springConstructors.UserData;
import springConstructors.mail.MailService;
import springConstructors.validation.ValidationRule;
import testUtils.AbstractTest;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.logs.Log;
import utils.logs.LogEntry;
import utils.logs.LogUtils;

public class ForgotPasswordTest extends AbstractTest{

    @Autowired
    @Qualifier("userData")
    private UserData defaultUserData;

    @Autowired
	@Qualifier("mailinator")
	private MailService mailService;

	@Autowired
	@Qualifier("emailValidationRule")
	private ValidationRule emailValidationRule;

	@Autowired
	@Qualifier("usernameValidationRule")
	private ValidationRule usernameValidationRule;

	@Autowired
	@Qualifier("passwordValidationRule")
	private ValidationRule passwordValidationRule;

	@Autowired
	@Qualifier("iMS")
	private IMS iMS;

	/*POSITIVE*/

	/*1. Portlet is displayed in popup*/
	@Test(groups = {"smoke"})
	public void portletIsDisplayedOnPopup(){
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();

	}
    /*1. Portlet is displayed on page*/
    @Test(groups = {"smoke"})
    public void portletIsDisplayedOnPage(){
        ForgotPasswordPage forgotPasswordPage = (ForgotPasswordPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.forgotPassword);
    }
	
	/*2. Submit correct data 3. Check confirmation popup*/
	@Test(groups = {"regression"})
	public void validPasswordRecovery(){
        // prepare userdata
        UserData userData = defaultUserData.getRandomUserData();

		// new user registration
        PortalUtils.registerUser(userData);

        // change password for newly registered user
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
        forgotPasswordPopup.recover(userData);

        boolean successfulPopupVisible=forgotPasswordPopup.confirmationPopupVisible();

		Assert.assertTrue(successfulPopupVisible == true);
	}

    /*4. Check email */
	@Test(groups = {"regression"})
	public void validPasswordRecoveryEmailReceived(){
        // prepare userdata
		UserData userData=defaultUserData.getRandomUserData();
        String email= mailService.generateEmail();
		userData.setEmail(email);

        // new user registration
		PortalUtils.registerUser(userData);

		// change password for newly registered user
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		ForgotPasswordPopup forgotPasswordPopup=homePage.navigateToForgotPassword();
        forgotPasswordPopup.recover(userData);
		boolean successfulPopupVisible=forgotPasswordPopup.confirmationPopupVisible();
        MailServicePage mailServicePage = mailService.navigateToInbox(email);
		mailServicePage.waitForEmail();
        Assert.assertTrue(successfulPopupVisible == true);
	}

    /*5. login with temporary password*/
	@Test(groups = {"regression"})
	public void ableToLoginWithNewPassword() {
		// prepare userdata
		UserData userData = defaultUserData.getRandomUserData();
		String email = mailService.generateEmail();
		userData.setEmail(email);

        // new user registration
        PortalUtils.registerUser(userData);

		// change password for newly registered user (forgotten password)
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
        forgotPasswordPopup.recover(userData);
		boolean successfulPopupVisible = forgotPasswordPopup.confirmationPopupVisible();

		// receive email and get password
		MailServicePage mailServicePage = mailService.navigateToInbox(email);
		mailServicePage.waitForEmail();
		String password = mailServicePage.getPasswordFromLetter();
		userData.setPassword(password);

		// login with new password
        homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ChangePasswordPopup changePasswordPopup = (ChangePasswordPopup) homePage.login(userData, Page.changePasswordPopup);
		Assert.assertTrue(successfulPopupVisible == true);
	}

    /*6. change temporary password (popup shown after login)*/
	@Test(groups = {"regression"})
	public void setNewPasswordAfterRecovery() {
		// prepare userdata
		UserData userData = defaultUserData.getRandomUserData();
		String email = mailService.generateEmail();
		userData.setEmail(email);

		// new user registration
        PortalUtils.registerUser(userData);

		// change password for newly registered user (forgotten password)
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
		forgotPasswordPopup.recover(userData);
		boolean successfulPopupVisible = forgotPasswordPopup.confirmationPopupVisible();

		// receive email and get password
		MailServicePage mailServicePage = mailService.navigateToInbox(email);
		mailServicePage.waitForEmail();
		String password = mailServicePage.getPasswordFromLetter();
		userData.setPassword(password);

		// login with new password
        homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		ChangePasswordPopup changePasswordPopup = (ChangePasswordPopup) homePage.login(userData, Page.changePasswordPopup);

        // set new password
		String newPassword = passwordValidationRule.generateValidString();
		ChangedPasswordPopup changedPasswordPopup = changePasswordPopup.fillFormAndSubmit(password, newPassword);
		boolean successfullyChangedPasswordMessageAppeared = changedPasswordPopup.successfulMessageAppeared();

		Assert.assertTrue(successfulPopupVisible == true && successfullyChangedPasswordMessageAppeared == true);
	}

    /*7. login with new password*/
	@Test(groups = {"regression"})
	public void setNewPasswordAfterRecoveryandLogin() {
		// prepare userdata
		UserData userData = defaultUserData.getRandomUserData();
		String email = mailService.generateEmail();
		userData.setEmail(email);

		// new user registration
        PortalUtils.registerUser(userData);

		// change password for newly registered user (forgotten password)
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
		forgotPasswordPopup.recover(userData);
		boolean successfulPopupVisible = forgotPasswordPopup.confirmationPopupVisible();

		// receive email and get password
		MailServicePage mailServicePage = mailService.navigateToInbox(email);
		mailServicePage.waitForEmail();
		String password = mailServicePage.getPasswordFromLetter();
		userData.setPassword(password);

		// login with new password
        homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		ChangePasswordPopup changePasswordPopup = (ChangePasswordPopup) homePage.login(userData, Page.changePasswordPopup);

        // set new password
		String newPassword = passwordValidationRule.generateValidString();
        ChangedPasswordPopup changedPasswordPopup = changePasswordPopup.fillFormAndSubmit(password, newPassword);
        boolean successfullyChangedPasswordMessageAppeared = changedPasswordPopup.successfulMessageAppeared();

        // update userData with new password
		userData.setPassword(newPassword);

		// login with new pass again
        homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.home, userData);

        Assert.assertTrue(successfulPopupVisible == true && successfullyChangedPasswordMessageAppeared == true);
	}

    /*8. Cancel resetting password */
	@Test(groups = {"regression"})
	public void cancelPasswordChange(){
		// prepare userdata
		UserData userData=defaultUserData.getRandomUserData();

        // open forgot password popup
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();

        // fill and cancel
		homePage=forgotPasswordPopup.fillDataAndCancel(userData);
		Assert.assertTrue(homePage != null);
	}

    /*9. Links work on popup - Login*/
	@Test(groups = {"regression"})
	public void openLoginPopupFromLinkOnForgotPasswordPopup(){
        // open forgot password popup
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();

        // click login link
		LoginPopup loginPopup = forgotPasswordPopup.navigateToLoginPopup();
	}

    /*9. Links work on popup - Registration*/
	@Test(groups = {"regression"})
	public void openRegistrationPopupFromLinkOnForgotPasswordPopup() {
        // open forgot password popup
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();

        // click register link
		RegistrationPage registrationPage = forgotPasswordPopup.navigateToRegistrationPage();
	}

    /*9. Links work on popup - Contact Us*/
	@Test(groups = {"regression"})
	public void openContactUsPopupFromLinkOnForgotPasswordPopup() {
        // open forgot password popup
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();

        // click contact us link
		ContactUsPopup contactUsPopup = forgotPasswordPopup.navigateToContactUs();
	}

    /*10. Close popup without resetting*/
	@Test(groups = {"regression"})
	public void closePasswordChangePopup(){
		// prepare userdata
		UserData userData=defaultUserData.getRandomUserData();

        // open forgot password popup
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();

        //
		homePage=forgotPasswordPopup.fillDataAndClosePopup(userData);
		Assert.assertTrue(homePage != null);
	}

	/*11. Logs*/
	@Test(groups = {"regression"})
	public void checkLogs(){

        // prepare userdata
        UserData userData=defaultUserData.getRandomUserData();

        // prepare log parameters
        LogCategory[]  logCategories = {LogCategory.ForgotPasswordRequest, LogCategory.ForgotPasswordResponse};
        String[] parameters = {"objectIdentity="+userData.getUsername()+"-playtech81001", "username="+userData.getUsername(), "email="+userData.getEmail()};

        // new user registration
        PortalUtils.registerUser(userData);

        // change password for newly registered user (forgotten password)
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
        forgotPasswordPopup.recover(userData);

        // get and verify logs
        Log log = LogUtils.getCurrentLogs(logCategories);
		LogEntry forgotRequest = log.getEntry(LogCategory.ForgotPasswordRequest);
		log.doResponsesContainErrors();
		forgotRequest.containsParameters(parameters);
        LogEntry forgotResponse = log.getEntry(LogCategory.ForgotPasswordResponse);

	}

    /*12. IMS Player Details Page shows new password*/
	@Test(groups = {"regression"})
	public void checkPasswordIsChangedInIMS() {
		// prepare userdata
		UserData userData = defaultUserData.getRandomUserData();
		String email = mailService.generateEmail();
		userData.setEmail(email);

		// new user registration
        PortalUtils.registerUser(userData);

        // change password for newly registered user (forgotten password)
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
        forgotPasswordPopup.recover(userData);
		boolean successfulPopupVisible = forgotPasswordPopup.confirmationPopupVisible();

		// receive email and get password
		MailServicePage mailServicePage = mailService.navigateToInbox(email);
		mailServicePage.waitForEmail();
		String password = mailServicePage.getPasswordFromLetter();
		userData.setPassword(password);

        // login with new password
        homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ChangePasswordPopup changePasswordPopup = (ChangePasswordPopup) homePage.login(userData, Page.changePasswordPopup);


        // set new password
        String newPassword = passwordValidationRule.generateValidString();
        ChangedPasswordPopup changedPasswordPopup = changePasswordPopup.fillFormAndSubmit(password, newPassword);
        boolean successfullyChangedPasswordMessageAppeared = changedPasswordPopup.successfulMessageAppeared();

        // close popup
        //changePasswordPopup.clickClose();

        // update userData with new password
        userData.setPassword(newPassword);

        // login with new pass again
        homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.home, userData);

		// check that password changed in ims
		IMSPlayerDetailsPage imsPlayerDetailsPage = iMS.navigateToPlayedDetails(userData.getUsername());
		String imsPassword = imsPlayerDetailsPage.getPassword();
		Assert.assertTrue(imsPassword.equals(newPassword));
	}

    /*NEGATIVE*/

	/*1. Try to submit FP form with incorrect email (valida but not the one specified for your account)*/
	@Test(groups = {"regression"})
	public void invalidPasswordRecovery(){
        // prepare userdata
        UserData userData=defaultUserData.getRegisteredUserData().cloneUserData();
        userData.setEmail("incorrectemail@mailService.com");

        // change password for newly registered user (forgotten password)
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
        forgotPasswordPopup.recover(userData);

		boolean validationErrorVisible=forgotPasswordPopup.validationErrorMessageVisible();

		Assert.assertTrue(validationErrorVisible == true);
	}

	/*2. Try to specify Date of birth showing that you are not 18 years yet*/
	@Test(groups = {"regression"})
	public void tryToSpecifyDateOfBirthLessThan18(){
        // prepare userdata
        UserData userData=defaultUserData.getRegisteredUserData().cloneUserData();
		userData.setBirthYear("2000");

        // change password for newly registered user (forgotten password)
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
        forgotPasswordPopup.recover(userData);

		boolean validationErrorVisible=forgotPasswordPopup.validationErrorIconVisible();
		Assert.assertTrue(validationErrorVisible);
	}

    /*3. Try to submit FP form with incorrect date of birth (valid but not the one specified for your account)*/
	@Test(groups = {"regression"})
	public void tryToSpecifyIncorrectDateOfBirth(){
        // prepare userdata
        UserData userData=defaultUserData.getRegisteredUserData().cloneUserData();
		userData.setBirthDay("23"); // set incorrect date of birth

        // change password for newly registered user (forgotten password)
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
        forgotPasswordPopup.recover(userData);

		boolean validationErrorVisible=forgotPasswordPopup.validationErrorMessageVisible();
		Assert.assertTrue(validationErrorVisible);
	}

    /*4. Try to submit FP form with not existing username*/
	@Test(groups = {"regression"})
	public void tryToSpecifyIncorrectUsername(){
		// prepare userdata
        UserData userData=defaultUserData.getRegisteredUserData().cloneUserData();
		userData.setUsername("incorrectUsername"); // set incorrect username

        // change password for newly registered user (forgotten password)
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
        forgotPasswordPopup.recover(userData);

		boolean validationErrorVisible=forgotPasswordPopup.validationErrorMessageVisible();
		Assert.assertTrue(validationErrorVisible);
	}

    /*5. Restore password and try to login with old password*/
	@Test(groups = {"regression"})
	public void unableToLoginWithOldPassword(){
		// prepare userdata
        UserData userData=defaultUserData.getRandomUserData();

        // new user registration
        PortalUtils.registerUser(userData);

        // change password for newly registered user (forgotten password)
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
        forgotPasswordPopup.recover(userData);

		boolean successfulPopupVisible=forgotPasswordPopup.confirmationPopupVisible();

        // login with old password
        homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        LoginPopup loginPopup = (LoginPopup) homePage.login(userData, Page.loginPopup);

		Assert.assertTrue(successfulPopupVisible == true);
	}

    /*6. Login with temporary password stops working after password is changed*/
	@Test(groups = {"regression"})
	public void aaatryToLoginWithTempPassAfterPassChanged() {
        // prepare userdata
        UserData userData = defaultUserData.getRandomUserData();
		String email = mailService.generateEmail();
		userData.setEmail(email);

        // new user registration
        PortalUtils.registerUser(userData);

        // change password for newly registered user (forgotten password)
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
        forgotPasswordPopup.recover(userData);

		// get temporary password
		MailServicePage mailServicePage = mailService.navigateToInbox(email);
		mailServicePage.waitForEmail();
		String tempPassword = mailServicePage.getPasswordFromLetter();

		userData.setPassword(tempPassword);

        // login with new password
        homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ChangePasswordPopup changePasswordPopup = (ChangePasswordPopup) homePage.login(userData, Page.changePasswordPopup);

        // set new password
        String newPassword = passwordValidationRule.generateValidString();
        ChangedPasswordPopup changedPasswordPopup = changePasswordPopup.fillFormAndSubmit(tempPassword, newPassword);

        // update userData with new password
        userData.setPassword(newPassword);

		// login with temp pass again
        homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		LoginPopup loginPopup = (LoginPopup) homePage.login(userData, Page.loginPopup);
	}

    /*7. When you are logged in with temporary password and on forced Change Password form enters incorrect old password you are shown an error.*/
	@Test(groups = {"regression"})
	public void incorrectOldPassword(){
        // prepare userdata
		UserData userData = defaultUserData.getForgotPasswordUserData().cloneUserData();

        // login with new password
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		ChangePasswordPopup changePasswordPopup = (ChangePasswordPopup) homePage.login(userData, Page.changePasswordPopup);

        // set new password
        changePasswordPopup.fillFormAndSubmit("Inc0rrect", passwordValidationRule.generateValidString());

		boolean errorMessageAppeared = changePasswordPopup.errorMessageAppeared();

		Assert.assertTrue(errorMessageAppeared == true);
	}

    /*8. New password which has been used recently*/
	@Test(groups = {"regression"})
	public void newPasswordUsedRecently(){
        // prepare userdata
		UserData userData = defaultUserData.getRandomUserData();
		String oldPassword = userData.getPassword();
		String email = mailService.generateEmail();
		userData.setEmail(email);

        // new user registration
        PortalUtils.registerUser(userData);

        // change password for newly registered user (forgotten password)
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
        forgotPasswordPopup.recover(userData);

        // get temporary password
		MailServicePage mailServicePage = mailService.navigateToInbox(email);
		mailServicePage.waitForEmail();
		String tempPassword = mailServicePage.getPasswordFromLetter();

        userData.setPassword(tempPassword);

        // login with new password
        homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ChangePasswordPopup changePasswordPopup = (ChangePasswordPopup) homePage.login(userData, Page.changePasswordPopup);

        // set old password
		changePasswordPopup.fillFormAndSubmit(tempPassword, oldPassword);

		boolean errorMessageAppeared = changePasswordPopup.errorMessageAppeared();

		Assert.assertTrue(errorMessageAppeared == true);
	}

    /*VALIDATION*/

    /*1. Username field validation*/
	@Test(groups = {"validation"})
	public void usernameFieldValidation() {
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
		forgotPasswordPopup.validateUsername(usernameValidationRule);
	}

    /*2. Email address field validation*/
	@Test(groups = {"validation"})
	public void emailFieldValidation() {
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
		forgotPasswordPopup.validateEmail(emailValidationRule);
	}

    /*3. Old password field validation on Change password popup*/
	@Test(groups = {"validation"})
	public void oldPasswordFieldValidation() {
        // prepare userdata
        UserData userData = defaultUserData.getForgotPasswordUserData().cloneUserData();

        // login with new password
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ChangePasswordPopup changePasswordPopup = (ChangePasswordPopup) homePage.login(userData, Page.changePasswordPopup);

        // validate
		changePasswordPopup.validateOldPassword(passwordValidationRule);
	}

    /*4. New password field validation on Change password popup*/
	@Test(groups = {"validation"})
	public void newPasswordFieldValidation() {
        // prepare userdata
        UserData userData = defaultUserData.getForgotPasswordUserData().cloneUserData();

        // login with new password
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ChangePasswordPopup changePasswordPopup = (ChangePasswordPopup) homePage.login(userData, Page.changePasswordPopup);

        // validate
		changePasswordPopup.validateNewPassword(passwordValidationRule);
	}
}
