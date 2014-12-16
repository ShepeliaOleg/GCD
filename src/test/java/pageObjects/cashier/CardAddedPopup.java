package pageObjects.cashier;

import pageObjects.core.AbstractPortalPopup;

public class CardAddedPopup extends AbstractPortalPopup{

    public static final String MESSAGE_XP = ROOT_XP + TITLE_XP + "[contains(text(), 'Card is added')]";

    public CardAddedPopup(){
        super(new String[] {MESSAGE_XP});
    }
}
