package utils;

import enums.ConfiguredPages;
import enums.Page;
import enums.PlayerCondition;
import enums.PromoCode;
import pageObjects.HomePage;
import pageObjects.admin.AdminPage;
import pageObjects.core.AbstractPage;
import pageObjects.core.AbstractPageObject;
import pageObjects.registration.RegistrationPage;
import springConstructors.UserData;
import utils.core.DataContainer;

public class PortalUtils {

    public static void loginUser(){
        navigateToHome().login(DataContainer.getUserData().getRegisteredUserData());
    }

    public static void loginUser(UserData userData){
        navigateToHome().login(userData);
    }

    public static AbstractPageObject loginUser(UserData userData, Page page){
        return navigateToHome().login(userData, page);
    }

    public static boolean isLoggedIn(){
        return new AbstractPage().isLoggedIn();
    }

    public static HomePage registerUser(){
        return (HomePage)registerUser(DataContainer.getUserData().getRandomUserData(), false);
    }

    public static HomePage registerUser(UserData userData){
        return (HomePage)registerUser(userData, false);
    }

    public static AbstractPageObject registerUser(UserData userData, Page expectedPage){
        return registerUser(userData, true, false, null, expectedPage);
    }

    public static AbstractPageObject registerUser(UserData userData, PromoCode promoCode){
        return registerUser(userData, true, false, promoCode, Page.homePage);
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

    public static AbstractPageObject registerUser(UserData userData, boolean isTermsAndConditionsChecked, boolean isReceiveBonusesChecked, PromoCode promoCode, Page expectedPage){
        return navigateToRegistration().registerUser(userData, isTermsAndConditionsChecked, isReceiveBonusesChecked, promoCode, expectedPage);
    }

    public static AbstractPage loginAdmin() {
        return navigateToAdmin().loginAdmin();
    }

    public static AbstractPage logout() {
        return new AbstractPage().logout();
    }

    private static HomePage navigateToHome(){
        return (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
    }

    private static AdminPage navigateToAdmin(){
        return (AdminPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.admin);
    }

    private static RegistrationPage navigateToRegistration() {
        return (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
    }
}
