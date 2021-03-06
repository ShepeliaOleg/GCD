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
        super(new String[]{ROOT_XP, BUTTON_ADD_CARD_XP.getXpath(), METHOD_HEADER_BASE_XP});
    }

    public void withdrawSuccessful(PaymentMethod type, String amount){
        withdrawSuccessful(type, amount, type.getAccount());
    }

    public void withdrawSuccessful(PaymentMethod type, String amount, String account){
        withdraw(type, amount, account);
//        AUTOFREE bonus should not be 'lose on withdraw'
//        if (WebDriverUtils.isVisible(LoseOnWithdrawPopup.BONUS_TITLE_XP, 0)) {
//            LoseOnWithdrawPopup loseOnWithdrawPopup = new LoseOnWithdrawPopup();
//            loseOnWithdrawPopup.clickAccept();
//        }
        new WithdrawSuccessfulNotification();
    }

    public void withdraw(PaymentMethod type, String amount){
        withdraw(type, amount, type.getAccount());
    }

    private void withdraw(PaymentMethod type, String amount, String account) {
        WithdrawConfirmationPopup withdrawConfirmationPopup = navigateToWithdrawConfirmationPopup(type, amount, account);
        withdrawConfirmationPopup.assertAccount(account);
        withdrawConfirmationPopup.assertAmount(amount);
        withdrawConfirmationPopup.clickAccept();
    }

    public void withdrawAddingAccount(PaymentMethod type, String amount, String account){
        addAccountByType(type);
        withdrawSuccessful(type, amount, account);
    }

    public void withdrawAddingAccount(PaymentMethod type, String amount){
        withdraw(type, amount, type.getSecondaryAccount());
    }

    public void cancelWithdraw(PaymentMethod type, String amount){
        WithdrawConfirmationPopup withdrawConfirmationPopup = navigateToWithdrawConfirmationPopup(type, amount);
        withdrawConfirmationPopup.closePopup();
    }

    private WithdrawConfirmationPopup navigateToWithdrawConfirmationPopup(PaymentMethod method, String amount){
        return navigateToWithdrawConfirmationPopup(method, amount, method.getAccount());
    }

    private WithdrawConfirmationPopup navigateToWithdrawConfirmationPopup(PaymentMethod method, String amount, String account){
        processPaymentByType(method, amount, account);
        return new WithdrawConfirmationPopup();
    }

    public void withdrawConfirmationPopupClose(PaymentMethod method, String amount){
        WithdrawConfirmationPopup withdrawConfirmationPopup = navigateToWithdrawConfirmationPopup(method, amount);
        withdrawConfirmationPopup.closePopup();
    }

    public void assertCardInterface(PaymentMethod paymentMethod){
        assertInterfaceByType(paymentMethod, new String[]{FIELD_AMOUNT_XP, FIELD_ACCOUNT_KNOWN_XP});
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

    public void assertQIWIInterface(){
        assertInterfaceByType(PaymentMethod.QIWI, new String[]{FIELD_AMOUNT_XP, FIELD_ACCOUNT_KNOWN_XP});
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

    /*MONEYBOOKERS*/

    public void assertMoneyBookersInterface() {
        assertInterfaceByType(PaymentMethod.MoneyBookers, new String[]{FIELD_ACCOUNT_KNOWN_XP, FIELD_AMOUNT_XP});
    }

    /*NETELLER*/

    public void assertNetellerInterface(boolean newUser) {
        String[] elements;
        if (newUser) {
            elements = new String[]{FIELD_AMOUNT_XP, FIELD_ACCOUNT_XP};
        } else {
            elements = new String[]{FIELD_AMOUNT_XP, FIELD_ACCOUNT_KNOWN_XP};
        }
        assertInterfaceByType(PaymentMethod.Neteller, elements);
    }

    public void withdrawNetellerInvalidAccount(String amount) {
        processPaymentByType(PaymentMethod.Neteller, amount, "111122223333", PaymentMethod.Neteller.getPassword());
        new WithdrawConfirmationPopup().clickAccept();
        new TransactionUnSuccessfulPopup().closePopup();
    }

    public void withdrawNetellerInvalidEmail(String amount) {
        processPaymentByType(PaymentMethod.Neteller, amount, "test@playtech.com", PaymentMethod.Neteller.getPassword());
        new WithdrawConfirmationPopup().clickAccept();
        new TransactionUnSuccessfulPopup().closePopup();
    }

    /*WEBMONEY*/

    public void assertWebMoneyInterface() {
        assertInterfaceByType(PaymentMethod.WebMoney, new String[]{FIELD_AMOUNT_XP, FIELD_ACCOUNT_KNOWN_XP});
    }
}
