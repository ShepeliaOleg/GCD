package pageObjects.external.ims;

import pageObjects.core.AbstractPage;
import utils.WebDriverUtils;

/**
 * Created by sergiich on 3/4/14.
 */

public class IMSTemplateToolsPage extends AbstractPage{

    private static final String BUTTON_LOGIN_LOGOUT_DATABASE_XP="//*[contains(text(), 'Login/logout database')]";

    public IMSTemplateToolsPage(){
        super(new String[]{BUTTON_LOGIN_LOGOUT_DATABASE_XP});
    }

    public IMSLoginDatabasePage clickLoginDatabase(){
        WebDriverUtils.click(BUTTON_LOGIN_LOGOUT_DATABASE_XP);
        return new IMSLoginDatabasePage();
    }
}
