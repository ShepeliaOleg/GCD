package enums;

public enum PaymentMethod {
    QIWI("QIWI via Safecharge", "9260366832", "9852887726", "ABCD1234", "wallettest11", ""),
    PayPal("playtech.mobile.mobile-cashier.method.paypal.title", "revita_1222083012_per@800pay.com","","222249464", "", "");

    PaymentMethod(String name, String account, String secondaryAccount, String password, String secondaryPassword, String promoCode){
        this.name = name;
        this.account = account;
        this.secondaryAccount = secondaryAccount;
        this.password = password;
        this.promoCode = promoCode;
    }

    private String name;
    private String account;
    private String secondaryAccount;
    private String secondaryPassword;
    private String password;
    private String promoCode;

    public String getPassword() {
        return password;
    }

    public String getAccount() {
        return account;
    }

    public String getSecondaryAccount() {
        return secondaryAccount;
    }

    public String getSecondaryPassword() {
        return secondaryPassword;
    }

    public String getName() {
        return name;
    }

    public String getPromoCode() {
        return promoCode;
    }

}
