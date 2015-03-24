package enums;

public enum PaymentMethod {
    QIWI("QIWIviaSC", "9379133149", "9379133149", "1234ABCD", "wallettest11"),
    PayPal("PayPal", "greesnm@aa.aa","test@800pay.com","rfhfylfi!", "222249464"), // revita_1222083012_per@800pay.com 222249464
//    Visa("Visa", "6980", "6376"),
//    MasterCard("MasterCard", "3295","1729"),
    Visa("Visa", "3177", "2254"),
    VisaLastUsedCC("Visa", "6773", "2927"),
    MasterCard("MasterCard", "4630","9817"),
    MasterCardLastUsedCC("MasterCard", "2996","6770"),
    Discover("Discover", "", ""),
    PaySafeCard("PaySafeCard", "6364 0700 8000 4190", "", "", ""),
    MoneyBookers("MoneyBookers", "olga.stepanova@playtech.com", "someshit@yahoo.co.uk", "Olga12345", ""),
    Envoy("Envoy", "", "", "", ""),
    Neteller("NETeller", "453501020503", "450424149137", "908379", "netellertest_EUR@neteller.com"),
    WebMoney("WebMoney", "Z253744324569", "Z253744324568", "", ""),
    PrePaidCards("Pre-paid Cards", "", "");

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
