package pageObjects.account;

import pageObjects.core.AbstractPage;
import utils.WebDriverUtils;

/*
 * User: ivanva
 * Date: 5/30/13
 */

public class BalancePage extends AbstractPage{
	public static final String BALANCE_ROOT_XP="//*[contains(@id, 'portlet_56_INSTANCE')]";
	private static final String BUTTON_DEPOSIT_XP="//p[contains(@class,'balance-btn')]/a";
	private static final String LABEL_MAIN_BALANCE_VALUE_XP = "//tr[1]/td[@class]/p";
	private static final String LABEL_TOTAL_BALANCE_VALUE_XP = "//tr[6]/td[@class]/p";

	public BalancePage(){
		super(new String[]{BALANCE_ROOT_XP, BUTTON_DEPOSIT_XP});
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
