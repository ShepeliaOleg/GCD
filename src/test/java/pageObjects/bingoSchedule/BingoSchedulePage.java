package pageObjects.bingoSchedule;

import pageObjects.core.AbstractPage;
import utils.WebDriverUtils;

public class BingoSchedulePage extends AbstractPage{
	private static final String ROOT_XP =                "//div[contains(@id, 'bingoscheduleportlet')]";
    private static final String BUTTON_SEE_ALL_GAMES_XP =ROOT_XP + "//div/a[contains(@class,'btn')]/span";
    private static final String HEADER_XP =              ROOT_XP + "//div[@class='bingo-header']";
    private static final String COLUMN_TITLE_XP =        HEADER_XP + "/div[contains(@class,'bingo-game-title')]";

	public BingoSchedulePage(){
		super(new String[]{ROOT_XP, BUTTON_SEE_ALL_GAMES_XP});
	}

    public int getColumnsNumber() {
        return WebDriverUtils.getXpathCount(COLUMN_TITLE_XP);
    }

//    @FindBy(xpath=BUTTON_SEE_ALL_GAMES_XP)
//    private WebElement buttonSeeAllGames;

}
