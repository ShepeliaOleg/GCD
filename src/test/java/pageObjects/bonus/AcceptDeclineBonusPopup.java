package pageObjects.bonus;

import pageObjects.core.AbstractPopup;

/**
 * User: sergiich
 * Date: 2/6/14
 */

public class AcceptDeclineBonusPopup extends AbstractPopup{

	public static final String BONUS_TITLE_XP=  TITLE_XP + "[contains(text(), 'Congratulations')]";

	public AcceptDeclineBonusPopup(){
		super(new String[]{BONUS_TITLE_XP, BUTTON_ACCEPT_XP});
	}

}
