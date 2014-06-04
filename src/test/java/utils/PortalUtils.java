package utils;

import enums.ConfiguredPages;
import enums.PlayerCondition;
import pageObjects.HomePage;
import pageObjects.registration.RegistrationPage;
import springConstructors.UserData;

public class PortalUtils {

    public static void registerUser(UserData userData){
        navigateToRegistration().registerUser(userData);
    }

//    public static void registerUser(){
//        navigateToRegistration().registerUser();
//    }

    private static RegistrationPage navigateToRegistration() {
        return (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
    }

    public static void loginUser(UserData userData){
        navigateToHome().login(userData);
    }

//    public static void loginUser(){
//        navigateToHome().login();
//    }

    private static HomePage navigateToHome(){
        return (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
    }
}
