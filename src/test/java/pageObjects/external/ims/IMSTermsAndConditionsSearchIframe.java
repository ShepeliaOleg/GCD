package pageObjects.external.ims;

import pageObjects.base.AbstractIframe;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 8/16/13
 */

public class IMSTermsAndConditionsSearchIframe extends AbstractIframe{

	private static final String RESULT_XP=					"//table[@class='result']";
	private static final String DROPDOWN_CASINO_XP=			"//*[@id='casinocode']";
	private static final String DROPDOWN_CLIENT_TYPE_XP=	"//*[@id='clienttype']";
	private static final String DROPDOWN_CLIENT_PLATFORM_XP="//*[@id='clientplatform']";
	private static final String FIELD_VERSION_XP=			"//*[@id='version']";
	private static final String CHECKBOX_INCLUDE_FROZEN_XP=	"//*[@id='frozen']";
	private static final String BUTTON_SEARCH_XP=			"//*[@id='submit']";
	private static final String BUTTON_MODIFY_XP=			"//input[@value = 'Modify']";
	private static final String CASINO_CODE=				"81001";
	private static final String CLIENT_TYPE=				"portal";
	private static final String CLIENT_PLATFORM=			"web";
	private static final String VERSION=					"3";

	public IMSTermsAndConditionsSearchIframe(String iframeId){
		super(iframeId);
	}

	private void setCasinoCode(){
		WebDriverUtils.setDropdownOptionByValue(DROPDOWN_CASINO_XP, CASINO_CODE);
	}

	private void setClientType(){
		WebDriverUtils.setDropdownOptionByValue(DROPDOWN_CLIENT_TYPE_XP, CLIENT_TYPE);
	}

	private void setClientPlatform(){
		WebDriverUtils.setDropdownOptionByValue(DROPDOWN_CLIENT_PLATFORM_XP, CLIENT_PLATFORM);
	}

	private void setVersion(){
		WebDriverUtils.clearAndInputTextToField(FIELD_VERSION_XP, VERSION);
	}

	private void clickFrozen(){
		WebDriverUtils.setCheckBoxState("frozen", "checked", true);
	}

	private void clickSearch(){
		WebDriverUtils.click(BUTTON_SEARCH_XP);
	}

	private void clickModify(){
		WebDriverUtils.click(BUTTON_MODIFY_XP);
	}

	private void searchForTermsAndConditions(){
		setCasinoCode();
		setClientType();
		setClientPlatform();
		setVersion();
		clickFrozen();
		clickSearch();
		WebDriverUtils.waitForElement(RESULT_XP);
	}

	public IMSTermsAndConditionsPage openTermsAndConditions(){
		searchForTermsAndConditions();
		quitIframe();
		IMSTermsAndConditionsSearchIframe imsTermsAndConditionsSearchIframe=new IMSTermsAndConditionsSearchIframe(IMSTermsAndConditionsSearchPage.SEARCH_IFRAME_ID);
		imsTermsAndConditionsSearchIframe.clickModify();
		quitIframe();
		return new IMSTermsAndConditionsPage();
	}

	public void quitIframe(){
		WebDriverUtils.switchFromIframe();
	}
}
