import enums.ConfiguredPages;
import enums.Page;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.changePassword.ChangePasswordPopup;
import pageObjects.changePassword.ChangedPasswordPopup;
import pageObjects.external.ims.IMSPlayerDetailsPage;
import pageObjects.external.mail.MailServicePage;
import pageObjects.forgotPassword.ForgotPasswordConfirmationPopup;
import pageObjects.forgotPassword.ForgotPasswordPopup;
import pageObjects.login.LoginPopup;
import springConstructors.IMS;
import springConstructors.UserData;
import springConstructors.ValidationRule;
import springConstructors.mail.MailService;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.TypeUtils;
import utils.core.AbstractTest;

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
//    /*1. Portlet is displayed on page*/
//    @Test(groups = {"smoke"})
//    public void portletIsDisplayedOnPage(){
//        ForgotPasswordPage forgotPasswordPage = (ForgotPasswordPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.forgotPassword);
//    }
	
	/*2. Submit correct data 3. Check confirmation popup*/
	@Test(groups = {"regression"})
	public void validPasswordRecovery(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
        ForgotPasswordConfirmationPopup forgotPasswordConfirmationPopup = forgotPasswordPopup.recoverPasswordValid(userData);
	}

    /*4. Check email */
	@Test(groups = {"regression"})
	public void validPasswordRecoveryEmailReceived(){
		UserData userData=defaultUserData.getRandomUserData();
        String email= mailService.generateEmail();
		userData.setEmail(email);
		PortalUtils.registerUser(userData);
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
        ForgotPasswordConfirmationPopup forgotPasswordConfirmationPopup = forgotPasswordPopup.recoverPasswordValid(userData);
        MailServicePage mailServicePage = mailService.navigateToInbox(email);
		mailServicePage.waitForEmail();
	}

    /*5. login with temporary password*/
	@Test(groups = {"regression"})
	public void ableToLoginWithNewPassword() {
		UserData userData = defaultUserData.getRandomUserData();
		String email = mailService.generateEmail();
		userData.setEmail(email);
        PortalUtils.registerUser(userData);
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
        ForgotPasswordConfirmationPopup forgotPasswordConfirmationPopup = forgotPasswordPopup.recoverPasswordValid(userData);
        MailServicePage mailServicePage = mailService.navigateToInbox(email);
        mailServicePage.waitForEmail();
		String password = mailServicePage.getPasswordFromLetter();
		userData.setPassword(password);
        homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ChangePasswordPopup changePasswordPopup = (ChangePasswordPopup) homePage.login(userData, Page.changePasswordPopup);
	}

    /*6. change temporary password (popup shown after login)*/
	@Test(groups = {"regression"})
	public void setNewPasswordAfterRecovery() {
		UserData userData = defaultUserData.getRandomUserData();
		String email = mailService.generateEmail();
		userData.setEmail(email);
        PortalUtils.registerUser(userData);
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
        ForgotPasswordConfirmationPopup forgotPasswordConfirmationPopup = forgotPasswordPopup.recoverPasswordValid(userData);
        TypeUtils.assertTrueWithLogs(forgotPasswordPopup.isErrorVisible(),"successfulPopupVisible");
		MailServicePage mailServicePage = mailService.navigateToInbox(email);
		mailServicePage.waitForEmail();
		String password = mailServicePage.getPasswordFromLetter();
		userData.setPassword(password);
        homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		ChangePasswordPopup changePasswordPopup = (ChangePasswordPopup) homePage.login(userData, Page.changePasswordPopup);
		String newPassword = passwordValidationRule.generateValidString();
		ChangedPasswordPopup changedPasswordPopup = changePasswordPopup.fillFormAndSubmit(password, newPassword);
        TypeUtils.assertTrueWithLogs(changedPasswordPopup.successfulMessageAppeared(),"successfullyChangedPasswordMessageAppeared");
	}

    /*7. login with new password*/
	@Test(groups = {"regression"})
	public void setNewPasswordAfterRecoveryAndLogin() {
		UserData userData = defaultUserData.getRandomUserData();
		String email = mailService.generateEmail();
		userData.setEmail(email);
        PortalUtils.registerUser(userData);
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
        ForgotPasswordConfirmationPopup forgotPasswordConfirmationPopup = forgotPasswordPopup.recoverPasswordValid(userData);
		MailServicePage mailServicePage = mailService.navigateToInbox(email);
		mailServicePage.waitForEmail();
		String password = mailServicePage.getPasswordFromLetter();
		userData.setPassword(password);
        homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		ChangePasswordPopup changePasswordPopup = (ChangePasswordPopup) homePage.login(userData, Page.changePasswordPopup);
		String newPassword = passwordValidationRule.generateValidString();
        ChangedPasswordPopup changedPasswordPopup = changePasswordPopup.fillFormAndSubmit(password, newPassword);
        changedPasswordPopup.closePopup();
		userData.setPassword(newPassword);
        homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.home, userData);
	}

//    /*8. Cancel resetting password */
//	@Test(groups = {"regression"})
//	public void cancelPasswordChange(){
//		UserData userData=defaultUserData.getRandomUserData();
//        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
//        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
//		homePage=forgotPasswordPopup.fillDataAndCancel(userData);
//	}

//    /*9. Links work on popup - Login*/
//	@Test(groups = {"regression"})
//	public void openLoginPopupFromLinkOnForgotPasswordPopup(){
//        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
//        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
//		LoginPopup loginPopup = forgotPasswordPopup.navigateToLoginPopup();
//	}
//
//    /*9. Links work on popup - Registration*/
//	@Test(groups = {"regression"})
//	public void openRegistrationPopupFromLinkOnForgotPasswordPopup() {
//        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
//        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
//		RegistrationPage registrationPage = forgotPasswordPopup.navigateToRegistrationPage();
//	}
//
//    /*9. Links work on popup - Contact Us*/
//	@Test(groups = {"regression"})
//	public void openContactUsPopupFromLinkOnForgotPasswordPopup() {
//        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
//        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
//		ContactUsPopup contactUsPopup = forgotPasswordPopup.navigateToContactUs();
//	}

    /*10. Close popup without resetting*/
	@Test(groups = {"regression"})
	public void closePasswordChangePopup(){
		UserData userData=defaultUserData.getRandomUserData();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
		forgotPasswordPopup.fillDataAndClosePopup(userData);
	}

//	/*11. Logs*/
//	@Test(groups = {"regression", "logs"})
//	public void checkLogs(){
//        try{
//            UserData userData=defaultUserData.getRandomUserData();
//            LogCategory[]  logCategories = {LogCategory.ForgotPasswordRequest, LogCategory.ForgotPasswordResponse};
//            String[] parameters = {"objectIdentity="+userData.getUsername()+"-playtech81001", "username="+userData.getUsername(), "email="+userData.getEmail()};
//            PortalUtils.registerUser(userData);
//            HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
//            ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
//            forgotPasswordPopup.recover(userData);
//            Log log = LogUtils.getCurrentLogs(logCategories);
//            LogEntry forgotRequest = log.getEntry(LogCategory.ForgotPasswordRequest);
//            log.doResponsesContainErrors();
//            forgotRequest.containsParameters(parameters);
//            LogEntry forgotResponse = log.getEntry(LogCategory.ForgotPasswordResponse);
//        }catch (RuntimeException e){
//            if(e.getMessage().contains("Not all registration logs appeared") || e.toString().contains("Logs have not been updated")){
//                throw new SkipException("Log page issue"+ WebDriverUtils.getUrlAndLogs());
//            }else{
//                throw new RuntimeException(e.getMessage());
//            }
//        }
//	}

    /*12. IMS Player Details Page shows new password*/
	@Test(groups = {"regression"})
	public void checkPasswordIsChangedInIMS() {
		UserData userData = defaultUserData.getRandomUserData();
		String email = mailService.generateEmail();
		userData.setEmail(email);
        PortalUtils.registerUser(userData);
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
        forgotPasswordPopup.recoverPasswordValid(userData);
		MailServicePage mailServicePage = mailService.navigateToInbox(email);
		mailServicePage.waitForEmail();
		String password = mailServicePage.getPasswordFromLetter();
		userData.setPassword(password);
        homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ChangePasswordPopup changePasswordPopup = (ChangePasswordPopup) homePage.login(userData, Page.changePasswordPopup);
        String newPassword = passwordValidationRule.generateValidString();
        ChangedPasswordPopup changedPasswordPopup = changePasswordPopup.fillFormAndSubmit(password, newPassword);
        userData.setPassword(newPassword);
        homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.home, userData);
		IMSPlayerDetailsPage imsPlayerDetailsPage = iMS.navigateToPlayedDetails(userData.getUsername());
		TypeUtils.assertTrueWithLogs(imsPlayerDetailsPage.getPassword().equals(newPassword),"Password is set on ims");
	}

    /*NEGATIVE*/

	/*1. Try to clickLogin FP form with incorrect email (valida but not the one specified for your account)*/
	@Test(groups = {"regression"})
	public void invalidPasswordRecovery(){
        UserData userData=defaultUserData.getRegisteredUserData().cloneUserData();
        userData.setEmail("incorrectemail@mailService.com");
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
        forgotPasswordPopup.recoverPasswordInvalid(userData);
        TypeUtils.assertTrueWithLogs(forgotPasswordPopup.isErrorVisible(),"validationErrorVisible");
	}

	/*2. Try to specify Date of birth showing that you are not 18 years yet*/
	@Test(groups = {"regression"})
	public void tryToSpecifyDateOfBirthLessThan18(){
        UserData userData=defaultUserData.getRegisteredUserData().cloneUserData();
		userData.setBirthYear("2000");
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
        forgotPasswordPopup.recoverPasswordInvalid(userData);
        TypeUtils.assertTrueWithLogs(forgotPasswordPopup.isErrorVisible(),"validationErrorVisible");
	}

    /*3. Try to clickLogin FP form with incorrect date of birth (valid but not the one specified for your account)*/
	@Test(groups = {"regression"})
	public void tryToSpecifyIncorrectDateOfBirth(){
        UserData userData=defaultUserData.getRegisteredUserData().cloneUserData();
		userData.setBirthDay("23"); // set incorrect date of birth
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
        forgotPasswordPopup.recoverPasswordInvalid(userData);
        TypeUtils.assertTrueWithLogs(forgotPasswordPopup.isErrorVisible(),"validationErrorVisible");
	}

    /*4. Try to clickLogin FP form with not existing username*/
	@Test(groups = {"regression"})
	public void tryToSpecifyIncorrectUsername(){
        UserData userData=defaultUserData.getRegisteredUserData().cloneUserData();
		userData.setUsername("incorrectUsername"); // set incorrect username
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
        forgotPasswordPopup.recoverPasswordInvalid(userData);
        TypeUtils.assertTrueWithLogs(forgotPasswordPopup.isErrorVisible(),"validationErrorVisible");
	}

    /*5. Restore password and try to login with old password*/
	@Test(groups = {"regression"})
	public void unableToLoginWithOldPassword(){
        UserData userData=defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
        forgotPasswordPopup.recoverPasswordValid(userData);
        homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        LoginPopup loginPopup = (LoginPopup) homePage.login(userData, Page.loginPopup);
	}

    /*6. Login with temporary password stops working after password is changed*/
	@Test(groups = {"regression"})
	public void tryToLoginWithTempPassAfterPassChanged() {
        UserData userData = defaultUserData.getRandomUserData();
		String email = mailService.generateEmail();
		userData.setEmail(email);
        PortalUtils.registerUser(userData);
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
        forgotPasswordPopup.recoverPasswordValid(userData);
		MailServicePage mailServicePage = mailService.navigateToInbox(email);
		mailServicePage.waitForEmail();
		String tempPassword = mailServicePage.getPasswordFromLetter();
		userData.setPassword(tempPassword);
        homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ChangePasswordPopup changePasswordPopup = (ChangePasswordPopup) homePage.login(userData, Page.changePasswordPopup);
        String newPassword = passwordValidationRule.generateValidString();
        ChangedPasswordPopup changedPasswordPopup = changePasswordPopup.fillFormAndSubmit(tempPassword, newPassword);
        userData.setPassword(newPassword);
        homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		LoginPopup loginPopup = (LoginPopup) homePage.login(userData, Page.loginPopup);
	}

    /*7. When you are logged in with temporary password and on forced Change Password form enters incorrect old password you are shown an error.*/
	@Test(groups = {"regression"})
	public void incorrectOldPassword(){
		UserData userData = defaultUserData.getForgotPasswordUserData().cloneUserData();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		ChangePasswordPopup changePasswordPopup = (ChangePasswordPopup) homePage.login(userData, Page.changePasswordPopup);
        changePasswordPopup.fillIncorrectFormAndSubmit("Inc0rrect", passwordValidationRule.generateValidString());
        TypeUtils.assertTrueWithLogs(changePasswordPopup.errorMessageAppeared(),"errorMessageAppeared");
	}

    /*8. New password which has been used recently*/
	@Test(groups = {"regression"})
	public void newPasswordUsedRecently(){
		UserData userData = defaultUserData.getRandomUserData();
		String oldPassword = userData.getPassword();
		String email = mailService.generateEmail();
		userData.setEmail(email);
        PortalUtils.registerUser(userData);
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
        forgotPasswordPopup.recoverPasswordValid(userData);
		MailServicePage mailServicePage = mailService.navigateToInbox(email);
		mailServicePage.waitForEmail();
		String tempPassword = mailServicePage.getPasswordFromLetter();
        userData.setPassword(tempPassword);
        homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ChangePasswordPopup changePasswordPopup = (ChangePasswordPopup) homePage.login(userData, Page.changePasswordPopup);
		changePasswordPopup.fillFormAndSubmit(tempPassword, oldPassword);
        TypeUtils.assertTrueWithLogs(changePasswordPopup.errorMessageAppeared(),"errorMessageAppeared");
	}

    /*VALIDATION*/

//    /*1. Username field validation*/
//	@Test(groups = {"validation"})
//	public void usernameFieldValidation() {
//        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
//        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
//		forgotPasswordPopup.validateUsername(usernameValidationRule);
//	}
//
//    /*2. Email address field validation*/
//	@Test(groups = {"validation"})
//	public void emailFieldValidation() {
//        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
//        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
//		forgotPasswordPopup.validateEmail(emailValidationRule);
//	}
//
//    /*3. Old password field validation on Change password popup*/
//	@Test(groups = {"validation"})
//	public void oldPasswordFieldValidation() {
//        UserData userData = defaultUserData.getForgotPasswordUserData().cloneUserData();
//        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
//        ChangePasswordPopup changePasswordPopup = (ChangePasswordPopup) homePage.login(userData, Page.changePasswordPopup);
//		changePasswordPopup.validateOldPassword(passwordValidationRule);
//	}
//
//    /*4. New password field validation on Change password popup*/
//	@Test(groups = {"validation"})
//	public void newPasswordFieldValidation() {
//        UserData userData = defaultUserData.getForgotPasswordUserData().cloneUserData();
//        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
//        ChangePasswordPopup changePasswordPopup = (ChangePasswordPopup) homePage.login(userData, Page.changePasswordPopup);
//		changePasswordPopup.validateNewPassword(passwordValidationRule);
//	}
}
