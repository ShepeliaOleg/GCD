package utils;

import enums.ConfiguredPages;
import enums.Page;
import enums.PlayerCondition;
import pageObjects.HomePage;
import pageObjects.InternalTagsPage;
import pageObjects.account.ChangePasswordPopup;
import pageObjects.account.LoginPopup;
import pageObjects.base.AbstractPage;
import pageObjects.base.AbstractPageObject;
import pageObjects.base.AbstractPopup;
import pageObjects.bingoSchedule.BingoSchedulePage;
import pageObjects.bonus.AcceptDeclineBonusPopup;
import pageObjects.bonus.OkBonusPopup;
import pageObjects.gamesPortlet.GamesPortletPage;
import pageObjects.inbox.InboxPage;
import pageObjects.popups.*;
import pageObjects.registration.RegistrationPage;
import springConstructors.UserData;
import utils.core.WebDriverObject;
import utils.logs.Log;
import utils.logs.LogUtils;

/**
 * User: sergiich
 * Date: 4/10/14
 */

public class NavigationUtils extends WebDriverObject{

	public static AbstractPage navigateToPortal(){
        navigateToPortal(PlayerCondition.any);
		return new AbstractPage();
	}

    public static AbstractPage navigateToPage(PlayerCondition condition, ConfiguredPages configuredPages){
        if (condition.equals(PlayerCondition.loggedIn)){
            WebDriverUtils.runtimeExceptionWithLogs("Tried to call logged in user without userdata");
        }
        return navigateToPage(condition, configuredPages, null);
    }

    public static AbstractPage navigateToPage(PlayerCondition condition, ConfiguredPages configuredPages, UserData userData){
        navigateToPortal(condition, configuredPages, userData);
        switch (configuredPages){
            case gamesList:
            case gamesStyleOne:
            case gamesStyleTwo:
            case gamesStyleThree:
            case gamesStyleFour:
            case gamesToFit:
            case gamesMinimum:
            case gamesFavourites:
            case gamesNavigationStyleNone:
            case gamesNavigationStyleRefineBy:
            case gamesNavigationStyleCategoryTabsTop:
            case gamesNavigationStyleCategoryTabsLeft:
            case gamesNavigationStyleCategoryTabsRefineByTop:
            case gamesNavigationStyleCategoryTabsRefineByLeft:return new GamesPortletPage();
            case bingoLobbyFeed:
            case bingoScheduleFeed:return new BingoSchedulePage();
            case internalTags:return new InternalTagsPage();
            case inbox:return new InboxPage();
            case bonusPage:return new InboxPage();
            default: throw new RuntimeException("Unexpected input in navigateToPage method");
        }
    }

    public static void navigateToPortal(PlayerCondition condition){
        navigateToPortal(condition, null, null);
    }

    public static void navigateToPortal(PlayerCondition condition, ConfiguredPages configuredPages, UserData userData){
        AbstractPage abstractPage;
        String suffix="";
        if(configuredPages!=null){
            suffix = configuredPages.toString();
        }
        WebDriverUtils.navigateToURL(baseUrl + suffix);
        if(WebDriverUtils.isVisible(AbstractPopup.ROOT_XP, 0)){
            abstractPage = (AbstractPage) closeAllPopups(Page.homePage);
        }else{
            abstractPage = new AbstractPage();
        }
        boolean isLoggedIn=abstractPage.isLoggedIn();
        switch (condition){
            case guest:
                if(isLoggedIn==true){
                    abstractPage.logout();
                }
                break;
            case loggedIn:
                if(isLoggedIn==true){
                    abstractPage.logout();
                }
                abstractPage.login(userData);
                WebDriverUtils.navigateToURL(baseUrl + configuredPages.toString());
                break;
        }
        LogUtils.setTimestamp();
    }

	//Popups
	public static AbstractPageObject closeAllPopups(Page exceptPage){
		AbstractPageObject result = null;
		int retries = 0;
		while(WebDriverUtils.isVisible(AbstractPopup.ROOT_XP, 4) && result==null){
			result = checkPopups(exceptPage);
			retries++;
			if(retries==10){
				WebDriverUtils.runtimeExceptionWithLogs("Unrecognizable popup appeared");
			}
		}
		if(exceptPage!=Page.homePage && exceptPage!=Page.registrationPage && result==null){
			WebDriverUtils.runtimeExceptionWithLogs(exceptPage.toString() + " was expected, but never appeared.");
		}
		if(exceptPage == Page.homePage){
			HomePage homePage = new HomePage();
			if(!homePage.isLoggedIn()){
				WebDriverUtils.runtimeExceptionWithLogs("Registration/Login failed");
			}
			result = homePage;
		}else if(exceptPage == Page.registrationPage){
			RegistrationPage registrationPage = new RegistrationPage();
			result=registrationPage;
		}
		return result;
	}

	private static AbstractPageObject checkPopups(Page exceptPage){
		if(WebDriverUtils.isVisible(LoaderPopup.ANIMATION_XP, 0)){
			WebDriverUtils.waitForElementToDisappear(LoaderPopup.ANIMATION_XP, 30);
			return null;
		}else if(WebDriverUtils.isVisible(WelcomePopup.BUTTON_OK_XP, 0)){
		    return processWelcomePopup(exceptPage);
		}else if(WebDriverUtils.isVisible(LoginPopup.INPUT_USERNAME_XP, 0)){
            processLoginPopup(exceptPage);
		}else if(WebDriverUtils.isVisible(AfterRegistrationPopup.BUTTON_DEPOSIT_XP, 0)){
			return processAfterRegistrationPopup(exceptPage);
		}else if(WebDriverUtils.isVisible(ReadTermsAndConditionsPopup.TITLE_XP, 0)){
		    return processReadTermsAndConditionsPopup(exceptPage);
		}else if(WebDriverUtils.isVisible(AcceptTermsAndConditionsPopup.IFRAME_XP, 0)){
            return processTermsAndConditionsPopup(exceptPage);
		}else if(WebDriverUtils.isVisible(ChangePasswordPopup.BUTTON_SUBMIT_XP, 0)){
            return processChangePasswordPopup(exceptPage);
		}else if(WebDriverUtils.isVisible(OkBonusPopup.BONUS_TEXT, 0) && WebDriverUtils.isVisible(OkBonusPopup.BUTTON_OK, 0)){
		    return processOkBonus(exceptPage);
		}else if(WebDriverUtils.isVisible(AcceptDeclineBonusPopup.BONUS_TEXT, 0) && WebDriverUtils.isVisible(AcceptDeclineBonusPopup.BUTTON_ACCEPT_XP, 0)){
			return processAcceptDecline(exceptPage);
		}else{
			return null;
		}
		return null;
	}

    private static WelcomePopup processWelcomePopup(Page exceptPage){
        WelcomePopup welcomePopup=new WelcomePopup();
        if (exceptPage == Page.welcomePopup){
            return welcomePopup;
        }else{
            welcomePopup.clickClose();
            return null;
        }
    }

    private static LoginPopup processLoginPopup(Page exceptPage){
        LoginPopup loginPopup = new LoginPopup();
        if(exceptPage == Page.loginPopup){
            return loginPopup;
        }else{
            WebDriverUtils.runtimeExceptionWithLogs("Registration/Login failed");
        }
        return null;
    }

    private static AfterRegistrationPopup processAfterRegistrationPopup(Page exceptPage){
        AfterRegistrationPopup afterRegistrationPopup=new AfterRegistrationPopup();
        if(exceptPage == Page.afterRegistrationPopup){
            return afterRegistrationPopup;
        }else{
            afterRegistrationPopup.clickClose();
            WebDriverUtils.waitForElementToDisappear(AfterRegistrationPopup.BUTTON_DEPOSIT_XP);
            return null;
        }
    }

    private static ReadTermsAndConditionsPopup processReadTermsAndConditionsPopup(Page exceptPage){
        ReadTermsAndConditionsPopup readTermsAndConditionsPopup=new ReadTermsAndConditionsPopup();
        if(exceptPage == Page.readTermsAndConditionsPopup){
            return readTermsAndConditionsPopup;
        }else{
            readTermsAndConditionsPopup.clickClose();
            WebDriverUtils.waitForElementToDisappear(ReadTermsAndConditionsPopup.TITLE_XP);
            return null;
        }
    }

    private static AcceptTermsAndConditionsPopup processTermsAndConditionsPopup(Page exceptPage){
        AcceptTermsAndConditionsPopup acceptTermsAndConditionsPopup = new AcceptTermsAndConditionsPopup();
        if(exceptPage == Page.acceptTermsAndConditionsPopup){
            return acceptTermsAndConditionsPopup;
        }else{
            acceptTermsAndConditionsPopup.clickClose();
            WebDriverUtils.waitForElementToDisappear(AcceptTermsAndConditionsPopup.BUTTON_ACCEPT_XP);
            return null;
        }
    }

    private static ChangePasswordPopup processChangePasswordPopup(Page exceptPage){
        ChangePasswordPopup changePasswordPopup = new ChangePasswordPopup();
        if(exceptPage == Page.changePasswordPopup){
            return changePasswordPopup;
        }else{
            WebDriverUtils.runtimeExceptionWithLogs("Password change prompt appeared");
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
}
