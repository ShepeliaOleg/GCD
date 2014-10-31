package pageObjects.menu;

import changeMyDetails.ChangeMyDetailsPage;
import pageObjects.cashier.deposit.DepositPage;
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
		super(new String[]{MY_BALANCE_XP});
	}

    public LogoutPopup clickLogout(){
        WebDriverUtils.click(BUTTON_LOGOUT_XP);
        return new LogoutPopup();
    }

    public ReferAFriendPopup clickReferAFriend(){
        WebDriverUtils.click(REFER_A_FRIEND_XP);
        return new ReferAFriendPopup();
    }

    public ChangeMyDetailsPage clickUpdateMyDetails(){
        WebDriverUtils.click(UPDATE_MY_DETAILS_XP);
        return new ChangeMyDetailsPage();
    }

    public ResponsibleGamingPage clickResponsibleGaming(){
        WebDriverUtils.click(RESPONSIBLE_GAMING_XP);
        return new ResponsibleGamingPage();
    }

    public ChangePasswordPopup clickChangePassword(){
        WebDriverUtils.click(CHANGE_PASSWORD_XP);
        return new ChangePasswordPopup();
    }

    public DepositPage clickDeposit() {
        WebDriverUtils.click(DEPOSIT_XP);
        return new DepositPage();
    }
}
