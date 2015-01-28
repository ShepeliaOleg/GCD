package enums;

public enum PaymentMethod {
    QIWI("QIWI via SC", "9260366832", "9379133149", "ABCD1234", "wallettest11"),
    PayPal("PayPal", "greesnm@aa.aa","test@800pay.com","rfhfylfi!", "222249464"), // revita_1222083012_per@800pay.com 222249464
    Visa("Visa", "6980", "6376"),
    MasterCard("MasterCard", "3295","1729"),
    Discover("Discover", "", ""),
    PaySafeCard("PaySafeCard", "6364 0700 8000 4190", "", "", ""),
    MoneyBookers("MoneyBookers", "testmbcustomerger@yahoo.co.uk", "someshit@yahoo.co.uk", "euroeuro1", ""),
    Envoy("Envoy", "", "", "", ""),
    Neteller("NETeller", "453501020503", "450424149137", "411392", "netellertest_EUR@neteller.com"),
    WebMoney("WebMoney", "Z253744324569", "Z253744324568", "", "");

    PaymentMethod(String name, String account, String secondaryAccount, String password, String emailAccount){
        this.name = name;
        this.account = account;
        this.secondaryAccount = secondaryAccount;
        this.password = password;
        this.email = emailAccount;
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
    private String email;

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

    public String getEmail() {
        return email;
    }
}
