import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.base.AbstractPage;
import pageObjects.header.Header;
import pageObjects.header.LoggedInHeader;
import pageObjects.responsibleGaming.ResponsibleGamingPage;
import pageObjects.responsibleGaming.SelfExcludeConfirmPopup;
import pageObjects.responsibleGaming.SelfExcludePopup;
import springConstructors.UserData;
import testUtils.AbstractTest;
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
		UserData userData = defaultUserData.getRegisteredUserData();
        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.responsibleGaming, userData);
	}

	/* 2. 1,2,3: Daily <= Weekly <= Monthly */
	@Test(groups = {"regression"})
	public void setValidDepositLimits123(){
        UserData userData=defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
		ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.responsibleGaming);
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits(randomLimits.get(0),randomLimits.get(1), randomLimits.get(2));
		responsibleGamingPage.submitDepositLimit();
		boolean successfullySetLimitsMessageDisplayed=responsibleGamingPage.depositsChangedSuccessfullyMessageVisible();
		Assert.assertTrue(successfullySetLimitsMessageDisplayed);
	}

	/* 3. 1,2,-: Daily <= Weekly & Monthly = not defined */
	@Test(groups = {"regression"})
	public void setValidDepositLimits120(){
        UserData userData=defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.responsibleGaming);
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits(randomLimits.get(1),randomLimits.get(2), "0");
		responsibleGamingPage.submitDepositLimit();
		boolean successfullySetLimitsMessageDisplayed=responsibleGamingPage.depositsChangedSuccessfullyMessageVisible();
		Assert.assertTrue(successfullySetLimitsMessageDisplayed);
	}

    /* 4. -,1,2: Weekly <= Monthly & Daily = not defined */
	@Test(groups = {"regression"})
	public void setValidDepositLimits012(){
        UserData userData=defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.responsibleGaming);
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits("0",randomLimits.get(1), randomLimits.get(2));
		responsibleGamingPage.submitDepositLimit();
		boolean successfullySetLimitsMessageDisplayed=responsibleGamingPage.depositsChangedSuccessfullyMessageVisible();
		Assert.assertTrue(successfullySetLimitsMessageDisplayed);
	}

    /* 5. 1,-,2: Monthly >= Daily & Weekly = not defined */
	@Test(groups = {"regression"})
	public void setValidDepositLimits102(){
        UserData userData=defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.responsibleGaming);
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits(randomLimits.get(1),"0", randomLimits.get(2));
		responsibleGamingPage.submitDepositLimit();
		boolean successfullySetLimitsMessageDisplayed=responsibleGamingPage.depositsChangedSuccessfullyMessageVisible();
		Assert.assertTrue(successfullySetLimitsMessageDisplayed);
	}

    /* 6. 1,-,-: Daily & Weekly = not defined & Monthly = not defined */
	@Test(groups = {"regression"})
	public void setValidDepositLimits100(){
        UserData userData=defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.responsibleGaming);
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits(randomLimits.get(1),"0", "0");
		responsibleGamingPage.submitDepositLimit();
		boolean successfullySetLimitsMessageDisplayed=responsibleGamingPage.depositsChangedSuccessfullyMessageVisible();
		Assert.assertTrue(successfullySetLimitsMessageDisplayed);
	}

    /* 7. -,1,-: Weekly & Daily = not defined & Monthly = not defined */
	@Test(groups = {"regression"})
	public void setValidDepositLimits010(){
        UserData userData=defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.responsibleGaming);
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits("0",randomLimits.get(1), "0");
		responsibleGamingPage.submitDepositLimit();
		boolean successfullySetLimitsMessageDisplayed=responsibleGamingPage.depositsChangedSuccessfullyMessageVisible();
		Assert.assertTrue(successfullySetLimitsMessageDisplayed);
	}

    /* 8. -,-,1: Monthly & Weekly = not defined & Daily = not defined */
	@Test(groups = {"regression"})
	public void setValidDepositLimits001(){
        UserData userData=defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.responsibleGaming);
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits("0","0", randomLimits.get(1));
		responsibleGamingPage.submitDepositLimit();
		boolean successfullySetLimitsMessageDisplayed=responsibleGamingPage.depositsChangedSuccessfullyMessageVisible();
        Assert.assertTrue(successfullySetLimitsMessageDisplayed);
	}

    /* 9. Currency sign displayed on portlet is equal to player currency, set on registration procedure */
	@Test(groups = {"regression"})
	public void currencyIsEqualToSetOnRegistration(){
        UserData userData=defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.responsibleGaming);
		String currencySignDaily =      responsibleGamingPage.getDailyLimitCurrency();
		String currencySignWeekly =     responsibleGamingPage.getWeeklyLimitCurrency();
		String currencySignMonthly =    responsibleGamingPage.getMonthlyLimitCurrency();
		Assert.assertTrue(currencySignDaily.startsWith("£") && currencySignWeekly.startsWith("£") && currencySignMonthly.startsWith("£"));
	}

    /* 10. Set time per session */
	@Test(groups = {"regression"})
	public void setTimePerSessionLimit(){
        UserData userData=defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.responsibleGaming);
		responsibleGamingPage.setRandomTimePerSession();
		responsibleGamingPage.submitTimePerSession();
		boolean successfullySetLimitMessageDisplayed = responsibleGamingPage.timePerSessionChangedSuccessfullyMessageVisible();
		Assert.assertTrue(successfullySetLimitMessageDisplayed);
	}

    /* 11. Set self-exlusion and verify player is logged out */
	@Test(groups = {"regression"})
	public void setSelfExclude(){
        UserData userData=defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.responsibleGaming);
		SelfExcludePopup selfExcludePopup=responsibleGamingPage.navigateToSelfExclude();
		SelfExcludeConfirmPopup selfExcludeConfirmPopup=selfExcludePopup.submitSelfExclude();
		boolean confirmationMessageVisible=selfExcludeConfirmPopup.confirmationMessagesVisible();
		selfExcludeConfirmPopup.clickOk();
		try{
			WebDriverUtils.waitForElementToDisappear(Header.LOGGED_IN_XP);
		}catch(RuntimeException e){
			WebDriverUtils.runtimeExceptionWithLogs("User was not logged out. Self exclusion failed.");
		}
		Assert.assertTrue(confirmationMessageVisible == true);
	}

    /* 13. Close self-exclusion popup */
	@Test(groups = {"regression"})
	public void cancelSelfExclusion(){
		UserData userData = defaultUserData.getRegisteredUserData();
		ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.responsibleGaming, userData);
		SelfExcludePopup selfExcludePopup = responsibleGamingPage.navigateToSelfExclude();
		selfExcludePopup.clickClose();
		Assert.assertTrue(new AbstractPage().isLoggedIn() == true);
	}

    /* 14. Several consecutive updates of deposit limits */
	@Test(groups = {"regression"})
	public void consecutiveValidUpdatesOfDepositLimits(){
        UserData userData=defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.responsibleGaming);
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList(6);
		responsibleGamingPage.setDepositLimits(randomLimits.get(0),randomLimits.get(1),randomLimits.get(2));
		responsibleGamingPage.submitDepositLimit();
		responsibleGamingPage.setDepositLimits(randomLimits.get(3),randomLimits.get(4),randomLimits.get(5));
		responsibleGamingPage.submitDepositLimit();
		boolean futureChangeNotificationDaily =         responsibleGamingPage.dailyFutureChangeNotificationVisible();
		boolean futureChangeNotificationWeekly =        responsibleGamingPage.weeklyFutureChangeNotificationVisible();
		boolean futureChangeNotificationMonthly =       responsibleGamingPage.monthlyFutureChangeNotificationVisible();
		boolean successfullySetLimitsMessageDisplayed = responsibleGamingPage.depositsChangedSuccessfullyMessageVisible();
		Assert.assertTrue(futureChangeNotificationDaily == true && futureChangeNotificationWeekly == true && futureChangeNotificationMonthly == true && successfullySetLimitsMessageDisplayed == true);
	}

    /* Negative */

    /*1. 2,1,-: Daily > Weekly & Monthly = not defined*/
	@Test(groups = {"regression"})
	public void setInvalidDepositLimits210(){
        UserData userData = defaultUserData.getRegisteredUserData();
        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.responsibleGaming, userData);
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits(randomLimits.get(2),randomLimits.get(1), "0");
		responsibleGamingPage.submitDepositLimit();
		boolean dailyErrorVisible=responsibleGamingPage.dailyValidationErrorMessageVisible();
		boolean weeklyErrorVisible=responsibleGamingPage.weeklyValidationErrorMessageVisible();
		boolean monthlyErrorVisible=responsibleGamingPage.monthlyValidationErrorMessageVisible();
		Assert.assertTrue(dailyErrorVisible == true && weeklyErrorVisible == true && monthlyErrorVisible == false);
	}

    /*2. -,2,1: Weekly > Monthly & Daily = not defined*/
	@Test(groups = {"regression"})
	public void setInvalidDepositLimits021(){
        UserData userData = defaultUserData.getRegisteredUserData();
        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.responsibleGaming, userData);
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits("0",randomLimits.get(2), randomLimits.get(1));
		responsibleGamingPage.submitDepositLimit();
		boolean dailyErrorVisible=responsibleGamingPage.dailyValidationErrorMessageVisible();
		boolean weeklyErrorVisible=responsibleGamingPage.weeklyValidationErrorMessageVisible();
		boolean monthlyErrorVisible=responsibleGamingPage.monthlyValidationErrorMessageVisible();
		Assert.assertTrue(dailyErrorVisible == false && weeklyErrorVisible == true && monthlyErrorVisible == true);
	}

    /*3. 2,-,1: Daily > Monthly & Weekly = not defined */
	@Test(groups = {"regression"})
	public void setInvalidDepositLimits201(){
        UserData userData = defaultUserData.getRegisteredUserData();
        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.responsibleGaming, userData);
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits(randomLimits.get(2),"0", randomLimits.get(1));
		responsibleGamingPage.submitDepositLimit();
		boolean dailyErrorVisible=responsibleGamingPage.dailyValidationErrorMessageVisible();
		boolean weeklyErrorVisible=responsibleGamingPage.weeklyValidationErrorMessageVisible();
		boolean monthlyErrorVisible=responsibleGamingPage.monthlyValidationErrorMessageVisible();
		Assert.assertTrue(dailyErrorVisible == true && weeklyErrorVisible == false && monthlyErrorVisible == true);
	}

    /*4. 3,2,1: Daily > Weekly > Monthly*/
	@Test(groups = {"regression"})
	public void setInvalidDepositLimits321(){
        UserData userData = defaultUserData.getRegisteredUserData();
        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.responsibleGaming, userData);
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits(randomLimits.get(2),randomLimits.get(1), randomLimits.get(0));
		responsibleGamingPage.submitDepositLimit();
		boolean dailyErrorVisible=responsibleGamingPage.dailyValidationErrorMessageVisible();
		boolean weeklyErrorVisible=responsibleGamingPage.weeklyValidationErrorMessageVisible();
		boolean monthlyErrorVisible=responsibleGamingPage.monthlyValidationErrorMessageVisible();
		Assert.assertTrue(dailyErrorVisible == true && weeklyErrorVisible == true && monthlyErrorVisible == true);
	}

    /*5. 2,3,1: Daily < Weekly & Weekly > Monthly*/
	@Test(groups = {"regression"})
	public void setInvalidDepositLimits231(){
        UserData userData = defaultUserData.getRegisteredUserData();
        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.responsibleGaming, userData);
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits(randomLimits.get(1),randomLimits.get(2), randomLimits.get(0));
		responsibleGamingPage.submitDepositLimit();
		boolean dailyErrorVisible=responsibleGamingPage.dailyValidationErrorMessageVisible();
		boolean weeklyErrorVisible=responsibleGamingPage.weeklyValidationErrorMessageVisible();
		boolean monthlyErrorVisible=responsibleGamingPage.monthlyValidationErrorMessageVisible();
		Assert.assertTrue(dailyErrorVisible == true && weeklyErrorVisible == true && monthlyErrorVisible == true);
	}

    /*6. 2,1,3: Daily > weekly & daily < monthly*/
	@Test(groups = {"regression"})
	public void setInvalidDepositLimits213(){
        UserData userData = defaultUserData.getRegisteredUserData();
        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.responsibleGaming, userData);
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits(randomLimits.get(1),randomLimits.get(0), randomLimits.get(2));
		responsibleGamingPage.submitDepositLimit();
		boolean dailyErrorVisible=responsibleGamingPage.dailyValidationErrorMessageVisible();
		boolean weeklyErrorVisible=responsibleGamingPage.weeklyValidationErrorMessageVisible();
		boolean monthlyErrorVisible=responsibleGamingPage.monthlyValidationErrorMessageVisible();
		Assert.assertTrue(dailyErrorVisible == true && weeklyErrorVisible == true && monthlyErrorVisible == false);
	}

    /*7. 3,1,2: daily > weekly & daily > monthly*/
	@Test(groups = {"regression"})
	public void setInvalidDepositLimits312(){
        UserData userData = defaultUserData.getRegisteredUserData();
        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.responsibleGaming, userData);
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits(randomLimits.get(2),randomLimits.get(0), randomLimits.get(1));
		responsibleGamingPage.submitDepositLimit();
		boolean dailyErrorVisible=responsibleGamingPage.dailyValidationErrorMessageVisible();
		boolean weeklyErrorVisible=responsibleGamingPage.weeklyValidationErrorMessageVisible();
		boolean monthlyErrorVisible=responsibleGamingPage.monthlyValidationErrorMessageVisible();
		Assert.assertTrue(dailyErrorVisible == true && weeklyErrorVisible == true && monthlyErrorVisible == true);
	}

    /*8. 1,3,2: daily < weekly & weekly > monthly*/
	@Test(groups = {"regression"})
	public void setInvalidDepositLimits132(){
        UserData userData = defaultUserData.getRegisteredUserData();
        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.responsibleGaming, userData);
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits(randomLimits.get(0),randomLimits.get(2), randomLimits.get(1));
		responsibleGamingPage.submitDepositLimit();
		boolean dailyErrorVisible=responsibleGamingPage.dailyValidationErrorMessageVisible();
		boolean weeklyErrorVisible=responsibleGamingPage.weeklyValidationErrorMessageVisible();
		boolean monthlyErrorVisible=responsibleGamingPage.monthlyValidationErrorMessageVisible();
		Assert.assertTrue(dailyErrorVisible == false && weeklyErrorVisible == true && monthlyErrorVisible == true);
	}

	@Test(groups = {"regression"})
	public void setSelfExclusion(){
        UserData userData=defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.responsibleGaming);
		SelfExcludePopup selfExcludePopup=responsibleGamingPage.navigateToSelfExclude();
		SelfExcludeConfirmPopup selfExcludeConfirmPopup=selfExcludePopup.submitSelfExclude();
		boolean confirmationMessageVisible=selfExcludeConfirmPopup.confirmationMessagesVisible();
		selfExcludeConfirmPopup.clickOk();
		try{
			WebDriverUtils.waitForElementToDisappear(LoggedInHeader.LOGGED_IN_XP);
		}catch(RuntimeException e){
			throw new RuntimeException("User was not logged out. Self exclusion failed.");
		}
		Assert.assertTrue(confirmationMessageVisible == true);
	}
}
