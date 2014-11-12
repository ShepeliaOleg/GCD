package pageObjects.cashier;

import pageObjects.core.AbstractPortalPopup;

public class TransactionUnSuccessfulPopup extends AbstractPortalPopup{

    private static final String ROOT_XP = "//*[contains(text(), 'Transaction was canceled') or contains(text(), 'transaction has been declined')]";

    public TransactionUnSuccessfulPopup(){
        super(new String[] {ROOT_XP});
    }
}
