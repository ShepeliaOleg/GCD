import enums.ConfiguredPages;
import enums.GameLaunch;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.admin.AdminCanNotPlayPopup;
import pageObjects.login.LoginPopup;
import pageObjects.webcontent.WebContentPage;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.TypeUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

public class WebContentTest extends AbstractTest{

    @Autowired
    @Qualifier("userData")
    private UserData defaultUserData;

    /*Web content display - Guest login popup login button*/
    @Test(groups = {"webcontent","regression"})
    public void displayButtonLaunchGameGuestLogin() {
        WebContentPage webContentPage = (WebContentPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.webContentGame);
        webContentPage.playAndValidateUrl(GameLaunch.button, 1, defaultUserData.getRegisteredUserData());
    }

    /*Web content display - Guest login popup cancel button*/
    @Test(groups = {"webcontent","regression"})
    public void displayButtonLaunchGameGuestCancel() {
        WebContentPage webContentPage = (WebContentPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.webContentGame);
        LoginPopup loginPopup = webContentPage.clickLoggedOut(GameLaunch.button, 1);
        loginPopup.close();
        validateFalse(WebDriverUtils.isGameLaunched(ConfiguredPages.webContentGame), "Game is not launched");
    }

    /*Web content display - Player play button*/
    @Test(groups = {"webcontent","regression"})
    public void displayButtonLaunchGamePlayer() {
        WebContentPage webContentPage = (WebContentPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.webContentGame, defaultUserData.getRegisteredUserData());
        webContentPage.playAndValidateUrl(GameLaunch.button, 1);
    }

    /*Web content display - Admin play button*/
    @Test(groups = {"webcontent","regression"})
    public void displayButtonLaunchGameAdmin() {
        WebContentPage webContentPage = (WebContentPage) NavigationUtils.navigateToPage(PlayerCondition.admin, ConfiguredPages.webContentGame);
        AdminCanNotPlayPopup adminCanNotPlayPopup = webContentPage.clickAdmin(GameLaunch.button, 1);
    }

    /*Web content display - Guest login popup login image*/
    @Test(groups = {"webcontent","regression"})
    public void displayImageLaunchGameGuestLogin() {
        WebContentPage webContentPage = (WebContentPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.webContentGame);
        webContentPage.playAndValidateUrl(GameLaunch.image, 1, defaultUserData.getRegisteredUserData());
    }

    /*Web content display - Guest login popup register*/
    @Test(groups = {"webcontent","regression"})
    public void displayImageLaunchGameGuestRegister() {
        WebContentPage webContentPage = (WebContentPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.webContentGame);
        LoginPopup loginPopup = webContentPage.clickLoggedOut(GameLaunch.button, 1);
        loginPopup.clickRegistration().registerUser(defaultUserData.getRandomUserData());
    }

    /*Web content display - Guest login popup cancel image*/
    @Test(groups = {"webcontent","regression"})
    public void displayImageLaunchGameGuestCancel() {
        WebContentPage webContentPage = (WebContentPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.webContentGame);
        LoginPopup loginPopup = webContentPage.clickLoggedOut(GameLaunch.image, 1);
        loginPopup.close();
        validateFalse(WebDriverUtils.isGameLaunched(ConfiguredPages.webContentGame), "Game is not launched");
    }

    /*Web content display - Player play image*/
    @Test(groups = {"webcontent","regression"})
    public void displayImageLaunchGamePlayer() {
        WebContentPage webContentPage = (WebContentPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.webContentGame, defaultUserData.getRegisteredUserData());
        webContentPage.playAndValidateUrl(GameLaunch.image, 1);
    }

    /*Web content display - Admin play image*/
    @Test(groups = {"webcontent","regression"})
    public void displayImageLaunchGameAdmin() {
        WebContentPage webContentPage = (WebContentPage) NavigationUtils.navigateToPage(PlayerCondition.admin, ConfiguredPages.webContentGame);
        AdminCanNotPlayPopup adminCanNotPlayPopup = webContentPage.clickAdmin(GameLaunch.image, 1);
    }

    /*Banner - Guest login popup login button slide 1*/
    @Test(groups = {"webcontent","regression","banner"})
    public void bannerButtonLaunchGameGuestLogin() {
        WebContentPage webContentPage = (WebContentPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.bannerWebContentGame);
        webContentPage.playAndValidateUrl(GameLaunch.button, 1, defaultUserData.getRegisteredUserData());
    }

    /*Banner - Guest login popup login button slide 2*/
    @Test(groups = {"webcontent","regression","banner"})
    public void bannerButtonLaunchGameGuestLoginSlide2() {
        WebContentPage webContentPage = (WebContentPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.bannerWebContentGame);
        webContentPage.clickNextSlide();
        webContentPage.playAndValidateUrl(GameLaunch.button, 2, defaultUserData.getRegisteredUserData());
    }

    /*Banner - Guest login popup cancel button slide 1*/
    @Test(groups = {"webcontent","regression","banner"})
    public void bannerButtonLaunchGameGuestCancel() {
        WebContentPage webContentPage = (WebContentPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.bannerWebContentGame);
        LoginPopup loginPopup = webContentPage.clickLoggedOut(GameLaunch.button, 1);
        loginPopup.close();
        validateFalse(WebDriverUtils.isGameLaunched(ConfiguredPages.bannerWebContentGame), "Game is not launched");
    }

    /*Banner - Guest login popup cancel button slide 2*/
    @Test(groups = {"webcontent","regression","banner"})
    public void bannerButtonLaunchGameGuestCancelSlide2() {
        WebContentPage webContentPage = (WebContentPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.bannerWebContentGame);
        webContentPage.clickNextSlide();
        LoginPopup loginPopup = webContentPage.clickLoggedOut(GameLaunch.button, 2);
        loginPopup.close();
        validateFalse(WebDriverUtils.isGameLaunched(ConfiguredPages.bannerWebContentGame), "Game is not launched");
    }

    /*Banner - Guest login popup register*/
    @Test(groups = {"webcontent","regression"})
    public void bannerImageLaunchGameGuestRegister() {
        WebContentPage webContentPage = (WebContentPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.bannerWebContentGame);
        LoginPopup loginPopup = webContentPage.clickLoggedOut(GameLaunch.button, 1);
        loginPopup.clickRegistration().registerUser(defaultUserData.getRandomUserData());
    }

    /*Banner - Player play button slide 1*/
    @Test(groups = {"webcontent","regression","banner"})
    public void bannerButtonLaunchGamePlayer() {
        UserData userData = defaultUserData.getRegisteredUserData();
        WebContentPage webContentPage = (WebContentPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.bannerWebContentGame, userData);
        webContentPage.playAndValidateUrl(GameLaunch.button, 1);
    }

    /*Banner - Player play button slide 2*/
    @Test(groups = {"webcontent","regression","banner"})
    public void bannerButtonLaunchGamePlayerSlide2() {
        UserData userData = defaultUserData.getRegisteredUserData();
        WebContentPage webContentPage = (WebContentPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.bannerWebContentGame, userData);
        webContentPage.clickNextSlide();
        webContentPage.playAndValidateUrl(GameLaunch.button, 2);
    }

    /*Banner - Admin play button slide 1*/
    @Test(groups = {"webcontent","regression"})
    public void bannerButtonLaunchGameAdmin() {
        WebContentPage webContentPage = (WebContentPage) NavigationUtils.navigateToPage(PlayerCondition.admin, ConfiguredPages.bannerWebContentGame);
        AdminCanNotPlayPopup adminCanNotPlayPopup = webContentPage.clickAdmin(GameLaunch.button, 1);
    }

    /*Banner - Admin play button slide 2*/
    @Test(groups = {"webcontent","regression"})
    public void bannerButtonLaunchGameAdminSlide2() {
        WebContentPage webContentPage = (WebContentPage) NavigationUtils.navigateToPage(PlayerCondition.admin, ConfiguredPages.bannerWebContentGame);
        webContentPage.clickNextSlide();
        AdminCanNotPlayPopup adminCanNotPlayPopup = webContentPage.clickAdmin(GameLaunch.button, 2);
    }

    /*Banner - Guest login popup login image slide 1*/
    @Test(groups = {"webcontent","regression","banner"})
    public void bannerImageLaunchGameGuestLogin() {
        WebContentPage webContentPage = (WebContentPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.bannerWebContentGame);
        webContentPage.playAndValidateUrl(GameLaunch.image, 2, defaultUserData.getRegisteredUserData());
    }

    /*Banner - Guest login popup login image slide 2*/
    @Test(groups = {"webcontent","regression","banner"})
    public void bannerImageLaunchGameGuestLoginSlide2() {
        WebContentPage webContentPage = (WebContentPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.bannerWebContentGame);
        webContentPage.clickNextSlide();
        webContentPage.playAndValidateUrl(GameLaunch.image, 2, defaultUserData.getRegisteredUserData());
    }

    /*Banner - Guest login popup cancel image slide 1*/
    @Test(groups = {"webcontent","regression","banner"})
    public void bannerImageLaunchGameGuestCancel() {
        WebContentPage webContentPage = (WebContentPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.bannerWebContentGame);
        LoginPopup loginPopup = webContentPage.clickLoggedOut(GameLaunch.image, 1);
        loginPopup.close();
        validateFalse(WebDriverUtils.isGameLaunched(ConfiguredPages.webContentGame), "Game is not launched");
    }

    /*Banner - Guest login popup cancel image slide 2*/
    @Test(groups = {"webcontent","regression","banner"})
    public void bannerImageLaunchGameGuestCancelSlide2() {
        WebContentPage webContentPage = (WebContentPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.bannerWebContentGame);
        webContentPage.clickNextSlide();
        LoginPopup loginPopup = webContentPage.clickLoggedOut(GameLaunch.image, 2);
        loginPopup.close();
        validateFalse(WebDriverUtils.isGameLaunched(ConfiguredPages.webContentGame), "Game is not launched");
    }

    /*Banner - Player play image slide 1*/
    @Test(groups = {"webcontent","regression","banner"})
    public void bannerImageLaunchGamePlayer() {
        WebContentPage webContentPage = (WebContentPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.bannerWebContentGame, defaultUserData.getRegisteredUserData());
        webContentPage.playAndValidateUrl(GameLaunch.image, 1);
    }

    /*Banner - Player play image slide 2*/
    @Test(groups = {"webcontent","regression","banner"})
    public void bannerImageLaunchGamePlayerSlide2() {
        WebContentPage webContentPage = (WebContentPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.bannerWebContentGame, defaultUserData.getRegisteredUserData());
        webContentPage.clickNextSlide();
        webContentPage.playAndValidateUrl(GameLaunch.image, 2);
    }

    /*Banner - Admin play image slide 1*/
    @Test(groups = {"webcontent","regression"})
    public void bannerImageLaunchGameAdmin() {
        WebContentPage webContentPage = (WebContentPage) NavigationUtils.navigateToPage(PlayerCondition.admin, ConfiguredPages.bannerWebContentGame);
        AdminCanNotPlayPopup adminCanNotPlayPopup = webContentPage.clickAdmin(GameLaunch.image, 1);
    }

    /*Banner - Admin play image slide 2*/
    @Test(groups = {"webcontent","regression"})
    public void bannerImageLaunchGameAdminSlide2() {
        WebContentPage webContentPage = (WebContentPage) NavigationUtils.navigateToPage(PlayerCondition.admin, ConfiguredPages.bannerWebContentGame);
        webContentPage.clickNextSlide();
        AdminCanNotPlayPopup adminCanNotPlayPopup = webContentPage.clickAdmin(GameLaunch.image, 2);
    }

}
