package pageObjects.referAFriend;

import pageObjects.core.AbstractPortalPopup;
import utils.WebDriverUtils;

public class ReferAFriendPopup extends AbstractPortalPopup {
	private static final String REFER_A_FRIEND_ROOT_XP=	ROOT_XP + "//*[contains(@class,'refer-a-friend')]";
    private static final String FIELD_EMAIL_XP=		    ROOT_XP + "//*[@name='email']";
    public  static final String BUTTON_CONFIRM_XP=		ROOT_XP + "//*[contains(@class, 'fn-invite')]";

    private static final String ERROR_XP=               ROOT_XP + "//*[contains(@class, 'message error')]";


	public ReferAFriendPopup(){
		super(new String[]{REFER_A_FRIEND_ROOT_XP, FIELD_EMAIL_XP, BUTTON_CONFIRM_XP, BUTTON_CLOSE_XP});
	}

    private void fillRecipientInfo(String email) {
        WebDriverUtils.clearAndInputTextToField(FIELD_EMAIL_XP, email);
    }

	private void clickSend(){
		WebDriverUtils.click(BUTTON_CONFIRM_XP);
	}
}
