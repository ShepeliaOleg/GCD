package pageObjects.login;

import pageObjects.core.AbstractPopup;

public class WelcomePopup extends AbstractPopup {
    public static final String TITLE_XP = AbstractPopup.TITLE_XP + "[contains(text(), 'Welcome')]";

    public WelcomePopup() {
        super(new String[]{TITLE_XP});
    }
}


