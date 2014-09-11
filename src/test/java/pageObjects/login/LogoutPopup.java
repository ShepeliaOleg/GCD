package pageObjects.login;

import pageObjects.core.AbstractPage;
import pageObjects.core.AbstractPopup;
import utils.WebDriverUtils;

public class LogoutPopup extends AbstractPopup {

    public final static String BUTTON_CLOSE_XP =	ROOT_XP + "//*[contains(@class, 'fn-decline')]";

    public LogoutPopup(){
        super(new String[]{BUTTON_ACCEPT_XP, BUTTON_CLOSE_XP});
    }

    public SignedOutPopup clickLogoutButton(){
        WebDriverUtils.click(BUTTON_ACCEPT_XP);
        return new SignedOutPopup();
    }

    public AbstractPage close(){
        closePopup();
        return new AbstractPage();
    }
}
