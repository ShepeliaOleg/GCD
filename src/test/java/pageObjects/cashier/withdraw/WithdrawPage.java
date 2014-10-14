package pageObjects.cashier.withdraw;

import enums.PaymentMethod;
import pageObjects.cashier.CashierPage;
import utils.WebDriverUtils;

public class WithdrawPage extends CashierPage {

    private static final String QIWI_NEW_USER_XP = "//*[contains(text(), 'You need to deposit via QIWI to withdraw')]";
    private static final String PAYPAL_NEW_USER_XP = "//*[contains(text(), 'You need to deposit via PayPal to withdraw')]";


    public WithdrawPage(){
        super();
    }

    public WithdrawSuccessfulPopup withdraw(PaymentMethod type, String amount){
        WithdrawConfirmationPopup withdrawConfirmationPopup = navigateToWithdrawConfirmationPopup(type, amount);
        withdrawConfirmationPopup.clickAccept();
        return new WithdrawSuccessfulPopup();
    }

    public WithdrawSuccessfulPopup withdrawAddingAccount(PaymentMethod type, String amount){
        addAccountByType(type);
        return withdraw(type, amount);
    }

    public void cancelWithdraw(PaymentMethod type, String amount){
        WithdrawConfirmationPopup withdrawConfirmationPopup = navigateToWithdrawConfirmationPopup(type, amount);
        withdrawConfirmationPopup.closePopup();
    }

    public WithdrawConfirmationPopup navigateToWithdrawConfirmationPopup(PaymentMethod method, String amount){
        processPaymentByType(method, amount);
        return new WithdrawConfirmationPopup();
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
}
