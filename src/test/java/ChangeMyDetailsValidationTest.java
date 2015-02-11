import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.changeMyDetails.ChangeMyDetailsPage;
import springConstructors.ValidationRule;
import utils.NavigationUtils;
import utils.core.AbstractTest;


public class ChangeMyDetailsValidationTest extends AbstractTest {

    @Autowired
    @Qualifier("countryValidationRule")
    private ValidationRule countryValidationRule;

    @Autowired
    @Qualifier("emailValidationRule")
    private ValidationRule emailValidationRule;

    @Autowired
    @Qualifier("fullPhoneValidationRule")
    private ValidationRule fullPhoneValidationRule;

    @Autowired
    @Qualifier("fullAddressValidationRule")
    private ValidationRule fullAddressValidationRule;

    @Autowired
    @Qualifier("postcodeValidationRule")
    private ValidationRule postcodeValidationRule;

    @Autowired
    @Qualifier("cityValidationRule")
    private ValidationRule cityValidationRule;

    /*1. Country field validation*/
    //@Test(groups = {"validation"})
    public void countryFieldValidation() {
        ChangeMyDetailsPage changeMyDetailsPage = (ChangeMyDetailsPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.updateMyDetails);
        changeMyDetailsPage.validateCountry(countryValidationRule);
    }

    /*2. Address field validation*/
    //@Test(groups = {"validation"})
    public void addressFieldValidation() {
        ChangeMyDetailsPage changeMyDetailsPage = (ChangeMyDetailsPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.updateMyDetails);
        changeMyDetailsPage.validateAddress(fullAddressValidationRule);
    }

    /*3. City field validation*/
    //@Test(groups = {"validation"})
    public void cityFieldValidation() {
        ChangeMyDetailsPage changeMyDetailsPage = (ChangeMyDetailsPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.updateMyDetails);
        changeMyDetailsPage.validateCity(cityValidationRule);
    }

    /*4. Post Code field validation*/
    @Test(groups = {"validation", "regression"})
    public void postCodeFieldValidation() {
        ChangeMyDetailsPage changeMyDetailsPage = (ChangeMyDetailsPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.updateMyDetails);
        changeMyDetailsPage.validatePostcode(postcodeValidationRule);
    }

    /*5. Phone field validation*/
    //@Test(groups = {"validation"})
    //Field 'mobile phone' is absent now
    public void phoneFieldValidation() {
        ChangeMyDetailsPage changeMyDetailsPage = (ChangeMyDetailsPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.updateMyDetails);
        changeMyDetailsPage.validatePhone(fullPhoneValidationRule);
    }

    /*6. Mobile Field validation*/
    @Test(groups = {"validation"})
    public void mobileFieldValidation() {
        skipTest("D-18909");
        ChangeMyDetailsPage changeMyDetailsPage = (ChangeMyDetailsPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.updateMyDetails);
        changeMyDetailsPage.validateMobile(fullPhoneValidationRule);
    }

    /*7. Email field validation*/
    @Test(groups = {"validation", "regression"})
    public void emailFieldValidation() {
        ChangeMyDetailsPage changeMyDetailsPage = (ChangeMyDetailsPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.updateMyDetails);
        changeMyDetailsPage.validateEmail(emailValidationRule);
    }
}