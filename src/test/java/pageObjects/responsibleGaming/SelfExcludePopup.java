package pageObjects.responsibleGaming;

import pageObjects.core.AbstractPopup;
import utils.WebDriverUtils;

/*
 * User: ivanva
 * Date: 6/13/13
 */

public class SelfExcludePopup extends AbstractPopup{

	private static final String BUTTON_OK_XP="//*[@id='selfExclusionViewModel']//button[@type='submit']";
	private static final String INPUT_NAME_XP="//*[@id='selfExclusionViewModel']//*[contains(@id,'name')]";
	private static final String INPUT_BIRTH_XP="//*[@id='selfExclusionViewModel']//*[contains(@id,'dateOfBirth')]";
	private static final String INPUT_USERNAME_XP="//*[@id='selfExclusionViewModel']//*[contains(@id,'userName')]";
	private static final String INPUT_ADDRESS_XP="//*[@id='selfExclusionViewModel']//*[contains(@id,'address1')]";
	private static final String INPUT_CITY_XP="//*[@id='selfExclusionViewModel']//*[contains(@id,'address2')]";
	private static final String INPUT_ZIP_CODE_XP="//*[@id='selfExclusionViewModel']//*[contains(@id,'postCode')]";

	public SelfExcludePopup(){
		super(new String[]{BUTTON_OK_XP});
	}

	public SelfExcludeConfirmPopup submitSelfExclude(){
		WebDriverUtils.click(BUTTON_OK_XP);
		return new SelfExcludeConfirmPopup();
	}

}
