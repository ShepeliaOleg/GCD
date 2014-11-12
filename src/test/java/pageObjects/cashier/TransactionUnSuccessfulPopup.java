package pageObjects.cashier;

import pageObjects.core.AbstractPopup;

public class TransactionUnSuccessfulPopup extends AbstractPopup{

    private static final String ROOT_XP = "//*[contains(text(), 'Transaction was unsuccessfull') or contains(text(), 'transaction has been declined')]";

    public TransactionUnSuccessfulPopup(){
        super(new String[] {ROOT_XP});
    }
}
