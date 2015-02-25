package pageObjects.cashier.withdraw;

import pageObjects.core.AbstractNotification;

public class PendingWithdrawCancelledNotification extends AbstractNotification{

    public PendingWithdrawCancelledNotification(){
        super("Transaction was canceled");
    }
}
