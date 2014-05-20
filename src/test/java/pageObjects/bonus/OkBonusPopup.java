package pageObjects.bonus;

import pageObjects.base.AbstractPopup;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 2/6/14
 */

public class OkBonusPopup extends AbstractPopup{

	public static final String BUTTON_OK= 	ROOT_XP + "//*[@class='ok']";
	public static final String LABEL_BONUS_TEXT = 	ROOT_XP + "//div[contains(text(), 'Congratulations, you have just received')]";

	public OkBonusPopup(){
		super(new String[]{LABEL_BONUS_TEXT});
	}

	public void close(){
		WebDriverUtils.click(BUTTON_OK);
	}
}
