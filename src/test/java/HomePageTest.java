import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.account.BalancePage;
import pageObjects.header.NavigationPanel;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.core.AbstractTest;

public class HomePageTest extends AbstractTest{

	@Autowired
	@Qualifier("userData")
	private UserData defaultUserData;

	@Test(groups = {"smoke"})
	public void openHomepageAndVerifyNavigation(){
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(ConfiguredPages.home);
		NavigationPanel navigationPanel= homePage.navigationPanel();
	}

	@Test(groups = {"smoke"})
	public void portletIsDisplayedOnMyAccountBalancePage() {
        BalancePage balancePortlet = (BalancePage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.balance, defaultUserData.getRegisteredUserData());
	}
}
