package pageObjects.external.ims;

import pageObjects.base.AbstractBrowserWindow;
import utils.WebDriverUtils;

public class IMSChangePassPopup extends AbstractBrowserWindow {

	private static final String BUTTON_CHANGE_PASSWORD=	"//input[@id='changepwd']";
	private static final String FIELD_CHANGE_PASSWORD=	"//input[@id='password1']";
	public static final String LABEL_PASSWORD_CHANGED=	"//*[contains(text(), 'New password has been generated and sent to user.')]";

	public IMSChangePassPopup(String mainWindowHandle){
		super(mainWindowHandle, new String[]{BUTTON_CHANGE_PASSWORD});
	}

	public void changePassword(String password){
		WebDriverUtils.inputTextToField(FIELD_CHANGE_PASSWORD, password);
		WebDriverUtils.click(BUTTON_CHANGE_PASSWORD);
		WebDriverUtils.waitForElement(LABEL_PASSWORD_CHANGED);
	}
}
