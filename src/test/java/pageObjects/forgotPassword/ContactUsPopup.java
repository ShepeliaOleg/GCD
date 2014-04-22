package pageObjects.forgotPassword;

import pageObjects.base.AbstractPopup;
import utils.WebDriverUtils;

/*
 * User: ivanva
 * Date: 4/19/13
 */

public class ContactUsPopup extends AbstractPopup{
	private static final String LABEL_PHONE_XP=	ROOT_XP + "//*[@class='phone']";
	private static final String LABEL_EMAIL_XP=	ROOT_XP + "//*[@class='email']";
	private static final String LABEL_CHAT_XP=	ROOT_XP + "//*[@class='chat']";
	public final static String CLOSE_XP=		ROOT_XP + "//div[h2[not(contains(text(), 'Login'))]]//a[@class='popupClose']";

	public ContactUsPopup(){
		super(new String[]{LABEL_CHAT_XP});
	}

	public void closePopup(){
		WebDriverUtils.click(CLOSE_XP);
	}

}
