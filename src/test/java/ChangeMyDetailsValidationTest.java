import enums.ConfiguredPages;
import enums.Licensee;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.account.ChangeMyDetailsPage;
import pageObjects.registration.RegistrationPage;
import springConstructors.UserData;
import springConstructors.ValidationRule;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;
import utils.validation.ValidationUtils;


public class ChangeMyDetailsValidationTest extends AbstractTest {


    @Autowired
    @Qualifier("emailValidationRule")
    private ValidationRule emailValidationRule;

    @Autowired
    @Qualifier("fullPhoneValidationRule")
    private ValidationRule fullPhoneValidationRule;

    @Autowired
    @Qualifier("fullMobileValidationRule")
    private ValidationRule fullMobileValidationRule;

    @Autowired
    @Qualifier("fullAddressValidationRule")
    private ValidationRule addressValidationRule;

    @Autowired
    @Qualifier("postcodeValidationRule")
    private ValidationRule postcodeValidationRule;

    @Autowired
    @Qualifier("cityValidationRule")
    private ValidationRule cityValidationRule;

    /*2. Address field validation*/
    @Test(groups = {"validation"})
    public void addressFieldValidation() {
        ChangeMyDetailsPage changeMyDetailsPage = (ChangeMyDetailsPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.updateMyDetails);
        changeMyDetailsPage.validateAddress(addressValidationRule);
    }

    /*3. City field validation*/
    @Test(groups = {"validation"})
    public void cityFieldValidation() {
        ChangeMyDetailsPage changeMyDetailsPage = (ChangeMyDetailsPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.updateMyDetails);
        changeMyDetailsPage.validateCity(cityValidationRule);
    }

    /*4. Post Code field validation*/
    @Test(groups = {"validation"})
    public void postCodeFieldValidation() {
        ChangeMyDetailsPage changeMyDetailsPage = (ChangeMyDetailsPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.updateMyDetails);
        changeMyDetailsPage.validatePostcode(postcodeValidationRule);
    }

    /*5. Phone field validation*/
    @Test(groups = {"validation"})
    public void phoneFieldValidation() {
        ChangeMyDetailsPage changeMyDetailsPage = (ChangeMyDetailsPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.updateMyDetails);
        changeMyDetailsPage.validatePhone(fullPhoneValidationRule);
    }

    /*6. Mobile Field validation*/
    @Test(groups = {"validation"})
    public void mobileFieldValidation() {
        ChangeMyDetailsPage changeMyDetailsPage = (ChangeMyDetailsPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.updateMyDetails);
        changeMyDetailsPage.validateMobile(fullMobileValidationRule);
    }

    /*7. Email field validation*/
    @Test(groups = {"validation"})
    public void emailFieldValidation() {
        ChangeMyDetailsPage changeMyDetailsPage = (ChangeMyDetailsPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.updateMyDetails);
        changeMyDetailsPage.validateEmail(emailValidationRule);
    }

    /*8. Email Verification field validation*/
    @Test(groups = {"validation"})
    public void verificationEmailFieldValidation() {
        ChangeMyDetailsPage changeMyDetailsPage = (ChangeMyDetailsPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.updateMyDetails);
        changeMyDetailsPage.validateVerificationEmail(emailValidationRule);
    }

    	/*NEGATIVE CASES*/

    /*#4. Email and email confirmation do not match*/
//    @Test(groups = {"registration", "regression", "validation"})
//    public void emailConfirmationValidation() {
//        String id = ChangeMyDetailsPage.getEmailVerificationName();
//        String email = DataContainer.getUserData().getRandomUserData().getEmail();
//        ChangeMyDetailsPage changeMyDetailsPage = (ChangeMyDetailsPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.updateMyDetails);
//        changeMyDetailsPage.setEmail(email);
//        ValidationUtils.inputFieldAndRefocus(ChangeMyDetailsPage.getEmailVerificationXpath(), email);
//        ValidationUtils.validateStatusAndToolTips(ValidationUtils.STATUS_NONE, id, email, ValidationUtils.STATUS_PASSED, ValidationUtils.STATUS_NONE);
//    }
//
//    @Test(groups = {"registration", "regression"})
//    public void emailDoNotMatch() {
//        ChangeMyDetailsPage changeMyDetailsPage = (ChangeMyDetailsPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.updateMyDetails);
//        changeMyDetailsPage.setEmail(DataContainer.getUserData().getRandomUserData().getEmail());
//        changeMyDetailsPage.setEmailVerification(emailValidationRule.generateValidString());
//        assertEquals("Emails dont match", ValidationUtils.getTooltipText(ChangeMyDetailsPage.getEmailVerificationName()), "Tooltip text");
//    }
}