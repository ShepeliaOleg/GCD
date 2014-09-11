package utils;

import enums.ConfiguredPages;
import enums.Page;
import enums.PlayerCondition;
import pageObjects.HomePage;
import pageObjects.core.AbstractPageObject;
import pageObjects.registration.classic.RegistrationPageAllSteps;
import pageObjects.registration.threeStep.RegistrationPageStepOne;
import springConstructors.UserData;
import utils.core.WebDriverObject;

public class PortalUtils extends WebDriverObject{

    private static HomePage navigateToHome(){
        return (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
    }

    public static void loginUser(UserData userData){
        navigateToHome().login(userData);
    }

    private static RegistrationPageAllSteps navigateToRegistration() {
        return (RegistrationPageAllSteps) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
    }

    private static RegistrationPageStepOne navigateToRegistrationStepOne() {
        return (RegistrationPageStepOne) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.registerSteps);
    }

    public static HomePage registerUser(UserData userData){
        return (HomePage)registerUser(userData, false);
    }

    public static AbstractPageObject registerUser(UserData userData, Page expectedPage){
        return registerUser(userData, true, false, null, expectedPage);
    }

    public static AbstractPageObject registerUser(UserData userData, String bonusCode){
        return registerUser(userData, true, false, bonusCode, Page.homePage);
    }

    public static AbstractPageObject registerUser(UserData userData, boolean isReceiveBonusesChecked){
        return registerUser(userData, true, isReceiveBonusesChecked, null, Page.homePage);
    }

    public static AbstractPageObject registerUser(UserData userData, boolean isTermsAndConditionsChecked, boolean isReceiveBonusesChecked){
        Page page;
        if(!isTermsAndConditionsChecked){
            page=Page.registrationPage;
        }else{
            page=Page.homePage;
        }
        return registerUser(userData, isTermsAndConditionsChecked, isReceiveBonusesChecked, null, page);
    }

    public static AbstractPageObject registerUser(UserData userData, boolean isTermsAndConditionsChecked, boolean isReceiveBonusesChecked, String bonusCode, Page expectedPage){
        if(platform.equals(PLATFORM_DESKTOP)){
            return navigateToRegistration().registerUser(userData, isTermsAndConditionsChecked, isReceiveBonusesChecked, bonusCode, expectedPage);
        }else {
            return navigateToRegistrationStepOne().registerUser(userData, isTermsAndConditionsChecked, isReceiveBonusesChecked, bonusCode, expectedPage);
        }
    }
}
