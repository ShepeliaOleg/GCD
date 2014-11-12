package pageObjects.external.ims;

import pageObjects.core.AbstractServerPage;
import utils.WebDriverUtils;
import utils.core.WebDriverFactory;

public class IMSWelcomeMessagePage extends AbstractServerPage {

	private static final String BUTTON_UPDATE_MESSAGE_XP =  "//*[@id='save']";
	private static final String CHECKBOX_FROZEN_XP =        "//*[@id='frozen']";
	private static final String LABEL_MESSAGE_SUCCESS_XP =  "//div[contains(text(), 'Message successfully updated')]";
    private static final String BUTTON_BACK_XP =            "//*[@id='pagelink']";

    public IMSWelcomeMessagePage(){
        super(new String[]{CHECKBOX_FROZEN_XP});
    }

	private void setFrozen(boolean state){
		WebDriverUtils.setCheckBoxState(WebDriverFactory.getServerDriver(), CHECKBOX_FROZEN_XP, state);
	}

	public IMSLoginDatabasePage setFreezeStateAndUpdate(boolean state) {
		setFrozen(state);
		WebDriverUtils.click(WebDriverFactory.getServerDriver(), BUTTON_UPDATE_MESSAGE_XP);
		WebDriverUtils.waitForElement(WebDriverFactory.getServerDriver(), LABEL_MESSAGE_SUCCESS_XP);
        WebDriverUtils.click(WebDriverFactory.getServerDriver(), BUTTON_BACK_XP);
        return new IMSLoginDatabasePage();
	}
}
