package enums;

public enum PaymentMethod {
    QIWI("QIWI via SC", "9260366832", "9379133149", "ABCD1234", "wallettest11"),
    PayPal("PayPal", "revita_1222083012_per@800pay.com","test@800pay.com","222249464", "222249464"),
    Visa("VISA", "4440","4448"),
    MasterCard("MasterCard", "5557",""),
    Discover("Discover", "", ""),
    PaySafeCard("PaySafeCard", "6364 0700 8000 4190", "", "", ""),
    MoneyBookers("playtech.mobile.mobile-cashier.method.moneybookers.title", "testmbcustomerger@yahoo.co.uk", "someshit@yahoo.co.uk", "euroeuro1", ""),
    Envoy("Envoy", "", "", "", ""),
    Neteller("NETeller", "458591047553", "450424149137", "411392", ""),
    WebMoney("WebMoney", "Z253744324569", "Z253744324568", "", "");

    PaymentMethod(String name, String account, String secondaryAccount, String password, String emailAccount){
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
