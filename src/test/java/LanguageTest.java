import enums.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.HomePage;
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
                assertEquals(WebDriverUtils.getBaseUrl() + shortLanguageCode, WebDriverUtils.getCurrentUrl().replace("/" + ConfiguredPages.home.toString(), ""), "Url for " + defaults.getLanguageNameByCode(languageCode) + " (" + languageCode + ") language.");
                assertEquals(defaults.getLanguageTranslationByLanguageCode(languageCode), homePage.getFooterText(), "Translation of login button for " + defaults.getLanguageNameByCode(languageCode) + " (" + languageCode + ") language.");
            }
        }
    }

    /*#4. */
    @Test(groups = {"regression"})
    public void languageChangedOnAddingLanguageCode5ToUrl(){
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.home);
        for (String languageCode : defaults.getLanguageCodesList()) {
            String shortLanguageCode = defaults.getLanguageUrlByLanguageCode(languageCode);
            WebDriverUtils.navigateToInternalURL(languageCode);
            WebDriverUtils.waitFor(TIMEOUT);
            assertEquals(WebDriverUtils.getBaseUrl() + shortLanguageCode, WebDriverUtils.getCurrentUrl().replace("/" + ConfiguredPages.home.toString(), ""), "Url for " + defaults.getLanguageNameByCode(languageCode) + " (" + languageCode + ") language.");
            assertEquals(defaults.getLanguageTranslationByLanguageCode(languageCode), homePage.getFooterText(), "Translation of login button for " + defaults.getLanguageNameByCode(languageCode) + " (" + languageCode + ") language.");
        }
    }

    /*#7. */
    @Test(groups = {"regression"})
    public void pageOpenedInDefaultLanguage(){
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.home);
        String defaultLanguageCode = defaults.getDefaultLanguage();
        assertEquals(WebDriverUtils.getBaseUrl(), WebDriverUtils.getCurrentUrl().replace(ConfiguredPages.home.toString(), ""), "Url for default language " + defaults.getLanguageNameByCode(defaultLanguageCode) + " (" + defaultLanguageCode + ").");
        assertEquals(defaults.getLanguageTranslationByLanguageCode(defaultLanguageCode), homePage.getFooterText(), "Translation of login button for default language" + defaults.getLanguageNameByCode(defaultLanguageCode) + " (" + defaultLanguageCode + ").");
    }

    /*#9. */
    @Test(groups = {"regression"})
    public void urlUpdatedOnLanguageChange(){
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.home);
        for (String languageCode : defaults.getLanguageCodesList()) {
            String shortLanguageCode = defaults.getLanguageUrlByLanguageCode(languageCode);
            homePage.setLanguage(languageCode);
            assertEquals(WebDriverUtils.getBaseUrl() + shortLanguageCode, WebDriverUtils.getCurrentUrl().replace("/" + ConfiguredPages.home.toString(), ""), "Url for " + defaults.getLanguageNameByCode(languageCode) + " (" + languageCode + ") language.");
            assertEquals(defaults.getLanguageTranslationByLanguageCode(languageCode), homePage.getFooterText(), "Translation of login button for " + defaults.getLanguageNameByCode(languageCode) + " (" + languageCode + ") language.");
        }
    }

    /*#10. */
    @Test(groups = {"regression"})
    public void loginAfterLanguageChange(){
        UserData userData = defaultUserData.getRegisteredUserData();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.home);
        for (String languageCode : defaults.getLanguageCodesList()) {
            String shortLanguageCode = defaults.getLanguageUrlByLanguageCode(languageCode);
            homePage.setLanguage(languageCode);
            PortalUtils.loginUser(userData);
            assertEquals(WebDriverUtils.getBaseUrl() + shortLanguageCode, WebDriverUtils.getCurrentUrl().replace("/" + ConfiguredPages.home.toString(), ""), "Url for " + defaults.getLanguageNameByCode(languageCode) + " (" + languageCode + ") language.");
            assertEquals(defaults.getLanguageTranslationByLanguageCode(languageCode), homePage.getFooterText(), "Translation of login button for " + defaults.getLanguageNameByCode(languageCode) + " (" + languageCode + ") language.");
            PortalUtils.logout();
        }
    }
}