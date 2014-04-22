package pageObjects.inbox;

import pageObjects.base.AbstractPage;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 4/10/14
 */

public class SentItemsPage extends AbstractPage{

	private static final String ROOT_XP=						"//*[@class='outbox']";
	private static final String BUTTON_PREVIOUS_PAGE_XP=		"//li[@class='first']/a";
	private static final String BUTTON_INBOX_XP=				"//a[@data-render='showInbox']";
	private static final String BUTTON_SENT_ITEMS_XP=			"//a[@data-render='sentItems']";
	private static final String BUTTON_SEND_MESSAGE_XP=			"//a[@data-render='sendMessage']";
	private static final String BUTTON_NEXT_PAGE_XP=			"//a[contains(text(),'Next')]";
	private static final String BUTTON_FIRST_PAGE_XP=			"//a[@data-page='1']";
	private static final String LINK_FIRST_MESSAGE_XP=			"//tr[1]//a[@data-action='viewSentMessage']";
	private static final String LINK_FIRST_MESSAGE_DELETE_XP=	"//tr[1]//a[@data-action='deleteSentMail']";
	
	private static final String LOADER_OVERLAY_XP=				"//*[@class='loading-mask-wrapper']";
	private static final String POPUP_OVERLAY_XP=				"//*[@class='reveal-modal-bg']";

	public SentItemsPage(){
		super(new String[]{ROOT_XP}, new String[]{LOADER_OVERLAY_XP, POPUP_OVERLAY_XP});
	}

	public SendMessagePopup clickSendMessage(){
		WebDriverUtils.click(BUTTON_SEND_MESSAGE_XP);
		return new SendMessagePopup();
	}

	public SentItemsPage clickNextPage(){
		WebDriverUtils.click(BUTTON_NEXT_PAGE_XP);
		WebDriverUtils.waitForElement(BUTTON_PREVIOUS_PAGE_XP);
		return new SentItemsPage();
	}

	public SentItemsPage clickPreviousPage(){
		WebDriverUtils.click(BUTTON_PREVIOUS_PAGE_XP);
		WebDriverUtils.waitForElementToDisappear(BUTTON_PREVIOUS_PAGE_XP);
		return new SentItemsPage();
	}

	public SentItemsPage clickFirstPage(){
		WebDriverUtils.click(BUTTON_FIRST_PAGE_XP);
		return new SentItemsPage();
	}

	public SentItemsPage deleteFirstMessage(){
		WebDriverUtils.click(LINK_FIRST_MESSAGE_DELETE_XP);
		WebDriverUtils.waitForElementToDisappear(LOADER_OVERLAY_XP);
		WebDriverUtils.waitForElementToDisappear(POPUP_OVERLAY_XP);
		return new SentItemsPage();
	}

	public SentMessagePopup openFirstMessage(){
		WebDriverUtils.click(LINK_FIRST_MESSAGE_XP);
		return new SentMessagePopup();
	}

	public String getFirstMessageSubject(){
		return WebDriverUtils.getElementText(LINK_FIRST_MESSAGE_XP);
	}

	public boolean isMessageListPresent(){
		return WebDriverUtils.isVisible(ROOT_XP);
	}
}
