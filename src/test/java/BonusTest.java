import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.bonus.BonusPage;
import pageObjects.bonus.FreeBonusPopup;
import pageObjects.core.AbstractPortalPage;
import springConstructors.BonusData;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

public class BonusTest extends AbstractTest {

    private UserData userData;
    private HomePage homePage;
    private BonusPage bonusPage;

    @Autowired
    @Qualifier("bonus")
    private BonusData bonusData;

    @Test(groups = {"regression"})
    public void addFreeBonusAmount() {
        userData = DataContainer.getUserData().getRandomUserData();
        userData.setCurrency("USD");
        homePage = PortalUtils.registerUser(userData);
        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.bonusPage);

        //- ADD +15 Euro
        //bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.bonusPage);

        bonusPage.getFreeBonus(bonusData.getBonusID());

        assertEquals(bonusData.getBonusAmount(), new AbstractPortalPage().getBalanceAmount(), "The current user amount isn't correspond expected bonus amount!");
        PortalUtils.logout();
    }

    @Test(groups = {"regression"})
    public void freeBonusPopUp() {
        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.bonusPage);

        String bonusTitle = bonusPage.getBonusTitle(bonusData.getBonusID());
        FreeBonusPopup freeBonusPopup = (FreeBonusPopup) bonusPage.clickFreeBonusLink(bonusData.getBonusID());
        freeBonusPopup.assertViewFreeBonusPopup(bonusTitle, bonusData.getGetFreeBonusButtonTitle(), bonusData.getLinksToTCbuttonTitle());
    }
}
