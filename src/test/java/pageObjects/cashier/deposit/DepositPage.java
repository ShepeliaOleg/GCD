package pageObjects.cashier.deposit;

import enums.PaymentMethod;
import pageObjects.cashier.CashierPage;
import pageObjects.cashier.TransactionSuccessfulPopup;
import pageObjects.cashier.TransactionUnSuccessfulPopup;
import springConstructors.UserData;
import utils.core.AbstractTest;

public class DepositPage extends CashierPage{

    public DepositPage(){
        super();
    }

    public void assertCardInterface(PaymentMethod paymentMethod){
        assertInterfaceByType(paymentMethod, new String[]{FIELD_AMOUNT_XP, DROPDOWN_ACCOUNT_XP, FIELD_CVV_XP, FIELD_PROMO_CODE_XP});
    }

    public void depositCard(PaymentMethod card, String amount){
        processPaymentByType(card, amount);
        TransactionSuccessfulPopup transactionSuccessfulPopup = new TransactionSuccessfulPopup();
        transactionSuccessfulPopup.closePopup();
    }

    public void depositCardExpired(PaymentMethod card, String amount) {
        processPaymentByType(card, amount, true);
        AbstractTest.validateTrue(isPortletErrorVisible(), "Error visible");
        AbstractTest.assertTrue(getPortletErrorMessage().contains("Expired"), "Error text contains 'Expired'");
    }

    /*Paypal*/

    public void assertPayPalInterface(){
        assertInterfaceByType(PaymentMethod.PayPal, new String[]{FIELD_AMOUNT_XP, FIELD_PROMO_CODE_XP});
    }

    public PayPalDepositPage depositPayPal(String amount){
        processPaymentByType(PaymentMethod.PayPal, amount);
        return new PayPalDepositPage();
    }

    /*QIWI*/

    public void assertQIWIInterface(){
        assertInterfaceByType(PaymentMethod.QIWI, new String[]{FIELD_AMOUNT_XP, FIELD_ACCOUNT_XP, FIELD_PROMO_CODE_XP});
    }

    public QIWIDepositPage depositQIWI(String amount){
        processPaymentByType(PaymentMethod.QIWI, amount);
        return new QIWIDepositPage();
    }

    /*PAYSAFECARD*/

    public void assertPaySafeCardInterface() {
        assertInterfaceByType(PaymentMethod.PaySafeCard, new String[]{FIELD_AMOUNT_XP, FIELD_PROMO_CODE_XP});
    }

    public PaySafeCardDepositPage depositPaySafeCard(String amount) {
        processPaymentByType(PaymentMethod.PaySafeCard, amount);
        return new PaySafeCardDepositPage();
    }

    /*ENVOY*/

    public EnvoyDepositPage depositEnvoy(String amount) {
        processPaymentByType(PaymentMethod.Envoy, amount);
        return new EnvoyDepositPage();
    }

    public void assertEnvoyInterface(UserData userData){
        assertInterfaceByType(PaymentMethod.Envoy, new String[]{FIELD_AMOUNT_XP, FIELD_PROMO_CODE_XP}, userData);
    }

    /*MONEYBOOKERS*/

    public void assertMoneyBookersInterface() {
        assertInterfaceByType(PaymentMethod.MoneyBookers, new String[]{FIELD_AMOUNT_XP, FIELD_PROMO_CODE_XP});
    }

    public MoneyBookersDepositPage depositMoneyBookers(String amount) {
        processPaymentByType(PaymentMethod.MoneyBookers, amount);
        return new MoneyBookersDepositPage();
    }

    /*NETELLER*/


    public void assertNetellerInterfaceExisting() {
        assertInterfaceByType(PaymentMethod.Neteller, new String[]{FIELD_AMOUNT_XP, DROPDOWN_ACCOUNT_XP, FIELD_PASSWORD_CODE_XP, FIELD_PROMO_CODE_XP});
    }

    public void assertNetellerInterfaceNew() {
        assertInterfaceByType(PaymentMethod.Neteller, new String[]{FIELD_AMOUNT_XP, FIELD_ACCOUNT_XP, FIELD_PASSWORD_CODE_XP, FIELD_PROMO_CODE_XP});
    }

    public TransactionSuccessfulPopup depositNeteller(String amount) {
        processPaymentByType(PaymentMethod.Neteller, amount);
        return new TransactionSuccessfulPopup();
    }

    public void depositNetellerInvalidAccount(String amount) {
        processPaymentByType(PaymentMethod.Neteller, amount, "111144443333", PaymentMethod.Neteller.getPassword());
        new TransactionUnSuccessfulPopup().closePopup();
    }

    public void depositNetellerInvalidPassword(String amount) {
        processPaymentByType(PaymentMethod.Neteller, amount, PaymentMethod.Neteller.getAccount(), "111222");
        new TransactionUnSuccessfulPopup().closePopup();
    }
}
