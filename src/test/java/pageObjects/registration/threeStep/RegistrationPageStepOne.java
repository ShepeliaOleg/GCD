package pageObjects.registration.threeStep;

import pageObjects.registration.AdultContentPopup;
import pageObjects.registration.RegistrationPage;
import springConstructors.UserData;
import utils.WebDriverUtils;


/**
 * User: ivan
 * Date: 7/31/13
 */

public class RegistrationPageStepOne extends RegistrationPage {

    private final static String ROOT_XP = 											"//*[contains(@class, 'portlet-registration__step')][1]";
    private final static String DROPDOWN_TITLE_NAME =                               "title";
    public final static String FIELD_EMAIL_VERIFICATION_NAME = 					"emailVerify";
    private final static String BUTTON_NEXT_XP=                                     ROOT_XP + "//button[contains(@class, 'fn-next')]";
    protected final static String LINK_ADULT_CONTENT_XP=							"//*[@data-article-id='18PLUS']";

    public RegistrationPageStepOne(){
        super(new String[]{ROOT_XP, BUTTON_NEXT_XP, LINK_ADULT_CONTENT_XP});
    }

    public static void registerNewUser(UserData userData, boolean termsAndConditions, boolean promotions, String bonusCode){
        fillDataAndSubmit(userData).fillDataAndSubmit(userData).fillDataAndSubmit(userData, termsAndConditions, promotions, bonusCode);
    }

    public static RegistrationPageStepTwo fillDataAndSubmit(UserData userData){
        fillTitle(userData.getTitle());
        fillFirstName(userData.getFirstName());
        fillLastName(userData.getLastName());
        fillBirthDay(userData.getBirthDay());
        fillBirthMonth(userData.getBirthMonth());
        fillBirthYear(userData.getBirthYear());
        fillGender(userData.getGender());
        fillEmail(userData.getEmail());
        fillEmailVerification(userData.getEmail());
        WebDriverUtils.waitFor(1000);
        clickNext();
        return new RegistrationPageStepTwo();
    }

    private static void fillTitle(String title){
        WebDriverUtils.setDropdownOptionByValue(getXpathByName(DROPDOWN_TITLE_NAME), title);
    }

    public static void fillEmailVerification(String confirmEmail){
        WebDriverUtils.clearAndInputTextToField(getXpathByName(FIELD_EMAIL_VERIFICATION_NAME), confirmEmail);
    }

    public AdultContentPopup clickAdultContent(){
        WebDriverUtils.click(LINK_ADULT_CONTENT_XP);
        return new AdultContentPopup();
    }

    private static void clickNext(){
        WebDriverUtils.click(BUTTON_NEXT_XP);
    }

}
