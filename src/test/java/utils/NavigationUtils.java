package utils;

import enums.ConfiguredPages;
import enums.Page;
import pageObjects.HomePage;
import pageObjects.account.ChangePasswordPopup;
import pageObjects.account.LoginPopup;
import pageObjects.base.AbstractPageObject;
import pageObjects.base.AbstractPopup;
import pageObjects.bingoSchedule.BingoSchedulePage;
import pageObjects.bonus.AcceptDeclineBonusPopup;
import pageObjects.bonus.OkBonusPopup;
import pageObjects.popups.*;
import pageObjects.registration.RegistrationPage;
import utils.core.WebDriverObject;
import utils.logs.LogUtils;

/**
 * User: sergiich
 * Date: 4/10/14
 */

public class NavigationUtils extends WebDriverObject{

	public static HomePage navigateToHome(){
		return navigateToPortal(false);
	}

	public static HomePage navigateToPortal(boolean forceLogout){
		HomePage homePage;
		WebDriverUtils.navigateToURL(baseUrl);
		if(WebDriverUtils.isVisible(AbstractPopup.ROOT_XP, 0)){
			homePage = (HomePage)closeAllPopups(Page.homePage);
		}else{
			homePage = new HomePage();
		}
		if(forceLogout){
			if(homePage.isLoggedIn()){
				homePage=(HomePage)homePage.logout();
			}
		}
		LogUtils.setTimestamp();
		return homePage;
	}


    public static BingoSchedulePage navigateToBingoSchedulePage(boolean forceLogout, ConfiguredPages feed) {
        return navigateToPortal(forceLogout).navigateToBingoSchedulePage(feed);
    }

    public static RegistrationPage navigateToRegistration(boolean forceLogout) {
        return navigateToPortal(forceLogout).navigateToRegistration();
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
			WelcomePopup welcomePopup=new WelcomePopup();
			if (exceptPage == Page.welcomePopup){
				return welcomePopup;
			}else{
				welcomePopup.clickClose();
				return null;
			}
		}else if(WebDriverUtils.isVisible(LoginPopup.INPUT_USERNAME_XP, 0)){
			LoginPopup loginPopup = new LoginPopup();
			if(exceptPage == Page.loginPopup){
				return loginPopup;
			}else{
				WebDriverUtils.runtimeExceptionWithLogs("Registration/Login failed");
			}
		}else if(WebDriverUtils.isVisible(AfterRegistrationPopup.BUTTON_DEPOSIT_XP, 0)){
			AfterRegistrationPopup afterRegistrationPopup=new AfterRegistrationPopup();
			if(exceptPage == Page.afterRegistrationPopup){
				return afterRegistrationPopup;
			}else{
				afterRegistrationPopup.clickClose();
				WebDriverUtils.waitForElementToDisappear(AfterRegistrationPopup.BUTTON_DEPOSIT_XP);
				return null;
			}
		}else if(WebDriverUtils.isVisible(ReadTermsAndConditionsPopup.TITLE_XP, 0)){
			ReadTermsAndConditionsPopup readTermsAndConditionsPopup=new ReadTermsAndConditionsPopup();
			if(exceptPage == Page.readTermsAndConditionsPopup){
				return readTermsAndConditionsPopup;
			}else{
				readTermsAndConditionsPopup.clickClose();
				WebDriverUtils.waitForElementToDisappear(ReadTermsAndConditionsPopup.TITLE_XP);
				return null;
			}
		}else if(WebDriverUtils.isVisible(AcceptTermsAndConditionsPopup.IFRAME_XP, 0)){
			AcceptTermsAndConditionsPopup acceptTermsAndConditionsPopup = new AcceptTermsAndConditionsPopup();
			if(exceptPage == Page.acceptTermsAndConditionsPopup){
				return acceptTermsAndConditionsPopup;
			}else{
				acceptTermsAndConditionsPopup.clickClose();
				WebDriverUtils.waitForElementToDisappear(AcceptTermsAndConditionsPopup.BUTTON_ACCEPT_XP);
				return null;
			}
		}else if(WebDriverUtils.isVisible(ChangePasswordPopup.BUTTON_SUBMIT_XP, 0)){
			ChangePasswordPopup changePasswordPopup = new ChangePasswordPopup();
			if(exceptPage == Page.changePasswordPopup){
				return changePasswordPopup;
			}else{
				WebDriverUtils.runtimeExceptionWithLogs("Password change prompt appeared");
			}
		}else if(WebDriverUtils.isVisible(OkBonusPopup.BONUS_TEXT, 0) && WebDriverUtils.isVisible(OkBonusPopup.BUTTON_OK, 0)){
			OkBonusPopup okBonusPopup = new OkBonusPopup();
			if(exceptPage == Page.okBonus){
				return okBonusPopup;
			}else{
				okBonusPopup.close();
				return null;
			}
		}else if(WebDriverUtils.isVisible(AcceptDeclineBonusPopup.BONUS_TEXT, 0) && WebDriverUtils.isVisible(AcceptDeclineBonusPopup.BUTTON_ACCEPT_XP, 0)){
			AcceptDeclineBonusPopup acceptDeclineBonusPopup = new AcceptDeclineBonusPopup();
			if(exceptPage == Page.acceptDeclineBonus){
				return acceptDeclineBonusPopup;
			}else{
				acceptDeclineBonusPopup.decline();
				closeAllPopups(Page.homePage);
				return null;
			}
		}else{
			return null;
		}
		return null;
	}
}
