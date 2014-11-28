package pageObjects.gamesPortlet;

import enums.Licensee;
import pageObjects.core.AbstractPortalPage;
import utils.WebDriverUtils;
import utils.core.DataContainer;

public class GameElement extends AbstractPortalPage{

	private static final String TAG_JACKPOT=			"data-game-jackpot";

	private String ROOT_GAME;
	private String buttonPlayReal;
	private String buttonPlayDemo;
	private String buttonFavouritesActive;
	private String buttonInfo;
	private String labelGameTitle;
	private String image;
	private String divNew;
	private String labelJackpot;
	private String gameID;

	public GameElement(String gameID){
		super(new String[]{"//*[@data-key='" + gameID + "']"});
		ROOT_GAME=				"//*[@data-key='" + gameID + "']";
		buttonPlayReal=			ROOT_GAME + "//*[contains(@class, 'btn_type_play')]";
		buttonPlayDemo=			ROOT_GAME + "//*[contains(@class, 'btn_type_play-demo')]";
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
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.sevenRegal)) {
            WebDriverUtils.mouseOver(ROOT_GAME);
            WebDriverUtils.click(buttonPlayReal);
        }else {
            WebDriverUtils.click(ROOT_GAME);
            new StartGamePopup().clickReal();
        }
	}

    public void clickPlayRealList(){
        WebDriverUtils.click(ROOT_GAME + "/a");
        new StartGamePopup().clickReal();
    }

	public void clickPlayDemo(){
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.sevenRegal)){
            WebDriverUtils.mouseOver(ROOT_GAME);
            WebDriverUtils.click(buttonPlayDemo);
        }else {
            WebDriverUtils.click(ROOT_GAME);
            new StartGamePopup().clickDemo();
        }
	}

	public void clickTitle(){
		WebDriverUtils.mouseOver(ROOT_GAME);
		WebDriverUtils.click(labelGameTitle);
	}

	public void favourite(){
        StartGamePopup startGamePopup = gameStartPopup();
        startGamePopup.favourite();
        startGamePopup.closePopup();
    }

    public void unFavourite(){
        StartGamePopup startGamePopup = gameStartPopup();
        startGamePopup.unFavourite();
        startGamePopup.closePopup();
    }

    private StartGamePopup gameStartPopup(){
        WebDriverUtils.click(ROOT_GAME);
        return new StartGamePopup();
    }

	public boolean isFavouriteActive(){
        StartGamePopup startGamePopup = gameStartPopup();
        boolean result = startGamePopup.isFavourite();
        startGamePopup.closePopup();
        return result;
	}

    public boolean isNew(){
		return WebDriverUtils.isVisible(divNew);
	}

	public boolean isDemoPresent(){
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.sevenRegal)){
            WebDriverUtils.mouseOver(ROOT_GAME);
            return WebDriverUtils.isVisible(buttonPlayDemo, 0);
        }else {
            WebDriverUtils.click(ROOT_GAME);
            return new StartGamePopup().isDemoPresent();
        }
	}

	public boolean isRealPresent(){
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.sevenRegal)){
            WebDriverUtils.mouseOver(ROOT_GAME);
            return WebDriverUtils.isVisible(buttonPlayReal, 0);
        }else {
            WebDriverUtils.click(ROOT_GAME);
            return new StartGamePopup().isRealPresent();
        }
	}

	public boolean isImagePresent(){
		return WebDriverUtils.isVisible(image, 0);
	}

    public boolean isTitlePresent(){
        return WebDriverUtils.isVisible(labelGameTitle, 0);
    }
}
