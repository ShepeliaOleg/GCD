package pageObjects.external.ims;

import pageObjects.base.AbstractIframe;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 8/16/13
 */

public class IMSTermsAndConditionsIframe extends AbstractIframe{

	private static final String FIELD_TERMS = 	"//*[@id='terms']";
	private static final String BUTTON_UPDATE = "//input[@value = 'Update']";

	public IMSTermsAndConditionsIframe(String iframeId){
		super(iframeId);
	}

	private void setFrozen(boolean state){
		WebDriverUtils.setCheckBoxState("frozen", "checked", state);
	}

	private void fillTerms(String termsAndConditionsText){
		WebDriverUtils.clearAndInputTextToField(FIELD_TERMS, termsAndConditionsText);
	}

	private void clickUpdate(){
		WebDriverUtils.click(BUTTON_UPDATE);
	}

	public void setFreezeStateAndUpdate(String termsAndConditionsText, boolean state){
		setFrozen(state);
		fillTerms(termsAndConditionsText);
		clickUpdate();
	}
}
