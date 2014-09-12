import enums.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import springConstructors.Defaults;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.TypeUtils;
import utils.core.AbstractTest;
import java.util.Collection;

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
        Collection<String> actualCountriesCodesList = homePage.getLanguageCodesList();
        Collection<String> diff=TypeUtils.getDiffElementsFromLists(actualCountriesCodesList, defaults.getLanguageList());
        TypeUtils.assertTrueWithLogs(diff.isEmpty());
    }

}
