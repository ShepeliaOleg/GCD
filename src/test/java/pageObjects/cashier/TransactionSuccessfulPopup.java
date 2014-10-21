package pageObjects.cashier;

import pageObjects.core.AbstractPopup;

public class TransactionSuccessfulPopup extends AbstractPopup {

    private static final String ROOT_XP = "//*[contains(@class, 'bonus-popup')]";

    public TransactionSuccessfulPopup(){
        super(new String[] {ROOT_XP});
    }
}
