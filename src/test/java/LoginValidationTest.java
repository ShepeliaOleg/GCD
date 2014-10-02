import enums.ConfiguredPages;
import enums.Page;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.core.AbstractPage;
import pageObjects.forgotPassword.ForgotPasswordPopup;
import pageObjects.login.LoginPopup;
import pageObjects.registration.RegistrationPage;
import springConstructors.IMS;
import springConstructors.UserData;
import springConstructors.ValidationRule;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

public class LoginValidationTest extends AbstractTest {

	@Autowired
	@Qualifier("usernameValidationRule")
	private ValidationRule usernameValidationRule;

	@Autowired
	@Qualifier("passwordValidationRule")
	private ValidationRule passwordValidationRule;

	/* VALIDATION */

//	/*1. Username in header*/
//	@Test(groups = {"validation"})
//	public void usernameFieldValidationInHeader() {
//		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
//		homePage.loggedOutHeader().validateUsername(usernameValidationRule);
//  	}
//
//	/*2. Password in header*/
//	@Test(groups = {"validation"})
//	public void passwordFieldValidationInHeader() {
//		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
//		homePage.loggedOutHeader().validatePassword(passwordValidationRule);
//	}
//
//	/*3. Username in login popup*/
//	@Test(groups = {"validation"})
//	public void usernameFieldValidationInLoginPopup() {
//		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
//		LoginPopup loginPopup = homePage.navigateToLoginForm();
//		loginPopup.validateUsername(usernameValidationRule);
//	}
//
//	/*4. Password in login popup*/
//	@Test(groups = {"validation"})
//	public void passwordFieldValidationInLoginPopup() {
//		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
//		LoginPopup loginPopup = homePage.navigateToLoginForm();
//		loginPopup.validatePassword(passwordValidationRule);
//	}
}
