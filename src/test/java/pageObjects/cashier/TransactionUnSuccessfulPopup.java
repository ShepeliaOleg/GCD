package pageObjects.cashier;

import pageObjects.core.AbstractPopup;

public class TransactionUnSuccessfulPopup extends AbstractPopup{

    private static final String ROOT_XP = "//*[contains(text(), 'Transaction was canceled')]";

    public TransactionUnSuccessfulPopup(){
        super(new String[] {ROOT_XP});
    }
}
