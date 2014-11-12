package pageObjects.login;

import pageObjects.core.AbstractPortalPage;
import pageObjects.core.AbstractPortalPopup;
import utils.WebDriverUtils;

public class SignedOutPopup extends AbstractPortalPopup {

    public static final String TITLE_XP = AbstractPortalPopup.TITLE_XP + "[contains(text(), 'Signed out')]";

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

    public AbstractPortalPage close() {
        closePopup();
        return new AbstractPortalPage();
    }
}
