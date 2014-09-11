package pageObjects.login;

import pageObjects.core.AbstractPage;
import pageObjects.core.AbstractPopup;
import utils.WebDriverUtils;

/**
 * Created by sergiich on 8/28/2014.
 */
public class SignedOutPopup extends AbstractPopup {

    public SignedOutPopup() {
        super(new String[]{BUTTON_ACCEPT_XP, BUTTON_CLOSE_XP});
    }

    private void clickLoginAgain() {
        WebDriverUtils.click(BUTTON_ACCEPT_XP);
    }

    public LoginPopup loginAgain() {
        clickLoginAgain();
        return new LoginPopup();
    }

    public AbstractPage close() {
        closePopup();
        return new AbstractPage();
    }
}
