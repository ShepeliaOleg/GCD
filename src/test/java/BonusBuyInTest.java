import enums.ConfiguredPages;
import enums.Page;
import enums.PlayerCondition;
import enums.PromoCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.bonus.BonusPage;
import pageObjects.bonus.OkBonusPopup;
import pageObjects.bonus.OptedInPopup;
import pageObjects.core.AbstractPortalPage;
import pageObjects.core.AbstractPortalPopup;
import springConstructors.BonusData;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

/**
 * Created by Vadymfe on 3/12/2015.
 */
public class BonusBuyInTest extends AbstractTest {

    private UserData userData;
    private BonusPage bonusPage;
    private OptedInPopup optedInPopup;
    private OkBonusPopup okBonusPopup;
    private String expErrMsg = "You do not have enough money to buy bonus";
    private String expInfoMsg = "Get a bonus of ";

    @Autowired
    @Qualifier("buyInMin")
    private BonusData buyInMin;

    @Autowired
    @Qualifier("buyInAvg")
    private BonusData buyInAvg;

    @Autowired
    @Qualifier("buyInMax")
    private BonusData buyInMax;

    //min amount < Real balance < max amount
    @Test(groups = {"regression"})
    public void addBuyInBonusAmount() {
        userData = DataContainer.getUserData().getRandomUserData();
        userData.setCurrency("USD");
        PortalUtils.registerUser(userData, true, true, PromoCode.valid, Page.homePage);
        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.bonusPage);

        bonusPage.getBonus(buyInAvg.getBonusID(), buyInAvg.getGetBonusButtonTitle());
        new OptedInPopup().confirmBuyInBonus();

        new AbstractPortalPopup().closePopup();
        assertEquals("40.00", new AbstractPortalPage().getBalanceAmount(), "The current user amount isn't correspond expected bonus amount!");
    }

    @Test(groups = {"regression"})
    public void congratsPopUpIsAppeared() {
        userData = DataContainer.getUserData().getRandomUserData();
        userData.setCurrency("EUR");
        PortalUtils.registerUser(userData, true, true, PromoCode.valid, Page.homePage);
        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.bonusPage);

        bonusPage.getBonus(buyInAvg.getBonusID(), buyInAvg.getGetBonusButtonTitle());
        //optedInPopup = new OptedInPopup();
        //optedInPopup.confirmBuyInBonus();
        new OptedInPopup().confirmBuyInBonus();

        okBonusPopup = new OkBonusPopup();
        okBonusPopup.assertPopupTitleText("");
        okBonusPopup.assertPopupContentText("Congratulations, you just received €20");
        okBonusPopup.closePopup();
    }

    @Test(groups = {"regression"})
    public void checkOptedInPopupAndClose() {
        NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);

        userData = DataContainer.getUserData().getRandomUserData();
        userData.setCurrency("EUR");
        PortalUtils.registerUser(userData, true, true, PromoCode.valid, Page.homePage);
        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.bonusPage);

        String bonusTitle = bonusPage.getBonusTitle(buyInAvg.getBonusID());
        bonusPage.getBonus(buyInAvg.getBonusID(), buyInAvg.getGetBonusButtonTitle());
        optedInPopup = new OptedInPopup();
        optedInPopup.assertOptedInPopupIsCorrect(buyInAvg.getBonusID(), bonusTitle, buyInAvg.getBonusAmount(), expInfoMsg+"€20.00");
        optedInPopup.closePopup();
    }

    @Test(groups = {"regression"})
    public void getMinimalCheckErrorMsg() {
        userData = DataContainer.getUserData().getRandomUserData();
        userData.setCurrency("EUR");
        PortalUtils.registerUser(userData, true, true, PromoCode.valid, Page.homePage);
        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.bonusPage);

        bonusPage.getBonus(buyInMin.getBonusID(), buyInMin.getGetBonusButtonTitle());
        optedInPopup = new OptedInPopup();
        optedInPopup.assertErrorMessage(expErrMsg);
    }

    @Test(groups = {"regression"})
    public void getMaximalCheckPopupAndClose() {
        userData = DataContainer.getUserData().getRandomUserData();
        //userData.setCurrency("EUR");
        userData.setCurrency("USD");
        PortalUtils.registerUser(userData, true, true, PromoCode.valid, Page.homePage);
        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.bonusPage);

        String bonusTitle = bonusPage.getBonusTitle(buyInMax.getBonusID());
        bonusPage.getBonus(buyInMax.getBonusID(), buyInMax.getGetBonusButtonTitle());
        optedInPopup = new OptedInPopup();
        optedInPopup.assertOptedInPopupIsCorrect(buyInMax.getBonusID(), bonusTitle, buyInMax.getBonusAmount(), expInfoMsg+"$57.00" );
    }

    @Test(groups = {"regression"})
    public void getMaximalCheckBonusAmount() {
        userData = DataContainer.getUserData().getRandomUserData();
        userData.setCurrency("USD");
        PortalUtils.registerUser(userData, true, true, PromoCode.valid, Page.homePage);
        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.bonusPage);

        bonusPage.getBonus(buyInMax.getBonusID(), buyInMax.getGetBonusButtonTitle());
        new OptedInPopup().confirmBuyInBonus();

        new AbstractPortalPopup().closePopup();
        assertEquals("67.00", new AbstractPortalPage().getBalanceAmount(), "The current user amount isn't correspond expected bonus amount!");
    }

    //Todo Validation amount field


    //@Test(groups = {"regression"})
    public void closePopup() {
        NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.home);

        userData = DataContainer.getUserData().getRandomUserData();
        userData.setCurrency("EUR");
        PortalUtils.registerUser(userData, true, true, PromoCode.valid, Page.homePage);
        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.bonusPage);

        bonusPage.getBonus(buyInMin.getBonusID(), buyInMin.getGetBonusButtonTitle());
        OptedInPopup optedInPopup = new OptedInPopup();
        optedInPopup.closePopup();
    }
}
