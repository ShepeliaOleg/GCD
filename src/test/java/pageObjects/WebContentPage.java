package pageObjects;

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

    public LoginPopup clickLoggedOut(GameLaunch type){
        clickByType(type);
        return new LoginPopup();
    }

    public AdminCanNotPlayPopup clickAdmin(GameLaunch type){
        clickByType(type);
        return new AdminCanNotPlayPopup();
    }

    private void clickButton(){
        WebDriverUtils.click(BUTTON_XP);
    }

    private void clickImage(){
        WebDriverUtils.click(IMAGE_XP);
    }

    private String clickByType(GameLaunch type){
        if(type.equals(GameLaunch.button)){
            clickButton();
            return getGameID(BUTTON_XP);
        }else if (type.equals(GameLaunch.image)){
            clickImage();
            return getGameID(GAMELAUNCH_XP);
        }else {
            throw new RuntimeException("Unknown game launch type");
        }
    }

    public boolean playAndValidateUrl(GameLaunch type, UserData userData){
        String gameID = clickByType(type);
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

    public boolean playAndValidateUrl(GameLaunch type){
        return playAndValidateUrl(type, null);
    }

    private String getGameID(String xpath){
        try{
            return WebDriverUtils.getAttribute(xpath, "data-game-code");
        }catch (Exception e){
            return WebDriverUtils.getAttribute(xpath, "data-gamecode");
        }
    }

    public void clickNextSlide(){
        new BannerPage().showNextSlide();
    }

}
