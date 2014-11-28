package pageObjects.account;

import pageObjects.core.AbstractNotification;

public class WithdrawLoadMoreNotificationPopup extends AbstractNotification {
    public static final String MESSAGE_XP = ROOT_XP + "[contains(text(), 'Successful withdraw')]";

    public WithdrawLoadMoreNotificationPopup(){
        super(new String[] {MESSAGE_XP});
    }
}
