package pageObjects.external.ims;

import pageObjects.core.AbstractPage;
import utils.WebDriverUtils;

public class IMSTemplateToolsPage extends AbstractPage{

    private static final String BUTTON_LOGIN_LOGOUT_DATABASE_XP="//*[@href='/ims/LoginLogoutMessages']";

    public IMSTemplateToolsPage(){
        super(new String[]{BUTTON_LOGIN_LOGOUT_DATABASE_XP});
    }

    public IMSLoginDatabasePage clickLoginDatabase(){
        WebDriverUtils.click(BUTTON_LOGIN_LOGOUT_DATABASE_XP);
        return new IMSLoginDatabasePage();
    }
}
