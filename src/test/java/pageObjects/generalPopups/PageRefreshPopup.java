package pageObjects.generalPopups;

import pageObjects.core.AbstractPortalPopup;
import utils.WebDriverUtils;

public class PageRefreshPopup extends AbstractPortalPopup {

    private final static String BUTTON_CLOSE=ROOT_XP+"//*[@data-action='close']";
    private final static String LABEL_TEXT = ROOT_XP+"//*[@class='message-area']";

    public PageRefreshPopup(){
        super(new String[]{BUTTON_CLOSE, LABEL_TEXT});
    }

    public void clickClose(){
        WebDriverUtils.click(BUTTON_CLOSE);
        WebDriverUtils.waitForElementToDisappear(AbstractPortalPopup.ROOT_XP);
    }
}
