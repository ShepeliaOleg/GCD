package pageObjects.core;

import enums.ConfiguredPages;
import enums.Licensee;
import enums.Page;
import enums.PlayerCondition;
import pageObjects.account.MyAccountPage;
import pageObjects.admin.AdminPageAdmin;
import pageObjects.cashier.deposit.DepositPage;
import pageObjects.changeLanguage.ChangeLanguagePage;
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
import utils.NavigationUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;
import utils.core.WebDriverFactory;

import java.util.Collection;

public class AbstractPortalPage extends AbstractPage {

    public static final String PORTLET_ERROR_XP= "//*[contains(@class,'error') or contains(@class, 'info__content')]";
    protected static final String LOADER_OVERLAY_XP=				"//*[@class='loading-mask-wrapper']";

    public AbstractPortalPage(String[] clickableBys, String[] invisibleBys) {
        super(WebDriverFactory.getPortalDriver(), clickableBys, invisibleBys);
    }

    public AbstractPortalPage(String[] clickableBys) {
        this(clickableBys, null);
    }

    public AbstractPortalPage() {
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
        return WebDriverUtils.isVisible(AdminPageAdmin.DOCKBAR_XP, 0);
    }

    public boolean isUsernameDisplayed(String username){
        return loggedInHeader().isUsernameDisplayed(username);
    }

    public String getBalanceCurrency(){
        return loggedInHeader().getBalanceCurrency();
    }

    public String getBalanceAmount(){
        return loggedInHeader().getBalanceAmount();
    }

    public AbstractPortalPage logout(){
        loggedInHeader().clickLogout().clickLogoutButton().close();
        return waitForLogout();
    }

    public AbstractPortalPage logoutAdmin(){
        AdminPageAdmin.clickLogout();
        return waitForAdminLogout();
    }

    public AbstractPortalPage waitForLogout(){
        return header().waitForLogout();
    }

    public AbstractPortalPage waitForAdminLogout(){
        WebDriverUtils.waitForElementToDisappear(AdminPageAdmin.DOCKBAR_XP, 30);
        return new AbstractPortalPage();
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

    public AbstractPortalPage login(UserData userData){
        return (AbstractPortalPage) this.login(userData, Page.homePage);
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
        if (DataContainer.getDriverData().getLicensee().equals(Licensee.sevenRegal)) {
            return header().getLanguageCodes();
        } else {
            ChangeLanguagePage changeLanguagePage = (ChangeLanguagePage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.changeLanguage);
            return changeLanguagePage.getLanguageCodes();
        }
    }

    public void setLanguage(String languageCode) {
        if (DataContainer.getDriverData().getLicensee().equals(Licensee.sevenRegal)) {
            header().setLanguage(languageCode);
        } else {
            ChangeLanguagePage changeLanguagePage = (ChangeLanguagePage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.changeLanguage);
            changeLanguagePage.changeLanguage(languageCode);
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

    public boolean isPortletErrorVisible(){
        return WebDriverUtils.isVisible(WebDriverFactory.getPortalDriver(), PORTLET_ERROR_XP, 2);
    }

    public String getPortletErrorMessage() {
        if(isPortletErrorVisible()){
            return WebDriverUtils.getElementText(WebDriverFactory.getPortalDriver(), PORTLET_ERROR_XP);
        }else {
            AbstractTest.failTest("Expected error message, but it did not appear");
            return null;
        }
    }

}




