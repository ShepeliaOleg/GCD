import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.login.LoginPopup;
import pageObjects.login.LogoutPopup;
import pageObjects.login.SignedOutPopup;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

public class LoginLogoutConfirmationTest extends AbstractTest {

	/* POSITIVE */
	
	/*17. Click Cancel on confirmation popup >> you are still logged in*/
    @Test(groups={"regression"})
    public void logoutConfirmationPopupCancel(){
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.home);
        LogoutPopup logoutPopup = homePage.navigateToLogoutPopup();
        assertTrue(logoutPopup.close().isLoggedIn(), "User is logged in");

    }

    /*18. Click Cancel on login suggestion popup >> popup is closed, you are logged out*/
    @Test(groups={"regression"})
    public void reloginPopupCancel(){
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.home);
        LogoutPopup logoutPopup = homePage.navigateToLogoutPopup();
        SignedOutPopup signedOutPopup = logoutPopup.clickLogoutButton();
        assertFalse(signedOutPopup.close().isLoggedIn(), "User is logged in");
    }

    /*19. Close log in popup >> login popup is closed, you are logged out*/
    @Test(groups={"regression"})
    public void reloginPopupReloginCancel(){
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.home);
        LogoutPopup logoutPopup = homePage.navigateToLogoutPopup();
        SignedOutPopup signedOutPopup = logoutPopup.clickLogoutButton();
        LoginPopup loginPopup = signedOutPopup.loginAgain();
        loginPopup.closePopup();
        assertFalse(homePage.isLoggedIn(), "User is logged in");
    }

    /*20. Log in again under the same player from login popup >> you are successfully logged in*/
    @Test(groups={"regression"})
    public void reloginPopupReloginSuccess(){
        UserData userData= DataContainer.getUserData().getRegisteredUserData();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.home);
        LogoutPopup logoutPopup = homePage.navigateToLogoutPopup();
        SignedOutPopup signedOutPopup = logoutPopup.clickLogoutButton();
        LoginPopup loginPopup = signedOutPopup.loginAgain();
        assertTrue(loginPopup.login(userData).isLoggedIn(), "User is logged in");
    }
}
