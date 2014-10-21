package pageObjects.header;

import pageObjects.account.MyAccountPage;
import pageObjects.inbox.InboxPage;
import pageObjects.login.LogoutPopup;
import springConstructors.UserData;
import utils.TypeUtils;
import utils.WebDriverUtils;

public class LoggedInHeader extends Header{

    private static final String LABEL_USERNAME_XP=	BALANCE_AREA+ "/b[1] | "+BALANCE_AREA+"/div/*[1]";
    private static final String LABEL_BALANCE_XP=	BALANCE_AREA+ "/b[2] | "+BALANCE_AREA+"/div/*[2]";
    //Desktop only
    private static final String LINK_MY_ACCOUNT_XP=	"//a[contains(@class, 'myaccount-link')]";
	private static final String BUTTON_LOGOUT_XP=	"//a[contains(@class, 'logout-link')]";
	private static final String LINK_INBOX_XP=		"//*[contains(@class,'inbox')]";

	public LoggedInHeader(){
		super(new String[]{Header.BALANCE_AREA, LABEL_BALANCE_XP, LABEL_USERNAME_XP});
	}

    public String getBalance(){
        return WebDriverUtils.getElementText(LABEL_BALANCE_XP);
    }

    public String getUsername(){
        return WebDriverUtils.getElementText(LABEL_USERNAME_XP);
    }

    public boolean isUsernameDisplayed(UserData userData){
        return (getUsername().contains(userData.getUsername()));
    }

    public int getBalanceChange(String oldBalance){
        return TypeUtils.convertBalance(getBalance()) - TypeUtils.convertBalance(oldBalance);
    }

    public LogoutPopup clickLogout(){
        if(platform.equals(PLATFORM_DESKTOP)){
            return clickLogoutButton();
        }else {
            return openMenu().loggedInMenu().clickLogout();
        }
    }

    //Desktop only

	public MyAccountPage navigateToMyAccount(){
		WebDriverUtils.click(LINK_MY_ACCOUNT_XP);
		return new MyAccountPage();
	}

	public InboxPage navigateToInbox(){
		WebDriverUtils.click(LINK_INBOX_XP);
		return new InboxPage();
	}

    private LogoutPopup clickLogoutButton(){
        WebDriverUtils.click(BUTTON_LOGOUT_XP);
        return new LogoutPopup();
    }


}
