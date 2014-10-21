package pageObjects.cashier.deposit;

import pageObjects.cashier.TransactionSuccessfulPopup;
import pageObjects.cashier.TransactionUnSuccessfulPopup;
import pageObjects.core.AbstractPage;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

public class EnvoyDepositPage extends AbstractPage{

    private static final String AMOUNT_XP = "";

    public TransactionUnSuccessfulPopup cancelDeposit(){
        return new TransactionUnSuccessfulPopup();
    }

    public TransactionSuccessfulPopup pay(String amount){
        return new TransactionSuccessfulPopup();
    }

    private String getAmount(){
        return WebDriverUtils.getElementText(AMOUNT_XP);
    }

    public void assertAmount(String amount){
        AbstractTest.assertEquals(amount, getAmount().substring(1), "Amount");
    }
}
