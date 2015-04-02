package pageObjects.account;

import pageObjects.core.AbstractPortalPage;
import utils.WebDriverUtils;


public class BalancePage extends AbstractPortalPage {
	//public static final String BALANCE_ROOT_XP="//*[contains(@id, 'portlet_56_INSTANCE')]";
	public static final String BALANCE_ROOT_XP="//*[contains(@class, 'portlet-56')]";
	private static final String BUTTON_DEPOSIT_XP="//p[contains(@class,'balance-btn')]/a";
	private static final String LABEL_MAIN_BALANCE_VALUE_XP = "//tr[1]/td[@class]/p";
	private static final String LABEL_TOTAL_BALANCE_VALUE_XP = "//tr[6]/td[@class]/p";
	private static final String BUTTON_CONVERT_COMPOINTS = "//*[contains(@class, 'fn-convert-to-money')]";
	//private static final String NOTIFICATION = "//*[contains(@class, 'bonus-popup') or contains(text(), 'Compoints has been converted successfully')]";
	private static final String NOTIFICATION = "//*[contains(@class, 'bonus-popup') or contains(text(), '";

	public BalancePage(){
		//super(new String[]{BALANCE_ROOT_XP, BUTTON_DEPOSIT_XP});
		super(new String[]{BALANCE_ROOT_XP});
	}

	public void convertCompPoints(String placeholder){
		WebDriverUtils.click(BUTTON_CONVERT_COMPOINTS);
		new AbstractPortalPage(new String[] {NOTIFICATION + placeholder +"')]" });
	}

	private void clickOnDepositButton(){
		WebDriverUtils.click(BUTTON_DEPOSIT_XP);
	}

	private String getMainBalance(){
		return WebDriverUtils.getElementText(LABEL_MAIN_BALANCE_VALUE_XP);
	}

	private String getTotalBalance(){
		return WebDriverUtils.getElementText(LABEL_TOTAL_BALANCE_VALUE_XP);
	}

}
