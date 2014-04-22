package pageObjects.inbox;

import pageObjects.base.AbstractPage;
import pageObjects.base.AbstractPageObject;
import pageObjects.base.AbstractPopup;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 4/10/14
 */

public class SendMessagePopup extends AbstractPopup{

	private static final String FIELD_MESSAGE_XP=		"//textarea[@id='mailMessage']";
	private static final String LABEL_ERROR_MESSAGE_XP=	"//div[@id='sendMessageError']";
	private static final String FIELD_SUBJECT_XP=		"//input[@id='mailSubject']";
	private static final String BUTTON_SEND_XP=			"//button[@id='sendMessageBtn']";

	public SendMessagePopup(){
		super(new String[]{FIELD_MESSAGE_XP});
	}

	public AbstractPageObject clickSendButton(boolean isSendingSuccessful){
		if(isSendingSuccessful){
			WebDriverUtils.click(BUTTON_SEND_XP);
			return new InboxPage();
		}else{
			WebDriverUtils.click(BUTTON_SEND_XP);
			return new SendMessagePopup();
		}
	}

	public AbstractPage clickCloseButton(boolean isSendMessagePopupOpenedFromInbox){
		WebDriverUtils.click(BUTTON_CLOSE_XP);
		if(isSendMessagePopupOpenedFromInbox){
			return new InboxPage();
		}else{
			return new SentItemsPage();
		}
	}

	public void enterSubject(String subject){
		WebDriverUtils.inputTextToField(FIELD_SUBJECT_XP, subject);
	}

	public void enterMessage(String message){
		WebDriverUtils.inputTextToField(FIELD_MESSAGE_XP, message);
	}

	public boolean isErrorPresent(){
		return WebDriverUtils.isVisible(LABEL_ERROR_MESSAGE_XP);
	}

	public InboxPage sendMessage(String text){
		enterSubject(text);
		enterMessage(text);
		return (InboxPage) clickSendButton(true);
	}

	public SendMessagePopup sendEmptyMessage(){
		enterSubject("");
		enterMessage("");
		return (SendMessagePopup) clickSendButton(false);
	}
}
