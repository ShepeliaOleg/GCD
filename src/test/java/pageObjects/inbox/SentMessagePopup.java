package pageObjects.inbox;

import pageObjects.core.AbstractPortalPopup;
import utils.WebDriverUtils;

public class SentMessagePopup extends AbstractPortalPopup{
	private static final String BUTTON_DELETE_XP=	ROOT_XP+"//a[@data-action='deleteSentMail']";
	private static final String FIELD_TO_XP=		"//th[contains(text(),'To')]/following-sibling::td";
	private static final String FIELD_SUBJECT_XP=	"//th[contains(text(),'Subject')]/following-sibling::td";
    private static final String FIELD_MESSAGE_XP=	"//th[contains(text(),'Message')]/following-sibling::td";

	public SentMessagePopup(){
			super(new String[]{BUTTON_CLOSE_XP.getXpath()});
			WebDriverUtils.waitFor(2000);
		}

	public String getReceiver(){
		return WebDriverUtils.getElementText(FIELD_TO_XP);
	}

	public String getSubject(){
		return WebDriverUtils.getElementText(FIELD_SUBJECT_XP);
	}

	public String getMessage(){
		return WebDriverUtils.getElementText(FIELD_MESSAGE_XP);
	}

	public SentItemsPage clickDeleteButton(){
		WebDriverUtils.click(BUTTON_DELETE_XP);
		return new SentItemsPage();
	}

	public SentItemsPage clickCloseButton(){
		WebDriverUtils.click(BUTTON_CLOSE_XP);
		return new SentItemsPage();
	}
}
