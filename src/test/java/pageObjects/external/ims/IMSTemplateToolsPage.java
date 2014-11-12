package pageObjects.external.ims;

import pageObjects.core.AbstractServerPage;
import utils.WebDriverUtils;
import utils.core.WebDriverFactory;

public class IMSTemplateToolsPage extends AbstractServerPage {

    private static final String BUTTON_LOGIN_LOGOUT_DATABASE_XP="//*[@href='/ims/LoginLogoutMessages']";

    public IMSTemplateToolsPage(){
        super(new String[]{BUTTON_LOGIN_LOGOUT_DATABASE_XP});
    }

    public IMSLoginDatabasePage clickLoginDatabase(){
        WebDriverUtils.click(WebDriverFactory.getServerDriver(), BUTTON_LOGIN_LOGOUT_DATABASE_XP);
        return new IMSLoginDatabasePage();
    }
}
