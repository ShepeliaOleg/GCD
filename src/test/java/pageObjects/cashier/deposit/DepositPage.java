package pageObjects.cashier.deposit;

import enums.PaymentMethod;
import pageObjects.cashier.CashierPage;

public class DepositPage extends CashierPage{

    public DepositPage(){
        super();
    }

    /*Paypal*/

    public void assertPayPalInterface(){
        assertInterfaceByType(PaymentMethod.QIWI, new String[]{FIELD_AMOUNT_XP, FIELD_PROMO_CODE_XP});
    }

    public PayPalDepositPage depositPayPal(String amount){
        processPaymentByType(PaymentMethod.PayPal, amount);
        return new PayPalDepositPage();
    }

    /*QIWI*/

    public void assertQIWIInterface(){
        assertInterfaceByType(PaymentMethod.QIWI, new String[]{FIELD_AMOUNT_XP, FIELD_ACCOUNT_XP, FIELD_PROMO_CODE_XP});
    }

    public QIWIDepositPage depositQIWI(String amount){
        processPaymentByType(PaymentMethod.QIWI, amount);
        return new QIWIDepositPage();
    }
}
