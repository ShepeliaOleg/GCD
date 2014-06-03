import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.base.AbstractPage;
import pageObjects.header.Header;
import pageObjects.header.LoggedInHeader;
import pageObjects.responsibleGaming.ResponsibleGamingPage;
import pageObjects.responsibleGaming.SelfExcludeConfirmPopup;
import pageObjects.responsibleGaming.SelfExcludePopup;
import springConstructors.UserData;
import testUtils.AbstractTest;
import testUtils.Assert;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.WebDriverUtils;

import java.util.List;

/**
 * User: sergiich
 * Date: 4/10/14
 */

public class ResponsibleGamingTest extends AbstractTest{

	@Autowired
	@Qualifier("userData")
	private UserData defaultUserData;
	
	/* Positive */
	
	/*1. Portlet is displayed */
	@Test(groups = {"smoke"})
	public void portletIsDisplayedOnMyAccountResponsibleGamingPage() {
        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.responsibleGaming, defaultUserData.getRegisteredUserData());
	}

	/* 2. 1,2,3: Daily <= Weekly <= Monthly */
	@Test(groups = {"regression"})
	public void setValidDepositLimits123(){
        PortalUtils.registerUser(defaultUserData.getRandomUserData());
		ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits(randomLimits.get(0),randomLimits.get(1), randomLimits.get(2));
		responsibleGamingPage.submitDepositLimit();
		boolean successfullySetLimitsMessageDisplayed=responsibleGamingPage.depositsChangedSuccessfullyMessageVisible();
		Assert.assertTrueWithLogs(successfullySetLimitsMessageDisplayed);
	}

	/* 3. 1,2,-: Daily <= Weekly & Monthly = not defined */
	@Test(groups = {"regression"})
	public void setValidDepositLimits120(){
        PortalUtils.registerUser(defaultUserData.getRandomUserData());
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits(randomLimits.get(1),randomLimits.get(2), "0");
		responsibleGamingPage.submitDepositLimit();
		boolean successfullySetLimitsMessageDisplayed=responsibleGamingPage.depositsChangedSuccessfullyMessageVisible();
		Assert.assertTrueWithLogs(successfullySetLimitsMessageDisplayed);
	}

    /* 4. -,1,2: Weekly <= Monthly & Daily = not defined */
	@Test(groups = {"regression"})
	public void setValidDepositLimits012(){
        PortalUtils.registerUser(defaultUserData.getRandomUserData());
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits("0",randomLimits.get(1), randomLimits.get(2));
		responsibleGamingPage.submitDepositLimit();
		boolean successfullySetLimitsMessageDisplayed=responsibleGamingPage.depositsChangedSuccessfullyMessageVisible();
		Assert.assertTrueWithLogs(successfullySetLimitsMessageDisplayed);
	}

    /* 5. 1,-,2: Monthly >= Daily & Weekly = not defined */
	@Test(groups = {"regression"})
	public void setValidDepositLimits102(){
        PortalUtils.registerUser(defaultUserData.getRandomUserData());
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits(randomLimits.get(1),"0", randomLimits.get(2));
		responsibleGamingPage.submitDepositLimit();
		boolean successfullySetLimitsMessageDisplayed=responsibleGamingPage.depositsChangedSuccessfullyMessageVisible();
		Assert.assertTrueWithLogs(successfullySetLimitsMessageDisplayed);
	}

    /* 6. 1,-,-: Daily & Weekly = not defined & Monthly = not defined */
	@Test(groups = {"regression"})
	public void setValidDepositLimits100(){
        PortalUtils.registerUser(defaultUserData.getRandomUserData());
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits(randomLimits.get(1),"0", "0");
		responsibleGamingPage.submitDepositLimit();
		boolean successfullySetLimitsMessageDisplayed=responsibleGamingPage.depositsChangedSuccessfullyMessageVisible();
		Assert.assertTrueWithLogs(successfullySetLimitsMessageDisplayed);
	}

    /* 7. -,1,-: Weekly & Daily = not defined & Monthly = not defined */
	@Test(groups = {"regression"})
	public void setValidDepositLimits010(){
        PortalUtils.registerUser(defaultUserData.getRandomUserData());
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits("0",randomLimits.get(1), "0");
		responsibleGamingPage.submitDepositLimit();
		boolean successfullySetLimitsMessageDisplayed=responsibleGamingPage.depositsChangedSuccessfullyMessageVisible();
		Assert.assertTrueWithLogs(successfullySetLimitsMessageDisplayed);
	}

    /* 8. -,-,1: Monthly & Weekly = not defined & Daily = not defined */
	@Test(groups = {"regression"})
	public void setValidDepositLimits001(){
        PortalUtils.registerUser(defaultUserData.getRandomUserData());
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits("0","0", randomLimits.get(1));
		responsibleGamingPage.submitDepositLimit();
		boolean successfullySetLimitsMessageDisplayed=responsibleGamingPage.depositsChangedSuccessfullyMessageVisible();
        Assert.assertTrueWithLogs(successfullySetLimitsMessageDisplayed);
	}

    /* 9. Currency sign displayed on portlet is equal to player currency, set on registration procedure */
	@Test(groups = {"regression"})
	public void currencyIsEqualToSetOnRegistration(){
        PortalUtils.registerUser(defaultUserData.getRandomUserData());
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
		String currencySignDaily =      responsibleGamingPage.getDailyLimitCurrency();
		String currencySignWeekly =     responsibleGamingPage.getWeeklyLimitCurrency();
		String currencySignMonthly =    responsibleGamingPage.getMonthlyLimitCurrency();
		Assert.assertTrueWithLogs(currencySignDaily.startsWith("£") && currencySignWeekly.startsWith("£") && currencySignMonthly.startsWith("£"));
	}

    /* 10. Set time per session */
	@Test(groups = {"regression"})
	public void setTimePerSessionLimit(){
        PortalUtils.registerUser(defaultUserData.getRandomUserData());
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
		responsibleGamingPage.setRandomTimePerSession();
		responsibleGamingPage.submitTimePerSession();
		boolean successfullySetLimitMessageDisplayed = responsibleGamingPage.timePerSessionChangedSuccessfullyMessageVisible();
		Assert.assertTrueWithLogs(successfullySetLimitMessageDisplayed);
	}

    /* 11. Set self-exlusion and verify player is logged out */
	@Test(groups = {"regression"})
	public void setSelfExclude(){
        PortalUtils.registerUser(defaultUserData.getRandomUserData());
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
		SelfExcludePopup selfExcludePopup=responsibleGamingPage.navigateToSelfExclude();
		SelfExcludeConfirmPopup selfExcludeConfirmPopup=selfExcludePopup.submitSelfExclude();
		boolean confirmationMessageVisible=selfExcludeConfirmPopup.confirmationMessagesVisible();
		selfExcludeConfirmPopup.clickOk();
		try{
			WebDriverUtils.waitForElementToDisappear(Header.LOGGED_IN_XP);
		}catch(RuntimeException e){
			WebDriverUtils.runtimeExceptionWithLogs("User was not logged out. Self exclusion failed.");
		}
		Assert.assertTrueWithLogs(confirmationMessageVisible == true);
	}

    /* 13. Close self-exclusion popup */
	@Test(groups = {"regression"})
	public void cancelSelfExclusion(){
		ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.responsibleGaming, defaultUserData.getRegisteredUserData());
		SelfExcludePopup selfExcludePopup = responsibleGamingPage.navigateToSelfExclude();
		selfExcludePopup.clickClose();
		Assert.assertTrueWithLogs(new AbstractPage().isLoggedIn() == true);
	}

    /* 14. Several consecutive updates of deposit limits */
	@Test(groups = {"regression"})
	public void consecutiveValidUpdatesOfDepositLimits(){
        PortalUtils.registerUser(defaultUserData.getRandomUserData());
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList(6);
		responsibleGamingPage.setDepositLimits(randomLimits.get(0),randomLimits.get(1),randomLimits.get(2));
		responsibleGamingPage.submitDepositLimit();
		responsibleGamingPage.setDepositLimits(randomLimits.get(3),randomLimits.get(4),randomLimits.get(5));
		responsibleGamingPage.submitDepositLimit();
		boolean futureChangeNotificationDaily =         responsibleGamingPage.dailyFutureChangeNotificationVisible();
		boolean futureChangeNotificationWeekly =        responsibleGamingPage.weeklyFutureChangeNotificationVisible();
		boolean futureChangeNotificationMonthly =       responsibleGamingPage.monthlyFutureChangeNotificationVisible();
		boolean successfullySetLimitsMessageDisplayed = responsibleGamingPage.depositsChangedSuccessfullyMessageVisible();
		Assert.assertTrueWithLogs(futureChangeNotificationDaily == true && futureChangeNotificationWeekly == true && futureChangeNotificationMonthly == true && successfullySetLimitsMessageDisplayed == true,
                WebDriverUtils.getUrlAndLogs());
	}

    /* Negative */

    /*1. 2,1,-: Daily > Weekly & Monthly = not defined*/
	@Test(groups = {"regression"})
	public void setInvalidDepositLimits210(){
        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.responsibleGaming, defaultUserData.getRegisteredUserData());
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits(randomLimits.get(2),randomLimits.get(1), "0");
		responsibleGamingPage.submitDepositLimit();
		boolean dailyErrorVisible=responsibleGamingPage.dailyValidationErrorMessageVisible();
		boolean weeklyErrorVisible=responsibleGamingPage.weeklyValidationErrorMessageVisible();
		boolean monthlyErrorVisible=responsibleGamingPage.monthlyValidationErrorMessageVisible();
		Assert.assertTrueWithLogs(dailyErrorVisible == true && weeklyErrorVisible == true && monthlyErrorVisible == false);
	}

    /*2. -,2,1: Weekly > Monthly & Daily = not defined*/
	@Test(groups = {"regression"})
	public void setInvalidDepositLimits021(){
        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.responsibleGaming, defaultUserData.getRegisteredUserData());
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits("0",randomLimits.get(2), randomLimits.get(1));
		responsibleGamingPage.submitDepositLimit();
		boolean dailyErrorVisible=responsibleGamingPage.dailyValidationErrorMessageVisible();
		boolean weeklyErrorVisible=responsibleGamingPage.weeklyValidationErrorMessageVisible();
		boolean monthlyErrorVisible=responsibleGamingPage.monthlyValidationErrorMessageVisible();
		Assert.assertTrueWithLogs(dailyErrorVisible == false && weeklyErrorVisible == true && monthlyErrorVisible == true);
	}

    /*3. 2,-,1: Daily > Monthly & Weekly = not defined */
	@Test(groups = {"regression"})
	public void setInvalidDepositLimits201(){
        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.responsibleGaming, defaultUserData.getRegisteredUserData());
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits(randomLimits.get(2),"0", randomLimits.get(1));
		responsibleGamingPage.submitDepositLimit();
		boolean dailyErrorVisible=responsibleGamingPage.dailyValidationErrorMessageVisible();
		boolean weeklyErrorVisible=responsibleGamingPage.weeklyValidationErrorMessageVisible();
		boolean monthlyErrorVisible=responsibleGamingPage.monthlyValidationErrorMessageVisible();
		Assert.assertTrueWithLogs(dailyErrorVisible == true && weeklyErrorVisible == false && monthlyErrorVisible == true);
	}

    /*4. 3,2,1: Daily > Weekly > Monthly*/
	@Test(groups = {"regression"})
	public void setInvalidDepositLimits321(){
        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.responsibleGaming, defaultUserData.getRegisteredUserData());
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits(randomLimits.get(2),randomLimits.get(1), randomLimits.get(0));
		responsibleGamingPage.submitDepositLimit();
		boolean dailyErrorVisible=responsibleGamingPage.dailyValidationErrorMessageVisible();
		boolean weeklyErrorVisible=responsibleGamingPage.weeklyValidationErrorMessageVisible();
		boolean monthlyErrorVisible=responsibleGamingPage.monthlyValidationErrorMessageVisible();
		Assert.assertTrueWithLogs(dailyErrorVisible == true && weeklyErrorVisible == true && monthlyErrorVisible == true);
	}

    /*5. 2,3,1: Daily < Weekly & Weekly > Monthly*/
	@Test(groups = {"regression"})
	public void setInvalidDepositLimits231(){
        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.responsibleGaming, defaultUserData.getRegisteredUserData());
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits(randomLimits.get(1),randomLimits.get(2), randomLimits.get(0));
		responsibleGamingPage.submitDepositLimit();
		boolean dailyErrorVisible=responsibleGamingPage.dailyValidationErrorMessageVisible();
		boolean weeklyErrorVisible=responsibleGamingPage.weeklyValidationErrorMessageVisible();
		boolean monthlyErrorVisible=responsibleGamingPage.monthlyValidationErrorMessageVisible();
		Assert.assertTrueWithLogs(dailyErrorVisible == true && weeklyErrorVisible == true && monthlyErrorVisible == true);
	}

    /*6. 2,1,3: Daily > weekly & daily < monthly*/
	@Test(groups = {"regression"})
	public void setInvalidDepositLimits213(){
        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.responsibleGaming, defaultUserData.getRegisteredUserData());
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits(randomLimits.get(1),randomLimits.get(0), randomLimits.get(2));
		responsibleGamingPage.submitDepositLimit();
		boolean dailyErrorVisible=responsibleGamingPage.dailyValidationErrorMessageVisible();
		boolean weeklyErrorVisible=responsibleGamingPage.weeklyValidationErrorMessageVisible();
		boolean monthlyErrorVisible=responsibleGamingPage.monthlyValidationErrorMessageVisible();
		Assert.assertTrueWithLogs(dailyErrorVisible == true && weeklyErrorVisible == true && monthlyErrorVisible == false);
	}

    /*7. 3,1,2: daily > weekly & daily > monthly*/
	@Test(groups = {"regression"})
	public void setInvalidDepositLimits312(){
        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.responsibleGaming, defaultUserData.getRegisteredUserData());
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits(randomLimits.get(2),randomLimits.get(0), randomLimits.get(1));
		responsibleGamingPage.submitDepositLimit();
		boolean dailyErrorVisible=responsibleGamingPage.dailyValidationErrorMessageVisible();
		boolean weeklyErrorVisible=responsibleGamingPage.weeklyValidationErrorMessageVisible();
		boolean monthlyErrorVisible=responsibleGamingPage.monthlyValidationErrorMessageVisible();
		Assert.assertTrueWithLogs(dailyErrorVisible == true && weeklyErrorVisible == true && monthlyErrorVisible == true);
	}

    /*8. 1,3,2: daily < weekly & weekly > monthly*/
	@Test(groups = {"regression"})
	public void setInvalidDepositLimits132(){
        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.responsibleGaming, defaultUserData.getRegisteredUserData());
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits(randomLimits.get(0),randomLimits.get(2), randomLimits.get(1));
		responsibleGamingPage.submitDepositLimit();
		boolean dailyErrorVisible=responsibleGamingPage.dailyValidationErrorMessageVisible();
		boolean weeklyErrorVisible=responsibleGamingPage.weeklyValidationErrorMessageVisible();
		boolean monthlyErrorVisible=responsibleGamingPage.monthlyValidationErrorMessageVisible();
		Assert.assertTrueWithLogs(dailyErrorVisible == false && weeklyErrorVisible == true && monthlyErrorVisible == true);
	}

	@Test(groups = {"regression"})
	public void setSelfExclusion(){
        PortalUtils.registerUser(defaultUserData.getRandomUserData());
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
		SelfExcludePopup selfExcludePopup=responsibleGamingPage.navigateToSelfExclude();
		SelfExcludeConfirmPopup selfExcludeConfirmPopup=selfExcludePopup.submitSelfExclude();
		boolean confirmationMessageVisible=selfExcludeConfirmPopup.confirmationMessagesVisible();
		selfExcludeConfirmPopup.clickOk();
		try{
			WebDriverUtils.waitForElementToDisappear(LoggedInHeader.LOGGED_IN_XP);
		}catch(RuntimeException e){
			WebDriverUtils.runtimeExceptionWithLogs("User was not logged out. Self exclusion failed.");
		}
		Assert.assertTrueWithLogs(confirmationMessageVisible == true);
	}
}
