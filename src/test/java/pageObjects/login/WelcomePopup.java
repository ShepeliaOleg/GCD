package pageObjects.login;

import pageObjects.core.AbstractPortalPopup;

public class WelcomePopup extends AbstractPortalPopup {
    public static final String TITLE_XP = AbstractPortalPopup.TITLE_XP + "[contains(text(), 'Welcome')]";

    public WelcomePopup() {
        super(new String[]{TITLE_XP});
    }
}


