package pageObjects.cashier;

import pageObjects.core.AbstractPortalPopup;

public class TransactionSuccessfulPopup extends AbstractPortalPopup {

    private static final String ROOT_XP = "//*[contains(@class, 'bonus-popup') or contains(text(), 'Transaction was successful')]";

    public TransactionSuccessfulPopup(){
        super(new String[] {ROOT_XP});
    }
}
