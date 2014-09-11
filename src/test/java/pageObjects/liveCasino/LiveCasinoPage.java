package pageObjects.liveCasino;

import pageObjects.account.LoginPopup;
import pageObjects.core.AbstractPage;
import pageObjects.core.AbstractPageObject;
import pageObjects.gamesPortlet.GameLaunchPopup;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 6/26/13
 */

public class LiveCasinoPage extends AbstractPage{

	private static final String ROOT_XP=					"//*[contains(@id, 'portlet_livetablefinder')]";
	private static final String FILTER_XP=					"//div[@class='live-table-finder-filter']";
	private static final String TABLES_XP=					"//div[@class='live-table-finder-tables']";

	private static final String FIELD_SORT_NAME_XP=			"//div[@class='live-table-header']//li[contains(@class, 'name')]";
	private static final String FIELD_SORT_GAME_TYPE_XP=	"//div[@class='live-table-header']//li[contains(@class, 'type')]";
	private static final String FIELD_SORT_DEALERS_NAME_XP=	"//div[@class='live-table-header']//li[contains(@class, 'dealer')]";

	private String checkBoxXP=								"//*[@class='live-table-finder-form']//input";
	private String labelCheckboxXP=							"//*[@class='live-table-finder-form']//label";
	private String labelNameXP=								"//*[@class='game-name']";
	private String labelGameTypeXP=							"//*[@class='game-type']";
	private String labelDealerXP=							"//*[@class='game-info']";
	private String buttonPlayXP=							"//td[4]//p/a";
	private String buttonInfoXP=							"//td[3]//p/a";

	public LiveCasinoPage(){
		super(new String[]{ROOT_XP, TABLES_XP, FILTER_XP});
	}

	public String getCheckboxLabelText(int index){
		String label=WebDriverUtils.getElementText("//*[@class='live-table-finder-form']//li[" + index + "]/label").toLowerCase();
		char[] labelArray=label.toCharArray();
		labelArray[0]=Character.toUpperCase(labelArray[0]);
		label=new String(labelArray);
		return label;
	}

	public String getNameElement(int index){
		return WebDriverUtils.getElementText("//tr[" + index + "]" + labelNameXP);
	}

	public String getGameTypeElement(int index){
		return WebDriverUtils.getElementText("//tr[" + index + "]" + labelGameTypeXP);
	}

	public String getDealerName(int index){
		return WebDriverUtils.getElementText("//tr[" + index + "]" + labelDealerXP);
	}

	public int getNumberOfRows(){
		return WebDriverUtils.getXpathCount(buttonPlayXP);
	}

	public int getNumberOfCheckBoxes(){
		return WebDriverUtils.getXpathCount(checkBoxXP);
	}

	public void sortName(){
		WebDriverUtils.click(FIELD_SORT_NAME_XP);
	}

	public void sortGameType(){
		WebDriverUtils.click(FIELD_SORT_GAME_TYPE_XP);
	}

	public void sortDealerName(){
		WebDriverUtils.click(FIELD_SORT_DEALERS_NAME_XP);
	}

	public DealerImagePopup clickInfo(int index){
		WebDriverUtils.click("//tr[" + index + "]" + buttonInfoXP);
		return new DealerImagePopup();
	}

	public AbstractPageObject clickPlay(int index, boolean isLoggedIn){
		WebDriverUtils.click("//tr[" + index + "]" + buttonPlayXP);
		if(isLoggedIn){
			return new GameLaunchPopup(getMainWindowHandle());
		}else{
			return new LoginPopup();
		}
	}

	public void clickCheckbox(int index){
		WebDriverUtils.click("//*[@class='live-table-finder-form']//li[" + index + "]/label");
	}

	public boolean isCheckboxResultsVisible(int index){
		return WebDriverUtils.isVisible("//*[@class='game-type'][contains(text(), '" + getCheckboxLabelText(index) + "')]");
	}
}
