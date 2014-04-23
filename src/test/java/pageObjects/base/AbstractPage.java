package pageObjects.base;

import enums.ConfiguredPages;
import enums.Page;
import pageObjects.InternalTagsPage;
import pageObjects.account.ChangeMyPasswordPage;
import pageObjects.account.LoginPopup;
import pageObjects.account.MyAccountPage;
import pageObjects.bingoSchedule.BingoSchedulePage;
import pageObjects.bonus.BonusPage;
import pageObjects.forgotPassword.ForgotPasswordPopup;
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

/**
 * User: sergiich
 * Date: 4/9/14
 */

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
		return loggedInHeader().clickLogout();
	}

	public GamesPortletPage navigateToCasino(){
		navigationPanel().selectCasinoTab();
		return new GamesPortletPage();
	}

	public BingoSchedulePage navigateToBingoSchedulePage(ConfiguredPages feed) {
		WebDriverUtils.navigateToInternalURL(feed.toString());
		return new BingoSchedulePage();
	}

	public GamesPortletPage navigateToGamesPortletPage(ConfiguredPages feed){
		WebDriverUtils.navigateToInternalURL(feed.toString());
		return new GamesPortletPage();
	}

	public InternalTagsPage navigateToInternalTagsPage(){
		WebDriverUtils.navigateToInternalURL(ConfiguredPages.internalTags.toString());
		return new InternalTagsPage();
	}

	public BonusPage navigateToBonusPage(){
		WebDriverUtils.navigateToInternalURL(ConfiguredPages.bonusPage.toString());
		return new BonusPage();
	}

	public ForgotPasswordPopup navigateToForgotPassword(){
		return loggedOutHeader().navigateToForgotPasswordPopup();
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

	public void waitForLogout(){
		loggedInHeader().waitForLogout();
	}

}



