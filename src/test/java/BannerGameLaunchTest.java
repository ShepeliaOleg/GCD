import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.testng.annotations.Test;
import pageObjects.admin.AdminCanNotPlayPopup;
import pageObjects.banner.BannerPage;
import pageObjects.login.LoginPopup;
import utils.NavigationUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

public class BannerGameLaunchTest extends AbstractTest{

    /*Banner - Guest login popup register*/
    @Test(groups = {"regression", "banner"})
    public void bannerImageLaunchGameGuestRegister() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.bannerGameTwoSlides);
        LoginPopup loginPopup = bannerPage.clickGameLoggedOut( 3);
        loginPopup.clickRegistration().registerUser(DataContainer.getUserData().getRandomUserData());
    }

    /*Banner - Guest login popup login image slide 1*/
    @Test(groups = {"regression","banner"})
    public void bannerImageLaunchGameGuestLogin() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.bannerGameTwoSlides);
        bannerPage.clickGameAndAssertUrl(1, DataContainer.getUserData().getRegisteredUserData());
    }

    /*Banner - Guest login popup login image slide 2*/
    @Test(groups = {"regression","banner"})
    public void bannerImageLaunchGameGuestLoginSlide2() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.bannerGameTwoSlides);
        bannerPage.showNextSlide();
        bannerPage.clickGameAndAssertUrl(2, DataContainer.getUserData().getRegisteredUserData());
    }

    /*Banner - Guest login popup cancel image slide 1*/
    @Test(groups = {"regression","banner"})
    public void bannerImageLaunchGameGuestCancel() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.bannerGameTwoSlides);
        LoginPopup loginPopup = bannerPage.clickGameLoggedOut(1);
        loginPopup.closePopup();
        validateFalse(WebDriverUtils.isGameLaunched(ConfiguredPages.bannerGameTwoSlides), "Game is not launched");
    }

    /*Banner - Guest login popup cancel image slide 2*/
    @Test(groups = {"regression","banner"})
    public void bannerImageLaunchGameGuestCancelSlide2() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.bannerGameTwoSlides);
        bannerPage.showNextSlide();
        LoginPopup loginPopup = bannerPage.clickGameLoggedOut(2);
        loginPopup.closePopup();
        validateFalse(WebDriverUtils.isGameLaunched(ConfiguredPages.bannerGameTwoSlides), "Game is not launched");
    }

    /*Banner - Player play image slide 1*/
    @Test(groups = {"regression","banner"})
    public void bannerImageLaunchGamePlayer() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.bannerGameTwoSlides);
        bannerPage.clickGameAndAssertUrl(1);
    }

    /*Banner - Player play image slide 2*/
    @Test(groups = {"regression","banner"})
    public void bannerImageLaunchGamePlayerSlide2() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.bannerGameTwoSlides);
        bannerPage.showNextSlide();
        bannerPage.clickGameAndAssertUrl(2);
    }

    /*Banner - Admin play image slide 1*/
    @Test(groups = {"admin"})
    public void bannerImageLaunchGameAdmin() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(PlayerCondition.admin, ConfiguredPages.bannerGameTwoSlides);
        AdminCanNotPlayPopup adminCanNotPlayPopup = bannerPage.clickGameAdmin(1);
    }

    /*Banner - Admin play image slide 2*/
    @Test(groups = {"admin"})
    public void bannerImageLaunchGameAdminSlide2() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(PlayerCondition.admin, ConfiguredPages.bannerGameTwoSlides);
        bannerPage.showNextSlide();
        AdminCanNotPlayPopup adminCanNotPlayPopup = bannerPage.clickGameAdmin(2);
    }
}