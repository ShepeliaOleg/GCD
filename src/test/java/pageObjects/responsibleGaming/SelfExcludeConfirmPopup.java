package pageObjects.responsibleGaming;

import pageObjects.HomePage;
import pageObjects.base.AbstractPopup;
import utils.WebDriverUtils;

/*
 * User: ivanva
 * Date: 6/13/13
 */

public class SelfExcludeConfirmPopup extends AbstractPopup{

	public static final String BUTTON_OK_XP=				"//*[@id='selfexclusion-ok']";
	private static final String LABEL_CONFIRM_MESSAGE_XP=	"//*[@class='selfexclusion-result']//*[@class='portlet-msg-success']";

	public SelfExcludeConfirmPopup(){
		super(new String[]{BUTTON_OK_XP});
	}

	public HomePage clickOk(){
		WebDriverUtils.click(BUTTON_OK_XP);
		WebDriverUtils.waitForElementToDisappear(BUTTON_OK_XP);
		return new HomePage();
	}

	public boolean confirmationMessagesVisible(){
		return WebDriverUtils.isVisible(LABEL_CONFIRM_MESSAGE_XP);
	}
}
