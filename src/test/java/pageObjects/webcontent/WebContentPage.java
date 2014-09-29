package pageObjects.webcontent;

import enums.GameLaunch;
import pageObjects.admin.AdminCanNotPlayPopup;
import pageObjects.banner.BannerPage;
import pageObjects.core.AbstractPage;
import pageObjects.gamesPortlet.GameLaunchPage;
import pageObjects.gamesPortlet.GameLaunchPopup;
import pageObjects.login.LoginPopup;
import springConstructors.UserData;
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
        switch (type){
            case button:
                clickButton(page);
                return getGameID("//li["+page+"]"+BUTTON_XP);
            case image:
                clickImage(page);
                return getGameID("//li["+page+"]"+IMAGE_XP+"/..");
            default:
                throw new RuntimeException("Unknown game launch type");
        }
    }

    public boolean playAndValidateUrl(GameLaunch type, int page, UserData userData){
        String gameID = clickByType(type, page);
        if(userData!=null){
            LoginPopup loginPopup = new LoginPopup();
            loginPopup.login(userData);
        }
        if(platform.equals(PLATFORM_DESKTOP)){
            return new GameLaunchPopup(getMainWindowHandle(), gameID).checkUrlAndClose();
        }else{
            return new GameLaunchPage(gameID).isUrlValid();
        }
    }

    public boolean playAndValidateUrl(GameLaunch type, int page){
        return playAndValidateUrl(type, page, null);
    }

    private String getGameID(String xpath){
        return WebDriverUtils.getAttribute(xpath, "data-game-code");
    }

    public void clickNextSlide(){
        new BannerPage().showNextSlide();
    }

}
