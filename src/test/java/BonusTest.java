import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
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
import utils.WebDriverUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

public class BonusTest extends AbstractTest {

    private UserData userData;
    private BonusPage bonusPage;
    private OkBonusPopup okBonusPopup;

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
        PortalUtils.registerUser(userData);
        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.bonusPage);

        //- ADD +15 Euro
        //bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.bonusPage);
        bonusPage.getBonus(freeBonus.getBonusID(), freeBonus.getGetBonusButtonTitle());
        new AbstractPortalPopup().closePopup();

        assertEquals(freeBonus.getBonusAmount(), new AbstractPortalPage().getBalanceAmount(), "The current user amount isn't correspond expected bonus amount!");
        //PortalUtils.logout();
    }

    @Test(groups = {"regression"})
    public void congratsPopUpIsAppeared() {
        userData = DataContainer.getUserData().getRandomUserData();
        userData.setCurrency("USD");
        PortalUtils.registerUser(userData);
        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.bonusPage);

        okBonusPopup = bonusPage.getBonus(freeBonus.getBonusID());
        okBonusPopup.assertPopupTitleText("Congratulations");
        okBonusPopup.assertPopupContentText("Congratulations, you just received a $ 10.00 bonus! Wishing you the best of luck in our games!");
        okBonusPopup.closePopup();
    }

    @Test(groups = {"regression"})
    public void freeBonusPopUp() {
        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.bonusPage);

        String bonusTitle = bonusPage.getBonusTitle(freeBonus.getBonusID());
        FreeBonusPopup freeBonusPopup = (FreeBonusPopup) bonusPage.clickFreeBonusLink(freeBonus.getBonusID());
        freeBonusPopup.assertViewFreeBonusPopup(bonusTitle, freeBonus.getGetBonusButtonTitle(), freeBonus.getLinksToTCbuttonTitle());
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
        bonusPage.clickTCLink(optInBonus.getBonusID());
    }

    @Test(groups = {"regression"})
    public void closeBonusPopUp() {

        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.bonusPage);
        bonusPage.openAndDeclineBonus(freeBonus.getBonusID());
        assertFalse(WebDriverUtils.isTextVisible(freeBonus.getGetBonusButtonTitle()), "Bonus Multi View was not disappeared");
        bonusPage.openAndDeclineBonus(optInBonus.getBonusID());
        assertFalse(WebDriverUtils.isTextVisible(optInBonus.getGetBonusButtonTitle()), "Bonus Multi View was not disappeared");
    }

    //OPT-IN bonus test
    @Test(groups = {"regression"})
    public void checkFrontEndStatusOptInBonus() {
        userData = DataContainer.getUserData().getRandomUserData();
        userData.setCurrency("USD");
        PortalUtils.registerUser(userData);
        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.bonusPage);

        bonusPage.getBonus(optInBonus.getBonusID(), optInBonus.getGetBonusButtonTitle());
        new AbstractPortalPopup().closePopup();

        bonusPage.getBonus(optInBonus.getBonusID(), "Opt-out");
        new AbstractPortalPopup().closePopup();
    }

    @Test(groups = {"regression"})
    public void checkFrontEndStatusTwoBMVoptInBonus() {
        userData = DataContainer.getUserData().getRandomUserData();
        userData.setCurrency("USD");
        PortalUtils.registerUser(userData);
        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.bonusPage);

        bonusPage.getBonus(optInBonus.getBonusID(), optInBonus.getGetBonusButtonTitle());
        new AbstractPortalPopup().closePopup();

        bonusPage.getBonus(optInBonus.getBonusID(), "Opt-out", 2);
        new AbstractPortalPopup().closePopup();
    }

    @Test(groups = {"regression"})
    public void optInBonusOnAfterRelogin() {

        userData = DataContainer.getUserData().getRandomUserData();
        userData.setCurrency("EUR");
        PortalUtils.registerUser(userData);
        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.bonusPage);

        bonusPage.getBonus(optInBonus.getBonusID(), optInBonus.getGetBonusButtonTitle());
        new AbstractPortalPopup().closePopup();
        PortalUtils.logout();

        PortalUtils.loginUser(userData);
        NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.bonusPage);
        bonusPage.getBonus(optInBonus.getBonusID(), "Opt-out");
        new AbstractPortalPopup().closePopup();
    }

    @Test(groups = {"regression"})
    public void checkIMSstatusOptInBonus() {
        userData = DataContainer.getUserData().getRandomUserData();
        userData.setCurrency("USD");

        PortalUtils.registerUser(userData);
        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.bonusPage);

        bonusPage.getBonus(optInBonus.getBonusID());
        new AbstractPortalPopup().closePopup();
        IMSUtils.checkPlayerHasEnabledOptInBonus(userData.getUsername(), optInBonus.getBonusID());

        NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.bonusPage);
        bonusPage.getBonus(optInBonus.getBonusID());
        new AbstractPortalPopup().closePopup();
        IMSUtils.checkPlayerHasDisabledOptInBonus(userData.getUsername(), optInBonus.getBonusID());
    }

    @Test(groups = {"regression"})
    public void onOffOptInBonusPopUp() {
        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.bonusPage);

        okBonusPopup = bonusPage.getBonus(optInBonus.getBonusID());
        okBonusPopup.assertPopupTitleText("");
        okBonusPopup.assertPopupContentText("You have been successfully Opted-in to");
        okBonusPopup.closePopup();

        okBonusPopup = bonusPage.getBonus(optInBonus.getBonusID());
        okBonusPopup.assertPopupTitleText("");
        okBonusPopup.assertPopupContentText("You have been successfully Opted-out from");
        okBonusPopup.closePopup();
    }

    //Bonus Multiview
    @Test(groups = {"regression"})
    public void showEqualUnequalBonusMultiView(){
        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.bonusPage);

        assertTrue(bonusPage.isBonusDisplayed("44730"), "Configured Free bonus with correct Promotion code WAS NOT appears on BMV");
        assertFalse(bonusPage.isBonusDisplayed("44732"), "Configured Free bonus with unequal Promotion code APPEARS on BMV");

        assertTrue(bonusPage.isBonusDisplayed("44811"), "Configured Opt-in bonus with correct Promotion code WAS NOT appears on BMV");
        assertFalse(bonusPage.isBonusDisplayed("44813"), "Configured Opt-in bonus with unequal Promotion code APPEARS on BMV");
    }

    @Test(groups = {"regression"})
    public void showBonusMultiViewWithEmptyContent(){

        userData = DataContainer.getUserData().getRandomUserData();
        userData.setCurrency("USD");
        PortalUtils.registerUser(userData);
        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.bonusPage);
        //-WebDriverUtils.waitFor(2000);
        bonusPage.getBonus("44734", freeBonus.getGetBonusButtonTitle());
        new AbstractPortalPopup().closePopup();
    }
}
