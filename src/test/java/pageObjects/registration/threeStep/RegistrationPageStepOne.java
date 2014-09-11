package pageObjects.registration.threeStep;

import pageObjects.core.AbstractPage;
import pageObjects.core.AbstractPageObject;
import pageObjects.registration.RegistrationPage;
import springConstructors.UserData;
import utils.WebDriverUtils;


/**
 * User: ivan
 * Date: 7/31/13
 */

public class RegistrationPageStepOne extends RegistrationPage {

    private final static String ROOT_XP = 											"//*[contains(@class, 'portlet-registration__step')][1]";
    private final static String FIELD_FIRSTNAME_XP =				 				ROOT_XP + "//input[@name='firstname']";
    private final static String FIELD_LASTNAME_XP = 								ROOT_XP + "//input[@name='lastname']";
	private final static String DROPDOWN_BIRTHDAY_XP=								ROOT_XP + "//select[@id='birthDay']";
	private final static String DROPDOWN_BIRTHMONTH_XP=								ROOT_XP + "//select[@id='birthMonth']";
	private final static String DROPDOWN_BIRTHYEAR_XP=								ROOT_XP + "//select[@id='birthYear']";
	private final static String DROPDOWN_GENDER_XP=									ROOT_XP + "//select[@name='sex']";
    private final static String FIELD_EMAIL_XP = 									ROOT_XP + "//input[@name='email']";
	private final static String LINK_ADULT_CONTENT_XP=								ROOT_XP + "//a[@data-article-id='18PLUS']";
    private final static String BUTTON_NEXT_XP=                                     ROOT_XP + "//button[contains(@class, 'fn-next')]";
    private final static String LABEL_TOOLTIP_XP=                                   ROOT_XP + "//*[@class='error-tooltip']";

    public RegistrationPageStepOne(){
        super(new String[]{ROOT_XP, BUTTON_NEXT_XP, LINK_ADULT_CONTENT_XP, FIELD_EMAIL_XP,
                DROPDOWN_GENDER_XP, DROPDOWN_BIRTHYEAR_XP,DROPDOWN_BIRTHMONTH_XP,
                DROPDOWN_BIRTHDAY_XP,FIELD_LASTNAME_XP,FIELD_FIRSTNAME_XP});
    }

    public static AbstractPageObject registerUser(UserData userData){
        return fillDataAndSubmit(userData).fillDataAndSubmit(userData).fillDataAndSubmit(userData);
    }

    private static RegistrationPageStepTwo fillDataAndSubmit(UserData userData){
        fillFirstname(userData.getFirstName());
        fillLastname(userData.getLastName());
        setBirthDay(userData.getBirthDay());
        setBirthMonth(userData.getBirthMonth());
        setBirthYear(userData.getBirthYear());
        setGender(userData.getGender());
        fillEmail(userData.getEmail());
        WebDriverUtils.waitFor(1000);
        clickNext();
        return new RegistrationPageStepTwo();
    }

    private static void clickNext(){
        WebDriverUtils.click(BUTTON_NEXT_XP);
    }

    private static void fillFirstname(String text){
        WebDriverUtils.clearAndInputTextToField(FIELD_FIRSTNAME_XP, text);
    }

    private static void fillLastname(String text){
        WebDriverUtils.clearAndInputTextToField(FIELD_LASTNAME_XP, text);
    }

    private static void setBirthDay(String text){
        WebDriverUtils.setDropdownOptionByValue(DROPDOWN_BIRTHDAY_XP, text);
    }

    private static void setBirthMonth(String text){
        WebDriverUtils.setDropdownOptionByValue(DROPDOWN_BIRTHMONTH_XP, text);
    }

    private static void setBirthYear(String text){
        WebDriverUtils.setDropdownOptionByValue(DROPDOWN_BIRTHYEAR_XP, text);
    }

    private static void setGender(String text){
        WebDriverUtils.setDropdownOptionByValue(DROPDOWN_GENDER_XP, text);
    }

    private static void fillEmail(String text){
        WebDriverUtils.clearAndInputTextToField(FIELD_EMAIL_XP, text);
    }
}
