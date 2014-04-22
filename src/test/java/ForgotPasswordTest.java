import enums.LogCategory;
import enums.Page;
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
import pageObjects.forgotPassword.ForgotPasswordPopup;
import pageObjects.registration.RegistrationPage;
import springConstructors.IMS;
import springConstructors.UserData;
import springConstructors.mail.MailService;
import springConstructors.validation.ValidationRule;
import testUtils.AbstractTest;
import utils.NavigationUtils;
import utils.logs.Log;
import utils.logs.LogEntry;
import utils.logs.LogUtils;

/**
 * User: sergiich
 * Date: 4/10/14
 */

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

	/*1. Portlet is displayed*/
	@Test(groups = {"smoke"})
	public void portletIsDisplayedForgottenPassword(){
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
	}
	
	/*2. Submit correct data 3. Check confirmation popup */
	@Test(groups = {"regression"})
	public void validPasswordRecovery(){
		// new user registration
		HomePage homePage=NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage=homePage.navigateToRegistration();
		UserData userData=defaultUserData.getRandomUserData();
		homePage=registrationPage.registerUser(userData);
		// change password for newly registered user
		homePage=(HomePage)homePage.logout();
		ForgotPasswordPopup passwordRecoveryFormPopup=homePage.navigateToForgotPassword();
		passwordRecoveryFormPopup.recoverPassword(userData);
		boolean successfulPopupVisible=passwordRecoveryFormPopup.confirmationPopupVisible();
		Assert.assertTrue(successfulPopupVisible == true);
	}

    /*4. Check email */
	@Test(groups = {"regression"})
	public void validPasswordRecoveryEmailReceived(){
		// new user registration
		HomePage homePage=NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage=homePage.navigateToRegistration();
		UserData userData=defaultUserData.getRandomUserData();
		String username=userData.getUsername();
		String email= mailService.generateEmail(username);
		userData.setEmail(email);
		homePage=(HomePage) registrationPage.registerUser(userData);
		// change password for newly registered user
		homePage=(HomePage)homePage.logout();
		ForgotPasswordPopup passwordRecoveryFormPopup=homePage.navigateToForgotPassword();
		passwordRecoveryFormPopup.recoverPassword(userData);
		boolean successfulPopupVisible=passwordRecoveryFormPopup.confirmationPopupVisible();
        MailServicePage mailServicePage = mailService.navigateToInbox(username);
		mailServicePage.waitForEmail();
        Assert.assertTrue(successfulPopupVisible == true);
	}

    /*5. login with temporary password*/
	@Test(groups = {"regression"})
	public void ableToLoginWithNewPassword() {
		// prepare userdata
		UserData userData = defaultUserData.getRandomUserData();
		String username = userData.getUsername();
		String email = mailService.generateEmail(username);
		userData.setEmail(email);
		// new user registration
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage = homePage.navigateToRegistration();
		homePage = (HomePage) registrationPage.registerUser(userData);
		// change password for newly registered user (forgotten password)
		homePage =(HomePage)homePage.logout();
		ForgotPasswordPopup passwordRecoveryFormPopup = homePage.navigateToForgotPassword();
		passwordRecoveryFormPopup.recoverPassword(userData);
		boolean successfulPopupVisible = passwordRecoveryFormPopup.confirmationPopupVisible();
		// receive email and get password
		MailServicePage mailServicePage = mailService.navigateToInbox(username);
		mailServicePage.waitForEmail();
		String password = mailServicePage.getPasswordFromLetter();
		userData.setPassword(password);
		// login with new password
		homePage = NavigationUtils.navigateToHome();
		ChangePasswordPopup changePasswordPopup = (ChangePasswordPopup) homePage.login(userData, Page.changePasswordPopup);
		Assert.assertTrue(successfulPopupVisible == true);
	}

    /*6. change temporary password (popup shown after login)*/
	@Test(groups = {"regression"})
	public void setNewPasswordAfterRecovery() {
		// prepare userdata
		UserData userData = defaultUserData.getRandomUserData();
		String username = userData.getUsername();
		String email = mailService.generateEmail(username);
		userData.setEmail(email);
		// new user registration
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage = homePage.navigateToRegistration();
		homePage = (HomePage) registrationPage.registerUser(userData);
		// change password for newly registered user (forgotten password)
		homePage =(HomePage)homePage.logout();
		ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
		forgotPasswordPopup.recoverPassword(userData);
		boolean successfulPopupVisible = forgotPasswordPopup.confirmationPopupVisible();
		// receive email and get password
		MailServicePage mailServicePage = mailService.navigateToInbox(username);
		mailServicePage.waitForEmail();
		String password = mailServicePage.getPasswordFromLetter();
		userData.setPassword(password);
		// login with new password
		homePage = NavigationUtils.navigateToHome();
		ChangePasswordPopup changePasswordPopup = (ChangePasswordPopup) homePage.login(userData, Page.changePasswordPopup);
		String newPassword = passwordValidationRule.generateValidString();
		ChangedPasswordPopup changedPasswordPopup = changePasswordPopup.fillForm(password, newPassword);
		boolean successfullyChangedPasswordMessageAppeared = changedPasswordPopup.successfulMessageAppeared();
		Assert.assertTrue(successfulPopupVisible == true && successfullyChangedPasswordMessageAppeared == true);
	}

    /*7. login with new password*/
	@Test(groups = {"regression"})
	public void setNewPasswordAfterRecoveryandLogin() {
		// prepare userdata
		UserData userData = defaultUserData.getRandomUserData();
		String username = userData.getUsername();
		String email = mailService.generateEmail(username);
		userData.setEmail(email);
		// new user registration
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage = homePage.navigateToRegistration();
		homePage = (HomePage) registrationPage.registerUser(userData);
		// change password for newly registered user (forgotten password)
		homePage = (HomePage)homePage.logout();
		ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
		forgotPasswordPopup.recoverPassword(userData);
		boolean successfulPopupVisible = forgotPasswordPopup.confirmationPopupVisible();
		// receive email and get password
		MailServicePage mailServicePage = mailService.navigateToInbox(username);
		mailServicePage.waitForEmail();
		String password = mailServicePage.getPasswordFromLetter();
		userData.setPassword(password);
		// login with new password
		homePage = NavigationUtils.navigateToHome();
		ChangePasswordPopup changePasswordPopup = (ChangePasswordPopup) homePage.login(userData, Page.changePasswordPopup);
		String newPassword = passwordValidationRule.generateValidString();
		changePasswordPopup.fillForm(password, newPassword);
		changePasswordPopup.clickClose();
		userData.setPassword(newPassword);
		// login with new pass again
		homePage = NavigationUtils.navigateToPortal(true);
		LoginPopup loginPopup = (LoginPopup) homePage.login(userData, Page.loginPopup);
	}

    /*8. Cancel resetting password */
	@Test(groups = {"regression"})
	public void cancelPasswordChange(){
		// prepare userdata
		UserData userData=defaultUserData.getRandomUserData();
		HomePage homePage=NavigationUtils.navigateToPortal(true);
		ForgotPasswordPopup forgotPasswordPopup=homePage.navigateToForgotPassword();
		homePage=forgotPasswordPopup.fillDataAndCancel(userData);
		Assert.assertTrue(homePage != null);
	}

    /*9. Links work on popup - Login*/
	@Test(groups = {"regression"})
	public void openLoginPopupFromLinkOnForgotPasswordPopup(){
		HomePage homePage=NavigationUtils.navigateToPortal(true);
		ForgotPasswordPopup forgotPasswordPopup=homePage.navigateToForgotPassword();
		LoginPopup loginPopup = forgotPasswordPopup.navigateToLoginPopup();
	}

    /*9. Links work on popup - Registration*/
	@Test(groups = {"regression"})
	public void openRegistrationPopupFromLinkOnForgotPasswordPopup() {
		HomePage homePage=NavigationUtils.navigateToPortal(true);
		ForgotPasswordPopup forgotPasswordPopup=homePage.navigateToForgotPassword();
		RegistrationPage registrationPage = forgotPasswordPopup.navigateToRegistrationPage();
	}

    /*9. Links work on popup - Contact Us*/
	@Test(groups = {"regression"})
	public void openContactUsPopupFromLinkOnForgotPasswordPopup() {
		HomePage homePage=NavigationUtils.navigateToPortal(true);
		ForgotPasswordPopup forgotPasswordPopup=homePage.navigateToForgotPassword();
		ContactUsPopup contactUsPopup = forgotPasswordPopup.navigateToContactUs();
	}

    /*10. Close popup without resetting*/
	@Test(groups = {"regression"})
	public void closePasswordChangePopup(){
		// prepare userdata
		UserData userData=defaultUserData.getRandomUserData();
		HomePage homePage=NavigationUtils.navigateToPortal(true);
		ForgotPasswordPopup forgotPasswordPopup=homePage.navigateToForgotPassword();
		homePage=forgotPasswordPopup.fillDataAndClosePopup(userData);
		Assert.assertTrue(homePage != null);
	}

	/*11. Logs*/
	@Test(groups = {"regression"})
	public void checkLogs(){
		LogCategory[]  logCategories = {LogCategory.ForgotPasswordRequest, LogCategory.ForgotPasswordResponse};
		UserData userData=defaultUserData.getRandomUserData();
		String[] parameters = {"objectIdentity="+userData.getUsername()+"-playtech81001",
				"username="+userData.getUsername(),
				"email="+userData.getEmail()};
		HomePage homePage=NavigationUtils.navigateToPortal(true);
		homePage = (HomePage)homePage.navigateToRegistration().registerUser(userData);
		homePage = (HomePage)homePage.logout();
		homePage.navigateToForgotPassword().recoverPassword(userData);
		Log log = LogUtils.getCurrentLogs();
		LogEntry forgotPass = log.getEntry(LogCategory.ForgotPasswordRequest);
		log.doResponsesContainErrors();
		forgotPass.containsParameters(parameters);
	}

    /*12. IMS Player Details Page shows new password */
	@Test(groups = {"regression"})
	public void checkPasswordIsChangedInIMS() {
		// prepare userdata
		UserData userData = defaultUserData.getRandomUserData();
		String username = userData.getUsername();
		String email = mailService.generateEmail(username);
		userData.setEmail(email);
		// new user registration
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage = homePage.navigateToRegistration();
		homePage = (HomePage) registrationPage.registerUser(userData);
		// change password for newly registered user (forgotten password)
		homePage = (HomePage)homePage.logout();
		ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
		forgotPasswordPopup.recoverPassword(userData);
		boolean successfulPopupVisible = forgotPasswordPopup.confirmationPopupVisible();
		// receive email and get password
		MailServicePage mailServicePage = mailService.navigateToInbox(username);
		mailServicePage.waitForEmail();
		String password = mailServicePage.getPasswordFromLetter();
		userData.setPassword(password);
		// login with new password
		homePage = NavigationUtils.navigateToHome();
		ChangePasswordPopup changePasswordPopup = (ChangePasswordPopup) homePage.login(userData, Page.changePasswordPopup);
		String newPassword = passwordValidationRule.generateValidString();
		changePasswordPopup.fillForm(password, newPassword);
		userData.setPassword(newPassword);
		homePage = (HomePage)NavigationUtils.navigateToPortal(true).login(userData);
		homePage.logout();
		IMSPlayerDetailsPage imsPlayerDetailsPage = iMS.navigateToPlayedDetails(username);
		String imsPassword = imsPlayerDetailsPage.getPassword();
		Assert.assertTrue(imsPassword.equals(newPassword));
	}

    /*NEGATIVE*/

	/*1. Try to submit FP form with incorrect email (valida but not the one specified for your account)*/
	@Test(groups = {"regression"})
	public void invalidPasswordRecovery(){
		HomePage homePage=NavigationUtils.navigateToPortal(true);
		ForgotPasswordPopup forgotPasswordPopup=homePage.navigateToForgotPassword();
		UserData userData=defaultUserData.getRegisteredUserData().cloneUserData();
		userData.setEmail("incorrectemail@mailService.com");
		forgotPasswordPopup.recoverPassword(userData);
		boolean validationErrorVisible=forgotPasswordPopup.validationErrorMessageVisible();
		Assert.assertTrue(validationErrorVisible == true);
	}

	/*2. Try to specify Date of birth showing that you are not 18 years yet*/
	@Test(groups = {"regression"})
	public void tryToSpecifyDateOfBirthLessThan18(){
		UserData userData=defaultUserData.getRegisteredUserData().cloneUserData();
		HomePage homePage=NavigationUtils.navigateToPortal(true);
		userData.setBirthYear("2000");
		ForgotPasswordPopup forgotPasswordPopup=homePage.navigateToForgotPassword();
		forgotPasswordPopup.recoverPassword(userData);
		boolean validationErrorVisible=forgotPasswordPopup.validationErrorIconVisible();
		Assert.assertTrue(validationErrorVisible);
	}

    /*3. Try to submit FP form with incorrect date of birth (valid but not the one specified for your account)*/
	@Test(groups = {"regression"})
	public void tryToSpecifyIncorrectDateOfBirth(){
		UserData userData=defaultUserData.getRegisteredUserData().cloneUserData();
		userData.setBirthDay("23"); // set incorrect date of birth
		HomePage homePage=NavigationUtils.navigateToPortal(true);
		ForgotPasswordPopup forgotPasswordPopup=homePage.navigateToForgotPassword();
		forgotPasswordPopup.recoverPassword(userData);
		boolean validationErrorVisible=forgotPasswordPopup.validationErrorMessageVisible();
		Assert.assertTrue(validationErrorVisible);
	}

    /*4. Try to submit FP form with not existing username*/
	@Test(groups = {"regression"})
	public void tryToSpecifyIncorrectUsername(){
		UserData userData=defaultUserData.getRegisteredUserData().cloneUserData();
		userData.setUsername("incorrectUsername"); // set incorrect username
		HomePage homePage=NavigationUtils.navigateToPortal(true);
		ForgotPasswordPopup forgotPasswordPopup=homePage.navigateToForgotPassword();
		forgotPasswordPopup.recoverPassword(userData);
		boolean validationErrorVisible=forgotPasswordPopup.validationErrorMessageVisible();
		Assert.assertTrue(validationErrorVisible);
	}

    /*5. Restore password and try to login with old password*/
	@Test(groups = {"regression"})
	public void unableToLoginWithOldPassword(){
		// new user registration
		HomePage homePage=NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage=homePage.navigateToRegistration();
		UserData userData=defaultUserData.getRandomUserData();
		homePage=(HomePage) registrationPage.registerUser(userData);
		// change password for newly registered user
		homePage=(HomePage)homePage.logout();
		ForgotPasswordPopup passwordRecoveryFormPopup=homePage.navigateToForgotPassword();
		passwordRecoveryFormPopup.recoverPassword(userData);
		boolean successfulPopupVisible=passwordRecoveryFormPopup.confirmationPopupVisible();
		homePage=NavigationUtils.navigateToHome();
		LoginPopup loginPopup=(LoginPopup) homePage.login(userData, Page.loginPopup);
		Assert.assertTrue(successfulPopupVisible == true);
	}

    /*6. Login with temporary password stops working after password is changed*/
	@Test(groups = {"regression"})
	public void tryToLoginWithTempPassAfterPassChanged() {
		UserData userData = defaultUserData.getRandomUserData();
		String username = userData.getUsername();
		String email = mailService.generateEmail(username);
		userData.setEmail(email);
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		homePage = (HomePage) homePage.navigateToRegistration().registerUser(userData);
		homePage = (HomePage)homePage.logout();
		homePage.navigateToForgotPassword().recoverPassword(userData);
		// get temporary password
		MailServicePage mailServicePage = mailService.navigateToInbox(username);
		mailServicePage.waitForEmail();
		String tempPassword = mailServicePage.getPasswordFromLetter();
		String newPassword = passwordValidationRule.generateValidString();
		userData.setPassword(tempPassword);
		// login with new password
		homePage = NavigationUtils.navigateToPortal(true);
		ChangePasswordPopup changePasswordPopup = (ChangePasswordPopup) homePage.login(userData, Page.changePasswordPopup);
		ChangedPasswordPopup changedPasswordPopup = changePasswordPopup.fillForm(tempPassword, newPassword);
		changedPasswordPopup.clickAccept();
		// login with temp pass again
		homePage = NavigationUtils.navigateToPortal(true);
		LoginPopup loginPopup = (LoginPopup) homePage.login(userData, Page.loginPopup);
	}

    /*7. When you are logged in with temporary password and on forced Change Password form enters incorrect old password you are shown an error.*/
	@Test(groups = {"regression"})
	public void incorrectOldPassword(){
		UserData userData = defaultUserData.getForgotPasswordUserData().cloneUserData();
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		ChangePasswordPopup changePasswordPopup = (ChangePasswordPopup) homePage.login(userData, Page.changePasswordPopup);
		changePasswordPopup.fillForm("Inc0rrect", passwordValidationRule.generateValidString());

		boolean errorMessageAppeared = changePasswordPopup.errorMessageAppeared();
		Assert.assertTrue(errorMessageAppeared == true);
	}

    /*8. New password which has been used recently*/
	@Test(groups = {"regression"})
	public void newPasswordUsedRecently(){
		UserData userData = defaultUserData.getRandomUserData();
		String username = userData.getUsername();
		String oldPassword = userData.getPassword();
		String email = mailService.generateEmail(username);
		userData.setEmail(email);
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		homePage = (HomePage) homePage.navigateToRegistration().registerUser(userData);
		homePage = (HomePage)homePage.logout();
		homePage.navigateToForgotPassword().recoverPassword(userData);
		// get temporary password
		MailServicePage mailServicePage = mailService.navigateToInbox(username);
		mailServicePage.waitForEmail();
		String tempPassword = mailServicePage.getPasswordFromLetter();
		String newPassword = passwordValidationRule.generateValidString();
		userData.setPassword(tempPassword);
		// login with new password
		homePage = NavigationUtils.navigateToPortal(true);
		ChangePasswordPopup changePasswordPopup = (ChangePasswordPopup) homePage.login(userData, Page.changePasswordPopup);
		changePasswordPopup.fillForm(tempPassword, oldPassword);

		boolean errorMessageAppeared = changePasswordPopup.errorMessageAppeared();
		Assert.assertTrue(errorMessageAppeared == true);
	}

    /*VALIDATION*/

    /*1. Username field validation*/
	@Test(groups = {"validation"})
	public void usernameFieldValidation() {
		HomePage homePage = NavigationUtils.navigateToHome();
		ForgotPasswordPopup forgotPasswordPopup=homePage.navigateToForgotPassword();
		forgotPasswordPopup.validateUsername(usernameValidationRule);
	}

    /*2. Email address field validation*/
	@Test(groups = {"validation"})
	public void emailFieldValidation() {
		HomePage homePage = NavigationUtils.navigateToHome();
		ForgotPasswordPopup forgotPasswordPopup=homePage.navigateToForgotPassword();
		forgotPasswordPopup.validateEmail(emailValidationRule);
	}
    /*3. Old password field validation on Change password popup*/
	@Test(groups = {"validation"})
	public void oldPasswordFieldValidation() {
		UserData userData = defaultUserData.getForgotPasswordUserData();
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		ChangePasswordPopup changePasswordPopup = (ChangePasswordPopup) homePage.login(userData, Page.changePasswordPopup);
		changePasswordPopup.validateOldPassword(passwordValidationRule);
	}

    /*4. New password field validation on Change password popup*/
	@Test(groups = {"validation"})
	public void newPasswordFieldValidation() {
		UserData userData = defaultUserData.getForgotPasswordUserData();
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		ChangePasswordPopup changePasswordPopup = (ChangePasswordPopup) homePage.login(userData, Page.changePasswordPopup);
		changePasswordPopup.validateNewPassword(passwordValidationRule);
	}
}
