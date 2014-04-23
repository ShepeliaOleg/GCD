import enums.ConfiguredPages;
import enums.Page;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.account.ChangeMyPasswordPage;
import pageObjects.account.LoginPopup;
import pageObjects.account.MyAccountPage;
import pageObjects.external.ims.IMSPlayerDetailsPage;
import springConstructors.IMS;
import springConstructors.UserData;
import springConstructors.validation.ValidationRule;
import testUtils.AbstractTest;
import utils.NavigationUtils;
import utils.PortalUtils;

/**
 * User: sergiich
 * Date: 4/10/14
 */
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

	/* 1. Portlet is displayed */
	@Test(groups = {"smoke"})
	public void portletIsDisplayedOnMyAccountChangeMyPasswordPage() {
		ChangeMyPasswordPage changeMyPasswordPage = (ChangeMyPasswordPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.changeMyPassword);
	}

	/*2. Submit correct data */
	@Test(groups = {"regression"})
	public void submitCorrectData(){
		UserData userData = defaultUserData.getRandomUserData();
		String newPass = passwordValidationRule.generateValidString();
        PortalUtils.registerUser(userData);
		ChangeMyPasswordPage changeMyPasswordPage = (ChangeMyPasswordPage) NavigationUtils.navigateToPage(ConfiguredPages.changeMyPassword);
		changeMyPasswordPage = changeMyPasswordPage.changePassword(userData.getPassword(), newPass);
		Assert.assertTrue(changeMyPasswordPage.isSuccessMessagePresent());
	}

	/*3. login with new password*/
	@Test(groups = {"regression"})
	public void loginWithNewPassword(){
		UserData userData = defaultUserData.getRandomUserData();
		String newPass = passwordValidationRule.generateValidString();
        PortalUtils.registerUser(userData);
        ChangeMyPasswordPage changeMyPasswordPage = (ChangeMyPasswordPage) NavigationUtils.navigateToPage(ConfiguredPages.changeMyPassword);
		changeMyPasswordPage.changePassword(userData.getPassword(), newPass);
		userData.setPassword(newPass);
		Assert.assertTrue(NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.home, userData).isLoggedIn());
	}

	/*4.Logs*/
	//No logged  requests sent from/to portal

	/*5. IMS Player Details Page*/
	@Test(groups = {"regression"})
	public void passwordChangedInIMS(){
		UserData userData = defaultUserData.getRandomUserData();
		String newPass = passwordValidationRule.generateValidString();
        PortalUtils.registerUser(userData);
        ChangeMyPasswordPage changeMyPasswordPage = (ChangeMyPasswordPage) NavigationUtils.navigateToPage(ConfiguredPages.changeMyPassword);
		changeMyPasswordPage = changeMyPasswordPage.changePassword(userData.getPassword(), newPass);
		IMSPlayerDetailsPage playerDetailsPage = iMS.navigateToPlayedDetails(userData.getUsername());
		String imsPass = playerDetailsPage.getPassword();
		Assert.assertTrue(imsPass.equals(newPass));

	}

	/*NEGATIVE*/

	/*1. Incorrect old password*/
	@Test(groups = {"regression"})
	public void incorrectOldPassword(){
		UserData userData = defaultUserData.getRandomUserData();
		String newPass = passwordValidationRule.generateValidString();
		String incorrectPass = passwordValidationRule.generateValidString();
        PortalUtils.registerUser(userData);
        ChangeMyPasswordPage changeMyPasswordPage = (ChangeMyPasswordPage) NavigationUtils.navigateToPage(ConfiguredPages.changeMyPassword);
		changeMyPasswordPage = changeMyPasswordPage.changePassword(incorrectPass, newPass);
		Assert.assertTrue(changeMyPasswordPage.isErrorPresent());
	}

	/*2. New password is the same as old*/
	@Test(groups = {"regression"})
	public void changeToSamePassword(){
		UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        ChangeMyPasswordPage changeMyPasswordPage = (ChangeMyPasswordPage) NavigationUtils.navigateToPage(ConfiguredPages.changeMyPassword);
		changeMyPasswordPage = changeMyPasswordPage.changePassword(userData.getPassword(), userData.getPassword());
		Assert.assertTrue(changeMyPasswordPage.isErrorPresent());
	}

	/*3. New password which has been used recently*/
	@Test(groups = {"regression"})
	public void recentlyUsedPassword(){
		boolean result = false;
		UserData userData = defaultUserData.getRandomUserData();
		String newPass = passwordValidationRule.generateValidString();
		String oldPass = userData.getPassword();
        PortalUtils.registerUser(userData);
        ChangeMyPasswordPage changeMyPasswordPage = (ChangeMyPasswordPage) NavigationUtils.navigateToPage(ConfiguredPages.changeMyPassword);
		changeMyPasswordPage = changeMyPasswordPage.changePassword(userData.getPassword(), newPass);
		if(changeMyPasswordPage.isSuccessMessagePresent()){
			changeMyPasswordPage = changeMyPasswordPage.changePassword(newPass, oldPass);
			result = changeMyPasswordPage.isErrorPresent();
		}else{
			result = false;
		}
        Assert.assertTrue(result);
	}

	/*4. New Password and Retype do not match*/
	@Test(groups = {"regression"})
	public void retypeIsNotEqualToPassword(){
		UserData userData = defaultUserData.getRandomUserData();
		String newPass = passwordValidationRule.generateValidString();
		String incorrectPass = passwordValidationRule.generateValidString();
        PortalUtils.registerUser(userData);
        ChangeMyPasswordPage changeMyPasswordPage = (ChangeMyPasswordPage) NavigationUtils.navigateToPage(ConfiguredPages.changeMyPassword);
		changeMyPasswordPage = changeMyPasswordPage.changePassword(userData.getPassword(), newPass, incorrectPass);
		Assert.assertTrue(changeMyPasswordPage.isFieldValidatorPresent());
	}

	/*5. Change password and try to log in with your old password*/
	@Test(groups = {"regression"})
	public void logInWIthOldPassword(){
		UserData userData = defaultUserData.getRandomUserData();
		String newPass = passwordValidationRule.generateValidString();
        PortalUtils.registerUser(userData);
        ChangeMyPasswordPage changeMyPasswordPage = (ChangeMyPasswordPage) NavigationUtils.navigateToPage(ConfiguredPages.changeMyPassword);
		changeMyPasswordPage.changePassword(userData.getPassword(), newPass);
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		LoginPopup loginPopup = (LoginPopup) homePage.login(userData, Page.loginPopup);
	}

    /*VALIDATION*/

	/*1. Old password field validation*/
	@Test(groups = {"validation"})
	public void oldPasswordFieldValidation(){
        ChangeMyPasswordPage changeMyPasswordPage = (ChangeMyPasswordPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.changeMyPassword);
		changeMyPasswordPage.validateOldPassword(passwordValidationRule);
	}

    /*2. New password field validation*/
	@Test(groups = {"validation"})
	public void newPasswordFieldValidation(){
        ChangeMyPasswordPage changeMyPasswordPage = (ChangeMyPasswordPage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.changeMyPassword);
		changeMyPasswordPage.validateNewPassword(passwordValidationRule);
	}
}
