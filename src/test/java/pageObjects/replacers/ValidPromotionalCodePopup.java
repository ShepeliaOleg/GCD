package pageObjects.replacers;

import pageObjects.core.AbstractPortalPopup;
import utils.WebDriverUtils;

public class ValidPromotionalCodePopup extends AbstractPortalPopup {
    private static final String MESSAGE_XP = "//*[@class='fn-popup-loader']";
    private static final String TITLE_XP = "//*[@class='popup-modal__title']";
    public static final String BONUS_GIVEN_MESSAGE_TXT = "Congratulations, you just received a $ 7.00 bonus! Wishing you the best of luck in our games!";

    public static final String TITLE_TK = "playtech.mobile.system.message.congratulations";


    public ValidPromotionalCodePopup() {
        super(new String[]{ROOT_XP, BUTTON_CLOSE_XP.getXpath()});
    }

    public String getMessage() {
        return WebDriverUtils.getElementText(MESSAGE_XP);
    }

    public String getTitleText() {
        return WebDriverUtils.getElementText(TITLE_XP);
    }
}
