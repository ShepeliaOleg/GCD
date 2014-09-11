import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import springConstructors.IMS;
import springConstructors.UserData;
import springConstructors.ValidationRule;
import springConstructors.mail.MailService;
import utils.NavigationUtils;
import utils.TypeUtils;
import utils.core.AbstractTest;

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
	@Test(groups = {"smoke", "broken"})
	public void portletIsDisplayedOnPopup(){
		HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
		ForgotUsernamePopup forgotUsernamePopup = homePage.navigateToForgotUsername();

	}
    /*1. Portlet is displayed on page*/
    @Test(groups = {"smoke", "broken"})
    public void portletIsDisplayedOnPage(){
        ForgotUsernamePage forgotUsernamePage = (ForgotUsernamePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.forgotUsername);
    }
	
	/*2. Submit correct data 3. Check confirmation popup*/
	@Test(groups = {"regression", "broken"})
	public void validPasswordRecovery(){
        UserData userData = defaultUserData.getRegisteredUserData().cloneUserData();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        ForgotUsernamePopup forgotUsernamePopup = homePage.navigateToForgotUsername();
        forgotUsernamePopup.recover(userData);
		TypeUtils.assertTrueWithLogs(forgotUsernamePopup.confirmationPopupVisible(), "successfulPopupVisible");
	}

}
