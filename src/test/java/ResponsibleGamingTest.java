import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.testng.annotations.Test;
import pageObjects.responsibleGaming.ResponsibleGamingPage;
import pageObjects.responsibleGaming.SuccessfullyChangedNotification;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

public class ResponsibleGamingTest extends AbstractTest{

	/* Positive */
	
	/*1. Portlet is displayed */
	@Test(groups = {"smoke"})
	public void portletIsDisplayedOnMyAccountResponsibleGamingPage() {
        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.responsibleGaming);
	}

	/* 2. 1,2,3: Daily <= Weekly <= Monthly */
	@Test(groups = {"regression"})
	public void setValidDepositLimits123(){
        PortalUtils.registerUser();
		ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
		responsibleGamingPage.setDepositLimits(1,2,3);
		new SuccessfullyChangedNotification();
	}

	/* 3. 1,2,-: Daily <= Weekly & Monthly = not defined */
	@Test(groups = {"regression"})
	public void setValidDepositLimits120(){
        PortalUtils.registerUser();
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
		responsibleGamingPage.setDepositLimits(1,2,0);
        new SuccessfullyChangedNotification();
    }

    /* 4. -,1,2: Weekly <= Monthly & Daily = not defined */
	@Test(groups = {"regression"})
	public void setValidDepositLimits012(){
        PortalUtils.registerUser();
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
		responsibleGamingPage.setDepositLimits(0,1,2);
        new SuccessfullyChangedNotification();
    }

    /* 5. 1,-,2: Monthly >= Daily & Weekly = not defined */
	@Test(groups = {"regression"})
	public void setValidDepositLimits102(){
        PortalUtils.registerUser();
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
		responsibleGamingPage.setDepositLimits(1,0,2);
        new SuccessfullyChangedNotification();
    }

    /* 6. 1,-,-: Daily & Weekly = not defined & Monthly = not defined */
	@Test(groups = {"regression"})
	public void setValidDepositLimits100(){
        PortalUtils.registerUser();
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
		responsibleGamingPage.setDepositLimits(1,0,0);
        new SuccessfullyChangedNotification();	}

    /* 7. -,1,-: Weekly & Daily = not defined & Monthly = not defined */
	@Test(groups = {"regression"})
	public void setValidDepositLimits010(){
        PortalUtils.registerUser();
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
		responsibleGamingPage.setDepositLimits(0,1,0);
        new SuccessfullyChangedNotification();
    }

    /* 8. -,-,1: Monthly & Weekly = not defined & Daily = not defined */
	@Test(groups = {"regression"})
	public void setValidDepositLimits001(){
        PortalUtils.registerUser();
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
		responsibleGamingPage.setDepositLimits(0,0,1);
        new SuccessfullyChangedNotification();
    }

    /* 9. Currency sign displayed on portlet is equal to player currency, set on registration procedure */
	@Test(groups = {"regression"})
	public void currencyIsEqualToSetOnRegistration(){
        UserData userData = DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData);
        String currency = userData.getCurrencySign();
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
		assertTrue(responsibleGamingPage.getDailyLimitCurrency().startsWith(currency),"currencySignDaily.startsWith('"+currency+"')");
        assertTrue(responsibleGamingPage.getWeeklyLimitCurrency().startsWith(currency),"currencySignWeekly.startsWith('"+currency+"')");
        assertTrue(responsibleGamingPage.getMonthlyLimitCurrency().startsWith(currency), "currencySignMonthly.startsWith('"+currency+"')");
	}

    /* 10. Set time per session */
	@Test(groups = {"regression"})
	public void setTimePerSessionLimit(){
        PortalUtils.registerUser();
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
		responsibleGamingPage.setRandomTimePerSession();
    }

    /* 14. Several consecutive updates of deposit limits */
	@Test(groups = {"regression"})
	public void consecutiveValidUpdatesOfDepositLimits(){
        PortalUtils.registerUser();
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
		responsibleGamingPage.setDepositLimits(1,2,3);
        new SuccessfullyChangedNotification();
		responsibleGamingPage.setDepositLimits(1,2,3);
        new SuccessfullyChangedNotification();
	}

    /* Negative */

    /*1. 2,1,-: Daily > Weekly & Monthly = not defined*/
	@Test(groups = {"regression"})
	public void setInvalidDepositLimits210(){
        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.responsibleGaming);
		responsibleGamingPage.setDepositLimits(2,1,0);
		responsibleGamingPage.checkErrors(true, true, false);
	}

    /*2. -,2,1: Weekly > Monthly & Daily = not defined*/
	@Test(groups = {"regression"})
	public void setInvalidDepositLimits021(){
        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.responsibleGaming);
		responsibleGamingPage.setDepositLimits(0,2,1);
		responsibleGamingPage.checkErrors(false, true, true);
	}

    /*3. 2,-,1: Daily > Monthly & Weekly = not defined */
	@Test(groups = {"regression"})
	public void setInvalidDepositLimits201(){
        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.responsibleGaming);
		responsibleGamingPage.setDepositLimits(2,0,1);
		responsibleGamingPage.checkErrors(true, false, true);
	}

    /*4. 3,2,1: Daily > Weekly > Monthly*/
	@Test(groups = {"regression"})
	public void setInvalidDepositLimits321(){
        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.responsibleGaming);
		responsibleGamingPage.setDepositLimits(3,2,1);
		responsibleGamingPage.checkErrors(true, true, true);
	}

    /*5. 2,3,1: Daily < Weekly & Weekly > Monthly*/
	@Test(groups = {"regression"})
	public void setInvalidDepositLimits231(){
        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.responsibleGaming);
		responsibleGamingPage.setDepositLimits(2,3,1);
		responsibleGamingPage.checkErrors(true, true, true);
	}

    /*6. 2,1,3: Daily > weekly & daily < monthly*/
	@Test(groups = {"regression"})
	public void setInvalidDepositLimits213(){
        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.responsibleGaming);
		responsibleGamingPage.setDepositLimits(2,1,3);
		responsibleGamingPage.checkErrors(true, true, false);
	}

    /*7. 3,1,2: daily > weekly & daily > monthly*/
	@Test(groups = {"regression"})
	public void setInvalidDepositLimits312(){
        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.responsibleGaming);
		responsibleGamingPage.setDepositLimits(3,1,2);
		responsibleGamingPage.checkErrors(true, true, true);
	}

    /*8. 1,3,2: daily < weekly & weekly > monthly*/
	@Test(groups = {"regression"})
	public void setInvalidDepositLimits132(){
        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.responsibleGaming);
		responsibleGamingPage.setDepositLimits(1,3,2);
		responsibleGamingPage.checkErrors(false, true, true);
	}

}
