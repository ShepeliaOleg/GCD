package pageObjects.bonus;

import pageObjects.core.AbstractPortalPopup;
import utils.WebDriverUtils;
import utils.core.WebDriverFactory;

import static utils.core.AbstractTest.assertEquals;
import static utils.core.AbstractTest.assertTrue;

public class OkBonusPopup extends AbstractPortalPopup{

    public static final String BONUS_TITLE_XP=  TITLE_XP + "[contains(text(), 'Congratulations')]";
    public static final String BONUS_TITLE_2_XP=  TITLE_XP + "[contains(text(), '(1/2)')]";
    public static final String BONUS_TITLE_3_XP=  TITLE_XP + "[contains(text(), '(1/3)')]";

    public OkBonusPopup(){
		//super(new String[]{BONUS_TITLE_XP, BUTTON_CLOSE_XP});
		super(new String[]{ROOT_XP, BUTTON_CLOSE_XP});
	}
//
//    public OkBonusPopup(String popupText){
//        super(new String[]{TITLE_XP, CONTENT_XP + "/div[contains(text(), '" + popupText + "')]", BUTTON_CLOSE_XP});
//    }

    public void checkPopupTitleText(String text) {
        assertEquals(text, WebDriverUtils.getElementText(TITLE_XP), "Pop Up title was not as expected");
    }

    public void checkPopupContentText(String text) {
        assertTrue(WebDriverUtils.getElementText(CONTENT_XP + "/div").contains(text), "Pop Up does not contain the expected text");
        //assertEquals(text, WebDriverUtils.getElementText(CONTENT_XP + "/div"), "Pop Up does not contain the expected text");
    }

    public boolean twoPopUpsIsAppeared(){
        return WebDriverUtils.isVisible(WebDriverFactory.getPortalDriver(), BONUS_TITLE_2_XP, 10);
    }

    public boolean threePopUpsIsAppeared(){
        return WebDriverUtils.isVisible(WebDriverFactory.getPortalDriver(), BONUS_TITLE_3_XP, 5);
    }
}
