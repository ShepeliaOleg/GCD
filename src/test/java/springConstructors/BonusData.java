package springConstructors;

public class BonusData {

    private String bonusID;
    private String bonusAmount;
    private String GetFreeBonusButtonTitle;
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

    public String getGetFreeBonusButtonTitle() {
        return GetFreeBonusButtonTitle;
    }

    public void setGetFreeBonusButtonTitle(String getFreeBonusButtonTitle) {
        GetFreeBonusButtonTitle = getFreeBonusButtonTitle;
    }

    public String getLinksToTCbuttonTitle() {
        return LinksToTCbuttonTitle;
    }

    public void setLinksToTCbuttonTitle(String linksToTCbuttonTitle) {
        LinksToTCbuttonTitle = linksToTCbuttonTitle;
    }
}
