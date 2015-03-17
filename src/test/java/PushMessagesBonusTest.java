import enums.*;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.bonus.AcceptDeclineBonusPopup;
import pageObjects.bonus.LoseOnWithdrawPopup;
import pageObjects.bonus.OkBonusPopup;
import pageObjects.cashier.withdraw.WithdrawPage;
import pageObjects.core.AbstractPortalPage;
import pageObjects.core.AbstractPortalPopup;
import pageObjects.gamesPortlet.GameLaunchPage;
import springConstructors.UserData;
import utils.*;
import utils.core.AbstractTest;
import utils.core.DataContainer;

public class PushMessagesBonusTest extends AbstractTest{

	private static final String BONUS_AMOUNT = "10.00";
    private static boolean refreshPage = true;

    /*Online Non-declinable Ok*/
    @Test(groups = {"regression", "mobile"})
    public void onlineNonDeclinableOk(){
        UserData userData = DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData);
        IMSUtils.sendPushMessage(userData, BONUS_AMOUNT, Page.okBonus);
        OkBonusPopup okBonusPopup = (OkBonusPopup)NavigationUtils.closeAllPopups(Page.okBonus);
        okBonusPopup.assertPopupTitleText("Congratulations");
        okBonusPopup.closePopup();
        assertEquals(BONUS_AMOUNT, new AbstractPortalPage().getBalanceAmount(refreshPage), "Balance");
    }

    /*Online Non-declinable offPopup*/
    @Test(groups = {"regression", "mobile"})
    public void onlineNonDeclinableOffPOpup(){
        UserData userData = DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData);
        IMSUtils.sendPushMessage(userData, BONUS_AMOUNT, Page.okBonus);
        OkBonusPopup okBonusPopup = (OkBonusPopup)NavigationUtils.closeAllPopups(Page.okBonus);
        okBonusPopup.assertPopupTitleText("Congratulations");
        okBonusPopup.clickOffPopup();
        assertEquals(BONUS_AMOUNT, new AbstractPortalPage().getBalanceAmount(refreshPage), "Balance");
    }

    /*Online Declinable accept*/
    @Test(groups = {"regression", "mobile"})
    public void onlineAccept(){
        //*skipTest("D-18046");
        UserData userData = DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData);
        IMSUtils.sendPushMessage(userData, BONUS_AMOUNT, Page.acceptDeclineBonus);
        assertEquals("0.00", new AbstractPortalPage().getBalanceAmount(), "Balance");
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup)NavigationUtils.closeAllPopups(Page.acceptDeclineBonus);
        acceptDeclineBonusPopup.clickAccept();
        assertEquals(BONUS_AMOUNT, new AbstractPortalPage().getBalanceAmount(refreshPage), "Balance");
    }

    /*Online Declinable decline*/
    @Test(groups = {"regression", "mobile"})
    public void onlineDecline(){
        //skipTest("D-18046");
        UserData userData = DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData);
        IMSUtils.sendPushMessage(userData, BONUS_AMOUNT, Page.acceptDeclineBonus);
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup)NavigationUtils.closeAllPopups(Page.acceptDeclineBonus);
        acceptDeclineBonusPopup.clickDecline();
        assertEquals("0.00", new AbstractPortalPage().getBalanceAmount(), "Balance");
    }

    /*Online Declinable clickOffPopup*/
    @Test(groups = {"regression", "mobile"})
    public void onlineOffPopup(){
        //skipTest("D-18046");
        UserData userData = DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData);
        IMSUtils.sendPushMessage(userData, BONUS_AMOUNT, Page.acceptDeclineBonus);
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup)NavigationUtils.closeAllPopups(Page.acceptDeclineBonus);
        acceptDeclineBonusPopup.clickOffPopup();
        new AcceptDeclineBonusPopup();
        assertEquals("0.00", new AbstractPortalPage().getBalanceAmount(), "Balance");
        acceptDeclineBonusPopup.closePopup();
    }

    /*Offline Non-declinable Ok*/
    @Test(groups = {"regression", "mobile"})
    public void offlineNonDeclinableOk(){
        skipTestWithIssues("Not described bug, was sent letter");
        UserData userData = DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData);
        PortalUtils.logout();
        IMSUtils.sendPushMessage(userData, BONUS_AMOUNT, Page.okBonus);
        //---PortalUtils.loginUser(userData, Page.homePage);
        OkBonusPopup okBonusPopup = (OkBonusPopup)PortalUtils.loginUser(userData, Page.okBonus);
        okBonusPopup.assertPopupTitleText("Congratulations");
        okBonusPopup.closePopup();
        assertEquals(BONUS_AMOUNT, new AbstractPortalPage().getBalanceAmount(refreshPage), "Balance");
    }

    /*Offline Non-declinable offPopup*/
    @Test(groups = {"regression", "mobile"})
    public void offlineNonDeclinableOffPOpup(){
        skipTestWithIssues("Not described bug, was sent letter");
        UserData userData = DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData);
        PortalUtils.logout();
        IMSUtils.sendPushMessage(userData, BONUS_AMOUNT, Page.okBonus);
        //---PortalUtils.loginUser(userData, Page.homePage);
        OkBonusPopup okBonusPopup = (OkBonusPopup)PortalUtils.loginUser(userData, Page.okBonus);
        okBonusPopup.assertPopupTitleText("Congratulations");
        okBonusPopup.clickOffPopup();
        assertEquals(BONUS_AMOUNT, new AbstractPortalPage().getBalanceAmount(refreshPage), "Balance");
    }

    /*Offline Declinable accept*/
    @Test(groups = {"regression", "mobile"})
    public void offlineAccept(){
//        skipTestWithIssues("D-18046");
        UserData userData = DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData);
        PortalUtils.logout();
        IMSUtils.sendPushMessage(userData, BONUS_AMOUNT, Page.acceptDeclineBonus);
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup)PortalUtils.loginUser(userData, Page.acceptDeclineBonus);
        acceptDeclineBonusPopup.clickAccept();
        assertEquals(BONUS_AMOUNT, new AbstractPortalPage().getBalanceAmount(refreshPage), "Balance");
    }

    /*Offline Declinable decline*/
    @Test(groups = {"regression", "mobile"})
    public void offlineDecline(){
//        skipTestWithIssues("D-18046");
        UserData userData = DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData);
        PortalUtils.logout();
        IMSUtils.sendPushMessage(userData, BONUS_AMOUNT, Page.acceptDeclineBonus);
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup)PortalUtils.loginUser(userData,Page.acceptDeclineBonus);
        acceptDeclineBonusPopup.clickDecline();
        assertEquals("0.00", new AbstractPortalPage().getBalanceAmount(), "Balance");
    }

    /*Offline Declinable clickOffPopup*/
    @Test(groups = {"regression", "mobile"})
    public void offlineOffPopup(){
//        skipTestWithIssues("D-18046");
        UserData userData = DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData);
        PortalUtils.logout();
        IMSUtils.sendPushMessage(userData, BONUS_AMOUNT, Page.acceptDeclineBonus);
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup)PortalUtils.loginUser(userData, Page.acceptDeclineBonus);
        acceptDeclineBonusPopup.clickOffPopup();
        new AcceptDeclineBonusPopup();
        assertEquals("0.00", new AbstractPortalPage().getBalanceAmount(), "Balance");
        acceptDeclineBonusPopup.closePopup();
    }

    /*Game Non-declinable*/
    @Test(groups = {"regression", "mobile"})
    public void gameNonDeclinableOk(){
//        skipTestWithIssues();
        UserData userData = DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData);
        GameLaunchPage gameLaunchPage = NavigationUtils.launchGameByUrl(userData);
        IMSUtils.sendPushMessage(userData, BONUS_AMOUNT, Page.okBonus);
        OkBonusPopup okBonusPopup = (OkBonusPopup)NavigationUtils.closeAllPopups(Page.okBonus);
        okBonusPopup.assertPopupTitleText("Congratulations");
        okBonusPopup.closePopup();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.home, userData);
        assertEquals(BONUS_AMOUNT, homePage.getBalanceAmount(refreshPage), "Balance");
        //NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.home);
    }

    /*Game Declinable accept*/
    @Test(groups = {"regression", "mobile"})
    public void gameAccept(){
//        skipTestWithIssues("D-18046");
        UserData userData = DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData);
        GameLaunchPage gameLaunchPage = NavigationUtils.launchGameByUrl(userData);
        IMSUtils.sendPushMessage(userData, BONUS_AMOUNT, Page.acceptDeclineBonus);
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup)NavigationUtils.closeAllPopups(Page.acceptDeclineBonus);
        acceptDeclineBonusPopup.clickAccept();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.home, userData);
        assertEquals(BONUS_AMOUNT, homePage.getBalanceAmount(refreshPage), "Balance");
    }

    /*Game Declinable decline*/
    @Test(groups = {"regression", "mobile"})
    public void gameDecline(){
//        skipTestWithIssues("D-18046");
        UserData userData = DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData);
        GameLaunchPage gameLaunchPage = NavigationUtils.launchGameByUrl(userData);
        IMSUtils.sendPushMessage(userData, BONUS_AMOUNT, Page.acceptDeclineBonus);
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup)NavigationUtils.closeAllPopups(Page.acceptDeclineBonus);
        acceptDeclineBonusPopup.clickDecline();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.home, userData);
        assertEquals("0.00", homePage.getBalanceAmount(refreshPage), "Balance");
    }

    /*Online Non-declinable refresh*/
    @Test(groups = {"regression", "mobile"})
    public void onlineNonDeclinableRefresh(){
        UserData userData = DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData);
        IMSUtils.sendPushMessage(userData, BONUS_AMOUNT, Page.okBonus);
        OkBonusPopup okBonusPopup = (OkBonusPopup)NavigationUtils.closeAllPopups(Page.okBonus);
        okBonusPopup.assertPopupTitleText("Congratulations");
        WebDriverUtils.refreshPage();
        assertFalse(WebDriverUtils.isVisible(AbstractPortalPopup.ROOT_XP, 5), "Bonus visible");
        assertEquals(BONUS_AMOUNT, new AbstractPortalPage().getBalanceAmount(refreshPage), "Balance");
    }

    /*Online Declinable refresh*/
    @Test(groups = {"regression", "mobile"})
    public void onlineDeclinableRefresh(){
//        skipTestWithIssues("D-18046");
        UserData userData = DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData);
        IMSUtils.sendPushMessage(userData, BONUS_AMOUNT, Page.acceptDeclineBonus);
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup) NavigationUtils.closeAllPopups(Page.acceptDeclineBonus);
        WebDriverUtils.refreshPage();
        acceptDeclineBonusPopup = new AcceptDeclineBonusPopup();
        acceptDeclineBonusPopup.clickAccept();
        assertEquals(BONUS_AMOUNT, new AbstractPortalPage().getBalanceAmount(refreshPage), "Balance");
    }

    /*Online Non-declinable and Declinable refresh*/
    @Test(groups = {"regression", "mobile"})
    public void onlineMultipleRefresh() {
//        skipTestWithIssues("D-18046");
        UserData userData = DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData);
        IMSUtils.sendPushMessage(userData, BONUS_AMOUNT, Page.okBonus, Page.acceptDeclineBonus);
        OkBonusPopup okBonusPopup = new OkBonusPopup();
        okBonusPopup.assertPopupTitleText("Congratulations (1/2)");
        validateTrue(okBonusPopup.twoPopUpsIsAppeared(), "Two bonus popups weren't appeared!");
        WebDriverUtils.refreshPage();
        okBonusPopup.assertPopupTitleText("Congratulations");
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = new AcceptDeclineBonusPopup();
        acceptDeclineBonusPopup.clickAccept();
        assertEquals(TypeUtils.calculateSum(BONUS_AMOUNT, BONUS_AMOUNT), new AbstractPortalPage().getBalanceAmount(refreshPage), "Balance");
    }

    /*Online Non-declinable and Declinable x2 navigation*/
    @Test(groups = {"regression", "mobile"})
    public void onlineMultipleNavigation() {
//        skipTestWithIssues("D-18046");
        UserData userData = DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData);
        IMSUtils.sendPushMessage(userData, BONUS_AMOUNT, Page.okBonus, Page.acceptDeclineBonus, Page.acceptDeclineBonus);
        OkBonusPopup okBonusPopup = new OkBonusPopup();
        okBonusPopup.assertPopupTitleText("Congratulations (1/3)");
        validateTrue(okBonusPopup.threePopUpsIsAppeared(), "Three bonus popups weren't appeared!");
        okBonusPopup.clickNext();
        okBonusPopup.assertPopupTitleText("Congratulations (2/3)");
        new AcceptDeclineBonusPopup().clickNext();
        okBonusPopup.assertPopupTitleText("Congratulations (3/3)");
        new AcceptDeclineBonusPopup().clickPrevious();
        okBonusPopup.assertPopupTitleText("Congratulations (2/3)");
        new AcceptDeclineBonusPopup().clickPrevious();
        okBonusPopup.assertPopupTitleText("Congratulations (1/3)");
        NavigationUtils.closeAllPopups(Page.homePage);
    }

    /*Online Non-declinable and Declinable x2 close start*/
    @Test(groups = {"regression", "mobile"})
    public void onlineMultipleNavigationCloseStart() {
        //skipTestWithIssues("D-18046");
        UserData userData = DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData);
        IMSUtils.sendPushMessage(userData, BONUS_AMOUNT, Page.okBonus, Page.acceptDeclineBonus, Page.acceptDeclineBonus);
        OkBonusPopup okBonusPopup = new OkBonusPopup();
        okBonusPopup.assertPopupTitleText("Congratulations (1/3)");
        validateTrue(okBonusPopup.threePopUpsIsAppeared(), "Three bonus popups weren't appeared!");
        okBonusPopup.closePopup();
        okBonusPopup.assertPopupTitleText("Congratulations (1/2)");
        new AcceptDeclineBonusPopup().clickDecline();
        okBonusPopup.assertPopupTitleText("Congratulations");
        new AcceptDeclineBonusPopup().clickDecline();
    }

    /*Online Non-declinable and Declinable x2 close end*/
    @Test(groups = {"regression", "mobile"})
    public void onlineMultipleNavigationCloseEnd() {
//        skipTestWithIssues("D-18046");
        UserData userData = DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData);
        IMSUtils.sendPushMessage(userData, BONUS_AMOUNT, Page.okBonus, Page.acceptDeclineBonus, Page.acceptDeclineBonus);
        OkBonusPopup okBonusPopup = new OkBonusPopup();
        validateTrue(okBonusPopup.threePopUpsIsAppeared(), "Three bonus popups weren't appeared!");
        okBonusPopup.assertPopupTitleText("Congratulations (1/3)");
        okBonusPopup.clickNext();
        okBonusPopup.assertPopupTitleText("Congratulations (2/3)");
        new AcceptDeclineBonusPopup().clickNext();
        okBonusPopup.assertPopupTitleText("Congratulations (3/3)");
        new AcceptDeclineBonusPopup().closePopup();
        okBonusPopup.assertPopupTitleText("Congratulations (2/3)");
        new AcceptDeclineBonusPopup().closePopup();
        okBonusPopup.assertPopupTitleText("Congratulations (1/3)");
        new OkBonusPopup();
    }

//	/*7. Push messages for bonus opt in/out */
//	//@Test(groups = {"regression", "mobile"})
//	public void pushMessageOptIn(){
//        UserData userData = DataContainer.getUserData().getRandomUserData();
//		PortalUtils.registerUser(userData);
//        BonusPage bonusPage = (BonusPage) NavigationUtils.navigateToPage(ConfiguredPages.bonusPage);
//		OptInPopup optInPopup = bonusPage.clickOptIn();
//		optInPopup.clickOptIn().closePopup();
//		OptOutPopup optOutPopup = bonusPage.clickOptOutBonus();
//		optOutPopup.clickOptOut().closePopup();
//	}

//	/*8. Push message for bonus buy in */
//	//@Test(groups = {"regression", "mobile"})
//	public void pushMessageBuyIn(){
//		UserData userData = DataContainer.getUserData().getRegisteredUserData();
//        HomePage homePage=(HomePage)NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.home, userData);
//		String balance = homePage.getBalance();
//        BonusPage bonusPage = (BonusPage) NavigationUtils.navigateToPage(ConfiguredPages.bonusPage);
//		BuyInPopup buyInPopup = bonusPage.clickBuyIn();
//		BonusBuyInPopup bonusBuyInPopup = buyInPopup.clickBuyIn();
//		bonusBuyInPopup.confirmBuyIn();
//		assertTrue(homePage.getBalanceChange(balance)==20);
//	}

    /*B-11130 Lose bonus confirmation on withdraw, fund transfer*/
    /*#1. Click Decline button*/
    @Test(groups = {"registration","regression"})
    public void loseOnWithdrawAccept(){
        skipTestWithIssues("D-18046, B-11130");
        UserData userData=DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData, true, true, PromoCode.valid, Page.homePage); // real money 10
        addLooseOnWithdrawBonus(userData, BONUS_AMOUNT); // bonus money 10
//        UserData userData=DataContainer.getUserData().getRegisteredUserData();
//        userData.setUsername("HSekq");
//        PortalUtils.loginUser(userData);
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        assertEquals("20.00", withdrawPage.getBalanceAmount(refreshPage), "Balance is 20 (10 real + 10 bonus).");
        withdrawPage.withdraw(PaymentMethod.PayPal, PromoCode.valid.getAmount());
        LoseOnWithdrawPopup loseOnWithdrawPopup = new LoseOnWithdrawPopup();
        loseOnWithdrawPopup.clickAccept();
        assertEquals("0.00", new AbstractPortalPage().getBalanceAmount(), "Balance");
    }

    private static void addLooseOnWithdrawBonus(UserData userData, String amount) {
        IMSUtils.sendPushMessage(userData, amount, Page.loseOnWithdraw);
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup) NavigationUtils.closeAllPopups(Page.acceptDeclineBonus);
        acceptDeclineBonusPopup.clickAccept();
    }
}
