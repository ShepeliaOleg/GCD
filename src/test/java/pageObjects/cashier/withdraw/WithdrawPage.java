package pageObjects.cashier.withdraw;

import pageObjects.cashier.CashierPage;

public class WithdrawPage extends CashierPage {

    public WithdrawPage(){
        super();
    }

    public WithdrawSuccessfulPopup withdrawQIWI(String amount){
        WithdrawConfirmationPopup withdrawConfirmationPopup = navigateToWithdrawQIWIConfirmationPopup(amount);
        withdrawConfirmationPopup.clickAccept();
        return new WithdrawSuccessfulPopup();
    }

    public WithdrawSuccessfulPopup withdrawQIWIAddAccount(String amount){
        addQIWIAccount();
        return withdrawQIWI(amount);
    }
    public void addQIWIAccount(){
        addAccount(QIWI, QIWI_ACCOUNT_2);
    }

    public void closeQIWIAccount(){
        closeAddAccountField(QIWI, QIWI_ACCOUNT);
    }

    public WithdrawConfirmationPopup navigateToWithdrawQIWIConfirmationPopup(String amount){
        return navigateToWithdrawConfirmationPopup(QIWI, amount, null, null, null);
    }

    public void cancelWithdrawQIWI(String amount){
        WithdrawConfirmationPopup withdrawConfirmationPopup = navigateToWithdrawConfirmationPopup(QIWI, amount, null, null, null);
        withdrawConfirmationPopup.closePopup();
    }

    private WithdrawConfirmationPopup navigateToWithdrawConfirmationPopup(String method, String amount, String promoCode, String account, String password){
        processPaymentByType(method, amount, promoCode, account, password);
        return new WithdrawConfirmationPopup();
    }

    public void addAccount(String method, String account){
        addAccountByType(method, account);
    }
}
