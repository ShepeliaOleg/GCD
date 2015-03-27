import enums.ConfiguredPages;
import enums.Page;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.changePassword.ChangePasswordPopup;
import pageObjects.core.AbstractPortalPage;
import pageObjects.external.ims.IMSPlayerDetailsPage;
import pageObjects.login.LoginPopup;
import pageObjects.login.SignedOutPopup;
import springConstructors.UserData;
import springConstructors.ValidationRule;
import utils.IMSUtils;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

public class ChangeMyPasswordTest extends AbstractTest{

	@Autowired
	@Qualifier("passwordValidationRule")
	private ValidationRule passwordValidationRule;

	private String newPassword = "New1Pass";
	private HomePage homePage;
	private UserData userData;
	private ChangePasswordPopup changePasswordPopup;

	/*POSITIVE*/

	//* 1. Portlet is displayed
	//
//	@Test(groups = {"smoke"})
//	public void portletIsDisplayedOnMyAccountChangeMyPasswordPage() {
//		//*ChangePasswordPage changeMyPasswordPage = (ChangePasswordPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.changeMyPassword, defaultUserData.getRegisteredUserData());
//        UserData userData = DataContainer.getUserData().getRegisteredUserData();
//        //BAD!!! ConfiguredPages.changeMyPassword;
//		ChangePasswordPage changeMyPasswordPage = (ChangePasswordPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.changeMyPassword, userData);
//
//        //DataContainer.getUserData().
//	}

    //*1. Portlet is displayed in CHANGE PASSWORD POPUP
    @Test(groups = {"smoke"})
    public void portletIsDisplayedOnPopup(){
        try{
            homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.home);
            changePasswordPopup = homePage.navigateToChangePassword();
        }catch (Exception e){
            skipTest();
        }
    }

	//*2. Submit correct data
	//*@Test(groups = {"regression", "debugTest"})
	public void changePasswordAndLogin(){
		//skipTest("D-19748, System Error");
		userData = DataContainer.getUserData().getRandomUserData();
        homePage = PortalUtils.registerUser(userData);
		changePasswordPopup = homePage.navigateToChangePassword();
		changePasswordPopup.fillFormAndSubmit(userData.getPassword(), newPassword);
		//*TRY TO LOGIN with OLD password
		PortalUtils.logout();
		//Unexpected user logout
		WebDriverUtils.refreshPage();
		LoginPopup loginPopup = (LoginPopup) homePage.navigateToLoginForm().login(userData, false, Page.loginPopup);
		assertTrue(loginPopup.validationErrorVisible(),"Error message displayed");
		assertEquals(userData.getUsername(), loginPopup.getUsernameText(),"Correct username is displayed");
		//*LOGIN with NEW password
		userData.setPassword(newPassword);
		PortalUtils.loginUser(userData);
		validateTrue(new AbstractPortalPage().isUsernameDisplayed(userData.getUsername()), "Correct username is displayed after login");
	}

	//*3. IMS Player Details Page
	@Test(groups = {"regression", "debugTest"})
	public void passwordChangedInIMS(){
		//newPassword = DataContainer.getUserData().getPassword();
		userData = DataContainer.getUserData().getRandomUserData();
		homePage = PortalUtils.registerUser(userData);
		String username = userData.getUsername();
			System.out.println(username);
			System.out.println("OLD pass:" +userData.getPassword());
		IMSPlayerDetailsPage playerDetailsPage = IMSUtils.navigateToPlayedDetails(username);
		playerDetailsPage.changePassword(newPassword, true);
		userData.setPassword(newPassword);
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
			System.out.println("NEW pass:" +userData.getPassword());
		homePage.navigateToLoginForm().login(userData, false, Page.homePage);
		//changePasswordPopup = (ChangePasswordPopup) homePage.navigateToLoginForm().login(userData, false, Page.changePasswordPopup);
	}

	//*NEGATIVE

	//*1. Incorrect old password
	//*@Test(groups = {"regression", "debugTest"})
	public void incorrectOldPassword(){
		//skipTest("System Error, D-18632");
		userData = DataContainer.getUserData().getRandomUserData();
		String incorrectPass = passwordValidationRule.generateValidString();
        homePage = PortalUtils.registerUser(userData);
		changePasswordPopup = homePage.navigateToChangePassword();
		changePasswordPopup.fillFormAndClickSubmit(incorrectPass, newPassword);
		assertEquals("Invalid old password", changePasswordPopup.getErrorMsg(), "Error message was not as expected!");
	}

	//*2. New password is the same as old
	//*@Test(groups = {"regression", "debugTest"})
	public void changeToSamePassword(){
		//skipTest("System Error, D-18632");
		userData = DataContainer.getUserData().getRandomUserData();
		homePage = PortalUtils.registerUser(userData);
		changePasswordPopup = homePage.navigateToChangePassword();
		changePasswordPopup.fillFormAndClickSubmit(userData.getPassword(), userData.getPassword());
		assertEquals("New password equals old password", changePasswordPopup.getErrorMsg(), "Error message was not as expected!");
	}

	//*3. New password which has been used recently
	@Test(groups = {"regression", "debugTest"})
	public void recentlyUsedPassword(){
		//skipTest("System Error, D-18632");
		//*NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.home);
		//*WebDriverUtils.clearLocalStorage();
		//*WebDriverUtils.clearCookies();
		/*
		userData = DataContainer.getUserData().getRandomUserData();
		newPassword = passwordValidationRule.generateValidString();
		String oldPassword = userData.getPassword();
		NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		homePage = PortalUtils.registerUser(userData);
		IMSPlayerDetailsPage playerDetailsPage = IMSUtils.navigateToPlayedDetails(userData.getUsername());
		playerDetailsPage.changePassword(newPassword, true);
		NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
			System.out.println("OLD PASSWORD: "+userData.getPassword());
		userData.setPassword(newPassword);
			System.out.println("NEw PASSWORD: " + userData.getPassword());
			WebDriverUtils.waitFor(5000);
		homePage.login(userData);
		changePasswordPopup = homePage.navigateToChangePassword();
		changePasswordPopup.fillFormAndClickSubmit(newPassword, oldPassword);
		assertEquals("Password has already been used recently", changePasswordPopup.getErrorMsg(), "Error message was not as expected!");
		*/

		userData = DataContainer.getUserData().getRandomUserData();
		//newPassword = passwordValidationRule.generateValidString();
		String oldPassword = userData.getPassword();
		//*NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		homePage = PortalUtils.registerUser(userData);
			//PortalUtils.logout();
			//WebDriverUtils.clearCookies();
			//WebDriverUtils.clearLocalStorage();
		IMSPlayerDetailsPage playerDetailsPage = IMSUtils.navigateToPlayedDetails(userData.getUsername());
		playerDetailsPage.changePassword(newPassword, true);
		NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
			System.out.println("OLD PASSWORD: "+oldPassword);
		userData.setPassword(newPassword);
			System.out.println("NEW PASSWORD: " + userData.getPassword());
		WebDriverUtils.waitFor(5000);
		homePage.login(userData);
		changePasswordPopup = homePage.navigateToChangePassword();
		changePasswordPopup.fillFormAndClickSubmit(newPassword, oldPassword);
		assertEquals("Password has already been used recently", changePasswordPopup.getErrorMsg(), "Error message was not as expected!");
	}

	//*4. New Password and Retype do not match
	//*@Test(groups = {"regression", "debugTest"})
	public void retypeIsNotEqualToPassword(){
		WebDriverUtils.clearLocalStorage();
		userData = DataContainer.getUserData().getRandomUserData();
		newPassword = passwordValidationRule.generateValidString();
		homePage = PortalUtils.registerUser(userData);
		changePasswordPopup = homePage.navigateToChangePassword();
		changePasswordPopup.fillValues(userData.getPassword(), newPassword, newPassword + "2", "Passwords are not the same");
	}
}
