import enums.ConfiguredPages;
import enums.Page;
import enums.PlayerCondition;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.core.AbstractPage;
import pageObjects.forgotPassword.ForgotPasswordPopup;
import pageObjects.login.LoginPopup;
import pageObjects.registration.RegistrationPage;
import springConstructors.UserData;
import utils.IMSUtils;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

public class LoginTest extends AbstractTest{

	/* POSITIVE */
	
	/*1. Valid user login*/
	@Test(groups = {"smoke","desktop"})
	public void validUserLoginHeader() {
		UserData userData = DataContainer.getUserData().getRegisteredUserData();
        PortalUtils.loginUser(userData);
		validateTrue(new AbstractPage().isUsernameDisplayed(userData.getUsername()), "Correct username is displayed after login");
	}

    /*username with spaces*/
    @Test(groups = {"regression"})
    public void spacedUsernameLoginFromLoginPopup(){
        UserData userData= DataContainer.getUserData().getRegisteredUserData();
        userData.setUsername(" "+userData.getUsername()+" ");
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        homePage.navigateToLoginForm().login(userData);
        validateTrue(new AbstractPage().isUsernameDisplayed(userData.getUsername().trim()), "Correct username is displayed after login");
    }

    /*2. Login popup is available*/
    @Test(groups = {"smoke"})
    public void validUserLoginPopup() {
        UserData userData = DataContainer.getUserData().getRegisteredUserData();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        homePage.navigateToLoginForm().login(userData);
        validateTrue(new AbstractPage().isUsernameDisplayed(userData.getUsername()), "Correct username is displayed after login");
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
		UserData userData=DataContainer.getUserData().getRegisteredUserData();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		homePage=(HomePage) homePage.login(userData, false);
        validateEquals("", homePage.logout().getEnteredUsernameFromLoginForm(), "Username empty after logout");
	}

	/*6. Login without Remember Me from popup*/
	@Test(groups = {"regression"})
	public void usernameNotSavedAfterLoginWithoutRememberMeOnLoginPopup(){
		UserData userData=DataContainer.getUserData().getRegisteredUserData();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		LoginPopup loginPopup=homePage.navigateToLoginForm();
		homePage= (HomePage) loginPopup.login(userData);
        loginPopup=homePage.navigateToLogoutPopup().clickLogoutButton().loginAgain();
        validateEquals("", loginPopup.getUsernameText(), "Username empty after logout");
	}

	/*7. Login with Remember Me from header*/
	@Test(groups = {"regression","desktop"})
	public void usernameSavedAfterLoginWithRememberMe(){
		UserData userData=DataContainer.getUserData().getRegisteredUserData();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		homePage=(HomePage) homePage.login(userData, true);
		validateEquals(userData.getUsername(), homePage.logout().getEnteredUsernameFromLoginForm(), "Correct username displayed");
	}

	/*8. Login with Remember Me from popup*/
	@Test(groups = {"regression"})
	public void usernameSavedAfterLoginWithRememberMeOnLoginPopup(){
		UserData userData=DataContainer.getUserData().getRegisteredUserData();
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		LoginPopup loginPopup=homePage.navigateToLoginForm();
		homePage=(HomePage) loginPopup.login(userData, true);
        loginPopup=homePage.navigateToLogoutPopup().clickLogoutButton().loginAgain();
		validateEquals(userData.getUsername(), loginPopup.getUsernameText(), "Correct username displayed");
	}

	/*9. Login + Remember Me + override old username from header*/
	@Test(groups = {"regression","desktop"})
	public void usernameIsOverwrittenAfterLoginWithRememberMe(){
		UserData userData=DataContainer.getUserData().getRegisteredUserData();
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
		UserData userData=DataContainer.getUserData().getRegisteredUserData();
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        LoginPopup loginPopup=homePage.navigateToLoginForm();
        homePage=(HomePage) loginPopup.login(userData, true);
        loginPopup=homePage.navigateToLogoutPopup().clickLogoutButton().loginAgain();
		userData.setUsername(userData.getUsername()+"2");
		homePage=(HomePage) loginPopup.login(userData, true);
		loginPopup=homePage.navigateToLogoutPopup().clickLogoutButton().loginAgain();
        validateEquals(userData.getUsername(), loginPopup.getUsernameText(), "Correct username displayed");
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
        loginPopup.closePopup();
        assertFalse(homePage.isLoggedIn(), "User is logged in");
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
//            UserData userData=DataContainer.getUserData().getRegisteredUserData();
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
//            UserData userData=DataContainer.getUserData().getRegisteredUserData();
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
		UserData userData=DataContainer.getUserData().getRegisteredUserData();
		PortalUtils.loginUser(userData);
        assertTrue(IMSUtils.isPlayerLoggedIn(userData.getUsername()),"User is logged in on IMS");
	}

    /* NEGATIVE */

    /*Case-sensitive login*/
    @Test(groups = {"regression"})
    public void loginWithLowerCaseUsername(){
        UserData userData=DataContainer.getUserData().getRegisteredUserData();
        userData.setUsername(userData.getUsernameLowercase());
        assertFailedLoginPopup(userData);
    }

    @Test(groups = {"regression"})
    public void loginWithUpperCaseUsername(){
        UserData userData=DataContainer.getUserData().getRegisteredUserData();
        userData.setUsername(userData.getUsernameUppercase());
        assertFailedLoginPopup(userData);
    }

    @Test(groups = {"regression"})
    public void loginWithMixedCaseUsername(){
        UserData userData=DataContainer.getUserData().getRegisteredUserData();
        userData.setUsername(userData.getUsernameMixedcase());
        assertFailedLoginPopup(userData);
    }

    /*#1. Login with invalid username from header*/
	@Test(groups = {"regression", "desktop"})
	public void invalidUsernameLoginFromHeader(){
		UserData userData=DataContainer.getUserData().getRegisteredUserData();
		userData.setUsername("incorrect");
        assertFailedLoginPopup(userData);
    }

    /*#2. Login with invalid username from popup*/
	@Test(groups = {"regression"})
	public void invalidUsernameLoginFromLoginPopup(){
        UserData userData=DataContainer.getUserData().getRegisteredUserData();
        userData.setUsername("incorrect");
        assertFailedLoginPopup(userData);
    }

    /*empty username*/
    @Test(groups = {"regression"})
    public void emptyUsernameLoginFromLoginPopup(){
        UserData userData=DataContainer.getUserData().getRegisteredUserData();
        userData.setUsername("");
        assertFailedLoginPopup(userData);
    }

    /*#3. Login wih invalid password from header*/
	@Test(groups = {"regression", "desktop"})
	public void invalidPasswordLoginFromHeader(){
        UserData userData=DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser();
        userData.setPassword("incorrect");
		assertFailedLoginPopup(userData);
	}

    /*#4. Login wih invalid password from popup*/
	@Test(groups = {"regression"})
	public void invalidPasswordLoginFromLoginPopup(){
        UserData userData=DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser();
        userData.setPassword("incorrect");
        assertFailedLoginPopup(userData);
	}

    /*empty password*/
    @Test(groups = {"regression"})
    public void emptyPasswordLoginFromLoginPopup(){
        UserData userData=DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser();
        userData.setPassword("");
        assertFailedLoginPopup(userData);
    }

    /*password with spaces*/
    @Test(groups = {"regression"})
    public void spacedPasswordLoginFromLoginPopup(){
        UserData userData=DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser();
        userData.setPassword(" " + userData.getPassword() + " ");
        assertFailedLoginPopup(userData);
    }

    /*password different case*/
    @Test(groups = {"regression"})
    public void differentCasePasswordLoginFromLoginPopup(){
        UserData userData=DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser();
        userData.setPassword(userData.getPasswordMixedcase());
        assertFailedLoginPopup(userData);
    }

    /*login with invalid credentials 3 times - try to log in with correct password - try to log in after unlock*/
    @Test(groups = {"regression"})
    public void freezeUserAfterInvalidLogins(){
        UserData userData=DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser();
        String correctPass = userData.getPassword();
        userData.setPassword("incorrect");
        for(int i=0;i<3;i++){
            assertFailedLoginPopup(userData);
        }
        userData.setPassword(correctPass);
        assertFailedLoginPopup(userData);
        IMSUtils.navigateToPlayedDetails(userData.getUsername()).resetFailedLogins();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        homePage.navigateToLoginForm().login(userData);
        validateTrue(new AbstractPage().isUsernameDisplayed(userData.getUsername()), "Correct username is displayed after login");
    }

    private void assertFailedLoginPopup(UserData userData) {
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        LoginPopup loginPopup = (LoginPopup) homePage.navigateToLoginForm().login(userData, false, Page.loginPopup);
        assertTrue(loginPopup.validationErrorVisible(),"Error message displayed");
        assertEquals(userData.getUsername(), loginPopup.getUsernameText(),"Correct username is displayed");
    }
}
