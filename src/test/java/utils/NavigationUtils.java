package utils;

import enums.*;
import pageObjects.HomePage;
import pageObjects.InternalTagsPage;
import pageObjects.account.BalancePage;
import pageObjects.changeMyDetails.ChangeMyDetailsPage;
import pageObjects.admin.AdminCanNotPlayPopup;
import pageObjects.admin.AdminPage;
import pageObjects.banner.BannerPage;
import pageObjects.banner.BannerPageProfileID;
import pageObjects.bingoSchedule.BingoSchedulePage;
import pageObjects.bonus.AcceptDeclineBonusPopup;
import pageObjects.bonus.BonusPage;
import pageObjects.bonus.OkBonusPopup;
import pageObjects.cashier.deposit.DepositPage;
import pageObjects.cashier.withdraw.WithdrawPage;
import pageObjects.changePassword.ChangePasswordPage;
import pageObjects.changePassword.ChangePasswordPopup;
import pageObjects.core.*;
import pageObjects.forgotPassword.ForgotPasswordPage;
import pageObjects.gamesPortlet.GameIncorrectId;
import pageObjects.gamesPortlet.GameLaunchPage;
import pageObjects.gamesPortlet.GameLaunchPopup;
import pageObjects.gamesPortlet.GamesPortletPage;
import pageObjects.inbox.InboxPage;
import pageObjects.liveCasino.LiveCasinoPage;
import pageObjects.login.AcceptTermsAndConditionsPopup;
import pageObjects.login.LoginPopup;
import pageObjects.login.WelcomePopup;
import pageObjects.pageInPopup.PageInPopupPage;
import pageObjects.pageInPopup.PageInPopupPopup;
import pageObjects.referAFriend.ReferAFriendPage;
import pageObjects.registration.AdultContentPopup;
import pageObjects.registration.AfterRegistrationPopup;
import pageObjects.registration.ReadTermsAndConditionsPopup;
import pageObjects.registration.RegistrationPage;
import pageObjects.responsibleGaming.ResponsibleGamingPage;
import pageObjects.responsibleGaming.SelfExcludePage;
import pageObjects.webcontent.WebContentPage;
import springConstructors.AffiliateData;
import springConstructors.UserData;
import utils.cookie.AffiliateCookie;
import utils.core.AbstractTest;
import utils.core.DataContainer;

public class NavigationUtils{

    private static final int POPUP_CHECK_RETRIES = 30;

    public static AbstractPageObject navigateToPage(ConfiguredPages configuredPages) {
        return navigateToPage(PlayerCondition.any, configuredPages, null);
    }

    public static AbstractPageObject navigateToPage(PlayerCondition condition, ConfiguredPages configuredPages) {
        switch (condition) {
            case player:
                return navigateToPage(condition, configuredPages, DataContainer.getUserData().getRegisteredUserData());
            default:
                return navigateToPage(condition, configuredPages, null);
        }
    }

    public static AbstractPageObject navigateToPage(PlayerCondition condition, ConfiguredPages configuredPage, UserData userData) {
        navigateToPortal(condition, configuredPage, userData);
        return getConfiguredPageObject(configuredPage);
    }

    public static AbstractPageObject getConfiguredPageObject(ConfiguredPages configuredPages) {
        switch (configuredPages){
            case admin:                                         return new AdminPage();
            case balance:                                       return new BalancePage();
            case banner5seconds:
            case bannerGame:
            case bannerGameTwoSlides:
            case bannerHtml:
            case bannerImage:
            case bannerInRotation:
            case bannerLink:
            case bannerMixed:
            case bannerNavigationArrows:
            case bannerNavigationArrowsBullets:
            case bannerNavigationBullets:
            case bannerNavigationButtons:
            case bannerWebContent:                              return new BannerPage();
            case bannerProfileNoProfileOneSlide:
            case bannerProfileNoProfileTwoSlides:
            case bannerProfileSingleProfileOneSlide:
            case bannerProfileMultiProfileOneSlide:
            case bannerProfileSingleSameProfileTwoSlides:
            case bannerProfileSingleProfileOneOfTwoSlides:
            case bannerProfileSingleDiffProfilesTwoSlides:
            case bannerProfileMultiProfileTwoSlides:
            case bannerProfileMultiProfileOneOfTwoSlides:       return new BannerPageProfileID();
            case bingoLobbyFeed:
            case bingoScheduleFeed:                             return new BingoSchedulePage();
            case bonusPage:                                     return new BonusPage();
            case updateMyDetails:                               return new ChangeMyDetailsPage();
            case changeMyPassword:                              return new ChangePasswordPage();
            case deposit:                                       return new DepositPage();
            case forgotPassword:                                return new ForgotPasswordPage();
            case gamesCasinoPage:
            case gamesFavourites:
            case gamesList:
            case gamesMinimum:
            case gamesNavigationStyleNone:
            case gamesNavigationStyleRefineBy:
            case gamesNavigationStyleCategoryTabsTop:
            case gamesNavigationStyleCategoryTabsLeft:
            case gamesNavigationStyleCategoryTabsRefineByTop:
            case gamesNavigationStyleCategoryTabsRefineByLeft:
            case gamesStyleOne:
            case gamesStyleTwo:
            case gamesStyleThree:
            case gamesStyleFour:
            case gamesToFit:                                    return new GamesPortletPage();
            case home:                                          return new HomePage();
            case inbox:                                         return new InboxPage();
            case internalTags:                                  return new InternalTagsPage();
            case liveTableFinder:                               return new LiveCasinoPage();
            case page_in_popup_scroll:
            case page_in_popup_child:
            case page_in_popup_a:                               return new PageInPopupPopup();
            case page_in_popup_b:                               return new AdultContentPopup();
            case page_in_popup_disabled:
            case page_in_popup_link_to_child:
            case page_in_popup_parent:                          return new PageInPopupPage();
            case permissions_page_admin:
            case permissions_page_all:
            case permissions_page_guest:
            case permissions_page_player:
            case permissions_page_player_guest:
            case permissions_portlet:                           return new AbstractPortalPage();
            case registerNoClientType:
            case registerClientTypeCreferrer:
            case register:                                      return new RegistrationPage();
            case referAFriend:                                  return new ReferAFriendPage();
            case responsibleGaming:                             return new ResponsibleGamingPage();
            case selfExclusion:                                 return new SelfExcludePage();
            case bannerWebContentGame:
            case webContentGame:                                return new WebContentPage();
            case withdraw:                                      return new WithdrawPage();
            default:
                AbstractTest.failTest("Unexpected input in navigateToPage method");
                return null;
        }
    }

    private static void navigateToPortal(PlayerCondition condition, ConfiguredPages configuredPages, UserData userData) {
        String suffix = configuredPages.toString();
        boolean reload = false;
//        LogUtils.setTimestamp();
        WebDriverUtils.navigateToInternalURL(suffix);
        AbstractPortalPage abstractPortalPage = new AbstractPortalPage();
        switch (condition) {
            case guest:
                if(WebDriverUtils.isVisible(AbstractPortalPopup.ROOT_XP, 0)){
                    checkPopups(Page.homePage);
                }
                if(PortalUtils.isLoggedIn()) {
                    PortalUtils.logout();
                    reload = true;
                }else if(abstractPortalPage.isAdminLoggedIn()) {
                    abstractPortalPage.logoutAdmin();
                    reload = true;
                }
                break;
            case player:
                if(WebDriverUtils.isVisible(LoginPopup.INPUT_USERNAME_XP, 3)) {
                    new LoginPopup().login(userData);
                }else{
                    if(WebDriverUtils.isVisible(AbstractPortalPopup.ROOT_XP, 0)) {
                        checkPopups(Page.homePage);
                    }
                    if(abstractPortalPage.isAdminLoggedIn()) {
                        abstractPortalPage.logoutAdmin();
                        abstractPortalPage.login(userData);
                        reload = true;
                    }else if(PortalUtils.isLoggedIn()) {
                        if(!abstractPortalPage.loggedInHeader().getUsername().equalsIgnoreCase(userData.getUsername())){
                            PortalUtils.logout();
                            abstractPortalPage.login(userData);
                            reload = true;
                        }
                    }else {
                        abstractPortalPage.login(userData);
                        reload = true;
                    }
                }
                break;
            case admin:
                if (!abstractPortalPage.isAdminLoggedIn()) {
                    if (PortalUtils.isLoggedIn()) {
                        PortalUtils.logout();
                    }
                    PortalUtils.loginAdmin();
                    reload = true;
                }
                break;
            case any:
                if (abstractPortalPage.isAdminLoggedIn()) {
                    abstractPortalPage.logoutAdmin();
                }
                break;
        }
        if(reload){
            WebDriverUtils.navigateToInternalURL(suffix);
        }
    }

    public static AbstractPageObject closeAllPopups(Page expectedPage) {
        AbstractPageObject abstractPageObject;
        int counter = 0;
        if (!expectedPage.equals(Page.gameLaunch)) {
            while (true) {
                if (counter == POPUP_CHECK_RETRIES) {
                    registrationError();
                }
                switch (getStatus()){
                    case wait:
                        counter++;
                        break;
                    case loginPopup:
                        if (expectedPage.equals(Page.loginPopup)) {
                            return new LoginPopup();
                        } else {
                            registrationError();
                        }
                        break;
                    case loggedIn:
                        if (expectedPage.equals(Page.homePage)) {
                            return new HomePage();
                        } else {
                            AbstractTest.failTest(expectedPage.toString() + " was expected, but never appeared.");
                        }
                    case popup:
                        abstractPageObject = checkPopups(expectedPage);
                        if (abstractPageObject != null) {
                            return abstractPageObject;
                        }
                }
            }
        } else {
            return null;
        }
    }

    private static LoginStatus getStatus() {
        if (WebDriverUtils.isVisible(AbstractPortalPopup.ROOT_XP, 1)) {
            if (WebDriverUtils.isVisible(LoginPopup.BUTTON_LOGIN_XP, 0)) {
                return LoginStatus.loginPopup;
            } else {
                return LoginStatus.popup;
            }
        } else if (PortalUtils.isLoggedIn() && !WebDriverUtils.isVisible(AbstractPortalPopup.ROOT_XP, 0)) {
            return LoginStatus.loggedIn;
        } else {
            return LoginStatus.wait;
        }
    }

    public static void registrationError() {
        String portletError = AbstractPortalPage.PORTLET_ERROR_XP;
        if (WebDriverUtils.isVisible(portletError, 0)) {
            String portletErrorText = WebDriverUtils.getElementText(portletError);
            if (portletErrorText.equals("Server system error")) {
                AbstractTest.skipTest("Registration failed with 'Server system error'");
            } else {
                AbstractTest.failTest("Registration/Login failed : " + portletErrorText);
            }
        } else {
            AbstractTest.failTest("Registration/Login failed");
        }
    }

    private static AbstractPageObject checkPopups(Page exceptPage) {
        if (WebDriverUtils.isVisible(WelcomePopup.TITLE_XP, 0)) {
            return processWelcomePopup(exceptPage);
        }else if (WebDriverUtils.isVisible(AfterRegistrationPopup.ROOT_XP, 0)) {
            return processAfterRegistrationPopup(exceptPage);
        } else if (WebDriverUtils.isVisible(LoginPopup.INPUT_USERNAME_XP, 0)) {
            return processLoginPopup(exceptPage);
        } else if (WebDriverUtils.isVisible(ReadTermsAndConditionsPopup.ROOT_XP, 0)) {
            return processReadTermsAndConditionsPopup(exceptPage);
        } else if (WebDriverUtils.isVisible(AcceptTermsAndConditionsPopup.TERMS_ROOT_XP, 0)) {
            return processTermsAndConditionsPopup(exceptPage);
        } else if (WebDriverUtils.isVisible(ChangePasswordPopup.ROOT_XP, 0)) {
            return processChangePasswordPopup(exceptPage);
        } else if (WebDriverUtils.isVisible(AcceptDeclineBonusPopup.BONUS_TITLE_XP, 0)){
            if(WebDriverUtils.isVisible(AcceptDeclineBonusPopup.BUTTON_ACCEPT_XP, 0)){
                return processAcceptDecline(exceptPage);
            }else {
                return processOkBonus(exceptPage);
            }
        }else {
            return processGenericPopup();
        }
    }

    private static AbstractPageObject processGenericPopup() {
        new AbstractPortalPopup().closePopup();
        return null;
    }

    private static WelcomePopup processWelcomePopup(Page exceptPage) {
        WelcomePopup welcomePopup = new WelcomePopup();
        if (exceptPage == Page.welcomePopup) {
            return welcomePopup;
        } else {
            welcomePopup.closePopup();
            return null;
        }
    }

    private static LoginPopup processLoginPopup(Page exceptPage) {
        LoginPopup loginPopup = new LoginPopup();
        if (exceptPage == Page.loginPopup) {
            return loginPopup;
        } else {
            loginPopup.closePopup();
        }
        return null;
    }

    private static AfterRegistrationPopup processAfterRegistrationPopup(Page exceptPage) {
        AfterRegistrationPopup afterRegistrationPopup = new AfterRegistrationPopup();
        if (exceptPage == Page.afterRegistrationPopup) {
            return afterRegistrationPopup;
        } else {
            afterRegistrationPopup.closePopup();
            WebDriverUtils.waitForElementToDisappear(AfterRegistrationPopup.ROOT_XP);
            return null;
        }
    }

    private static ReadTermsAndConditionsPopup processReadTermsAndConditionsPopup(Page exceptPage) {
        ReadTermsAndConditionsPopup readTermsAndConditionsPopup = new ReadTermsAndConditionsPopup();
        if (exceptPage == Page.readTermsAndConditionsPopup) {
            return readTermsAndConditionsPopup;
        } else {
            readTermsAndConditionsPopup.closePopup();
            WebDriverUtils.waitForElementToDisappear(ReadTermsAndConditionsPopup.ROOT_XP);
            return null;
        }
    }

    private static AcceptTermsAndConditionsPopup processTermsAndConditionsPopup(Page exceptPage) {
        AcceptTermsAndConditionsPopup acceptTermsAndConditionsPopup = new AcceptTermsAndConditionsPopup();
        if (exceptPage == Page.acceptTermsAndConditionsPopup) {
            return acceptTermsAndConditionsPopup;
        } else {
            acceptTermsAndConditionsPopup.clickAccept();
            return null;
        }
    }

    private static ChangePasswordPopup processChangePasswordPopup(Page exceptPage) {
        ChangePasswordPopup changePasswordPopup = new ChangePasswordPopup();
        if (exceptPage == Page.changePasswordPopup) {
            return changePasswordPopup;
        } else {
            AbstractTest.failTest("Password change prompt appeared");
        }
        return null;
    }

    private static OkBonusPopup processOkBonus(Page exceptPage) {
        OkBonusPopup okBonusPopup = new OkBonusPopup();
        if (exceptPage == Page.okBonus) {
            return okBonusPopup;
        } else {
            okBonusPopup.closePopup();
            return null;
        }
    }

    private static AcceptDeclineBonusPopup processAcceptDecline(Page exceptPage) {
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = new AcceptDeclineBonusPopup();
        if (exceptPage == Page.acceptDeclineBonus) {
            return acceptDeclineBonusPopup;
        } else {
            acceptDeclineBonusPopup.clickDecline();
            return null;
        }
    }

    public static void navigateToAffiliateURL(ConfiguredPages page, AffiliateData affiliateData) {
        WebDriverUtils.navigateToInternalURL(page.toString() + affiliateData.getRelativeURL());
        AffiliateCookie affiliateCookie = new AffiliateCookie("");
        if (affiliateCookie.isPresent()) {
            affiliateCookie.validateValue(affiliateData);
        } else {
            AbstractTest.results.add("Affiliate cookie should be created on affiliate URL request, but it is absent.");
        }
    }

    public static void assertGameLaunch(String gameId, Integer realMode) {
        if (DataContainer.getDriverData().getLicensee().equals(Licensee.sevenRegal)) {
            new GameLaunchPopup(new AbstractPortalPage().getMainWindowHandle(), gameId).assertGameLaunchAndClose();
        } else {
            GameLaunchPage gameLaunchPage = new GameLaunchPage(gameId, realMode);
            AbstractTest.assertTrue(gameLaunchPage.iFrameGameUrlIsValid(), "Game '" + gameId + "' launched.");
        }
    }

    public static AbstractPageObject launchGameByUrl(PlayerCondition playerCondition, String gameId) {
        return launchGameByUrl(playerCondition, gameId, null);
    }

    public static AbstractPageObject launchGameByUrl(PlayerCondition playerCondition, String gameId, Integer realMode) {
        navigateToPage(playerCondition, ConfiguredPages.home);
        String gameUrl = GameLaunchPage.IFRAME_LAUNCH_GAME_URL + gameId;
        if (realMode != null) {
            gameUrl = gameUrl.concat(GameLaunchPage.GAME_MODE_URL + realMode);
        }
        WebDriverUtils.navigateToInternalURL(gameUrl);
        if (GameCategories.groupAll.getGames().contains(gameId)) {
            switch (playerCondition) {
                case guest:
                    return new LoginPopup();
                case player:
                    return new GameLaunchPage(gameId, realMode);
                case admin:
                    return new AdminCanNotPlayPopup();
                default:
                    AbstractTest.failTest("Player condition for game launch should be one of: guest, player, admin.");
                    return null;
            }
        } else {
            return new GameIncorrectId();
        }
    }

    public static GameLaunchPage launchGameByUrl(UserData userData) {
        String gameID = GameLaunchPage.IFRAME_GAME_1;
        navigateToPage(PlayerCondition.player, ConfiguredPages.home, userData);
        String gameUrl = GameLaunchPage.IFRAME_LAUNCH_GAME_URL + gameID;
        WebDriverUtils.navigateToInternalURL(gameUrl);
        return new GameLaunchPage(gameID, null);
    }
}
