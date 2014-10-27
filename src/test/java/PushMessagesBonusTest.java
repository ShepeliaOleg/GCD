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
import springConstructors.IMS;
import springConstructors.UserData;
import springConstructors.ValidationRule;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.TypeUtils;
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

    /*Online Non-declinable*/
    @Test(groups = {"regression", "push"})
    public void okMessage(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        iMS.sendPushMessage(userData, Page.okBonus, BONUS_AMOUNT);
        OkBonusPopup okBonusPopup = (OkBonusPopup)NavigationUtils.closeAllPopups(Page.okBonus);
        okBonusPopup.close();
        assertEquals(BONUS_AMOUNT, new AbstractPage().getBalanceAmount(), "Balance");
    }
    /*Online Declinable accept*/
    @Test(groups = {"regression", "push"})
    public void accept(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        iMS.sendPushMessage(userData, Page.acceptDeclineBonus, BONUS_AMOUNT);
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup)NavigationUtils.closeAllPopups(Page.acceptDeclineBonus);
        HomePage homePage = acceptDeclineBonusPopup.accept();
        assertEquals(BONUS_AMOUNT, homePage.getBalanceAmount(), "Balance");
    }

    /*Online Declinable decline*/
    @Test(groups = {"regression", "push"})
    public void decline(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        iMS.sendPushMessage(userData, Page.acceptDeclineBonus, BONUS_AMOUNT);
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup)NavigationUtils.closeAllPopups(Page.acceptDeclineBonus);
        HomePage homePage = acceptDeclineBonusPopup.decline();
        assertEquals("0.00", homePage.getBalanceAmount(), "Balance");
    }
    /*Online Declinable clickOffPopup*/
    /*Online Declinable ringfencing accept*/
    /*Online Declinable ringfencing decline*/
    /*Online Declinable ringfencing clickOffPopup*/

    /*Offline Non-declinable online*/
    /*Offline Declinable accept*/
    /*Offline Declinable decline*/
    /*Offline Declinable clickOffPopup*/
    /*Offline Declinable ringfencing accept*/
    /*Offline Declinable ringfencing decline*/
    /*Offline Declinable ringfencing clickOffPopup*/

    /*Game Non-declinable*/
    /*Game Declinable accept*/
    /*Game Declinable decline*/

    /*Online Non-declinable refresh*/
    /*Online Declinable refresh*/
    /*Online Non-declinable and Declinable refresh*/

    /*Online Non-declinable and Declinable x2 navigation*/
    /*Online Non-declinable and Declinable x2 close start*/
    /*Online Non-declinable and Declinable x2 close end*/


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
