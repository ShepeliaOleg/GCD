import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.account.ReferAFriendPage;
import pageObjects.external.mail.MailServicePage;
import springConstructors.UserData;
import springConstructors.mail.MailService;
import springConstructors.ValidationRule;
import testUtils.AbstractTest;
import utils.NavigationUtils;
import utils.TypeUtils;

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
		ReferAFriendPage referAFriendPage = (ReferAFriendPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.referAFriend, userData);
	}

    /* 2. Send one invitation from RAF */
	@Test(groups = {"regression"})
	public void successfulMessageAppearedOnCorrectRequest() {
        String username1 = nameValidationRule.generateValidString();
        String email1 = emailValidationRule.generateValidString();
        ReferAFriendPage referAFriendPage = (ReferAFriendPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.referAFriend, defaultUserData.getRegisteredUserData());
		referAFriendPage.fillFirstRecipientInfo(username1, email1);
		referAFriendPage.send();
        TypeUtils.assertTrueWithLogs(referAFriendPage.notificationMessageIsSuccessful(),"messageSuccessful");
	}

    /* 3. Invitation comes to email box */
	@Test(groups = {"regression"})
	public void recommendationEmailReceivedOnCorrectRequest(){
        String username = nameValidationRule.generateValidString();
        String email = mailService.generateEmail();
        ReferAFriendPage referAFriendPage = (ReferAFriendPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.referAFriend, defaultUserData.getRegisteredUserData());
		referAFriendPage.fillFirstRecipientInfo(username, email);
		referAFriendPage.send();
        TypeUtils.assertTrueWithLogs(referAFriendPage.notificationMessageIsSuccessful(),"messageSuccessful");
		MailServicePage mailServicePage = mailService.navigateToInbox(email);
		mailServicePage.waitForEmail();
        TypeUtils.assertFalseWithLogs(mailServicePage.inboxIsEmpty(),"inbox empty");
	}

    /* 4. Send invitations to several emails */
	@Test(groups = {"regression"})
	public void successfulMessageAppearedOnSendingMultipleUsersCorrectRequest(){
        String username1 = nameValidationRule.generateValidString();
        String email1 = emailValidationRule.generateValidString();
        String username2 = nameValidationRule.generateValidString();
        String email2 = emailValidationRule.generateValidString();
        ReferAFriendPage referAFriendPage = (ReferAFriendPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.referAFriend, defaultUserData.getRegisteredUserData());
        referAFriendPage.fillFirstRecipientInfo(username1, email1);
		referAFriendPage.fillSecondRecipientInfo(username2, email2);
		referAFriendPage.send();
        TypeUtils.assertTrueWithLogs(referAFriendPage.notificationMessageIsSuccessful(),"messageSuccessful");
	}

    /* Negative*/

    /* 1. Name field is filled out while email field is empty */
	@Test(groups = {"regression"})
	public void failMessageAppearedOnSendingEmptyEmail(){
        ReferAFriendPage referAFriendPage = (ReferAFriendPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.referAFriend, defaultUserData.getRegisteredUserData());
		String username1 = nameValidationRule.generateValidString();
		referAFriendPage.fillFirstRecipientInfo(username1, "");
		referAFriendPage.send();
        TypeUtils.assertFalseWithLogs(referAFriendPage.notificationMessageIsSuccessful(),"messageSuccessful");
	}

    /* 2. Email field is filled out while name field is empty */
	@Test(groups = {"regression"})
	public void failMessageAppearedOnSendingEmptyName(){
        ReferAFriendPage referAFriendPage = (ReferAFriendPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.referAFriend, defaultUserData.getRegisteredUserData());
		String username1 = nameValidationRule.generateValidString();
		String email1 = emailValidationRule.generateValidString();
		referAFriendPage.fillFirstRecipientInfo("", email1);
		referAFriendPage.send();
        TypeUtils.assertFalseWithLogs(referAFriendPage.notificationMessageIsSuccessful(),"messageSuccessful");
	}

    /* 3. Specify one email for several referrals */
	@Test(groups = {"regression"})
	public void successfulMessageAppearedOnSendingMultipleUsersIncorrectRequest(){
        ReferAFriendPage referAFriendPage = (ReferAFriendPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.referAFriend, defaultUserData.getRegisteredUserData());
		String username1 = nameValidationRule.generateValidString();
		String email1 = emailValidationRule.generateValidString();
		String username2 = nameValidationRule.generateValidString();
		referAFriendPage.fillFirstRecipientInfo(username1, email1);
		referAFriendPage.fillSecondRecipientInfo(username2, email1);
		referAFriendPage.send();
        TypeUtils.assertFalseWithLogs(referAFriendPage.notificationMessageIsSuccessful(),"messageSuccessful");
	}

    /* 4. Send an invitation to already used email */
	@Test(groups = {"regression"})
	public void failMessageAppearedOnSendingAlreadySentEmail(){
        String username1 = nameValidationRule.generateValidString();
        String email1 = emailValidationRule.generateValidString();
        ReferAFriendPage referAFriendPage = (ReferAFriendPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.referAFriend, defaultUserData.getRegisteredUserData());
		referAFriendPage.fillFirstRecipientInfo(username1, email1);
		referAFriendPage.send();
        TypeUtils.assertTrueWithLogs(referAFriendPage.notificationMessageIsSuccessful(),"messageSuccessful");
		referAFriendPage.fillFirstRecipientInfo(username1, email1);
		referAFriendPage.send();
        TypeUtils.assertFalseWithLogs(referAFriendPage.notificationMessageIsSuccessful(),"messageSuccessful");
	}

    /* validation */

//	/*1. Name field validation*/
//	@Test(groups = {"validation"})
//	public void nameFieldValidation(){
//        ReferAFriendPage referAFriendPage = (ReferAFriendPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.referAFriend, defaultUserData.getRegisteredUserData());
//		referAFriendPage.validateName(nameValidationRule);
//	}
//
//	/*2. Email field validation*/
//	@Test(groups = {"validation"})
//	public void emailFieldValidation(){
//        ReferAFriendPage referAFriendPage = (ReferAFriendPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.referAFriend, defaultUserData.getRegisteredUserData());
//		referAFriendPage.validateEmail(emailValidationRule);
//	}
}
