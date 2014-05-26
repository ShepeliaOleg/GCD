package pageObjects.header;

import pageObjects.account.MyAccountPage;
import pageObjects.base.AbstractPage;
import pageObjects.inbox.InboxPage;
import springConstructors.UserData;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 4/10/14
 */

public class LoggedInHeader extends Header{

	private static final String LABEL_BALANCE_XP=	"//*[@class='balance']/span";
	private static final String LABEL_USERNAME_XP=	"//*[contains(@class,'userName')]";
    private static final String LINK_MY_ACCOUNT_XP=	"//a/span[text()='My account']";
	private static final String BUTTON_LOGOUT_XP=	"//*[contains(@class,'logout')]";
	private static final String LINK_INBOX_XP=		"//*[contains(@class,'inbox')]";

	public LoggedInHeader(){
		super(new String[]{LOGGED_IN_XP});
	}

	public MyAccountPage navigateToMyAccount(){
		WebDriverUtils.click(LINK_MY_ACCOUNT_XP);
		return new MyAccountPage();
	}

	public InboxPage navigateToInbox(){
		WebDriverUtils.click(LINK_INBOX_XP);
		return new InboxPage();
	}

	public AbstractPage clickLogout(){
		WebDriverUtils.click(BUTTON_LOGOUT_XP);
		return waitForLogout();
	}

	public String getBalance(){
		return WebDriverUtils.getElementText(LABEL_BALANCE_XP);
	}

	public int compareBalances(String oldBalance){
		String newBalance = getBalance();
		return convertBalance(newBalance) - convertBalance(oldBalance);
	}

	public String getWelcomeMessage(){
		return WebDriverUtils.getElementText(LABEL_USERNAME_XP);
	}

	public boolean isUsernameDisplayed(UserData userData){
		String username=userData.getUsername();
		return (getWelcomeMessage().contains(username));
	}

	private int convertBalance(String balance){
		return Integer.parseInt(balance.substring(0, balance.indexOf('.')).replace(",", "").replace("£", ""));
	}
}
