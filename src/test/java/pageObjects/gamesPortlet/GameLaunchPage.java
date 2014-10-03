package pageObjects.gamesPortlet;

import pageObjects.core.AbstractPage;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

public class GameLaunchPage extends AbstractPage {

    private static final String ROOT_XP="//*[@class='gameFrame']";
    private static final String CORRECT_GAME_URL="game="+PLACEHOLDER;
    private static String gameID;

    public GameLaunchPage(String gameID){
        super(new String[]{ROOT_XP});
        GameLaunchPage.gameID = gameID;
    }

    public String getUrl(){
        return WebDriverUtils.getCurrentUrl();
    }

	private boolean isUrlValid(){
		String url;
		for (int i=0; i<=30; i++){
			url=getUrl();
				if(url.contains(CORRECT_GAME_URL.replace(PLACEHOLDER, gameID))){
					return true;
				}else
					WebDriverUtils.waitFor(1000);
		}
		return false;
	}

    public void assertGameLaunch(){
        AbstractTest.assertTrue(isUrlValid(), "Game '"+gameID+"' launched");
    }
}