import enums.ConfiguredPages;
import enums.Page;
import enums.PlayerCondition;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.core.AbstractPortalPage;
import pageObjects.forgotPassword.ForgotPasswordPopup;
import pageObjects.header.Header;
import pageObjects.header.LoggedInHeader;

import pageObjects.header.LoggedOutHeader;
import pageObjects.login.LoginPopup;
import pageObjects.registration.RegistrationPage;
import springConstructors.UserData;
import utils.IMSUtils;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

public class LoginTestGCD extends AbstractTest{

	/* POSITIVE */
	
	/*1. Valid user login*/
	@Test(groups = {"smoke"})
	public void validUserLoginHeader() {
		UserData userData = DataContainer.getUserData().getRegisteredUserData();
        PortalUtils.loginUser(userData);
		validateTrue(new AbstractPortalPage().isUsernameDisplayed(userData.getFirstName().toLowerCase()), "Correct username is displayed after login");
	}

    /*2. Login popup is available*/
    @Test(groups = {"smoke"})
    public void validUserLoginPopup() {
        UserData userData = DataContainer.getUserData().getRegisteredUserData();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        homePage.navigateToLoginForm().login(userData);
        validateTrue(new AbstractPortalPage().isUsernameDisplayed(userData.getFirstName().toLowerCase()), "Correct username is displayed after login");
    }

	/*3. Remember Me disabled by default in header*/
	@Test(groups = {"regression","desktop"})
	public void rememberMeDisabledByDefaultInHeader(){
		WebDriverUtils.clearCookies();
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        validateFalse(homePage.getRememberMeCheckBoxState(), "Remember me enabled by default");
	}

	/*7. Login with Remember Me from header*/
	@Test(groups = {"regression","desktop"})
	public void usernameSavedAfterLoginWithRememberMe(){
        UserData userData=DataContainer.getUserData().getRegisteredUserData();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        homePage.loginFromHeader(userData, true, Page.homePage);
        homePage.logout();
        validateEquals(userData.getUsername(), homePage.loggedOutHeader().getUsernameText().toLowerCase(), "Correct username displayed");
	}

    /*5. Login without Remember Me from header*/
    @Test(groups = {"regression","desktop"})
    public void usernameNotSavedAfterLoginWithoutRememberMe(){
        UserData userData=DataContainer.getUserData().getRegisteredUserData();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        homePage=(HomePage) homePage.login(userData, false);
        validateEquals("", homePage.logout().getEnteredUsernameFromLoginForm(), "Username empty after logout");
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
        homePage.logout();
        System.out.println(userData.getUsername() + " ------");
        validateEquals(userData.getUsername(), homePage.getEnteredUsernameFromLoginForm().toLowerCase(), "Correct username displayed");
    }

    /*#4. Login wih invalid password from popup*/
    @Test(groups = {"regression"})
    public void invalidPasswordLoginFromLoginPopup(){
        UserData userData=DataContainer.getUserData().getRegisteredUserData();
        userData.setPassword("incorrect");
        assertFailedLoginPopup(userData);
    }

    /*#2. Login with invalid username*/
    @Test(groups = {"regression"})
    public void invalidUsernameLoginFromLoginPopup(){
        UserData userData=DataContainer.getUserData().getRegisteredUserData();
        userData.setUsername("incorrect");
        assertFailedLoginPopup(userData);
    }

    @Test(groups = {"regression"})
    public void emptyPasswordLoginFromLoginPopup(){
        UserData userData=DataContainer.getUserData().getRandomUserData();
        userData.setPassword("");
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        LoginPopup loginPopup = (LoginPopup) homePage.navigateToLoginForm().login(userData, false, Page.loginPopup);
        assertEquals(userData.getUsername(), loginPopup.getUsernameText().toUpperCase(),"Correct username is displayed");
    }

    /*username with spaces*/
    @Test(groups = {"regression"})
    public void spacedUsernameLoginFromLoginPopup(){
        UserData userData= DataContainer.getUserData().getRegisteredUserData();
        userData.setUsername(" "+userData.getUsername()+" ");
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        homePage.navigateToLoginForm().login(userData);
        validateTrue(new AbstractPortalPage().isUsernameDisplayed(userData.getFirstName().trim()), "Correct username is displayed after login");
        // validateTrue(new AbstractPortalPage().isUsernameDisplayed("TEST"), "Correct username is displayed after login");
    }

    /*password with spaces*/
    @Test(groups = {"regression"})
    public void spacedPasswordLoginFromLoginPopup(){
        UserData userData=DataContainer.getUserData().getRegisteredUserData();
        userData.setPassword(" " + userData.getPassword() + " ");
        //PortalUtils.loginUser(userData);
        assertFailedLoginPopup(userData);
    }

    @Test(groups = {"regression"})
    public void openRegisterPageFromLinkOnLoginPopup(){
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        LoginPopup loginPopup=homePage.navigateToLoginForm();
        RegistrationPage registrationPage = loginPopup.clickRegistration();
    }

    @Test(groups = {"regression"})
    public void openForgotUsernameOrPasswordPopupFromLinkOnLoginPopup(){
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        LoginPopup loginPopup=homePage.navigateToLoginForm();
        ForgotPasswordPopup forgotPasswordPopup=loginPopup.clickForgotPassword();
    }














    private void assertFailedLoginPopup(UserData userData) {
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        LoginPopup loginPopup = (LoginPopup) homePage.navigateToLoginForm().login(userData, false, Page.loginPopup);
        assertTrue(loginPopup.validationErrorVisible(),"Error message displayed");
        assertEquals(userData.getUsername(), loginPopup.getUsernameText().toLowerCase(),"Correct username is displayed");
    }
}
