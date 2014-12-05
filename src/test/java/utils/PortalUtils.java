package utils;

import enums.*;
import pageObjects.HomePage;
import pageObjects.admin.AdminPageAdmin;
import pageObjects.admin.AdminPageGuest;
import pageObjects.admin.settings.SettingsPopup;
import pageObjects.core.AbstractLiferayPopup;
import pageObjects.core.AbstractPageObject;
import pageObjects.core.AbstractPortalPage;
import pageObjects.registration.RegistrationPage;
import springConstructors.UserData;
import utils.core.AbstractTest;
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
        return new AbstractPortalPage().isLoggedIn();
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

    public static AbstractPortalPage loginAdmin() {
        return navigateToAdmin().loginAdmin();
    }

    public static AbstractPortalPage logout() {
        return new AbstractPortalPage().logout();
    }

    private static HomePage navigateToHome(){
        return (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
    }

    public static AdminPageGuest navigateToAdmin(){
        return (AdminPageGuest) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.admin);
    }

    private static RegistrationPage navigateToRegistration() {
        return (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
    }

    public static void openSettings() {
        openSettings(SettingsTab.none);
    }

    public static AbstractLiferayPopup openSettings(SettingsTab tab) {
        AdminPageAdmin adminPageAdmin = (AdminPageAdmin) NavigationUtils.navigateToPage(PlayerCondition.admin, ConfiguredPages.admin);
        SettingsPopup settingsPopup = adminPageAdmin.openSettings();
        switch (tab) {
            case siteConfiguration:
                return settingsPopup.openSiteConfiguration();
            case headerConfiguration:
            case footerConfiguration:
            case popupsConfiguration:
            case gamesConfiguration:
            case supportChatConfiguration:
            case countryCurrencyConfiguration:
            case galaxyConfiguration:
            case mobileConfiguration:
            case cashierConfiguration:
            case otherConfiguration:
            case servicesConfiguration:
            case sessionConfiguration:
            case dynamicTagsConfiguration:
            case quickMenuConfiguration:
            case portalConfiguration:
            case cdnPurge:
            case serverAdministration:
            case cacheConfiguration:
            case regulations:
            case ssoPasOpenAPI:
            case responsibleGaming:
            case biIntegrationConfiguration:
            default:
                AbstractTest.failTest("Unexpected input in openSettings method");
                return null;
        }
    }
}
