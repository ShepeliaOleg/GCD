package pageObjects.inbox;

import pageObjects.core.AbstractPortalPopup;
import utils.WebDriverUtils;

public class ReplyToReceivedInboxMessagePopup extends AbstractPortalPopup{

	private static final String FIELD_MESSAGE_XP=	ROOT_XP + "//*[@id = 'mailMessageArea']";
	private static final String FIELD_SUBJECT_XP=	ROOT_XP + "//*[@id = 'mailSubject']";
	private static final String BUTTON_SEND_XP=		ROOT_XP + "//*[@id = 'sendMessageBtn']";

	public ReplyToReceivedInboxMessagePopup(){
		super(new String[]{BUTTON_CLOSE_XP});
	}

	public InboxPage clickSendButton(){
		WebDriverUtils.click(BUTTON_SEND_XP);
		return new InboxPage();
	}

	public void fillSubjectField(String text){
		WebDriverUtils.clearAndInputTextToField(FIELD_SUBJECT_XP, text);
	}

	public void fillMessageField(String text){
		WebDriverUtils.clearAndInputTextToField(FIELD_MESSAGE_XP, text);
	}

	public InboxPage sendReply(String text){
		fillSubjectField(text);
		fillMessageField(text);
		clickSendButton();
		return new InboxPage();
	}

}
