import enums.ConfiguredPages;
import enums.Page;
import enums.PlayerCondition;
import enums.PromoCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.account.BalancePage;
import pageObjects.bonus.BonusPage;
import pageObjects.bonus.OkBonusPopup;
import pageObjects.bonus.OptedInPopup;
import pageObjects.gamesPortlet.GameElement;
import pageObjects.gamesPortlet.GameLaunchPage;
import pageObjects.gamesPortlet.GamesPortletPage;
import springConstructors.BonusData;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.TypeUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

public class BonusBuyInTest extends AbstractTest {

    private UserData userData;
    private BonusPage bonusPage;
    private OptedInPopup optedInPopup;
    private OkBonusPopup okBonusPopup;
    private boolean refreshPage = true;
    private String oldBalance;

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
        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.bonusPage);
        oldBalance = bonusPage.getBalanceAmount();

        bonusPage.getBonus(buyInAvg.getBonusID(), buyInAvg.getGetBonusButtonTitle());
        new OptedInPopup().getBonusAndCheckAmount(TypeUtils.calculateSum(oldBalance, buyInAvg.getBonusAmount()));
    }

    @Test(groups = {"regression"})
    public void congratsPopUpIsAppeared() {
        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.bonusPage);

        bonusPage.getBonus(buyInAvg.getBonusID(), buyInAvg.getGetBonusButtonTitle());
        new OptedInPopup().confirmBuyInBonus();

        okBonusPopup = new OkBonusPopup();
        okBonusPopup.closePopup();
        okBonusPopup.assertPopupTitleText("Congratulations");
        okBonusPopup.assertPopupContentText("Congratulations, you just received a " + getСurrencySymbol(buyInAvg.getCurrency()) + String.format("%1$,.0f", Float.parseFloat(buyInAvg.getBonusAmount())));
        okBonusPopup.closePopup();
    }

    @Test(groups = {"regression"})
    public void checkOptedInPopupAndClose() {
        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.bonusPage);
        oldBalance = bonusPage.getBalanceAmount();

        String bonusTitle = bonusPage.getBonusTitle(buyInAvg.getBonusID());
        bonusPage.getBonus(buyInAvg.getBonusID(), buyInAvg.getGetBonusButtonTitle());
        optedInPopup = new OptedInPopup();
        optedInPopup.assertOptedInPopupIsCorrect(buyInAvg.getBonusID(), bonusTitle, oldBalance, prepareExpInfoMsg(buyInAvg));
        optedInPopup.closePopup();
    }

    @Test(groups = {"regression"})
    public void getMinimalCheckErrorMsg() {
        userData = DataContainer.getUserData().getRandomUserData();
        userData.setCurrency(buyInMin.getCurrency());
        PortalUtils.registerUser(userData, true, true, PromoCode.valid, Page.homePage);
        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.bonusPage);

        bonusPage.getBonus(buyInMin.getBonusID(), buyInMin.getGetBonusButtonTitle());
        optedInPopup = new OptedInPopup();
        optedInPopup.assertErrorMessage(buyInMin.getOtherInfo());
    }

    @Test(groups = {"regression"})
    public void getMaximalCheckPopupAndClose() {
        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.bonusPage);

        String bonusTitle = bonusPage.getBonusTitle(buyInMax.getBonusID());
        bonusPage.getBonus(buyInMax.getBonusID(), buyInMax.getGetBonusButtonTitle());
        optedInPopup = new OptedInPopup();
        optedInPopup.assertOptedInPopupIsCorrect(buyInMax.getBonusID(), bonusTitle, "9.00", prepareExpInfoMsg(buyInMax));
    }

    @Test(groups = {"regression"})
    public void getMaximalCheckBonusAmount() {
        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.bonusPage);
        oldBalance = bonusPage.getBalanceAmount();

        bonusPage.getBonus(buyInMax.getBonusID(), buyInMax.getGetBonusButtonTitle());
        new OptedInPopup().getBonusAndCheckAmount(TypeUtils.calculateSum(oldBalance, buyInMax.getBonusAmount()));
    }

    @Test(groups = {"regression"})
    public void termsAndConditionsCheckboxNotApprove() {
        bonusPage = (BonusPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.bonusPage);

        bonusPage.getBonus(buyInAvg.getBonusID(), buyInAvg.getGetBonusButtonTitle());
        optedInPopup = new OptedInPopup();
        optedInPopup.clickBuyInButton();
        optedInPopup.assertErrorMessage("Please approve the T&C to continue");
    }

    //ToDo Translation Key check

    //Todo Validation tooltip amount field
    // Validation Rules


//    private String getСurrencySymbol(String currencyKey){
//        for(Object el: DataContainer.getDefaults().getCurrencyList()){
//            String line = (String ) el;
//            if (line.contains(currencyKey)) {
//                return line.split("@")[1];
//            }
//        }
//        return null;
//    }

    private String prepareExpInfoMsg(BonusData data){
        //return data.getOtherInfo() + getСurrencySymbol(data.getCurrency()) + String.format("%1$,.2f", data.getBonusAmount());
        //return data.getOtherInfo() + getСurrencySymbol(DataContainer.getUserData().getCurrency()) + String.format("%1$,.2f", data.getBonusAmount());
        return data.getOtherInfo() + getСurrencySymbol(DataContainer.getUserData().getCurrency()) + data.getBonusAmount();
    }

    //Compoints
    @Test
    public void acumulateCompointsTest(){

        BalancePage balancePage = (BalancePage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.balance );
        balancePage.convertCompPoints("Not enough points to convert");

        GamesPortletPage gamesPortletPage = (GamesPortletPage)NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.gamesFavourites);
        gamesPortletPage.playReal(4);
        gamesPortletPage.waitGameLoaded();
        balancePage = (BalancePage) gamesPortletPage.doSpin();
        oldBalance = balancePage.getBalanceAmount();
        balancePage.convertCompPoints("Compoints has been converted successfully");
        assertEquals(TypeUtils.calculateSum("25.00", oldBalance), balancePage.getBalanceAmount(true), "Add balance was not as expected");
    }
}
