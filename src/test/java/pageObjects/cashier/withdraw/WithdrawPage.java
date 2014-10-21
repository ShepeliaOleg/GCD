package pageObjects.cashier.withdraw;

import enums.PaymentMethod;
import pageObjects.cashier.CashierPage;
import pageObjects.cashier.TransactionUnSuccessfulPopup;
import springConstructors.UserData;
import utils.WebDriverUtils;

public class WithdrawPage extends CashierPage {

    private static final String QIWI_NEW_USER_XP = "//*[contains(text(), 'You need to deposit via QIWI to withdraw')]";
    private static final String PAYPAL_NEW_USER_XP = "//*[contains(text(), 'You need to deposit via PayPal to withdraw')]";


    public WithdrawPage(){
        super();
    }

    public void withdrawSuccessful(PaymentMethod type, String amount){
        WithdrawConfirmationPopup withdrawConfirmationPopup = navigateToWithdrawConfirmationPopup(type, amount);
        withdrawConfirmationPopup.clickAccept();
        new WithdrawSuccessfulNotification();
    }

    public void withdrawAddingAccount(PaymentMethod type, String amount){
        addAccountByType(type);
        withdrawSuccessful(type, amount);
    }

    public void cancelWithdraw(PaymentMethod type, String amount){
        WithdrawConfirmationPopup withdrawConfirmationPopup = navigateToWithdrawConfirmationPopup(type, amount);
        withdrawConfirmationPopup.closePopup();
    }

    private WithdrawConfirmationPopup navigateToWithdrawConfirmationPopup(PaymentMethod method, String amount){
        processPaymentByType(method, amount);
        return new WithdrawConfirmationPopup();
    }

    public void assertWithdrawConfirmationPopupAndClose(PaymentMethod method, String amount){
        WithdrawConfirmationPopup withdrawConfirmationPopup = navigateToWithdrawConfirmationPopup(method, amount);
        withdrawConfirmationPopup.assertAccount(method.getAccount());
        withdrawConfirmationPopup.assertAmount(amount);
        withdrawConfirmationPopup.closePopup();
    }

    public void assertCardInterface(PaymentMethod paymentMethod){
        assertInterfaceByType(paymentMethod, new String[]{FIELD_AMOUNT_XP, FIELD_ACCOUNT_XP, FIELD_CVV_XP, FIELD_PROMO_CODE_XP});
    }

    public void withdrawExpired(PaymentMethod card, String amount) {
        processPaymentByType(card, amount, true);

    }

    /*PAYPAL*/

    public void assertPayPalInterface(){
        assertInterfaceByType(PaymentMethod.PayPal, new String[]{FIELD_AMOUNT_XP, FIELD_ACCOUNT_XP});
    }

    public boolean isPayPalNewUserNotificationPresent() {
        return WebDriverUtils.isVisible(PAYPAL_NEW_USER_XP);
    }


    /*QIWI*/

    public boolean isQIWINewUserNotificationPresent() {
        return WebDriverUtils.isVisible(QIWI_NEW_USER_XP);
    }

    public void assertQIWIInterface(){
        assertInterfaceByType(PaymentMethod.QIWI, new String[]{FIELD_AMOUNT_XP, FIELD_ACCOUNT_XP});
    }

    /*ENVOY*/

    public void assertEnvoyInterface(UserData userData){
        assertInterfaceByType(PaymentMethod.Envoy, new String[]{FIELD_AMOUNT_XP}, userData);
    }

    public TransactionUnSuccessfulPopup withdrawEnvoy(String amount){
        WithdrawConfirmationPopup withdrawConfirmationPopup = navigateToWithdrawConfirmationPopup(PaymentMethod.Envoy, amount);
        withdrawConfirmationPopup.clickAccept();
        new WithdrawEnvoyPage().cancel();
        return new TransactionUnSuccessfulPopup();
    }
}
