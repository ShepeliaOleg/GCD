import enums.BannerNavigationType;
import enums.BannerSlideType;
import enums.ConfiguredPages;
import org.testng.annotations.Test;
import pageObjects.banner.BannerPage;
import testUtils.AbstractTest;
import utils.NavigationUtils;
import utils.TypeUtils;
import java.util.List;

public class BannerTest extends AbstractTest{

	/*POSITIVE*/

	/* 1. Portlet is available */
	@Test(groups = {"smoke"})
	public void portletIsDisplayed() {
		BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.bannerImage);
	}

    /* 2. Banner shows WebContent */
    @Test(groups = {"regression"})
    public void bannerWebContent() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.bannerWebContent);
        List<BannerSlideType> slidesTypes = bannerPage.getSlidesTypes();

        TypeUtils.assertTrueWithLogs(slidesTypes.size() == 1, "Expected number of slides is 1.");
        TypeUtils.assertTrueWithLogs(slidesTypes.contains(BannerSlideType.webContent), "There is no slide with WebContent content present.");
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(1), "WebContent is not visible.");
    }

    /* 3. Banner shows image */
	@Test(groups = {"regression"})
	public void bannerImage() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.bannerImage);
        List<BannerSlideType> slidesTypes = bannerPage.getSlidesTypes();

        TypeUtils.assertTrueWithLogs(slidesTypes.size() == 1, "Expected number of slides is 1.");
        TypeUtils.assertTrueWithLogs(slidesTypes.contains(BannerSlideType.image), "There is no slide with image content present.");
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(1), "Image is not visible.");
	}
    /* 4. Banner shows HTML */
    @Test(groups = {"regression"})
    public void bannerHtml() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.bannerHtml);
        List<BannerSlideType> slidesTypes = bannerPage.getSlidesTypes();

        TypeUtils.assertTrueWithLogs(slidesTypes.size() == 1, "Expected number of slides is 1.");
        TypeUtils.assertTrueWithLogs(slidesTypes.contains(BannerSlideType.html), "There is no slide with HTML content present.");
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(1), "HTML is not visible.");
    }

    /* 5. Banner shows mixed content */
    @Test(groups = {"regression"})
    public void bannerMixed() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.bannerMixed);
        List<BannerSlideType> slidesTypes = bannerPage.getSlidesTypes();

        TypeUtils.assertTrueWithLogs(slidesTypes.size() == 3, "Expected number of slides is 3.");
        TypeUtils.assertTrueWithLogs(slidesTypes.contains(BannerSlideType.image), "There is no slide with image content present.");
        TypeUtils.assertTrueWithLogs(slidesTypes.contains(BannerSlideType.webContent), "There is no slide with WebContent content present.");
        TypeUtils.assertTrueWithLogs(slidesTypes.contains(BannerSlideType.html), "There is no slide with HTML content present.");
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(1), "Slide with image was not displayed.");
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(2), "Slide with WebContent was not displayed.");
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(3), "Slide with HTML was not displayed");
    }

    /* 8. Navigation – buttons*/
    public void bannerNavigationButtons() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.bannerNavigationButtons);
    }

    /* 9. Navigation – arrows*/
    public void bannerNavigationArrows() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.bannerNavigationArrows);
        bannerPage.showNextSlide(BannerNavigationType.arrows);
    }

    /* 10. Navigation - arrows and bullets*/
    public void bannerNavigationArrowsBullets() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.bannerNavigationArrowsBullets);
    }

    /* 11. Navigation – bullets*/
    public void bannerNavigationBullets() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.bannerNavigationBullets);
    }

    /* 12. Navigation – none*/
    public void bannerNavigationNone() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.bannerImage);
        BannerNavigationType bannerNavigationType = bannerPage.getNavigationType();
        TypeUtils.assertEqualsWithLogs(bannerNavigationType, BannerNavigationType.none, "Banner configured to have no navigation, but some navigation items are displayed.");
    }

    /* 14. Banner as link*/

    /* 15. Include banner in rotation*/
    @Test(groups = {"regression"})
    public void bannerInRotation() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.bannerInRotation);
        int slidesNumber = bannerPage.getSlidesCount();

        TypeUtils.assertTrueWithLogs(slidesNumber == 3, "Expected number of slides is 3.");
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(1), "First slide is not displayed, in spite of it included in rotation.");
        TypeUtils.assertTrueWithLogs(!bannerPage.slideIsDisplayed(2), "Second slide is displayed, in spite of it not included in rotation.");
        TypeUtils.assertTrueWithLogs(bannerPage.slideIsDisplayed(3), "Third slide is not displayed, in spite of it included in rotation.");
    }

    /* 17. Number of banners*/

    /* 18. Time to display*/
    @Test(groups = {"regression"})
    public void banner5seconds() {
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.banner5seconds);
        int slidesNumber = bannerPage.getSlidesCount();

        TypeUtils.assertTrueWithLogs(slidesNumber == 2, "Expected number of slides is 2.");

        long appearingOfSecondSlide = bannerPage.whenSlideDisplayed(2);
        long appearingOfFirstSlide =  bannerPage.whenSlideDisplayed(1);
        long timeToDisplay = appearingOfFirstSlide - appearingOfSecondSlide;

        TypeUtils.assertTrueWithLogs(4999 < timeToDisplay && timeToDisplay < 5001, "Slides should switch each 5 seconds, but it took " + timeToDisplay + " ms.");
    }

    /* 19. Game launch from banner*/

}
