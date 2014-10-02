import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.changePassword.ChangePasswordPopup;
import springConstructors.IMS;
import springConstructors.UserData;
import springConstructors.ValidationRule;
import utils.NavigationUtils;
import utils.core.AbstractTest;

public class ChangeMyPasswordTest extends AbstractTest{

	@Autowired
	@Qualifier("passwordValidationRule")
	private ValidationRule passwordValidationRule;

	@Autowired
	@Qualifier("iMS")
	private IMS iMS;

	@Autowired
	@Qualifier("userData")
	private UserData defaultUserData;

	/*POSITIVE*/
//
//	/* 1. Portlet is displayed */
//	@Test(groups = {"smoke"})
//	public void portletIsDisplayedOnMyAccountChangeMyPasswordPage() {
//		ChangePasswordPage changeMyPasswordPage = (ChangePasswordPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.changeMyPassword, defaultUserData.getRegisteredUserData());
//	}

    /*1. Portlet is displayed in popup*/
    @Test(groups = {"smoke"})
    public void portletIsDisplayedOnPopup(){
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.home, defaultUserData.getRegisteredUserData());
        ChangePasswordPopup changePasswordPopup = homePage.navigateToChangePassword();

    }
//
//	/*2. Submit correct data */
//	@Test(groups = {"regression"})
//	public void submitCorrectData(){
//		UserData userData = defaultUserData.getRandomUserData();
//		String newPass = passwordValidationRule.generateValidString();
//        PortalUtils.registerUser(userData);
//		ChangePasswordPage changeMyPasswordPage = (ChangePasswordPage) NavigationUtils.navigateToPage(ConfiguredPages.changeMyPassword);
//		changeMyPasswordPage = changeMyPasswordPage.changePassword(userData.getPassword(), newPass);
//		TypeUtils.assertTrueWithLogs(changeMyPasswordPage.isSuccessMessagePresent(),"success message present");
//	}
//
//	/*3. login with new password*/
//	@Test(groups = {"regression"})
//	public void loginWithNewPassword(){
//		UserData userData = defaultUserData.getRandomUserData();
//		String newPass = passwordValidationRule.generateValidString();
//        PortalUtils.registerUser(userData);
//        ChangePasswordPage changeMyPasswordPage = (ChangePasswordPage) NavigationUtils.navigateToPage(ConfiguredPages.changeMyPassword);
//		changeMyPasswordPage.changePassword(userData.getPassword(), newPass);
//		userData.setPassword(newPass);
//		TypeUtils.assertTrueWithLogs(NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.home, userData).isLoggedIn(), "Logged in");
//	}
//
//	/*5. IMS Player Details Page*/
//	@Test(groups = {"regression"})
//	public void passwordChangedInIMS(){
//		UserData userData = defaultUserData.getRandomUserData();
//		String newPass = passwordValidationRule.generateValidString();
//        PortalUtils.registerUser(userData);
//        ChangePasswordPage changeMyPasswordPage = (ChangePasswordPage) NavigationUtils.navigateToPage(ConfiguredPages.changeMyPassword);
//		changeMyPasswordPage = changeMyPasswordPage.changePassword(userData.getPassword(), newPass);
//		IMSPlayerDetailsPage playerDetailsPage = iMS.navigateToPlayedDetails(userData.getUsername());
//		String imsPass = playerDetailsPage.getPassword();
//		TypeUtils.assertTrueWithLogs(imsPass.equals(newPass), "Password is changed on ims");
//
//	}
//
//	/*NEGATIVE*/
//
//	/*1. Incorrect old password*/
//	@Test(groups = {"regression"})
//	public void incorrectOldPassword(){
//		UserData userData = defaultUserData.getRandomUserData();
//		String newPass = passwordValidationRule.generateValidString();
//		String incorrectPass = passwordValidationRule.generateValidString();
//        PortalUtils.registerUser(userData);
//        ChangePasswordPage changeMyPasswordPage = (ChangePasswordPage) NavigationUtils.navigateToPage(ConfiguredPages.changeMyPassword);
//		changeMyPasswordPage = changeMyPasswordPage.changePassword(incorrectPass, newPass);
//		TypeUtils.assertTrueWithLogs(changeMyPasswordPage.isErrorPresent(),"Error is present");
//	}
//
//	/*2. New password is the same as old*/
//	@Test(groups = {"regression"})
//	public void changeToSamePassword(){
//		UserData userData = defaultUserData.getRandomUserData();
//        PortalUtils.registerUser(userData);
//        ChangePasswordPage changeMyPasswordPage = (ChangePasswordPage) NavigationUtils.navigateToPage(ConfiguredPages.changeMyPassword);
//		changeMyPasswordPage = changeMyPasswordPage.changePassword(userData.getPassword(), userData.getPassword());
//		TypeUtils.assertTrueWithLogs(changeMyPasswordPage.isErrorPresent(),"Error is present");
//	}
//
//	/*3. New password which has been used recently*/
//	@Test(groups = {"regression"})
//	public void recentlyUsedPassword(){
//		boolean result = false;
//		UserData userData = defaultUserData.getRandomUserData();
//		String newPass = passwordValidationRule.generateValidString();
//		String oldPass = userData.getPassword();
//        PortalUtils.registerUser(userData);
//        ChangePasswordPage changeMyPasswordPage = (ChangePasswordPage) NavigationUtils.navigateToPage(ConfiguredPages.changeMyPassword);
//		changeMyPasswordPage = changeMyPasswordPage.changePassword(userData.getPassword(), newPass);
//		if(changeMyPasswordPage.isSuccessMessagePresent()){
//			changeMyPasswordPage = changeMyPasswordPage.changePassword(newPass, oldPass);
//			result = changeMyPasswordPage.isErrorPresent();
//		}else{
//			result = false;
//		}
//        TypeUtils.assertTrueWithLogs(result,"Error is present");
//	}
//
//	/*4. New Password and Retype do not match*/
//	@Test(groups = {"regression"})
//	public void retypeIsNotEqualToPassword(){
//		UserData userData = defaultUserData.getRandomUserData();
//		String newPass = passwordValidationRule.generateValidString();
//		String incorrectPass = passwordValidationRule.generateValidString();
//        PortalUtils.registerUser(userData);
//        ChangePasswordPage changeMyPasswordPage = (ChangePasswordPage) NavigationUtils.navigateToPage(ConfiguredPages.changeMyPassword);
//		changeMyPasswordPage = changeMyPasswordPage.changePassword(userData.getPassword(), newPass, incorrectPass);
//		TypeUtils.assertTrueWithLogs(changeMyPasswordPage.isFieldValidatorPresent(),"Field validator present");
//	}
//
//	/*5. Change password and try to log in with your old password*/
//	@Test(groups = {"regression"})
//	public void logInWIthOldPassword(){
//		UserData userData = defaultUserData.getRandomUserData();
//		String newPass = passwordValidationRule.generateValidString();
//        PortalUtils.registerUser(userData);
//        ChangePasswordPage changeMyPasswordPage = (ChangePasswordPage) NavigationUtils.navigateToPage(ConfiguredPages.changeMyPassword);
//		changeMyPasswordPage.changePassword(userData.getPassword(), newPass);
//        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
//		LoginPopup loginPopup = (LoginPopup) homePage.login(userData, Page.loginPopup);
//	}
}
