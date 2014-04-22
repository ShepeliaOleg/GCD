package pageObjects.external.ims;

import pageObjects.base.AbstractPage;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 7/24/13
 */

public class IMSHomePage extends AbstractPage{

	private static final String LINK_TAB_PLAYER_MANAGEMENT_XP=	"//*[@id='player_management_link']";
	private static final String LINK_TAB_TEMPLATE_TOOLS_XP=		"//*[@id='template_tools_link']";
	private static final String LINK_TAB_SYSTEM_MANAGEMENT_XP=	"//*[@id='system_management_link']";
	private static final String LINK_LOGOUT=					"//a[contains(text(), 'Log out')]";

	public IMSHomePage(){
		super(new String[]{LINK_TAB_PLAYER_MANAGEMENT_XP});
	}

	public IMSPlayerManagementPage clickPlayerManagement(){
		WebDriverUtils.click(LINK_TAB_PLAYER_MANAGEMENT_XP);
		return new IMSPlayerManagementPage();
	}

	public IMSSystemManagementPage clickSystemManagement(){
		WebDriverUtils.click(LINK_TAB_SYSTEM_MANAGEMENT_XP);
		return new IMSSystemManagementPage();
	}

	public IMSTemplateToolsPage clickTemplateTools(){
		WebDriverUtils.click(LINK_TAB_TEMPLATE_TOOLS_XP);
		return new IMSTemplateToolsPage();
	}

	public void logOut(){
		WebDriverUtils.click(LINK_LOGOUT);
	}
}
