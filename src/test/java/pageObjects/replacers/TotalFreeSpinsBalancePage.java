package pageObjects.replacers;

import pageObjects.core.AbstractPortalPage;
import utils.WebDriverUtils;

/**
 * Created by serhiist on 3/31/2015.
 */
public class TotalFreeSpinsBalancePage extends AbstractPortalPage {
    private static final String ROOT_XP = "//*[@id='layout-column__column-1']";
    private static final String TOTAL_FREE_SPINS_BALANCE_XP = ROOT_XP + "//div[1]//p/span";

    public TotalFreeSpinsBalancePage() {
        super(new String[]{ROOT_XP});
    }

    public String getTotalFreeSpinBalance (){
        return WebDriverUtils.getElementText(TOTAL_FREE_SPINS_BALANCE_XP);
    }
}
