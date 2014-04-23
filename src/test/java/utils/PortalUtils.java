package utils;

import enums.ConfiguredPages;
import enums.PlayerCondition;
import pageObjects.HomePage;
import pageObjects.registration.RegistrationPage;
import springConstructors.UserData;

/**
 * Created by sergiich on 4/23/14.
 */

public class PortalUtils {

    public static void registerUser(UserData userData){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        registrationPage.registerUser(userData);
    }

    public static void loginUser(UserData userData){
        navigateToHome().login(userData);
    }

    public static void loginUser(){
        navigateToHome().login();
    }

    private static HomePage navigateToHome(){
        return (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
    }
}
