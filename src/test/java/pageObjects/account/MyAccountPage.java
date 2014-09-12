package pageObjects.account;

import pageObjects.changePassword.ChangePasswordPage;
import pageObjects.core.AbstractPage;
import pageObjects.inbox.InboxPage;
import pageObjects.referAFriend.ReferAFriendPage;
import pageObjects.responsibleGaming.ResponsibleGamingPage;
import utils.WebDriverUtils;

/*
 * User: ivanva
 * Date: 4/18/13
 */

public class MyAccountPage extends AbstractPage{
	private static final String ROOT_XP=					"//*[@id='content']";
	private static final String LINK_CONTACTUS_XP=			"//li/a[text()='Contact Us']";
	private static final String LINK_BALANCE_XP=			"//li/a[text()='Balance']";
	private static final String LINK_CHANGE_MY_DETAILS_XP=	"//li/a[text()='Change My Details']";
	private static final String LINK_CHANGE_MY_PASSWORD_XP=	"//li/a[text()='Change My Password']";
	private static final String LINK_QUICK_DEPOSIT_XP=		"//li/a[text()='Quick Deposit']";
	private static final String LINK_INBOX_XP=				"//li/a[text()='Inbox']";
	private static final String LINK_RESPONSIBLE_GAMING_XP=	"//li/a[text()='Responsible Gaming']";
	private static final String LINK_REFER_A_FRIEND_XP=		"//li/a[text()='Refer a Friend']";
	private static final String LINK_BINGO_GAME_HISTORY_XP=	"//li/a[text()='Bingo Game History']";
	private static final String LINK_GAME_HISTORY_XP=		"//li/a[text()='Game History']";
	private static final String LINK_FAQ_XP = 				"//li/a[text()='FAQ']";

	public MyAccountPage(){
		super(new String[]{ROOT_XP, LINK_CONTACTUS_XP});
	}

	public BalancePage navigateToBalancePortlet(){
		WebDriverUtils.click(LINK_BALANCE_XP);
		return new BalancePage();
	}

	public ChangeMyDetailsPage navigateToChangeMyDetails(){
		WebDriverUtils.click(LINK_CHANGE_MY_DETAILS_XP);
		return new ChangeMyDetailsPage();
	}

	public ChangePasswordPage navigateToChangeMyPassword(){
		WebDriverUtils.click(LINK_CHANGE_MY_PASSWORD_XP);
		return new ChangePasswordPage();
	}

	public void navigateToQuickDeposit(){
		WebDriverUtils.click(LINK_QUICK_DEPOSIT_XP);
	}

	public InboxPage navigateToInbox(){
		WebDriverUtils.click(LINK_INBOX_XP);
		return new InboxPage();
	}

	public ResponsibleGamingPage navigateToResponsibleGaming(){
		WebDriverUtils.click(LINK_RESPONSIBLE_GAMING_XP);
		return new ResponsibleGamingPage();
	}

//	public BingoPage navigateToBingoGamesHistory(){
//		WebDriverUtils.click(LINK_BINGO_GAME_HISTORY_XP);
//		return new BingoPage();
//	}

	public void navigateToGameHistory(){
		WebDriverUtils.click(LINK_GAME_HISTORY_XP);
	}

	public void navigateToFAQ(){
		WebDriverUtils.click(LINK_FAQ_XP);
	}

	public void navigateToContactUs(){
		WebDriverUtils.click(LINK_CONTACTUS_XP);
	}

}
