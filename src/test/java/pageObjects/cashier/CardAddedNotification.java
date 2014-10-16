package pageObjects.cashier;

import pageObjects.core.AbstractNotification;
import utils.WebDriverUtils;

public class CardAddedNotification extends AbstractNotification{

    public static final String MESSAGE_XP = ROOT_XP + "[contains(text(), 'Card added')]";

    public CardAddedNotification(){
        super(new String[] {MESSAGE_XP});
    }
}
