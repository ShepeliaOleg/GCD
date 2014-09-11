package pageObjects.registration.threeStep;

import pageObjects.base.AbstractPage;
import springConstructors.UserData;
import utils.WebDriverUtils;

/**
 * User: ivan
 * Date: 7/31/13
 */

public class RegistrationPageStepTwo extends AbstractPage{

    private static final String ROOT_XP = 											"//*[contains(@class, 'portlet-registration__step')][2]";
    private final static String BUTTON_NEXT_XP=                                     ROOT_XP + "//button[contains(@class, 'fn-next')]";
    private final static String BUTTON_PREVIOUS_XP=                                 ROOT_XP + "//button[contains(@class, 'fn-prev')]";
    private final static String FIELD_ADDRESS_XP = 								    ROOT_XP + "//*[@name='address']";
    private final static String FIELD_CITY_XP = 									ROOT_XP + "//*[@name='city']";
    private final static String FIELD_ZIP_XP = 									    ROOT_XP + "//*[@name='zip']";
    private final static String FIELD_MOBILE_XP = 									ROOT_XP + "//*[@name='cellphone']";
    private final static String DROPDOWN_COUNTRY_XP = 								ROOT_XP + "//*[@name='countrycode']";
    private final static String LABEL_TOOLTIP_XP=                                   ROOT_XP + "//*[@class='error-tooltip']";

    public RegistrationPageStepTwo(){
		super(new String[]{ROOT_XP, BUTTON_NEXT_XP, BUTTON_PREVIOUS_XP, FIELD_ADDRESS_XP, FIELD_CITY_XP,
                FIELD_MOBILE_XP, DROPDOWN_COUNTRY_XP});
	}

    public static RegistrationPageStepThree fillDataAndSubmit(UserData userData){
        fillAddress(userData.getAddress());
        fillCity(userData.getCity());
        fillZip(userData.getPostCode());
        setCountry(userData.getCountry());
        fillMobile(userData.getMobile());
        WebDriverUtils.waitFor(1000);
        clickNext();
        return new RegistrationPageStepThree();
    }

    private static void clickNext(){
        WebDriverUtils.click(BUTTON_NEXT_XP);
    }

    private static RegistrationPageStepOne clickPrevious(){
        WebDriverUtils.click(BUTTON_PREVIOUS_XP);
        return new RegistrationPageStepOne();
    }

    private static void fillAddress(String text){
        WebDriverUtils.clearAndInputTextToField(FIELD_ADDRESS_XP, text);
    }

    private static void fillCity(String text){
        WebDriverUtils.clearAndInputTextToField(FIELD_CITY_XP, text);
    }

    private static void fillZip(String text){
        WebDriverUtils.clearAndInputTextToField(FIELD_ZIP_XP, text);
    }

    private static void setCountry(String text){
        WebDriverUtils.setDropdownOptionByValue(DROPDOWN_COUNTRY_XP, text);
    }

    private static void fillMobile(String text){
        WebDriverUtils.clearAndInputTextToField(FIELD_MOBILE_XP, text);
    }
}
