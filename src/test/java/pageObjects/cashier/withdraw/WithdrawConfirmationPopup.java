package pageObjects.cashier.withdraw;

import pageObjects.core.AbstractPopup;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

public class WithdrawConfirmationPopup extends AbstractPopup{

    private static final String ROOT_XP = "//*[contains(text(), 'Are you sure you want to withdraw')]";

    public WithdrawConfirmationPopup(){
        super(new String[]{ROOT_XP});
    }

    public void assertAmount(String amount){
        AbstractTest.assertTrue(WebDriverUtils.getElementText(ROOT_XP).contains(amount), "Message contains '"+amount+"'");
    }

    public void assertAccount(String account){
        AbstractTest.assertTrue(WebDriverUtils.getElementText(ROOT_XP).contains(account), "Message contains '"+account+"'");
    }
}
