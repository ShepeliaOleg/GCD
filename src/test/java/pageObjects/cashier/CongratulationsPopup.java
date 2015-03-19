package pageObjects.cashier;

import pageObjects.core.AbstractPortalPopup;

/**
 * Created by serhiist on 3/19/2015.
 */
public class CongratulationsPopup extends AbstractPortalPopup {
    private static final String ROOT_XP = "//div[contains(text(), 'Congratulations')]";

    public CongratulationsPopup(){
        super(new String[] {ROOT_XP});
    }

}
