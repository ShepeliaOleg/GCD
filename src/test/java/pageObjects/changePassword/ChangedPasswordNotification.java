package pageObjects.changePassword;

import pageObjects.core.AbstractNotification;
import utils.WebDriverUtils;

public class ChangedPasswordNotification extends AbstractNotification{
    public static final String ROOT_XP =            "//*[contains(@class, 'popup_type_info ') or @class='info__content']";
	public final static String BUTTON_OK_XP=				"//*[@title='ok']";
	private final static String LABEL_MESSAGE_SUCCESS_XP=	"//*[contains(text(),'Password Changed')]";

	public ChangedPasswordNotification(){

		super(new String[]{LABEL_MESSAGE_SUCCESS_XP});
		//super(new String[]{ROOT_XP});
	}

	public ChangedPasswordNotification(String locator){

		super(new String[]{locator});
	}

	public boolean successfulMessageAppeared(){
		return WebDriverUtils.isVisible(LABEL_MESSAGE_SUCCESS_XP);
	}
}

