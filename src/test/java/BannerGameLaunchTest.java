import enums.BannerNavigationType;
import enums.BannerSlideType;
import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.banner.BannerPage;
import pageObjects.external.ExternalPage;
import pageObjects.gamesPortlet.GameLaunchPopup;
import pageObjects.login.LoginPopup;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.TypeUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

import java.util.List;

public class BannerGameLaunchTest extends AbstractTest{

    @Autowired
    @Qualifier("userData")
    private UserData defaultUserData;

	/* 14. Game launch from banner as guest user*/
    @Test(groups = {"regression"})
    public void bannerLaunchGameGuestPlayer() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.bannerGame);
        assertTrue(bannerPage.slideIsDisplayed(1), "First slide is displayed");
        LoginPopup loginPopup = (LoginPopup) bannerPage.clickSlide(1);
        loginPopup.login(defaultUserData.getRegisteredUserData());
        GameLaunchPopup gameLaunchPopup = new GameLaunchPopup(bannerPage.getMainWindowHandle());
        assertTrue(gameLaunchPopup.checkUrlAndClose(), "Game url is valid");
    }

    /* 15. Game launch from banner as logged in player*/
    @Test(groups = {"regression"})
    public void bannerLaunchGameLoggedInPlayer() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.bannerGame, defaultUserData.getRegisteredUserData());
        assertTrue(bannerPage.slideIsDisplayed(1), "First slide is displayed");
        GameLaunchPopup gameLaunchPopup = (GameLaunchPopup) bannerPage.clickSlide(1);
        assertTrue(gameLaunchPopup.checkUrlAndClose(), "Game url is valid");
    }
}
