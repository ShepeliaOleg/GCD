package pageObjects.gamesPortlet;

import pageObjects.core.AbstractBrowserWindow;
import utils.WebDriverUtils;

public class GameLaunchPopup extends AbstractBrowserWindow {

	private static final String VALID_GAME_URL="/launchcasino.html";
    private static final String NO_FLASH_URL="/flashless.html";
    private static String gameID = "";

    public GameLaunchPopup(String mainWindowHandle){
        super(mainWindowHandle);
    }

    public GameLaunchPopup(String mainWindowHandle, String gameID){
        super(mainWindowHandle);
        this.gameID = gameID;
    }

	public boolean isUrlValid(){
		String url;
		for (int i=0; i<30; i++){
			url=getWindowUrl();
				if((url.contains(VALID_GAME_URL)&&url.contains(gameID)) || url.contains(NO_FLASH_URL)){
					return true;
				}else
					WebDriverUtils.waitFor(1000);
		}
		return false;
	}

    public boolean checkUrlAndClose(){
        boolean isValid = isUrlValid();
        close();
        return isValid;
    }


}
