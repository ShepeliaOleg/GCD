package pageObjects.external.ims;

import pageObjects.core.AbstractPage;
import utils.WebDriverUtils;

/**
 * Created by sergiich on 3/5/14.
 */

public class IMSWelcomeMessagePage extends AbstractPage{

	private static final String BUTTON_UPDATE_MESSAGE_XP = "//*[@id='save']";
	private static final String CHECKBOX_FROZEN_XP = "//*[@id='frozen']";
	private static final String LABEL_MESSAGE_SUCCESS_XP = "//div[contains(text(), 'Message successfully updated')]";

    public IMSWelcomeMessagePage(){
        super(new String[]{CHECKBOX_FROZEN_XP});
    }

	private void setFrozen(boolean state){
		WebDriverUtils.setCheckBoxState(CHECKBOX_FROZEN_XP, "checked", state);
	}

	public void setFreezeStateAndUpdate(boolean state) {
		setFrozen(state);
		WebDriverUtils.click(BUTTON_UPDATE_MESSAGE_XP);
		WebDriverUtils.waitForElement(LABEL_MESSAGE_SUCCESS_XP);
	}
}
