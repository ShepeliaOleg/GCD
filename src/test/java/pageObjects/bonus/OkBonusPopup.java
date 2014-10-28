package pageObjects.bonus;

import pageObjects.core.AbstractPopup;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 2/6/14
 */

public class OkBonusPopup extends AbstractPopup{

	public static final String BUTTON_OK= 	ROOT_XP + "//*[@class='ok']";
    public static final String BONUS_TITLE_XP=  TITLE_XP + "[contains(text(), 'Congratulations')]";

    public OkBonusPopup(){
		super(new String[]{BONUS_TITLE_XP, BUTTON_OK});
	}

	public void close(){
		WebDriverUtils.click(BUTTON_OK);
	}
}
