import enums.ConfiguredPages;
import enums.GameCategories;
import enums.PlayerCondition;
import enums.SortBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.account.LoginPopup;
import pageObjects.gamesPortlet.GameElement;
import pageObjects.gamesPortlet.GameInfoPopup;
import pageObjects.gamesPortlet.GameLaunchPopup;
import pageObjects.gamesPortlet.GamesPortletPage;
import springConstructors.UserData;
import springConstructors.validation.ValidationRule;
import testUtils.AbstractTest;
import utils.NavigationUtils;
import utils.RandomUtils;
import utils.TypeUtils;
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

	/*1. Portlet is displayed, game can be launched */
	@Test(groups = {"smoke"})
	public void startFirstAvailableGameInGamePortlet(){
		GamesPortletPage gamesPortletPage = (GamesPortletPage) NavigationUtils.navigateToPage(ConfiguredPages.gamesCasinoPage);
		GameLaunchPopup gameLaunchPopup = gamesPortletPage.playDemo();
        boolean correctGamePopupUrl = gameLaunchPopup.isUrlValid();
		String url = gameLaunchPopup.getWindowUrl();
		gameLaunchPopup.closePopup();
		TypeUtils.assertTrueWithLogs(correctGamePopupUrl, "Game url is valid: " + url);
	}

	/*2.1. Refine By: Top level*/
	@Test(groups = {"regression"})
	public void refineByNavigationStyleTopLevel (){
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryMenuIsHidden(),"categoryTabsAreHidden");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupTop),"refineByOptionsIncludeTopCategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub1, GameCategories.groupSub2), "refineByOptionsIncludeSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isRefineByUnselected(), "noCategoryIsSelectedInRefineBy");
        gamesPortletPage.correctGamesAreDisplayed(GameCategories.groupAll);
        TypeUtils.assertFalseWithLogs(gamesPortletPage.isResetFilterPresent(),"resetFilterOptionIsPresent");
        TypeUtils.assertTrueWithLogs(WebDriverUtils.getCurrentUrl().endsWith(ConfiguredPages.gamesNavigationStyleRefineBy.toString()), "pageURLIsCorrect");
	}

	/*2.2. Refine By: Top > Category without subcategories*/
	@Test(groups = {"regression"})
	public void refineByNavigationFromTopToCategoryWithNoSubcategories () {        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);
		gamesPortletPage.refineBy(GameCategories.cat1No);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryMenuIsHidden(),"categoryTabsAreHidden");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupTop),"refineByOptionsIncludeTopCategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub1, GameCategories.groupSub2),"refineByOptionsIncludeSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveRefineByOption().equals(GameCategories.cat1No),"selectedCategoryIsDisplayedInRefineBy");
        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1No);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isResetFilterPresent(),"resetFilterOptionIsPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1No),"pageURLContainsCategoryPath");
	}

	/*2.3. Refine By: Top > Category with subcategories*/
	@Test(groups = {"regression"})
	public void refineByNavigationFromTopToCategoryWithSubcategories () {        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);
		gamesPortletPage.refineBy(GameCategories.cat1);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryMenuIsHidden(),"categoryTabsAreHidden");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupTop),"refineByOptionsIncludeTopCategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub1),"refineByOptionsIncludeCorrectSubcategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub2),"refineByOptionsIncludeIncorrectSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isRefineByUnselected(),"noCategoryIsSelectedInRefineBy");
        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isResetFilterPresent(),"resetFilterOptionIsPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1),"pageURLContainsCategoryPath");
	}

	/*2.4. Refine By: Category with subcategories > subcategory*/
	@Test(groups = {"regression"})
	public void refineByNavigationFromCategoryWithSubcategoriesToSubcategory () {
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);
		gamesPortletPage.refineBy(GameCategories.cat1);
		gamesPortletPage.refineBy(GameCategories.cat1SubA);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryMenuIsHidden(),"categoryTabsAreHidden");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupTop), "refineByOptionsIncludeTopCategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub1),"refineByOptionsIncludeCorrectSubcategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub2), "refineByOptionsIncludeIncorrectSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveRefineByOption().equals(GameCategories.cat1SubA),"selectedCategoryIsDisplayedInRefineBy");
        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1SubA);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isResetFilterPresent(),"resetFilterOptionIsPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1SubA),"pageURLContainsCategoryPath");
	}

	/*2.5.  Refine By: Subcategory 1 > Subcategory 2*/
	@Test(groups = {"regression"})
	public void refineByNavigationFromSubcategoryToSubcategory (){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);
		gamesPortletPage.refineBy(GameCategories.cat1);
		gamesPortletPage.refineBy(GameCategories.cat1SubA);
		gamesPortletPage.refineBy(GameCategories.cat1SubB);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryMenuIsHidden(),"categoryTabsAreHidden");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupTop), "refineByOptionsIncludeTopCategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub1),"refineByOptionsIncludeCorrectSubcategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub2), "refineByOptionsIncludeIncorrectSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveRefineByOption().equals(GameCategories.cat1SubB),"selectedCategoryIsDisplayedInRefineBy");
        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1SubB);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isResetFilterPresent(),"resetFilterOptionIsPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1SubB),"pageURLContainsCategoryPath");
	}

	/*2.6. Refine By: Category without subcategories 1 > Category without subcategories 2*/
	@Test(groups = {"regression"})
	public void refineByNavigationFromCategoryWithoutSubcategoriesToCategoryWithoutSubcategories () {
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);
		gamesPortletPage.refineBy(GameCategories.cat1No);
		gamesPortletPage.refineBy(GameCategories.cat2No);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryMenuIsHidden(),"categoryTabsAreHidden");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupTop),"refineByOptionsIncludeTopCategories");
        TypeUtils.assertFalseWithLogs((gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub1, GameCategories.groupSub2)), "refineByOptionsIncludeSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveRefineByOption().equals(GameCategories.cat2No),"selectedCategoryIsDisplayedInRefineBy");
        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat2No);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isResetFilterPresent(),"resetFilterOptionIsPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat2No),"pageURLContainsCategoryPath");
	}

	/*2.7. Refine By: Category without subcategories > Top*/
	@Test(groups = {"regression"})
	public void refineByNavigationFromCategoryWithoutSubcategoriesToTop () {
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);
		gamesPortletPage.refineBy(GameCategories.cat1No);
		gamesPortletPage.clickRefineByResetFilter();
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryMenuIsHidden(),"categoryTabsAreHidden");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupTop),"refineByOptionsIncludeTopCategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub1, GameCategories.groupSub2),"refineByOptionsIncludeSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isRefineByUnselected(),"noCategoryIsSelectedInRefineBy");
        gamesPortletPage.correctGamesAreDisplayed(GameCategories.groupAll);
        TypeUtils.assertFalseWithLogs(gamesPortletPage.isResetFilterPresent(),"resetFilterOptionIsPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsPageURL(ConfiguredPages.gamesNavigationStyleRefineBy.toString()),"pageURLIsCorrect");
	}

	/*2.8. Refine By: Category with subcategories > Top*/
	@Test(groups = {"regression"})
	public void refineByNavigationFromCategoryWithSubcategoriesToTop () {
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);
		gamesPortletPage.refineBy(GameCategories.cat2);
		gamesPortletPage.clickRefineByResetFilter();
		TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryMenuIsHidden(),"categoryTabsAreHidden");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupTop),"refineByOptionsIncludeTopCategories");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub1, GameCategories.groupSub2),"refineByOptionsIncludeSubcategories");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isRefineByUnselected(),"noCategoryIsSelectedInRefineBy");
		gamesPortletPage.correctGamesAreDisplayed(GameCategories.groupAll);
		TypeUtils.assertFalseWithLogs(gamesPortletPage.isResetFilterPresent(),"resetFilterOptionIsPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsPageURL(ConfiguredPages.gamesNavigationStyleRefineBy.toString()),"pageURLIsCorrect");
	}

	/*2.9. Refine By: Subcategory > Parent category*/
	@Test(groups = {"regression"})
	public void  refineByNavigationFromSubcategoryToParentCategory () {        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);
		gamesPortletPage.refineBy(GameCategories.cat1);
		gamesPortletPage.refineBy(GameCategories.cat1SubA);
		gamesPortletPage.isResetFilterPresent();
		TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryMenuIsHidden(),"categoryTabsAreHidden");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupTop),"refineByOptionsIncludeTopCategories");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub1),"refineByOptionsIncludeCorrectSubcategories");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub2),"refineByOptionsIncludeIncorrectSubcategories");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isRefineByUnselected(),"noCategoryIsSelectedInRefineBy");
		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isResetFilterPresent(),"resetFilterOptionIsPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1),"pageURLContainsCategoryPath");
	}

	/*3.1.1. Category Tabs (Top): Top*/
	@Test(groups = {"regression"})
	public void categoryTabsTopNavigationStyleTopLevel (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop), "correctCategoryTabsAreDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.isActiveCategoryTabPresent(), "isActiveCategoryTabPresent");
		gamesPortletPage.correctGamesAreDisplayed(GameCategories.groupAll);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsPageURL(ConfiguredPages.gamesNavigationStyleCategoryTabsTop.toString()),"pageURLIsCorrect");
	}

	/*3.1.2. Category Tabs (Top): Top >> Category without sub-categories*/
	@Test(groups = {"regression"})
	public void categoryTabsTopNavigationFromTopToCategoryWithoutSubcategories (){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
		gamesPortletPage.clickCategoryTab(GameCategories.cat1No);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat1No),"correctTabIsActive");
		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1No);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1No),"pageURLIsCorrect");
	}

	/*3.1.3. Category Tabs (Top): Top >> Category with sub-categories*/
	@Test(groups = {"regression"})
	public void categoryTabsTopNavigationFromTopToCategoryWithSubcategories (){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
		gamesPortletPage.clickCategoryTab(GameCategories.cat1);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupSub1),"correctSubcategoriesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(),"isActiveSubcategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat1),"correctTabIsActive");
		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1),"pageURLIsCorrect");
	}


	/*3.1.4. Category Tabs (Top): Category with subcategories > subcategory*/
	@Test(groups = {"regression"})
	public void categoryTabsTopNavigationFromCategoryWithSubcategoriesToSubcategory (){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
		gamesPortletPage.clickCategoryTab(GameCategories.cat2);
		gamesPortletPage.clickCategoryTab(GameCategories.cat2SubA);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupSub2),"correctSubcategoriesAreDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupSub1),"incorrectSubcategoriesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(),"isActiveSubcategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat2),"correctCategoryTabIsActive");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveSubcategoryTabXp().equals(GameCategories.cat2SubA),"correctSubcategoryTabIsActive");
		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat2SubA);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat2SubA),"pageURLIsCorrect");
	}

	/*3.1.5. Category Tabs (Top): Subcategory > subcategory*/
	@Test(groups = {"regression"})
	public void categoryTabsTopNavigationFromSubcategoryToSubcategory (){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
		gamesPortletPage.clickCategoryTab(GameCategories.cat2);
		gamesPortletPage.clickCategoryTab(GameCategories.cat2SubA);
		gamesPortletPage.clickCategoryTab(GameCategories.cat2SubB);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupSub2),"correctSubcategoriesAreDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupSub1),"incorrectSubcategoriesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(),"isActiveSubcategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat2),"correctCategoryTabIsActive");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveSubcategoryTabXp().equals(GameCategories.cat2SubB),"correctSubcategoryTabIsActive");
		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat2SubB);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat2SubB),"pageURLIsCorrect");
	}

	/*3.1.6. Category Tabs (Top): Category without subcategories > Category without subcategories*/
	@Test(groups = {"regression"})
	public void categoryTabsTopNavigationFromCatNoSubcatToCatNoSubcat (){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
		gamesPortletPage.clickCategoryTab(GameCategories.cat1No);
		gamesPortletPage.clickCategoryTab(GameCategories.cat2No);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat2No),"correctTabIsActive");
		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat2No);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat2No),"pageURLIsCorrect");
	}


	/*3.1.7. Category without subcategories > Category with subcategories*/
	@Test(groups = {"regression"})
	public void categoryTabsTopNavigationFromCatNoSubcatToCatSubcat (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
		gamesPortletPage.clickCategoryTab(GameCategories.cat1No);
		gamesPortletPage.clickCategoryTab(GameCategories.cat1);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupSub1),"correctSubcategoriesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(),"isActiveSubcategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat1),"correctTabIsActive");
		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1),"pageURLIsCorrect");
	}

	/*3.1.8. Category with subcategories > Category without subcategories*/
	@Test(groups = {"regression"})
	public void categoryTabsTopNavigationFromCatSubCatToCatNoScubcat (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
		gamesPortletPage.clickCategoryTab(GameCategories.cat2);
		gamesPortletPage.clickCategoryTab(GameCategories.cat1No);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat1No),"correctTabIsActive");
		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1No);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1No),"pageURLIsCorrect");
	}

	/*3.1.9. Category with subcategories > Category with subcategories*/
	@Test(groups = {"regression"})
	public void categoryTabsTopNavigationFromCatSubcatToCatSubcat (){        
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
		gamesPortletPage.clickCategoryTab(GameCategories.cat1);
		gamesPortletPage.clickCategoryTab(GameCategories.cat2);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupSub2),"correctSubcategoriesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(),"isActiveSubcategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat2),"correctTabIsActive");
		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat2);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat2),"pageURLIsCorrect");
	}

	/*3.1.10. Category Tabs (Top): Subcategory >> Parent category*/
	@Test(groups = {"regression"})
	public void categoryTabsTopNavigationFromSubcategoryToParentCategory (){        
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
		gamesPortletPage.clickCategoryTab(GameCategories.cat1);
		gamesPortletPage.clickCategoryTab(GameCategories.cat1SubC);
		gamesPortletPage.clickCategoryTab(GameCategories.cat1);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupSub1),"correctSubcategoriesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(),"isActiveSubcategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat1),"correctTabIsActive");
		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1),"pageURLIsCorrect");
	}

	/*3.2.1. Category Tabs (Left): Top*/
	@Test(groups = {"regression"})
	public void categoryTabsLeftNavigationStyleTopLevel (){        
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop), "correctCategoryTabsAreDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.isActiveCategoryTabPresent(), "isActiveCategoryTabPresent");
		gamesPortletPage.correctGamesAreDisplayed(GameCategories.groupAll);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsPageURL(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft.toString()),"pageURLIsCorrect");
	}

	/*3.2.2. Category Tabs (Left): Top >> Category without sub-categories*/
	@Test(groups = {"regression"})
	public void categoryTabsLeftNavigationFromTopToCategoryWithoutSubcategories (){        
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
		gamesPortletPage.clickCategoryTab(GameCategories.cat1No);
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat1No),"correctTabIsActive");
		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1No);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1No),"pageURLIsCorrect");
	}

	/*3.2.3. Category Tabs (Left): Top >> Category with sub-categories*/
	@Test(groups = {"regression"})
	public void categoryTabsLeftNavigationFromTopToCategoryWithSubcategories (){        
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
		gamesPortletPage.clickCategoryTab(GameCategories.cat1);
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupSub1),"correctSubcategoriesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(),"isActiveSubcategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat1),"correctTabIsActive");
		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1),"pageURLIsCorrect");
	}


	/*3.2.4. Category Tabs (Left): Category with subcategories > subcategory*/
	@Test(groups = {"regression"})
	public void categoryTabsLeftNavigationFromCategoryWithSubcategoriesToSubcategory (){        
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
		gamesPortletPage.clickCategoryTab(GameCategories.cat2);
		gamesPortletPage.clickCategoryTab(GameCategories.cat2SubA);
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupSub2),"correctSubcategoriesAreDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupSub1),"incorrectSubcategoriesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(),"isActiveSubcategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat2),"correctCategoryTabIsActive");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveSubcategoryTabXp().equals(GameCategories.cat2SubA),"correctSubcategoryTabIsActive");
		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat2SubA);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat2SubA),"pageURLIsCorrect");
	}

	/*3.2.5. Category Tabs (Left): Subcategory > subcategory*/
	@Test(groups = {"regression"})
	public void categoryTabsLeftNavigationFromSubcategoryToSubcategory (){        
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
		gamesPortletPage.clickCategoryTab(GameCategories.cat2);
		gamesPortletPage.clickCategoryTab(GameCategories.cat2SubA);
		gamesPortletPage.clickCategoryTab(GameCategories.cat2SubB);
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupSub2),"correctSubcategoriesAreDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupSub1),"incorrectSubcategoriesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(),"isActiveSubcategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat2),"correctCategoryTabIsActive");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveSubcategoryTabXp().equals(GameCategories.cat2SubB),"correctSubcategoryTabIsActive");
		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat2SubB);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat2SubB),"pageURLIsCorrect");
	}

	/*3.2.6. Category Tabs (Left): Category without subcategories > Category without subcategories*/
	@Test(groups = {"regression"})
	public void categoryLeftTopNavigationFromCatNoSubcatToCatNoSubcat (){        
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
		gamesPortletPage.clickCategoryTab(GameCategories.cat1);
		gamesPortletPage.clickCategoryTab(GameCategories.cat2);
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat2No),"correctTabIsActive");
		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat2No);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat2No),"pageURLIsCorrect");
	}


	/*3.2.7. Category Tabs (Left): Category without subcategories > Category with subcategories*/
	@Test(groups = {"regression"})
	public void categoryTabsLeftNavigationFromCatNoSubcatToCatSubcat (){        
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
		gamesPortletPage.clickCategoryTab(GameCategories.cat1No);
		gamesPortletPage.clickCategoryTab(GameCategories.cat1);
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupSub1),"correctSubcategoriesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(),"isActiveSubcategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat1),"correctTabIsActive");
		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1),"pageURLIsCorrect");

	}

	/*3.2.8. Category Tabs (Left): Category with subcategories > Category without subcategories*/
	@Test(groups = {"regression"})
	public void categoryTabsLeftNavigationFromCatSubCatToCatNoScubcat (){        
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
		gamesPortletPage.clickCategoryTab(GameCategories.cat2);
		gamesPortletPage.clickCategoryTab(GameCategories.cat1No);
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat1No),"correctTabIsActive");
		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1No);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1No),"pageURLIsCorrect");
	}

	/*3.2.9. Category Tabs (Left): Category with subcategories > Category with subcategories*/
	@Test(groups = {"regression"})
	public void categoryTabsLeftNavigationFromCatSubcatToCatSubcat (){        
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
		gamesPortletPage.clickCategoryTab(GameCategories.cat1);
		gamesPortletPage.clickCategoryTab(GameCategories.cat2);
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupSub2),"correctSubcategoriesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(),"isActiveSubcategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat2),"correctTabIsActive");
		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat2);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat2),"pageURLIsCorrect");
	}

	/*3.2.10. Category Tabs (Left): Subcategory >> Parent category*/
	@Test(groups = {"regression"})
	public void categoryTabsLeftNavigationFromSubcategoryToParentCategory (){        
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
		gamesPortletPage.clickCategoryTab(GameCategories.cat1);
		gamesPortletPage.clickCategoryTab(GameCategories.cat1SubC);
		gamesPortletPage.clickCategoryTab(GameCategories.cat1);
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupSub1),"correctSubcategoriesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(),"isActiveSubcategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat1),"correctTabIsActive");
		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1),"pageURLIsCorrect");
	}

    //    4.1.1. Category Tabs And Refine By (Top): Top
    @Test(groups = {"regression"})
    public void categoryTabsRefineByTopNavigationStyleTopLevel (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByTop);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        gamesPortletPage.correctGamesAreDisplayed(GameCategories.groupAll);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsPageURL(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByTop.toString()),"pageURLIsCorrect");
    }


    //    4.1.2. Category Tabs And Refine By (Top): Top >> Category without sub-categories
    @Test(groups = {"regression"})
    public void categoryTabsRefineByTopNavigationFromTopToCategoryWithoutSubcategories (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByTop);
        gamesPortletPage.clickCategoryTab(GameCategories.cat1No);
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat1No),"correctTabIsActive");
        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1No);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1No),"pageURLIsCorrect");
    }

    //    4.1.3. Category Tabs And Refine By (Top): Top >> Category with sub-categories
    @Test(groups = {"regression"})
    public void categoryTabsRefineByTopNavigationFromTopToCategoryWithSubcategories (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByTop);
        gamesPortletPage.clickCategoryTab(GameCategories.cat1);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByOptionsIncludeTopCategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupTop),"refineByOptionsIncludeCorrectSubcategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub1),"refineByOptionsIncludeIncorrectSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub2),"noCategoryIsSelectedInRefineBy");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.isRefineByUnselected(),"resetFilterOptionIsPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isResetFilterPresent(),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat1),"correctTabIsActive");
        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1),"pageURLIsCorrect");
    }

    //    4.1.4. Category Tabs And Refine By (Top): Category with subcategories > subcategory
    @Test(groups = {"regression"})     
    public void categoryTabsRefineByTopNavigationFromCategoryWithSubcategoriesToSubcategory (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByTop);
        gamesPortletPage.clickCategoryTab(GameCategories.cat2);
        gamesPortletPage.refineBy(GameCategories.cat2SubA);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupTop),"refineByOptionsIncludeTopCategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub2),"refineByOptionsIncludeCorrectSubcategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub1),"refineByOptionsIncludeIncorrectSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveRefineByOption().equals(GameCategories.cat2SubA),"selectedCategoryIsDisplayedInRefineBy");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isResetFilterPresent(),"resetFilterOptionIsPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat2),"correctCategoryTabIsActive");
        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat2SubA);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat2SubA),"pageURLIsCorrect");
    }
    /*4.1.5.  Category Tabs And Refine By (Top): Subcategory 1 > Subcategory 2 */
    @Test(groups = {"regression"})     
    public void categoryTabsRefineByTopNavigationFromSubcategoryToSubcategory (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByTop);
        gamesPortletPage.clickCategoryTab(GameCategories.cat1);
        gamesPortletPage.refineBy(GameCategories.cat1SubB);
        gamesPortletPage.refineBy(GameCategories.cat1SubC);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupTop),"refineByOptionsIncludeTopCategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub1),"refineByOptionsIncludeCorrectSubcategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub2),"refineByOptionsIncludeIncorrectSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveRefineByOption().equals(GameCategories.cat1SubC),"selectedCategoryIsDisplayedInRefineBy");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isResetFilterPresent(),"resetFilterOptionIsPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat1),"correctCategoryTabIsActive");
        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1SubC);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1SubC),"pageURLIsCorrect");
    }

    /* 4.1.6. Category Tabs And Refine By (Top): Category without subcategories 1 > Category without subcategories 2*/
    @Test(groups = {"regression"})     
    public void categoryTabsRefineByTopNavigationFromCatNoSubcatToCatNoSubcat (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByTop);
        gamesPortletPage.clickCategoryTab(GameCategories.cat1No);
        gamesPortletPage.clickCategoryTab(GameCategories.cat2No);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat2No),"correctTabIsActive");
        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat2No);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat2No),"pageURLIsCorrect");
    }

    /* 4.1.7. Category Tabs And Refine By (Top): Category without subcategories > Category with subcategories*/
    @Test(groups = {"regression"})
    public void categoryTabsRefineByTopNavigationFromCatNoSubcatToCatSubCat (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByTop);
        gamesPortletPage.clickCategoryTab(GameCategories.cat2No);
        gamesPortletPage.clickCategoryTab(GameCategories.cat1);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByOptionsIncludeTopCategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupTop),"refineByOptionsIncludeCorrectSubcategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub1),"refineByOptionsIncludeIncorrectSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub2),"noCategoryIsSelectedInRefineBy");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.isRefineByUnselected(),"resetFilterOptionIsPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isResetFilterPresent(),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat1),"correctTabIsActive");
        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1),"pageURLIsCorrect");
    }

    /* 4.1.8. Category Tabs And Refine By (Top): Category with subcategories > Category without subcategories*/
    @Test(groups = {"regression"})
    public void categoryTabsRefineByTopNavigationFromCatSubcatToCatNoSubcat (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByTop);
        gamesPortletPage.clickCategoryTab(GameCategories.cat2);
        gamesPortletPage.clickCategoryTab(GameCategories.cat1No);
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop), "correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat1No),"correctTabIsActive");
        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1No);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1No),"pageURLIsCorrect");
    }

    /* 4.1.9. Category Tabs And Refine By (Top): Category with subcategories > Category with subcategories*/
    @Test(groups = {"regression"})
    public void categoryTabsRefineByTopNavigationFromCatSubcatToCatSubcat(){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByTop);
        gamesPortletPage.clickCategoryTab(GameCategories.cat2);
        gamesPortletPage.clickCategoryTab(GameCategories.cat1);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByOptionsIncludeTopCategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupTop),"refineByOptionsIncludeCorrectSubcategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub1),"refineByOptionsIncludeIncorrectSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub2),"noCategoryIsSelectedInRefineBy");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.isRefineByUnselected(),"resetFilterOptionIsPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isResetFilterPresent(),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat1),"correctTabIsActive");
        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1),"pageURLIsCorrect");
    }

    /*4.1.10. Category Tabs And Refine By (Top): Subcategory > Parent category*/
    @Test(groups = {"regression"})
    public void categoryTabsRefineByTopNavigationFromSubcategoryToParentCategory (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByTop);
        gamesPortletPage.clickCategoryTab(GameCategories.cat2);
        gamesPortletPage.refineBy(GameCategories.cat2SubD);
        gamesPortletPage.clickRefineByResetFilter();
        TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByOptionsIncludeTopCategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupTop),"refineByOptionsIncludeCorrectSubcategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub2),"refineByOptionsIncludeIncorrectSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub1),"noCategoryIsSelectedInRefineBy");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.isRefineByUnselected(),"resetFilterOptionIsPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isResetFilterPresent(),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat2),"correctTabIsActive");
        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat2);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat2),"pageURLIsCorrect");
    }

    //    4.2.1. Category Tabs And Refine By (Left): Top
    @Test(groups = {"regression"})
    public void categoryTabsRefineByLeftNavigationStyleTopLevel (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByLeft);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        gamesPortletPage.correctGamesAreDisplayed(GameCategories.groupAll);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsPageURL(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByLeft.toString()),"pageURLIsCorrect");
    }

    //    4.2.2. Category Tabs And Refine By (Left): Top >> Category without sub-categories
    @Test(groups = {"regression"})
    public void categoryTabsRefineByLeftNavigationFromTopToCategoryWithoutSubcategories (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByLeft);
        gamesPortletPage.clickCategoryTab(GameCategories.cat1No);
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat1No),"correctTabIsActive");
        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1No);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1No),"pageURLIsCorrect");
    }

    //    4.2.3. Category Tabs And Refine By (Left): Top >> Category with sub-categories
    @Test(groups = {"regression"})
    public void categoryTabsRefineByLeftNavigationFromTopToCategoryWithSubcategories (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByLeft);
        gamesPortletPage.clickCategoryTab(GameCategories.cat1);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByOptionsIncludeTopCategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupTop),"refineByOptionsIncludeCorrectSubcategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub1),"refineByOptionsIncludeIncorrectSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub2),"noCategoryIsSelectedInRefineBy");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.isRefineByUnselected(),"resetFilterOptionIsPresent");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.isResetFilterPresent(),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat1),"correctTabIsActive");
        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1),"pageURLIsCorrect");
    }

    //    4.2.4. Category Tabs And Refine By (Left): Category with subcategories > subcategory
    @Test(groups = {"regression"})     
    public void categoryTabsRefineByLeftNavigationFromCategoryWithSubcategoriesToSubcategory (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByLeft);
        gamesPortletPage.clickCategoryTab(GameCategories.cat2);
        gamesPortletPage.refineBy(GameCategories.cat2SubA);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupTop),"refineByOptionsIncludeTopCategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub2),"refineByOptionsIncludeCorrectSubcategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub1),"refineByOptionsIncludeIncorrectSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveRefineByOption().equals(GameCategories.cat2SubA),"selectedCategoryIsDisplayedInRefineBy");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isResetFilterPresent(),"resetFilterOptionIsPresent");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat2),"correctCategoryTabIsActive");
        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat2SubA);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat2SubA),"pageURLIsCorrect");
    }

    /*4.2.5.  Category Tabs And Refine By (Left): Subcategory 1 > Subcategory 2 */
    @Test(groups = {"regression"})     
    public void categoryTabsRefineByLeftNavigationFromSubcategoryToSubcategory (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByLeft);
        gamesPortletPage.clickCategoryTab(GameCategories.cat1);
        gamesPortletPage.refineBy(GameCategories.cat1SubB);
        gamesPortletPage.refineBy(GameCategories.cat1SubC);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupTop),"refineByOptionsIncludeTopCategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub1),"refineByOptionsIncludeCorrectSubcategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub2),"refineByOptionsIncludeIncorrectSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveRefineByOption().equals(GameCategories.cat1SubC),"selectedCategoryIsDisplayedInRefineBy");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isResetFilterPresent(),"resetFilterOptionIsPresent");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat1),"correctCategoryTabIsActive");
        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1SubC);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1SubC),"pageURLIsCorrect");

    }

    /* 4.2.6. Category Tabs And Refine By (Left): Category without subcategories 1 > Category without subcategories 2*/
    @Test(groups = {"regression"})
    public void categoryTabsRefineByLeftNavigationFromCatNoSubcatToCatNoSubcat (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByLeft);
        gamesPortletPage.clickCategoryTab(GameCategories.cat1No);
        gamesPortletPage.clickCategoryTab(GameCategories.cat2No);
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat2No),"correctTabIsActive");
        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat2No);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat2No),"pageURLIsCorrect");
      }

    /* 4.2.7. Category Tabs And Refine By (Left): Category without subcategories > Category with subcategories*/
    @Test(groups = {"regression"})
    public void categoryTabsRefineByLeftNavigationFromCatNoSubcatToCatSubCat (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByLeft);
        gamesPortletPage.clickCategoryTab(GameCategories.cat2No);
        gamesPortletPage.clickCategoryTab(GameCategories.cat1);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByOptionsIncludeTopCategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupTop),"refineByOptionsIncludeCorrectSubcategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub1),"refineByOptionsIncludeIncorrectSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub2),"noCategoryIsSelectedInRefineBy");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.isRefineByUnselected(),"resetFilterOptionIsPresent");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.isResetFilterPresent(),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat1),"correctTabIsActive");
        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1),"pageURLIsCorrect");
    }

    /* 4.2.8. Category Tabs And Refine By (Left): Category with subcategories > Category without subcategories*/
    @Test(groups = {"regression"})     
    public void categoryTabsRefineByLeftNavigationFromCatSubcatToCatNoSubcat (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByLeft);
        gamesPortletPage.clickCategoryTab(GameCategories.cat2);
        gamesPortletPage.clickCategoryTab(GameCategories.cat1No);
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat1No),"correctTabIsActive");
        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1No);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1No),"pageURLIsCorrect");
    }

    /* 4.2.9. Category Tabs And Refine By (Left): Category with subcategories > Category with subcategories*/
    @Test(groups = {"regression"})
    public void categoryTabsRefineByLeftNavigationFromCatSubcatToCatSubcat(){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByLeft);
        gamesPortletPage.clickCategoryTab(GameCategories.cat2);
        gamesPortletPage.clickCategoryTab(GameCategories.cat1);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByOptionsIncludeTopCategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupTop),"refineByOptionsIncludeCorrectSubcategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub1),"refineByOptionsIncludeIncorrectSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub2),"noCategoryIsSelectedInRefineBy");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.isRefineByUnselected(),"resetFilterOptionIsPresent");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.isResetFilterPresent(),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat1),"correctTabIsActive");
        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1),"pageURLIsCorrect");
    }

    /*4.2.10. Category Tabs And Refine By (Left): Subcategory > Parent category*/
    @Test(groups = {"regression"})
    public void categoryTabsRefineByLeftNavigationFromSubcategoryToParentCategory (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByLeft);
        gamesPortletPage.clickCategoryTab(GameCategories.cat2);
        gamesPortletPage.refineBy(GameCategories.cat2SubD);
        gamesPortletPage.clickRefineByResetFilter();
        TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByOptionsIncludeTopCategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupTop),"refineByOptionsIncludeCorrectSubcategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub2),"refineByOptionsIncludeIncorrectSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(GameCategories.groupSub1),"noCategoryIsSelectedInRefineBy");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.isRefineByUnselected(),"resetFilterOptionIsPresent");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.isResetFilterPresent(),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(GameCategories.cat2),"correctTabIsActive");
        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat2);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat2),"pageURLIsCorrect");
    }

    /*5. Navigation style = None All navigation controls are absent All Games are displayed*/
	@Test(groups = {"regression"})
	public void noneNavigationStyle (){        
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleNone);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.allNavigationControlsAreHidden(),"allNavigationControlsHidden");
		gamesPortletPage.correctGamesAreDisplayed(GameCategories.groupAll);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsPageURL(ConfiguredPages.gamesNavigationStyleNone.toString()),"pageURLIsCorrect");
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
        TypeUtils.assertTrueWithLogs(gameElement.isNew(),"is sorted by New");

//        Alphabetical sorting
		gamesPortletPage.sortBy(SortBy.Alphabetical);
		ArrayList<String> gameIDs = new ArrayList<String>();
        boolean isAlphabetical =  true;
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
		TypeUtils.assertTrueWithLogs(isJackpotDesc,"is sorted by jackpots");
        TypeUtils.assertTrueWithLogs(isAlphabetical,"is sorted alphabetically");
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
		TypeUtils.assertTrueWithLogs(searchedId.equals(gameID),"Search correct");
        TypeUtils.assertTrueWithLogs(gamesAreNotShown, "games are not shown for wrong search");
	}

	/*14. Item View Styles*/
	/*14.1. Style 1 - play, demo, view game info*/
	@Test(groups = {"regression"})
	public void demoAndRealGameCanBeStartedFromItemViewStyleOne(){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.gamesStyleOne, defaultUserData.getRegisteredUserData());
		GameLaunchPopup gameLaunchPopup = gamesPortletPage.playDemo();
        boolean correctGamePopupUrl = gameLaunchPopup.isUrlValid();
		gameLaunchPopup.closePopup();
        TypeUtils.assertTrueWithLogs(correctGamePopupUrl, "Game url 1 is valid");
		gameLaunchPopup = (GameLaunchPopup)gamesPortletPage.playReal(true);
        correctGamePopupUrl = gameLaunchPopup.isUrlValid();
		gameLaunchPopup.closePopup();
        TypeUtils.assertTrueWithLogs(correctGamePopupUrl, "Game url 2 is valid");
		GameInfoPopup gameInfoPopup = gamesPortletPage.clickInfo();
        TypeUtils.assertTrueWithLogs(gameInfoPopup.isTitleCorrect(), "is popup title correct");
		gameInfoPopup.clickClose();
	}

	/*14.2. Item View Style 2 - play, demo, view game info*/
	@Test(groups = {"regression"})
	public void demoAndRealGameCanBeStartedFromItemViewStyleTwo(){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.gamesStyleTwo, defaultUserData.getRegisteredUserData());
		GameLaunchPopup gameLaunchPopup = gamesPortletPage.playDemo();
        boolean correctGamePopupUrl = gameLaunchPopup.isUrlValid();
        gameLaunchPopup.closePopup();
        TypeUtils.assertTrueWithLogs(correctGamePopupUrl, "Game url 1 is valid");
		gameLaunchPopup = (GameLaunchPopup)gamesPortletPage.playReal(true);
        correctGamePopupUrl = gameLaunchPopup.isUrlValid();
		gameLaunchPopup.closePopup();
        TypeUtils.assertTrueWithLogs(correctGamePopupUrl, "Game url 2 is valid");
		GameInfoPopup gameInfoPopup = gamesPortletPage.clickInfo();
        TypeUtils.assertTrueWithLogs(gameInfoPopup.isTitleCorrect(), "is popup title correct");
		gameInfoPopup.clickClose();
	}

	/*14.3. Item View Style 3 - play, demo, view game info*/
	@Test(groups = {"regression"})
	public void demoAndRealGameCanBeStartedFromItemViewStyleThree(){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.gamesStyleThree, defaultUserData.getRegisteredUserData());
		GameLaunchPopup gameLaunchPopup = gamesPortletPage.playDemo();
        boolean correctGamePopupUrl = gameLaunchPopup.isUrlValid();
		gameLaunchPopup.closePopup();
        TypeUtils.assertTrueWithLogs(correctGamePopupUrl, "Game url 1 is valid");
		try{
			gameLaunchPopup = (GameLaunchPopup)gamesPortletPage.playReal(true);
		}catch(RuntimeException e){
			gameLaunchPopup = (GameLaunchPopup)gamesPortletPage.playRealFromTitle(true);
		}
        correctGamePopupUrl = gameLaunchPopup.isUrlValid();
		gameLaunchPopup.closePopup();
        TypeUtils.assertTrueWithLogs(correctGamePopupUrl, "Game url 2 is valid");
		GameInfoPopup gameInfoPopup = gamesPortletPage.clickInfo();
        TypeUtils.assertTrueWithLogs(gameInfoPopup.isTitleCorrect(), "is popup title correct");
		gameInfoPopup.clickClose();
	}

	/*14.4. Item View Style 4 - play, demo, view game info*/
	@Test(groups = {"regression"})
	public void demoAndRealGameCanBeStartedFromItemViewStyleFour(){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.gamesStyleFour, defaultUserData.getRegisteredUserData());
		GameLaunchPopup gameLaunchPopup = gamesPortletPage.playDemo();
        boolean correctGamePopupUrl = gameLaunchPopup.isUrlValid();
		gameLaunchPopup.closePopup();
        TypeUtils.assertTrueWithLogs(correctGamePopupUrl, "Game url 1 is valid");
		gameLaunchPopup = (GameLaunchPopup)gamesPortletPage.playReal(true);
        correctGamePopupUrl = gameLaunchPopup.isUrlValid();
		gameLaunchPopup.closePopup();
        TypeUtils.assertTrueWithLogs(correctGamePopupUrl, "Game url 2 is valid");
		GameInfoPopup gameInfoPopup = gamesPortletPage.clickInfo();
        TypeUtils.assertTrueWithLogs(gameInfoPopup.isTitleCorrect(), "is popup title correct");
		gameInfoPopup.clickClose();
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
		TypeUtils.assertTrueWithLogs(firstPage.equals(firstPageReopened),"Is back at first page");
        TypeUtils.assertFalseWithLogs(firstPage.equals(secondPage),"Switched to second page");
	}
	/*15.3. Navigation types - To Fit*/
	@Test(groups = {"regression"})
	public void toFitModeIsDisplayedAndGameCanBeStarted(){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.gamesToFit, defaultUserData.getRegisteredUserData());
		GameLaunchPopup gameLaunchPopup = gamesPortletPage.playReal(RandomUtils.generateRandomIntBetween(26, gamesPortletPage.getAllGameNames().size()));
        boolean correctGamePopupUrl = gameLaunchPopup.isUrlValid();
		gameLaunchPopup.closePopup();
        TypeUtils.assertTrueWithLogs(correctGamePopupUrl, "Game url is valid");
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
        TypeUtils.assertTrueWithLogs(gameElement.isFavouriteActive(),"Is favorite");
        NavigationUtils.navigateToPage(ConfiguredPages.gamesFavourites);
		gameElement = new GameElement(gameID);
		gameElement.clickFavouriteActive();
		gamesPortletPage = new GamesPortletPage();
        TypeUtils.assertFalseWithLogs(gamesPortletPage.isGamePresent(gameID),"is still favorite");
	}

	/*22. Switching between Item and gamesList view*/
	@Test(groups = {"regression"})
	public void viewModeButtonIsPresentAndChangesMode(){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesStyleOne);
		String gameId = gamesPortletPage.getRandomGameName();
		GameElement gameElement = new GameElement(gameId);
        TypeUtils.assertTrueWithLogs(gameElement.isImagePresent(),"image view");
		gamesPortletPage = gamesPortletPage.clickListView();
        TypeUtils.assertFalseWithLogs(gameElement.isImagePresent(),"list view");
		gamesPortletPage.clickItemView();
		TypeUtils.assertTrueWithLogs(gameElement.isImagePresent(),"image view after list");
	}

	/*24. Disable demo mode for logged in player*/
	@Test(groups = {"regression"})
	public void loggedInUserTriesToPlayDisabledDemoNoDemoButtonsFound(){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.gamesMinimum, defaultUserData.getRegisteredUserData());
        TypeUtils.assertFalseWithLogs(gamesPortletPage.isDemoPresent(),"demo buttons present");
	}

	/*29.1. Guest launches a game in real mode and faces login popup*/
	@Test(groups = {"regression"})
	public void loggedOutUserTriesToPlayRealGameGetsLogInPrompt(){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.gamesStyleOne);
		LoginPopup loginPopup = (LoginPopup)gamesPortletPage.playReal(false);
	}


	/*30. Player launches game from list view*/
	@Test(groups = {"regression"})
	public void gameCanBeStartedFromListView(){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.gamesStyleOne, defaultUserData.getRegisteredUserData());
		GameLaunchPopup gameLaunchPopup = (GameLaunchPopup)gamesPortletPage./*clickAllGames().*/playReal(true);
        boolean correctGamePopupUrl = gameLaunchPopup.isUrlValid();
		gameLaunchPopup.closePopup();
        TypeUtils.assertTrueWithLogs(correctGamePopupUrl, "Game url is valid");
	}

}
