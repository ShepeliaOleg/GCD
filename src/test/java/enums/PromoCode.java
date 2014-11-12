package enums;

public enum PromoCode {
    valid("FREE"),
    invalid("HELL");

    private String code;
    private static final String AMOUNT = "10.00";

    PromoCode(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getAmount() {
        return AMOUNT;
    }
}
