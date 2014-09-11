package pageObjects.external.mailq;

import pageObjects.core.AbstractPage;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 8/7/13
 */

public class MailQSentTicketPage extends AbstractPage{

	private static final String ROOT_XP=			"//*[@id='ticketLogTitle']";
	private static final String CHANGE_SUBJECT_XP=	"//input[@value='Reopen']";
	private static final String BUTTON_TICKETS_XP=	"//li[1]//span[@class='menuName']";

	public MailQSentTicketPage(){
		super(new String[]{ROOT_XP, CHANGE_SUBJECT_XP});
	}

	public MailQHomePage clickButtonTickets(){
		WebDriverUtils.click(BUTTON_TICKETS_XP);
		return new MailQHomePage();
	}

}
