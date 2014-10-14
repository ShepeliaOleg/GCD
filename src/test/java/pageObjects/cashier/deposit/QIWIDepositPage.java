package pageObjects.cashier.deposit;

import pageObjects.cashier.TransactionSuccessfulPopup;
import pageObjects.cashier.TransactionUnSuccessfulPopup;
import pageObjects.core.AbstractPage;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

public class QIWIDepositPage extends AbstractPage{

    private static final String ROOT_XP = "//*[@class='paymentContent']";
    private static final String PHONE_XP = "*//[@id='phone']";
    private static final String AMOUNT_XP = "//*[@class='payment-description_cnt_fields_i'][2]/strong";
    private static final String PAY_BUTTON = "//*[@class='orangeBtn']";
    private static final String PASSWORD_XP = "//*[@name='password']";
    private static final String PROVIDER_COMMISION_XP = "//*[contains(@class, 'providerComm')]";
    private final String ACCOUNT;
    private final String AMOUNT;

    private static final String PASSWORD = "ABCD1234";
    private static final String PASSWORD_INCORRECT = "incorrect";

    public QIWIDepositPage(String account, String amount){
        super(new String[]{ROOT_XP});
        ACCOUNT = account;
        AMOUNT = amount;
    }

    public TransactionSuccessfulPopup pay(){
        clickButtonPay();
        WebDriverUtils.waitForElement(PASSWORD_XP);
        fillPassword(PASSWORD);
        clickButtonPay();
        WebDriverUtils.waitForElement(PROVIDER_COMMISION_XP);
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
        return WebDriverUtils.getElementText(PHONE_XP).replace("+7", "");
    }

    private String getAmount(){
        return WebDriverUtils.getElementText(AMOUNT_XP).replace("RUB", "").trim();
    }

    private void clickButtonPay(){
        WebDriverUtils.click(PAY_BUTTON);
    }

    private void fillPassword(String password){
        WebDriverUtils.clearAndInputTextToField(PASSWORD_XP, password);
    }

    public void assertAccount(){
        AbstractTest.assertEquals(ACCOUNT, getPhone(), "Account");
    }

    public void assertAmount(){
        AbstractTest.assertEquals(AMOUNT, getAmount(), "Amount");
    }
}
