package pageObjects.webcontent;

import enums.GameLaunch;
import enums.Page;
import pageObjects.admin.AdminCanNotPlayPopup;
import pageObjects.banner.BannerPage;
import pageObjects.core.AbstractPage;
import pageObjects.gamesPortlet.GameLaunchPage;
import pageObjects.gamesPortlet.GameLaunchPopup;
import pageObjects.login.LoginPopup;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.WebDriverUtils;

public class WebContentPage extends AbstractPage{

    private static final String GAMELAUNCH_XP = "//*[contains(@class,'fn-launch-game')]";
    private static final String IMAGE_XP =      GAMELAUNCH_XP + "/img";
    private static final String BUTTON_XP =     GAMELAUNCH_XP + "[contains(@class, 'btn')]";

    public WebContentPage(){
        super(new String[]{IMAGE_XP});
    }

    public LoginPopup clickLoggedOut(GameLaunch type, int page){
        clickByType(type, page);
        return new LoginPopup();
    }

    public AdminCanNotPlayPopup clickAdmin(GameLaunch type, int page){
        clickByType(type, page);
        return new AdminCanNotPlayPopup();
    }

    private void clickButton(int page){
        WebDriverUtils.click("//li["+page+"]"+BUTTON_XP);
    }

    private void clickImage(int page){
        WebDriverUtils.click("//li["+page+"]"+IMAGE_XP);
    }

    private String clickByType(GameLaunch type, int page){
        String multiPage;
        if(WebDriverUtils.isVisible(BannerPage.ROOT_XP, 1)){
            multiPage = "//li["+page+"]";
        }else {
            multiPage = "";
        }
        String gameId = getGameID(type, multiPage);
        clickGame(type, multiPage);
        return gameId;
    }

    private void clickGame(GameLaunch type, String multipage){
        String xpath = BUTTON_XP;
        if(type.equals(GameLaunch.image)){
            xpath = IMAGE_XP;
        }
        WebDriverUtils.click(multipage+xpath);
    }

    public void playAndAssertUrl(GameLaunch type, int page, UserData userData){
        String gameID = clickByType(type, page);
        if(userData!=null){
            LoginPopup loginPopup = new LoginPopup();
            loginPopup.login(userData, false, Page.gameLaunch);
        }
        NavigationUtils.assertGameLaunch(gameID, 1);
    }

    public void playAndAssertUrl(GameLaunch type, int page){
        playAndAssertUrl(type, page, null);
    }

    private String getGameID(GameLaunch type, String multiPage){
        String xpath;
        if(type.equals(GameLaunch.image)){
            xpath=IMAGE_XP + "/..";
        }else {
            xpath = BUTTON_XP;
        }
        return WebDriverUtils.getAttribute(multiPage+xpath, "data-game-code");
    }

    public void clickNextSlide(){
        new BannerPage().showNextSlide();
    }

}
