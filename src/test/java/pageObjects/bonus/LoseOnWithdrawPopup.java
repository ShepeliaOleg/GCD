package pageObjects.bonus;

import pageObjects.core.AbstractPortalPopup;

public class LoseOnWithdrawPopup extends AbstractPortalPopup {

	public static final String BONUS_TITLE_XP=  TITLE_XP + "[contains(text(), 'INFO')]";

	public LoseOnWithdrawPopup(){
		super(new String[]{BONUS_TITLE_XP, BUTTON_ACCEPT_XP, BUTTON_CLOSE_XP});
	}

}
