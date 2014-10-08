import enums.ConfiguredPages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.account.deposit.DepositPage;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.core.AbstractTest;

public class DepositMethodsTest extends AbstractTest{

    @Autowired
    @Qualifier("userData")
    private UserData defaultUserData;

    @Test(groups = {"regression", "mobile"})
    public void QIWIIsVisible(){
        UserData userData = defaultUserData.getRandomUserData();
        userData.setCurrency("RUB");
        userData.setCountry("RU");
        PortalUtils.registerUser(userData);
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.assertQIWIInterface();
    }
}
