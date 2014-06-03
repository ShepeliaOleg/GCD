package pageObjects.gamesPortlet;

import enums.SortBy;
import pageObjects.account.LoginPopup;
import pageObjects.base.AbstractPage;
import pageObjects.base.AbstractPageObject;
import utils.RandomUtils;
import utils.WebDriverUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: sergiich
 * Date: 7/8/13
 */

public class GamesPortletPage extends AbstractPage{
	//General
	private static final String TAG_GAME_ID=							"value";
	private static final String TAG_GAME_NAME=							"data-name";
	private static final String TAG_JACKPOT=							"data-game-jackpot";
	private static final String TICKER_JACKPOT_XP = 					"//span[@class='game-jackpot']";
	private static final String GAMES_PORTLET_ROOT_XP=					"//div[@data-portlet = 'gamesinfo']";
	private static final String GAMES_XP=								"//*[@class='gm-item-wrap']";
	private static final String BEGINNING_GAMES_XP = 					"//ul[1]";
	private static final String TOGGLE_XP = 							"//li[contains(@class, 'toggle')]";
	private static final String CATEGORY_NAME_XP = 						"data-category";
	private static final String BUTTON_NEXT_XP= 						"//div[@class='next']/a";
	private static final String BUTTON_PREVIOUS_XP= 					"//div[@class='prev']/a";
	private static final String BUTTON_LIST_VIEW_XP=					"//a[@data-view='LIST']";
	private static final String BUTTON_ITEM_VIEW_XP=					"//a[@data-view='ITEM']";
	private static final String FIELD_SEARCH_XP = 						"//input[contains(@class, 'field-search')]";
	//Refine By
	private static final String CATEGORY_REFINE_BY_XP_BEGINNING = 		"//*[@class='filterbox']//a[@data-category = ";
	private static final String DROPD0WN_REFINE_BY_XP = 				"//*[contains(@class, 'games-filter-box')]//div";
	private static final String DROPDOWN_REFINE_BY_OPTION_FULL_XP = 	DROPD0WN_REFINE_BY_XP + "//a[contains(@class,'datalink')]";
	private static final String DROPDOWN_REFINE_BY_OPTION_PARTIAL_XP = 	"/a[contains(@class,'datalink')]";
	private static final String DROPDOWN_REFINE_BY_ACTIVE_CATEGORY_XP = DROPD0WN_REFINE_BY_XP + "//*[@class='active main-toggle']";
	//Category Tabs
	private static final String TAB_CATEGORY_XP= 						"//*[contains(@class,'nav')]//a[@data-category = ";
	private static final String TAB_ACTIVE_CATEGORY_XP= 				"//*[contains(@class,'nav')]//li[@class='active']/a[@data-category]";
	private static final String TAB_ACTIVE_SUBCATEGORY_XP= 				"//*[contains(@class,'subnav')]//li[@class='active']/a[@data-category]";
	private static final String MENU_TOP_XP = 							"//*[contains(@class,'tabs')]";
	private static final String MENU_LEFT_XP = 							"//*[contains(@class,'menu')]";
	private static final String MAIN_TABS_XP =  						"//*[@class='nav']";
	private static final String SUBCATEGORY_TABS_XP =  					"//*[@class='subnav']";
	private static final String MENU_CATEGORY_TABS_TOP_XP = 			MENU_TOP_XP + MAIN_TABS_XP;
	private static final String MENU_SUBCATEGORY_TABS_TOP_XP = 			MENU_TOP_XP + SUBCATEGORY_TABS_XP;
	private static final String MENU_CATEGORY_TABS_LEFT_XP = 			MENU_LEFT_XP + MAIN_TABS_XP;
	private static final String MENU_SUBCATEGORY_TABS_LEFT_XP = 		MENU_LEFT_XP + SUBCATEGORY_TABS_XP;
	//Games Portlet Categories
	public static final String CAT_NO_SUBCAT1_RELATIVE_URL = 			"/cat_no_subcat1";
	public static final String CAT_NO_SUBCAT2_RELATIVE_URL = 			"/cat_no_subcat2";
	public static final String SUBCAT_A_RELATIVE_URL = 					"/subcat_a";
	public static final String SUBCAT_B_RELATIVE_URL = 					"/subcat_b";
	public static final String SUBCAT_C_RELATIVE_URL = 					"/subcat_c";
	public static final String SUBCAT_D_RELATIVE_URL = 					"/subcat_d";
	public static final String CAT_SUBCAT1_RELATIVE_URL = 				"/cat_subcat1";
	public static final String CAT_WITH_SUBCAT1_SUBCAT_A_RELATIVE_URL = CAT_SUBCAT1_RELATIVE_URL + SUBCAT_A_RELATIVE_URL;
	public static final String CAT_WITH_SUBCAT1_SUBCAT_B_RELATIVE_URL = CAT_SUBCAT1_RELATIVE_URL + SUBCAT_B_RELATIVE_URL;
	public static final String CAT_WITH_SUBCAT1_SUBCAT_C_RELATIVE_URL = CAT_SUBCAT1_RELATIVE_URL + SUBCAT_C_RELATIVE_URL;
	public static final String CAT_SUBCAT2_RELATIVE_URL = 				"/cat_subcat2";
	public static final String CAT_WITH_SUBCAT2_SUBCAT_A_RELATIVE_URL = CAT_SUBCAT2_RELATIVE_URL + SUBCAT_A_RELATIVE_URL;
	public static final String CAT_WITH_SUBCAT2_SUBCAT_B_RELATIVE_URL = CAT_SUBCAT2_RELATIVE_URL + SUBCAT_B_RELATIVE_URL;
	public static final String CAT_WITH_SUBCAT2_SUBCAT_C_RELATIVE_URL = CAT_SUBCAT2_RELATIVE_URL + SUBCAT_C_RELATIVE_URL;
	public static final String CAT_WITH_SUBCAT2_SUBCAT_D_RELATIVE_URL = CAT_SUBCAT2_RELATIVE_URL + SUBCAT_D_RELATIVE_URL;
	//Sortby
	public static final String DROPDOWN_SORT_BY_XP=						"//*[contains(@class, 'games-select-box')]/div";
	public static final String ITEM_SORT_BY_NONE_XP=					"//a[@data-sortby='none']";
	public static final String ITEM_SORT_BY_POPULARITY_XP=				"//a[@data-sortby='popularity']";
	public static final String ITEM_SORT_BY_NEW_XP=						"//a[@data-sortby='new']";
	public static final String ITEM_SORT_BY_JACKPOTS=					"//a[@data-sortby='jackpotSize']";
	public static final String ITEM_SORT_BY_NAME_XP=					"//a[@data-sortby='name']";
	
	private static final int RETRIES=50;

	public GamesPortletPage(){
		super(new String[]{GAMES_PORTLET_ROOT_XP, GAMES_XP});
	}

	// General
	public ArrayList<String> getAllGameNames(){
		ArrayList<String> gameIDs=new ArrayList<String>(150);
		int xPathCount=WebDriverUtils.getXpathCount(BEGINNING_GAMES_XP + GAMES_XP);
		for(int i=1; i <= xPathCount; i++){
			gameIDs.add(getGameName(i));
		}
		return gameIDs;
	}

	public String getGameName(int index){
		return WebDriverUtils.getAttribute(GAMES_XP + "[" + index + "]", TAG_GAME_NAME);
	}

	public String getRandomGameName(){
		return RandomUtils.getRandomElementsFromList(getAllGameNames(), 1).get(0);
	}

	public ArrayList<String> getAllGameIDs(){
		ArrayList<String> gameIDs=new ArrayList<String>(150);
		int xPathCount=WebDriverUtils.getXpathCount(GAMES_XP);
		for(int i=1; i <= xPathCount; i++){
			gameIDs.add(getGameID(i));
		}
		return gameIDs;
	}

	public String getGameID(int index){
		return WebDriverUtils.getAttribute(GAMES_XP + "[" + index + "]//input[contains(@name,'Code')]", TAG_GAME_ID);
	}

	public String getRandomGameID(){
		return RandomUtils.getRandomElementsFromList(getAllGameIDs(), 1).get(0);
	}

	public boolean isGamePresent(String gameID){
		boolean isGamePresent=false;
		try{
			GameElement gameElement=new GameElement(gameID);
			isGamePresent=true;
		}catch(RuntimeException e){
			isGamePresent=false;
		}
		return isGamePresent;
	}

	public boolean correctGamesAreDisplayed(List<String> gamesToBeDisplayed, List<String> gamesDisplayed){
		return gamesToBeDisplayed.containsAll(gamesDisplayed);
	}

	public boolean allNavigationControlsAreHidden() {
		return (categoryMenuIsHidden() == true &&
				refineByDropDownIsPresent() == false);
	}

	public boolean currentURLIsCategoryURL(String categoryRelativeURL){
		String currentURL = WebDriverUtils.getCurrentUrl();
		return (currentURL.endsWith("#".concat(categoryRelativeURL)));
	}

	public boolean currentURLIsPageURL (String pageRelativeURL){
		String currentURL = WebDriverUtils.getCurrentUrl();
		return (currentURL.endsWith(pageRelativeURL));
	}

	public int getJackpotGamesCount (){
		int jackpotGamesCount = 0;
		if (WebDriverUtils.isVisible(TOGGLE_XP)){
			int slidesCount = WebDriverUtils.getXpathCount(TOGGLE_XP);
			for (int i = 0; i < slidesCount; i++ ){
				jackpotGamesCount = jackpotGamesCount + WebDriverUtils.getXpathCount(TICKER_JACKPOT_XP);
				clickNextButton();
			}

		} else {
			jackpotGamesCount =  WebDriverUtils.getXpathCount(TICKER_JACKPOT_XP);
		}
		return jackpotGamesCount;
	}

	public int getGameItemsNumberOnSlide(){
		return WebDriverUtils.getXpathCount(GAMES_XP);

	}

	public int getTotalGameItemsNumber () {
		int total =0;
		if (WebDriverUtils.isVisible(TOGGLE_XP)){
			int slidesCount = WebDriverUtils.getXpathCount(TOGGLE_XP);
			for (int i = 0; i < slidesCount; i++ ){
				total = total + WebDriverUtils.getXpathCount(GAMES_XP);
				clickNextButton();
			}

		} else {
			total = WebDriverUtils.getXpathCount(GAMES_XP);
		}
		return total;
	}

	// Navigation controls
	public GamesPortletPage clickNextButton(){
		WebDriverUtils.click(BUTTON_NEXT_XP);
		WebDriverUtils.waitFor();
		return new GamesPortletPage();
	}

	public GamesPortletPage clickPreviousButton(){
		WebDriverUtils.click(BUTTON_PREVIOUS_XP);
		WebDriverUtils.waitFor();
		return new GamesPortletPage();
	}

	public GamesPortletPage inputSearch(String gameId){
		WebDriverUtils.clearAndInputTextToField(FIELD_SEARCH_XP, gameId);
		return new GamesPortletPage();
	}

	public GamesPortletPage clickListView(){
		WebDriverUtils.click(BUTTON_LIST_VIEW_XP);
		return new GamesPortletPage();
	}

	public GamesPortletPage clickItemView(){
		WebDriverUtils.click(BUTTON_ITEM_VIEW_XP);
		return new GamesPortletPage();
	}

	public GamesPortletPage sortBy(SortBy sortBy){
		switch(sortBy){
			case None:
				clickDropDownOption(DROPDOWN_SORT_BY_XP, ITEM_SORT_BY_NONE_XP);
				break;
			case New:
				clickDropDownOption(DROPDOWN_SORT_BY_XP, ITEM_SORT_BY_NEW_XP);
				break;
			case Jackpot:
				clickDropDownOption(DROPDOWN_SORT_BY_XP, ITEM_SORT_BY_JACKPOTS);
				break;
			case Alphabetical:
				clickDropDownOption(DROPDOWN_SORT_BY_XP, ITEM_SORT_BY_NAME_XP);
				break;
			case MostPopular:
				clickDropDownOption(DROPDOWN_SORT_BY_XP, ITEM_SORT_BY_POPULARITY_XP);
				break;
		}
		return new GamesPortletPage();
	}

	//Game item controls
	public GameLaunchPopup playDemo(){
		ArrayList<String> checkedGames=new ArrayList<String>();
		int gameCount = WebDriverUtils.getXpathCount(GAMES_XP);
		for(int i=1; i <= gameCount; i++){
			String gameId= getGameName(i);
			if(checkedGames.isEmpty() || (!checkedGames.isEmpty() && !checkedGames.contains(gameId))){
				GameElement gameElement=new GameElement(gameId);
				if(gameElement.isDemoPresent()){
					gameElement.clickPlayDemo();
					break;
				}else{
					checkedGames.add(gameId);
				}
			}
			if(i == RETRIES){
				WebDriverUtils.runtimeExceptionWithLogs("No demo game buttons found");
			}
		}
		return new GameLaunchPopup(getMainWindowHandle());
	}

	public AbstractPageObject playReal(boolean isLoggedIn){
		AbstractPageObject result=null;
		ArrayList<String> checkedGames=new ArrayList<String>();
		for(int i=0; i <= RETRIES; i++){
			String gameId= getRandomGameName();
			if(checkedGames.isEmpty() || (!checkedGames.isEmpty() && !checkedGames.contains(gameId))){
				GameElement gameElement=new GameElement(gameId);
				if(gameElement.isRealPresent()){
					gameElement.clickPlayReal();
					break;
				}else{
					checkedGames.add(gameId);
				}
			}
			if(i == RETRIES){
				WebDriverUtils.runtimeExceptionWithLogs("No real game buttons found");
			}
		}
		if(isLoggedIn){
			result=new GameLaunchPopup(getMainWindowHandle());
		}else{
			result=new LoginPopup();
		}
		return result;
	}

	public AbstractPageObject playRealFromTitle(boolean isLoggedIn){
		AbstractPageObject result=null;
		ArrayList<String> checkedGames=new ArrayList<String>();
		int gameCount = WebDriverUtils.getXpathCount(GAMES_XP);
		for(int i=1; i <= gameCount; i++){
			String gameId = getGameName(i);
			if(checkedGames.isEmpty() || (!checkedGames.isEmpty() && !checkedGames.contains(gameId))){
				GameElement gameElement=new GameElement(gameId);
				if(gameElement.isTitlePresent()){
					gameElement.clickTitle();
					break;
				}else{
					checkedGames.add(gameId);
				}
			}
			if(i == RETRIES){
				WebDriverUtils.runtimeExceptionWithLogs("No real game buttons found and couldn't click image");
			}
		}
		if(isLoggedIn){
			result=new GameLaunchPopup(getMainWindowHandle());
		}else{
			result=new LoginPopup();
		}
		return result;
	}

	public GameLaunchPopup playReal(int index){
		String gameId= getGameName(index);
		GameElement gameElement=new GameElement(gameId);
		if(gameElement.isRealPresent()){
			gameElement.clickPlayReal();
		}else{
			WebDriverUtils.runtimeExceptionWithLogs("No real game button found");
		}
		return new GameLaunchPopup(getMainWindowHandle());
	}

	public GameInfoPopup clickInfo(){
		ArrayList<String> checkedGames=null;
		String gameId=null;
		int gameCount = WebDriverUtils.getXpathCount(GAMES_XP);
		for(int i=1; i <= gameCount; i++){
			gameId = getGameName(i);
			if(checkedGames == null || (checkedGames != null && !checkedGames.contains(gameId))){
				GameElement gameElement=new GameElement(gameId);
				if(gameElement.isInfoPresent()){
					gameElement.clickInfo();
					break;
				}else{
					checkedGames.add(gameId);
				}
			}
			if(i == RETRIES){
				WebDriverUtils.runtimeExceptionWithLogs("No Info buttons found");
			}
		}
		return new GameInfoPopup(gameId);
	}

	public boolean isDemoPresent(){
		boolean isDemoPresent=false;
		ArrayList<String> allGameIDs= getAllGameNames();
		for(int i=0; i < allGameIDs.size(); i++){
			String gameId=allGameIDs.get(i);
			GameElement gameElement=new GameElement(gameId);
			if(gameElement.isDemoPresent()){
				isDemoPresent=true;
				break;
			}
		}
		return isDemoPresent;
	}

	// Category tabs
	public GamesPortletPage clickCategoryTab (String categoryURL){
		WebDriverUtils.click(getCategoryXpath(categoryURL));
		return new GamesPortletPage();
	}

	private String getCategoryXpath (String categoryURL) {
		return TAB_CATEGORY_XP +"'" + categoryURL + "']";
	}

	public boolean isCategoryTabPresent(String categoryURL){
		return WebDriverUtils.isVisible(getCategoryXpath(categoryURL));
	}

	public boolean topCategoryMenuIsPresent(){
		return WebDriverUtils.isVisible(MENU_CATEGORY_TABS_TOP_XP, 0);
	}

	public boolean topSubcategoryMenuIsPresent() {
		return WebDriverUtils.isVisible(MENU_SUBCATEGORY_TABS_TOP_XP, 0);
	}

	public boolean leftCategoryMenuIsPresent(){
		return WebDriverUtils.isVisible(MENU_CATEGORY_TABS_LEFT_XP, 0);
	}

	public boolean leftSubcategoryMenuIsPresent() {
		return WebDriverUtils.isVisible(MENU_SUBCATEGORY_TABS_LEFT_XP, 0);
	}

	public boolean categoryMenuIsHidden() {
		return (topCategoryMenuIsPresent()== false &&
				topSubcategoryMenuIsPresent() == false &&
				leftCategoryMenuIsPresent() == false &&
				leftSubcategoryMenuIsPresent() == false);
	}

	public boolean categoriesMenuIncludesTopCategories () {
		return (isCategoryTabPresent(CAT_NO_SUBCAT1_RELATIVE_URL)&&
				isCategoryTabPresent(CAT_NO_SUBCAT2_RELATIVE_URL)&&
				isCategoryTabPresent(CAT_SUBCAT1_RELATIVE_URL)&&
				isCategoryTabPresent(CAT_SUBCAT2_RELATIVE_URL));
	}

	public boolean childCategoriesForCatSubcat1DisplayedInCategoryTabsMenu (){
		return (isCategoryTabPresent(CAT_WITH_SUBCAT1_SUBCAT_A_RELATIVE_URL)&&
				isCategoryTabPresent(CAT_WITH_SUBCAT1_SUBCAT_B_RELATIVE_URL)&&
				isCategoryTabPresent(CAT_WITH_SUBCAT1_SUBCAT_C_RELATIVE_URL));
	}

	public boolean childCategoriesForCatSubcat2DisplayedInCategoryTabsMenu (){
		return (isCategoryTabPresent(CAT_WITH_SUBCAT2_SUBCAT_A_RELATIVE_URL)&&
				isCategoryTabPresent(CAT_WITH_SUBCAT2_SUBCAT_B_RELATIVE_URL)&&
				isCategoryTabPresent(CAT_WITH_SUBCAT2_SUBCAT_C_RELATIVE_URL)&&
				isCategoryTabPresent(CAT_WITH_SUBCAT2_SUBCAT_D_RELATIVE_URL));
	}

	public boolean isActiveCategoryTabPresent (){
		return (WebDriverUtils.getXpathCount(TAB_ACTIVE_CATEGORY_XP) > 0);
	}

	public boolean isActiveSubcategoryTabPresent (){
		return (WebDriverUtils.getXpathCount(TAB_ACTIVE_SUBCATEGORY_XP) > 0);
	}

	public String getActiveCategoryName (){
		return WebDriverUtils.getElementText(TAB_ACTIVE_CATEGORY_XP);

	}

	public String getActiveCategoryTabXp () {
		return WebDriverUtils.getAttribute(TAB_ACTIVE_CATEGORY_XP, CATEGORY_NAME_XP);
	}

	public String getActiveSubcategoryName (){
		return WebDriverUtils.getElementText(TAB_ACTIVE_SUBCATEGORY_XP);

	}

	public String getActiveSubcategoryTabXp () {
		return WebDriverUtils.getAttribute(TAB_ACTIVE_SUBCATEGORY_XP, CATEGORY_NAME_XP);
	}


	// Refine By
	public GamesPortletPage refineBy (String categoryURL){
		clickDropDownOption(DROPD0WN_REFINE_BY_XP, getRefineByCategoryXpath(categoryURL));
		return new GamesPortletPage();
	}

	private String getRefineByCategoryXpath (String categoryURL) {
		return CATEGORY_REFINE_BY_XP_BEGINNING +"'" + categoryURL + "']";
	}

	public boolean isCategoryItemPresentInRefineBy  (String categoryURL){
		return  WebDriverUtils.isVisible(getRefineByCategoryXpath(categoryURL));
	}

	private void clickDropDownOption(String dropdownRefineBy, String categoryXpath){
		WebDriverUtils.waitForElement(dropdownRefineBy);
		if (!WebDriverUtils.isVisible(categoryXpath, 0)){
			WebDriverUtils.click(dropdownRefineBy);
			WebDriverUtils.waitForElement(categoryXpath);
		}
		WebDriverUtils.click(categoryXpath);
		WebDriverUtils.waitForElement(GAMES_XP);
	}

	public void clickRefineByOptionByText (String text){
		clickDropDownOption(DROPD0WN_REFINE_BY_XP, DROPDOWN_REFINE_BY_OPTION_FULL_XP + "[text()='" + text + "']");
//        WebDriverUtils.waitForElement(GAMES_XP);
	}

	public boolean refineByDropDownIsPresent(){
		return WebDriverUtils.isVisible(DROPD0WN_REFINE_BY_XP, 0);
	}

	public boolean isRefineByOptionPresent (String refineByOption){
		return isRefineByOptionPresent (getAllRefineByOptions(),refineByOption);
	}

	public boolean isRefineByOptionPresent (List<String> refineByOptions, String refineByOption){
		return refineByOptions.contains(refineByOption);
	}

	public List<String> getAllRefineByOptions () {
		ArrayList<String> refineByOptions=new ArrayList<String>();
		int xPathCount=WebDriverUtils.getXpathCount(DROPDOWN_REFINE_BY_OPTION_FULL_XP);
		for(int i=1; i <= xPathCount; i++){
			refineByOptions.add(WebDriverUtils.getAttribute(DROPD0WN_REFINE_BY_XP + "//li" + "[" + i + "]" + DROPDOWN_REFINE_BY_OPTION_PARTIAL_XP, CATEGORY_NAME_XP));
		}
		return refineByOptions;
	}

	public boolean isRefineByTextOptionPresent (String refineByOptionText){
		return getAllRefineByOptionsTexts().contains(refineByOptionText);
	}

	public List<String> getAllRefineByOptionsTexts () {
		ArrayList<String> refineByOptions=new ArrayList<String>();
		int xPathCount=WebDriverUtils.getXpathCount(DROPDOWN_REFINE_BY_OPTION_FULL_XP);
		for(int i=1; i <= xPathCount; i++){
			boolean refineByVisible =  WebDriverUtils.isVisible(DROPD0WN_REFINE_BY_XP + "//*[@class='filterbox']//li", 0);
			if (!refineByVisible) {
				clickDropDownOption(DROPD0WN_REFINE_BY_XP, DROPDOWN_REFINE_BY_ACTIVE_CATEGORY_XP);
			}
			refineByOptions.add(WebDriverUtils.getElementText(DROPD0WN_REFINE_BY_XP + "//ul[@class='filterbox']/li" + "[" + i + "]" + "/a"));
		}
		return refineByOptions;
	}


	public String getActiveRefineByOptionText () {
		return WebDriverUtils.getElementText(DROPDOWN_REFINE_BY_ACTIVE_CATEGORY_XP);
	}

	public String getActiveRefineByOption(){
		return WebDriverUtils.getAttribute(DROPDOWN_REFINE_BY_OPTION_FULL_XP + "[text() = '" + getActiveRefineByOptionText() + "']", CATEGORY_NAME_XP);
	}

	public boolean refineByOptionsIncludeTopCategories () {
		List<String> refineByOptions = getAllRefineByOptions();
		return (isRefineByOptionPresent(refineByOptions, CAT_NO_SUBCAT1_RELATIVE_URL)&&
				isRefineByOptionPresent(refineByOptions, CAT_NO_SUBCAT2_RELATIVE_URL)&&
				isRefineByOptionPresent(refineByOptions, CAT_SUBCAT1_RELATIVE_URL)&&
				isRefineByOptionPresent(refineByOptions, CAT_SUBCAT2_RELATIVE_URL));
	}

	public boolean childCategoriesForCatSubcat1DisplayedInRefineBy (){
		List<String> refineByOptions = getAllRefineByOptions();
		return (isRefineByOptionPresent(refineByOptions, CAT_WITH_SUBCAT1_SUBCAT_A_RELATIVE_URL)&&
				isRefineByOptionPresent(refineByOptions, CAT_WITH_SUBCAT1_SUBCAT_B_RELATIVE_URL)&&
				isRefineByOptionPresent(refineByOptions, CAT_WITH_SUBCAT1_SUBCAT_C_RELATIVE_URL));
	}

	public boolean childCategoriesForCatSubcat2DisplayedInRefineBy (){
		List<String> refineByOptions = getAllRefineByOptions();
		return (isRefineByOptionPresent(refineByOptions, CAT_WITH_SUBCAT2_SUBCAT_A_RELATIVE_URL)&&
				isRefineByOptionPresent(refineByOptions, CAT_WITH_SUBCAT2_SUBCAT_B_RELATIVE_URL)&&
				isRefineByOptionPresent(refineByOptions, CAT_WITH_SUBCAT2_SUBCAT_C_RELATIVE_URL)&&
				isRefineByOptionPresent(refineByOptions, CAT_WITH_SUBCAT2_SUBCAT_D_RELATIVE_URL));
	}

}
