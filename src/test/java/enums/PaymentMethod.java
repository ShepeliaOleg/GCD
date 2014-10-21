package enums;

public enum PaymentMethod {
    QIWI("QIWI via Safecharge", "9260366832", "9852887726", "ABCD1234", "wallettest11"),
    PayPal("playtech.mobile.mobile-cashier.method.paypal.title", "revita_1222083012_per@800pay.com","test@800pay.com","222249464", "222249464"),
    Visa("VISA", "4440","4448"),
    MasterCard("MasterCard", "5557",""),
    Discover("Discover", "", ""),
    PaySafeCard("playtech.mobile.mobile-cashier.deposit.paysafecard.title", "6364 0700 8000 4190", "", "", ""),
    Envoy("playtech.mobile.mobile-cashier.deposit.envoy.title", "", "", "", "");

    PaymentMethod(String name, String account, String secondaryAccount, String password, String secondaryPassword){
        this.name = name;
        this.account = account;
        this.secondaryAccount = secondaryAccount;
        this.password = password;
    }

    PaymentMethod(String name, String valid, String expired){
        this.name = name;
        this.account = valid;
        this.secondaryAccount = expired;
        this.password = null;
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
