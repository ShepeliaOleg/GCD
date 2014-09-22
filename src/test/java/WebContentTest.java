import enums.ConfiguredPages;
import enums.GameLaunch;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.login.LoginPopup;
import pageObjects.webcontent.WebcontentPage;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.TypeUtils;
import utils.core.AbstractTest;

public class WebContentTest extends AbstractTest{

    @Autowired
    @Qualifier("userData")
    private UserData defaultUserData;

    /*Web content display - Guest login popup login*/
    @Test(groups = {"webcontent","regression"})
    public void displayButtonLaunchGameGuestLogin() {
        WebcontentPage webcontentPage = (WebcontentPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.webContentGame);
        webcontentPage.playAndValidateUrl(GameLaunch.button, defaultUserData.getRegisteredUserData());
    }

    /*Web content display - Guest login popup cancel*/
    @Test(groups = {"webcontent","regression"})
    public void displayButtonLaunchGameGuestCancel() {
        WebcontentPage webcontentPage = (WebcontentPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.webContentGame);
        LoginPopup loginPopup = webcontentPage.clickLoggedOut(GameLaunch.button);
        loginPopup.close();
        TypeUtils.assertTrueWithLogs(webcontentPage.validateGameNotLaunched(ConfiguredPages.webContentGame), "Game is not launched");
    }

    /*Web content display - Player play*/
    @Test(groups = {"webcontent","regression"})
    public void displayButtonLaunchGamePlayer() {
        WebcontentPage webcontentPage = (WebcontentPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.webContentGame, defaultUserData.getRegisteredUserData());
        webcontentPage.playAndValidateUrl(GameLaunch.button);
    }

    /*Web content display - Guest login popup login*/
    @Test(groups = {"webcontent","regression"})
    public void displayImageLaunchGameGuestLogin() {
        WebcontentPage webcontentPage = (WebcontentPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.webContentGame);
        webcontentPage.playAndValidateUrl(GameLaunch.image, defaultUserData.getRegisteredUserData());
    }

    /*Web content display - Guest login popup cancel*/
    @Test(groups = {"webcontent","regression"})
    public void displayImageLaunchGameGuestCancel() {
        WebcontentPage webcontentPage = (WebcontentPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.webContentGame);
        LoginPopup loginPopup = webcontentPage.clickLoggedOut(GameLaunch.image);
        loginPopup.close();
        TypeUtils.assertTrueWithLogs(webcontentPage.validateGameNotLaunched(ConfiguredPages.webContentGame), "Game is not launched");
    }

    /*Web content display - Player play*/
    @Test(groups = {"webcontent","regression"})
    public void displayImageLaunchGamePlayer() {
        WebcontentPage webcontentPage = (WebcontentPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.webContentGame, defaultUserData.getRegisteredUserData());
        webcontentPage.playAndValidateUrl(GameLaunch.image);

    }

    /*Banner - Guest login popup login*/
    @Test(groups = {"webcontent","regression","banner"})
    public void bannerButtonLaunchGameGuestLogin() {
        WebcontentPage webcontentPage = (WebcontentPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.bannerWebContentGame);
        webcontentPage.playAndValidateUrl(GameLaunch.button, defaultUserData.getRegisteredUserData());
    }

    /*Banner - Guest login popup cancel*/
    @Test(groups = {"webcontent","regression","banner"})
    public void bannerButtonLaunchGameGuestCancel() {
        WebcontentPage webcontentPage = (WebcontentPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.bannerWebContentGame);
        LoginPopup loginPopup = webcontentPage.clickLoggedOut(GameLaunch.button);
        loginPopup.close();
        TypeUtils.assertTrueWithLogs(webcontentPage.validateGameNotLaunched(ConfiguredPages.webContentGame), "Game is not launched");
    }

    /*Banner - Player play*/
    @Test(groups = {"webcontent","regression","banner"})
    public void bannerButtonLaunchGamePlayer() {
        UserData userData = defaultUserData.getRegisteredUserData();
        WebcontentPage webcontentPage = (WebcontentPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.bannerWebContentGame, userData);
        webcontentPage.playAndValidateUrl(GameLaunch.button);
    }

    /*Banner - Guest login popup login*/
    @Test(groups = {"webcontent","regression","banner"})
    public void bannerImageLaunchGameGuestLogin() {
        WebcontentPage webcontentPage = (WebcontentPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.bannerWebContentGame);
        webcontentPage.playAndValidateUrl(GameLaunch.image, defaultUserData.getRegisteredUserData());
    }

    /*Banner - Guest login popup cancel*/
    @Test(groups = {"webcontent","regression","banner"})
    public void bannerImageLaunchGameGuestCancel() {
        WebcontentPage webcontentPage = (WebcontentPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.bannerWebContentGame);
        LoginPopup loginPopup = webcontentPage.clickLoggedOut(GameLaunch.image);
        loginPopup.close();
        TypeUtils.assertTrueWithLogs(webcontentPage.validateGameNotLaunched(ConfiguredPages.webContentGame), "Game is not launched");
    }

    /*Banner - Player play*/
    @Test(groups = {"webcontent","regression","banner"})
    public void bannerImageLaunchGamePlayer() {
        WebcontentPage webcontentPage = (WebcontentPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.bannerWebContentGame, defaultUserData.getRegisteredUserData());
        webcontentPage.playAndValidateUrl(GameLaunch.image);
    }

}
