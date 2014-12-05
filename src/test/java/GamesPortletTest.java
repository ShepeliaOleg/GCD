import enums.ConfiguredPages;
import enums.GameCategories;
import enums.Page;
import enums.PlayerCondition;
import org.testng.annotations.Test;
import pageObjects.gamesPortlet.*;
import pageObjects.login.LoginPopup;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

public class GamesPortletTest extends AbstractTest {

	/*1. Portlet is displayed, game can be launched */
	@Test(groups = {"regression", "smoke"})
	public void startFirstAvailableGameInGamePortlet(){
        try{
            GamesPortletPage gamesPortletPage = (GamesPortletPage) NavigationUtils.navigateToPage(ConfiguredPages.gamesCasinoPage);
            gamesPortletPage.playDemoAndAssertUrl();
        }catch (Exception e){
            skipTest();
        }
    }

//	/*2.1. Refine By: Top level*/
//	@Test(groups = {"regression"})
//	public void refineByNavigationStyleTopLevel (){
//		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryMenuIsHidden(),"categoryTabsAreHidden");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(true, GameCategories.groupTop),"refineByOptionsIncludeTopCategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(false, GameCategories.groupSub1, GameCategories.groupSub2), "refineByOptionsIncludeSubcategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isRefineByUnselected(), "noCategoryIsSelectedInRefineBy");
//        gamesPortletPage.correctGamesAreDisplayed(GameCategories.groupAll);
//        TypeUtils.assertFalseWithLogs(gamesPortletPage.isResetFilterPresent(),"resetFilterOptionIsPresent");
//        TypeUtils.assertTrueWithLogs(WebDriverUtils.getCurrentUrl().endsWith(ConfiguredPages.gamesNavigationStyleRefineBy.toString()), "pageURLIsCorrect");
//	}
//
//	/*2.2. Refine By: Top > Category without subcategories*/
//	@Test(groups = {"regression"})
//	public void refineByNavigationFromTopToCategoryWithNoSubcategories () {
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);
//		gamesPortletPage.refineBy(GameCategories.cat1No);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryMenuIsHidden(),"categoryTabsAreHidden");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(true, GameCategories.groupTop),"refineByOptionsIncludeTopCategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(false, GameCategories.groupSub1, GameCategories.groupSub2),"refineByOptionsIncludeSubcategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveRefineByOption(GameCategories.cat1No),"selectedCategoryIsDisplayedInRefineBy");
//        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1No);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isResetFilterPresent(),"resetFilterOptionIsPresent");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1No),"pageURLContainsCategoryPath");
//	}
//
//	/*2.3. Refine By: Top > Category with subcategories*/
//	@Test(groups = {"regression"})
//	public void refineByNavigationFromTopToCategoryWithSubcategories () {
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);
//		gamesPortletPage.refineBy(GameCategories.cat1);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryMenuIsHidden(),"categoryTabsAreHidden");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(true, GameCategories.groupSub1),"refineByOptionsIncludeCorrectSubcategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(false, GameCategories.groupSub2, GameCategories.groupTop),"refineByOptionsIncludeIncorrectSubcategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isRefineByUnselected(),"noCategoryIsSelectedInRefineBy");
//        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isResetFilterPresent(),"resetFilterOptionIsPresent");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1),"pageURLContainsCategoryPath");
//	}
//
//	/*2.4. Refine By: Category with subcategories > subcategory*/
//	@Test(groups = {"regression"})
//	public void refineByNavigationFromCategoryWithSubcategoriesToSubcategory () {
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);
//		gamesPortletPage.refineBy(GameCategories.cat1);
//		gamesPortletPage.refineBy(GameCategories.cat1SubA);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryMenuIsHidden(),"categoryTabsAreHidden");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(true, GameCategories.groupSub1),"refineByOptionsIncludeCorrectSubcategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(false, GameCategories.groupSub2, GameCategories.groupTop), "refineByOptionsIncludeIncorrectSubcategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveRefineByOption(GameCategories.cat1SubA),"selectedCategoryIsDisplayedInRefineBy");
//        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1SubA);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isResetFilterPresent(),"resetFilterOptionIsPresent");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1SubA),"pageURLContainsCategoryPath");
//	}
//
//	/*2.5.  Refine By: Subcategory 1 > Subcategory 2*/
//	@Test(groups = {"regression"})
//	public void refineByNavigationFromSubcategoryToSubcategory (){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);
//		gamesPortletPage.refineBy(GameCategories.cat1);
//		gamesPortletPage.refineBy(GameCategories.cat1SubA);
//		gamesPortletPage.refineBy(GameCategories.cat1SubB);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryMenuIsHidden(),"categoryTabsAreHidden");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(true, GameCategories.groupSub1),"refineByOptionsIncludeCorrectSubcategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(false, GameCategories.groupTop, GameCategories.groupSub2), "refineByOptionsIncludeTopCategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveRefineByOption(GameCategories.cat1SubB),"selectedCategoryIsDisplayedInRefineBy");
//        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1SubB);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isResetFilterPresent(),"resetFilterOptionIsPresent");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1SubB),"pageURLContainsCategoryPath");
//	}
//
//	/*2.6. Refine By: Category without subcategories 1 > Category without subcategories 2*/
//	@Test(groups = {"regression"})
//	public void refineByNavigationFromCategoryWithoutSubcategoriesToCategoryWithoutSubcategories () {
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);
//		gamesPortletPage.refineBy(GameCategories.cat1No);
//		gamesPortletPage.refineBy(GameCategories.cat2No);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryMenuIsHidden(),"categoryTabsAreHidden");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(true, GameCategories.groupTop),"refineByOptionsIncludeTopCategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(false, GameCategories.groupSub1, GameCategories.groupSub2), "refineByOptionsIncludeSubcategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveRefineByOption(GameCategories.cat2No),"selectedCategoryIsDisplayedInRefineBy");
//        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat2No);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isResetFilterPresent(),"resetFilterOptionIsPresent");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat2No),"pageURLContainsCategoryPath");
//	}
//
//	/*2.7. Refine By: Category without subcategories > Top*/
//	@Test(groups = {"regression"})
//	public void refineByNavigationFromCategoryWithoutSubcategoriesToTop () {
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);
//		gamesPortletPage.refineBy(GameCategories.cat1No);
//		gamesPortletPage.clickRefineByResetFilter();
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryMenuIsHidden(),"categoryTabsAreHidden");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(true, GameCategories.groupTop),"refineByOptionsIncludeTopCategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(false, GameCategories.groupSub1, GameCategories.groupSub2),"refineByOptionsIncludeSubcategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isRefineByUnselected(),"noCategoryIsSelectedInRefineBy");
//        gamesPortletPage.correctGamesAreDisplayed(GameCategories.groupAll);
//        TypeUtils.assertFalseWithLogs(gamesPortletPage.isResetFilterPresent(),"resetFilterOptionIsPresent");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsPageURL(ConfiguredPages.gamesNavigationStyleRefineBy.toString()),"pageURLIsCorrect");
//	}
//
//	/*2.8. Refine By: Category with subcategories > Top*/
//	@Test(groups = {"regression"})
//	public void refineByNavigationFromCategoryWithSubcategoriesToTop () {
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);
//		gamesPortletPage.refineBy(GameCategories.cat2);
//		gamesPortletPage.clickRefineByResetFilter();
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryMenuIsHidden(),"categoryTabsAreHidden");
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(true, GameCategories.groupTop),"refineByOptionsIncludeTopCategories");
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(false, GameCategories.groupSub1, GameCategories.groupSub2),"refineByOptionsIncludeSubcategories");
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.isRefineByUnselected(),"noCategoryIsSelectedInRefineBy");
//		gamesPortletPage.correctGamesAreDisplayed(GameCategories.groupAll);
//		TypeUtils.assertFalseWithLogs(gamesPortletPage.isResetFilterPresent(),"resetFilterOptionIsPresent");
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsPageURL(ConfiguredPages.gamesNavigationStyleRefineBy.toString()),"pageURLIsCorrect");
//	}
//
//	/*2.9. Refine By: Subcategory > Parent category*/
//	@Test(groups = {"regression"})
//	public void  refineByNavigationFromSubcategoryToParentCategory () {
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleRefineBy);
//		gamesPortletPage.refineBy(GameCategories.cat1);
//		gamesPortletPage.refineBy(GameCategories.cat1SubA);
//        gamesPortletPage.clickRefineByResetFilter();
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByDropDownIsPresent(),"refineByDropDownIsDisplayed");
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryMenuIsHidden(),"categoryTabsAreHidden");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(true, GameCategories.groupSub1),"refineByOptionsIncludeCorrectSubcategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(false, GameCategories.groupTop, GameCategories.groupSub2),"refineByOptionsIncludeTopCategories");
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.isRefineByUnselected(),"isRefineByUnselected");
//		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.isResetFilterPresent(),"resetFilterOptionIsPresent");
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1),"pageURLContainsCategoryPath");
//	}
//
//	/*3.1.1. Category Tabs (Top): Top*/
//	@Test(groups = {"regression"})
//	public void categoryTabsTopNavigationStyleTopLevel (){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
//        gamesPortletPage.checkNavigation(false, true, false, false, false);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop), "correctCategoryTabsAreDisplayed");
//		TypeUtils.assertFalseWithLogs(gamesPortletPage.isActiveCategoryTabPresent(), "isActiveCategoryTabPresent");
//		gamesPortletPage.correctGamesAreDisplayed(GameCategories.groupAll);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsPageURL(ConfiguredPages.gamesNavigationStyleCategoryTabsTop.toString()),"pageURLIsCorrect");
//	}
//
//	/*3.1.2. Category Tabs (Top): Top >> Category without sub-categories*/
//	@Test(groups = {"regression"})
//	public void categoryTabsTopNavigationFromTopToCategoryWithoutSubcategories (){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat1No);
//        gamesPortletPage.checkNavigation(false, true, false, false, false);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat1No),"correctTabIsActive");
//		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1No);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1No),"pageURLIsCorrect");
//	}
//
//	/*3.1.3. Category Tabs (Top): Top >> Category with sub-categories*/
//	@Test(groups = {"regression"})
//	public void categoryTabsTopNavigationFromTopToCategoryWithSubcategories (){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat1);
//gamesPortletPage.checkNavigation(false, true, true, false, false);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop, GameCategories.groupSub1),"correctCategoryTabsAreDisplayed");
//		TypeUtils.assertFalseWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(),"isActiveSubcategoryTabPresent");
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat1),"correctTabIsActive");
//		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1),"pageURLIsCorrect");
//	}
//
//
//	/*3.1.4. Category Tabs (Top): Category with subcategories > subcategory*/
//	@Test(groups = {"regression"})
//	public void categoryTabsTopNavigationFromCategoryWithSubcategoriesToSubcategory (){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat2);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat2SubA);
//        gamesPortletPage.checkNavigation(false, true, true, false, false);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop, GameCategories.groupSub2),"correctCategoryTabsAreDisplayed");
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(false, GameCategories.groupSub1),"incorrectSubcategoriesAreDisplayed");
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat2),"correctCategoryTabIsActive");
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(GameCategories.cat2SubA),"correctSubcategoryTabIsActive");
//		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat2SubA);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat2SubA),"pageURLIsCorrect");
//	}
//
//	/*3.1.5. Category Tabs (Top): Subcategory > subcategory*/
//	@Test(groups = {"regression"})
//	public void categoryTabsTopNavigationFromSubcategoryToSubcategory (){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat2);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat2SubA);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat2SubB);
//        gamesPortletPage.checkNavigation(false, true, true, false, false);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop, GameCategories.groupSub2),"correctCategoryTabsAreDisplayed");
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(false, GameCategories.groupSub1),"incorrectSubcategoriesAreDisplayed");
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat2),"correctCategoryTabIsActive");
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(GameCategories.cat2SubB),"correctSubcategoryTabIsActive");
//		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat2SubB);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat2SubB),"pageURLIsCorrect");
//	}
//
//	/*3.1.6. Category Tabs (Top): Category without subcategories > Category without subcategories*/
//	@Test(groups = {"regression"})
//	public void categoryTabsTopNavigationFromCatNoSubcatToCatNoSubcat (){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat1No);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat2No);
//        gamesPortletPage.checkNavigation(false, true, false, false, false);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat2No),"correctTabIsActive");
//		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat2No);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat2No),"pageURLIsCorrect");
//	}
//
//
//	/*3.1.7. Category without subcategories > Category with subcategories*/
//	@Test(groups = {"regression"})
//	public void categoryTabsTopNavigationFromCatNoSubcatToCatSubcat (){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat1No);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat1);
//        gamesPortletPage.checkNavigation(false, true, true, false, false);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop, GameCategories.groupSub1),"correctCategoryTabsAreDisplayed");
//		TypeUtils.assertFalseWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(),"isActiveSubcategoryTabPresent");
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat1),"correctTabIsActive");
//		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1),"pageURLIsCorrect");
//	}
//
//	/*3.1.8. Category with subcategories > Category without subcategories*/
//	@Test(groups = {"regression"})
//	public void categoryTabsTopNavigationFromCatSubCatToCatNoScubcat (){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat2);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat1No);
//        gamesPortletPage.checkNavigation(false, true, false, false, false);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat1No),"correctTabIsActive");
//		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1No);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1No),"pageURLIsCorrect");
//	}
//
//	/*3.1.9. Category with subcategories > Category with subcategories*/
//	@Test(groups = {"regression"})
//	public void categoryTabsTopNavigationFromCatSubcatToCatSubcat (){
//		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat1);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat2);
//        gamesPortletPage.checkNavigation(false, true, true, false, false);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop, GameCategories.groupSub2),"correctCategoryTabsAreDisplayed");
//		TypeUtils.assertFalseWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(),"isActiveSubcategoryTabPresent");
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat2),"correctTabIsActive");
//		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat2);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat2),"pageURLIsCorrect");
//	}
//
//	/*3.1.10. Category Tabs (Top): Subcategory >> Parent category*/
//	@Test(groups = {"regression"})
//	public void categoryTabsTopNavigationFromSubcategoryToParentCategory (){
//		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsTop);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat1);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat1SubC);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat1);
//        gamesPortletPage.checkNavigation(false, true, true, false, false);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
//		TypeUtils.assertFalseWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(),"isActiveSubcategoryTabPresent");
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat1),"correctTabIsActive");
//		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1),"pageURLIsCorrect");
//	}
//
//	/*3.2.1. Category Tabs (Left): Top*/
//	@Test(groups = {"regression"})
//	public void categoryTabsLeftNavigationStyleTopLevel (){
//		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
//        gamesPortletPage.checkNavigation(false, false, false, true, false);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop), "correctCategoryTabsAreDisplayed");
//		TypeUtils.assertFalseWithLogs(gamesPortletPage.isActiveCategoryTabPresent(), "isActiveCategoryTabPresent");
//		gamesPortletPage.correctGamesAreDisplayed(GameCategories.groupAll);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsPageURL(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft.toString()),"pageURLIsCorrect");
//	}
//
//	/*3.2.2. Category Tabs (Left): Top >> Category without sub-categories*/
//	@Test(groups = {"regression"})
//	public void categoryTabsLeftNavigationFromTopToCategoryWithoutSubcategories (){
//		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat1No);
//        gamesPortletPage.checkNavigation(false, false, false, true, false);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat1No),"correctTabIsActive");
//		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1No);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1No),"pageURLIsCorrect");
//	}
//
//	/*3.2.3. Category Tabs (Left): Top >> Category with sub-categories*/
//	@Test(groups = {"regression"})
//	public void categoryTabsLeftNavigationFromTopToCategoryWithSubcategories (){
//		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat1);
//        gamesPortletPage.checkNavigation(false, false, false, true, true);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop,GameCategories.groupSub1),"correctCategoryTabsAreDisplayed");
//		TypeUtils.assertFalseWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(),"isActiveSubcategoryTabPresent");
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat1),"correctTabIsActive");
//		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1),"pageURLIsCorrect");
//	}
//
//
//	/*3.2.4. Category Tabs (Left): Category with subcategories > subcategory*/
//	@Test(groups = {"regression"})
//	public void categoryTabsLeftNavigationFromCategoryWithSubcategoriesToSubcategory (){
//		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat2);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat2SubA);
//        gamesPortletPage.checkNavigation(false, false, false, true, true);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop, GameCategories.groupSub2),"correctCategoryTabsAreDisplayed");
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(false, GameCategories.groupSub1),"incorrectSubcategoriesAreDisplayed");
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat2),"correctCategoryTabIsActive");
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(GameCategories.cat2SubA),"correctSubcategoryTabIsActive");
//		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat2SubA);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat2SubA),"pageURLIsCorrect");
//	}
//
//	/*3.2.5. Category Tabs (Left): Subcategory > subcategory*/
//	@Test(groups = {"regression"})
//	public void categoryTabsLeftNavigationFromSubcategoryToSubcategory (){
//		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat2);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat2SubA);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat2SubB);
//        gamesPortletPage.checkNavigation(false, false, false, true, true);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop, GameCategories.groupSub2),"correctCategoryTabsAreDisplayed");
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(false, GameCategories.groupSub1),"incorrectSubcategoriesAreDisplayed");
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat2),"correctCategoryTabIsActive");
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(GameCategories.cat2SubB),"correctSubcategoryTabIsActive");
//		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat2SubB);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat2SubB),"pageURLIsCorrect");
//	}
//
//	/*3.2.6. Category Tabs (Left): Category without subcategories > Category without subcategories*/
//	@Test(groups = {"regression"})
//	public void categoryLeftTopNavigationFromCatNoSubcatToCatNoSubcat (){
//		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat1No);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat2No);
//        gamesPortletPage.checkNavigation(false, false, false, true, false);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat2No),"correctTabIsActive");
//		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat2No);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat2No),"pageURLIsCorrect");
//	}
//
//
//	/*3.2.7. Category Tabs (Left): Category without subcategories > Category with subcategories*/
//	@Test(groups = {"regression"})
//	public void categoryTabsLeftNavigationFromCatNoSubcatToCatSubcat (){
//		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat1No);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat1);
//        gamesPortletPage.checkNavigation(false, false, false, true, true);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop, GameCategories.groupSub1),"correctCategoryTabsAreDisplayed");
//		TypeUtils.assertFalseWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(),"isActiveSubcategoryTabPresent");
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat1),"correctTabIsActive");
//		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1),"pageURLIsCorrect");
//
//	}
//
//	/*3.2.8. Category Tabs (Left): Category with subcategories > Category without subcategories*/
//	@Test(groups = {"regression"})
//	public void categoryTabsLeftNavigationFromCatSubCatToCatNoScubcat (){
//		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat2);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat1No);
//        gamesPortletPage.checkNavigation(false, false, false, true, false);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat1No),"correctTabIsActive");
//		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1No);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1No),"pageURLIsCorrect");
//	}
//
//	/*3.2.9. Category Tabs (Left): Category with subcategories > Category with subcategories*/
//	@Test(groups = {"regression"})
//	public void categoryTabsLeftNavigationFromCatSubcatToCatSubcat (){
//		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat1);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat2);
//        gamesPortletPage.checkNavigation(false, false, false, true, true);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop, GameCategories.groupSub2),"correctCategoryTabsAreDisplayed");
//		TypeUtils.assertFalseWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(),"isActiveSubcategoryTabPresent");
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat2),"correctTabIsActive");
//		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat2);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat2),"pageURLIsCorrect");
//	}
//
//	/*3.2.10. Category Tabs (Left): Subcategory >> Parent category*/
//	@Test(groups = {"regression"})
//	public void categoryTabsLeftNavigationFromSubcategoryToParentCategory (){
//		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsLeft);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat1);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat1SubC);
//		gamesPortletPage.clickCategoryTab(GameCategories.cat1);
//        gamesPortletPage.checkNavigation(false, false, false, true, true);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop, GameCategories.groupSub1),"correctCategoryTabsAreDisplayed");
//		TypeUtils.assertFalseWithLogs(gamesPortletPage.isActiveSubcategoryTabPresent(),"isActiveSubcategoryTabPresent");
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat1),"correctTabIsActive");
//		gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1),"pageURLIsCorrect");
//	}
//
//    //    4.1.1. Category Tabs And Refine By (Top): Top
//    @Test(groups = {"regression"})
//    public void categoryTabsRefineByTopNavigationStyleTopLevel (){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByTop);
//        gamesPortletPage.checkNavigation(false, true, false, false, false);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
//        TypeUtils.assertFalseWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
//        gamesPortletPage.correctGamesAreDisplayed(GameCategories.groupAll);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsPageURL(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByTop.toString()),"pageURLIsCorrect");
//    }
//
//
//    //    4.1.2. Category Tabs And Refine By (Top): Top >> Category without sub-categories
//    @Test(groups = {"regression"})
//    public void categoryTabsRefineByTopNavigationFromTopToCategoryWithoutSubcategories (){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByTop);
//        gamesPortletPage.clickCategoryTab(GameCategories.cat1No);
//        gamesPortletPage.checkNavigation(false, true, false, false, false);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat1No),"correctTabIsActive");
//        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1No);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1No),"pageURLIsCorrect");
//    }
//
//    //    4.1.3. Category Tabs And Refine By (Top): Top >> Category with sub-categories
//    @Test(groups = {"regression"})
//    public void categoryTabsRefineByTopNavigationFromTopToCategoryWithSubcategories (){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByTop);
//        gamesPortletPage.clickCategoryTab(GameCategories.cat1);
//        gamesPortletPage.checkNavigation(true, true, false, false, false);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(true, GameCategories.groupSub1),"refineByOptionsIncludeCorrectSubcategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(false, GameCategories.groupTop, GameCategories.groupSub2),"refineByOptionsIncludeIncorrectSubcategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isRefineByUnselected(),"isRefineByUnselected");
//        TypeUtils.assertFalseWithLogs(gamesPortletPage.isResetFilterPresent(),"isResetFilterPresent");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat1),"correctTabIsActive");
//        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1),"pageURLIsCorrect");
//    }
//
//    //    4.1.4. Category Tabs And Refine By (Top): Category with subcategories > subcategory
//    @Test(groups = {"regression"})
//    public void categoryTabsRefineByTopNavigationFromCategoryWithSubcategoriesToSubcategory (){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByTop);
//        gamesPortletPage.clickCategoryTab(GameCategories.cat2);
//        gamesPortletPage.refineBy(GameCategories.cat2SubA);
//        gamesPortletPage.checkNavigation(true, true, false, false, false);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isResetFilterPresent(),"resetFilterOptionIsPresent");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(true, GameCategories.groupSub2),"refineByOptionsIncludeCorrectSubcategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(false, GameCategories.groupTop, GameCategories.groupSub1),"refineByOptionsIncludeTopCategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveRefineByOption(GameCategories.cat2SubA),"selectedCategoryIsDisplayedInRefineBy");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat2),"correctCategoryTabIsActive");
//        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat2SubA);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat2SubA),"pageURLIsCorrect");
//    }
//    /*4.1.5.  Category Tabs And Refine By (Top): Subcategory 1 > Subcategory 2 */
//    @Test(groups = {"regression"})
//    public void categoryTabsRefineByTopNavigationFromSubcategoryToSubcategory (){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByTop);
//        gamesPortletPage.clickCategoryTab(GameCategories.cat1);
//        gamesPortletPage.refineBy(GameCategories.cat1SubB);
//        gamesPortletPage.refineBy(GameCategories.cat1SubC);
//        gamesPortletPage.checkNavigation(true, true, false, false, false);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(true, GameCategories.groupSub1),"refineByOptionsIncludeCorrectSubcategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(false, GameCategories.groupTop,GameCategories.groupSub2),"refineByOptionsIncludeTopCategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveRefineByOption(GameCategories.cat1SubC),"selectedCategoryIsDisplayedInRefineBy");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isResetFilterPresent(),"resetFilterOptionIsPresent");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat1),"correctCategoryTabIsActive");
//        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1SubC);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1SubC),"pageURLIsCorrect");
//    }
//
//    /* 4.1.6. Category Tabs And Refine By (Top): Category without subcategories 1 > Category without subcategories 2*/
//    @Test(groups = {"regression"})
//    public void categoryTabsRefineByTopNavigationFromCatNoSubcatToCatNoSubcat (){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByTop);
//        gamesPortletPage.clickCategoryTab(GameCategories.cat1No);
//        gamesPortletPage.clickCategoryTab(GameCategories.cat2No);
//        gamesPortletPage.checkNavigation(false, true, false, false, false);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat2No),"correctTabIsActive");
//        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat2No);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat2No),"pageURLIsCorrect");
//    }
//
//    /* 4.1.7. Category Tabs And Refine By (Top): Category without subcategories > Category with subcategories*/
//    @Test(groups = {"regression"})
//    public void categoryTabsRefineByTopNavigationFromCatNoSubcatToCatSubCat (){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByTop);
//        gamesPortletPage.clickCategoryTab(GameCategories.cat2No);
//        gamesPortletPage.clickCategoryTab(GameCategories.cat1);
//        gamesPortletPage.checkNavigation(true, true, false, false, false);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(true, GameCategories.groupSub1),"refineByOptionsIncludeCorrectSubcategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(false, GameCategories.groupTop, GameCategories.groupSub2),"refineByOptionsIncludeIncorrectSubcategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isRefineByUnselected(),"isRefineByUnselected");
//        TypeUtils.assertFalseWithLogs(gamesPortletPage.isResetFilterPresent(),"isResetFilterPresent");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat1),"correctTabIsActive");
//        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1),"pageURLIsCorrect");
//    }
//
//    /* 4.1.8. Category Tabs And Refine By (Top): Category with subcategories > Category without subcategories*/
//    @Test(groups = {"regression"})
//    public void categoryTabsRefineByTopNavigationFromCatSubcatToCatNoSubcat (){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByTop);
//        gamesPortletPage.clickCategoryTab(GameCategories.cat2);
//        gamesPortletPage.clickCategoryTab(GameCategories.cat1No);
//        gamesPortletPage.checkNavigation(false, true, false, false, false);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop), "correctCategoryTabsAreDisplayed");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat1No),"correctTabIsActive");
//        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1No);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1No),"pageURLIsCorrect");
//    }
//
//    /* 4.1.9. Category Tabs And Refine By (Top): Category with subcategories > Category with subcategories*/
//    @Test(groups = {"regression"})
//    public void categoryTabsRefineByTopNavigationFromCatSubcatToCatSubcat(){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByTop);
//        gamesPortletPage.clickCategoryTab(GameCategories.cat2);
//        gamesPortletPage.clickCategoryTab(GameCategories.cat1);
//        gamesPortletPage.checkNavigation(true, true, false, false, false);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(true, GameCategories.groupSub1),"refineByOptionsIncludeCorrectSubcategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(false, GameCategories.groupTop, GameCategories.groupSub2),"refineByOptionsIncludeIncorrectSubcategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isRefineByUnselected(),"isRefineByUnselected");
//        TypeUtils.assertFalseWithLogs(gamesPortletPage.isResetFilterPresent(),"isResetFilterPresent");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat1),"correctTabIsActive");
//        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1),"pageURLIsCorrect");
//    }
//
//    /*4.1.10. Category Tabs And Refine By (Top): Subcategory > Parent category*/
//    @Test(groups = {"regression"})
//    public void categoryTabsRefineByTopNavigationFromSubcategoryToParentCategory (){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByTop);
//        gamesPortletPage.clickCategoryTab(GameCategories.cat2);
//        gamesPortletPage.refineBy(GameCategories.cat2SubD);
//        gamesPortletPage.clickRefineByResetFilter();
//        gamesPortletPage.checkNavigation(true, true, false, false, false);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(true, GameCategories.groupSub2),"refineByOptionsIncludeCorrectSubcategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(false, GameCategories.groupTop, GameCategories.groupSub1),"refineByOptionsIncludeIncorrectSubcategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isRefineByUnselected(),"isRefineByUnselected");
//        TypeUtils.assertFalseWithLogs(gamesPortletPage.isResetFilterPresent(),"isResetFilterPresent");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat2),"correctTabIsActive");
//        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat2);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat2),"pageURLIsCorrect");
//    }
//
//    //    4.2.1. Category Tabs And Refine By (Left): Top
//    @Test(groups = {"regression"})
//    public void categoryTabsRefineByLeftNavigationStyleTopLevel (){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByLeft);
//        gamesPortletPage.checkNavigation(false, false, false, true, false);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
//        TypeUtils.assertFalseWithLogs(gamesPortletPage.isActiveCategoryTabPresent(),"isActiveCategoryTabPresent");
//        gamesPortletPage.correctGamesAreDisplayed(GameCategories.groupAll);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsPageURL(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByLeft.toString()),"pageURLIsCorrect");
//    }
//
//    //    4.2.2. Category Tabs And Refine By (Left): Top >> Category without sub-categories
//    @Test(groups = {"regression"})
//    public void categoryTabsRefineByLeftNavigationFromTopToCategoryWithoutSubcategories (){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByLeft);
//        gamesPortletPage.clickCategoryTab(GameCategories.cat1No);
//        gamesPortletPage.checkNavigation(false, false, false, true, false);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat1No),"correctTabIsActive");
//        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1No);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1No),"pageURLIsCorrect");
//    }
//
//    //    4.2.3. Category Tabs And Refine By (Left): Top >> Category with sub-categories
//    @Test(groups = {"regression"})
//    public void categoryTabsRefineByLeftNavigationFromTopToCategoryWithSubcategories (){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByLeft);
//        gamesPortletPage.clickCategoryTab(GameCategories.cat1);
//        gamesPortletPage.checkNavigation(true, false, false, true, false);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(true, GameCategories.groupSub1),"refineByOptionsIncludeCorrectSubcategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(false, GameCategories.groupTop, GameCategories.groupSub2),"refineByOptionsIncludeIncorrectSubcategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isRefineByUnselected(),"isRefineByUnselected");
//        TypeUtils.assertFalseWithLogs(gamesPortletPage.isResetFilterPresent(),"isResetFilterPresent");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat1),"correctTabIsActive");
//        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1),"pageURLIsCorrect");
//    }
//
//    //    4.2.4. Category Tabs And Refine By (Left): Category with subcategories > subcategory
//    @Test(groups = {"regression"})
//    public void categoryTabsRefineByLeftNavigationFromCategoryWithSubcategoriesToSubcategory (){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByLeft);
//        gamesPortletPage.clickCategoryTab(GameCategories.cat2);
//        gamesPortletPage.refineBy(GameCategories.cat2SubA);
//        gamesPortletPage.checkNavigation(true, false, false, true, false);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(true, GameCategories.groupSub2),"refineByOptionsIncludeCorrectSubcategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(false, GameCategories.groupSub1, GameCategories.groupTop),"refineByOptionsIncludeIncorrectSubcategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveRefineByOption(GameCategories.cat2SubA),"selectedCategoryIsDisplayedInRefineBy");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isResetFilterPresent(),"resetFilterOptionIsPresent");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat2),"correctCategoryTabIsActive");
//        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat2SubA);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat2SubA),"pageURLIsCorrect");
//    }
//
//    /*4.2.5.  Category Tabs And Refine By (Left): Subcategory 1 > Subcategory 2 */
//    @Test(groups = {"regression"})
//    public void categoryTabsRefineByLeftNavigationFromSubcategoryToSubcategory (){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByLeft);
//        gamesPortletPage.clickCategoryTab(GameCategories.cat1);
//        gamesPortletPage.refineBy(GameCategories.cat1SubB);
//        gamesPortletPage.refineBy(GameCategories.cat1SubC);
//        gamesPortletPage.checkNavigation(true, false, false, true, false);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(true, GameCategories.groupSub1),"refineByOptionsIncludeCorrectSubcategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(false, GameCategories.groupTop, GameCategories.groupSub2),"refineByOptionsIncludeTopCategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveRefineByOption(GameCategories.cat1SubC),"selectedCategoryIsDisplayedInRefineBy");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isResetFilterPresent(),"resetFilterOptionIsPresent");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat1),"correctCategoryTabIsActive");
//        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1SubC);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1SubC),"pageURLIsCorrect");
//
//    }
//
//    /* 4.2.6. Category Tabs And Refine By (Left): Category without subcategories 1 > Category without subcategories 2*/
//    @Test(groups = {"regression"})
//    public void categoryTabsRefineByLeftNavigationFromCatNoSubcatToCatNoSubcat (){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByLeft);
//        gamesPortletPage.clickCategoryTab(GameCategories.cat1No);
//        gamesPortletPage.clickCategoryTab(GameCategories.cat2No);
//        gamesPortletPage.checkNavigation(false, false, false, true, false);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat2No),"correctTabIsActive");
//        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat2No);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat2No),"pageURLIsCorrect");
//      }
//
//    /* 4.2.7. Category Tabs And Refine By (Left): Category without subcategories > Category with subcategories*/
//    @Test(groups = {"regression"})
//    public void categoryTabsRefineByLeftNavigationFromCatNoSubcatToCatSubCat (){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByLeft);
//        gamesPortletPage.clickCategoryTab(GameCategories.cat2No);
//        gamesPortletPage.clickCategoryTab(GameCategories.cat1);
//        gamesPortletPage.checkNavigation(true, false, false, true, false);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(true, GameCategories.groupSub1),"refineByOptionsIncludeCorrectSubcategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(false, GameCategories.groupTop, GameCategories.groupSub2),"refineByOptionsExcludeIncorrectSubcategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isRefineByUnselected(),"isRefineByUnselected");
//        TypeUtils.assertFalseWithLogs(gamesPortletPage.isResetFilterPresent(),"isResetFilterPresent");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat1),"correctTabIsActive");
//        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1),"pageURLIsCorrect");
//    }
//
//    /* 4.2.8. Category Tabs And Refine By (Left): Category with subcategories > Category without subcategories*/
//    @Test(groups = {"regression"})
//    public void categoryTabsRefineByLeftNavigationFromCatSubcatToCatNoSubcat (){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByLeft);
//        gamesPortletPage.clickCategoryTab(GameCategories.cat2);
//        gamesPortletPage.clickCategoryTab(GameCategories.cat1No);
//        gamesPortletPage.checkNavigation(false, false, false, true, false);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat1No),"correctTabIsActive");
//        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1No);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1No),"pageURLIsCorrect");
//    }
//
//    /* 4.2.9. Category Tabs And Refine By (Left): Category with subcategories > Category with subcategories*/
//    @Test(groups = {"regression"})
//    public void categoryTabsRefineByLeftNavigationFromCatSubcatToCatSubcat(){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByLeft);
//        gamesPortletPage.clickCategoryTab(GameCategories.cat2);
//        gamesPortletPage.clickCategoryTab(GameCategories.cat1);
//        gamesPortletPage.checkNavigation(true, false, false, true, false);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(true, GameCategories.groupSub1),"refineByOptionsIncludeCorrectSubcategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(false, GameCategories.groupTop, GameCategories.groupSub2),"refineByOptionsIncludeIncorrectSubcategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isRefineByUnselected(),"isRefineByUnselected");
//        TypeUtils.assertFalseWithLogs(gamesPortletPage.isResetFilterPresent(),"isResetFilterPresent");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat1),"correctTabIsActive");
//        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat1);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat1),"pageURLIsCorrect");
//    }
//
//    /*4.2.10. Category Tabs And Refine By (Left): Subcategory > Parent category*/
//    @Test(groups = {"regression"})
//    public void categoryTabsRefineByLeftNavigationFromSubcategoryToParentCategory (){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleCategoryTabsRefineByLeft);
//        gamesPortletPage.clickCategoryTab(GameCategories.cat2);
//        gamesPortletPage.refineBy(GameCategories.cat2SubD);
//        gamesPortletPage.clickRefineByResetFilter();
//        gamesPortletPage.checkNavigation(true, false, false, true, false);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(true, GameCategories.groupSub2),"refineByOptionsIncludeCorrectSubcategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.refineByOptionsInclude(false, GameCategories.groupTop, GameCategories.groupSub1),"refineByOptionsIncludeIncorrectSubcategories");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isRefineByUnselected(),"isRefineByUnselected");
//        TypeUtils.assertFalseWithLogs(gamesPortletPage.isResetFilterPresent(),"isResetFilterPresent");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.categoryTabsInclude(true, GameCategories.groupTop),"correctCategoryTabsAreDisplayed");
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.isActiveCategoryTabPresent(GameCategories.cat2),"correctTabIsActive");
//        gamesPortletPage.correctGamesAreDisplayed(GameCategories.cat2);
//        TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsCategoryURL(GameCategories.cat2),"pageURLIsCorrect");
//    }
//
//    /*5. Navigation style = None All navigation controls are absent All Games are displayed*/
//	@Test(groups = {"regression"})
//	public void noneNavigationStyle (){
//		GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesNavigationStyleNone);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.allNavigationControlsAreHidden(),"allNavigationControlsHidden");
//		gamesPortletPage.correctGamesAreDisplayed(GameCategories.groupAll);
//		TypeUtils.assertTrueWithLogs(gamesPortletPage.currentURLIsPageURL(ConfiguredPages.gamesNavigationStyleNone.toString()),"pageURLIsCorrect");
//	}
//
//    /*7. Sort By for Casino games*/
//    /*7.1. New - descending by New tag*/
//    /*7.3. A-Z - acsending alphabetical*/
//    /*7.6. Jackpot Size - descending by JP size*/
//	@Test(groups = {"regression"})
//	public void sortByAllOptionsWork(){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesStyleOne);
//		int gamesPerSlide = gamesPortletPage.getGameItemsNumberOnSlide();
//		int gamesTotalNumber = gamesPortletPage.getTotalGameItemsNumber();
////        New sorting
//		gamesPortletPage.sortBy(SortBy.New);
//		String gameID = gamesPortletPage.getGameName(1);
//		GameElement gameElement = new GameElement(gameID);
//        TypeUtils.assertTrueWithLogs(gameElement.isNew(),"is sorted by New");
//
////        Alphabetical sorting
//		gamesPortletPage.sortBy(SortBy.Alphabetical);
//		ArrayList<String> gameIDs = new ArrayList<String>();
//        boolean isAlphabetical =  true;
//		for(int i=1; i<=gamesPerSlide; i++){
//			gameIDs.add(gamesPortletPage.getGameName(i));
//		}
//		for (int i=1; i<gameIDs.size();i++){
//			if(gameIDs.get(i).compareTo(gameIDs.get(i-1))<0){
//				isAlphabetical = false;
//			}
//		}
////        Jackpots sorting
//		int jackpotsCount = gamesPortletPage.getJackpotGamesCount();
//		if (jackpotsCount > gamesPerSlide){
//			jackpotsCount = gamesPerSlide;
//		}
//		ArrayList<Double> jackpots = new ArrayList(jackpotsCount);
//		gamesPortletPage.sortBy(SortBy.Jackpot);
//        boolean isJackpotDesc = true;
//		for(int i=1; i<=jackpotsCount; i++){
//			gameID = gamesPortletPage.getGameName(i);
//			gameElement = new GameElement(gameID);
//			jackpots.add(Double.parseDouble(gameElement.getJackpot()));
//		}
//		for (int i=1; i<jackpots.size();i++){
//			if (jackpots.get(i) > jackpots.get(i-1)){
//				isJackpotDesc = false;
//			}
//		}
//		TypeUtils.assertTrueWithLogs(isJackpotDesc,"is sorted by jackpots");
//        TypeUtils.assertTrueWithLogs(isAlphabetical,"is sorted alphabetically");
//	}
//
//	/*11. Specific category search on GP with "Use Separate Page for each game category" disabled*/
//	/*11.1.1. Search is applied on each char entering and provides correct results*/
//	/*11.1.3.  No results are displayed for a search parameter which has no matches with the list of games*/
//	@Test(groups = {"regression"})
//	public void searchFindsCorrectGameAndNoGamesAreShownForRandomSearch(){
//        boolean gamesAreNotShown = false;
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesStyleOne);
//		String gameID = gamesPortletPage.getRandomGameName();
//		gamesPortletPage = gamesPortletPage.inputSearch(gameID);
//		String searchedId = gamesPortletPage.getGameName(1);
//		try{
//			gamesPortletPage = gamesPortletPage.inputSearch(searchValidationRule.generateValidString());
//		}catch(RuntimeException e){
//			gamesAreNotShown = true;
//		}
//		TypeUtils.assertTrueWithLogs(searchedId.equals(gameID),"Search correct");
//        TypeUtils.assertTrueWithLogs(gamesAreNotShown, "games are not shown for wrong search");
//	}
//
//	/*14. Item View Styles*/
//	/*14.1. Style 1 - play, demo, view game info*/
//	@Test(groups = {"regression"})
//	public void demoAndRealGameCanBeStartedFromItemViewStyleOne(){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.gamesStyleOne, defaultUserData.getRegisteredUserData());
//		GameLaunchPopup gameLaunchPopup = gamesPortletPage.playDemo();
//        boolean correctGamePopupUrl = gameLaunchPopup.iFrameGameUrlIsValid();
//		gameLaunchPopup.close();
//        TypeUtils.assertTrueWithLogs(correctGamePopupUrl, "Game url 1 is valid");
//		gameLaunchPopup = (GameLaunchPopup)gamesPortletPage.playReal(true);
//        correctGamePopupUrl = gameLaunchPopup.iFrameGameUrlIsValid();
//		gameLaunchPopup.close();
//        TypeUtils.assertTrueWithLogs(correctGamePopupUrl, "Game url 2 is valid");
//		GameInfoPopup gameInfoPopup = gamesPortletPage.clickInfo();
//        TypeUtils.assertTrueWithLogs(gameInfoPopup.isTitleCorrect(), "is popup title correct");
//		gameInfoPopup.clickClose();
//	}
//
//	/*14.2. Item View Style 2 - play, demo, view game info*/
//	@Test(groups = {"regression"})
//	public void demoAndRealGameCanBeStartedFromItemViewStyleTwo(){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.gamesStyleTwo, defaultUserData.getRegisteredUserData());
//		GameLaunchPopup gameLaunchPopup = gamesPortletPage.playDemo();
//        boolean correctGamePopupUrl = gameLaunchPopup.iFrameGameUrlIsValid();
//        gameLaunchPopup.close();
//        TypeUtils.assertTrueWithLogs(correctGamePopupUrl, "Game url 1 is valid");
//		gameLaunchPopup = (GameLaunchPopup)gamesPortletPage.playReal(true);
//        correctGamePopupUrl = gameLaunchPopup.iFrameGameUrlIsValid();
//		gameLaunchPopup.close();
//        TypeUtils.assertTrueWithLogs(correctGamePopupUrl, "Game url 2 is valid");
//		GameInfoPopup gameInfoPopup = gamesPortletPage.clickInfo();
//        TypeUtils.assertTrueWithLogs(gameInfoPopup.isTitleCorrect(), "is popup title correct");
//		gameInfoPopup.clickClose();
//	}
//
//	/*14.3. Item View Style 3 - play, demo, view game info*/
//	@Test(groups = {"regression"})
//	public void demoAndRealGameCanBeStartedFromItemViewStyleThree(){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.gamesStyleThree, defaultUserData.getRegisteredUserData());
//		GameLaunchPopup gameLaunchPopup = gamesPortletPage.playDemo();
//        boolean correctGamePopupUrl = gameLaunchPopup.iFrameGameUrlIsValid();
//		gameLaunchPopup.close();
//        TypeUtils.assertTrueWithLogs(correctGamePopupUrl, "Game url 1 is valid");
//		try{
//			gameLaunchPopup = (GameLaunchPopup)gamesPortletPage.playReal(true);
//		}catch(RuntimeException e){
//			gameLaunchPopup = (GameLaunchPopup)gamesPortletPage.playRealFromTitle(true);
//		}
//        correctGamePopupUrl = gameLaunchPopup.iFrameGameUrlIsValid();
//		gameLaunchPopup.close();
//        TypeUtils.assertTrueWithLogs(correctGamePopupUrl, "Game url 2 is valid");
//		GameInfoPopup gameInfoPopup = gamesPortletPage.clickInfo();
//        TypeUtils.assertTrueWithLogs(gameInfoPopup.isTitleCorrect(), "is popup title correct");
//		gameInfoPopup.clickClose();
//	}
//
//	/*14.4. Item View Style 4 - play, demo, view game info*/
//	@Test(groups = {"regression"})
//	public void demoAndRealGameCanBeStartedFromItemViewStyleFour(){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.gamesStyleFour, defaultUserData.getRegisteredUserData());
//		GameLaunchPopup gameLaunchPopup = gamesPortletPage.playDemo();
//        boolean correctGamePopupUrl = gameLaunchPopup.iFrameGameUrlIsValid();
//		gameLaunchPopup.close();
//        TypeUtils.assertTrueWithLogs(correctGamePopupUrl, "Game url 1 is valid");
//		gameLaunchPopup = (GameLaunchPopup)gamesPortletPage.playReal(true);
//        correctGamePopupUrl = gameLaunchPopup.iFrameGameUrlIsValid();
//		gameLaunchPopup.close();
//        TypeUtils.assertTrueWithLogs(correctGamePopupUrl, "Game url 2 is valid");
//		GameInfoPopup gameInfoPopup = gamesPortletPage.clickInfo();
//        TypeUtils.assertTrueWithLogs(gameInfoPopup.isTitleCorrect(), "is popup title correct");
//		gameInfoPopup.clickClose();
//	}
//
	/*15. Navigation types*/
	/*15.1. Slider : next/back arrows navigation*/
	@Test(groups = {"regression", "desktop"})
	public void sliderPaginationWorksCorrectly(){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesStyleOne);
		String firstPageGame = gamesPortletPage.getGameID(1,1);
        String secondPageGame = gamesPortletPage.getGameID(2,1);
		gamesPortletPage = gamesPortletPage.clickNextButton();
        assertTrue(gamesPortletPage.isGamePresent(secondPageGame), "Game from second page visible");
        assertFalse(gamesPortletPage.isGamePresent(firstPageGame), "Game from first page visible");
        gamesPortletPage = gamesPortletPage.clickPreviousButton();
        assertTrue(gamesPortletPage.isGamePresent(firstPageGame), "Game from first page visible");
        assertFalse(gamesPortletPage.isGamePresent(secondPageGame), "Game from second page visible");
	}

//	/*15.3. Navigation types - To Fit*/
//	@Test(groups = {"regression"})
//	public void toFitModeIsDisplayedAndGameCanBeStarted(){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.gamesToFit, defaultUserData.getRegisteredUserData());
//		GameLaunchPopup gameLaunchPopup = gamesPortletPage.playReal(RandomUtils.generateRandomIntBetween(26, gamesPortletPage.getAllGameNames().size()));
//        boolean correctGamePopupUrl = gameLaunchPopup.iFrameGameUrlIsValid();
//		gameLaunchPopup.close();
//        TypeUtils.assertTrueWithLogs(correctGamePopupUrl, "Game url is valid");
//	}
//
    /*16. Use Favorites*/
    /*16.1. Player adds games to favorites*/
    /*16.2. Player removes games from favorites*/
	/*17.2. When a game is removed from favorites it disappears from Favorites-only portlet*/
	@Test(groups = {"regression"})
	public void gamesCanBeAddedToFavouritesCategoryAndRemoved(){
		GamesPortletPage gamesPortletPage = (GamesPortletPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.gamesFavourites);
		final String gameID = gamesPortletPage.getRandomGameID();
		GameElement gameElement = new GameElement(gameID);
		gameElement.favourite();
        assertTrue(gameElement.isFavouriteActive(), "Is favorite");
        gamesPortletPage.clickCategoryTab(GameCategories.favourites);
		gameElement = new GameElement(gameID);
		gameElement.unFavourite();
        assertFalse(gamesPortletPage.isGamePresent(gameID), "is still favorite");
	}

    @Test(groups = {"regression"})
    public void favouritesCategoryPlayerFirst(){
        GamesPortletPage gamesPortletPage = (GamesPortletPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.gamesFavouritesCategoryFirst);
        assertTrue(gamesPortletPage.isCategoryTabPresent(GameCategories.favourites), "Favourites category present");
        gamesPortletPage.assertCategoryFirst(GameCategories.favourites);
    }

    @Test(groups = {"regression"})
    public void removeAllGamesFromCategory(){
        GamesPortletPage gamesPortletPage = (GamesPortletPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.gamesFavouritesCategoryFirst);
        assertEquals(0, gamesPortletPage.getNumberOfGames(), "Number of favourites");
        assertTrue(gamesPortletPage.isNoGamesMessageVisible(), "Message visible");
        gamesPortletPage.clickCategoryTab(GameCategories.all);
        final String gameID = gamesPortletPage.getRandomGameID();
        GameElement gameElement = new GameElement(gameID);
        gameElement.favourite();
        gamesPortletPage.clickCategoryTab(GameCategories.favourites);
        gameElement.unFavourite();
        assertEquals(0, gamesPortletPage.getNumberOfGames(), "Number of favourites");
        assertTrue(gamesPortletPage.isNoGamesMessageVisible(), "Message visible");
        WebDriverUtils.refreshPage();
        assertEquals(0, gamesPortletPage.getNumberOfGames(), "Number of favourites");
        assertTrue(gamesPortletPage.isNoGamesMessageVisible(), "Message visible");
    }

    @Test(groups = {"regression"})
    public void removeAllGamesNotFromCategory(){
        GamesPortletPage gamesPortletPage = (GamesPortletPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.gamesFavourites);
        final String gameID = gamesPortletPage.getRandomGameID();
        GameElement gameElement = new GameElement(gameID);
        gameElement.favourite();
        gameElement.unFavourite();
        gamesPortletPage.clickCategoryTab(GameCategories.favourites);
        assertEquals(0, gamesPortletPage.getNumberOfGames(), "Number of favourites");
        assertTrue(gamesPortletPage.isNoGamesMessageVisible(), "Message visible");
        WebDriverUtils.refreshPage();
        assertEquals(0, gamesPortletPage.getNumberOfGames(), "Number of favourites");
        assertTrue(gamesPortletPage.isNoGamesMessageVisible(), "Message visible");
    }

    @Test(groups = {"regression"})
    public void favouritesCategoryPlayerLast(){
        GamesPortletPage gamesPortletPage = (GamesPortletPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.gamesFavourites);
        assertTrue(gamesPortletPage.isCategoryTabPresent(GameCategories.favourites), "Favourites category present");
        gamesPortletPage.assertCategoryLast(GameCategories.favourites);
    }

    @Test(groups = {"regression"})
    public void favouritesNoCategoryPlayer(){
        GamesPortletPage gamesPortletPage = (GamesPortletPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.gamesFavouritesNoCategory);
        assertFalse(gamesPortletPage.isCategoryTabPresent(GameCategories.favourites), "Favourites category present");
    }

    @Test(groups = {"regression"})
    public void favouritesNavigationsStyleNone(){
        GamesPortletPage gamesPortletPage = (GamesPortletPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.gamesNavigationStyleNone);
        assertFalse(gamesPortletPage.isCategoryTabPresent(GameCategories.favourites), "Favourites category present");
    }

    @Test(groups = {"regression"})
    public void favouritesCategoryGuest(){
        GamesPortletPage gamesPortletPage = (GamesPortletPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.gamesFavourites);
        assertFalse(gamesPortletPage.isCategoryTabPresent(GameCategories.favourites), "Favourites category present");
    }

    @Test(groups = {"admin"})
    public void favouritesCategoryAdmin(){
        GamesPortletPage gamesPortletPage = (GamesPortletPage) NavigationUtils.navigateToPage(PlayerCondition.admin, ConfiguredPages.gamesFavourites);
        assertFalse(gamesPortletPage.isCategoryTabPresent(GameCategories.favourites), "Favourites category present");

    }
//
//	/*22. Switching between Item and gamesList view*/
//	@Test(groups = {"regression"})
//	public void viewModeButtonIsPresentAndChangesMode(){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(ConfiguredPages.gamesStyleOne);
//		String gameId = gamesPortletPage.getRandomGameName();
//		GameElement gameElement = new GameElement(gameId);
//        TypeUtils.assertTrueWithLogs(gameElement.isImagePresent(),"image view");
//		gamesPortletPage = gamesPortletPage.clickListView();
//        TypeUtils.assertFalseWithLogs(gameElement.isImagePresent(),"list view");
//		gamesPortletPage.clickItemView();
//		TypeUtils.assertTrueWithLogs(gameElement.isImagePresent(),"image view after list");
//	}
//
//	/*24. Disable demo mode for logged in player*/
//	@Test(groups = {"regression"})
//	public void loggedInUserTriesToPlayDisabledDemoNoDemoButtonsFound(){
//        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.gamesMinimum, defaultUserData.getRegisteredUserData());
//        TypeUtils.assertFalseWithLogs(gamesPortletPage.isDemoPresent(),"demo buttons present");
//	}

	/*29.1. Guest launches a game in real mode and faces login popup*/
	@Test(groups = {"regression"})
	public void loggedOutUserTriesToPlayRealGameGetsLogInPrompt(){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.gamesStyleOne);
		LoginPopup loginPopup = (LoginPopup)gamesPortletPage.playRealLoggedOut();
	}

	/*30. Player launches game from list view*/
	@Test(groups = {"regression", "desktop"})
	public void gameCanBeStartedFromListView(){
        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.gamesList);
		GameLaunchPopup gameLaunchPopup = (GameLaunchPopup) gamesPortletPage.playRealList(true);
        gameLaunchPopup.assertGameLaunchAndClose();
	}

    /*B-13152 Load game by URL */
    /*iFrame game*/
    /*1. */
    @Test(groups = {"regression"})
    public void launchIFrameGameByUrlPlayer(){
        GameLaunchPage gameLaunchPage = (GameLaunchPage) NavigationUtils.launchGameByUrl(PlayerCondition.player, GameLaunchPage.IFRAME_GAME_1);
        assertTrue(gameLaunchPage.iFrameGameUrlIsValid(), "Game url is valid.");
        assertTrue(gameLaunchPage.isRealMode(), "Game launched in real mode by default.");
    }

    /*2. */
    @Test(groups = {"regression"})
    public void launchIFrameGameByUrlPlayerRealMode(){
        GameLaunchPage gameLaunchPage = (GameLaunchPage) NavigationUtils.launchGameByUrl(PlayerCondition.player, GameLaunchPage.IFRAME_GAME_1, 1);
        assertTrue(gameLaunchPage.iFrameGameUrlIsValid(), "Game url is valid.");
        assertTrue(gameLaunchPage.isRealMode(), "Game launched in real mode.");
    }

    /*3. */
    @Test(groups = {"regression"})
    public void launchIFrameGameByUrlPlayerDemoMode(){
        GameLaunchPage gameLaunchPage = (GameLaunchPage) NavigationUtils.launchGameByUrl(PlayerCondition.player, GameLaunchPage.IFRAME_GAME_1, 0);
        assertTrue(gameLaunchPage.iFrameGameUrlIsValid(), "Game url is valid.");
        assertFalse(gameLaunchPage.isRealMode(), "Game launched in demo mode.");
    }

    /*4. */
    @Test(groups = {"regression"})
    public void launchIFrameGameByUrlGuestPlay(){
        LoginPopup loginPopup = (LoginPopup) NavigationUtils.launchGameByUrl(PlayerCondition.guest, GameLaunchPage.IFRAME_GAME_1);
        GameLaunchPage gameLaunchPage = (GameLaunchPage) loginPopup.login(Page.gameLaunch);
        assertTrue(gameLaunchPage.iFrameGameUrlIsValid(), "Game url is valid after login.");
    }

    /*5. */
    @Test(groups = {"regression"})
    public void launchIFrameGameByUrlGuestPlayRealMode(){
        LoginPopup loginPopup = (LoginPopup) NavigationUtils.launchGameByUrl(PlayerCondition.guest, GameLaunchPage.IFRAME_GAME_1, 1);
        GameLaunchPage gameLaunchPage = (GameLaunchPage) loginPopup.login(Page.gameLaunch);
        assertTrue(gameLaunchPage.iFrameGameUrlIsValid(), "Game url is valid after login.");
        assertTrue(gameLaunchPage.isRealMode(), "Game launched in real mode.");
    }
    /*redirect game*/
    /*6. */
    @Test(groups = {"regression"})
    public void launchRedirectGameByUrlPlayer(){
        GameLaunchPage gameLaunchPage = (GameLaunchPage) NavigationUtils.launchGameByUrl(PlayerCondition.player, GameLaunchPage.REDIRECT_GAME_1);
        assertTrue(gameLaunchPage.redirectGameUrlIsValid(), "Game url is valid.");
        assertTrue(gameLaunchPage.isRealMode(), "Game launched in real mode by default.");
    }

    /*7. */
    @Test(groups = {"regression"})
    public void launchRedirectGameByUrlPlayerRealMode(){
        GameLaunchPage gameLaunchPage = (GameLaunchPage) NavigationUtils.launchGameByUrl(PlayerCondition.player, GameLaunchPage.REDIRECT_GAME_1, 1);
        assertTrue(gameLaunchPage.redirectGameUrlIsValid(), "Game url is valid.");
        assertTrue(gameLaunchPage.isRealMode(), "Game launched in real mode.");
    }

    /*8. */
    @Test(groups = {"regression"})
    public void launchRedirectGameByUrlPlayerDemoMode(){
        GameLaunchPage gameLaunchPage = (GameLaunchPage) NavigationUtils.launchGameByUrl(PlayerCondition.player, GameLaunchPage.REDIRECT_GAME_1, 0);
        assertTrue(gameLaunchPage.redirectGameUrlIsValid(), "Game url is valid.");
        assertFalse(gameLaunchPage.isRealMode(), "Game launched in demo mode.");
    }

    /*9. */
    @Test(groups = {"regression"})
    public void launchRedirectGameByUrlGuestPlay(){
        LoginPopup loginPopup = (LoginPopup) NavigationUtils.launchGameByUrl(PlayerCondition.guest, GameLaunchPage.REDIRECT_GAME_1);
        GameLaunchPage gameLaunchPage = (GameLaunchPage) loginPopup.login(Page.gameLaunch);
        assertTrue(gameLaunchPage.redirectGameUrlIsValid(), "Game url is valid after login.");
    }

    /*10. */
    @Test(groups = {"regression"})
    public void launchRedirectGameByUrlGuestPlayDemoMode(){
        GameLaunchPage gameLaunchPage = (GameLaunchPage)  NavigationUtils.launchGameByUrl(PlayerCondition.guest, GameLaunchPage.REDIRECT_GAME_1, 0);
        assertTrue(gameLaunchPage.redirectGameUrlIsValid(), "Game url is valid.");
        assertFalse(gameLaunchPage.isRealMode(), "Game launched in demo mode.");
    }

    /*other*/
    /*11. */
    @Test(groups = {"regression"})
    public void launchIFrameGameByUrlGuestCancel(){
        LoginPopup loginPopup = (LoginPopup) NavigationUtils.launchGameByUrl(PlayerCondition.guest, GameLaunchPage.IFRAME_GAME_1);
        loginPopup.closePopup();
        assertFalse(WebDriverUtils.isGameLaunched(ConfiguredPages.home), "Game is not launched after cancel login.");
    }

    /*12. */
    @Test(groups = {"regression"})
    public void launchIFrameGameByUrlPlayerIncorrectGameId(){
        GameIncorrectId gameIncorrectId = (GameIncorrectId) NavigationUtils.launchGameByUrl(PlayerCondition.player, "incorrectGameId");
        assertFalse(WebDriverUtils.isGameLaunched(ConfiguredPages.home), "Game with incorrect code is not launched.");
    }

    /*13. */
    @Test(groups = {"regression"})
    public void launchIFrameGameByUrlPlayerIncorrectGameMode(){
        GameLaunchPage gameLaunchPage = (GameLaunchPage) NavigationUtils.launchGameByUrl(PlayerCondition.guest, GameLaunchPage.IFRAME_GAME_1, 2);
        assertTrue(gameLaunchPage.isRealMode(), "Game launched in real mode.");
    }

    /*14. */
    @Test(groups = {"regression"})
    public void launchRedirectGameByUrlPlayerIncorrectGameId(){
        GameLaunchPage gameLaunchPage = (GameLaunchPage) NavigationUtils.launchGameByUrl(PlayerCondition.player, GameLaunchPage.NO_DEMO_GAME, 0);
        assertTrue(gameLaunchPage.isRealMode(), "Game launched in real mode.");
    }


}
