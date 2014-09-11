package pageObjects.external.mailq;

import pageObjects.core.AbstractPage;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 8/2/13
 */

public class MailQHomePage extends AbstractPage{

	private static final String DESCENDING_SORT=				"/sort/age%7Cdesc";
	private static final String ASCENDING_SORT=					"/sort/importance%7Cdesc_age%7Casc";
	private static final String TOPNAV_XP=						"//*[@id='topnav']";
	private static final String CREATE_TICKET_XP=				"//a[contains(text(), 'Create ticket')]";
	private static final String SORTED_DESCENDING=				"//th[@class='sort_desc']";
	private static final String EXTEND_LATEST_MESSAGE_SUBJECT=	"//tr[1]//td[@class='ticketMessageColumn']/a[1]";
	private static final String LATEST_MESSAGE_SUBJECT=			"//tr[1]//td[@class='ticketMessageColumn']/a[contains(@href, 'view')]";
	private static final String LATEST_MESSAGE_LONG_SUBJECT=	"//tr[1]//div[@class='ticketSubject']/a";
	private static final String EXTENDED_MESSAGE=				"//span[contains(@class, 'plus marginRightThin minus')]";
	private static final String LINK_LOGOUT_XP = 				"//a[contains(text(), 'Logout')]";
	private static final int RETRIES=							50;
	private String uRL=											WebDriverUtils.getCurrentUrl();

	public MailQHomePage(){
		super(new String[]{TOPNAV_XP});
		changePageIfSortIsNotDescending();
	}

	public MailQHomePage(boolean valid){
		super(new String[]{TOPNAV_XP});
	}

	private boolean isDescendingSortingApplied(){
		return uRL.contains(DESCENDING_SORT);
	}

	private boolean isAscendingSortingApplied(){
		return uRL.contains(ASCENDING_SORT);
	}

	private MailQHomePage changePageIfSortIsNotDescending(){
		if(!isDescendingSortingApplied() && !isAscendingSortingApplied()){
			WebDriverUtils.navigateToURL(uRL.concat(DESCENDING_SORT));
		}else if(!isDescendingSortingApplied() && isAscendingSortingApplied()){
			WebDriverUtils.navigateToURL(uRL.replace(ASCENDING_SORT, DESCENDING_SORT));
		}
		WebDriverUtils.waitForElement(SORTED_DESCENDING);
		return new MailQHomePage(true);
	}

	public void extendLatestSubject(){
		WebDriverUtils.click(EXTEND_LATEST_MESSAGE_SUBJECT);
		WebDriverUtils.waitForElement(EXTENDED_MESSAGE);
	}

	public MailQReplyPage clickLatestSubjectIfMessageIsReceived(String subject){
		if(isMessageAppeared(subject)){
			clickLatestSubject();
			return new MailQReplyPage();
		}else{
			WebDriverUtils.runtimeExceptionWithUrl("The message did not appear after " + RETRIES + " retries");
		}
		return null;
	}

	public String getLatestSubject(){
		if(WebDriverUtils.isVisible(LATEST_MESSAGE_SUBJECT, 1)){
			return WebDriverUtils.getElementText(LATEST_MESSAGE_SUBJECT);
		}else{
			return WebDriverUtils.getElementText(LATEST_MESSAGE_LONG_SUBJECT);
		}

	}

	private MailQReplyPage clickLatestSubject(){
		if(WebDriverUtils.isVisible(LATEST_MESSAGE_SUBJECT, 1)){
			WebDriverUtils.click(LATEST_MESSAGE_SUBJECT);
		}else{
			WebDriverUtils.click(LATEST_MESSAGE_LONG_SUBJECT);
		}
		return new MailQReplyPage();
	}

	public MailQNewTicketPage clickCreateTicket(){
		WebDriverUtils.click(CREATE_TICKET_XP);
		return new MailQNewTicketPage();
	}

	private MailQHomePage refresh(){
		WebDriverUtils.refreshPage();
		return new MailQHomePage();
	}

	public boolean isMessageAppeared(String subject){
		boolean isMessageAppeared=false;
		for(int i=0; i <= RETRIES; i++){
			extendLatestSubject();
			String latestSubject=getLatestSubject();
			if(subject.equals(latestSubject)){
				isMessageAppeared=true;
				break;
			}else{
				refresh();
			}
		}
		return isMessageAppeared;
	}

	public MailQLoginPage logoutFromMailQ(){
		WebDriverUtils.click(LINK_LOGOUT_XP);
		return new MailQLoginPage();
	}
}
