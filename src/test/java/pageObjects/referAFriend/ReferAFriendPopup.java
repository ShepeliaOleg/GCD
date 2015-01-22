package pageObjects.referAFriend;

import enums.ConfiguredPages;
import enums.PlayerCondition;
import pageObjects.HomePage;
import pageObjects.core.AbstractPortalPopup;
import springConstructors.ValidationRule;
import utils.NavigationUtils;
import utils.WebDriverUtils;
import utils.validation.ValidationUtils;

public class ReferAFriendPopup extends AbstractPortalPopup {
	private static final String REFER_A_FRIEND_ROOT_XP=	ROOT_XP + "//*[contains(@class,'refer-a-friend')]";
    private static final String FIELD_EMAIL_XP=		    ROOT_XP + "//*[@name='email']";
    private static final String BUTTON_CONFIRM_XP=		ROOT_XP + "//*[@class='popup-modal__button fn-invite']";
	private static final String EMAIL_SENT_MSG_XP=		ROOT_XP + "//*[@class='info__content']";
    private static final String ERROR_XP=               ROOT_XP + "//*[contains(@class, 'message error')]";
	private static final String POPUP_TITLE_XP=			ROOT_XP + "//*[@class='popup-modal__title']";
	private static final String FIELD_EMAIL_TOOLTIP_ID = "friendEmail";


	public ReferAFriendPopup(){
		super(new String[]{REFER_A_FRIEND_ROOT_XP, FIELD_EMAIL_XP, BUTTON_CONFIRM_XP, BUTTON_CLOSE_XP});
	}

    public void fillRecipientInfo(String email) {
        WebDriverUtils.clearAndInputTextToField(FIELD_EMAIL_XP, email);
    }

	public void clickSend(){
		WebDriverUtils.click(BUTTON_CONFIRM_XP);
	}

	public boolean isEmailSent() {
		return WebDriverUtils.isVisible(EMAIL_SENT_MSG_XP);
	}

	public void validateEmail(ValidationRule emailValidationRule) {
		ValidationUtils.validateField(FIELD_EMAIL_XP, emailValidationRule, FIELD_EMAIL_TOOLTIP_ID);
	}

	public String popupTitleText(){
		return WebDriverUtils.getElementText(POPUP_TITLE_XP);
	}

	public boolean isErrorMessageVisible (){
		return WebDriverUtils.isVisible(ERROR_XP);
	}
}
