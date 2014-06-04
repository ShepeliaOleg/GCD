import enums.Categories;
import enums.ConfiguredPages;
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
import springConstructors.GameCategories;
import springConstructors.GameControlLabels;
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
		TypeUtils.assertTrueWithLogs(correctGamePopupUrl, "Game url is valid: " + url);
	}

	/*2.1. Refine By: Top level*/
	@Test(groups = {"regression"})
	public void refineByNavigationStyleTopLevel (){
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryMenuIsHidden(),"categoryTabsAreHidden");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.top),"refineByOptionsIncludeTopCategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.first)&&gamesPortletPage.refineByOptionsInclude(Categories.second), "refineByOptionsIncludeSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveRefineByOptionText().equals(gameControlLabels.getRefineByLabel()), "noCategoryIsSelectedInRefineBy");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getAllGames()), "gamesAreDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.isRefineByTextOptionPresent(gameControlLabels.getResetFilterLabel()),"resetFilterOptionIsPresent");
        TypeUtils.assertTrueWithLogs(WebDriverUtils.getCurrentUrl().endsWith(ConfiguredPages.gamesNavigationStyleRefineBy.toString()), "pageURLIsCorrect");
	}

	/*2.2. Refine By: Top > Category without subcategories*/
	@Test(groups = {"regression"})
	public void refineByNavigationFromTopToCategoryWithNoSubcategories () {        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);
		gamesPortletPage.refineBy(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryMenuIsHidden(),"categoryTabsAreHidden");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.top),"refineByOptionsIncludeTopCategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.first)&&gamesPortletPage.refineByOptionsInclude(Categories.second),"refineByOptionsIncludeSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveRefineByOption().equals(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL),"selectedCategoryIsDisplayedInRefineBy");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1()), "correctGamesAreDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2()),"invalidGamesAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isRefineByTextOptionPresent(gameControlLabels.getResetFilterLabel()),"resetFilterOptionIsPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL),"pageURLContainsCategoryPath");
	}

	/*2.3. Refine By: Top > Category with subcategories*/
	@Test(groups = {"regression"})
	public void refineByNavigationFromTopToCategoryWithSubcategories () {        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);
		gamesPortletPage.refineBy(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryMenuIsHidden(),"categoryTabsAreHidden");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.top),"refineByOptionsIncludeTopCategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.first),"refineByOptionsIncludeCorrectSubcategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.second),"refineByOptionsIncludeIncorrectSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveRefineByOptionText().equals(gameControlLabels.getRefineByLabel()),"noCategoryIsSelectedInRefineBy");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1()),"gamesAreDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2()),"invalidGamesAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isRefineByTextOptionPresent(gameControlLabels.getResetFilterLabel()),"resetFilterOptionIsPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL),"pageURLContainsCategoryPath");
	}

	/*2.4. Refine By: Category with subcategories > subcategory*/
	@Test(groups = {"regression"})
	public void refineByNavigationFromCategoryWithSubcategoriesToSubcategory () {
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);
		gamesPortletPage.refineBy(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		gamesPortletPage.refineBy(gamesPortletPage.CAT_WITH_SUBCAT1_SUBCAT_A_RELATIVE_URL);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryMenuIsHidden(),"categoryTabsAreHidden");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.top), "refineByOptionsIncludeTopCategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.first),"refineByOptionsIncludeCorrectSubcategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.second), "refineByOptionsIncludeIncorrectSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveRefineByOption().equals(gamesPortletPage.CAT_WITH_SUBCAT1_SUBCAT_A_RELATIVE_URL),"selectedCategoryIsDisplayedInRefineBy");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1_subcatA()),"gamesAreDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1())&&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2())&&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1_subcatB())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1_subcatC()),"invalidGamesAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isRefineByTextOptionPresent(gameControlLabels.getResetFilterLabel()),"resetFilterOptionIsPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_WITH_SUBCAT1_SUBCAT_A_RELATIVE_URL),"pageURLContainsCategoryPath");
	}

	/*2.5.  Refine By: Subcategory 1 > Subcategory 2*/
	@Test(groups = {"regression"})
	public void refineByNavigationFromSubcategoryToSubcategory (){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);
		gamesPortletPage.refineBy(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		gamesPortletPage.refineBy(gamesPortletPage.CAT_WITH_SUBCAT1_SUBCAT_A_RELATIVE_URL);
		gamesPortletPage.refineBy(gamesPortletPage.CAT_WITH_SUBCAT1_SUBCAT_B_RELATIVE_URL);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryMenuIsHidden(),"categoryTabsAreHidden");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.top), "refineByOptionsIncludeTopCategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.first),"refineByOptionsIncludeCorrectSubcategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.second), "refineByOptionsIncludeIncorrectSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveRefineByOption().equals(gamesPortletPage.CAT_WITH_SUBCAT1_SUBCAT_B_RELATIVE_URL),"selectedCategoryIsDisplayedInRefineBy");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1_subcatB()),"gamesAreDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())&&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1_subcatA())&&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1_subcatC()),"invalidGamesAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isRefineByTextOptionPresent(gameControlLabels.getResetFilterLabel()),"resetFilterOptionIsPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_WITH_SUBCAT1_SUBCAT_B_RELATIVE_URL),"pageURLContainsCategoryPath");
	}

	/*2.6. Refine By: Category without subcategories 1 > Category without subcategories 2*/
	@Test(groups = {"regression"})
	public void refineByNavigationFromCategoryWithoutSubcategoriesToCategoryWithoutSubcategories () {
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);
		gamesPortletPage.refineBy(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
		gamesPortletPage.refineBy(gamesPortletPage.CAT_NO_SUBCAT2_RELATIVE_URL);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryMenuIsHidden(),"categoryTabsAreHidden");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.top),"refineByOptionsIncludeTopCategories");
        TypeUtils.assertFalseWithLogs((gamesPortletPage.refineByOptionsInclude(Categories.first) && gamesPortletPage.refineByOptionsInclude(Categories.second)), "refineByOptionsIncludeSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveRefineByOption().equals(gamesPortletPage.CAT_NO_SUBCAT2_RELATIVE_URL),"selectedCategoryIsDisplayedInRefineBy");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2()),"gamesAreDisplayed");
        TypeUtils.assertFalseWithLogs((gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1())
                && gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1())
                && gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2())), "invalidGamesAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isRefineByTextOptionPresent(gameControlLabels.getResetFilterLabel()),"resetFilterOptionIsPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_NO_SUBCAT2_RELATIVE_URL),"pageURLContainsCategoryPath");
	}

	/*2.7. Refine By: Category without subcategories > Top*/
	@Test(groups = {"regression"})
	public void refineByNavigationFromCategoryWithoutSubcategoriesToTop () {
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);
		gamesPortletPage.refineBy(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
		gamesPortletPage.clickRefineByOptionByText(gameControlLabels.getResetFilterLabel());
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryMenuIsHidden(),"categoryTabsAreHidden");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.top),"refineByOptionsIncludeTopCategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.first)
                &&gamesPortletPage.refineByOptionsInclude(Categories.second),"refineByOptionsIncludeSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveRefineByOptionText().equals(gameControlLabels.getRefineByLabel()),"noCategoryIsSelectedInRefineBy");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getAllGames()),"gamesAreDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.isRefineByTextOptionPresent(gameControlLabels.getResetFilterLabel()),"resetFilterOptionIsPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsPageURL(ConfiguredPages.gamesNavigationStyleRefineBy.toString()),"pageURLIsCorrect");
	}

	/*2.8. Refine By: Category with subcategories > Top*/
	@Test(groups = {"regression"})
	public void refineByNavigationFromCategoryWithSubcategoriesToTop () {
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);
		gamesPortletPage.refineBy(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL);
		gamesPortletPage.clickRefineByOptionByText(gameControlLabels.getResetFilterLabel());
		TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryMenuIsHidden(),"categoryTabsAreHidden");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.top),"refineByOptionsIncludeTopCategories");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.first)
                &&gamesPortletPage.refineByOptionsInclude(Categories.second),"refineByOptionsIncludeSubcategories");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveRefineByOptionText().equals(gameControlLabels.getRefineByLabel()),"noCategoryIsSelectedInRefineBy");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getAllGames()),"gamesAreDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.isRefineByTextOptionPresent(gameControlLabels.getResetFilterLabel()),"resetFilterOptionIsPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsPageURL(ConfiguredPages.gamesNavigationStyleRefineBy.toString()),"pageURLIsCorrect");
	}

	/*2.9. Refine By: Subcategory > Parent category*/
	@Test(groups = {"regression"})
	public void  refineByNavigationFromSubcategoryToParentCategory () {        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);
		gamesPortletPage.refineBy(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		gamesPortletPage.refineBy(gamesPortletPage.CAT_WITH_SUBCAT1_SUBCAT_A_RELATIVE_URL);
		gamesPortletPage.clickRefineByOptionByText(gameControlLabels.getResetFilterLabel());
		TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryMenuIsHidden(),"categoryTabsAreHidden");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.top),"refineByOptionsIncludeTopCategories");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.first),"refineByOptionsIncludeCorrectSubcategories");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.second),"refineByOptionsIncludeIncorrectSubcategories");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveRefineByOptionText().equals(gameControlLabels.getRefineByLabel()),"noCategoryIsSelectedInRefineBy");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1()),"gamesAreDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2()),"invalidGamesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isRefineByTextOptionPresent(gameControlLabels.getResetFilterLabel()),"resetFilterOptionIsPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL),"pageURLContainsCategoryPath");
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
		TypeUtils.assertFalseWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getAllGames()),"gamesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsPageURL(ConfiguredPages.gamesNavigationStyleCategoryTabsTop.toString()),"pageURLIsCorrect");
	}

	/*3.1.2. Category Tabs (Top): Top >> Category without sub-categories*/
	@Test(groups = {"regression"})
	public void categoryTabsTopNavigationFromTopToCategoryWithoutSubcategories (){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL),"correctTabIsActive");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1()),"gamesAreDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2()),"incorrectGamesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL),"pageURLIsCorrect");
	}

	/*3.1.3. Category Tabs (Top): Top >> Category with sub-categories*/
	@Test(groups = {"regression"})
	public void categoryTabsTopNavigationFromTopToCategoryWithSubcategories (){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.first),"correctSubcategoriesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(),"isActiveSubcategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL),"correctTabIsActive");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1()),"gamesAreDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2()),"incorrectGamesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL),"pageURLIsCorrect");
	}


	/*3.1.4. Category Tabs (Top): Category with subcategories > subcategory*/
	@Test(groups = {"regression"})
	public void categoryTabsTopNavigationFromCategoryWithSubcategoriesToSubcategory (){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_A_RELATIVE_URL);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.second),"correctSubcategoriesAreDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.categoryTabsInclude(Categories.first),"incorrectSubcategoriesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(),"isActiveSubcategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL),"correctCategoryTabIsActive");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveSubcategoryTabXp().equals(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_A_RELATIVE_URL),"correctSubcategoryTabIsActive");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2_subcatA()),"gamesAreDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2_subcatB())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2_subcatC())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2_subcatD()),"incorrectGamesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_A_RELATIVE_URL),"pageURLIsCorrect");
	}

	/*3.1.5. Category Tabs (Top): Subcategory > subcategory*/
	@Test(groups = {"regression"})
	public void categoryTabsTopNavigationFromSubcategoryToSubcategory (){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_A_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_B_RELATIVE_URL);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.second),"correctSubcategoriesAreDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.categoryTabsInclude(Categories.first),"incorrectSubcategoriesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(),"isActiveSubcategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL),"correctCategoryTabIsActive");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveSubcategoryTabXp().equals(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_B_RELATIVE_URL),"correctSubcategoryTabIsActive");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2_subcatB()),"gamesAreDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2_subcatA())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2_subcatC())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2_subcatD()),"incorrectGamesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_B_RELATIVE_URL),"pageURLIsCorrect");
	}

	/*3.1.6. Category Tabs (Top): Category without subcategories > Category without subcategories*/
	@Test(groups = {"regression"})
	public void categoryTabsTopNavigationFromCatNoSubcatToCatNoSubcat (){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_NO_SUBCAT2_RELATIVE_URL);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_NO_SUBCAT2_RELATIVE_URL),"correctTabIsActive");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2()),"gamesAreDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2()),"incorrectGamesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_NO_SUBCAT2_RELATIVE_URL),"pageURLIsCorrect");
	}


	/*3.1.7. Category without subcategories > Category with subcategories*/
	@Test(groups = {"regression"})
	public void categoryTabsTopNavigationFromCatNoSubcatToCatSubcat (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.first),"correctSubcategoriesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(),"isActiveSubcategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL),"correctTabIsActive");		
		TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1()),"gamesAreDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2()),"incorrectGamesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL),"pageURLIsCorrect");
	}

	/*3.1.8. Category with subcategories > Category without subcategories*/
	@Test(groups = {"regression"})
	public void categoryTabsTopNavigationFromCatSubCatToCatNoScubcat (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL),"correctTabIsActive");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1()),"gamesAreDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2()),"incorrectGamesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL),"pageURLIsCorrect");
	}

	/*3.1.9. Category with subcategories > Category with subcategories*/
	@Test(groups = {"regression"})
	public void categoryTabsTopNavigationFromCatSubcatToCatSubcat (){        
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.second),"correctSubcategoriesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(),"isActiveSubcategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL),"correctTabIsActive");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2()),"gamesAreDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1()),"incorrectGamesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL),"pageURLIsCorrect");
	}

	/*3.1.10. Category Tabs (Top): Subcategory >> Parent category*/
	@Test(groups = {"regression"})
	public void categoryTabsTopNavigationFromSubcategoryToParentCategory (){        
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_WITH_SUBCAT1_SUBCAT_C_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.first),"correctSubcategoriesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(),"isActiveSubcategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL),"correctTabIsActive");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1()),"gamesAreDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2()),"incorrectGamesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL),"pageURLIsCorrect");
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
		TypeUtils.assertFalseWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getAllGames()),"gamesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsPageURL(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft.toString()),"pageURLIsCorrect");
	}

	/*3.2.2. Category Tabs (Left): Top >> Category without sub-categories*/
	@Test(groups = {"regression"})
	public void categoryTabsLeftNavigationFromTopToCategoryWithoutSubcategories (){        
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL),"correctTabIsActive");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1()),"gamesAreDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2()),"incorrectGamesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL),"pageURLIsCorrect");
	}

	/*3.2.3. Category Tabs (Left): Top >> Category with sub-categories*/
	@Test(groups = {"regression"})
	public void categoryTabsLeftNavigationFromTopToCategoryWithSubcategories (){        
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.first),"correctSubcategoriesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(),"isActiveSubcategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL),"correctTabIsActive");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1()),"gamesAreDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2()),"incorrectGamesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL),"pageURLIsCorrect");
	}


	/*3.2.4. Category Tabs (Left): Category with subcategories > subcategory*/
	@Test(groups = {"regression"})
	public void categoryTabsLeftNavigationFromCategoryWithSubcategoriesToSubcategory (){        
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_A_RELATIVE_URL);
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.second),"correctSubcategoriesAreDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.categoryTabsInclude(Categories.first),"incorrectSubcategoriesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(),"isActiveSubcategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL),"correctCategoryTabIsActive");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveSubcategoryTabXp().equals(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_A_RELATIVE_URL),"correctSubcategoryTabIsActive");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2_subcatA()),"gamesAreDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2_subcatB())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2_subcatC())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2_subcatD()),"incorrectGamesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_A_RELATIVE_URL),"pageURLIsCorrect");
	}

	/*3.2.5. Category Tabs (Left): Subcategory > subcategory*/
	@Test(groups = {"regression"})
	public void categoryTabsLeftNavigationFromSubcategoryToSubcategory (){        
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_A_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_B_RELATIVE_URL);
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.second),"correctSubcategoriesAreDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.categoryTabsInclude(Categories.first),"incorrectSubcategoriesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(),"isActiveSubcategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL),"correctCategoryTabIsActive");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveSubcategoryTabXp().equals(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_B_RELATIVE_URL),"correctSubcategoryTabIsActive");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2_subcatB()),"gamesAreDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2_subcatA())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2_subcatC())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2_subcatD()),"incorrectGamesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_B_RELATIVE_URL),"pageURLIsCorrect");
	}

	/*3.2.6. Category Tabs (Left): Category without subcategories > Category without subcategories*/
	@Test(groups = {"regression"})
	public void categoryLeftTopNavigationFromCatNoSubcatToCatNoSubcat (){        
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_NO_SUBCAT2_RELATIVE_URL);
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_NO_SUBCAT2_RELATIVE_URL),"correctTabIsActive");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2()),"gamesAreDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2()),"incorrectGamesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_NO_SUBCAT2_RELATIVE_URL),"pageURLIsCorrect");
	}


	/*3.2.7. Category Tabs (Left): Category without subcategories > Category with subcategories*/
	@Test(groups = {"regression"})
	public void categoryTabsLeftNavigationFromCatNoSubcatToCatSubcat (){        
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.first),"correctSubcategoriesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(),"isActiveSubcategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL),"correctTabIsActive");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1()),"gamesAreDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2()),"incorrectGamesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL),"pageURLIsCorrect");

	}

	/*3.2.8. Category Tabs (Left): Category with subcategories > Category without subcategories*/
	@Test(groups = {"regression"})
	public void categoryTabsLeftNavigationFromCatSubCatToCatNoScubcat (){        
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL),"correctTabIsActive");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1()),"gamesAreDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                        &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1())
                        &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2()),"incorrectGamesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL),"pageURLIsCorrect");
	}

	/*3.2.9. Category Tabs (Left): Category with subcategories > Category with subcategories*/
	@Test(groups = {"regression"})
	public void categoryTabsLeftNavigationFromCatSubcatToCatSubcat (){        
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL);
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.second),"correctSubcategoriesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(),"isActiveSubcategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL),"correctTabIsActive");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2()),"gamesAreDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1()),"incorrectGamesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL),"pageURLIsCorrect");
	}

	/*3.2.10. Category Tabs (Left): Subcategory >> Parent category*/
	@Test(groups = {"regression"})
	public void categoryTabsLeftNavigationFromSubcategoryToParentCategory (){        
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_WITH_SUBCAT1_SUBCAT_C_RELATIVE_URL);
		gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.first),"correctSubcategoriesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(),"isActiveSubcategoryTabPresent");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL),"correctTabIsActive");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1()),"gamesAreDisplayed");
		TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2()),"incorrectGamesAreDisplayed");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL),"pageURLIsCorrect");
	}

    //    4.1.1. Category Tabs And Refine By (Top): Top
    @Test(groups = {"regression","broken"})
    public void categoryTabsRefineByTopNavigationStyleTopLevel (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByTop);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getAllGames()),"gamesAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsPageURL(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByTop.toString()),"pageURLIsCorrect");
    }


    //    4.1.2. Category Tabs And Refine By (Top): Top >> Category without sub-categories
    @Test(groups = {"regression","broken"})
    public void categoryTabsRefineByTopNavigationFromTopToCategoryWithoutSubcategories (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByTop);
        gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL),"correctTabIsActive");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1()),"gamesAreDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2()),"incorrectGamesAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL),"pageURLIsCorrect");
    }

    //    4.1.3. Category Tabs And Refine By (Top): Top >> Category with sub-categories
    @Test(groups = {"regression","broken"})
    public void categoryTabsRefineByTopNavigationFromTopToCategoryWithSubcategories (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByTop);
        gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByOptionsIncludeTopCategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.top),"refineByOptionsIncludeCorrectSubcategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.first),"refineByOptionsIncludeIncorrectSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.second),"noCategoryIsSelectedInRefineBy");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.getActiveRefineByOptionText().equals(gameControlLabels.getRefineByLabel()),"resetFilterOptionIsPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isRefineByTextOptionPresent(gameControlLabels.getResetFilterLabel()),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL),"correctTabIsActive");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1()),"gamesAreDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2()),"incorrectGamesAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL),"pageURLIsCorrect");
    }

    //    4.1.4. Category Tabs And Refine By (Top): Category with subcategories > subcategory
    @Test(groups = {"regression"})     
    public void categoryTabsRefineByTopNavigationFromCategoryWithSubcategoriesToSubcategory (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByTop);
        gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL);
        gamesPortletPage.refineBy(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_A_RELATIVE_URL);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.top),"refineByOptionsIncludeTopCategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.second),"refineByOptionsIncludeCorrectSubcategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.first),"refineByOptionsIncludeIncorrectSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveRefineByOption().equals(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_A_RELATIVE_URL),"selectedCategoryIsDisplayedInRefineBy");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isRefineByTextOptionPresent(gameControlLabels.getResetFilterLabel()),"resetFilterOptionIsPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL),"correctCategoryTabIsActive");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2_subcatA()),"correctGamesAreDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2_subcatB())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2_subcatC())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2_subcatD()),"incorrectGamesAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_A_RELATIVE_URL),"pageURLIsCorrect");
    }
    /*4.1.5.  Category Tabs And Refine By (Top): Subcategory 1 > Subcategory 2 */
    @Test(groups = {"regression"})     
    public void categoryTabsRefineByTopNavigationFromSubcategoryToSubcategory (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByTop);
        gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
        gamesPortletPage.refineBy(gamesPortletPage.CAT_WITH_SUBCAT1_SUBCAT_B_RELATIVE_URL);
        gamesPortletPage.refineBy(gamesPortletPage.CAT_WITH_SUBCAT1_SUBCAT_C_RELATIVE_URL);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.top),"refineByOptionsIncludeTopCategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.first),"refineByOptionsIncludeCorrectSubcategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.second),"refineByOptionsIncludeIncorrectSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveRefineByOption().equals(gamesPortletPage.CAT_WITH_SUBCAT1_SUBCAT_C_RELATIVE_URL),"selectedCategoryIsDisplayedInRefineBy");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isRefineByTextOptionPresent(gameControlLabels.getResetFilterLabel()),"resetFilterOptionIsPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL),"correctCategoryTabIsActive");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1_subcatC()),"gamesAreDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1_subcatA())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2_subcatB()),"incorrectGamesAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_WITH_SUBCAT1_SUBCAT_C_RELATIVE_URL),"pageURLIsCorrect");
    }

    /* 4.1.6. Category Tabs And Refine By (Top): Category without subcategories 1 > Category without subcategories 2*/
    @Test(groups = {"regression"})     
    public void categoryTabsRefineByTopNavigationFromCatNoSubcatToCatNoSubcat (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByTop);
        gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
        gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_NO_SUBCAT2_RELATIVE_URL);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_NO_SUBCAT2_RELATIVE_URL),"correctTabIsActive");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2()),"gamesAreDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2()),"incorrectGamesAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_NO_SUBCAT2_RELATIVE_URL),"pageURLIsCorrect");
    }

    /* 4.1.7. Category Tabs And Refine By (Top): Category without subcategories > Category with subcategories*/
    @Test(groups = {"regression","broken"})
    public void categoryTabsRefineByTopNavigationFromCatNoSubcatToCatSubCat (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByTop);
        gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_NO_SUBCAT2_RELATIVE_URL);
        gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByOptionsIncludeTopCategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.top),"refineByOptionsIncludeCorrectSubcategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.first),"refineByOptionsIncludeIncorrectSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.second),"noCategoryIsSelectedInRefineBy");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.getActiveRefineByOptionText().equals(gameControlLabels.getRefineByLabel()),"resetFilterOptionIsPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isRefineByTextOptionPresent(gameControlLabels.getResetFilterLabel()),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL),"correctTabIsActive");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1()),"gamesAreDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2()),"incorrectGamesAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL),"pageURLIsCorrect");
    }

    /* 4.1.8. Category Tabs And Refine By (Top): Category with subcategories > Category without subcategories*/
    @Test(groups = {"regression","broken"})
    public void categoryTabsRefineByTopNavigationFromCatSubcatToCatNoSubcat (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByTop);
        gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL);
        gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top), "correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL),"correctTabIsActive");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1()),"gamesAreDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2()),"incorrectGamesAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL),"pageURLIsCorrect");
    }

    /* 4.1.9. Category Tabs And Refine By (Top): Category with subcategories > Category with subcategories*/
    @Test(groups = {"regression","broken"})
    public void categoryTabsRefineByTopNavigationFromCatSubcatToCatSubcat(){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByTop);
        gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL);
        gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByOptionsIncludeTopCategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.top),"refineByOptionsIncludeCorrectSubcategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.first),"refineByOptionsIncludeIncorrectSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.second),"noCategoryIsSelectedInRefineBy");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.getActiveRefineByOptionText().equals(gameControlLabels.getRefineByLabel()),"resetFilterOptionIsPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isRefineByTextOptionPresent(gameControlLabels.getResetFilterLabel()),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL),"correctTabIsActive");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1()),"correctGamesAreDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2()),"incorrectGamesAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL),"pageURLIsCorrect");
    }

    /*4.1.10. Category Tabs And Refine By (Top): Subcategory > Parent category*/
    @Test(groups = {"regression","broken"})
    public void categoryTabsRefineByTopNavigationFromSubcategoryToParentCategory (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByTop);
        gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL);
        gamesPortletPage.refineBy(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_D_RELATIVE_URL);
        gamesPortletPage.clickRefineByOptionByText(gameControlLabels.getResetFilterLabel());
        TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByOptionsIncludeTopCategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.top),"refineByOptionsIncludeCorrectSubcategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.second),"refineByOptionsIncludeIncorrectSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.first),"noCategoryIsSelectedInRefineBy");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.getActiveRefineByOptionText().equals(gameControlLabels.getRefineByLabel()),"resetFilterOptionIsPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isRefineByTextOptionPresent(gameControlLabels.getResetFilterLabel()),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL),"correctTabIsActive");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2()),"gamesAreDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1()),"incorrectGamesAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL),"pageURLIsCorrect");
    }

    //    4.2.1. Category Tabs And Refine By (Left): Top
    @Test(groups = {"regression","broken"})
    public void categoryTabsRefineByLeftNavigationStyleTopLevel (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByLeft);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getAllGames()),"gamesAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsPageURL(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByLeft.toString()),"pageURLIsCorrect");
    }

    //    4.2.2. Category Tabs And Refine By (Left): Top >> Category without sub-categories
    @Test(groups = {"regression","broken"})
    public void categoryTabsRefineByLeftNavigationFromTopToCategoryWithoutSubcategories (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByLeft);
        gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL),"correctTabIsActive");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1()),"gamesAreDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2()),"incorrectGamesAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL),"pageURLIsCorrect");
    }

    //    4.2.3. Category Tabs And Refine By (Left): Top >> Category with sub-categories
    @Test(groups = {"regression","broken"})
    public void categoryTabsRefineByLeftNavigationFromTopToCategoryWithSubcategories (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByLeft);
        gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByOptionsIncludeTopCategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.top),"refineByOptionsIncludeCorrectSubcategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.first),"refineByOptionsIncludeIncorrectSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.second),"noCategoryIsSelectedInRefineBy");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.getActiveRefineByOptionText().equals(gameControlLabels.getRefineByLabel()),"resetFilterOptionIsPresent");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.isRefineByTextOptionPresent(gameControlLabels.getResetFilterLabel()),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL),"correctTabIsActive");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1()),"gamesAreDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2()),"incorrectGamesAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL),"pageURLIsCorrect");
    }

    //    4.2.4. Category Tabs And Refine By (Left): Category with subcategories > subcategory
    @Test(groups = {"regression"})     
    public void categoryTabsRefineByLeftNavigationFromCategoryWithSubcategoriesToSubcategory (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByLeft);
        gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL);
        gamesPortletPage.refineBy(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_A_RELATIVE_URL);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.top),"refineByOptionsIncludeTopCategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.second),"refineByOptionsIncludeCorrectSubcategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.first),"refineByOptionsIncludeIncorrectSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveRefineByOption().equals(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_A_RELATIVE_URL),"selectedCategoryIsDisplayedInRefineBy");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isRefineByTextOptionPresent(gameControlLabels.getResetFilterLabel()),"resetFilterOptionIsPresent");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL),"correctCategoryTabIsActive");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2_subcatA()),"gamesAreDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2_subcatB())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2_subcatC())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2_subcatD()),"incorrectGamesAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_A_RELATIVE_URL),"pageURLIsCorrect");
    }

    /*4.2.5.  Category Tabs And Refine By (Left): Subcategory 1 > Subcategory 2 */
    @Test(groups = {"regression"})     
    public void categoryTabsRefineByLeftNavigationFromSubcategoryToSubcategory (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByLeft);
        gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
        gamesPortletPage.refineBy(gamesPortletPage.CAT_WITH_SUBCAT1_SUBCAT_B_RELATIVE_URL);
        gamesPortletPage.refineBy(gamesPortletPage.CAT_WITH_SUBCAT1_SUBCAT_C_RELATIVE_URL);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.top),"refineByOptionsIncludeTopCategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.first),"refineByOptionsIncludeCorrectSubcategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.second),"refineByOptionsIncludeIncorrectSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveRefineByOption().equals(gamesPortletPage.CAT_WITH_SUBCAT1_SUBCAT_C_RELATIVE_URL),"selectedCategoryIsDisplayedInRefineBy");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isRefineByTextOptionPresent(gameControlLabels.getResetFilterLabel()),"resetFilterOptionIsPresent");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL),"correctCategoryTabIsActive");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1_subcatC()),"gamesAreDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1_subcatA())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2_subcatB()),"incorrectGamesAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_WITH_SUBCAT1_SUBCAT_C_RELATIVE_URL),"pageURLIsCorrect");

    }

    /* 4.2.6. Category Tabs And Refine By (Left): Category without subcategories 1 > Category without subcategories 2*/
    @Test(groups = {"regression","broken"})
    public void categoryTabsRefineByLeftNavigationFromCatNoSubcatToCatNoSubcat (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByLeft);
        gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
        gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_NO_SUBCAT2_RELATIVE_URL);
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_NO_SUBCAT2_RELATIVE_URL),"correctTabIsActive");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2()),"gamesAreDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2()),"incorrectGamesAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_NO_SUBCAT2_RELATIVE_URL),"pageURLIsCorrect");
      }

    /* 4.2.7. Category Tabs And Refine By (Left): Category without subcategories > Category with subcategories*/
    @Test(groups = {"regression","broken"})
    public void categoryTabsRefineByLeftNavigationFromCatNoSubcatToCatSubCat (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByLeft);
        gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_NO_SUBCAT2_RELATIVE_URL);
        gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByOptionsIncludeTopCategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.top),"refineByOptionsIncludeCorrectSubcategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.first),"refineByOptionsIncludeIncorrectSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.second),"noCategoryIsSelectedInRefineBy");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.getActiveRefineByOptionText().equals(gameControlLabels.getRefineByLabel()),"resetFilterOptionIsPresent");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.isRefineByTextOptionPresent(gameControlLabels.getResetFilterLabel()),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL),"correctTabIsActive");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1()),"gamesAreDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2()),"incorrectGamesAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL),"pageURLIsCorrect");
    }

    /* 4.2.8. Category Tabs And Refine By (Left): Category with subcategories > Category without subcategories*/
    @Test(groups = {"regression"})     
    public void categoryTabsRefineByLeftNavigationFromCatSubcatToCatNoSubcat (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByLeft);
        gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL);
        gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL);
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL),"correctTabIsActive");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1()),"gamesAreDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2()),"incorrectGamesAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_NO_SUBCAT1_RELATIVE_URL),"pageURLIsCorrect");
    }

    /* 4.2.9. Category Tabs And Refine By (Left): Category with subcategories > Category with subcategories*/
    @Test(groups = {"regression","broken"})
    public void categoryTabsRefineByLeftNavigationFromCatSubcatToCatSubcat(){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByLeft);
        gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL);
        gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL);
        TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByOptionsIncludeTopCategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.top),"refineByOptionsIncludeCorrectSubcategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.first),"refineByOptionsIncludeIncorrectSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.second),"noCategoryIsSelectedInRefineBy");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.getActiveRefineByOptionText().equals(gameControlLabels.getRefineByLabel()),"resetFilterOptionIsPresent");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.isRefineByTextOptionPresent(gameControlLabels.getResetFilterLabel()),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL),"correctTabIsActive");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1()),"gamesAreDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2()),"incorrectGamesAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_SUBCAT1_RELATIVE_URL),"pageURLIsCorrect");
    }

    /*4.2.10. Category Tabs And Refine By (Left): Subcategory > Parent category*/
    @Test(groups = {"regression","broken"})
    public void categoryTabsRefineByLeftNavigationFromSubcategoryToParentCategory (){        
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByLeft);
        gamesPortletPage.clickCategoryTab(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL);
        gamesPortletPage.refineBy(gamesPortletPage.CAT_WITH_SUBCAT2_SUBCAT_D_RELATIVE_URL);
        gamesPortletPage.clickRefineByOptionByText(gameControlLabels.getResetFilterLabel());
        TypeUtils.assertTrueWithLogs(gamesPortletPage.topCategoryMenuIsPresent(),"refineByDropDownIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByOptionsIncludeTopCategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.top),"refineByOptionsIncludeCorrectSubcategories");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.second),"refineByOptionsIncludeIncorrectSubcategories");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(Categories.first),"noCategoryIsSelectedInRefineBy");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.getActiveRefineByOptionText().equals(gameControlLabels.getRefineByLabel()),"resetFilterOptionIsPresent");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.isRefineByTextOptionPresent(gameControlLabels.getResetFilterLabel()),"topCategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.leftCategoryMenuIsPresent(),"leftCategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.topSubcategoryMenuIsPresent(),"topSubcategoriesMenuIsDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.leftSubcategoryMenuIsPresent(),"leftSubcategoriesMenuIsDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(Categories.top),"correctCategoryTabsAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.getActiveCategoryTabXp().equals(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL),"correctTabIsActive");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat2()),"gamesAreDisplayed");
        TypeUtils.assertFalseWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat1())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatNoSubcat2())
                &&gamesPortletPage.gamesAreDisplayed(gameCategories.getCatSubcat1()),"incorrectGamesAreDisplayed");
        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(gamesPortletPage.CAT_SUBCAT2_RELATIVE_URL),"pageURLIsCorrect");
    }

    /*5. Navigation style = None All navigation controls are absent All Games are displayed*/
	@Test(groups = {"regression"})
	public void noneNavigationStyle (){        
		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleNone);
		TypeUtils.assertTrueWithLogs(gamesPortletPage.allNavigationControlsAreHidden(),"allNavigationControlsHidden");
		TypeUtils.assertTrueWithLogs(gamesPortletPage.gamesAreDisplayed(gameCategories.getAllGames()),"gamesAreDisplayed");
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
