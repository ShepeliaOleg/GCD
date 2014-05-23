import enums.ConfiguredPages;
import enums.LogCategory;
import enums.Page;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.account.ChangePasswordPopup;
import pageObjects.account.ChangedPasswordPopup;
import pageObjects.account.LoginPopup;
import pageObjects.external.ims.IMSPlayerDetailsPage;
import pageObjects.external.mail.MailServicePage;
import pageObjects.forgotPassword.*;
import pageObjects.registration.RegistrationPage;
import springConstructors.IMS;
import springConstructors.UserData;
import springConstructors.mail.MailService;
import springConstructors.validation.ValidationRule;
import testUtils.AbstractTest;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.logs.Log;
import utils.logs.LogEntry;
import utils.logs.LogUtils;

public class ForgotUsernameTest extends AbstractTest{

    @Autowired
    @Qualifier("userData")
    private UserData defaultUserData;

    @Autowired
	@Qualifier("mailinator")
	private MailService mailService;

	@Autowired
	@Qualifier("emailValidationRule")
	private ValidationRule emailValidationRule;

	@Autowired
	@Qualifier("usernameValidationRule")
	private ValidationRule usernameValidationRule;

	@Autowired
	@Qualifier("iMS")
	private IMS iMS;

	/*POSITIVE*/

	/*1. Portlet is displayed in popup*/
	@Test(groups = {"broken"})
	public void portletIsDisplayedOnPopup(){
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		ForgotUsernamePopup forgotUsernamePopup = homePage.navigateToForgotUsername();

	}
    /*1. Portlet is displayed on page*/
    @Test(groups = {"broken"})
    public void portletIsDisplayedOnPage(){
        ForgotUsernamePage forgotUsernamePage = (ForgotUsernamePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.forgotUsername);
    }
	
	/*2. Submit correct data 3. Check confirmation popup*/
	@Test(groups = {"broken"})
	public void validPasswordRecovery(){
        // prepare userdata
        UserData userData = defaultUserData.getRegisteredUserData().cloneUserData();

        // remind username for registered user
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotUsernamePopup forgotUsernamePopup = homePage.navigateToForgotUsername();
        forgotUsernamePopup.recover(userData);

        boolean successfulPopupVisible=forgotUsernamePopup.confirmationPopupVisible();

		Assert.assertTrue(successfulPopupVisible == true);
	}

}
