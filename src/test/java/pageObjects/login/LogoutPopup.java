package pageObjects.login;

import pageObjects.core.AbstractPortalPage;
import pageObjects.core.AbstractPortalPopup;
import utils.WebDriverUtils;

public class LogoutPopup extends AbstractPortalPopup {

    public final static String BUTTON_CLOSE_XP =	ROOT_XP + "//*[contains(@class, 'fn-decline')]";

    public LogoutPopup(){
        super(new String[]{BUTTON_ACCEPT_XP.getXpath(), BUTTON_CLOSE_XP});
    }

    public SignedOutPopup clickLogoutButton(){
        WebDriverUtils.click(BUTTON_ACCEPT_XP);
        return new SignedOutPopup();
    }

    public AbstractPortalPage close(){
        closePopup();
        return new AbstractPortalPage();
    }
}
