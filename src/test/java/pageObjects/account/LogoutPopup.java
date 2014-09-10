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

    public SignedOutPopup clickLogoutButton(){
        WebDriverUtils.click(BUTTON_ACCEPT_XP);
        return new SignedOutPopup();
    }

    @Override
    public void clickClose(){
        WebDriverUtils.click(BUTTON_CLOSE_XP);
    }

    public AbstractPage closePopup(){
        clickClose();
        return new AbstractPage();
    }

    public AbstractPage logout(){
        return clickLogoutButton().closePopup().waitForLogout();
    }
}
