package pageObjects.cashier.deposit;

import enums.PaymentMethod;
import pageObjects.cashier.TransactionSuccessfulPopup;
import pageObjects.cashier.TransactionUnSuccessfulPopup;
import pageObjects.core.AbstractPortalPage;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

public class MoneyBookersDepositPage extends AbstractPortalPage {

    private static final String ROOT_XP = "//*[contains(text(), 'Welcome back to Skrill')]";
    private static final String LINK_CANCEL_XP = "//*[@class='button_secondary']";
    private static final String BUTTON_LOGIN_XP = "//*[@class='button_inner']";
    private static final String BUTTON_BACK_XP = "//*[@id='payConfirm']";
    private static final String FIELD_EMAIL_XP = "//*[@id='email']";
    private static final String FIELD_PASSWORD_XP = "//*[@id='password']";
    private static final String LABEL_AMOUNT_XP = "//*[@id='header_summary']";

    public MoneyBookersDepositPage(){
        super(new String[]{ROOT_XP});
    }

    public TransactionUnSuccessfulPopup cancelDeposit(){
        WebDriverUtils.click(LINK_CANCEL_XP);
        WebDriverUtils.waitForElement(BUTTON_BACK_XP);
        WebDriverUtils.click(BUTTON_BACK_XP);
        return new TransactionUnSuccessfulPopup();
    }

    public TransactionSuccessfulPopup pay(){
        WebDriverUtils.inputTextToField(FIELD_PASSWORD_XP, PaymentMethod.MoneyBookers.getPassword());
        WebDriverUtils.click(BUTTON_LOGIN_XP);
        WebDriverUtils.waitForElement(BUTTON_BACK_XP);
        WebDriverUtils.click(BUTTON_BACK_XP);
        return new TransactionSuccessfulPopup();
    }

    private String getAmount(){
        String text = WebDriverUtils.getElementText(LABEL_AMOUNT_XP);
        return text.replace("Payment of ", "").substring(0, text.indexOf("EUR"));
    }

    public void assertAmount(String amount) {
        AbstractTest.assertEquals(amount, getAmount(), "Amount");
    }
}
