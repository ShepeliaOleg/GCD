package pageObjects.bonus;

import pageObjects.core.AbstractPortalPopup;

public class OkBonusPopup extends AbstractPortalPopup{

    public static final String BONUS_TITLE_XP=  TITLE_XP + "[contains(text(), 'Congratulations')]";

    public OkBonusPopup(){
		super(new String[]{BONUS_TITLE_XP, BUTTON_CLOSE_XP});
	}
}
