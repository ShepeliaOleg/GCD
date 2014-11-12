package pageObjects.inbox;

import pageObjects.core.AbstractPortalPage;
import utils.WebDriverUtils;

public class InboxPage extends AbstractPortalPage {

	private static final String ROOT_XP=						"//*[@class='inbox']";
	private static final String BUTTON_SEND_MESSAGE_XP=			"//a[@data-render='sendMessage']";
	private static final String BUTTON_SENT_ITEMS_XP=			"//a[@data-render='sentItems']";
	private static final String BUTTON_INBOX_XP=				"//a[@data-render='showInbox']";
	private static final String LINK_FIRST_MESSAGE_XP=			"//tr[1]//a[@data-action='viewInboxMessage']";
	private static final String LINK_FIRST_MESSAGE_DELETE_XP=	"//tr[1]//a[@data-action='deleteMail']";
	private static final String POPUP_OVERLAY_XP=				"//*[@class='reveal-modal-bg']";
	private static final String LOADER_OVERLAY_XP=				"//*[@class='loading-mask-wrapper']";
	private static final String LABEL_UNREAD_XP=				"//td/span[contains(text(), 'No')]";
	private static final int RETRIES=							20;

	public InboxPage(){
		super(new String[]{ROOT_XP, BUTTON_SEND_MESSAGE_XP},
			new String[]{LOADER_OVERLAY_XP, POPUP_OVERLAY_XP});
	}

	public SendMessagePopup clickSendMessage(){
		WebDriverUtils.click(BUTTON_SEND_MESSAGE_XP);
		return new SendMessagePopup();
	}

	public SentItemsPage clickSentItems(){
		WebDriverUtils.click(BUTTON_SENT_ITEMS_XP);
		return new SentItemsPage();
	}

	public String getFirstMessageSubject(){
		return WebDriverUtils.getElementText(LINK_FIRST_MESSAGE_XP);
	}

	public boolean isMessagePresent(){
		return WebDriverUtils.isVisible(LINK_FIRST_MESSAGE_XP);
	}

	public boolean isMessageListPresent(){
		return WebDriverUtils.isVisible(ROOT_XP);
	}

	public boolean waitForMessageToAppear(){
		boolean isMessageAppeared=false;
		for(int i=0; i <= RETRIES; i++){
			if(isMessagePresent()){
				isMessageAppeared=true;
				break;
			}else{
				refresh();
			}
		}
		return isMessageAppeared;
	}

	private InboxPage refresh(){
		WebDriverUtils.refreshPage();
		return new InboxPage();
	}

	public InboxPage deleteFirstMessage(){
		WebDriverUtils.click(LINK_FIRST_MESSAGE_DELETE_XP);
		return new InboxPage();
	}

	public ReceivedInboxMessagePopup openFirstMessage(){
		WebDriverUtils.click(LINK_FIRST_MESSAGE_XP);
		return new ReceivedInboxMessagePopup();
	}

	public int getNumberOfUnreadMessages(){
		return WebDriverUtils.getXpathCount(LABEL_UNREAD_XP);
	}
}

