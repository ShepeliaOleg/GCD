package pageObjects.account;

import pageObjects.base.AbstractPage;
import pageObjects.base.AbstractPopup;
import utils.WebDriverUtils;

/**
 * Created by sergiich on 8/28/2014.
 */
public class SignedOutPopup extends AbstractPopup {

    public final static String BUTTON_CLOSE_XP =	ROOT_XP + "//*[contains(@class, 'fn-decline')]";

    public SignedOutPopup() {
        super(new String[]{BUTTON_ACCEPT_XP, BUTTON_CLOSE_XP});
    }

    private void clickLoginAgain() {
        WebDriverUtils.click(BUTTON_ACCEPT_XP);
    }

    private LoginPopup loginAgain() {
        clickLoginAgain();
        return new LoginPopup();
    }

    public void clickClose(){
        WebDriverUtils.click(BUTTON_CLOSE_XP);
    }

    public AbstractPage closePopup() {
        clickClose();
        return new AbstractPage();
    }
}
