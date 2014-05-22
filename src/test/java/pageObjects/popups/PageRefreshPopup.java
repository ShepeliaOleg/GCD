package pageObjects.popups;

import pageObjects.base.AbstractPopup;
import utils.WebDriverUtils;

/**
 * Created by sergiich on 5/20/14.
 */
public class PageRefreshPopup extends AbstractPopup{

    private final static String BUTTON_CLOSE=ROOT_XP+"//*[@data-action='close']";
    private final static String LABEL_TEXT = ROOT_XP+"//*[@class='message-area']";

    public PageRefreshPopup(){
        super(new String[]{BUTTON_CLOSE, LABEL_TEXT});
    }

    public void clickClose(){
        WebDriverUtils.click(BUTTON_CLOSE);
        WebDriverUtils.waitForElementToDisappear(AbstractPopup.ROOT_XP);
    }
}
