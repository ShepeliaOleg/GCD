package pageObjects.banner;

import enums.BannerNavigationType;
import enums.BannerSlideType;
import pageObjects.core.AbstractPage;
import pageObjects.core.AbstractPageObject;
import pageObjects.gamesPortlet.GameLaunchPopup;
import pageObjects.login.LoginPopup;
import utils.WebDriverUtils;
import java.util.ArrayList;
import java.util.List;

public class BannerPage extends AbstractPage {
    private static final int    TIMEOUT_NOW =                       0;
    private static final int    TIMEOUT_SHORT =                     1;
    private static final int    TIMEOUT_LONG =                      10;
	protected static final String ROOT_XP =                         "//*[contains(@class,'portlet-banner')]/..";
    private static final String TITLE_XP =                          ROOT_XP + "//span[contains(@class,'title')]";
    private static final String BODY_XP =                           ROOT_XP + "//ul[contains(@class,'paging')]";
    private static final String LIST_XP =                           "//li";
    private static final String SLIDES_XP =                         BODY_XP + LIST_XP;
    private static final String SLIDE_CONTENT_XP =                  "//*[contains(@class,'slide-content')]";
    private static final String NAVIGATION_XP =                     ROOT_XP + "//*[@class='pagination']";
    private static final String NAVIGATION_ARROW_PREV_XP =          NAVIGATION_XP + "//*[contains(@class,'previous')]";
    private static final String NAVIGATION_ARROW_NEXT_XP =          NAVIGATION_XP + "//*[contains(@class,'next')]";
    private static final String NAVIGATION_BULLETS_XP =             NAVIGATION_XP + LIST_XP + "[contains(@class,'_bullet')]";
    private static final String NAVIGATION_BUTTONS_XP =             NAVIGATION_XP + LIST_XP + "[contains(@class,'_number')]";

	public BannerPage(){
		super(new String[]{ROOT_XP});
	}

    public BannerPage(String[] visible){
        super(visible);
    }


    public int getSlidesCount() {
        return WebDriverUtils.getXpathCount(SLIDES_XP);
    }

    public List<BannerSlideType> getSlidesTypes() {
        List<BannerSlideType> bannerSlideTypes = new ArrayList<>();
        for (int i=1; i<=getSlidesCount(); i++) {
            bannerSlideTypes.add(getSlideTypeByXpath(getSlideXpathByIndex(i)));
        }
        return bannerSlideTypes;
    }

    private String getSlideXpathByIndex(int slideIndex) {
        return SLIDES_XP + "[" + slideIndex + "]" + SLIDE_CONTENT_XP + "//div";
    }

    private BannerSlideType getSlideTypeByXpath(String xpath) {
        String attribute = WebDriverUtils.getAttribute(xpath, "class");

        if (attribute.contains("image")) {
                return BannerSlideType.image;
            } else if (attribute.contains("webcontent")) {
                return BannerSlideType.webContent;
            } else if (attribute.contains("html")) {
                return BannerSlideType.html;
            } else
                return null;
    }

    private boolean slideIsDisplayed(String xpath) {
        return slideIsDisplayed(xpath, TIMEOUT_SHORT);
    }

    private boolean slideIsDisplayed(String xpath, long timeout) {
        return WebDriverUtils.isElementVisible(xpath, timeout);
    }

    public boolean slideIsDisplayed(int slideIndex) {
        return slideIsDisplayed(getSlideXpathByIndex(slideIndex));
    }

    public boolean slideIsDisplayed(int slideIndex, int timeout) {
        return slideIsDisplayed(getSlideXpathByIndex(slideIndex), timeout);
    }

    private long whenSlideDisplayed(String xpath) {
        return whenSlideDisplayed(xpath, TIMEOUT_LONG);
    }

    private long whenSlideDisplayed(String xpath, long timer) {
        if (slideIsDisplayed(xpath, timer)) {
            return System.currentTimeMillis();
        } else
            throw new RuntimeException("Expected slide by xpath: '" + xpath +"' was not appeared after " + timer + " seconds.");
    }

    public long whenSlideDisplayed(int slideIndex) {
        return whenSlideDisplayed(getSlideXpathByIndex(slideIndex));
    }

    private int getDisplayedSlideIndex() {
        return getDisplayedSlideIndex(getSlidesCount());
    }

    private int getDisplayedSlideIndex(int slidesCount) {
        for (int slideIndex = 1; slideIndex <= slidesCount; slideIndex++) {
            if (slideIsDisplayed(getSlideXpathByIndex(slideIndex), TIMEOUT_NOW)) {
                return slideIndex;
            }
        }
        return 0;
    }

    public void showNextSlide() {
        WebDriverUtils.click(NAVIGATION_ARROW_NEXT_XP);
        WebDriverUtils.waitFor();
    }

    public static boolean isNextArrowVisible() {
        return WebDriverUtils.isVisible(NAVIGATION_ARROW_NEXT_XP, 1);
    }

    private void showPreviousSlide() {
        WebDriverUtils.click(NAVIGATION_ARROW_PREV_XP);
    }

    private void showSlideBullets(int slideIndex) {
        WebDriverUtils.click(NAVIGATION_BULLETS_XP + "[" + slideIndex + "]");
    }

    private void showSlideButtons(int slideIndex) {
        WebDriverUtils.click(NAVIGATION_BUTTONS_XP + "[" + slideIndex + "]");
    }

    public void showSlide(int slideIndex) {
        showSlide(slideIndex, getNavigationType());
    }

    public void showSlide(int slideIndex, BannerNavigationType navigationType) {
        int slidesCount = getSlidesCount();
        if (slideIndex > 0 && slideIndex < slidesCount) {
            int slideIndexDiff = slideIndex - getDisplayedSlideIndex();
            int slideIndexDiffAbs = Math.abs(slideIndexDiff);
            if (slideIndexDiff != 0) {
                switch (navigationType) {
                    case arrows:
                        for (int i=0; i<slideIndexDiffAbs; i++) {
                            if (slideIndexDiff > 0) {
                                showNextSlide();
                            } else {
                                showPreviousSlide();
                            }
                        }
                    break;
                    case bullets:
                        showSlideBullets(slideIndex);
                    break;
                    case buttons:
                        showSlideButtons(slideIndex);
                    break;
                    case none:
                        WebDriverUtils.runtimeExceptionWithUrl("Cannot show slide " + slideIndex + ". There is no navigation items present.");
                }
            }
        }   else {
            WebDriverUtils.runtimeExceptionWithUrl("Cannot show slide " + slideIndex + ". Slide index is out of range.");
        }
    }

    public BannerNavigationType getNavigationType() {
        boolean arrowsVisible = WebDriverUtils.isVisible(NAVIGATION_ARROW_NEXT_XP, TIMEOUT_NOW) ||  WebDriverUtils.isVisible(NAVIGATION_ARROW_NEXT_XP, TIMEOUT_NOW);
        boolean bulletsVisible = WebDriverUtils.isVisible(NAVIGATION_BULLETS_XP, TIMEOUT_NOW);

        if (arrowsVisible && bulletsVisible) {
            return BannerNavigationType.arrowsAndBullets;
        } else if (arrowsVisible) {
            return BannerNavigationType.arrows;
        } else if (bulletsVisible) {
            return BannerNavigationType.bullets;
        } else if (WebDriverUtils.isVisible(NAVIGATION_BUTTONS_XP, TIMEOUT_NOW)) {
            return BannerNavigationType.buttons;
        } else
            return BannerNavigationType.none;
    }

    public boolean arrowsDisplayed() {
        int slidesCount = getSlidesCount();
        int currentSlideIndex = getDisplayedSlideIndex(slidesCount);

        boolean arrowNextVisible =  WebDriverUtils.isVisible(NAVIGATION_ARROW_NEXT_XP, TIMEOUT_NOW);
        boolean arrowPreviousVisible =  WebDriverUtils.isVisible(NAVIGATION_ARROW_NEXT_XP, TIMEOUT_NOW);

        if (currentSlideIndex == 1) {
            return !arrowPreviousVisible && arrowNextVisible;
        } else if (currentSlideIndex == slidesCount) {
            return arrowPreviousVisible && !arrowNextVisible;
        } else {
            return arrowPreviousVisible && arrowNextVisible;
        }

    }

    public AbstractPageObject clickSlide(int slideIndex) {
        return clickSlide(getSlideXpathByIndex(slideIndex));
    }

    private AbstractPageObject clickSlide(String xpath) {
        WebDriverUtils.click(xpath);
        if (isLoggedIn()) {
            return new GameLaunchPopup(getMainWindowHandle());
        } else {
            return new LoginPopup();
        }
    }


}
