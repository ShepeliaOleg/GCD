import enums.ConfiguredPages;
import enums.Page;
import enums.PlayerCondition;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.core.AbstractPageObject;
import pageObjects.login.WelcomePopup;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

public class LanguageTest extends AbstractTest {

    private int TIMEOUT = 3000;

	/*POSITIVE*/

    /*#2. The list of supported languages is correct*/
    @Test(groups = {"regression"})
    public void countryList(){
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.home);
        assertEqualsCollections(DataContainer.getDefaults().getLanguageCodesList(), homePage.getLanguageCodesList(), "Language list corresponds with config");
    }

    /*#3. */
    @Test(groups = {"regression"})
    public void languageChangedOnAddingLanguageCode2ToUrl(){
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.home);
        for (String languageCode : DataContainer.getDefaults().getLanguageCodesList()) {
            String shortLanguageCode = DataContainer.getDefaults().getLanguageUrlByLanguageCode(languageCode);
            if (shortLanguageCode.length() == 2) {
                WebDriverUtils.navigateToInternalURL(shortLanguageCode);
                WebDriverUtils.waitFor(TIMEOUT);
                assertLanguageChange(homePage, languageCode);
            }
        }
    }

    /*#4. */
    @Test(groups = {"regression"})
    public void languageChangedOnAddingLanguageCode5ToUrl(){
        skipTestWithIssues("D-14495");
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.home);
        for (String languageCode : DataContainer.getDefaults().getLanguageCodesList()) {
            WebDriverUtils.navigateToInternalURL(languageCode);
            WebDriverUtils.waitFor(TIMEOUT);
            assertLanguageChange(homePage, languageCode);
        }
    }

    /*#7. */
    @Test(groups = {"regression"})
    public void pageOpenedInDefaultLanguage(){
        WebDriverUtils.clearLocalStorage();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.home);
        WebDriverUtils.refreshPage();
        assertLanguageChange(homePage, DataContainer.getDefaults().getDefaultLanguage());
    }

    /*#9. */
    @Test(groups = {"regression"})
    public void urlUpdatedOnLanguageChange(){
        skipTestWithIssues("D-14495");
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.home);
        for (String languageCode : DataContainer.getDefaults().getLanguageCodesList()) {
            homePage.setLanguage(languageCode);
            assertLanguageChange(homePage, languageCode);
        }
    }

    /*#10. */
    @Test(groups = {"regression", "login", "mobile"})
    public void loginAfterLanguageChangeWelcomeMessage(){
        skipTestWithIssues("D-14844");
        UserData userData = DataContainer.getUserData().getRegisteredUserData();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        for (String languageCode : DataContainer.getDefaults().getLanguageCodesList()) {
            homePage.setLanguage(languageCode);
            WelcomePopup welcomePopup = (WelcomePopup) homePage.login(userData, Page.welcomePopup);
            assertLanguageChange(welcomePopup, languageCode);
            welcomePopup.closePopup();
            PortalUtils.logout();
        }
    }

    private void assertLanguageChange(AbstractPageObject page, String languageCode) {
        String languageName =       DataContainer.getDefaults().getLanguageNameByCode(languageCode);
        String shortLanguageCode =  DataContainer.getDefaults().getLanguageUrlByLanguageCode(languageCode);
        if (languageCode.equals(DataContainer.getDefaults().getDefaultLanguage())) {
            shortLanguageCode = "";
        }
        assertEquals(shortLanguageCode, WebDriverUtils.getCurrentLanguageCode(), "Url for " + languageName + " (" + languageCode + ")");

        assertEquals(DataContainer.getDefaults().getLanguageTranslationByLanguageCode(languageCode), page.getTranslationText(), "Translation for " + languageName + " (" + languageCode + ")");
    }

}