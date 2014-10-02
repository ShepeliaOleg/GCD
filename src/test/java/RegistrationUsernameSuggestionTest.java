import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.registration.RegistrationPage;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

public class RegistrationUsernameSuggestionTest extends AbstractTest{

	@Autowired
	@Qualifier("userData")
	private UserData defaultUserData;

    /*#??. Suggestion does not appear on entering new username*/
    @Test(groups = {"registration","regression"})
    public void usernameSuggestionNoSuggestion(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        String username=defaultUserData.getRandomUserData().getUsername();
        assertEquals("No username suggestion", registrationPage.getUsernameSuggestion(username, defaultUserData.getRandomUserData()), "Suggestion for unique username");
    }

    /*#??. Suggestion appeared on entering already registered username*/
    @Test(groups = {"registration","regression"})
    public void usernameSuggestion(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        String username=defaultUserData.getRegisteredUserData().getUsername();
        String usernameSuggestionMessage = registrationPage.getUsernameSuggestion(username, defaultUserData.getRandomUserData());
        assertTrue(usernameSuggestionMessage.startsWith("This username is already in use. Suggested username is:"), "(Actual: '"+usernameSuggestionMessage+"')Username suggestion message contains preamble");
        assertTrue(usernameSuggestionMessage.contains(username.toUpperCase()), "(Actual: '"+usernameSuggestionMessage+"')Username suggestion message contains '" + username + "'");
    }

    /*#??. Suggestion filled out*/
    @Test(groups = {"registration","regression"})
    public void usernameSuggestionClick(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        String username=defaultUserData.getRegisteredUserData().getUsername();
        assertTrue(registrationPage.clickUsernameSuggestionAndValidateInput(username, defaultUserData.getRandomUserData()), "Suggestion entered after click");
    }

    /*#??. Suggestion disappers after refocus*/
    @Test(groups = {"registration","regression"})
    public void usernameSuggestionClickAfterFill(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        String username=defaultUserData.getRegisteredUserData().getUsername();
        registrationPage.clickUsernameSuggestionAndValidateInput(username, defaultUserData.getRandomUserData());
        registrationPage.clickUsernameField();
        WebDriverUtils.waitFor(1000);
        assertFalse(registrationPage.isSuggestionVisible(), "Suggestion tooltip still visible after click");
    }

    /*#??. Field is editable after suggestion has been used*/
    @Test(groups = {"registration","regression"})
    public void usernameSuggestionEditableAfterFill(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        String username=defaultUserData.getRegisteredUserData().getUsername();
        registrationPage.clickUsernameSuggestionAndValidateInput(username, defaultUserData.getRandomUserData());
        String newUsername = defaultUserData.getRandomUserData().getUsername();
        RegistrationPage.fillUsername(newUsername);
        assertEquals(newUsername, registrationPage.getFilledUsername(), "Username can be reentered");
    }

    /*#??. Suggestions are done on each Username field refocus*/
    @Test(groups = {"registration","regression"})
    public void usernameSuggestionAppearsEveryTime(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        String username=defaultUserData.getRegisteredUserData().getUsername();
        registrationPage.clickUsernameSuggestionAndValidateInput(username, defaultUserData.getRandomUserData());
        String newUsername = defaultUserData.getRandomUserData().getUsername();
        RegistrationPage.inputAndRefocusUsername(username);
        assertTrue(registrationPage.isSuggestionVisible(), "Suggestion visible second time");
        RegistrationPage.inputAndRefocusUsername(username);
        assertTrue(registrationPage.isSuggestionVisible(), "Suggestion visible third time");
        RegistrationPage.inputAndRefocusUsername(newUsername);
        assertFalse(registrationPage.isSuggestionVisible(), "Suggestion visible on valid email");
        RegistrationPage.inputAndRefocusUsername(username);
        assertTrue(registrationPage.isSuggestionVisible(), "Suggestion visible fourth time");
    }
}
