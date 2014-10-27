package pageObjects.core;

import enums.Page;
import pageObjects.account.MyAccountPage;
import pageObjects.admin.AdminPage;
import pageObjects.cashier.deposit.DepositPage;
import pageObjects.changePassword.ChangePasswordPopup;
import pageObjects.footer.Footer;
import pageObjects.forgotPassword.ForgotPasswordPopup;
import pageObjects.gamesPortlet.GameLaunchPopup;
import pageObjects.header.Header;
import pageObjects.header.LoggedInHeader;
import pageObjects.header.LoggedOutHeader;
import pageObjects.header.NavigationPanel;
import pageObjects.inbox.InboxPage;
import pageObjects.login.LoginPopup;
import pageObjects.login.LogoutPopup;
import pageObjects.menu.Menu;
import pageObjects.referAFriend.ReferAFriendPopup;
import pageObjects.registration.classic.RegistrationPageAllSteps;
import springConstructors.UserData;
import utils.WebDriverUtils;

import java.util.Collection;

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

    private Footer footer(){
        return new Footer();
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

    public boolean isAdminLoggedIn(){
        return WebDriverUtils.isVisible(AdminPage.DOCKBAR_XP, 0);
    }

    public boolean isUsernameDisplayed(UserData userData){
        return loggedInHeader().isUsernameDisplayed(userData);
    }

    public String getBalanceCurrency(){
        return loggedInHeader().getBalanceCurrency();
    }

    public String getBalanceAmount(){
        return loggedInHeader().getBalanceAmount();
    }

    public AbstractPage logout(){
        loggedInHeader().clickLogout().clickLogoutButton().closePopup();
        return waitForLogout();
    }

    public AbstractPage logoutAdmin(){
        AdminPage.clickLogout();
        return waitForAdminLogout();
    }

    public AbstractPage waitForLogout(){
        return header().waitForLogout();
    }

    public AbstractPage waitForAdminLogout(){
        WebDriverUtils.waitForElementToDisappear(AdminPage.DOCKBAR_XP, 30);
        return new AbstractPage();
    }


    public LoginPopup navigateToLoginForm(){
        return loggedOutHeader().navigateToLoginPopup();
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

    public Collection<String> getLanguageCodesList() {
        if (platform.equals(PLATFORM_DESKTOP)) {
            return header().getLanguageCodes();
        } else {
            return openMenu().getLanguageCodes();
        }
    }

    public void setLanguage(String languageCode) {
        if (platform.equals(PLATFORM_DESKTOP)) {
            header().setLanguage(languageCode);
        } else {
            openMenu().setLanguage(languageCode);
        }
        WebDriverUtils.waitFor();
    }

    //Mobile only
    public ChangePasswordPopup navigateToChangePassword(){
        return loggedInHeader().openMenu().loggedInMenu().clickChangePassword();
    }

    public ReferAFriendPopup navigateToReferAFriend() {
        return header().openMenu().loggedInMenu().clickReferAFriend();
    }

    public Menu openMenu() {
        return header().openMenu();
    }

    public DepositPage navigateToDepositPage(){
        return header().openMenu().loggedInMenu().clickDeposit();
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

    public String getTranslationText() {
        return footer().getFooterText();
    }

}



