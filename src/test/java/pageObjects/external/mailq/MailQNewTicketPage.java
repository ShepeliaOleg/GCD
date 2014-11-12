package pageObjects.external.mailq;

import org.openqa.selenium.Keys;
import pageObjects.core.AbstractServerPage;
import utils.WebDriverUtils;
import utils.core.WebDriverFactory;

public class MailQNewTicketPage extends AbstractServerPage{

	private static final String ROOT_XP=				"//*[@id='newTicket']";
	private static final String BUTTON_CREATE_TICKET_XP="//*[@id='create']";
	private static final String CHECKBOX_EMAIL_XP=		"//*[@id='sendAs-1";
	private static final String CHECKBOX_MESSAGE_XP=	"//*[@id='sendAs-2']";
	private static final String FIELD_EMAIL_XP=			"//*[@id='client']";
	private static final String FIELD_SUBJECT_XP=		"//*[@id='subject']";
	private static final String FIELD_MESSAGE_XP=		"//*[@id='message']";
	private static final String DROPDOWN_CATEGORY_XP=	"//*[@id='category']";
	private static final String DROPDOWN_GATEWAY_XP=	"//*[@id='gateway']";
	private static final String DROPDOWN_SUBJECT_XP=	"//*[@id='topic']";

	public MailQNewTicketPage(){
		super(new String[]{ROOT_XP, BUTTON_CREATE_TICKET_XP});
	}

	private void setFieldMessage(String text){
		WebDriverUtils.clearAndInputTextToField(WebDriverFactory.getServerDriver(), FIELD_MESSAGE_XP, text);
	}

	private void setFieldSubject(String subject){
		WebDriverUtils.clearAndInputTextToField(WebDriverFactory.getServerDriver(), FIELD_SUBJECT_XP, subject);
	}

	private MailQNewTicketPage setFieldEmail(String email){
		WebDriverUtils.clearAndInputTextToField(WebDriverFactory.getServerDriver(), FIELD_EMAIL_XP, email);
		WebDriverUtils.pressKey(WebDriverFactory.getServerDriver(), Keys.ENTER);
		return new MailQNewTicketPage();
	}

	public void setEmailCheckbox(boolean state){
		WebDriverUtils.setCheckBoxState(WebDriverFactory.getServerDriver(), CHECKBOX_EMAIL_XP, state);
	}

	public void setMessageCheckbox(boolean state){
		WebDriverUtils.setCheckBoxState(WebDriverFactory.getServerDriver(), CHECKBOX_MESSAGE_XP, state);
	}

	public void setCategoryDropdown(){
		WebDriverUtils.setDropdownOptionByValue(WebDriverFactory.getServerDriver(), DROPDOWN_CATEGORY_XP, "1");
	}

	public void setGatewayDropdown(){
		WebDriverUtils.setDropdownOptionByValue(WebDriverFactory.getServerDriver(), DROPDOWN_GATEWAY_XP, "1");
	}

	public void setSubjectDropdown(){
		WebDriverUtils.setDropdownOptionByValue(WebDriverFactory.getServerDriver(), DROPDOWN_SUBJECT_XP, "2");
	}

	public MailQSentTicketPage clickCreateButton(){
		WebDriverUtils.click(WebDriverFactory.getServerDriver(), BUTTON_CREATE_TICKET_XP);
		return new MailQSentTicketPage();
	}

	public MailQHomePage sendMessage(String email, String subject, String text, boolean sendEmail, boolean sendMessage){
		MailQNewTicketPage mailQNewTicketPage=setFieldEmail(email);
		mailQNewTicketPage.setCategoryDropdown();
		mailQNewTicketPage.setGatewayDropdown();
		mailQNewTicketPage.setSubjectDropdown();
		mailQNewTicketPage.setFieldSubject(subject);
		mailQNewTicketPage.setFieldMessage(text);
		mailQNewTicketPage.setEmailCheckbox(sendEmail);
		mailQNewTicketPage.setMessageCheckbox(sendMessage);
		return mailQNewTicketPage.clickCreateButton().clickButtonTickets();
	}
}
