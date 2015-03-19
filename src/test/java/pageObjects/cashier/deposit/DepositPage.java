package pageObjects.cashier.deposit;

import enums.PaymentMethod;
import enums.PromoCode;
import pageObjects.bonus.AcceptDeclineBonusPopup;
import pageObjects.cashier.CashierPage;
import pageObjects.cashier.TransactionSuccessfulPopup;
import pageObjects.cashier.TransactionUnSuccessfulPopup;
import springConstructors.UserData;
import utils.core.AbstractTest;

public class DepositPage extends CashierPage{

    public DepositPage(){
        super(new String[]{ROOT_XP, BUTTON_ADD_CARD_XP, METHOD_HEADER_BASE_XP});
    }

    public void assertCardInterface(PaymentMethod paymentMethod){
        assertInterfaceByType(paymentMethod, new String[]{FIELD_AMOUNT_XP, FIELD_ACCOUNT_KNOWN_XP, FIELD_CVV_XP, FIELD_PROMO_CODE_XP});
    }

    public void depositCard(PaymentMethod card, String amount){
        processPaymentByType(card, amount);
        TransactionSuccessfulPopup transactionSuccessfulPopup = new TransactionSuccessfulPopup();
        transactionSuccessfulPopup.closePopup();
    }

    public void depositCardValidPromoCode(PaymentMethod card, String amount){
        processPaymentByType(card, amount, PromoCode.valid);
        refresh(); //  D-18311
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = new AcceptDeclineBonusPopup();
        acceptDeclineBonusPopup.clickAccept();
//        TransactionSuccessfulPopup transactionSuccessfulPopup = new TransactionSuccessfulPopup(); // D-18311
//        transactionSuccessfulPopup.closePopup(); // D-18311
    }

    public void depositCardExpired(PaymentMethod card, String amount) {
        processPaymentByType(card, amount, true);
        AbstractTest.validateTrue(isPortletErrorVisible(), "Error visible");
        AbstractTest.assertTrue(getPortletErrorMessage().contains("Expired"), "Error text contains 'Expired'");
    }

    public DepositPage depositInvalidPromoCode(PaymentMethod paymentMethod, String amount) {
        processPaymentByType(paymentMethod, amount, PromoCode.invalid);
        return new DepositPage();
    }

    /*Paypal*/

    public void assertPayPalInterface(){
        assertInterfaceByType(PaymentMethod.PayPal, new String[]{FIELD_AMOUNT_XP, FIELD_PROMO_CODE_XP});
    }

    public PayPalDepositPage depositPayPal(String amount){
        processPaymentByType(PaymentMethod.PayPal, amount);
        return new PayPalDepositPage();
    }

    public PayPalDepositPage depositPayPalValidPromoCode(String amount){
        processPaymentByType(PaymentMethod.PayPal, amount, PromoCode.valid);
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

    public QIWIDepositPage depositQIWIValidPromoCode(String amount){
        processPaymentByType(PaymentMethod.QIWI, amount, PromoCode.valid);
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

    public PaySafeCardDepositPage depositPaySafeCardValidPromoCode(String amount) {
        processPaymentByType(PaymentMethod.PaySafeCard, amount, PromoCode.valid);
        return new PaySafeCardDepositPage();
    }

    /*ENVOY*/

    public EnvoyDepositPage depositEnvoy(String amount) {
        processPaymentByType(PaymentMethod.Envoy, amount);
        return new EnvoyDepositPage();
    }

    public EnvoyDepositPage depositEnvoyValidPromoCode(String amount) {
        processPaymentByType(PaymentMethod.Envoy, amount, PromoCode.valid);
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

    public MoneyBookersDepositPage depositMoneyBookersValidPromoCode(String amount) {
        processPaymentByType(PaymentMethod.MoneyBookers, amount, PromoCode.valid);
        return new MoneyBookersDepositPage();
    }

    /*NETELLER*/


    public void assertNetellerInterface(boolean newUser) {
        String[] elements;
        if (newUser) {
            elements = new String[]{FIELD_AMOUNT_XP, FIELD_ACCOUNT_XP, FIELD_PASSWORD_XP, FIELD_PROMO_CODE_XP};
        } else {
            elements = new String[]{FIELD_AMOUNT_XP, FIELD_ACCOUNT_KNOWN_XP, FIELD_PASSWORD_XP, FIELD_PROMO_CODE_XP};
        }
        assertInterfaceByType(PaymentMethod.Neteller, elements);
    }

    public TransactionSuccessfulPopup depositNetellerValidPromoCode(String amount) {
        processPaymentByType(PaymentMethod.Neteller, amount, PromoCode.valid);
        return new TransactionSuccessfulPopup();
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

    /*Webmoney*/

    public void assertWebMoneyInterface() {
        assertInterfaceByType(PaymentMethod.WebMoney, new String[]{FIELD_AMOUNT_XP, FIELD_PROMO_CODE_XP});
    }

    public WebMoneyDepositPage depositWebMoney(String amount) {
        processPaymentByType(PaymentMethod.WebMoney, amount);
        return new WebMoneyDepositPage();
    }

    /*PrePaidCards*/

    public void assertPrePaidcardInterface() {
        assertInterfaceByType(PaymentMethod.PrePaidCards, new String[]{FIELD_NUMBER_PREPAID_XP, FIELD_PASSWORD_XP, FIELD_PROMO_CODE_XP});
    }

    public void depositPrePaidCardWithEmptyFields() {
        processPrePaidCard("", "", "");
    }
}
