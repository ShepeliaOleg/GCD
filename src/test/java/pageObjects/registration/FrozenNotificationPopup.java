package pageObjects.registration;

import pageObjects.core.AbstractPortalPopup;

public class FrozenNotificationPopup extends AbstractPortalPopup{

    private static final String ROOT_XP = AbstractPortalPopup.TITLE_XP + "[contains(text(), 'Failed login')]";

    public FrozenNotificationPopup(){
        super(new String[] {ROOT_XP});
    }
}
