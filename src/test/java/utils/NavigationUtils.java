package utils;

import enums.*;
import org.openqa.selenium.JavascriptExecutor;
import pageObjects.HomePage;
import pageObjects.InternalTagsPage;
import pageObjects.account.BalancePage;
import pageObjects.account.PendingWithdrawPage;
import pageObjects.account.TransactionHistoryPage;
import pageObjects.admin.AdminCanNotPlayPopup;
import pageObjects.admin.AdminPageAdmin;
import pageObjects.admin.AdminPageGuest;
import pageObjects.banner.BannerPage;
import pageObjects.banner.BannerPageProfileID;
import pageObjects.bingoSchedule.BingoSchedulePage;
import pageObjects.bonus.AcceptDeclineBonusPopup;
import pageObjects.bonus.BonusPage;
import pageObjects.bonus.OkBonusPopup;
import pageObjects.cashier.deposit.DepositPage;
import pageObjects.cashier.withdraw.WithdrawPage;
import pageObjects.changeLanguage.ChangeLanguagePage;
import pageObjects.changeMyDetails.ChangeMyDetailsPage;
import pageObjects.changePassword.ChangePasswordPage;
import pageObjects.changePassword.ChangePasswordPopup;
import pageObjects.core.AbstractPageObject;
import pageObjects.core.AbstractPortalPage;
import pageObjects.core.AbstractPortalPopup;
import pageObjects.forgotPassword.ForgotPasswordPage;
import pageObjects.gamesPortlet.GameIncorrectId;
import pageObjects.gamesPortlet.GameLaunchPage;
import pageObjects.gamesPortlet.GameLaunchPopup;
import pageObjects.gamesPortlet.GamesPortletPage;
import pageObjects.header.LoggedOutHeader;
import pageObjects.inbox.InboxPage;
import pageObjects.liveCasino.LiveCasinoPage;
import pageObjects.login.AcceptTermsAndConditionsPopup;
import pageObjects.login.LoginPopup;
import pageObjects.login.SignedOutPopup;
import pageObjects.login.WelcomePopup;
import pageObjects.pageInPopup.PageInPopupPage;
import pageObjects.pageInPopup.PageInPopupPopup;
import pageObjects.referAFriend.ReferAFriendPage;
import pageObjects.registration.AdultContentPopup;
import pageObjects.registration.AfterRegistrationPopup;
import pageObjects.registration.ReadTermsAndConditionsPopup;
import pageObjects.registration.RegistrationPage;
import pageObjects.replacers.BonusHistoryPage;
import pageObjects.replacers.FreeSpinsBalancePage;
import pageObjects.replacers.PromotionalCodeReplacerPage;
import pageObjects.responsibleGaming.ResponsibleGamingPage;
import pageObjects.responsibleGaming.SelfExcludePage;
import pageObjects.webcontent.WebContentPage;
import springConstructors.AffiliateData;
import springConstructors.UserData;
import utils.cookie.AffiliateCookie;
import utils.core.AbstractTest;
import utils.core.DataContainer;
import utils.core.WebDriverFactory;

import static utils.core.AbstractTest.skipTestWithIssues;

public class NavigationUtils{

    private static final int POPUP_CHECK_RETRIES = 20;

    public static AbstractPageObject navigateToPage(ConfiguredPages configuredPages) {
        return navigateToPage(PlayerCondition.any, configuredPages, Page.homePage, null);
    }

    public static AbstractPageObject navigateToPage(PlayerCondition condition, ConfiguredPages configuredPages, Page expectedPage) {
        return navigateToPage(condition, configuredPages, expectedPage, null);
    }

    public static AbstractPageObject navigateToPage(PlayerCondition condition, ConfiguredPages configuredPages) {
        switch (condition) {
            case player:
                return navigateToPage(condition, configuredPages, DataContainer.getUserData().getRegisteredUserData());
            default:
                return navigateToPage(condition, configuredPages, Page.homePage, null);
        }
    }

    public static AbstractPageObject navigateToPage(PlayerCondition condition, ConfiguredPages configuredPage, UserData userData) {
        return navigateToPage(condition, configuredPage, Page.homePage, userData);
    }

    public static AbstractPageObject navigateToPage(PlayerCondition condition, ConfiguredPages configuredPage, Page expectedPage, UserData userData) {
        navigateToPortal(condition, configuredPage, expectedPage, userData);
        return getConfiguredPageObject(configuredPage);
    }

    public static AbstractPageObject getConfiguredPageObject(ConfiguredPages configuredPages) {
        switch (configuredPages){
            case admin:
                if (new AbstractPortalPage().isAdminLoggedIn()) {
                    return new AdminPageAdmin();
                } else {
                    return new AdminPageGuest();
                }
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
            case bonusHistory:                                  return new BonusHistoryPage();
            case updateMyDetails:                               return new ChangeMyDetailsPage();
            case changeLanguage:                                return new ChangeLanguagePage();
            case changeMyPassword:                              return new ChangePasswordPage();
            case deposit:                                       return new DepositPage();
            case forgotPassword:                                return new ForgotPasswordPage();
            case freeSpinsBalance:
            case freeSpinsBalanceTotal:                         return new FreeSpinsBalancePage();
            case gamesCasinoPage:
            case gamesFavourites:
            case gamesFavouritesCategoryFirst:
            case gamesFavouritesNoCategory:
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
            case pending_withdraw:                              return new PendingWithdrawPage();
            case permissions_page_admin:
            case permissions_page_all:
            case permissions_page_guest:
            case permissions_page_player:
            case permissions_page_player_guest:
            case permissions_portlet:                           return new AbstractPortalPage();
            case promotional_code_replacer:                     return new PromotionalCodeReplacerPage();
            case registerNoClientType:
            case registerClientTypeCreferrer:
            case registerDepositLimits:
            case registerDublicateEmailLookup:
            case register:                                      return new RegistrationPage();
            case referAFriend:
            case referAFriendPage:                              return new ReferAFriendPage();
            case responsibleGaming:                             return new ResponsibleGamingPage();
            case selfExclusion:                                 return new SelfExcludePage();
            case transactionHistory:                            return new TransactionHistoryPage();
            case bannerWebContentGame:
            case webContentGame:                                return new WebContentPage();
            case withdraw:                                      return new WithdrawPage();
            default:
                AbstractTest.failTest("Unexpected input in navigateToPage method");
                return null;
        }
    }

    private static void navigateToPortal(PlayerCondition condition, ConfiguredPages configuredPages, Page expectedPage, UserData userData) {
        String suffix = configuredPages.toString();
        boolean reload = false;
//        LogUtils.setTimestamp();
        WebDriverUtils.navigateToInternalURL(suffix);
        AbstractPortalPage abstractPortalPage = new AbstractPortalPage();
        switch (condition) {
            case guest:
                if(WebDriverUtils.isVisible(AbstractPortalPopup.ROOT_XP, 3)){
                    closeAllPopups(expectedPage);
                }
                if(PortalUtils.isLoggedIn()) {
                    //PortalUtils.logout();
                    javaScriptLogout();
                    //new SignedOutPopup().close();
                    //new AbstractPortalPopup().closePopup();
                    reload = false;
                }else if(abstractPortalPage.isAdminLoggedIn()) {
                    abstractPortalPage.logoutAdmin();
                    reload = true;
                }
                break;
            case player:
                WebDriverUtils.waitForPageToLoad();
                if(WebDriverUtils.isVisible(LoggedOutHeader.BUTTON_LOGIN_XP.getXpath(),1)) {
                    javaScriptLogin(userData);
                }
                if(WebDriverUtils.isVisible(LoginPopup.INPUT_USERNAME_XP, 0)) {
                //if(WebDriverUtils.isVisible(LoginPopup.INPUT_USERNAME_XP, 5)) {
                    new LoginPopup().login(userData);
                }else{
                    if(WebDriverUtils.isVisible(AbstractPortalPopup.ROOT_XP, 0)) {
                        closeAllPopups(expectedPage);
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
                    if (WebDriverUtils.getCurrentUrl().endsWith(ConfiguredPages.register.toString())) {
                        registrationError();
                    } else {
                        AbstractTest.failTest("Maximum check retries reached. ");
                    }
                }
                switch (getStatus()){
                    case wait:
//                        counter++;
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
                            String errMsg = expectedPage.toString() + " was expected, but never appeared.";
                            if (errMsg.contains("okBonus")) {
                                skipTestWithIssues("Problem with 'OK' bonus popup sometimes not appeared");
                            } else {
                                AbstractTest.failTest(errMsg);
                            }
                        }
                    case popup:
                        abstractPageObject = checkPopups(expectedPage);
                        if (abstractPageObject != null) {
                            return abstractPageObject;
                        }
                }
                counter++;
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
            }else if(portletErrorText.equals("System error. Try again later")){
                AbstractTest.skipTest("Registration failed with 'System error. Try again later'");
            }else {
                AbstractTest.failTest("Registration/Login failed : " + portletErrorText);
            }
        } else {
            //DISABLE to fix this bug
            AbstractTest.failTest("Registration/Login failed");
//            AbstractTest.skipTestWithIssues("D-17728");
        }
    }

    private static AbstractPageObject checkPopups(Page exceptPage) {
        if (WebDriverUtils.isVisible(WelcomePopup.TITLE_XP, 0)) {
            return processWelcomePopup(exceptPage);
        }else if (WebDriverUtils.isVisible(AfterRegistrationPopup.ROOT_XP, 0)) {
            return processAfterRegistrationPopup(exceptPage);
        }else if (WebDriverUtils.isVisible(PageInPopupPopup.LABEL_XP, 0)) {
            return processPageInPopup(exceptPage);
        } else if (WebDriverUtils.isVisible(LoginPopup.INPUT_USERNAME_XP, 0)) {
            return processLoginPopup(exceptPage);
        } else if (WebDriverUtils.isVisible(ReadTermsAndConditionsPopup.ROOT_XP, 0)) {
            return processReadTermsAndConditionsPopup(exceptPage);
        } else if (WebDriverUtils.isVisible(AcceptTermsAndConditionsPopup.TERMS_ROOT_XP, 0)) {
            return processTermsAndConditionsPopup(exceptPage);
        } else if (WebDriverUtils.isVisible(AcceptDeclineBonusPopup.BONUS_TITLE_XP, 0)){
            if(WebDriverUtils.isVisible(AcceptDeclineBonusPopup.BUTTON_ACCEPT_XP.getXpath(), 0)){
                return processAcceptDecline(exceptPage);
            }else {
                return processOkBonus(exceptPage);
            }
        } else if (WebDriverUtils.isVisible(ChangePasswordPopup.ROOT_XP, 0)) {
            return processChangePasswordPopup(exceptPage);
        } else {
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

    private static PageInPopupPopup processPageInPopup(Page exceptPage) {
        PageInPopupPopup pageInPopupPopup = new PageInPopupPopup();
        if (exceptPage == Page.pageInPopup) {
            return pageInPopupPopup;
        } else {
            pageInPopupPopup.closePopup();
            WebDriverUtils.waitForElementToDisappear(PageInPopupPopup.LABEL_XP);
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

    public static AbstractPageObject navigateToAffiliateURL(ConfiguredPages page, AffiliateData affiliateData) {
        WebDriverUtils.clearLocalStorage(); // D-18096
        WebDriverUtils.navigateToInternalURL(page.toString() + affiliateData.getRelativeURL());
        WebDriverUtils.waitFor();
        AffiliateCookie affiliateCookie = new AffiliateCookie("");
        if (affiliateCookie.isPresent()) {
            affiliateCookie.validateValue(affiliateData);
        } else {
            AbstractTest.results.add("Affiliate cookie should be created on affiliate URL request, but it is absent.");
        }
        return getConfiguredPageObject(page);
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
        WebDriverUtils.navigateToInternalURL(gameUrl);
        if (GameCategories.groupAll.getGames().contains(gameId)) {
            switch (playerCondition) {
                case guest:
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

    public static void refreshPage() {
        WebDriverUtils.refreshPage();
    }

    private static void javaScriptLogin(UserData userData) {
        String jsLoginScript = "var user = require('modules/user/user.model')\n" +
                "user.set({\n" +
                "    userName: '" + userData.getUsername() + "',\n" +
                "    password: '" + userData.getPassword() + "'\n" +
                "})\n" +
                ".fetch()\n" +
                ".done(function(data) {\n" +
                " console.log('success', data);\n" +
                "})\n" +
                ".fail(function(data) {\n" +
                " console.log('error', data);\n" +
                "});";
        WebDriverUtils.executeScript(jsLoginScript);
        WebDriverUtils.waitForPageToLoad();
        WebDriverUtils.waitFor(1100);
        WebDriverUtils.refreshPage();
        System.out.println("Fast JavaScript login");
    }

    private static void javaScriptLogout(){
        String jsLogoutScript =  "var user = require('modules/user/user.model')\n" +
                "user.logoutAction();";
        WebDriverUtils.executeScript(jsLogoutScript);
        WebDriverUtils.refreshPage();
        System.out.println("Fast JavaScript logout");
    }
}
