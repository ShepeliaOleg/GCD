package pageObjects.menu;

import pageObjects.account.ChangeMyDetailsPage;
import pageObjects.changePassword.ChangePasswordPopup;
import pageObjects.login.LogoutPopup;
import pageObjects.referAFriend.ReferAFriendPopup;
import pageObjects.responsibleGaming.ResponsibleGamingPage;
import utils.WebDriverUtils;

public class LoggedInMenu extends Menu {

    protected static final String BUTTON_LOGOUT_XP =        ROOT_XP + "//*[contains(@class,'fn-logout')]";
    private static final String REFER_A_FRIEND_XP =         ROOT_XP + "//*[preceding-sibling::*[contains(@class,'micon-refer-friend')]]";
    private static final String MY_BALANCE_XP =             ROOT_XP + "//*[preceding-sibling::*[contains(@class,'micon-my-balance')]]";
    private static final String UPDATE_MY_DETAILS_XP =      ROOT_XP + "//*[preceding-sibling::*[contains(@class,'micon-update-details')]]";
    private static final String DEPOSIT_XP =                ROOT_XP + "//*[preceding-sibling::*[contains(@class,'micon-deposit')]]";
    private static final String WITHDRAW_XP =               ROOT_XP + "//*[preceding-sibling::*[contains(@class,'micon-withdraw')]]";
    private static final String PENDING_WITHDRAW_XP =       ROOT_XP + "//*[preceding-sibling::*[contains(@class,'micon-my-balance')]]";
    private static final String TRANSACTION_HISTORY_XP =    ROOT_XP + "//*[preceding-sibling::*[contains(@class,'micon-pending-transactions')]]";
    private static final String RESPONSIBLE_GAMING_XP =     ROOT_XP + "//*[preceding-sibling::*[contains(@class,'micon-responsible-gaming')]]";
    private static final String SELF_EXCLUSION_XP =         ROOT_XP + "//*[preceding-sibling::*[contains(@class,'micon-self-exclusion')]]";
    private static final String CHANGE_PASSWORD_XP =        ROOT_XP + "//*[preceding-sibling::*[contains(@class,'micon-password')]]";
    private static final String HIDE_BALANCE_XP =           ROOT_XP + "//*[preceding-sibling::*[contains(@class,'micon-balance')]]";

	public LoggedInMenu(){
		super(new String[]{REFER_A_FRIEND_XP, MY_BALANCE_XP, UPDATE_MY_DETAILS_XP, DEPOSIT_XP, WITHDRAW_XP, PENDING_WITHDRAW_XP, TRANSACTION_HISTORY_XP, RESPONSIBLE_GAMING_XP, /*SELF_EXCLUSION_XP,*/ CHANGE_PASSWORD_XP, HIDE_BALANCE_XP});
	}

    public LogoutPopup clickLogout(){
        WebDriverUtils.click(BUTTON_LOGOUT_XP);
        return new LogoutPopup();
    }

    public ReferAFriendPopup navigateToReferAFriend(){
        WebDriverUtils.click(REFER_A_FRIEND_XP);
        return new ReferAFriendPopup();
    }

    public ChangeMyDetailsPage navigateToUpdateMyDetails(){
        WebDriverUtils.click(UPDATE_MY_DETAILS_XP);
        return new ChangeMyDetailsPage();
    }

    public ResponsibleGamingPage navigateToResponsibleGaming(){
        WebDriverUtils.click(RESPONSIBLE_GAMING_XP);
        return new ResponsibleGamingPage();
    }

    public ChangePasswordPopup navigateToChangePassword(){
        WebDriverUtils.click(CHANGE_PASSWORD_XP);
        return new ChangePasswordPopup();
    }
}
