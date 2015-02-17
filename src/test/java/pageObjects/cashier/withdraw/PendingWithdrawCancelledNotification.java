package pageObjects.cashier.withdraw;

import pageObjects.core.AbstractNotification;

public class PendingWithdrawCancelledNotification extends AbstractNotification{

    public static final String MESSAGE_XP = ROOT_XP + "[contains(text(), 'Transaction was canceled')]";

    public PendingWithdrawCancelledNotification(){
        super(new String[] {MESSAGE_XP});
    }
}
