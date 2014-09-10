package pageObjects.banner;

import enums.BannerNavigationType;
import enums.BannerSlideType;
import pageObjects.base.AbstractPage;
import utils.WebDriverUtils;

import java.util.ArrayList;
import java.util.List;

public class BannerPage extends AbstractPage{
    private static final int    TIMEOUT =                           10;
	private static final String ROOT_XP =                           "//*[contains(@id,'banner')]";
    private static final String TITLE_XP =                          ROOT_XP + "//span[contains(@class,'title')]";
    private static final String BODY_XP =                           ROOT_XP + "//ul[contains(@class,'paging')]";
    private static final String SLIDES_XP =                         BODY_XP + "//li";
    private static final String SLIDE_CONTENT_XP =                  "//*[contains(@class,'slide-content')]";
    private static final String NAVIGATION_ARROW_PREV_XP =          "";
    private static final String NAVIGATION_ARROW_NEXT_XP =          "";
    private static final String NAVIGATION_BULLETS_XP =             "";
    private static final String NAVIGATION_BUTTONS_XP =             "";

	public BannerPage(){
		super(new String[]{ROOT_XP, TITLE_XP, BODY_XP});
	}

    public int getSlidesCount() {
        return WebDriverUtils.getXpathCount(SLIDES_XP);
    }

    public List<BannerSlideType> getSlidesTypes() {
        List<BannerSlideType> bannerSlideTypes = new ArrayList<>();
        for (int i=1; i<=getSlidesCount(); i++) {
            bannerSlideTypes.add(getSlideTypeByXpath(getSlideXpathByNumber(i)));
        }
        return bannerSlideTypes;
    }

    private String getSlideXpathByNumber(int number) {
        return SLIDES_XP + "[" + number + "]" + SLIDE_CONTENT_XP + "//div";
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
        return slideIsDisplayed(xpath, TIMEOUT);
    }

    private boolean slideIsDisplayed(String xpath, long timeout) {
        return WebDriverUtils.isElementVisible(xpath, timeout);
    }

    public boolean slideIsDisplayed(int slideIndex) {
        return slideIsDisplayed(getSlideXpathByNumber(slideIndex));
    }

    private long whenSlideDisplayed(String xpath) {
        return whenSlideDisplayed(xpath, TIMEOUT);
    }

    private long whenSlideDisplayed(String xpath, long timer) {
        if (slideIsDisplayed(xpath, timer)) {
            return System.currentTimeMillis();
        } else
            throw new RuntimeException("Expected slide by xpath:" + xpath +" was not appeared after " + timer);
    }

    public long whenSlideDisplayed(int slideIndex) {
        return whenSlideDisplayed(getSlideXpathByNumber(slideIndex));
    }

    public void showNextSlide(BannerNavigationType navigationType) {
        switch (navigationType) {
            case arrows:
                WebDriverUtils.click(NAVIGATION_ARROW_NEXT_XP);
            case bullets:

            case buttons:

        }
    }

    public BannerNavigationType getNavigationType() {
        boolean arrowsVisible =  WebDriverUtils.isVisible(NAVIGATION_ARROW_NEXT_XP, 1);
        boolean bulletsVisible = WebDriverUtils.isVisible(NAVIGATION_BULLETS_XP, 1);
        boolean buttonsVisible = WebDriverUtils.isVisible(NAVIGATION_BUTTONS_XP, 1);

        if (arrowsVisible && bulletsVisible) {
            return BannerNavigationType.arrowsAndBullets;
        } else if (arrowsVisible) {
            return BannerNavigationType.arrows;
        } else if (bulletsVisible) {
            return BannerNavigationType.bullets;
        } else if (buttonsVisible) {
            return BannerNavigationType.buttons;
        } else
            return BannerNavigationType.none;
    }
}
