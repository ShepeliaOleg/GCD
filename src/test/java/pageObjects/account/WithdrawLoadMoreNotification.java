package pageObjects.account;

import pageObjects.core.AbstractNotification;

public class WithdrawLoadMoreNotification extends AbstractNotification {
    public static final String MESSAGE_XP = ROOT_XP + "[contains(text(), 'Successful withdraw')]";

    public WithdrawLoadMoreNotification(){
        super(new String[] {MESSAGE_XP});
    }
}
