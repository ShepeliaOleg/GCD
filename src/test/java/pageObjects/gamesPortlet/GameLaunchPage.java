package pageObjects.gamesPortlet;

import pageObjects.core.AbstractPortalPage;
import pageObjects.core.AbstractServerIframe;
import utils.WebDriverUtils;
import utils.core.WebDriverFactory;

public class GameLaunchPage extends AbstractPortalPage {

    private static final String ROOT_XP =                   "//*[contains(@class,'gameFrame') or contains(@class,'game-iframe')]";
    private static final String GAME_MODE_XP=               "//*[@class='gameMode']";
    public  static final String IFRAME_LAUNCH_GAME_URL =    "igaming/";
    public  static final String REDIRECT_LAUNCH_GAME_URL =  "?game=";
    public  static final String GAME_MODE_URL =             "&real=";
    //game codes
    public  static final String IFRAME_GAME_1 =             "hlk2";
    public  static final String IFRAME_GAME_2 =             "mrcb";
    public  static final String REDIRECT_GAME_1 =           "hlk2";
    public  static final String NO_DEMO_GAME =              "hlk2";

    public  static final int TIMEOUT =              30;

    private static String  gameId;
    private static Integer realMode;

    public GameLaunchPage(String gameId, Integer realMode){
        super(new String[]{ROOT_XP});
        this.gameId = gameId;
        this.realMode = realMode;
    }

	public boolean iFrameGameUrlIsValid(){
		String url;
		for (int i=0; i<TIMEOUT; i++){
			url = WebDriverUtils.getCurrentUrl();
			if (realMode == null) {
                if(url.contains(IFRAME_LAUNCH_GAME_URL + gameId)){
                    return true;
                }
		    } else {
                if (url.contains(IFRAME_LAUNCH_GAME_URL +  gameId + GAME_MODE_URL + realMode)); {
                    return true;
                }
            }
        WebDriverUtils.waitFor(1000);
		}
		return false;
	}

    public boolean redirectGameUrlIsValid() {
        WebDriverUtils.waitFor(1000);
        String url = WebDriverUtils.getCurrentUrl();
        int mode;
        if (url.contains(REDIRECT_LAUNCH_GAME_URL + gameId)) {
            if (realMode == null || realMode == 1) {
                mode = 1;
            } else {
                mode = 0;
            }
            return url.contains(GAME_MODE_URL + mode) ? true : false;
        } else {
            return false;
        }
    }

    public boolean isRealMode() {
        WebDriverUtils.switchToIframeByXpath(WebDriverFactory.getPortalDriver(), ROOT_XP);
        WebDriverUtils.waitForElement(GAME_MODE_XP, 30);
        return WebDriverUtils.getElementText(GAME_MODE_XP).equals("PLAYING FOR REAL");
//        return new GameLaunchIframe().isRealMode();
    }

    public class GameLaunchIframe extends AbstractServerIframe {
        private static final String GAME_MODE_XP=               "//*[@class='gameMode']";

        public GameLaunchIframe(String iframeId) {
            super(iframeId);
        }

        public boolean isRealMode() {
            waitForGameToLoad();
            return WebDriverUtils.getElementText(GAME_MODE_XP).equals("PLAYING FOR REAL");
        }

        private void waitForGameToLoad() {
            WebDriverUtils.waitForElement(GAME_MODE_XP, 30);
        }
    }
}