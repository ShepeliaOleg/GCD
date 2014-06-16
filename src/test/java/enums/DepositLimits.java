package enums;

import pageObjects.responsibleGaming.ResponsibleGamingPage;

/**
 * User: sergiich
 * Date: 6/16/2014
 */
public enum DepositLimits{
	daily(ResponsibleGamingPage.DROPDOWN_DAILY_XP),
	weekly(ResponsibleGamingPage.DROPDOWN_WEEKLY_XP),
	monthly(ResponsibleGamingPage.DROPDOWN_MONTHLY_XP);

	private final String name;

	private DepositLimits(String s) {
		name = s;
	}

	public String getXpath(){
		return name;
	}
}
