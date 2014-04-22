import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.account.MyAccountPage;
import pageObjects.inbox.*;
import pageObjects.registration.RegistrationPage;
import springConstructors.MailQ;
import springConstructors.UserData;
import springConstructors.mail.MailService;
import springConstructors.validation.ValidationRule;
import testUtils.AbstractTest;
import utils.NavigationUtils;

/**
 * User: sergiich
 * Date: 4/10/14
 */

public class InboxTest extends AbstractTest{

	@Autowired
	@Qualifier("userData")
	private UserData defaultUserData;

	@Autowired
	@Qualifier("mailQ")
	private MailQ mailQ;

	@Autowired
	@Qualifier("mailinator")
	private MailService mailService;

	@Autowired
	@Qualifier("emailSubjectValidationRule")
	private ValidationRule emailSubjectValidationRule;

	/*POSITIVE*/

	/*1. Portlet is displayed*/
	@Test(groups = {"smoke"})
	public void portletIsDisplayedOnMyAccountInboxPage() {
		UserData userData = defaultUserData.getRegisteredUserData();
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		homePage = (HomePage)homePage.login(userData);
		MyAccountPage myAccountPage = homePage.navigateToMyAccount();
		InboxPage inboxPage = myAccountPage.navigateToInbox();
	}

    /*2. Message is successfully sent and displayed in MailQ*/
	@Test(groups = {"regression"})
	public void checkMessageIsSentToMailQ(){
		String emailText = emailSubjectValidationRule.generateValidString();
		UserData userData = defaultUserData.getRegisteredUserData();
		HomePage homePage = (HomePage)NavigationUtils.navigateToPortal(true).login(userData);
		InboxPage inboxPage = homePage.navigateToInboxPage();
		inboxPage.clickSendMessage().sendMessage(emailText);
		boolean isMessageReceived = mailQ.checkIsMessageAppearedAndLogout(emailText);
		Assert.assertTrue(isMessageReceived) ;
	}

    /*3. Reply to the sent message form MailQ is successfully received*/
	@Test(groups = {"regression"})
	public void checkReplyIsReceivedFromMailQ(){
		String emailText = emailSubjectValidationRule.generateValidString();
		UserData userData = defaultUserData.getRandomUserData();
		String username = userData.getUsername();
		String email = mailService.generateEmail(username);
		userData.setEmail(email);
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage = homePage.navigateToRegistration();
		homePage = (HomePage)registrationPage.registerUser(userData);
		InboxPage inboxPage = homePage.navigateToInboxPage();
		inboxPage.clickSendMessage().sendMessage(emailText);
		mailQ.sendReplyAndLogout(emailText, emailText, true, true);
		homePage = NavigationUtils.navigateToHome();
		boolean isReplyReceived = homePage.navigateToInboxPage().waitForMessageToAppear();
		Assert.assertTrue(isReplyReceived);
	}

    /*4. New message from MailQ is successfully received.*/
	@Test(groups = {"regression"})
	public void checkMessageReceivedFromMailQ(){
		String emailText = emailSubjectValidationRule.generateValidString();
		UserData userData = defaultUserData.getRandomUserData();
		String username = userData.getUsername();
		String email = mailService.generateEmail(username);
		userData.setEmail(email);
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage = homePage.navigateToRegistration();
		registrationPage.registerUser(userData);
		mailQ.sendMessageAndLogout(email, emailText, emailText, true, true);
		homePage=NavigationUtils.navigateToHome();
		InboxPage inboxPage=homePage.navigateToInboxPage();
		boolean isMessageReceived=inboxPage.waitForMessageToAppear();
		Assert.assertTrue(isMessageReceived) ;
	}

    /*5. Player views the list of sent messages*/
	@Test(groups = {"regression"})
	public void checkListOfSentMessages(){
		UserData userData=defaultUserData.getRegisteredUserData();
		HomePage homePage=(HomePage)NavigationUtils.navigateToPortal(true).login(userData);
		InboxPage inboxPage=homePage.navigateToInboxPage();
		boolean isMessageListPresent=inboxPage.clickSentItems().isMessageListPresent();
		Assert.assertTrue(isMessageListPresent) ;
	}

    /*6. Player views the list of received messages*/
	@Test(groups = {"regression"})
	public void checkListOfReceivedMessages(){
		UserData userData=defaultUserData.getRegisteredUserData();
		HomePage homePage=(HomePage)NavigationUtils.navigateToPortal(true).login(userData);
		InboxPage inboxPage=homePage.navigateToInboxPage();
		boolean isMessageListPresent=inboxPage.isMessageListPresent();
		Assert.assertTrue(isMessageListPresent) ;
	}

    /*7. Detailed view of a sent message can be opened*/
	@Test(groups = {"regression"})
	public void sendMessageAndCheckItsPresenceInSentItems(){
		String emailText = emailSubjectValidationRule.generateValidString();
		UserData userData = defaultUserData.getRegisteredUserData();
		HomePage homePage = (HomePage)NavigationUtils.navigateToPortal(true).login(userData);
		InboxPage inboxPage = homePage.navigateToInboxPage();
		SendMessagePopup sendMessagePopup = inboxPage.clickSendMessage();
		inboxPage = sendMessagePopup.sendMessage(emailText);
		SentItemsPage sentItemsPage = inboxPage.clickSentItems();
		SentMessagePopup sentMessagePopup = sentItemsPage.openFirstMessage();
		Assert.assertTrue(emailText.equals(sentMessagePopup.getSubject()) && emailText.equals(sentMessagePopup.getMessage())) ;
	}

    /*8. Detailed view of a received message can be opened*/
	@Test(groups = {"regression"})
	public void sendMessageFromMailQAndCheckSubjectAndText(){
		String emailText = emailSubjectValidationRule.generateValidString();
		UserData userData = defaultUserData.getRandomUserData();
		String username = userData.getUsername();
		String email = mailService.generateEmail(username);
		userData.setEmail(email);
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage = homePage.navigateToRegistration();
		registrationPage.registerUser(userData);
		mailQ.sendMessageAndLogout(email, emailText, emailText, true, true);
		homePage=NavigationUtils.navigateToHome();
		InboxPage inboxPage=homePage.navigateToInboxPage();
		inboxPage.waitForMessageToAppear();
		ReceivedInboxMessagePopup receivedInboxMessagePopup=inboxPage.openFirstMessage();
		Assert.assertTrue(emailText.equals(receivedInboxMessagePopup.getSubject()) && emailText.equals(receivedInboxMessagePopup.getMessage())) ;
	}

    /*9. Player deletes a sent message from the list*/
	@Test(groups = {"regression"})
	public void deleteSentMessageFromListInSentItems(){
		UserData userData = defaultUserData.getRegisteredUserData();
		String emailText = emailSubjectValidationRule.generateValidString();
		InboxPage inboxPage = NavigationUtils.navigateToPortal(true).login(userData).navigateToInboxPage();
		SendMessagePopup sendMessagePopup = inboxPage.clickSendMessage();
		inboxPage = sendMessagePopup.sendMessage(emailText);
		SentItemsPage sentItemsPage = inboxPage.clickSentItems();
		sentItemsPage = sentItemsPage.deleteFirstMessage();
		SentMessagePopup sentMessagePopup = sentItemsPage.openFirstMessage();
		Assert.assertTrue(!emailText.equals(sentMessagePopup.getSubject()) && !emailText.equals(sentMessagePopup.getMessage())) ;
	}

    /*10. Player deletes a sent message from details popup*/
	@Test(groups = {"regression"})
	public void deleteSentMessageFromViewMessagePopupInSentItems(){
		UserData userData = defaultUserData.getRegisteredUserData();
		String emailText = emailSubjectValidationRule.generateValidString();
		InboxPage inboxPage = NavigationUtils.navigateToPortal(true).login(userData).navigateToInboxPage();
		SendMessagePopup sendMessagePopup = inboxPage.clickSendMessage();
		inboxPage = sendMessagePopup.sendMessage(emailText);
		SentItemsPage sentItemsPage = inboxPage.clickSentItems();
		SentMessagePopup sentMessagePopup = sentItemsPage.openFirstMessage();
		sentItemsPage = sentMessagePopup.clickDeleteButton();
		sentMessagePopup = sentItemsPage.openFirstMessage();
		Assert.assertTrue(!emailText.equals(sentMessagePopup.getSubject()) && !emailText.equals(sentMessagePopup.getMessage())) ;
	}

    /*11. Player deletes a received message from the list view*/
	@Test(groups = {"regression"})
	public void deleteReceivedMessageFromListInInbox(){
		String emailText = emailSubjectValidationRule.generateValidString();
		UserData userData = defaultUserData.getRandomUserData();
		String username = userData.getUsername();
		String email = mailService.generateEmail(username);
		userData.setEmail(email);
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage = homePage.navigateToRegistration();
		homePage = (HomePage)registrationPage.registerUser(userData);
		mailQ.sendMessageAndLogout(email, emailText, emailText, true, true);
		homePage = NavigationUtils.navigateToHome();
		InboxPage inboxPage = homePage.navigateToInboxPage();
		if(inboxPage.waitForMessageToAppear()){
			inboxPage = new InboxPage();
			inboxPage = inboxPage.deleteFirstMessage();
		}
		boolean isMessageStillPresent = inboxPage.isMessagePresent();
		Assert.assertTrue(!isMessageStillPresent);
	}

    /*12. Player deletes a received message from the details popup*/
	@Test(groups = {"regression"})
	public void deleteReceivedMessageFromViewMessagePopupInInbox(){
		String emailText = emailSubjectValidationRule.generateValidString();
		UserData userData = defaultUserData.getRandomUserData();
		String username = userData.getUsername();
		String email = mailService.generateEmail(username);
		userData.setEmail(email);
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage = homePage.navigateToRegistration();
		registrationPage.registerUser(userData);
		mailQ.sendMessageAndLogout(email, emailText, emailText, true, true);
		homePage = NavigationUtils.navigateToHome();
		InboxPage inboxPage = homePage.navigateToInboxPage();
		if(inboxPage.waitForMessageToAppear()){
			inboxPage = new InboxPage();
			inboxPage = inboxPage.openFirstMessage().clickDeleteButton();
		}
		boolean isMessageStillPresent = inboxPage.isMessagePresent();
		Assert.assertTrue(!isMessageStillPresent);
	}

    /*13. Pagination on Inbox portlet*/
	@Test(groups = {"regression"})
	public void checkPaginationWorksCorrectly(){
		UserData userData=defaultUserData.getRegisteredUserData();
		HomePage homePage=(HomePage)NavigationUtils.navigateToPortal(true).login(userData);
		InboxPage inboxPage=homePage.navigateToInboxPage();
		SentItemsPage sentItemsPage=inboxPage.clickSentItems();
		String firstPageSubject=sentItemsPage.getFirstMessageSubject();
		sentItemsPage=sentItemsPage.clickNextPage();
		String secondPageSubject=sentItemsPage.getFirstMessageSubject();
		sentItemsPage=sentItemsPage.clickPreviousPage();
		String firstPageRefreshedSubject=sentItemsPage.getFirstMessageSubject();
		Assert.assertTrue(!firstPageSubject.equals(secondPageSubject) && firstPageSubject.equals(firstPageRefreshedSubject)) ;
	}

    /*16. Received message comes to player’s email*/
	@Test(groups = {"regression"})
	public void checkMessageIsReceivedOnPlayerEmail(){
		String emailText = emailSubjectValidationRule.generateValidString();
		UserData userData = defaultUserData.getRandomUserData();
		String username = userData.getUsername();
		String email = mailService.generateEmail(username);
		userData.setEmail(email);
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage = homePage.navigateToRegistration();
		homePage = (HomePage) registrationPage.registerUser(userData);
		InboxPage inboxPage = homePage.navigateToInboxPage();
		inboxPage.clickSendMessage().sendMessage(emailText);
		mailQ.sendMessageAndLogout(email, emailText, emailText, true, true);
		mailService.navigateToInbox(username).waitForEmail();
		Assert.assertTrue(true) ;
	}

    /*17. Player replies to MailQ from the details view of a received message*/
	@Test(groups = {"regression"})
	public void checkReplyIsSentToMailQ(){
		String emailText = emailSubjectValidationRule.generateValidString();
		UserData userData = defaultUserData.getRandomUserData();
		String username = userData.getUsername();
		String email = mailService.generateEmail(username);
		userData.setEmail(email);
		HomePage homePage = NavigationUtils.navigateToPortal(true);
		RegistrationPage registrationPage = homePage.navigateToRegistration();
		registrationPage.registerUser(userData);
		mailQ.sendMessageAndLogout(email, emailText, emailText, false, true);
		homePage = NavigationUtils.navigateToHome();
		InboxPage inboxPage = homePage.navigateToInboxPage();
		if(inboxPage.waitForMessageToAppear()){
			inboxPage = new InboxPage();
			ReceivedInboxMessagePopup receivedInboxMessagePopup = inboxPage.openFirstMessage();
			ReplyToReceivedInboxMessagePopup replyToReceivedInboxMessagePopup = receivedInboxMessagePopup.clickReplyButton();
			replyToReceivedInboxMessagePopup.sendReply(emailText);
		}
		boolean isReplyAppeared = mailQ.checkIsMessageAppearedAndLogout(emailText);
		Assert.assertTrue(isReplyAppeared);
	}

    /*NEGATIVE*/

    /*1. Empty subject and body*/
	@Test(groups = {"regression"})
	public void sendEmptyMessageAndCheckValidationAppears(){
		UserData userData=defaultUserData.getRegisteredUserData();
		InboxPage inboxPage=NavigationUtils.navigateToPortal(true).login(userData).navigateToInboxPage();
		SendMessagePopup sendMessagePopup=inboxPage.clickSendMessage();
		Assert.assertTrue(sendMessagePopup.sendEmptyMessage().isErrorPresent());
	}

}
