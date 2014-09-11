package pageObjects.registration.threeStep;

import pageObjects.registration.RegistrationPage;
import springConstructors.UserData;
import utils.WebDriverUtils;

public class RegistrationPageStepTwo extends RegistrationPage {

    private static final String ROOT_XP = 											"//*[contains(@class, 'portlet-registration__step')][2]";
    private final static String BUTTON_NEXT_XP=                                     ROOT_XP + "//button[contains(@class, 'fn-next')]";
    private final static String BUTTON_PREVIOUS_XP=                                 ROOT_XP + "//button[contains(@class, 'fn-prev')]";
    private final static String FIELD_MOBILE_XP = 									ROOT_XP + "//*[@name='cellphone']";

    public RegistrationPageStepTwo(){
		super(new String[]{ROOT_XP, BUTTON_NEXT_XP, BUTTON_PREVIOUS_XP, FIELD_MOBILE_XP, });
	}

    public static RegistrationPageStepThree fillDataAndSubmit(UserData userData){
        fillAddress(userData.getAddress());
        fillCity(userData.getCity());
        fillPostCode(userData.getPostCode());
        fillCountry(userData.getCountry());
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

    private static void fillMobile(String text){
        WebDriverUtils.clearAndInputTextToField(FIELD_MOBILE_XP, text);
    }
}
