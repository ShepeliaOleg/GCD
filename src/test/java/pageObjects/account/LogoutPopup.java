package pageObjects.account;

import pageObjects.base.AbstractPage;
import pageObjects.base.AbstractPopup;
import utils.WebDriverUtils;

/**
 * Created by sergiich on 8/28/2014.
 */
public class LogoutPopup extends AbstractPopup {

    public final static String BUTTON_CLOSE_XP =	ROOT_XP + "//*[contains(@class, 'fn-decline')]";

    public LogoutPopup(){
        super(new String[]{BUTTON_ACCEPT_XP, BUTTON_CLOSE_XP});
    }

    private void clickLogoutButton(){
        WebDriverUtils.click(BUTTON_ACCEPT_XP);
    }

    public AbstractPage logout(){
        clickLogoutButton();
        return new SignedOutPopup().closePopup().waitForLogout();
    }
}
