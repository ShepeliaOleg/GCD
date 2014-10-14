package pageObjects.cashier.deposit;

import pageObjects.cashier.CashierPage;

public class DepositPage extends CashierPage{

    public DepositPage(){
        super();
    }

    public MoneyBookersDepositPage depositMoneybookers(String amount, String promoCode){
        processPaymentByType(MONEYBOOKERS, amount, promoCode, null, null);
        return new MoneyBookersDepositPage();
    }

    public PayPalDepositPage depositPayPal(String amount, String promoCode){
        processPaymentByType(PAYPAL, amount, promoCode, null, null);
        return new PayPalDepositPage();
    }

    public WebMoneyDepositPage depositWebmoney(String amount, String promoCode){
        processPaymentByType(WEBMONEY, amount, promoCode, null, null);
        return new WebMoneyDepositPage();
    }

    public NETellerDepositPage depositNETeller(String amount, String promoCode, String account, String password){
        processPaymentByType(NETELLER, amount, promoCode, account, password);
        return new NETellerDepositPage();
    }

    public QIWIDepositPage depositQIWI(String amount, String promoCode){
        processPaymentByType(QIWI, amount, promoCode, QIWI_ACCOUNT, null);
        return new QIWIDepositPage(QIWI_ACCOUNT, amount);
    }

    public QIWIDepositPage depositQIWI(String amount){
        processPaymentByType(QIWI, amount, null, QIWI_ACCOUNT, null);
        return new QIWIDepositPage(QIWI_ACCOUNT, amount);
    }
}
