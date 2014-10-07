package pageObjects.registration.threeStep;

import pageObjects.registration.RegistrationPage;
import springConstructors.UserData;
import springConstructors.ValidationRule;
import utils.WebDriverUtils;
import utils.validation.ValidationUtils;

public class RegistrationPageStepTwo extends RegistrationPage {

    private static final String ROOT_XP = 											"//*[contains(@class, 'portlet-registration__step')][2]";
    private final static String BUTTON_NEXT_XP=                                     ROOT_XP + "//button[contains(@class, 'fn-next')]";
    private final static String BUTTON_PREVIOUS_XP=                                 ROOT_XP + "//button[contains(@class, 'fn-prev')]";
    private final static String FIELD_PHONE_MOBILE_VALIDATION_NAME = 				"phone";
    private final static String FIELD_PHONE_MOBILE_NAME = 					        "cellphone";
    private final static String FIELD_PHONE_COUNTRY_CODE_NAME  = 					"area";



    public RegistrationPageStepTwo(){
		super(new String[]{ROOT_XP, BUTTON_NEXT_XP, BUTTON_PREVIOUS_XP});
	}

    public RegistrationPageStepThree fillDataAndSubmit(UserData userData){
        fillAddress(userData.getAddress());
        fillCity(userData.getCity());
        fillPostCode(userData.getPostCode());
        fillCountry(userData.getCountry());
        fillMobile(userData.getPhone());
        fillPhoneAreaCode(userData.getPhoneAreaCode());
        WebDriverUtils.waitFor(1000);
        clickNext();
        return new RegistrationPageStepThree();
    }

    public static void clickNext(){
        WebDriverUtils.click(BUTTON_NEXT_XP);
    }

    private static RegistrationPageStepOne clickPrevious(){
        WebDriverUtils.click(BUTTON_PREVIOUS_XP);
        return new RegistrationPageStepOne();
    }

    private static void fillMobile(String text){
        WebDriverUtils.clearAndInputTextToField(getXpathByName(FIELD_PHONE_MOBILE_NAME), text);
    }

    public static void fillPhoneAreaCode(String phoneAreaCode){
        WebDriverUtils.clearAndInputTextToField(getXpathByName(FIELD_PHONE_COUNTRY_CODE_NAME), phoneAreaCode);
    }

    public void validatePhoneAreaCodeField(ValidationRule rule) {
        ValidationUtils.validateField(getXpathByName(FIELD_PHONE_COUNTRY_CODE_NAME), rule, FIELD_PHONE_COUNTRY_CODE_NAME);
    }

    public void validatePhoneField(ValidationRule rule) {
        ValidationUtils.validateField(getXpathByName(FIELD_PHONE_MOBILE_NAME), rule, FIELD_PHONE_MOBILE_VALIDATION_NAME);
    }
}
