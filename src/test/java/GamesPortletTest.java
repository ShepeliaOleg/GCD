import enums.ConfiguredPages;
import enums.PlayerCondition;
import enums.SortBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.account.LoginPopup;
import pageObjects.gamesPortlet.GameElement;
import pageObjects.gamesPortlet.GameInfoPopup;
import pageObjects.gamesPortlet.GameLaunchPopup;
import pageObjects.gamesPortlet.GamesPortletPage;
import springConstructors.GameCategories;
import springConstructors.GameControlLabels;
import springConstructors.UserData;
import springConstructors.validation.ValidationRule;
import testUtils.AbstractTest;
import utils.NavigationUtils;
import utils.RandomUtils;
import utils.WebDriverUtils;

import java.util.ArrayList;

/**
 * User: sergiich
 * Date: 4/10/14
 */
public class GamesPortletTest extends AbstractTest{

	@Autowired
	@Qualifier("userData")
	private UserData defaultUserData;

	@Autowired
	@Qualifier("searchValidationRule")
	private ValidationRule searchValidationRule;

	@Autowired
	@Qualifier("gameCategories")
	private GameCategories gameCategories;

	@Autowired
	@Qualifier("gameControlLabels")
	private GameControlLabels gameControlLabels;

	/*1. Portlet is displayed, game can be launched */
	@Test(groups = {"smoke"})
	public void startFirstAvailableGameInGamePortlet(){
		GamesPortletPage gamesPortletPage = (GamesPortletPage) NavigationUtils.navigateToPage(ConfiguredPages.gamesCasinoPage);
		GameLaunchPopup gameLaunchPopup = gamesPortletPage.playDemo();
		boolean correctGamePopupUrl = gameLaunchPopup.isUrlValid();
		String url = gameLaunchPopup.getWindowUrl();
		gameLaunchPopup.closePopup();
		if (correctGamePopupUrl == false) {
			WebDriverUtils.runtimeExceptionWithLogs("Game url is not correct = " + url);
		}
	}

	/*2.1. Refine By: Top level*/
	@Test(groups = {"regression"})
	public void refineByNavigationStyleTopLevel (){
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);
		boolean refineByDropDownIsDisplayed = gamesPortletPage.refineByDropDownIsPresent();
		boolean categoryTabsAreHidden = gamesPortletPage.categoryMenuIsHidden();
		boolean refineByOptionsIncludeTopCategories =  gamesPortletPage.refineByOptionsIncludeTopCategories();
		boolean refineByOptionsIncludeSubcategories =
				(gamesPortletPage.childCategoriesForCatSubcat1DisplayedInRefineBy()&&
						gamesPortletPage.childCategoriesForCatSubcat2DisplayedInRefineBy());
		boolean noCategoryIsSelectedInRefineBy = gamesPortletPage.getActiveRefineByOptionText().equals(gameControlLabels.getRefineByLabel());
		boolean correctGamesAreDisplayed = gamesPortletPage.correctGamesAreDisplayed(gameCategories.getAllGames(), gamesPortletPage.getAllGameIDs());
		boolean resetFilterOptionIsPresent = gamesPortletPage.isRefineByTextOptionPresent(gameControlLabels.getResetFilterLabel());
		boolean pageURLIsCorrect = gamesPortletPage.currentURLIsPageURL(ConfiguredPages.gamesNavigationStyleRefineBy.toString());
		if (refineByDropDownIsDisplayed == true &&
				categoryTabsAreHidden == true&&
				refineByOptionsIncludeTopCategories == true&&
				refineByOptionsIncludeSubcategories == false&&
				noCategoryIsSelectedInRefineBy == true&&
				correctGamesAreDisplayed == true &&
				resetFilterOptionIsPresent == false &&
				pageURLIsCorrect ==true) {
		} else {
			WebDriverUtils.runtimeExceptionWithLogs(
					"refineByDropDownIsDisplayed = " + refineByDropDownIsDisplayed + " ER: true;" +
							"\n categoryTabsAreHidden = " + categoryTabsAreHidden + " ER: true;" +
							"\n refineByOptionsIncludeTopCategories = " + refineByOptionsIncludeTopCategories + " ER: true;" +
							"\n refineByOptionsIncludeSubcategories = " + refineByOptionsIncludeSubcategories + " ER: false;" +
							"\n noCategoryIsSelectedInRefineBy = " + noCategoryIsSelectedInRefineBy + " ER: true;" +
							"\n correctGamesAreDisplayed = " + correctGamesAreDisplayed + " ER: true;" +
							"\n resetFilterOptionIsPresent = " + resetFilterOptionIsPresent + " ER: false;" +
							"\n pageURLIsCorrect = " + pageURLIsCorrect + " ER: true.");
		}
	}

	/*2.2. Refine By: Top > Category without subcategories*/
	@Test(groups = {"regression"})
	public void refineByNavigationFromTopToCategoryWithNoSubcategories () {
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);
		gamesPortletPage.refineBy(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
		boolean refineByDropDownIsDisplayed = gamesPortletPage.refineByDropDownIsPresent();
		boolean categoryTabsAreHidden = gamesPortletPage.categoryMenuIsHidden();
		boolean refineByOptionsIncludeTopCategories =  gamesPortletPage.refineByOptionsIncludeTopCategories();
		boolean refineByOptionsIncludeSubcategories =
				(gamesPortletPage.childCategoriesForCatSubcat1DisplayedInRefineBy()&&
						gamesPortletPage.childCategoriesForCatSubcat2DisplayedInRefineBy());
		boolean selectedCategoryIsDisplayedInRefineBy = gamesPortletPage.getActiveRefineByOption().equals(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
		ArrayList<String> gamesDisplayed = gamesPortletPage.getAllGameIDs();
		boolean correctGamesAreDisplayed =  gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat1(),gamesDisplayed);
		boolean invalidGamesAreDisplayed =
				(gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat2(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat1(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2(),gamesDisplayed));
		boolean resetFilterOptionIsPresent = gamesPortletPage.isRefineByTextOptionPresent(gameControlLabels.getResetFilterLabel());
		boolean pageURLContainsCategoryPath = gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
		if (refineByDropDownIsDisplayed == true&&
				categoryTabsAreHidden == true&&
				refineByOptionsIncludeTopCategories == true&&
				refineByOptionsIncludeSubcategories == false&&
				selectedCategoryIsDisplayedInRefineBy == true&&
				correctGamesAreDisplayed == true&&
				invalidGamesAreDisplayed == false&&
				resetFilterOptionIsPresent == true &&
				pageURLContainsCategoryPath == true){
		}else {
			WebDriverUtils.runtimeExceptionWithLogs(
					"refineByDropDownIsDisplayed = " + refineByDropDownIsDisplayed + " ER: true;" +
							"\n categoryTabsAreHidden = " + categoryTabsAreHidden + " ER: true;" +
							"\n refineByOptionsIncludeTopCategories = " + refineByOptionsIncludeTopCategories + " ER: true;" +
							"\n refineByOptionsIncludeSubcategories = " + refineByOptionsIncludeSubcategories + " ER: false;" +
							"\n selectedCategoryIsDisplayedInRefineBy = " + selectedCategoryIsDisplayedInRefineBy + " ER: true;" +
							"\n correctGamesAreDisplayed = " + correctGamesAreDisplayed + " ER: true;" +
							"\n invalidGamesAreDisplayed = " + invalidGamesAreDisplayed + " ER: false;" +
							"\n resetFilterOptionIsPresent = " + resetFilterOptionIsPresent + " ER: true;" +
							"\n pageURLContainsCategoryPath = " + pageURLContainsCategoryPath + " ER: true;");
		}
	}

	/*2.3. Refine By: Top > Category with subcategories*/
	@Test(groups = {"regression"})
	public void refineByNavigationFromTopToCategoryWithSubcategories () {
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);
		gamesPortletPage.refineBy(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		boolean refineByDropDownIsDisplayed = gamesPortletPage.refineByDropDownIsPresent();
		boolean categoryTabsAreHidden = gamesPortletPage.categoryMenuIsHidden();
		boolean refineByOptionsIncludeTopCategories =  gamesPortletPage.refineByOptionsIncludeTopCategories();
		boolean refineByOptionsIncludeCorrectSubcategories = gamesPortletPage.childCategoriesForCatSubcat1DisplayedInRefineBy();
		boolean refineByOptionsIncludeIncorrectSubcategories = gamesPortletPage.childCategoriesForCatSubcat2DisplayedInRefineBy();
		boolean noCategoryIsSelectedInRefineBy = gamesPortletPage.getActiveRefineByOptionText().equals(gameControlLabels.getRefineByLabel());
		ArrayList <String> gamesDisplayed = gamesPortletPage.getAllGameIDs();
		boolean correctGamesAreDisplayed =  gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat1(),gamesDisplayed);
		boolean invalidGamesAreDisplayed =
				(gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat1(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat2(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2(),gamesDisplayed));
		boolean resetFilterOptionIsPresent = gamesPortletPage.isRefineByTextOptionPresent(gameControlLabels.getResetFilterLabel());
		boolean pageURLContainsCategoryPath = gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		if(refineByDropDownIsDisplayed == true&&
				categoryTabsAreHidden == true&&
				refineByOptionsIncludeTopCategories == false&&
				refineByOptionsIncludeCorrectSubcategories ==true &&
				refineByOptionsIncludeIncorrectSubcategories == false&&
				noCategoryIsSelectedInRefineBy == true&&
				correctGamesAreDisplayed == true&&
				invalidGamesAreDisplayed == false&&
				resetFilterOptionIsPresent == true&&
				pageURLContainsCategoryPath == true){
		}else{
			WebDriverUtils.runtimeExceptionWithLogs(
					"refineByDropDownIsDisplayed = " + refineByDropDownIsDisplayed + " ER: true;" +
							"\n categoryTabsAreHidden = " + categoryTabsAreHidden + " ER: true;" +
							"\n refineByOptionsIncludeTopCategories = " + refineByOptionsIncludeTopCategories + " ER: false;" +
							"\n refineByOptionsIncludeCorrectSubcategories = " + refineByOptionsIncludeCorrectSubcategories + " ER: true;" +
							"\n refineByOptionsIncludeIncorrectSubcategories = " + refineByOptionsIncludeIncorrectSubcategories + " ER: false;" +
							"\n noCategoryIsSelectedInRefineBy = " + noCategoryIsSelectedInRefineBy + " ER: true;" +
							"\n correctGamesAreDisplayed = " + correctGamesAreDisplayed + " ER: true;" +
							"\n invalidGamesAreDisplayed = " + invalidGamesAreDisplayed + " ER: false;" +
							"\n resetFilterOptionIsPresent = " + resetFilterOptionIsPresent + " ER: true;" +
							"\n pageURLContainsCategoryPath = " + pageURLContainsCategoryPath + " ER: true.");
		}

	}

	/*2.4. Refine By: Category with subcategories > subcategory*/
	@Test(groups = {"regression"})
	public void refineByNavigationFromCategoryWithSubcategoriesToSubcategory () {
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);
		gamesPortletPage.refineBy(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		gamesPortletPage.refineBy(gamesPortletPage.CAT_WITH_SUBCAT1_SUBCAT_A_RELATIVE_URL);
		boolean refineByDropDownIsDisplayed = gamesPortletPage.refineByDropDownIsPresent();
		boolean categoryTabsAreHidden = gamesPortletPage.categoryMenuIsHidden();
		boolean refineByOptionsIncludeTopCategories =  gamesPortletPage.refineByOptionsIncludeTopCategories();
		boolean refineByOptionsIncludeCorrectSubcategories = gamesPortletPage.childCategoriesForCatSubcat1DisplayedInRefineBy();
		boolean refineByOptionsIncludeIncorrectSubcategories = gamesPortletPage.childCategoriesForCatSubcat2DisplayedInRefineBy();
		boolean selectedCategoryIsDisplayedInRefineBy = gamesPortletPage.getActiveRefineByOption().equals(gamesPortletPage.CAT_WITH_SUBCAT1_SUBCAT_A_RELATIVE_URL);
		ArrayList <String> gamesDisplayed = gamesPortletPage.getAllGameIDs();
		boolean correctGamesAreDisplayed =  gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat1_subcatA(),gamesDisplayed);
		boolean invalidGamesAreDisplayed =
				(gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat1(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat2(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2(),gamesDisplayed))&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat1_subcatB(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat1_subcatC(),gamesDisplayed);
		boolean resetFilterOptionIsPresent = gamesPortletPage.isRefineByTextOptionPresent(gameControlLabels.getResetFilterLabel());
		boolean pageURLContainsCategoryPath = gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_WITH_SUBCAT1_SUBCAT_A_RELATIVE_URL);
		if (refineByDropDownIsDisplayed == true&&
				categoryTabsAreHidden == true&&
				refineByOptionsIncludeTopCategories == false&&
				refineByOptionsIncludeCorrectSubcategories ==true &&
				refineByOptionsIncludeIncorrectSubcategories == false&&
				selectedCategoryIsDisplayedInRefineBy == true&&
				correctGamesAreDisplayed == true&&
				invalidGamesAreDisplayed == false&&
				resetFilterOptionIsPresent == true &&
				pageURLContainsCategoryPath == true){
		}else {
			WebDriverUtils.runtimeExceptionWithLogs(
					"refineByDropDownIsDisplayed = " + refineByDropDownIsDisplayed + " ER: true;" +
							"\n categoryTabsAreHidden = " + categoryTabsAreHidden + " ER: true;" +
							"\n refineByOptionsIncludeTopCategories = " + refineByOptionsIncludeTopCategories + " ER: false;" +
							"\n refineByOptionsIncludeCorrectSubcategories = " + refineByOptionsIncludeCorrectSubcategories + " ER: true;" +
							"\n refineByOptionsIncludeIncorrectSubcategories = " + refineByOptionsIncludeIncorrectSubcategories + " ER: false" +
							"\n selectedCategoryIsDisplayedInRefineBy = " + selectedCategoryIsDisplayedInRefineBy + " ER: true;" +
							"\n correctGamesAreDisplayed = " + correctGamesAreDisplayed + " ER: true;" +
							"\n invalidGamesAreDisplayed = " + invalidGamesAreDisplayed + " ER: false;" +
							"\n resetFilterOptionIsPresent = " + resetFilterOptionIsPresent + " ER: true;" +
							"\n pageURLContainsCategoryPath = " + pageURLContainsCategoryPath + " ER: true;");
		}
	}

	/*2.5.  Refine By: Subcategory 1 > Subcategory 2*/
	@Test(groups = {"regression"})
	public void refineByNavigationFromSubcategoryToSubcategory (){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);;
		gamesPortletPage.refineBy(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		gamesPortletPage.refineBy(gamesPortletPage.CAT_WITH_SUBCAT1_SUBCAT_A_RELATIVE_URL);
		gamesPortletPage.refineBy(gamesPortletPage.CAT_WITH_SUBCAT1_SUBCAT_B_RELATIVE_URL);
		boolean refineByDropDownIsDisplayed = gamesPortletPage.refineByDropDownIsPresent();
		boolean categoryTabsAreHidden = gamesPortletPage.categoryMenuIsHidden();
		boolean refineByOptionsIncludeTopCategories =  gamesPortletPage.refineByOptionsIncludeTopCategories();
		boolean refineByOptionsIncludeCorrectSubcategories = gamesPortletPage.childCategoriesForCatSubcat1DisplayedInRefineBy();
		boolean refineByOptionsIncludeIncorrectSubcategories = gamesPortletPage.childCategoriesForCatSubcat2DisplayedInRefineBy();
		boolean selectedCategoryIsDisplayedInRefineBy = gamesPortletPage.getActiveRefineByOption().equals(gamesPortletPage.CAT_WITH_SUBCAT1_SUBCAT_B_RELATIVE_URL);
		ArrayList <String> gamesDisplayed = gamesPortletPage.getAllGameIDs();
		boolean correctGamesAreDisplayed =  gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat1_subcatB(),gamesDisplayed);
		boolean invalidGamesAreDisplayed =
				(gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat1(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat2(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2(),gamesDisplayed))&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat1_subcatA(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat1_subcatC(),gamesDisplayed);
		boolean resetFilterOptionIsPresent = gamesPortletPage.isRefineByTextOptionPresent(gameControlLabels.getResetFilterLabel());
		boolean pageURLContainsCategoryPath = gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_WITH_SUBCAT1_SUBCAT_B_RELATIVE_URL);
		if (refineByDropDownIsDisplayed == true&&
				categoryTabsAreHidden == true&&
				refineByOptionsIncludeTopCategories == false&&
				refineByOptionsIncludeCorrectSubcategories ==true &&
				refineByOptionsIncludeIncorrectSubcategories == false&&
				selectedCategoryIsDisplayedInRefineBy == true&&
				correctGamesAreDisplayed == true&&
				invalidGamesAreDisplayed == false&&
				resetFilterOptionIsPresent == true &&
				pageURLContainsCategoryPath == true) {
		}  else {
			WebDriverUtils.runtimeExceptionWithLogs(
					"refineByDropDownIsDisplayed = " + refineByDropDownIsDisplayed + " ER: true;" +
							"\n categoryTabsAreHidden = " + categoryTabsAreHidden + " ER: true;" +
							"\n refineByOptionsIncludeTopCategories = " + refineByOptionsIncludeTopCategories + " ER: false;" +
							"\n refineByOptionsIncludeCorrectSubcategories = " + refineByOptionsIncludeCorrectSubcategories + " ER: true;" +
							"\n refineByOptionsIncludeIncorrectSubcategories = " + refineByOptionsIncludeIncorrectSubcategories + " ER: false;" +
							"\n selectedCategoryIsDisplayedInRefineBy = " + selectedCategoryIsDisplayedInRefineBy + " ER: true;" +
							"\n correctGamesAreDisplayed = " + correctGamesAreDisplayed + " ER: true;" +
							"\n invalidGamesAreDisplayed = " + invalidGamesAreDisplayed + " ER: false;" +
							"\n resetFilterOptionIsPresent = " + resetFilterOptionIsPresent + " ER: true;" +
							"\n pageURLContainsCategoryPath = " + pageURLContainsCategoryPath + " ER: true;");
		}
	}

	/*2.6. Refine By: Category without subcategories 1 > Category without subcategories 2*/
	@Test(groups = {"regression"})
	public void refineByNavigationFromCategoryWithoutSubcategoriesToCategoryWithoutSubcategories () {
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);
		gamesPortletPage.refineBy(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
		gamesPortletPage.refineBy(gamesPortletPage.CAT_NO_SUBCAT2_RELATIVE_URL);
		boolean refineByDropDownIsDisplayed = gamesPortletPage.refineByDropDownIsPresent();
		boolean categoryTabsAreHidden = gamesPortletPage.categoryMenuIsHidden();
		boolean refineByOptionsIncludeTopCategories =  gamesPortletPage.refineByOptionsIncludeTopCategories();
		boolean refineByOptionsIncludeSubcategories =
				(gamesPortletPage.childCategoriesForCatSubcat1DisplayedInRefineBy()&&
						gamesPortletPage.childCategoriesForCatSubcat2DisplayedInRefineBy());
		boolean selectedCategoryIsDisplayedInRefineBy = gamesPortletPage.getActiveRefineByOption().equals(gamesPortletPage.CAT_NO_SUBCAT2_RELATIVE_URL);
		ArrayList <String> gamesDisplayed = gamesPortletPage.getAllGameIDs();
		boolean correctGamesAreDisplayed =  gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat2(),gamesDisplayed);
		boolean invalidGamesAreDisplayed =
				(gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat1(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat1(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2(),gamesDisplayed));
		boolean resetFilterOptionIsPresent = gamesPortletPage.isRefineByTextOptionPresent(gameControlLabels.getResetFilterLabel());
		boolean pageURLContainsCategoryPath = gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_NO_SUBCAT2_RELATIVE_URL);
		if (refineByDropDownIsDisplayed == true&&
				categoryTabsAreHidden == true&&
				refineByOptionsIncludeTopCategories == true&&
				refineByOptionsIncludeSubcategories == false&&
				selectedCategoryIsDisplayedInRefineBy == true&&
				correctGamesAreDisplayed == true&&
				invalidGamesAreDisplayed == false&&
				resetFilterOptionIsPresent == true&&
				pageURLContainsCategoryPath == true) {
		} else {
			WebDriverUtils.runtimeExceptionWithLogs(
					"refineByDropDownIsDisplayed = " + refineByDropDownIsDisplayed + " ER: true;" +
							"\n categoryTabsAreHidden = " + categoryTabsAreHidden + " ER: true;" +
							"\n refineByOptionsIncludeTopCategories = " + refineByOptionsIncludeTopCategories + " ER: true;" +
							"\n refineByOptionsIncludeSubcategories = " + refineByOptionsIncludeSubcategories + " ER: false;" +
							"\n selectedCategoryIsDisplayedInRefineBy = " + selectedCategoryIsDisplayedInRefineBy + " ER: true;" +
							"\n correctGamesAreDisplayed = " + correctGamesAreDisplayed + " ER: true;" +
							"\n invalidGamesAreDisplayed = " + invalidGamesAreDisplayed + " ER: false;" +
							"\n resetFilterOptionIsPresent = " + resetFilterOptionIsPresent + " ER: true;" +
							"\n pageURLContainsCategoryPath = " + pageURLContainsCategoryPath + " ER: true;");
		}

	}

	/*2.7. Refine By: Category without subcategories > Top*/
	@Test(groups = {"regression"})
	public void refineByNavigationFromCategoryWithoutSubcategoriesToTop () {
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);
		gamesPortletPage.refineBy(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
		gamesPortletPage.clickRefineByOptionByText(gameControlLabels.getResetFilterLabel());
		boolean refineByDropDownIsDisplayed = gamesPortletPage.refineByDropDownIsPresent();
		boolean categoryTabsAreHidden = gamesPortletPage.categoryMenuIsHidden();
		boolean refineByOptionsIncludeTopCategories =  gamesPortletPage.refineByOptionsIncludeTopCategories();
		boolean refineByOptionsIncludeSubcategories =
				(gamesPortletPage.childCategoriesForCatSubcat1DisplayedInRefineBy()&&
						gamesPortletPage.childCategoriesForCatSubcat2DisplayedInRefineBy());
		boolean noCategoryIsSelectedInRefineBy = gamesPortletPage.getActiveRefineByOptionText().equals(gameControlLabels.getRefineByLabel());
		boolean correctGamesAreDisplayed = gamesPortletPage.correctGamesAreDisplayed(gameCategories.getAllGames(), gamesPortletPage.getAllGameIDs());
		boolean resetFilterOptionIsPresent = gamesPortletPage.isRefineByTextOptionPresent(gameControlLabels.getResetFilterLabel());
		boolean pageURLIsCorrect = gamesPortletPage.currentURLIsPageURL(ConfiguredPages.gamesNavigationStyleRefineBy.toString());
		if (refineByDropDownIsDisplayed == true&&
				categoryTabsAreHidden == true&&
				refineByOptionsIncludeTopCategories == true&&
				refineByOptionsIncludeSubcategories == false&&
				noCategoryIsSelectedInRefineBy == true &&
				correctGamesAreDisplayed == true&&
				resetFilterOptionIsPresent == false&&
				pageURLIsCorrect == true) {
		} else {
			WebDriverUtils.runtimeExceptionWithLogs(
					"refineByDropDownIsDisplayed = " + refineByDropDownIsDisplayed + " ER: true;" +
							"\n categoryTabsAreHidden = " + categoryTabsAreHidden + " ER: true;" +
							"\n refineByOptionsIncludeTopCategories = " + refineByOptionsIncludeTopCategories + " ER: true;" +
							"\n refineByOptionsIncludeSubcategories = " + refineByOptionsIncludeSubcategories + " ER: false;" +
							"\n noCategoryIsSelectedInRefineBy = " + noCategoryIsSelectedInRefineBy + " ER: true;" +
							"\n correctGamesAreDisplayed = " + correctGamesAreDisplayed + " ER: true;" +
							"\n resetFilterOptionIsPresent = " + resetFilterOptionIsPresent + " ER: false;" +
							"\n pageURLIsCorrect = " + pageURLIsCorrect + " ER: true;");
		}
	}

	/*2.8. Refine By: Category with subcategories > Top*/
	@Test(groups = {"regression"})
	public void refineByNavigationFromCategoryWithSubcategoriesToTop () {
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);
		gamesPortletPage.refineBy(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL);
		gamesPortletPage.clickRefineByOptionByText(gameControlLabels.getResetFilterLabel());
		boolean refineByDropDownIsDisplayed = gamesPortletPage.refineByDropDownIsPresent();
		boolean categoryTabsAreHidden = gamesPortletPage.categoryMenuIsHidden();
		boolean refineByOptionsIncludeTopCategories =  gamesPortletPage.refineByOptionsIncludeTopCategories();
		boolean refineByOptionsIncludeSubcategories =
				(gamesPortletPage.childCategoriesForCatSubcat1DisplayedInRefineBy()&&
						gamesPortletPage.childCategoriesForCatSubcat2DisplayedInRefineBy());
		boolean noCategoryIsSelectedInRefineBy = gamesPortletPage.getActiveRefineByOptionText().equals(gameControlLabels.getRefineByLabel());
		boolean correctGamesAreDisplayed = gamesPortletPage.correctGamesAreDisplayed(gameCategories.getAllGames(), gamesPortletPage.getAllGameIDs());
		boolean resetFilterOptionIsPresent = gamesPortletPage.isRefineByTextOptionPresent(gameControlLabels.getResetFilterLabel());
		boolean pageURLIsCorrect = gamesPortletPage.currentURLIsPageURL(ConfiguredPages.gamesNavigationStyleRefineBy.toString());
		if (refineByDropDownIsDisplayed == true&&
				categoryTabsAreHidden == true&&
				refineByOptionsIncludeTopCategories == true&&
				refineByOptionsIncludeSubcategories == false&&
				noCategoryIsSelectedInRefineBy == true &&
				correctGamesAreDisplayed == true&&
				resetFilterOptionIsPresent == false&&
				pageURLIsCorrect == true) {
		} else {
			WebDriverUtils.runtimeExceptionWithLogs(
					"refineByDropDownIsDisplayed = " + refineByDropDownIsDisplayed + " ER: true;" +
							"\n categoryTabsAreHidden = " + categoryTabsAreHidden + " ER: true;" +
							"\n refineByOptionsIncludeTopCategories = " + refineByOptionsIncludeTopCategories + " ER: true;" +
							"\n refineByOptionsIncludeSubcategories = " + refineByOptionsIncludeSubcategories + " ER: false;" +
							"\n noCategoryIsSelectedInRefineBy = " + noCategoryIsSelectedInRefineBy + " ER: true;" +
							"\n correctGamesAreDisplayed = " + correctGamesAreDisplayed + " ER: true;" +
							"\n resetFilterOptionIsPresent = " + resetFilterOptionIsPresent + " ER: false;" +
							"\n pageURLIsCorrect = " + pageURLIsCorrect + " ER: true;");
		}
	}

	/*2.9. Refine By: Subcategory > Parent category*/
	@Test(groups = {"regression"})
	public void  refineByNavigationFromSubcategoryToParentCategory () {
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);
		gamesPortletPage.refineBy(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		gamesPortletPage.refineBy(gamesPortletPage.CAT_WITH_SUBCAT1_SUBCAT_A_RELATIVE_URL);
		gamesPortletPage.clickRefineByOptionByText(gameControlLabels.getResetFilterLabel());
		boolean refineByDropDownIsDisplayed = gamesPortletPage.refineByDropDownIsPresent();
		boolean categoryTabsAreHidden = gamesPortletPage.categoryMenuIsHidden();
		boolean refineByOptionsIncludeTopCategories =  gamesPortletPage.refineByOptionsIncludeTopCategories();
		boolean refineByOptionsIncludeCorrectSubcategories = gamesPortletPage.childCategoriesForCatSubcat1DisplayedInRefineBy();
		boolean refineByOptionsIncludeIncorrectSubcategories = gamesPortletPage.childCategoriesForCatSubcat2DisplayedInRefineBy();
		boolean noCategoryIsSelectedInRefineBy = gamesPortletPage.getActiveRefineByOptionText().equals(gameControlLabels.getRefineByLabel());
		ArrayList <String> gamesDisplayed = gamesPortletPage.getAllGameIDs();
		boolean correctGamesAreDisplayed =  gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat1(),gamesDisplayed);
		boolean invalidGamesAreDisplayed =
				(gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat1(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat2(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2(),gamesDisplayed));
		boolean resetFilterOptionIsPresent = gamesPortletPage.isRefineByTextOptionPresent(gameControlLabels.getResetFilterLabel());
		boolean pageURLContainsCategoryPath = gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		if(refineByDropDownIsDisplayed == true&&
				categoryTabsAreHidden == true&&
				refineByOptionsIncludeTopCategories == false&&
				refineByOptionsIncludeCorrectSubcategories ==true &&
				refineByOptionsIncludeIncorrectSubcategories == false&&
				noCategoryIsSelectedInRefineBy == true&&
				correctGamesAreDisplayed == true&&
				invalidGamesAreDisplayed == false&&
				resetFilterOptionIsPresent == true&&
				pageURLContainsCategoryPath == true){
		}else{
			WebDriverUtils.runtimeExceptionWithLogs(
					"refineByDropDownIsDisplayed = " + refineByDropDownIsDisplayed + " ER: true;" +
							"\n categoryTabsAreHidden = " + categoryTabsAreHidden + " ER: true;" +
							"\n refineByOptionsIncludeTopCategories = " + refineByOptionsIncludeTopCategories + " ER: false;" +
							"\n refineByOptionsIncludeCorrectSubcategories = " + refineByOptionsIncludeCorrectSubcategories + " ER: true;" +
							"\n refineByOptionsIncludeIncorrectSubcategories = " + refineByOptionsIncludeIncorrectSubcategories + " ER: false;" +
							"\n noCategoryIsSelectedInRefineBy = " + noCategoryIsSelectedInRefineBy + " ER: true;" +
							"\n correctGamesAreDisplayed = " + correctGamesAreDisplayed + " ER: true;" +
							"\n invalidGamesAreDisplayed = " + invalidGamesAreDisplayed + " ER: false;" +
							"\n resetFilterOptionIsPresent = " + resetFilterOptionIsPresent + " ER: true;" +
							"\n pageURLContainsCategoryPath = " + pageURLContainsCategoryPath + " ER: true.");
		}
	}

	/*3.1.1. Category Tabs (Top): Top*/
	@Test(groups = {"regression"})
	public void categoryTabsTopNavigationStyleTopLevel (){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
		boolean topCategoriesMenuIsDisplayed = gamesPortletPage.topCategoryMenuIsPresent();
		boolean refineByDropDownIsDisplayed = gamesPortletPage.refineByDropDownIsPresent();
		boolean leftCategoriesMenuIsDisplayed = gamesPortletPage.leftCategoryMenuIsPresent();
		boolean topSubcategoriesMenuIsDisplayed = gamesPortletPage.topSubcategoryMenuIsPresent();
		boolean leftSubcategoriesMenuIsDisplayed = gamesPortletPage.leftSubcategoryMenuIsPresent();
		boolean correctCategoryTabsAreDisplayed = gamesPortletPage.categoriesMenuIncludesTopCategories ();
		boolean isActiveCategoryTabPresent = gamesPortletPage.isActiveCategoryTabPresent();
		boolean correctGamesAreDisplayed = gamesPortletPage.correctGamesAreDisplayed(gameCategories.getAllGames(), gamesPortletPage.getAllGameIDs());
		boolean pageURLIsCorrect = gamesPortletPage.currentURLIsPageURL(ConfiguredPages.gamesNavigationStyleCategoryTabsTop.toString());
		if (topCategoriesMenuIsDisplayed == true &&
				refineByDropDownIsDisplayed == false &&
				leftCategoriesMenuIsDisplayed == false&&
				topSubcategoriesMenuIsDisplayed == false &&
				leftSubcategoriesMenuIsDisplayed == false &&
				isActiveCategoryTabPresent == false &&
				correctCategoryTabsAreDisplayed == true &&
				correctGamesAreDisplayed == true &&
				pageURLIsCorrect == true) {
		}else {
			WebDriverUtils.runtimeExceptionWithLogs
					("topCategoriesMenuIsDisplayed = " + topCategoriesMenuIsDisplayed + " ER: true;" +
							"\n refineByDropDownIsDisplayed = " + refineByDropDownIsDisplayed + " ER: false;" +
							"\n leftCategoriesMenuIsDisplayed = " + leftCategoriesMenuIsDisplayed + " ER: false;" +
							"\n topSubcategoriesMenuIsDisplayed = " + topSubcategoriesMenuIsDisplayed + " ER: false;" +
							"\n leftSubcategoriesMenuIsDisplayed = " + leftSubcategoriesMenuIsDisplayed + " ER: false;" +
							"\n isActiveCategoryTabPresent = " + isActiveCategoryTabPresent + " ER: false;" +
							"\n correctCategoryTabsAreDisplayed = " + correctCategoryTabsAreDisplayed + " ER: true;" +
							"\n correctGamesAreDisplayed = " + correctGamesAreDisplayed + " ER: true;" +
							"\n pageURLIsCorrect = " + pageURLIsCorrect + " ER: true;");
		}
	}

	/*3.1.2. Category Tabs (Top): Top >> Category without sub-categories*/
	@Test(groups = {"regression"})
	public void categoryTabsTopNavigationFromTopToCategoryWithoutSubcategories (){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
		boolean topCategoriesMenuIsDisplayed = gamesPortletPage.topCategoryMenuIsPresent();
		boolean refineByDropDownIsDisplayed = gamesPortletPage.refineByDropDownIsPresent();
		boolean leftCategoriesMenuIsDisplayed = gamesPortletPage.leftCategoryMenuIsPresent();
		boolean topSubcategoriesMenuIsDisplayed = gamesPortletPage.topSubcategoryMenuIsPresent();
		boolean leftSubcategoriesMenuIsDisplayed = gamesPortletPage.leftSubcategoryMenuIsPresent();
		boolean correctCategoryTabsAreDisplayed = gamesPortletPage.categoriesMenuIncludesTopCategories ();
		boolean isActiveCategoryTabPresent = gamesPortletPage.isActiveCategoryTabPresent();
		boolean correctTabIsActive = gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
		ArrayList <String> gamesDisplayed = gamesPortletPage.getAllGameIDs();
		boolean correctGamesAreDisplayed = gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat1(),gamesDisplayed);
		boolean incorrectGamesAreDisplayed =
				(gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat2(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat1(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2(),gamesDisplayed));
		boolean pageURLIsCorrect = gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
		if (topCategoriesMenuIsDisplayed == true &&
				refineByDropDownIsDisplayed == false &&
				leftCategoriesMenuIsDisplayed == false&&
				topSubcategoriesMenuIsDisplayed == false &&
				leftSubcategoriesMenuIsDisplayed == false &&
				correctCategoryTabsAreDisplayed == true &&
				isActiveCategoryTabPresent == true &&
				correctTabIsActive == true &&
				correctGamesAreDisplayed == true &&
				incorrectGamesAreDisplayed == false &&
				pageURLIsCorrect == true) {
		}else {
			WebDriverUtils.runtimeExceptionWithLogs
					("topCategoriesMenuIsDisplayed = " + topCategoriesMenuIsDisplayed + " ER: true;" +
							"\n refineByDropDownIsDisplayed = " + refineByDropDownIsDisplayed + " ER: false;" +
							"\n leftCategoriesMenuIsDisplayed = " + leftCategoriesMenuIsDisplayed + " ER: false;" +
							"\n topSubcategoriesMenuIsDisplayed = " + topSubcategoriesMenuIsDisplayed + " ER: false;" +
							"\n leftSubcategoriesMenuIsDisplayed = " + leftSubcategoriesMenuIsDisplayed + " ER: false;" +
							"\n correctCategoryTabsAreDisplayed = " + correctCategoryTabsAreDisplayed + " ER: true;" +
							"\n isActiveCategoryTabPresent = " + isActiveCategoryTabPresent + " ER: true;" +
							"\n correctTabIsActive = " + correctTabIsActive + " ER: true;" +
							"\n correctGamesAreDisplayed = " + correctGamesAreDisplayed + " ER: true;" +
							"\n incorrectGamesAreDisplayed = " + incorrectGamesAreDisplayed + " ER: false;" +
							"\n pageURLIsCorrect = " + pageURLIsCorrect + " ER: true;");
		}
	}

	/*3.1.3. Category Tabs (Top): Top >> Category with sub-categories*/
	@Test(groups = {"regression"})
	public void categoryTabsTopNavigationFromTopToCategoryWithSubcategories (){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		boolean topCategoriesMenuIsDisplayed = gamesPortletPage.topCategoryMenuIsPresent();
		boolean refineByDropDownIsDisplayed = gamesPortletPage.refineByDropDownIsPresent();
		boolean leftCategoriesMenuIsDisplayed = gamesPortletPage.leftCategoryMenuIsPresent();
		boolean topSubcategoriesMenuIsDisplayed = gamesPortletPage.topSubcategoryMenuIsPresent();
		boolean leftSubcategoriesMenuIsDisplayed = gamesPortletPage.leftSubcategoryMenuIsPresent();
		boolean correctCategoryTabsAreDisplayed = gamesPortletPage.categoriesMenuIncludesTopCategories ();
		boolean correctSubcategoriesAreDisplayed = gamesPortletPage.childCategoriesForCatSubcat1DisplayedInCategoryTabsMenu();
		boolean isActiveCategoryTabPresent = gamesPortletPage.isActiveCategoryTabPresent();
		boolean isActiveSubcategoryTabPresent = gamesPortletPage.isActiveSubcategoryTabPresent();
		boolean correctTabIsActive = gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		ArrayList <String> gamesDisplayed = gamesPortletPage.getAllGameIDs();
		boolean correctGamesAreDisplayed = gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat1(),gamesDisplayed);
		boolean incorrectGamesAreDisplayed =
				(gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat1(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat2(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2(),gamesDisplayed));
		boolean pageURLIsCorrect = gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		if (topCategoriesMenuIsDisplayed == true &&
				refineByDropDownIsDisplayed == false &&
				leftCategoriesMenuIsDisplayed == false&&
				topSubcategoriesMenuIsDisplayed == true &&
				leftSubcategoriesMenuIsDisplayed == false &&
				correctCategoryTabsAreDisplayed == true &&
				correctSubcategoriesAreDisplayed == true &&
				isActiveCategoryTabPresent == true &&
				isActiveSubcategoryTabPresent == false &&
				correctTabIsActive == true &&
				correctGamesAreDisplayed == true &&
				incorrectGamesAreDisplayed == false &&
				pageURLIsCorrect == true) {
		}else {
			WebDriverUtils.runtimeExceptionWithLogs
					("topCategoriesMenuIsDisplayed = " + topCategoriesMenuIsDisplayed + " ER: true;" +
							"\n refineByDropDownIsDisplayed = " + refineByDropDownIsDisplayed + " ER: false;" +
							"\n leftCategoriesMenuIsDisplayed = " + leftCategoriesMenuIsDisplayed + " ER: false;" +
							"\n topSubcategoriesMenuIsDisplayed = " + topSubcategoriesMenuIsDisplayed + " ER: true;" +
							"\n leftSubcategoriesMenuIsDisplayed = " + leftSubcategoriesMenuIsDisplayed + " ER: false;" +
							"\n correctCategoryTabsAreDisplayed = " + correctCategoryTabsAreDisplayed + " ER: true;" +
							"\n correctSubcategoriesAreDisplayed = " + correctSubcategoriesAreDisplayed + " ER: true;" +
							"\n isActiveCategoryTabPresent = " + isActiveCategoryTabPresent + " ER: true;" +
							"\n isActiveSubcategoryTabPresent = " + isActiveSubcategoryTabPresent + " ER: false;" +
							"\n correctTabIsActive = " + correctTabIsActive + " ER: true;" +
							"\n correctGamesAreDisplayed = " + correctGamesAreDisplayed + " ER: true;" +
							"\n incorrectGamesAreDisplayed = " + incorrectGamesAreDisplayed + " ER: false;" +
							"\n pageURLIsCorrect = " + pageURLIsCorrect + " ER: true;");
		}
	}


	/*3.1.4. Category Tabs (Top): Category with subcategories > subcategory*/
	@Test(groups = {"regression"})
	public void categoryTabsTopNavigationFromCategoryWithSubcategoriesToSubcategory (){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_A_RELATIVE_URL);
		boolean topCategoriesMenuIsDisplayed = gamesPortletPage.topCategoryMenuIsPresent();
		boolean refineByDropDownIsDisplayed = gamesPortletPage.refineByDropDownIsPresent();
		boolean leftCategoriesMenuIsDisplayed = gamesPortletPage.leftCategoryMenuIsPresent();
		boolean topSubcategoriesMenuIsDisplayed = gamesPortletPage.topSubcategoryMenuIsPresent();
		boolean leftSubcategoriesMenuIsDisplayed = gamesPortletPage.leftSubcategoryMenuIsPresent();
		boolean correctCategoryTabsAreDisplayed = gamesPortletPage.categoriesMenuIncludesTopCategories ();
		boolean correctSubcategoriesAreDisplayed = gamesPortletPage.childCategoriesForCatSubcat2DisplayedInCategoryTabsMenu();
		boolean incorrectSubcategoriesAreDisplayed = gamesPortletPage.childCategoriesForCatSubcat1DisplayedInCategoryTabsMenu();
		boolean isActiveCategoryTabPresent = gamesPortletPage.isActiveCategoryTabPresent();
		boolean isActiveSubcategoryTabPresent = gamesPortletPage.isActiveSubcategoryTabPresent();
		boolean correctCategoryTabIsActive = gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL);
		boolean correctSubcategoryTabIsActive = gamesPortletPage.getActiveSubcategoryTabXp().equals(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_A_RELATIVE_URL);
		ArrayList <String> gamesDisplayed = gamesPortletPage.getAllGameIDs();
		boolean correctGamesAreDisplayed = gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2_subcatA(),gamesDisplayed);
		boolean incorrectGamesAreDisplayed =
				(gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat1(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat2(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat1(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2_subcatB(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2_subcatC(),gamesDisplayed) &&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2_subcatD(),gamesDisplayed));
		boolean pageURLIsCorrect = gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_A_RELATIVE_URL);
		if (topCategoriesMenuIsDisplayed == true &&
				refineByDropDownIsDisplayed == false &&
				leftCategoriesMenuIsDisplayed == false&&
				topSubcategoriesMenuIsDisplayed == true &&
				leftSubcategoriesMenuIsDisplayed == false &&
				correctCategoryTabsAreDisplayed == true &&
				correctSubcategoriesAreDisplayed == true &&
				incorrectSubcategoriesAreDisplayed == false &&
				isActiveCategoryTabPresent == true &&
				isActiveSubcategoryTabPresent == true &&
				correctCategoryTabIsActive == true &&
				correctSubcategoryTabIsActive == true &&
				correctGamesAreDisplayed == true &&
				incorrectGamesAreDisplayed == false &&
				pageURLIsCorrect == true) {
		}else {
			WebDriverUtils.runtimeExceptionWithLogs
					("topCategoriesMenuIsDisplayed = " + topCategoriesMenuIsDisplayed + " ER: true;" +
							"\n refineByDropDownIsDisplayed = " + refineByDropDownIsDisplayed + " ER: false;" +
							"\n leftCategoriesMenuIsDisplayed = " + leftCategoriesMenuIsDisplayed + " ER: false;" +
							"\n topSubcategoriesMenuIsDisplayed = " + topSubcategoriesMenuIsDisplayed + " ER: true;" +
							"\n leftSubcategoriesMenuIsDisplayed = " + leftSubcategoriesMenuIsDisplayed + " ER: false;" +
							"\n correctCategoryTabsAreDisplayed = " + correctCategoryTabsAreDisplayed + " ER: true;" +
							"\n correctSubcategoriesAreDisplayed = " + correctSubcategoriesAreDisplayed + " ER: true;" +
							"\n incorrectSubcategoriesAreDisplayed = " + incorrectSubcategoriesAreDisplayed + " ER: false;" +
							"\n isActiveCategoryTabPresent = " + isActiveCategoryTabPresent + " ER: true;" +
							"\n isActiveSubcategoryTabPresent = " + isActiveSubcategoryTabPresent + " ER: true;" +
							"\n correctCategoryTabIsActive = " + correctCategoryTabIsActive + " ER: true;" +
							"\n correctSubcategoryTabIsActive = " + correctSubcategoryTabIsActive + " ER: true;" +
							"\n correctGamesAreDisplayed = " + correctGamesAreDisplayed + " ER: true;" +
							"\n incorrectGamesAreDisplayed = " + incorrectGamesAreDisplayed + " ER: false;" +
							"\n pageURLIsCorrect = " + pageURLIsCorrect + " ER: true;");
		}
	}

	/*3.1.5. Category Tabs (Top): Subcategory > subcategory*/
	@Test(groups = {"regression"})
	public void categoryTabsTopNavigationFromSubcategoryToSubcategory (){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_A_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_B_RELATIVE_URL);
		boolean topCategoriesMenuIsDisplayed = gamesPortletPage.topCategoryMenuIsPresent();
		boolean refineByDropDownIsDisplayed = gamesPortletPage.refineByDropDownIsPresent();
		boolean leftCategoriesMenuIsDisplayed = gamesPortletPage.leftCategoryMenuIsPresent();
		boolean topSubcategoriesMenuIsDisplayed = gamesPortletPage.topSubcategoryMenuIsPresent();
		boolean leftSubcategoriesMenuIsDisplayed = gamesPortletPage.leftSubcategoryMenuIsPresent();
		boolean correctCategoryTabsAreDisplayed = gamesPortletPage.categoriesMenuIncludesTopCategories ();
		boolean correctSubcategoriesAreDisplayed = gamesPortletPage.childCategoriesForCatSubcat2DisplayedInCategoryTabsMenu();
		boolean incorrectSubcategoriesAreDisplayed = gamesPortletPage.childCategoriesForCatSubcat1DisplayedInCategoryTabsMenu();
		boolean isActiveCategoryTabPresent = gamesPortletPage.isActiveCategoryTabPresent();
		boolean isActiveSubcategoryTabPresent = gamesPortletPage.isActiveSubcategoryTabPresent();
		boolean correctCategoryTabIsActive = gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL);
		boolean correctSubcategoryTabIsActive = gamesPortletPage.getActiveSubcategoryTabXp().equals(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_B_RELATIVE_URL);
		ArrayList <String> gamesDisplayed = gamesPortletPage.getAllGameIDs();
		boolean correctGamesAreDisplayed = gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2_subcatB(),gamesDisplayed);
		boolean incorrectGamesAreDisplayed =
				(gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat1(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat2(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat1(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2_subcatA(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2_subcatC(),gamesDisplayed) &&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2_subcatD(),gamesDisplayed));
		boolean pageURLIsCorrect = gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_B_RELATIVE_URL);
		if (topCategoriesMenuIsDisplayed == true &&
				refineByDropDownIsDisplayed == false &&
				leftCategoriesMenuIsDisplayed == false&&
				topSubcategoriesMenuIsDisplayed == true &&
				leftSubcategoriesMenuIsDisplayed == false &&
				correctCategoryTabsAreDisplayed == true &&
				correctSubcategoriesAreDisplayed == true &&
				incorrectSubcategoriesAreDisplayed == false &&
				isActiveCategoryTabPresent == true &&
				isActiveSubcategoryTabPresent == true &&
				correctCategoryTabIsActive == true &&
				correctSubcategoryTabIsActive == true &&
				correctGamesAreDisplayed == true &&
				incorrectGamesAreDisplayed == false &&
				pageURLIsCorrect == true) {
		}else {
			WebDriverUtils.runtimeExceptionWithLogs
					("topCategoriesMenuIsDisplayed = " + topCategoriesMenuIsDisplayed + " ER: true;" +
							"\n refineByDropDownIsDisplayed = " + refineByDropDownIsDisplayed + " ER: false;" +
							"\n leftCategoriesMenuIsDisplayed = " + leftCategoriesMenuIsDisplayed + " ER: false;" +
							"\n topSubcategoriesMenuIsDisplayed = " + topSubcategoriesMenuIsDisplayed + " ER: true;" +
							"\n leftSubcategoriesMenuIsDisplayed = " + leftSubcategoriesMenuIsDisplayed + " ER: false;" +
							"\n correctCategoryTabsAreDisplayed = " + correctCategoryTabsAreDisplayed + " ER: true;" +
							"\n correctSubcategoriesAreDisplayed = " + correctSubcategoriesAreDisplayed + " ER: true;" +
							"\n incorrectSubcategoriesAreDisplayed = " + incorrectSubcategoriesAreDisplayed + " ER: false;" +
							"\n isActiveCategoryTabPresent = " + isActiveCategoryTabPresent + " ER: true;" +
							"\n isActiveSubcategoryTabPresent = " + isActiveSubcategoryTabPresent + " ER: true;" +
							"\n correctCategoryTabIsActive = " + correctCategoryTabIsActive + " ER: true;" +
							"\n correctSubcategoryTabIsActive = " + correctSubcategoryTabIsActive + " ER: true;" +
							"\n correctGamesAreDisplayed = " + correctGamesAreDisplayed + " ER: true;" +
							"\n incorrectGamesAreDisplayed = " + incorrectGamesAreDisplayed + " ER: false;" +
							"\n pageURLIsCorrect = " + pageURLIsCorrect + " ER: true;");
		}
	}

	/*3.1.6. Category Tabs (Top): Category without subcategories > Category without subcategories*/
	@Test(groups = {"regression"})
	public void categoryTabsTopNavigationFromCatNoSubcatToCatNoSubcat (){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_NO_SUBCAT2_RELATIVE_URL);
		boolean topCategoriesMenuIsDisplayed = gamesPortletPage.topCategoryMenuIsPresent();
		boolean refineByDropDownIsDisplayed = gamesPortletPage.refineByDropDownIsPresent();
		boolean leftCategoriesMenuIsDisplayed = gamesPortletPage.leftCategoryMenuIsPresent();
		boolean topSubcategoriesMenuIsDisplayed = gamesPortletPage.topSubcategoryMenuIsPresent();
		boolean leftSubcategoriesMenuIsDisplayed = gamesPortletPage.leftSubcategoryMenuIsPresent();
		boolean correctCategoryTabsAreDisplayed = gamesPortletPage.categoriesMenuIncludesTopCategories ();
		boolean isActiveCategoryTabPresent = gamesPortletPage.isActiveCategoryTabPresent();
		boolean correctTabIsActive = gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_NO_SUBCAT2_RELATIVE_URL);
		ArrayList <String> gamesDisplayed = gamesPortletPage.getAllGameIDs();
		boolean correctGamesAreDisplayed = gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat2(),gamesDisplayed);
		boolean incorrectGamesAreDisplayed =
				(gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat1(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat1(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2(),gamesDisplayed));
		boolean pageURLIsCorrect = gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_NO_SUBCAT2_RELATIVE_URL);
		if (topCategoriesMenuIsDisplayed == true &&
				refineByDropDownIsDisplayed == false &&
				leftCategoriesMenuIsDisplayed == false&&
				topSubcategoriesMenuIsDisplayed == false &&
				leftSubcategoriesMenuIsDisplayed == false &&
				correctCategoryTabsAreDisplayed == true &&
				isActiveCategoryTabPresent == true &&
				correctTabIsActive == true &&
				correctGamesAreDisplayed == true &&
				incorrectGamesAreDisplayed == false &&
				pageURLIsCorrect == true) {
		}else {
			WebDriverUtils.runtimeExceptionWithLogs
					("topCategoriesMenuIsDisplayed = " + topCategoriesMenuIsDisplayed + " ER: true;" +
							"\n refineByDropDownIsDisplayed = " + refineByDropDownIsDisplayed + " ER: false;" +
							"\n leftCategoriesMenuIsDisplayed = " + leftCategoriesMenuIsDisplayed + " ER: false;" +
							"\n topSubcategoriesMenuIsDisplayed = " + topSubcategoriesMenuIsDisplayed + " ER: false;" +
							"\n leftSubcategoriesMenuIsDisplayed = " + leftSubcategoriesMenuIsDisplayed + " ER: false;" +
							"\n correctCategoryTabsAreDisplayed = " + correctCategoryTabsAreDisplayed + " ER: true;" +
							"\n isActiveCategoryTabPresent = " + isActiveCategoryTabPresent + " ER: true;" +
							"\n correctTabIsActive = " + correctTabIsActive + " ER: true;" +
							"\n correctGamesAreDisplayed = " + correctGamesAreDisplayed + " ER: true;" +
							"\n incorrectGamesAreDisplayed = " + incorrectGamesAreDisplayed + " ER: false;" +
							"\n pageURLIsCorrect = " + pageURLIsCorrect + " ER: true;");
		}
	}


	/*3.1.7. Category without subcategories > Category with subcategories*/
	@Test(groups = {"regression"})
	public void categoryTabsTopNavigationFromCatNoSubcatToCatSubcat (){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		boolean topCategoriesMenuIsDisplayed = gamesPortletPage.topCategoryMenuIsPresent();
		boolean refineByDropDownIsDisplayed = gamesPortletPage.refineByDropDownIsPresent();
		boolean leftCategoriesMenuIsDisplayed = gamesPortletPage.leftCategoryMenuIsPresent();
		boolean topSubcategoriesMenuIsDisplayed = gamesPortletPage.topSubcategoryMenuIsPresent();
		boolean leftSubcategoriesMenuIsDisplayed = gamesPortletPage.leftSubcategoryMenuIsPresent();
		boolean correctCategoryTabsAreDisplayed = gamesPortletPage.categoriesMenuIncludesTopCategories ();
		boolean correctSubcategoriesAreDisplayed = gamesPortletPage.childCategoriesForCatSubcat1DisplayedInCategoryTabsMenu();
		boolean isActiveCategoryTabPresent = gamesPortletPage.isActiveCategoryTabPresent();
		boolean isActiveSubcategoryTabPresent = gamesPortletPage.isActiveSubcategoryTabPresent();
		boolean correctTabIsActive = gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		ArrayList <String> gamesDisplayed = gamesPortletPage.getAllGameIDs();
		boolean correctGamesAreDisplayed = gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat1(),gamesDisplayed);
		boolean incorrectGamesAreDisplayed =
				(gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat1(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat2(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2(),gamesDisplayed));
		boolean pageURLIsCorrect = gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		if (topCategoriesMenuIsDisplayed == true &&
				refineByDropDownIsDisplayed == false &&
				leftCategoriesMenuIsDisplayed == false&&
				topSubcategoriesMenuIsDisplayed == true &&
				leftSubcategoriesMenuIsDisplayed == false &&
				correctCategoryTabsAreDisplayed == true &&
				correctSubcategoriesAreDisplayed == true &&
				isActiveCategoryTabPresent == true &&
				isActiveSubcategoryTabPresent == false &&
				correctTabIsActive == true &&
				correctGamesAreDisplayed == true &&
				incorrectGamesAreDisplayed == false &&
				pageURLIsCorrect == true) {
		}else {
			WebDriverUtils.runtimeExceptionWithLogs
					("topCategoriesMenuIsDisplayed = " + topCategoriesMenuIsDisplayed + " ER: true;" +
							"\n refineByDropDownIsDisplayed = " + refineByDropDownIsDisplayed + " ER: false;" +
							"\n leftCategoriesMenuIsDisplayed = " + leftCategoriesMenuIsDisplayed + " ER: false;" +
							"\n topSubcategoriesMenuIsDisplayed = " + topSubcategoriesMenuIsDisplayed + " ER: true;" +
							"\n leftSubcategoriesMenuIsDisplayed = " + leftSubcategoriesMenuIsDisplayed + " ER: false;" +
							"\n correctCategoryTabsAreDisplayed = " + correctCategoryTabsAreDisplayed + " ER: true;" +
							"\n correctSubcategoriesAreDisplayed = " + correctSubcategoriesAreDisplayed + " ER: true;" +
							"\n isActiveCategoryTabPresent = " + isActiveCategoryTabPresent + " ER: true;" +
							"\n isActiveSubcategoryTabPresent = " + isActiveSubcategoryTabPresent + " ER: false;" +
							"\n correctTabIsActive = " + correctTabIsActive + " ER: true;" +
							"\n correctGamesAreDisplayed = " + correctGamesAreDisplayed + " ER: true;" +
							"\n incorrectGamesAreDisplayed = " + incorrectGamesAreDisplayed + " ER: false;" +
							"\n pageURLIsCorrect = " + pageURLIsCorrect + " ER: true;");
		}
	}

	/*3.1.8. Category with subcategories > Category without subcategories*/
	@Test(groups = {"regression"})
	public void categoryTabsTopNavigationFromCatSubCatToCatNoScubcat (){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
		boolean topCategoriesMenuIsDisplayed = gamesPortletPage.topCategoryMenuIsPresent();
		boolean refineByDropDownIsDisplayed = gamesPortletPage.refineByDropDownIsPresent();
		boolean leftCategoriesMenuIsDisplayed = gamesPortletPage.leftCategoryMenuIsPresent();
		boolean topSubcategoriesMenuIsDisplayed = gamesPortletPage.topSubcategoryMenuIsPresent();
		boolean leftSubcategoriesMenuIsDisplayed = gamesPortletPage.leftSubcategoryMenuIsPresent();
		boolean correctCategoryTabsAreDisplayed = gamesPortletPage.categoriesMenuIncludesTopCategories ();
		boolean isActiveCategoryTabPresent = gamesPortletPage.isActiveCategoryTabPresent();
		boolean correctTabIsActive = gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
		ArrayList <String> gamesDisplayed = gamesPortletPage.getAllGameIDs();
		boolean correctGamesAreDisplayed = gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat1(),gamesDisplayed);
		boolean incorrectGamesAreDisplayed =
				(gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat2(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat1(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2(),gamesDisplayed));
		boolean pageURLIsCorrect = gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
		if (topCategoriesMenuIsDisplayed == true &&
				refineByDropDownIsDisplayed == false &&
				leftCategoriesMenuIsDisplayed == false&&
				topSubcategoriesMenuIsDisplayed == false &&
				leftSubcategoriesMenuIsDisplayed == false &&
				correctCategoryTabsAreDisplayed == true &&
				isActiveCategoryTabPresent == true &&
				correctTabIsActive == true &&
				correctGamesAreDisplayed == true &&
				incorrectGamesAreDisplayed == false &&
				pageURLIsCorrect == true) {
		}else {
			WebDriverUtils.runtimeExceptionWithLogs
					("topCategoriesMenuIsDisplayed = " + topCategoriesMenuIsDisplayed + " ER: true;" +
							"\n refineByDropDownIsDisplayed = " + refineByDropDownIsDisplayed + " ER: false;" +
							"\n leftCategoriesMenuIsDisplayed = " + leftCategoriesMenuIsDisplayed + " ER: false;" +
							"\n topSubcategoriesMenuIsDisplayed = " + topSubcategoriesMenuIsDisplayed + " ER: false;" +
							"\n leftSubcategoriesMenuIsDisplayed = " + leftSubcategoriesMenuIsDisplayed + " ER: false;" +
							"\n correctCategoryTabsAreDisplayed = " + correctCategoryTabsAreDisplayed + " ER: true;" +
							"\n isActiveCategoryTabPresent = " + isActiveCategoryTabPresent + " ER: true;" +
							"\n correctTabIsActive = " + correctTabIsActive + " ER: true;" +
							"\n correctGamesAreDisplayed = " + correctGamesAreDisplayed + " ER: true;" +
							"\n incorrectGamesAreDisplayed = " + incorrectGamesAreDisplayed + " ER: false;" +
							"\n pageURLIsCorrect = " + pageURLIsCorrect + " ER: true;");
		}
	}

	/*3.1.9. Category with subcategories > Category with subcategories*/
	@Test(groups = {"regression"})
	public void categoryTabsTopNavigationFromCatSubcatToCatSubcat (){
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL);
		boolean topCategoriesMenuIsDisplayed = gamesPortletPage.topCategoryMenuIsPresent();
		boolean refineByDropDownIsDisplayed = gamesPortletPage.refineByDropDownIsPresent();
		boolean leftCategoriesMenuIsDisplayed = gamesPortletPage.leftCategoryMenuIsPresent();
		boolean topSubcategoriesMenuIsDisplayed = gamesPortletPage.topSubcategoryMenuIsPresent();
		boolean leftSubcategoriesMenuIsDisplayed = gamesPortletPage.leftSubcategoryMenuIsPresent();
		boolean correctCategoryTabsAreDisplayed = gamesPortletPage.categoriesMenuIncludesTopCategories ();
		boolean correctSubcategoriesAreDisplayed = gamesPortletPage.childCategoriesForCatSubcat2DisplayedInCategoryTabsMenu();
		boolean isActiveCategoryTabPresent = gamesPortletPage.isActiveCategoryTabPresent();
		boolean isActiveSubcategoryTabPresent = gamesPortletPage.isActiveSubcategoryTabPresent();
		boolean correctTabIsActive = gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL);
		ArrayList <String> gamesDisplayed = gamesPortletPage.getAllGameIDs();
		boolean correctGamesAreDisplayed = gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2(),gamesDisplayed);
		boolean incorrectGamesAreDisplayed =
				(gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat1(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat2(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat1(),gamesDisplayed));
		boolean pageURLIsCorrect = gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL);
		if (topCategoriesMenuIsDisplayed == true &&
				refineByDropDownIsDisplayed == false &&
				leftCategoriesMenuIsDisplayed == false&&
				topSubcategoriesMenuIsDisplayed == true &&
				leftSubcategoriesMenuIsDisplayed == false &&
				correctCategoryTabsAreDisplayed == true &&
				correctSubcategoriesAreDisplayed == true &&
				isActiveCategoryTabPresent == true &&
				isActiveSubcategoryTabPresent == false &&
				correctTabIsActive == true &&
				correctGamesAreDisplayed == true &&
				incorrectGamesAreDisplayed == false &&
				pageURLIsCorrect == true) {
		}else {
			WebDriverUtils.runtimeExceptionWithLogs
					("topCategoriesMenuIsDisplayed = " + topCategoriesMenuIsDisplayed + " ER: true;" +
							"\n refineByDropDownIsDisplayed = " + refineByDropDownIsDisplayed + " ER: false;" +
							"\n leftCategoriesMenuIsDisplayed = " + leftCategoriesMenuIsDisplayed + " ER: false;" +
							"\n topSubcategoriesMenuIsDisplayed = " + topSubcategoriesMenuIsDisplayed + " ER: true;" +
							"\n leftSubcategoriesMenuIsDisplayed = " + leftSubcategoriesMenuIsDisplayed + " ER: false;" +
							"\n correctCategoryTabsAreDisplayed = " + correctCategoryTabsAreDisplayed + " ER: true;" +
							"\n correctSubcategoriesAreDisplayed = " + correctSubcategoriesAreDisplayed + " ER: true;" +
							"\n isActiveCategoryTabPresent = " + isActiveCategoryTabPresent + " ER: true;" +
							"\n isActiveSubcategoryTabPresent = " + isActiveSubcategoryTabPresent + " ER: false;" +
							"\n correctTabIsActive = " + correctTabIsActive + " ER: true;" +
							"\n correctGamesAreDisplayed = " + correctGamesAreDisplayed + " ER: true;" +
							"\n incorrectGamesAreDisplayed = " + incorrectGamesAreDisplayed + " ER: false;" +
							"\n pageURLIsCorrect = " + pageURLIsCorrect + " ER: true;");
		}
	}

	/*3.1.10. Category Tabs (Top): Subcategory >> Parent category*/
	@Test(groups = {"regression"})
	public void categoryTabsTopNavigationFromSubcategoryToParentCategory (){
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_WITH_SUBCAT1_SUBCAT_C_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		boolean topCategoriesMenuIsDisplayed = gamesPortletPage.topCategoryMenuIsPresent();
		boolean refineByDropDownIsDisplayed = gamesPortletPage.refineByDropDownIsPresent();
		boolean leftCategoriesMenuIsDisplayed = gamesPortletPage.leftCategoryMenuIsPresent();
		boolean topSubcategoriesMenuIsDisplayed = gamesPortletPage.topSubcategoryMenuIsPresent();
		boolean leftSubcategoriesMenuIsDisplayed = gamesPortletPage.leftSubcategoryMenuIsPresent();
		boolean correctCategoryTabsAreDisplayed = gamesPortletPage.categoriesMenuIncludesTopCategories ();
		boolean correctSubcategoriesAreDisplayed = gamesPortletPage.childCategoriesForCatSubcat1DisplayedInCategoryTabsMenu();
		boolean isActiveCategoryTabPresent = gamesPortletPage.isActiveCategoryTabPresent();
		boolean isActiveSubcategoryTabPresent = gamesPortletPage.isActiveSubcategoryTabPresent();
		boolean correctTabIsActive = gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		ArrayList <String> gamesDisplayed = gamesPortletPage.getAllGameIDs();
		boolean correctGamesAreDisplayed = gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat1(),gamesDisplayed);
		boolean incorrectGamesAreDisplayed =
				(gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat1(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat2(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2(),gamesDisplayed));
		boolean pageURLIsCorrect = gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		if (topCategoriesMenuIsDisplayed == true &&
				refineByDropDownIsDisplayed == false &&
				leftCategoriesMenuIsDisplayed == false&&
				topSubcategoriesMenuIsDisplayed == true &&
				leftSubcategoriesMenuIsDisplayed == false &&
				correctCategoryTabsAreDisplayed == true &&
				correctSubcategoriesAreDisplayed == true &&
				isActiveCategoryTabPresent == true &&
				isActiveSubcategoryTabPresent == false &&
				correctTabIsActive == true &&
				correctGamesAreDisplayed == true &&
				incorrectGamesAreDisplayed == false &&
				pageURLIsCorrect == true) {
		}else {
			WebDriverUtils.runtimeExceptionWithLogs
					("topCategoriesMenuIsDisplayed = " + topCategoriesMenuIsDisplayed + " ER: true;" +
							"\n refineByDropDownIsDisplayed = " + refineByDropDownIsDisplayed + " ER: false;" +
							"\n leftCategoriesMenuIsDisplayed = " + leftCategoriesMenuIsDisplayed + " ER: false;" +
							"\n topSubcategoriesMenuIsDisplayed = " + topSubcategoriesMenuIsDisplayed + " ER: true;" +
							"\n leftSubcategoriesMenuIsDisplayed = " + leftSubcategoriesMenuIsDisplayed + " ER: false;" +
							"\n correctCategoryTabsAreDisplayed = " + correctCategoryTabsAreDisplayed + " ER: true;" +
							"\n correctSubcategoriesAreDisplayed = " + correctSubcategoriesAreDisplayed + " ER: true;" +
							"\n isActiveCategoryTabPresent = " + isActiveCategoryTabPresent + " ER: true;" +
							"\n isActiveSubcategoryTabPresent = " + isActiveSubcategoryTabPresent + " ER: false;" +
							"\n correctTabIsActive = " + correctTabIsActive + " ER: true;" +
							"\n correctGamesAreDisplayed = " + correctGamesAreDisplayed + " ER: true;" +
							"\n incorrectGamesAreDisplayed = " + incorrectGamesAreDisplayed + " ER: false;" +
							"\n pageURLIsCorrect = " + pageURLIsCorrect + " ER: true;");
		}
	}

	/*3.2.1. Category Tabs (Left): Top*/
	@Test(groups = {"regression"})
	public void categoryTabsLeftNavigationStyleTopLevel (){
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
		boolean topCategoriesMenuIsDisplayed = gamesPortletPage.topCategoryMenuIsPresent();
		boolean refineByDropDownIsDisplayed = gamesPortletPage.refineByDropDownIsPresent();
		boolean leftCategoriesMenuIsDisplayed = gamesPortletPage.leftCategoryMenuIsPresent();
		boolean topSubcategoriesMenuIsDisplayed = gamesPortletPage.topSubcategoryMenuIsPresent();
		boolean leftSubcategoriesMenuIsDisplayed = gamesPortletPage.leftSubcategoryMenuIsPresent();
		boolean correctCategoryTabsAreDisplayed = gamesPortletPage.categoriesMenuIncludesTopCategories ();
		boolean isActiveCategoryTabPresent = gamesPortletPage.isActiveCategoryTabPresent();
		boolean correctGamesAreDisplayed = gamesPortletPage.correctGamesAreDisplayed(gameCategories.getAllGames(), gamesPortletPage.getAllGameIDs());
		boolean pageURLIsCorrect = gamesPortletPage.currentURLIsPageURL(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft.toString());
		if (topCategoriesMenuIsDisplayed == false &&
				refineByDropDownIsDisplayed == false &&
				leftCategoriesMenuIsDisplayed == true&&
				topSubcategoriesMenuIsDisplayed == false &&
				leftSubcategoriesMenuIsDisplayed == false &&
				isActiveCategoryTabPresent == false &&
				correctCategoryTabsAreDisplayed == true &&
				correctGamesAreDisplayed == true &&
				pageURLIsCorrect == true) {
		}else {
			WebDriverUtils.runtimeExceptionWithLogs
					("topCategoriesMenuIsDisplayed = " + topCategoriesMenuIsDisplayed + " ER: false;" +
							"\n refineByDropDownIsDisplayed = " + refineByDropDownIsDisplayed + " ER: false;" +
							"\n leftCategoriesMenuIsDisplayed = " + leftCategoriesMenuIsDisplayed + " ER: true;" +
							"\n topSubcategoriesMenuIsDisplayed = " + topSubcategoriesMenuIsDisplayed + " ER: false;" +
							"\n leftSubcategoriesMenuIsDisplayed = " + leftSubcategoriesMenuIsDisplayed + " ER: false;" +
							"\n isActiveCategoryTabPresent = " + isActiveCategoryTabPresent + " ER: false;" +
							"\n correctCategoryTabsAreDisplayed = " + correctCategoryTabsAreDisplayed + " ER: true;" +
							"\n correctGamesAreDisplayed = " + correctGamesAreDisplayed + " ER: true;" +
							"\n pageURLIsCorrect = " + pageURLIsCorrect + " ER: true;");
		}
	}

	/*3.2.2. Category Tabs (Left): Top >> Category without sub-categories*/
	@Test(groups = {"regression"})
	public void categoryTabsLeftNavigationFromTopToCategoryWithoutSubcategories (){
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
		boolean topCategoriesMenuIsDisplayed = gamesPortletPage.topCategoryMenuIsPresent();
		boolean refineByDropDownIsDisplayed = gamesPortletPage.refineByDropDownIsPresent();
		boolean leftCategoriesMenuIsDisplayed = gamesPortletPage.leftCategoryMenuIsPresent();
		boolean topSubcategoriesMenuIsDisplayed = gamesPortletPage.topSubcategoryMenuIsPresent();
		boolean leftSubcategoriesMenuIsDisplayed = gamesPortletPage.leftSubcategoryMenuIsPresent();
		boolean correctCategoryTabsAreDisplayed = gamesPortletPage.categoriesMenuIncludesTopCategories ();
		boolean isActiveCategoryTabPresent = gamesPortletPage.isActiveCategoryTabPresent();
		boolean correctTabIsActive = gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
		ArrayList <String> gamesDisplayed = gamesPortletPage.getAllGameIDs();
		boolean correctGamesAreDisplayed = gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat1(),gamesDisplayed);
		boolean incorrectGamesAreDisplayed =
				(gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat2(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat1(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2(),gamesDisplayed));
		boolean pageURLIsCorrect = gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
		if (topCategoriesMenuIsDisplayed == false &&
				refineByDropDownIsDisplayed == false &&
				leftCategoriesMenuIsDisplayed == true&&
				topSubcategoriesMenuIsDisplayed == false &&
				leftSubcategoriesMenuIsDisplayed == false &&
				correctCategoryTabsAreDisplayed == true &&
				isActiveCategoryTabPresent == true &&
				correctTabIsActive == true &&
				correctGamesAreDisplayed == true &&
				incorrectGamesAreDisplayed == false &&
				pageURLIsCorrect == true) {
		}else {
			WebDriverUtils.runtimeExceptionWithLogs
					("topCategoriesMenuIsDisplayed = " + topCategoriesMenuIsDisplayed + " ER: false;" +
							"\n refineByDropDownIsDisplayed = " + refineByDropDownIsDisplayed + " ER: false;" +
							"\n leftCategoriesMenuIsDisplayed = " + leftCategoriesMenuIsDisplayed + " ER: true;" +
							"\n topSubcategoriesMenuIsDisplayed = " + topSubcategoriesMenuIsDisplayed + " ER: false;" +
							"\n leftSubcategoriesMenuIsDisplayed = " + leftSubcategoriesMenuIsDisplayed + " ER: false;" +
							"\n correctCategoryTabsAreDisplayed = " + correctCategoryTabsAreDisplayed + " ER: true;" +
							"\n isActiveCategoryTabPresent = " + isActiveCategoryTabPresent + " ER: true;" +
							"\n correctTabIsActive = " + correctTabIsActive + " ER: true;" +
							"\n correctGamesAreDisplayed = " + correctGamesAreDisplayed + " ER: true;" +
							"\n incorrectGamesAreDisplayed = " + incorrectGamesAreDisplayed + " ER: false;" +
							"\n pageURLIsCorrect = " + pageURLIsCorrect + " ER: true;");
		}
	}

	/*3.2.3. Category Tabs (Left): Top >> Category with sub-categories*/
	@Test(groups = {"regression"})
	public void categoryTabsLeftNavigationFromTopToCategoryWithSubcategories (){
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		boolean topCategoriesMenuIsDisplayed = gamesPortletPage.topCategoryMenuIsPresent();
		boolean refineByDropDownIsDisplayed = gamesPortletPage.refineByDropDownIsPresent();
		boolean leftCategoriesMenuIsDisplayed = gamesPortletPage.leftCategoryMenuIsPresent();
		boolean topSubcategoriesMenuIsDisplayed = gamesPortletPage.topSubcategoryMenuIsPresent();
		boolean leftSubcategoriesMenuIsDisplayed = gamesPortletPage.leftSubcategoryMenuIsPresent();
		boolean correctCategoryTabsAreDisplayed = gamesPortletPage.categoriesMenuIncludesTopCategories ();
		boolean correctSubcategoriesAreDisplayed = gamesPortletPage.childCategoriesForCatSubcat1DisplayedInCategoryTabsMenu();
		boolean isActiveCategoryTabPresent = gamesPortletPage.isActiveCategoryTabPresent();
		boolean isActiveSubcategoryTabPresent = gamesPortletPage.isActiveSubcategoryTabPresent();
		boolean correctTabIsActive = gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		ArrayList <String> gamesDisplayed = gamesPortletPage.getAllGameIDs();
		boolean correctGamesAreDisplayed = gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat1(),gamesDisplayed);
		boolean incorrectGamesAreDisplayed =
				(gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat1(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat2(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2(),gamesDisplayed));
		boolean pageURLIsCorrect = gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		if (topCategoriesMenuIsDisplayed == false &&
				refineByDropDownIsDisplayed == false &&
				leftCategoriesMenuIsDisplayed == true&&
				topSubcategoriesMenuIsDisplayed == false &&
				leftSubcategoriesMenuIsDisplayed == true &&
				correctCategoryTabsAreDisplayed == true &&
				correctSubcategoriesAreDisplayed == true &&
				isActiveCategoryTabPresent == true &&
				isActiveSubcategoryTabPresent == false &&
				correctTabIsActive == true &&
				correctGamesAreDisplayed == true &&
				incorrectGamesAreDisplayed == false &&
				pageURLIsCorrect == true) {
		}else {
			WebDriverUtils.runtimeExceptionWithLogs
					("topCategoriesMenuIsDisplayed = " + topCategoriesMenuIsDisplayed + " ER: false;" +
							"\n refineByDropDownIsDisplayed = " + refineByDropDownIsDisplayed + " ER: false;" +
							"\n leftCategoriesMenuIsDisplayed = " + leftCategoriesMenuIsDisplayed + " ER: true;" +
							"\n topSubcategoriesMenuIsDisplayed = " + topSubcategoriesMenuIsDisplayed + " ER: false;" +
							"\n leftSubcategoriesMenuIsDisplayed = " + leftSubcategoriesMenuIsDisplayed + " ER: true;" +
							"\n correctCategoryTabsAreDisplayed = " + correctCategoryTabsAreDisplayed + " ER: true;" +
							"\n correctSubcategoriesAreDisplayed = " + correctSubcategoriesAreDisplayed + " ER: true;" +
							"\n isActiveCategoryTabPresent = " + isActiveCategoryTabPresent + " ER: true;" +
							"\n isActiveSubcategoryTabPresent = " + isActiveSubcategoryTabPresent + " ER: false;" +
							"\n correctTabIsActive = " + correctTabIsActive + " ER: true;" +
							"\n correctGamesAreDisplayed = " + correctGamesAreDisplayed + " ER: true;" +
							"\n incorrectGamesAreDisplayed = " + incorrectGamesAreDisplayed + " ER: false;" +
							"\n pageURLIsCorrect = " + pageURLIsCorrect + " ER: true;");
		}
	}


	/*3.2.4. Category Tabs (Left): Category with subcategories > subcategory*/
	@Test(groups = {"regression"})
	public void categoryTabsLeftNavigationFromCategoryWithSubcategoriesToSubcategory (){
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_A_RELATIVE_URL);
		boolean topCategoriesMenuIsDisplayed = gamesPortletPage.topCategoryMenuIsPresent();
		boolean refineByDropDownIsDisplayed = gamesPortletPage.refineByDropDownIsPresent();
		boolean leftCategoriesMenuIsDisplayed = gamesPortletPage.leftCategoryMenuIsPresent();
		boolean topSubcategoriesMenuIsDisplayed = gamesPortletPage.topSubcategoryMenuIsPresent();
		boolean leftSubcategoriesMenuIsDisplayed = gamesPortletPage.leftSubcategoryMenuIsPresent();
		boolean correctCategoryTabsAreDisplayed = gamesPortletPage.categoriesMenuIncludesTopCategories ();
		boolean correctSubcategoriesAreDisplayed = gamesPortletPage.childCategoriesForCatSubcat2DisplayedInCategoryTabsMenu();
		boolean incorrectSubcategoriesAreDisplayed = gamesPortletPage.childCategoriesForCatSubcat1DisplayedInCategoryTabsMenu();
		boolean isActiveCategoryTabPresent = gamesPortletPage.isActiveCategoryTabPresent();
		boolean isActiveSubcategoryTabPresent = gamesPortletPage.isActiveSubcategoryTabPresent();
		boolean correctCategoryTabIsActive = gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL);
		boolean correctSubcategoryTabIsActive = gamesPortletPage.getActiveSubcategoryTabXp().equals(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_A_RELATIVE_URL);
		ArrayList <String> gamesDisplayed = gamesPortletPage.getAllGameIDs();
		boolean correctGamesAreDisplayed = gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2_subcatA(),gamesDisplayed);
		boolean incorrectGamesAreDisplayed =
				(gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat1(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat2(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat1(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2_subcatB(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2_subcatC(),gamesDisplayed) &&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2_subcatD(),gamesDisplayed));
		boolean pageURLIsCorrect = gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_A_RELATIVE_URL);
		if (topCategoriesMenuIsDisplayed == false &&
				refineByDropDownIsDisplayed == false &&
				leftCategoriesMenuIsDisplayed == true&&
				topSubcategoriesMenuIsDisplayed == false &&
				leftSubcategoriesMenuIsDisplayed == true &&
				correctCategoryTabsAreDisplayed == true &&
				correctSubcategoriesAreDisplayed == true &&
				incorrectSubcategoriesAreDisplayed == false &&
				isActiveCategoryTabPresent == true &&
				isActiveSubcategoryTabPresent == true &&
				correctCategoryTabIsActive == true &&
				correctSubcategoryTabIsActive == true &&
				correctGamesAreDisplayed == true &&
				incorrectGamesAreDisplayed == false &&
				pageURLIsCorrect == true) {
		}else {
			WebDriverUtils.runtimeExceptionWithLogs
					("topCategoriesMenuIsDisplayed = " + topCategoriesMenuIsDisplayed + " ER: false;" +
							"\n refineByDropDownIsDisplayed = " + refineByDropDownIsDisplayed + " ER: false;" +
							"\n leftCategoriesMenuIsDisplayed = " + leftCategoriesMenuIsDisplayed + " ER: true;" +
							"\n topSubcategoriesMenuIsDisplayed = " + topSubcategoriesMenuIsDisplayed + " ER: false;" +
							"\n leftSubcategoriesMenuIsDisplayed = " + leftSubcategoriesMenuIsDisplayed + " ER: true;" +
							"\n correctCategoryTabsAreDisplayed = " + correctCategoryTabsAreDisplayed + " ER: true;" +
							"\n correctSubcategoriesAreDisplayed = " + correctSubcategoriesAreDisplayed + " ER: true;" +
							"\n incorrectSubcategoriesAreDisplayed = " + incorrectSubcategoriesAreDisplayed + " ER: false;" +
							"\n isActiveCategoryTabPresent = " + isActiveCategoryTabPresent + " ER: true;" +
							"\n isActiveSubcategoryTabPresent = " + isActiveSubcategoryTabPresent + " ER: true;" +
							"\n correctCategoryTabIsActive = " + correctCategoryTabIsActive + " ER: true;" +
							"\n correctSubcategoryTabIsActive = " + correctSubcategoryTabIsActive + " ER: true;" +
							"\n correctGamesAreDisplayed = " + correctGamesAreDisplayed + " ER: true;" +
							"\n incorrectGamesAreDisplayed = " + incorrectGamesAreDisplayed + " ER: false;" +
							"\n pageURLIsCorrect = " + pageURLIsCorrect + " ER: true;");
		}
	}

	/*3.2.5. Category Tabs (Left): Subcategory > subcategory*/
	@Test(groups = {"regression"})
	public void categoryTabsLeftNavigationFromSubcategoryToSubcategory (){
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_A_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_B_RELATIVE_URL);
		boolean topCategoriesMenuIsDisplayed = gamesPortletPage.topCategoryMenuIsPresent();
		boolean refineByDropDownIsDisplayed = gamesPortletPage.refineByDropDownIsPresent();
		boolean leftCategoriesMenuIsDisplayed = gamesPortletPage.leftCategoryMenuIsPresent();
		boolean topSubcategoriesMenuIsDisplayed = gamesPortletPage.topSubcategoryMenuIsPresent();
		boolean leftSubcategoriesMenuIsDisplayed = gamesPortletPage.leftSubcategoryMenuIsPresent();
		boolean correctCategoryTabsAreDisplayed = gamesPortletPage.categoriesMenuIncludesTopCategories ();
		boolean correctSubcategoriesAreDisplayed = gamesPortletPage.childCategoriesForCatSubcat2DisplayedInCategoryTabsMenu();
		boolean incorrectSubcategoriesAreDisplayed = gamesPortletPage.childCategoriesForCatSubcat1DisplayedInCategoryTabsMenu();
		boolean isActiveCategoryTabPresent = gamesPortletPage.isActiveCategoryTabPresent();
		boolean isActiveSubcategoryTabPresent = gamesPortletPage.isActiveSubcategoryTabPresent();
		boolean correctCategoryTabIsActive = gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL);
		boolean correctSubcategoryTabIsActive = gamesPortletPage.getActiveSubcategoryTabXp().equals(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_B_RELATIVE_URL);
		ArrayList <String> gamesDisplayed = gamesPortletPage.getAllGameIDs();
		boolean correctGamesAreDisplayed = gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2_subcatB(),gamesDisplayed);
		boolean incorrectGamesAreDisplayed =
				(gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat1(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat2(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat1(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2_subcatA(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2_subcatC(),gamesDisplayed) &&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2_subcatD(),gamesDisplayed));
		boolean pageURLIsCorrect = gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_B_RELATIVE_URL);
		if (topCategoriesMenuIsDisplayed == false &&
				refineByDropDownIsDisplayed == false &&
				leftCategoriesMenuIsDisplayed == true&&
				topSubcategoriesMenuIsDisplayed == false &&
				leftSubcategoriesMenuIsDisplayed == true &&
				correctCategoryTabsAreDisplayed == true &&
				correctSubcategoriesAreDisplayed == true &&
				incorrectSubcategoriesAreDisplayed == false &&
				isActiveCategoryTabPresent == true &&
				isActiveSubcategoryTabPresent == true &&
				correctCategoryTabIsActive == true &&
				correctSubcategoryTabIsActive == true &&
				correctGamesAreDisplayed == true &&
				incorrectGamesAreDisplayed == false &&
				pageURLIsCorrect == true) {
		}else {
			WebDriverUtils.runtimeExceptionWithLogs
					("topCategoriesMenuIsDisplayed = " + topCategoriesMenuIsDisplayed + " ER: false;" +
							"\n refineByDropDownIsDisplayed = " + refineByDropDownIsDisplayed + " ER: false;" +
							"\n leftCategoriesMenuIsDisplayed = " + leftCategoriesMenuIsDisplayed + " ER: true;" +
							"\n topSubcategoriesMenuIsDisplayed = " + topSubcategoriesMenuIsDisplayed + " ER: false;" +
							"\n leftSubcategoriesMenuIsDisplayed = " + leftSubcategoriesMenuIsDisplayed + " ER: true;" +
							"\n correctCategoryTabsAreDisplayed = " + correctCategoryTabsAreDisplayed + " ER: true;" +
							"\n correctSubcategoriesAreDisplayed = " + correctSubcategoriesAreDisplayed + " ER: true;" +
							"\n incorrectSubcategoriesAreDisplayed = " + incorrectSubcategoriesAreDisplayed + " ER: false;" +
							"\n isActiveCategoryTabPresent = " + isActiveCategoryTabPresent + " ER: true;" +
							"\n isActiveSubcategoryTabPresent = " + isActiveSubcategoryTabPresent + " ER: true;" +
							"\n correctCategoryTabIsActive = " + correctCategoryTabIsActive + " ER: true;" +
							"\n correctSubcategoryTabIsActive = " + correctSubcategoryTabIsActive + " ER: true;" +
							"\n correctGamesAreDisplayed = " + correctGamesAreDisplayed + " ER: true;" +
							"\n incorrectGamesAreDisplayed = " + incorrectGamesAreDisplayed + " ER: false;" +
							"\n pageURLIsCorrect = " + pageURLIsCorrect + " ER: true;");
		}
	}

	/*3.2.6. Category Tabs (Left): Category without subcategories > Category without subcategories*/
	@Test(groups = {"regression"})
	public void categoryLeftTopNavigationFromCatNoSubcatToCatNoSubcat (){
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_NO_SUBCAT2_RELATIVE_URL);
		boolean topCategoriesMenuIsDisplayed = gamesPortletPage.topCategoryMenuIsPresent();
		boolean refineByDropDownIsDisplayed = gamesPortletPage.refineByDropDownIsPresent();
		boolean leftCategoriesMenuIsDisplayed = gamesPortletPage.leftCategoryMenuIsPresent();
		boolean topSubcategoriesMenuIsDisplayed = gamesPortletPage.topSubcategoryMenuIsPresent();
		boolean leftSubcategoriesMenuIsDisplayed = gamesPortletPage.leftSubcategoryMenuIsPresent();
		boolean correctCategoryTabsAreDisplayed = gamesPortletPage.categoriesMenuIncludesTopCategories ();
		boolean isActiveCategoryTabPresent = gamesPortletPage.isActiveCategoryTabPresent();
		boolean correctTabIsActive = gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_NO_SUBCAT2_RELATIVE_URL);
		ArrayList <String> gamesDisplayed = gamesPortletPage.getAllGameIDs();
		boolean correctGamesAreDisplayed = gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat2(),gamesDisplayed);
		boolean incorrectGamesAreDisplayed =
				(gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat1(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat1(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2(),gamesDisplayed));
		boolean pageURLIsCorrect = gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_NO_SUBCAT2_RELATIVE_URL);
		if (topCategoriesMenuIsDisplayed == false &&
				refineByDropDownIsDisplayed == false &&
				leftCategoriesMenuIsDisplayed == true&&
				topSubcategoriesMenuIsDisplayed == false &&
				leftSubcategoriesMenuIsDisplayed == false &&
				correctCategoryTabsAreDisplayed == true &&
				isActiveCategoryTabPresent == true &&
				correctTabIsActive == true &&
				correctGamesAreDisplayed == true &&
				incorrectGamesAreDisplayed == false &&
				pageURLIsCorrect == true) {
		}else {
			WebDriverUtils.runtimeExceptionWithLogs
					("topCategoriesMenuIsDisplayed = " + topCategoriesMenuIsDisplayed + " ER: false;" +
							"\n refineByDropDownIsDisplayed = " + refineByDropDownIsDisplayed + " ER: false;" +
							"\n leftCategoriesMenuIsDisplayed = " + leftCategoriesMenuIsDisplayed + " ER: true;" +
							"\n topSubcategoriesMenuIsDisplayed = " + topSubcategoriesMenuIsDisplayed + " ER: false;" +
							"\n leftSubcategoriesMenuIsDisplayed = " + leftSubcategoriesMenuIsDisplayed + " ER: false;" +
							"\n correctCategoryTabsAreDisplayed = " + correctCategoryTabsAreDisplayed + " ER: true;" +
							"\n isActiveCategoryTabPresent = " + isActiveCategoryTabPresent + " ER: true;" +
							"\n correctTabIsActive = " + correctTabIsActive + " ER: true;" +
							"\n correctGamesAreDisplayed = " + correctGamesAreDisplayed + " ER: true;" +
							"\n incorrectGamesAreDisplayed = " + incorrectGamesAreDisplayed + " ER: false;" +
							"\n pageURLIsCorrect = " + pageURLIsCorrect + " ER: true;");
		}
	}


	/*3.2.7. Category Tabs (Left): Category without subcategories > Category with subcategories*/
	@Test(groups = {"regression"})
	public void categoryTabsLeftNavigationFromCatNoSubcatToCatSubcat (){
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		boolean topCategoriesMenuIsDisplayed = gamesPortletPage.topCategoryMenuIsPresent();
		boolean refineByDropDownIsDisplayed = gamesPortletPage.refineByDropDownIsPresent();
		boolean leftCategoriesMenuIsDisplayed = gamesPortletPage.leftCategoryMenuIsPresent();
		boolean topSubcategoriesMenuIsDisplayed = gamesPortletPage.topSubcategoryMenuIsPresent();
		boolean leftSubcategoriesMenuIsDisplayed = gamesPortletPage.leftSubcategoryMenuIsPresent();
		boolean correctCategoryTabsAreDisplayed = gamesPortletPage.categoriesMenuIncludesTopCategories ();
		boolean correctSubcategoriesAreDisplayed = gamesPortletPage.childCategoriesForCatSubcat1DisplayedInCategoryTabsMenu();
		boolean isActiveCategoryTabPresent = gamesPortletPage.isActiveCategoryTabPresent();
		boolean isActiveSubcategoryTabPresent = gamesPortletPage.isActiveSubcategoryTabPresent();
		boolean correctTabIsActive = gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		ArrayList <String> gamesDisplayed = gamesPortletPage.getAllGameIDs();
		boolean correctGamesAreDisplayed = gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat1(),gamesDisplayed);
		boolean incorrectGamesAreDisplayed =
				(gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat1(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat2(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2(),gamesDisplayed));
		boolean pageURLIsCorrect = gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		if (topCategoriesMenuIsDisplayed == false &&
				refineByDropDownIsDisplayed == false &&
				leftCategoriesMenuIsDisplayed == true&&
				topSubcategoriesMenuIsDisplayed == false &&
				leftSubcategoriesMenuIsDisplayed == true &&
				correctCategoryTabsAreDisplayed == true &&
				correctSubcategoriesAreDisplayed == true &&
				isActiveCategoryTabPresent == true &&
				isActiveSubcategoryTabPresent == false &&
				correctTabIsActive == true &&
				correctGamesAreDisplayed == true &&
				incorrectGamesAreDisplayed == false &&
				pageURLIsCorrect == true) {
		}else {
			WebDriverUtils.runtimeExceptionWithLogs
					("topCategoriesMenuIsDisplayed = " + topCategoriesMenuIsDisplayed + " ER: false;" +
							"\n refineByDropDownIsDisplayed = " + refineByDropDownIsDisplayed + " ER: false;" +
							"\n leftCategoriesMenuIsDisplayed = " + leftCategoriesMenuIsDisplayed + " ER: true;" +
							"\n topSubcategoriesMenuIsDisplayed = " + topSubcategoriesMenuIsDisplayed + " ER: false;" +
							"\n leftSubcategoriesMenuIsDisplayed = " + leftSubcategoriesMenuIsDisplayed + " ER: true;" +
							"\n correctCategoryTabsAreDisplayed = " + correctCategoryTabsAreDisplayed + " ER: true;" +
							"\n correctSubcategoriesAreDisplayed = " + correctSubcategoriesAreDisplayed + " ER: true;" +
							"\n isActiveCategoryTabPresent = " + isActiveCategoryTabPresent + " ER: true;" +
							"\n isActiveSubcategoryTabPresent = " + isActiveSubcategoryTabPresent + " ER: false;" +
							"\n correctTabIsActive = " + correctTabIsActive + " ER: true;" +
							"\n correctGamesAreDisplayed = " + correctGamesAreDisplayed + " ER: true;" +
							"\n incorrectGamesAreDisplayed = " + incorrectGamesAreDisplayed + " ER: false;" +
							"\n pageURLIsCorrect = " + pageURLIsCorrect + " ER: true;");
		}
	}

	/*3.2.8. Category Tabs (Left): Category with subcategories > Category without subcategories*/
	@Test(groups = {"regression"})
	public void categoryTabsLeftNavigationFromCatSubCatToCatNoScubcat (){
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
		boolean topCategoriesMenuIsDisplayed = gamesPortletPage.topCategoryMenuIsPresent();
		boolean refineByDropDownIsDisplayed = gamesPortletPage.refineByDropDownIsPresent();
		boolean leftCategoriesMenuIsDisplayed = gamesPortletPage.leftCategoryMenuIsPresent();
		boolean topSubcategoriesMenuIsDisplayed = gamesPortletPage.topSubcategoryMenuIsPresent();
		boolean leftSubcategoriesMenuIsDisplayed = gamesPortletPage.leftSubcategoryMenuIsPresent();
		boolean correctCategoryTabsAreDisplayed = gamesPortletPage.categoriesMenuIncludesTopCategories ();
		boolean isActiveCategoryTabPresent = gamesPortletPage.isActiveCategoryTabPresent();
		boolean correctTabIsActive = gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
		ArrayList <String> gamesDisplayed = gamesPortletPage.getAllGameIDs();
		boolean correctGamesAreDisplayed = gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat1(),gamesDisplayed);
		boolean incorrectGamesAreDisplayed =
				(gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat2(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat1(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2(),gamesDisplayed));
		boolean pageURLIsCorrect = gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
		if (topCategoriesMenuIsDisplayed == false &&
				refineByDropDownIsDisplayed == false &&
				leftCategoriesMenuIsDisplayed == true&&
				topSubcategoriesMenuIsDisplayed == false &&
				leftSubcategoriesMenuIsDisplayed == false &&
				correctCategoryTabsAreDisplayed == true &&
				isActiveCategoryTabPresent == true &&
				correctTabIsActive == true &&
				correctGamesAreDisplayed == true &&
				incorrectGamesAreDisplayed == false &&
				pageURLIsCorrect == true) {
		}else {
			WebDriverUtils.runtimeExceptionWithLogs
					("topCategoriesMenuIsDisplayed = " + topCategoriesMenuIsDisplayed + " ER: false;" +
							"\n refineByDropDownIsDisplayed = " + refineByDropDownIsDisplayed + " ER: false;" +
							"\n leftCategoriesMenuIsDisplayed = " + leftCategoriesMenuIsDisplayed + " ER: true;" +
							"\n topSubcategoriesMenuIsDisplayed = " + topSubcategoriesMenuIsDisplayed + " ER: false;" +
							"\n leftSubcategoriesMenuIsDisplayed = " + leftSubcategoriesMenuIsDisplayed + " ER: false;" +
							"\n correctCategoryTabsAreDisplayed = " + correctCategoryTabsAreDisplayed + " ER: true;" +
							"\n isActiveCategoryTabPresent = " + isActiveCategoryTabPresent + " ER: true;" +
							"\n correctTabIsActive = " + correctTabIsActive + " ER: true;" +
							"\n correctGamesAreDisplayed = " + correctGamesAreDisplayed + " ER: true;" +
							"\n incorrectGamesAreDisplayed = " + incorrectGamesAreDisplayed + " ER: false;" +
							"\n pageURLIsCorrect = " + pageURLIsCorrect + " ER: true;");
		}
	}

	/*3.2.9. Category Tabs (Left): Category with subcategories > Category with subcategories*/
	@Test(groups = {"regression"})
	public void categoryTabsLeftNavigationFromCatSubcatToCatSubcat (){
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL);
		boolean topCategoriesMenuIsDisplayed = gamesPortletPage.topCategoryMenuIsPresent();
		boolean refineByDropDownIsDisplayed = gamesPortletPage.refineByDropDownIsPresent();
		boolean leftCategoriesMenuIsDisplayed = gamesPortletPage.leftCategoryMenuIsPresent();
		boolean topSubcategoriesMenuIsDisplayed = gamesPortletPage.topSubcategoryMenuIsPresent();
		boolean leftSubcategoriesMenuIsDisplayed = gamesPortletPage.leftSubcategoryMenuIsPresent();
		boolean correctCategoryTabsAreDisplayed = gamesPortletPage.categoriesMenuIncludesTopCategories ();
		boolean correctSubcategoriesAreDisplayed = gamesPortletPage.childCategoriesForCatSubcat2DisplayedInCategoryTabsMenu();
		boolean isActiveCategoryTabPresent = gamesPortletPage.isActiveCategoryTabPresent();
		boolean isActiveSubcategoryTabPresent = gamesPortletPage.isActiveSubcategoryTabPresent();
		boolean correctTabIsActive = gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL);
		ArrayList <String> gamesDisplayed = gamesPortletPage.getAllGameIDs();
		boolean correctGamesAreDisplayed = gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2(),gamesDisplayed);
		boolean incorrectGamesAreDisplayed =
				(gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat1(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat2(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat1(),gamesDisplayed));
		boolean pageURLIsCorrect = gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL);
		if (topCategoriesMenuIsDisplayed == false &&
				refineByDropDownIsDisplayed == false &&
				leftCategoriesMenuIsDisplayed == true&&
				topSubcategoriesMenuIsDisplayed == false &&
				leftSubcategoriesMenuIsDisplayed == true &&
				correctCategoryTabsAreDisplayed == true &&
				correctSubcategoriesAreDisplayed == true &&
				isActiveCategoryTabPresent == true &&
				isActiveSubcategoryTabPresent == false &&
				correctTabIsActive == true &&
				correctGamesAreDisplayed == true &&
				incorrectGamesAreDisplayed == false &&
				pageURLIsCorrect == true) {
		}else {
			WebDriverUtils.runtimeExceptionWithLogs
					("topCategoriesMenuIsDisplayed = " + topCategoriesMenuIsDisplayed + " ER: false;" +
							"\n refineByDropDownIsDisplayed = " + refineByDropDownIsDisplayed + " ER: false;" +
							"\n leftCategoriesMenuIsDisplayed = " + leftCategoriesMenuIsDisplayed + " ER: true;" +
							"\n topSubcategoriesMenuIsDisplayed = " + topSubcategoriesMenuIsDisplayed + " ER: false;" +
							"\n leftSubcategoriesMenuIsDisplayed = " + leftSubcategoriesMenuIsDisplayed + " ER: true;" +
							"\n correctCategoryTabsAreDisplayed = " + correctCategoryTabsAreDisplayed + " ER: true;" +
							"\n correctSubcategoriesAreDisplayed = " + correctSubcategoriesAreDisplayed + " ER: true;" +
							"\n isActiveCategoryTabPresent = " + isActiveCategoryTabPresent + " ER: true;" +
							"\n isActiveSubcategoryTabPresent = " + isActiveSubcategoryTabPresent + " ER: false;" +
							"\n correctTabIsActive = " + correctTabIsActive + " ER: true;" +
							"\n correctGamesAreDisplayed = " + correctGamesAreDisplayed + " ER: true;" +
							"\n incorrectGamesAreDisplayed = " + incorrectGamesAreDisplayed + " ER: false;" +
							"\n pageURLIsCorrect = " + pageURLIsCorrect + " ER: true;");
		}
	}

	/*3.2.10. Category Tabs (Left): Subcategory >> Parent category*/
	@Test(groups = {"regression"})
	public void categoryTabsLeftNavigationFromSubcategoryToParentCategory (){
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_WITH_SUBCAT1_SUBCAT_C_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		boolean topCategoriesMenuIsDisplayed = gamesPortletPage.topCategoryMenuIsPresent();
		boolean refineByDropDownIsDisplayed = gamesPortletPage.refineByDropDownIsPresent();
		boolean leftCategoriesMenuIsDisplayed = gamesPortletPage.leftCategoryMenuIsPresent();
		boolean topSubcategoriesMenuIsDisplayed = gamesPortletPage.topSubcategoryMenuIsPresent();
		boolean leftSubcategoriesMenuIsDisplayed = gamesPortletPage.leftSubcategoryMenuIsPresent();
		boolean correctCategoryTabsAreDisplayed = gamesPortletPage.categoriesMenuIncludesTopCategories ();
		boolean correctSubcategoriesAreDisplayed = gamesPortletPage.childCategoriesForCatSubcat1DisplayedInCategoryTabsMenu();
		boolean isActiveCategoryTabPresent = gamesPortletPage.isActiveCategoryTabPresent();
		boolean isActiveSubcategoryTabPresent = gamesPortletPage.isActiveSubcategoryTabPresent();
		boolean correctTabIsActive = gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		ArrayList <String> gamesDisplayed = gamesPortletPage.getAllGameIDs();
		boolean correctGamesAreDisplayed = gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat1(),gamesDisplayed);
		boolean incorrectGamesAreDisplayed =
				(gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat1(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatNoSubcat2(),gamesDisplayed)&&
						gamesPortletPage.correctGamesAreDisplayed(gameCategories.getCatSubcat2(),gamesDisplayed));
		boolean pageURLIsCorrect = gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		if (topCategoriesMenuIsDisplayed == false &&
				refineByDropDownIsDisplayed == false &&
				leftCategoriesMenuIsDisplayed == true&&
				topSubcategoriesMenuIsDisplayed == false &&
				leftSubcategoriesMenuIsDisplayed == true &&
				correctCategoryTabsAreDisplayed == true &&
				correctSubcategoriesAreDisplayed == true &&
				isActiveCategoryTabPresent == true &&
				isActiveSubcategoryTabPresent == false &&
				correctTabIsActive == true &&
				correctGamesAreDisplayed == true &&
				incorrectGamesAreDisplayed == false &&
				pageURLIsCorrect == true) {
		}else {
			WebDriverUtils.runtimeExceptionWithLogs
					("topCategoriesMenuIsDisplayed = " + topCategoriesMenuIsDisplayed + " ER: false;" +
							"\n refineByDropDownIsDisplayed = " + refineByDropDownIsDisplayed + " ER: false;" +
							"\n leftCategoriesMenuIsDisplayed = " + leftCategoriesMenuIsDisplayed + " ER: true;" +
							"\n topSubcategoriesMenuIsDisplayed = " + topSubcategoriesMenuIsDisplayed + " ER: false;" +
							"\n leftSubcategoriesMenuIsDisplayed = " + leftSubcategoriesMenuIsDisplayed + " ER: true;" +
							"\n correctCategoryTabsAreDisplayed = " + correctCategoryTabsAreDisplayed + " ER: true;" +
							"\n correctSubcategoriesAreDisplayed = " + correctSubcategoriesAreDisplayed + " ER: true;" +
							"\n isActiveCategoryTabPresent = " + isActiveCategoryTabPresent + " ER: true;" +
							"\n isActiveSubcategoryTabPresent = " + isActiveSubcategoryTabPresent + " ER: false;" +
							"\n correctTabIsActive = " + correctTabIsActive + " ER: true;" +
							"\n correctGamesAreDisplayed = " + correctGamesAreDisplayed + " ER: true;" +
							"\n incorrectGamesAreDisplayed = " + incorrectGamesAreDisplayed + " ER: false;" +
							"\n pageURLIsCorrect = " + pageURLIsCorrect + " ER: true;");
		}
	}

    /*5. Navigation style = None All navigation controls are absent All Games are displayed*/
	@Test(groups = {"regression"})
	public void noneNavigationStyle (){
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleNone);
		boolean allNavigationControlsHidden = gamesPortletPage.allNavigationControlsAreHidden();
		boolean correctGamesAreDisplayed = gamesPortletPage.correctGamesAreDisplayed(gameCategories.getAllGames(), gamesPortletPage.getAllGameIDs());
		boolean pageURLIsCorrect = gamesPortletPage.currentURLIsPageURL(ConfiguredPages.gamesNavigationStyleNone.toString());
		if (allNavigationControlsHidden == true &&
				correctGamesAreDisplayed == true&&
				pageURLIsCorrect == true) {
		} else {
			WebDriverUtils.runtimeExceptionWithLogs(
					"allNavigationControlsHidden = " + allNavigationControlsHidden + " ER: true;" +
							"\n correctGamesAreDisplayed = " + correctGamesAreDisplayed + " ER: true;" +
							"\n pageURLIsCorrect = " + pageURLIsCorrect + " ER: true;");
		}
	}

    /*7. Sort By for Casino games*/
    /*7.1. New - descending by New tag*/
    /*7.3. A-Z - acsending alphabetical*/
    /*7.6. Jackpot Size - descending by JP size*/
	@Test(groups = {"regression"})
	public void sortByAllOptionsWork(){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesStyleOne);
		int gamesPerSlide = gamesPortletPage.getGameItemsNumberOnSlide();
		int gamesTotalNumber = gamesPortletPage.getTotalGameItemsNumber();
//        New sorting
		gamesPortletPage.sortBy(SortBy.New);
		String gameID = gamesPortletPage.getGameName(1);
		GameElement gameElement = new GameElement(gameID);
		boolean isNew = gameElement.isNew();

//        Alphabetical sorting
		gamesPortletPage.sortBy(SortBy.Alphabetical);
		ArrayList<String> gameIDs = new ArrayList<String>();
		boolean isAlphabetical = true;
		for(int i=1; i<=gamesPerSlide; i++){
			gameIDs.add(gamesPortletPage.getGameName(i));
		}
		for (int i=1; i<gameIDs.size();i++){
			if(gameIDs.get(i).compareTo(gameIDs.get(i-1))<0){
				isAlphabetical = false;
			}
		}
//        Jackpots sorting
		int jackpotsCount = gamesPortletPage.getJackpotGamesCount();
		if (jackpotsCount > gamesPerSlide){
			jackpotsCount = gamesPerSlide;
		}
		ArrayList<Double> jackpots = new ArrayList(jackpotsCount);
		gamesPortletPage.sortBy(SortBy.Jackpot);
		boolean isJackpotDesc = true;
		for(int i=1; i<=jackpotsCount; i++){
			gameID = gamesPortletPage.getGameName(i);
			gameElement = new GameElement(gameID);
			jackpots.add(Double.parseDouble(gameElement.getJackpot()));
		}
		for (int i=1; i<jackpots.size();i++){
			if (jackpots.get(i) > jackpots.get(i-1)){
				isJackpotDesc = false;
			}
		}
		Assert.assertTrue(isNew && isJackpotDesc && isAlphabetical);
	}

	/*11. Specific category search on GP with "Use Separate Page for each game category" disabled*/
	/*11.1.1. Search is applied on each char entering and provides correct results*/
	/*11.1.3.  No results are displayed for a search parameter which has no matches with the list of games*/
	@Test(groups = {"regression"})
	public void searchFindsCorrectGameAndNoGamesAreShownForRandomSearch(){
		boolean gamesAreNotShown = false;
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesStyleOne);
		String gameID = gamesPortletPage.getRandomGameName();
		gamesPortletPage = gamesPortletPage.inputSearch(gameID);
		String searchedId = gamesPortletPage.getGameName(1);
		try{
			gamesPortletPage = gamesPortletPage.inputSearch(searchValidationRule.generateValidString());
		}catch(RuntimeException e){
			gamesAreNotShown = true;
		}
		Assert.assertTrue(searchedId.equals(gameID) && gamesAreNotShown);
	}

	/*14. Item View Styles*/
	/*14.1. Style 1 - play, demo, view game info*/
	@Test(groups = {"regression"})
	public void demoAndRealGameCanBeStartedFromItemViewStyleOne(){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.gamesStyleOne, defaultUserData.getRegisteredUserData());
		GameLaunchPopup gameLaunchPopup = gamesPortletPage.playDemo();
		boolean isURLOneCorrect = gameLaunchPopup.isUrlValid();
		gameLaunchPopup.closePopup();
		gameLaunchPopup = (GameLaunchPopup)gamesPortletPage.playReal(true);
		boolean isURLTwoCorrect = gameLaunchPopup.isUrlValid();
		gameLaunchPopup.closePopup();
		GameInfoPopup gameInfoPopup = gamesPortletPage.clickInfo();
		boolean isPopupTitleCorrect = gameInfoPopup.isTitleCorrect();
		gameInfoPopup.clickClose();
		Assert.assertTrue(isURLOneCorrect && isURLTwoCorrect && isPopupTitleCorrect);
	}

	/*14.2. Item View Style 2 - play, demo, view game info*/
	@Test(groups = {"regression"})
	public void demoAndRealGameCanBeStartedFromItemViewStyleTwo(){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.gamesStyleTwo, defaultUserData.getRegisteredUserData());
		GameLaunchPopup gameLaunchPopup = gamesPortletPage.playDemo();
		boolean isURLOneCorrect = gameLaunchPopup.isUrlValid();
		gameLaunchPopup.closePopup();
		gameLaunchPopup = (GameLaunchPopup)gamesPortletPage.playReal(true);
		boolean isURLTwoCorrect = gameLaunchPopup.isUrlValid();
		gameLaunchPopup.closePopup();
		GameInfoPopup gameInfoPopup = gamesPortletPage.clickInfo();
		boolean isPopupTitleCorrect = gameInfoPopup.isTitleCorrect();
		gameInfoPopup.clickClose();
		Assert.assertTrue(isURLOneCorrect && isURLTwoCorrect && isPopupTitleCorrect);
	}

	/*14.3. Item View Style 3 - play, demo, view game info*/
	@Test(groups = {"regression"})
	public void demoAndRealGameCanBeStartedFromItemViewStyleThree(){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.gamesStyleThree, defaultUserData.getRegisteredUserData());
		GameLaunchPopup gameLaunchPopup = gamesPortletPage.playDemo();
		boolean isURLOneCorrect = gameLaunchPopup.isUrlValid();
		gameLaunchPopup.closePopup();
		try{
			gameLaunchPopup = (GameLaunchPopup)gamesPortletPage.playReal(true);
		}catch(RuntimeException e){
			gameLaunchPopup = (GameLaunchPopup)gamesPortletPage.playRealFromTitle(true);
		}
		boolean isURLTwoCorrect = gameLaunchPopup.isUrlValid();
		gameLaunchPopup.closePopup();

		GameInfoPopup gameInfoPopup = gamesPortletPage.clickInfo();
		boolean isPopupTitleCorrect = gameInfoPopup.isTitleCorrect();
		gameInfoPopup.clickClose();
		Assert.assertTrue(isURLOneCorrect && isURLTwoCorrect && isPopupTitleCorrect);
	}

	/*14.4. Item View Style 4 - play, demo, view game info*/
	@Test(groups = {"regression"})
	public void demoAndRealGameCanBeStartedFromItemViewStyleFour(){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.gamesStyleFour, defaultUserData.getRegisteredUserData());
		GameLaunchPopup gameLaunchPopup = gamesPortletPage.playDemo();
		boolean isURLOneCorrect = gameLaunchPopup.isUrlValid();
		gameLaunchPopup.closePopup();

		gameLaunchPopup = (GameLaunchPopup)gamesPortletPage.playReal(true);
		boolean isURLTwoCorrect = gameLaunchPopup.isUrlValid();
		gameLaunchPopup.closePopup();

		GameInfoPopup gameInfoPopup = gamesPortletPage.clickInfo();
		boolean isPopupTitleCorrect = gameInfoPopup.isTitleCorrect();
		gameInfoPopup.clickClose();
		Assert.assertTrue(isURLOneCorrect && isURLTwoCorrect && isPopupTitleCorrect);
	}

	/*15. Navigation types*/
	/*15.1. Slider : next/back arrows navigation*/
	@Test(groups = {"regression"})
	public void sliderPaginationWorksCorrectly(){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesStyleOne);
		String firstPage = gamesPortletPage.getGameName(1);
		gamesPortletPage = gamesPortletPage.clickNextButton();
		String secondPage = gamesPortletPage.getGameName(1);
		gamesPortletPage = gamesPortletPage.clickPreviousButton();
		String firstPageReopened = gamesPortletPage.getGameName(1);
		Assert.assertTrue(firstPage.equals(firstPageReopened) && !firstPage.equals(secondPage));
	}
	/*15.3. Navigation types - To Fit*/
	@Test(groups = {"regression"})
	public void toFitModeIsDisplayedAndGameCanBeStarted(){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.gamesToFit, defaultUserData.getRegisteredUserData());
		GameLaunchPopup gameLaunchPopup = gamesPortletPage.playReal(RandomUtils.generateRandomIntBetween(26, gamesPortletPage.getAllGameNames().size()));
		boolean isURLCorrect = gameLaunchPopup.isUrlValid();
		gameLaunchPopup.closePopup();
		Assert.assertTrue(isURLCorrect);
	}

    /*16. Use Favorites*/
    /*16.1. Player adds games to favorites*/
    /*16.2. Player removes games from favorites*/
	/*17.2. When a game is removed from favorites it disappears from Favorites-only portlet*/
	@Test(groups = {"regression"})
	public void gamesCanBeAddedToFavouritesOnlyPortletAndRemoved(){
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.gamesStyleOne,defaultUserData.getRegisteredUserData());
		final String gameID = gamesPortletPage.getRandomGameName();
		GameElement gameElement = new GameElement(gameID);
		gameElement.clickFavourite();
		boolean isFavourite = gameElement.isFavouriteActive();
        NavigationUtils.navigateToPage(ConfiguredPages.gamesFavourites);
		gameElement = new GameElement(gameID);
		gameElement.clickFavouriteActive();
		gamesPortletPage = new GamesPortletPage();
		boolean isGamePresent = gamesPortletPage.isGamePresent(gameID);

		if (isFavourite == true &&
				isGamePresent == false){
		} else {
			WebDriverUtils.runtimeExceptionWithLogs(
					"isFavourite = " + isFavourite + " ER: true;" +
							"\n isGamePresent = " + isGamePresent + " ER: false;");
		}
	}

	/*22. Switching between Item and gamesList view*/
	@Test(groups = {"regression"})
	public void viewModeButtonIsPresentAndChangesMode(){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesStyleOne);
		String gameId = gamesPortletPage.getRandomGameName();
		GameElement gameElement = new GameElement(gameId);
		boolean present = gameElement.isImagePresent();
		gamesPortletPage = gamesPortletPage.clickListView();
		boolean notPresent = gameElement.isImagePresent();
		gamesPortletPage.clickItemView();
		boolean presentAgain = gameElement.isImagePresent();
		Assert.assertTrue(present && !notPresent && presentAgain);
	}

	/*24. Disable demo mode for logged in player*/
	@Test(groups = {"regression"})
	public void loggedInUserTriesToPlayDisabledDemoNoDemoButtonsFound(){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.gamesMinimum, defaultUserData.getRegisteredUserData());
		boolean isDemoPresent = gamesPortletPage.isDemoPresent();
		Assert.assertTrue(!isDemoPresent);
	}

	/*29.1. Guest launches a game in real mode and faces login popup*/
	@Test(groups = {"regression"})
	public void loggedOutUserTriesToPlayRealGameGetsLogInPrompt(){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.gamesStyleOne);
		LoginPopup loginPopup = (LoginPopup)gamesPortletPage.playReal(false);
		Assert.assertTrue(loginPopup !=null);
	}


	/*30. Player launches game from list view*/
	@Test(groups = {"regression"})
	public void gameCanBeStartedFromListView(){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.gamesStyleOne, defaultUserData.getRegisteredUserData());
		GameLaunchPopup gameLaunchPopup = (GameLaunchPopup)gamesPortletPage./*clickAllGames().*/playReal(true);
		boolean isURLCorrect = gameLaunchPopup.isUrlValid();
		gameLaunchPopup.closePopup();
		Assert.assertTrue(isURLCorrect);
	}

}
