import enums.BannerNavigationType;
import enums.BannerSlideType;
import enums.ConfiguredPages;
import org.testng.annotations.Test;
import pageObjects.banner.BannerPage;
import pageObjects.external.ExternalPage;
import utils.NavigationUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

import java.util.List;

public class BannerTest extends AbstractTest{

	/*POSITIVE*/

    /* 1. Portlet is available */
    @Test(groups = {"smoke"})
    public void portletIsDisplayed() {
        try{
            BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.bannerImage);
        }catch (Exception e){
            skipTest();
        }
    }

    /* 2. Banner shows image */
    @Test(groups = {"regression"})
    public void bannerImage() {
        skipTestWithIssues("D-19983");
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.bannerImage);
        List<BannerSlideType> slidesTypes = bannerPage.getSlidesTypes();
        validateEquals(1, slidesTypes.size(), "Number of slides");
        assertEquals(BannerSlideType.image, slidesTypes.get(0), "Slide type");
        assertTrue(bannerPage.slideIsDisplayed(1), "Slide with Image is not displayed.");
    }

    /* 3. Banner shows HTML */
    @Test(groups = {"regression"})
    public void bannerHtml() {
        skipTestWithIssues("D-19983");
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.bannerHtml);
        List<BannerSlideType> slidesTypes = bannerPage.getSlidesTypes();
        validateEquals(1, slidesTypes.size(), "Number of slides");
        assertEquals(BannerSlideType.html, slidesTypes.get(0), "Slide type");
        assertTrue(bannerPage.slideIsDisplayed(1), "Slide with HTML is not displayed.");
    }

    /* 4. Banner shows WebContent */
    @Test(groups = {"regression"})
    public void bannerWebContent() {
        skipTestWithIssues("D-19983");
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.bannerWebContent);
        List<BannerSlideType> slidesTypes = bannerPage.getSlidesTypes();
        validateEquals(1, slidesTypes.size(), "Number of slides");
        assertEquals(BannerSlideType.webContent, slidesTypes.get(0), "Slide type");
        assertTrue(bannerPage.slideIsDisplayed(1), "Slide with WebContent is not displayed.");
    }

    /* 5. Banner shows mixed content */
    @Test(groups = {"regression"})
    public void bannerMixed() {
        skipTestWithIssues("D-19983");
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.bannerMixed);
        List<BannerSlideType> slidesTypes = bannerPage.getSlidesTypes();
        validateEquals(3, slidesTypes.size(), "Number of slides");
        assertEquals(BannerSlideType.image, slidesTypes.get(0),     "First slide");
        assertEquals(BannerSlideType.html, slidesTypes.get(1),      "Second slide");
        assertEquals(BannerSlideType.webContent, slidesTypes.get(2),"Third slide");
        assertTrue(bannerPage.slideIsDisplayed(1, 10), "First slide with image was not displayed.");
        assertTrue(bannerPage.slideIsDisplayed(2, 10), "Second slide with HTML was not displayed");
        assertTrue(bannerPage.slideIsDisplayed(3, 10), "Third slide with WebContent was not displayed.");
    }

    /* 6. Navigation – buttons*/
    @Test(groups = {"regression"})
    public void bannerNavigationButtons() {
        skipTestWithIssues("D-19983");
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.bannerNavigationButtons);
        assertEquals(BannerNavigationType.buttons, bannerPage.getNavigationType(), "Navigation type");
        bannerPage.showSlide(1);
        assertTrue(bannerPage.slideIsDisplayed(1), "First slide was not displayed after clicking first button.");
        bannerPage.showSlide(2);
        assertTrue(bannerPage.slideIsDisplayed(2), "Second slide was not displayed after clicking second button.");
        bannerPage.showSlide(3);
        assertTrue(bannerPage.slideIsDisplayed(3), "Third slide was not displayed after clicking third button.");
    }

    /* 7. Navigation – arrows*/
    @Test(groups = {"regression"})
    public void bannerNavigationArrows() {
        skipTestWithIssues("D-19983");
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.bannerNavigationArrows);
        assertEquals(BannerNavigationType.arrows, bannerPage.getNavigationType(), "Navigation type");
        bannerPage.showSlide(1);
        assertTrue(bannerPage.slideIsDisplayed(1), "First slide was not displayed after clicking next arrow.");
        assertTrue(bannerPage.arrowsDisplayed(),   "Only Next arrow should be displayed on first slide.");
        bannerPage.showSlide(2);
        assertTrue(bannerPage.slideIsDisplayed(2), "Second slide was not displayed after clicking next arrow.");
        assertTrue(bannerPage.arrowsDisplayed(),   "Both Next and Previous arrows should be displayed on medium slide.");
        bannerPage.showSlide(3);
        assertTrue(bannerPage.slideIsDisplayed(3), "Third slide was not displayed after clicking next arrow.");
        assertTrue(bannerPage.arrowsDisplayed(),   "Only Previous arrow should be displayed on last slide.");
        bannerPage.showSlide(2);
        assertTrue(bannerPage.slideIsDisplayed(2), "Second slide was not displayed after clicking previous arrow.");
        assertTrue(bannerPage.arrowsDisplayed(),   "Both Next and Previous arrows should be displayed on medium slide.");
        bannerPage.showSlide(1);
        assertTrue(bannerPage.slideIsDisplayed(1), "First slide was not displayed after clicking previous arrow.");
        assertTrue(bannerPage.arrowsDisplayed(),   "Only Next arrow should be displayed on first slide.");

    }

    /* 8. Navigation - arrows and bullets*/
    @Test(groups = {"regression"})
    public void bannerNavigationArrowsBullets() {
        skipTestWithIssues("D-19983");
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.bannerNavigationArrowsBullets);
        assertTrue(bannerPage.slideIsDisplayed(1), "First slide is not displayed.");
        assertEquals(BannerNavigationType.arrowsAndBullets, bannerPage.getNavigationType(), "Navigation type");
        assertTrue(bannerPage.slideIsDisplayed(1), "First slide was not displayed after clicking next arrow.");
        assertTrue(bannerPage.arrowsDisplayed(),   "Only Next arrow should be displayed on first slide.");
        bannerPage.showSlide(2);
        assertTrue(bannerPage.slideIsDisplayed(2), "Second slide was not displayed after clicking next arrow.");
        assertTrue(bannerPage.arrowsDisplayed(), "Both Next and Previous arrows should be displayed on medium slide.");
        bannerPage.showSlide(3);
        assertTrue(bannerPage.slideIsDisplayed(3), "Third slide was not displayed after clicking next arrow.");
        assertTrue(bannerPage.arrowsDisplayed(),   "Only Previous arrow should be displayed on last slide.");
        bannerPage.showSlide(2);
        assertTrue(bannerPage.slideIsDisplayed(2), "Second slide was not displayed after clicking previous arrow.");
        assertTrue(bannerPage.arrowsDisplayed(), "Both Next and Previous arrows should be displayed on medium slide.");
        bannerPage.showSlide(1);
        assertTrue(bannerPage.slideIsDisplayed(1), "First slide was not displayed after clicking previous arrow.");
        assertTrue(bannerPage.arrowsDisplayed(),   "Only Next arrow should be displayed on first slide.");
        bannerPage.showSlide(3, BannerNavigationType.bullets);
        assertTrue(bannerPage.slideIsDisplayed(3), "Third slide was not displayed after clicking third bullet.");
        assertTrue(bannerPage.arrowsDisplayed(),   "Only Next arrow should be displayed on first slide.");
        bannerPage.showSlide(2, BannerNavigationType.bullets);
        assertTrue(bannerPage.slideIsDisplayed(2), "Second slide was not displayed after clicking second bullet.");
        assertTrue(bannerPage.arrowsDisplayed(),   "Both Next and Previous arrows should be displayed on medium slide.");
        bannerPage.showSlide(1, BannerNavigationType.bullets);
        assertTrue(bannerPage.slideIsDisplayed(1), "First slide was not displayed after clicking first bullet.");
        assertTrue(bannerPage.arrowsDisplayed(),   "Only Previous arrow should be displayed on last slide.");

    }

    /* 9. Navigation – bullets*/
    @Test(groups = {"regression"})
    public void bannerNavigationBullets() {
        skipTestWithIssues("D-19983");
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.bannerNavigationBullets);
        assertTrue(bannerPage.slideIsDisplayed(1), "First slide is not displayed.");
        assertEquals(BannerNavigationType.bullets, bannerPage.getNavigationType(), "Navigation type");
        bannerPage.showSlide(3);
        assertTrue(bannerPage.slideIsDisplayed(3), "Third slide was not displayed after clicking third bullet.");
        bannerPage.showSlide(1);
        assertTrue(bannerPage.slideIsDisplayed(1), "First slide was not displayed after clicking first bullet.");
        bannerPage.showSlide(2);
        assertTrue(bannerPage.slideIsDisplayed(2), "Second slide was not displayed after clicking second bullet.");
    }

    /* 10. Navigation – none*/
    @Test(groups = {"regression"})
    public void bannerNavigationNone() {
        skipTestWithIssues("D-19983");
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.bannerImage);
        assertTrue(bannerPage.slideIsDisplayed(1), "First slide is not displayed.");
        assertEquals(BannerNavigationType.none, bannerPage.getNavigationType(), "Navigation type");
    }

    /* 11. Banner as link*/
    @Test(groups = {"regression"})
    public void bannerLink() {
        skipTestWithIssues("D-19983");
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.bannerLink);
        assertTrue(bannerPage.slideIsDisplayed(1), "First slide is not displayed.");
        bannerPage.clickSlide(1);
        ExternalPage externalPage = new ExternalPage(bannerPage.getMainWindowHandle());
        String currentUrl = WebDriverUtils.getCurrentUrl();
        externalPage.close();
        assertEquals("https://www.google.com.ua/", currentUrl, "Banner should link to 'https://www.google.com.ua/', but '" + currentUrl + "' is opened.");

    }

    /* 12. Include banner in rotation*/
    @Test(groups = {"regression"})
    public void bannerInRotation() {
        skipTestWithIssues("D-19983");
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.bannerInRotation);
        List<BannerSlideType> slidesTypes = bannerPage.getSlidesTypes();
        validateEquals(2, slidesTypes.size(), "Number of slides");
        assertEquals(BannerSlideType.image, slidesTypes.get(0), "First slide");
        assertEquals(BannerSlideType.webContent, slidesTypes.get(1), "Second slide");
        assertTrue(bannerPage.slideIsDisplayed(1), "First slide is displayed");
        assertTrue(bannerPage.slideIsDisplayed(2), "Second slide is displayed");
    }

    /* 13. Time to display*/
    @Test(groups = {"regression"})
    public void banner5seconds() {
        skipTestWithIssues("D-19983");
        BannerPage bannerPage = (BannerPage) NavigationUtils.navigateToPage(ConfiguredPages.banner5seconds);
        int slidesNumber = bannerPage.getSlidesCount();
        validateEquals(3, slidesNumber, "Number of slides");
        long appearingOfSecondSlide = bannerPage.whenSlideDisplayed(2);
        long appearingOfThirdSlide =  bannerPage.whenSlideDisplayed(3);
        long timeToDisplay = appearingOfThirdSlide - appearingOfSecondSlide;
        assertTrue(4800 < timeToDisplay && timeToDisplay < 5200, "(Actual: '"+timeToDisplay+"ms') Slides switched after 5 seconds.");
    }
}