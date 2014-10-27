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
    @Test(groups = {"regression", "push"})
    public void onlineNonDeclinableOk(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.okBonus);
        OkBonusPopup okBonusPopup = (OkBonusPopup)NavigationUtils.closeAllPopups(Page.okBonus);
        okBonusPopup.close();
        assertEquals(BONUS_AMOUNT, new AbstractPage().getBalanceAmount(), "Balance");
    }

    /*Online Non-declinable offPopup*/
    @Test(groups = {"regression", "push"})
    public void onlineNonDeclinableOffPOpup(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.okBonus);
        OkBonusPopup okBonusPopup = (OkBonusPopup)NavigationUtils.closeAllPopups(Page.okBonus);
        okBonusPopup.clickOffPopup();
        assertEquals(BONUS_AMOUNT, new AbstractPage().getBalanceAmount(), "Balance");
    }

    /*Online Declinable accept*/
    @Test(groups = {"regression", "push"})
    public void onlineAccept(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.acceptDeclineBonus);
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup)NavigationUtils.closeAllPopups(Page.acceptDeclineBonus);
        HomePage homePage = acceptDeclineBonusPopup.accept();
        assertEquals(BONUS_AMOUNT, homePage.getBalanceAmount(), "Balance");
    }

    /*Online Declinable decline*/
    @Test(groups = {"regression", "push"})
    public void onlineDecline(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.acceptDeclineBonus);
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup)NavigationUtils.closeAllPopups(Page.acceptDeclineBonus);
        HomePage homePage = acceptDeclineBonusPopup.decline();
        assertEquals("0.00", homePage.getBalanceAmount(), "Balance");
    }

    /*Online Declinable clickOffPopup*/
    @Test(groups = {"regression", "push"})
    public void onlineOffPopup(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.acceptDeclineBonus);
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup)NavigationUtils.closeAllPopups(Page.acceptDeclineBonus);
        acceptDeclineBonusPopup.clickOffPopup();
        new AcceptDeclineBonusPopup();
        assertEquals("0.00", new AbstractPage().getBalanceAmount(), "Balance");
        HomePage homePage = acceptDeclineBonusPopup.decline();
    }

    /*Online Declinable ringfencing accept*/
    /*Online Declinable ringfencing decline*/
    /*Online Declinable ringfencing clickOffPopup*/

    /*Offline Non-declinable Ok*/
    @Test(groups = {"regression", "push"})
    public void offlineNonDeclinableOk(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        PortalUtils.logout();
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.okBonus);
        PortalUtils.loginUser(userData);
        OkBonusPopup okBonusPopup = (OkBonusPopup)NavigationUtils.closeAllPopups(Page.okBonus);
        okBonusPopup.close();
        assertEquals(BONUS_AMOUNT, new AbstractPage().getBalanceAmount(), "Balance");
    }

    /*Offline Non-declinable offPopup*/
    @Test(groups = {"regression", "push"})
    public void offlineNonDeclinableOffPOpup(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.okBonus);
        OkBonusPopup okBonusPopup = (OkBonusPopup)NavigationUtils.closeAllPopups(Page.okBonus);
        okBonusPopup.clickOffPopup();
        assertEquals(BONUS_AMOUNT, new AbstractPage().getBalanceAmount(), "Balance");
    }

    /*Offline Declinable accept*/
    @Test(groups = {"regression", "push"})
    public void offlineAccept(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        PortalUtils.logout();
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.acceptDeclineBonus);
        PortalUtils.loginUser(userData);
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup)NavigationUtils.closeAllPopups(Page.acceptDeclineBonus);
        HomePage homePage = acceptDeclineBonusPopup.accept();
        assertEquals(BONUS_AMOUNT, homePage.getBalanceAmount(), "Balance");
    }

    /*Offline Declinable decline*/
    @Test(groups = {"regression", "push"})
    public void offlineDecline(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        PortalUtils.logout();
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.acceptDeclineBonus);
        PortalUtils.loginUser(userData);
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup)NavigationUtils.closeAllPopups(Page.acceptDeclineBonus);
        HomePage homePage = acceptDeclineBonusPopup.decline();
        assertEquals("0.00", homePage.getBalanceAmount(), "Balance");
    }

    /*Offline Declinable clickOffPopup*/
    @Test(groups = {"regression", "push"})
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
        HomePage homePage = acceptDeclineBonusPopup.decline();
    }

    /*Offline Declinable ringfencing clickOffPopup*/
    /*Offline Declinable ringfencing accept*/
    /*Offline Declinable ringfencing decline*/

    /*Game Non-declinable*/
    @Test(groups = {"regression", "push"})
    public void gameNonDeclinableOk(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        GameLaunchPage gameLaunchPage = NavigationUtils.launchGameByUrl(userData);
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.okBonus);
        OkBonusPopup okBonusPopup = (OkBonusPopup)NavigationUtils.closeAllPopups(Page.okBonus);
        okBonusPopup.close();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.home, userData);
        assertEquals(BONUS_AMOUNT, homePage.getBalanceAmount(), "Balance");
    }

    /*Game Declinable accept*/
    @Test(groups = {"regression", "push"})
    public void gameAccept(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        GameLaunchPage gameLaunchPage = NavigationUtils.launchGameByUrl(userData);
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.acceptDeclineBonus);
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup)NavigationUtils.closeAllPopups(Page.acceptDeclineBonus);
        acceptDeclineBonusPopup.accept();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.home, userData);
        assertEquals(BONUS_AMOUNT, homePage.getBalanceAmount(), "Balance");
    }

    /*Game Declinable decline*/
    @Test(groups = {"regression", "push"})
    public void gameDecline(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        GameLaunchPage gameLaunchPage = NavigationUtils.launchGameByUrl(userData);
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.acceptDeclineBonus);
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup)NavigationUtils.closeAllPopups(Page.acceptDeclineBonus);
        acceptDeclineBonusPopup.decline();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.home, userData);
        assertEquals("0.00", homePage.getBalanceAmount(), "Balance");
    }

    /*Online Non-declinable refresh*/
    @Test(groups = {"regression", "push"})
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
    @Test(groups = {"regression", "push"})
    public void onlineDeclinableRefresh(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.acceptDeclineBonus);
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup)NavigationUtils.closeAllPopups(Page.acceptDeclineBonus);
        WebDriverUtils.refreshPage();
        acceptDeclineBonusPopup = new AcceptDeclineBonusPopup();
        HomePage homePage = acceptDeclineBonusPopup.accept();
        assertEquals(BONUS_AMOUNT, homePage.getBalanceAmount(), "Balance");
    }

    /*Online Non-declinable and Declinable refresh*/
    @Test(groups = {"regression", "push"})
    public void onlineMultipleRefresh() {
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.okBonus, Page.acceptDeclineBonus);
        OkBonusPopup okBonusPopup = (OkBonusPopup)NavigationUtils.closeAllPopups(Page.okBonus);
        WebDriverUtils.refreshPage();
        assertFalse(WebDriverUtils.isVisible(AbstractPopup.ROOT_XP, 5), "Bonus visible");
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = new AcceptDeclineBonusPopup();
        HomePage homePage = acceptDeclineBonusPopup.accept();
        assertEquals(TypeUtils.calculateSum(BONUS_AMOUNT, BONUS_AMOUNT), homePage.getBalanceAmount(), "Balance");
    }

    /*Online Non-declinable and Declinable x2 navigation*/
    @Test(groups = {"regression", "push"})
    public void onlineMultipleNavigation() {
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.okBonus, Page.acceptDeclineBonus, Page.acceptDeclineBonus);
        OkBonusPopup okBonusPopup = (OkBonusPopup)NavigationUtils.closeAllPopups(Page.okBonus);
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup) okBonusPopup.clickNext();
        acceptDeclineBonusPopup = (AcceptDeclineBonusPopup) acceptDeclineBonusPopup.clickNext();
        acceptDeclineBonusPopup = (AcceptDeclineBonusPopup) acceptDeclineBonusPopup.clickPrevious();
        okBonusPopup = (OkBonusPopup) acceptDeclineBonusPopup.clickPrevious();
        NavigationUtils.closeAllPopups(Page.homePage);
    }

    /*Online Non-declinable and Declinable x2 close start*/
    @Test(groups = {"regression", "push"})
    public void onlineMultipleNavigationCloseStart() {
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.okBonus, Page.acceptDeclineBonus, Page.acceptDeclineBonus);
        OkBonusPopup okBonusPopup = (OkBonusPopup)NavigationUtils.closeAllPopups(Page.okBonus);
        okBonusPopup.closePopup();
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = new AcceptDeclineBonusPopup();
        acceptDeclineBonusPopup.decline();
        acceptDeclineBonusPopup = new AcceptDeclineBonusPopup();
        NavigationUtils.closeAllPopups(Page.homePage);
    }

    /*Online Non-declinable and Declinable x2 close end*/
    @Test(groups = {"regression", "push"})
    public void onlineMultipleNavigationCloseEnd() {
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        iMS.sendPushMessage(userData, BONUS_AMOUNT, Page.okBonus, Page.acceptDeclineBonus, Page.acceptDeclineBonus);
        OkBonusPopup okBonusPopup = (OkBonusPopup)NavigationUtils.closeAllPopups(Page.okBonus);
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup) okBonusPopup.clickNext();
        acceptDeclineBonusPopup = (AcceptDeclineBonusPopup) acceptDeclineBonusPopup.clickNext();
        acceptDeclineBonusPopup.decline();
        acceptDeclineBonusPopup = new AcceptDeclineBonusPopup();
        acceptDeclineBonusPopup.decline();
        okBonusPopup = new OkBonusPopup();
        NavigationUtils.closeAllPopups(Page.homePage);
    }


//	/*7. Push messages for bonus opt in/out */
//	@Test(groups = {"regression", "push"})
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
//	@Test(groups = {"regression", "push"})
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
