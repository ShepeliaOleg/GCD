import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.testng.annotations.Test;
import pageObjects.registration.RegistrationPage;
import utils.NavigationUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

public class RegistrationUsernameSuggestionTest extends AbstractTest{

    /*#??. Suggestion does not appear on entering new username*/
    @Test(groups = {"registration","regression"})
    public void usernameSuggestionNoSuggestion(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        String username=DataContainer.getUserData().getRandomUserData().getUsername();
        assertEquals("No username suggestion", registrationPage.getUsernameSuggestion(username, DataContainer.getUserData().getRandomUserData()), "Suggestion for unique username");
    }

    /*#??. Suggestion appeared on entering already registered username*/
    @Test(groups = {"registration","regression"})
    public void usernameSuggestion(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        String username= DataContainer.getUserData().getRegisteredUserData().getUsername();
        String usernameSuggestionMessage = registrationPage.getUsernameSuggestion(username, DataContainer.getUserData().getRandomUserData());
        assertTrue(usernameSuggestionMessage.startsWith("This username is already in use. Suggested username is:"), "(Actual: '"+usernameSuggestionMessage+"')Username suggestion message contains preamble");
        assertTrue(usernameSuggestionMessage.contains(username.toUpperCase()), "(Actual: '"+usernameSuggestionMessage+"')Username suggestion message contains '" + username + "'");
    }

    /*#??. Suggestion filled out*/
    @Test(groups = {"registration","regression"})
    public void usernameSuggestionClick(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        String username=DataContainer.getUserData().getRegisteredUserData().getUsername();
        assertTrue(registrationPage.clickUsernameSuggestionAndValidateInput(username, DataContainer.getUserData().getRandomUserData()), "Suggestion entered after click");
    }

    /*#??. Suggestion disappers after refocus*/
    @Test(groups = {"registration","regression"})
    public void usernameSuggestionClickAfterFill(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        String username=DataContainer.getUserData().getRegisteredUserData().getUsername();
        registrationPage.clickUsernameSuggestionAndValidateInput(username, DataContainer.getUserData().getRandomUserData());
        registrationPage.clickUsernameField();
        WebDriverUtils.waitFor(1000);
        assertFalse(registrationPage.isSuggestionVisible(), "Suggestion tooltip still visible after click");
    }

    /*#??. Field is editable after suggestion has been used*/
    @Test(groups = {"registration","regression"})
    public void usernameSuggestionEditableAfterFill(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        String username=DataContainer.getUserData().getRegisteredUserData().getUsername();
        registrationPage.clickUsernameSuggestionAndValidateInput(username, DataContainer.getUserData().getRandomUserData());
        String newUsername = DataContainer.getUserData().getRandomUserData().getUsername();
        RegistrationPage.fillUsername(newUsername);
        assertEquals(newUsername, registrationPage.getFilledUsername(), "Username can be reentered");
    }

    /*#??. Suggestions are done on each Username field refocus*/
    @Test(groups = {"registration","regression"})
    public void usernameSuggestionAppearsEveryTime(){
        RegistrationPage registrationPage = (RegistrationPage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.register);
        String username=DataContainer.getUserData().getRegisteredUserData().getUsername();
        registrationPage.clickUsernameSuggestionAndValidateInput(username, DataContainer.getUserData().getRandomUserData());
        String newUsername = DataContainer.getUserData().getRandomUserData().getUsername();
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
