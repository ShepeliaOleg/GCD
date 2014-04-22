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

	public GameLaunchPopup(String mainWindowHandle){
		super(mainWindowHandle);
		this.mainWindowHandle=mainWindowHandle;
	}

	public String getWindowUrl(){
		return WebDriverUtils.getCurrentUrl();
	}

	public boolean isUrlValid(){
		return getWindowUrl().contains(VALID_GAME_URL);
	}

	public String getMainWindowHandle(){

		return mainWindowHandle;
	}

	public void closePopup(){
		WebDriverUtils.closeCurrentWindow();
		WebDriverUtils.switchToWindow(getMainWindowHandle());
	}
}
