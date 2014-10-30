import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import springConstructors.MailQ;
import springConstructors.ValidationRule;
import springConstructors.mail.MailService;
import utils.core.AbstractTest;

/**
 * User: sergiich
 * Date: 4/10/14
 */

public class InboxTest extends AbstractTest{


	@Autowired
	@Qualifier("mailQ")
	private MailQ mailQ;

    @Autowired
    @Qualifier("mailinator")
    private MailService mailService;

    @Autowired
    @Qualifier("emailValidationRule")
    private ValidationRule emailValidationRule;

	@Autowired
	@Qualifier("emailSubjectValidationRule")
	private ValidationRule emailSubjectValidationRule;

	/*POSITIVE*/

//	/*1. Portlet is displayed*/
//	@Test(groups = {"smoke"})
//	public void portletIsDisplayedOnMyAccountInboxPage() {
//        UserData userData = defaultUserData.getRegisteredUserData();
//		InboxPage inboxPage = (InboxPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.inbox, userData);
//	}
//
//    /*2. Message is successfully sent and displayed in MailQ*/
//	@Test(groups = {"regression"})
//	public void checkMessageIsSentToMailQ(){
//        UserData userData = defaultUserData.getRandomUserData();
//        String emailText = emailSubjectValidationRule.generateValidString();
//        String email = emailValidationRule.generateValidString();
//        userData.setEmail(email);
//        PortalUtils.registerUser(userData);
//        InboxPage inboxPage = (InboxPage) NavigationUtils.navigateToPage(ConfiguredPages.inbox);
//		inboxPage.clickSendMessage().sendMessage(emailText);
//        TypeUtils.assertTrueWithLogs(mailQ.checkIsMessageAppearedAndLogout(emailText),"isMessageReceived");
//	}
//
//    /*3. Reply to the sent message form MailQ is successfully received*/
//	@Test(groups = {"regression"})
//	public void checkReplyIsReceivedFromMailQ(){
//        UserData userData = defaultUserData.getRandomUserData();
//        String emailText = emailSubjectValidationRule.generateValidString();
//		String email = emailValidationRule.generateValidString();
//		userData.setEmail(email);
//        PortalUtils.registerUser(userData);
//		InboxPage inboxPage = (InboxPage) NavigationUtils.navigateToPage(ConfiguredPages.inbox);
//		inboxPage.clickSendMessage().sendMessage(emailText);
//		mailQ.sendReplyAndLogout(emailText, emailText, true, true);
//        inboxPage = (InboxPage) NavigationUtils.navigateToPage(ConfiguredPages.inbox);
//        TypeUtils.assertTrueWithLogs(inboxPage.waitForMessageToAppear(),"isReplyReceived");
//	}
//
//    /*4. New message from MailQ is successfully received.*/
//	@Test(groups = {"regression"})
//	public void checkMessageReceivedFromMailQ(){
//        UserData userData = defaultUserData.getRandomUserData();
//        String emailText = emailSubjectValidationRule.generateValidString();
//		String email = emailValidationRule.generateValidString();
//		userData.setEmail(email);
//        PortalUtils.registerUser(userData);
//		mailQ.sendMessageAndLogout(email, emailText, emailText, true, true);
//        InboxPage inboxPage = (InboxPage) NavigationUtils.navigateToPage(ConfiguredPages.inbox);
//        TypeUtils.assertTrueWithLogs(inboxPage.waitForMessageToAppear(),"isMessageReceived");
//	}
//
//    /*5. Player views the list of sent messages*/
//	@Test(groups = {"regression"})
//	public void checkListOfSentMessages(){
//        InboxPage inboxPage = (InboxPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.inbox, defaultUserData.getRegisteredUserData());
//        TypeUtils.assertTrueWithLogs(inboxPage.clickSentItems().isMessageListPresent(),"isMessageListPresent");
//	}
//
//    /*6. Player views the list of received messages*/
//	@Test(groups = {"regression"})
//	public void checkListOfReceivedMessages(){
//        InboxPage inboxPage = (InboxPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.inbox, defaultUserData.getRegisteredUserData());
//        TypeUtils.assertTrueWithLogs(inboxPage.isMessageListPresent(),"isMessageListPresent");
//	}
//
//    /*7. Detailed view of a sent message can be opened*/
//	@Test(groups = {"regression"})
//	public void sendMessageAndCheckItsPresenceInSentItems(){
//        String emailText = emailSubjectValidationRule.generateValidString();
//        InboxPage inboxPage = (InboxPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.inbox, defaultUserData.getRegisteredUserData());
//		SendMessagePopup sendMessagePopup = inboxPage.clickSendMessage();
//		inboxPage = sendMessagePopup.sendMessage(emailText);
//		SentItemsPage sentItemsPage = inboxPage.clickSentItems();
//		SentMessagePopup sentMessagePopup = sentItemsPage.openFirstMessage();
//		TypeUtils.assertTrueWithLogs(emailText.equals(sentMessagePopup.getSubject()),"Correct subject received");
//        TypeUtils.assertTrueWithLogs(emailText.equals(sentMessagePopup.getMessage()),"Correct message received") ;
//	}
//
//    /*8. Detailed view of a received message can be opened*/
//	@Test(groups = {"regression"})
//	public void sendMessageFromMailQAndCheckSubjectAndText(){
//		String emailText = emailSubjectValidationRule.generateValidString();
//		UserData userData = defaultUserData.getRandomUserData();
//		String email = emailValidationRule.generateValidString();
//		userData.setEmail(email);
//		PortalUtils.registerUser(userData);
//		mailQ.sendMessageAndLogout(email, emailText, emailText, true, true);
//        InboxPage inboxPage = (InboxPage) NavigationUtils.navigateToPage(ConfiguredPages.inbox);
//		inboxPage.waitForMessageToAppear();
//		ReceivedInboxMessagePopup receivedInboxMessagePopup=inboxPage.openFirstMessage();
//		TypeUtils.assertTrueWithLogs(emailText.equals(receivedInboxMessagePopup.getSubject()), "Correct subject received");
//        TypeUtils.assertTrueWithLogs(emailText.equals(receivedInboxMessagePopup.getMessage()), "Correct message received");
//	}
//
//    /*9. Player deletes a sent message from the list*/
//	@Test(groups = {"regression"})
//	public void deleteSentMessageFromListInSentItems(){
//        String emailText = emailSubjectValidationRule.generateValidString();
//        InboxPage inboxPage = (InboxPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.inbox, defaultUserData.getRegisteredUserData());
//		SendMessagePopup sendMessagePopup = inboxPage.clickSendMessage();
//		inboxPage = sendMessagePopup.sendMessage(emailText);
//		SentItemsPage sentItemsPage = inboxPage.clickSentItems();
//		sentItemsPage = sentItemsPage.deleteFirstMessage();
//		SentMessagePopup sentMessagePopup = sentItemsPage.openFirstMessage();
//        TypeUtils.assertFalseWithLogs(emailText.equals(sentMessagePopup.getSubject()), "subject is same");
//        TypeUtils.assertFalseWithLogs(emailText.equals(sentMessagePopup.getMessage()),"message is same") ;
//	}
//
//    /*10. Player deletes a sent message from details popup*/
//	@Test(groups = {"regression"})
//	public void deleteSentMessageFromViewMessagePopupInSentItems(){
//        String emailText = emailSubjectValidationRule.generateValidString();
//        InboxPage inboxPage = (InboxPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.inbox, defaultUserData.getRegisteredUserData());
//		SendMessagePopup sendMessagePopup = inboxPage.clickSendMessage();
//		inboxPage = sendMessagePopup.sendMessage(emailText);
//		SentItemsPage sentItemsPage = inboxPage.clickSentItems();
//		SentMessagePopup sentMessagePopup = sentItemsPage.openFirstMessage();
//		sentItemsPage = sentMessagePopup.clickDeleteButton();
//		sentMessagePopup = sentItemsPage.openFirstMessage();
//		TypeUtils.assertFalseWithLogs(emailText.equals(sentMessagePopup.getSubject()), "subject is same");
//        TypeUtils.assertFalseWithLogs(emailText.equals(sentMessagePopup.getMessage()),"message is same") ;
//	}
//
//    /*11. Player deletes a received message from the list view*/
//	@Test(groups = {"regression"})
//	public void deleteReceivedMessageFromListInInbox(){
//		String emailText = emailSubjectValidationRule.generateValidString();
//		UserData userData = defaultUserData.getRandomUserData();
//		String email = emailValidationRule.generateValidString();
//		userData.setEmail(email);
//		PortalUtils.registerUser(userData);
//		mailQ.sendMessageAndLogout(email, emailText, emailText, true, true);
//        InboxPage inboxPage = (InboxPage) NavigationUtils.navigateToPage(ConfiguredPages.inbox);
//		if(inboxPage.waitForMessageToAppear()){
//			inboxPage = new InboxPage();
//			inboxPage = inboxPage.deleteFirstMessage();
//		}
//        TypeUtils.assertFalseWithLogs(inboxPage.isMessagePresent(),"isMessageStillPresent");
//	}
//
//    /*12. Player deletes a received message from the details popup*/
//	@Test(groups = {"regression"})
//	public void deleteReceivedMessageFromViewMessagePopupInInbox(){
//		String emailText = emailSubjectValidationRule.generateValidString();
//		UserData userData = defaultUserData.getRandomUserData();
//		String email = emailValidationRule.generateValidString();
//		userData.setEmail(email);
//		PortalUtils.registerUser(userData);
//		mailQ.sendMessageAndLogout(email, emailText, emailText, true, true);
//        InboxPage inboxPage = (InboxPage) NavigationUtils.navigateToPage(ConfiguredPages.inbox);
//		if(inboxPage.waitForMessageToAppear()){
//			inboxPage = new InboxPage();
//			inboxPage = inboxPage.openFirstMessage().clickDeleteButton();
//		}
//        TypeUtils.assertFalseWithLogs(inboxPage.isMessagePresent(),"isMessageStillPresent");
//	}
//
//    /*13. Pagination on Inbox portlet*/
//	@Test(groups = {"regression"})
//	public void checkPaginationWorksCorrectly(){
//        InboxPage inboxPage = (InboxPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.inbox, defaultUserData.getRegisteredUserData());
//		SentItemsPage sentItemsPage=inboxPage.clickSentItems();
//		String firstPageSubject=sentItemsPage.getFirstMessageSubject();
//		sentItemsPage=sentItemsPage.clickNextPage();
//		String secondPageSubject=sentItemsPage.getFirstMessageSubject();
//		sentItemsPage=sentItemsPage.clickPreviousPage();
//		String firstPageRefreshedSubject=sentItemsPage.getFirstMessageSubject();
//		TypeUtils.assertFalseWithLogs(firstPageSubject.equals(secondPageSubject),"page not changed");
//        TypeUtils.assertTrueWithLogs(firstPageSubject.equals(firstPageRefreshedSubject),"first page opened") ;
//	}
//
//    /*16. Received message comes to player’s email*/
//	@Test(groups = {"regression"})
//	public void checkMessageIsReceivedOnPlayerEmail(){
//		String emailText = emailSubjectValidationRule.generateValidString();
//		UserData userData = defaultUserData.getRandomUserData();
//		String email = mailService.generateEmail();
//		userData.setEmail(email);
//		PortalUtils.registerUser(userData);
//        InboxPage inboxPage = (InboxPage) NavigationUtils.navigateToPage(ConfiguredPages.inbox);
//		inboxPage.clickSendMessage().sendMessage(emailText);
//		mailQ.sendMessageAndLogout(email, emailText, emailText, true, true);
//		mailService.navigateToInbox(email).waitForEmail();
//	}
//
//    /*17. Player replies to MailQ from the details view of a received message*/
//	@Test(groups = {"regression"})
//	public void checkReplyIsSentToMailQ(){
//		String emailText = emailSubjectValidationRule.generateValidString();
//		UserData userData = defaultUserData.getRandomUserData();
//		String email = emailValidationRule.generateValidString();
//		userData.setEmail(email);
//        PortalUtils.registerUser(userData);
//		mailQ.sendMessageAndLogout(email, emailText, emailText, false, true);
//        InboxPage inboxPage = (InboxPage) NavigationUtils.navigateToPage(ConfiguredPages.inbox);
//		if(inboxPage.waitForMessageToAppear()){
//			inboxPage = new InboxPage();
//			ReceivedInboxMessagePopup receivedInboxMessagePopup = inboxPage.openFirstMessage();
//			ReplyToReceivedInboxMessagePopup replyToReceivedInboxMessagePopup = receivedInboxMessagePopup.clickReplyButton();
//			replyToReceivedInboxMessagePopup.sendReply(emailText);
//		}
//        TypeUtils.assertTrueWithLogs(mailQ.checkIsMessageAppearedAndLogout(emailText),"isReplyAppeared");
//	}
//
//    /*NEGATIVE*/
//
//    /*1. Empty subject and body*/
//	@Test(groups = {"regression"})
//	public void sendEmptyMessageAndCheckValidationAppears(){
//        InboxPage inboxPage = (InboxPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.inbox, defaultUserData.getRegisteredUserData());
//		SendMessagePopup sendMessagePopup=inboxPage.clickSendMessage();
//		TypeUtils.assertTrueWithLogs(sendMessagePopup.sendEmptyMessage().isErrorPresent(), "Error present");
//	}

}
