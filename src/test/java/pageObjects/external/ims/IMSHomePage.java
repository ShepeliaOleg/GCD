package pageObjects.external.ims;

import pageObjects.core.AbstractServerPage;
import utils.WebDriverUtils;
import utils.core.WebDriverFactory;

public class IMSHomePage extends AbstractServerPage {

	public static final String LINK_TAB_PLAYER_MANAGEMENT_XP=	"//*[@id='player_management_link']";
	private static final String LINK_TAB_TEMPLATE_TOOLS_XP=		"//*[@id='template_tools_link']";
	private static final String LINK_TAB_SYSTEM_MANAGEMENT_XP=	"//*[@id='system_management_link']";
	private static final String LINK_LOGOUT=					"//a[contains(text(), 'Log out')]";
    private static final String LINK_TAB_PAYMENTS_XP =          "//*[@id='payments_link']";

	public IMSHomePage(){
		super(new String[]{LINK_TAB_PLAYER_MANAGEMENT_XP});
	}

	public IMSPlayerManagementPage clickPlayerManagement(){
		WebDriverUtils.click(WebDriverFactory.getServerDriver(), LINK_TAB_PLAYER_MANAGEMENT_XP);
		return new IMSPlayerManagementPage();
	}

	public IMSTemplateToolsPage clickTemplateTools(){
		WebDriverUtils.click(WebDriverFactory.getServerDriver(), LINK_TAB_TEMPLATE_TOOLS_XP);
		return new IMSTemplateToolsPage();
	}

    public IMSPaymentsPage clickPayments(){
        WebDriverUtils.click(LINK_TAB_PAYMENTS_XP);
        return new IMSPaymentsPage();
    }

	public void logOut(){
		WebDriverUtils.click(WebDriverFactory.getServerDriver(), LINK_LOGOUT);
	}
}
