package pageObjects.webcontent;

import enums.ConfiguredPages;
import enums.GameLaunch;
import enums.PlayerCondition;
import pageObjects.core.AbstractPage;
import pageObjects.gamesPortlet.GameElement;
import pageObjects.gamesPortlet.GameLaunchPage;
import pageObjects.gamesPortlet.GameLaunchPopup;
import pageObjects.login.LoginPopup;
import springConstructors.UserData;
import utils.WebDriverUtils;

public class WebcontentPage extends AbstractPage{

    private static final String GAMELAUNCH_XP = "//*[@class='fn-launch-game']";
    private static final String IMAGE_XP =      GAMELAUNCH_XP + "/img";
    private static final String BUTTON_XP =     "//*[contains(@class, 'btn')][contains(@class, 'play')]";

    public WebcontentPage(){
        super(new String[]{IMAGE_XP});
    }

    public LoginPopup clickLoggedOut(GameLaunch type){
        clickByType(type);
        return new LoginPopup();
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
            return new GameLaunchPopup(getMainWindowHandle(), gameID).isUrlValid();
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

    public boolean validateGameNotLaunched(ConfiguredPages page){
        if(WebDriverUtils.getWindowHandles().size() == 1&&WebDriverUtils.getCurrentUrl().contains(page.toString())){
        return true;
        }else {
            return false;
        }
    }


}
