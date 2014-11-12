package pageObjects.responsibleGaming;

import pageObjects.core.AbstractPortalPage;
import utils.WebDriverUtils;

public class SelfExcludePage extends AbstractPortalPage {

    private static final String ROOT_XP = "//*[contains(@class, 'self-exclusion')]";
    private static final String DROPDOWN_PERIOD_XP = "//*[@id='period']";
    private static final String BUTTON_SUBMIT_XP = ROOT_XP + "//*[@type='submit']";


	public SelfExcludePage(){
		super(new String[]{ROOT_XP});
	}

	public SelfExcludeSuccessfulNotification submitSelfExclude(){
		WebDriverUtils.click(BUTTON_SUBMIT_XP);
		return new SelfExcludeSuccessfulNotification();
	}

}
