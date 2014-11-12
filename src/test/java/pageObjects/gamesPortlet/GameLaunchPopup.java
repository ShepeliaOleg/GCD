package pageObjects.gamesPortlet;

import pageObjects.core.AbstractPortalBrowserWindow;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

public class GameLaunchPopup extends AbstractPortalBrowserWindow {

    private static final String CORRECT_GAME_URL="game="+PLACEHOLDER;
    private static final String NO_FLASH_URL="/flashless.html";
    private static String gameID = "";

    public GameLaunchPopup(String mainWindowHandle){
        super(mainWindowHandle);
    }

    public GameLaunchPopup(String mainWindowHandle, String gameID){
        super(mainWindowHandle);
        GameLaunchPopup.gameID = gameID;
    }

	private boolean isUrlValid(){
		String url;
		for (int i=0; i<30; i++){
			url=getWindowUrl();
				if(url.contains(CORRECT_GAME_URL.replace(PLACEHOLDER, gameID))){
					return true;
				}else
					WebDriverUtils.waitFor(1000);
		}
		return false;
	}

    public void assertGameLaunchAndClose(){
        AbstractTest.assertTrue(isUrlValid(), "Game '"+gameID+"' launched");
        close();
    }


}
