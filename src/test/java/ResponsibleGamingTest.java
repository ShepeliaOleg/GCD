import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.testng.annotations.Test;
import pageObjects.responsibleGaming.ResponsibleGamingPage;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.core.AbstractTest;

public class ResponsibleGamingTest extends AbstractTest{

	/* Positive */
	
	/*1. Portlet is displayed */
//	@Test(groups = {"smoke"})
//	public void portletIsDisplayedOnMyAccountResponsibleGamingPage() {
//        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.responsibleGaming);
//	}
//
//	/* 2. 1,2,3: Daily <= Weekly <= Monthly */
//	@Test(groups = {"regression"})
//	public void setValidDepositLimits123(){
//        PortalUtils.registerUser();
//		ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
//		responsibleGamingPage.setDepositLimits(1,2,3);
//		assertTrue(responsibleGamingPage.depositsChangedSuccessfullyMessageVisible(), "successfullySetLimitsMessageDisplayed");
//	}
//
//	/* 3. 1,2,-: Daily <= Weekly & Monthly = not defined */
//	@Test(groups = {"regression"})
//	public void setValidDepositLimits120(){
//        PortalUtils.registerUser();
//        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
//		responsibleGamingPage.setDepositLimits(1,2,0);
//		assertTrue(responsibleGamingPage.depositsChangedSuccessfullyMessageVisible(), "successfullySetLimitsMessageDisplayed");
//	}
//
//    /* 4. -,1,2: Weekly <= Monthly & Daily = not defined */
//	@Test(groups = {"regression"})
//	public void setValidDepositLimits012(){
//        PortalUtils.registerUser();
//        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
//		responsibleGamingPage.setDepositLimits(0,1,2);
//		assertTrue(responsibleGamingPage.depositsChangedSuccessfullyMessageVisible(), "successfullySetLimitsMessageDisplayed");
//	}
//
//    /* 5. 1,-,2: Monthly >= Daily & Weekly = not defined */
//	@Test(groups = {"regression"})
//	public void setValidDepositLimits102(){
//        PortalUtils.registerUser();
//        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
//		responsibleGamingPage.setDepositLimits(1,0,2);
//		assertTrue(responsibleGamingPage.depositsChangedSuccessfullyMessageVisible(), "successfullySetLimitsMessageDisplayed");
//	}
//
//    /* 6. 1,-,-: Daily & Weekly = not defined & Monthly = not defined */
//	@Test(groups = {"regression"})
//	public void setValidDepositLimits100(){
//        PortalUtils.registerUser();
//        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
//		responsibleGamingPage.setDepositLimits(1,0,0);
//		assertTrue(responsibleGamingPage.depositsChangedSuccessfullyMessageVisible(), "successfullySetLimitsMessageDisplayed");
//	}
//
//    /* 7. -,1,-: Weekly & Daily = not defined & Monthly = not defined */
//	@Test(groups = {"regression"})
//	public void setValidDepositLimits010(){
//        PortalUtils.registerUser();
//        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
//		responsibleGamingPage.setDepositLimits(0,1,0);
//		assertTrue(responsibleGamingPage.depositsChangedSuccessfullyMessageVisible(), "successfullySetLimitsMessageDisplayed");
//	}
//
//    /* 8. -,-,1: Monthly & Weekly = not defined & Daily = not defined */
//	@Test(groups = {"regression"})
//	public void setValidDepositLimits001(){
//        PortalUtils.registerUser();
//        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
//		responsibleGamingPage.setDepositLimits(0,0,1);
//        assertTrue(responsibleGamingPage.depositsChangedSuccessfullyMessageVisible(), "successfullySetLimitsMessageDisplayed");
//	}
//
//    /* 9. Currency sign displayed on portlet is equal to player currency, set on registration procedure */
//	@Test(groups = {"regression"})
//	public void currencyIsEqualToSetOnRegistration(){
//        PortalUtils.registerUser();
//        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
//		assertTrue(responsibleGamingPage.getDailyLimitCurrency().startsWith("£"),"currencySignDaily.startsWith(\"£\")");
//        assertTrue(responsibleGamingPage.getWeeklyLimitCurrency().startsWith("£"),"currencySignWeekly.startsWith(\"£\")");
//        assertTrue(responsibleGamingPage.getMonthlyLimitCurrency().startsWith("£"), "currencySignMonthly.startsWith(\"£\")");
//	}
//
//    /* 10. Set time per session */
//	@Test(groups = {"regression"})
//	public void setTimePerSessionLimit(){
//        PortalUtils.registerUser();
//        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
//		responsibleGamingPage.setRandomTimePerSession();
//		responsibleGamingPage.submitTimePerSession();
//		assertTrue(responsibleGamingPage.timePerSessionChangedSuccessfullyMessageVisible(), "successfullySetLimitMessageDisplayed");
//	}
//
//    /* 14. Several consecutive updates of deposit limits */
//	@Test(groups = {"regression"})
//	public void consecutiveValidUpdatesOfDepositLimits(){
//        PortalUtils.registerUser();
//        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
//		responsibleGamingPage.setDepositLimits(1,2,3);
//        assertTrue(responsibleGamingPage.depositsChangedSuccessfullyMessageVisible(), "successfullySetLimitsMessageDisplayed");
//		responsibleGamingPage.setDepositLimits(1,2,3);
//        assertTrue(responsibleGamingPage.depositsChangedSuccessfullyMessageVisible(), "successfullySetLimitsMessageDisplayed");
//	}
//
//    /* Negative */
//
//    /*1. 2,1,-: Daily > Weekly & Monthly = not defined*/
//	@Test(groups = {"regression"})
//	public void setInvalidDepositLimits210(){
//        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.responsibleGaming);
//		responsibleGamingPage.setDepositLimits(2,1,0);
//		responsibleGamingPage.checkErrors(true, true, false);
//	}
//
//    /*2. -,2,1: Weekly > Monthly & Daily = not defined*/
//	@Test(groups = {"regression"})
//	public void setInvalidDepositLimits021(){
//        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.responsibleGaming);
//		responsibleGamingPage.setDepositLimits(0,2,1);
//		responsibleGamingPage.checkErrors(false, true, true);
//	}
//
//    /*3. 2,-,1: Daily > Monthly & Weekly = not defined */
//	@Test(groups = {"regression"})
//	public void setInvalidDepositLimits201(){
//        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.responsibleGaming);
//		responsibleGamingPage.setDepositLimits(2,0,1);
//		responsibleGamingPage.checkErrors(true, false, true);
//	}
//
//    /*4. 3,2,1: Daily > Weekly > Monthly*/
//	@Test(groups = {"regression"})
//	public void setInvalidDepositLimits321(){
//        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.responsibleGaming);
//		responsibleGamingPage.setDepositLimits(3,2,1);
//		responsibleGamingPage.checkErrors(true, true, true);
//	}
//
//    /*5. 2,3,1: Daily < Weekly & Weekly > Monthly*/
//	@Test(groups = {"regression"})
//	public void setInvalidDepositLimits231(){
//        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.responsibleGaming);
//		responsibleGamingPage.setDepositLimits(2,3,1);
//		responsibleGamingPage.checkErrors(true, true, true);
//	}
//
//    /*6. 2,1,3: Daily > weekly & daily < monthly*/
//	@Test(groups = {"regression"})
//	public void setInvalidDepositLimits213(){
//        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.responsibleGaming);
//		responsibleGamingPage.setDepositLimits(2,1,3);
//		responsibleGamingPage.checkErrors(true, true, false);
//	}
//
//    /*7. 3,1,2: daily > weekly & daily > monthly*/
//	@Test(groups = {"regression"})
//	public void setInvalidDepositLimits312(){
//        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.responsibleGaming);
//		responsibleGamingPage.setDepositLimits(3,1,2);
//		responsibleGamingPage.checkErrors(true, true, true);
//	}
//
//    /*8. 1,3,2: daily < weekly & weekly > monthly*/
//	@Test(groups = {"regression"})
//	public void setInvalidDepositLimits132(){
//        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.responsibleGaming);
//		responsibleGamingPage.setDepositLimits(1,3,2);
//		responsibleGamingPage.checkErrors(false, true, true);
//	}

}
