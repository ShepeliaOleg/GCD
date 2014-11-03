package pageObjects.login;

import pageObjects.core.AbstractPage;
import pageObjects.core.AbstractPopup;
import utils.WebDriverUtils;

public class SignedOutPopup extends AbstractPopup {

    public static final String TITLE_XP = AbstractPopup.TITLE_XP + "[contains(text(), 'Signed out')]";

    public SignedOutPopup() {
        super(new String[]{TITLE_XP, BUTTON_ACCEPT_XP, BUTTON_DECLINE_XP});
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
