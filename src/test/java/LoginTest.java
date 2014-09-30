import enums.ConfiguredPages;
import enums.Page;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.core.AbstractPage;
import pageObjects.forgotPassword.ForgotPasswordPopup;
import pageObjects.login.LoginPopup;
import pageObjects.login.LogoutPopup;
import pageObjects.login.SignedOutPopup;
import pageObjects.registration.RegistrationPage;
import pageObjects.registration.classic.RegistrationPageAllSteps;
import springConstructors.IMS;
import springConstructors.UserData;
import springConstructors.ValidationRule;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.TypeUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

public class LoginTest extends AbstractTest {

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
	@Test(groups = {"smoke","desktop"})
	public void validUserLoginHeader() {
		UserData userData = defaultUserData.getRegisteredUserData();
        PortalUtils.loginUser(userData);
		validateTrue(new AbstractPage().isUsernameDisplayed(userData), "Correct username is displayed after login");
	}

    /*2. Login popup is available*/
    @Test(groups = {"smoke"})
    public void validUserLoginPopup() {
        UserData userData = defaultUserData.getRegisteredUserData();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        homePage.navigateToLoginForm().login(userData);
        validateTrue(new AbstractPage().isUsernameDisplayed(userData), "Correct username is displayed after login");
    }

	/*3. Remember Me disabled by default in header*/
	@Test(groups = {"regression","desktop"})
	public void rememberMeDisabledByDefaultInHeader(){
		WebDriverUtils.clearCookies();
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        validateFalse(homePage.getRememberMeCheckBoxState(), "Remember me enabled by default");
	}

	/*4. Remember Me disabled by default in popup*/
	@Test(groups = {"regression"})
	public void rememberMeDisabledByDefaultOnLoginPopup(){
		WebDriverUtils.clearCookies();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		LoginPopup loginPopup=homePage.navigateToLoginForm();
        validateFalse(loginPopup.getRememberMeCheckBoxState(), "Remember me enabled by default");
	}

	/*5. Login without Remember Me from header*/
	@Test(groups = {"regression","desktop"})
	public void usernameNotSavedAfterLoginWithoutRememberMe(){
		UserData userData=defaultUserData.getRegisteredUserData();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		homePage=(HomePage) homePage.login(userData, false);
        validateEquals("", homePage.logout().getEnteredUsernameFromLoginForm(), "Username empty after logout");
	}

	/*6. Login without Remember Me from popup*/
	@Test(groups = {"regression"})
	public void usernameNotSavedAfterLoginWithoutRememberMeOnLoginPopup(){
		UserData userData=defaultUserData.getRegisteredUserData();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		LoginPopup loginPopup=homePage.navigateToLoginForm();
		homePage= (HomePage) loginPopup.login(userData);
        loginPopup=homePage.navigateToLogoutPopup().clickLogoutButton().loginAgain();
        validateEquals("", loginPopup.getUsernameText(), "Username empty after logout");
	}

	/*7. Login with Remember Me from header*/
	@Test(groups = {"regression","desktop"})
	public void usernameSavedAfterLoginWithRememberMe(){
		UserData userData=defaultUserData.getRegisteredUserData();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		homePage=(HomePage) homePage.login(userData, true);
		validateEquals(userData.getUsername(), homePage.logout().getEnteredUsernameFromLoginForm(), "Correct username displayed");
	}

	/*8. Login with Remember Me from popup*/
	@Test(groups = {"regression"})
	public void usernameSavedAfterLoginWithRememberMeOnLoginPopup(){
		UserData userData=defaultUserData.getRegisteredUserData();
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		LoginPopup loginPopup=homePage.navigateToLoginForm();
		homePage=(HomePage) loginPopup.login(userData, true);
        loginPopup=homePage.navigateToLogoutPopup().clickLogoutButton().loginAgain();
		validateEquals(userData.getUsername(), loginPopup.getUsernameText(), "Correct username displayed");
	}

	/*9. Login + Remember Me + override old username from header*/
	@Test(groups = {"regression","desktop"})
	public void usernameIsOverwrittenAfterLoginWithRememberMe(){
		UserData userData=defaultUserData.getRegisteredUserData();
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		homePage=(HomePage) homePage.login(userData, true);
		homePage.logout();
        userData.setUsername(userData.getUsername()+"2");
		homePage=(HomePage) homePage.login(userData, true);
        homePage=(HomePage) homePage.logout();
        validateEquals(userData.getUsername(), homePage.getEnteredUsernameFromLoginForm(), "Correct username displayed");
	}

	/*10. Login + Remember Me + override old username from popoup*/
	@Test(groups = {"regression"})
	public void usernameIsOverwrittenAfterLoginWithRememberMeOnLoginPopup(){
		UserData userData=defaultUserData.getRegisteredUserData();
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        LoginPopup loginPopup=homePage.navigateToLoginForm();
        homePage=(HomePage) loginPopup.login(userData, true);
        loginPopup=homePage.navigateToLogoutPopup().clickLogoutButton().loginAgain();
		userData.setUsername(userData.getUsername()+"2");
		homePage=(HomePage) loginPopup.login(userData, true);
		loginPopup=homePage.navigateToLogoutPopup().clickLogoutButton().loginAgain();
        validateEquals(userData.getUsername(), loginPopup.getUsernameText(), "Correct username displayed");
	}

	/*11.1 Case-sensitive login*/
	@Test(groups = {"regression"})
	public void loginWithLowerCaseUsername(){
		UserData userData=defaultUserData.getRegisteredUserData();
		userData.setUsername("lowercase");
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		homePage=(HomePage)homePage.login(userData);
        assertTrue(homePage.isUsernameDisplayed(userData),"Username is displayed after login");
	}

	/*11.2 Case-sensitive login*/
	@Test(groups = {"regression"})
	public void loginWithUpperCaseUsername(){
		UserData userData=defaultUserData.getRegisteredUserData();
		userData.setUsername("UPPERCASE");
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		homePage=(HomePage)homePage.login(userData);
        assertTrue(homePage.isUsernameDisplayed(userData),"Username is displayed after login");
	}

	/*11.3 Case-sensitive login*/
	@Test(groups = {"regression"})
	public void loginWithMixedCaseUsername(){
		UserData userData=defaultUserData.getRegisteredUserData();
		userData.setUsername("MiXeDcAsE");
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		homePage=(HomePage)homePage.login(userData);
        assertTrue(homePage.isUsernameDisplayed(userData),"Username is displayed after login");
	}

    /*#12.1 Links work on popup*/
	@Test(groups = {"regression"})
	public void openRegisterPageFromLinkOnLoginPopup(){
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		LoginPopup loginPopup=homePage.navigateToLoginForm();
		RegistrationPage registrationPage = loginPopup.clickRegistration();
	}

//	/*#12.2 Links work on popup*/
//	@Test(groups = {"regression"})
//	public void openContactUsPopupFromLinkOnLoginPopup(){
//		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
//		LoginPopup loginPopup=homePage.navigateToLoginForm();
//		ContactUsPopup contactUsPopup=loginPopup.navigateToContactUs();
//	}

	/*#12.3 Links work on popup*/
	@Test(groups = {"regression"})
	public void openForgotPasswordPopupFromLinkOnLoginPopup(){
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		LoginPopup loginPopup=homePage.navigateToLoginForm();
		ForgotPasswordPopup forgotPasswordPopup=loginPopup.clickForgotPassword();
	}

    /*#13.1 Close popup*/
	@Test(groups = {"regression"})
	public void closeLoginPopup(){
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		LoginPopup loginPopup=homePage.navigateToLoginForm();
        assertFalse(loginPopup.close().isLoggedIn(), "User is logged in");
	}

//	/*#13.2 Close popup*/
//	@Test(groups = {"regression"})
//	public void closeLoginPopupByCancelButton(){
//		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
//		LoginPopup loginPopup=homePage.navigateToLoginForm();
//		homePage=loginPopup.cancel();
//        TypeUtils.assertFalseWithLogs(homePage.isLoggedIn(), "logged in");
//	}

//	/*14. Login logs*/
//	@Test(groups = {"regression","logs"})
//	public void loginLogs(){
//        try{
//            LogCategory[] logCategories = new LogCategory[]{LogCategory.LoginRequest,
//                    LogCategory.LoginResponse,
//                    LogCategory.StartWindowSessionRequest,
//                    LogCategory.StartWindowSessionResponse};
//            UserData userData=defaultUserData.getRegisteredUserData();
//            String[] loginParameters = {"objectIdentity="+userData.getUsername()+"-playtech81001",
//                    "clientPlatform=web",
//                    "clientType=portal"};
//            String[] sessionParameters = {"objectIdentity="+userData.getUsername()+"-playtech81001"};
//            HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
//            homePage.login(userData);
//            Log log = LogUtils.getCurrentLogs(logCategories);
//            log.doResponsesContainErrors();
//            LogEntry logReq = log.getEntry(LogCategory.LoginRequest);
//            LogEntry winSessionReq = log.getEntry(LogCategory.StartWindowSessionRequest);
//            logReq.containsParameters(loginParameters);
//            winSessionReq.containsParameters(sessionParameters);
//        }catch (RuntimeException e){
//            if(e.getMessage().contains("Not all registration logs appeared") || e.toString().contains("Logs have not been updated")){
//                throw new SkipException("Log page issue"+WebDriverUtils.getUrlAndLogs());
//            }else{
//                throw new RuntimeException(e.getMessage());
//            }
//        }
//	}
//
//	/*15. Logout logs*/
//	@Test(groups = {"regression","logs"})
//	public void logoutLogs(){
//        try{
//            LogCategory[] logCategories = new LogCategory[]{LogCategory.LogoutRequest};
//            UserData userData=defaultUserData.getRegisteredUserData();
//            String[] logoutParameters = {"objectIdentity="+userData.getUsername()+"-playtech81001"};
//            HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
//            homePage.login(userData);
//            homePage.logout();
//            Log log = LogUtils.getCurrentLogs(logCategories, 10);
//            LogEntry logReq = log.getEntry(LogCategory.LogoutRequest);
//            logReq.containsParameters(logoutParameters);
//        }catch (RuntimeException e){
//            if(e.getMessage().contains("Not all registration logs appeared") || e.toString().contains("Logs have not been updated")){
//                throw new SkipException("Log page issue"+WebDriverUtils.getUrlAndLogs());
//            }else{
//                throw new RuntimeException(e.getMessage());
//            }
//        }
//	}

    /*16. IMS Player Details Page*/
	@Test(groups = {"regression"})
	public void loginAndCheckStatusInIMS(){
		UserData userData=defaultUserData.getRegisteredUserData();
		PortalUtils.loginUser(userData);
        assertTrue(iMS.isPlayerLoggedIn(userData.getUsername()),"User is logged in on IMS");
	}

    /*17. Click Cancel on confirmation popup >> you are still logged in*/
    @Test(groups={"regression"})
    public void logoutConfirmationPopupCancel(){
        UserData userData=defaultUserData.getRegisteredUserData();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.home, userData);
        LogoutPopup logoutPopup = homePage.navigateToLogoutPopup();
        assertTrue(logoutPopup.close().isLoggedIn(), "User is logged in");

    }

    /*18. Click Cancel on login suggestion popup >> popup is closed, you are logged out*/
    @Test(groups={"regression"})
    public void reloginPopupCancel(){
        UserData userData=defaultUserData.getRegisteredUserData();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.home, userData);
        LogoutPopup logoutPopup = homePage.navigateToLogoutPopup();
        SignedOutPopup signedOutPopup = logoutPopup.clickLogoutButton();
        assertFalse(signedOutPopup.close().isLoggedIn(), "User is logged in");
    }

    /*19. Close log in popup >> login popup is closed, you are logged out*/
    @Test(groups={"regression"})
    public void reloginPopupReloginCancel(){
        UserData userData=defaultUserData.getRegisteredUserData();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.home, userData);
        LogoutPopup logoutPopup = homePage.navigateToLogoutPopup();
        SignedOutPopup signedOutPopup = logoutPopup.clickLogoutButton();
        LoginPopup loginPopup = signedOutPopup.loginAgain();
        assertFalse(loginPopup.close().isLoggedIn(), "User is logged in");
    }

    /*20. Log in again under the same player from login popup >> you are successfully logged in*/
    @Test(groups={"regression"})
    public void reloginPopupReloginSuccess(){
        UserData userData=defaultUserData.getRegisteredUserData();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.home, userData);
        LogoutPopup logoutPopup = homePage.navigateToLogoutPopup();
        SignedOutPopup signedOutPopup = logoutPopup.clickLogoutButton();
        LoginPopup loginPopup = signedOutPopup.loginAgain();
        assertTrue(loginPopup.login(userData).isLoggedIn(), "User is logged in");
    }

    /* NEGATIVE */

    /*#1. Login with invalid username from header*/
	@Test(groups = {"regression", "desktop"})
	public void invalidUsernameLoginFromHeader(){
		UserData userData=defaultUserData.getRegisteredUserData();
		userData.setUsername("incorrect");
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		LoginPopup loginPopup=(LoginPopup) homePage.login(userData, Page.loginPopup);
		assertTrue(loginPopup.validationErrorVisible(),"Error message displayed");
        assertEquals(userData.getUsername(), loginPopup.getUsernameText(), "Correct username is displayed");
        assertTrue((loginPopup.getPasswordText()).isEmpty(),"Password is empty");
	}

    /*#2. Login with invalid username from popup*/
	@Test(groups = {"regression"})
	public void invalidUsernameLoginFromLoginPopup(){
		UserData userData=defaultUserData.getRegisteredUserData();
		userData.setUsername("incorrect");
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		LoginPopup loginPopup=homePage.navigateToLoginForm();
		loginPopup=(LoginPopup) loginPopup.login(userData, false, Page.loginPopup);
        assertTrue(loginPopup.validationErrorVisible(),"Error message displayed");
        assertEquals(userData.getUsername(), loginPopup.getUsernameText(),"Correct username is displayed");
        assertTrue((loginPopup.getPasswordText()).isEmpty(),"Password is empty");
	}
	/*#3. Login wih invalid password from header*/
	@Test(groups = {"regression", "desktop"})
	public void invalidPasswordLoginFromHeader(){
		UserData userData=defaultUserData.getRegisteredUserData();
		userData.setPassword("incorrect");
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		LoginPopup loginPopup=(LoginPopup) homePage.login(userData, false, Page.loginPopup);
        assertTrue(loginPopup.validationErrorVisible(),"Error message displayed");
        assertEquals(userData.getUsername() ,loginPopup.getUsernameText(),"Correct username is displayed");
        assertTrue((loginPopup.getPasswordText()).isEmpty(),"Password is empty");
	}

    /*#4. Login wih invalid password from popup*/
	@Test(groups = {"regression"})
	public void invalidPasswordLoginFromLoginPopup(){
		UserData userData=defaultUserData.getRegisteredUserData();
		userData.setPassword("incorrect");
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		LoginPopup loginPopup=homePage.navigateToLoginForm();
		loginPopup=(LoginPopup) loginPopup.login(userData, false, Page.loginPopup);
        assertTrue(loginPopup.validationErrorVisible(),"Error message displayed");
        assertEquals(userData.getUsername(), loginPopup.getUsernameText(),"Correct username is displayed");
        assertTrue((loginPopup.getPasswordText()).isEmpty(),"Password is empty");
	}

    /*Session is saved after visiting external resource*/
    @Test(groups = {"regression"})
    public void sessionIsSavedAfterVisitingExternalResource(){
        UserData userData = defaultUserData.getRegisteredUserData();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.home, userData);
        WebDriverUtils.navigateToURL("http://www.google.com/");
        WebDriverUtils.waitForPageToLoad();
        homePage = (HomePage) NavigationUtils.navigateToPage(ConfiguredPages.home);
        assertTrue(homePage.isLoggedIn(), "Is still logged in");
    }

    /* VALIDATION */

//	/*1. Username in header*/
//	@Test(groups = {"validation"})
//	public void usernameFieldValidationInHeader() {
//		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
//		homePage.loggedOutHeader().validateUsername(usernameValidationRule);
//  	}
//
//	/*2. Password in header*/
//	@Test(groups = {"validation"})
//	public void passwordFieldValidationInHeader() {
//		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
//		homePage.loggedOutHeader().validatePassword(passwordValidationRule);
//	}
//
//	/*3. Username in login popup*/
//	@Test(groups = {"validation"})
//	public void usernameFieldValidationInLoginPopup() {
//		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
//		LoginPopup loginPopup = homePage.navigateToLoginForm();
//		loginPopup.validateUsername(usernameValidationRule);
//	}
//
//	/*4. Password in login popup*/
//	@Test(groups = {"validation"})
//	public void passwordFieldValidationInLoginPopup() {
//		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
//		LoginPopup loginPopup = homePage.navigateToLoginForm();
//		loginPopup.validatePassword(passwordValidationRule);
//	}
}
