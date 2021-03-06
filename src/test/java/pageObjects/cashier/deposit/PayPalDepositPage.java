package pageObjects.cashier.deposit;

import enums.PaymentMethod;
import pageObjects.bonus.OkBonusPopup;
import pageObjects.cashier.TransactionSuccessfulPopup;
import pageObjects.cashier.TransactionUnSuccessfulPopup;
import pageObjects.core.AbstractPortalPage;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

public class PayPalDepositPage extends AbstractPortalPage {

    private static final String AMOUNT_XP = "//*[@class='amount']";
    private static final String BUTTON_LOGIN_XP = "//*[@id='submitLogin']";
    private static final String FIELD_LOGIN_EMAIL_XP = "//*[@id='login_email']";
    private static final String FIELD_LOGIN_PASSWORD_XP = "//*[@id='login_password']";
    private static final String LINK_LOGIN_XP = "//*[@id='loadLogin']";
    private static final String BUTTON_CONTINUE_XP = "//*[@id='continue']";
    private static final String LINK_CANCEL = "//*[@name='cancel_return']";

    public PayPalDepositPage(){
        super(new String[]{AMOUNT_XP});
    }

    private String getAmount(){
        return WebDriverUtils.getElementText(AMOUNT_XP);
    }

    public void assertAmount(String amount){
        AbstractTest.assertTrue(getAmount().contains(amount), "Amount");
    }

    public TransactionSuccessfulPopup pay(String amount){
        return pay(amount, false);
    }
    public TransactionSuccessfulPopup pay(String amount, boolean withBonus){
        if(!WebDriverUtils.isVisible(FIELD_LOGIN_EMAIL_XP, 0)){
            WebDriverUtils.click(LINK_LOGIN_XP);
            WebDriverUtils.waitForElement(FIELD_LOGIN_EMAIL_XP, 30);
        }
        WebDriverUtils.clearAndInputTextToField(FIELD_LOGIN_EMAIL_XP, PaymentMethod.PayPal.getAccount());
        WebDriverUtils.clearAndInputTextToField(FIELD_LOGIN_PASSWORD_XP, PaymentMethod.PayPal.getPassword());
        WebDriverUtils.click(BUTTON_LOGIN_XP);
        WebDriverUtils.waitForElement(BUTTON_CONTINUE_XP, 30);
        WebDriverUtils.click(BUTTON_CONTINUE_XP);
        if (withBonus) {
            new OkBonusPopup().clickAccept();
        }
        return new TransactionSuccessfulPopup();
    }

    private void clickCancel(){
        WebDriverUtils.click(LINK_CANCEL);
    }

    public TransactionUnSuccessfulPopup cancelDeposit(){
        clickCancel();
        WebDriverUtils.waitForElementToDisappear(LINK_CANCEL, 30);
        return new TransactionUnSuccessfulPopup();
    }
}
