package pageObjects.bonus;

import pageObjects.core.AbstractPortalPopup;

public class AcceptDeclineBonusPopup extends AbstractPortalPopup {

	public static final String BONUS_TITLE_XP=  TITLE_XP + "[contains(text(), 'Congratulations')]";

	public AcceptDeclineBonusPopup(){
		super(new String[]{BONUS_TITLE_XP, BUTTON_ACCEPT_XP});
	}

}
