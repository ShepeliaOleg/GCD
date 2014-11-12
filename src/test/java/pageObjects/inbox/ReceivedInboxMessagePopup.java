package pageObjects.inbox;

import pageObjects.core.AbstractPortalPopup;
import utils.WebDriverUtils;

public class ReceivedInboxMessagePopup extends AbstractPortalPopup{

	private static final String BUTTON_DELETE_XP=	ROOT_XP + "//a[@data-action = 'deleteMail']";
	private static final String BUTTON_REPLY_XP=	ROOT_XP + "//a[@data-title = 'Reply']";
	private static final String FIELD_SUBJECT_XP=	"//th[contains(text(),'Subject')]/following-sibling::td";
	private static final String FIELD_MESSAGE_XP=	"//th[contains(text(),'Message')]/following-sibling::td";

	public ReceivedInboxMessagePopup(){
		super(new String[]{BUTTON_CLOSE_XP});
	}

	public InboxPage clickDeleteButton(){
		WebDriverUtils.click(BUTTON_DELETE_XP);
		return new InboxPage();
	}

	public InboxPage clickCloseButton(){
		WebDriverUtils.click(BUTTON_REPLY_XP);
		return new InboxPage();
	}

	public ReplyToReceivedInboxMessagePopup clickReplyButton(){
		WebDriverUtils.click(BUTTON_REPLY_XP);
		return new ReplyToReceivedInboxMessagePopup();
	}

	public String getSubject(){
		return WebDriverUtils.getElementText(FIELD_SUBJECT_XP);
	}

	public String getMessage(){
		return WebDriverUtils.getElementText(FIELD_MESSAGE_XP);
	}
}
