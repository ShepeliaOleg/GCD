package pageObjects.cashier.deposit;

import enums.PaymentMethod;
import pageObjects.cashier.TransactionSuccessfulPopup;
import pageObjects.cashier.TransactionUnSuccessfulPopup;
import pageObjects.core.AbstractPortalPage;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

public class QIWIDepositPage extends AbstractPortalPage {

    private static final String ROOT_XP =               "//*[@class='paymentContent']";
    private static final String PHONE_XP =              "//*[@id='phone']";
    private static final String AMOUNT_XP =             "//*[@class='payment-description_cnt_fields_i'][2]/strong";
    private static final String PAY_BUTTON =            "//*[@class='orangeBtn']";
    private static final String DROPDOWN_CURRENCY_XP =  "//*[@id='ui-id-1-button']";
    private static final String CURRENCY_RUB_XP =       "//a[contains(text(),'RUB')]";
    private static final String PASSWORD_XP =           "//*[@name='password']";

    private static final String PROVIDER_COMMISION_XP = "//*[contains(@class, 'providerComm')]";
    private static final String PASSWORD_INCORRECT = "incorrect";

    public QIWIDepositPage(){
        super(new String[]{ROOT_XP, PAY_BUTTON, PHONE_XP, AMOUNT_XP});
    }

    public TransactionSuccessfulPopup pay(){
        if(!WebDriverUtils.isVisible(DROPDOWN_CURRENCY_XP,1)){
            fillPassword(PaymentMethod.QIWI.getPassword());
            clickButtonPay();
            WebDriverUtils.waitForElement(DROPDOWN_CURRENCY_XP);
        }
        clickDropdown();
        WebDriverUtils.waitFor();
        if(getRUBIntegerAmount()>getRUBIntegerBalance()){
            AbstractTest.skipTest("Not enough money on card balance. ");
        }
        clickRUB();
//        WebDriverUtils.waitForElement(PROVIDER_COMMISION_XP);
        WebDriverUtils.waitForElement(PAY_BUTTON);
        clickButtonPay();
        return new TransactionSuccessfulPopup();
    }

    public TransactionUnSuccessfulPopup payInvalid() {
        clickButtonPay();
        WebDriverUtils.waitForElement(PASSWORD_XP);
        fillPassword(PASSWORD_INCORRECT);
        clickButtonPay();
        return new TransactionUnSuccessfulPopup();
    }

    private String getPhone(){
        return WebDriverUtils.getAttribute(PHONE_XP, "value").replace("+7", "");
    }

    private String getAmount(){
        return WebDriverUtils.getElementText(AMOUNT_XP).replace("RUB", "").trim();
    }

    private void clickButtonPay(){
        WebDriverUtils.click(PAY_BUTTON);
    }

    private void clickDropdown(){
        WebDriverUtils.click(DROPDOWN_CURRENCY_XP);
    }

    private void clickRUB(){
        WebDriverUtils.click(CURRENCY_RUB_XP);
    }

    private int getRUBIntegerBalance(){
        return Integer.parseInt(WebDriverUtils.getElementText(CURRENCY_RUB_XP).replace("С кошелька:", "").replace("RUB", "").replace(",", "").trim());
    }

    private int getRUBIntegerAmount(){
        return Integer.parseInt(getAmount().replace(",",""));
    }

    private void fillPassword(String password){
        WebDriverUtils.clearAndInputTextToField(PASSWORD_XP, password);
    }

    public void assertAccount(String account){
        AbstractTest.assertEquals(account, getPhone(), "Account");
    }

    public void assertAmount(String amount){
        AbstractTest.assertEquals(amount.replace(",","").replace(".",""), getAmount().replace(",","").replace(".",""), "Amount");
    }
}
