package pageObjects.registration.threeStep;

import enums.PromoCode;
import pageObjects.registration.AdultContentPopup;
import pageObjects.registration.RegistrationPage;
import springConstructors.UserData;
import utils.Locator;
import utils.WebDriverUtils;

public class RegistrationPageStepOne extends RegistrationPage {

    private final static String ROOT_XP = 											"//*[contains(@class, 'portlet-registration__step')][1]";
    private final static String DROPDOWN_TITLE_NAME =                               "title";
    public final static String FIELD_EMAIL_VERIFICATION_NAME = 					    "emailVerify";
    private final static String BUTTON_NEXT_XP=                                     ROOT_XP + "//button[contains(@class, 'fn-next')]";
    protected final static Locator LINK_ADULT_CONTENT_XP=							new Locator("fn-popup-open", "//*[@data-article-id='18PLUS']", ".fn-register-step:nth-child(1) .fn-popup-open");

    public RegistrationPageStepOne(){
        super(new String[]{ROOT_XP, BUTTON_NEXT_XP, LINK_ADULT_CONTENT_XP.getXpath()});
    }

    public void registerNewUser(UserData userData, boolean termsAndConditions, boolean promotions, PromoCode promoCode){
        fillDataAndSubmit(userData).fillDataAndSubmit(userData).fillDataAndSubmit(userData, termsAndConditions, promotions, promoCode);
    }

    public RegistrationPageStepTwo fillDataAndSubmit(UserData userData){
        fillFirstName(userData.getFirstName());
        fillLastName(userData.getLastName());
        fillTitle(userData.getTitle());
        fillBirthDay(userData.getBirthDay());
        fillBirthMonth(userData.getBirthMonth());
        fillBirthYear(userData.getBirthYear());
        fillEmail(userData.getEmail());
        fillEmailVerification(userData.getEmail());
        fillGender(userData.getGender());
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

    public void clickNext(){
        WebDriverUtils.click(BUTTON_NEXT_XP);
    }

    @Override
    public void copyAndPasteEmail() {
        WebDriverUtils.copy(getXpathByName(FIELD_EMAIL_NAME));
        WebDriverUtils.paste(getXpathByName(FIELD_EMAIL_VERIFICATION_NAME));
    }

    @Override
    public String getEmailVerification() {
        return WebDriverUtils.getElementText(getXpathByName(FIELD_EMAIL_VERIFICATION_NAME));
    }
}
