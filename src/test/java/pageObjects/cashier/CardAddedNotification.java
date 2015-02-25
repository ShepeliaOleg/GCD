package pageObjects.cashier;

import pageObjects.core.AbstractNotification;

public class CardAddedNotification extends AbstractNotification {
    public static final String MESSAGE_XP = ROOT_XP + "[contains(text(), 'Card is added')]";

    public CardAddedNotification(){
        super(new String[] {MESSAGE_XP});
    }
}
