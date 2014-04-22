package pageObjects.external.ims;

import pageObjects.base.AbstractBrowserWindowPopup;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 2/10/14
 */

public class IMSChangePassPopup extends AbstractBrowserWindowPopup{

	private static final String BUTTON_CHANGE_PASSWORD=	"//input[@id='changepwd']";
	private static final String FIELD_CHANGE_PASSWORD=	"//input[@id='password1']";
	private static final String LABEL_PASSWORD_CHANGED=	"//*[contains(text(), 'New password has been generated and sent to user.')]";

	private String mainWindowHandle;
	private static final String VALID_GAME_URL="/launchcasino.html";

	public IMSChangePassPopup(String mainWindowHandle){
		super(mainWindowHandle, new String[]{BUTTON_CHANGE_PASSWORD});
		this.mainWindowHandle=mainWindowHandle;
	}

	public String getMainWindowHandle(){

		return mainWindowHandle;
	}

	public void changePassword(String password){
		WebDriverUtils.inputTextToField(FIELD_CHANGE_PASSWORD, password);
		WebDriverUtils.click(BUTTON_CHANGE_PASSWORD);
		WebDriverUtils.waitForElement(LABEL_PASSWORD_CHANGED);
	}

	public void closePopup(){
		WebDriverUtils.closeCurrentWindow();
		WebDriverUtils.switchToWindow(getMainWindowHandle());
	}
}
