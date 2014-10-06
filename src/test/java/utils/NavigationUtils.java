package utils;

import enums.ConfiguredPages;
import enums.Page;
import enums.PlayerCondition;
import org.testng.SkipException;
import pageObjects.HomePage;
import pageObjects.InternalTagsPage;
import pageObjects.account.BalancePage;
import pageObjects.account.ChangeMyDetailsPage;
import pageObjects.admin.AdminPage;
import pageObjects.banner.BannerPage;
import pageObjects.banner.BannerPageProfileID;
import pageObjects.bingoSchedule.BingoSchedulePage;
import pageObjects.bonus.AcceptDeclineBonusPopup;
import pageObjects.bonus.BonusPage;
import pageObjects.bonus.OkBonusPopup;
import pageObjects.changePassword.ChangePasswordPage;
import pageObjects.changePassword.ChangePasswordPopup;
import pageObjects.core.AbstractPage;
import pageObjects.core.AbstractPageObject;
import pageObjects.core.AbstractPopup;
import pageObjects.forgotPassword.ForgotPasswordPage;
import pageObjects.gamesPortlet.GameLaunchPage;
import pageObjects.gamesPortlet.GameLaunchPopup;
import pageObjects.gamesPortlet.GamesPortletPage;
import pageObjects.inbox.InboxPage;
import pageObjects.liveCasino.LiveCasinoPage;
import pageObjects.login.AcceptTermsAndConditionsPopup;
import pageObjects.login.LoginPopup;
import pageObjects.login.WelcomePopup;
import pageObjects.referAFriend.ReferAFriendPage;
import pageObjects.registration.AfterRegistrationPopup;
import pageObjects.registration.ReadTermsAndConditionsPopup;
import pageObjects.registration.RegistrationPage;
import pageObjects.registration.classic.RegistrationPageAllSteps;
import pageObjects.registration.threeStep.RegistrationPageStepThree;
import pageObjects.responsibleGaming.ResponsibleGamingPage;
import pageObjects.webcontent.WebContentPage;
import springConstructors.AffiliateData;
import springConstructors.UserData;
import utils.cookie.AffiliateCookie;
import utils.core.AbstractTest;
import utils.core.WebDriverObject;

public class NavigationUtils extends WebDriverObject{

    private static final int POPUP_CHECK_RETRIES = 30;
    private static final int POPUP_WAIT_TIMEOUT = 7;

    public static AbstractPage navigateToPage(ConfiguredPages configuredPages){
        return navigateToPage(PlayerCondition.any, configuredPages, null);
    }

	public static AbstractPage navigateToPage(PlayerCondition condition, ConfiguredPages configuredPages){
        if (condition.equals(PlayerCondition.loggedIn)){
            WebDriverUtils.runtimeExceptionWithUrl("No userdata set for logged in user");
        }
        return navigateToPage(condition, configuredPages, null);
    }

    public static AbstractPage navigateToPage(PlayerCondition condition, ConfiguredPages configuredPages, UserData userData){
        navigateToPortal(condition, configuredPages, userData);
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
            case changeMyDetails:                               return new ChangeMyDetailsPage();
            case changeMyPassword:                              return new ChangePasswordPage();
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
            case registerNoClientType:
            case registerClientTypeCreferrer:
            case register:                                      return new RegistrationPage();
            case referAFriend:                                  return new ReferAFriendPage();
            case responsibleGaming:
            case selfExclusion:                                 return new ResponsibleGamingPage();
            case bannerWebContentGame:
            case webContentGame:                                return new WebContentPage();
            default: throw new RuntimeException("Unexpected input in navigateToPage method");
        }
    }

    private static void navigateToPortal(PlayerCondition condition, ConfiguredPages configuredPages, UserData userData){
        String suffix=configuredPages.toString();
//        LogUtils.setTimestamp();
        WebDriverUtils.navigateToInternalURL(suffix);
        AbstractPage abstractPage = new AbstractPage();
        switch (condition){
            case guest:
                logoutAdminIfLoggedIn(abstractPage);
                logoutIfLoggedIn(abstractPage);
                WebDriverUtils.navigateToInternalURL(suffix);
                break;
            case loggedIn:
                logoutAdminIfLoggedIn(abstractPage);
                logoutIfLoggedIn(abstractPage);
                abstractPage.login(userData);
                WebDriverUtils.navigateToInternalURL(suffix);
                break;
            case admin:
                if(!abstractPage.isAdminLoggedIn()){
                    logoutIfLoggedIn(abstractPage);
                    PortalUtils.loginAdmin();
                }
                WebDriverUtils.navigateToInternalURL(suffix);
                break;
            case any:
                logoutAdminIfLoggedIn(abstractPage);
                break;
        }
    }

    private static void logoutIfLoggedIn(AbstractPage abstractPage){
        if(abstractPage.isLoggedIn()){
            abstractPage.logout();
        }
    }

    private static void logoutAdminIfLoggedIn(AbstractPage abstractPage){
        if(abstractPage.isAdminLoggedIn()){
            abstractPage.logoutAdmin();
        }
    }

	//Popups
	public static AbstractPageObject closeAllPopups(Page exceptPage){
		AbstractPageObject result = null;
        int counter = 0;
		int retries = 0;
		while((registrationNotFinished(counter))&& result==null){
            result = checkPopups(exceptPage);
			retries++;
            counter++;
            WebDriverUtils.waitFor();
            if(retries==POPUP_CHECK_RETRIES){
                registrationError();
			}
		}
		if(exceptPage!=Page.homePage && exceptPage!=Page.registrationPage && result==null){
			WebDriverUtils.runtimeExceptionWithUrl(exceptPage.toString() + " was expected, but never appeared.");
		}
        if(exceptPage==Page.gameLaunch){
            return result;
        }
		if(exceptPage == Page.homePage){
			HomePage homePage = new HomePage();
			if(!homePage.isLoggedIn()){
                registrationError();
			}
			result = homePage;
		}else if(exceptPage == Page.registrationPage){
            result = new RegistrationPage();
		}
		return result;
	}

    public static void registrationError(){
        if(WebDriverUtils.isVisible(AbstractPage.PORTLET_ERROR_XP, 0)){
            WebDriverUtils.runtimeExceptionWithUrl("Registration/Login failed : " + WebDriverUtils.getElementText(AbstractPage.PORTLET_ERROR_XP));
        }else{
            WebDriverUtils.runtimeExceptionWithUrl("Registration/Login failed");
        }
    }

    private static boolean registrationNotFinished(int counter){
        String loader = RegistrationPageAllSteps.LOADING_ANIMATION_XP;
        if(platform.equals(PLATFORM_MOBILE)){
            loader = RegistrationPageStepThree.LOADING_ANIMATION_XP;
        }
        return WebDriverUtils.isVisible(loader, 0)||
               WebDriverUtils.isVisible(AbstractPopup.ROOT_XP, 0)||
               counter<POPUP_WAIT_TIMEOUT;
    }

	private static AbstractPageObject checkPopups(Page exceptPage){
        if(WebDriverUtils.isVisible(AfterRegistrationPopup.ROOT_XP, 0)){
            return processAfterRegistrationPopup(exceptPage);
		}else if(WebDriverUtils.isVisible(LoginPopup.INPUT_USERNAME_XP, 0)){
            return processLoginPopup(exceptPage);
        }else if(WebDriverUtils.isVisible(ReadTermsAndConditionsPopup.ROOT_XP, 0)){
            return processReadTermsAndConditionsPopup(exceptPage);
        }else if(WebDriverUtils.isVisible(AcceptTermsAndConditionsPopup.TERMS_ROOT_XP, 0)){
            return processTermsAndConditionsPopup(exceptPage);
        }else if(WebDriverUtils.isVisible(ChangePasswordPopup.ROOT_XP, 0)){
            return processChangePasswordPopup(exceptPage);
        }else if(WebDriverUtils.isVisible(OkBonusPopup.LABEL_BONUS_TEXT, 0) && WebDriverUtils.isVisible(OkBonusPopup.BUTTON_OK, 0)){
            return processOkBonus(exceptPage);
        }else if(WebDriverUtils.isVisible(AcceptDeclineBonusPopup.BONUS_TEXT, 0) && WebDriverUtils.isVisible(AcceptDeclineBonusPopup.BUTTON_ACCEPT_XP, 0)){
            return processAcceptDecline(exceptPage);
        }else if(WebDriverUtils.isVisible(WelcomePopup.ROOT_XP, 0)){
            return processWelcomePopup(exceptPage);
        }else{
			return null;
		}
	}

    private static WelcomePopup processWelcomePopup(Page exceptPage){
        WelcomePopup welcomePopup=new WelcomePopup();
        if (exceptPage == Page.welcomePopup){
            return welcomePopup;
        }else{
            welcomePopup.closePopup();
            return null;
        }
    }

    private static LoginPopup processLoginPopup(Page exceptPage){
        LoginPopup loginPopup = new LoginPopup();
        if(exceptPage == Page.loginPopup){
            return loginPopup;
        }else{
            if(WebDriverUtils.isVisible(LoginPopup.LABEL_TIMEOUT_ERROR_XP, 2)){
                throw new SkipException("IMS timeout"+ WebDriverUtils.getUrlAndLogs());
            }else{
                WebDriverUtils.runtimeExceptionWithUrl("Registration/Login failed : " + WebDriverUtils.getElementText(LoginPopup.LABEL_VALIDATION_ERROR_XP));
            }
        }
        return null;
    }

    private static AfterRegistrationPopup processAfterRegistrationPopup(Page exceptPage){
        AfterRegistrationPopup afterRegistrationPopup=new AfterRegistrationPopup();
        if(exceptPage == Page.afterRegistrationPopup){
            return afterRegistrationPopup;
        }else{
            afterRegistrationPopup.closePopup();
            WebDriverUtils.waitForElementToDisappear(AfterRegistrationPopup.ROOT_XP);
            return null;
        }
    }

    private static ReadTermsAndConditionsPopup processReadTermsAndConditionsPopup(Page exceptPage){
        ReadTermsAndConditionsPopup readTermsAndConditionsPopup=new ReadTermsAndConditionsPopup();
        if(exceptPage == Page.readTermsAndConditionsPopup){
            return readTermsAndConditionsPopup;
        }else{
            readTermsAndConditionsPopup.closePopup();
            WebDriverUtils.waitForElementToDisappear(ReadTermsAndConditionsPopup.ROOT_XP);
            return null;
        }
    }

    private static AcceptTermsAndConditionsPopup processTermsAndConditionsPopup(Page exceptPage){
        AcceptTermsAndConditionsPopup acceptTermsAndConditionsPopup = new AcceptTermsAndConditionsPopup();
        if(exceptPage == Page.acceptTermsAndConditionsPopup){
            return acceptTermsAndConditionsPopup;
        }else{
            acceptTermsAndConditionsPopup.clickAccept();
            return null;
        }
    }

    private static ChangePasswordPopup processChangePasswordPopup(Page exceptPage){
        ChangePasswordPopup changePasswordPopup = new ChangePasswordPopup();
        if(exceptPage == Page.changePasswordPopup){
            return changePasswordPopup;
        }else{
            WebDriverUtils.runtimeExceptionWithUrl("Password change prompt appeared");
        }
        return null;
    }

    private static OkBonusPopup processOkBonus(Page exceptPage){
        OkBonusPopup okBonusPopup = new OkBonusPopup();
        if(exceptPage == Page.okBonus){
            return okBonusPopup;
        }else{
            okBonusPopup.close();
            return null;
        }
    }

    private static AcceptDeclineBonusPopup processAcceptDecline(Page exceptPage){
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = new AcceptDeclineBonusPopup();
        if(exceptPage == Page.acceptDeclineBonus){
            return acceptDeclineBonusPopup;
        }else{
            acceptDeclineBonusPopup.decline();
            return null;
        }
    }

    public static void navigateToAffiliateURL(ConfiguredPages page, AffiliateData affiliateData) {
        WebDriverUtils.navigateToInternalURL(page.toString() + affiliateData.getRelativeURL());
        AffiliateCookie affiliateCookie = new AffiliateCookie("");
        if (affiliateCookie.isPresent()) {
            affiliateCookie.validateValue(affiliateData);
        }   else {
            AbstractTest.results.add("Affiliate cookie should be created on affiliate URL request, but it is absent.");
        }
    }

    public static void assertGameLaunch(String gameID) {
        if(platform.equals(PLATFORM_DESKTOP)){
            new GameLaunchPopup(new AbstractPage().getMainWindowHandle(), gameID).assertGameLaunchAndClose();
        }else{
            new GameLaunchPage(gameID).assertGameLaunch();
        }
    }
}
