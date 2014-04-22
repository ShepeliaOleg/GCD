import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.account.MyAccountPage;
import pageObjects.account.ReferAFriendPage;
import pageObjects.external.mail.MailServicePage;
import springConstructors.UserData;
import springConstructors.mail.MailService;
import springConstructors.validation.ValidationRule;
import testUtils.AbstractTest;
import utils.NavigationUtils;

/**
 * User: sergiich
 * Date: 4/10/14
 */
public class ReferAFriendTest extends AbstractTest{

	@Autowired
	@Qualifier("mailinator")
	private MailService mailService;

	@Autowired
	@Qualifier("nameOnRAFValidationRule")
	private ValidationRule nameValidationRule;

	@Autowired
	@Qualifier("emailValidationRule")
	private ValidationRule emailValidationRule;

	@Autowired
	@Qualifier("userData")
	private UserData defaultUserData;

	/* Positive */

	/* 1. Portlet is displayed */
	@Test(groups = {"smoke"})
	public void portletIsDisplayedOnMyAccountReferAFriendPage() {
		UserData userData = defaultUserData.getRegisteredUserData();
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		homePage = (HomePage)homePage.login(userData);
		MyAccountPage myAccountPage = homePage.navigateToMyAccount();
	}

    /* 2. Send one invitation from RAF */
	@Test(groups = {"regression"})
	public void successfulMessageAppearedOnCorrectRequest() {
		UserData userData = defaultUserData.getRegisteredUserData();
		HomePage homePage = (HomePage)NavigationUtils.navigateToPortal(true).login(userData);
		MyAccountPage myAccountPage = homePage.navigateToMyAccount();
		ReferAFriendPage referAFriendPage = myAccountPage.navigateToReferAFriend();
		String username1 = nameValidationRule.generateValidString();
		String email1 = mailService.generateEmail(username1);
		referAFriendPage.fillFirstRecipientInfo(username1, email1);
		referAFriendPage.send();
		boolean messageSuccessful=referAFriendPage.notificationMessageIsSuccessful();
		Assert.assertTrue(messageSuccessful == true);
	}

    /* 3. Invitation comes to email box */
	@Test(groups = {"regression"})
	public void recommendationEmailReceivedOnCorrectRequest(){
		UserData userData = defaultUserData.getRegisteredUserData();
		HomePage homePage = (HomePage)NavigationUtils.navigateToPortal(true).login(userData);
		MyAccountPage myAccountPage=homePage.navigateToMyAccount();
		ReferAFriendPage referAFriendPage=myAccountPage.navigateToReferAFriend();
		String username = nameValidationRule.generateValidString();
		String email = mailService.generateEmail(username);
		referAFriendPage.fillFirstRecipientInfo(username, email);
		referAFriendPage.send();
		boolean messageSuccessful=referAFriendPage.notificationMessageIsSuccessful();
		MailServicePage mailServicePage = mailService.navigateToInbox(email);
		mailServicePage.waitForEmail();
		boolean inboxEmpty= mailServicePage.inboxIsEmpty();
		Assert.assertTrue(messageSuccessful == true && inboxEmpty == false);
	}

    /* 4. Send invitations to several emails */
	@Test(groups = {"regression"})
	public void successfulMessageAppearedOnSendingMultipleUsersCorrectRequest(){
		UserData userData = defaultUserData.getRegisteredUserData();
		HomePage homePage = (HomePage)NavigationUtils.navigateToPortal(true).login(userData);
		MyAccountPage myAccountPage=homePage.navigateToMyAccount();
		ReferAFriendPage referAFriendPage=myAccountPage.navigateToReferAFriend();
		String username1 = nameValidationRule.generateValidString();
		String email1 = mailService.generateEmail(username1);
		String username2 = nameValidationRule.generateValidString();
		String email2 = mailService.generateEmail(username2);
		referAFriendPage.fillFirstRecipientInfo(username1, email1);
		referAFriendPage.fillSecondRecipientInfo(username2, email2);
		referAFriendPage.send();
		boolean messageSuccessful=referAFriendPage.notificationMessageIsSuccessful();
		Assert.assertTrue(messageSuccessful == true);
	}

    /* Negative*/

    /* 1. Name field is filled out while email field is empty */
	@Test(groups = {"regression"})
	public void failMessageAppearedOnSendingEmptyEmail(){
		UserData userData = defaultUserData.getRegisteredUserData();
		HomePage homePage = (HomePage)NavigationUtils.navigateToPortal(true).login(userData);
		MyAccountPage myAccountPage=homePage.navigateToMyAccount();
		ReferAFriendPage referAFriendPage=myAccountPage.navigateToReferAFriend();
		String username1 = nameValidationRule.generateValidString();
		referAFriendPage.fillFirstRecipientInfo(username1, "");
		referAFriendPage.send();
		boolean messageSuccessful=referAFriendPage.notificationMessageIsSuccessful();
		Assert.assertTrue(messageSuccessful == false);
	}

    /* 2. Email field is filled out while name field is empty */
	@Test(groups = {"regression"})
	public void failMessageAppearedOnSendingEmptyName(){
		UserData userData = defaultUserData.getRegisteredUserData();
		HomePage homePage = (HomePage)NavigationUtils.navigateToPortal(true).login(userData);
		MyAccountPage myAccountPage=homePage.navigateToMyAccount();
		ReferAFriendPage referAFriendPage=myAccountPage.navigateToReferAFriend();
		String username1 = nameValidationRule.generateValidString();
		String email1 = mailService.generateEmail(username1);
		referAFriendPage.fillFirstRecipientInfo("", email1);
		referAFriendPage.send();
		boolean messageSuccessful=referAFriendPage.notificationMessageIsSuccessful();
		Assert.assertTrue(messageSuccessful == false);
	}

    /* 3. Specify one email for several referrals */
	@Test(groups = {"regression"})
	public void successfulMessageAppearedOnSendingMultipleUsersIncorrectRequest(){
		UserData userData = defaultUserData.getRegisteredUserData();
		HomePage homePage = (HomePage)NavigationUtils.navigateToPortal(true).login(userData);
		MyAccountPage myAccountPage=homePage.navigateToMyAccount();
		ReferAFriendPage referAFriendPage=myAccountPage.navigateToReferAFriend();
		String username1 = nameValidationRule.generateValidString();
		String email1 = mailService.generateEmail(username1);
		String username2 = nameValidationRule.generateValidString();
		referAFriendPage.fillFirstRecipientInfo(username1, email1);
		referAFriendPage.fillSecondRecipientInfo(username2, email1);
		referAFriendPage.send();
		boolean messageSuccessful=referAFriendPage.notificationMessageIsSuccessful();
		Assert.assertTrue(messageSuccessful == false);
	}

    /* 4. Send an invitation to already used email */
	@Test(groups = {"regression"})
	public void failMessageAppearedOnSendingAlreadySentEmail(){
		UserData userData = defaultUserData.getRegisteredUserData();
		HomePage homePage = (HomePage)NavigationUtils.navigateToPortal(true).login(userData);
		MyAccountPage myAccountPage=homePage.navigateToMyAccount();
		ReferAFriendPage referAFriendPage=myAccountPage.navigateToReferAFriend();

		String username1 = nameValidationRule.generateValidString();
		String email1 = mailService.generateEmail(username1);

		referAFriendPage.fillFirstRecipientInfo(username1, email1);
		referAFriendPage.send();
		boolean messageSuccessful=referAFriendPage.notificationMessageIsSuccessful();
		referAFriendPage.fillFirstRecipientInfo(username1, email1);
		referAFriendPage.send();
		boolean messageUnsuccessful=referAFriendPage.notificationMessageIsSuccessful();
		Assert.assertTrue(messageSuccessful == true && messageUnsuccessful == false);
	}

    /* validation */

	/*1. Name field validation*/
	@Test(groups = {"validation"})
	public void nameFieldValidation(){
		UserData userData = defaultUserData.getRegisteredUserData();
		HomePage homePage = (HomePage)NavigationUtils.navigateToPortal(true).login(userData);
		MyAccountPage myAccountPage=homePage.navigateToMyAccount();
		ReferAFriendPage referAFriendPage=myAccountPage.navigateToReferAFriend();
		referAFriendPage.validateName(nameValidationRule);
	}

	/*2. Email field validation*/
	@Test(groups = {"validation"})
	public void emailFieldValidation(){
		UserData userData = defaultUserData.getRegisteredUserData();
		HomePage homePage = (HomePage)NavigationUtils.navigateToPortal(true).login(userData);
		MyAccountPage myAccountPage=homePage.navigateToMyAccount();
		ReferAFriendPage referAFriendPage=myAccountPage.navigateToReferAFriend();
		referAFriendPage.validateEmail(emailValidationRule);
	}
}
