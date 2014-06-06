import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.responsibleGaming.ResponsibleGamingPage;
import springConstructors.UserData;
import testUtils.AbstractTest;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.TypeUtils;
import java.util.List;

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
		responsibleGamingPage.setDepositLimits(randomLimits.get(1),randomLimits.get(2), randomLimits.get(3));
		responsibleGamingPage.submitDepositLimit();
		TypeUtils.assertTrueWithLogs(responsibleGamingPage.depositsChangedSuccessfullyMessageVisible(), "successfullySetLimitsMessageDisplayed");
	}

	/* 3. 1,2,-: Daily <= Weekly & Monthly = not defined */
	@Test(groups = {"regression"})
	public void setValidDepositLimits120(){
        PortalUtils.registerUser(defaultUserData.getRandomUserData());
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits(randomLimits.get(1),randomLimits.get(2), "0");
		responsibleGamingPage.submitDepositLimit();
		TypeUtils.assertTrueWithLogs(responsibleGamingPage.depositsChangedSuccessfullyMessageVisible(), "successfullySetLimitsMessageDisplayed");
	}

    /* 4. -,1,2: Weekly <= Monthly & Daily = not defined */
	@Test(groups = {"regression"})
	public void setValidDepositLimits012(){
        PortalUtils.registerUser(defaultUserData.getRandomUserData());
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits("0",randomLimits.get(1), randomLimits.get(2));
		responsibleGamingPage.submitDepositLimit();
		TypeUtils.assertTrueWithLogs(responsibleGamingPage.depositsChangedSuccessfullyMessageVisible(), "successfullySetLimitsMessageDisplayed");
	}

    /* 5. 1,-,2: Monthly >= Daily & Weekly = not defined */
	@Test(groups = {"regression"})
	public void setValidDepositLimits102(){
        PortalUtils.registerUser(defaultUserData.getRandomUserData());
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits(randomLimits.get(1),"0", randomLimits.get(2));
		responsibleGamingPage.submitDepositLimit();
		TypeUtils.assertTrueWithLogs(responsibleGamingPage.depositsChangedSuccessfullyMessageVisible(), "successfullySetLimitsMessageDisplayed");
	}

    /* 6. 1,-,-: Daily & Weekly = not defined & Monthly = not defined */
	@Test(groups = {"regression"})
	public void setValidDepositLimits100(){
        PortalUtils.registerUser(defaultUserData.getRandomUserData());
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits(randomLimits.get(1),"0", "0");
		responsibleGamingPage.submitDepositLimit();
		TypeUtils.assertTrueWithLogs(responsibleGamingPage.depositsChangedSuccessfullyMessageVisible(), "successfullySetLimitsMessageDisplayed");
	}

    /* 7. -,1,-: Weekly & Daily = not defined & Monthly = not defined */
	@Test(groups = {"regression"})
	public void setValidDepositLimits010(){
        PortalUtils.registerUser(defaultUserData.getRandomUserData());
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits("0",randomLimits.get(1), "0");
		responsibleGamingPage.submitDepositLimit();
		TypeUtils.assertTrueWithLogs(responsibleGamingPage.depositsChangedSuccessfullyMessageVisible(), "successfullySetLimitsMessageDisplayed");
	}

    /* 8. -,-,1: Monthly & Weekly = not defined & Daily = not defined */
	@Test(groups = {"regression"})
	public void setValidDepositLimits001(){
        PortalUtils.registerUser(defaultUserData.getRandomUserData());
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits("0","0", randomLimits.get(1));
		responsibleGamingPage.submitDepositLimit();
        TypeUtils.assertTrueWithLogs(responsibleGamingPage.depositsChangedSuccessfullyMessageVisible(), "successfullySetLimitsMessageDisplayed");
	}

    /* 9. Currency sign displayed on portlet is equal to player currency, set on registration procedure */
	@Test(groups = {"regression"})
	public void currencyIsEqualToSetOnRegistration(){
        PortalUtils.registerUser(defaultUserData.getRandomUserData());
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
		TypeUtils.assertTrueWithLogs(responsibleGamingPage.getDailyLimitCurrency().startsWith("£"),"currencySignDaily.startsWith(\"£\")");
        TypeUtils.assertTrueWithLogs(responsibleGamingPage.getWeeklyLimitCurrency().startsWith("£"),"currencySignWeekly.startsWith(\"£\")");
        TypeUtils.assertTrueWithLogs(responsibleGamingPage.getMonthlyLimitCurrency().startsWith("£"), "currencySignMonthly.startsWith(\"£\")");
	}

    /* 10. Set time per session */
	@Test(groups = {"regression"})
	public void setTimePerSessionLimit(){
        PortalUtils.registerUser(defaultUserData.getRandomUserData());
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
		responsibleGamingPage.setRandomTimePerSession();
		responsibleGamingPage.submitTimePerSession();
		TypeUtils.assertTrueWithLogs(responsibleGamingPage.timePerSessionChangedSuccessfullyMessageVisible(), "successfullySetLimitMessageDisplayed");
	}

    /* 14. Several consecutive updates of deposit limits */
	@Test(groups = {"regression"})
	public void consecutiveValidUpdatesOfDepositLimits(){
        PortalUtils.registerUser(defaultUserData.getRandomUserData());
        ResponsibleGamingPage responsibleGamingPage= (ResponsibleGamingPage) NavigationUtils.navigateToPage(ConfiguredPages.responsibleGaming);
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList(6);
		responsibleGamingPage.setDepositLimits(randomLimits.get(1),randomLimits.get(2),randomLimits.get(3));
		responsibleGamingPage.submitDepositLimit();
        TypeUtils.assertTrueWithLogs(responsibleGamingPage.depositsChangedSuccessfullyMessageVisible(), "successfullySetLimitsMessageDisplayed");
		responsibleGamingPage.setDepositLimits(randomLimits.get(3),randomLimits.get(4),randomLimits.get(5));
		responsibleGamingPage.submitDepositLimit();
        TypeUtils.assertTrueWithLogs(responsibleGamingPage.depositsChangedSuccessfullyMessageVisible(), "successfullySetLimitsMessageDisplayed");
	}

    /* Negative */

    /*1. 2,1,-: Daily > Weekly & Monthly = not defined*/
	@Test(groups = {"regression"})
	public void setInvalidDepositLimits210(){
        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.responsibleGaming, defaultUserData.getRegisteredUserData());
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits(randomLimits.get(2),randomLimits.get(1), "0");
		responsibleGamingPage.submitDepositLimit();
		TypeUtils.assertTrueWithLogs(responsibleGamingPage.dailyValidationErrorMessageVisible(), "dailyErrorVisible");
        TypeUtils.assertTrueWithLogs(responsibleGamingPage.weeklyValidationErrorMessageVisible(),"weeklyErrorVisible");
        TypeUtils.assertFalseWithLogs(responsibleGamingPage.monthlyValidationErrorMessageVisible(),"monthlyErrorVisible");
	}

    /*2. -,2,1: Weekly > Monthly & Daily = not defined*/
	@Test(groups = {"regression"})
	public void setInvalidDepositLimits021(){
        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.responsibleGaming, defaultUserData.getRegisteredUserData());
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits("0",randomLimits.get(2), randomLimits.get(1));
		responsibleGamingPage.submitDepositLimit();
		TypeUtils.assertFalseWithLogs(responsibleGamingPage.dailyValidationErrorMessageVisible(),"dailyErrorVisible");
        TypeUtils.assertTrueWithLogs(responsibleGamingPage.weeklyValidationErrorMessageVisible(),"weeklyErrorVisible");
        TypeUtils.assertTrueWithLogs(responsibleGamingPage.monthlyValidationErrorMessageVisible(),"monthlyErrorVisible");
	}

    /*3. 2,-,1: Daily > Monthly & Weekly = not defined */
	@Test(groups = {"regression"})
	public void setInvalidDepositLimits201(){
        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.responsibleGaming, defaultUserData.getRegisteredUserData());
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits(randomLimits.get(2),"0", randomLimits.get(1));
		responsibleGamingPage.submitDepositLimit();
		TypeUtils.assertTrueWithLogs(responsibleGamingPage.dailyValidationErrorMessageVisible(),"dailyErrorVisible");
        TypeUtils.assertFalseWithLogs(responsibleGamingPage.weeklyValidationErrorMessageVisible(),"weeklyErrorVisible");
        TypeUtils.assertTrueWithLogs(responsibleGamingPage.monthlyValidationErrorMessageVisible(),"monthlyErrorVisible");
	}

    /*4. 3,2,1: Daily > Weekly > Monthly*/
	@Test(groups = {"regression"})
	public void setInvalidDepositLimits321(){
        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.responsibleGaming, defaultUserData.getRegisteredUserData());
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits(randomLimits.get(3),randomLimits.get(2), randomLimits.get(1));
		responsibleGamingPage.submitDepositLimit();
		TypeUtils.assertTrueWithLogs(responsibleGamingPage.dailyValidationErrorMessageVisible(),"dailyErrorVisible");
        TypeUtils.assertTrueWithLogs(responsibleGamingPage.weeklyValidationErrorMessageVisible(),"weeklyErrorVisible");
        TypeUtils.assertTrueWithLogs(responsibleGamingPage.monthlyValidationErrorMessageVisible(),"monthlyErrorVisible");
	}

    /*5. 2,3,1: Daily < Weekly & Weekly > Monthly*/
	@Test(groups = {"regression"})
	public void setInvalidDepositLimits231(){
        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.responsibleGaming, defaultUserData.getRegisteredUserData());
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits(randomLimits.get(2),randomLimits.get(3), randomLimits.get(1));
		responsibleGamingPage.submitDepositLimit();
		TypeUtils.assertTrueWithLogs(responsibleGamingPage.dailyValidationErrorMessageVisible(),"dailyErrorVisible");
        TypeUtils.assertTrueWithLogs(responsibleGamingPage.weeklyValidationErrorMessageVisible(),"weeklyErrorVisible");
        TypeUtils.assertTrueWithLogs(responsibleGamingPage.monthlyValidationErrorMessageVisible(),"monthlyErrorVisible");
	}

    /*6. 2,1,3: Daily > weekly & daily < monthly*/
	@Test(groups = {"regression"})
	public void setInvalidDepositLimits213(){
        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.responsibleGaming, defaultUserData.getRegisteredUserData());
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits(randomLimits.get(2),randomLimits.get(1), randomLimits.get(3));
		responsibleGamingPage.submitDepositLimit();
		TypeUtils.assertTrueWithLogs(responsibleGamingPage.dailyValidationErrorMessageVisible(),"dailyErrorVisible");
        TypeUtils.assertTrueWithLogs(responsibleGamingPage.weeklyValidationErrorMessageVisible(),"weeklyErrorVisible");
        TypeUtils.assertFalseWithLogs(responsibleGamingPage.monthlyValidationErrorMessageVisible(),"monthlyErrorVisible");
	}

    /*7. 3,1,2: daily > weekly & daily > monthly*/
	@Test(groups = {"regression"})
	public void setInvalidDepositLimits312(){
        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.responsibleGaming, defaultUserData.getRegisteredUserData());
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits(randomLimits.get(3),randomLimits.get(1), randomLimits.get(2));
		responsibleGamingPage.submitDepositLimit();
		TypeUtils.assertTrueWithLogs(responsibleGamingPage.dailyValidationErrorMessageVisible(),"dailyErrorVisible");
        TypeUtils.assertTrueWithLogs(responsibleGamingPage.weeklyValidationErrorMessageVisible(), "weeklyErrorVisible");
        TypeUtils.assertTrueWithLogs(responsibleGamingPage.monthlyValidationErrorMessageVisible(),"monthlyErrorVisible");
	}

    /*8. 1,3,2: daily < weekly & weekly > monthly*/
	@Test(groups = {"regression"})
	public void setInvalidDepositLimits132(){
        ResponsibleGamingPage responsibleGamingPage = (ResponsibleGamingPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.responsibleGaming, defaultUserData.getRegisteredUserData());
		List<String> randomLimits = responsibleGamingPage.getSortedRandomLimitsList();
		responsibleGamingPage.setDepositLimits(randomLimits.get(1),randomLimits.get(3), randomLimits.get(2));
		responsibleGamingPage.submitDepositLimit();
		TypeUtils.assertFalseWithLogs(responsibleGamingPage.dailyValidationErrorMessageVisible(),"dailyErrorVisible");
        TypeUtils.assertTrueWithLogs(responsibleGamingPage.weeklyValidationErrorMessageVisible(),"weeklyErrorVisible");
        TypeUtils.assertTrueWithLogs(responsibleGamingPage.monthlyValidationErrorMessageVisible(),"monthlyErrorVisible");
	}

}
