package springConstructors;

import pageObjects.external.mailq.MailQHomePage;
import pageObjects.external.mailq.MailQLoginPage;
import pageObjects.external.mailq.MailQNewTicketPage;
import pageObjects.external.mailq.MailQReplyPage;
import utils.WebDriverUtils;

public class MailQ {
	protected String mailServiceUrl;

	public MailQ(String mailServiceUrl){
		this.mailServiceUrl=mailServiceUrl;
	}

	public MailQHomePage navigateToMailQ(){
		MailQHomePage mailQHomePage;
		WebDriverUtils.navigateToURL(mailServiceUrl);
		if(WebDriverUtils.isVisible(MailQLoginPage.BUTTON_SUBMIT_XP)){
			MailQLoginPage mailQLoginPage=new MailQLoginPage();
			mailQHomePage=mailQLoginPage.logInToMailQ();
		}else{
			mailQHomePage=new MailQHomePage();
		}
		return mailQHomePage;
	}

	public MailQHomePage sendMessage(String email, String subject, String text, boolean sendEmail, boolean sendMessage){
		MailQNewTicketPage mailQNewTicketPage=navigateToMailQ().clickCreateTicket();
		return mailQNewTicketPage.sendMessage(email, subject, text, sendEmail, sendMessage);
	}

	public void sendMessageAndLogout(String email, String subject, String text, boolean sendEmail, boolean sendMessage){
		sendMessage(email, subject, text, sendEmail, sendMessage).logoutFromMailQ();
	}

	public MailQHomePage sendReply(String subject, String text, boolean sendEmail, boolean sendMessage){
		MailQReplyPage mailQReplyPage=navigateToMailQ().clickLatestSubjectIfMessageIsReceived(subject);
		return mailQReplyPage.sendMessage(text, true, true);
	}

	public void sendReplyAndLogout(String subject, String text, boolean sendEmail, boolean sendMessage){
		sendReply(subject, text, sendEmail, sendMessage).logoutFromMailQ();
	}

	public boolean checkIsMessageAppearedAndLogout(String emailText){
		boolean result=navigateToMailQ().isMessageAppeared(emailText);
		return result;
	}
}
