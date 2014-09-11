import enums.BannerNavigationType;
import enums.BannerSlideType;
import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.account.LoginPopup;
import pageObjects.banner.BannerPage;
import pageObjects.external.ExternalPage;
import pageObjects.gamesPortlet.GameLaunchPopup;
import springConstructors.UserData;
import testUtils.AbstractTest;
import utils.NavigationUtils;
import utils.TypeUtils;
import utils.WebDriverUtils;
import java.util.List;

public class BannerTest extends AbstractTest{

    @Autowired
    @Qualifier("userData")
    private UserData defaultUserData;

	/*POSITIVE*/

	/* 1. Portlet is available */
	@Test(groups = {"smoke"})
	public void portletIsDisplayed() {
		BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.bannerImage);
	}

    /* 2. Banner shows image */
    @Test(groups = {"regression"})
    public void bannerImage() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.bannerImage);
        List<BannerSlideType> slidesTypes = bannerPage.getSlidesTypes();

        TypeUtils.assertTrueWithLogs(slidesTypes.size() == 1, "Expected number of slides is 1.");
        TypeUtils.assertTrueWithLogs(slidesTypes.get(0).equals(BannerSlideType.image), "There is no slide with image content present.");
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(1), "Slide with Image is not displayed.");
    }

    /* 3. Banner shows HTML */
    @Test(groups = {"regression"})
    public void bannerHtml() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.bannerHtml);
        List<BannerSlideType> slidesTypes = bannerPage.getSlidesTypes();

        TypeUtils.assertTrueWithLogs(slidesTypes.size() == 1, "Expected number of slides is 1.");
        TypeUtils.assertTrueWithLogs(slidesTypes.get(0).equals(BannerSlideType.html), "There is no slide with HTML content present.");
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(1), "Slide with HTML is not displayed.");
    }

    /* 4. Banner shows WebContent */
    @Test(groups = {"regression"})
    public void bannerWebContent() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.bannerWebContent);
        List<BannerSlideType> slidesTypes = bannerPage.getSlidesTypes();

        TypeUtils.assertTrueWithLogs(slidesTypes.size() == 1, "Expected number of slides is 1.");
        TypeUtils.assertTrueWithLogs(slidesTypes.get(0).equals(BannerSlideType.webContent), "There is no slide with WebContent content present.");
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(1), "Slide with WebContent is not displayed.");
    }

    /* 5. Banner shows mixed content */
    @Test(groups = {"regression"})
    public void bannerMixed() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.bannerMixed);
        List<BannerSlideType> slidesTypes = bannerPage.getSlidesTypes();

        TypeUtils.assertTrueWithLogs(slidesTypes.size() == 3, "Expected number of slides is 3.");
        TypeUtils.assertTrueWithLogs(slidesTypes.get(0).equals(BannerSlideType.image),      "First slide is not image content type.");
        TypeUtils.assertTrueWithLogs(slidesTypes.get(1).equals(BannerSlideType.html),       "Second slide is not HTML content type.");
        TypeUtils.assertTrueWithLogs(slidesTypes.get(2).equals(BannerSlideType.webContent), "Third slide is not WebContent content type.");
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(1, 10), "First slide with image was not displayed.");
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(2, 10), "Second slide with HTML was not displayed");
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(3, 10), "Third slide with WebContent was not displayed.");
    }

    /* 6. Navigation – buttons*/
    @Test(groups = {"regression"})
    public void bannerNavigationButtons() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.bannerNavigationButtons);
        TypeUtils.assertEqualsWithLogs(bannerPage.getNavigationType(), BannerNavigationType.buttons, "Expected navigation type is 'Buttons'.");
        bannerPage.showSlide(1);
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(1), "First slide was not displayed after clicking first button.");
        bannerPage.showSlide(2);
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(2), "Second slide was not displayed after clicking second button.");
        bannerPage.showSlide(3);
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(3), "Third slide was not displayed after clicking third button.");
    }

    /* 7. Navigation – arrows*/
    @Test(groups = {"regression"})
    public void bannerNavigationArrows() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.bannerNavigationArrows);
        TypeUtils.assertEqualsWithLogs(bannerPage.getNavigationType(), BannerNavigationType.arrows, "Expected navigation type is 'Arrows'.");
        bannerPage.showSlide(1);
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(1), "First slide was not displayed after clicking next arrow.");
        TypeUtils.assertTrueWithLogs(bannerPage.arrowsDisplayed(),   "Only Next arrow should be displayed on first slide.");
        bannerPage.showSlide(2);
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(2), "Second slide was not displayed after clicking next arrow.");
        TypeUtils.assertTrueWithLogs(bannerPage.arrowsDisplayed(),   "Both Next and Previous arrows should be displayed on medium slide.");
        bannerPage.showSlide(3);
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(3), "Third slide was not displayed after clicking next arrow.");
        TypeUtils.assertTrueWithLogs(bannerPage.arrowsDisplayed(),   "Only Previous arrow should be displayed on last slide.");
        bannerPage.showSlide(2);
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(2), "Second slide was not displayed after clicking previous arrow.");
        TypeUtils.assertTrueWithLogs(bannerPage.arrowsDisplayed(),   "Both Next and Previous arrows should be displayed on medium slide.");
        bannerPage.showSlide(1);
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(1), "First slide was not displayed after clicking previous arrow.");
        TypeUtils.assertTrueWithLogs(bannerPage.arrowsDisplayed(),   "Only Next arrow should be displayed on first slide.");

    }

    /* 8. Navigation - arrows and bullets*/
    @Test(groups = {"regression"})
    public void bannerNavigationArrowsBullets() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.bannerNavigationArrowsBullets);
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(1), "First slide is not displayed.");
        TypeUtils.assertEqualsWithLogs(bannerPage.getNavigationType(), BannerNavigationType.arrowsAndBullets, "Expected navigation type is 'Arrows and Bullets'.");
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(1), "First slide was not displayed after clicking next arrow.");
        TypeUtils.assertTrueWithLogs(bannerPage.arrowsDisplayed(),   "Only Next arrow should be displayed on first slide.");
        bannerPage.showSlide(2);
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(2), "Second slide was not displayed after clicking next arrow.");
        TypeUtils.assertTrueWithLogs(bannerPage.arrowsDisplayed(), "Both Next and Previous arrows should be displayed on medium slide.");
        bannerPage.showSlide(3);
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(3), "Third slide was not displayed after clicking next arrow.");
        TypeUtils.assertTrueWithLogs(bannerPage.arrowsDisplayed(),   "Only Previous arrow should be displayed on last slide.");
        bannerPage.showSlide(2);
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(2), "Second slide was not displayed after clicking previous arrow.");
        TypeUtils.assertTrueWithLogs(bannerPage.arrowsDisplayed(), "Both Next and Previous arrows should be displayed on medium slide.");
        bannerPage.showSlide(1);
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(1), "First slide was not displayed after clicking previous arrow.");
        TypeUtils.assertTrueWithLogs(bannerPage.arrowsDisplayed(),   "Only Next arrow should be displayed on first slide.");
        bannerPage.showSlide(3, BannerNavigationType.bullets);
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(3), "Third slide was not displayed after clicking third bullet.");
        TypeUtils.assertTrueWithLogs(bannerPage.arrowsDisplayed(),   "Only Next arrow should be displayed on first slide.");
        bannerPage.showSlide(2, BannerNavigationType.bullets);
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(2), "Second slide was not displayed after clicking second bullet.");
        TypeUtils.assertTrueWithLogs(bannerPage.arrowsDisplayed(),   "Both Next and Previous arrows should be displayed on medium slide.");
        bannerPage.showSlide(1, BannerNavigationType.bullets);
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(1), "First slide was not displayed after clicking first bullet.");
        TypeUtils.assertTrueWithLogs(bannerPage.arrowsDisplayed(),   "Only Previous arrow should be displayed on last slide.");

    }

    /* 9. Navigation – bullets*/
    @Test(groups = {"regression"})
    public void bannerNavigationBullets() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.bannerNavigationBullets);
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(1), "First slide is not displayed.");
        TypeUtils.assertEqualsWithLogs(bannerPage.getNavigationType(), BannerNavigationType.bullets, "Expected navigation type is 'Bullets'.");
        bannerPage.showSlide(3);
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(3), "Third slide was not displayed after clicking third bullet.");
        bannerPage.showSlide(1);
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(1), "First slide was not displayed after clicking first bullet.");
        bannerPage.showSlide(2);
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(2), "Second slide was not displayed after clicking second bullet.");
    }

    /* 10. Navigation – none*/
    @Test(groups = {"regression"})
    public void bannerNavigationNone() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.bannerImage);
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(1), "First slide is not displayed.");
        TypeUtils.assertEqualsWithLogs(bannerPage.getNavigationType(), BannerNavigationType.none, "Expected navigation type is 'None'.");
    }

    /* 11. Banner as link*/
    @Test(groups = {"regression"})
    public void bannerLink() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.bannerLink);
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(1), "First slide is not displayed.");
        bannerPage.clickSlide(1);
        ExternalPage externalPage = new ExternalPage(bannerPage.getMainWindowHandle());
        String currentUrl = WebDriverUtils.getCurrentUrl();
        externalPage.close();
        TypeUtils.assertEqualsWithLogs(currentUrl, "https://www.google.com.ua/", "Banner should link to 'https://www.google.com.ua/', but '" + currentUrl + "' is opened.");

    }

    /* 12. Include banner in rotation*/
    @Test(groups = {"regression"})
    public void bannerInRotation() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.bannerInRotation);

        List<BannerSlideType> slidesTypes = bannerPage.getSlidesTypes();

        TypeUtils.assertTrueWithLogs(slidesTypes.size() == 2, "Expected number of slides is 2.");
        TypeUtils.assertTrueWithLogs(slidesTypes.get(0).equals(BannerSlideType.image),      "First slide is not image content type.");
        TypeUtils.assertTrueWithLogs(slidesTypes.get(1).equals(BannerSlideType.webContent), "Second slide is not WebContent content type.");
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(1), "First slide is not displayed.");
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(2), "Second slide is not displayed.");

    }
    /* 13. Time to display*/
    @Test(groups = {"regression"})
    public void banner5seconds() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.banner5seconds);
        int slidesNumber = bannerPage.getSlidesCount();

        TypeUtils.assertTrueWithLogs(slidesNumber == 2, "Expected number of slides is 2.");

        long appearingOfSecondSlide = bannerPage.whenSlideDisplayed(2);
        long appearingOfFirstSlide =  bannerPage.whenSlideDisplayed(1);
        long timeToDisplay = appearingOfFirstSlide - appearingOfSecondSlide;

        TypeUtils.assertTrueWithLogs(4900 < timeToDisplay && timeToDisplay < 5100, "Slides should switch each 5 seconds, but it took " + timeToDisplay + " ms.");
    }

    /* 14. Game launch from banner as guest user*/
    @Test(groups = {"regression"})
    public void bannerLaunchGameGuestPlayer() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.bannerGame);
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(1), "First slide is not displayed.");
        LoginPopup loginPopup = (LoginPopup) bannerPage.clickSlide(1);
    }

    /* 15. Game launch from banner as logged in player*/
    @Test(groups = {"regression"})
    public void bannerLaunchGameLoggedInPlayer() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.bannerGame, defaultUserData.getRegisteredUserData());
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(1), "First slide is not displayed.");
        GameLaunchPopup gameLaunchPopup = (GameLaunchPopup) bannerPage.clickSlide(1);
        boolean correctGamePopupUrl = gameLaunchPopup.isUrlValid();
        gameLaunchPopup.close();
        TypeUtils.assertTrueWithLogs(correctGamePopupUrl, "Game url is valid");

    }


}
