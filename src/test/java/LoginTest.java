import enums.ConfiguredPages;
import enums.LogCategory;
import enums.Page;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.account.LoginPopup;
import pageObjects.base.AbstractPage;
import pageObjects.forgotPassword.ContactUsPopup;
import pageObjects.forgotPassword.ForgotPasswordPopup;
import pageObjects.popups.AcceptTermsAndConditionsPopup;
import pageObjects.registration.RegistrationPage;
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
public class LoginTest extends AbstractTest{

	@Autowired
	@Qualifier("userData")
	private UserData defaultUserData;
	 
	@Autowired
	@Qualifier("usernameValidationRule")
	private ValidationRule usernameValidationRule;

	@Autowired
	@Qualifier("passwordValidationRule")
	private ValidationRule passwordValidationRule;

	@Autowired
	@Qualifier("emailSubjectValidationRule")
	private ValidationRule emailSubjectValidationRule;

	@Autowired
	@Qualifier("iMS")
	private IMS iMS;
	
	/* POSITIVE */
	
	/*1. Valid user login*/
	@Test(groups = {"smoke"})
	public void validUserLogin() {
		UserData userData = defaultUserData.getRegisteredUserData();
        PortalUtils.loginUser(userData);
		Assert.assertEquals(new AbstractPage().isUsernameDisplayed(userData), true);
	}

	/*3. Remember Me disabled by default in header*/
	@Test(groups = {"regression"})
	public void rememberMeDisabledByDefaultInHeader(){
		WebDriverUtils.clearCookies();
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		Assert.assertFalse(homePage.getRememberMeCheckBoxState());
	}

	/*4. Remember Me disabled by default in popup*/
	@Test(groups = {"regression"})
	public void rememberMeDisabledByDefaultOnLoginPopup(){
		WebDriverUtils.clearCookies();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		LoginPopup loginPopup=homePage.navigateToLoginForm();
		Assert.assertFalse(loginPopup.getRememberMeCheckBoxState());
	}

	/*5. Login without Remember Me from header*/
	@Test(groups = {"regression"})
	public void usernameNotSavedAfterLoginWithoutRememberMe(){
		UserData userData=defaultUserData.getRegisteredUserData();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		homePage=(HomePage) homePage.login(userData, false);
		String username=homePage.logout().getEnteredUsernameFromLoginForm();
		Assert.assertTrue(username.equals(""));
	}

	/*6. Login without Remember Me from popup*/
	@Test(groups = {"regression"})
	public void usernameNotSavedAfterLoginWithoutRememberMeOnLoginPopup(){
		UserData userData=defaultUserData.getRegisteredUserData();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		LoginPopup loginPopup=homePage.navigateToLoginForm();
		homePage=loginPopup.login(userData);
		loginPopup=homePage.logout().navigateToLoginForm();
		String username=loginPopup.getUsernameText();
		Assert.assertTrue(username.equals(""));
	}

	/*7. Login with Remember Me from header*/
	@Test(groups = {"regression"})
	public void usernameSavedAfterLoginWithRememberMe(){
		UserData userData=defaultUserData.getRegisteredUserData();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		homePage=(HomePage) homePage.login(userData, true);
        String username=homePage.logout().getEnteredUsernameFromLoginForm();
		Assert.assertTrue(username.equals(userData.getUsername()));
	}

	/*8. Login with Remember Me from popup*/
	@Test(groups = {"regression"})
	public void usernameSavedAfterLoginWithRememberMeOnLoginPopup(){
		UserData userData=defaultUserData.getRegisteredUserData();
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		LoginPopup loginPopup=homePage.navigateToLoginForm();
		homePage=(HomePage) loginPopup.login(userData, true);
		loginPopup=homePage.logout().navigateToLoginForm();
		String username=loginPopup.getUsernameText();
		Assert.assertTrue(username.equals(userData.getUsername()));
	}

	/*9. Login + Remember Me + override old username from header*/
	@Test(groups = {"regression"})
	public void usernameIsOverwrittenAfterLoginWithRememberMe(){
		UserData userData=defaultUserData.getRegisteredUserData();
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		homePage=(HomePage) homePage.login(userData, true);
		String username1=homePage.logout().getEnteredUsernameFromLoginForm();
		userData.setUsername("player73");
		userData.setPassword("123456");
		homePage=(HomePage) homePage.login(userData, true);
        String username2=homePage.logout().getEnteredUsernameFromLoginForm();
		Assert.assertTrue(username1.equals("") == false && username2.equals(username1) == false);
	}

	/*10. Login + Remember Me + override old username from popoup*/
	@Test(groups = {"regression"})
	public void usernameIsOverwrittenAfterLoginWithRememberMeOnLoginPopup(){
		UserData userData=defaultUserData.getRegisteredUserData();
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		LoginPopup loginPopup=homePage.navigateToLoginForm();
		homePage=(HomePage) loginPopup.login(userData, true);
		loginPopup=homePage.logout().navigateToLoginForm();
        String changedUsername = userData.getUsername()+"1";
		userData.setUsername(changedUsername);
		homePage=(HomePage) loginPopup.login(userData, true);
		loginPopup=homePage.logout().navigateToLoginForm();
		String username2=loginPopup.getUsernameText();
		Assert.assertTrue(username2.equals(changedUsername));
	}

	/*11.1 Case-sensitive login*/
	@Test(groups = {"regression"})
	public void loginWithLowerCaseUsername(){
		UserData userData=defaultUserData.getRegisteredUserData();
		userData.setUsername("lowercase");
		userData.setPassword("123456");
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		homePage=(HomePage)homePage.login(userData);
		boolean successfulLogin=homePage.usernameOfLoggedInPlayerIsDisplayedInHeader(userData);
		Assert.assertTrue(successfulLogin);
	}

	/*11.2 Case-sensitive login*/
	@Test(groups = {"regression"})
	public void loginWithUpperCaseUsername(){
		UserData userData=defaultUserData.getRegisteredUserData();
		userData.setUsername("UPPERCASE");
		userData.setPassword("123456");
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		homePage=(HomePage)homePage.login(userData);
		boolean successfulLogin=homePage.usernameOfLoggedInPlayerIsDisplayedInHeader(userData);
		Assert.assertTrue(successfulLogin);
	}

	/*11.3 Case-sensitive login*/
	@Test(groups = {"regression"})
	public void loginWithMixedCaseUsername(){
		UserData userData=defaultUserData.getRegisteredUserData();
		userData.setUsername("MiXeDcAsE");
		userData.setPassword("123456");
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		homePage=(HomePage)homePage.login(userData);
		boolean successfulLogin=homePage.usernameOfLoggedInPlayerIsDisplayedInHeader(userData);
		Assert.assertTrue(successfulLogin);
	}

    /*#12.1 Links work on popup*/
	@Test(groups = {"regression"})
	public void openRegisterPageFromLinkOnLoginPopup(){
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		LoginPopup loginPopup=homePage.navigateToLoginForm();
		RegistrationPage registrationPage = loginPopup.navigateToRegistration();
	}

	/*#12.2 Links work on popup*/
	@Test(groups = {"regression"})
	public void openContactUsPopupFromLinkOnLoginPopup(){
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		LoginPopup loginPopup=homePage.navigateToLoginForm();
		ContactUsPopup contactUsPopup=loginPopup.navigateToContactUs();
	}

	/*#12.3 Links work on popup*/
	@Test(groups = {"regression"})
	public void openForgotPasswordPopupFromLinkOnLoginPopup(){
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		LoginPopup loginPopup=homePage.navigateToLoginForm();
		ForgotPasswordPopup forgotPasswordPopup=loginPopup.navigateToForgotPassword();
	}

    /*#13.1 Close popup*/
	@Test(groups = {"regression"})
	public void closeLoginPopupByXButton(){
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		LoginPopup loginPopup=homePage.navigateToLoginForm();
		homePage=loginPopup.close();
		boolean isLoggedIn=homePage.isLoggedIn();
		Assert.assertTrue(isLoggedIn == false);
	}

	/*#13.2 Close popup*/
	@Test(groups = {"regression"})
	public void closeLoginPopupByCancelButton(){
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		LoginPopup loginPopup=homePage.navigateToLoginForm();
		homePage=loginPopup.cancel();
		boolean isLoggedIn=homePage.isLoggedIn();
		Assert.assertTrue(isLoggedIn == false);
	}

	/*14. Login logs*/
	@Test(groups = {"regression","logs"})
	public void loginLogs(){
        try{
            LogCategory[] logCategories = new LogCategory[]{LogCategory.LoginRequest,
                    LogCategory.LoginResponse,
                    LogCategory.StartWindowSessionRequest,
                    LogCategory.StartWindowSessionResponse};
            UserData userData=defaultUserData.getRegisteredUserData();
            String[] loginParameters = {"objectIdentity="+userData.getUsername()+"-playtech81001",
                    "clientPlatform=web",
                    "clientType=portal"};
            String[] sessionParameters = {"objectIdentity="+userData.getUsername()+"-playtech81001"};
            HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
            homePage.login(userData);
            Log log = LogUtils.getCurrentLogs(logCategories);
            log.doResponsesContainErrors();
            LogEntry logReq = log.getEntry(LogCategory.LoginRequest);
            LogEntry winSessionReq = log.getEntry(LogCategory.StartWindowSessionRequest);
            logReq.containsParameters(loginParameters);
            winSessionReq.containsParameters(sessionParameters);
        }catch (RuntimeException e){
            if(e.getMessage().contains("Not all registration logs appeared") || e.toString().contains("Logs have not been updated")){
                throw new SkipException("Log page issue"+WebDriverUtils.getUrlAndLogs());
            }else{
                throw new RuntimeException(e.getMessage());
            }
        }
	}

	/*15. Logout logs*/
	@Test(groups = {"regression","logs"})
	public void logoutLogs(){
        try{
            LogCategory[] logCategories = new LogCategory[]{LogCategory.LogoutRequest};
            UserData userData=defaultUserData.getRegisteredUserData();
            String[] logoutParameters = {"objectIdentity="+userData.getUsername()+"-playtech81001"};
            HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
            homePage.login(userData);
            homePage.logout();
            Log log = LogUtils.getCurrentLogs(logCategories, 10);
            LogEntry logReq = log.getEntry(LogCategory.LogoutRequest);
            logReq.containsParameters(logoutParameters);
        }catch (RuntimeException e){
            if(e.getMessage().contains("Not all registration logs appeared") || e.toString().contains("Logs have not been updated")){
                throw new SkipException("Log page issue"+WebDriverUtils.getUrlAndLogs());
            }else{
                throw new RuntimeException(e.getMessage());
            }
        }
	}

    /*16. IMS Player Details Page*/
	@Test(groups = {"regression"})
	public void loginAndCheckStatusInIMS(){
		UserData userData=defaultUserData.getRegisteredUserData();
		userData.setUsername("MiXeDcAsE");
		userData.setPassword("123456");
		PortalUtils.loginUser(userData);
		boolean successfulLogin=iMS.isPlayerLoggedIn(userData.getUsername());
		Assert.assertTrue(successfulLogin);
	}

    /* NEGATIVE */

    /*#1. Login with invalid username from header*/
	@Test(groups = {"regression"})
	public void invalidUsernameLoginFromHeader(){
		UserData userData=defaultUserData.getRegisteredUserData();
		userData.setUsername("incorrect");
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		LoginPopup loginPopup=(LoginPopup) homePage.login(userData, Page.loginPopup);
		boolean incorrectCredentialsErrorMessageDisplayed=loginPopup.validationErrorVisible();
		boolean usernameDisplayed=(userData.getUsername()).equals(loginPopup.getUsernameText());
		boolean passwordEmpty=(loginPopup.getPasswordText()).isEmpty();
		Assert.assertTrue(incorrectCredentialsErrorMessageDisplayed == true && usernameDisplayed == true && passwordEmpty == true);
	}

    /*#2. Login with invalid username from popup*/
	@Test(groups = {"regression"})
	public void invalidUsernameLoginFromLoginPopup(){
		UserData userData=defaultUserData.getRegisteredUserData();
		userData.setUsername("incorrect");
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		LoginPopup loginPopup=homePage.navigateToLoginForm();
		loginPopup=(LoginPopup) loginPopup.login(userData, false, Page.loginPopup);
		boolean incorrectCredentialsErrorMessageDisplayed=loginPopup.validationErrorVisible();
		boolean usernameDisplayed=(loginPopup.getUsernameText()).equals(userData.getUsername());
		boolean passwordEmpty=(loginPopup.getPasswordText()).isEmpty();
		Assert.assertTrue(incorrectCredentialsErrorMessageDisplayed == true && usernameDisplayed == true && passwordEmpty == true);
	}
	/*#3. Login wih invalid password from header*/
	@Test(groups = {"regression"})
	public void invalidPasswordLoginFromHeader(){
		UserData userData=defaultUserData.getRegisteredUserData();
		userData.setPassword("incorrect");
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		LoginPopup loginPopup=(LoginPopup) homePage.login(userData, false, Page.loginPopup);
		boolean incorrectCredentialsErrorMessageDisplayed=loginPopup.validationErrorVisible();
		boolean usernameDisplayed=(loginPopup.getUsernameText()).equals(userData.getUsername());
		boolean passwordEmpty=(loginPopup.getPasswordText()).isEmpty();
		Assert.assertTrue(incorrectCredentialsErrorMessageDisplayed == true && usernameDisplayed == true && passwordEmpty == true);
	}

    /*#4. Login wih invalid password from popup*/
	@Test(groups = {"regression"})
	public void invalidPasswordLoginFromLoginPopup(){
		UserData userData=defaultUserData.getRegisteredUserData();
		userData.setPassword("incorrect");
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		LoginPopup loginPopup=homePage.navigateToLoginForm();
		loginPopup=(LoginPopup) loginPopup.login(userData, false, Page.loginPopup);
		boolean incorrectCredentialsErrorMessageDisplayed=loginPopup.validationErrorVisible();
		boolean usernameDisplayed=(loginPopup.getUsernameText()).equals(userData.getUsername());
		boolean passwordEmpty=(loginPopup.getPasswordText()).isEmpty();
		Assert.assertTrue(incorrectCredentialsErrorMessageDisplayed == true && usernameDisplayed == true && passwordEmpty == true);
	}

    /* VALIDATION */

	/*1. Username in header*/
	@Test(groups = {"validation"})
	public void usernameFieldValidationInHeader() {
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		homePage.loggedOutHeader().validateUsername(usernameValidationRule);
  	}

	/*2. Password in header*/
	@Test(groups = {"validation"})
	public void passwordFieldValidationInHeader() {
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		homePage.loggedOutHeader().validatePassword(passwordValidationRule);
	}

	/*3. Username in login popup*/
	@Test(groups = {"validation"})
	public void usernameFieldValidationInLoginPopup() {
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		LoginPopup loginPopup = homePage.navigateToLoginForm();
		loginPopup.validateUsername(usernameValidationRule);
	}

	/*4. Password in login popup*/
	@Test(groups = {"validation"})
	public void passwordFieldValidationInLoginPopup() {
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		LoginPopup loginPopup = homePage.navigateToLoginForm();
		loginPopup.validatePassword(passwordValidationRule);
	}
}
