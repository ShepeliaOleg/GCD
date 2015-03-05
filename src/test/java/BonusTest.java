import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.bonus.BonusPage;
import pageObjects.bonus.FreeBonusPopup;
import pageObjects.bonus.OkBonusPopup;
import pageObjects.core.AbstractPortalPage;
import pageObjects.core.AbstractPortalPopup;
import springConstructors.BonusData;
import springConstructors.UserData;
import utils.IMSUtils;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

public class BonusTest extends AbstractTest {

    private UserData userData;
    private HomePage homePage;
    private BonusPage bonusPage;

    @Autowired
    @Qualifier("freeBonus")
    private BonusData freeBonus;

    @Autowired
    @Qualifier("optInBonus")
    private BonusData optInBonus;

    //FREE bonus test
    @Test(groups = {"regression"})
    public void addFreeBonusAmount() {
        userData = DataContainer.getUserData().getRandomUserData();
        userData.setCurrency("USD");
        homePage = PortalUtils.registerUser(userData);
        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.bonusPage);

        //- ADD +15 Euro
        //bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.bonusPage);
        bonusPage.getBonus(freeBonus.getBonusID(), freeBonus.getGetFreeBonusButtonTitle());
        new AbstractPortalPopup().closePopup();

        assertEquals(freeBonus.getBonusAmount(), new AbstractPortalPage().getBalanceAmount(), "The current user amount isn't correspond expected bonus amount!");
        //PortalUtils.logout();
    }

    @Test(groups = {"regression"})
    public void congratsPopUpIsAppered() {
        userData = DataContainer.getUserData().getRandomUserData();
        userData.setCurrency("USD");
        homePage = PortalUtils.registerUser(userData);
        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.bonusPage);

        bonusPage.getBonus(freeBonus.getBonusID());
        new OkBonusPopup().closePopup();
    }

    @Test(groups = {"regression"})
    public void freeBonusPopUp() {
        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.bonusPage);

        String bonusTitle = bonusPage.getBonusTitle(freeBonus.getBonusID());
        FreeBonusPopup freeBonusPopup = (FreeBonusPopup) bonusPage.clickFreeBonusLink(freeBonus.getBonusID());
        freeBonusPopup.assertViewFreeBonusPopup(bonusTitle, freeBonus.getGetFreeBonusButtonTitle(), freeBonus.getLinksToTCbuttonTitle());
    }

    @Test(groups = {"regression"})
    public void tcPopUpIsApperedFromPopup() {

        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.bonusPage);

        FreeBonusPopup freeBonusPopup = (FreeBonusPopup) bonusPage.clickFreeBonusLink(freeBonus.getBonusID());
        freeBonusPopup.clickShowTC();
    }

    @Test(groups = {"regression"})
    public void tcPopUpIsApperedFromPage() {

        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.bonusPage);
        bonusPage.clickTCLink(freeBonus.getBonusID());
    }

    //OPT-IN bonus test
    @Test(groups = {"regression"})
    public void addRemoveOptInBonus() {
        userData = DataContainer.getUserData().getRandomUserData();
        userData.setCurrency("USD");
        homePage = PortalUtils.registerUser(userData);
        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.bonusPage);

        bonusPage.getBonus(optInBonus.getBonusID(), optInBonus.getGetFreeBonusButtonTitle());
        new AbstractPortalPopup().closePopup();

        bonusPage.getBonus(optInBonus.getBonusID(), "Opt-out");
        new AbstractPortalPopup().closePopup();
    }

    @Test(groups = {"regression"})
    public void enableDisableOptInBonusAndCheckInIMS() {
        userData = DataContainer.getUserData().getRandomUserData();
        userData.setCurrency("USD");

        homePage = PortalUtils.registerUser(userData);
        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.bonusPage);

        bonusPage.getBonus(optInBonus.getBonusID());
        new AbstractPortalPopup().closePopup();
        IMSUtils.checkPlayerHasEnabledOptInBonus(userData.getUsername(), optInBonus.getBonusID());

        NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.bonusPage);
        bonusPage.getBonus(optInBonus.getBonusID());
        new AbstractPortalPopup().closePopup();
        IMSUtils.checkPlayerHasDisabledOptInBonus(userData.getUsername(), optInBonus.getBonusID());
    }
}
