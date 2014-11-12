package pageObjects.cashier.withdraw;

import pageObjects.core.AbstractPortalPage;
import utils.WebDriverUtils;

public class WithdrawEnvoyPage extends AbstractPortalPage{

    private static final String ROOT_XP = "//*[contains(@id, '_wizBankDetailsCapture_')]";
    private static final String BUTTON_CANCEL_XP = "//*[contains(@id, 'CancelButton')]";

    public WithdrawEnvoyPage(){
        super(new String[]{ROOT_XP});
    }

    public void cancel(){
        WebDriverUtils.click(BUTTON_CANCEL_XP);
    }
}
