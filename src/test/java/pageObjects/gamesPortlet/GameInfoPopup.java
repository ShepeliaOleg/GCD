package pageObjects.gamesPortlet;

import pageObjects.core.AbstractPopup;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 7/22/13
 */
public class GameInfoPopup extends AbstractPopup{
	private static final String LABEL_TITLE_XP="//*[@class = 'title']";
	private String gameID;

	public GameInfoPopup(String gameID){
		super(new String[]{BUTTON_CLOSE_XP});
		this.gameID=gameID;
	}

	public void clickClose(){
		WebDriverUtils.click(BUTTON_CLOSE_XP);
		WebDriverUtils.waitForElementToDisappear(ROOT_XP);
	}

	public String getTitle(){
		return WebDriverUtils.getElementText(LABEL_TITLE_XP);
	}

	public boolean isTitleCorrect(){
		return getTitle().equalsIgnoreCase(gameID);
	}
}
