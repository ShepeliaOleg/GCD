package springConstructors;

public class BonusData {

    private String bonusID;
    //private String bonusAmount;
    private Float bonusAmount;
    private String getBonusButtonTitle;
    private String otherInfo;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    private String currency;

    public String getBonusID() {
        return bonusID;
    }

    public void setBonusID(String bonusID) {
        this.bonusID = bonusID;
    }

    public Float getBonusAmount() {
        return bonusAmount;
    }

    public void setBonusAmount(Float bonusAmount) {
        this.bonusAmount = bonusAmount;
    }

    /*public String getBonusAmount() {
        return bonusAmount;
    }

    public void setBonusAmount(String bonusAmount) {
        this.bonusAmount = bonusAmount;
    }*/

    public String getGetBonusButtonTitle() {
        return getBonusButtonTitle;
    }

    public void setGetBonusButtonTitle(String getBonusButtonTitle) {
        this.getBonusButtonTitle = getBonusButtonTitle;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }
}
