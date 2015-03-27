package pageObjects.webcontent;

import enums.GameLaunch;
import enums.Page;
import pageObjects.admin.AdminCanNotPlayPopup;
import pageObjects.banner.BannerPage;
import pageObjects.core.AbstractPortalPage;
import pageObjects.login.LoginPopup;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.WebDriverUtils;

public class WebContentPage extends AbstractPortalPage{

    private static final String GAMELAUNCH_XP = "//*[contains(@class,'fn-launch-game')]";
    private static final String IMAGE_XP =      GAMELAUNCH_XP + "/img";
    private static final String BUTTON_XP =     GAMELAUNCH_XP + "[contains(@class, 'btn')]";

    public WebContentPage(){
        super(new String[]{IMAGE_XP});
    }

    public LoginPopup clickLoggedOut(GameLaunch type, int slideIndex){
        clickByType(type, slideIndex);
        return new LoginPopup();
    }

    public AdminCanNotPlayPopup clickAdmin(GameLaunch type, int slideIndex){
        clickByType(type, slideIndex);
        return new AdminCanNotPlayPopup();
    }

    private String getSlidePrefix(int slideIndex) {
        return "//*[" + slideIndex + "]";
        //return "//li[" + slideIndex + "]";
    }

    private void clickButton(int slideIndex){
        WebDriverUtils.click(getSlidePrefix(slideIndex) + BUTTON_XP);
    }

    private void clickImage(int slideIndex){
        WebDriverUtils.click(getSlidePrefix(slideIndex) + IMAGE_XP);
    }

    private String clickByType(GameLaunch type, int slideIndex){
        String gameId = getGameID(type, slideIndex);
        clickGameOnSlide(type, slideIndex);
        return gameId;
    }

    private void clickGameOnSlide(GameLaunch type, int slideIndex){
        switch (type){
            case button:
                clickButton(slideIndex); break;
            case image:
                clickImage(slideIndex);  break;
        }
    }

    public void playAndAssertUrl(GameLaunch type, int slideIndex, UserData userData){
        String gameId = clickByType(type, slideIndex);
        if(userData!=null){
            LoginPopup loginPopup = new LoginPopup();
            loginPopup.login(userData, false, Page.gameLaunch);
        }
        NavigationUtils.assertGameLaunch(gameId, 1);
    }

    public void playAndAssertUrl(GameLaunch type, int slideIndex){
        playAndAssertUrl(type, slideIndex, null);
    }

    private String getGameID(GameLaunch type, int slideIndex){
        String xpath = null;
        switch (type) {
            case image:
                xpath = WebDriverUtils.getPrecedingElement(IMAGE_XP); break;
            case button:
                xpath = BUTTON_XP; break;
        }
        return WebDriverUtils.getAttribute(getSlidePrefix(slideIndex) + xpath, "data-game-code");
    }

    public void clickNextSlide(){
        new BannerPage().showNextSlide();
    }

}
