import enums.ConfiguredPages;
import enums.Page;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.account.ChangePasswordPopup;
import pageObjects.account.ChangedPasswordPopup;
import pageObjects.account.ForceLogoutPopup;
import pageObjects.bonus.*;
import pageObjects.popups.WelcomePopup;
import springConstructors.IMS;
import springConstructors.UserData;
import springConstructors.validation.ValidationRule;
import testUtils.AbstractTest;
import utils.NavigationUtils;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 4/10/14
 */
public class PushMessagesTest extends AbstractTest{

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

	private static final String BONUS_AMOUNT = "50";

    /* POSITIVE */

	/*1. Login message */
	@Test(groups = {"regression"})
	public void loginMessage(){
		UserData userData = defaultUserData.getRegisteredUserData();
		WelcomePopup welcomePopup = (WelcomePopup) NavigationUtils.navigateToPortal(true).login(userData, Page.welcomePopup);
		welcomePopup.clickClose();
	}

	/*2. Push logout */
	@Test(groups = {"regression"})
	public void pushLogout(){
        HomePage homePage;
		UserData userData = defaultUserData.getRegisteredUserData();
		NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.home, userData);
		iMS.sendPushMessage(userData, Page.logout);
		try{
			WebDriverUtils.waitForElement(ForceLogoutPopup.LOGOUT_POPUP, 300);
		}catch(RuntimeException e){
			WebDriverUtils.runtimeExceptionWithLogs("User was not logged out after 180 seconds");
		}
		ForceLogoutPopup forceLogoutPopup = new ForceLogoutPopup();
		homePage = forceLogoutPopup.close();
		homePage.waitForLogout();
	}

	/*3. Forced password change */
	@Test(groups = {"regression"})
	public void forcedPassChange(){
		String imsPass = passwordValidationRule.generateValidString();
		String newPass = passwordValidationRule.generateValidString();
		UserData userData = defaultUserData.getRandomUserData();
		NavigationUtils.navigateToPortal(true).navigateToRegistration().registerUser(userData).logout();
		userData.setPassword(imsPass);
		iMS.sendPushMessage(userData, Page.changePasswordPopup);
		ChangePasswordPopup changePasswordPopup = (ChangePasswordPopup)NavigationUtils.navigateToPortal(true).login(userData, Page.changePasswordPopup);
		userData.setPassword(newPass);
		ChangedPasswordPopup changedPasswordPopup = changePasswordPopup.fillForm(imsPass, userData.getPassword());
		Assert.assertTrue(changedPasswordPopup.successfulMessageAppeared());
	}

	/*6. Update balance after receiving a bonus */
	@Test(groups = {"regression"})
	public void updateBalanceBonus(){
		UserData userData = defaultUserData.getRegisteredUserData();
		HomePage homePage=(HomePage)NavigationUtils.navigateToPortal(true).login(userData);
		String balance = homePage.getBalance();
		iMS.sendPushMessage(userData, Page.okBonus, BONUS_AMOUNT);
		NavigationUtils.closeAllPopups(Page.homePage);
		Assert.assertTrue(homePage.compareBalances(balance)==Integer.parseInt(BONUS_AMOUNT));
	}

	/*7. Push messages for bonus opt in/out */
	@Test(groups = {"regression"})
	public void pushMessageOptIn(){
		UserData userData = defaultUserData.getRandomUserData();
		HomePage homePage = NavigationUtils.navigateToPortal(true).navigateToRegistration().registerUser(userData);
		OptInPopup optInPopup = homePage.navigateToBonusPage().clickOptIn();
		optInPopup.clickOptIn().closePopup();
		BonusPage bonusPage = new BonusPage();
		OptOutPopup optOutPopup = bonusPage.clickOptOut();
		optOutPopup.clickOptOut().closePopup();
	}

	/*8. Push message for bonus buy in */
//	@Test(groups = {"regression"})
//	public void pushMessageBuyIn(){
//		UserData userData = defaultUserData.getRegisteredUserData();
//		HomePage homePage=NavigationUtils.navigateToPortal(true).login(userData);
//		String balance = homePage.getBalance();
//		BuyInBonusPopup buyInBonusPopup = homePage.navigateToBonusPage().clickBuyIn();
//		OkBonusPopup okBonusPopup = buyInBonusPopup.clickFreeBonus();
//		homePage = okBonusPopup.close();
//		Assert.assertTrue(homePage.compareBalances(balance)==10);
//	}

	/*9. Push message for free bonus*/
	@Test(groups = {"regression"})
	public void pushMessageFreeBonus(){
		UserData userData = defaultUserData.getRegisteredUserData();
		HomePage homePage=(HomePage)NavigationUtils.navigateToPortal(true).login(userData);
		String balance = homePage.getBalance();
		FreeBonusPopup freeBonusPopup = homePage.navigateToBonusPage().clickFreeBonus();
		OkBonusPopup okBonusPopup = freeBonusPopup.clickFreeBonus();
		okBonusPopup.close();
		Assert.assertTrue(homePage.compareBalances(balance)==10);
	}

	/*10. Push message for promo code redemption*/
	@Test(groups = {"regression"})
	public void pushMessagePromoCode(){
		UserData userData = defaultUserData.getRegisteredUserData();
		HomePage homePage=(HomePage)NavigationUtils.navigateToPortal(true).login(userData);
		String balance = homePage.getBalance();
		OkBonusPopup okBonusPopup = homePage.navigateToBonusPage().submitCode();
		okBonusPopup.close();
		Assert.assertTrue(homePage.compareBalances(balance)==10);
	}

	/*11.  Accept/Decline push message - Accept*/
	@Test(groups = {"regression"})
	public void accept(){
		UserData userData = defaultUserData.getRandomUserData();
		HomePage homePage=NavigationUtils.navigateToPortal(true).navigateToRegistration().registerUser(userData);
		iMS.sendPushMessage(userData, Page.acceptDeclineBonus, BONUS_AMOUNT);
		AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup)NavigationUtils.closeAllPopups(Page.acceptDeclineBonus);
		homePage = acceptDeclineBonusPopup.accept();
		Assert.assertTrue(homePage.getBalance().equals("£" + BONUS_AMOUNT+".00"));
	}

	/*12. Accept/Decline push message - Decline*/
	@Test(groups = {"regression"})
	public void decline(){
		UserData userData = defaultUserData.getRandomUserData();
		HomePage homePage=NavigationUtils.navigateToPortal(true).navigateToRegistration().registerUser(userData);
		iMS.sendPushMessage(userData, Page.acceptDeclineBonus, BONUS_AMOUNT);
		AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup)NavigationUtils.closeAllPopups(Page.acceptDeclineBonus);
		homePage = acceptDeclineBonusPopup.decline();
		Assert.assertTrue(homePage.getBalance().equals("£0.00"));
	}

	/*13.  No Accept/Decline push message*/
	@Test(groups = {"regression"})
	public void okMessage(){
		UserData userData = defaultUserData.getRandomUserData();
		HomePage homePage=NavigationUtils.navigateToPortal(true).navigateToRegistration().registerUser(userData);
		iMS.sendPushMessage(userData, Page.okBonus, BONUS_AMOUNT);
		OkBonusPopup okBonusPopup = (OkBonusPopup)NavigationUtils.closeAllPopups(Page.okBonus);
		okBonusPopup.close();
		Assert.assertTrue(homePage.getBalance().equals("£" + BONUS_AMOUNT+".00"));
	}

    /*14. Login message disabled */
	@Test(groups = {"regression"})
	public void loginMessageDisabled(){
		UserData userData = defaultUserData.getRandomUserData();
		userData.setEmail(emailValidationRule.generateValidString());
		HomePage homePage=NavigationUtils.navigateToPortal(true).navigateToRegistration().registerUser(userData);
		homePage.logout();
		try{
			iMS.freezeWelcomeMessages();
			homePage=(HomePage)NavigationUtils.navigateToPortal(true).login(userData);
			homePage.isLoggedIn();
		}
		finally{
			iMS.unFreezeWelcomeMessages();
		}
	}
}
