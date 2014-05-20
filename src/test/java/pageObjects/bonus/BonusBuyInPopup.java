package pageObjects.bonus;

import pageObjects.base.AbstractPopup;
import pageObjects.popups.PageRefreshPopup;
import utils.WebDriverUtils;

/**
 * Created by sergiich on 5/20/14.
 */
public class BonusBuyInPopup extends AbstractPopup{

    private static final String BUTTON_BUY_IN = ROOT_XP+"//button[@name='buyInSubmit']";
    private static final String BUTTON_CANCEL = ROOT_XP+"//a[contains(@class, 'jsCancelButton')]";
    private static final String CHECKBOX_TNC = ROOT_XP+"//input[@name='termsAndConditions']";
    private static final String FIELD_BUY_IN_AMOUNT = ROOT_XP+"//input[@name='buyInAmount']";

    public BonusBuyInPopup(){
        super(new String[]{BUTTON_BUY_IN, BUTTON_CANCEL, CHECKBOX_TNC});
    }

    private OkBonusPopup clickBuyIn(){
        WebDriverUtils.click(BUTTON_BUY_IN);
        return new OkBonusPopup();
    }

    private void clickCancel(){
        WebDriverUtils.click(BUTTON_CANCEL);
    }

    private void checkTNC(){
        WebDriverUtils.click(CHECKBOX_TNC);
    }

    public void confirmBuyIn(){
        checkTNC();
        clickBuyIn().close();
        new PageRefreshPopup().clickClose();
    }
}
