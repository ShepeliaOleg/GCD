import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import springConstructors.Defaults;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

public class LanguageTest extends AbstractTest {

    @Autowired
    @Qualifier("userData")
    private UserData defaultUserData;

    @Autowired
    @Qualifier("defaults")
    private Defaults defaults;

	/*POSITIVE*/

    /*#1. The list of supported languages is correct*/
    @Test(groups = {"regression"})
    public void countryList(){
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.home);
        assertEqualsCollections(defaults.getLanguageCodesList(), homePage.getLanguageCodesList(), "Language list corresponds with config");
    }

    /*#2. */
    @Test(groups = {"regression"})
    public void urlUpdatedOnLanguageChange(){
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.home);
        for (String languageCode : defaults.getLanguageCodesList()) {
            homePage.setLanguage(languageCode);
            assertEquals(defaults.getLanguageUrlByLanguageCode(languageCode), WebDriverUtils.getCurrentUrl().replace(WebDriverUtils.getBaseUrl(), "").replace(ConfiguredPages.home.toString(), ""), "Url for " + defaults.getLanguageNameByCode(languageCode) + " (" + languageCode + ") language.");
        }
    }

//    @Test(groups = {"regression"})
//    public void languageCookieFirstTimePlayerGuest(){
//        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
//        String languageCode = defaults.getRandomLanguageCode();
//        LanguageCookie languageCookie = new LanguageCookie();
//        TypeUtils.assertFalseWithLogs(languageCookie.isPresent(), "Language cookie is created for first time guest.");
//        homePage.setLanguage(languageCode);
//        TypeUtils.assertTrueWithLogs(languageCookie.isPresent(), "Language cookie is not created after selecting language.");
//        TypeUtils.assertEqualsWithLogs(languageCookie.getValue(), languageCode, "");
//    }
//    @Test(groups = {"regression"})
//    public void languageCookieFirstTimePlayerLoggedIn(){
//        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.loggedIn, ConfiguredPages.home);
//        TypeUtils.assertFalseWithLogs(defaults.languageCookieExists(), "");
//        String languageCode = defaults.getRandomLanguageCode();
//        //homePage.setLanguage(languageCode);
//        TypeUtils.assertTrueWithLogs(defaults.languageCookieExists(), "");
//        TypeUtils.assertEqualsWithLogs(defaults.getLanguageCookieValue(), languageCode, "");
//    }
//
//    @Test(groups = {"regression"})
//    public void languageCookie(){
//        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(PlayerCondition.any, ConfiguredPages.home);
//        Collection<String> languageCodesList = defaults.getLanguageCodesList();
//        for (String languageCode: languageCodesList) {
//            WebDriverUtils.clearCookies();
//            defaults.addLanguageCookie(languageCode);
//            WebDriverUtils.refreshPage();
//            //       TypeUtils.assertEqualsWithLogs(homePage.header(). , defaults.getLanguageValue(languageCode), "");
//        }
//    }
}