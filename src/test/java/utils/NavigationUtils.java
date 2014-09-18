package utils;

import enums.ConfiguredPages;
import enums.Page;
import enums.PlayerCondition;
import org.testng.SkipException;
import pageObjects.HomePage;
import pageObjects.InternalTagsPage;
import pageObjects.account.BalancePage;
import pageObjects.account.ChangeMyDetailsPage;
import pageObjects.banner.BannerPage;
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
import pageObjects.gamesPortlet.GamesPortletPage;
import pageObjects.generalPopups.LoaderPopup;
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
import pageObjects.registration.threeStep.RegistrationPageStepOne;
import pageObjects.registration.threeStep.RegistrationPageStepThree;
import pageObjects.responsibleGaming.ResponsibleGamingPage;
import springConstructors.UserData;
import utils.core.WebDriverObject;

public class NavigationUtils extends WebDriverObject{

    private static final int POPUP_CHECK_RETRIES = 30;
    private static final int POPUP_WAIT_TIMEOUT = 10;
    private static String LOADING_ANIMATION;

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
            case balance:                                       return new BalancePage();
            case banner5seconds:
            case bannerGame:
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
            case register:                                      return new RegistrationPage();
            case referAFriend:                                  return new ReferAFriendPage();
            case responsibleGaming:
            case selfExclusion:                                 return new ResponsibleGamingPage();
            default: throw new RuntimeException("Unexpected input in navigateToPage method");
        }
    }

    private static void navigateToPortal(PlayerCondition condition, ConfiguredPages configuredPages, UserData userData){
        AbstractPage abstractPage;
        String suffix=configuredPages.toString();
//        LogUtils.setTimestamp();
        WebDriverUtils.navigateToInternalURL(suffix);
//        abstractPage = (AbstractPage) closeAllPopups(Page.homePage);
        abstractPage = new AbstractPage();
        boolean isLoggedIn=abstractPage.isLoggedIn();
        switch (condition){
            case guest:
                if(isLoggedIn==true){
                    abstractPage.logout();
                }
                WebDriverUtils.navigateToInternalURL(suffix);
                break;
            case loggedIn:
                if(isLoggedIn==true){
                    abstractPage.logout();
                }
                abstractPage.login(userData);
                WebDriverUtils.navigateToInternalURL(suffix);
                break;
        }
    }

	//Popups
	public static AbstractPageObject closeAllPopups(Page exceptPage){
		AbstractPageObject result = null;
		int retries = 0;
		while((registrationNotFinished())&& result==null){
            WebDriverUtils.waitFor(1000);
            result = checkPopups(exceptPage);
			retries++;
			if(retries==POPUP_CHECK_RETRIES){
				WebDriverUtils.runtimeExceptionWithUrl("Registration/Login takes too long");
			}
		}
		if(exceptPage!=Page.homePage && exceptPage!=Page.registrationPage && result==null){
			WebDriverUtils.runtimeExceptionWithUrl(exceptPage.toString() + " was expected, but never appeared.");
		}
		if(exceptPage == Page.homePage){
			HomePage homePage = new HomePage();
			if(!homePage.isLoggedIn()){
                processRegistrationError();
			}
			result = homePage;
		}else if(exceptPage == Page.registrationPage){
			RegistrationPage registrationPage = new RegistrationPage();
			result=registrationPage;
		}
		return result;
	}

    private static boolean registrationNotFinished(){
        if(platform.equals(PLATFORM_MOBILE)){
            LOADING_ANIMATION = RegistrationPageStepThree.LOADING_ANIMATION_XP;
        }else {
            LOADING_ANIMATION = RegistrationPageAllSteps.LOADING_ANIMATION_XP;
        }
        return WebDriverUtils.isVisible(AbstractPopup.ROOT_XP, 3) ||
                WebDriverUtils.isVisible(LOADING_ANIMATION, 1);
    }

	private static AbstractPageObject checkPopups(Page exceptPage){
		if(WebDriverUtils.isVisible(LOADING_ANIMATION, 0)){
			WebDriverUtils.waitForElementToDisappear(LOADING_ANIMATION, 30);
			return null;
        }else if(WebDriverUtils.isVisible(AfterRegistrationPopup.AFTER_REG_ROOT_XP, 0)){
            return processAfterRegistrationPopup(exceptPage);
		}else if(WebDriverUtils.isVisible(AbstractPopup.BUTTON_CLOSE_XP, 0)&&!WebDriverUtils.isVisible(AbstractPopup.BUTTON_ACCEPT_XP, 0)){
		    return processWelcomePopup(exceptPage);
		}else if(WebDriverUtils.isVisible(LoginPopup.INPUT_USERNAME_XP, 0)){
            return processLoginPopup(exceptPage);
		}else if(WebDriverUtils.isVisible(ReadTermsAndConditionsPopup.TITLE_XP, 0)){
		    return processReadTermsAndConditionsPopup(exceptPage);
		}else if(WebDriverUtils.isVisible(AcceptTermsAndConditionsPopup.TERMS_ROOT_XP, 0)){
            return processTermsAndConditionsPopup(exceptPage);
		}else if(WebDriverUtils.isVisible(ChangePasswordPopup.BUTTON_SUBMIT_XP, 0)){
            return processChangePasswordPopup(exceptPage);
		}else if(WebDriverUtils.isVisible(OkBonusPopup.LABEL_BONUS_TEXT, 0) && WebDriverUtils.isVisible(OkBonusPopup.BUTTON_OK, 0)){
		    return processOkBonus(exceptPage);
		}else if(WebDriverUtils.isVisible(AcceptDeclineBonusPopup.BONUS_TEXT, 0) && WebDriverUtils.isVisible(AcceptDeclineBonusPopup.BUTTON_ACCEPT_XP, 0)){
			return processAcceptDecline(exceptPage);
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
            WebDriverUtils.waitForElementToDisappear(AfterRegistrationPopup.AFTER_REG_ROOT_XP);
            return null;
        }
    }

    private static ReadTermsAndConditionsPopup processReadTermsAndConditionsPopup(Page exceptPage){
        ReadTermsAndConditionsPopup readTermsAndConditionsPopup=new ReadTermsAndConditionsPopup();
        if(exceptPage == Page.readTermsAndConditionsPopup){
            return readTermsAndConditionsPopup;
        }else{
            readTermsAndConditionsPopup.closePopup();
            WebDriverUtils.waitForElementToDisappear(ReadTermsAndConditionsPopup.TITLE_XP);
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

    private static void processRegistrationError(){
        String errorXP = AbstractPage.PORTLET_ERROR_XP;
        if(WebDriverUtils.isVisible(errorXP, 2)){
            String errorText = WebDriverUtils.getElementText(errorXP);
            if(errorText.contains("timeout")){
                throw new SkipException("IMS timeout"+ WebDriverUtils.getUrlAndLogs());
            }else {
                WebDriverUtils.runtimeExceptionWithUrl("Registration/Login failed : " + errorText);
            }
        }else{
            WebDriverUtils.runtimeExceptionWithUrl("Registration/Login failed");
        }
    }


}
