package utils;

import enums.ConfiguredPages;
import enums.PlayerCondition;
import pageObjects.registration.RegistrationPage;
import springConstructors.UserData;
import utils.core.WebDriverObject;

/**
 * Created by sergiich on 4/23/14.
 */
public class PortalUtils extends WebDriverObject {

    public static void registerUser(UserData userData){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register, userData);
        registrationPage.registerUser(userData);
    }
}
