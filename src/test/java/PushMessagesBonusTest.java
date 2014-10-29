import enums.ConfiguredPages;
import enums.Page;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.bonus.AcceptDeclineBonusPopup;
import pageObjects.bonus.BonusPage;
import pageObjects.bonus.FreeBonusPopup;
import pageObjects.bonus.OkBonusPopup;
import pageObjects.core.AbstractPage;
import pageObjects.core.AbstractPopup;
import pageObjects.gamesPortlet.GameLaunchPage;
import springConstructors.IMS;
import springConstructors.UserData;
import springConstructors.ValidationRule;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.TypeUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

public class PushMessagesBonusTest extends AbstractTest{

	@Autowired
	@Qualifier("passwordValidationRule")
	private ValidationRule passwordValidationRule;

	@Autowired
	@Qualifier("iMS")
	private IMS iMS;

	@Autowired
	@Qualifier("emailValidationRule")
	private ValidationRule emailValidationRule;

	@Autowired
	@Qualifier("userData")
	private UserData defaultUserData;

	private static final String BONUS_AMOUNT = "10.00";

    /*Online Non-declinable Ok*/
    @Test(groups = {"regression", "mobile"})
    public void onlineNonDeclinableOk(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.okBonus);
        OkBonusPopup okBonusPopup = (OkBonusPopup)NavigationUtils.closeAllPopups(Page.okBonus);
        okBonusPopup.closePopup();
        assertEquals(BONUS_AMOUNT, new AbstractPage().getBalanceAmount(), "Balance");
    }

    /*Online Non-declinable offPopup*/
    @Test(groups = {"regression", "mobile"})
    public void onlineNonDeclinableOffPOpup(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.okBonus);
        OkBonusPopup okBonusPopup = (OkBonusPopup)NavigationUtils.closeAllPopups(Page.okBonus);
        okBonusPopup.clickOffPopup();
        assertEquals(BONUS_AMOUNT, new AbstractPage().getBalanceAmount(), "Balance");
    }

    /*Online Declinable accept*/
    @Test(groups = {"regression", "mobile"})
    public void onlineAccept(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.acceptDeclineBonus);
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup)NavigationUtils.closeAllPopups(Page.acceptDeclineBonus);
        acceptDeclineBonusPopup.clickAccept();
        assertEquals(BONUS_AMOUNT, new AbstractPage().getBalanceAmount(), "Balance");
    }

    /*Online Declinable decline*/
    @Test(groups = {"regression", "mobile"})
    public void onlineDecline(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.acceptDeclineBonus);
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup)NavigationUtils.closeAllPopups(Page.acceptDeclineBonus);
        acceptDeclineBonusPopup.clickDecline();
        assertEquals("0.00", new AbstractPage().getBalanceAmount(), "Balance");
    }

    /*Online Declinable clickOffPopup*/
    @Test(groups = {"regression", "mobile"})
    public void onlineOffPopup(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.acceptDeclineBonus);
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup)NavigationUtils.closeAllPopups(Page.acceptDeclineBonus);
        acceptDeclineBonusPopup.clickOffPopup();
        new AcceptDeclineBonusPopup();
        assertEquals("0.00", new AbstractPage().getBalanceAmount(), "Balance");
        acceptDeclineBonusPopup.closePopup();
    }

    /*Offline Non-declinable Ok*/
    @Test(groups = {"regression", "mobile"})
    public void offlineNonDeclinableOk(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        PortalUtils.logout();
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.okBonus);
        PortalUtils.loginUser(userData);
        OkBonusPopup okBonusPopup = (OkBonusPopup)NavigationUtils.closeAllPopups(Page.okBonus);
        okBonusPopup.closePopup();
        assertEquals(BONUS_AMOUNT, new AbstractPage().getBalanceAmount(), "Balance");
    }

    /*Offline Non-declinable offPopup*/
    @Test(groups = {"regression", "mobile"})
    public void offlineNonDeclinableOffPOpup(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.okBonus);
        OkBonusPopup okBonusPopup = (OkBonusPopup)NavigationUtils.closeAllPopups(Page.okBonus);
        okBonusPopup.clickOffPopup();
        assertEquals(BONUS_AMOUNT, new AbstractPage().getBalanceAmount(), "Balance");
    }

    /*Offline Declinable accept*/
    @Test(groups = {"regression", "mobile"})
    public void offlineAccept(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        PortalUtils.logout();
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.acceptDeclineBonus);
        PortalUtils.loginUser(userData);
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup)NavigationUtils.closeAllPopups(Page.acceptDeclineBonus);
        acceptDeclineBonusPopup.clickAccept();
        assertEquals(BONUS_AMOUNT, new AbstractPage().getBalanceAmount(), "Balance");
    }

    /*Offline Declinable decline*/
    @Test(groups = {"regression", "mobile"})
    public void offlineDecline(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        PortalUtils.logout();
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.acceptDeclineBonus);
        PortalUtils.loginUser(userData);
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup)NavigationUtils.closeAllPopups(Page.acceptDeclineBonus);
        acceptDeclineBonusPopup.clickDecline();
        assertEquals("0.00", new AbstractPage().getBalanceAmount(), "Balance");
    }

    /*Offline Declinable clickOffPopup*/
    @Test(groups = {"regression", "mobile"})
    public void offlineOffPopup(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        PortalUtils.logout();
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.acceptDeclineBonus);
        PortalUtils.loginUser(userData);
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup)NavigationUtils.closeAllPopups(Page.acceptDeclineBonus);
        acceptDeclineBonusPopup.clickOffPopup();
        new AcceptDeclineBonusPopup();
        assertEquals("0.00", new AbstractPage().getBalanceAmount(), "Balance");
        acceptDeclineBonusPopup.closePopup();
    }

    /*Game Non-declinable*/
    @Test(groups = {"regression", "mobile"})
    public void gameNonDeclinableOk(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        GameLaunchPage gameLaunchPage = NavigationUtils.launchGameByUrl(userData);
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.okBonus);
        OkBonusPopup okBonusPopup = (OkBonusPopup)NavigationUtils.closeAllPopups(Page.okBonus);
        okBonusPopup.closePopup();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.home, userData);
        assertEquals(BONUS_AMOUNT, homePage.getBalanceAmount(), "Balance");
    }

    /*Game Declinable accept*/
    @Test(groups = {"regression", "mobile"})
    public void gameAccept(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        GameLaunchPage gameLaunchPage = NavigationUtils.launchGameByUrl(userData);
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.acceptDeclineBonus);
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup)NavigationUtils.closeAllPopups(Page.acceptDeclineBonus);
        acceptDeclineBonusPopup.clickAccept();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.home, userData);
        assertEquals(BONUS_AMOUNT, homePage.getBalanceAmount(), "Balance");
    }

    /*Game Declinable decline*/
    @Test(groups = {"regression", "mobile"})
    public void gameDecline(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        GameLaunchPage gameLaunchPage = NavigationUtils.launchGameByUrl(userData);
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.acceptDeclineBonus);
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup)NavigationUtils.closeAllPopups(Page.acceptDeclineBonus);
        acceptDeclineBonusPopup.clickDecline();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.home, userData);
        assertEquals("0.00", homePage.getBalanceAmount(), "Balance");
    }

    /*Online Non-declinable refresh*/
    @Test(groups = {"regression", "mobile"})
    public void onlineNonDeclinableRefresh(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.okBonus);
        OkBonusPopup okBonusPopup = (OkBonusPopup)NavigationUtils.closeAllPopups(Page.okBonus);
        WebDriverUtils.refreshPage();
        assertFalse(WebDriverUtils.isVisible(AbstractPopup.ROOT_XP, 5), "Bonus visible");
        assertEquals(BONUS_AMOUNT, new AbstractPage().getBalanceAmount(), "Balance");
    }

    /*Online Declinable refresh*/
    @Test(groups = {"regression", "mobile"})
    public void onlineDeclinableRefresh(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.acceptDeclineBonus);
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup)NavigationUtils.closeAllPopups(Page.acceptDeclineBonus);
        WebDriverUtils.refreshPage();
        acceptDeclineBonusPopup = new AcceptDeclineBonusPopup();
        acceptDeclineBonusPopup.clickAccept();
        assertEquals(BONUS_AMOUNT, new AbstractPage().getBalanceAmount(), "Balance");
    }

    /*Online Non-declinable and Declinable refresh*/
    @Test(groups = {"regression", "mobile"})
    public void onlineMultipleRefresh() {
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.okBonus, Page.acceptDeclineBonus);
        OkBonusPopup okBonusPopup = (OkBonusPopup)NavigationUtils.closeAllPopups(Page.okBonus);
        WebDriverUtils.refreshPage();
        assertFalse(WebDriverUtils.isVisible(AbstractPopup.ROOT_XP, 5), "Bonus visible");
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = new AcceptDeclineBonusPopup();
        acceptDeclineBonusPopup.clickAccept();
        assertEquals(TypeUtils.calculateSum(BONUS_AMOUNT, BONUS_AMOUNT), new AbstractPage().getBalanceAmount(), "Balance");
    }

    /*Online Non-declinable and Declinable x2 navigation*/
    @Test(groups = {"regression", "mobile"})
    public void onlineMultipleNavigation() {
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.okBonus, Page.acceptDeclineBonus, Page.acceptDeclineBonus);
        OkBonusPopup okBonusPopup = (OkBonusPopup)NavigationUtils.closeAllPopups(Page.okBonus);
        okBonusPopup.clickNext();
        new AcceptDeclineBonusPopup().clickNext();
        new AcceptDeclineBonusPopup().clickPrevious();
        new AcceptDeclineBonusPopup().clickPrevious();
        new OkBonusPopup();
    }

    /*Online Non-declinable and Declinable x2 close start*/
    @Test(groups = {"regression", "mobile"})
    public void onlineMultipleNavigationCloseStart() {
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.okBonus, Page.acceptDeclineBonus, Page.acceptDeclineBonus);
        OkBonusPopup okBonusPopup = (OkBonusPopup)NavigationUtils.closeAllPopups(Page.okBonus);
        okBonusPopup.closePopup();
        new AcceptDeclineBonusPopup().clickDecline();
        new AcceptDeclineBonusPopup();
    }

    /*Online Non-declinable and Declinable x2 close end*/
    @Test(groups = {"regression", "mobile"})
    public void onlineMultipleNavigationCloseEnd() {
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.okBonus, Page.acceptDeclineBonus, Page.acceptDeclineBonus);
        OkBonusPopup okBonusPopup = (OkBonusPopup)NavigationUtils.closeAllPopups(Page.okBonus);
        okBonusPopup.clickNext();
        new AcceptDeclineBonusPopup().clickNext();
        new AcceptDeclineBonusPopup().closePopup();
        new AcceptDeclineBonusPopup().closePopup();
        new OkBonusPopup();
    }


//	/*7. Push messages for bonus opt in/out */
//	@Test(groups = {"regression", "mobile"})
//	public void pushMessageOptIn(){
//        UserData userData = defaultUserData.getRandomUserData();
//		PortalUtils.registerUser(userData);
//        BonusPage bonusPage = (BonusPage) NavigationUtils.navigateToPage(ConfiguredPages.bonusPage);
//		OptInPopup optInPopup = bonusPage.clickOptIn();
//		optInPopup.clickOptIn().closePopup();
//		OptOutPopup optOutPopup = bonusPage.clickOptOutBonus();
//		optOutPopup.clickOptOut().closePopup();
//	}

//	/*8. Push message for bonus buy in */
//	@Test(groups = {"regression", "mobile"})
//	public void pushMessageBuyIn(){
//		UserData userData = defaultUserData.getRegisteredUserData();
//        HomePage homePage=(HomePage)NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.home, userData);
//		String balance = homePage.getBalance();
//        BonusPage bonusPage = (BonusPage) NavigationUtils.navigateToPage(ConfiguredPages.bonusPage);
//		BuyInPopup buyInPopup = bonusPage.clickBuyIn();
//		BonusBuyInPopup bonusBuyInPopup = buyInPopup.clickBuyIn();
//		bonusBuyInPopup.confirmBuyIn();
//		assertTrue(homePage.getBalanceChange(balance)==20);
//	}
}
