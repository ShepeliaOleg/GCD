package pageObjects.gamesPortlet;

import pageObjects.base.AbstractPage;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 7/15/13
 */
public class GameElement extends AbstractPage{

	private static final String TAG_JACKPOT=			"data-game-jackpot";
	private static final String GAMES_PORTLET_ROOT_XP=	"//div[@data-portlet = 'gamesinfo']";

	private String ROOT_GAME;
	private String buttonPlayReal;
	private String buttonPlayDemo;
	private String buttonFavourites;
	private String buttonFavouritesActive;
	private String buttonInfo;
	private String labelGameTitle;
	private String image;
	private String divNew;
	private String labelJackpot;
	private String gameID;

	public GameElement(String gameID){
		super(new String[]{GAMES_PORTLET_ROOT_XP, "//li[@data-name='" + gameID + "']"});
		ROOT_GAME=				"//li[@data-name='" + gameID + "']";
		buttonPlayReal=			ROOT_GAME + "//a[contains(@class, 'play')]";
		buttonPlayDemo=			ROOT_GAME + "//a[contains(@class, 'demo')]";
		buttonFavourites=		ROOT_GAME + "//a[contains(@class, 'games-favorite')]";
		buttonFavouritesActive=	ROOT_GAME + "//a[contains(@class, 'games-favorite active')]";
		buttonInfo=				ROOT_GAME + "//a[@class='btn info']";
		labelGameTitle=			ROOT_GAME + "//a[@class='launcher ']";
		image=					ROOT_GAME + "//img";
		divNew=					ROOT_GAME + "//div[@class='game-tag-new']";
		labelJackpot=			ROOT_GAME + "//span[@class='game-jackpot']";
		this.gameID=			gameID;
	}

	public String getLabelGameTitle(){
		return WebDriverUtils.getElementText(labelGameTitle);
	}

	public String getGameId(){
		return gameID;
	}

	public String getJackpot(){
		return WebDriverUtils.getAttribute(labelJackpot, TAG_JACKPOT);
	}

	public boolean isJackpotPresent(){
		return WebDriverUtils.isVisible(labelJackpot);
	}

	public boolean isInfoPresent(){
		return WebDriverUtils.isVisible(buttonInfo);
	}

	public void clickPlayReal(){
		WebDriverUtils.mouseOver(ROOT_GAME);
		WebDriverUtils.click(buttonPlayReal);
	}

	public void clickPlayDemo(){
		WebDriverUtils.mouseOver(ROOT_GAME);
		WebDriverUtils.click(buttonPlayDemo);
	}

	public void clickTitle(){
		WebDriverUtils.mouseOver(ROOT_GAME);
		WebDriverUtils.click(labelGameTitle);
	}

	public void clickFavourite(){
		WebDriverUtils.click(buttonFavourites);
		WebDriverUtils.waitFor();
	}

	public void clickFavouriteActive(){
		WebDriverUtils.click(buttonFavouritesActive);
		WebDriverUtils.waitFor();
	}

	public boolean isFavouriteActive(){
		return WebDriverUtils.isVisible(buttonFavouritesActive);
	}

	public GameInfoPopup clickInfo(){
		WebDriverUtils.click(buttonInfo);
		return new GameInfoPopup(getGameId());
	}

	public boolean isNew(){
		return WebDriverUtils.isVisible(divNew);
	}

	public boolean isDemoPresent(){
		WebDriverUtils.mouseOver(ROOT_GAME);
		return WebDriverUtils.isVisible(buttonPlayDemo, 0);
	}

	public boolean isRealPresent(){
		WebDriverUtils.mouseOver(ROOT_GAME);
		return WebDriverUtils.isVisible(buttonPlayReal, 0);
	}

	public boolean isImagePresent(){
		return WebDriverUtils.isVisible(image, 0);
	}

    public boolean isTitlePresent(){
        return WebDriverUtils.isVisible(labelGameTitle, 0);
    }
}
