import org.testng.annotations.Test;
import pageObjects.HomePage;
import springConstructors.UserData;
import utils.PortalUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

/**
 * Created by olegs on 8/13/2015.
 */
public class testClass extends AbstractTest {

    @Test(groups = {"registration","smoke","regression", "COR-1509"})
    public void validUserRegistration() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        userData.setCountry(DataContainer.getDefaults().getDefaultCountry());
        HomePage homePage = PortalUtils.registerUser();
        validateTrue(homePage.isLoggedIn(), "User is logged in");
    }
}
