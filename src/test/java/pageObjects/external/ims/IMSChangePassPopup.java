package pageObjects.external.ims;

import pageObjects.core.AbstractServerBrowserWindow;
import utils.WebDriverUtils;
import utils.core.WebDriverFactory;

public class IMSChangePassPopup extends AbstractServerBrowserWindow {

	private static final String BUTTON_CHANGE_PASSWORD=	"//input[@id='changepwd']";
	private static final String FIELD_CHANGE_PASSWORD=	"//input[@id='password1']";
	public static final String LABEL_PASSWORD_CHANGED=	"//div[@id='message-info-1']";
	public static final String CHECKBOX_DO_NOT_REQUIRE_PASSWORD = "//input[@id='disablepasswordchange']";

	public IMSChangePassPopup(String mainWindowHandle){
		super(mainWindowHandle, new String[]{BUTTON_CHANGE_PASSWORD});
	}

	public void changePassword(String password, boolean checkBoxState){
		WebDriverUtils.inputTextToField(WebDriverFactory.getServerDriver(), FIELD_CHANGE_PASSWORD, password);
		WebDriverUtils.setCheckBoxState(WebDriverFactory.getServerDriver(), CHECKBOX_DO_NOT_REQUIRE_PASSWORD, checkBoxState);
		WebDriverUtils.click(WebDriverFactory.getServerDriver(), BUTTON_CHANGE_PASSWORD);
		WebDriverUtils.waitForElement(WebDriverFactory.getServerDriver(), LABEL_PASSWORD_CHANGED);
	}
}
