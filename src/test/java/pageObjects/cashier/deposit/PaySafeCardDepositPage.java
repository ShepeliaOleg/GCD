package pageObjects.cashier.deposit;

import enums.PaymentMethod;
import pageObjects.cashier.TransactionSuccessfulPopup;
import pageObjects.cashier.TransactionUnSuccessfulPopup;
import pageObjects.core.AbstractPage;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

public class PaySafeCardDepositPage extends AbstractPage{

    private static final String LINK_CANCEL_XP = "//*[@id='pinForm:cancel']";
    private static final String BUTTON_PAY_XP = "//*[@id='pinForm:pay']";
    private static final String CHECKBOX_TERMS_XP = "//*[@id='pinForm:agb']";
    private static final String LABEL_AMOUNT_XP = "//*[@id='dispositionAmount']//span[2]";
    private static final String FIELD_PIN_1_XP = "//*[@id='pinForm:rn01']";
    private static final String FIELD_PIN_2_XP = "//*[@id='pinForm:rn02']";
    private static final String FIELD_PIN_3_XP = "//*[@id='pinForm:rn03']";
    private static final String FIELD_PIN_4_XP = "//*[@id='pinForm:rn04']";

    private void clickPay(){
        WebDriverUtils.click(BUTTON_PAY_XP);
    }

    private void clickCancel(){
        WebDriverUtils.click(LINK_CANCEL_XP);
    }

    private void setCheckboxTerms(boolean state){
        WebDriverUtils.setCheckBoxState(CHECKBOX_TERMS_XP, state);
    }

    private String getAmount(){
        return WebDriverUtils.getElementText(LABEL_AMOUNT_XP);
    }

    private void fillPin(String fullPin){
        String[] pin = fullPin.split(" ");
        WebDriverUtils.clearAndInputTextToField(FIELD_PIN_1_XP, pin[0]);
        WebDriverUtils.clearAndInputTextToField(FIELD_PIN_2_XP, pin[1]);
        WebDriverUtils.clearAndInputTextToField(FIELD_PIN_3_XP, pin[2]);
        WebDriverUtils.clearAndInputTextToField(FIELD_PIN_4_XP, pin[3]);
    }

    public TransactionSuccessfulPopup pay(){
        fillPin(PaymentMethod.PaySafeCard.getAccount());
        setCheckboxTerms(true);
        clickPay();
        return new TransactionSuccessfulPopup();
    }

    public TransactionUnSuccessfulPopup cancelDeposit(){
        clickCancel();
        WebDriverUtils.waitForElementToDisappear(LINK_CANCEL_XP);
        return new TransactionUnSuccessfulPopup();
    }

    public void assertAmount(String amount){
        AbstractTest.assertEquals(amount, getAmount(), "Amount");
    }

}
