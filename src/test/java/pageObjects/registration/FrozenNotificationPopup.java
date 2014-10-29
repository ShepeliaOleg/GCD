package pageObjects.registration;

import pageObjects.core.AbstractPopup;

public class FrozenNotificationPopup extends AbstractPopup{

    private static final String ROOT_XP = AbstractPopup.TITLE_XP + "[contains(text(), 'Failed login')]";

    public FrozenNotificationPopup(){
        super(new String[] {ROOT_XP});
    }
}
