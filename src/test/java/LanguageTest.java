import enums.ConfiguredPages;
import enums.Page;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.core.AbstractPageObject;
import pageObjects.login.WelcomePopup;
import springConstructors.Defaults;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

public class LanguageTest extends AbstractTest {

    private int TIMEOUT = 3000;

    @Autowired
    @Qualifier("userData")
    private UserData defaultUserData;

    @Autowired
    @Qualifier("defaults")
    private Defaults defaults;

	/*POSITIVE*/

    /*#2. The list of supported languages is correct*/
    @Test(groups = {"regression"})
    public void countryList(){
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.home);
        assertEqualsCollections(defaults.getLanguageCodesList(), homePage.getLanguageCodesList(), "Language list corresponds with config");
    }

    /*#3. */
    @Test(groups = {"regression"})
    public void languageChangedOnAddingLanguageCode2ToUrl(){
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.home);
        for (String languageCode : defaults.getLanguageCodesList()) {
            String shortLanguageCode = defaults.getLanguageUrlByLanguageCode(languageCode);
            if (shortLanguageCode.length() == 2) {
                WebDriverUtils.navigateToInternalURL(shortLanguageCode);
                WebDriverUtils.waitFor(TIMEOUT);
                assertLanguageChange(homePage, languageCode, shortLanguageCode);
            }
        }
    }

    /*#4. */
    @Test(groups = {"regression"})
    public void languageChangedOnAddingLanguageCode5ToUrl(){
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.home);
        for (String languageCode : defaults.getLanguageCodesList()) {
            WebDriverUtils.navigateToInternalURL(languageCode);
            WebDriverUtils.waitFor(TIMEOUT);
            assertLanguageChange(homePage, languageCode, defaults.getLanguageUrlByLanguageCode(languageCode));
        }
    }

    /*#7. */
    @Test(groups = {"regression"})
    public void pageOpenedInDefaultLanguage(){
        WebDriverUtils.clearCookies();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.home);
        assertLanguageChange(homePage, defaults.getDefaultLanguage(), "");
    }

    /*#9. */
    @Test(groups = {"regression"})
    public void urlUpdatedOnLanguageChange(){
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.home);
        for (String languageCode : defaults.getLanguageCodesList()) {
            homePage.setLanguage(languageCode);
            assertLanguageChange(homePage, languageCode, defaults.getLanguageUrlByLanguageCode(languageCode));
        }
    }

    /*#10. */
    @Test(groups = {"regression, login, mobile"})
    public void loginAfterLanguageChangeWelcomeMessage(){
        UserData userData = defaultUserData.getRegisteredUserData();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.home);
        for (String languageCode : defaults.getLanguageCodesList()) {
            homePage.setLanguage(languageCode);
            WelcomePopup welcomePopup = (WelcomePopup) homePage.login(userData, Page.welcomePopup);
            assertLanguageChange(welcomePopup, languageCode, defaults.getLanguageUrlByLanguageCode(languageCode));
            welcomePopup.closePopup();
            PortalUtils.logout();
        }
    }

    private void assertLanguageChange(AbstractPageObject page, String languageCode, String shortLanguageCode) {
        String languageName = defaults.getLanguageNameByCode(languageCode);
        assertEquals(shortLanguageCode, WebDriverUtils.getCurrentLanguageCode(), "Url for " + languageName + " (" + languageCode + ")");

        assertEquals(defaults.getLanguageTranslationByLanguageCode(languageCode), page.getTranslationText(), "Translation for " + languageName + " (" + languageCode + ")");
    }

}