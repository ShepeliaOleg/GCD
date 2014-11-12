package pageObjects.admin;

import pageObjects.core.AbstractPortalPopup;

public class AdminCanNotPlayPopup extends AbstractPortalPopup{

    private static final String ADMIN_CAN_NOT_PLAY_XP = ROOT_XP + "//*[contains(text(), 'You are admin and can't play')]";

    public AdminCanNotPlayPopup(){
        super(new String[]{ADMIN_CAN_NOT_PLAY_XP});
    }
}
