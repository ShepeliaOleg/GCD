package pageObjects.gamesPortlet;

import pageObjects.base.AbstractBrowserWindowPopup;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 7/31/13
 */

public class GameLaunchPopup extends AbstractBrowserWindowPopup{

	private String mainWindowHandle;
    private static final String VALID_GAME_URL="/launchcasino.html";
    private static final String NO_FLASH_URL="/flashless.html";

    public GameLaunchPopup(String mainWindowHandle){
        super(mainWindowHandle);
        this.mainWindowHandle=mainWindowHandle;
    }

    public String getWindowUrl(){
        return WebDriverUtils.getCurrentUrl();
    }

    public boolean isUrlValid(){
        for (int i=0; i<30; i++){
            if(getWindowUrl().contains(VALID_GAME_URL) || getWindowUrl().contains(NO_FLASH_URL)){
                return true;
            }else WebDriverUtils.waitFor(1000);
        }
        return false;
    }

	public String getMainWindowHandle(){

		return mainWindowHandle;
	}

	public void closePopup(){
		WebDriverUtils.closeCurrentWindow();
		WebDriverUtils.switchToWindow(getMainWindowHandle());
	}
}
