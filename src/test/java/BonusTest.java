import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.bonus.BonusPage;
import pageObjects.bonus.FreeBonusPopup;
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
    private static final String GET_FREE_BONUS_BUTTON_TITLE = "Get free bonus";
    private static final String LINKS_TO_TC_BUTTON_TITLE = "Links to T&Cs";

    @Test(groups = {"regression"})
    public void addFreeBonusAmount() {
        userData = DataContainer.getUserData().getRandomUserData();
        userData.setCurrency("USD");
        homePage = PortalUtils.registerUser(userData);
        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.bonusPage);

        //- ADD +15 Euro
        //bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.bonusPage);

        bonusPage.getFreeBonus(bonusID);

        assertEquals(bonusAmount, new AbstractPortalPage().getBalanceAmount(), "The current user amount isn't correspond expected bonus amount!");
        PortalUtils.logout();
    }

    @Test(groups = {"regression"})
    public void freeBonusPopUp() {
        //- Another user, another currency, USUAL test FAILS
        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.bonusPage);

        //- New register user test PASS
        //userData = DataContainer.getUserData().getRandomUserData();
        //userData.setCurrency("USD");
        //homePage = PortalUtils.registerUser(userData);
        //bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.bonusPage);

        String bonusTitle = bonusPage.getBonusTitle(bonusID);
        FreeBonusPopup freeBonusPopup = (FreeBonusPopup) bonusPage.clickFreeBonusLink(bonusID);
        freeBonusPopup.assertViewFreeBonusPopup(bonusTitle, GET_FREE_BONUS_BUTTON_TITLE, LINKS_TO_TC_BUTTON_TITLE);
    }
}
