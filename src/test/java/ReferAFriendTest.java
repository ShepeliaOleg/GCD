import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.external.mail.MailServicePage;
import pageObjects.referAFriend.ReferAFriendPage;
import pageObjects.referAFriend.ReferAFriendPopup;
import springConstructors.ValidationRule;
import springConstructors.mail.MailService;
import utils.NavigationUtils;
import utils.core.AbstractTest;

public class ReferAFriendTest extends AbstractTest{

	@Autowired
	@Qualifier("emailValidationRuleReferAFriend")
	private ValidationRule emailValidationRuleReferAFriend;

    @Autowired
    @Qualifier("mailinator")
    private MailService mailService;

	/** Positive */
    /** 1. Portlet is displayed */
    /*popup*/
    @Test
    public void portletIsDisplayedOnMyAccountReferAFriendPopup() {
        try{
            HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.home);
            homePage.navigateToReferAFriend();
        }catch (Exception e){
            skipTest();
        }
    }

    /*page*/
    @Test
    public void portletIsDisplayedOnMyAccountReferAFriendPage() {
        try{
            NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.referAFriendPage);
        }catch (Exception e){
            skipTest();
        }
    }

    /** 2 Send an invitation*/
    /*popup*/
    @Test
    public void successfullMessageAppearedOnCorrectRequestPopup() {
        String email = emailValidationRuleReferAFriend.generateValidString();
        navigateToReferAFriendPopupAndSendInvitation(email);
    }

    /*page*/
    @Test
    public void successfullMessageAppearedOnCorrectRequestPage() {
        String email = emailValidationRuleReferAFriend.generateValidString();
        navigateToReferAFriendPageAndSendInvitation(email);
    }

    /** 3. Invitation comes to email box */
    /*popup*/
    @Test
    public void recommendationEmailReceivedOnCorrectRequestPopup(){
        String email = mailService.generateEmail();
        navigateToReferAFriendPageAndSendInvitation(email);
        MailServicePage mailServicePage = mailService.navigateToInbox(email);
        mailServicePage.waitForInvitationEmail(1000);
    }

    /*page*/
    @Test
    public void recommendationEmailReceivedOnCorrectRequestPage(){
        String email = mailService.generateEmail();
        navigateToReferAFriendPageAndSendInvitation(email);
        MailServicePage mailServicePage = mailService.navigateToInbox(email);
        mailServicePage.waitForInvitationEmail(1000);
    }

    /** Negative*/
    /** 1. Send an invitation to already used email */
    /*popup*/
    @Test
    public void failMessageAppearedOnSendingAlreadySentEmailPopup(){
        String email = emailValidationRuleReferAFriend.generateValidString();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.home);
        ReferAFriendPopup referAFriendPopup = homePage.navigateToReferAFriend();
        referAFriendPopup.fillRecipientInfo(email);
        referAFriendPopup.clickSend();
        assertTrue(referAFriendPopup.isEmailSent(), "Invitation was sent to email: " + email);
        ReferAFriendPopup referAFriendPopup1 = homePage.navigateToReferAFriend();
        referAFriendPopup1.fillRecipientInfo(email);
        referAFriendPopup1.clickSend();
        assertTrue(referAFriendPopup1.isErrorMessageVisible(), "Can't send invitation to same email twice");
    }

    /*page*/
    @Test
    public void failMessageAppearedOnSendingAlreadySentEmailPage(){
        String email = emailValidationRuleReferAFriend.generateValidString();
        ReferAFriendPage referAFriendPage = (ReferAFriendPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.referAFriendPage);
        referAFriendPage.fillRecipientInfo(email);
        referAFriendPage.clickSend();
        assertTrue(referAFriendPage.isEmailSent(), "Invitation was sent to email: " + email);
        ReferAFriendPage referAFriendPage1 = (ReferAFriendPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.referAFriendPage);
        referAFriendPage1.fillRecipientInfo(email);
        referAFriendPage1.clickSend();
        assertTrue(!referAFriendPage1.isErrorMessageVisible(), "Can't send invitation to same email twice");
    }

    /** validation */
    /** 2. Email field validation*/
    /*popup*/
    @Test
    public void emailFieldValidationPopup(){
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.home);
        ReferAFriendPopup referAFriendPopup = homePage.navigateToReferAFriend();
        referAFriendPopup.validateEmail(emailValidationRuleReferAFriend);
    }

    /*page*/
    @Test
    public void emailFieldValidationPage(){
        ReferAFriendPage referAFriendPage = (ReferAFriendPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.referAFriendPage);
        referAFriendPage.validateEmail(emailValidationRuleReferAFriend);
    }


    public void navigateToReferAFriendPopupAndSendInvitation(String email){
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.home);
        ReferAFriendPopup referAFriendPopup = homePage.navigateToReferAFriend();
        referAFriendPopup.fillRecipientInfo(email);
        referAFriendPopup.clickSend();
        assertTrue(referAFriendPopup.isEmailSent(), "Invitation was sent to email: " + email);
    }

    public void navigateToReferAFriendPageAndSendInvitation(String email){
        ReferAFriendPage referAFriendPage = (ReferAFriendPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.referAFriendPage);
        referAFriendPage.fillRecipientInfo(email);
        referAFriendPage.clickSend();
        assertTrue(referAFriendPage.isEmailSent(), "Invitation was sent to email: " + email);
    }

//	/* 1. Portlet is displayed */
//	@Test(groups = {"smoke"})
//	public void portletIsDisplayedOnMyAccountReferAFriendPage() {
//        UserData userData = defaultUserData.getRegisteredUserData();
//		ReferAFriendPage referAFriendPage = (ReferAFriendPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.referAFriend, userData);
//	}

//    /* 2. Send one invitation from RAF */
//	@Test(groups = {"regression"})
//	public void successfulMessageAppearedOnCorrectRequest() {
//        String username1 = nameValidationRule.generateValidString();
//        String email1 = emailValidationRule.generateValidString();
//        ReferAFriendPage referAFriendPage = (ReferAFriendPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.referAFriend, defaultUserData.getRegisteredUserData());
//		referAFriendPage.fillFirstRecipientInfo(username1, email1);
//		referAFriendPage.send();
//        TypeUtils.assertTrueWithLogs(referAFriendPage.notificationMessageIsSuccessful(),"messageSuccessful");
//	}
//
//    /* 3. Invitation comes to email box */
//	@Test(groups = {"regression"})
//	public void recommendationEmailReceivedOnCorrectRequest(){
//        String username = nameValidationRule.generateValidString();
//        String email = mailService.generateEmail();
//        ReferAFriendPage referAFriendPage = (ReferAFriendPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.referAFriend, defaultUserData.getRegisteredUserData());
//		referAFriendPage.fillFirstRecipientInfo(username, email);
//		referAFriendPage.send();
//        TypeUtils.assertTrueWithLogs(referAFriendPage.notificationMessageIsSuccessful(),"messageSuccessful");
//		MailServicePage mailServicePage = mailService.navigateToInbox(email);
//		mailServicePage.waitForEmail();
//        TypeUtils.assertFalseWithLogs(mailServicePage.inboxIsEmpty(),"inbox empty");
//	}
//
//    /* 4. Send invitations to several emails */
//	@Test(groups = {"regression"})
//	public void successfulMessageAppearedOnSendingMultipleUsersCorrectRequest(){
//        String username1 = nameValidationRule.generateValidString();
//        String email1 = emailValidationRule.generateValidString();
//        String username2 = nameValidationRule.generateValidString();
//        String email2 = emailValidationRule.generateValidString();
//        ReferAFriendPage referAFriendPage = (ReferAFriendPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.referAFriend, defaultUserData.getRegisteredUserData());
//        referAFriendPage.fillFirstRecipientInfo(username1, email1);
//		referAFriendPage.fillSecondRecipientInfo(username2, email2);
//		referAFriendPage.send();
//        TypeUtils.assertTrueWithLogs(referAFriendPage.notificationMessageIsSuccessful(),"messageSuccessful");
//	}
//
//    /* Negative*/
//
//    /* 1. Name field is filled out while email field is empty */
//	@Test(groups = {"regression"})
//	public void failMessageAppearedOnSendingEmptyEmail(){
//        ReferAFriendPage referAFriendPage = (ReferAFriendPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.referAFriend, defaultUserData.getRegisteredUserData());
//		String username1 = nameValidationRule.generateValidString();
//		referAFriendPage.fillFirstRecipientInfo(username1, "");
//		referAFriendPage.send();
//        TypeUtils.assertFalseWithLogs(referAFriendPage.notificationMessageIsSuccessful(),"messageSuccessful");
//	}
//
//    /* 2. Email field is filled out while name field is empty */
//	@Test(groups = {"regression"})
//	public void failMessageAppearedOnSendingEmptyName(){
//        ReferAFriendPage referAFriendPage = (ReferAFriendPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.referAFriend, defaultUserData.getRegisteredUserData());
//		String username1 = nameValidationRule.generateValidString();
//		String email1 = emailValidationRule.generateValidString();
//		referAFriendPage.fillFirstRecipientInfo("", email1);
//		referAFriendPage.send();
//        TypeUtils.assertFalseWithLogs(referAFriendPage.notificationMessageIsSuccessful(),"messageSuccessful");
//	}
//
//    /* 3. Specify one email for several referrals */
//	@Test(groups = {"regression"})
//	public void successfulMessageAppearedOnSendingMultipleUsersIncorrectRequest(){
//        ReferAFriendPage referAFriendPage = (ReferAFriendPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.referAFriend, defaultUserData.getRegisteredUserData());
//		String username1 = nameValidationRule.generateValidString();
//		String email1 = emailValidationRule.generateValidString();
//		String username2 = nameValidationRule.generateValidString();
//		referAFriendPage.fillFirstRecipientInfo(username1, email1);
//		referAFriendPage.fillSecondRecipientInfo(username2, email1);
//		referAFriendPage.send();
//        TypeUtils.assertFalseWithLogs(referAFriendPage.notificationMessageIsSuccessful(),"messageSuccessful");
//	}
//
//    /* 4. Send an invitation to already used email */
//	@Test(groups = {"regression"})
//	public void failMessageAppearedOnSendingAlreadySentEmail(){
//        String username1 = nameValidationRule.generateValidString();
//        String email1 = emailValidationRule.generateValidString();
//        ReferAFriendPage referAFriendPage = (ReferAFriendPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.referAFriend, defaultUserData.getRegisteredUserData());
//		referAFriendPage.fillFirstRecipientInfo(username1, email1);
//		referAFriendPage.send();
//        TypeUtils.assertTrueWithLogs(referAFriendPage.notificationMessageIsSuccessful(),"messageSuccessful");
//		referAFriendPage.fillFirstRecipientInfo(username1, email1);
//		referAFriendPage.send();
//        TypeUtils.assertFalseWithLogs(referAFriendPage.notificationMessageIsSuccessful(),"messageSuccessful");
//	}
//
//    /* validation */

//	/*1. Name field validation*/
//	@Test(groups = {"validation"})
//	public void nameFieldValidation(){
//        ReferAFriendPage referAFriendPage = (ReferAFriendPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.referAFriend, defaultUserData.getRegisteredUserData());
//		referAFriendPage.validateName(nameValidationRule);
//	}
//
//	/*2. Email field validation*/
//	@Test(groups = {"validation"})
//	public void emailFieldValidation(){
//        ReferAFriendPage referAFriendPage = (ReferAFriendPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.referAFriend, defaultUserData.getRegisteredUserData());
//		referAFriendPage.validateEmail(emailValidationRule);
//	}
}
