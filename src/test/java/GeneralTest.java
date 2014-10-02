import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

public class GeneralTest extends AbstractTest {

    @Autowired
    @Qualifier("userData")
    private UserData defaultUserData;

    /*Session is saved after visiting external resource*/
    @Test(groups = {"regression"})
    public void sessionIsSavedAfterVisitingExternalResource(){
        UserData userData = defaultUserData.getRegisteredUserData();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.home, userData);
        WebDriverUtils.navigateToURL("http://www.google.com/");
        WebDriverUtils.waitForPageToLoad();
        homePage = (HomePage) NavigationUtils.navigateToPage(ConfiguredPages.home);
        assertTrue(homePage.isLoggedIn(), "Is still logged in");
    }
}
