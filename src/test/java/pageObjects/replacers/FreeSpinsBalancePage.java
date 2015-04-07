package pageObjects.replacers;

import pageObjects.core.AbstractPortalPage;
import utils.WebDriverUtils;

/**
 * Created by serhiist on 3/31/2015.
 */
public class FreeSpinsBalancePage extends AbstractPortalPage {
    private static final String FREE_SPIN_BALANCE_ROOT_XP = "//*[@id='layout-column__column-1']";
    private static final String TOTAL_FREE_SPIN_BALANCE_XP = FREE_SPIN_BALANCE_ROOT_XP + "//div[1]//p/span";

    public FreeSpinsBalancePage() {
        super(new String[]{TOTAL_FREE_SPIN_BALANCE_XP});
    }

    public String getTotalFreeSpinBalance (){
        return WebDriverUtils.getElementText(TOTAL_FREE_SPIN_BALANCE_XP);
    }

}
