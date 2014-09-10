package pageObjects.base;

import enums.Page;
import pageObjects.account.ChangeMyPasswordPage;
import pageObjects.account.LoginPopup;
import pageObjects.account.LogoutPopup;
import pageObjects.account.MyAccountPage;
import pageObjects.forgotPassword.ForgotPasswordPopup;
import pageObjects.forgotPassword.ForgotUsernamePopup;
import pageObjects.gamesPortlet.GameLaunchPopup;
import pageObjects.gamesPortlet.GamesPortletPage;
import pageObjects.header.Header;
import pageObjects.header.LoggedInHeader;
import pageObjects.header.LoggedOutHeader;
import pageObjects.header.NavigationPanel;
import pageObjects.inbox.InboxPage;
import pageObjects.liveCasino.LiveCasinoPage;
import pageObjects.registration.RegistrationPage;
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

	public NavigationPanel navigationPanel(){
		return new NavigationPanel();
	}

	public boolean isLoggedIn(){
		return header().isLoggedIn();
	}

	public AbstractPage logout(){
		return clickLogout().logout();
	}

    public LogoutPopup clickLogout(){
        return loggedInHeader().clickLogout();
    }


	public GamesPortletPage navigateToCasino(){
		navigationPanel().selectCasinoTab();
		return new GamesPortletPage();
	}

	public ForgotPasswordPopup navigateToForgotPassword(){
		return loggedOutHeader().navigateToForgotPasswordPopup();
	}

    public ForgotUsernamePopup navigateToForgotUsername() {
        return navigateToForgotPassword().switchToForgotUsernamePopup();
    }
	public RegistrationPage navigateToRegistration(){
		return loggedOutHeader().openRegistrationForm();
	}

	public LiveCasinoPage navigateToLiveCasino(){
		return navigationPanel().selectLiveCasinoTab();
	}

	public LoginPopup navigateToLoginForm(){
		return loggedOutHeader().openLoginForm();
	}

	public ChangeMyPasswordPage navigateToChangePasswordPage(){
		return loggedInHeader().navigateToMyAccount().navigateToChangeMyPassword();
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

//    public AbstractPage login(){
//        return (AbstractPage) this.login(defaultUserData.getRegisteredUserData(), Page.homePage);
//    }

	public AbstractPage login(UserData userData){
		return (AbstractPage) this.login(userData, Page.homePage);
	}

	public AbstractPageObject login(UserData userData, boolean rememberMeEnable){
		return loggedOutHeader().login(userData, rememberMeEnable);
	}

	public AbstractPageObject login(UserData userData, Page page){
		return loggedOutHeader().login(userData, page);
	}

	public AbstractPageObject login(UserData userData, boolean rememberMeEnable, Page page){
		return loggedOutHeader().login(userData, rememberMeEnable, page);
	}

	public boolean isUsernameDisplayed(UserData userData){
		return loggedInHeader().isUsernameDisplayed(userData);
	}

	public boolean getRememberMeCheckBoxState(){
		return loggedOutHeader().getRememberMeCheckBoxState();
	}

	public String getEnteredUsernameFromLoginForm(){
		return loggedOutHeader().getUsernameText();
	}

	public String getBalance(){
		return loggedInHeader().getBalance();
	}

	public boolean usernameOfLoggedInPlayerIsDisplayedInHeader(UserData userData){
		return loggedInHeader().isUsernameDisplayed(userData);
	}

	public int compareBalances(String balance){
		return loggedInHeader().compareBalances(balance);
	}

	public AbstractPage waitForLogout(){
		return header().waitForLogout();
	}

}



