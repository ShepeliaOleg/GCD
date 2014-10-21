package pageObjects.cashier.withdraw;

import pageObjects.core.AbstractNotification;

public class WithdrawSuccessfulNotification extends AbstractNotification{

    public static final String MESSAGE_XP = ROOT_XP + "[contains(text(), 'Successful withdraw')]";

    public WithdrawSuccessfulNotification(){
        super(new String[] {MESSAGE_XP});
    }
}
