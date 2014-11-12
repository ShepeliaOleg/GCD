package pageObjects.bonus;

import pageObjects.core.AbstractPortalPopup;
import utils.WebDriverUtils;

public class BuyInPopup extends AbstractPortalPopup{

    private static final String BUTTON_BUY_IN = ROOT_XP+"//a[@data-action='BUYIN']";
    private static final String BUTTON_TNC = ROOT_XP+"//a[contains(@class, 'termsAndConditionsLink')]";

    public BuyInPopup(){
        super(new String[]{BUTTON_BUY_IN});
    }

    public BonusBuyInPopup clickBuyIn(){
        WebDriverUtils.click(BUTTON_BUY_IN);
        return new BonusBuyInPopup();
    }

    public void clickTNC(){
        WebDriverUtils.click(BUTTON_TNC);
    }
}
