package springConstructors;

public class BonusData {

    private String bonusID;
    private String bonusAmount;
    private String getBonusButtonTitle;
    private String LinksToTCbuttonTitle;

    public String getBonusID() {
        return bonusID;
    }

    public void setBonusID(String bonusID) {
        this.bonusID = bonusID;
    }

    public String getBonusAmount() {
        return bonusAmount;
    }

    public void setBonusAmount(String bonusAmount) {
        this.bonusAmount = bonusAmount;
    }

    public String getGetBonusButtonTitle() {
        return getBonusButtonTitle;
    }

    public void setGetBonusButtonTitle(String getBonusButtonTitle) {
        this.getBonusButtonTitle = getBonusButtonTitle;
    }

    public String getLinksToTCbuttonTitle() {
        return LinksToTCbuttonTitle;
    }

    public void setLinksToTCbuttonTitle(String linksToTCbuttonTitle) {
        LinksToTCbuttonTitle = linksToTCbuttonTitle;
    }
}
