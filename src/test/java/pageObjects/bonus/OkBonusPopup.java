package pageObjects.bonus;

import pageObjects.core.AbstractPortalPopup;
import utils.WebDriverUtils;
import utils.core.WebDriverFactory;

public class OkBonusPopup extends AbstractPortalPopup{

    public static final String BONUS_TITLE_XP=  TITLE_XP + "[contains(text(), 'Congratulations')]";
    public static final String BONUS_TITLE_2_XP=  TITLE_XP + "[contains(text(), '(1/2)')]";
    public static final String BONUS_TITLE_3_XP=  TITLE_XP + "[contains(text(), '(1/3)')]";

    public OkBonusPopup(){
		super(new String[]{BONUS_TITLE_XP, BUTTON_CLOSE_XP});
	}

    public boolean twoPopUpsIsAppeared(){
        return WebDriverUtils.isVisible(WebDriverFactory.getPortalDriver(), BONUS_TITLE_2_XP, 10);
    }

    public boolean threePopUpsIsAppeared(){
        return WebDriverUtils.isVisible(WebDriverFactory.getPortalDriver(), BONUS_TITLE_3_XP, 5);
    }
}
