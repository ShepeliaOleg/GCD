package pageObjects.external.mailq;

import pageObjects.core.AbstractServerPage;
import utils.WebDriverUtils;
import utils.core.WebDriverFactory;

public class MailQSentTicketPage extends AbstractServerPage{

	private static final String ROOT_XP=			"//*[@id='ticketLogTitle']";
	private static final String CHANGE_SUBJECT_XP=	"//input[@value='Reopen']";
	private static final String BUTTON_TICKETS_XP=	"//li[1]//span[@class='menuName']";

	public MailQSentTicketPage(){
		super(new String[]{ROOT_XP, CHANGE_SUBJECT_XP});
	}

	public MailQHomePage clickButtonTickets(){
		WebDriverUtils.click(WebDriverFactory.getServerDriver(), BUTTON_TICKETS_XP);
		return new MailQHomePage();
	}

}
