package pageObjects.header;

import enums.Licensee;
import pageObjects.account.MyAccountPage;
import pageObjects.inbox.InboxPage;
import pageObjects.login.LogoutPopup;
import springConstructors.UserData;
import utils.TypeUtils;
import utils.WebDriverUtils;
import utils.core.DataContainer;

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

    public String getBalanceCurrency(){
        return TypeUtils.getBalanceCurrency(getBalance());
    }

    public String getBalanceAmount(){
        WebDriverUtils.refreshPage();
        return TypeUtils.getBalanceAmount(getBalance());
    }

    public String getUsername(){
        return WebDriverUtils.getElementText(LABEL_USERNAME_XP);
    }

    public boolean isUsernameDisplayed(String username){
        return (getUsername().contains(username));
    }

    public LogoutPopup clickLogout(){
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.sevenRegal)){
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
