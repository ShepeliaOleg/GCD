import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.bonus.BonusPage;
import pageObjects.core.AbstractPortalPage;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

public class BonusTest extends AbstractTest {

    private UserData userData;
    private HomePage homePage;
    private BonusPage bonusPage;
    private static final String bonusID = "44730";
    private static final String bonusAmount = "10.00";

    @Test(groups = {"regression"})
    public void addFreeBonusAmount() {
        userData = DataContainer.getUserData().getRandomUserData();
        userData.setCurrency("USD");
        homePage = PortalUtils.registerUser(userData);
        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.bonusPage);
        bonusPage.getFreeBonus(bonusID);

        assertEquals(bonusAmount, new AbstractPortalPage().getBalanceAmount(), "The current user amount isn't correspond expected bonus amount!");
    }

    //@Test
    //public void checkBonusPopUp() {
    //    bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.bonusPage);
    //    bonusPage.clickFreeBonusLink(bonusID);
    //    System.out.println("12345");
    //}

}
