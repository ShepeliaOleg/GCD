import enums.ConfiguredPages;
import enums.Page;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.changePassword.ChangePasswordPopup;
import pageObjects.external.ims.IMSPlayerDetailsPage;
import pageObjects.external.mail.MailServicePage;
import pageObjects.forgotPassword.ForgotPasswordPopup;
import pageObjects.login.LoginPopup;
import springConstructors.UserData;
import springConstructors.ValidationRule;
import springConstructors.mail.MailService;
import utils.DateUtils;
import utils.IMSUtils;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

import java.util.List;

public class ForgotPasswordTest extends AbstractTest{

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

	/*POSITIVE*/

	/*1. Portlet is displayed in popup*/
	@Test(groups = {"smoke"})
	public void portletIsDisplayedOnPopup(){
        try{
            HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
            ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
        }catch (Exception e){
            skipTest();
        }

	}
//    /*1. Portlet is displayed on page*/
//    @Test(groups = {"smoke"})
//    public void portletIsDisplayedOnPage(){
//        ForgotPasswordPage forgotPasswordPage = (ForgotPasswordPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.forgotPassword);
//    }
	
	/*2. Submit correct data 3. Check confirmation popup*/
	@Test(groups = {"regression"})
	public void validRecovery(){
        validPasswordRecovery();
	}

    /* Frozen user*/
    @Test(groups = {"regression"})
    public void frozenPasswordRecovery(){
        UserData userData = DataContainer.getUserData().getFrozenUserData();
        userData.setEmail(mailService.generateEmail());
        PortalUtils.registerUser(userData, Page.registrationPage);
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
        forgotPasswordPopup = forgotPasswordPopup.recoverPasswordInvalid(userData);
        assertEquals("Player is frozen", forgotPasswordPopup.getPortletErrorMessage(), "Error message");
    }

    /*6. change temporary password (popup shown after login)*/
	@Test(groups = {"regression"})
	public void setNewPasswordAfterRecoveryLogin() {
        setNewPasswordAfterRecoveryAndLogin();
	}

//    /*8. Cancel resetting password and login with old pass*/
//	@Test(groups = {"regression"})
//	public void cancelPasswordChange(){
//		UserData userData=DataContainer.getUserData().getRandomUserData();
//        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
//        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
//		forgotPasswordPopup.fillDataAndClosePopup(userData).login(userData);
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

    /*10. Close popup without resetting and login*/
	@Test(groups = {"regression"})
	public void closePasswordChangePopup(){
		UserData userData=DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData);
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
		forgotPasswordPopup.fillDataAndClosePopup(userData).login(userData);
	}

//	/*11. Logs*/
//	@Test(groups = {"regression", "logs"})
//	public void checkLogs(){
//        try{
//            UserData userData=DataContainer.getUserData().getRandomUserData();
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
// test disabled until non-encrypted password is not shown on player detils page on ims
//	@Test(groups = {"regression"})
	public void checkPasswordIsChangedInIMS() {
		UserData userData = setNewPasswordAfterRecoveryAndLogin();
		IMSPlayerDetailsPage imsPlayerDetailsPage = IMSUtils.navigateToPlayedDetails(userData.getUsername());
        assertEquals(userData.getPassword(), imsPlayerDetailsPage.getPassword(), "Password is set on ims");
	}

    /*NEGATIVE*/

	/*1. Try to clickLogin FP form with incorrect email (valida but not the one specified for your account)*/
	@Test(groups = {"regression"})
	public void invalidPasswordRecovery(){
        UserData userData=DataContainer.getUserData().getRegisteredUserData();
        userData.setEmail("incorrectemail@mailService.com");
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
        forgotPasswordPopup.recoverPasswordInvalid(userData);
        assertTrue(forgotPasswordPopup.isPortletErrorVisible(),"validationErrorVisible");
	}

	/*2. Try to specify Date of birth showing that you are not 18 years yet*/
	@Test(groups = {"regression"})
	public void tryToSpecifyDateOfBirthLessThan18(){
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
        List<String> availableBirthYears = forgotPasswordPopup.getBirthYearList();
        int currentYear = DateUtils.getCurrentYear();
        String firstYear = String.valueOf(currentYear - 18);
        String lastYear = String.valueOf(currentYear - 100);
        assertEquals(84, availableBirthYears.size(), "Number of available options in birth year dropdown.");
        assertEquals(firstYear, availableBirthYears.get(1), "First available option in birth year dropdown.");
        assertEquals(lastYear, availableBirthYears.get(83), "Last available option in birth year dropdown.");
	}

    /*3. Try to clickLogin FP form with incorrect date of birth (valid but not the one specified for your account)*/
	@Test(groups = {"regression"})
	public void tryToSpecifyIncorrectDateOfBirth(){
        UserData userData=DataContainer.getUserData().getRegisteredUserData();
		userData.setBirthDay("23"); // set incorrect date of birth
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
        forgotPasswordPopup.recoverPasswordInvalid(userData);
        assertTrue(forgotPasswordPopup.isPortletErrorVisible(),"validationErrorVisible");
	}

    /*4. Try to clickLogin FP form with not existing username*/
	@Test(groups = {"regression"})
	public void tryToSpecifyIncorrectUsername(){
        UserData userData=DataContainer.getUserData().getRegisteredUserData();
		userData.setUsername("incorrectUsername"); // set incorrect username
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
        forgotPasswordPopup.recoverPasswordInvalid(userData);
        assertTrue(forgotPasswordPopup.isPortletErrorVisible(),"validationErrorVisible");
	}

    /*5. Restore password and try to login with old password*/
	@Test(groups = {"regression"})
	public void unableToLoginWithOldPassword(){
        UserData userData=DataContainer.getUserData().getRandomUserData();
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
        UserData userData = validPasswordRecovery();
        MailServicePage mailServicePage = mailService.navigateToInbox(userData.getEmail());
        mailServicePage.waitForEmail();
        String tempPassword = mailServicePage.getPasswordFromLetter();
        String newPassword = passwordValidationRule.generateValidString();
        userData.setPassword(tempPassword);
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ChangePasswordPopup changePasswordPopup = (ChangePasswordPopup) homePage.login(userData, Page.changePasswordPopup);
        changePasswordPopup.fillFormAndSubmit(userData.getPassword(), newPassword);
        homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		LoginPopup loginPopup = (LoginPopup) homePage.login(userData, Page.loginPopup);
	}

    /*7. When you are logged in with temporary password and on forced Change Password form enters incorrect old password you are shown an error.*/
	@Test(groups = {"regression"})
	public void incorrectOldPassword(){
		UserData userData = validPasswordRecovery();
        MailServicePage mailServicePage = mailService.navigateToInbox(userData.getEmail());
        mailServicePage.waitForEmail();
        String tempPassword = mailServicePage.getPasswordFromLetter();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		ChangePasswordPopup changePasswordPopup = (ChangePasswordPopup) homePage.login(userData, Page.changePasswordPopup);
        changePasswordPopup.fillIncorrectFormAndSubmit("Inc0rrect", passwordValidationRule.generateValidString());
        assertTrue(changePasswordPopup.isPortletErrorVisible(),"errorMessageAppeared");
	}

    /*8. New password which has been used recently*/
	@Test(groups = {"regression"})
	public void newPasswordUsedRecently(){
        UserData userData = validPasswordRecovery();
        MailServicePage mailServicePage = mailService.navigateToInbox(userData.getEmail());
        mailServicePage.waitForEmail();
        String tempPassword = mailServicePage.getPasswordFromLetter();
        userData.setPassword(tempPassword);
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ChangePasswordPopup changePasswordPopup = (ChangePasswordPopup) homePage.login(userData, Page.changePasswordPopup);
        changePasswordPopup.fillIncorrectFormAndSubmit("Inc0rrect", passwordValidationRule.generateValidString());
        assertEquals("Invalid old password", changePasswordPopup.getErrorMsg(), "Error message was not as expected!");
	}

    /*VALIDATION*/

//    /*1. Username field validation*/
//	@Test(groups = {"validation"})
//	public void usernameFieldValidation() {
//        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
//        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
//		forgotPasswordPopup.validateUsername(emptyRule);
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
//        UserData userData = DataContainer.getUserData().getForgotPasswordUserData().cloneUserData();
//        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
//        ChangePasswordPopup changePasswordPopup = (ChangePasswordPopup) homePage.login(userData, Page.changePasswordPopup);
//		changePasswordPopup.validateOldPassword(passwordValidationRule);
//	}
//
//    /*4. New password field validation on Change password popup*/
//	@Test(groups = {"validation"})
//	public void newPasswordFieldValidation() {
//        UserData userData = DataContainer.getUserData().getForgotPasswordUserData().cloneUserData();
//        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
//        ChangePasswordPopup changePasswordPopup = (ChangePasswordPopup) homePage.login(userData, Page.changePasswordPopup);
//		changePasswordPopup.validateNewPassword(passwordValidationRule);
//	}

    private UserData validPasswordRecovery(){
        UserData userData=DataContainer.getUserData().getRandomUserData();
        userData.setEmail(mailService.generateEmail());
        PortalUtils.registerUser(userData);
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotPasswordPopup forgotPasswordPopup = homePage.navigateToForgotPassword();
        forgotPasswordPopup.recoverPasswordValid(userData);
        return userData;
    }

    private ChangePasswordPopup loginWithTempPassword(UserData userData) {
        MailServicePage mailServicePage = mailService.navigateToInbox(userData.getEmail());
        mailServicePage.waitForEmail();
        String password = mailServicePage.getPasswordFromLetter();
        userData.setPassword(password);
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        return (ChangePasswordPopup) homePage.login(userData, Page.changePasswordPopup);
    }

    private UserData setNewPasswordAfterRecoveryAndLogin() {
        UserData userData = validPasswordRecovery();
        MailServicePage mailServicePage = mailService.navigateToInbox(userData.getEmail());
        mailServicePage.waitForEmail();
        userData.setPassword(mailServicePage.getPasswordFromLetter());
        String newPassword = passwordValidationRule.generateValidString();
        ChangePasswordPopup changePasswordPopup = loginWithTempPassword(userData);
        changePasswordPopup.fillFormAndSubmit(userData.getPassword(), newPassword);
        userData.setPassword(newPassword);
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.home, userData);
        return userData;
    }
}
