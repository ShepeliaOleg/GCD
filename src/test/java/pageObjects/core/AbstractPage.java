package pageObjects.core;

import enums.Page;
import pageObjects.account.MyAccountPage;
import pageObjects.changePassword.ChangePasswordPopup;
import pageObjects.forgotPassword.ForgotPasswordPopup;
import pageObjects.gamesPortlet.GameLaunchPopup;
import pageObjects.header.Header;
import pageObjects.header.LoggedInHeader;
import pageObjects.header.LoggedOutHeader;
import pageObjects.header.NavigationPanel;
import pageObjects.inbox.InboxPage;
import pageObjects.login.LoginPopup;
import pageObjects.login.LogoutPopup;
import pageObjects.referAFriend.ReferAFriendPopup;
import pageObjects.registration.classic.RegistrationPageAllSteps;
import springConstructors.UserData;
import utils.WebDriverUtils;

public class AbstractPage extends AbstractPageObject{

	private String mainWindowHandle;

	public AbstractPage(String[] clickableBys, String[] invisibleBys){
		super(clickableBys, invisibleBys);
		this.mainWindowHandle=WebDriverUtils.getWindowHandle();
		WebDriverUtils.waitForPageToLoad();
	}

	public String getMainWindowHandle(){
		return mainWindowHandle;
	}

	public AbstractPage(String[] clickableBys){
		this(clickableBys, null);
	}

	public AbstractPage(){
		this(null, null);
	}

	public Header header(){
		return new Header();
	}

	public LoggedInHeader loggedInHeader(){
		return new LoggedInHeader();
	}

	public LoggedOutHeader loggedOutHeader(){
		return new LoggedOutHeader();
	}

    public boolean isLoggedIn(){
        return header().isLoggedIn();
    }

    public boolean isUsernameDisplayed(UserData userData){
        return loggedInHeader().isUsernameDisplayed(userData);
    }

    public String getBalance(){
        return loggedInHeader().getBalance();
    }

    public int compareBalances(String balance){
        return loggedInHeader().compareBalances(balance);
    }

    public AbstractPage logout(){
        loggedInHeader().clickLogout().clickLogoutButton().closePopup();
        return waitForLogout();
    }

    public AbstractPage waitForLogout(){
        return header().waitForLogout();
    }

    public LoginPopup navigateToLoginForm(){
        return loggedOutHeader().clickButtonLogin();
    }

    public LogoutPopup navigateToLogoutPopup(){
        return loggedInHeader().clickLogout();
    }

    public ForgotPasswordPopup navigateToForgotPassword(){
        return loggedOutHeader().navigateToForgotPasswordPopup();
    }

    public RegistrationPageAllSteps navigateToRegistration(){
        return loggedOutHeader().openRegistrationForm();
    }

    public AbstractPage login(UserData userData){
        return (AbstractPage) this.login(userData, Page.homePage);
    }

    public AbstractPageObject login(UserData userData, Page page){
        return this.login(userData, false, page);
    }

    public AbstractPageObject login(UserData userData, boolean rememberMeEnable){
        return this.login(userData, rememberMeEnable, Page.homePage);
    }

    public AbstractPageObject login(UserData userData, boolean rememberMeEnable, Page page){
        return loggedOutHeader().login(userData, rememberMeEnable, page);
    }

    //Mobile only
    public ChangePasswordPopup navigateToChangePassword(){
        return loggedInHeader().openMenu().loggedInMenu().navigateToChangePassword();
    }

    public ReferAFriendPopup navigateToReferAFriend() {
        return header().openMenu().loggedInMenu().navigateToReferAFriend();
    }


    //Desktop only
//	public GamesPortletPage navigateToCasino(){
//		navigationPanel().selectCasinoTab();
//		return new GamesPortletPage();
//	}
//    public ForgotUsernamePopup navigateToForgotUsername() {
//        return navigateToForgotPassword().switchToForgotUsernamePopup();
//    }
//
//	public LiveCasinoPage navigateToLiveCasino(){
//		return navigationPanel().selectLiveCasinoTab();
//	}

	public NavigationPanel navigationPanel(){
		return new NavigationPanel();
	}

	public MyAccountPage navigateToMyAccount(){
		return loggedInHeader().navigateToMyAccount();
	}

	public InboxPage navigateToInboxPage(){
		return loggedInHeader().navigateToInbox();
	}

	public GameLaunchPopup switchToGameWindow(){
		return new GameLaunchPopup(getMainWindowHandle());
	}

	public boolean getRememberMeCheckBoxState(){
		return loggedOutHeader().getRememberMeCheckBoxState();
	}

	public String getEnteredUsernameFromLoginForm(){
		return loggedOutHeader().getUsernameText();
	}
}



